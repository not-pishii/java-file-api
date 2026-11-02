package gen.facts.p;

import gen.facts.p.Author_.Builder;
import gen.facts.p.Author_.Builder2;
import gen.facts.p.Author_.Canonical;
import gen.facts.p.Author_.Data;
import gen.facts.p.Author_Heritage.Implement;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Author;
import p.Tag;

/// The full metamodel of [Author], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Author] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.annotation.Annotation_].
///
/// A generated class extends or implements [Author] by the proof its metamodel starts, with the stages of `Author_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Author.class, fingerprint = "008e1cfc51e30618f17dbce367e84baecfcc6a75ab9e7d4afcae8e31ba13f7be", complete = true, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Author_ {
    /// The shape of [Author] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Author] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Author_"), "008e1cfc51e30618f17dbce367e84baecfcc6a75ab9e7d4afcae8e31ba13f7be", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Author"), List.of(), List.of(), List.of(ClassDesc.of("java.lang.annotation.Annotation")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("annotationType"), Signature.of("name"), Signature.of("tags"), Signature.of("year")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), false, List.of(), List.of(new AnnotationUse(ClassDesc.of("java.lang.annotation.Repeatable"), List.of(new AnnotationMember("value", AnnotationValues.classValue(ClassDesc.of("p.Notes"))))), new AnnotationUse(ClassDesc.of("java.lang.annotation.Retention"), List.of(new AnnotationMember("value", AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.RetentionPolicy"), "RUNTIME")))), new AnnotationUse(ClassDesc.of("java.lang.annotation.Target"), List.of(new AnnotationMember("value", AnnotationValues.array(AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "TYPE"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "METHOD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "FIELD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "CONSTRUCTOR"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "PARAMETER")))))), new Author_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Author], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Author].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Author interface sealed=no nesting=top
        annotations @java.lang.annotation.Repeatable(p.Notes.class); @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME); @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE; java.lang.annotation.ElementType.METHOD; java.lang.annotation.ElementType.FIELD; java.lang.annotation.ElementType.CONSTRUCTOR; java.lang.annotation.ElementType.PARAMETER})
        annotation name: java.lang.String; year: int = 0; tags: p.Tag[] = {}; retention RUNTIME; targets CONSTRUCTOR; FIELD; METHOD; PARAMETER; TYPE; repeatable yes
        tparams -
        superclasses -
        interfaces java.lang.annotation.Annotation
        supertypes -
        enum -
        record -
        members declared-accessible
        member method public abstract name() -> java.lang.String throws -
        member method public abstract tags() -> p.Tag[] throws -
        member method public abstract year() -> int throws -
        inherit method annotationType() erased () overrides - by interface abstract java.lang.annotation.Annotation () -> java.lang.Class<? extends java.lang.annotation.Annotation> throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides java.lang.annotation.Annotation by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides java.lang.annotation.Annotation by class public concrete java.lang.Object () -> int throws -
        inherit method name() erased () overrides - by interface abstract p.Author () -> java.lang.String throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method tags() erased () overrides - by interface abstract p.Author () -> p.Tag[] throws -
        inherit method toString() erased () overrides java.lang.annotation.Annotation by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        inherit method year() erased () overrides - by interface abstract p.Author () -> int throws -
        table abstract annotationType(); name(); tags(); year()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Author].
    public static final InterfaceToken<Author> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The elements of the annotation type, their defaults, its retention, its targets and whether it is repeatable.
    public static final AnnotationType<Author> ANNOTATION = UnsafeFacts.annotationType(TOKEN, List.of(UnsafeFacts.annotationElement(ClassDesc.of("p.Author"), "name", Types.of(ClassDesc.of("java.lang.String")), Optional.empty()), UnsafeFacts.annotationElement(ClassDesc.of("p.Author"), "year", PrimitiveTypeRef.INT, Optional.of(AnnotationValues.literal(0))), UnsafeFacts.annotationElement(ClassDesc.of("p.Author"), "tags", Types.array(Types.of(ClassDesc.of("p.Tag"))), Optional.of(AnnotationValues.array()))), RetentionPolicy.RUNTIME, Set.of(ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR), true);

    /// Starts a use of the annotation type.
    public static final class Builder {
        private final List<me.supcheg.javafile.facts.AnnotationMember> members;

        private Builder(List<me.supcheg.javafile.facts.AnnotationMember> members) {
            this.members = members;
        }

        /// Gives the element &#96;name&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 name(AnnotationValue<String> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "name", value));
        }

        /// Gives the element &#96;year&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder year(AnnotationValue<Int> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "year", value));
        }

        /// Gives the element &#96;tags&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder tags(AnnotationValue<Tag[]> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "tags", value));
        }
    }

    /// A use of the annotation type that has given the first 1 required element(s).
    public static final class Builder2 {
        private final List<me.supcheg.javafile.facts.AnnotationMember> members;

        private Builder2(List<me.supcheg.javafile.facts.AnnotationMember> members) {
            this.members = members;
        }

        /// Gives the element &#96;name&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 name(AnnotationValue<String> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "name", value));
        }

        /// Gives the element &#96;year&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 year(AnnotationValue<Int> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "year", value));
        }

        /// Gives the element &#96;tags&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder2 tags(AnnotationValue<Tag[]> value) {
            return new Builder2(UnsafeFacts.annotationMembers(members, "tags", value));
        }

        /// The use of the annotation type, every required element given a value.
        ///
        /// @return the use
        public AnnotationRef<Author> build() {
            return UnsafeFacts.annotationRef(ANNOTATION, members);
        }
    }

    /// Starts a use of the annotation type: a value for every element without a default before &#96;build()&#96;.
    ///
    /// @return the first stage of the builder
    public static Builder annotation() {
        return new Builder(List.of());
    }

    /// The fact of [Author#name()].
    public static final MethodRef0<Author, String> name_ = UnsafeInvocables.method(TOKEN, "name", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of [Author#tags()].
    public static final MethodRef0<Author, Tag[]> tags_ = UnsafeInvocables.method(TOKEN, "tags", ArrayToken.of(UnsafeFacts.<Tag>interfaceToken(Tag_.Data.SHAPE)), MemberTraits.ABSTRACT);

    /// The fact of [Author#year()].
    public static final MethodRef0<Author, Int> year_ = UnsafeInvocables.method(TOKEN, "year", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    private Author_() {
    }

    /// Starts the proof that a class being declared implements [Author]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Author_Heritage().implement(TOKEN, self);
    }
}
