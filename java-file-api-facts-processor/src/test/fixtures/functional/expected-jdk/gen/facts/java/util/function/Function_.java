package gen.facts.java.util.function;

import gen.facts.java.util.function.Function_.Canonical;
import gen.facts.java.util.function.Function_.Data;
import gen.facts.java.util.function.Function_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Function]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Function]: it is here as a supertype of [p.StrFn] and [p.StrOp], whose inherited members are called through this metamodel.
///
/// A member [Function] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Function]
/// @param <R> a type argument of [Function]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Function.class, fingerprint = "8388e7ddbbe8b9b468b5c8c2f0ef57f0f9d2079c957f52d5c39fd6b3a815164c", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Function_<T, R> {
    /// The shape of [Function] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Function] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.function.Function_"), "8388e7ddbbe8b9b468b5c8c2f0ef57f0f9d2079c957f52d5c39fd6b3a815164c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.function.Function"), List.of(new TypeParam("T", List.of()), new TypeParam("R", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("T"), Types.typeVar("R")), List.of()), new MethodTableTemplate(Set.of(Signature.of("apply", Param.var(0))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("identity")), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Function] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Function] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("R")), Types.extendsBound(Types.typeVar("V"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("T")), Types.exact(Types.typeVar("V"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("java.util.function.Function"), Signature.of("apply", Param.var(0)), List.of(), List.of(Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("R")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("V")), Types.extendsBound(Types.typeVar("T"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("V")), Types.exact(Types.typeVar("R"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("java.util.function.Function"), Signature.of("identity"), List.of(new TypeParam("T", List.of())), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("T")), Types.exact(Types.typeVar("T"))))), List.of(), Set.of(List.of()), List.of());
            }
        }
    }

    /// The canonical form of [Function], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Function].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.util.function.Function interface sealed=no
        tparams #0; #1
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract apply(#0) -> #1 throws -
        member method public overridable <^0> andThen(java.util.function.Function<? super #1, ? extends ^0>) -> java.util.function.Function<#0, ^0> throws -
        member method public overridable <^0> compose(java.util.function.Function<? super ^0, ? extends #0>) -> java.util.function.Function<^0, #1> throws -
        member method public static <^0> identity() -> java.util.function.Function<^0, ^0> throws -
        sam apply(#0) -> #1 throws -
        inherit method public abstract java.util.function.Function apply(#0) -> #1 throws - erased (java.lang.Object) overrides -
        inherit method public default java.util.function.Function <^0> andThen(java.util.function.Function<? super #1, ? extends ^0>) -> java.util.function.Function<#0, ^0> throws - erased (java.util.function.Function) overrides -
        inherit method public default java.util.function.Function <^0> compose(java.util.function.Function<? super ^0, ? extends #0>) -> java.util.function.Function<^0, #1> throws - erased (java.util.function.Function) overrides -
        inherit method public static java.util.function.Function <^0> identity() -> java.util.function.Function<^0, ^0> throws - erased () overrides -
        table abstract apply(#0)
        table concrete andThen(java.util.function.Function); compose(java.util.function.Function); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static identity()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Function] with a wildcard for every type argument.
    public static final InterfaceToken<Function<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Function] with the type arguments of this metamodel.
    public final InterfaceToken<Function<T, R>> token;

    /// The fact of [Function#apply(Object)].
    public final MethodRef1<Function<T, R>, R, T> apply_T;

    /// The fact of the single abstract method [Function#apply(Object)], which a lambda implements.
    public final Sam1<Function<T, R>, R, T> sam;

    private final RefToken<T> t;

    private final RefToken<R> r;

    /// The metamodel of [Function] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    /// @param r the token of the type argument `R`
    public Function_(RefToken<T> t, RefToken<R> r) {
        this.t = t;
        this.r = r;
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(r));
        this.apply_T = UnsafeFacts.method(token, "apply", r, UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(apply_T);
    }

    /// The fact of [Function#andThen(Function)], for the type arguments the tokens give.
    ///
    /// @param <V> a type argument of the method
    /// @param v the token of the type argument `V`
    /// @return the fact
    public <V> MethodRef1<Function<T, R>, Function<T, V>, Function<? super R, ? extends V>> andThen_Function(RefToken<V> v) {
        return UnsafeFacts.method(token, "andThen", UnsafeFacts.<Function<T, V>>interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(v)), UnsafeFacts.<Function<? super R, ? extends V>>interfaceToken(Data.SHAPE, TokenArg.superBound(r), TokenArg.extendsBound(v)), MemberTraits.OVERRIDABLE.withTypeArgs(v));
    }

    /// The fact of [Function#compose(Function)], for the type arguments the tokens give.
    ///
    /// @param <V> a type argument of the method
    /// @param v the token of the type argument `V`
    /// @return the fact
    public <V> MethodRef1<Function<T, R>, Function<V, R>, Function<? super V, ? extends T>> compose_Function(RefToken<V> v) {
        return UnsafeFacts.method(token, "compose", UnsafeFacts.<Function<V, R>>interfaceToken(Data.SHAPE, TokenArg.exact(v), TokenArg.exact(r)), UnsafeFacts.<Function<? super V, ? extends T>>interfaceToken(Data.SHAPE, TokenArg.superBound(v), TokenArg.extendsBound(t)), MemberTraits.OVERRIDABLE.withTypeArgs(v));
    }

    /// The fact of [Function#identity()], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> StaticMethodRef0<Function<T, T>> identity(RefToken<T> t) {
        return UnsafeFacts.staticMethod(ANY, "identity", UnsafeFacts.<Function<T, T>>interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(t)), MemberTraits.FINAL.withTypeArgs(t));
    }
}
