package gen.facts.p;

import gen.facts.p.IoB_.Canonical;
import gen.facts.p.IoB_.Data;
import java.io.IOException;
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
import p.IoB;

/// The full metamodel of [IoB]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [IoB]: it is here as a supertype of [p.Same], whose inherited members are called through this metamodel.
///
/// A member [IoB] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = IoB.class, fingerprint = "40f07ba25d9a202afb751ca51898e18d9466e08415b7af94c24f8294035bb4b8", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class IoB_ {
    /// The shape of [IoB] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [IoB] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.IoB_"), "40f07ba25d9a202afb751ca51898e18d9466e08415b7af94c24f8294035bb4b8", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.IoB"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [IoB], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [IoB].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.IoB interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract m() -> void throws java.io.IOException\nsam m() -> void throws java.io.IOException\ntable abstract m()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [IoB].
    public static final InterfaceToken<IoB> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [IoB#m()].
    public static final VoidMethodRef0<IoB> m = UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    /// The fact of the single abstract method [IoB#m()], which a lambda implements.
    public static final VoidSam0<IoB> sam = UnsafeFacts.voidSam(m);

    private IoB_() {
    }
}
