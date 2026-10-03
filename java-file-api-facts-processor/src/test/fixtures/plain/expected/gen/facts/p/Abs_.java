package gen.facts.p;

import gen.facts.p.Abs_.Canonical;
import gen.facts.p.Abs_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.AbstractCtorRef2;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Abs;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Abs.class, fingerprint = "42382ab13a51135a74b26a1b7dfa571d08785d731b2b7cfe5f3998acd08d750f", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Abs_ {
    public static final class Data {
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Abs_"), "42382ab13a51135a74b26a1b7dfa571d08785d731b2b7cfe5f3998acd08d750f", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Abs"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("clone"), Signature.of("done"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("make")), Set.of(Signature.of("Abs"), Signature.of("Abs", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("Abs", Param.fixed(ConstantDescs.CD_long)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Abs abstract-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember ctor(java.lang.String, int) throws -\nmember method abstract run() -> void throws -\nmember method overridable done() -> void throws -\nmember method static make() -> p.Abs throws -\ntable abstract run()\ntable concrete clone(); done(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static make()\ntable ctor Abs(); Abs(java.lang.String, int); Abs(long)\n";

        private Canonical() {
        }
    }

    public static final AbstractClassToken<Abs> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    public static final AbstractCtorRef0<Abs> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    public static final AbstractCtorRef2<Abs, String, Int> super_String_int = UnsafeFacts.abstractCtor(TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    public static final VoidMethodRef0<Abs> done = UnsafeFacts.voidMethod(TOKEN, "done", MemberTraits.OVERRIDABLE);

    public static final StaticMethodRef0<Abs> make = UnsafeFacts.staticMethod(TOKEN, "make", TOKEN, MemberTraits.FINAL);

    public static final VoidMethodRef0<Abs> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    private Abs_() {
    }
}
