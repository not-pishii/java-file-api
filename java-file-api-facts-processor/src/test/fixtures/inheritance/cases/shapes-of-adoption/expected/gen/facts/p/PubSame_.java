package gen.facts.p;

import gen.facts.p.PubSame_.Canonical;
import gen.facts.p.PubSame_.Data;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PubSame;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubSame.class, fingerprint = "54b87bcf6434e071bbf128b5c7630be89bd991470a559a69f3751e70b9a311f0", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PubSame_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubSame_"), "54b87bcf6434e071bbf128b5c7630be89bd991470a559a69f3751e70b9a311f0", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubSame"), List.of(), List.of(ClassDesc.of("p.HSame"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("tag"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("tag", Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("PubSame"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.PubSame open-class sealed=no\ntparams -\nsuperclasses p.HSame; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant java.lang.String TAG = \"field\"\nmember method overridable tag() -> java.lang.String throws -\nmember method static tag(int) -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); tag(); toString(); wait(); wait(long); wait(long, int)\ntable static tag(int)\ntable ctor PubSame()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<PubSame> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final StaticFieldRef<String> TAG = UnsafeFacts.constantField(TOKEN, "TAG", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "field");

    public static final CtorRef0<PubSame> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<PubSame, String> tag = UnsafeFacts.method(TOKEN, "tag", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final StaticMethodRef1<String, Int> tag_int = UnsafeFacts.staticMethod(TOKEN, "tag", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    private PubSame_() {
    }
}
