package me.supcheg.javafile.typed;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_Class;
import static java.lang.constant.ConstantDescs.CD_List;
import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_byte;
import static java.lang.constant.ConstantDescs.CD_char;
import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_int;
import static java.lang.constant.ConstantDescs.CD_long;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnvTest {
    private static final ClassDesc STRING_BUILDER = ClassDesc.of("java.lang.StringBuilder");
    private static final ClassDesc CHAR_SEQUENCE = ClassDesc.of("java.lang.CharSequence");
    private static final ClassDesc ARRAY_LIST = ClassDesc.of("java.util.ArrayList");

    private final Env env = Env.of(getClass().getClassLoader());

    @Test
    void provesDeclaredMembers() {
        assertThat(env.method(CD_String, "charAt", MethodTypeDesc.of(CD_char, CD_int)))
                .hasToString("char java.lang.String.charAt(int)");
        assertThat(env.staticMethod(CD_String, "valueOf", MethodTypeDesc.of(CD_String, CD_int)))
                .hasToString("static java.lang.String java.lang.String.valueOf(int)");
        assertThat(env.staticField(ClassDesc.of("java.lang.Math"), "PI", CD_double))
                .hasToString("static double java.lang.Math.PI");
        assertThat(env.ctor(STRING_BUILDER, CD_String)).hasToString("new java.lang.StringBuilder(java.lang.String)");
    }

    @Test
    void provesInheritedMembers() {
        assertThat(env.method(CHAR_SEQUENCE, "length", MethodTypeDesc.of(CD_int))
                        .owner()
                        .isInterface())
                .isTrue();
        assertThat(env.method(CD_String, "getClass", MethodTypeDesc.of(CD_Class)))
                .hasToString("java.lang.Class java.lang.String.getClass()");
        assertThat(env.method(CD_String, "hashCode", MethodTypeDesc.of(CD_int))).isNotNull();
        assertThat(env.method(STRING_BUILDER, "capacity", MethodTypeDesc.of(CD_int)))
                .isNotNull();
        assertThat(env.staticMethod(CD_List, "of", MethodTypeDesc.of(CD_List))).isNotNull();
    }

    @Test
    void readsJdkClassesFromJrt() {
        Env jrt = new Env(ClassSource.jrt());

        assertThat(jrt.method(CD_String, "charAt", MethodTypeDesc.of(CD_char, CD_int)))
                .isNotNull();
        assertThatThrownBy(() -> jrt.type(ClassDesc.of("com.example", "Missing")))
                .isInstanceOf(NoSuchSymbolException.class);
    }

    @Test
    void missingMethodThrows() {
        assertThatThrownBy(() -> env.method(CD_String, "frobnicate", MethodTypeDesc.of(CD_int)))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessage("int java.lang.String.frobnicate() does not exist");
    }

    @Test
    void wrongParamsThrow() {
        assertThatThrownBy(() -> env.method(CD_String, "charAt", MethodTypeDesc.of(CD_char, CD_long)))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessageStartingWith(
                        "char java.lang.String.charAt(long) does not exist, found char java.lang.String.charAt(int)");
    }

    @Test
    void wrongReturnTypeThrows() {
        assertThatThrownBy(() -> env.method(CD_String, "charAt", MethodTypeDesc.of(CD_int, CD_int)))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessageStartingWith(
                        "int java.lang.String.charAt(int) does not exist, found char java.lang.String.charAt(int)");
    }

    @Test
    void staticMismatchThrows() {
        assertThatThrownBy(() -> env.method(CD_String, "valueOf", MethodTypeDesc.of(CD_String, CD_int)))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessage("java.lang.String java.lang.String.valueOf(int) does not exist,"
                        + " found static java.lang.String java.lang.String.valueOf(int)");
        assertThatThrownBy(() -> env.staticMethod(CD_String, "length", MethodTypeDesc.of(CD_int)))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessage("static int java.lang.String.length() does not exist, found int java.lang.String.length()");
    }

    @Test
    void staticInterfaceMethodsAreNotInherited() {
        assertThatThrownBy(() -> env.staticMethod(ARRAY_LIST, "of", MethodTypeDesc.of(CD_List)))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessage("static java.util.List java.util.ArrayList.of() does not exist");
    }

    @Test
    void unknownClassThrows() {
        assertThatThrownBy(() -> env.method(ClassDesc.of("com.example", "Missing"), "get", MethodTypeDesc.of(CD_int)))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessage("type com.example.Missing does not exist");
    }

    @Test
    void privateFieldThrows() {
        assertThatThrownBy(() -> env.field(CD_String, "value", CD_byte.arrayType()))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessage("byte[] java.lang.String.value does not exist");
    }

    @Test
    void missingCtorThrows() {
        assertThatThrownBy(() -> env.ctor(CD_String, CD_double))
                .isInstanceOf(NoSuchSymbolException.class)
                .hasMessageStartingWith("new java.lang.String(double) does not exist, found new java.lang.String(");
    }
}
