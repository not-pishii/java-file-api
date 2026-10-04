package gen.facts.p;

import gen.facts.p.IfaceOnly_.Canonical;
import gen.facts.p.IfaceOnly_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.IfaceOnly;

/// The full metamodel of [IfaceOnly], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [IfaceOnly] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = IfaceOnly.class, fingerprint = "9f61e3f6f0b56c63098ea9d0c85f8c3286fa9552036e79f0c509ad2c0e60842f", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class IfaceOnly_ {
    /// The shape of [IfaceOnly] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [IfaceOnly] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.IfaceOnly_"), "9f61e3f6f0b56c63098ea9d0c85f8c3286fa9552036e79f0c509ad2c0e60842f", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.IfaceOnly"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("name")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [IfaceOnly], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [IfaceOnly].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.IfaceOnly interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract name() -> java.lang.String throws -\nsam name() -> java.lang.String throws -\ntable abstract name()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [IfaceOnly].
    public static final InterfaceToken<IfaceOnly> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [IfaceOnly#name()].
    public static final MethodRef0<IfaceOnly, String> name = UnsafeFacts.method(TOKEN, "name", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [IfaceOnly#name()], which a lambda implements.
    public static final Sam0<IfaceOnly, String> sam = UnsafeFacts.sam(name);

    private IfaceOnly_() {
    }
}
