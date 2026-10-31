package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;

import java.lang.constant.ClassDesc;
import java.util.List;

/// Whether a class being declared is `Serializable`, and what that asks of its members: javac warns
/// (`[serial]`) of a serializable class that does not declare its serial version, and of an instance
/// field of it whose type is not serializable — what a serialization of the class could not write.
/// The typed layer renders no code javac warns of, so a serializable class declares its version
/// ([MemberBuilder#declareSerialVersionUID]) and no such field, and one that is none declares no
/// version.
sealed interface Serial {
    ClassDesc CD_SERIALIZABLE = ClassDesc.of("java.io", "Serializable");

    /// The class is not `Serializable`.
    record Not() implements Serial {}

    /// The class is `Serializable` by a supertype.
    ///
    /// @param supertype the supertype that makes it so
    /// @param variables the type parameters of the class, each with its bounds
    record By(DeclaredToken<?> supertype, List<Subtyping.Bounded> variables) implements Serial {
        public By {
            variables = List.copyOf(variables);
        }
    }

    /// What a class of these supertypes is.
    ///
    /// @param inherited what the class inherits, of its supertypes
    /// @param variables its type parameters, each with its bounds
    /// @return whether the class is `Serializable`
    static Serial of(Inheritance inherited, List<Subtyping.Bounded> variables) {
        return inherited.supertypes().stream()
                .map(Inheritance.Supertype::token)
                .filter(supertype -> isSerializable(supertype.shape()))
                .findFirst()
                .<Serial>map(supertype -> new By(supertype, variables))
                .orElseGet(Not::new);
    }

    /// Rejects the serial version of a class that is not `Serializable`, which no serialization reads.
    ///
    /// @param described the class, for the message
    /// @throws IllegalArgumentException if the class is not `Serializable`
    default void requireSerializable(String described) {
        switch (this) {
            case Not _ ->
                throw new IllegalArgumentException(described + " is not Serializable: no serialization reads"
                        + " its serial version, and it declares none");
            case By _ -> {}
        }
    }

    /// Rejects an instance field of a type that is not serializable, of a class that is: javac warns of
    /// it (`[serial]`).
    ///
    /// @param field the field
    /// @param described the class, for the message
    /// @throws IllegalArgumentException if the class is `Serializable` and the type of `field` is not
    default void requireSerializable(FieldRef<?, ?> field, String described) {
        switch (this) {
            case Not _ -> {}
            case By(var supertype, var variables) -> {
                if (!isSerializable(field.type(), variables)) {
                    throw new IllegalArgumentException("field " + field.name() + " of " + described + " is of "
                            + field.type() + ", which is not Serializable, and the class is, by " + supertype
                            + ": javac warns of the field (-Xlint:serial) — a serialization of the class cannot"
                            + " write it");
                }
            }
        }
    }

    /// Rejects the end of the declarations of a `Serializable` class that did not declare its serial
    /// version.
    ///
    /// @param declared whether the class declared its serial version
    /// @param described the class, for the message
    /// @throws IllegalStateException if the class is `Serializable` and declared no serial version
    default void requireVersion(boolean declared, String described) {
        switch (this) {
            case Not _ -> {}
            case By(var supertype, var _) -> {
                if (!declared) {
                    throw new IllegalStateException(described + " is Serializable, by " + supertype + ", and"
                            + " declares no serial version: javac warns of it (-Xlint:serial) — declare it by"
                            + " declareSerialVersionUID(long) before bodies()");
                }
            }
        }
    }

    /// Whether a value of `type` is serializable: a primitive, an array of what is, a class or interface
    /// that is `Serializable`, a type parameter of the class one of whose bounds is.
    static boolean isSerializable(TypeToken<?> type, List<Subtyping.Bounded> variables) {
        return switch (type) {
            case PrimitiveToken<?, ?, ?> _ -> true;
            case me.supcheg.javafile.facts.AnnotatedRefToken<?> annotated ->
                isSerializable(annotated.token(), variables);
            case ArrayToken<?, ?> array -> isSerializable(array.component(), variables);
            case DeclaredToken<?> declared -> isSerializable(declared.shape());
            // a variable the generator vouched for has bounds no one knows
            case TypeVarToken<?> variable ->
                variables.stream()
                        .filter(bounded -> bounded.variable() == variable)
                        .flatMap(bounded -> bounded.bounds().stream())
                        .anyMatch(bound -> isSerializable(bound.shape()));
        };
    }

    /// Whether the type of `shape` is `Serializable`: the interface itself, or one that extends or
    /// implements it, which its shape lists ([TypeShape#interfaces()]).
    private static boolean isSerializable(TypeShape<?> shape) {
        return shape.desc().equals(CD_SERIALIZABLE) || shape.interfaces().contains(CD_SERIALIZABLE);
    }
}
