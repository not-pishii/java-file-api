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

    private static Compilation compile(String types) {
        return ProcessorHarness.succeeded(ProcessorHarness.process(List.of(), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(types)));
    }

    @Test
    void underStrictTheSkippedMembersOfASupertypeThatIsNotAskedForAreWarnings() {
        List<String> strict = List.of("-Ajavafile.facts.strict=true");
        String generator = """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """;

        // Map, which HashMap implements, has of(…) of more parameters than a fact can have
        Compilation supertype = ProcessorHarness.succeeded(
                ProcessorHarness.process(List.of(), strict, List.of(), generator.formatted("java.util.HashMap.class")));
        assertThat(warnings(supertype))
                .isNotEmpty()
                .allMatch(warning -> warning.startsWith(
                        "java.util.Map (a supertype of java.util.HashMap): no fact of method <K,V>of("));

        // asked for, Map is held to strict, a supertype of HashMap though it is
        Compilation asked = ProcessorHarness.process(
                List.of(), strict, List.of(), generator.formatted("java.util.HashMap.class, java.util.Map.class"));
        assertThat(asked.status()).isEqualTo(Compilation.Status.FAILURE);
        assertThat(errors(asked))
                .isNotEmpty()
                .allMatch(error -> error.startsWith("java.util.Map: no fact of method <K,V>of("));
        assertThat(warnings(asked)).isEmpty();
    }

    /// The types with full metamodels among what a compilation generated, by binary name without `java.`.
    private static List<String> full(Compilation compilation) {
        return ProcessorHarness.resources(compilation).keySet().stream()
                .filter(path -> path.startsWith("META-INF/javafile/metamodel/full/java."))
                .map(path -> path.substring("META-INF/javafile/metamodel/full/java.".length()))
                .toList();
    }

    @Test
    void aStringBuilderHasTheMembersOfItsHiddenSuperclassAndItsSupertypesAreFull() throws Exception {
        Compilation compilation = compile("java.lang.StringBuilder.class");
        ClassLoader loader = ProcessorHarness.compileAndLoad(compilation, out, List.of());

        // AbstractStringBuilder is not public: no metamodel, and its members are those of StringBuilder
        assertThat(full(compilation))
                .containsExactly(
                        "io.Serializable",
                        "lang.Appendable",
                        "lang.CharSequence",
                        "lang.Comparable",
                        "lang.Object",
                        "lang.StringBuilder");
        assertThat(sources(compilation)).doesNotContainKey("gen.facts.java.lang.AbstractStringBuilder_");
        String builder = "gen.facts.java.lang.StringBuilder_";
        assertThat(factNames(loader, builder))
                .contains("capacity", "setLength_int", "ensureCapacity_int", "trimToSize", "charAt_int", "length")
                .contains("append_String", "reverse", "toString", "new_");
        assertThat(((Invocable) fact(loader, builder, "capacity")).owner()).isEqualTo(token(loader, builder));
        assertThat(((Invocable) fact(loader, builder, "capacity")).traits()).isEqualTo(MemberTraits.FINAL);
        // what StringBuilder overrides with its own type is its own, not the one that returns the hidden type
        assertThat(((Invocable) fact(loader, builder, "append_String"))
                        .resultType()
                        .orElseThrow())
                .isEqualTo(token(loader, builder));
        // and length() is there for any CharSequence without anybody asking for CharSequence
        assertThat(factNames(loader, "gen.facts.java.lang.CharSequence_"))
                .contains("length", "charAt_int", "subSequence_int_int", "isEmpty");
        assertThat(factNames(loader, "gen.facts.java.lang.Object_")).contains("toString", "equals_Object", "hashCode");
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void theSupertypesOfACollectionAnEnumARecordAndAFunctionalInterfaceAreFull() throws Exception {
        Compilation compilation = generate(
                "java.util.ArrayList.class, java.util.concurrent.TimeUnit.class, p.Point.class,"
                        + " java.util.function.UnaryOperator.class, java.util.function.BinaryOperator.class",
                "package p; public record Point(int x, int y) {}");
        ClassLoader loader = load(compilation);

        assertThat(full(compilation))
                .contains(
                        // ArrayList: its abstract superclasses are public
                        "util.AbstractList",
                        "util.AbstractCollection",
                        "util.List",
                        "util.SequencedCollection",
                        "util.Collection",
                        "lang.Iterable",
                        "util.RandomAccess",
                        "lang.Cloneable",
                        "io.Serializable",
                        // an enum and a record
                        "lang.Enum",
                        "lang.Comparable",
                        "lang.constant.Constable",
                        "lang.Record",
                        // the interfaces the operators have their methods from
                        "util.function.Function",
                        "util.function.BiFunction",
                        "lang.Object");
        assertThat(memberNames(loader, "gen.facts.java.util.AbstractCollection_"))
                .contains("isEmpty", "size");
        assertThat(memberNames(loader, "gen.facts.java.util.Collection_")).contains("stream", "add_E");
        assertThat(memberNames(loader, "gen.facts.java.lang.Enum_")).contains("name", "ordinal", "compareTo_E");
        assertThat(memberNames(loader, "gen.facts.java.lang.Record_"))
                .contains("equals_Object", "hashCode", "toString");
        assertThat(memberNames(loader, "gen.facts.java.util.function.BiFunction_"))
                .contains("apply_T_U", "andThen_Function", "sam");
        assertThat(factNames(loader, "gen.facts.p.Point_")).contains("new_int_int", "x", "y");
        assertThat(warnings(compilation)).isEmpty();
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
