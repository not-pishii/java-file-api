package gen.facts.p;

import gen.facts.p.Iface_.Canonical;
import gen.facts.p.Iface_.Data;
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
import p.Iface;

/// The full metamodel of [Iface], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Iface] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Iface.class, fingerprint = "9f18d6454ab45d33616f972a624688abd7fc3c7b766852d2ae342de22210fd99", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Iface_ {
    /// The shape of [Iface] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Iface] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Iface_"), "9f18d6454ab45d33616f972a624688abd7fc3c7b766852d2ae342de22210fd99", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Iface"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Iface], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Iface].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Iface interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Iface].
    public static final InterfaceToken<Iface> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Iface_() {
    }
}
