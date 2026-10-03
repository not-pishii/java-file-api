package gen.facts.java.lang;

import gen.facts.java.lang.Record_.Canonical;
import gen.facts.java.lang.Record_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Record.class, fingerprint = "c6ebfc1ff6b4142b16d243eeba0b4893ee4045e48ce9679f22401b68b818206a", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Record_ {
    public static final class Data {
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Record_"), "c6ebfc1ff6b4142b16d243eeba0b4893ee4045e48ce9679f22401b68b818206a", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("java.lang.Record"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("hashCode"), Signature.of("toString")), Set.of(Signature.of("clone"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Record"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.Record abstract-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract equals(java.lang.Object) -> boolean throws -\nmember method abstract hashCode() -> int throws -\nmember method abstract toString() -> java.lang.String throws -\ntable abstract equals(java.lang.Object); hashCode(); toString()\ntable concrete clone(); finalize(); getClass(); notify(); notifyAll(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Record()\n";

        private Canonical() {
        }
    }

    public static final AbstractClassToken<Record> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    public static final MethodRef1<Record, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(Object_.Data.SHAPE), MemberTraits.ABSTRACT);

    public static final MethodRef0<Record, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    public static final MethodRef0<Record, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.ABSTRACT);

    private Record_() {
    }
}
