package gen.facts.p;

import gen.facts.p.NoneB_.Canonical;
import gen.facts.p.NoneB_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.NoneB;

/// The full metamodel of [NoneB]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [NoneB]: it is here as a supertype of [p.OneWithout], whose inherited members are called through this metamodel.
///
/// A member [NoneB] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = NoneB.class, fingerprint = "7f554f8c98bb065eaf6880da6b3303bed7d4c3dd65a8c957b8ed340c032475b2", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class NoneB_ {
    /// The shape of [NoneB] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [NoneB] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.NoneB_"), "7f554f8c98bb065eaf6880da6b3303bed7d4c3dd65a8c957b8ed340c032475b2", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.NoneB"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [NoneB], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [NoneB].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.NoneB interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract m() -> void throws -\nsam m() -> void throws -\ntable abstract m()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [NoneB].
    public static final InterfaceToken<NoneB> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [NoneB#m()].
    public static final VoidMethodRef0<NoneB> m = UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [NoneB#m()], which a lambda implements.
    public static final VoidSam0<NoneB> sam = UnsafeFacts.voidSam(m);

    private NoneB_() {
    }
}
