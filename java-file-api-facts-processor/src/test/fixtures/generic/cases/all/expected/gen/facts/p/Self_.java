package gen.facts.p;

import gen.facts.p.Self_.Canonical;
import gen.facts.p.Self_.Data;
import gen.facts.p.Self_Heritage.Extend;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Self;

/// The full metamodel of [Self], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Self] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Self] by the proof its metamodel starts, with the stages of `Self_Heritage`.
///
/// @param <S> a type argument of [Self]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Self.class, fingerprint = "3e8380b7c699e4a8607cc288c4cd9a058f1c100f98ad6541e96a3283fa92a486", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Self_<S extends Self<S>> {
    /// The shape of [Self] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Self] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Self_"), "3e8380b7c699e4a8607cc288c4cd9a058f1c100f98ad6541e96a3283fa92a486", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Self"), List.of(new TypeParam("S", List.of(new ParameterizedTypeRef(ClassDesc.of("p.Self"), List.of(Types.exact(Types.typeVar("S"))))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("S")), List.of()), new MethodTableTemplate(Set.of(Signature.of("me")), Set.of(Signature.of("clone"), Signature.of("compare", Param.var(0)), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Self"))), false, new Self_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Self], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Self].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Self abstract-class sealed=no nesting=top
        tparams #0 extends p.Self<#0>
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public abstract me() -> #0 throws -
        member method public overridable compare(#0) -> int throws -
        inherit ctor Self() public () throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method compare(#0) erased (p.Self) overrides - by class public concrete p.Self (#0) -> int throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method me() erased () overrides - by class public abstract p.Self () -> #0 throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract me()
        table concrete clone(); compare(#0); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Self()
        """;

        private Canonical() {
        }
    }

    /// The token of [Self] with a wildcard for every type argument.
    public static final AbstractClassToken<Self<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Self] with the type arguments of this metamodel.
    public final AbstractClassToken<Self<S>> token;

    /// The fact of [Self#Self()].
    public final SuperCtorRef0<Self<S>> super_;

    /// The fact of [Self#compare(Self)].
    public final MethodRef1<Self<S>, Int, S> compare_S;

    /// The fact of [Self#me()].
    public final MethodRef0<Self<S>, S> me;

    /// The metamodel of [Self] with the type arguments the tokens give.
    ///
    /// @param s the token of the type argument `S`
    public Self_(RefToken<S> s) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(s));
        this.super_ = UnsafeInvocables.superCtor(token, MemberTraits.FINAL);
        this.compare_S = UnsafeInvocables.method(token, "compare", PrimitiveToken.INT, UnsafeFacts.param(s, Param.var(0)), MemberTraits.OVERRIDABLE);
        this.me = UnsafeInvocables.method(token, "me", s, MemberTraits.ABSTRACT);
    }

    /// Starts the proof that a class being declared extends [Self]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self_> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self_> Extend<S, Self_> extend(RefToken<Self_> self) {
        return new Self_Heritage().extend(this.token, self);
    }
}
