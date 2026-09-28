package me.supcheg.javafile.code;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MethodTypeArgsTest {

    @Test
    void shortConstructorsHaveNoTypeArgs() {
        assertThat(new MethodCallExpr(Optional.empty(), "m", List.of()).typeArgs())
                .isEmpty();
        assertThat(new StaticMethodCallExpr(Types.LIST, "of", List.of()).typeArgs())
                .isEmpty();
    }

    @Test
    void rendersExplicitTypeArgs() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("com.example", "Calls"),
                cb -> cb.withVoidMethod(
                        "m",
                        mb -> mb.withBody(b -> b.exprStatement(new MethodCallExpr(
                                        Optional.of(new StaticMethodCallExpr(
                                                Types.LIST, "of", List.of(), List.of(Types.STRING))),
                                        "get",
                                        List.of(Exprs.literal(0)),
                                        List.of()))
                                .exprStatement(new MethodCallExpr(
                                        Optional.of(Exprs.this_()),
                                        "m2",
                                        List.of(),
                                        List.of(Types.array(Types.INT)))))));

        assertThat(file.render()).contains("List.<String>of().get(0);").contains("this.<int[]>m2();");
    }

    @Test
    void rejectsTypeArgsOnUnqualifiedCall() {
        assertThatThrownBy(() -> new MethodCallExpr(Optional.empty(), "m", List.of(), List.of(Types.STRING)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("explicit type arguments require a qualified call: m");
    }

    @Test
    void rejectsPrimitiveTypeArgs() {
        assertThatThrownBy(() -> new StaticMethodCallExpr(Types.LIST, "of", List.of(), List.of(Types.INT)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("a type argument must be a reference type, got: int");
    }
}
