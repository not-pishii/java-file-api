package gen.facts.p;

import gen.facts.p.NotFoundB_.Canonical;
import gen.facts.p.NotFoundB_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.NotFoundB;

/// The full metamodel of [NotFoundB]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [NotFoundB]: it is here as a supertype of [p.Nested], whose inherited members are called through this metamodel.
///
/// A member [NotFoundB] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = NotFoundB.class, fingerprint = "0e10ed85c45d2c37d3a896f94b9b171c5a2f6f70f956abab1c5a81cc5f90791e", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class NotFoundB_ {
    /// The shape of [NotFoundB] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [NotFoundB] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.NotFoundB_"), "0e10ed85c45d2c37d3a896f94b9b171c5a2f6f70f956abab1c5a81cc5f90791e", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.NotFoundB"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [NotFoundB], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [NotFoundB].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.NotFoundB interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract m() -> void throws java.io.FileNotFoundException\nsam m() -> void throws java.io.FileNotFoundException\ntable abstract m()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [NotFoundB].
    public static final InterfaceToken<NotFoundB> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [NotFoundB#m()].
    public static final VoidMethodRef0<NotFoundB> m = UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<FileNotFoundException>openClassToken(gen.facts.java.io.FileNotFoundException_.Data.SHAPE)));

    /// The fact of the single abstract method [NotFoundB#m()], which a lambda implements.
    public static final VoidSam0<NotFoundB> sam = UnsafeFacts.voidSam(m);

    private NotFoundB_() {
    }
}
