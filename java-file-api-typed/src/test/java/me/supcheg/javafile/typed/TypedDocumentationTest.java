package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.fixtures.Tag;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.annotation.RetentionPolicy_;
import me.supcheg.javafile.typed.testfacts.java.lang.annotation.Retention_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Unconstrained_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The documentation comments of the typed layer (6.5): every declaration
/// and member form renders one where Java puts it, the text is written as
/// given, and a text that would end the comment is refused.
class TypedDocumentationTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final DocComment DOC = DocComment.of("A documented member.");
    private static final DocComment TAGGED = DocComment.of(d ->
            d.paragraph("Returns the value.").param("value", "the parameter").returns("the value"));

    private static me.supcheg.javafile.facts.AnnotationRef<Tag> tag(String value) {
        return Tag_.annotation().value(AnnotationValues.string(value)).build();
    }

    @Test
    void aClassAndItsFieldsMethodsAndConstructorRenderTheirComments() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.withDoc(DOC);
                        h.withDoc("A documented class.");
                        var cb = h.extendsObject().members();
                        cb.withDoc("A class of prose.");
                        var a = cb.declareField("a", PrimitiveToken.INT, DOC, tag("a"));
                        var b = cb.declareField(
                                "b",
                                PrimitiveToken.INT,
                                Set.of(Modifier.PUBLIC, Modifier.FINAL, Modifier.TRANSIENT),
                                DOC);
                        var c = cb.declareMutableField("c", PrimitiveToken.INT, DOC, tag("c"));
                        var d = cb.declareMutableField(
                                "d",
                                PrimitiveToken.INT,
                                Set.of(Modifier.PUBLIC, Modifier.TRANSIENT, Modifier.VOLATILE),
                                DOC);
                        var e = cb.declareStaticField("E", PrimitiveToken.INT, DOC, tag("e"));
                        var f = cb.declareMutableStaticField("F", PrimitiveToken.INT, DOC, tag("f"));
                        var m = cb.documented(TAGGED).declareMethod("m", PrimitiveToken.INT);
                        var sm = cb.documented("A documented static method.")
                                .declareStaticMethod("sm", PrimitiveToken.INT);
                        var annotated = cb.documented(DOC)
                                .annotated(tag("method"))
                                .declareMethod("annotated", PrimitiveToken.INT);
                        var sync = cb.documented(DOC)
                                .modified(Modifier.SYNCHRONIZED)
                                .declareMethod("sync", PrimitiveToken.INT);
                        var ctor = cb.documented(DOC).declareConstructor();
                        var bodies = cb.bodies();
                        bodies.initialize(a, literal(0));
                        bodies.initialize(b, literal(0));
                        bodies.initialize(c, literal(0));
                        bodies.initialize(d, literal(0));
                        bodies.initialize(e, literal(0));
                        bodies.initialize(f, literal(0));
                        bodies.define(m, (body, self) -> body.return_(literal(0)));
                        bodies.define(sm, body -> body.return_(literal(0)));
                        bodies.define(annotated, (body, self) -> body.return_(literal(0)));
                        bodies.define(sync, (body, self) -> body.return_(literal(0)));
                        bodies.define(ctor, (body, self) -> body.end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("/**\n * A class of prose.\n */\npublic final class Probe")
                .contains(" * A documented member.")
                .contains(" * A documented static method.")
                .contains("@return the value")
                .contains("@Tag(\"method\")");
    }

    @Test
    void aSubclassDocumentsItself() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Sub");
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var sh = h.extends_(Base_.extend(h.self()));
                        var cb = sh.members();
                        cb.withDoc(DOC);
                        cb.withDoc("A documented subclass.");
                        var ctor = cb.declareConstructor();
                        var bodies = cb.bodies(ctor, c -> c.super_(Base_.new_, (b, self) -> b.end()));
                        return bodies;
                    }
                });

        assertThat(file.render()).contains("/**\n * A documented subclass.\n */\npublic final class Sub");
        CompiledClasses.of(file);
    }

    @Test
    void anInterfaceDocumentsItsMethodsAndConstant() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Contract");
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.withDoc(DOC);
                        ib.withDoc("A documented interface.");
                        ib.documented(TAGGED).declareAbstractMethod("value", PrimitiveToken.INT, PrimitiveToken.INT);
                        var d = ib.documented(DOC).declareDefaultMethod("d", PrimitiveToken.INT);
                        var s = ib.documented("A static method.").declareStaticMethod("s", PrimitiveToken.INT);
                        var p = ib.documented(DOC).declarePrivateMethod("p", PrimitiveToken.INT);
                        var constant = ib.declareConstant("MAX", PrimitiveToken.INT, DOC, tag("constant"));
                        var bodies = ib.bodies();
                        bodies.initialize(constant, literal(10));
                        bodies.define(d, (b, self) -> b.return_(literal(0)));
                        bodies.define(s, b -> b.return_(literal(0)));
                        bodies.define(p, (b, self) -> b.return_(literal(0)));
                        return bodies;
                    }
                });

        assertThat(file.render())
                .contains(" * A documented interface.")
                .contains("@param value the parameter")
                .contains("@return the value")
                .contains(" * A static method.");
        CompiledClasses.of(file);
    }

    @Test
    void aRecordDocumentsItsComponentsMethodsAndStaticField() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Point");
        JavaFile file =
                TypedJavaFile.record(UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedRecordSpec() {
                    @Override
                    public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                        rb.withDoc(DOC);
                        rb.withDoc("A documented record.");
                        rb.component("x", PrimitiveToken.INT, DOC);
                        rb.component("y", PrimitiveToken.INT, DOC, tag("component"));
                        var m = rb.documented(TAGGED).declareMethod("m", PrimitiveToken.INT);
                        var s = rb.documented(DOC).declareStaticMethod("s", PrimitiveToken.INT);
                        var f = rb.declareStaticField("S", PrimitiveToken.INT, DOC, tag("static"));
                        rb.documented(DOC);
                        var compact = rb.declareCompactConstructor();
                        var bodies = rb.bodies();
                        bodies.initialize(f, literal(0));
                        bodies.define(m, (b, self) -> b.return_(literal(0)));
                        bodies.define(s, b -> b.return_(literal(0)));
                        bodies.define(compact, (b, self) -> b.end());
                        return bodies;
                    }
                });

        assertThat(file.render())
                .contains(" * A documented record.")
                .contains(" * A documented member.")
                .contains("@Tag(\"component\")")
                .contains("@Tag(\"static\")");
        CompiledClasses.of(file);
    }

    @Test
    void anEnumDocumentsItsConstantsFieldsAndMethods() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Suit");
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.withDoc(DOC);
                eb.withDoc("A documented enum.");
                eb.constant("A", DOC);
                eb.constant("B", DOC, List.of(), null);
                var field = eb.declareField("f", PrimitiveToken.INT, Set.of(Modifier.PRIVATE), DOC);
                var stored = eb.declareStaticField("S", PrimitiveToken.INT, DOC, tag("static"));
                var changing = eb.declareMutableStaticField("C", PrimitiveToken.INT, DOC, tag("changing"));
                var m = eb.documented(TAGGED).declareMethod("m", PrimitiveToken.INT);
                var bodies = eb.bodies();
                bodies.initialize(field, literal(0));
                bodies.initialize(stored, literal(0));
                bodies.initialize(changing, literal(0));
                bodies.define(m, (b, self) -> b.return_(literal(0)));
                return bodies;
            }
        });

        assertThat(file.render())
                .contains(" * A documented enum.")
                .contains(" * A documented member.")
                .contains("@Tag(\"static\")")
                .contains("@Tag(\"changing\")");
        CompiledClasses.of(file);
    }

    @Test
    void anAnnotationTypeDocumentsItselfAndEveryElementForm() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Every");
        JavaFile file = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.withDoc(DOC);
                        ab.withDoc("A documented annotation type.");
                        ab.element("plain", String_.TOKEN);
                        ab.element(
                                "unconstrained",
                                String_.TOKEN,
                                Unconstrained_.annotation()
                                        .value(AnnotationValues.string("u"))
                                        .build());
                        ab.element("marked", String_.TOKEN, tag("marked"));
                        ab.element("documented", String_.TOKEN, DOC, tag("documented"));
                        ab.element("defaulted", PrimitiveToken.INT, AnnotationValues.int_(0));
                        ab.element("defaultedMarked", PrimitiveToken.INT, AnnotationValues.int_(0), tag("dm"));
                        ab.element("defaultedDocumented", PrimitiveToken.INT, AnnotationValues.int_(0), DOC, tag("dd"));
                        ab.annotationElement("nested", Tag_.ANNOTATION);
                        ab.annotationElement("nestedMarked", Tag_.ANNOTATION, tag("nm"));
                        ab.annotationElement("nestedDocumented", Tag_.ANNOTATION, DOC, tag("nd"));
                        ab.annotationElement("nestedDefaulted", Tag_.ANNOTATION, AnnotationValues.nested(tag("value")));
                        ab.annotationElement(
                                "nestedDefaultedMarked",
                                Tag_.ANNOTATION,
                                AnnotationValues.nested(tag("value")),
                                tag("ndm"));
                        ab.annotationElement(
                                "nestedDefaultedDocumented",
                                Tag_.ANNOTATION,
                                AnnotationValues.nested(tag("value")),
                                DOC,
                                tag("ndd"));
                        ab.annotationArrayElement("array", Tag_.ANNOTATION);
                        ab.annotationArrayElement("arrayMarked", Tag_.ANNOTATION, tag("am"));
                        ab.annotationArrayElement("arrayDocumented", Tag_.ANNOTATION, DOC, tag("ad"));
                        ab.annotationArrayElement(
                                "arrayDefaulted",
                                Tag_.ANNOTATION,
                                AnnotationValues.array(AnnotationValues.nested(tag("value"))));
                        ab.annotationArrayElement(
                                "arrayDefaultedMarked",
                                Tag_.ANNOTATION,
                                AnnotationValues.array(AnnotationValues.nested(tag("value"))),
                                tag("adm"));
                        ab.annotationArrayElement(
                                "arrayDefaultedDocumented",
                                Tag_.ANNOTATION,
                                AnnotationValues.array(AnnotationValues.nested(tag("value"))),
                                DOC,
                                tag("add"));
                        return ab;
                    }
                });

        assertThat(file.render())
                .contains(" * A documented annotation type.")
                .contains("@Tag(\"marked\")")
                .contains("@Tag(\"add\")");
        CompiledClasses.of(file);
    }

    @Test
    void annotationsOnElementsAreReflectedOnTheCompiledAnnotationType() throws Exception {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Route");
        JavaFile file = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.element("value", String_.TOKEN, tag("v"));
                        ab.element("count", PrimitiveToken.INT, AnnotationValues.int_(1), tag("c"));
                        return ab;
                    }
                });

        CompiledClasses compiled = CompiledClasses.of(file);
        Method value = compiled.generated().getDeclaredMethod("value");
        Method count = compiled.generated().getDeclaredMethod("count");

        assertThat(value.getAnnotation(Tag.class).value()).isEqualTo("v");
        assertThat(value.getDefaultValue()).isNull();
        assertThat(count.getAnnotation(Tag.class).value()).isEqualTo("c");
        assertThat(count.getDefaultValue()).isEqualTo(1);
    }

    @Test
    void anAnnotationThatCannotAnnotateAMethodIsRefusedOnAnElement() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Route");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.element(
                                        "value",
                                        String_.TOKEN,
                                        Retention_.annotation()
                                                .value(AnnotationValues.enum_(RetentionPolicy_.RUNTIME))
                                                .build());
                                return ab;
                            }
                        }))
                .withMessageContaining("cannot annotate");
    }

    @Test
    void aCommentThatWouldEndTheCommentIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.withDoc("before */ after");
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("contains */");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.documented(DocComment.of("before */ after"));
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("contains */");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.component("x", PrimitiveToken.INT, DocComment.of("before */ after"));
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("contains */");
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
                            @Override
                            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                                eb.constant("A", DocComment.of("before */ after"));
                                return eb.bodies();
                            }
                        }))
                .withMessageContaining("contains */");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.annotationType(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                            @Override
                            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                                ab.element("value", String_.TOKEN, DocComment.of("before */ after"));
                                return ab;
                            }
                        }))
                .withMessageContaining("contains */");
    }

    @Test
    void aDocumentedFieldOfTheWrongModifiersIsRefused() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField("a", PrimitiveToken.INT, Set.of(), DOC);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("cannot be");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField("a", PrimitiveToken.INT, Set.of(Modifier.PUBLIC), DOC);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("cannot be");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareField(
                                        "a",
                                        PrimitiveToken.INT,
                                        Set.of(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC),
                                        DOC);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("cannot be");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareMutableField("a", PrimitiveToken.INT, Set.of(), DOC);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("cannot be");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareMutableField(
                                        "a", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.FINAL), DOC);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("cannot be");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareMutableField(
                                        "a", PrimitiveToken.INT, Set.of(Modifier.PUBLIC, Modifier.STATIC), DOC);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("cannot be");
    }

    @Test
    void aRecordRefusesADocumentedMutableStaticField() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedRecordSpec() {
                            @Override
                            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                                rb.declareMutableStaticField("C", PrimitiveToken.INT, DOC);
                                return rb.bodies();
                            }
                        }))
                .withMessageContaining("cannot be mutable");
    }

    @Test
    void aMultiLineCommentWithTagsIsRenderedAsWritten() {
        DocComment doc = DocComment.of(d -> d.paragraph("The first line.\nThe second line.")
                .param("x", "the value")
                .returns("the result"));
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Tagged");
        JavaFile file = TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), desc, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        ib.withDoc(doc);
                        ib.documented(doc).declareAbstractMethod("compute", PrimitiveToken.INT, PrimitiveToken.INT);
                        return ib.bodies();
                    }
                });

        assertThat(file.render()).contains("""
                /**
                 * The first line.
                 * The second line.
                 *
                 * @param x the value
                 * @return the result
                 */
                public interface Tagged {""");
        CompiledClasses.of(file);
    }
}
