package gen.facts.p;

import gen.facts.p.PubConst_.Canonical;
import gen.facts.p.PubConst_.Data;
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
import p.PubConst;

/// The full metamodel of [PubConst]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [PubConst]: it is here as a supertype of [p.Impl], whose inherited members are called through this metamodel.
///
/// A member [PubConst] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [PubConst] by the proof its metamodel starts, with the stages of `PubConst_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubConst.class, fingerprint = "0a0a9f68b4476e03fb88280b4ca74050377140c2965a6bd3faa3b156e8b06e29", complete = true, format = 16)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubConst_ {
    /// The shape of [PubConst] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubConst] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubConst_"), "0a0a9f68b4476e03fb88280b4ca74050377140c2965a6bd3faa3b156e8b06e29", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PubConst"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of()), false, new PubConst_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubConst], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubConst].
        static final String TEXT = """
        javafile-facts-canonical 10
        type p.PubConst interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member field public static constant java.lang.String K = "public"
        member field public static constant java.lang.String name = "public"
        inherit field public p.PubConst K
        inherit field public p.PubConst name
        table abstract -
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [PubConst].
    public static final InterfaceToken<PubConst> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [PubConst#K].
    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "public");

    /// The fact of [PubConst#name].
    public static final StaticFieldRef<String> name = UnsafeFacts.constantField(TOKEN, "name", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "public");

    private PubConst_() {
    }

    /// Starts the proof that a class being declared implements [PubConst]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, PubConst> implement(RefToken<Self> self) {
        return new PubConst_Heritage().implement(TOKEN, self);
    }
}
