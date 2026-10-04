package gen.facts.p;

import gen.facts.p.OneWithout_.Canonical;
import gen.facts.p.OneWithout_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.OneWithout;

/// The full metamodel of [OneWithout], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [OneWithout] inherits has its fact in the metamodel of the supertype that declares it: [IoA_] and [NoneB_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = OneWithout.class, fingerprint = "a4f093e1424d1534ca1c9dbec1fe950e68f68ec23726a3edbdb97bc4008c1afc", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class OneWithout_ {
    /// The shape of [OneWithout] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [OneWithout] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.OneWithout_"), "a4f093e1424d1534ca1c9dbec1fe950e68f68ec23726a3edbdb97bc4008c1afc", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.OneWithout"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [OneWithout], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [OneWithout].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.OneWithout interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nsam m() -> void throws -\ntable abstract m()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [OneWithout].
    public static final InterfaceToken<OneWithout> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of the single abstract method [p.IoA#m()], which a lambda implements.
    public static final VoidSam0<OneWithout> sam = UnsafeFacts.voidSam(UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT));

    private OneWithout_() {
    }
}
