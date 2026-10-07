package gen.facts.p;

import gen.facts.p.PubSame_.Canonical;
import gen.facts.p.PubSame_.Data;
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
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubSame;

/// The full metamodel of [PubSame], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubSame] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HSame`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubSame.class, fingerprint = "65b165413e5e82b527c4bd7bd43ae8bb2df6bae5eefb27536c789ece164918f7", complete = true, format = 10)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubSame_ {
    /// The shape of [PubSame] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubSame] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubSame_"), "65b165413e5e82b527c4bd7bd43ae8bb2df6bae5eefb27536c789ece164918f7", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubSame"), List.of(), List.of(ClassDesc.of("p.HSame"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("tag"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("tag", Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("PubSame"))), false, new PubSame_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubSame], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubSame].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.PubSame open-class sealed=no
        tparams -
        superclasses p.HSame; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant java.lang.String TAG = "field"
        member method public overridable tag() -> java.lang.String throws -
        member method public static tag(int) -> java.lang.String throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.HSame tag() -> java.lang.String throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.HSame tag(int) -> java.lang.String throws - erased (int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); tag(); toString(); wait(); wait(long); wait(long, int)
        table static tag(int)
        table ctor PubSame()
        """;

        private Canonical() {
        }
    }

    /// The token of [PubSame].
    public static final OpenClassToken<PubSame> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubSame#TAG], declared in `p.HSame`, which is not `public`.
    public static final StaticFieldRef<String> TAG = UnsafeFacts.constantField(TOKEN, "TAG", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "field");

    /// The fact of [PubSame#PubSame()].
    public static final CtorRef0<PubSame> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [PubSame#tag()], declared in `p.HSame`, which is not `public`.
    public static final MethodRef0<PubSame, String> tag = UnsafeFacts.method(TOKEN, "tag", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubSame#tag(int)], declared in `p.HSame`, which is not `public`.
    public static final StaticMethodRef1<String, Int> tag_int = UnsafeFacts.staticMethod(TOKEN, "tag", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    private PubSame_() {
    }
}
