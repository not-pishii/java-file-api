package gen.facts.p;

import gen.facts.p.Wide_.Canonical;
import gen.facts.p.Wide_.Data;
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
import p.Wide;

/// The full metamodel of [Wide], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Wide] inherits has its fact in the metamodel of the supertype that declares it: [Two_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Wide.class, fingerprint = "d1e9a85dee529c4afce3fd109cc6c1e8c36635f1fc48d7ddb5097f13a0aece16", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Wide_ {
    /// The shape of [Wide] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Wide] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Wide_"), "d1e9a85dee529c4afce3fd109cc6c1e8c36635f1fc48d7ddb5097f13a0aece16", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Wide"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("b")), Set.of(Signature.of("a"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Wide], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Wide].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Wide interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method overridable a() -> void throws -\nsam b() -> void throws -\ntable abstract b()\ntable concrete a(); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Wide].
    public static final InterfaceToken<Wide> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Wide#a()].
    public static final VoidMethodRef0<Wide> a = UnsafeFacts.voidMethod(TOKEN, "a", MemberTraits.OVERRIDABLE);

    /// The fact of the single abstract method [p.Two#b()], which a lambda implements.
    public static final VoidSam0<Wide> sam = UnsafeFacts.voidSam(UnsafeFacts.voidMethod(TOKEN, "b", MemberTraits.ABSTRACT));

    private Wide_() {
    }
}
