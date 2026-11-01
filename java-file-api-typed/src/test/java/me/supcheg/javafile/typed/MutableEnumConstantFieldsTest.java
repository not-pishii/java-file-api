package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.fixtures.Tag;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.reflect.Field;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.assignStaticField;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static org.assertj.core.api.Assertions.assertThat;

/// The mutable fields of a constant body: an instance field and a static one, declared in every form
/// of annotation and documentation, initialised by the body, assigned by a helper method and read
/// back. javac accepts both in the body of a constant, which is an anonymous class (JLS 15.9.5), and
/// warns of neither under `-Xlint:all`.
class MutableEnumConstantFieldsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Plan");

    private static JavaFile plan() {
        return TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var apply = eb.declareAbstractMethod("apply", PrimitiveToken.INT);
                eb.constant("A", body -> {
                    var value = body.documented("The value of the constant.")
                            .declareMutableField("value", PrimitiveToken.INT);
                    var tagged = body.declareMutableField(
                            "tagged",
                            PrimitiveToken.INT,
                            Tag_.annotation()
                                    .value(AnnotationValues.string("tagged field"))
                                    .build());
                    var documented = body.declareMutableField(
                            "documented",
                            PrimitiveToken.INT,
                            DocComment.of("A documented annotated field."),
                            Tag_.annotation()
                                    .value(AnnotationValues.string("documented field"))
                                    .build());
                    var total = body.documented("The total of the constants.")
                            .declareMutableStaticField("total", PrimitiveToken.INT);
                    var taggedTotal = body.declareMutableStaticField(
                            "taggedTotal",
                            PrimitiveToken.INT,
                            Tag_.annotation()
                                    .value(AnnotationValues.string("tagged static field"))
                                    .build());
                    var documentedTotal = body.declareMutableStaticField(
                            "documentedTotal",
                            PrimitiveToken.INT,
                            DocComment.of("A documented annotated static field."),
                            Tag_.annotation()
                                    .value(AnnotationValues.string("documented static field"))
                                    .build());
                    var bump = body.declareVoidMethod("bump");
                    body.initialize(value, literal(1));
                    body.initialize(tagged, literal(2));
                    body.initialize(documented, literal(3));
                    body.initialize(total, literal(0));
                    body.initialize(taggedTotal, literal(0));
                    body.initialize(documentedTotal, literal(0));
                    body.override(
                            apply,
                            (b, self) -> b.exec(voidCall(self, bump))
                                    .return_(addInt(field(self, value), staticField(total))));
                    body.define(
                            bump,
                            (b, self) -> b.exec(assignField(self, value, addInt(field(self, value), literal(1))))
                                    .exec(assignStaticField(total, addInt(staticField(total), literal(10))))
                                    .end());
                });
                return eb.bodies();
            }
        });
    }

    @Test
    void aConstantBodyDeclaresMutableInstanceAndStaticFieldsAndAssignsThem() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(plan());

        assertThat(compiled.source())
                .contains("        /**\n         * The value of the constant.\n         */\n"
                        + "        public int value = 1;")
                .contains("        @Tag(\"tagged field\")\n        public int tagged = 2;")
                .contains("        /**\n         * A documented annotated field.\n         */\n"
                        + "        @Tag(\"documented field\")\n        public int documented = 3;")
                .contains("        /**\n         * The total of the constants.\n         */\n"
                        + "        public static int total = 0;")
                .contains("        @Tag(\"tagged static field\")\n        public static int taggedTotal = 0;")
                .contains("        /**\n         * A documented annotated static field.\n         */\n"
                        + "        @Tag(\"documented static field\")\n        public static int documentedTotal = 0;")
                .contains("this.value = this.value + 1;")
                .contains("total = total + 10;");

        Object constant = compiled.generated().getField("A").get(null);
        var apply = constant.getClass().getDeclaredMethod("apply");
        apply.trySetAccessible();
        assertThat(apply.invoke(constant)).isEqualTo(12);
        assertThat(apply.invoke(constant)).isEqualTo(23);

        Field value = constant.getClass().getDeclaredField("value");
        value.trySetAccessible();
        assertThat(value.get(constant)).isEqualTo(3);
        Field total = constant.getClass().getDeclaredField("total");
        total.trySetAccessible();
        assertThat(total.get(null)).isEqualTo(20);
        Field tagged = constant.getClass().getDeclaredField("tagged");
        tagged.trySetAccessible();
        assertThat(tagged.getAnnotation(Tag.class).value()).isEqualTo("tagged field");
        Field documented = constant.getClass().getDeclaredField("documented");
        documented.trySetAccessible();
        assertThat(documented.getAnnotation(Tag.class).value()).isEqualTo("documented field");
        Field taggedTotal = constant.getClass().getDeclaredField("taggedTotal");
        taggedTotal.trySetAccessible();
        assertThat(taggedTotal.getAnnotation(Tag.class).value()).isEqualTo("tagged static field");
        Field documentedTotal = constant.getClass().getDeclaredField("documentedTotal");
        documentedTotal.trySetAccessible();
        assertThat(documentedTotal.getAnnotation(Tag.class).value()).isEqualTo("documented static field");
    }

    @Test
    void aMutableFieldReturnsTheFactItsDeclarationPromises() {
        MutableFieldRef<?, ?>[] instance = new MutableFieldRef<?, ?>[1];
        MutableStaticFieldRef<?>[] shared = new MutableStaticFieldRef<?>[1];
        JavaFile file = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", body -> {
                    var value = body.declareMutableField("value", PrimitiveToken.INT);
                    var total = body.declareMutableStaticField("total", PrimitiveToken.INT);
                    instance[0] = value;
                    shared[0] = total;
                    body.initialize(value, literal(0));
                    body.initialize(total, literal(0));
                });
                return eb.bodies();
            }
        });

        assertThat(file.render()).contains("public int value = 0;").contains("public static int total = 0;");
        assertThat(instance[0].name()).isEqualTo("value");
        assertThat(shared[0].name()).isEqualTo("total");
    }
}
