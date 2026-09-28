package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.mulInt;
import static me.supcheg.javafile.typed.Expressions.this_;

/// Positive end-to-end compile test (§11): a class built entirely through
/// the typed layer — `typed → core → render → javac`.
class PointCompileTest {

    private static final ClassDesc POINT = ClassDesc.of("me.supcheg.example", "Point");

    @Test
    void typedPointClassRendersAndCompiles() {
        JavaFile file = TypedJavaFile.class_(POINT, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                FinalClassToken<Self> self = cb.self();
                var x = cb.field("x", PrimitiveToken.INT, literal(1));
                var y = cb.field("y", PrimitiveToken.INT, literal(2));

                cb.method("sumOfSquares", PrimitiveToken.INT, body -> {
                    Expr<Self> self0 = this_(self);
                    return body.return_(
                            addInt(mulInt(field(self0, x), field(self0, x)), mulInt(field(self0, y), field(self0, y))));
                });
            }
        });

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
