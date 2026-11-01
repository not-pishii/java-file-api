package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// The empty statement (6.3): it renders `;` in a block, compiles under `-Xlint:all`, and changes neither
/// reachability nor scope.
class EmptyStatementCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Blank");

    @Test
    void theEmptyStatementRendersInABlockAndCompiles() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var nothing = cb.declareStaticMethod("nothing", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                nothing,
                                b -> b.empty()
                                        .if_(literal(true), t -> t.empty())
                                        .empty()
                                        .return_(literal(0)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("public static int nothing() {\n        ;\n        if (true) {\n            ;\n        }\n"
                        + "        ;\n        return 0;\n    }");
        assertThat(compiled.invoke("nothing")).isEqualTo(0);
    }
}
