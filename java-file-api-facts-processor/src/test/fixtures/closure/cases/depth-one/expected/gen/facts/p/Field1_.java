package gen.facts.p;

import gen.facts.p.Field1_.Canonical;
import gen.facts.p.Field1_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Field1;

/// The token-only metamodel of [Field1]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Field1]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Field1.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Field1.class, fingerprint = "f204fd9b34689fa29597f838b385fb9c01c3458752eec9785f47886353ffaf6c", complete = false, format = 20)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Field1_ {
    /// The shape of [Field1] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Field1] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Field1_"), "f204fd9b34689fa29597f838b385fb9c01c3458752eec9785f47886353ffaf6c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Field1"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Field1")), Set.of(), Map.ofEntries(Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("finalize"), List.of(new Thrown(ClassDesc.of("java.lang.Throwable"), List.of(ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Field1], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Field1].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Field1 open-class sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        record -
        members none
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Field1()
        """;

        private Canonical() {
        }
    }

    /// The token of [Field1].
    public static final OpenClassToken<Field1> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Field1_() {
    }
}
