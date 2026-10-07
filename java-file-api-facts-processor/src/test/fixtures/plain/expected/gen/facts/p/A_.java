package gen.facts.p;

import gen.facts.p.A_.Canonical;
import gen.facts.p.A_.Data;
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
import me.supcheg.javafile.facts.MethodRef0;
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
import p.A;
import p.B;

/// The full metamodel of [A], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [A] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [A] by the proof its metamodel starts, with the stages of `A_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = A.class, fingerprint = "61e7f4854eaa224be44546aae8cc29d93cc5e4009c68f537dd2fcd5da311cbc3", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class A_ {
    /// The shape of [A] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [A] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.A_"), "61e7f4854eaa224be44546aae8cc29d93cc5e4009c68f537dd2fcd5da311cbc3", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.A"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("b"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("self"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("A"))), false, new A_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [A], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [A].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.A open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable b() -> p.B throws -
        member method public overridable self() -> p.A throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.A b() -> p.B throws - erased () overrides -
        inherit method public concrete p.A self() -> p.A throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete b(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); self(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor A()
        """;

        private Canonical() {
        }
    }

    /// The token of [A].
    public static final OpenClassToken<A> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [A#A()].
    public static final CtorRef0<A> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [A#b()].
    public static final MethodRef0<A, B> b = UnsafeFacts.method(TOKEN, "b", UnsafeFacts.<B>openClassToken(B_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [A#self()].
    public static final MethodRef0<A, A> self = UnsafeFacts.method(TOKEN, "self", TOKEN, MemberTraits.OVERRIDABLE);

    private A_() {
    }

    /// Starts the proof that a class being declared extends [A]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, A> extend(RefToken<Self> self) {
        return new A_Heritage().extend(TOKEN, self);
    }
}
