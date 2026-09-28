package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassTypeRef;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

/// A token of an enum type that also carries the enum's constants, in
/// declaration order (§3.5).
///
/// A constant is obtained with [#constant(String)], which fails fast for a
/// name the enum does not declare. Lowering checks a `switch` over the enum
/// for exhaustiveness against [#constants()].
///
/// @param <T> the Java type this token stands for
public final class EnumToken<T> extends ClassTokenData implements ClassToken<T> {
    private static final ClassDesc CD_ENUM = ClassDesc.of("java.lang.Enum");

    private final List<String> constants;

    private EnumToken(ClassTypeRef typeRef, List<String> constants, MethodTable methods) {
        super(typeRef, List.of(CD_ENUM, ConstantDescs.CD_Object), methods);
        this.constants = List.copyOf(constants);
    }

    /// Introduces the fact that an enum type exists with the given constants.
    ///
    /// For fact sources only; see [TypeToken].
    ///
    /// @param typeRef the enum type
    /// @param constants the enum's constant names, in declaration order
    /// @param methods the enum's instance methods, declared and inherited
    /// @param <T> the Java type the token stands for; the caller vouches for it
    /// @return the token
    public static <T> EnumToken<T> introduce(ClassTypeRef typeRef, List<String> constants, MethodTable methods) {
        return new EnumToken<>(typeRef, constants, methods);
    }

    /// The enum's constant names, in declaration order.
    ///
    /// @return the constant names
    public List<String> constants() {
        return constants;
    }

    /// Looks up a constant of the enum.
    ///
    /// @param name the constant name
    /// @return the constant
    /// @throws FactLookupException if the enum declares no such constant
    public EnumConstant<T> constant(String name) {
        if (!constants.contains(name)) {
            throw new FactLookupException("enum constant " + this + "." + name, "the enum token", constants);
        }
        return new EnumConstant<>(this, name);
    }
}
