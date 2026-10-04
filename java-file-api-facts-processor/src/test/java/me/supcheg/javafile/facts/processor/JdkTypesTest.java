package me.supcheg.javafile.facts.processor;

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
import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The processor on types of the JDK, whose members are many and odd: the generated metamodels compile under every
/// lint and their facts describe the members. A scenario test and not a fixture: the metamodels of the JDK types are
/// large, and a snapshot of them is bound to the version of the JDK.
class JdkTypesTest {
    private static final String TYPES = "java.lang.String.class, java.lang.StringBuilder.class,"
            + " java.lang.Object.class, java.lang.Math.class, java.lang.Integer.class, java.lang.Double.class,"
            + " java.lang.System.class, java.time.DayOfWeek.class, java.lang.Throwable.class,"
            + " java.lang.Runnable.class, java.lang.CharSequence.class, java.io.IOException.class,"
            + " java.lang.Character.class, java.util.concurrent.TimeUnit.class, java.lang.Thread.class,"
            + " java.nio.file.Files.class, java.nio.charset.StandardCharsets.class";

    private static final String GENERIC_TYPES = "java.util.List.class, java.util.Map.class, java.util.Map.Entry.class,"
            + " java.util.Optional.class, java.util.function.Function.class, java.util.function.UnaryOperator.class,"
            + " java.lang.Comparable.class, java.util.Comparator.class, java.util.stream.Stream.class,"
            + " java.util.ArrayList.class, java.lang.Enum.class, java.lang.Class.class, java.lang.String.class,"
            + " java.lang.Integer.class";

    @TempDir
    Path out;

    private static String generator(String types) {
        return """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(types);
    }

    private static Javac processor() {
        return Javac.facts();
    }

    private static Compiled processed(String types) {
        return processor().compile(generator(types)).orFail();
    }

    /// The metamodels of a run of the processor, compiled under every lint and every check of their
    /// documentation comments with nothing to say, and loaded.
    private Loaded load(Compiled compiled, Path... libraries) throws IOException {
        Path directory = Javac.plain()
                .documented()
                .classpath(libraries)
                .compile(compiled.generated())
                .clean()
                .writeTo(out.resolve("metamodels"));
        return new Loaded(new URLClassLoader(
                Stream.concat(Stream.of(directory), Stream.of(libraries))
                        .map(path -> {
                            try {
                                return path.toUri().toURL();
                            } catch (MalformedURLException e) {
                                throw new IllegalStateException(e);
                            }
                        })
                        .toArray(URL[]::new),
                JdkTypesTest.class.getClassLoader()));
    }

    /// The facts of the metamodels, read by name.
    private record Loaded(ClassLoader loader) {
        Object fact(String metamodel, String field) throws ReflectiveOperationException {
            return loader.loadClass(metamodel).getField(field).get(null);
        }

        static Object fact(Object instance, String field) throws ReflectiveOperationException {
            return instance.getClass().getField(field).get(instance);
        }

        DeclaredToken<?> token(String metamodel) throws ReflectiveOperationException {
            return (DeclaredToken<?>) fact(metamodel, "TOKEN");
        }

        /// The names of the `public static` fields of a metamodel that hold facts, sorted: all but `TOKEN`.
        List<String> factNames(String metamodel) throws ReflectiveOperationException {
            return Arrays.stream(loader.loadClass(metamodel).getFields())
                    .filter(f -> Modifier.isStatic(f.getModifiers()))
                    .map(Field::getName)
                    .filter(name -> !name.equals("TOKEN"))
                    .sorted()
                    .toList();
        }

        /// The names of the facts of a metamodel, sorted: its `public` fields, of the class and of its instances, but
        /// `TOKEN`, `ANY` and `token`, and its methods, which make the facts of generic methods.
        List<String> memberNames(String metamodel) throws ReflectiveOperationException {
            Class<?> type = loader.loadClass(metamodel);
            return Stream.concat(
                            Arrays.stream(type.getFields())
                                    .map(Field::getName)
                                    .filter(name ->
                                            !List.of("TOKEN", "ANY", "token").contains(name)),
                            Arrays.stream(type.getDeclaredMethods())
                                    .filter(m -> Modifier.isPublic(m.getModifiers()))
                                    .map(Method::getName))
                    .sorted()
                    .toList();
        }

        /// An instance of a generic metamodel: its constructor takes a token per type parameter.
        Object instance(String metamodel, RefToken<?>... witnesses) throws ReflectiveOperationException {
            Class<?>[] parameters = new Class<?>[witnesses.length];
            Arrays.fill(parameters, RefToken.class);
            return loader.loadClass(metamodel).getConstructor(parameters).newInstance((Object[]) witnesses);
        }

        /// The fact of a generic method: what the method of a metamodel returns for a token per type parameter.
        /// `instance` is `null` for a `static` one.
        Object made(String metamodel, Object instance, String method, RefToken<?>... witnesses)
                throws ReflectiveOperationException {
            Class<?>[] parameters = new Class<?>[witnesses.length];
            Arrays.fill(parameters, RefToken.class);
            return loader.loadClass(metamodel).getMethod(method, parameters).invoke(instance, (Object[]) witnesses);
        }
    }

    @Test
    void theFactsOfJdkTypesCompileAndDescribeTheirMembers() throws Exception {
        Loaded loaded = load(processed(TYPES));
        String string = "gen.facts.java.lang.String_";

        assertThat(loaded.token(string)).isInstanceOf(FinalClassToken.class);
        Invocable length = (Invocable) loaded.fact(string, "length");
        assertThat(length.resultType().orElseThrow()).isSameAs(PrimitiveToken.INT);
        assertThat(length.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) loaded.fact(string, "charAt_int")).resultType().orElseThrow())
                .isSameAs(PrimitiveToken.CHAR);
        assertThat(((Invocable) loaded.fact(string, "substring_int_int")).params())
                .hasSize(2);
        assertThat(((Invocable) loaded.fact(string, "valueOf_int")).traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) loaded.fact(string, "getBytes_String")).traits().throwsTypes())
                .extracting(t -> t.typeRef())
                .containsExactly(Types.of(ClassDesc.of("java.io.UnsupportedEncodingException")));

        assertThat(((StaticFieldRef<?>) loaded.fact("gen.facts.java.lang.Integer_", "MAX_VALUE")).constantValue())
                .contains(Integer.MAX_VALUE);
        String dbl = "gen.facts.java.lang.Double_";
        assertThat(((StaticFieldRef<?>) loaded.fact(dbl, "NaN")).constantValue())
                .contains(Double.NaN);
        assertThat(((StaticFieldRef<?>) loaded.fact(dbl, "POSITIVE_INFINITY")).constantValue())
                .contains(Double.POSITIVE_INFINITY);
        assertThat(((StaticFieldRef<?>) loaded.fact(dbl, "MIN_VALUE")).constantValue())
                .contains(Double.MIN_VALUE);

        assertThat(((EnumToken<?>) loaded.token("gen.facts.java.time.DayOfWeek_")).constants())
                .containsExactly("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        assertThat(((EnumConstant<?>) loaded.fact("gen.facts.java.time.DayOfWeek_", "MONDAY")).name())
                .isEqualTo("MONDAY");
        assertThat(loaded.fact("gen.facts.java.lang.Runnable_", "sam")).isInstanceOf(VoidSam0.class);
    }

    @Test
    void anObjectMetamodelHasOnlyTheMethodsObjectDeclares() throws Exception {
        Loaded loaded = load(processed(TYPES));

        assertThat(loaded.factNames("gen.facts.java.lang.Object_"))
                .contains("equals_Object", "hashCode", "toString", "new_")
                .doesNotContain("length");
    }

    @Test
    void underStrictTheSkippedMembersOfASupertypeThatIsNotAskedForAreWarnings() {
        Javac strict = processor().options("-Ajavafile.facts.strict=true");

        // Map, which HashMap implements, has of(…) of more parameters than a fact can have
        Compiled supertype =
                strict.compile(generator("java.util.HashMap.class")).orFail();
        assertThat(supertype.warnings())
                .isNotEmpty()
                .allMatch(warning -> warning.startsWith(
                        "java.util.Map (a supertype of java.util.HashMap): no fact of method <K,V>of("));

        // asked for, Map is held to strict, a supertype of HashMap though it is
        Compiled asked = strict.compile(generator("java.util.HashMap.class, java.util.Map.class"));
        assertThat(asked.succeeded()).isFalse();
        assertThat(asked.errors())
                .isNotEmpty()
                .allMatch(error -> error.startsWith("java.util.Map: no fact of method <K,V>of("));
        assertThat(asked.warnings()).isEmpty();
    }

    /// The types with full metamodels among what a compilation generated, by binary name without `java.`.
    private static List<String> full(Compiled compiled) {
        return compiled.resources().keySet().stream()
                .filter(path -> path.startsWith("META-INF/javafile/metamodel/full/java."))
                .map(path -> path.substring("META-INF/javafile/metamodel/full/java.".length()))
                .toList();
    }

    @Test
    void aStringBuilderHasTheMembersOfItsHiddenSuperclassAndItsSupertypesAreFull() throws Exception {
        Compiled compiled = processed("java.lang.StringBuilder.class");
        Loaded loaded = load(compiled);

        // AbstractStringBuilder is not public: no metamodel, and its members are those of StringBuilder
        assertThat(full(compiled))
                .containsExactly(
                        "io.Serializable",
                        "lang.Appendable",
                        "lang.CharSequence",
                        "lang.Comparable",
                        "lang.Object",
                        "lang.StringBuilder");
        assertThat(compiled.sources()).doesNotContainKey("gen.facts.java.lang.AbstractStringBuilder_");
        String builder = "gen.facts.java.lang.StringBuilder_";
        assertThat(loaded.factNames(builder))
                .contains("capacity", "setLength_int", "ensureCapacity_int", "trimToSize", "charAt_int", "length")
                .contains("append_String", "reverse", "toString", "new_");
        assertThat(((Invocable) loaded.fact(builder, "capacity")).owner()).isEqualTo(loaded.token(builder));
        assertThat(((Invocable) loaded.fact(builder, "capacity")).traits()).isEqualTo(MemberTraits.FINAL);
        // what StringBuilder overrides with its own type is its own, not the one that returns the hidden type
        assertThat(((Invocable) loaded.fact(builder, "append_String"))
                        .resultType()
                        .orElseThrow())
                .isEqualTo(loaded.token(builder));
        // and length() is there for any CharSequence without anybody asking for CharSequence
        assertThat(loaded.factNames("gen.facts.java.lang.CharSequence_"))
                .contains("length", "charAt_int", "subSequence_int_int", "isEmpty");
        assertThat(loaded.factNames("gen.facts.java.lang.Object_")).contains("toString", "equals_Object", "hashCode");
        assertThat(compiled.warnings()).isEmpty();
    }

    /// `capacity()` is declared by the package-private `AbstractStringBuilder`, `LOCSIG` by the package-private
    /// `ZipConstants`: the facts `StringBuilder_` and `ZipFile_` adopt are rendered by the typed layer as members
    /// of the `public` types, which javac compiles in another package and the JVM runs.
    @Test
    void theMembersJdkTypesAdoptAreCalledThroughTheTypedLayer() throws Exception {
        Compiled compilation = processor()
                .compile(generator("java.lang.StringBuilder.class, java.util.zip.ZipFile.class"), """
                package use;

                import gen.facts.java.lang.StringBuilder_;
                import gen.facts.java.util.zip.ZipFile_;
                import java.lang.constant.ClassDesc;
                import me.supcheg.javafile.facts.PrimitiveToken;
                import me.supcheg.javafile.typed.TypedClassBuilder;
                import me.supcheg.javafile.typed.TypedJavaFile;

                import static me.supcheg.javafile.typed.Expressions.call;
                import static me.supcheg.javafile.typed.Expressions.staticField;

                public final class Run {
                    public static String source() {
                        return TypedJavaFile.class_(ClassDesc.of("out", "Out"), new TypedJavaFile.TypedClassSpec() {
                                    @Override
                                    public <Self> void build(TypedClassBuilder<Self> cb) {
                                        cb.staticMethod(
                                                "capacity",
                                                PrimitiveToken.INT,
                                                StringBuilder_.TOKEN,
                                                (b, builder) -> b.return_(call(builder, StringBuilder_.capacity)));
                                        cb.staticMethod(
                                                "locsig",
                                                PrimitiveToken.LONG,
                                                StringBuilder_.TOKEN,
                                                (b, builder) -> b.return_(staticField(ZipFile_.LOCSIG)));
                                    }
                                })
                                .render();
                    }
                }
                """)
                .orFail();
        Path classes = compilation.writeTo(out.resolve("classes"));
        String source;
        try (URLClassLoader run =
                new URLClassLoader(new URL[] {classes.toUri().toURL()}, JdkTypesTest.class.getClassLoader())) {
            source = (String) run.loadClass("use.Run").getMethod("source").invoke(null);
        }

        assertThat(source).contains("return v0.capacity();").contains("return ZipFile.LOCSIG;");
        Path rendered = Javac.plain().alone().linted().compile(source).clean().writeTo(out.resolve("rendered"));
        try (URLClassLoader loader =
                new URLClassLoader(new URL[] {rendered.toUri().toURL()}, null)) {
            Class<?> made = loader.loadClass("out.Out");
            assertThat(made.getMethod("capacity", StringBuilder.class).invoke(null, new StringBuilder(32)))
                    .isEqualTo(32);
            assertThat(made.getMethod("locsig", StringBuilder.class).invoke(null, new StringBuilder()))
                    .isEqualTo(0x04034b50L);
        }
    }

    /// `Comparator` redeclares `equals(Object)`: that is a member it declares, with a fact of its own, as an
    /// override in a class is; `Object`, a supertype of the interface (JLS 9.2), has the others.
    @Test
    void anInterfaceThatRedeclaresAMethodOfObjectHasAFactOfThatMethodAlone() {
        Map<String, String> sources = processed("java.util.Comparator.class").sources();

        assertThat(sources.get("gen.facts.java.util.Comparator_"))
                .contains(" equals_Object;")
                .doesNotContain("> hashCode")
                .doesNotContain("> toString");
        assertThat(sources.get("gen.facts.java.lang.Object_"))
                .contains("complete = true")
                .contains(" equals_Object = ")
                .contains(" hashCode = ")
                .contains(" toString = ");
    }

    @Test
    void theSupertypesOfACollectionAnEnumARecordAndAFunctionalInterfaceAreFull() throws Exception {
        Path library = Javac.plain()
                .compile("package p; public record Point(int x, int y) {}")
                .orFail()
                .writeTo(out.resolve("lib"));
        Compiled compiled = processor()
                .classpath(library)
                .compile(generator("java.util.ArrayList.class, java.util.concurrent.TimeUnit.class, p.Point.class,"
                        + " java.util.function.UnaryOperator.class, java.util.function.BinaryOperator.class"))
                .orFail();
        Loaded loaded = load(compiled, library);

        assertThat(full(compiled))
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
        assertThat(loaded.memberNames("gen.facts.java.util.AbstractCollection_"))
                .contains("isEmpty", "size");
        assertThat(loaded.memberNames("gen.facts.java.util.Collection_")).contains("stream", "add_E");
        assertThat(loaded.memberNames("gen.facts.java.lang.Enum_")).contains("name", "ordinal", "compareTo_E");
        assertThat(loaded.memberNames("gen.facts.java.lang.Record_")).contains("equals_Object", "hashCode", "toString");
        assertThat(loaded.memberNames("gen.facts.java.util.function.BiFunction_"))
                .contains("apply_T_U", "andThen_Function", "sam");
        assertThat(loaded.factNames("gen.facts.p.Point_")).contains("new_int_int", "x", "y");
        assertThat(compiled.warnings()).isEmpty();
    }

    private static TypeRef applied(ClassDesc type, TypeArg... args) {
        return new ParameterizedTypeRef(type, List.of(args));
    }

    @Test
    void theFactsOfGenericJdkTypesCompileAndDescribeTheirMembers() throws Exception {
        Compiled compiled = processed(GENERIC_TYPES);
        Loaded loaded = load(compiled);
        RefToken<?> string = loaded.token("gen.facts.java.lang.String_");
        RefToken<?> integer = loaded.token("gen.facts.java.lang.Integer_");
        String listMetamodel = "gen.facts.java.util.List_";
        String streamMetamodel = "gen.facts.java.util.stream.Stream_";
        ClassDesc function = ClassDesc.of("java.util.function.Function");
        ClassDesc stream = ClassDesc.of("java.util.stream.Stream");

        Object list = loaded.instance(listMetamodel, string);
        DeclaredToken<?> listOfString = (DeclaredToken<?>) Loaded.fact(list, "token");
        assertThat(listOfString.typeRef()).isEqualTo(applied(ConstantDescs.CD_List, Types.exact(Types.STRING)));
        Invocable get = (Invocable) Loaded.fact(list, "get_int");
        assertThat(get.resultType().orElseThrow()).isSameAs(string);
        assertThat(get.traits()).isEqualTo(MemberTraits.ABSTRACT);
        assertThat(((Invocable) Loaded.fact(list, "add_E")).signature())
                .isEqualTo(new MethodSignature("add", List.of(ConstantDescs.CD_String)));
        Invocable of = (Invocable) loaded.made(listMetamodel, null, "of_EArray", integer);
        assertThat(of.owner()).isSameAs(loaded.fact(listMetamodel, "ANY"));
        assertThat(of.resultType().orElseThrow().typeRef())
                .isEqualTo(applied(ConstantDescs.CD_List, Types.exact(integer.typeRef())));
        assertThat(of.traits()).isEqualTo(MemberTraits.FINAL.withTypeArgs(integer));

        // <R> Stream<R> map(Function<? super T, ? extends R>)
        Object streamOfString = loaded.instance(streamMetamodel, string);
        Invocable map = (Invocable) loaded.made(streamMetamodel, streamOfString, "map_Function", integer);
        assertThat(map.resultType().orElseThrow().typeRef()).isEqualTo(applied(stream, Types.exact(integer.typeRef())));
        assertThat(map.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(
                        applied(function, Types.superBound(Types.STRING), Types.extendsBound(integer.typeRef())));
        assertThat(map.traits()).isEqualTo(MemberTraits.ABSTRACT.withTypeArgs(integer));

        // <X extends Throwable> T orElseThrow(Supplier<? extends X>) throws X
        Object optional = loaded.instance("gen.facts.java.util.Optional_", string);
        assertThat(Loaded.fact(optional, "token")).isInstanceOf(FinalClassToken.class);
        RefToken<?> failure = loaded.token("gen.facts.java.lang.Throwable_");
        Invocable orElseThrow =
                (Invocable) loaded.made("gen.facts.java.util.Optional_", optional, "orElseThrow_Supplier", failure);
        assertThat(List.<Object>copyOf(orElseThrow.traits().throwsTypes())).containsExactly(failure);
        assertThat(orElseThrow.resultType().orElseThrow()).isSameAs(string);

        // the sam of a generic functional interface, declared and inherited
        Object fn = loaded.instance("gen.facts.java.util.function.Function_", string, integer);
        Sam1<?, ?, ?> apply = (Sam1<?, ?, ?>) Loaded.fact(fn, "sam");
        assertThat(apply.method()).isSameAs(Loaded.fact(fn, "apply_T"));
        assertThat(apply.result()).isSameAs(integer);
        assertThat(apply.param1()).isSameAs(string);
        Object operator = loaded.instance("gen.facts.java.util.function.UnaryOperator_", string);
        Sam1<?, ?, ?> inherited = (Sam1<?, ?, ?>) Loaded.fact(operator, "sam");
        assertThat(inherited.method().name()).isEqualTo("apply");
        assertThat(inherited.owner()).isSameAs(Loaded.fact(operator, "token"));
        assertThat(inherited.result()).isSameAs(string);
        assertThat(loaded.memberNames("gen.facts.java.util.function.UnaryOperator_"))
                .containsExactly("identity", "sam");
        // equals(Object) of Comparator does not count
        assertThat(Loaded.fact(loaded.instance("gen.facts.java.util.Comparator_", string), "sam"))
                .isInstanceOf(Sam2.class);
        assertThat(Loaded.fact(loaded.instance("gen.facts.java.lang.Comparable_", string), "sam"))
                .isInstanceOf(Sam1.class);

        // E extends Enum<E>, a nested generic type, a type that mentions itself in every way
        Object entry = loaded.instance("gen.facts.java.util.Map_Entry_", string, integer);
        assertThat(((Invocable) Loaded.fact(entry, "getValue")).resultType().orElseThrow())
                .isSameAs(integer);
        Object cls = loaded.instance("gen.facts.java.lang.Class_", string);
        assertThat(((Invocable) Loaded.fact(cls, "getSuperclass"))
                        .resultType()
                        .orElseThrow()
                        .typeRef())
                .isEqualTo(applied(ClassDesc.of("java.lang.Class"), Types.superBound(Types.STRING)));
        assertThat(compiled.sources().get("gen.facts.java.lang.Enum_"))
                .contains("public final class Enum_<E extends Enum<E>> {")
                .contains("public static <T extends Enum<T>> StaticMethodRef2<T, Class<T>, String>"
                        + " valueOf_Class_String(RefToken<T> t) {");
        Object arrayList = loaded.instance("gen.facts.java.util.ArrayList_", string);
        assertThat(Loaded.fact(arrayList, "new_")).isInstanceOf(CtorRef0.class);
        assertThat(((Invocable) Loaded.fact(arrayList, "new_Collection")).params())
                .extracting(TypeToken::typeRef)
                .containsExactly(applied(ConstantDescs.CD_Collection, Types.extendsBound(Types.STRING)));
    }

    @Test
    void onlyMembersOfTooManyParametersOfTheGenericJdkTypesHaveNoFact() {
        assertThat(processed(GENERIC_TYPES).warnings())
                .allMatch(warning -> warning.startsWith("java.util.Map: no fact of method <K,V>of(")
                        && warning.endsWith(" parameters, more than 12"))
                .hasSize(4);
    }
}
