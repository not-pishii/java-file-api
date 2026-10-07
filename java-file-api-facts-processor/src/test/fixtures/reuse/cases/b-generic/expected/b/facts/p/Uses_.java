package b.facts.p;

import b.facts.p.Uses_.Canonical;
import b.facts.p.Uses_.Data;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Box;
import p.Uses;

/// The full metamodel of [Uses], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Uses] inherits has its fact in the metamodel of the supertype that declares it: [a.facts.java.lang.Object_].
///
/// A generated class extends or implements [Uses] by the proof `Uses_Heritage` starts.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Uses.class, fingerprint = "c82eb80dd500738d1bf97d8261df8019706b135b5f69d89f362acdff65e16029", complete = true, format = 11)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Uses_ {
    /// The shape of [Uses] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Uses] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("b.facts.p.Uses_"), "c82eb80dd500738d1bf97d8261df8019706b135b5f69d89f362acdff65e16029", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Uses"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("any", Param.fixed(ClassDesc.of("p.Box"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("strings"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Uses"))), false, new Uses_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Uses], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Uses].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Uses open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable any(p.Box<java.lang.Integer>) -> p.Box<?> throws -
        member method public overridable strings() -> p.Box<java.lang.String> throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Uses any(p.Box<java.lang.Integer>) -> p.Box<?> throws - erased (p.Box) overrides -
        inherit method public concrete p.Uses strings() -> p.Box<java.lang.String> throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete any(p.Box); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); strings(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Uses()
        """;

        private Canonical() {
        }
    }

    /// The token of [Uses].
    public static final OpenClassToken<Uses> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Uses#Uses()].
    public static final CtorRef0<Uses> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Uses#any(Box)].
    public static final MethodRef1<Uses, Box<?>, Box<Integer>> any_Box = UnsafeFacts.method(TOKEN, "any", UnsafeFacts.<Box<?>>openClassToken(a.facts.p.Box_.Data.SHAPE, TokenArg.unbounded()), UnsafeFacts.<Box<Integer>>openClassToken(a.facts.p.Box_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<Integer>finalClassToken(b.facts.java.lang.Integer_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    /// The fact of [Uses#strings()].
    public static final MethodRef0<Uses, Box<String>> strings = UnsafeFacts.method(TOKEN, "strings", UnsafeFacts.<Box<String>>openClassToken(a.facts.p.Box_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(a.facts.java.lang.String_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    private Uses_() {
    }
}
