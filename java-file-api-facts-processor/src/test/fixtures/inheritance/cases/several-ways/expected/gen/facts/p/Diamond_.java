package gen.facts.p;

import gen.facts.p.Diamond_.Canonical;
import gen.facts.p.Diamond_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Diamond;

/// The full metamodel of [Diamond], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Diamond] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.Root`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [Diamond] by the proof its metamodel starts, with the stages of `Diamond_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Diamond.class, fingerprint = "d567a031f8827c17e8bf4f692b86361186cdff6fdbf512b05a0c1d35507e912f", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Diamond_ {
    /// The shape of [Diamond] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Diamond] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Diamond_"), "d567a031f8827c17e8bf4f692b86361186cdff6fdbf512b05a0c1d35507e912f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Diamond"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.Left"), ClassDesc.of("p.Right"), ClassDesc.of("p.Root")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("root"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Diamond"))), false, new Diamond_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Diamond], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Diamond].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Diamond open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces p.Left; p.Right; p.Root
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant int ROOT = 1
        member method public overridable root() -> java.lang.String throws -
        inherit ctor Diamond() public () throws -
        inherit field public p.Root ROOT
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method root() erased () overrides - by interface default p.Root () -> java.lang.String throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); root(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Diamond()
        """;

        private Canonical() {
        }
    }

    /// The token of [Diamond].
    public static final OpenClassToken<Diamond> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Diamond#ROOT], declared in `p.Root`, which is not `public`.
    public static final StaticFieldRef<Int> ROOT = UnsafeFacts.constantField(TOKEN, "ROOT", PrimitiveToken.INT, 1);

    /// The fact of [Diamond#Diamond()].
    public static final CtorRef0<Diamond> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Diamond#root()], declared in `p.Root`, which is not `public`.
    public static final MethodRef0<Diamond, String> root = UnsafeFacts.method(TOKEN, "root", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Diamond_() {
    }

    /// Starts the proof that a class being declared extends [Diamond]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Diamond> extend(RefToken<Self> self) {
        return new Diamond_Heritage().extend(TOKEN, self);
    }
}
