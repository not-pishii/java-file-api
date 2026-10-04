package gen.facts.p;

import gen.facts.p.Low_.Canonical;
import gen.facts.p.Low_.Data;
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
import p.Low;

/// The full metamodel of [Low], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Low] inherits has its fact in the metamodel of the supertype that declares it: [High_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Low.class, fingerprint = "e1b04f5ff8ed445421cdace418162235cb27d4adb13d34f947623fe4867574c6", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Low_ {
    /// The shape of [Low] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Low] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Low_"), "e1b04f5ff8ed445421cdace418162235cb27d4adb13d34f947623fe4867574c6", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Low"), List.of(), List.of(ClassDesc.of("p.High"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("seen"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Low"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Low], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Low].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Low open-class sealed=no\ntparams -\nsuperclasses p.High; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); seen(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Low()\n";

        private Canonical() {
        }
    }

    /// The token of [Low].
    public static final OpenClassToken<Low> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Low#Low()].
    public static final CtorRef0<Low> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Low_() {
    }
}
