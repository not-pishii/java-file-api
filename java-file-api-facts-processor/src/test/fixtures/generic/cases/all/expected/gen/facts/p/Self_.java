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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Self.class, fingerprint = "36594969c7945c44750a5819e5cf73cf5ef1f56c54acb93cacf11b463ffab200", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Self_<S extends Self<S>> {
    public static final class Data {
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Self_"), "36594969c7945c44750a5819e5cf73cf5ef1f56c54acb93cacf11b463ffab200", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Self"), List.of(new TypeParam("S", List.of(new ParameterizedTypeRef(ClassDesc.of("p.Self"), List.of(Types.exact(Types.typeVar("S"))))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("S")), List.of()), new MethodTableTemplate(Set.of(Signature.of("me")), Set.of(Signature.of("clone"), Signature.of("compare", Param.var(0)), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Self"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Self abstract-class sealed=no\ntparams #0 extends p.Self<#0>\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method abstract me() -> #0 throws -\nmember method overridable compare(#0) -> int throws -\ntable abstract me()\ntable concrete clone(); compare(#0); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Self()\n";

        private Canonical() {
        }
    }

    public static final AbstractClassToken<Self<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    public final AbstractClassToken<Self<S>> token;

    public final AbstractCtorRef0<Self<S>> super_;

    public final MethodRef1<Self<S>, Int, S> compare_S;

    public final MethodRef0<Self<S>, S> me;

    public Self_(RefToken<S> s) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(s));
        this.super_ = UnsafeFacts.abstractCtor(token, MemberTraits.FINAL);
        this.compare_S = UnsafeFacts.method(token, "compare", PrimitiveToken.INT, UnsafeFacts.param(s, Param.var(0)), MemberTraits.OVERRIDABLE);
        this.me = UnsafeFacts.method(token, "me", s, MemberTraits.ABSTRACT);
    }
}
