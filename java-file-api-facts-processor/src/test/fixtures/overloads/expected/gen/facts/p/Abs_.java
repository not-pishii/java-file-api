package gen.facts.p;

import gen.facts.p.Abs_.Canonical;
import gen.facts.p.Abs_.Data;
import gen.facts.p.Abs_Heritage.Extend;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Abs;

/// The full metamodel of [Abs], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Abs] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Abs] by the proof its metamodel starts, with the stages of `Abs_Heritage`.
///
/// @param <T> a type argument of [Abs]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Abs.class, fingerprint = "ec7c626d24acb6407e75666cf16d9aa090e9e0ad4a55a0812606b9229073650d", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Abs_<T> {
    /// The shape of [Abs] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Abs] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Abs_"), "ec7c626d24acb6407e75666cf16d9aa090e9e0ad4a55a0812606b9229073650d", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Abs"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("m", Param.var(0))), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Abs"))), false, new Abs_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Abs], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Abs].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Abs abstract-class sealed=no
        tparams #0
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public abstract m(#0) -> java.lang.String throws -
        member method public overridable m(java.lang.String) -> java.lang.String throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public abstract p.Abs m(#0) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Abs m(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract m(#0)
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(java.lang.String); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Abs()
        """;

        private Canonical() {
        }
    }

    /// The token of [Abs] with a wildcard for every type argument.
    public static final AbstractClassToken<Abs<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Abs] with the type arguments of this metamodel.
    public final AbstractClassToken<Abs<T>> token;

    /// The fact of [Abs#Abs()].
    public final SuperCtorRef0<Abs<T>> super_;

    /// The fact of [Abs#m(String)].
    public final MethodRef1<Abs<T>, String, String> m_String;

    /// The fact of [Abs#m(Object)].
    public final MethodRef1<Abs<T>, String, T> m_T;

    /// The metamodel of [Abs] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Abs_(RefToken<T> t) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(t));
        this.super_ = UnsafeFacts.superCtor(token, MemberTraits.FINAL);
        this.m_String = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.m_T = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT);
    }

    /// Starts the proof that a class being declared extends [Abs]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Extend<T, Self> extend(RefToken<Self> self) {
        return new Abs_Heritage().extend(this.token, self);
    }
}
