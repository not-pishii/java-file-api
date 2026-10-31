import gen.facts.java.lang.String_;
import gen.facts.p.Annotated_;
import gen.facts.p.Author_;
import gen.facts.p.Level_;
import gen.facts.p.Tag_;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.typed.ClassHeader;
import me.supcheg.javafile.typed.MemberBodies;
import me.supcheg.javafile.typed.TypeUses;
import me.supcheg.javafile.typed.TypedJavaFile;
import p.Author;
import p.Tag;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// The annotations of a metamodel: the fact of an annotation type, the
/// type-state builder of a use of it, and what the typed layer renders of a
/// use on a class, a member, a parameter and a type use.
public final class Annotations {
    private Annotations() {}

    private static AnnotationRef<p.Tag> tag(String value) {
        return Tag_.annotation().value(AnnotationValues.string(value)).build();
    }

    private static AnnotationRef<p.Author> author() {
        return Author_.annotation()
                .name(AnnotationValues.string("Ada"))
                .year(AnnotationValues.int_(1815))
                .tags(AnnotationValues.array(AnnotationValues.nested(tag("type"))))
                .build();
    }

    public static void theFactOfAnAnnotationTypeTellsItsElementsAndTheirDefaults() {
        assertThat(Author_.ANNOTATION.elements()).extracting(element -> element.name())
                .containsExactly("name", "year", "tags");
        assertThat(Author_.ANNOTATION.required()).extracting(element -> element.name())
                .containsExactly("name");
        assertThat(Author_.ANNOTATION.retention()).isEqualTo(RetentionPolicy.RUNTIME);
        assertThat(Author_.ANNOTATION.targets()).contains(ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER);
        assertThat(Author_.ANNOTATION.repeatable()).isTrue();
        assertThat(Tag_.ANNOTATION.element("rank").orElseThrow().defaultValue()).isPresent();
        assertThat(Author_.ANNOTATION.element("name").orElseThrow().defaultValue()).isEmpty();
    }

    public static void aRequiredElementIsGivenBeforeTheUseIsBuilt() {
        AnnotationRef<p.Author> author = author();
        assertThat(author.members()).extracting(member -> member.name()).containsExactly("name", "year", "tags");
        assertThat(author.toUse().members()).extracting(member -> member.name())
                .containsExactly("name", "year", "tags");
    }

    public static void aFactCarriesTheAnnotationsOfItsTypeMemberAndParameters() {
        assertThat(Annotated_.Data.SHAPE.annotations()).extracting(use -> use.type().displayName())
                .containsExactly("Author");
        assertThat(Annotated_.new_.traits().annotations()).extracting(use -> use.type().displayName())
                .containsExactly("Author");
        assertThat(Annotated_.greet_String.traits().annotations()).extracting(use -> use.type().displayName())
                .containsExactly("Author", "Tag");
        assertThat(Annotated_.greet_String.traits().parameterAnnotations()).hasSize(1);
        assertThat(Annotated_.greet_String.traits().parameterAnnotations().getFirst())
                .extracting(use -> use.type().displayName())
                .containsExactly("Tag");
    }

    public static void aClassAndItsMembersAreRenderedWithTheAnnotationsGiven(Typed typed) {
        String source = typed.declare(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.withAnnotation(author()).extendsObject().members();
                var word = cb.declareField("word", TypeUses.annotated(String_.TOKEN, tag("use")));
                var greet = cb.annotated(tag("method"))
                        .annotatedParameter(0, tag("parameter"))
                        .declareMethod("greet", String_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.initialize(word, literal("hi"));
                bodies.<String, String>define(greet, (b, self, who) -> b.return_(literal("hi")));
                return bodies;
            }
        });

        assertThat(source)
                .contains("@Author(name = \"Ada\", year = 1815, tags = {")
                .contains("@Tag(\"type\")")
                .contains("@Tag(\"use\") String word")
                .contains("@Tag(\"method\")")
                .contains("public String greet(@Tag(\"parameter\") String v0)");
    }

    public static void anEnumValueIsAValueOfAnElement() {
        AnnotationRef<p.Tag> tag = Tag_.annotation()
                .value(AnnotationValues.string("levels"))
                .levels(AnnotationValues.array(
                        AnnotationValues.enum_(Level_.LOW), AnnotationValues.enum_(Level_.HIGH)))
                .rank(AnnotationValues.int_(2))
                .type(AnnotationValues.class_(gen.facts.java.lang.String_.TOKEN))
                .build();
        assertThat(tag.members()).extracting(member -> member.name())
                .containsExactly("value", "levels", "rank", "type");
        assertThat(tag.members().get(1).value().toString()).contains("LOW");
    }

    public static void aUseOfAnElementTheTypeDoesNotHaveIsRejected() {
        assertThat(Author_.ANNOTATION.element("missing")).isEmpty();
        assertThat(Author_.ANNOTATION.required()).extracting(element -> element.name())
                .doesNotContain("year", "tags");
    }
}
