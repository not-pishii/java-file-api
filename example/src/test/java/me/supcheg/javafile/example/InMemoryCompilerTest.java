package me.supcheg.javafile.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryCompilerTest {

    @Test
    void compilesAndInvokesAStaticMethod() throws Exception {
        String source = "package me.supcheg.example;"
                + "public class Adder {"
                + "    public static int add(int a, int b) { return a + b; }"
                + "}";

        Class<?> adderClass = InMemoryCompiler.compileAndLoad("me.supcheg.example.Adder", source);
        Method addMethod = adderClass.getMethod("add", int.class, int.class);

        assertThat((int) addMethod.invoke(null, 2, 3)).isEqualTo(5);
    }
}
