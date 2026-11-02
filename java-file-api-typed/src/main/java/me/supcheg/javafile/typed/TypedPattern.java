package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.TypeToken;

import java.util.List;
import java.util.stream.Stream;

/// A pattern of the typed layer (JLS 14.30): a type test that binds a value,
/// or a record deconstruction whose components are patterns of their own.
///
/// A pattern is made by [Patterns], which checks it against the facts of the
/// types it names: a record pattern has one pattern per component, each of a
/// type the component has, and names a record. A [Type] binds through a
/// [PatternBinding], which is in scope in the branch the pattern opens.
///
/// @param <T> the type the pattern matches
public sealed interface TypedPattern<T> permits TypedPattern.Type, TypedPattern.Deconstruction {

    /// The type the pattern matches: the type test of a [Type], the record of a [Deconstruction].
    ///
    /// @return the type
    TypeToken<?> matched();

    /// Every binding of the pattern, in the order the components appear.
    ///
    /// @return the bindings
    Stream<PatternBinding<?>> bindings();

    /// `T name`: the value is tested to be a `T` and bound to `name`.
    ///
    /// @param type the matched type
    /// @param binding the binding
    /// @param <T> the matched type
    record Type<T>(TypeToken<T> type, PatternBinding<T> binding) implements TypedPattern<T> {

        @Override
        public TypeToken<?> matched() {
            return type;
        }

        @Override
        public Stream<PatternBinding<?>> bindings() {
            return Stream.of(binding);
        }
    }

    /// `R(p1, ..., pn)`: the value is deconstructed as the record `R`, and
    /// each component is matched by the pattern at its position.
    ///
    /// @param record the deconstructed record
    /// @param components the patterns of the components, in declaration order
    /// @param <T> the record type
    record Deconstruction<T>(DeclaredToken<T> record, List<TypedPattern<?>> components) implements TypedPattern<T> {
        public Deconstruction {
            components = List.copyOf(components);
        }

        @Override
        public TypeToken<?> matched() {
            return record;
        }

        @Override
        public Stream<PatternBinding<?>> bindings() {
            return components.stream().flatMap(TypedPattern::bindings);
        }
    }
}
