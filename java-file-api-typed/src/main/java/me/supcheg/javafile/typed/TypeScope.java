package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;

import java.util.stream.Stream;

/// The type parameters that are in scope where a type is mentioned: a type
/// parameter of a class the typed layer declares is in scope in the header
/// and the instance members of that class, and nowhere else (JLS 6.3) — not
/// in another class, where the name would mean nothing or another variable,
/// and not in a `static` member of its own class (JLS 8.1.2), where neither
/// the parameter nor the class with its parameters for type arguments, the
/// type of `this`, may be named.
///
/// A type variable is known by its token, which tells the declaration it is
/// a type parameter of ([TypeVarToken.Origin]): two of one name are two
/// variables. One a generator vouched for
/// ([me.supcheg.javafile.facts.UnsafeFacts#typeVarToken]) is in scope where
/// the generator says it is.
sealed interface TypeScope {

    /// Holds a type to the scope: every type variable it mentions, at any depth, is in scope.
    ///
    /// @param type the type
    /// @throws IllegalStateException if a type parameter of a class is mentioned out of its scope
    default void require(TypeToken<?> type) {
        variables(type).forEach(variable -> {
            switch (variable.origin()) {
                case TypeVarToken.Origin.Vouched _ -> {}
                case TypeVarToken.Origin.Declared(DeclarationIdentity declaration) ->
                    require(type, variable, declaration);
            }
        });
    }

    private void require(TypeToken<?> type, TypeVarToken<?> variable, DeclarationIdentity declaration) {
        String mentioned = type == variable ? "" : ", which " + type + " mentions,";
        switch (this) {
            case Standalone _ ->
                throw new IllegalStateException("the type parameter " + variable + mentioned + " is in scope in"
                        + " the class that declares it alone (JLS 6.3), and this code is in the body of no class");
            case Instance(DeclarationIdentity mine, String where) -> {
                if (mine != declaration) {
                    throw new IllegalStateException("the type parameter " + variable + mentioned + " is not in"
                            + " scope in " + where + ": it is a type parameter of another class, in scope in"
                            + " the declaration of that class alone (JLS 6.3)");
                }
            }
            case Static(DeclarationIdentity mine, String where) -> {
                if (mine != declaration) {
                    throw new IllegalStateException("the type parameter " + variable + mentioned + " is not in"
                            + " scope in " + where + ": it is a type parameter of another class, in scope in"
                            + " the declaration of that class alone (JLS 6.3)");
                }
                throw new IllegalStateException("the type parameter " + variable + mentioned + " is not in scope"
                        + " in " + where + ": a static member of a generic class refers neither to a type"
                        + " parameter of the class nor to the class with its type parameters (JLS 8.1.2)");
            }
        }
    }

    /// The type variables `type` mentions: itself, its component, its type arguments and their bounds.
    private static Stream<TypeVarToken<?>> variables(TypeToken<?> type) {
        return switch (type) {
            case PrimitiveToken<?, ?, ?> _ -> Stream.empty();
            case TypeVarToken<?> variable -> Stream.of(variable);
            case ArrayToken<?, ?> array -> variables(array.component());
            case DeclaredToken<?> declared ->
                declared.typeArguments().stream().flatMap(argument -> switch (argument) {
                    case TokenArg.Exact(var exact) -> variables(exact);
                    case TokenArg.Extends(var bound) -> variables(bound);
                    case TokenArg.Super(var bound) -> variables(bound);
                    case TokenArg.Unbounded _ -> Stream.<TypeVarToken<?>>empty();
                });
        };
    }

    /// In the body of no class.
    record Standalone() implements TypeScope {}

    /// In the header or an instance member of a class: its type parameters are in scope.
    ///
    /// @param declaration the declaration of the class, known by its identity
    /// @param where the place, for messages
    record Instance(DeclarationIdentity declaration, String where) implements TypeScope {}

    /// In a `static` member of a class: no type parameter is in scope.
    ///
    /// @param declaration the declaration of the class, known by its identity
    /// @param where the place, for messages
    record Static(DeclarationIdentity declaration, String where) implements TypeScope {}
}
