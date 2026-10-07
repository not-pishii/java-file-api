package gen.facts.java.lang.constant;

import gen.facts.java.lang.constant.ConstantDesc_.Canonical;
import gen.facts.java.lang.constant.ConstantDesc_.Data;
import gen.facts.java.lang.constant.ConstantDesc_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDesc;
import java.lang.constant.ConstantDescs;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [ConstantDesc]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [ConstantDesc]: it is here as a supertype of [Integer], whose inherited members are called through this metamodel.
///
/// A member [ConstantDesc] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ConstantDesc.class, fingerprint = "6fc566d6271f2154d4f7d6729257a53a165d715f91cdd6e659a2fd92ba776a96", complete = true, format = 9)
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
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.constant.ConstantDesc_"), "6fc566d6271f2154d4f7d6729257a53a165d715f91cdd6e659a2fd92ba776a96", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.constant.ConstantDesc"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), true, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [ConstantDesc] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [ConstantDesc] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("java.lang.constant.ConstantDesc"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.ReflectiveOperationException"))), Set.of(List.of(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), List.of());
            }
        }
    }

    /// The canonical form of [ConstantDesc], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [ConstantDesc].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.lang.constant.ConstantDesc interface sealed=yes
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup) -> java.lang.Object throws java.lang.ReflectiveOperationException
        inherit method public abstract java.lang.constant.ConstantDesc resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup) -> java.lang.Object throws java.lang.ReflectiveOperationException erased (java.lang.invoke.MethodHandles$Lookup) overrides -
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
    public static final MethodRef1<ConstantDesc, Object, Lookup> resolveConstantDesc_MethodHandles_Lookup = UnsafeFacts.method(TOKEN, "resolveConstantDesc", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), UnsafeFacts.<Lookup>finalClassToken(gen.facts.java.lang.invoke.MethodHandles_Lookup_.Data.SHAPE), MemberTraits.ABSTRACT.throwing(UnsafeFacts.<ReflectiveOperationException>openClassToken(gen.facts.java.lang.ReflectiveOperationException_.Data.SHAPE)));

    private ConstantDesc_() {
    }
}
