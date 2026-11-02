package gen.facts.java.lang;

import gen.facts.java.lang.Deprecated_.Builder;
import gen.facts.java.lang.Deprecated_.Canonical;
import gen.facts.java.lang.Deprecated_.Data;
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
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Deprecated]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Deprecated]: it is only mentioned in the signatures of [p.Noted]. For the facts of its members add `Deprecated.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Deprecated.class, fingerprint = "41a14247448760b589536fd8be9c43b05981971e05112af707ea40cbde6603ad", complete = false, format = 20)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Deprecated_ {
    /// The shape of [Deprecated] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Deprecated] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Deprecated_"), "41a14247448760b589536fd8be9c43b05981971e05112af707ea40cbde6603ad", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.Deprecated"), List.of(), List.of(), List.of(ClassDesc.of("java.lang.annotation.Annotation")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("annotationType"), Signature.of("forRemoval"), Signature.of("since")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(new AnnotationUse(ClassDesc.of("java.lang.annotation.Documented"), List.of()), new AnnotationUse(ClassDesc.of("java.lang.annotation.Retention"), List.of(new AnnotationMember("value", AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.RetentionPolicy"), "RUNTIME")))), new AnnotationUse(ClassDesc.of("java.lang.annotation.Target"), List.of(new AnnotationMember("value", AnnotationValues.array(AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "CONSTRUCTOR"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "FIELD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "LOCAL_VARIABLE"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "METHOD"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "PACKAGE"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "MODULE"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "PARAMETER"), AnnotationValues.enumValue(ClassDesc.of("java.lang.annotation.ElementType"), "TYPE")))))), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Deprecated], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Deprecated].
        static final String TEXT = """
        javafile-facts-canonical 13
        type java.lang.Deprecated interface sealed=no nesting=top
        annotations @java.lang.annotation.Documented; @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME); @java.lang.annotation.Target({java.lang.annotation.ElementType.CONSTRUCTOR; java.lang.annotation.ElementType.FIELD; java.lang.annotation.ElementType.LOCAL_VARIABLE; java.lang.annotation.ElementType.METHOD; java.lang.annotation.ElementType.PACKAGE; java.lang.annotation.ElementType.MODULE; java.lang.annotation.ElementType.PARAMETER; java.lang.annotation.ElementType.TYPE})
        annotation since: java.lang.String = ""; forRemoval: boolean = false; retention RUNTIME; targets CONSTRUCTOR; FIELD; LOCAL_VARIABLE; METHOD; MODULE; PACKAGE; PARAMETER; TYPE; repeatable no
        tparams -
        superclasses -
        interfaces java.lang.annotation.Annotation
        supertypes -
        enum -
        record -
        members none
        table abstract annotationType(); forRemoval(); since()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Deprecated].
    public static final InterfaceToken<Deprecated> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The elements of the annotation type, their defaults, its retention, its targets and whether it is repeatable.
    public static final AnnotationType<Deprecated> ANNOTATION = UnsafeFacts.annotationType(TOKEN, List.of(UnsafeFacts.annotationElement(ClassDesc.of("java.lang.Deprecated"), "since", Types.of(ClassDesc.of("java.lang.String")), Optional.of(AnnotationValues.literal(""))), UnsafeFacts.annotationElement(ClassDesc.of("java.lang.Deprecated"), "forRemoval", PrimitiveTypeRef.BOOLEAN, Optional.of(AnnotationValues.literal(false)))), RetentionPolicy.RUNTIME, Set.of(ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.PACKAGE, ElementType.MODULE), false);

    /// Starts a use of the annotation type.
    public static final class Builder {
        private final List<me.supcheg.javafile.facts.AnnotationMember> members;

        private Builder(List<me.supcheg.javafile.facts.AnnotationMember> members) {
            this.members = members;
        }

        /// Gives the element &#96;since&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder since(AnnotationValue<String> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "since", value));
        }

        /// Gives the element &#96;forRemoval&#96; a value.
        ///
        /// @param value the value
        /// @return the stage after the element is given
        public Builder forRemoval(AnnotationValue<Bool> value) {
            return new Builder(UnsafeFacts.annotationMembers(members, "forRemoval", value));
        }

        /// The use of the annotation type, every required element given a value.
        ///
        /// @return the use
        public AnnotationRef<Deprecated> build() {
            return UnsafeFacts.annotationRef(ANNOTATION, members);
        }
    }

    /// Starts a use of the annotation type: a value for every element without a default before &#96;build()&#96;.
    ///
    /// @return the first stage of the builder
    public static Builder annotation() {
        return new Builder(List.of());
    }

    private Deprecated_() {
    }
}
