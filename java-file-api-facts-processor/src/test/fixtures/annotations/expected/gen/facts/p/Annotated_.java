package gen.facts.p;

import gen.facts.p.Annotated_.Canonical;
import gen.facts.p.Annotated_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.annotation.AnnotationMember;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
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
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Annotated;
import p.Level;
import p.Tag;

/// The full metamodel of [Annotated], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Annotated] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Annotated] by the proof its metamodel starts, with the stages of `Annotated_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Annotated.class, fingerprint = "4ca60e31d96c65bd1e0c77b901ee42b43f61d43dde6225ad5006758810c724fc", complete = true, format = 17)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Annotated_ {
    /// The shape of [Annotated] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Annotated] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Annotated_"), "4ca60e31d96c65bd1e0c77b901ee42b43f61d43dde6225ad5006758810c724fc", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Annotated"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("greet", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Annotated")), Set.of()), false, List.of(new AnnotationUse(ClassDesc.of("p.Author"), List.of(new AnnotationMember("name", AnnotationValues.literal("Ada")), new AnnotationMember("tags", AnnotationValues.array(AnnotationValues.nested(new AnnotationUse(ClassDesc.of("p.Tag"), List.of(new AnnotationMember("big", AnnotationValues.literal(2L)), new AnnotationMember("decimal", AnnotationValues.literal(2.0f)), new AnnotationMember("flag", AnnotationValues.literal(false)), new AnnotationMember("letter", AnnotationValues.literal(98)), new AnnotationMember("levels", AnnotationValues.array(AnnotationValues.enumValue(ClassDesc.of("p.Level"), "LOW"), AnnotationValues.enumValue(ClassDesc.of("p.Level"), "HIGH"))), new AnnotationMember("medium", AnnotationValues.literal(4)), new AnnotationMember("rank", AnnotationValues.literal(2)), new AnnotationMember("ratio", AnnotationValues.literal(2.5)), new AnnotationMember("small", AnnotationValues.literal(3)), new AnnotationMember("type", AnnotationValues.classValue(ClassDesc.of("java.lang.String"))), new AnnotationMember("value", AnnotationValues.literal("type"))))))), new AnnotationMember("year", AnnotationValues.literal(1815))))), new Annotated_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Annotated], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Annotated].
        static final String TEXT = """
        javafile-facts-canonical 11
        type p.Annotated open-class sealed=no nesting=top
        annotations @p.Author(name = "Ada", tags = {@p.Tag(big = 2L, decimal = 2.0f, flag = false, letter = 98, levels = {p.Level.LOW; p.Level.HIGH}, medium = 4, rank = 2, ratio = 2.5, small = 3, type = java.lang.String.class, value = "type")}, year = 1815)
        annotation -
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws - annotations @p.Author(name = "constructor")
        member field public instance mutable @p.Tag(array = java.lang.String[].class, big = 3L, decimal = 3.0f, flag = true, letter = 99, levels = {p.Level.HIGH}, medium = 5, primitive = int.class, rank = 3, ratio = 3.5, small = 4, type = java.lang.Integer.class, value = "field") java.lang.String name annotations @p.Author(name = "field"); @p.Tag(array = java.lang.String[].class, big = 3L, decimal = 3.0f, flag = true, letter = 99, levels = {p.Level.HIGH}, medium = 5, primitive = int.class, rank = 3, ratio = 3.5, small = 4, type = java.lang.Integer.class, value = "field")
        member method public overridable greet(@p.Tag("parameter") @p.Tag("parameter") java.lang.String) -> @p.Tag(array = int[].class, big = 4L, decimal = 4.0f, flag = false, letter = 100, levels = {p.Level.LOW}, medium = 7, primitive = byte.class, rank = 4, ratio = 4.5, small = 6, type = java.lang.Object.class, value = "method") java.lang.String throws - annotations @p.Author(name = "method", tags = {@p.Tag("nested")}); @p.Tag(array = int[].class, big = 4L, decimal = 4.0f, flag = false, letter = 100, levels = {p.Level.LOW}, medium = 7, primitive = byte.class, rank = 4, ratio = 4.5, small = 6, type = java.lang.Object.class, value = "method")
        inherit ctor Annotated() public () throws -
        inherit field public p.Annotated name
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method greet(java.lang.String) erased (java.lang.String) overrides - by class public concrete p.Annotated (@p.Tag("parameter") java.lang.String) -> @p.Tag(array = int[].class, big = 4L, decimal = 4.0f, flag = false, letter = 100, levels = {p.Level.LOW}, medium = 7, primitive = byte.class, rank = 4, ratio = 4.5, small = 6, type = java.lang.Object.class, value = "method") java.lang.String throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); greet(java.lang.String); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Annotated()
        """;

        private Canonical() {
        }
    }

    /// The token of [Annotated].
    public static final OpenClassToken<Annotated> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Annotated#name].
    public static final MutableFieldRef<Annotated, @Tag(array = String[].class, big = 3L, decimal = 3.0f, flag = true, letter = 99, levels = {
        Level.HIGH
    }, medium = 5, primitive = int.class, rank = 3, ratio = 3.5, small = 4, type = Integer.class, value = "field") String> name = UnsafeFacts.mutableField(TOKEN, "name", UnsafeFacts.<@Tag(array = String[].class, big = 3L, decimal = 3.0f, flag = true, letter = 99, levels = {
        Level.HIGH
    }, medium = 5, primitive = int.class, rank = 3, ratio = 3.5, small = 4, type = Integer.class, value = "field") String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));

    /// The fact of [Annotated#Annotated()].
    public static final CtorRef0<Annotated> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL.annotated(new AnnotationUse(ClassDesc.of("p.Author"), List.of(new AnnotationMember("name", AnnotationValues.literal("constructor"))))));

    /// The fact of [Annotated#greet(String)].
    public static final MethodRef1<Annotated, @Tag(array = int[].class, big = 4L, decimal = 4.0f, flag = false, letter = 100, levels = {
        Level.LOW
    }, medium = 7, primitive = byte.class, rank = 4, ratio = 4.5, small = 6, type = Object.class, value = "method") String, @Tag("parameter") String> greet_String = UnsafeInvocables.method(TOKEN, "greet", UnsafeFacts.<@Tag(array = int[].class, big = 4L, decimal = 4.0f, flag = false, letter = 100, levels = {
        Level.LOW
    }, medium = 7, primitive = byte.class, rank = 4, ratio = 4.5, small = 6, type = Object.class, value = "method") String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<@Tag("parameter") String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE.annotated(new AnnotationUse(ClassDesc.of("p.Author"), List.of(new AnnotationMember("name", AnnotationValues.literal("method")), new AnnotationMember("tags", AnnotationValues.array(AnnotationValues.nested(new AnnotationUse(ClassDesc.of("p.Tag"), List.of(new AnnotationMember("value", AnnotationValues.literal("nested"))))))))), new AnnotationUse(ClassDesc.of("p.Tag"), List.of(new AnnotationMember("array", AnnotationValues.classValue(ClassDesc.ofDescriptor("[I"))), new AnnotationMember("big", AnnotationValues.literal(4L)), new AnnotationMember("decimal", AnnotationValues.literal(4.0f)), new AnnotationMember("flag", AnnotationValues.literal(false)), new AnnotationMember("letter", AnnotationValues.literal(100)), new AnnotationMember("levels", AnnotationValues.array(AnnotationValues.enumValue(ClassDesc.of("p.Level"), "LOW"))), new AnnotationMember("medium", AnnotationValues.literal(7)), new AnnotationMember("primitive", AnnotationValues.classValue(ConstantDescs.CD_byte)), new AnnotationMember("rank", AnnotationValues.literal(4)), new AnnotationMember("ratio", AnnotationValues.literal(4.5)), new AnnotationMember("small", AnnotationValues.literal(6)), new AnnotationMember("type", AnnotationValues.classValue(ClassDesc.of("java.lang.Object"))), new AnnotationMember("value", AnnotationValues.literal("method"))))).annotatedParameters(List.of(List.of(new AnnotationUse(ClassDesc.of("p.Tag"), List.of(new AnnotationMember("value", AnnotationValues.literal("parameter"))))))));

    private Annotated_() {
    }

    /// Starts the proof that a class being declared extends [Annotated]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Annotated> extend(RefToken<Self> self) {
        return new Annotated_Heritage().extend(TOKEN, self);
    }
}
