package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

import java.util.Optional;
import java.util.Set;

/// The fact that a type has a static field `name` of type `T` that can be
/// read. A static field that can also be assigned is a [MutableStaticFieldRef].
///
/// A `final` field initialized with a constant expression is a constant
/// variable (JLS 4.12.4) and carries its value: a loop condition reading it
/// is a constant expression, which changes reachability (JLS 14.22), and
/// lowering needs the value to reproduce the compiler's verdict.
///
/// @param <T> the field type
public sealed class StaticFieldRef<T> permits MutableStaticFieldRef {
    private static final Set<Class<?>> CONSTANT_TYPES = Set.of(
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Boolean.class,
            Character.class,
            Byte.class,
            Short.class,
            String.class);

    private final DeclaredToken<?> owner;
    private final String name;
    private final TypeToken<T> type;
    private final Optional<Object> constantValue;

    StaticFieldRef(DeclaredToken<?> owner, String name, TypeToken<T> type, Optional<Object> constantValue) {
        this.owner = owner;
        this.name = Identifiers.requireValid(name);
        this.type = type;
        this.constantValue = constantValue;
        constantValue.ifPresent(value -> {
            if (!CONSTANT_TYPES.contains(value.getClass())) {
                throw new IllegalArgumentException("not a constant value: " + value.getClass());
            }
        });
    }

    /// Introduces the fact that a type has a `final` static field that is
    /// not a constant variable.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param owner the type owning the field
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact
    public static <T> StaticFieldRef<T> introduce(DeclaredToken<?> owner, String name, TypeToken<T> type) {
        return new StaticFieldRef<>(owner, name, type, Optional.empty());
    }

    /// Introduces the fact that a type has a constant variable: a `final`
    /// static field of a primitive or `String` type initialized with a
    /// constant expression.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param owner the type owning the field
    /// @param name the field name
    /// @param type the field type
    /// @param value the constant value, a box or a `String`
    /// @param <T> the field type
    /// @return the fact
    /// @throws IllegalArgumentException if `value` is not a box or a `String`
    public static <T> StaticFieldRef<T> introduceConstant(
            DeclaredToken<?> owner, String name, TypeToken<T> type, T value) {
        return new StaticFieldRef<>(owner, name, type, Optional.of(value));
    }

    /// The type owning the field.
    ///
    /// @return the owner token
    public final DeclaredToken<?> owner() {
        return owner;
    }

    /// The field name.
    ///
    /// @return the name
    public final String name() {
        return name;
    }

    /// The field type.
    ///
    /// @return the type token
    public final TypeToken<T> type() {
        return type;
    }

    /// The value of a constant variable.
    ///
    /// @return the value, or empty if the field is not a constant variable
    public final Optional<Object> constantValue() {
        return constantValue;
    }

    @Override
    public final String toString() {
        return "static " + type + " " + owner + "." + name;
    }
}
