package gen.facts.p;

import gen.facts.p.NoneB_.Canonical;
import gen.facts.p.NoneB_.Data;
import gen.facts.p.NoneB_Heritage.Implement;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.NoneB;

/// The full metamodel of [NoneB]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [NoneB]: it is here as a supertype of [p.OneWithout], whose inherited members are called through this metamodel.
///
/// A member [NoneB] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [NoneB] by the proof its metamodel starts, with the stages of `NoneB_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = NoneB.class, fingerprint = "cdbcd12fa03b2977611121866636d50b235062a25a4e3aae814b9070f0a0500c", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class NoneB_ {
    /// The shape of [NoneB] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [NoneB] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.NoneB_"), "cdbcd12fa03b2977611121866636d50b235062a25a4e3aae814b9070f0a0500c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.NoneB"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new NoneB_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [NoneB], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [NoneB].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.NoneB interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract m() -> void throws -
        sam m() -> void throws -
        inherit method public abstract p.NoneB m() -> void throws - erased () overrides -
        table abstract m()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [NoneB].
    public static final InterfaceToken<NoneB> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [NoneB#m()].
    public static final VoidMethodRef0<NoneB> m = UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [NoneB#m()], which a lambda implements.
    public static final VoidSam0<NoneB> sam = UnsafeFacts.voidSam(m);

    private NoneB_() {
    }

    /// Starts the proof that a class being declared implements [NoneB]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new NoneB_Heritage().implement(TOKEN, self);
    }
}
