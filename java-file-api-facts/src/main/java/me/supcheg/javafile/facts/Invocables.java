package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

import java.util.Set;
import java.util.stream.Collectors;

/// Helpers of the generated arity families.
final class Invocables {
    private Invocables() {}

    static String requireMethodName(String name) {
        return Identifiers.requireValid(name);
    }

    static <F> InterfaceToken<F> requireSam(DeclaredToken<F> owner, Invocable method) {
        if (!(owner instanceof InterfaceToken<F> iface)) {
            throw new IllegalArgumentException(describe(method) + " is not a method of an interface");
        }
        if (!iface.methods().abstractMethods().equals(Set.of(method.signature()))) {
            throw new IllegalArgumentException(describe(method) + " is not the single abstract method of " + iface
                    + ", whose abstract methods are " + iface.methods().abstractMethods());
        }
        return iface;
    }

    static String describe(Invocable invocable) {
        String params = invocable.params().stream().map(Object::toString).collect(Collectors.joining(", ", "(", ")"));
        return switch (invocable.kind()) {
            case CONSTRUCTOR ->
                (invocable.owner() instanceof AbstractClassToken<?> ? "super " : "new ") + invocable.owner() + params;
            case STATIC_METHOD ->
                "static " + result(invocable) + " " + invocable.owner() + "." + invocable.name() + params;
            case INSTANCE_METHOD -> result(invocable) + " " + invocable.owner() + "." + invocable.name() + params;
        };
    }

    private static String result(Invocable invocable) {
        return invocable.resultType().map(Object::toString).orElse("void");
    }
}
