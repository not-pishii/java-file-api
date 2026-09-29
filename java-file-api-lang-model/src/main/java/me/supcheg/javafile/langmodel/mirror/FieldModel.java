package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.TypeRef;

import java.util.Set;

/// A field.
///
/// @param name the field name
/// @param isStatic whether the field is `static`
/// @param type the field type
/// @param mutability whether the field can be assigned, and its value if it is a constant
public record FieldModel(String name, boolean isStatic, TypeRef type, Mutability mutability) implements MemberModel {

    /// @throws IllegalArgumentException if `name` is not a Java identifier, or the field is a
    ///                                  [Mutability.Constant] but not `static`
    public FieldModel {
        Identifiers.requireValid(name);
        if (mutability instanceof Mutability.Constant && !isStatic) {
            throw new IllegalArgumentException("instance field " + name + " cannot be a constant");
        }
    }

    /// Whether a field can be assigned, and its value if it is a constant.
    public sealed interface Mutability permits Mutability.Mutable, Mutability.Final, Mutability.Constant {

        /// A field that is not `final`.
        Mutable MUTABLE = new Mutable();

        /// A `final` field that is not a constant.
        Final FINAL = new Final();

        /// A field that is not `final`, [#MUTABLE].
        record Mutable() implements Mutability {}

        /// A `final` field that is not a constant, [#FINAL].
        record Final() implements Mutability {}

        /// A `static final` field of a primitive type or `String` initialized
        /// with a constant expression (JLS 4.12.4): its value is part of the
        /// API, compiled into the code that reads it.
        ///
        /// An instance `final` field initialized so is a constant variable
        /// too, but reading it through an instance is not a constant
        /// expression, so it is [#FINAL].
        ///
        /// @param value the value: a `String` or the box of a primitive
        record Constant(Object value) implements Mutability {
            private static final Set<Class<?>> TYPES = Set.of(
                    String.class,
                    Boolean.class,
                    Byte.class,
                    Short.class,
                    Character.class,
                    Integer.class,
                    Long.class,
                    Float.class,
                    Double.class);

            /// @throws IllegalArgumentException if `value` is neither a `String` nor the box of a primitive
            public Constant {
                if (!TYPES.contains(value.getClass())) {
                    throw new IllegalArgumentException("a constant is a String or a boxed primitive, got "
                            + value.getClass().getName());
                }
            }
        }
    }
}
