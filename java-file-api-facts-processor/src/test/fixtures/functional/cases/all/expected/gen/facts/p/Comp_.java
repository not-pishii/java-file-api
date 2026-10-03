package gen.facts.p;

import gen.facts.p.Comp_.Canonical;
import gen.facts.p.Comp_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Comp;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Comp.class, fingerprint = "f38e7296802c838395c89050ba79b47230998aadfed568fee281afb1243a0564", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Comp_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Comp_"), "f38e7296802c838395c89050ba79b47230998aadfed568fee281afb1243a0564", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Comp"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Comp")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Comp interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract compareTo(p.Comp) -> int throws -\nmember method abstract hashCode() -> int throws -\nmember method abstract toString() -> java.lang.String throws -\nsam compareTo(p.Comp) -> int throws -\ntable abstract compareTo(p.Comp)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Comp> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final MethodRef1<Comp, Int, Comp> compareTo_Comp = UnsafeFacts.method(TOKEN, "compareTo", PrimitiveToken.INT, TOKEN, MemberTraits.ABSTRACT);

    public static final MethodRef0<Comp, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    public static final MethodRef0<Comp, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    public static final Sam1<Comp, Int, Comp> sam = UnsafeFacts.sam(compareTo_Comp);

    private Comp_() {
    }
}
