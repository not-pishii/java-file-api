package me.supcheg.javafile.type;

/// A reference to one of the eight Java primitive types.
public enum PrimitiveTypeRef implements TypeRef {
    INT("int"),
    LONG("long"),
    DOUBLE("double"),
    FLOAT("float"),
    BOOLEAN("boolean"),
    BYTE("byte"),
    SHORT("short"),
    CHAR("char");

    private final String sourceName;

    PrimitiveTypeRef(String sourceName) {
        this.sourceName = sourceName;
    }

    /// Returns the Java source keyword for this primitive type, e.g. `"int"`.
    ///
    /// @return the primitive's source-level keyword
    public String sourceName() {
        return sourceName;
    }
}
