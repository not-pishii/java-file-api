package gen.facts.p;

import gen.facts.p.PubB_.Canonical;
import gen.facts.p.PubB_.Data;
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
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubB;

/// The full metamodel of [PubB]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [PubB]: it is here as a supertype of [p.Impl], whose inherited members are called through this metamodel.
///
/// A member [PubB] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [PubB] by the proof its metamodel starts, with the stages of `PubB_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubB.class, fingerprint = "f16eeb5e23ce2df41a3249c257ec76485974d73fd74a74dd2fe62f82d02055af", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubB_ {
    /// The shape of [PubB] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubB] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubB_"), "f16eeb5e23ce2df41a3249c257ec76485974d73fd74a74dd2fe62f82d02055af", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PubB"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new PubB_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubB], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubB].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.PubB interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member field public static constant java.lang.String k = "PubB.k"
        member field public static constant java.lang.String s = "PubB.s"
        table abstract -
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [PubB].
    public static final InterfaceToken<PubB> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [PubB#k].
    public static final StaticFieldRef<String> k = UnsafeFacts.constantField(TOKEN, "k", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "PubB.k");

    /// The fact of [PubB#s].
    public static final StaticFieldRef<String> s = UnsafeFacts.constantField(TOKEN, "s", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "PubB.s");

    private PubB_() {
    }

    /// Starts the proof that a class being declared implements [PubB]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, PubB> implement(RefToken<Self> self) {
        return new PubB_Heritage().implement(TOKEN, self);
    }
}
