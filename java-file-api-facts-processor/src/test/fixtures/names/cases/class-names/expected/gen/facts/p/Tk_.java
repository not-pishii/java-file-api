package gen.facts.p;

import gen.facts.p.Tk_.Canonical;
import gen.facts.p.Tk_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Other;
import p.Tk;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Tk.class, fingerprint = "53f2d9dcde8911187550a99a90eb719d92c2c4a5bb0d0a383457f2d80106da61", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Tk_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Tk_"), "53f2d9dcde8911187550a99a90eb719d92c2c4a5bb0d0a383457f2d80106da61", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Tk"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Tk"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Tk open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int Float\nmember field instance mutable int MemberTraits\nmember field instance mutable int Other_\nmember field instance mutable int UnsafeFacts\nmember field instance mutable int fine\nmember method overridable other() -> p.Other throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); other(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Tk()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Tk> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<Tk, Int> Float = UnsafeFacts.mutableField(TOKEN, "Float", PrimitiveToken.INT);

    public static final MutableFieldRef<Tk, Int> MemberTraits_ = UnsafeFacts.mutableField(TOKEN, "MemberTraits", PrimitiveToken.INT);

    public static final MutableFieldRef<Tk, Int> Other__ = UnsafeFacts.mutableField(TOKEN, "Other_", PrimitiveToken.INT);

    public static final MutableFieldRef<Tk, Int> UnsafeFacts_ = UnsafeFacts.mutableField(TOKEN, "UnsafeFacts", PrimitiveToken.INT);

    public static final MutableFieldRef<Tk, Int> fine = UnsafeFacts.mutableField(TOKEN, "fine", PrimitiveToken.INT);

    public static final CtorRef0<Tk> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Tk, Other> other = UnsafeFacts.method(TOKEN, "other", UnsafeFacts.<Other>openClassToken(Other_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Tk_() {
    }
}
