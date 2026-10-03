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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ov.class, fingerprint = "0bc04b9aa137986317017ecc0e9b75205ccd125a3aad7f4d9ef8ca1949913c0c", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Ov_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ov_"), "0bc04b9aa137986317017ecc0e9b75205ccd125a3aad7f4d9ef8ca1949913c0c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ov"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("k", Param.fixed(ConstantDescs.CD_int)), Signature.of("k", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("m", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("m", Param.fixed(ClassDesc.of("java.util.List"))), Signature.of("n", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("size"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Ov", Param.fixed(ClassDesc.of("java.awt.List"))), Signature.of("Ov", Param.fixed(ClassDesc.of("java.util.List"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Ov open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(java.awt.List) throws -\nmember field instance mutable int size\nmember method overridable <^0> k(^0) -> void throws -\nmember method overridable <^0> size() -> ^0 throws -\nmember method overridable k(int) -> void throws -\nmember method overridable m(java.awt.List) -> void throws -\nmember method overridable n(java.awt.List) -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); k(int); k(java.lang.Object); m(java.awt.List); m(java.util.List); n(java.awt.List); notify(); notifyAll(); size(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Ov(java.awt.List); Ov(java.util.List)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Ov> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<Ov, Int> size = UnsafeFacts.mutableField(TOKEN, "size", PrimitiveToken.INT);

    public static final CtorRef1<Ov, java.awt.List> new_java_awt_List = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.FINAL);

    public static final VoidMethodRef1<Ov, Int> k_int = UnsafeFacts.voidMethod(TOKEN, "k", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<Ov, java.awt.List> m_java_awt_List = UnsafeFacts.voidMethod(TOKEN, "m", UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<Ov, java.awt.List> n_List = UnsafeFacts.voidMethod(TOKEN, "n", UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Ov_() {
    }

    public static <T> VoidMethodRef1<Ov, T> k_T(RefToken<T> t) {
        return UnsafeFacts.voidMethod(TOKEN, "k", UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    public static <T> MethodRef0<Ov, T> size_(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "size", t, MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }
}
