package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class GeneratedMessagesCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc BUNDLE = ClassDesc.of("java.util", "ResourceBundle");

    @Test
    void theGeneratedMessagesClassCompiles() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Messages"), cb -> cb.withModifiers(
                        Modifier.FINAL)
                .withField("bundle", Types.of(BUNDLE), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                .withConstructor(ctor -> ctor.withModifiers(Modifier.PUBLIC)
                        .withParam("bundle", Types.of(BUNDLE))
                        .withBody(b -> b.assign(b.field(b.this_(), "bundle"), b.field("bundle"))))
                .withMethod("greeting", Types.of(STRING), mb -> mb.withParam("name", Types.of(STRING))
                        .withBody(b -> b.return_(b.call(b.field("bundle"), "getString", b.literal("greeting"))))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
