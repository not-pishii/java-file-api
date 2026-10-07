package gen.facts.java.io;

import gen.facts.java.io.Serializable_.Canonical;
import gen.facts.java.io.Serializable_.Data;
import java.io.Serializable;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Serializable]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Serializable]: it is here as a supertype of [p.Color] and [p.Num], whose inherited members are called through this metamodel.
///
/// A member [Serializable] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Serializable] by the proof its metamodel starts, with the stages of `Serializable_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Serializable.class, fingerprint = "cacee7631b9d7b754f03f0c889191cea41033d7c4a1b0ad64ee6d560efa1f434", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Serializable_ {
    /// The shape of [Serializable] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Serializable] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.io.Serializable_"), "cacee7631b9d7b754f03f0c889191cea41033d7c4a1b0ad64ee6d560efa1f434", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.io.Serializable"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Serializable_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Serializable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Serializable].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.io.Serializable interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        table abstract -
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Serializable].
    public static final InterfaceToken<Serializable> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Serializable_() {
    }

    /// Starts the proof that a class being declared implements [Serializable]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Serializable> implement(RefToken<Self> self) {
        return new Serializable_Heritage().implement(TOKEN, self);
    }
}
