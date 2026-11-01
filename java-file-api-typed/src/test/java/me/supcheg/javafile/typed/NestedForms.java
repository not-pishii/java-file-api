package me.supcheg.javafile.typed;

import me.supcheg.javafile.testing.CompiledClasses;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/// The member types the generated tests of the arities of member types declare: a nested class of
/// four to twelve type parameters with a method `first` of the last of them, made by reflection from
/// the classes compiled together.
final class NestedForms {
    private NestedForms() {}

    /// `new binaryName().first(value)`, of the nested class `binaryName` of the compiled `classes`,
    /// rethrowing what it throws.
    ///
    /// @param classes the classes compiled together, to load the nested class from
    /// @param binaryName the binary name of the nested class
    /// @param value the argument of `first`
    /// @return the result
    static Object first(CompiledClasses classes, String binaryName, Object value) throws Throwable {
        Class<?> type = classes.loader().loadClass(binaryName);
        var constructor = type.getDeclaredConstructor();
        constructor.trySetAccessible();
        Object instance = constructor.newInstance();
        Method first = type.getDeclaredMethod("first", Object.class);
        first.trySetAccessible();
        try {
            return first.invoke(instance, value);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
