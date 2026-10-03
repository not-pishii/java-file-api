package gen.facts.p;

import gen.facts.p.Sh_.Canonical;
import gen.facts.p.Sh_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Sh;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sh.class, fingerprint = "deab35d3e38cec4d995bc49c7cfc517af5ab5fa84485a625b8d5f827b29908de", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Sh_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sh_"), "deab35d3e38cec4d995bc49c7cfc517af5ab5fa84485a625b8d5f827b29908de", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Sh"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Sh"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Sh open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int x\nmember field instance mutable int x_\nmember method overridable <^0> x() -> ^0 throws -\nmember method overridable other() -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); other(); toString(); wait(); wait(long); wait(long, int); x()\ntable static -\ntable ctor Sh()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Sh> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<Sh, Int> x = UnsafeFacts.mutableField(TOKEN, "x", PrimitiveToken.INT);

    public static final CtorRef0<Sh> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final VoidMethodRef0<Sh> other = UnsafeFacts.voidMethod(TOKEN, "other", MemberTraits.OVERRIDABLE);

    private Sh_() {
    }
}
