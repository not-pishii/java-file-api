package gen.facts.p;

import gen.facts.p.Kept_.Canonical;
import gen.facts.p.Kept_.Data;
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
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Kept;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Kept.class, fingerprint = "4c2ade6540e740b55741d80d5ef058c61afede0b3ab592ae2f745fed52d6eb5d", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Kept_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Kept_"), "4c2ade6540e740b55741d80d5ef058c61afede0b3ab592ae2f745fed52d6eb5d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Kept"), List.of(), List.of(ClassDesc.of("p.BadMid"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.BadMid"), List.of(Types.exact(Types.of(ClassDesc.of("p.Secret"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("bad"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("more"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Kept"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Kept open-class sealed=no\ntparams -\nsuperclasses p.BadMid; java.lang.Object\nsupertypes p.BadMid<p.Secret>\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant java.lang.String K = \"k\"\nmember method overridable more() -> java.lang.String throws -\nmember method overridable run() -> java.lang.String throws -\ntable abstract -\ntable concrete bad(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); more(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Kept()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Kept> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "k");

    public static final CtorRef0<Kept> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Kept, String> more = UnsafeFacts.method(TOKEN, "more", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Kept, String> run = UnsafeFacts.method(TOKEN, "run", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Kept_() {
    }
}
