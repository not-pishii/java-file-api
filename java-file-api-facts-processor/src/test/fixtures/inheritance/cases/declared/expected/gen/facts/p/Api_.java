package gen.facts.p;

import gen.facts.p.Api_.Canonical;
import gen.facts.p.Api_.Data;
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
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Api;

/// The full metamodel of [Api]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Api]: it is here as a supertype of [p.Derived], whose inherited members are called through this metamodel.
///
/// A member [Api] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Api.class, fingerprint = "6e08a5de8fdd3ef45a1cc7a88edfdedcc8b72bec2e40d05849ed271167152a39", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Api_ {
    /// The shape of [Api] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Api] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Api_"), "6e08a5de8fdd3ef45a1cc7a88edfdedcc8b72bec2e40d05849ed271167152a39", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Api"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("abs")), Set.of(Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("iface")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Api], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Api].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Api interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract abs() -> void throws -\nmember method overridable dflt() -> void throws -\nmember method static iface() -> void throws -\nsam abs() -> void throws -\ntable abstract abs()\ntable concrete dflt(); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static iface()\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Api].
    public static final InterfaceToken<Api> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Api#abs()].
    public static final VoidMethodRef0<Api> abs = UnsafeFacts.voidMethod(TOKEN, "abs", MemberTraits.ABSTRACT);

    /// The fact of [Api#dflt()].
    public static final VoidMethodRef0<Api> dflt = UnsafeFacts.voidMethod(TOKEN, "dflt", MemberTraits.OVERRIDABLE);

    /// The fact of [Api#iface()].
    public static final VoidStaticMethodRef0 iface = UnsafeFacts.voidStaticMethod(TOKEN, "iface", MemberTraits.FINAL);

    /// The fact of the single abstract method [Api#abs()], which a lambda implements.
    public static final VoidSam0<Api> sam = UnsafeFacts.voidSam(abs);

    private Api_() {
    }
}
