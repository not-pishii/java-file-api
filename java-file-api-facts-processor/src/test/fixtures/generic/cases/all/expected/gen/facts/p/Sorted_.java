package gen.facts.p;

import gen.facts.p.Sorted_.Canonical;
import gen.facts.p.Sorted_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
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
import p.Sorted;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sorted.class, fingerprint = "4226dff27970b622664f47a08676e95a2d03a8c90f57001f8486b6f07848202e", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Sorted_<T extends Comparable<T>> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sorted_"), "4226dff27970b622664f47a08676e95a2d03a8c90f57001f8486b6f07848202e", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Sorted"), List.of(new TypeParam("T", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("T"))))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("max"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("with", Param.var(0))), Set.of(), Set.of(Signature.of("Sorted", Param.var(0)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Sorted open-class sealed=no\ntparams #0 extends java.lang.Comparable<#0>\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(#0) throws -\nmember method overridable max() -> #0 throws -\nmember method overridable with(#0) -> p.Sorted<#0> throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); max(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); with(#0)\ntable static -\ntable ctor Sorted(#0)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Sorted<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    public final OpenClassToken<Sorted<T>> token;

    public final CtorRef1<Sorted<T>, T> new_T;

    public final MethodRef0<Sorted<T>, T> max;

    public final MethodRef1<Sorted<T>, Sorted<T>, T> with_T;

    public Sorted_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_T = UnsafeFacts.ctor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL);
        this.max = UnsafeFacts.method(token, "max", t, MemberTraits.OVERRIDABLE);
        this.with_T = UnsafeFacts.method(token, "with", token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
    }
}
