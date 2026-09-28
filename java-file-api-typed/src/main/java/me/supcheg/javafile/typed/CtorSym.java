package me.supcheg.javafile.typed;

import java.lang.constant.MethodTypeDesc;
import java.util.Objects;

/// A constructor proven to exist; obtained from [TypeSym], [Env], or a definition in [TypeHandle].
public final class CtorSym {
    private final TypeSym owner;
    private final MethodTypeDesc type;

    CtorSym(TypeSym owner, MethodTypeDesc type) {
        this.owner = owner;
        this.type = type;
    }

    /// The class declaring the constructor.
    ///
    /// @return the owner
    public TypeSym owner() {
        return owner;
    }

    /// The erased parameter types, with a `void` return type.
    ///
    /// @return the constructor type
    public MethodTypeDesc type() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CtorSym other && owner.desc().equals(other.owner.desc()) && type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner.desc(), type);
    }

    @Override
    public String toString() {
        return Descs.ctor(owner.desc(), type);
    }
}
