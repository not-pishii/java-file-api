package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulInt;

/// Positive end-to-end compile test (§11): a class built entirely through
/// the typed layer — `typed → core → render → javac`.
class PointCompileTest {

    private static final ClassDesc POINT = ClassDesc.of("me.supcheg.example", "Point");

    @Test
    void typedPointClassRendersAndCompiles() {
        JavaFile file = TypedJavaFile.class_(POINT, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                var x = cb.field("x", PrimitiveToken.INT, literal(1));
                var y = cb.field("y", PrimitiveToken.INT, literal(2));

                cb.method(
                        "sumOfSquares",
                        PrimitiveToken.INT,
                        (body, self) -> body.return_(addInt(
                                mulInt(field(self, x), field(self, x)), mulInt(field(self, y), field(self, y)))));
            }
        });

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
