package gen.facts.p;

import gen.facts.p.GoneApi_.Canonical;
import gen.facts.p.GoneApi_.Data;
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
import p.GoneApi;

/// The full metamodel of [GoneApi]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [GoneApi]: it is here as a supertype of [p.Sub], whose inherited members are called through this metamodel.
///
/// A member [GoneApi] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method gone(p.Secret)`, which mentions types that are not public: p.Secret
///
/// A generated class extends or implements [GoneApi] by the proof its metamodel starts, with the stages of `GoneApi_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = GoneApi.class, fingerprint = "bf09ca5bb75eb5e7bf994a4f72df5a80e2b2dd30812c6e2eb5e8e058d3313acf", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class GoneApi_ {
    /// The shape of [GoneApi] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [GoneApi] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.GoneApi_"), "bf09ca5bb75eb5e7bf994a4f72df5a80e2b2dd30812c6e2eb5e8e058d3313acf", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.GoneApi"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("gone", Param.fixed(ClassDesc.of("p.Secret"))), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new GoneApi_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [GoneApi], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [GoneApi].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.GoneApi interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        inherit method public default p.GoneApi gone(p.Secret) -> void throws - erased (p.Secret) overrides -
        table abstract -
        table concrete equals(java.lang.Object); getClass(); gone(p.Secret); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [GoneApi].
    public static final InterfaceToken<GoneApi> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private GoneApi_() {
    }

    /// Starts the proof that a class being declared implements [GoneApi]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, GoneApi> implement(RefToken<Self> self) {
        return new GoneApi_Heritage().implement(TOKEN, self);
    }
}
