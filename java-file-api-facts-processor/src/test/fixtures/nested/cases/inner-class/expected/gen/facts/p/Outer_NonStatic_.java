package gen.facts.p;

import gen.facts.p.Outer_NonStatic_.Canonical;
import gen.facts.p.Outer_NonStatic_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Outer.NonStatic;

/// The full metamodel of [NonStatic], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [NonStatic] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `constructor NonStatic()`, which is the constructor of an inner class, which needs an enclosing instance
/// - `constructor NonStatic(int)`, which is the constructor of an inner class, which needs an enclosing instance
///
/// No generated class extends or implements [NonStatic]: it is an inner class, which is extended with an instance of the class it is nested in.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = NonStatic.class, fingerprint = "3725d68a8849764eea14f187096d334dc8d5aefc9a1a572db30aa3af8e01373d", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Outer_NonStatic_ {
    /// The shape of [NonStatic] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [NonStatic] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Outer_NonStatic_"), "3725d68a8849764eea14f187096d334dc8d5aefc9a1a572db30aa3af8e01373d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Outer$NonStatic"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Outer$NonStatic"), Signature.of("Outer$NonStatic", Param.fixed(ConstantDescs.CD_int)))), false, new Outer_NonStatic_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [NonStatic], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [NonStatic].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Outer$NonStatic open-class sealed=no nesting=inner
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member ctor public (int) throws -
        member method public overridable x() -> int throws -
        inherit ctor Outer$NonStatic() public () throws -
        inherit ctor Outer$NonStatic(int) public (int) throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        inherit method x() erased () overrides - by class public concrete p.Outer$NonStatic () -> int throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()
        table static -
        table ctor Outer$NonStatic(); Outer$NonStatic(int)
        """;

        private Canonical() {
        }
    }

    /// The token of [NonStatic].
    public static final OpenClassToken<NonStatic> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [NonStatic#x()].
    public static final MethodRef0<NonStatic, Int> x = UnsafeInvocables.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private Outer_NonStatic_() {
    }
}
