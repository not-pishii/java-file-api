package gen.facts.p;

import gen.facts.p.SubOv_.Canonical;
import gen.facts.p.SubOv_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.SubOv;

/// The full metamodel of [SubOv], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [SubOv] inherits has its fact in the metamodel of the supertype that declares it: [Ov_].
///
/// A generated class extends or implements [SubOv] by the proof its metamodel starts, with the stages of `SubOv_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = SubOv.class, fingerprint = "9acb3ac8408610e8968dcc4d1ca8d72e154b765f34f534a6cde8b57407343e2c", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class SubOv_ {
    /// The shape of [SubOv] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [SubOv] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.SubOv_"), "9acb3ac8408610e8968dcc4d1ca8d72e154b765f34f534a6cde8b57407343e2c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.SubOv"), List.of(), List.of(ClassDesc.of("p.Ov"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("c", Param.fixed(ClassDesc.of("java.lang.Comparable"))), Signature.of("c", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("solo", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("solo", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("SubOv"))), false, new SubOv_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [SubOv], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [SubOv].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.SubOv open-class sealed=no
        tparams -
        superclasses p.Ov; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable solo(java.lang.String) -> java.lang.String throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Ov <^0 extends java.lang.Comparable<^0>> c(^0) -> java.lang.String throws - erased (java.lang.Comparable) overrides -
        inherit method public concrete p.Ov <^0> m(^0) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Ov <^0> solo(^0) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Ov c(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        inherit method public concrete p.Ov m(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        inherit method public concrete p.SubOv solo(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Ov <^0> s(^0) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public static p.Ov s(java.lang.Integer) -> java.lang.String throws - erased (java.lang.Integer) overrides -
        table abstract -
        table concrete c(java.lang.Comparable); c(java.lang.String); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(java.lang.Object); m(java.lang.String); notify(); notifyAll(); solo(java.lang.Object); solo(java.lang.String); toString(); wait(); wait(long); wait(long, int)
        table static s(java.lang.Integer); s(java.lang.Object)
        table ctor SubOv()
        """;

        private Canonical() {
        }
    }

    /// The token of [SubOv].
    public static final OpenClassToken<SubOv> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [SubOv#SubOv()].
    public static final CtorRef0<SubOv> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [SubOv#solo(String)].
    public static final MethodRef1<SubOv, String, String> solo_String = UnsafeFacts.method(TOKEN, "solo", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private SubOv_() {
    }

    /// Starts the proof that a class being declared extends [SubOv]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, SubOv> extend(RefToken<Self> self) {
        return new SubOv_Heritage().extend(TOKEN, self);
    }
}
