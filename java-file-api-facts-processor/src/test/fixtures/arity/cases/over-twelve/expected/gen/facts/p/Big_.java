package gen.facts.p;

import gen.facts.p.Big_.Canonical;
import gen.facts.p.Big_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Big;

/// The full metamodel of [Big], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Big] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `constructor Big(int,int,int,int,int,int,int,int,int,int,int,int,int)`, which has 13 parameters, more than 12
/// - `method thirteen(int,int,int,int,int,int,int,int,int,int,int,int,int)`, which has 13 parameters, more than 12
/// - `method staticThirteen(int,int,int,int,int,int,int,int,int,int,int,int,int)`, which has 13 parameters, more than 12
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Big.class, fingerprint = "3fce51c6c04b6e425894b8cb3549c3727661804443b095241d34479fabf62bff", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Big_ {
    /// The shape of [Big] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Big] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Big_"), "3fce51c6c04b6e425894b8cb3549c3727661804443b095241d34479fabf62bff", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Big"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ok"), Signature.of("thirteen", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toString"), Signature.of("twelve", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("staticThirteen", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("Big", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("Big", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Big], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Big].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Big open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (int, int, int, int, int, int, int, int, int, int, int, int) throws -
        member method public overridable ok() -> void throws -
        member method public overridable twelve(int, int, int, int, int, int, int, int, int, int, int, int) -> void throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); ok(); thirteen(int, int, int, int, int, int, int, int, int, int, int, int, int); toString(); twelve(int, int, int, int, int, int, int, int, int, int, int, int); wait(); wait(long); wait(long, int)
        table static staticThirteen(int, int, int, int, int, int, int, int, int, int, int, int, int)
        table ctor Big(int, int, int, int, int, int, int, int, int, int, int, int); Big(int, int, int, int, int, int, int, int, int, int, int, int, int)
        """;

        private Canonical() {
        }
    }

    /// The token of [Big].
    public static final OpenClassToken<Big> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Big#Big(int, int, int, int, int, int, int, int, int, int, int, int)].
    public static final CtorRef12<Big, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int> new_int_int_int_int_int_int_int_int_int_int_int_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Big#ok()].
    public static final VoidMethodRef0<Big> ok = UnsafeFacts.voidMethod(TOKEN, "ok", MemberTraits.OVERRIDABLE);

    /// The fact of [Big#twelve(int, int, int, int, int, int, int, int, int, int, int, int)].
    public static final VoidMethodRef12<Big, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int> twelve_int_int_int_int_int_int_int_int_int_int_int_int = UnsafeFacts.voidMethod(TOKEN, "twelve", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private Big_() {
    }
}
