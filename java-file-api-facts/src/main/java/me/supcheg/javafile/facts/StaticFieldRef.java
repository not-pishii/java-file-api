package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;

import java.lang.constant.ConstantDescs;
import java.util.Optional;

/// The fact that a type has a static field `name` of type `T` that can be
/// read. A static field that can also be assigned is a [MutableStaticFieldRef].
///
/// A `final` field initialized with a constant expression is a constant
/// variable (JLS 4.12.4) and carries its value: a loop condition reading it
/// is a constant expression, which changes reachability (JLS 14.22), and
/// lowering needs the value to reproduce the compiler's verdict.
///
/// The value of a constant variable is the box of a primitive field's value
/// — an `Integer` for a `Prim.Int` field — or a `String`; it is checked
/// against the field type when the fact is introduced.
///
/// @param <T> the field type
public sealed class StaticFieldRef<T> implements MemberFact permits MutableStaticFieldRef {
    private final DeclaredToken<?> owner;
    private final String name;
    private final TypeToken<T> type;
    private final Optional<Object> constantValue;
    private final Access access;

    StaticFieldRef(
            DeclaredToken<?> owner, String name, TypeToken<T> type, Optional<Object> constantValue, Access access) {
        this.owner = owner;
        this.name = Identifiers.requireValid(name);
        this.type = type;
        this.constantValue = constantValue;
        this.access = access;
        constantValue.ifPresent(value -> requireConstantOf(type, value));
    }

    private static void requireConstantOf(TypeToken<?> type, Object value) {
        boolean matches =
                switch (type) {
                    case PrimitiveToken<?, ?, ?> primitive ->
                        primitive.boxClass().isInstance(value);
                    case RefToken<?> ref -> ref.erasure().equals(ConstantDescs.CD_String) && value instanceof String;
                };
        if (!matches) {
            throw new IllegalArgumentException("not a constant value of type " + type + ": " + value + " ("
                    + value.getClass().getName() + ")");
        }
    }

    /// The type owning the field.
    ///
    /// @return the owner token
    @Override
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

    /// The access of the field.
    ///
    /// @return the access
    @Override
    public final Access access() {
        return access;
    }

    @Override
    public final String toString() {
        return "static " + type + " " + owner + "." + name;
    }
}
