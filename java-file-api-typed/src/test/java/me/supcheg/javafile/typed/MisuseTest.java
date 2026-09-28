package me.supcheg.javafile.typed;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.List;

import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_char;
import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_int;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.typed.Syms.call;
import static me.supcheg.javafile.typed.Syms.field;
import static me.supcheg.javafile.typed.Syms.new_;
import static me.supcheg.javafile.typed.Syms.staticCall;
import static me.supcheg.javafile.typed.Syms.staticField;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MisuseTest {
    private static final ClassDesc FOO = ClassDesc.of("com.example", "Foo");

    private final Env env = Env.of(getClass().getClassLoader());
    private final MethodSym charAt = env.method(CD_String, "charAt", MethodTypeDesc.of(CD_char, CD_int));
    private final MethodSym valueOf = env.staticMethod(CD_String, "valueOf", MethodTypeDesc.of(CD_String, CD_int));

    @Test
    void wrongArityThrows() {
        assertThatThrownBy(() -> call(literal("s"), charAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("char java.lang.String.charAt(int) takes 1 argument(s), got 0");
        assertThatThrownBy(() -> new_(env.ctor(CD_Object), literal(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("new java.lang.Object() takes 0 argument(s), got 1");
    }

    @Test
    void staticMismatchThrows() {
        assertThatThrownBy(() -> call(literal("s"), valueOf, literal(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("static java.lang.String java.lang.String.valueOf(int) is static");
        assertThatThrownBy(() -> staticCall(charAt, literal(0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("char java.lang.String.charAt(int) is not static");
        FieldSym pi = env.staticField(ClassDesc.of("java.lang.Math"), "PI", CD_double);
        assertThatThrownBy(() -> field(literal(0), pi)).isInstanceOf(IllegalArgumentException.class);
        Unit unit = new Unit(env);
        FieldSym x = unit.class_(FOO).defineField("x", CD_int);
        assertThatThrownBy(() -> staticField(x)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void invalidSupertypesThrow() {
        Unit unit = new Unit(env);
        TypeHandle foo = unit.class_(FOO);

        assertThatThrownBy(() -> foo.implement(env.type(CD_String)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("java.lang.String is not an interface");
        assertThatThrownBy(() -> foo.extend(env.type(ClassDesc.of("java.lang.Runnable"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("java.lang.Runnable is an interface");
    }

    @Test
    void duplicateDefinitionThrows() {
        TypeHandle foo = new Unit(env).class_(FOO);
        foo.defineMethod("m", MethodTypeDesc.of(CD_int), (code, p) -> code.return_(literal(1)));

        assertThatThrownBy(() -> foo.defineMethod("m", MethodTypeDesc.of(CD_int), (code, p) -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("int com.example.Foo.m() is already defined");
    }

    @Test
    void wrongParamNameCountThrows() {
        TypeHandle foo = new Unit(env).class_(FOO);

        assertThatThrownBy(() ->
                        foo.defineMethod("m", MethodTypeDesc.of(CD_int, CD_int), List.of("a", "b"), (code, p) -> {}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("expected 1 parameter name(s), got [a, b]");
    }

    @Test
    void ctorAfterDefaultCtorWasUsedThrows() {
        TypeHandle foo = new Unit(env).class_(FOO);
        foo.ctor();

        assertThatThrownBy(() -> foo.defineCtor(List.of(CD_int), (code, p) -> {}))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("the default constructor of com.example.Foo is already in use");
    }

    @Test
    void definedCtorReplacesDefaultCtor() {
        TypeHandle foo = new Unit(env).class_(FOO);
        foo.defineCtor(List.of(CD_int), (code, p) -> {});

        assertThatThrownBy(foo::ctor)
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessage("new com.example.Foo() does not exist, found new com.example.Foo(int)");
    }

    @Test
    void definitionAfterBuildThrows() {
        Unit unit = new Unit(env);
        TypeHandle foo = unit.class_(FOO);
        unit.build();

        assertThatThrownBy(() -> foo.defineField("x", CD_int))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("com.example.Foo is already built");
    }

    @Test
    void recordCannotDeclareInstanceFields() {
        TypeHandle point = new Unit(env).record(FOO, new Component("x", CD_int));

        assertThatThrownBy(() -> point.defineField("y", CD_int)).isInstanceOf(IllegalStateException.class);
    }
}
