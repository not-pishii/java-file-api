package gen.facts.p;

import gen.facts.p.Mid_.Canonical;
import gen.facts.p.Mid_.Data;
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
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Mid;

/// The full metamodel of [Mid]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Mid]: it is here as a supertype of [p.X], whose inherited members are called through this metamodel.
///
/// A member [Mid] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HiddenI`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [Mid] by the proof its metamodel starts, with the stages of `Mid_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mid.class, fingerprint = "0d49580af1354cb883606901d10fd7c365fa15bd79e9b4d38e82597f46c21de1", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Mid_ {
    /// The shape of [Mid] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Mid] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mid_"), "0d49580af1354cb883606901d10fd7c365fa15bd79e9b4d38e82597f46c21de1", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mid"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.HiddenI")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("mid"), Signature.of("more"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Mid"))), false, new Mid_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Mid], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Mid].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Mid open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces p.HiddenI
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant java.lang.String K = "k"
        member method public overridable mid() -> void throws -
        member method public overridable more() -> java.lang.String throws -
        member method public overridable run() -> java.lang.String throws -
        inherit ctor Mid() public () throws -
        inherit field public p.HiddenI K
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method mid() erased () overrides - by class public concrete p.Mid () -> void throws -
        inherit method more() erased () overrides - by interface default p.HiddenI () -> java.lang.String throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method run() erased () overrides - by interface default p.HiddenI () -> java.lang.String throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); mid(); more(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Mid()
        """;

        private Canonical() {
        }
    }

    /// The token of [Mid].
    public static final OpenClassToken<Mid> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Mid#K], declared in `p.HiddenI`, which is not `public`.
    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "k");

    /// The fact of [Mid#Mid()].
    public static final CtorRef0<Mid> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Mid#mid()].
    public static final VoidMethodRef0<Mid> mid = UnsafeInvocables.voidMethod(TOKEN, "mid", MemberTraits.OVERRIDABLE);

    /// The fact of [Mid#more()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Mid, String> more = UnsafeInvocables.method(TOKEN, "more", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Mid#run()], declared in `p.HiddenI`, which is not `public`.
    public static final MethodRef0<Mid, String> run = UnsafeInvocables.method(TOKEN, "run", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Mid_() {
    }

    /// Starts the proof that a class being declared extends [Mid]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Mid> extend(RefToken<Self> self) {
        return new Mid_Heritage().extend(TOKEN, self);
    }
}
