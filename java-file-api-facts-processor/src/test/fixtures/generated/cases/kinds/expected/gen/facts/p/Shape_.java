package gen.facts.p;

import gen.facts.p.Shape_.Canonical;
import gen.facts.p.Shape_.Data;
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
import p.Shape;

/// The full metamodel of [Shape], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Shape] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Shape.class, fingerprint = "f7ddf1a996349414864000e7240ea218ec5cb04e562cb9ef528aa47adb4d0b53", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Shape_ {
    /// The shape of [Shape] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Shape] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Shape_"), "f7ddf1a996349414864000e7240ea218ec5cb04e562cb9ef528aa47adb4d0b53", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Shape"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), true);

        private Data() {
        }
    }

    /// The canonical form of [Shape], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Shape].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Shape interface sealed=yes\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Shape].
    public static final InterfaceToken<Shape> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Shape_() {
    }
}
