package gen.facts.p;

import gen.facts.p.Ov_.Canonical;
import gen.facts.p.Ov_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Ov;

/// The full metamodel of [Ov], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Ov] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method m(java.util.List<p.Hidden>)`, which mentions types that are not public: p.Hidden
/// - `constructor Ov(java.util.List<p.Hidden>)`, which mentions types that are not public: p.Hidden
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ov.class, fingerprint = "f606e2486a0715c0cd07712a0ce76e9e7e5cf7e0d37857af98d76e23b2970a27", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Ov_ {
    /// The shape of [Ov] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Ov] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ov_"), "f606e2486a0715c0cd07712a0ce76e9e7e5cf7e0d37857af98d76e23b2970a27", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ov"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("k", Param.fixed(ConstantDescs.CD_int)), Signature.of("k", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("m", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("m", Param.fixed(ClassDesc.of("java.util.List"))), Signature.of("n", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("size"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Ov", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("Ov", Param.fixed(ClassDesc.of("java.util.List"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Ov], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Ov].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Ov open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(java.awt.List) throws -\nmember field instance mutable int size\nmember method overridable <^0> k(^0) -> void throws -\nmember method overridable <^0> size() -> ^0 throws -\nmember method overridable k(int) -> void throws -\nmember method overridable m(java.awt.List) -> void throws -\nmember method overridable n(java.awt.List) -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); k(int); k(java.lang.Object); m(java.awt.List); m(java.util.List); n(java.awt.List); notify(); notifyAll(); size(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Ov(java.awt.List); Ov(java.util.List)\n";

        private Canonical() {
        }
    }

    /// The token of [Ov].
    public static final OpenClassToken<Ov> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Ov#size].
    public static final MutableFieldRef<Ov, Int> size = UnsafeFacts.mutableField(TOKEN, "size", PrimitiveToken.INT);

    /// The fact of [Ov#Ov(java.awt.List)].
    public static final CtorRef1<Ov, java.awt.List> new_java_awt_List = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Ov#k(int)].
    public static final VoidMethodRef1<Ov, Int> k_int = UnsafeFacts.voidMethod(TOKEN, "k", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#m(java.awt.List)].
    public static final VoidMethodRef1<Ov, java.awt.List> m_java_awt_List = UnsafeFacts.voidMethod(TOKEN, "m", UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#n(java.awt.List)].
    public static final VoidMethodRef1<Ov, java.awt.List> n_List = UnsafeFacts.voidMethod(TOKEN, "n", UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Ov_() {
    }

    /// The fact of [Ov#k(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> VoidMethodRef1<Ov, T> k_T(RefToken<T> t) {
        return UnsafeFacts.voidMethod(TOKEN, "k", UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Ov#size()], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef0<Ov, T> size_(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "size", t, MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }
}
