package gen.facts.p;

import gen.facts.p.Hid2_.Canonical;
import gen.facts.p.Hid2_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Hid2;

/// The full metamodel of [Hid2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Hid2] inherits has its fact in the metamodel of the supertype that declares it: [Hid_].
///
/// These members have no fact:
///
/// - the single abstract method, which mentions types that are not public: p.Secret
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Hid2.class, fingerprint = "93df33bc7c1d1660ca3d1d3181cd8f3649ddb19866b9bfb612710bad51cc91cd", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Hid2_ {
    /// The shape of [Hid2] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Hid2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Hid2_"), "93df33bc7c1d1660ca3d1d3181cd8f3649ddb19866b9bfb612710bad51cc91cd", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Hid2"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("s")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Hid2], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Hid2].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Hid2 interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract s()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Hid2].
    public static final InterfaceToken<Hid2> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Hid2_() {
    }
}
