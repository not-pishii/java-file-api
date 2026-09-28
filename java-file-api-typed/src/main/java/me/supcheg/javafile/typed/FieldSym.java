package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.Objects;

/// A field proven to exist; obtained from [TypeSym], [Env], or a definition in [TypeHandle].
public final class FieldSym {
    private final TypeSym owner;
    private final String name;
    private final ClassDesc type;
    private final boolean isStatic;

    FieldSym(TypeSym owner, String name, ClassDesc type, boolean isStatic) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.isStatic = isStatic;
    }

    /// The type the field was proven on; it declares or inherits the field.
    ///
    /// @return the owner
    public TypeSym owner() {
        return owner;
    }

    /// The field name.
    ///
    /// @return the name
    public String name() {
        return name;
    }

    /// The erased field type.
    ///
    /// @return the field type
    public ClassDesc type() {
        return type;
    }

    /// Whether the field is `static`.
    ///
    /// @return `true` for a static field
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FieldSym other
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
        return Descs.field(owner.desc(), name, type, isStatic);
    }
}
