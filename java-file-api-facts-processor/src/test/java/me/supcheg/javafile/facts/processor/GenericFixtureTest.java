package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `generic` (mini-spec §9.2): a generic type gets a full metamodel
/// in terms of its type parameters, which keep their bounds; a signature
/// that mentions a parameterized or a raw type gets a token made from the
/// shape of that type.
class GenericFixtureTest extends FixtureSupport {
    private static final String[] LIBRARY = {
        """
        package p;
        public class Box<T> {
            public T value;
            public final T initial = null;
            public static int count;
            public static final String NAME = "box";
            public Box(T value) {}
            public Box() {}
            public T get() { return value; }
            public void set(T value) {}
            public Box<T> self() { return this; }
            public Box<Box<T>> nested() { return null; }
            public java.util.List<T> asList() { return null; }
            public T[] toArray(T[] into) { return into; }
            public void addAll(java.util.Collection<? extends T> from) {}
            public void drainTo(java.util.Collection<? super T> to) {}
            public boolean sameAs(Box<?> other) { return false; }
            public java.util.List raw(java.util.Map raw) { return null; }
            public Box rawSelf() { return null; }
            public static Box<String> ofString() { return null; }
            public static int size(Box<?> box) { return 0; }
            public int[] ints() { return null; }
        }
        """,
        """
        package p;
        public final class Pair<A, B> {
            public final A first = null;
            public final B second = null;
            public Pair(A first, B second) {}
            public Pair<B, A> swap() { return null; }
            public java.util.Map.Entry<A, B> entry() { return null; }
        }
        """,
        """
        package p;
        public class Sorted<T extends Comparable<T>> {
            public Sorted(T first) {}
            public T max() { return null; }
            public Sorted<T> with(T next) { return this; }
        }
        """,
        """
        package p;
        public abstract class Both<T extends Number & Comparable<T>> {
            public Both() {}
            public abstract T pick();
        }
        """,
        """
        package p;
        public abstract class Self<S extends Self<S>> {
            public abstract S me();
            public int compare(S other) { return 0; }
        }
        """,
        "package p; public interface Source<T> { T next() throws java.io.IOException; }",
        "package p; public interface Op<T> extends java.util.function.Function<T, T> {}",
        """
        package p;
        public class Uses {
            public java.util.Map<String, ? extends Number> wild;
            public Box<String> strings() { return null; }
            public void take(Sorted<Integer> sorted) {}
            public Box raw() { return null; }
            public java.util.List<? super Integer>[] lists() { return null; }
            public Pair<int[], String[]> arrays() { return null; }
            public java.util.List<?> any() { return null; }
        }
        """,
        "package p; public class Fail<X extends Exception> { public void run() throws X {} }",
        "package p; public class RawBound<T extends Comparable> { public T get() { return null; } }",
        """
        package p;
        public class Names<List, UnsafeFacts, Token> {
            public List first;
            public Token token;
            public java.util.List<UnsafeFacts> all() { return null; }
            public <Data> Data data(Data data) { return data; }
        }
        """
    };

    private static final String ALL =
            "p.Box.class, p.Pair.class, p.Sorted.class, p.Both.class, p.Self.class, p.Source.class,"
                    + " p.Op.class, p.Uses.class, p.Fail.class, p.RawBound.class, p.Names.class, String.class,"
                    + " Integer.class, Object.class";

    private static final String BOX = "gen.facts.p.Box_";
    private static final ClassDesc CD_BOX = ClassDesc.of("p.Box");

    private static RefToken<?> string(ClassLoader loader) throws ReflectiveOperationException {
        return token(loader, "gen.facts.java.lang.String_");
    }

    private static RefToken<?> integer(ClassLoader loader) throws ReflectiveOperationException {
        return token(loader, "gen.facts.java.lang.Integer_");
    }

    private static TypeRef result(Object fact) {
        return ((Invocable) fact).resultType().orElseThrow().typeRef();
    }

    private static List<TypeRef> params(Object fact) {
        return ((Invocable) fact)
                .params().stream().<TypeRef>map(TypeToken::typeRef).toList();
    }

    private static TypeRef applied(ClassDesc type, TypeArg... args) {
        return new ParameterizedTypeRef(type, List.of(args));
    }

    @Test
    void aRequestedGenericTypeGetsAFullMetamodel() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(sources(compilation).get(BOX))
                .contains("complete = true, format = 3)")
                .contains("public final class Box_<T> {");
        assertThat(ProcessorHarness.resources(compilation))
                .containsEntry("META-INF/javafile/metamodel/full/p.Box", "gen.facts.p.Box_\n")
                .doesNotContainKey("META-INF/javafile/metamodel/token/p.Box");
        assertThat(memberNames(loader, BOX))
                .containsExactly(
                        "NAME",
                        "addAll_Collection",
                        "asList",
                        "count",
                        "drainTo_Collection",
                        "get",
                        "initial",
                        "ints",
                        "nested",
                        "new_",
                        "new_T",
                        "ofString",
                        "rawSelf",
                        "raw_Map",
                        "sameAs_Box",
                        "self",
                        "set_T",
                        "size_Box",
                        "toArray_TArray",
                        "value");
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void theFactsOfAnInstanceAreInTermsOfTheTokenItIsMadeWith() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));
        RefToken<?> string = string(loader);
        Object box = instance(loader, BOX, string);
        DeclaredToken<?> token = (DeclaredToken<?>) fact(box, "token");
        TypeRef boxOfString = applied(CD_BOX, Types.exact(Types.STRING));

        assertThat(token).isInstanceOf(OpenClassToken.class);
        assertThat(token.typeRef()).isEqualTo(boxOfString);
        assertThat(token.shape()).isSameAs(shape(loader, BOX));
        Invocable get = (Invocable) fact(box, "get");
        assertThat(get.owner()).isSameAs(token);
        assertThat(get.resultType().orElseThrow()).isSameAs(string);
        assertThat(get.traits()).isEqualTo(MemberTraits.OVERRIDABLE);
        assertThat(params(fact(box, "set_T"))).containsExactly(Types.STRING);
        assertThat(((Invocable) fact(box, "set_T")).params().getFirst()).isSameAs(string);
        assertThat(((MutableFieldRef<?, ?>) fact(box, "value")).type()).isSameAs(string);
        assertThat(((FieldRef<?, ?>) fact(box, "initial")).type()).isSameAs(string);
        assertThat(((FieldRef<?, ?>) fact(box, "initial")).owner()).isSameAs(token);
        assertThat(((Invocable) fact(box, "new_")).owner()).isSameAs(token);
        assertThat(params(fact(box, "new_T"))).containsExactly(Types.STRING);
        // the type itself, applied to its own type parameters, is the token of the instance
        assertThat(((Invocable) fact(box, "self")).resultType().orElseThrow()).isSameAs(token);
        assertThat(result(fact(box, "nested"))).isEqualTo(applied(CD_BOX, Types.exact(boxOfString)));
        assertThat(result(fact(box, "toArray_TArray"))).isEqualTo(Types.array(Types.STRING));
        assertThat(params(fact(box, "toArray_TArray"))).containsExactly(Types.array(Types.STRING));
        assertThat(((Invocable) fact(box, "ints")).resultType().orElseThrow()).isEqualTo(PrimitiveToken.INT.array());
        // the method table of the token is erased with its argument, as the signature of the fact is
        assertThat(token.methods().concreteMethods())
                .contains(new MethodSignature("set", List.of(ConstantDescs.CD_String)))
                .contains(((Invocable) fact(box, "set_T")).signature());
    }

    @Test
    void parameterizedTypesInSignaturesAreTokensOfTheirShapes() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        Object box = instance(loader, BOX, string(loader));

        DeclaredToken<?> list = (DeclaredToken<?>)
                ((Invocable) fact(box, "asList")).resultType().orElseThrow();
        assertThat(list.typeRef()).isEqualTo(applied(ConstantDescs.CD_List, Types.exact(Types.STRING)));
        assertThat(list.shape()).isSameAs(shape(loader, "gen.facts.java.util.List_"));
        assertThat(list.methods().abstractMethods())
                .contains(new MethodSignature("add", List.of(ConstantDescs.CD_String)));
        // wildcards of every kind
        assertThat(params(fact(box, "addAll_Collection")))
                .containsExactly(applied(ConstantDescs.CD_Collection, Types.extendsBound(Types.STRING)));
        assertThat(params(fact(box, "drainTo_Collection")))
                .containsExactly(applied(ConstantDescs.CD_Collection, Types.superBound(Types.STRING)));
        assertThat(params(fact(box, "sameAs_Box"))).containsExactly(applied(CD_BOX, Types.unbounded()));
        // a metamodel refers to the others by their shapes alone: no token and no instance of another
        assertNoTokenOfAnotherMetamodel(sources(compilation));
        assertThat(sources(compilation).values()).noneMatch(source -> source.matches("(?s).*\\bnew \\w+_<.*"));
        assertThat(sources(compilation).get(BOX))
                .contains("UnsafeFacts.<List<T>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE,"
                        + " TokenArg.exact(t))")
                .contains("UnsafeFacts.<Collection<? super T>>interfaceToken("
                        + "gen.facts.java.util.Collection_.Data.SHAPE, TokenArg.superBound(t))")
                .contains("UnsafeFacts.<Box<Box<T>>>openClassToken(Data.SHAPE, TokenArg.exact(token))");
    }

    @Test
    void aGenericTypeWithoutTypeArgumentsIsARawToken() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        Object box = instance(loader, BOX, string(loader));

        DeclaredToken<?> rawList = (DeclaredToken<?>)
                ((Invocable) fact(box, "raw_Map")).resultType().orElseThrow();
        assertThat(rawList.typeRef()).isEqualTo(Types.of(ConstantDescs.CD_List));
        assertThat(rawList.shape()).isSameAs(shape(loader, "gen.facts.java.util.List_"));
        assertThat(params(fact(box, "raw_Map"))).containsExactly(Types.of(ConstantDescs.CD_Map));
        // the raw type of the type itself is not the token of the instance
        assertThat(result(fact(box, "rawSelf"))).isEqualTo(Types.of(CD_BOX));
        assertThat(result(fact(loader, "gen.facts.p.Uses_", "raw"))).isEqualTo(Types.of(CD_BOX));
        assertThat(sources(compilation).get(BOX))
                .contains("\"rawtypes\"")
                .contains("MethodRef1<Box<T>, List, Map> raw_Map")
                .contains("UnsafeFacts.<Box>openClassToken(Data.SHAPE)");
        assertThat(sources(compilation).get("gen.facts.p.Pair_")).doesNotContain("\"rawtypes\"");
        // a raw type in a bound of a type parameter
        assertThat(sources(compilation).get("gen.facts.p.RawBound_"))
                .contains("\"rawtypes\"")
                .contains("public final class RawBound_<T extends Comparable> {");
    }

    @Test
    void theStaticMembersOfAGenericTypeAreFactsOfTheClassOwnedByAny() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));
        Object any = fact(loader, BOX, "ANY");

        StaticFieldRef<?> name = (StaticFieldRef<?>) fact(loader, BOX, "NAME");
        assertThat(name.constantValue()).contains("box");
        assertThat(name.owner()).isSameAs(any);
        assertThat(fact(loader, BOX, "count")).isInstanceOf(MutableStaticFieldRef.class);
        Invocable ofString = (Invocable) fact(loader, BOX, "ofString");
        assertThat(ofString.owner()).isSameAs(any);
        assertThat(result(ofString)).isEqualTo(applied(CD_BOX, Types.exact(Types.STRING)));
        assertThat(params(fact(loader, BOX, "size_Box"))).containsExactly(applied(CD_BOX, Types.unbounded()));
        assertThat(factNames(loader, BOX)).containsExactly("ANY", "NAME", "count", "ofString", "size_Box");
    }

    @Test
    void everyTypeParameterTakesAToken() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));
        RefToken<?> string = string(loader);
        RefToken<?> integer = integer(loader);
        ClassDesc pairDesc = ClassDesc.of("p.Pair");
        Object pair = instance(loader, "gen.facts.p.Pair_", string, integer);

        assertThat(fact(pair, "token")).isInstanceOf(FinalClassToken.class);
        assertThat(((DeclaredToken<?>) fact(pair, "token")).typeRef())
                .isEqualTo(applied(pairDesc, Types.exact(Types.STRING), Types.exact(integer.typeRef())));
        assertThat(((FieldRef<?, ?>) fact(pair, "first")).type()).isSameAs(string);
        assertThat(((FieldRef<?, ?>) fact(pair, "second")).type()).isSameAs(integer);
        assertThat(params(fact(pair, "new_A_B"))).containsExactly(Types.STRING, integer.typeRef());
        // another parameterization of the type itself is made from its own shape
        DeclaredToken<?> swapped =
                (DeclaredToken<?>) ((Invocable) fact(pair, "swap")).resultType().orElseThrow();
        assertThat(swapped.typeRef())
                .isEqualTo(applied(pairDesc, Types.exact(integer.typeRef()), Types.exact(Types.STRING)));
        assertThat(swapped.shape()).isSameAs(shape(loader, "gen.facts.p.Pair_"));
        assertThat(result(fact(pair, "entry")))
                .isEqualTo(applied(
                        ClassDesc.of("java.util.Map$Entry"),
                        Types.exact(Types.STRING),
                        Types.exact(integer.typeRef())));
        assertThat(((Invocable) fact(pair, "swap")).traits()).isEqualTo(MemberTraits.FINAL);
    }

    @Test
    void theTypeParametersOfAMetamodelKeepTheirBounds() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);

        assertThat(sources(compilation).get("gen.facts.p.Sorted_"))
                .contains("public final class Sorted_<T extends Comparable<T>> {")
                .contains("public Sorted_(RefToken<T> t) {");
        assertThat(sources(compilation).get("gen.facts.p.Both_"))
                .contains("public final class Both_<T extends Number & Comparable<T>> {");
        assertThat(sources(compilation).get("gen.facts.p.Self_"))
                .contains("public final class Self_<S extends Self<S>> {");
        assertThat(sources(compilation).get("gen.facts.p.Fail_"))
                .contains("public final class Fail_<X extends Exception> {");
        assertThat(shape(loader, "gen.facts.p.Both_").typeParameters())
                .containsExactly(new TypeParam(
                        "T",
                        List.of(
                                Types.of(ClassDesc.of("java.lang.Number")),
                                Types.parameterized(ClassDesc.of("java.lang.Comparable"), Types.typeVar("T")))));
        // the constructor of an abstract class is a fact for super(...) alone
        Object both = instance(loader, "gen.facts.p.Both_", integer(loader));
        assertThat(fact(both, "super_")).isInstanceOf(AbstractCtorRef0.class);
        assertThat(result(fact(both, "pick"))).isEqualTo(integer(loader).typeRef());
        assertThat(((Invocable) fact(both, "pick")).traits()).isEqualTo(MemberTraits.ABSTRACT);
    }

    @Test
    void aTokenOfATypeArgumentOutOfBoundsDoesNotCompile() {
        load(generate(ALL, LIBRARY));
        String imports = """
                package u;
                import gen.facts.p.Sorted_;
                import gen.facts.p.Both_;
                import gen.facts.p.Box_;
                import gen.facts.p.Self_;
                import gen.facts.java.lang.String_;
                import gen.facts.java.lang.Integer_;
                import gen.facts.java.lang.Object_;
                """;

        // the control: the same code with type arguments within bounds
        assertThat(errors(use(imports + """
                class Use {
                    Object sorted = new Sorted_<>(String_.TOKEN);
                    Object explicit = new Sorted_<String>(String_.TOKEN);
                    Object both = new Both_<>(Integer_.TOKEN);
                    Object box = new Box_<>(Object_.TOKEN);
                }
                """))).isEmpty();
        assertThat(errors(use(imports + "class Use { Object o = new Sorted_<Object>(Object_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("type argument java.lang.Object is not within bounds of type-variable T");
        assertThat(errors(use(imports + "class Use { Object o = new Sorted_<>(Object_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("has incompatible bounds");
        // Comparable<String>, but not a Number
        assertThat(errors(use(imports + "class Use { Object o = new Both_<>(String_.TOKEN); }")))
                .singleElement()
                .asString()
                .contains("has incompatible bounds");
        // Box<String> is not a Self<Box<String>>
        assertThat(errors(use(imports + "class Use { Object o = new Self_<>(new Box_<>(String_.TOKEN).token); }")))
                .singleElement()
                .asString()
                .contains("has incompatible bounds");
    }

    @Test
    void aGenericFunctionalInterfaceHasASam() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        RefToken<?> string = string(loader);

        Object source = instance(loader, "gen.facts.p.Source_", string);
        Sam0<?, ?> sam = (Sam0<?, ?>) fact(source, "sam");
        assertThat(sam.method()).isSameAs(fact(source, "next"));
        assertThat(sam.owner()).isSameAs(fact(source, "token"));
        assertThat(sam.result()).isSameAs(string);
        assertThat(sam.method().traits().throwsTypes())
                .extracting(RefToken::typeRef)
                .containsExactly(Types.of(ClassDesc.of("java.io.IOException")));
        // inherited: Function.apply as a member of Op<T>, with T for both of its type arguments
        Object op = instance(loader, "gen.facts.p.Op_", string);
        Sam1<?, ?, ?> inherited = (Sam1<?, ?, ?>) fact(op, "sam");
        assertThat(inherited.method().name()).isEqualTo("apply");
        assertThat(inherited.owner()).isSameAs(fact(op, "token"));
        assertThat(inherited.result()).isSameAs(string);
        assertThat(inherited.param1()).isSameAs(string);
        assertThat(inherited.method().traits()).isEqualTo(MemberTraits.ABSTRACT);
        assertThat(memberNames(loader, "gen.facts.p.Op_")).containsExactly("sam");
        assertThat(sources(compilation).get("gen.facts.p.Op_"))
                .contains("this.sam = UnsafeFacts.sam(UnsafeFacts.method(token, \"apply\", t,"
                        + " UnsafeFacts.param(t, Param.var(0)),"
                        + " MemberTraits.ABSTRACT));");
        // the types the inherited sam mentions are in the closure: none here but the type parameter
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void aTypeThatIsNotGenericMentionsParameterizedTypesByTheirShapes() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        String uses = "gen.facts.p.Uses_";
        TypeRef integer = integer(loader).typeRef();

        DeclaredToken<?> strings = (DeclaredToken<?>)
                ((Invocable) fact(loader, uses, "strings")).resultType().orElseThrow();
        assertThat(strings.typeRef()).isEqualTo(applied(CD_BOX, Types.exact(Types.STRING)));
        assertThat(strings.shape()).isSameAs(shape(loader, BOX));
        assertThat(params(fact(loader, uses, "take_Sorted")))
                .containsExactly(applied(ClassDesc.of("p.Sorted"), Types.exact(integer)));
        assertThat(((MutableFieldRef<?, ?>) fact(loader, uses, "wild")).type().typeRef())
                .isEqualTo(applied(
                        ConstantDescs.CD_Map,
                        Types.exact(Types.STRING),
                        Types.extendsBound(Types.of(ClassDesc.of("java.lang.Number")))));
        assertThat(result(fact(loader, uses, "lists")))
                .isEqualTo(Types.array(applied(ConstantDescs.CD_List, Types.superBound(integer))));
        assertThat(result(fact(loader, uses, "arrays")))
                .isEqualTo(applied(
                        ClassDesc.of("p.Pair"),
                        Types.exact(Types.array(Types.INT)),
                        Types.exact(Types.array(Types.STRING))));
        assertThat(result(fact(loader, uses, "any"))).isEqualTo(applied(ConstantDescs.CD_List, Types.unbounded()));
        assertThat(sources(compilation).get(uses))
                .contains("UnsafeFacts.<Box<String>>openClassToken(Box_.Data.SHAPE, TokenArg.exact(");
        // the types of the type arguments are in the closure
        assertThat(sources(compilation).keySet())
                .contains("gen.facts.java.lang.Number_", "gen.facts.java.util.Map_", "gen.facts.java.util.List_");
    }

    @Test
    void aMethodOfAGenericTypeThrowsTheTokenOfItsTypeParameter() throws Exception {
        ClassLoader loader = load(generate(ALL, LIBRARY));
        RefToken<?> ioException = token(loader, "gen.facts.java.io.IOException_");

        Object fail = instance(loader, "gen.facts.p.Fail_", ioException);

        assertThat(List.<Object>copyOf(((Invocable) fact(fail, "run")).traits().throwsTypes()))
                .containsExactly(ioException);
    }

    @Test
    void aTypeParameterNamedLikeWhatTheMetamodelUsesIsRenamed() throws Exception {
        Compilation compilation = generate(ALL, LIBRARY);
        ClassLoader loader = load(compilation);
        String names = "gen.facts.p.Names_";
        RefToken<?> string = string(loader);
        RefToken<?> integer = integer(loader);
        RefToken<?> object = token(loader, "gen.facts.java.lang.Object_");

        // List and UnsafeFacts are classes the metamodel names; the field token is escaped, so the
        // parameter after the type parameter Token is escaped once more
        assertThat(sources(compilation).get(names))
                .contains("public final class Names_<List__, UnsafeFacts_, Token> {")
                .contains("MutableFieldRef<Names<List__, UnsafeFacts_, Token>, Token> token_;")
                .contains("public Names_(RefToken<List__> list__, RefToken<UnsafeFacts_> unsafeFacts_,"
                        + " RefToken<Token> token__) {")
                .contains("public <Data_> MethodRef1<Names<List__, UnsafeFacts_, Token>, Data_, Data_>"
                        + " data_Data(RefToken<Data_> data_) {");
        assertThat(memberNames(loader, names)).containsExactly("all", "data_Data", "first", "new_", "token_");
        // the shape keeps the names the type declares
        assertThat(shape(loader, names).typeParameters())
                .extracting(TypeParam::name)
                .containsExactly("List", "UnsafeFacts", "Token");
        Object instance = instance(loader, names, string, integer, object);
        assertThat(((MutableFieldRef<?, ?>) fact(instance, "first")).type()).isSameAs(string);
        assertThat(((MutableFieldRef<?, ?>) fact(instance, "token_")).name()).isEqualTo("token");
        assertThat(((MutableFieldRef<?, ?>) fact(instance, "token_")).type()).isSameAs(object);
        assertThat(result(fact(instance, "all")))
                .isEqualTo(applied(ConstantDescs.CD_List, Types.exact(integer.typeRef())));
        Invocable data = (Invocable) made(loader, names, instance, "data_Data", string);
        assertThat(data.params()).containsExactly(string);
        assertThat(data.traits().typeArgs()).containsExactly(string);
    }

    private static final String[] BOUNDS = {
        "package p; class Hidden {}",
        "package p; public class Dol$lar {}",
        "package p; public @interface Marker {}",
        "package p; public class ByHidden<T extends Hidden> {}",
        "package p; public class ByDollar<T extends java.util.List<Dol$lar>> {}",
        "package p; public class ByMarker<T extends Marker> {}",
        """
        package p;
        public class Mentions {
            public ByHidden<?> hidden() { return null; }
            public ByDollar<?> dollar() { return null; }
            public ByMarker<?> marker() { return null; }
        }
        """
    };

    @Test
    void aMentionedTypeWhoseBoundsTheMetamodelCannotWriteTakesNoTokens() throws Exception {
        Compilation compilation = generate("p.Mentions.class", BOUNDS);
        ClassLoader loader = load(compilation);

        // without the bound javac would take a token of any type: the metamodel has no type parameters
        assertThat(sources(compilation).get("gen.facts.p.ByHidden_"))
                .contains("public final class ByHidden_ {")
                .contains(
                        "public static final OpenClassToken<ByHidden> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);")
                .doesNotContain("RefToken");
        assertThat(sources(compilation).get("gen.facts.p.ByDollar_"))
                .contains("public final class ByDollar_ {")
                .doesNotContain("RefToken");
        // a bound the metamodel can write is kept, whether or not its type has a metamodel
        assertThat(sources(compilation).get("gen.facts.p.ByMarker_"))
                .contains("public final class ByMarker_<T extends Marker> {")
                .contains("public ByMarker_(RefToken<T> t) {");
        // the shape is that of the generic type all the same, and a signature applies it
        String mentions = "gen.facts.p.Mentions_";
        assertThat(shape(loader, "gen.facts.p.ByHidden_").typeParameters()).hasSize(1);
        DeclaredToken<?> hidden = (DeclaredToken<?>)
                ((Invocable) fact(loader, mentions, "hidden")).resultType().orElseThrow();
        assertThat(hidden.typeRef()).isEqualTo(applied(ClassDesc.of("p.ByHidden"), Types.unbounded()));
        assertThat(hidden.shape()).isSameAs(shape(loader, "gen.facts.p.ByHidden_"));
        assertThat(result(fact(loader, mentions, "dollar")))
                .isEqualTo(applied(ClassDesc.of("p.ByDollar"), Types.unbounded()));
        assertThat(result(fact(loader, mentions, "marker")))
                .isEqualTo(applied(ClassDesc.of("p.ByMarker"), Types.unbounded()));
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void aRequestedTypeWhoseBoundsTheMetamodelCannotDeclareIsAnError() {
        // the control: the type that mentions them is requested with no error
        assertThat(errors(attempt(List.of(), "p.Mentions.class", BOUNDS))).isEmpty();
        assertThat(errors(attempt(List.of(), "p.ByHidden.class", BOUNDS)))
                .containsExactly("no metamodel of p.ByHidden: the bounds of the type parameters of p.ByHidden mention"
                        + " types that are not public: p.Hidden");
        assertThat(errors(attempt(List.of(), "p.ByDollar.class", BOUNDS)))
                .containsExactly("no metamodel of p.ByDollar: the bounds of the type parameters of p.ByDollar mention"
                        + " p.Dol$lar, which has no metamodel: a class with $ in its simple name is not supported yet");
        assertThat(errors(attempt(List.of(), "p.ByMarker.class", BOUNDS)))
                .containsExactly("no metamodel of p.ByMarker: the bounds of the type parameters of p.ByMarker mention"
                        + " p.Marker, which has no metamodel: annotation interface p.Marker is not supported yet");
    }
}
