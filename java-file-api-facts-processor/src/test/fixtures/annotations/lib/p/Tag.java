package p;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;

@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER, TYPE_USE})
public @interface Tag {
    String value();

    int rank() default 1;

    Level[] levels() default {};

    Class<?> type() default Object.class;

    Class<?> primitive() default int.class;

    Class<?> array() default String[].class;

    boolean flag() default true;

    long big() default 1L;

    double ratio() default 1.5;

    char letter() default 'a';

    byte small() default 1;

    short medium() default 2;

    float decimal() default 1.0f;
}
