package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;

import java.util.List;
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

    /// This scope, and the type parameters a member declares of its own, which are in scope in the
    /// signature and the body of that member alone (JLS 6.3).
    ///
    /// @param variables the tokens of the type parameters of the member
    /// @param where the place, for messages
    /// @return the scope of the member
    default TypeScope method(List<TypeVarToken<?>> variables, String where) {
        return variables.isEmpty() ? this : new Method(this, variables, where);
    }

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
            case Method(TypeScope enclosing, List<TypeVarToken<?>> variables, String where) -> {
                if (!variables.contains(variable)) {
                    enclosing.require(type);
                }
            }
            case Standalone _ ->
                throw new IllegalStateException("the type parameter " + variable + mentioned + " is in scope in"
                        + " the class that declares it alone (JLS 6.3), and this code is in the body of no class");
            case Instance(DeclarationIdentity mine, String where) ->
                requireDeclaredBy(mine, declaration, variable + mentioned, where);
            case Static(DeclarationIdentity mine, String where) -> {
                requireDeclaredBy(mine, declaration, variable + mentioned, where);
                throw new IllegalStateException("the type parameter " + variable + mentioned + " is not in scope"
                        + " in " + where + ": a static member of a generic class refers neither to a type"
                        + " parameter of the class nor to the class with its type parameters (JLS 8.1.2)");
            }
            case Nested(List<DeclarationIdentity> declarations, String where) -> {
                if (!declarations.contains(declaration)) {
                    throw new IllegalStateException("the type parameter " + variable + mentioned + " is not in scope"
                            + " in " + where + ": it is a type parameter of another class, in scope in the"
                            + " declaration of that class alone (JLS 6.3)");
                }
            }
        }
    }

    private static void requireDeclaredBy(
            DeclarationIdentity mine, DeclarationIdentity declaration, String variable, String where) {
        if (mine != declaration) {
            throw new IllegalStateException("the type parameter " + variable + " is not in scope in " + where
                    + ": it is a type parameter of another declaration, of a class or of a method or constructor,"
                    + " in scope in the declaration of that one alone (JLS 6.3)");
        }
    }

    /// The type variables `type` mentions: itself, its component, its type arguments and their bounds.
    static Stream<TypeVarToken<?>> variables(TypeToken<?> type) {
        return switch (type) {
            case PrimitiveToken<?, ?, ?> _ -> Stream.empty();
            case me.supcheg.javafile.facts.AnnotatedRefToken<?> annotated -> variables(annotated.token());
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

    /// In the header or an instance member of a member type: the type parameters of the
    /// enclosing types, from the outermost in, and those of the type itself are in scope.
    ///
    /// @param declarations the declarations whose type parameters are in scope, the innermost last
    /// @param where the place, for messages
    record Nested(List<DeclarationIdentity> declarations, String where) implements TypeScope {
        public Nested {
            declarations = List.copyOf(declarations);
        }
    }

    /// In the signature or the body of a generic method or constructor: the type parameters of the
    /// member itself are in scope, and those of the classes it is declared in as they were
    /// ([Instance] or [Static]), which the signature and the body of the member are held to as well.
    ///
    /// A type parameter of a member is in scope nowhere else: not in another member of the class, not
    /// in a field, and not in a member type (JLS 6.3).
    ///
    /// @param enclosing the scope the member is declared in
    /// @param variables the tokens of the type parameters of the member
    /// @param where the place, for messages
    record Method(TypeScope enclosing, List<TypeVarToken<?>> variables, String where) implements TypeScope {
        public Method {
            variables = List.copyOf(variables);
        }
    }
}
