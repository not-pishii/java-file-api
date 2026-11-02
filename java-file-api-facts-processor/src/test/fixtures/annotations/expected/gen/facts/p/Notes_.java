package gen.facts.p;

import gen.facts.p.Notes_.Builder;
import gen.facts.p.Notes_.Builder2;
import gen.facts.p.Notes_.Canonical;
import gen.facts.p.Notes_.Data;
import gen.facts.p.Notes_Heritage.Implement;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Author;
import p.Notes;

/// The full metamodel of [Notes], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Notes] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.annotation.Annotation_].
///
/// A generated class extends or implements [Notes] by the proof its metamodel starts, with the stages of `Notes_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Notes.class, fingerprint = "5ff30c5ec153b82fda1183e2407cb1a5af433a3355da786f96d3569d3eb213ae", complete = true, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Notes_ {
    /// The shape of [Notes] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Notes] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Notes_"), "5ff30c5ec153b82fda1183e2407cb1a5af433a3355da786f96d3569d3eb213ae", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Notes"), List.of(), List.of(), List.of(ClassDesc.of("java.lang.annotation.Annotation")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("annotationType"), Signature.of("value")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), false, List.of(), List.of(new AnnotationUse(ClassDesc.of("java.lang.annotation.Retention"), List.of(new AnnotationMember("value", AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.RetentionPolicy"), "RUNTIME")))), new AnnotationUse(ClassDesc.of("java.lang.annotation.Target"), List.of(new AnnotationMember("value", AnnotationValues.array(AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "TYPE"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "METHOD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "FIELD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "CONSTRUCTOR"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "PARAMETER")))))), new Notes_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Notes], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Notes].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Notes interface sealed=no nesting=top
        annotations @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME); @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE; java.lang.annotation.ElementType.METHOD; java.lang.annotation.ElementType.FIELD; java.lang.annotation.ElementType.CONSTRUCTOR; java.lang.annotation.ElementType.PARAMETER})
        annotation value: p.Author[]; retention RUNTIME; targets CONSTRUCTOR; FIELD; METHOD; PARAMETER; TYPE; repeatable no
        tparams -
        superclasses -
        interfaces java.lang.annotation.Annotation
        supertypes -
        enum -
        record -
        members declared-accessible
        member method public abstract value() -> p.Author[] throws -
        inherit method annotationType() erased () overrides - by interface abstract java.lang.annotation.Annotation () -> java.lang.Class<? extends java.lang.annotation.Annotation> throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides java.lang.annotation.Annotation by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides java.lang.annotation.Annotation by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides java.lang.annotation.Annotation by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method value() erased () overrides - by interface abstract p.Notes () -> p.Author[] throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract annotationType(); value()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Notes].
    public static final InterfaceToken<Notes> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The elements of the annotation type, their defaults, its retention, its targets and whether it is repeatable.
    public static final AnnotationType<Notes> ANNOTATION = UnsafeFacts.annotationType(TOKEN, List.of(UnsafeFacts.annotationElement(ClassDesc.of("p.Notes"), "value", Types.array(Types.of(ClassDesc.of("p.Author"))), Optional.empty())), RetentionPolicy.RUNTIME, Set.of(ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR), false);

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
        public Builder2 value(AnnotationValue<Author[]> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "value", value));
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
        public Builder2 value(AnnotationValue<Author[]> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "value", value));
        }

        /// The use of the annotation type, every required element given a value.
        ///
        /// @return the use
        public AnnotationRef<Notes> build() {
            return UnsafeFacts.annotationRef(ANNOTATION, members);
        }
    }

    /// Starts a use of the annotation type: a value for every element without a default before &#96;build()&#96;.
    ///
    /// @return the first stage of the builder
    public static Builder annotation() {
        return new Builder(List.of());
    }

    /// The fact of [Notes#value()].
    public static final MethodRef0<Notes, Author[]> value_ = UnsafeInvocables.method(TOKEN, "value", ArrayToken.of(UnsafeFacts.<Author>interfaceToken(Author_.Data.SHAPE)), MemberTraits.ABSTRACT);

    private Notes_() {
    }

    /// Starts the proof that a class being declared implements [Notes]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Notes_Heritage().implement(TOKEN, self);
    }
}
