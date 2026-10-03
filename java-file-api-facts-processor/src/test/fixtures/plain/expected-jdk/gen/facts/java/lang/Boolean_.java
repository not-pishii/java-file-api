package gen.facts.java.lang;

import gen.facts.java.lang.Boolean_.Canonical;
import gen.facts.java.lang.Boolean_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Boolean.class, fingerprint = "fc5d479e7be40cc9489940af876fe407db441fa2d2517b6f1b85087eee0479a1", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Boolean_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Boolean_"), "fc5d479e7be40cc9489940af876fe407db441fa2d2517b6f1b85087eee0479a1", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Boolean"), List.of(), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Boolean"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("booleanValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Boolean"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("getBoolean", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("logicalAnd", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("logicalOr", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("logicalXor", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("parseBoolean", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Boolean", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("Boolean", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.Boolean final-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes java.lang.Comparable<java.lang.Boolean>\nenum -\nmembers none\ntable abstract -\ntable concrete booleanValue(); clone(); compareTo(java.lang.Boolean); describeConstable(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static compare(boolean, boolean); getBoolean(java.lang.String); hashCode(boolean); logicalAnd(boolean, boolean); logicalOr(boolean, boolean); logicalXor(boolean, boolean); parseBoolean(java.lang.String); toString(boolean); valueOf(boolean); valueOf(java.lang.String)\ntable ctor Boolean(boolean); Boolean(java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Boolean> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Boolean_() {
    }
}
