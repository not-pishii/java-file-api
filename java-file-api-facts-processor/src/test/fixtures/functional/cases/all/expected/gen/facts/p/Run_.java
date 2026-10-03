package gen.facts.p;

import gen.facts.p.Run_.Canonical;
import gen.facts.p.Run_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Run;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Run.class, fingerprint = "f64c2ac274f553532e18140aa8f8e7b56e9a9353d109c05ee6bffd2e18fa26d5", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Run_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Run_"), "f64c2ac274f553532e18140aa8f8e7b56e9a9353d109c05ee6bffd2e18fa26d5", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Run"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Run interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract run() -> void throws -\nsam run() -> void throws -\ntable abstract run()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Run> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final VoidMethodRef0<Run> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    public static final VoidSam0<Run> sam = UnsafeFacts.voidSam(run);

    private Run_() {
    }
}
