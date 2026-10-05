package gen.facts.p;

import gen.facts.p.Both_.Canonical;
import gen.facts.p.Both_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Both;

/// The full metamodel of [Both], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Both] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Both]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Both.class, fingerprint = "5723581a63e92f7b104218b74db4d3b3de63f9f8a90fafd814b196b0027596ca", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Both_<T extends Number & Comparable<T>> {
    /// The shape of [Both] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Both] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Both_"), "5723581a63e92f7b104218b74db4d3b3de63f9f8a90fafd814b196b0027596ca", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Both"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("java.lang.Number")), new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("T"))))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("pick")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Both"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Both], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Both].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Both abstract-class sealed=no\ntparams #0 extends java.lang.Number & java.lang.Comparable<#0>\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method abstract pick() -> #0 throws -\ntable abstract pick()\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Both()\n";

        private Canonical() {
        }
    }

    /// The token of [Both] with a wildcard for every type argument.
    public static final AbstractClassToken<Both<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Both] with the type arguments of this metamodel.
    public final AbstractClassToken<Both<T>> token;

    /// The fact of [Both#Both()].
    public final AbstractCtorRef0<Both<T>> super_;

    /// The fact of [Both#pick()].
    public final MethodRef0<Both<T>, T> pick;

    /// The metamodel of [Both] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Both_(RefToken<T> t) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(t));
        this.super_ = UnsafeFacts.abstractCtor(token, MemberTraits.FINAL);
        this.pick = UnsafeFacts.method(token, "pick", t, MemberTraits.ABSTRACT);
    }
}
