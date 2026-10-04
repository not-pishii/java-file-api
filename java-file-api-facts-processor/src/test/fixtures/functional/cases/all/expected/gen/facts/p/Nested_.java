package gen.facts.p;

import gen.facts.p.Nested_.Canonical;
import gen.facts.p.Nested_.Data;
import java.io.FileNotFoundException;
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
import p.Nested;

/// The full metamodel of [Nested], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Nested] inherits has its fact in the metamodel of the supertype that declares it: [IoA_] and [NotFoundB_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Nested.class, fingerprint = "17908b08f5b1b8fa228bbda786118e240a73d0881fc707df09bc833785bd202c", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Nested_ {
    /// The shape of [Nested] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Nested] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Nested_"), "17908b08f5b1b8fa228bbda786118e240a73d0881fc707df09bc833785bd202c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Nested"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Nested], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Nested].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Nested interface sealed=no\ntparams -\nsuperclasses -\ninterfaces p.IoA; p.NotFoundB\nsupertypes -\nenum -\nmembers declared-public\nsam m() -> void throws java.io.FileNotFoundException\ntable abstract m()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Nested].
    public static final InterfaceToken<Nested> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of the single abstract method [p.IoA#m()], which a lambda implements.
    public static final VoidSam0<Nested> sam = UnsafeFacts.voidSam(UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<FileNotFoundException>openClassToken(gen.facts.java.io.FileNotFoundException_.Data.SHAPE))));

    private Nested_() {
    }
}
