package gen.facts.p;

import gen.facts.p.PubNest_.Canonical;
import gen.facts.p.PubNest_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubNest;

/// The full metamodel of [PubNest], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubNest] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HNest`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// These members have no fact:
///
/// - `method make() of p.HNest`, which mentions types that are not public: p.HNest$In
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubNest.class, fingerprint = "bdee53746163c50e490bf630f265f727863ac8d41abc9a8d47ab0ff67bc3ca68", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubNest_ {
    /// The shape of [PubNest] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubNest] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubNest_"), "bdee53746163c50e490bf630f265f727863ac8d41abc9a8d47ab0ff67bc3ca68", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubNest"), List.of(), List.of(ClassDesc.of("p.HNest"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("make"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubNest"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [PubNest], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubNest].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.PubNest open-class sealed=no
        tparams -
        superclasses p.HNest; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member method overridable plain() -> java.lang.String throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); make(); notify(); notifyAll(); plain(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor PubNest()
        """;

        private Canonical() {
        }
    }

    /// The token of [PubNest].
    public static final OpenClassToken<PubNest> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubNest#PubNest()].
    public static final CtorRef0<PubNest> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [PubNest#plain()], declared in `p.HNest`, which is not `public`.
    public static final MethodRef0<PubNest, String> plain = UnsafeFacts.method(TOKEN, "plain", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private PubNest_() {
    }
}
