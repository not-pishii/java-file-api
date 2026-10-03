package gen.facts.java.lang.constant;

import gen.facts.java.lang.constant.Constable_.Canonical;
import gen.facts.java.lang.constant.Constable_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Constable.class, fingerprint = "59e0c82f334611c42bbbf4854a7a913af248482c0869cd5d79868e7231858396", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Constable_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.constant.Constable_"), "59e0c82f334611c42bbbf4854a7a913af248482c0869cd5d79868e7231858396", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.constant.Constable"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("describeConstable")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.lang.constant.Constable interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract describeConstable() -> java.util.Optional<? extends java.lang.constant.ConstantDesc> throws -\nsam describeConstable() -> java.util.Optional<? extends java.lang.constant.ConstantDesc> throws -\ntable abstract describeConstable()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Constable> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final MethodRef0<Constable, Optional<? extends ConstantDesc>> describeConstable = UnsafeFacts.method(TOKEN, "describeConstable", UnsafeFacts.<Optional<? extends ConstantDesc>>finalClassToken(gen.facts.java.util.Optional_.Data.SHAPE, TokenArg.extendsBound(UnsafeFacts.<ConstantDesc>interfaceToken(ConstantDesc_.Data.SHAPE))), MemberTraits.ABSTRACT);

    public static final Sam0<Constable, Optional<? extends ConstantDesc>> sam = UnsafeFacts.sam(describeConstable);

    private Constable_() {
    }
}
