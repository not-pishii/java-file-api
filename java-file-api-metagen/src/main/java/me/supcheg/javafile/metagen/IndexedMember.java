package me.supcheg.javafile.metagen;

import java.lang.constant.ClassDesc;

/// A member found in a class file.
///
/// @param owner the type the member was looked up on; it declares or inherits it
/// @param kind the member kind
/// @param name the member name, `<init>` for a constructor
/// @param descriptor the erased method or field descriptor
/// @param isStatic whether the member is static
record IndexedMember(ClassDesc owner, Kind kind, String name, String descriptor, boolean isStatic) {

    /// A member kind.
    enum Kind {
        METHOD,
        FIELD,
        CTOR
    }

    @Override
    public String toString() {
        return Descs.describe(owner, kind, name, descriptor, isStatic);
    }
}
