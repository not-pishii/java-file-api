package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;

/**
 * A function of 8 arguments, which `java.util.function` has none of: the shape of the generator's lambda that builds the body of a lambda of the generated code ([Expressions]).
 *
 * @param <T1> the type of the first argument
 * @param <T2> the type of the second argument
 * @param <T3> the type of the third argument
 * @param <T4> the type of the fourth argument
 * @param <T5> the type of the fifth argument
 * @param <T6> the type of the sixth argument
 * @param <T7> the type of the seventh argument
 * @param <T8> the type of the eighth argument
 * @param <R> the type of the result
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@FunctionalInterface
public interface Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
    /**
     * Applies the function.
     *
     * @param t1 the first argument
     * @param t2 the second argument
     * @param t3 the third argument
     * @param t4 the fourth argument
     * @param t5 the fifth argument
     * @param t6 the sixth argument
     * @param t7 the seventh argument
     * @param t8 the eighth argument
     * @return the result
     */
    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8);
}
