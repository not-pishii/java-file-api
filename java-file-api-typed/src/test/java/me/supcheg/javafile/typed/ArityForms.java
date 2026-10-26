package me.supcheg.javafile.typed;

import me.supcheg.javafile.testing.CompiledClasses;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The classes the tests of the arity families declare ([ArityFiles]), compiled together in one run of javac under
/// `-Xlint:all` the first time any is asked for, and what a test does with them: makes an instance, calls a method.
final class ArityForms {
    private static final Map<String, CompiledClasses> COMPILED = CompiledClasses.ofAll(ArityFiles.all()).stream()
            .collect(Collectors.toUnmodifiableMap(
                    compiled -> compiled.generated().getSimpleName(), Function.identity()));

    private ArityForms() {}

    private static Class<?> load(String type) {
        return COMPILED.get(type).generated();
    }

    /// A new instance of the class `type`, by its constructor of the types of `args`.
    static Object create(String type, List<?> args) throws ReflectiveOperationException {
        Class<?>[] types = args.stream().map(Object::getClass).toArray(Class<?>[]::new);
        return load(type).getConstructor(types).newInstance(args.toArray());
    }

    /// The result of the method `name` of the class `type` called with `args`, on `target` or, if it is `null`, as a
    /// static one; rethrowing what it throws.
    static Object invoke(String type, Object target, String name, List<?> args) throws Throwable {
        Method method = Stream.of(load(type).getMethods())
                .filter(candidate -> candidate.getName().equals(name) && candidate.getParameterCount() == args.size())
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("no method " + name + " of " + args.size() + " parameters in " + type));
        try {
            return method.invoke(target, args.toArray());
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
