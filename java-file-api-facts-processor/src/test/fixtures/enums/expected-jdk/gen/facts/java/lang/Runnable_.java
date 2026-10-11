package gen.facts.java.lang;

import gen.facts.java.lang.Runnable_.Canonical;
import gen.facts.java.lang.Runnable_.Data;
import gen.facts.java.lang.Runnable_Heritage.Implement;
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

/// The full metamodel of [Runnable]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Runnable]: it is here as a supertype of [p.Tok], whose inherited members are called through this metamodel.
///
/// A member [Runnable] inherits has its fact in the metamodel of the supertype that declares it: [Object_].
///
/// A generated class extends or implements [Runnable] by the proof its metamodel starts, with the stages of `Runnable_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Runnable.class, fingerprint = "f019670a715b7f5753bf0332943d877afa4e49dab5640f14e8b099c8b8c895c4", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Runnable_ {
    /// The shape of [Runnable] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Runnable] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Runnable_"), "f019670a715b7f5753bf0332943d877afa4e49dab5640f14e8b099c8b8c895c4", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.Runnable"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Runnable_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Runnable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Runnable].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.lang.Runnable interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract run() -> void throws -
        sam run() -> void throws -
        inherit method public abstract java.lang.Runnable run() -> void throws - erased () overrides -
        table abstract run()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Runnable].
    public static final InterfaceToken<Runnable> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Runnable#run()].
    public static final VoidMethodRef0<Runnable> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Runnable#run()], which a lambda implements.
    public static final VoidSam0<Runnable> sam = UnsafeFacts.voidSam(run);

    private Runnable_() {
    }

    /// Starts the proof that a class being declared implements [Runnable]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Runnable_Heritage().implement(TOKEN, self);
    }
}
