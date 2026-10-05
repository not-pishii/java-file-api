package gen.facts.p;

import gen.facts.p.Impl2_.Canonical;
import gen.facts.p.Impl2_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
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
import p.Impl2;

/// The full metamodel of [Impl2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Impl2] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PubBase_].
///
/// `p.HIface`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// These members have no fact:
///
/// - `field K of p.HIface`, which is ambiguous in p.Impl2 with field K of p.PubBase
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Impl2.class, fingerprint = "4a5c518d16d1e690c242ae693d8493ac47d42b614565571532431e94015e851e", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Impl2_ {
    /// The shape of [Impl2] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Impl2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Impl2_"), "4a5c518d16d1e690c242ae693d8493ac47d42b614565571532431e94015e851e", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Impl2"), List.of(), List.of(ClassDesc.of("p.PubBase"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Impl2"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Impl2], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Impl2].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Impl2 open-class sealed=no
        tparams -
        superclasses p.PubBase; java.lang.Object
        interfaces p.HIface
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Impl2()
        """;

        private Canonical() {
        }
    }

    /// The token of [Impl2].
    public static final OpenClassToken<Impl2> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Impl2#Impl2()].
    public static final CtorRef0<Impl2> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Impl2_() {
    }
}
