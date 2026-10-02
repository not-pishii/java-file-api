package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.Sam2;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The processor on types of the JDK, whose members are many and odd: the
/// generated metamodels compile under every lint and their facts describe the
/// members.
class JdkTypesTest extends FixtureSupport {
    private static final String TYPES = "java.lang.String.class, java.lang.StringBuilder.class,"
            + " java.lang.Object.class, java.lang.Math.class, java.lang.Integer.class, java.lang.Double.class,"
            + " java.lang.System.class, java.time.DayOfWeek.class, java.lang.Throwable.class,"
            + " java.lang.Runnable.class, java.lang.CharSequence.class, java.io.IOException.class,"
            + " java.lang.Character.class, java.util.concurrent.TimeUnit.class, java.lang.Thread.class,"
            + " java.nio.file.Files.class, java.nio.charset.StandardCharsets.class";

    private Compilation compile() {
        return ProcessorHarness.succeeded(ProcessorHarness.process(List.of(), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(TYPES)));
    }

    @Test
    void theFactsOfJdkTypesCompileAndDescribeTheirMembers() throws Exception {
        Compilation compilation = compile();
        ClassLoader loader = ProcessorHarness.compileAndLoad(compilation, out, List.of());
        String string = "gen.facts.java.lang.String_";

        assertThat(token(loader, string)).isInstanceOf(FinalClassToken.class);
        Invocable length = (Invocable) fact(loader, string, "length");
        assertThat(length.resultType().orElseThrow()).isSameAs(PrimitiveToken.INT);
        assertThat(length.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) fact(loader, string, "charAt_int")).resultType().orElseThrow())
                .isSameAs(PrimitiveToken.CHAR);
        assertThat(((Invocable) fact(loader, string, "substring_int_int")).params())
                .hasSize(2);
        assertThat(((Invocable) fact(loader, string, "valueOf_int")).traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) fact(loader, string, "getBytes_String"))
                        .traits()
                        .throwsTypes())
                .extracting(t -> t.typeRef())
                .containsExactly(Types.of(ClassDesc.of("java.io.UnsupportedEncodingException")));

        String integer = "gen.facts.java.lang.Integer_";
        assertThat(((StaticFieldRef<?>) fact(loader, integer, "MAX_VALUE")).constantValue())
                .contains(Integer.MAX_VALUE);
        String dbl = "gen.facts.java.lang.Double_";
        assertThat(((StaticFieldRef<?>) fact(loader, dbl, "NaN")).constantValue())
                .contains(Double.NaN);
        assertThat(((StaticFieldRef<?>) fact(loader, dbl, "POSITIVE_INFINITY")).constantValue())
                .contains(Double.POSITIVE_INFINITY);
        assertThat(((StaticFieldRef<?>) fact(loader, dbl, "MIN_VALUE")).constantValue())
                .contains(Double.MIN_VALUE);

        assertThat(((EnumToken<?>) token(loader, "gen.facts.java.time.DayOfWeek_")).constants())
                .containsExactly("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        assertThat(((EnumConstant<?>) fact(loader, "gen.facts.java.time.DayOfWeek_", "MONDAY")).name())
                .isEqualTo("MONDAY");
        assertThat(fact(loader, "gen.facts.java.lang.Runnable_", "sam")).isInstanceOf(VoidSam0.class);
    }

    @Test
    void anObjectMetamodelHasOnlyTheMethodsObjectDeclares() throws Exception {
        ClassLoader loader = ProcessorHarness.compileAndLoad(compile(), out, List.of());

        assertThat(factNames(loader, "gen.facts.java.lang.Object_"))
                .contains("equals_Object", "hashCode", "toString", "new_")
                .doesNotContain("length");
    }

    private static final String GENERIC_TYPES = "java.util.List.class, java.util.Map.class, java.util.Map.Entry.class,"
            + " java.util.Optional.class, java.util.function.Function.class, java.util.function.UnaryOperator.class,"
            + " java.lang.Comparable.class, java.util.Comparator.class, java.util.stream.Stream.class,"
            + " java.util.ArrayList.class, java.lang.Enum.class, java.lang.Class.class, java.lang.String.class,"
            + " java.lang.Integer.class";

    private Compilation compileGeneric() {
        return ProcessorHarness.succeeded(ProcessorHarness.process(List.of(), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(GENERIC_TYPES)));
    }

    private static TypeRef applied(ClassDesc type, TypeArg... args) {
        return new ParameterizedTypeRef(type, List.of(args));
    }

    @Test
    void theFactsOfGenericJdkTypesCompileAndDescribeTheirMembers() throws Exception {
        Compilation compilation = compileGeneric();
        ClassLoader loader = ProcessorHarness.compileAndLoad(compilation, out, List.of());
        RefToken<?> string = token(loader, "gen.facts.java.lang.String_");
        RefToken<?> integer = token(loader, "gen.facts.java.lang.Integer_");
        String listMetamodel = "gen.facts.java.util.List_";
        String streamMetamodel = "gen.facts.java.util.stream.Stream_";
        ClassDesc function = ClassDesc.of("java.util.function.Function");
        ClassDesc stream = ClassDesc.of("java.util.stream.Stream");

        Object list = instance(loader, listMetamodel, string);
        DeclaredToken<?> listOfString = (DeclaredToken<?>) fact(list, "token");
        assertThat(listOfString.typeRef()).isEqualTo(applied(ConstantDescs.CD_List, Types.exact(Types.STRING)));
        Invocable get = (Invocable) fact(list, "get_int");
        assertThat(get.resultType().orElseThrow()).isSameAs(string);
        assertThat(get.traits()).isEqualTo(MemberTraits.ABSTRACT);
        assertThat(((Invocable) fact(list, "add_E")).signature())
                .isEqualTo(new MethodSignature("add", List.of(ConstantDescs.CD_String)));
        Invocable of = (Invocable) made(loader, listMetamodel, null, "of_EArray", integer);
        assertThat(of.owner()).isSameAs(fact(loader, listMetamodel, "ANY"));
        assertThat(of.resultType().orElseThrow().typeRef())
                .isEqualTo(applied(ConstantDescs.CD_List, Types.exact(integer.typeRef())));
        assertThat(of.traits()).isEqualTo(MemberTraits.FINAL.withTypeArgs(integer));

        // <R> Stream<R> map(Function<? super T, ? extends R>)
        Object streamOfString = instance(loader, streamMetamodel, string);
        Invocable map = (Invocable) made(loader, streamMetamodel, streamOfString, "map_Function", integer);
        assertThat(map.resultType().orElseThrow().typeRef()).isEqualTo(applied(stream, Types.exact(integer.typeRef())));
        assertThat(map.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(
                        applied(function, Types.superBound(Types.STRING), Types.extendsBound(integer.typeRef())));
        assertThat(map.traits()).isEqualTo(MemberTraits.ABSTRACT.withTypeArgs(integer));

        // <X extends Throwable> T orElseThrow(Supplier<? extends X>) throws X
        Object optional = instance(loader, "gen.facts.java.util.Optional_", string);
        assertThat(fact(optional, "token")).isInstanceOf(FinalClassToken.class);
        RefToken<?> failure = token(loader, "gen.facts.java.lang.Throwable_");
        Invocable orElseThrow =
                (Invocable) made(loader, "gen.facts.java.util.Optional_", optional, "orElseThrow_Supplier", failure);
        assertThat(List.<Object>copyOf(orElseThrow.traits().throwsTypes())).containsExactly(failure);
        assertThat(orElseThrow.resultType().orElseThrow()).isSameAs(string);

        // the sam of a generic functional interface, declared and inherited
        Object fn = instance(loader, "gen.facts.java.util.function.Function_", string, integer);
        Sam1<?, ?, ?> apply = (Sam1<?, ?, ?>) fact(fn, "sam");
        assertThat(apply.method()).isSameAs(fact(fn, "apply_T"));
        assertThat(apply.result()).isSameAs(integer);
        assertThat(apply.param1()).isSameAs(string);
        Object operator = instance(loader, "gen.facts.java.util.function.UnaryOperator_", string);
        Sam1<?, ?, ?> inherited = (Sam1<?, ?, ?>) fact(operator, "sam");
        assertThat(inherited.method().name()).isEqualTo("apply");
        assertThat(inherited.owner()).isSameAs(fact(operator, "token"));
        assertThat(inherited.result()).isSameAs(string);
        assertThat(memberNames(loader, "gen.facts.java.util.function.UnaryOperator_"))
                .containsExactly("identity", "sam");
        // equals(Object) of Comparator does not count
        assertThat(fact(instance(loader, "gen.facts.java.util.Comparator_", string), "sam"))
                .isInstanceOf(Sam2.class);
        assertThat(fact(instance(loader, "gen.facts.java.lang.Comparable_", string), "sam"))
                .isInstanceOf(Sam1.class);

        // E extends Enum<E>, a nested generic type, a type that mentions itself in every way
        Object entry = instance(loader, "gen.facts.java.util.Map_Entry_", string, integer);
        assertThat(((Invocable) fact(entry, "getValue")).resultType().orElseThrow())
                .isSameAs(integer);
        Object cls = instance(loader, "gen.facts.java.lang.Class_", string);
        assertThat(((Invocable) fact(cls, "getSuperclass"))
                        .resultType()
                        .orElseThrow()
                        .typeRef())
                .isEqualTo(applied(ClassDesc.of("java.lang.Class"), Types.superBound(Types.STRING)));
        assertThat(ProcessorHarness.generatedSources(compilation).get("gen.facts.java.lang.Enum_"))
                .contains("public final class Enum_<E extends Enum<E>> {")
                .contains("public static <T extends Enum<T>> StaticMethodRef2<T, Class<T>, String>"
                        + " valueOf_Class_String(RefToken<T> t) {");
        Object arrayList = instance(loader, "gen.facts.java.util.ArrayList_", string);
        assertThat(fact(arrayList, "new_")).isInstanceOf(CtorRef0.class);
        assertThat(((Invocable) fact(arrayList, "new_Collection")).params())
                .extracting(TypeToken::typeRef)
                .containsExactly(applied(ConstantDescs.CD_Collection, Types.extendsBound(Types.STRING)));
    }

    @Test
    void onlyMembersOfTooManyParametersOfTheGenericJdkTypesHaveNoFact() {
        assertThat(warnings(compileGeneric()))
                .allMatch(warning -> warning.startsWith("java.util.Map: no fact of method <K,V>of(")
                        && warning.endsWith(" parameters, more than 12"))
                .hasSize(4);
    }
}
