package gen.facts.java.lang.constant;

import gen.facts.java.lang.constant.ConstantDesc_.Canonical;
import gen.facts.java.lang.constant.ConstantDesc_.Data;
import gen.facts.java.lang.constant.ConstantDesc_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDesc;
import java.lang.constant.ConstantDescs;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [ConstantDesc]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [ConstantDesc]: it is here as a supertype of [Integer], whose inherited members are called through this metamodel.
///
/// A member [ConstantDesc] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [ConstantDesc] by the proof its metamodel starts, with the stages of `ConstantDesc_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ConstantDesc.class, fingerprint = "01bef9f951f571d8fc4d0d83d25fedfb49a4af8ed715a1d24e1c5fb22975da05", complete = true, format = 18)
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
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.constant.ConstantDesc_"), "01bef9f951f571d8fc4d0d83d25fedfb49a4af8ed715a1d24e1c5fb22975da05", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.constant.ConstantDesc"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of()), true, List.of(ClassDesc.of("java.lang.constant.ClassDesc"), ClassDesc.of("java.lang.constant.MethodHandleDesc"), ClassDesc.of("java.lang.constant.MethodTypeDesc"), ClassDesc.of("java.lang.Double"), ClassDesc.of("java.lang.constant.DynamicConstantDesc"), ClassDesc.of("java.lang.Float"), ClassDesc.of("java.lang.Integer"), ClassDesc.of("java.lang.Long"), ClassDesc.of("java.lang.String")), List.of(), new ConstantDesc_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [ConstantDesc], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [ConstantDesc].
        static final String TEXT = """
        javafile-facts-canonical 12
        type java.lang.constant.ConstantDesc interface sealed=yes nesting=top permits=java.lang.Double; java.lang.Float; java.lang.Integer; java.lang.Long; java.lang.String; java.lang.constant.ClassDesc; java.lang.constant.DynamicConstantDesc; java.lang.constant.MethodHandleDesc; java.lang.constant.MethodTypeDesc
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup) -> java.lang.Object throws java.lang.ReflectiveOperationException
        inherit method resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup) erased (java.lang.invoke.MethodHandles$Lookup) overrides - by interface abstract java.lang.constant.ConstantDesc (java.lang.invoke.MethodHandles$Lookup) -> java.lang.Object throws java.lang.ReflectiveOperationException
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

    /// The fact of [ConstantDesc#resolveConstantDesc(Lookup)].
    public static final MethodRef1<ConstantDesc, Object, Lookup> resolveConstantDesc_MethodHandles_Lookup = UnsafeInvocables.method(TOKEN, "resolveConstantDesc", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), UnsafeFacts.<Lookup>finalClassToken(gen.facts.java.lang.invoke.MethodHandles_Lookup_.Data.SHAPE), MemberTraits.ABSTRACT.throwing(UnsafeFacts.<ReflectiveOperationException>openClassToken(gen.facts.java.lang.ReflectiveOperationException_.Data.SHAPE)));

    private ConstantDesc_() {
    }

    /// Starts the proof that a class being declared implements [ConstantDesc]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new ConstantDesc_Heritage().implement(TOKEN, self);
    }
}
