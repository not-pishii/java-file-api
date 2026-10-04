package gen.facts.p;

import gen.facts.p.IoA_.Canonical;
import gen.facts.p.IoA_.Data;
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
import p.IoA;

/// The full metamodel of [IoA]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [IoA]: it is here as a supertype of [p.Disjoint], [p.Nested], [p.OneWithout] and 1 more requested type, whose inherited members are called through this metamodel.
///
/// A member [IoA] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = IoA.class, fingerprint = "8e275f5296b8bda68c43ddad39cdb03332d318f33c72c4a5f96d12bd195ace64", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class IoA_ {
    /// The shape of [IoA] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [IoA] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.IoA_"), "8e275f5296b8bda68c43ddad39cdb03332d318f33c72c4a5f96d12bd195ace64", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.IoA"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [IoA], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [IoA].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.IoA interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract m() -> void throws java.io.IOException\nsam m() -> void throws java.io.IOException\ntable abstract m()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [IoA].
    public static final InterfaceToken<IoA> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [IoA#m()].
    public static final VoidMethodRef0<IoA> m = UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    /// The fact of the single abstract method [IoA#m()], which a lambda implements.
    public static final VoidSam0<IoA> sam = UnsafeFacts.voidSam(m);

    private IoA_() {
    }
}
