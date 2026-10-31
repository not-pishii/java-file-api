package me.supcheg.javafile.facts;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The facts of an annotation type and of a use of it: the values of every
/// kind, the elements and their defaults, and the rule an element without a
/// default holds a use to.
class AnnotationFactsTest {
    interface Author {}

    interface Tag {}

    interface Level {}

    private static final ClassDesc AUTHOR = ClassDesc.of("p", "Author");
    private static final ClassDesc TAG = ClassDesc.of("p", "Tag");
    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc LEVEL = ClassDesc.of("p", "Level");

    private static AnnotationType<Author> authorType() {
        InterfaceToken<Author> token = UnsafeFacts.interfaceToken(Types.of(AUTHOR), MethodTable.EMPTY);
        AnnotationElement<Author, String> name =
                UnsafeFacts.annotationElement(AUTHOR, "name", Types.STRING, Optional.empty());
        AnnotationElement<Author, Prim.Int> year = UnsafeFacts.annotationElement(
                AUTHOR,
                "year",
                Types.INT,
                Optional.of(AnnotationValues.int_(1815).toCore()));
        return UnsafeFacts.annotationType(
                token,
                List.of(name, year),
                RetentionPolicy.RUNTIME,
                Set.of(ElementType.TYPE, ElementType.METHOD),
                true);
    }

    private static AnnotationUse use(ClassDesc type) {
        return new AnnotationUse(type, List.of());
    }

    @Test
    void theFactOfAnAnnotationTypeTellsItsElementsAndTheirDefaults() {
        AnnotationType<Author> type = authorType();

        assertThat(type.token().erasure()).isEqualTo(AUTHOR);
        assertThat(type.elements()).extracting(AnnotationElement::name).containsExactly("name", "year");
        assertThat(type.required()).extracting(AnnotationElement::name).containsExactly("name");
        assertThat(type.element("year").orElseThrow().defaultValue()).isPresent();
        assertThat(type.requireElement("name").required()).isTrue();
        assertThat(type.retention()).isEqualTo(RetentionPolicy.RUNTIME);
        assertThat(type.targets()).containsExactlyInAnyOrder(ElementType.TYPE, ElementType.METHOD);
        assertThat(type.repeatable()).isTrue();
        assertThat(type.toString()).contains("name", "year");
        assertThat(type.requireElement("year").annotation()).isEqualTo(AUTHOR);
        assertThat(type.requireElement("year").type()).isEqualTo(Types.INT);
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> type.requireElement("missing"))
                .withMessageContaining("no element p.Author.missing");
    }

    @Test
    void anElementAndItsTypeAreHeldToTheTypeOfTheAnnotation() {
        InterfaceToken<Author> token = UnsafeFacts.interfaceToken(Types.of(AUTHOR), MethodTable.EMPTY);
        AnnotationElement<Author, String> name =
                UnsafeFacts.annotationElement(AUTHOR, "name", Types.STRING, Optional.empty());

        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        UnsafeFacts.annotationType(token, List.of(name, name), RetentionPolicy.CLASS, Set.of(), false))
                .withMessageContaining("two elements name");
        AnnotationElement<Author, String> other =
                UnsafeFacts.annotationElement(TAG, "value", Types.STRING, Optional.empty());
        assertThatIllegalArgumentException()
                .isThrownBy(
                        () -> UnsafeFacts.annotationType(token, List.of(other), RetentionPolicy.CLASS, Set.of(), false))
                .withMessageContaining("is no element of");
    }

    @Test
    void aUseGivesEveryRequiredElementAndNoOther() {
        AnnotationType<Author> type = authorType();
        AnnotationRef<Author> use =
                UnsafeFacts.annotationRef(type, List.of(new AnnotationMember("name", AnnotationValues.string("Ada"))));

        assertThat(use.type()).isSameAs(type);
        assertThat(use.members())
                .extracting(me.supcheg.javafile.annotation.AnnotationMember::name)
                .containsExactly("name");
        assertThat(use.toUse().type()).isEqualTo(AUTHOR);
        AnnotationRef<Author> same =
                UnsafeFacts.annotationRef(type, List.of(new AnnotationMember("name", AnnotationValues.string("Ada"))));
        assertThat(same).isEqualTo(use);
        assertThat(same.hashCode()).isEqualTo(use.hashCode());
        assertThat(same.toString()).contains("Author");
        assertThat(same)
                .isNotEqualTo(UnsafeFacts.annotationRef(
                        type,
                        List.of(
                                new AnnotationMember("name", AnnotationValues.string("Ada")),
                                new AnnotationMember("year", AnnotationValues.int_(1815)))));
        assertThat(same).isNotEqualTo("not an annotation");
        assertThat(same)
                .isNotEqualTo(UnsafeFacts.annotationRef(
                        type, List.of(new AnnotationMember("name", AnnotationValues.string("Grace")))));
        assertThat(type.requireElement("name").toString()).contains("Author.name");
        assertThat(type.requireElement("year").toString()).isNotNull();

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> UnsafeFacts.annotationRef(type, List.of()))
                .withMessageContaining("p.Author.name, which has no default");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> UnsafeFacts.annotationRef(
                        type, List.of(new AnnotationMember("missing", AnnotationValues.string("x")))))
                .withMessageContaining("no element p.Author.missing");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> UnsafeFacts.annotationRef(
                        type,
                        List.of(
                                new AnnotationMember("name", AnnotationValues.string("a")),
                                new AnnotationMember("name", AnnotationValues.string("b")))))
                .withMessageContaining("no value of element p.Author.name");
        assertThat(UnsafeFacts.annotationMembers(
                        List.of(new AnnotationMember("name", AnnotationValues.string("a"))),
                        "year",
                        AnnotationValues.int_(1)))
                .hasSize(2);
    }

    @Test
    void everyValueOfAnElementIsTypedByTheElement() {
        EnumToken<Level> levels =
                UnsafeFacts.enumToken(new ClassTypeRef(LEVEL), List.of("LOW", "HIGH"), MethodTable.EMPTY);
        AnnotationRef<Tag> tag = UnsafeFacts.annotationRef(
                UnsafeFacts.annotationType(
                        UnsafeFacts.interfaceToken(Types.of(TAG), MethodTable.EMPTY),
                        List.of(UnsafeFacts.annotationElement(TAG, "value", Types.STRING, Optional.empty())),
                        RetentionPolicy.CLASS,
                        Set.of(),
                        false),
                List.of(new AnnotationMember("value", AnnotationValues.string("a"))));

        assertThat(AnnotationValues.string("x").toCore()).isNotNull();
        assertThat(AnnotationValues.bool(true).toCore()).isNotNull();
        assertThat(AnnotationValues.int_(1).toCore()).isNotNull();
        assertThat(AnnotationValues.long_(2L).toCore()).isNotNull();
        assertThat(AnnotationValues.double_(3.0).toCore()).isNotNull();
        assertThat(AnnotationValues.float_(4.0f).toCore()).isNotNull();
        assertThat(AnnotationValues.byte_((byte) 5).toCore()).isNotNull();
        assertThat(AnnotationValues.short_((short) 6).toCore()).isNotNull();
        assertThat(AnnotationValues.char_('c').toCore()).isNotNull();
        assertThat(AnnotationValues.enum_(levels.constant("HIGH")).toCore()).isNotNull();
        assertThat(AnnotationValues.class_(UnsafeFacts.<String>finalClassToken(
                                Types.STRING, List.of(java.lang.constant.ConstantDescs.CD_Object), MethodTable.EMPTY))
                        .toCore())
                .isNotNull();
        assertThat(AnnotationValues.nested(tag).toCore()).isNotNull();
        assertThat(AnnotationValues.array(AnnotationValues.int_(1), AnnotationValues.int_(2))
                        .toCore())
                .isNotNull();
        assertThat(AnnotationValues.array(List.of(AnnotationValues.int_(1))).toCore())
                .isNotNull();
        assertThat(AnnotationValues.array()).isNotNull();
        assertThat(AnnotationValues.int_(1)).isEqualTo(AnnotationValues.int_(1));
        assertThat(AnnotationValues.int_(1).hashCode())
                .isEqualTo(AnnotationValues.int_(1).hashCode());
        assertThat(AnnotationValues.int_(1).toString()).isNotNull();
        assertThat(AnnotationValues.int_(1)).isNotEqualTo(AnnotationValues.int_(2));
        assertThat(AnnotationValues.int_(1)).isNotEqualTo("not a value");
        assertThatIllegalArgumentException()
                .isThrownBy(
                        () -> AnnotationValues.array(AnnotationValues.int_(1)).single())
                .withMessageContaining("no element of another");
    }

    @Test
    void aReferenceTypeUseCarriesItsAnnotations() {
        AnnotationUse tag = use(TAG);
        FinalClassToken<String> string = UnsafeFacts.finalClassToken(
                Types.STRING, List.of(java.lang.constant.ConstantDescs.CD_Object), MethodTable.EMPTY);
        RefToken<String> annotated = UnsafeFacts.annotated(string, tag);

        assertThat(annotated).isInstanceOf(AnnotatedRefToken.class);
        assertThat(((AnnotatedRefToken<String>) annotated).token()).isSameAs(string);
        assertThat(((AnnotatedRefToken<String>) annotated).annotations()).containsExactly(tag);
        assertThat(annotated.typeRef()).isInstanceOf(ClassTypeRef.class);
        assertThat(((ClassTypeRef) annotated.typeRef()).annotations()).containsExactly(tag);
        assertThat(annotated.erasure()).isEqualTo(STRING);
        assertThat(annotated.toString()).contains("Tag");
        assertThat(annotated).isEqualTo(UnsafeFacts.annotated(string, tag));
        assertThat(annotated.hashCode())
                .isEqualTo(UnsafeFacts.annotated(string, tag).hashCode());
        assertThat(annotated).isNotEqualTo(UnsafeFacts.annotated(string, use(ClassDesc.of("p", "Other"))));
        assertThat(annotated).isNotEqualTo("not a token");
        assertThat(new AnnotatedRefToken<>(string, List.of()).toString()).isEqualTo(string.toString());
        assertThat(UnsafeFacts.annotated(string)).isSameAs(string);

        InterfaceToken<List<String>> list = UnsafeFacts.interfaceToken(
                Types.parameterized(ClassDesc.of("java.util", "List"), Types.STRING), MethodTable.EMPTY);
        RefToken<List<String>> listUse = UnsafeFacts.annotated(list, tag);
        assertThat(((me.supcheg.javafile.type.ParameterizedTypeRef) listUse.typeRef()).annotations())
                .containsExactly(tag);
        ArrayToken<String[], String> array = ArrayToken.of(string);
        assertThat(((me.supcheg.javafile.type.ArrayTypeRef)
                                UnsafeFacts.annotated(array, tag).typeRef())
                        .annotations())
                .containsExactly(tag);
        TypeVarToken<Object> variable = UnsafeFacts.typeVarToken(
                new me.supcheg.javafile.type.TypeVarRef("T"), java.lang.constant.ConstantDescs.CD_Object);
        assertThat(((me.supcheg.javafile.type.TypeVarRef)
                                UnsafeFacts.annotated(variable, tag).typeRef())
                        .annotations())
                .containsExactly(tag);
    }

    @Test
    void aShapeWithAHeritageAndNoAnnotationIsMade() {
        TypeShape<DeclaredKind.OpenClass> shape = UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("p", "S_"), "0".repeat(64), () -> "javafile-facts-canonical 11"),
                DeclaredKind.OPEN_CLASS,
                ClassDesc.of("p", "S"),
                List.of(),
                List.of(java.lang.constant.ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                false,
                () -> new Heritage.Told(List.of(), List.of(), List.of()));

        assertThat(shape.annotations()).isEmpty();
    }
}
