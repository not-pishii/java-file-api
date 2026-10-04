package me.supcheg.javafile.transform;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.builder.AnnotationTypeBuilder;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Optional;

import static me.supcheg.javafile.code.Exprs.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// A transform loses no comment: neither that of the declaration it rebuilds
/// nor those of the members it passes on.
class DocTransformTest {
    private static final DocComment TYPE = DocComment.of("The type.");
    private static final DocComment MEMBER = DocComment.of("A member.");

    @Test
    void aClassKeepsItsCommentAndThoseOfItsMembers() {
        ClassDecl original = new ClassBuilder(ClassDesc.of("p", "C"))
                .withDoc(TYPE)
                .withField("count", Types.INT, fb -> fb.withDoc(MEMBER))
                .withConstructor(ctor -> ctor.withDoc(MEMBER))
                .withVoidMethod("run", mb -> mb.withDoc(MEMBER))
                .withAbstractMethod("area", Types.INT, mb -> mb.withDoc(MEMBER))
                .build();

        ClassDecl transformed = Transforms.transform(original, (builder, member) -> builder.accept(member));

        assertThat(transformed).isEqualTo(original);
        assertThat(transformed.doc()).contains(TYPE);
        assertThat(transformed.members())
                .hasSize(4)
                .allSatisfy(member -> assertThat(member).extracting("doc").isEqualTo(Optional.of(MEMBER)));
    }

    @Test
    void anInterfaceARecordAnEnumAndAnAnnotationTypeKeepTheirComments() {
        InterfaceDecl anInterface = new InterfaceBuilder(ClassDesc.of("p", "I"))
                .withDoc(TYPE)
                .withConstant("N", Types.INT, kb -> kb.withDoc(MEMBER).withInitializer(literal(1)))
                .withDefaultMethod("one", Types.INT, mb -> mb.withDoc(MEMBER).withBody(b -> b.return_(literal(1))))
                .withStaticMethod("two", Types.INT, mb -> mb.withDoc(MEMBER).withBody(b -> b.return_(literal(2))))
                .build();
        RecordDecl aRecord = new RecordBuilder(ClassDesc.of("p", "R"))
                .withDoc(d -> d.paragraph("The type.").param("x", "the x"))
                .withComponent("x", Types.INT)
                .withStaticField("ZERO", Types.INT, fb -> fb.withDoc(MEMBER).withInitializer(literal(0)))
                .build();
        EnumDecl anEnum = new EnumBuilder(ClassDesc.of("p", "E"))
                .withDoc(TYPE)
                .withConstant("A", c -> c.withDoc(MEMBER))
                .withConstant("B", c -> c.withDoc(MEMBER).withVoidMethod("run", mb -> mb.withDoc(MEMBER)))
                .withConstructor(ctor -> ctor.withDoc(MEMBER))
                .build();
        AnnotationTypeDecl anAnnotation = new AnnotationTypeBuilder(ClassDesc.of("p", "A"))
                .withDoc(TYPE)
                .withElement("value", Types.INT, MEMBER)
                .withElement("other", Types.INT, AnnotationValues.literal(1), MEMBER)
                .build();

        assertThat(Transforms.transform(anInterface, (builder, member) -> builder.accept(member)))
                .isEqualTo(anInterface);
        assertThat(Transforms.transform(aRecord, (builder, member) -> builder.accept(member)))
                .isEqualTo(aRecord);
        assertThat(Transforms.transform(anEnum, (builder, member) -> builder.accept(member)))
                .isEqualTo(anEnum);
        assertThat(Transforms.transform(anAnnotation, (builder, element) -> builder.accept(element)))
                .isEqualTo(anAnnotation);
        assertThat(anInterface.doc()).contains(TYPE);
        assertThat(anInterface.members())
                .allSatisfy(member -> assertThat(member).extracting("doc").isEqualTo(Optional.of(MEMBER)));
        assertThat(aRecord.members())
                .allSatisfy(member -> assertThat(member).extracting("doc").isEqualTo(Optional.of(MEMBER)));
        assertThat(anEnum.constants())
                .allSatisfy(constant -> assertThat(constant.doc()).contains(MEMBER));
        assertThat(anEnum.members())
                .allSatisfy(member -> assertThat(member).extracting("doc").isEqualTo(Optional.of(MEMBER)));
        assertThat(anAnnotation.elements())
                .allSatisfy(element -> assertThat(element.doc()).contains(MEMBER));
    }

    @Test
    void theBodyOfAnEnumConstantIsTransformedAndTheConstantKeepsItsComment() {
        EnumDecl original = new EnumBuilder(ClassDesc.of("p", "E"))
                .withConstant(
                        "A",
                        c -> c.withDoc(MEMBER)
                                .withVoidMethod("run", mb -> mb.withDoc(MEMBER))
                                .withVoidMethod("debug", mb -> {}))
                .build();

        EnumDecl transformed = Transforms.transform(original, (builder, member) -> {
            if (!(member instanceof MethodDecl method && method.name().equals("debug"))) {
                builder.accept(member);
            }
        });

        assertThat(transformed.constants()).singleElement().satisfies(constant -> {
            assertThat(constant.doc()).contains(MEMBER);
            assertThat(constant.body()).singleElement().extracting("doc").isEqualTo(Optional.of(MEMBER));
        });
    }

    @Test
    void aTransformOfAFileRendersTheCommentsItWasBuiltWith() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("p", "C"), cb -> cb.withDoc(TYPE).withField("count", Types.INT, fb -> fb.withDoc(MEMBER)));

        JavaFile transformed = file.transformClass((builder, member) -> {
            if (member instanceof FieldDecl f) {
                builder.accept(
                        new FieldDecl("renamed", f.type(), f.annotations(), f.modifiers(), f.initializer(), f.doc()));
            }
        });

        assertThat(transformed.render()).isEqualTo("""
                        package p;

                        /**
                         * The type.
                         */
                        public class C {
                            /**
                             * A member.
                             */
                            public int renamed;
                        }
                        """);
    }
}
