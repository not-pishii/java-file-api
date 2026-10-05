package gen.facts.p;

import gen.facts.p.Twins_.Canonical;
import gen.facts.p.Twins_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Twins;

/// The full metamodel of [Twins], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Twins] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.Tight`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Twins.class, fingerprint = "a24b38b7ee9a6e33e456e80cd423f066abffec73f1659c4f9e006ceb39a86ddc", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Twins_ {
    /// The shape of [Twins] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Twins] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Twins_"), "a24b38b7ee9a6e33e456e80cd423f066abffec73f1659c4f9e006ceb39a86ddc", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Twins"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("twin")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Twins"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Twins], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Twins].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Twins abstract-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces p.Loose; p.Tight
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member method abstract twin() -> java.lang.String throws -
        table abstract twin()
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Twins()
        """;

        private Canonical() {
        }
    }

    /// The token of [Twins].
    public static final AbstractClassToken<Twins> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Twins#Twins()].
    public static final AbstractCtorRef0<Twins> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Twins#twin()], declared in `p.Tight`, which is not `public`.
    public static final MethodRef0<Twins, String> twin = UnsafeFacts.method(TOKEN, "twin", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    private Twins_() {
    }
}
