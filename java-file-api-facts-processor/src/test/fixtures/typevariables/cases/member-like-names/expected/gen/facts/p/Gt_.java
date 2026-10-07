package gen.facts.p;

import gen.facts.p.Gt_.Canonical;
import gen.facts.p.Gt_.Data;
import gen.facts.p.Gt_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Gt;

/// The full metamodel of [Gt], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Gt] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Gt]
/// @param <E> a type argument of [Gt]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Gt.class, fingerprint = "7a38658709f350768bf4434f452a1f8f2bdaf2f03b5ccc9bfee6586873f8e25d", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Gt_<T, E> {
    /// The shape of [Gt] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Gt] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Gt_"), "7a38658709f350768bf4434f452a1f8f2bdaf2f03b5ccc9bfee6586873f8e25d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Gt"), List.of(new TypeParam("T", List.of()), new TypeParam("E", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T"), Types.typeVar("E")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("E", Param.fixed(ClassDesc.of("java.lang.Object")), Param.var(0)), Signature.of("T", Param.var(1)), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("Gt", Param.var(0), Param.var(1)))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Gt] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Gt] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13()), List.of(c0()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Gt"), Signature.of("E", Param.fixed(ClassDesc.of("java.lang.Object")), Param.var(0)), List.of(new TypeParam("U", List.of())), List.of(Types.typeVar("U"), Types.typeVar("T")), Arity.FIXED, new Of(Types.typeVar("U")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Gt"), Signature.of("T", Param.var(1)), List.of(), List.of(Types.typeVar("E")), Arity.FIXED, new Of(Types.typeVar("T")), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Gt"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("T", List.of()), new TypeParam("E", List.of())), List.of(Types.typeVar("T"), Types.typeVar("E")), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("p.Gt"), List.of(Types.exact(Types.typeVar("T")), Types.exact(Types.typeVar("E"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"), ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Gt", Param.var(0), Param.var(1)), List.of(), List.of(Types.typeVar("T"), Types.typeVar("E")), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Gt], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Gt].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Gt open-class sealed=no
        tparams #0; #1
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (#0, #1) throws -
        member field public instance mutable #0 value
        member field public instance mutable #1 U
        member field public static constant int T = 1
        member field public static mutable int E
        member method public overridable <^0> E(^0, #0) -> ^0 throws -
        member method public overridable T(#1) -> #0 throws -
        member method public static <^0, ^1> of(^0, ^1) -> p.Gt<^0, ^1> throws -
        inherit ctor public (#0, #1) throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Gt <^0> E(^0, #0) -> ^0 throws - erased (java.lang.Object, java.lang.Object) overrides -
        inherit method public concrete p.Gt T(#1) -> #0 throws - erased (java.lang.Object) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Gt <^0, ^1> of(^0, ^1) -> p.Gt<^0, ^1> throws - erased (java.lang.Object, java.lang.Object) overrides -
        table abstract -
        table concrete E(java.lang.Object, #0); T(#1); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static of(java.lang.Object, java.lang.Object)
        table ctor Gt(#0, #1)
        """;

        private Canonical() {
        }
    }

    /// The token of [Gt] with a wildcard for every type argument.
    public static final OpenClassToken<Gt<?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The fact of [Gt#E].
    public static final MutableStaticFieldRef<Int> E = UnsafeFacts.mutableStaticField(ANY, "E", PrimitiveToken.INT);

    /// The fact of [Gt#T].
    public static final StaticFieldRef<Int> T = UnsafeFacts.constantField(ANY, "T", PrimitiveToken.INT, 1);

    /// The token of [Gt] with the type arguments of this metamodel.
    public final OpenClassToken<Gt<T, E>> token;

    /// The fact of [Gt#U].
    public final MutableFieldRef<Gt<T, E>, E> U;

    /// The fact of [Gt#value].
    public final MutableFieldRef<Gt<T, E>, T> value;

    /// The fact of [Gt#Gt(Object, Object)].
    public final CtorRef2<Gt<T, E>, T, E> new_T_E;

    /// The fact of [Gt#T(Object)].
    public final MethodRef1<Gt<T, E>, T, E> T_E;

    private final RefToken<T> t;

    private final RefToken<E> e;

    /// The metamodel of [Gt] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    /// @param e the token of the type argument `E`
    public Gt_(RefToken<T> t, RefToken<E> e) {
        this.t = t;
        this.e = e;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(e));
        this.U = UnsafeFacts.mutableField(token, "U", e);
        this.value = UnsafeFacts.mutableField(token, "value", t);
        this.new_T_E = UnsafeFacts.ctor(token, UnsafeFacts.param(t, Param.var(0)), UnsafeFacts.param(e, Param.var(1)), MemberTraits.FINAL);
        this.T_E = UnsafeFacts.method(token, "T", t, UnsafeFacts.param(e, Param.var(1)), MemberTraits.OVERRIDABLE);
    }

    /// The fact of [Gt#E(Object, Object)], for the type arguments the tokens give.
    ///
    /// @param <U> a type argument of the method
    /// @param u the token of the type argument `U`
    /// @return the fact
    public <U> MethodRef2<Gt<T, E>, U, U, T> E_U_T(RefToken<U> u) {
        return UnsafeFacts.method(token, "E", u, UnsafeFacts.param(u, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE.withTypeArgs(u));
    }

    /// The fact of [Gt#of(Object, Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param <E> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @param e the token of the type argument `E`
    /// @return the fact
    public static <T, E> StaticMethodRef2<Gt<T, E>, T, E> of_T_E(RefToken<T> t, RefToken<E> e) {
        return UnsafeFacts.staticMethod(ANY, "of", UnsafeFacts.<Gt<T, E>>openClassToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(e)), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), UnsafeFacts.param(e, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(t, e));
    }
}
