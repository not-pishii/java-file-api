package gen.facts.p;

import gen.facts.p.Sealed_.Canonical;
import gen.facts.p.Sealed_.Data;
import gen.facts.p.Sealed_Heritage.Implement;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Sealed;

/// The full metamodel of [Sealed], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sealed] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Sealed] by the proof its metamodel starts, with the stages of `Sealed_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sealed.class, fingerprint = "f6c1a8e06a4f14bec72ce3a5d32f75f998641adbf20992d3949358ce6da29fb0", complete = true, format = 18)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Sealed_ {
    /// The shape of [Sealed] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sealed] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sealed_"), "f6c1a8e06a4f14bec72ce3a5d32f75f998641adbf20992d3949358ce6da29fb0", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Sealed"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of()), true, List.of(ClassDesc.of("p.SealedImpl")), List.of(), new Sealed_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Sealed], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sealed].
        static final String TEXT = """
        javafile-facts-canonical 12
        type p.Sealed interface sealed=yes nesting=top permits=p.SealedImpl
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract run() -> void throws -
        inherit method run() erased () overrides - by interface abstract p.Sealed () -> void throws -
        table abstract run()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Sealed].
    public static final InterfaceToken<Sealed> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Sealed#run()].
    public static final VoidMethodRef0<Sealed> run = UnsafeInvocables.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    private Sealed_() {
    }

    /// Starts the proof that a class being declared implements [Sealed]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Sealed_Heritage().implement(TOKEN, self);
    }
}
