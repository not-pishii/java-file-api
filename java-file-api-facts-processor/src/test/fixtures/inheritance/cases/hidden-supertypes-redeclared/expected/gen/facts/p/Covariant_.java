package gen.facts.p;

import gen.facts.p.Covariant_.Canonical;
import gen.facts.p.Covariant_.Data;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Covariant;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Covariant.class, fingerprint = "fe6d8a1e9f7f8b3b02c99b9f8f97caf21b09690c1d12c052a1ca91b9b59b473c", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Covariant_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Covariant_"), "fe6d8a1e9f7f8b3b02c99b9f8f97caf21b09690c1d12c052a1ca91b9b59b473c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Covariant"), List.of(), List.of(ClassDesc.of("p.Near"), ClassDesc.of("p.Far"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Far"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))), new ParameterizedTypeRef(ClassDesc.of("p.Near"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("api"), Signature.of("beyond"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("near"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("overridden"), Signature.of("pack"), Signature.of("prot"), Signature.of("pub"), Signature.of("redeclared"), Signature.of("self"), Signature.of("set", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("sfar"), Signature.of("snear")), Set.of(Signature.of("Covariant"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Covariant open-class sealed=no\ntparams -\nsuperclasses p.Near; p.Far; java.lang.Object\nsupertypes p.Far<java.lang.String>; p.Near<java.lang.String>\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable java.lang.String item\nmember field static constant java.lang.String CONST = \"const\"\nmember field static constant java.lang.String FAR = \"far\"\nmember field static constant java.lang.String HID = \"near\"\nmember field static mutable int counter\nmember method overridable api() -> java.lang.String throws -\nmember method overridable dflt() -> java.lang.String throws -\nmember method overridable get() -> java.lang.String throws -\nmember method overridable near() -> java.lang.String throws -\nmember method overridable overridden() -> java.lang.String throws -\nmember method overridable pub() -> java.lang.String throws -\nmember method overridable redeclared() -> java.lang.String throws -\nmember method overridable self() -> p.Covariant throws -\nmember method overridable set(java.lang.String) -> void throws -\nmember method static sfar() -> java.lang.String throws -\nmember method static snear() -> java.lang.String throws -\ntable abstract -\ntable concrete api(); beyond(); clone(); dflt(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); near(); notify(); notifyAll(); overridden(); pack(); prot(); pub(); redeclared(); self(); set(java.lang.String); toString(); wait(); wait(long); wait(long, int)\ntable static sfar(); snear()\ntable ctor Covariant()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Covariant> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final StaticFieldRef<String> CONST = UnsafeFacts.constantField(TOKEN, "CONST", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "const");

    public static final StaticFieldRef<String> FAR = UnsafeFacts.constantField(TOKEN, "FAR", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "far");

    public static final StaticFieldRef<String> HID = UnsafeFacts.constantField(TOKEN, "HID", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "near");

    public static final MutableStaticFieldRef<Int> counter = UnsafeFacts.mutableStaticField(TOKEN, "counter", PrimitiveToken.INT);

    public static final MutableFieldRef<Covariant, String> item = UnsafeFacts.mutableField(TOKEN, "item", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    public static final CtorRef0<Covariant> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Covariant, String> api = UnsafeFacts.method(TOKEN, "api", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Covariant, String> dflt = UnsafeFacts.method(TOKEN, "dflt", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Covariant, String> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Covariant, String> near = UnsafeFacts.method(TOKEN, "near", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Covariant, String> overridden = UnsafeFacts.method(TOKEN, "overridden", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Covariant, String> pub = UnsafeFacts.method(TOKEN, "pub", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Covariant, String> redeclared = UnsafeFacts.method(TOKEN, "redeclared", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Covariant, Covariant> self = UnsafeFacts.method(TOKEN, "self", TOKEN, MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<Covariant, String> set_String = UnsafeFacts.voidMethod(TOKEN, "set", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final StaticMethodRef0<String> sfar = UnsafeFacts.staticMethod(TOKEN, "sfar", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final StaticMethodRef0<String> snear = UnsafeFacts.staticMethod(TOKEN, "snear", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Covariant_() {
    }
}
