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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The entry of a type ([Entry]): its steps are the methods javac asks a
/// class that extends or implements the type for — a class with a method per
/// step compiles, and without any one of them does not —, and a type no
/// generated class can extend or implement has none, and tells why.
class EntryTest {

    /// What the entry of a type comes to, and the type as a class that takes its steps is written by.
    ///
    /// @param heritage what a class that names the type with its own type parameters inherits
    private record Read(Entry entry, boolean isInterface, List<String> typeParams, Heritage.Applied heritage) {}

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
            Models models = new Models(env.getElementUtils(), env.getTypeUtils());
            // every type a signature mentions is taken to have a metamodel, as in a closure that has them all
            Targets targets = new Targets(
                    models,
                    model.members().stream()
                            .flatMap(Mentions::of)
                            .map(Models::binaryName)
                            .distinct()
                            .collect(Collectors.toMap(name -> name, name -> ClassDesc.of("gen", "Assumed_"))),
                    Map.of());
            return new Read(
                    Entry.of(model, MemberPlan.of(element, models, targets, Set.of()), env.getElementUtils()),
                    element.getKind().isInterface(),
                    model.typeParams().stream()
                            .map(param -> param.name()
                                    + (param.bounds().isEmpty()
                                            ? ""
                                            : " extends "
                                                    + param.bounds().stream()
                                                            .map(EntryTest::java)
                                                            .collect(Collectors.joining(" & "))))
                            .toList(),
                    switch (model.heritage()) {
                        case Heritage.Told told ->
                            told.applied(model.typeParams().stream()
                                    .<TypeRef>map(param -> new TypeVarRef(param.name()))
                                    .toList());
                        case Heritage.Untold _ -> new Heritage.Applied(List.of(), List.of());
                    });
        });
    }

    /// A class that extends or implements a type with a method for each of `steps`.
    private static String probe(String type, Read read, List<Entry.Step> steps) {
        return probe(
                type,
                read,
                steps.stream()
                        .map(step -> method(step.told().name(), step.params(), step.result(), ""))
                        .toList());
    }

    /// A class that extends or implements a type, with `methods`.
    private static String probe(String type, Read read, Collection<String> methods) {
        String declared = read.typeParams().isEmpty() ? "" : "<" + String.join(", ", read.typeParams()) + ">";
        String applied = read.typeParams().isEmpty()
                ? ""
                : read.typeParams().stream()
                        .map(param -> param.split(" ")[0])
                        .collect(Collectors.joining(", ", "<", ">"));
        return "class Probe" + declared + (read.isInterface() ? " implements " : " extends ") + type + applied + " {\n"
                + String.join("", methods)
                + "}\n";
    }

    /// A public method that throws, declared to throw `thrown`.
    private static String method(String name, List<TypeRef> params, Heritage.Result<TypeRef> result, String thrown) {
        return "    public "
                + switch (result) {
                    case Heritage.Result.Nothing<TypeRef> _ -> "void";
                    case Heritage.Result.Of<TypeRef>(TypeRef type) -> java(type);
                }
                + " " + name
                + IntStream.range(0, params.size())
                        .mapToObj(i -> java(params.get(i)) + " p" + i)
                        .collect(Collectors.joining(", ", "(", ")"))
                + (thrown.isEmpty() ? "" : " throws " + thrown)
                + " { throw new IllegalStateException(); }\n";
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
        requireTheStepsAreWhatJavacAsksFor(type, read, Javac.plain());
    }

    /// With every step a class compiles: there is no abstract method the steps do not tell. Without any
    /// one of them it does not: every step is of a method javac asks for.
    private static void requireTheStepsAreWhatJavacAsksFor(String type, Read read, Javac javac) {
        List<Entry.Step> steps = ((Entry.Offered) read.entry()).steps();
        Compiled complete = javac.compile(probe(type, read, steps));
        assertThat(complete.errors()).as(probe(type, read, steps)).isEmpty();
        assertThat(IntStream.range(0, steps.size()).boxed()).allSatisfy(left -> {
            List<Entry.Step> others = IntStream.range(0, steps.size())
                    .filter(i -> i != left)
                    .mapToObj(steps::get)
                    .toList();
            assertThat(javac.compile(probe(type, read, others)).errors())
                    .as("without %s", steps.get(left).name())
                    .anyMatch(error -> error.contains("is not abstract and does not override abstract method "
                            + steps.get(left).told().name() + "("));
        });
    }

    // ------------------------------------------------------------------
    // The forms of a library the types of the JDK above do not have
    // ------------------------------------------------------------------

    private static final List<String> LIBRARY = List.of(
            "package p1; public abstract class A { abstract void pkg(); public A() {} }",
            "package p2; public abstract class B extends p1.A { public B() {} }",
            "package p2; public abstract class AbsBase { public abstract void m(); }",
            "package p2; public class FinBase { public final void m() {} }",
            "package p2; public interface I { default void m() {} }",
            "package p2; public interface J { void m(); }",
            "package p2; public abstract class X extends AbsBase implements I {}",
            "package p2; public class Y extends FinBase implements I {}",
            "package p2; public abstract class U extends AbsBase implements I, J {}",
            "package p2; public interface TA { void t() throws java.io.IOException; }",
            "package p2; public interface TB { void t() throws java.sql.SQLException; }",
            "package p2; public interface TC extends TA, TB {}",
            "package p2; public interface TD { void t() throws java.io.FileNotFoundException, InterruptedException; }",
            "package p2; public interface TE extends TA, TD {}",
            "package p2; public abstract class PA { protected abstract void q(); }",
            "package p2; public interface PI { void q() throws java.io.IOException; }",
            "package p2; public abstract class PX extends PA implements PI {}",
            "package p2; public class SA { public static Object s() throws Exception { return null; } }",
            "package p2; public class SB extends SA { public static String s() { return null; } }",
            "package p2; public class SC extends SB {}",
            "package p2; public class Given { public String get() throws java.io.IOException { return null; } }",
            "package p2; public abstract class Again extends Given { public abstract String get(); }",
            "package p2; public interface Objects { Object get() throws Exception; }",
            "package p2; public interface Texts { CharSequence get() throws Exception; }",
            "package p2; public interface Both extends Objects, Texts {}",
            "package p2; public abstract class Half extends Given implements Objects {}",
            "package p2; public interface Sink<T> { void put(T value); }",
            "package p2; public interface Left extends Sink<String> {}",
            "package p2; public interface Right extends Sink<String> {}",
            "package p2; public interface Twice extends Left, Right {}",
            "package p2; public abstract class Deep<T> implements Sink<T>, Comparable<Deep<T>> {}");

    private Path library() {
        return Javac.plain().compile(LIBRARY.toArray(String[]::new)).orFail().writeTo(lib);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "p2.X",
                "p2.Y",
                "p2.U",
                "p2.TC",
                "p2.TE",
                "p2.PX",
                "p2.SC",
                "p2.Again",
                "p2.Both",
                "p2.Half",
                "p2.Twice",
                "p2.Deep"
            })
    void theStepsOfATypeOfALibraryAreTheMethodsJavacAsksAClassFor(String type) {
        Path classpath = library();

        requireTheStepsAreWhatJavacAsksFor(
                type, read(type, classpath), Javac.plain().classpath(classpath));
    }

    @Test
    void theStepsOfTheFormsOfALibraryAreThoseOfTheMethodsThatStandAbstract() {
        Path classpath = library();

        // the abstract method of the superclass stands beside the default one of the interface
        assertThat(steps(read("p2.X", classpath))).containsExactly("m");
        assertThat(steps(read("p2.U", classpath))).containsExactly("m");
        // the final one of the superclass overrides it
        assertThat(steps(read("p2.Y", classpath))).isEmpty();
        assertThat(steps(read("p2.TC", classpath))).containsExactly("t");
        assertThat(steps(read("p2.PX", classpath))).containsExactly("q");
        assertThat(steps(read("p2.SC", classpath))).isEmpty();
        // declared abstract again
        assertThat(steps(read("p2.Again", classpath))).containsExactly("get");
        assertThat(steps(read("p2.Both", classpath))).containsExactly("get");
        // the class wins: its method implements that of the interface
        assertThat(steps(read("p2.Half", classpath))).isEmpty();
        assertThat(steps(read("p2.Twice", classpath))).containsExactly("put_String");
        assertThat(steps(read("p2.Deep", classpath))).containsExactly("compareTo_Deep", "put_T");
    }

    @Test
    void aTypeWithAnAbstractMethodOfPackageAccessItDoesNotEvenInheritHasNoEntry() {
        Path classpath = library();
        Read read = read("p2.B", classpath);

        assertThat(read.entry())
                .isEqualTo(new Entry.Refused("its abstract method pkg() has package access in p1.A, so a class of"
                        + " another package cannot implement it"));
        // and javac asks a class of another package for the method it cannot give
        assertThat(Javac.plain()
                        .classpath(classpath)
                        .compile(probe("p2.B", read, List.<String>of()))
                        .errors())
                .anyMatch(error -> error.contains("does not override abstract method pkg() in p1.A"));
    }

    /// The exceptions an override is tried with: checked ones, a subclass and a superclass among them.
    private static final List<Class<? extends Throwable>> THROWN = List.of(
            java.io.IOException.class,
            java.io.FileNotFoundException.class,
            java.sql.SQLException.class,
            InterruptedException.class,
            CloneNotSupportedException.class,
            Exception.class,
            Throwable.class);

    /// An override is held against every declaration its method has in the heritage: javac accepts one
    /// with the result of the first declaration and a `throws` clause every declaration covers, and
    /// rejects one of a method that stands `final`, and one that throws what a declaration does not cover.
    @ParameterizedTest
    @ValueSource(
            strings = {
                "p2.X",
                "p2.Y",
                "p2.U",
                "p2.TC",
                "p2.TE",
                "p2.PX",
                "p2.SC",
                "p2.Again",
                "p2.Both",
                "p2.Half",
                "p2.Twice"
            })
    void anOverrideByTheHeritageOfATypeIsWhatJavacAccepts(String type) {
        Path classpath = library();
        Read read = read(type, classpath);
        Javac javac = Javac.plain().classpath(classpath);
        List<Entry.Step> steps = ((Entry.Offered) read.entry()).steps();
        // the methods an instance method of a class of another package overrides, each by its declarations
        Map<Heritage.Method<Heritage.Inherited>, List<Heritage.Plain>> overridable = read.heritage().methods().stream()
                .filter(method -> switch (method.standing()) {
                    case STATIC -> false;
                    case ABSTRACT, DEFAULT, CONCRETE, FINAL -> true;
                })
                .collect(Collectors.toMap(
                        method -> method,
                        method -> method.declarations().stream()
                                .map(declared -> (Heritage.Plain) declared)
                                .toList(),
                        (first, second) -> first,
                        LinkedHashMap::new));

        assertThat(overridable).isNotEmpty().allSatisfy((method, declarations) -> {
            Heritage.Plain first = declarations.getFirst();
            // the other abstract methods, so that the class is complete
            Map<String, String> others = steps.stream()
                    .filter(step -> !step.told().signature().equals(method.signature()))
                    .collect(Collectors.toMap(
                            Entry.Step::name,
                            step -> method(step.told().name(), step.params(), step.result(), ""),
                            (one, other) -> one,
                            LinkedHashMap::new));
            Stream.concat(Stream.of(""), THROWN.stream().map(Class::getName)).forEach(thrown -> {
                List<String> methods = Stream.concat(
                                others.values().stream(),
                                Stream.of(method(method.name(), first.params(), first.result(), thrown)))
                        .toList();
                boolean covered = thrown.isEmpty()
                        || declarations.stream()
                                .allMatch(declaration ->
                                        declaration.throwsTypes().stream().anyMatch(clause -> covers(clause, thrown)));
                boolean allowed =
                        switch (method.standing()) {
                            case FINAL -> false;
                            case ABSTRACT, DEFAULT, CONCRETE, STATIC -> covered;
                        };
                assertThat(javac.compile(probe(type, read, methods)).errors().isEmpty())
                        .as("%s of %s overridden throwing %s:%n%s", method.signature(), type, thrown, method)
                        .isEqualTo(allowed);
            });
        });
    }

    /// Whether a `throws` clause of `clause` lets an override throw the exception class `thrown`.
    private static boolean covers(TypeRef clause, String thrown) {
        try {
            return clause instanceof ClassTypeRef cls
                    && Class.forName(Models.binaryName(cls.desc())).isAssignableFrom(Class.forName(thrown));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
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
            assertThat(step.told().declarations())
                    .extracting(declaration -> declaration.by().type())
                    .containsExactly(ClassDesc.of("p", "Texts"), ClassDesc.of("p", "Objects"));
            assertThat(step.result())
                    .isEqualTo(new Heritage.Result.Of<>(new ClassTypeRef(ClassDesc.of("java.lang.CharSequence"))));
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
                "package p; public interface Overloads { void m(); void m(int i); void m(String s); void n(a.X x);"
                        + " void n(b.X x); }");

        assertThat(steps(overloads)).containsExactly("m", "m_String", "m_int", "n_a_X", "n_b_X");
    }

    @Test
    void aTypeWithAnAbstractMethodNoGeneratedClassImplementsHasNoEntry() {
        String hidden = "package p; class Hidden {}";

        assertThat(((Entry.Refused) ofLibrary("p.Pack", "package p; public abstract class Pack { abstract void m(); }")
                                .entry())
                        .reason())
                .isEqualTo("its abstract method m() has package access in p.Pack, so a class of another package"
                        + " cannot implement it");
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
        // a method of variable arity is overridden by one — unless javac would warn of the override
        assertThat(ofLibrary("p.Many", "package p; public interface Many { void m(String... more); }")
                        .entry())
                .isInstanceOf(Entry.Offered.class);
        assertThat(((Entry.Refused)
                                ofLibrary("p.Spread", "package p; public interface Spread<T> { void m(T... more); }")
                                        .entry())
                        .reason())
                .isEqualTo("its abstract method m(#0[]) is of variable arity with an element type that is not"
                        + " reifiable, so javac warns of heap pollution where a generated method overrides it");
        assertThat(((Entry.Refused) ofLibrary(
                                        "p.Either",
                                        "package p; public interface Either extends Spreading, Taking {}",
                                        "package p; public interface Spreading { void m(String... more); }",
                                        "package p; public interface Taking { void m(String[] all); }")
                                .entry())
                        .reason())
                .isEqualTo("its abstract method m(java.lang.String[]) is of variable arity in one of p.Spreading and"
                        + " p.Taking and not in another, so javac warns of whichever a generated method is");
        assertThat(((Entry.Refused) ofLibrary(
                                        "p.Raw",
                                        "package p; public interface Raw extends Typed, Erased {}",
                                        "package p; public interface Typed { void m(java.util.List<String> list); }",
                                        "package p; public interface Erased { void m(java.util.List list); }")
                                .entry())
                        .reason())
                .isEqualTo("its abstract method m(java.util.List) is declared with other parameter types by p.Typed"
                        + " and p.Erased, so no one method of a generated class implements each");
    }

    @Test
    void aTypeWhoseHeritageOrBoundsCannotBeToldHasNoEntry() {
        assertThat(((Entry.Refused) ofLibrary(
                                        "p.Odd",
                                        "package p; public class Odd { public Outer<String>.Inner odd() { return null; } }",
                                        "package p; public class Outer<T> { public class Inner {} }")
                                .entry())
                        .reason())
                .isEqualTo("a member a class would inherit of it has a signature that cannot be told");
        assertThat(((Entry.Refused) ofLibrary(
                                        "p.Bounded",
                                        "package p; public class Bounded<T extends Hidden> { public Bounded() {} }",
                                        "package p; class Hidden {}")
                                .entry())
                        .reason())
                .isEqualTo("a bound of its type parameters mentions a type that is not public: p.Hidden");
    }

    @Test
    void anAbstractMethodOfMoreParametersThanAStepTakesRefusesTheEntry() {
        assertThat(((Entry.Refused) ofLibrary(
                                        "p.Wide",
                                        "package p; public interface Wide { void m(int a, int b, int c, int d, int e, int f,"
                                                + " int g, int h, int i, int j, int k, int l, int n); }")
                                .entry())
                        .reason())
                .endsWith(" has 13 parameters, more than 12");
    }

    @Test
    void aMethodOfVariableArityOfAReifiableElementTypeIsAStep() {
        // an override of it is declared without a warning of javac: a primitive, an array, a List<?>
        assertThat(ofLibrary("p.Ints", "package p; public interface Ints { void m(int... more); }")
                        .entry())
                .isInstanceOf(Entry.Offered.class);
        assertThat(ofLibrary("p.Arrays", "package p; public interface Arrays { void m(String[]... more); }")
                        .entry())
                .isInstanceOf(Entry.Offered.class);
        assertThat(ofLibrary("p.Lists", "package p; public interface Lists { void m(java.util.List<?>... more); }")
                        .entry())
                .isInstanceOf(Entry.Offered.class);
    }

    @Test
    void anEntryIsOfferedOnlyWhereTheFactsItIsUsedWithAreThere() {
        String thirteen = IntStream.range(0, 13).mapToObj(i -> "int p" + i).collect(Collectors.joining(", "));

        // no constructor has a fact: none can be called by a generated one
        assertThat(((Entry.Refused)
                                ofLibrary("p.Wide", "package p; public class Wide { public Wide(" + thirteen + ") {} }")
                                        .entry())
                        .reason())
                .isEqualTo("none of its constructors has a fact, so no generated constructor calls one");
    }

    @Test
    void aTypeParameterNamedLikeAClassOfTheProofIsNoObstacle() {
        Read named = ofLibrary(
                "p.Named", "package p; public interface Named<Complete, Told, List> { Complete get(Told t, List l); }");

        assertThat(steps(named)).containsExactly("get_Told_List");
    }

    @Test
    void theClassBeingDeclaredIsNamedLikeNoTypeParameterOfTheType() {
        Read self = ofLibrary("p.Selfish", "package p; public interface Selfish<Self, Self_> { Self get(); }");

        assertThat(((Entry.Offered) self.entry()).self()).isEqualTo("Self__");
    }
}
