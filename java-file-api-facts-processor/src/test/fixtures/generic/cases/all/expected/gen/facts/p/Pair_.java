package gen.facts.p;

import gen.facts.p.Pair_.Canonical;
import gen.facts.p.Pair_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Pair;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Pair.class, fingerprint = "487a1fcee9609e0a65ea0dcd348dee9fab9f03de1579c625dc7c9d81a6237cc8", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Pair_<A, B> {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Pair_"), "487a1fcee9609e0a65ea0dcd348dee9fab9f03de1579c625dc7c9d81a6237cc8", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Pair"), List.of(new TypeParam("A", List.of()), new TypeParam("B", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("A"), Types.typeVar("B")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("entry"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("swap"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Pair", Param.var(0), Param.var(1)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Pair final-class sealed=no\ntparams #0; #1\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(#0, #1) throws -\nmember field instance final #0 first\nmember field instance final #1 second\nmember method final entry() -> java.util.Map$Entry<#0, #1> throws -\nmember method final swap() -> p.Pair<#1, #0> throws -\ntable abstract -\ntable concrete clone(); entry(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); swap(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Pair(#0, #1)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Pair<?, ?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    public final FinalClassToken<Pair<A, B>> token;

    public final FieldRef<Pair<A, B>, A> first;

    public final FieldRef<Pair<A, B>, B> second;

    public final CtorRef2<Pair<A, B>, A, B> new_A_B;

    public final MethodRef0<Pair<A, B>, Entry<A, B>> entry;

    public final MethodRef0<Pair<A, B>, Pair<B, A>> swap;

    public Pair_(RefToken<A> a, RefToken<B> b) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(a), TokenArg.exact(b));
        this.first = UnsafeFacts.field(token, "first", a);
        this.second = UnsafeFacts.field(token, "second", b);
        this.new_A_B = UnsafeFacts.ctor(token, UnsafeFacts.param(a, Param.var(0)), UnsafeFacts.param(b, Param.var(1)), MemberTraits.FINAL);
        this.entry = UnsafeFacts.method(token, "entry", UnsafeFacts.<Entry<A, B>>interfaceToken(gen.facts.java.util.Map_Entry_.Data.SHAPE, TokenArg.exact(a), TokenArg.exact(b)), MemberTraits.FINAL);
        this.swap = UnsafeFacts.method(token, "swap", UnsafeFacts.<Pair<B, A>>finalClassToken(Data.SHAPE, TokenArg.exact(b), TokenArg.exact(a)), MemberTraits.FINAL);
    }
}
