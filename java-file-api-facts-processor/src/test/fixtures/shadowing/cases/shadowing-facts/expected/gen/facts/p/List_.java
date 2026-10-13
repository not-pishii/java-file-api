package gen.facts.p;

import gen.facts.p.List_.Canonical;
import gen.facts.p.List_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.List;

/// The full metamodel of [List], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [List] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [List] by the proof its metamodel starts, with the stages of `List_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = List.class, fingerprint = "68c9bb3b9393dd5ab211a13f9b474abd257f131550f031dade241d5f8cb6309a", complete = true, format = 14)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.List_"), "68c9bb3b9393dd5ab211a13f9b474abd257f131550f031dade241d5f8cb6309a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.List"), java.util.List.of(), java.util.List.of(ClassDesc.of("java.lang.Object")), java.util.List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("canonical", Param.fixed(ClassDesc.of("p.Data"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("load"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("List"))), false, new List_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [List], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [List].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.List open-class sealed=no nesting=top
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
        inherit ctor List() public () throws -
        inherit field public p.List Supertypes
        inherit field public p.List java
        inherit field public p.List p
        inherit method canonical(p.Data) erased (p.Data) overrides - by class public concrete p.List (p.Data) -> p.Canonical throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method load() erased () overrides - by class public concrete p.List () -> p.Data throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
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
    public static final CtorRef0<List> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [List#canonical(p.Data)].
    public static final MethodRef1<List, p.Canonical, p.Data> canonical_Data = UnsafeInvocables.method(TOKEN, "canonical", UnsafeFacts.<p.Canonical>openClassToken(Canonical_.Data.SHAPE), UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [List#load()].
    public static final MethodRef0<List, p.Data> load = UnsafeInvocables.method(TOKEN, "load", UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private List_() {
    }

    /// Starts the proof that a class being declared extends [List]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, List> extend(RefToken<Self> self) {
        return new List_Heritage().extend(TOKEN, self);
    }
}
