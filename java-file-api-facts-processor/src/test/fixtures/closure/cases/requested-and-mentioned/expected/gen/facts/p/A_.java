package gen.facts.p;

import gen.facts.p.A_.Canonical;
import gen.facts.p.A_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.A;
import p.Arg;
import p.B;
import p.Bound;
import p.CtorEx;
import p.Elem;
import p.Ex;
import p.Field1;
import p.Holder;
import p.Lower;
import p.MBound;
import p.Param1;
import p.Prot;
import p.Result;

/// The full metamodel of [A], which `@Facts` asks for: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// A member [A] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method dollar()`, which mentions p.Dol$lar, which has no metamodel: a class with $ in its simple name is not supported yet
/// - `method marker()`, which mentions p.Marker, which has no metamodel: annotation interface p.Marker is not supported yet
/// - `method hidden()`, which mentions types that are not public: p.Hidden
///
/// @param <T> a type argument of [A]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = A.class, fingerprint = "b6165e2630bc0a974e166b0463a3dcac1f2ff9c390699a58546fab342ac45258", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class A_<T extends Bound> {
    /// The shape of [A] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [A] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.A_"), "b6165e2630bc0a974e166b0463a3dcac1f2ff9c390699a58546fab342ac45258", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.A"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Bound"))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("arr"), Signature.of("b"), Signature.of("clone"), Signature.of("dollar"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("g", Param.fixed(ClassDesc.of("java.util.List"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("m", Param.fixed(ClassDesc.of("p.Arg"))), Signature.of("marker"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("pkg"), Signature.of("prot"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("A", Param.fixed(ClassDesc.of("p.Param1"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [A], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [A].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.A open-class sealed=no
        tparams #0 extends p.Bound
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (p.Param1) throws p.CtorEx
        member field public static final p.Field1 F
        member method protected overridable prot() -> p.Prot throws -
        member method public overridable <^0 extends p.MBound> g(java.util.List<? super p.Lower>) -> p.Holder<p.Elem> throws -
        member method public overridable arr() -> p.Arg[] throws -
        member method public overridable b() -> p.B throws -
        member method public overridable dollar() -> p.Dol$lar throws -
        member method public overridable m(p.Arg) -> p.Result throws p.Ex
        member method public overridable marker() -> p.Marker throws -
        table abstract -
        table concrete arr(); b(); clone(); dollar(); equals(java.lang.Object); finalize(); g(java.util.List); getClass(); hashCode(); hidden(); m(p.Arg); marker(); notify(); notifyAll(); pkg(); prot(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor A(p.Param1)
        """;

        private Canonical() {
        }
    }

    /// The token of [A] with a wildcard for every type argument.
    public static final OpenClassToken<A<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The fact of [A#F].
    public static final StaticFieldRef<Field1> F = UnsafeFacts.staticField(ANY, "F", UnsafeFacts.<Field1>openClassToken(Field1_.Data.SHAPE));

    /// The token of [A] with the type arguments of this metamodel.
    public final OpenClassToken<A<T>> token;

    /// The fact of [A#A(Param1)].
    public final CtorRef1<A<T>, Param1> new_Param1;

    /// The fact of [A#arr()].
    public final MethodRef0<A<T>, Arg[]> arr;

    /// The fact of [A#b()].
    public final MethodRef0<A<T>, B> b;

    /// The fact of [A#m(Arg)].
    public final MethodRef1<A<T>, Result, Arg> m_Arg;

    /// The fact of [A#prot()], which is `protected`: a subclass alone uses it.
    public final Protected<A<T>, MethodRef0<A<T>, Prot>> prot;

    private final RefToken<T> t;

    /// The metamodel of [A] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public A_(RefToken<T> t) {
        this.t = t;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_Param1 = UnsafeFacts.ctor(token, UnsafeFacts.<Param1>openClassToken(Param1_.Data.SHAPE), MemberTraits.FINAL.throwing(UnsafeFacts.<CtorEx>openClassToken(CtorEx_.Data.SHAPE)));
        this.arr = UnsafeFacts.method(token, "arr", ArrayToken.of(UnsafeFacts.<Arg>openClassToken(Arg_.Data.SHAPE)), MemberTraits.OVERRIDABLE);
        this.b = UnsafeFacts.method(token, "b", UnsafeFacts.<B>openClassToken(B_.Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.m_Arg = UnsafeFacts.method(token, "m", UnsafeFacts.<Result>openClassToken(Result_.Data.SHAPE), UnsafeFacts.<Arg>openClassToken(Arg_.Data.SHAPE), MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<Ex>openClassToken(Ex_.Data.SHAPE)));
        this.prot = UnsafeFacts.protected_(token, UnsafeFacts.method(token, "prot", UnsafeFacts.<Prot>openClassToken(Prot_.Data.SHAPE), MemberTraits.OVERRIDABLE.with(Access.PROTECTED)));
    }

    /// The fact of [A#g(List)], for the type arguments the tokens give.
    ///
    /// @param <U> a type argument of the method
    /// @param u the token of the type argument `U`
    /// @return the fact
    public <U extends MBound> MethodRef1<A<T>, Holder<Elem>, List<? super Lower>> g_List(RefToken<U> u) {
        return UnsafeFacts.method(token, "g", UnsafeFacts.<Holder<Elem>>openClassToken(Holder_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<Elem>openClassToken(Elem_.Data.SHAPE))), UnsafeFacts.<List<? super Lower>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.superBound(UnsafeFacts.<Lower>openClassToken(Lower_.Data.SHAPE))), MemberTraits.OVERRIDABLE.withTypeArgs(u));
    }
}
