package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.lang.model.element.TypeElement;
import java.lang.constant.ClassDesc;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/// The entry of a type ([Entry]): its steps are the methods javac asks a
/// class that extends or implements the type for — a class with a method per
/// step compiles, and without any one of them does not —, and a type no
/// generated class can extend or implement has none, and tells why.
class EntryTest {

    /// What the entry of a type comes to, and the type as a class that takes its steps is written by.
    private record Read(Entry entry, boolean isInterface, List<String> typeParams) {}

    private static Read read(String type, Path... classpath) {
        return Javac.plain().alone().classpath(classpath).inFirstRound(env -> {
            TypeElement element = env.getElementUtils().getTypeElement(type);
            TypeModel model =
                    switch (new Models(env.getElementUtils(), env.getTypeUtils())
                            .of(element, MemberFilter.DECLARED_ACCESSIBLE)) {
                        case Translation.Ok<TypeModel>(TypeModel value) -> value;
                        case Translation.Deferred<TypeModel> deferred ->
                            throw new IllegalStateException(deferred.toString());
                        case Translation.Unrepresentable<TypeModel> none ->
                            throw new IllegalStateException(none.toString());
                    };
            return new Read(
                    Entry.of(element, model, env.getElementUtils()),
                    element.getKind().isInterface(),
                    model.typeParams().stream()
                            .map(param -> param.name()
                                    + (param.bounds().isEmpty()
                                            ? ""
                                            : " extends "
                                                    + param.bounds().stream()
                                                            .map(EntryTest::java)
                                                            .collect(Collectors.joining(" & "))))
                            .toList());
        });
    }

    /// A class that extends or implements a type with a method for each of `steps`.
    private static String probe(String type, Read read, List<Entry.Step> steps) {
        String declared = read.typeParams().isEmpty() ? "" : "<" + String.join(", ", read.typeParams()) + ">";
        String applied = read.typeParams().isEmpty()
                ? ""
                : read.typeParams().stream()
                        .map(param -> param.split(" ")[0])
                        .collect(Collectors.joining(", ", "<", ">"));
        return "class Probe" + declared + (read.isInterface() ? " implements " : " extends ") + type + applied + " {\n"
                + steps.stream()
                        .map(step -> "    public "
                                + switch (step.method().result()) {
                                    case Heritage.Result.Nothing _ -> "void";
                                    case Heritage.Result.Of(TypeRef result) -> java(result);
                                }
                                + " " + step.method().name()
                                + IntStream.range(0, step.method().params().size())
                                        .mapToObj(
                                                i -> java(step.method().params().get(i)) + " p" + i)
                                        .collect(Collectors.joining(", ", "(", ")"))
                                + " { throw new IllegalStateException(); }\n")
                        .collect(Collectors.joining())
                + "}\n";
    }

    private static String java(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> primitive.name().toLowerCase(Locale.ROOT);
            case ArrayTypeRef array -> java(array.component()) + "[]";
            case TypeVarRef variable -> variable.name();
            case ClassTypeRef cls -> name(cls.desc());
            case ParameterizedTypeRef parameterized ->
                parameterized.args().stream()
                        .map(EntryTest::java)
                        .collect(Collectors.joining(", ", name(parameterized.raw()) + "<", ">"));
        };
    }

    private static String java(TypeArg argument) {
        return switch (argument) {
            case ExactTypeArg exact -> java(exact.type());
            case ExtendsTypeArg bound -> "? extends " + java(bound.bound());
            case SuperTypeArg bound -> "? super " + java(bound.bound());
            case UnboundedTypeArg _ -> "?";
        };
    }

    private static String name(ClassDesc desc) {
        return Models.binaryName(desc).replace('$', '.');
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "java.util.AbstractList",
                "java.util.AbstractMap",
                "java.util.AbstractSet",
                "java.lang.Comparable",
                "java.lang.Runnable",
                "java.util.Iterator",
                "java.lang.CharSequence",
                "java.io.InputStream",
                "java.io.Reader",
                "java.lang.Number",
                "java.util.Map",
                "java.util.concurrent.Callable",
                "java.lang.AutoCloseable",
                "java.util.function.BiFunction",
                "java.util.Comparator",
                "java.lang.Object",
                "java.lang.Exception",
                "java.util.ArrayList"
            })
    void theStepsOfATypeOfTheJdkAreTheMethodsJavacAsksAClassFor(String type) {
        Read read = read(type);

        assertThat(read.entry()).isInstanceOf(Entry.Offered.class);
        List<Entry.Step> steps = ((Entry.Offered) read.entry()).steps();
        // with every step the class compiles: there is no abstract method the steps do not tell
        Compiled complete = Javac.plain().compile(probe(type, read, steps));
        assertThat(complete.errors()).as(probe(type, read, steps)).isEmpty();
        // and without any one of them it does not: every step is of a method javac asks for
        assertThat(IntStream.range(0, steps.size()).boxed()).allSatisfy(left -> {
            List<Entry.Step> others = IntStream.range(0, steps.size())
                    .filter(i -> i != left)
                    .mapToObj(steps::get)
                    .toList();
            assertThat(Javac.plain().compile(probe(type, read, others)).errors())
                    .as("without %s", steps.get(left).name())
                    .anyMatch(error -> error.contains("is not abstract and does not override abstract method "
                            + steps.get(left).method().name() + "("));
        });
    }

    @Test
    void theStepsAreNamedAsTheFactsOfMethodsAreAndTakenInTheOrderOfTheirNames() {
        assertThat(steps(read("java.util.AbstractList"))).containsExactly("get_int", "size");
        assertThat(steps(read("java.lang.CharSequence")))
                .containsExactly("charAt_int", "length", "subSequence_int_int");
        assertThat(steps(read("java.lang.Comparable"))).containsExactly("compareTo_T");
        assertThat(steps(read("java.io.Reader"))).containsExactly("close", "read_charArray_int_int");
        assertThat(steps(read("java.lang.Object"))).isEmpty();
    }

    private static List<String> steps(Read read) {
        return ((Entry.Offered) read.entry())
                .steps().stream().map(Entry.Step::name).toList();
    }

    private static String refusal(String type) {
        return ((Entry.Refused) read(type).entry()).reason();
    }

    @Test
    void aTypeNoGeneratedClassCanExtendOrImplementHasNoEntryAndTellsWhy() {
        assertThat(refusal("java.lang.String")).isEqualTo("it is a final class, which no class extends");
        assertThat(refusal("java.time.DayOfWeek")).isEqualTo("it is an enum class, which no class extends");
        assertThat(refusal("java.lang.Enum")).isEqualTo("no class extends java.lang.Enum but an enum or a record does");
        assertThat(refusal("java.lang.constant.ConstantDesc"))
                .isEqualTo("it is sealed: only the classes it permits extend or implement it");
        // an abstract method with type parameters of its own
        assertThat(refusal("java.util.Collection"))
                .isEqualTo(
                        "its abstract method toArray(java.lang.Object[]) is generic, and a generated method has no type"
                                + " parameters yet");
        assertThat(refusal("java.util.List")).contains("is generic");
    }

    @TempDir
    Path lib;

    /// The entry of a type of a library compiled for the test.
    private Read ofLibrary(String type, String... sources) {
        Path classpath = Javac.plain().compile(sources).orFail().writeTo(lib);
        return read(type, classpath);
    }

    @Test
    void theAbstractMethodsOfOneSignatureAreOneStepOfTheOneThatStandsForThem() {
        Read both = ofLibrary(
                "p.Both",
                "package p; public interface Objects { Object get(); }",
                "package p; public interface Texts { CharSequence get(); }",
                "package p; public interface Both extends Objects, Texts {}");

        // the one of the most specific result: a class that implements Both returns a CharSequence
        assertThat(((Entry.Offered) both.entry()).steps()).singleElement().satisfies(step -> {
            assertThat(step.name()).isEqualTo("get");
            assertThat(step.method().declaredBy()).isEqualTo(ClassDesc.of("p", "Texts"));
            assertThat(step.method().result())
                    .isEqualTo(new Heritage.Result.Of(new ClassTypeRef(ClassDesc.of("java.lang.CharSequence"))));
        });
    }

    @Test
    void aMethodASuperclassImplementsIsNoStepAndOneItLeavesAbstractIs() {
        Read half = ofLibrary(
                "p.Half",
                "package p; public interface Api { void done(); void left(); }",
                "package p; public abstract class Half implements Api { public void done() {} protected Half() {} }");

        assertThat(steps(half)).containsExactly("left");
    }

    @Test
    void theStepsOfOverloadsAreToldApartByTheirParametersAndByThePackagesOfThoseIfNeedBe() {
        Read overloads = ofLibrary(
                "p.Overloads",
                "package a; public class X {}",
                "package b; public class X {}",
                "package p; public interface Overloads { void m(); void m(int i); void m(String... s); void n(a.X x);"
                        + " void n(b.X x); }");

        assertThat(steps(overloads)).containsExactly("m", "m_StringArray", "m_int", "n_a_X", "n_b_X");
    }

    @Test
    void aTypeWithAnAbstractMethodNoGeneratedClassImplementsHasNoEntry() {
        String hidden = "package p; class Hidden {}";

        assertThat(((Entry.Refused) ofLibrary("p.Pack", "package p; public abstract class Pack { abstract void m(); }")
                                .entry())
                        .reason())
                .isEqualTo("its abstract method m() has package access, so a class of another package cannot"
                        + " implement it");
        assertThat(((Entry.Refused) ofLibrary("p.Hides", hidden, "package p; public interface Hides { Hidden m(); }")
                                .entry())
                        .reason())
                .isEqualTo("its abstract method m() mentions p.Hidden, which a class of another package cannot name");
        assertThat(((Entry.Refused)
                                ofLibrary("p.Outer.Inner", "package p; public class Outer { public class Inner {} }")
                                        .entry())
                        .reason())
                .startsWith("it is an inner class");
        assertThat(((Entry.Refused) ofLibrary("p.Closed", "package p; public class Closed { Closed() {} }")
                                .entry())
                        .reason())
                .isEqualTo("it has no constructor a class of another package calls");
        assertThat(((Entry.Refused)
                                ofLibrary("p.Named", "package p; public interface Named<Complete> { Complete get(); }")
                                        .entry())
                        .reason())
                .isEqualTo("its type parameter Complete is named like a class the proof mentions");
    }

    @Test
    void theClassBeingDeclaredIsNamedLikeNoTypeParameterOfTheType() {
        Read self = ofLibrary("p.Selfish", "package p; public interface Selfish<Self, Self_> { Self get(); }");

        assertThat(((Entry.Offered) self.entry()).self()).isEqualTo("Self__");
    }
}
