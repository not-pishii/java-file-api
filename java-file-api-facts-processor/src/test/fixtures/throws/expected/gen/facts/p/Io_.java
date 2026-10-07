package gen.facts.p;

import gen.facts.p.Io_.Canonical;
import gen.facts.p.Io_.Data;
import gen.facts.p.Io_.Data.Inherited;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Failure;
import p.Io;

/// The full metamodel of [Io], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Io] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method hidden()`, which mentions types that are not public: p.Secret
/// - `constructor Io(java.lang.String)`, which mentions types that are not public: p.Secret
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Io.class, fingerprint = "4c0f4e0ced30c471aea93e901bcdc70e32de68db7fb74e8e7b3da05f9009446b", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Io_ {
    /// The shape of [Io] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Io] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Io_"), "4c0f4e0ced30c471aea93e901bcdc70e32de68db7fb74e8e7b3da05f9009446b", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Io"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("custom"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("generic"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("locked"), Signature.of("multi"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("read"), Signature.of("toString"), Signature.of("unchecked"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("util")), Set.of(Signature.of("Io"), Signature.of("Io", Param.fixed(ConstantDescs.CD_int)), Signature.of("Io", Param.fixed(ClassDesc.of("java.lang.String"))))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Io] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Io] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12(), m13(), m14(), m15(), m16(), m17(), m18()), List.of(c0(), c1(), c2()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(List.of()), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Io"), Signature.of("custom"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("p.Failure"))), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(List.of()), List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Io"), Signature.of("generic"), List.of(new TypeParam("X", List.of(Types.of(ClassDesc.of("java.lang.Throwable"))))), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.typeVar("X")), Set.of(List.of()), List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), List.of(), List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Io"), Signature.of("hidden"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("p.Secret"))), Set.of(List.of()), List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("p.Io"), Signature.of("locked"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("p.Failure"))), Set.of(List.of()), List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Io"), Signature.of("multi"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.io.IOException")), Types.of(ClassDesc.of("java.lang.InterruptedException")), Types.of(ClassDesc.of("java.lang.IllegalStateException"))), Set.of(List.of()), List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Io"), Signature.of("read"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.io.IOException"))), Set.of(List.of()), List.of());
            }

            private static Method m13() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of()), List.of());
            }

            private static Method m14() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.Io"), Signature.of("unchecked"), List.of(), List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(Types.of(ClassDesc.of("java.lang.IllegalArgumentException"))), Set.of(List.of()), List.of());
            }

            private static Method m15() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Io"), Signature.of("util"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.Exception"))), Set.of(List.of()), List.of());
            }

            private static Method m16() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of()), List.of());
            }

            private static Method m17() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(), List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long)), List.of());
            }

            private static Method m18() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Io"), List.of(), List.of(), Arity.FIXED, List.of(Types.of(ClassDesc.of("java.io.IOException"))));
            }

            private static Constructor c1() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Io", Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.INT), Arity.FIXED, List.of());
            }

            private static Constructor c2() {
                return new Constructor(Visibility.PUBLIC, Signature.of("Io", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, List.of(Types.of(ClassDesc.of("p.Secret"))));
            }
        }
    }

    /// The canonical form of [Io], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Io].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Io open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws java.io.IOException
        member ctor public (int) throws -
        member method public final locked() -> void throws p.Failure
        member method public overridable <^0 extends java.lang.Throwable> generic() -> void throws ^0
        member method public overridable custom() -> void throws p.Failure
        member method public overridable multi() -> void throws java.io.IOException, java.lang.IllegalStateException, java.lang.InterruptedException
        member method public overridable read() -> void throws java.io.IOException
        member method public overridable unchecked() -> int throws java.lang.IllegalArgumentException
        member method public static util() -> void throws java.lang.Exception
        inherit ctor public () throws java.io.IOException
        inherit ctor public (int) throws -
        inherit ctor public (java.lang.String) throws p.Secret
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Io <^0 extends java.lang.Throwable> generic() -> void throws ^0 erased () overrides -
        inherit method public concrete p.Io custom() -> void throws p.Failure erased () overrides -
        inherit method public concrete p.Io hidden() -> void throws p.Secret erased () overrides -
        inherit method public concrete p.Io multi() -> void throws java.io.IOException, java.lang.IllegalStateException, java.lang.InterruptedException erased () overrides -
        inherit method public concrete p.Io read() -> void throws java.io.IOException erased () overrides -
        inherit method public concrete p.Io unchecked() -> int throws java.lang.IllegalArgumentException erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public final p.Io locked() -> void throws p.Failure erased () overrides -
        inherit method public static p.Io util() -> void throws java.lang.Exception erased () overrides -
        table abstract -
        table concrete clone(); custom(); equals(java.lang.Object); finalize(); generic(); getClass(); hashCode(); hidden(); locked(); multi(); notify(); notifyAll(); read(); toString(); unchecked(); wait(); wait(long); wait(long, int)
        table static util()
        table ctor Io(); Io(int); Io(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Io].
    public static final OpenClassToken<Io> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Io#Io()].
    public static final CtorRef0<Io> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    /// The fact of [Io#Io(int)].
    public static final CtorRef1<Io, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Io#custom()].
    public static final VoidMethodRef0<Io> custom = UnsafeFacts.voidMethod(TOKEN, "custom", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<Failure>openClassToken(Failure_.Data.SHAPE)));

    /// The fact of [Io#locked()].
    public static final VoidMethodRef0<Io> locked = UnsafeFacts.voidMethod(TOKEN, "locked", MemberTraits.FINAL.throwing(UnsafeFacts.<Failure>openClassToken(Failure_.Data.SHAPE)));

    /// The fact of [Io#multi()].
    public static final VoidMethodRef0<Io> multi = UnsafeFacts.voidMethod(TOKEN, "multi", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE), UnsafeFacts.<InterruptedException>openClassToken(gen.facts.java.lang.InterruptedException_.Data.SHAPE), UnsafeFacts.<IllegalStateException>openClassToken(gen.facts.java.lang.IllegalStateException_.Data.SHAPE)));

    /// The fact of [Io#read()].
    public static final VoidMethodRef0<Io> read = UnsafeFacts.voidMethod(TOKEN, "read", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    /// The fact of [Io#unchecked()].
    public static final MethodRef0<Io, Int> unchecked = UnsafeFacts.method(TOKEN, "unchecked", PrimitiveToken.INT, MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IllegalArgumentException>openClassToken(gen.facts.java.lang.IllegalArgumentException_.Data.SHAPE)));

    /// The fact of [Io#util()].
    public static final VoidStaticMethodRef0 util = UnsafeFacts.voidStaticMethod(TOKEN, "util", MemberTraits.FINAL.throwing(UnsafeFacts.<Exception>openClassToken(gen.facts.java.lang.Exception_.Data.SHAPE)));

    private Io_() {
    }

    /// The fact of [Io#generic()], for the type arguments the tokens give.
    ///
    /// @param <X> a type argument of the method
    /// @param x the token of the type argument `X`
    /// @return the fact
    public static <X extends Throwable> VoidMethodRef0<Io> generic(RefToken<X> x) {
        return UnsafeFacts.voidMethod(TOKEN, "generic", MemberTraits.OVERRIDABLE.throwing(x).withTypeArgs(x));
    }
}
