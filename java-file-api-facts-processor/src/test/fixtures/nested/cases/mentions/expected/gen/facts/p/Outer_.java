package gen.facts.p;

import gen.facts.p.Outer_.Canonical;
import gen.facts.p.Outer_.Data;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Outer;
import p.Outer.E;
import p.Outer.Inner;
import p.Outer.NonStatic;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Outer.class, fingerprint = "3833a1f9a10fa158ebad4980837bf90e3c55d4c1629b18ccc26294c62ba4c448", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Outer_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_"), "3833a1f9a10fa158ebad4980837bf90e3c55d4c1629b18ccc26294c62ba4c448", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Outer"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("e"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("inner"), Signature.of("nonStatic"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Outer"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Outer open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable e() -> p.Outer$E throws -\nmember method overridable inner() -> p.Outer$Inner throws -\nmember method overridable nonStatic() -> p.Outer$NonStatic throws -\ntable abstract -\ntable concrete clone(); e(); equals(java.lang.Object); finalize(); getClass(); hashCode(); inner(); nonStatic(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Outer()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Outer> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Outer> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Outer, E> e = UnsafeFacts.method(TOKEN, "e", UnsafeFacts.<E>enumToken(Outer_E_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Outer, Inner> inner = UnsafeFacts.method(TOKEN, "inner", UnsafeFacts.<Inner>openClassToken(Outer_Inner_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Outer, NonStatic> nonStatic = UnsafeFacts.method(TOKEN, "nonStatic", UnsafeFacts.<NonStatic>openClassToken(Outer_NonStatic_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Outer_() {
    }
}
