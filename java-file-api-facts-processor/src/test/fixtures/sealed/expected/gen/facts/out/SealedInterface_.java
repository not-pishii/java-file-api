package gen.facts.out;

import gen.facts.out.SealedInterface_.Canonical;
import gen.facts.out.SealedInterface_.Data;
import gen.facts.out.SealedInterface_Heritage.Implement;
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
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import out.SealedInterface;

/// The full metamodel of [SealedInterface], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [SealedInterface] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [SealedInterface] by the proof its metamodel starts, with the stages of `SealedInterface_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = SealedInterface.class, fingerprint = "5b55085a7d1efa4715d09b601f6649e11c9f877b8f6a662f5ef5a8c388adeb17", complete = true, format = 19)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class SealedInterface_ {
    /// The shape of [SealedInterface] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [SealedInterface] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.out.SealedInterface_"), "5b55085a7d1efa4715d09b601f6649e11c9f877b8f6a662f5ef5a8c388adeb17", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("out.SealedInterface"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("sides")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), true, List.of(ClassDesc.of("out.Out")), List.of(), new SealedInterface_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [SealedInterface], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [SealedInterface].
        static final String TEXT = """
        javafile-facts-canonical 12
        type out.SealedInterface interface sealed=yes nesting=top permits=out.Out
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract sides() -> int throws -
        inherit method sides() erased () overrides - by interface abstract out.SealedInterface () -> int throws -
        table abstract sides()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [SealedInterface].
    public static final InterfaceToken<SealedInterface> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [SealedInterface#sides()].
    public static final MethodRef0<SealedInterface, Int> sides = UnsafeInvocables.method(TOKEN, "sides", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    private SealedInterface_() {
    }

    /// Starts the proof that a class being declared implements [SealedInterface]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new SealedInterface_Heritage().implement(TOKEN, self);
    }
}
