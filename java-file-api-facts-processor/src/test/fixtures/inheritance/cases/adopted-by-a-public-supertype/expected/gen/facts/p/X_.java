package gen.facts.p;

import gen.facts.p.X_.Canonical;
import gen.facts.p.X_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.X;

/// The full metamodel of [X], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [X] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [Mid_].
///
/// `p.Own`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [X] by the proof `X_Heritage` starts.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = X.class, fingerprint = "a25a933d097654448bbd6fc0ecb1afdd90b2bca15156b4b0000696e44af606d2", complete = true, format = 11)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class X_ {
    /// The shape of [X] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [X] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.X_"), "a25a933d097654448bbd6fc0ecb1afdd90b2bca15156b4b0000696e44af606d2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.X"), List.of(), List.of(ClassDesc.of("p.Mid"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.HiddenI"), ClassDesc.of("p.Own")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("mid"), Signature.of("more"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("own"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("X"))), false, new X_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [X], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [X].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.X open-class sealed=no
        tparams -
        superclasses p.Mid; java.lang.Object
        interfaces p.HiddenI; p.Own
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant int OWN = 2
        member method public overridable more() -> java.lang.String throws -
        member method public overridable own() -> void throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Mid mid() -> void throws - erased () overrides -
        inherit method public concrete p.X more() -> java.lang.String throws - erased () overrides p.HiddenI
        inherit method public default p.HiddenI run() -> java.lang.String throws - erased () overrides -
        inherit method public default p.Own own() -> void throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); mid(); more(); notify(); notifyAll(); own(); run(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor X()
        """;

        private Canonical() {
        }
    }

    /// The token of [X].
    public static final OpenClassToken<X> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [X#OWN], declared in `p.Own`, which is not `public`.
    public static final StaticFieldRef<Int> OWN = UnsafeFacts.constantField(TOKEN, "OWN", PrimitiveToken.INT, 2);

    /// The fact of [X#X()].
    public static final CtorRef0<X> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [X#more()].
    public static final MethodRef0<X, String> more = UnsafeFacts.method(TOKEN, "more", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [X#own()], declared in `p.Own`, which is not `public`.
    public static final VoidMethodRef0<X> own = UnsafeFacts.voidMethod(TOKEN, "own", MemberTraits.OVERRIDABLE);

    private X_() {
    }
}
