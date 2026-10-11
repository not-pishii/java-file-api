package gen.facts.p;

import gen.facts.p.Ov_.Canonical;
import gen.facts.p.Ov_.Data;
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
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Ov;

/// The full metamodel of [Ov], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Ov] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Ov] by the proof its metamodel starts, with the stages of `Ov_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ov.class, fingerprint = "a2c68a0ed825cc9757049dda2437b7f8865a935ca53e221aba0f63e7564f85e1", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Ov_ {
    /// The shape of [Ov] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Ov] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ov_"), "a2c68a0ed825cc9757049dda2437b7f8865a935ca53e221aba0f63e7564f85e1", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ov"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("c", Param.fixed(ClassDesc.of("java.lang.Comparable"))), Signature.of("c", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("solo", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("Ov"))), false, new Ov_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Ov], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Ov].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Ov open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable <^0 extends java.lang.Comparable<^0>> c(^0) -> java.lang.String throws -
        member method public overridable <^0> m(^0) -> java.lang.String throws -
        member method public overridable <^0> solo(^0) -> java.lang.String throws -
        member method public overridable c(java.lang.String) -> java.lang.String throws -
        member method public overridable m(java.lang.String) -> java.lang.String throws -
        member method public static <^0> s(^0) -> java.lang.String throws -
        member method public static s(java.lang.Integer) -> java.lang.String throws -
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
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Ov <^0> s(^0) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public static p.Ov s(java.lang.Integer) -> java.lang.String throws - erased (java.lang.Integer) overrides -
        table abstract -
        table concrete c(java.lang.Comparable); c(java.lang.String); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(java.lang.Object); m(java.lang.String); notify(); notifyAll(); solo(java.lang.Object); toString(); wait(); wait(long); wait(long, int)
        table static s(java.lang.Integer); s(java.lang.Object)
        table ctor Ov()
        """;

        private Canonical() {
        }
    }

    /// The token of [Ov].
    public static final OpenClassToken<Ov> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Ov#Ov()].
    public static final CtorRef0<Ov> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Ov#c(String)].
    public static final MethodRef1<Ov, String, String> c_String = UnsafeFacts.method(TOKEN, "c", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#m(String)].
    public static final MethodRef1<Ov, String, String> m_String = UnsafeFacts.method(TOKEN, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#s(Integer)].
    public static final StaticMethodRef1<String, Integer> s_Integer = UnsafeFacts.staticMethod(TOKEN, "s", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Integer>finalClassToken(gen.facts.java.lang.Integer_.Data.SHAPE), MemberTraits.FINAL);

    private Ov_() {
    }

    /// The fact of [Ov#c(Comparable)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T extends Comparable<T>> MethodRef1<Ov, String, T> c_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "c", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Comparable"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Ov#m(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef1<Ov, String, T> m_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Ov#s(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> StaticMethodRef1<String, T> s_T(RefToken<T> t) {
        return UnsafeFacts.staticMethod(TOKEN, "s", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(t));
    }

    /// The fact of [Ov#solo(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef1<Ov, String, T> solo_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "solo", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// Starts the proof that a class being declared extends [Ov]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Ov> extend(RefToken<Self> self) {
        return new Ov_Heritage().extend(TOKEN, self);
    }
}
