package gen.facts.p;

import gen.facts.p.RawBound_.Canonical;
import gen.facts.p.RawBound_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.RawBound;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = RawBound.class, fingerprint = "16d28ff48c7925187c4aacf51ca065b8684710b91bb68c39d09e233d4a78e509", complete = true, format = 5)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class RawBound_<T extends Comparable> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.RawBound_"), "16d28ff48c7925187c4aacf51ca065b8684710b91bb68c39d09e233d4a78e509", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.RawBound"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("java.lang.Comparable"))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("RawBound"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.RawBound open-class sealed=no\ntparams #0 extends java.lang.Comparable\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable get() -> #0 throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor RawBound()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<RawBound<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    public final OpenClassToken<RawBound<T>> token;

    public final CtorRef0<RawBound<T>> new_;

    public final MethodRef0<RawBound<T>, T> get;

    public RawBound_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.get = UnsafeFacts.method(token, "get", t, MemberTraits.OVERRIDABLE);
    }
}
