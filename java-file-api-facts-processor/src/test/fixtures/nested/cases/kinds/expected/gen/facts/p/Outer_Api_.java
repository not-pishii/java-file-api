package gen.facts.p;

import gen.facts.p.Outer_Api_.Canonical;
import gen.facts.p.Outer_Api_.Data;
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
import p.Outer.Api;

/// The full metamodel of [Api], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Api] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Api.class, fingerprint = "f3d7f6fbcf14287635c871c92604749c43bb7006691bec06fd86d0b595aa0740", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_Api_ {
    /// The shape of [Api] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Api] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_Api_"), "f3d7f6fbcf14287635c871c92604749c43bb7006691bec06fd86d0b595aa0740", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Outer$Api"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Api], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Api].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Outer$Api interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract run() -> void throws -\nsam run() -> void throws -\ntable abstract run()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Api].
    public static final InterfaceToken<Api> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Api#run()].
    public static final VoidMethodRef0<Api> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Api#run()], which a lambda implements.
    public static final VoidSam0<Api> sam = UnsafeFacts.voidSam(run);

    private Outer_Api_() {
    }
}
