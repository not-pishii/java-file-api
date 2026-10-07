package gen.facts.p;

import gen.facts.p.PubA_.Canonical;
import gen.facts.p.PubA_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubA;

/// The full metamodel of [PubA]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [PubA]: it is here as a supertype of [p.Impl], whose inherited members are called through this metamodel.
///
/// A member [PubA] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubA.class, fingerprint = "a81589960333de4af38bf599d314d75395817de47d0beedc68e0add8d291b849", complete = true, format = 10)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubA_ {
    /// The shape of [PubA] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubA] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubA_"), "a81589960333de4af38bf599d314d75395817de47d0beedc68e0add8d291b849", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubA"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubA"))), false, new PubA_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubA], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubA].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.PubA open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable java.lang.String k
        member field public static mutable java.lang.String s
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor PubA()
        """;

        private Canonical() {
        }
    }

    /// The token of [PubA].
    public static final OpenClassToken<PubA> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubA#k].
    public static final MutableFieldRef<PubA, String> k = UnsafeFacts.mutableField(TOKEN, "k", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [PubA#s].
    public static final MutableStaticFieldRef<String> s = UnsafeFacts.mutableStaticField(TOKEN, "s", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [PubA#PubA()].
    public static final CtorRef0<PubA> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private PubA_() {
    }
}
