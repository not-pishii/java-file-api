package gen.facts.java.io;

import gen.facts.java.io.Serializable_.Canonical;
import gen.facts.java.io.Serializable_.Data;
import java.io.Serializable;
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

/// The full metamodel of [Serializable]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Serializable]: it is here as a supertype of [p.Color] and [p.Num], whose inherited members are called through this metamodel.
///
/// A member [Serializable] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Serializable.class, fingerprint = "76ac3d2240e9de42f65ecba9c6614d5238921d23f628d6146cc7e54565b5f51c", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Serializable_ {
    /// The shape of [Serializable] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Serializable] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.io.Serializable_"), "76ac3d2240e9de42f65ecba9c6614d5238921d23f628d6146cc7e54565b5f51c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.io.Serializable"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Serializable], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Serializable].
        static final String TEXT = "javafile-facts-canonical 4\ntype java.io.Serializable interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Serializable].
    public static final InterfaceToken<Serializable> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Serializable_() {
    }
}
