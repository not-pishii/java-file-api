package gen.facts.p;

import gen.facts.p.Up_.Canonical;
import gen.facts.p.Up_.Data;
import gen.facts.p.Up_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Constructor;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Up;

/// The full metamodel of [Up], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Up] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <Gen> a type argument of [Up]
/// @param <Java> a type argument of [Up]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Up.class, fingerprint = "65bc792f054f67edc2783b68a4867ea9435371a12c775f186049f4ef47aba232", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Up_<Gen, Java> {
    /// The shape of [Up] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Up] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Up_"), "65bc792f054f67edc2783b68a4867ea9435371a12c775f186049f4ef47aba232", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Up"), List.of(new TypeParam("Gen", List.of()), new TypeParam("Java", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("Gen"), Types.typeVar("Java")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.var(1)), Signature.of("clone"), Signature.of("each", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Up"))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Up] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Up] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12()), List.of(c0()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Up"), Signature.of("all", Param.var(1)), List.of(), List.of(Types.typeVar("Java")), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.Set"), List.of(Types.exact(Types.typeVar("Gen"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Up"), Signature.of("each", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(new TypeParam("Me", List.of())), List.of(Types.typeVar("Me")), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.Set"), List.of(Types.exact(Types.typeVar("Me"))))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Up"), List.of(), List.of(), Arity.FIXED, List.of());
            }
        }
    }

    /// The canonical form of [Up], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Up].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Up open-class sealed=no
        tparams #0; #1
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable <^0> each(^0) -> java.util.Set<^0> throws -
        member method public overridable all(#1) -> java.util.Set<#0> throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Up <^0> each(^0) -> java.util.Set<^0> throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.Up all(#1) -> java.util.Set<#0> throws - erased (java.lang.Object) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete all(#1); clone(); each(java.lang.Object); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Up()
        """;

        private Canonical() {
        }
    }

    /// The token of [Up] with a wildcard for every type argument.
    public static final OpenClassToken<Up<?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Up] with the type arguments of this metamodel.
    public final OpenClassToken<Up<Gen, Java>> token;

    /// The fact of [Up#Up()].
    public final CtorRef0<Up<Gen, Java>> new_;

    /// The fact of [Up#all(Object)].
    public final MethodRef1<Up<Gen, Java>, Set<Gen>, Java> all_Java;

    private final RefToken<Gen> gen_;

    private final RefToken<Java> java;

    /// The metamodel of [Up] with the type arguments the tokens give.
    ///
    /// @param gen_ the token of the type argument `Gen`
    /// @param java the token of the type argument `Java`
    public Up_(RefToken<Gen> gen_, RefToken<Java> java) {
        this.gen_ = gen_;
        this.java = java;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(gen_), TokenArg.exact(java));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.all_Java = UnsafeFacts.method(token, "all", UnsafeFacts.<Set<Gen>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(gen_)), UnsafeFacts.param(java, Param.var(1)), MemberTraits.OVERRIDABLE);
    }

    /// The fact of [Up#each(Object)], for the type arguments the tokens give.
    ///
    /// @param <Me> a type argument of the method
    /// @param me the token of the type argument `Me`
    /// @return the fact
    public <Me> MethodRef1<Up<Gen, Java>, Set<Me>, Me> each_Me(RefToken<Me> me) {
        return UnsafeFacts.method(token, "each", UnsafeFacts.<Set<Me>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(me)), UnsafeFacts.param(me, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(me));
    }
}
