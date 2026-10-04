package gen.facts.p;

import gen.facts.p.Marker_.Canonical;
import gen.facts.p.Marker_.Data;
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
import p.Marker;

/// The full metamodel of [Marker]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Marker]: it is here as a supertype of [p.Svc], whose inherited members are called through this metamodel.
///
/// A member [Marker] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Marker.class, fingerprint = "f3f6b172f237a3f5a7b1b2f203df3873358d571d1413f26d71a6a9b7b5d5c2a3", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Marker_ {
    /// The shape of [Marker] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Marker] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Marker_"), "f3f6b172f237a3f5a7b1b2f203df3873358d571d1413f26d71a6a9b7b5d5c2a3", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Marker"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Marker], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Marker].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Marker interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Marker].
    public static final InterfaceToken<Marker> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Marker_() {
    }
}
