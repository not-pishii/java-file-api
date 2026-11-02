package gen.facts.p;

import gen.facts.p.Marker_.Builder;
import gen.facts.p.Marker_.Canonical;
import gen.facts.p.Marker_.Data;
import java.lang.annotation.RetentionPolicy;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AnnotationMember;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.AnnotationType;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Marker;

/// The token-only metamodel of [Marker]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Marker]: it is only mentioned in the signatures of [p.ByMarker]. For the facts of its members add `Marker.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Marker.class, fingerprint = "aa4a8e45d55c0323828f5d8a98360b7eab2fb0d58c704d14f5d1932db0cffe50", complete = false, format = 20)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Marker_ {
    /// The shape of [Marker] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Marker] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Marker_"), "aa4a8e45d55c0323828f5d8a98360b7eab2fb0d58c704d14f5d1932db0cffe50", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Marker"), List.of(), List.of(), List.of(ClassDesc.of("java.lang.annotation.Annotation")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("annotationType")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Marker], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Marker].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Marker interface sealed=no nesting=top
        annotations -
        annotation -; retention CLASS; targets -; repeatable no
        tparams -
        superclasses -
        interfaces java.lang.annotation.Annotation
        supertypes -
        enum -
        record -
        members none
        table abstract annotationType()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Marker].
    public static final InterfaceToken<Marker> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The elements of the annotation type, their defaults, its retention, its targets and whether it is repeatable.
    public static final AnnotationType<Marker> ANNOTATION = UnsafeFacts.annotationType(TOKEN, List.of(), RetentionPolicy.CLASS, Set.of(), false);

    /// Starts a use of the annotation type.
    public static final class Builder {
        private final List<AnnotationMember> members;

        private Builder(List<AnnotationMember> members) {
            this.members = members;
        }

        /// The use of the annotation type, every required element given a value.
        ///
        /// @return the use
        public AnnotationRef<Marker> build() {
            return UnsafeFacts.annotationRef(ANNOTATION, members);
        }
    }

    /// Starts a use of the annotation type: a value for every element without a default before &#96;build()&#96;.
    ///
    /// @return the first stage of the builder
    public static Builder annotation() {
        return new Builder(List.of());
    }

    private Marker_() {
    }
}
