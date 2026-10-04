package gen.facts.p;

import gen.facts.p.Empty_.Canonical;
import gen.facts.p.Empty_.Data;
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
import p.Empty;

/// The full metamodel of [Empty], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Empty] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Empty.class, fingerprint = "df95bf01891bac30eea90cea25064b2d1d8976da35116d04e72b2e34d4a4e702", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Empty_ {
    /// The shape of [Empty] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Empty] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Empty_"), "df95bf01891bac30eea90cea25064b2d1d8976da35116d04e72b2e34d4a4e702", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Empty"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Empty], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Empty].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Empty interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Empty].
    public static final InterfaceToken<Empty> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Empty_() {
    }
}
