package gen.facts.p;

import gen.facts.p.Tag_.Builder;
import gen.facts.p.Tag_.Builder2;
import gen.facts.p.Tag_.Canonical;
import gen.facts.p.Tag_.Data;
import gen.facts.p.Tag_Heritage.Implement;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.annotation.AnnotationMember;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.AnnotationType;
import me.supcheg.javafile.facts.AnnotationValue;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Byte;
import me.supcheg.javafile.facts.Prim.Char;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Float;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.Prim.Short;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Level;
import p.Tag;

/// The full metamodel of [Tag], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Tag] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.annotation.Annotation_].
///
/// A generated class extends or implements [Tag] by the proof its metamodel starts, with the stages of `Tag_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Tag.class, fingerprint = "e9228b6b37764d64661b1a78fc47eac61bd5b7021eb0fd40d6896fc91a563b94", complete = true, format = 17)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Tag_ {
    /// The shape of [Tag] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Tag] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Tag_"), "e9228b6b37764d64661b1a78fc47eac61bd5b7021eb0fd40d6896fc91a563b94", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Tag"), List.of(), List.of(), List.of(ClassDesc.of("java.lang.annotation.Annotation")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("annotationType"), Signature.of("array"), Signature.of("big"), Signature.of("decimal"), Signature.of("flag"), Signature.of("letter"), Signature.of("levels"), Signature.of("medium"), Signature.of("primitive"), Signature.of("rank"), Signature.of("ratio"), Signature.of("small"), Signature.of("type"), Signature.of("value")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of()), false, List.of(new AnnotationUse(ClassDesc.of("java.lang.annotation.Retention"), List.of(new AnnotationMember("value", AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.RetentionPolicy"), "RUNTIME")))), new AnnotationUse(ClassDesc.of("java.lang.annotation.Target"), List.of(new AnnotationMember("value", AnnotationValues.array(AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "TYPE"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "FIELD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "METHOD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "PARAMETER"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "TYPE_USE")))))), new Tag_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Tag], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Tag].
        static final String TEXT = """
        javafile-facts-canonical 11
        type p.Tag interface sealed=no nesting=top
        annotations @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME); @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE; java.lang.annotation.ElementType.FIELD; java.lang.annotation.ElementType.METHOD; java.lang.annotation.ElementType.PARAMETER; java.lang.annotation.ElementType.TYPE_USE})
        annotation value: java.lang.String; rank: int = 1; levels: p.Level[] = {}; type: java.lang.Class<?> = java.lang.Object.class; primitive: java.lang.Class<?> = int.class; array: java.lang.Class<?> = java.lang.String[].class; flag: boolean = true; big: long = 1L; ratio: double = 1.5; letter: char = 97; small: byte = 1; medium: short = 2; decimal: float = 1.0f; retention RUNTIME; targets FIELD; METHOD; PARAMETER; TYPE; TYPE_USE; repeatable no
        tparams -
        superclasses -
        interfaces java.lang.annotation.Annotation
        supertypes -
        enum -
        members declared-accessible
        member method public abstract array() -> java.lang.Class<?> throws -
        member method public abstract big() -> long throws -
        member method public abstract decimal() -> float throws -
        member method public abstract flag() -> boolean throws -
        member method public abstract letter() -> char throws -
        member method public abstract levels() -> p.Level[] throws -
        member method public abstract medium() -> short throws -
        member method public abstract primitive() -> java.lang.Class<?> throws -
        member method public abstract rank() -> int throws -
        member method public abstract ratio() -> double throws -
        member method public abstract small() -> byte throws -
        member method public abstract type() -> java.lang.Class<?> throws -
        member method public abstract value() -> java.lang.String throws -
        inherit method annotationType() erased () overrides - by interface abstract java.lang.annotation.Annotation () -> java.lang.Class<? extends java.lang.annotation.Annotation> throws -
        inherit method array() erased () overrides - by interface abstract p.Tag () -> java.lang.Class<?> throws -
        inherit method big() erased () overrides - by interface abstract p.Tag () -> long throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method decimal() erased () overrides - by interface abstract p.Tag () -> float throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides java.lang.annotation.Annotation by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method flag() erased () overrides - by interface abstract p.Tag () -> boolean throws -
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides java.lang.annotation.Annotation by class public concrete java.lang.Object () -> int throws -
        inherit method letter() erased () overrides - by interface abstract p.Tag () -> char throws -
        inherit method levels() erased () overrides - by interface abstract p.Tag () -> p.Level[] throws -
        inherit method medium() erased () overrides - by interface abstract p.Tag () -> short throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method primitive() erased () overrides - by interface abstract p.Tag () -> java.lang.Class<?> throws -
        inherit method rank() erased () overrides - by interface abstract p.Tag () -> int throws -
        inherit method ratio() erased () overrides - by interface abstract p.Tag () -> double throws -
        inherit method small() erased () overrides - by interface abstract p.Tag () -> byte throws -
        inherit method toString() erased () overrides java.lang.annotation.Annotation by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method type() erased () overrides - by interface abstract p.Tag () -> java.lang.Class<?> throws -
        inherit method value() erased () overrides - by interface abstract p.Tag () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract annotationType(); array(); big(); decimal(); flag(); letter(); levels(); medium(); primitive(); rank(); ratio(); small(); type(); value()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Tag].
    public static final InterfaceToken<Tag> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The elements of the annotation type, their defaults, its retention, its targets and whether it is repeatable.
    public static final AnnotationType<Tag> ANNOTATION = UnsafeFacts.annotationType(TOKEN, List.of(UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "value", Types.of(ClassDesc.of("java.lang.String")), Optional.empty()), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "rank", PrimitiveTypeRef.INT, Optional.of(AnnotationValues.literal(1))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "levels", Types.array(Types.of(ClassDesc.of("p.Level"))), Optional.of(AnnotationValues.array())), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "type", new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()), Optional.of(AnnotationValues.classValue(ClassDesc.of("java.lang.Object")))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "primitive", new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()), Optional.of(AnnotationValues.classValue(ConstantDescs.CD_int))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "array", new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()), List.of()), Optional.of(AnnotationValues.classValue(ClassDesc.ofDescriptor("[Ljava/lang/String;")))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "flag", PrimitiveTypeRef.BOOLEAN, Optional.of(AnnotationValues.literal(true))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "big", PrimitiveTypeRef.LONG, Optional.of(AnnotationValues.literal(1L))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "ratio", PrimitiveTypeRef.DOUBLE, Optional.of(AnnotationValues.literal(1.5))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "letter", PrimitiveTypeRef.CHAR, Optional.of(AnnotationValues.literal(97))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "small", PrimitiveTypeRef.BYTE, Optional.of(AnnotationValues.literal(1))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "medium", PrimitiveTypeRef.SHORT, Optional.of(AnnotationValues.literal(2))), UnsafeFacts.annotationElement(ClassDesc.of("p.Tag"), "decimal", PrimitiveTypeRef.FLOAT, Optional.of(AnnotationValues.literal(1.0f)))), RetentionPolicy.RUNTIME, Set.of(ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE), false);

    /// Starts a use of the annotation type.
    public static final class Builder {
        private final List<me.supcheg.javafile.facts.AnnotationMember> members;

        private Builder(List<me.supcheg.javafile.facts.AnnotationMember> members) {
            this.members = members;
        }

        /// Gives the element &#96;value&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 value(AnnotationValue<String> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "value", value));
        }

        /// Gives the element &#96;rank&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder rank(AnnotationValue<Int> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "rank", value));
        }

        /// Gives the element &#96;levels&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder levels(AnnotationValue<Level[]> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "levels", value));
        }

        /// Gives the element &#96;type&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder type(AnnotationValue<Class<?>> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "type", value));
        }

        /// Gives the element &#96;primitive&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder primitive(AnnotationValue<Class<?>> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "primitive", value));
        }

        /// Gives the element &#96;array&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder array(AnnotationValue<Class<?>> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "array", value));
        }

        /// Gives the element &#96;flag&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder flag(AnnotationValue<Bool> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "flag", value));
        }

        /// Gives the element &#96;big&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder big(AnnotationValue<Long> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "big", value));
        }

        /// Gives the element &#96;ratio&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder ratio(AnnotationValue<Double> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "ratio", value));
        }

        /// Gives the element &#96;letter&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder letter(AnnotationValue<Char> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "letter", value));
        }

        /// Gives the element &#96;small&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder small(AnnotationValue<Byte> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "small", value));
        }

        /// Gives the element &#96;medium&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder medium(AnnotationValue<Short> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "medium", value));
        }

        /// Gives the element &#96;decimal&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder decimal(AnnotationValue<Float> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "decimal", value));
        }
    }

    /// A use of the annotation type that has given the first 1 required element(s).
    public static final class Builder2 {
        private final List<me.supcheg.javafile.facts.AnnotationMember> members;

        private Builder2(List<me.supcheg.javafile.facts.AnnotationMember> members) {
            this.members = members;
        }

        /// Gives the element &#96;value&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 value(AnnotationValue<String> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "value", value));
        }

        /// Gives the element &#96;rank&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 rank(AnnotationValue<Int> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "rank", value));
        }

        /// Gives the element &#96;levels&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 levels(AnnotationValue<Level[]> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "levels", value));
        }

        /// Gives the element &#96;type&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 type(AnnotationValue<Class<?>> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "type", value));
        }

        /// Gives the element &#96;primitive&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 primitive(AnnotationValue<Class<?>> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "primitive", value));
        }

        /// Gives the element &#96;array&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 array(AnnotationValue<Class<?>> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "array", value));
        }

        /// Gives the element &#96;flag&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 flag(AnnotationValue<Bool> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "flag", value));
        }

        /// Gives the element &#96;big&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 big(AnnotationValue<Long> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "big", value));
        }

        /// Gives the element &#96;ratio&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 ratio(AnnotationValue<Double> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "ratio", value));
        }

        /// Gives the element &#96;letter&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 letter(AnnotationValue<Char> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "letter", value));
        }

        /// Gives the element &#96;small&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 small(AnnotationValue<Byte> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "small", value));
        }

        /// Gives the element &#96;medium&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 medium(AnnotationValue<Short> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "medium", value));
        }

        /// Gives the element &#96;decimal&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 decimal(AnnotationValue<Float> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "decimal", value));
        }

        /// The use of the annotation type, every required element given a value.
        ///
        /// @return the use
        public AnnotationRef<Tag> build() {
            return UnsafeFacts.annotationRef(ANNOTATION, members);
        }
    }

    /// Starts a use of the annotation type: a value for every element without a default before &#96;build()&#96;.
    ///
    /// @return the first stage of the builder
    public static Builder annotation() {
        return new Builder(List.of());
    }

    /// The fact of [Tag#array()].
    public static final MethodRef0<Tag, Class<?>> array_ = UnsafeInvocables.method(TOKEN, "array", UnsafeFacts.<Class<?>>finalClassToken(gen.facts.java.lang.Class_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.ABSTRACT);

    /// The fact of [Tag#big()].
    public static final MethodRef0<Tag, Long> big_ = UnsafeInvocables.method(TOKEN, "big", PrimitiveToken.LONG, MemberTraits.ABSTRACT);

    /// The fact of [Tag#decimal()].
    public static final MethodRef0<Tag, Float> decimal_ = UnsafeInvocables.method(TOKEN, "decimal", PrimitiveToken.FLOAT, MemberTraits.ABSTRACT);

    /// The fact of [Tag#flag()].
    public static final MethodRef0<Tag, Bool> flag_ = UnsafeInvocables.method(TOKEN, "flag", PrimitiveToken.BOOLEAN, MemberTraits.ABSTRACT);

    /// The fact of [Tag#letter()].
    public static final MethodRef0<Tag, Char> letter_ = UnsafeInvocables.method(TOKEN, "letter", PrimitiveToken.CHAR, MemberTraits.ABSTRACT);

    /// The fact of [Tag#levels()].
    public static final MethodRef0<Tag, Level[]> levels_ = UnsafeInvocables.method(TOKEN, "levels", ArrayToken.of(UnsafeFacts.<Level>enumToken(Level_.Data.SHAPE)), MemberTraits.ABSTRACT);

    /// The fact of [Tag#medium()].
    public static final MethodRef0<Tag, Short> medium_ = UnsafeInvocables.method(TOKEN, "medium", PrimitiveToken.SHORT, MemberTraits.ABSTRACT);

    /// The fact of [Tag#primitive()].
    public static final MethodRef0<Tag, Class<?>> primitive_ = UnsafeInvocables.method(TOKEN, "primitive", UnsafeFacts.<Class<?>>finalClassToken(gen.facts.java.lang.Class_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.ABSTRACT);

    /// The fact of [Tag#rank()].
    public static final MethodRef0<Tag, Int> rank_ = UnsafeInvocables.method(TOKEN, "rank", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [Tag#ratio()].
    public static final MethodRef0<Tag, Double> ratio_ = UnsafeInvocables.method(TOKEN, "ratio", PrimitiveToken.DOUBLE, MemberTraits.ABSTRACT);

    /// The fact of [Tag#small()].
    public static final MethodRef0<Tag, Byte> small_ = UnsafeInvocables.method(TOKEN, "small", PrimitiveToken.BYTE, MemberTraits.ABSTRACT);

    /// The fact of [Tag#type()].
    public static final MethodRef0<Tag, Class<?>> type_ = UnsafeInvocables.method(TOKEN, "type", UnsafeFacts.<Class<?>>finalClassToken(gen.facts.java.lang.Class_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.ABSTRACT);

    /// The fact of [Tag#value()].
    public static final MethodRef0<Tag, String> value_ = UnsafeInvocables.method(TOKEN, "value", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    private Tag_() {
    }

    /// Starts the proof that a class being declared implements [Tag]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Tag_Heritage().implement(TOKEN, self);
    }
}
