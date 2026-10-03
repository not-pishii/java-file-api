package gen.facts.p;

import gen.facts.p.List_.Canonical;
import gen.facts.p.List_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.List;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = List.class, fingerprint = "bfef554bb7666540e643ac49b8fb300abd380ec345b1253b31bc9b77e5238176", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class List_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.List_"), "bfef554bb7666540e643ac49b8fb300abd380ec345b1253b31bc9b77e5238176", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.List"), java.util.List.of(), java.util.List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("canonical", Param.fixed(ClassDesc.of("p.Data"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("load"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("List"))), java.util.List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.List open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int Supertypes\nmember field instance mutable int java\nmember field instance mutable int p\nmember method overridable canonical(p.Data) -> p.Canonical throws -\nmember method overridable load() -> p.Data throws -\ntable abstract -\ntable concrete canonical(p.Data); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); load(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor List()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<List> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<List, Int> Supertypes_ = UnsafeFacts.mutableField(TOKEN, "Supertypes", PrimitiveToken.INT);

    public static final MutableFieldRef<List, Int> java_ = UnsafeFacts.mutableField(TOKEN, "java", PrimitiveToken.INT);

    public static final MutableFieldRef<List, Int> p_ = UnsafeFacts.mutableField(TOKEN, "p", PrimitiveToken.INT);

    public static final CtorRef0<List> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef1<List, p.Canonical, p.Data> canonical_Data = UnsafeFacts.method(TOKEN, "canonical", UnsafeFacts.<p.Canonical>openClassToken(Canonical_.Data.SHAPE), UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<List, p.Data> load = UnsafeFacts.method(TOKEN, "load", UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private List_() {
    }
}
