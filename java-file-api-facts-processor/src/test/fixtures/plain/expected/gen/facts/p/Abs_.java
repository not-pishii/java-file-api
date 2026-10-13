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
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.SuperCtorRef1;
import me.supcheg.javafile.facts.SuperCtorRef2;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Abs;

/// The full metamodel of [Abs], which `@Facts` asks for: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// A member [Abs] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Abs] by the proof its metamodel starts, with the stages of `Abs_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Abs.class, fingerprint = "42fda27dc3a11f5964e55b57de667140a6e01da8e806b1d1f4c106ccf9c49241", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Abs_ {
    /// The shape of [Abs] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Abs] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Abs_"), "42fda27dc3a11f5964e55b57de667140a6e01da8e806b1d1f4c106ccf9c49241", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Abs"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("clone"), Signature.of("done"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("make")), Set.of(Signature.of("Abs"), Signature.of("Abs", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("Abs", Param.fixed(ConstantDescs.CD_long)))), false, new Abs_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Abs], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Abs].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Abs abstract-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor protected (long) throws -
        member ctor public () throws -
        member ctor public (java.lang.String, int) throws -
        member method public abstract run() -> void throws -
        member method public overridable done() -> void throws -
        member method public static make() -> p.Abs throws -
        inherit ctor Abs() public () throws -
        inherit ctor Abs(java.lang.String, int) public (java.lang.String, int) throws -
        inherit ctor Abs(long) protected (long) throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method done() erased () overrides - by class public concrete p.Abs () -> void throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method make() erased () overrides - by class public static p.Abs () -> p.Abs throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method run() erased () overrides - by class public abstract p.Abs () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract run()
        table concrete clone(); done(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static make()
        table ctor Abs(); Abs(java.lang.String, int); Abs(long)
        """;

        private Canonical() {
        }
    }

    /// The token of [Abs].
    public static final AbstractClassToken<Abs> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Abs#Abs()].
    public static final SuperCtorRef0<Abs> super_ = UnsafeInvocables.superCtor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Abs#Abs(String, int)].
    public static final SuperCtorRef2<Abs, String, Int> super_String_int = UnsafeInvocables.superCtor(TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Abs#Abs(long)], which is `protected`: a subclass alone uses it.
    public static final SuperCtorRef1<Abs, Long> super_long = UnsafeInvocables.superCtor(TOKEN, PrimitiveToken.LONG, MemberTraits.FINAL.with(Access.PROTECTED));

    /// The fact of [Abs#done()].
    public static final VoidMethodRef0<Abs> done = UnsafeInvocables.voidMethod(TOKEN, "done", MemberTraits.OVERRIDABLE);

    /// The fact of [Abs#make()].
    public static final StaticMethodRef0<Abs> make = UnsafeInvocables.staticMethod(TOKEN, "make", TOKEN, MemberTraits.FINAL);

    /// The fact of [Abs#run()].
    public static final VoidMethodRef0<Abs> run = UnsafeInvocables.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    private Abs_() {
    }

    /// Starts the proof that a class being declared extends [Abs]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Extend<Self> extend(RefToken<Self> self) {
        return new Abs_Heritage().extend(TOKEN, self);
    }
}
