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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [ConstantDesc]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [ConstantDesc]: it is only mentioned in the signatures of [java.lang.constant.Constable]. For the facts of its members add `ConstantDesc.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ConstantDesc.class, fingerprint = "7ff6b10ac57dd7a91b3b7be3ef600b58e1ab270c291fe45190e21935e4ea0914", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class ConstantDesc_ {
    /// The shape of [ConstantDesc] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [ConstantDesc] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.constant.ConstantDesc_"), "7ff6b10ac57dd7a91b3b7be3ef600b58e1ab270c291fe45190e21935e4ea0914", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.constant.ConstantDesc"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), true);

        private Data() {
        }
    }

    /// The canonical form of [ConstantDesc], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [ConstantDesc].
        static final String TEXT = """
        javafile-facts-canonical 5
        type java.lang.constant.ConstantDesc interface sealed=yes
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members none
        table abstract resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [ConstantDesc].
    public static final InterfaceToken<ConstantDesc> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private ConstantDesc_() {
    }
}
