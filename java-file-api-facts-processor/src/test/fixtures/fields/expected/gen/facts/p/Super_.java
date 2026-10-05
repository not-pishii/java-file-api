package gen.facts.p;

import gen.facts.p.Super_.Canonical;
import gen.facts.p.Super_.Data;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Super;

/// The full metamodel of [Super]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Super]: it is here as a supertype of [p.Sub], whose inherited members are called through this metamodel.
///
/// A member [Super] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Super.class, fingerprint = "7c5dc096129935c7d091b116dc0ba0c8853de39b3d0ece8fc05260c0e6e939c4", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Super_ {
    /// The shape of [Super] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Super] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Super_"), "7c5dc096129935c7d091b116dc0ba0c8853de39b3d0ece8fc05260c0e6e939c4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Super"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Super"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Super], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Super].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Super open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable java.lang.String f\nmember field static mutable java.lang.String g\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Super()\n";

        private Canonical() {
        }
    }

    /// The token of [Super].
    public static final OpenClassToken<Super> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Super#f].
    public static final MutableFieldRef<Super, String> f = UnsafeFacts.mutableField(TOKEN, "f", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Super#g].
    public static final MutableStaticFieldRef<String> g = UnsafeFacts.mutableStaticField(TOKEN, "g", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Super#Super()].
    public static final CtorRef0<Super> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Super_() {
    }
}
