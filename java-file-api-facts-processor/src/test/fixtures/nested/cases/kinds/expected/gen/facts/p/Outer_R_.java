package gen.facts.p;

import gen.facts.p.Outer_R_.Canonical;
import gen.facts.p.Outer_R_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import me.supcheg.javafile.facts.RecordComponent;
import me.supcheg.javafile.facts.RecordShape.Of;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.jspecify.annotations.NullMarked;
import p.Outer.R;

/// The full metamodel of [R], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [R] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Record_].
///
/// No generated class extends or implements [R]: it is a final class, which no class extends.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = R.class, fingerprint = "81b1eaffd4b33e32c1d5e96045e27d7edec510a0600e6337dfb8fafb18e12f46", complete = true, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Outer_R_ {
    /// The shape of [R] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [R] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_R_"), "81b1eaffd4b33e32c1d5e96045e27d7edec510a0600e6337dfb8fafb18e12f46", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Outer$R"), List.of(), List.of(ClassDesc.of("java.lang.Record"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Outer$R", Param.fixed(ConstantDescs.CD_int))), Set.of(), Map.ofEntries(Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("finalize"), List.of(new Thrown(ClassDesc.of("java.lang.Throwable"), List.of(ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(), new Of(List.of(new RecordComponent("x", PrimitiveTypeRef.INT))));

        private Data() {
        }
    }

    /// The canonical form of [R], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [R].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Outer$R final-class sealed=no nesting=static
        annotations -
        annotation -
        tparams -
        superclasses java.lang.Record; java.lang.Object
        interfaces -
        supertypes -
        enum -
        record x int
        members declared-accessible
        member ctor public (int) throws -
        member method public final equals(java.lang.Object) -> boolean throws -
        member method public final hashCode() -> int throws -
        member method public final toString() -> java.lang.String throws -
        member method public final x() -> int throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()
        table static -
        table ctor Outer$R(int)
        """;

        private Canonical() {
        }
    }

    /// The token of [R].
    public static final FinalClassToken<R> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [R#R(int)].
    public static final CtorRef1<R, Int> new_int = UnsafeInvocables.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [R#equals(Object)].
    public static final MethodRef1<R, Bool, Object> equals_Object = UnsafeInvocables.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [R#hashCode()].
    public static final MethodRef0<R, Int> hashCode = UnsafeInvocables.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [R#toString()].
    public static final MethodRef0<R, String> toString = UnsafeInvocables.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [R#x()].
    public static final MethodRef0<R, Int> x = UnsafeInvocables.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.FINAL);

    private Outer_R_() {
    }
}
