package me.supcheg.javafile.facts;

/// A constant of an enum, obtained from [EnumToken#constant(String)].
///
/// @param <E> the enum type
public final class EnumConstant<E> {
    private final EnumToken<E> owner;
    private final String name;

    EnumConstant(EnumToken<E> owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    /// The enum declaring the constant.
    ///
    /// @return the enum token
    public EnumToken<E> owner() {
        return owner;
    }

    /// The constant name.
    ///
    /// @return the name
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EnumConstant<?> other && owner.equals(other.owner) && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return owner.hashCode() * 31 + name.hashCode();
    }

    @Override
    public String toString() {
        return owner + "." + name;
    }
}
