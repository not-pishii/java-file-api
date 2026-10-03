package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.FactParam;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.jdk.CharSequence_;
import me.supcheg.javafile.facts.jdk.Integer_;
import me.supcheg.javafile.facts.jdk.String_;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// A fact is known among the members of its owner by the signature it
/// declares, not by the erased one (§6.1, §10: one overload is one fact).
/// Two overloads that erase alike under the type arguments of a fact —
/// `m(T)` and `m(String)` of a `Twin<String>`, `<T> g(T)` with `String` for
/// `T` and `g(String)` — are not told apart by any cast: lowering rejects
/// the call where both are members of the owner, and calls through the
/// owner where the receiver's type adds the other one.
class OverloadTwinsTest {

    /// Phantom of `fixtures.Twin<T>`.
    interface TwinP<T> {}

    /// Phantom of `fixtures.SubTwin<T>`, a subclass of `Twin<T>`.
    interface SubTwinP<T> extends TwinP<T> {}

    /// Phantom of `fixtures.Plain` and of its subclass `fixtures.SubPlain`.
    interface PlainP {}

    interface SubPlainP extends PlainP {}

    private static final ClassDesc TWIN = ClassDesc.of("fixtures", "Twin");
    private static final ClassDesc SUB_TWIN = ClassDesc.of("fixtures", "SubTwin");
    private static final ClassDesc PLAIN = ClassDesc.of("fixtures", "Plain");
    private static final ClassDesc SUB_PLAIN = ClassDesc.of("fixtures", "SubPlain");

    private static final Param OBJECT = Param.fixed(ConstantDescs.CD_Object);
    private static final Param STRING = Param.fixed(ConstantDescs.CD_String);

    /// `m(T)` and `m(String)`.
    private static final Set<Signature> M_OF_T_AND_OF_STRING =
            Set.of(Signature.of("m", Param.var(0)), Signature.of("m", STRING));

    /// `<T> g(T)` and `g(String)`.
    private static final Set<Signature> GENERIC_G_AND_G_OF_STRING =
            Set.of(Signature.of("g", OBJECT), Signature.of("g", STRING));

    private static TypeShape<DeclaredKind.OpenClass> shape(
            ClassDesc desc, List<String> typeParameters, MethodTableTemplate methods) {
        return UnsafeFacts.shape(
                DeclaredKind.OPEN_CLASS,
                desc,
                typeParameters.stream()
                        .map(name -> new TypeParam(name, List.of()))
                        .toList(),
                List.of(ConstantDescs.CD_Object),
                Supertypes.NONE,
                methods,
                List.of(),
                false);
    }

    private static MethodTableTemplate instanceMethods(Set<Signature> methods) {
        return new MethodTableTemplate(Set.of(), methods, Set.of());
    }

    private static final TypeShape<DeclaredKind.OpenClass> TWIN_SHAPE = shape(
            TWIN,
            List.of("T"),
            new MethodTableTemplate(
                    Set.of(),
                    M_OF_T_AND_OF_STRING,
                    Set.of(),
                    Set.of(Signature.of("Twin", Param.var(0)), Signature.of("Twin", STRING))));

    private static <T> OpenClassToken<TwinP<T>> twin(RefToken<T> argument) {
        return UnsafeFacts.openClassToken(TWIN_SHAPE, TokenArg.exact(argument));
    }

    /// `String m(T)` of `Twin<T>`.
    private static <T> MethodRef1<TwinP<T>, String, T> mOfT(DeclaredToken<TwinP<T>> owner, RefToken<T> argument) {
        return UnsafeFacts.method(
                owner, "m", String_.TOKEN, UnsafeFacts.param(argument, Param.var(0)), MemberTraits.DEFAULT);
    }

    /// `String m(String)` of `Twin<T>`.
    private static <T> MethodRef1<TwinP<T>, String, String> mOfString(DeclaredToken<TwinP<T>> owner) {
        return UnsafeFacts.method(owner, "m", String_.TOKEN, String_.TOKEN, MemberTraits.DEFAULT);
    }

    /// `<T> String g(T)` of `owner`, with `witness` for `T`.
    private static <O, T> MethodRef1<O, String, T> genericG(DeclaredToken<O> owner, RefToken<T> witness) {
        return UnsafeFacts.method(
                owner,
                "g",
                String_.TOKEN,
                UnsafeFacts.param(witness, OBJECT),
                MemberTraits.DEFAULT.withTypeArgs(witness));
    }

    /// Renders `static String call(R receiver) { return <body>; }`.
    private static <R> String render(RefToken<R> receiver, Function<Expr<R>, Expr<String>> body) {
        return TypedJavaFile.class_(ClassDesc.of("me.supcheg.example", "Twins"), new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        cb.staticMethod("call", String_.TOKEN, receiver, (b, r) -> b.return_(body.apply(r)));
                    }
                })
                .render();
    }

    // ------------------------------------------------------------------
    // a method of a type parameter and an overload of the type argument
    // ------------------------------------------------------------------

    @Test
    void aMethodOfATypeParameterAndTheOverloadOfItsArgumentAreRejectedBothWays() {
        OpenClassToken<TwinP<String>> strings = twin(String_.TOKEN);

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> render(strings, t -> call(t, mOfT(strings, String_.TOKEN), literal("x"))))
                .withMessageContaining("declared m(#0) and is m(java.lang.String) here")
                .withMessageContaining("as the overload declared m(java.lang.String) is")
                .withMessageContaining("in fixtures.Twin<java.lang.String>")
                .satisfies(e -> assertThat(e.similar()).containsExactly("m(java.lang.String)"));
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> render(strings, t -> call(t, mOfString(strings), literal("x"))))
                .withMessageContaining("declared m(java.lang.String) and is m(java.lang.String) here")
                .withMessageContaining("as the overload declared m(#0) is");
    }

    @Test
    void underAnotherTypeArgumentBothAreCalled() {
        OpenClassToken<TwinP<Integer>> integers = twin(Integer_.TOKEN);
        OpenClassToken<TwinP<CharSequence>> sequences = twin(CharSequence_.TOKEN);

        assertThat(render(integers, t -> call(t, mOfT(integers, Integer_.TOKEN), literalNull(Integer_.TOKEN))))
                .contains("v0.m((Integer) null)");
        assertThat(render(integers, t -> call(t, mOfString(integers), literal("x"))))
                .contains("v0.m(\"x\")");
        // m(String) is more specific for a String: the argument is cast to what m(T) takes
        assertThat(render(sequences, t -> call(t, mOfT(sequences, CharSequence_.TOKEN), literal("x"))))
                .contains("v0.m((CharSequence) \"x\")");
    }

    @Test
    void theOnlyMethodOfItsNameIsCalledWithoutACast() {
        TypeShape<DeclaredKind.OpenClass> lone =
                shape(TWIN, List.of("T"), instanceMethods(Set.of(Signature.of("m", Param.var(0)))));
        OpenClassToken<TwinP<CharSequence>> sequences =
                UnsafeFacts.openClassToken(lone, TokenArg.exact(CharSequence_.TOKEN));

        assertThat(render(sequences, t -> call(t, mOfT(sequences, CharSequence_.TOKEN), literal("x"))))
                .contains("v0.m(\"x\")");
        // a fact that does not say it is declared by the type parameter is not the method of the table
        assertThat(render(
                        sequences,
                        t -> call(
                                t,
                                UnsafeFacts.method(
                                        sequences, "m", String_.TOKEN, CharSequence_.TOKEN, MemberTraits.DEFAULT),
                                literal("x"))))
                .contains("v0.m((CharSequence) \"x\")");
    }

    @Test
    void aParameterDeclaredByATypeParameterIsOfItsTypeArgument() {
        OpenClassToken<TwinP<String>> strings = twin(String_.TOKEN);
        FactParam<Integer> notTheArgument = UnsafeFacts.param(Integer_.TOKEN, Param.var(0));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.method(strings, "m", String_.TOKEN, notTheArgument, MemberTraits.DEFAULT))
                .withMessage("a parameter declared #0 of fixtures.Twin<java.lang.String> is java.lang.String,"
                        + " not java.lang.Integer");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.method(
                        strings,
                        "m",
                        String_.TOKEN,
                        UnsafeFacts.param(String_.TOKEN, Param.var(1)),
                        MemberTraits.DEFAULT))
                .withMessageContaining("is of no type parameter");
    }

    @Test
    void anArrayOfATypeParameterAndTheOverloadOfTheArrayOfItsArgumentAreRejected() {
        // class Twin<T> { String n(T[]); String n(Integer[]); }
        TypeShape<DeclaredKind.OpenClass> arrays = shape(
                TWIN,
                List.of("T"),
                instanceMethods(Set.of(
                        Signature.of("n", Param.var(0, 1)),
                        Signature.of(
                                "n",
                                Param.fixed(ClassDesc.of("java.lang.Integer").arrayType())))));
        OpenClassToken<TwinP<Integer>> integers = UnsafeFacts.openClassToken(arrays, TokenArg.exact(Integer_.TOKEN));
        OpenClassToken<TwinP<String>> strings = UnsafeFacts.openClassToken(arrays, TokenArg.exact(String_.TOKEN));

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() ->
                        render(integers, t -> call(t, nOfTs(integers, Integer_.TOKEN), noneOf(Integer_.TOKEN))))
                .withMessageContaining("declared n(#0[]) and is n(java.lang.Integer[]) here")
                .withMessageContaining("as the overload declared n(java.lang.Integer[]) is");
        assertThat(render(strings, t -> call(t, nOfTs(strings, String_.TOKEN), noneOf(String_.TOKEN))))
                .contains("v0.n((String[]) null)");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.method(
                        strings,
                        "n",
                        String_.TOKEN,
                        UnsafeFacts.param(ArrayToken.of(Integer_.TOKEN), Param.var(0, 1)),
                        MemberTraits.DEFAULT))
                .withMessage("a parameter declared #0[] of fixtures.Twin<java.lang.String> is java.lang.String[],"
                        + " not java.lang.Integer[]");
    }

    /// `String n(T[])` of `Twin<T>`.
    private static <T> MethodRef1<TwinP<T>, String, T[]> nOfTs(DeclaredToken<TwinP<T>> owner, RefToken<T> argument) {
        return UnsafeFacts.method(
                owner,
                "n",
                String_.TOKEN,
                UnsafeFacts.param(ArrayToken.of(argument), Param.var(0, 1)),
                MemberTraits.DEFAULT);
    }

    private static <T> Expr<T[]> noneOf(RefToken<T> component) {
        return literalNull(ArrayToken.of(component));
    }

    // ------------------------------------------------------------------
    // a generic method and an overload of its type argument
    // ------------------------------------------------------------------

    private static final OpenClassToken<PlainP> PLAIN_TOKEN =
            UnsafeFacts.openClassToken(shape(PLAIN, List.of(), instanceMethods(GENERIC_G_AND_G_OF_STRING)));

    @Test
    void aGenericMethodGivenTheTypeOfItsOverloadIsRejected() {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> render(PLAIN_TOKEN, p -> call(p, genericG(PLAIN_TOKEN, String_.TOKEN), literal("x"))))
                .withMessageContaining("declared g(java.lang.Object) and is g(java.lang.String) here")
                .withMessageContaining("as the overload declared g(java.lang.String) is in fixtures.Plain");
    }

    @Test
    void aGenericMethodGivenAnotherTypeAndItsOverloadAreCalled() {
        assertThat(render(
                        PLAIN_TOKEN, p -> call(p, genericG(PLAIN_TOKEN, Integer_.TOKEN), literalNull(Integer_.TOKEN))))
                .contains("v0.<Integer>g((Integer) null)");
        // javac prefers g(String) to <T> g(T) for a String: the fact of g(String) is what it resolves
        assertThat(render(
                        PLAIN_TOKEN,
                        p -> call(
                                p,
                                UnsafeFacts.method(
                                        PLAIN_TOKEN, "g", String_.TOKEN, String_.TOKEN, MemberTraits.DEFAULT),
                                literal("x"))))
                .contains("v0.g(\"x\")");
    }

    @Test
    void aStaticGenericMethodGivenTheTypeOfItsOverloadIsRejected() {
        OpenClassToken<PlainP> statics = UnsafeFacts.openClassToken(
                shape(PLAIN, List.of(), new MethodTableTemplate(Set.of(), Set.of(), GENERIC_G_AND_G_OF_STRING)));

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> renderStaticG(statics, String_.TOKEN))
                .withMessageContaining("declared g(java.lang.Object) and is g(java.lang.String) here");
        assertThat(renderStaticG(statics, Integer_.TOKEN)).contains("Plain.<Integer>g((Integer) null)");
    }

    /// Renders the call of `static <T> String g(T)` of `owner` with `witness` for `T`.
    private static <T> String renderStaticG(DeclaredToken<?> owner, RefToken<T> witness) {
        StaticMethodRef1<String, T> g = UnsafeFacts.staticMethod(
                owner,
                "g",
                String_.TOKEN,
                UnsafeFacts.param(witness, OBJECT),
                MemberTraits.FINAL.withTypeArgs(witness));
        return render(String_.TOKEN, s -> staticCall(g, literalNull(witness)));
    }

    // ------------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------------

    @Test
    void aConstructorOfATypeParameterAndTheOverloadOfItsArgumentAreRejected() {
        OpenClassToken<TwinP<String>> strings = twin(String_.TOKEN);
        OpenClassToken<TwinP<Integer>> integers = twin(Integer_.TOKEN);

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> render(String_.TOKEN, s -> {
                    Expr<TwinP<String>> made = new_(
                            UnsafeFacts.ctor(
                                    strings, UnsafeFacts.param(String_.TOKEN, Param.var(0)), MemberTraits.DEFAULT),
                            literal("x"));
                    return call(made, mOfString(strings), s);
                }))
                .withMessageContaining("declared Twin(#0) and is Twin(java.lang.String) here")
                .withMessageContaining("as the overload declared Twin(java.lang.String) is");
        assertThat(render(String_.TOKEN, s -> {
                    Expr<TwinP<Integer>> made = new_(
                            UnsafeFacts.ctor(
                                    integers, UnsafeFacts.param(Integer_.TOKEN, Param.var(0)), MemberTraits.DEFAULT),
                            literalNull(Integer_.TOKEN));
                    return call(made, mOfString(integers), s);
                }))
                .contains("new Twin<Integer>((Integer) null).m(v0)");
    }

    // ------------------------------------------------------------------
    // an overload added by the type of the receiver
    // ------------------------------------------------------------------

    @Test
    void anOverloadAddedByTheSubtypeOfTheReceiverIsLeftOutByCallingThroughTheOwner() {
        TypeShape<DeclaredKind.OpenClass> base =
                shape(TWIN, List.of("T"), instanceMethods(Set.of(Signature.of("m", Param.var(0)))));
        TypeShape<DeclaredKind.OpenClass> sub = shape(SUB_TWIN, List.of("T"), instanceMethods(M_OF_T_AND_OF_STRING));
        OpenClassToken<TwinP<String>> baseOfString = UnsafeFacts.openClassToken(base, TokenArg.exact(String_.TOKEN));
        OpenClassToken<TwinP<Integer>> baseOfInteger = UnsafeFacts.openClassToken(base, TokenArg.exact(Integer_.TOKEN));
        OpenClassToken<SubTwinP<String>> subOfString = UnsafeFacts.openClassToken(sub, TokenArg.exact(String_.TOKEN));
        OpenClassToken<SubTwinP<Integer>> subOfInteger =
                UnsafeFacts.openClassToken(sub, TokenArg.exact(Integer_.TOKEN));

        // SubTwin<String> has m(T) and m(String), both m(String): only Twin<String> has one
        assertThat(render(subOfString, s -> call(s, mOfT(baseOfString, String_.TOKEN), literal("x"))))
                .contains("((Twin<String>) v0).m(\"x\")");
        assertThat(render(subOfInteger, s -> call(s, mOfT(baseOfInteger, Integer_.TOKEN), literalNull(Integer_.TOKEN))))
                .contains("v0.m((Integer) null)");
    }

    @Test
    void anOverloadOfAnInheritedGenericMethodIsLeftOutByCallingThroughTheOwner() {
        OpenClassToken<PlainP> base =
                UnsafeFacts.openClassToken(shape(PLAIN, List.of(), instanceMethods(Set.of(Signature.of("g", OBJECT)))));
        OpenClassToken<SubPlainP> sub =
                UnsafeFacts.openClassToken(shape(SUB_PLAIN, List.of(), instanceMethods(GENERIC_G_AND_G_OF_STRING)));

        assertThat(render(sub, s -> call(s, genericG(base, String_.TOKEN), literal("x"))))
                .contains("((Plain) v0).<String>g(\"x\")");
        assertThat(render(sub, s -> call(s, genericG(base, Integer_.TOKEN), literalNull(Integer_.TOKEN))))
                .contains("v0.<Integer>g((Integer) null)");
    }

    @Test
    void aReceiverOfATypeVariableIsCalledThroughTheOwnerAsItsBoundsAreNotKnown() {
        // <X extends SubPlain> with SubPlain adding g(String): nothing says so to lowering
        OpenClassToken<PlainP> base =
                UnsafeFacts.openClassToken(shape(PLAIN, List.of(), instanceMethods(Set.of(Signature.of("g", OBJECT)))));
        TypeVarToken<PlainP> x = UnsafeFacts.typeVarToken(Types.typeVar("X"), PLAIN);

        assertThat(render(x, v -> call(v, genericG(base, String_.TOKEN), literal("x"))))
                .contains("((Plain) v0).<String>g(\"x\")");
        // no arguments, no overload to tell from
        assertThat(render(x, v -> call(v, UnsafeFacts.method(base, "name", String_.TOKEN, MemberTraits.DEFAULT))))
                .contains("return v0.name();");
    }
}
