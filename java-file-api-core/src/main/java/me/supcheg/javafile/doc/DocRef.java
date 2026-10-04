package me.supcheg.javafile.doc;

import me.supcheg.javafile.Identifiers;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A program element a documentation comment refers to: a type, or a member
/// of one.
///
/// A type is named as the code of the file names it: by its simple name if
/// the file imports or declares it or it needs no import, and qualified
/// otherwise. A reference never adds an import.
///
/// The parameters that tell a method or a constructor from its overloads are
/// [ClassDesc]s: the erasures of the declared parameter types, as javadoc
/// matches them — a primitive, a class or an interface without type
/// arguments, an array of those; `Object`, or the first bound, for a type
/// variable; the array type for a varargs parameter.
public sealed interface DocRef {

    /// A class, interface, enum, record or annotation interface.
    ///
    /// @param type the type
    /// @return the reference
    /// @throws IllegalArgumentException if `type` is a primitive or an array
    static DocRef type(ClassDesc type) {
        return new Type(type);
    }

    /// A field or an enum constant.
    ///
    /// @param owner the type that has the field
    /// @param name the name of the field
    /// @return the reference
    /// @throws IllegalArgumentException if `owner` is a primitive or an array, or `name` is not an identifier
    static DocRef field(ClassDesc owner, String name) {
        return new Field(owner, name);
    }

    /// A method.
    ///
    /// @param owner the type that has the method
    /// @param name the name of the method
    /// @param params the erasures of its parameter types, in order
    /// @return the reference
    /// @throws IllegalArgumentException if `owner` is a primitive or an array, `name` is not an identifier, or a
    ///                                  parameter is `void`
    static DocRef method(ClassDesc owner, String name, ClassDesc... params) {
        return new Method(owner, name, List.of(params));
    }

    /// A constructor.
    ///
    /// @param owner the class that has the constructor
    /// @param params the erasures of its parameter types, in order
    /// @return the reference
    /// @throws IllegalArgumentException if `owner` is a primitive or an array, or a parameter is `void`
    static DocRef constructor(ClassDesc owner, ClassDesc... params) {
        return new Constructor(owner, List.of(params));
    }

    /// A reference to a type.
    ///
    /// @param type the type
    record Type(ClassDesc type) implements DocRef {
        /// @throws IllegalArgumentException if `type` is a primitive or an array
        public Type {
            requireDeclared(type);
        }
    }

    /// A reference to a field or an enum constant.
    ///
    /// @param owner the type that has the field
    /// @param name the name of the field
    record Field(ClassDesc owner, String name) implements DocRef {
        /// @throws IllegalArgumentException if `owner` is a primitive or an array, or `name` is not an identifier
        public Field {
            requireDeclared(owner);
            name = Identifiers.requireValid(name);
        }
    }

    /// A reference to a method.
    ///
    /// @param owner the type that has the method
    /// @param name the name of the method
    /// @param params the erasures of its parameter types, in order
    record Method(ClassDesc owner, String name, List<ClassDesc> params) implements DocRef {
        /// @throws IllegalArgumentException if `owner` is a primitive or an array, `name` is not an identifier, or
        ///                                  a parameter is `void`
        public Method {
            requireDeclared(owner);
            name = Identifiers.requireValid(name);
            params = requireParams(params);
        }
    }

    /// A reference to a constructor.
    ///
    /// @param owner the class that has the constructor
    /// @param params the erasures of its parameter types, in order
    record Constructor(ClassDesc owner, List<ClassDesc> params) implements DocRef {
        /// @throws IllegalArgumentException if `owner` is a primitive or an array, or a parameter is `void`
        public Constructor {
            requireDeclared(owner);
            params = requireParams(params);
        }
    }

    private static void requireDeclared(ClassDesc type) {
        if (!type.isClassOrInterface()) {
            throw new IllegalArgumentException("not a class or an interface: " + type.displayName());
        }
    }

    private static List<ClassDesc> requireParams(List<ClassDesc> params) {
        List<ClassDesc> copy = List.copyOf(params);
        if (copy.stream().anyMatch(param -> param.descriptorString().equals("V"))) {
            throw new IllegalArgumentException("void is not the type of a parameter");
        }
        return copy;
    }
}
