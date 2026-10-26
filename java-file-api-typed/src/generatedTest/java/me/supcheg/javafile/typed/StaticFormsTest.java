package me.supcheg.javafile.typed;

import java.lang.reflect.Constructor;
import java.util.List;
import javax.annotation.processing.Generated;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The classes of static forms are constructed here, which runs the constructor of the generated superclass that holds their forms: nothing else constructs them.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
class StaticFormsTest {
    @Test
    public void theClassesOfStaticFormsAreConstructed() throws ReflectiveOperationException {
        for (Class<?> type : List.of(Expressions.class, Overrides.class)) {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            Assertions.assertThat(constructor.newInstance()).isInstanceOf(type);
        }
    }
}
