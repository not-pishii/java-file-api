package me.supcheg.javafile.facts;

import java.util.List;

/// A token of an enum type that also carries the enum's constants, in
/// declaration order (§3.5).
///
/// A constant is obtained with [#constant(String)], which fails fast for a
/// name the enum does not declare. The typed layer checks a `switch` over
/// the enum for exhaustiveness against [#constants()], where the `switch`
/// is built.
///
/// @param <T> the Java type this token stands for
public final class EnumToken<T> extends ClassTokenData implements ClassToken<T> {

    EnumToken(TypeShape<DeclaredKind.EnumClass> shape) {
        super(shape, List.of());
    }

    /// The enum's constant names, in declaration order.
    ///
    /// @return the constant names
    public List<String> constants() {
        return shape().enumConstants();
    }

    /// Looks up a constant of the enum.
    ///
    /// @param name the constant name
    /// @return the constant
    /// @throws FactLookupException if the enum declares no such constant
    public EnumConstant<T> constant(String name) {
        if (!constants().contains(name)) {
            throw new FactLookupException("enum constant " + this + "." + name, "the enum token", constants());
        }
        return new EnumConstant<>(this, name);
    }
}
