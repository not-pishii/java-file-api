package me.supcheg.javafile.facts;

import java.util.List;

/// What the declaration of a record tells beyond the facts of a class: its
/// components, in declaration order ([Of]), or that the type is no record
/// ([None]).
///
/// A record is a final class, so a shape of a record is of
/// [DeclaredKind.FinalClass] and its token a [FinalClassToken]; this is what
/// tells it from another final class, and an empty record from a class that
/// declares no component.
///
/// @see TypeShape#recordShape()
public sealed interface RecordShape permits RecordShape.None, RecordShape.Of {

    /// The shape of a type that is no record.
    // a record nested in the interface is made by its initializer as `Heritage.UNTOLD` is, which the check accepts
    @SuppressWarnings("ClassInitializationDeadlock")
    None NONE = new None();

    /// The components of the record, in declaration order; empty for a type that is no record.
    ///
    /// @return the components
    List<RecordComponent> components();

    /// The type is no record.
    record None() implements RecordShape {
        @Override
        public List<RecordComponent> components() {
            return List.of();
        }
    }

    /// The type is a record with these components, in declaration order; none for a record
    /// that declares no component.
    ///
    /// @param components the components
    record Of(List<RecordComponent> components) implements RecordShape {
        public Of {
            components = List.copyOf(components);
            for (RecordComponent component : components) {
                if (components.stream()
                                .filter(other -> other.name().equals(component.name()))
                                .count()
                        > 1) {
                    throw new IllegalArgumentException(
                            "a record declares the component " + component.name() + " twice: " + components);
                }
            }
        }
    }
}
