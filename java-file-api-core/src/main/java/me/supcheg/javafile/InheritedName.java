package me.supcheg.javafile;

/// A simple name the type of a file inherits from a supertype, which the
/// renderer cannot read off the declaration: in the body of the type the
/// name means the inherited member, not a type of that name the file would
/// import. A file is told the names with
/// [JavaFile#inheriting(java.util.Collection)], and the renderer writes a
/// type they hide by its qualified name.
public sealed interface InheritedName permits InheritedName.Field, InheritedName.MemberType {

    /// The simple name.
    ///
    /// @return the name
    String name();

    /// An inherited field. Where an expression may stand — before `.member`
    /// or `::method` — its name means the field, so a type of that name is
    /// written qualified there (JLS 6.4.2).
    ///
    /// @param name the name of the field
    record Field(String name) implements InheritedName {
        public Field {
            Identifiers.requireValid(name);
        }
    }

    /// An inherited member class or interface. Its name shadows a type of
    /// that name everywhere in the body, so such a type is written qualified
    /// throughout the file (JLS 6.4.1).
    ///
    /// @param name the simple name of the member type
    record MemberType(String name) implements InheritedName {
        public MemberType {
            Identifiers.requireValid(name);
        }
    }
}
