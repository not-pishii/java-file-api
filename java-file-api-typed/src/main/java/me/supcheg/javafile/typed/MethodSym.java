package me.supcheg.javafile.typed;

import java.lang.constant.MethodTypeDesc;
import java.util.Objects;

/// A method proven to exist; obtained from [TypeSym], [Env], or a definition in [TypeHandle].
public final class MethodSym {
    private final TypeSym owner;
    private final String name;
    private final MethodTypeDesc type;
    private final boolean isStatic;

    MethodSym(TypeSym owner, String name, MethodTypeDesc type, boolean isStatic) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.isStatic = isStatic;
    }

    /// The type the method was proven on; it declares or inherits the method.
    ///
    /// @return the owner
    public TypeSym owner() {
        return owner;
    }

    /// The method name.
    ///
    /// @return the name
    public String name() {
        return name;
    }

    /// The erased return and parameter types.
    ///
    /// @return the method type
    public MethodTypeDesc type() {
        return type;
    }

    /// Whether the method is `static`.
    ///
    /// @return `true` for a static method
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MethodSym other
                && owner.desc().equals(other.owner.desc())
                && name.equals(other.name)
                && type.equals(other.type)
                && isStatic == other.isStatic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner.desc(), name, type, isStatic);
    }

    @Override
    public String toString() {
        return Descs.method(owner.desc(), name, type, isStatic);
    }
}
