package gen.facts.p;

import gen.facts.p.SubOv_.Canonical;
import gen.facts.p.SubOv_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.SubOv;

/// The full metamodel of [SubOv], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [SubOv] inherits has its fact in the metamodel of the supertype that declares it: [Ov_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = SubOv.class, fingerprint = "37a50508870f584397d76eac77f037de4e47fdacd127eaa3b90bed529c47cbb4", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class SubOv_ {
    /// The shape of [SubOv] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [SubOv] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.SubOv_"), "37a50508870f584397d76eac77f037de4e47fdacd127eaa3b90bed529c47cbb4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.SubOv"), List.of(), List.of(ClassDesc.of("p.Ov"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("c", Param.fixed(ClassDesc.of("java.lang.Comparable"))), Signature.of("c", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("solo", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("solo", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("SubOv"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [SubOv], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [SubOv].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.SubOv open-class sealed=no\ntparams -\nsuperclasses p.Ov; java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable solo(java.lang.String) -> java.lang.String throws -\ntable abstract -\ntable concrete c(java.lang.Comparable); c(java.lang.String); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(java.lang.Object); m(java.lang.String); notify(); notifyAll(); solo(java.lang.Object); solo(java.lang.String); toString(); wait(); wait(long); wait(long, int)\ntable static s(java.lang.Integer); s(java.lang.Object)\ntable ctor SubOv()\n";

        private Canonical() {
        }
    }

    /// The token of [SubOv].
    public static final OpenClassToken<SubOv> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [SubOv#SubOv()].
    public static final CtorRef0<SubOv> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [SubOv#solo(String)].
    public static final MethodRef1<SubOv, String, String> solo_String = UnsafeFacts.method(TOKEN, "solo", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private SubOv_() {
    }
}
