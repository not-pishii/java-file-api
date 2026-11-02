package gen.facts.p;

import gen.facts.p.SealedSub_.Canonical;
import gen.facts.p.SealedSub_.Data;
import gen.facts.p.SealedSub_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.SealedSub;

/// The full metamodel of [SealedSub], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [SealedSub] inherits has its fact in the metamodel of the supertype that declares it: [Run_].
///
/// A generated class extends or implements [SealedSub] by the proof its metamodel starts, with the stages of `SealedSub_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = SealedSub.class, fingerprint = "513a4501076988ca40c81ef3855614a52c94bf4aa96b0e87bd1c034296ec0d24", complete = true, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class SealedSub_ {
    /// The shape of [SealedSub] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [SealedSub] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.SealedSub_"), "513a4501076988ca40c81ef3855614a52c94bf4aa96b0e87bd1c034296ec0d24", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.SealedSub"), List.of(), List.of(), List.of(ClassDesc.of("p.Run")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), true, List.of(ClassDesc.of("p.SealedSubImpl")), List.of(), new SealedSub_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [SealedSub], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [SealedSub].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.SealedSub interface sealed=yes nesting=top permits=p.SealedSubImpl
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces p.Run
        supertypes -
        enum -
        record -
        members declared-accessible
        inherit method run() erased () overrides - by interface abstract p.Run () -> void throws -
        table abstract run()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [SealedSub].
    public static final InterfaceToken<SealedSub> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private SealedSub_() {
    }

    /// Starts the proof that a class being declared implements [SealedSub]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new SealedSub_Heritage().implement(TOKEN, self);
    }
}
