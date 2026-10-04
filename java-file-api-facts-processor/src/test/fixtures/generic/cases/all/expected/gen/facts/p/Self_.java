package gen.facts.p;

import gen.facts.p.Self_.Canonical;
import gen.facts.p.Self_.Data;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Self;

/// The full metamodel of [Self], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Self] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <S> a type argument of [Self]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Self.class, fingerprint = "d3294ad1dc39c6be5e082aba5055fa341e0756f350b20c44acd0edcdf2c89b9b", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Self_<S extends Self<S>> {
    /// The shape of [Self] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Self] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Self_"), "d3294ad1dc39c6be5e082aba5055fa341e0756f350b20c44acd0edcdf2c89b9b", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Self"), List.of(new TypeParam("S", List.of(new ParameterizedTypeRef(ClassDesc.of("p.Self"), List.of(Types.exact(Types.typeVar("S"))))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("S")), List.of()), new MethodTableTemplate(Set.of(Signature.of("me")), Set.of(Signature.of("clone"), Signature.of("compare", Param.var(0)), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Self"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Self], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Self].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Self abstract-class sealed=no\ntparams #0 extends p.Self<#0>\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method abstract me() -> #0 throws -\nmember method overridable compare(#0) -> int throws -\ntable abstract me()\ntable concrete clone(); compare(#0); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Self()\n";

        private Canonical() {
        }
    }

    /// The token of [Self] with a wildcard for every type argument.
    public static final AbstractClassToken<Self<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Self] with the type arguments of this metamodel.
    public final AbstractClassToken<Self<S>> token;

    /// The fact of [Self#Self()].
    public final AbstractCtorRef0<Self<S>> super_;

    /// The fact of [Self#compare(Self)].
    public final MethodRef1<Self<S>, Int, S> compare_S;

    /// The fact of [Self#me()].
    public final MethodRef0<Self<S>, S> me;

    /// The metamodel of [Self] with the type arguments the tokens give.
    ///
    /// @param s the token of the type argument `S`
    public Self_(RefToken<S> s) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(s));
        this.super_ = UnsafeFacts.abstractCtor(token, MemberTraits.FINAL);
        this.compare_S = UnsafeFacts.method(token, "compare", PrimitiveToken.INT, UnsafeFacts.param(s, Param.var(0)), MemberTraits.OVERRIDABLE);
        this.me = UnsafeFacts.method(token, "me", s, MemberTraits.ABSTRACT);
    }
}
