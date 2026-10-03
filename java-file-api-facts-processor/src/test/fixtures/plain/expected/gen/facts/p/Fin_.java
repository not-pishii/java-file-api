package gen.facts.p;

import gen.facts.p.Fin_.Canonical;
import gen.facts.p.Fin_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Fin;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fin.class, fingerprint = "469faa35522ec7922e4192f4d53d0b6e291f76702dc7e348bdffa1d017d928ce", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Fin_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fin_"), "469faa35522ec7922e4192f4d53d0b6e291f76702dc7e348bdffa1d017d928ce", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Fin"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Fin"), Signature.of("Fin", Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Fin final-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember ctor(int) throws -\nmember method final x() -> int throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()\ntable static -\ntable ctor Fin(); Fin(int)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Fin> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    public static final CtorRef0<Fin> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final CtorRef1<Fin, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    public static final MethodRef0<Fin, Int> x = UnsafeFacts.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.FINAL);

    private Fin_() {
    }
}
