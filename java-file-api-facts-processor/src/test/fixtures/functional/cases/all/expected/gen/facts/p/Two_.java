package gen.facts.p;

import gen.facts.p.Two_.Canonical;
import gen.facts.p.Two_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Two;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Two.class, fingerprint = "d63332cdf727960d49735ddd8a27cff84d0a0e230ebe6bee11f8a77e0b421cd8", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Two_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Two_"), "d63332cdf727960d49735ddd8a27cff84d0a0e230ebe6bee11f8a77e0b421cd8", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Two"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("a"), Signature.of("b")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Two interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract a() -> void throws -\nmember method abstract b() -> void throws -\ntable abstract a(); b()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Two> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final VoidMethodRef0<Two> a = UnsafeFacts.voidMethod(TOKEN, "a", MemberTraits.ABSTRACT);

    public static final VoidMethodRef0<Two> b = UnsafeFacts.voidMethod(TOKEN, "b", MemberTraits.ABSTRACT);

    private Two_() {
    }
}
