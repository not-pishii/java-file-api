package me.supcheg.javafile.render;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.doc.DocText;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/// A documentation comment names a type as the code of the file does, and
/// changes neither the imports nor a name in the code.
class DocImportsTest {
    private static final ClassDesc HOLDER = ClassDesc.of("com.example", "Holder");
    private static final ClassDesc UTIL_LIST = ClassDesc.of("java.util", "List");
    private static final ClassDesc AWT_LIST = ClassDesc.of("java.awt", "List");
    private static final ClassDesc ENTRY = ClassDesc.of("java.util", "Map").nested("Entry");
    private static final SourceRenderer.Format MARKDOWN = SourceRenderer.standardFormat(DocStyle.MARKDOWN);

    private static DocComment linking(ClassDesc... types) {
        return DocComment.of(d -> d.paragraph(DocText.of(t -> {
            for (ClassDesc type : types) {
                t.link(DocRef.type(type)).text(" ");
            }
        })));
    }

    private static String render(Consumer<ClassBuilder> spec) {
        return JavaFile.class_(HOLDER, spec::accept).render(MARKDOWN);
    }

    private static String withoutComments(String source) {
        return source.lines().filter(line -> !line.strip().startsWith("///")).collect(Collectors.joining("\n"));
    }

    @Test
    void aTypeOnlyACommentNamesIsQualifiedAndNotImported() {
        assertThat(render(cb -> cb.withDoc(linking(UTIL_LIST, ENTRY)))).isEqualTo("""
                        package com.example;

                        /// [java.util.List] [java.util.Map.Entry]
                        public class Holder {
                        }
                        """);
    }

    @Test
    void aTypeTheCodeImportsIsNamedByItsSimpleNameAlsoBeforeTheCodeThatImportsIt() {
        assertThat(render(cb -> cb.withDoc(linking(UTIL_LIST, ENTRY))
                        .withField("list", Types.of(UTIL_LIST))
                        .withField("entry", Types.of(ENTRY))))
                .isEqualTo("""
                        package com.example;

                        import java.util.List;
                        import java.util.Map.Entry;

                        /// [List] [Entry]
                        public class Holder {
                            public List list;

                            public Entry entry;
                        }
                        """);
    }

    @Test
    void aTypeThatNeedsNoImportIsNamedByItsSimpleName() {
        assertThat(render(cb -> cb.withDoc(linking(
                                ConstantDescs.CD_String,
                                ClassDesc.of("com.example", "Neighbour"),
                                HOLDER,
                                HOLDER.nested("Part")))
                        .withNestedClass(HOLDER.nested("Part"), part -> {})))
                .isEqualTo("""
                        package com.example;

                        /// [String] [Neighbour] [Holder] [Part]
                        public class Holder {
                            public class Part {
                            }
                        }
                        """);
    }

    @Test
    void aTypeWhoseSimpleNameMeansAnotherInTheFileIsQualified() {
        assertThat(render(cb -> cb.withDoc(linking(AWT_LIST, UTIL_LIST, ClassDesc.of("java.lang", "Part")))
                        .withField("list", Types.of(UTIL_LIST))
                        .withNestedClass(HOLDER.nested("Part"), part -> part.withDoc(linking(AWT_LIST)))))
                .isEqualTo("""
                        package com.example;

                        import java.util.List;

                        /// [java.awt.List] [List] [java.lang.Part]
                        public class Holder {
                            public List list;

                            /// [java.awt.List]
                            public class Part {
                            }
                        }
                        """);
    }

    @Test
    void aNestedTypeOfThePackageThatIsNotImportedIsNamedThroughItsOuterType() {
        assertThat(render(cb -> cb.withDoc(
                        linking(ClassDesc.of("com.example", "Neighbour").nested("Inner")))))
                .contains("/// [Neighbour.Inner]");
    }

    @Test
    void aCommentThatNamesATypeFirstDoesNotTakeItsSimpleNameFromTheCode() {
        Consumer<ClassBuilder> code =
                cb -> cb.withField("awt", Types.of(AWT_LIST)).withField("util", Types.of(UTIL_LIST));
        String plain = render(code);
        String documented = render(code.andThen(cb -> cb.withDoc(linking(UTIL_LIST, AWT_LIST))));

        assertThat(documented).contains("/// [java.util.List] [List]");
        assertThat(withoutComments(documented)).isEqualTo(withoutComments(plain));
        assertThat(plain).contains("import java.awt.List;").contains("public java.util.List util;");
    }

    @Test
    void theParametersAndTheExceptionOfATagAreNamedLikeTheLinks() {
        String source = render(cb -> cb.withField("list", Types.of(UTIL_LIST))
                .withVoidMethod(
                        "run",
                        mb -> mb.withDoc(d -> d.paragraph(DocText.of(
                                        t -> t.link(DocRef.method(HOLDER, "run", UTIL_LIST, AWT_LIST.arrayType()))))
                                .throws_(ClassDesc.of("java.io", "IOException"), "never")
                                .see(DocRef.constructor(UTIL_LIST, AWT_LIST)))));

        assertThat(source)
                .contains("    /// [Holder#run(List, java.awt.List\\[\\])]\n")
                .contains("    /// @throws java.io.IOException never\n")
                .contains("    /// @see List#List(java.awt.List)\n")
                .doesNotContain("import java.io")
                .doesNotContain("import java.awt");
    }
}
