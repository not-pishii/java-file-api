package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Optional;

import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.staticCall;
import static org.assertj.core.api.Assertions.assertThat;

class ConstantNameCompileTest {

    private static final ClassDesc OPTIONAL = ClassDesc.of("java.util", "Optional");

    @Test
    void aConstantOfAnInterfaceHidesTheTypeOfItsNameBeforeAMember() throws Exception {
        // JLS 6.4.2: `Optional.empty()` after `int Optional = 1;` is a member of the int, which has none
        JavaFile file = JavaFile.interface_(
                ClassDesc.of("me.supcheg.example", "Limits"),
                ib -> ib.withConstant("Optional", Types.INT, literal(1))
                        .withStaticMethod(
                                "none",
                                Types.parameterized(OPTIONAL, Types.STRING),
                                mb -> mb.withBody(b -> b.return_(staticCall(OPTIONAL, "empty")))));

        String source = file.render();
        Class<?> limits = InMemoryCompiler.compileAndLoad(file.qualifiedName(), source);

        assertThat(source).contains("int Optional = 1;").contains("return java.util.Optional.empty();");
        assertThat(limits.getField("Optional").get(null)).isEqualTo(1);
        assertThat(limits.getMethod("none").invoke(null)).isEqualTo(Optional.empty());
    }
}
