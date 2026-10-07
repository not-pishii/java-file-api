package gen.facts.p;

import gen.facts.p.Sup_.Canonical;
import gen.facts.p.Sup_.Data;
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
import p.Sup;

/// The full metamodel of [Sup]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Sup]: it is here as a supertype of [p.Sub], whose inherited members are called through this metamodel.
///
/// A member [Sup] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method lost(p.Secret)`, which mentions types that are not public: p.Secret
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sup.class, fingerprint = "82612012158d4991c8e4668e1a5dc7c7418ff0c059096f14300bafd0ffd9133b", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Sup_ {
    /// The shape of [Sup] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sup] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sup_"), "82612012158d4991c8e4668e1a5dc7c7418ff0c059096f14300bafd0ffd9133b", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Sup"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("lost", Param.fixed(ClassDesc.of("p.Secret"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Sup"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Sup], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sup].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Sup open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); lost(p.Secret); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Sup()
        """;

        private Canonical() {
        }
    }

    /// The token of [Sup].
    public static final OpenClassToken<Sup> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Sup#Sup()].
    public static final CtorRef0<Sup> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Sup_() {
    }
}
