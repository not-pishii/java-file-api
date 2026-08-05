package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

/// Proves a nested type declared via a real nested [ClassDesc]
/// (`Outer.nested("Inner")`) round-trips through the renderer as valid Java:
/// declared with its bare leaf name, and referenced elsewhere in the same
/// file through [me.supcheg.javafile.render.ImportManager]'s import/dotted-
/// fallback rules — not the invalid `Outer$Inner` binary form. Unit-level
/// coverage for each renderer lives in `ImportManagerTest`,
/// `TypeRefRendererTest`, and `TypeDeclRendererTest`; this test proves the
/// emitted source actually type-checks.
class NestedTypeCompileTest {

    @Test
    void classWithANestedStaticClassAndAFieldReferencingItCompiles() {
        ClassDesc outer = ClassDesc.of("me.supcheg.example", "Outer");
        ClassDesc inner = outer.nested("Inner");

        ClassDecl nested = new ClassDecl(
                inner,
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());

        JavaFile file = JavaFile.class_(outer, cb -> {
            cb.accept(nested);
            cb.withField("value", Types.of(inner), fb -> {});
        });

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
