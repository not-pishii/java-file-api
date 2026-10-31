package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Author_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The annotations of the typed layer: a class, a member, a parameter and a
/// type use are rendered as javac accepts them, the use built by the
/// type-state builder of the annotation type, compiled under every lint and
/// run.
class AnnotationsTest {

    private static AnnotationRef<me.supcheg.javafile.typed.fixtures.Tag> tag(String value) {
        return Tag_.annotation().value(AnnotationValues.string(value)).build();
    }

    private static AnnotationRef<me.supcheg.javafile.typed.fixtures.Author> author() {
        return Author_.annotation()
                .name(AnnotationValues.string("Ada"))
                .year(AnnotationValues.int_(1815))
                .tags(AnnotationValues.array(AnnotationValues.nested(tag("type"))))
                .build();
    }

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Annotated"), spec);
    }

    @Test
    void aClassItsFieldAndItsMethodAreRenderedWithTheirAnnotationsAndRun() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.withAnnotation(author()).extendsObject().members();
                var name = cb.declareField("name", TypeUses.annotated(String_.TOKEN, tag("field")));
                var greet = cb.annotated(tag("method"))
                        .annotatedParameter(0, tag("parameter"))
                        .declareStaticMethod("greet", String_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.initialize(name, literal("hi"));
                bodies.define(greet, (b, who) -> b.return_(who));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("@Author(name = \"Ada\", year = 1815, tags = {")
                .contains("@Tag(\"type\")")
                .contains("public final @Tag(\"field\") String name = \"hi\";")
                .contains("@Tag(\"method\")")
                .contains("public static String greet(@Tag(\"parameter\") String v0) {");
        assertThat(compiled.invoke("greet", "x")).isEqualTo("x");
    }

    @Test
    void aNestedAndArrayValueIsRenderedAsJavacAcceptsIt() {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.withAnnotation(author()).extendsObject().members();
                var made = cb.declareStaticMethod("made", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(made, b -> b.return_(literal("made")));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("tags = {")
                .contains("@Tag(\"type\")")
                .doesNotContain("tags = @Tag");
    }

    @Test
    void aTypeUseOfAListAndAMutableFieldAreAnnotatedToo() throws Throwable {
        AnnotationRef<me.supcheg.javafile.typed.fixtures.Tag> tagged = tag("field");
        RefToken<String> annotated = TypeUses.annotated(String_.TOKEN, List.of(tagged));
        assertThat(Tokens.annotated(annotated)).isSameAs(String_.TOKEN);
        assertThat(Tokens.annotated(String_.TOKEN)).isSameAs(String_.TOKEN);
        assertThat(Tokens.isReifiable(annotated)).isTrue();
        assertThat(Rivals.mayConvert(annotated, ConstantDescs.CD_String)).isTrue();
        assertThat(
                        ExceptionType.of(UnsafeFacts.annotated(IOException_.TOKEN, tagged.toUse()))
                                instanceof ExceptionType.OfClass)
                .isTrue();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ExceptionType.of(me.supcheg.javafile.facts.ArrayToken.of(String_.TOKEN)));
        assertThat(Serial.isSerializable(annotated, List.of())).isTrue();
        assertThat(Constants.fold(new Node.Lit(new me.supcheg.javafile.code.FloatLiteral(1.0f))))
                .isInstanceOf(Constants.Folded.OfDouble.class);
        assertThat(new Lowering(UnsafeFacts.unverifiedClasspath())
                        .hasTwin(annotated, me.supcheg.javafile.typed.testfacts.java.lang.String_.indexOf_int_int_int))
                .isFalse();

        Subtyping.Declaring declaring = new Subtyping.Declaring(ClassDesc.of("p", "Out"), List.of(), List.of());
        assertThat(Subtyping.prove(declaring, annotated, me.supcheg.javafile.type.Types.OBJECT))
                .isInstanceOf(Subtyping.Verdict.Proven.class);
        assertThat(Subtyping.prove(
                        declaring,
                        annotated,
                        me.supcheg.javafile.type.Types.of(ClassDesc.of("java.lang", "CharSequence"))))
                .isInstanceOf(Subtyping.Verdict.Proven.class);
        assertThat(Subtyping.prove(
                        declaring,
                        TypeUses.annotated(
                                new me.supcheg.javafile.typed.testfacts.java.util.List_<>(String_.TOKEN).token, tagged),
                        me.supcheg.javafile.type.Types.parameterized(
                                ClassDesc.of("java.util", "List"), me.supcheg.javafile.type.Types.STRING)))
                .isInstanceOf(Subtyping.Verdict.class);
        RefToken<String[]> array = me.supcheg.javafile.facts.ArrayToken.of(String_.TOKEN);
        assertThat(Subtyping.prove(
                        declaring,
                        TypeUses.annotated(array, tagged),
                        me.supcheg.javafile.type.Types.array(me.supcheg.javafile.type.Types.STRING)))
                .isInstanceOf(Subtyping.Verdict.Proven.class);

        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().withAnnotation(author()).members();
                var mutable = cb.declareMutableField("word", String_.TOKEN, tagged);
                var plain = cb.annotated().declareVoidStaticMethod("plain");
                var bodies = cb.bodies();
                bodies.initialize(mutable, literal("x"));
                bodies.define(plain, b -> b.end());
                return bodies;
            }
        }));

        assertThat(compiled.source()).contains("@Tag(\"field\")").contains("public String word = \"x\";");
        assertThat(compiled.invoke("getClass")).isNotNull();
    }

    @Test
    void onlyTheParametersGivenAnnotationsAreRenderedWithThem() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var greet = cb.annotatedParameter(0, tag("first"))
                        .declareStaticMethod("greet", String_.TOKEN, String_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(greet, (b, first, second) -> b.return_(first));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public static String greet(@Tag(\"first\") String v0, String v1) {")
                .doesNotContain("@Tag(\"first\") String v1");
        assertThat(compiled.invoke("greet", "a", "b")).isEqualTo("a");
    }

    @Test
    void theAnnotationsOfAParameterAddToThoseAlreadyGiven() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var greet = cb.annotatedParameter(0, tag("first"))
                        .annotatedParameter(0, author())
                        .declareStaticMethod("greet", String_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(greet, (b, who) -> b.return_(who));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("@Tag(\"first\") @Author(name = \"Ada\", year = 1815, tags = {")
                .contains("String v0)");
        assertThat(compiled.invoke("greet", "a")).isEqualTo("a");
    }
}
