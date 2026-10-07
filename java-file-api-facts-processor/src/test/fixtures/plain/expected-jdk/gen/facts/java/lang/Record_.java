package gen.facts.java.lang;

import gen.facts.java.lang.Record_.Canonical;
import gen.facts.java.lang.Record_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.Access;
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
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Record]: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// `@Facts` does not ask for [Record]: it is here as a supertype of [p.Rec], whose inherited members are called through this metamodel.
///
/// A member [Record] inherits has its fact in the metamodel of the supertype that declares it: [Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Record.class, fingerprint = "5d9f500fc976a0b446a870cf0a83a8fa0a59da9b770a31f4343f86cbf991a40d", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Record_ {
    /// The shape of [Record] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Record] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Record_"), "5d9f500fc976a0b446a870cf0a83a8fa0a59da9b770a31f4343f86cbf991a40d", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("java.lang.Record"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("hashCode"), Signature.of("toString")), Set.of(Signature.of("clone"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Record"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Record], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Record].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.Record abstract-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor protected () throws -
        member method public abstract equals(java.lang.Object) -> boolean throws -
        member method public abstract hashCode() -> int throws -
        member method public abstract toString() -> java.lang.String throws -
        table abstract equals(java.lang.Object); hashCode(); toString()
        table concrete clone(); finalize(); getClass(); notify(); notifyAll(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Record()
        """;

        private Canonical() {
        }
    }

    /// The token of [Record].
    public static final AbstractClassToken<Record> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Record#Record()], which is `protected`: a subclass alone uses it.
    public static final SuperCtorRef0<Record> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL.with(Access.PROTECTED));

    /// The fact of [Record#equals(Object)].
    public static final MethodRef1<Record, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(Object_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of [Record#hashCode()].
    public static final MethodRef0<Record, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [Record#toString()].
    public static final MethodRef0<Record, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.ABSTRACT);

    private Record_() {
    }
}
