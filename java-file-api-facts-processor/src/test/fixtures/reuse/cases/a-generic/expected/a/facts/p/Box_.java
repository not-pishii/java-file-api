package a.facts.p;

import a.facts.p.Box_.Canonical;
import a.facts.p.Box_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Box;

/// The full metamodel of [Box], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Box] inherits has its fact in the metamodel of the supertype that declares it: [a.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Box]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Box.class, fingerprint = "f095275cc019e8593cdc942b80ba08a03d72d4145e33b2f4cac7bc8873a24d44", complete = true, format = 10)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Box_<T extends Comparable<T>> {
    /// The shape of [Box] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Box] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("a.facts.p.Box_"), "f095275cc019e8593cdc942b80ba08a03d72d4145e33b2f4cac7bc8873a24d44", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Box"), List.of(new TypeParam("T", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("T"))))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.var(0, 1)), Signature.of("as", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Box", Param.var(0)))), false, new Box_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Box], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Box].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Box open-class sealed=no
        tparams #0 extends java.lang.Comparable<#0>
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (#0) throws -
        member field public instance mutable #0 value
        member method public final all(#0[]) -> #0[] throws -
        member method public overridable <^0> as(^0) -> ^0 throws -
        inherit ctor public (#0) throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Box <^0> as(^0) -> ^0 throws - erased (java.lang.Object) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public final p.Box all(#0...) -> #0[] throws - erased (java.lang.Comparable[]) overrides -
        table abstract -
        table concrete all(#0[]); as(java.lang.Object); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Box(#0)
        """;

        private Canonical() {
        }
    }

    /// The token of [Box] with a wildcard for every type argument.
    public static final OpenClassToken<Box<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Box] with the type arguments of this metamodel.
    public final OpenClassToken<Box<T>> token;

    /// The fact of [Box#value].
    public final MutableFieldRef<Box<T>, T> value;

    /// The fact of [Box#Box(Comparable)].
    public final CtorRef1<Box<T>, T> new_T;

    /// The fact of [Box#all(Comparable\[\])].
    public final MethodRef1<Box<T>, T[], T[]> all_TArray;

    private final RefToken<T> t;

    /// The metamodel of [Box] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Box_(RefToken<T> t) {
        this.t = t;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.value = UnsafeFacts.mutableField(token, "value", t);
        this.new_T = UnsafeFacts.ctor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL);
        this.all_TArray = UnsafeFacts.method(token, "all", ArrayToken.of(t), UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.FINAL);
    }

    /// The fact of [Box#as(Object)], for the type arguments the tokens give.
    ///
    /// @param <R> a type argument of the method
    /// @param r the token of the type argument `R`
    /// @return the fact
    public <R> MethodRef1<Box<T>, R, R> as_R(RefToken<R> r) {
        return UnsafeFacts.method(token, "as", r, UnsafeFacts.param(r, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(r));
    }
}
