package gen.facts.p;

import gen.facts.p.Gt_.Canonical;
import gen.facts.p.Gt_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Gt;

/// The full metamodel of [Gt], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Gt] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Gt]
/// @param <E> a type argument of [Gt]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Gt.class, fingerprint = "e49346a0a5d7ee95d27d87bcafd5b1f6a8920166e94859505d9db13c2f2f410b", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Gt_<T, E> {
    /// The shape of [Gt] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Gt] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Gt_"), "e49346a0a5d7ee95d27d87bcafd5b1f6a8920166e94859505d9db13c2f2f410b", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Gt"), List.of(new TypeParam("T", List.of()), new TypeParam("E", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T"), Types.typeVar("E")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("E", Param.fixed(ClassDesc.of("java.lang.Object")), Param.var(0)), Signature.of("T", Param.var(1)), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("Gt", Param.var(0), Param.var(1)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Gt], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Gt].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Gt open-class sealed=no\ntparams #0; #1\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(#0, #1) throws -\nmember field instance mutable #0 value\nmember field instance mutable #1 U\nmember field static constant int T = 1\nmember field static mutable int E\nmember method overridable <^0> E(^0, #0) -> ^0 throws -\nmember method overridable T(#1) -> #0 throws -\nmember method static <^0, ^1> of(^0, ^1) -> p.Gt<^0, ^1> throws -\ntable abstract -\ntable concrete E(java.lang.Object, #0); T(#1); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static of(java.lang.Object, java.lang.Object)\ntable ctor Gt(#0, #1)\n";

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
