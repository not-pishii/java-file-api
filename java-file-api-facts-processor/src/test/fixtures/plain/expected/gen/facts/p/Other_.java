package gen.facts.p;

import gen.facts.p.Other_.Canonical;
import gen.facts.p.Other_.Data;
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
import p.Greeter;
import p.Other;

/// The full metamodel of [Other], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Other] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Other] by the proof its metamodel starts, with the stages of `Other_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Other.class, fingerprint = "de999698d03588d9c90201f849d56b28ef40303740305cebd1f6612637bee2e2", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Other_ {
    /// The shape of [Other] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Other] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Other_"), "de999698d03588d9c90201f849d56b28ef40303740305cebd1f6612637bee2e2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Other"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("greeter"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other", Param.fixed(ClassDesc.of("p.Greeter"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Other"))), false, new Other_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Other], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Other].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Other open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable greeter() -> p.Greeter throws -
        member method public overridable other(p.Greeter) -> p.Other throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Other greeter() -> p.Greeter throws - erased () overrides -
        inherit method public concrete p.Other other(p.Greeter) -> p.Other throws - erased (p.Greeter) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); greeter(); hashCode(); notify(); notifyAll(); other(p.Greeter); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Other()
        """;

        private Canonical() {
        }
    }

    /// The token of [Other].
    public static final OpenClassToken<Other> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Other#Other()].
    public static final CtorRef0<Other> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Other#greeter()].
    public static final MethodRef0<Other, Greeter> greeter = UnsafeFacts.method(TOKEN, "greeter", UnsafeFacts.<Greeter>openClassToken(Greeter_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Other#other(Greeter)].
    public static final MethodRef1<Other, Other, Greeter> other_Greeter = UnsafeFacts.method(TOKEN, "other", TOKEN, UnsafeFacts.<Greeter>openClassToken(Greeter_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Other_() {
    }

    /// Starts the proof that a class being declared extends [Other]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Other> extend(RefToken<Self> self) {
        return new Other_Heritage().extend(TOKEN, self);
    }
}
