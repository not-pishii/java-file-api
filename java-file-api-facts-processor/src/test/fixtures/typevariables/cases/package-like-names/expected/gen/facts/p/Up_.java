package gen.facts.p;

import gen.facts.p.Up_.Canonical;
import gen.facts.p.Up_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Up;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Up.class, fingerprint = "9957b82a5da33a004f590c236096c1d0992991a9e0d3f48864d0238b584b5d41", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Up_<Gen, Java> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Up_"), "9957b82a5da33a004f590c236096c1d0992991a9e0d3f48864d0238b584b5d41", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Up"), List.of(new TypeParam("Gen", List.of()), new TypeParam("Java", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("Gen"), Types.typeVar("Java")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.var(1)), Signature.of("clone"), Signature.of("each", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Up"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Up open-class sealed=no\ntparams #0; #1\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable <^0> each(^0) -> java.util.Set<^0> throws -\nmember method overridable all(#1) -> java.util.Set<#0> throws -\ntable abstract -\ntable concrete all(#1); clone(); each(java.lang.Object); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Up()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Up<?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    public final OpenClassToken<Up<Gen, Java>> token;

    public final CtorRef0<Up<Gen, Java>> new_;

    public final MethodRef1<Up<Gen, Java>, Set<Gen>, Java> all_Java;

    private final RefToken<Gen> gen_;

    private final RefToken<Java> java;

    public Up_(RefToken<Gen> gen_, RefToken<Java> java) {
        this.gen_ = gen_;
        this.java = java;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(gen_), TokenArg.exact(java));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.all_Java = UnsafeFacts.method(token, "all", UnsafeFacts.<Set<Gen>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(gen_)), UnsafeFacts.param(java, Param.var(1)), MemberTraits.OVERRIDABLE);
    }

    public <Me> MethodRef1<Up<Gen, Java>, Set<Me>, Me> each_Me(RefToken<Me> me) {
        return UnsafeFacts.method(token, "each", UnsafeFacts.<Set<Me>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(me)), UnsafeFacts.param(me, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(me));
    }
}
