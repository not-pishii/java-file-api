package gen.facts.p;

import gen.facts.p.Big_.Canonical;
import gen.facts.p.Big_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
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
import me.supcheg.javafile.facts.RefToken;
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
///
/// A generated class extends or implements [Big] by the proof its metamodel starts, with the stages of `Big_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Big.class, fingerprint = "3eb477826fe9d9c68a7054053c8dae4b9713009e0ae465255f6c6566437a6a86", complete = true, format = 14)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Big_"), "3eb477826fe9d9c68a7054053c8dae4b9713009e0ae465255f6c6566437a6a86", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Big"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ok"), Signature.of("thirteen", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toString"), Signature.of("twelve", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("staticThirteen", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("Big", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("Big", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)))), false, new Big_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Big], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Big].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Big open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (int, int, int, int, int, int, int, int, int, int, int, int) throws -
        member method public overridable ok() -> void throws -
        member method public overridable twelve(int, int, int, int, int, int, int, int, int, int, int, int) -> void throws -
        inherit ctor Big(int, int, int, int, int, int, int, int, int, int, int, int) public (int, int, int, int, int, int, int, int, int, int, int, int) throws -
        inherit ctor Big(int, int, int, int, int, int, int, int, int, int, int, int, int) public (int, int, int, int, int, int, int, int, int, int, int, int, int) throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method ok() erased () overrides - by class public concrete p.Big () -> void throws -
        inherit method staticThirteen(int, int, int, int, int, int, int, int, int, int, int, int, int) erased (int, int, int, int, int, int, int, int, int, int, int, int, int) overrides - by class public static p.Big (int, int, int, int, int, int, int, int, int, int, int, int, int) -> int throws -
        inherit method thirteen(int, int, int, int, int, int, int, int, int, int, int, int, int) erased (int, int, int, int, int, int, int, int, int, int, int, int, int) overrides - by class public concrete p.Big (int, int, int, int, int, int, int, int, int, int, int, int, int) -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method twelve(int, int, int, int, int, int, int, int, int, int, int, int) erased (int, int, int, int, int, int, int, int, int, int, int, int) overrides - by class public concrete p.Big (int, int, int, int, int, int, int, int, int, int, int, int) -> void throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
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

    /// Starts the proof that a class being declared extends [Big]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Big> extend(RefToken<Self> self) {
        return new Big_Heritage().extend(TOKEN, self);
    }
}
