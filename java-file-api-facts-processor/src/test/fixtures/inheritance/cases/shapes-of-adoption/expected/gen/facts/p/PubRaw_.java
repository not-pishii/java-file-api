package gen.facts.p;

import gen.facts.p.PubRaw_.Canonical;
import gen.facts.p.PubRaw_.Data;
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
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PubRaw;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubRaw.class, fingerprint = "e10373c2ab650cb17ebc0c7534b59ae56c95f81be04701b554f204296e70203b", complete = true, format = 5)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class PubRaw_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubRaw_"), "e10373c2ab650cb17ebc0c7534b59ae56c95f81be04701b554f204296e70203b", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubRaw"), List.of(), List.of(ClassDesc.of("p.HG"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("all"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubRaw"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.PubRaw open-class sealed=no\ntparams -\nsuperclasses p.HG; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable java.lang.Object value\nmember method overridable all() -> java.util.List throws -\nmember method overridable get() -> java.lang.Object throws -\nmember method overridable put(java.lang.Object) -> void throws -\ntable abstract -\ntable concrete all(); clone(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); notify(); notifyAll(); put(java.lang.Object); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor PubRaw()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<PubRaw> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<PubRaw, Object> value = UnsafeFacts.mutableField(TOKEN, "value", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE));

    public static final CtorRef0<PubRaw> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<PubRaw, List> all = UnsafeFacts.method(TOKEN, "all", UnsafeFacts.<List>interfaceToken(gen.facts.java.util.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<PubRaw, Object> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<PubRaw, Object> put_Object = UnsafeFacts.voidMethod(TOKEN, "put", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private PubRaw_() {
    }
}
