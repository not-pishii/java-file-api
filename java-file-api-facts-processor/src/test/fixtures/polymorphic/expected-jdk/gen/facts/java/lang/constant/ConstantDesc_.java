package gen.facts.java.lang.constant;

import gen.facts.java.lang.constant.ConstantDesc_.Canonical;
import gen.facts.java.lang.constant.ConstantDesc_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;

/// The token-only metamodel of [ConstantDesc]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [ConstantDesc]: it is only mentioned in the signatures of [java.lang.constant.Constable]. For the facts of its members add `ConstantDesc.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ConstantDesc.class, fingerprint = "4e70a032c739772906ded6464333899722a6319092b8e1da2fed616f6c398f7b", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class ConstantDesc_ {
    /// The shape of [ConstantDesc] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [ConstantDesc] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.constant.ConstantDesc_"), "4e70a032c739772906ded6464333899722a6319092b8e1da2fed616f6c398f7b", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.constant.ConstantDesc"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), true);

        private Data() {
        }
    }

    /// The canonical form of [ConstantDesc], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [ConstantDesc].
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.constant.ConstantDesc interface sealed=yes\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers none\ntable abstract resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [ConstantDesc].
    public static final InterfaceToken<ConstantDesc> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private ConstantDesc_() {
    }
}
