package gen.facts.p;

import gen.facts.p.List_.Canonical;
import gen.facts.p.List_.Data;
import gen.facts.p.List_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.List;

/// The full metamodel of [List], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [List] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = List.class, fingerprint = "bbea34df68170c3a5ba25d1978a56332b67834b04bf056820f5f258e98fdd3d8", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class List_ {
    /// The shape of [List] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [List] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.List_"), "bbea34df68170c3a5ba25d1978a56332b67834b04bf056820f5f258e98fdd3d8", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.List"), java.util.List.of(), java.util.List.of(ClassDesc.of("java.lang.Object")), java.util.List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("canonical", Param.fixed(ClassDesc.of("p.Data"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("load"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("List"))), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [List] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [List] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(java.util.List.of(m0(), m1(), m2(), m3(), m4(), m5(), m6(), m7(), m8(), m9(), m10(), m11(), m12()), java.util.List.of(c0()));

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.List"), Signature.of("canonical", Param.fixed(ClassDesc.of("p.Data"))), java.util.List.of(), java.util.List.of(Types.of(ClassDesc.of("p.Data"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Canonical"))), java.util.List.of(), Set.of(java.util.List.of(ClassDesc.of("p.Data"))), java.util.List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("clone"), java.util.List.of(), java.util.List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.Object"))), java.util.List.of(Types.of(ClassDesc.of("java.lang.CloneNotSupportedException"))), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), java.util.List.of(), java.util.List.of(Types.of(ClassDesc.of("java.lang.Object"))), Arity.FIXED, new Of(PrimitiveTypeRef.BOOLEAN), java.util.List.of(), Set.of(java.util.List.of(ClassDesc.of("java.lang.Object"))), java.util.List.of());
            }

            private static Method m3() {
                return new Method(Visibility.PROTECTED, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("finalize"), java.util.List.of(), java.util.List.of(), Arity.FIXED, Result.NOTHING, java.util.List.of(Types.of(ClassDesc.of("java.lang.Throwable"))), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m4() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("getClass"), java.util.List.of(), java.util.List.of(), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), java.util.List.of(Types.unbounded()))), java.util.List.of(), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m5() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("hashCode"), java.util.List.of(), java.util.List.of(), Arity.FIXED, new Of(PrimitiveTypeRef.INT), java.util.List.of(), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m6() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("p.List"), Signature.of("load"), java.util.List.of(), java.util.List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.Data"))), java.util.List.of(), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m7() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notify"), java.util.List.of(), java.util.List.of(), Arity.FIXED, Result.NOTHING, java.util.List.of(), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m8() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("notifyAll"), java.util.List.of(), java.util.List.of(), Arity.FIXED, Result.NOTHING, java.util.List.of(), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m9() {
                return new Method(Visibility.PUBLIC, Dispatch.CONCRETE, ClassDesc.of("java.lang.Object"), Signature.of("toString"), java.util.List.of(), java.util.List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), java.util.List.of(), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m10() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait"), java.util.List.of(), java.util.List.of(), Arity.FIXED, Result.NOTHING, java.util.List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(java.util.List.of()), java.util.List.of());
            }

            private static Method m11() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), java.util.List.of(), java.util.List.of(PrimitiveTypeRef.LONG), Arity.FIXED, Result.NOTHING, java.util.List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(java.util.List.of(ConstantDescs.CD_long)), java.util.List.of());
            }

            private static Method m12() {
                return new Method(Visibility.PUBLIC, Dispatch.FINAL, ClassDesc.of("java.lang.Object"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), java.util.List.of(), java.util.List.of(PrimitiveTypeRef.LONG, PrimitiveTypeRef.INT), Arity.FIXED, Result.NOTHING, java.util.List.of(Types.of(ClassDesc.of("java.lang.InterruptedException"))), Set.of(java.util.List.of(ConstantDescs.CD_long, ConstantDescs.CD_int)), java.util.List.of());
            }

            private static Constructor c0() {
                return new Constructor(Visibility.PUBLIC, Signature.of("List"), java.util.List.of(), java.util.List.of(), Arity.FIXED, java.util.List.of());
            }
        }
    }

    /// The canonical form of [List], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [List].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.List open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable int Supertypes
        member field public instance mutable int java
        member field public instance mutable int p
        member method public overridable canonical(p.Data) -> p.Canonical throws -
        member method public overridable load() -> p.Data throws -
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.List canonical(p.Data) -> p.Canonical throws - erased (p.Data) overrides -
        inherit method public concrete p.List load() -> p.Data throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete canonical(p.Data); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); load(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor List()
        """;

        private Canonical() {
        }
    }

    /// The token of [List].
    public static final OpenClassToken<List> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [List#Supertypes].
    public static final MutableFieldRef<List, Int> Supertypes_ = UnsafeFacts.mutableField(TOKEN, "Supertypes", PrimitiveToken.INT);

    /// The fact of [List#java].
    public static final MutableFieldRef<List, Int> java_ = UnsafeFacts.mutableField(TOKEN, "java", PrimitiveToken.INT);

    /// The fact of [List#p].
    public static final MutableFieldRef<List, Int> p_ = UnsafeFacts.mutableField(TOKEN, "p", PrimitiveToken.INT);

    /// The fact of [List#List()].
    public static final CtorRef0<List> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [List#canonical(p.Data)].
    public static final MethodRef1<List, p.Canonical, p.Data> canonical_Data = UnsafeFacts.method(TOKEN, "canonical", UnsafeFacts.<p.Canonical>openClassToken(Canonical_.Data.SHAPE), UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [List#load()].
    public static final MethodRef0<List, p.Data> load = UnsafeFacts.method(TOKEN, "load", UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private List_() {
    }
}
