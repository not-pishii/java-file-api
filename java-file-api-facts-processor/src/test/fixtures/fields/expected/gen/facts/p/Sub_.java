package gen.facts.p;

import gen.facts.p.Sub_.Canonical;
import gen.facts.p.Sub_.Data;
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
import p.Sub;

/// The full metamodel of [Sub], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sub] inherits has its fact in the metamodel of the supertype that declares it: [Super_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sub.class, fingerprint = "981347e759b6a7797b296ddab44b3455f1cc52462f7d7fbdf53b9335b0fe543f", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Sub_ {
    /// The shape of [Sub] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sub] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sub_"), "981347e759b6a7797b296ddab44b3455f1cc52462f7d7fbdf53b9335b0fe543f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Sub"), List.of(), List.of(ClassDesc.of("p.Super"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Sub"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Sub], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sub].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Sub open-class sealed=no\ntparams -\nsuperclasses p.Super; java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable java.lang.String f\nmember field static mutable java.lang.String g\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Sub()\n";

        private Canonical() {
        }
    }

    /// The token of [Sub].
    public static final OpenClassToken<Sub> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Sub#f].
    public static final MutableFieldRef<Sub, String> f = UnsafeFacts.mutableField(TOKEN, "f", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Sub#g].
    public static final MutableStaticFieldRef<String> g = UnsafeFacts.mutableStaticField(TOKEN, "g", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Sub#Sub()].
    public static final CtorRef0<Sub> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Sub_() {
    }
}
