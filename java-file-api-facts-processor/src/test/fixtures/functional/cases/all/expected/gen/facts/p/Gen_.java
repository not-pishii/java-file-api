package gen.facts.p;

import gen.facts.p.Gen_.Canonical;
import gen.facts.p.Gen_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Gen;

/// The full metamodel of [Gen], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Gen] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Gen.class, fingerprint = "48ce0d1c5321725f465a784aca2863511f16be8a952e2951654e611786d7a66c", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Gen_ {
    /// The shape of [Gen] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Gen] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Gen_"), "48ce0d1c5321725f465a784aca2863511f16be8a952e2951654e611786d7a66c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Gen"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Gen], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Gen].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Gen interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract <^0> id(^0) -> ^0 throws -\ntable abstract id(java.lang.Object)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Gen].
    public static final InterfaceToken<Gen> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Gen_() {
    }

    /// The fact of [Gen#id(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef1<Gen, T, T> id_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "id", t, UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.ABSTRACT.withTypeArgs(t));
    }
}
