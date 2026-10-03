package gen.facts.p;

import gen.facts.p.Gen_.Canonical;
import gen.facts.p.Gen_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Gen;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Gen.class, fingerprint = "750db6f1183754c9dfcf48398d08baf723665b86b24c7c57fe5016cebed330a2", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Gen_<E> {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Gen_"), "750db6f1183754c9dfcf48398d08baf723665b86b24c7c57fe5016cebed330a2", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Gen"), List.of(new TypeParam("E", List.of())), List.of(ClassDesc.of("p.Near"), ClassDesc.of("p.Far"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Far"), List.of(Types.exact(Types.typeVar("E")))), new ParameterizedTypeRef(ClassDesc.of("p.Near"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("api"), Signature.of("beyond"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("near"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("overridden"), Signature.of("pack"), Signature.of("prot"), Signature.of("pub"), Signature.of("redeclared"), Signature.of("self"), Signature.of("set", Param.var(0)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sfar"), Signature.of("snear")), Set.of(Signature.of("Gen"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Gen final-class sealed=no\ntparams #0\nsuperclasses p.Near; p.Far; java.lang.Object\nsupertypes p.Far<#0>; p.Near<#0>\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable #0 item\nmember field static constant java.lang.String CONST = \"const\"\nmember field static constant java.lang.String FAR = \"far\"\nmember field static constant java.lang.String HID = \"near\"\nmember field static mutable int counter\nmember method final api() -> java.lang.String throws -\nmember method final dflt() -> java.lang.String throws -\nmember method final get() -> #0 throws -\nmember method final near() -> java.lang.String throws -\nmember method final overridden() -> java.lang.String throws -\nmember method final pub() -> java.lang.String throws -\nmember method final redeclared() -> java.lang.String throws -\nmember method final set(#0) -> void throws -\nmember method static sfar() -> java.lang.String throws -\nmember method static snear() -> java.lang.String throws -\ntable abstract -\ntable concrete api(); beyond(); clone(); dflt(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); near(); notify(); notifyAll(); overridden(); pack(); prot(); pub(); redeclared(); self(); set(#0); toString(); wait(); wait(long); wait(long, int)\ntable static sfar(); snear()\ntable ctor Gen()\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Gen<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    public static final StaticFieldRef<String> CONST = UnsafeFacts.constantField(ANY, "CONST", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "const");

    public static final StaticFieldRef<String> FAR = UnsafeFacts.constantField(ANY, "FAR", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "far");

    public static final StaticFieldRef<String> HID = UnsafeFacts.constantField(ANY, "HID", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "near");

    public static final MutableStaticFieldRef<Int> counter = UnsafeFacts.mutableStaticField(ANY, "counter", PrimitiveToken.INT);

    public static final StaticMethodRef0<String> sfar = UnsafeFacts.staticMethod(ANY, "sfar", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final StaticMethodRef0<String> snear = UnsafeFacts.staticMethod(ANY, "snear", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public final FinalClassToken<Gen<E>> token;

    public final MutableFieldRef<Gen<E>, E> item;

    public final CtorRef0<Gen<E>> new_;

    public final MethodRef0<Gen<E>, String> api;

    public final MethodRef0<Gen<E>, String> dflt;

    public final MethodRef0<Gen<E>, E> get;

    public final MethodRef0<Gen<E>, String> near;

    public final MethodRef0<Gen<E>, String> overridden;

    public final MethodRef0<Gen<E>, String> pub;

    public final MethodRef0<Gen<E>, String> redeclared;

    public final VoidMethodRef1<Gen<E>, E> set_E;

    public Gen_(RefToken<E> e) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(e));
        this.item = UnsafeFacts.mutableField(token, "item", e);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.api = UnsafeFacts.method(token, "api", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.dflt = UnsafeFacts.method(token, "dflt", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.get = UnsafeFacts.method(token, "get", e, MemberTraits.FINAL);
        this.near = UnsafeFacts.method(token, "near", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.overridden = UnsafeFacts.method(token, "overridden", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.pub = UnsafeFacts.method(token, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.redeclared = UnsafeFacts.method(token, "redeclared", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.set_E = UnsafeFacts.voidMethod(token, "set", UnsafeFacts.param(e, Param.var(0)), MemberTraits.FINAL);
    }
}
