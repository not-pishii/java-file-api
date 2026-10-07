package gen.facts.p;

import gen.facts.p.Taken_Abstract_.Canonical;
import gen.facts.p.Taken_Abstract_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Taken.Abstract;

/// The full metamodel of [Abstract], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Abstract] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Abstract] by the proof `Taken_Abstract_Heritage` starts.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Abstract.class, fingerprint = "6c69fab365611ca425bfde461aabafb5b7a4e9752d7dfa3ff0ee4cdfaee6534a", complete = true, format = 11)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Taken_Abstract_ {
    /// The shape of [Abstract] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Abstract] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Taken_Abstract_"), "6c69fab365611ca425bfde461aabafb5b7a4e9752d7dfa3ff0ee4cdfaee6534a", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Taken$Abstract"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("Method")), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Taken$Abstract"))), false, new Taken_Abstract_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Abstract], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Abstract].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Taken$Abstract abstract-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable int Complete
        member field public instance mutable int Implementation0
        member method public abstract Method() -> int throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public abstract p.Taken$Abstract Method() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract Method()
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Taken$Abstract()
        """;

        private Canonical() {
        }
    }

    /// The token of [Abstract].
    public static final AbstractClassToken<Abstract> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Abstract#Complete].
    public static final MutableFieldRef<Abstract, Int> Complete = UnsafeFacts.mutableField(TOKEN, "Complete", PrimitiveToken.INT);

    /// The fact of [Abstract#Implementation0].
    public static final MutableFieldRef<Abstract, Int> Implementation0 = UnsafeFacts.mutableField(TOKEN, "Implementation0", PrimitiveToken.INT);

    /// The fact of [Abstract#Abstract()].
    public static final SuperCtorRef0<Abstract> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Abstract#Method()].
    public static final MethodRef0<Abstract, Int> Method = UnsafeFacts.method(TOKEN, "Method", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    private Taken_Abstract_() {
    }
}
