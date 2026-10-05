package gen.facts.p;

import gen.facts.p.Rec_.Canonical;
import gen.facts.p.Rec_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Rec;

/// The full metamodel of [Rec], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Rec] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Record_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Rec.class, fingerprint = "3350db5c7d4ddc614c19e3dcf64c6ad23d07fa56b332e0d7a45e6ff28f6e0d3c", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Rec_ {
    /// The shape of [Rec] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Rec] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Rec_"), "3350db5c7d4ddc614c19e3dcf64c6ad23d07fa56b332e0d7a45e6ff28f6e0d3c", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Rec"), List.of(), List.of(ClassDesc.of("java.lang.Record"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Rec", Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Rec], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Rec].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Rec final-class sealed=no
        tparams -
        superclasses java.lang.Record; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor(int) throws -
        member method final equals(java.lang.Object) -> boolean throws -
        member method final hashCode() -> int throws -
        member method final toString() -> java.lang.String throws -
        member method final x() -> int throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()
        table static -
        table ctor Rec(int)
        """;

        private Canonical() {
        }
    }

    /// The token of [Rec].
    public static final FinalClassToken<Rec> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [Rec#Rec(int)].
    public static final CtorRef1<Rec, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Rec#equals(Object)].
    public static final MethodRef1<Rec, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Rec#hashCode()].
    public static final MethodRef0<Rec, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Rec#toString()].
    public static final MethodRef0<Rec, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Rec#x()].
    public static final MethodRef0<Rec, Int> x = UnsafeFacts.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.FINAL);

    private Rec_() {
    }
}
