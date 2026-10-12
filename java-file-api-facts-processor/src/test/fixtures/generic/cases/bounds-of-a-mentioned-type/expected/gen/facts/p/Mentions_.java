package gen.facts.p;

import gen.facts.p.Mentions_.Canonical;
import gen.facts.p.Mentions_.Data;
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
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.ByDollar;
import p.ByHidden;
import p.ByMarker;
import p.Mentions;

/// The full metamodel of [Mentions], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Mentions] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Mentions] by the proof its metamodel starts, with the stages of `Mentions_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mentions.class, fingerprint = "36189af1e3f51d2fc4255f8f63f42e1fd42cc171cb697a5a8ae9f0a380d9f79f", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Mentions_ {
    /// The shape of [Mentions] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Mentions] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mentions_"), "36189af1e3f51d2fc4255f8f63f42e1fd42cc171cb697a5a8ae9f0a380d9f79f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mentions"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("dollar"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("marker"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Mentions"))), false, new Mentions_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Mentions], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Mentions].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Mentions open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable dollar() -> p.ByDollar<?> throws -
        member method public overridable hidden() -> p.ByHidden<?> throws -
        member method public overridable marker() -> p.ByMarker<?> throws -
        inherit ctor Mentions() public () throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method dollar() erased () overrides - by class public concrete p.Mentions () -> p.ByDollar<?> throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method hidden() erased () overrides - by class public concrete p.Mentions () -> p.ByHidden<?> throws -
        inherit method marker() erased () overrides - by class public concrete p.Mentions () -> p.ByMarker<?> throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); dollar(); equals(java.lang.Object); finalize(); getClass(); hashCode(); hidden(); marker(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Mentions()
        """;

        private Canonical() {
        }
    }

    /// The token of [Mentions].
    public static final OpenClassToken<Mentions> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Mentions#Mentions()].
    public static final CtorRef0<Mentions> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Mentions#dollar()].
    public static final MethodRef0<Mentions, ByDollar<?>> dollar = UnsafeFacts.method(TOKEN, "dollar", UnsafeFacts.<ByDollar<?>>openClassToken(ByDollar_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    /// The fact of [Mentions#hidden()].
    public static final MethodRef0<Mentions, ByHidden<?>> hidden = UnsafeFacts.method(TOKEN, "hidden", UnsafeFacts.<ByHidden<?>>openClassToken(ByHidden_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    /// The fact of [Mentions#marker()].
    public static final MethodRef0<Mentions, ByMarker<?>> marker = UnsafeFacts.method(TOKEN, "marker", UnsafeFacts.<ByMarker<?>>openClassToken(ByMarker_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    private Mentions_() {
    }

    /// Starts the proof that a class being declared extends [Mentions]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Mentions> extend(RefToken<Self> self) {
        return new Mentions_Heritage().extend(TOKEN, self);
    }
}
