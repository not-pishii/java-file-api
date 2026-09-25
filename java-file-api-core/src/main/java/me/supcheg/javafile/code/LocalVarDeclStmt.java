package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// A local variable declaration: `int x;`, `int x = 1;`, or `var x = 1;`.
/// A `var` declaration always has an initializer.
public sealed interface LocalVarDeclStmt extends Stmt permits LocalVarDeclStmt.Typed, LocalVarDeclStmt.Inferred {

    /// An explicitly typed local variable declaration, e.g. `int x;` or `int x = 1;`.
    ///
    /// @param type the declared variable type
    /// @param name the variable name, a valid Java identifier
    /// @param initializer the initializer expression, or empty to omit it
    record Typed(TypeRef type, String name, Optional<Expr> initializer) implements LocalVarDeclStmt {
        public Typed {
            name = Identifiers.requireValid(name);
        }
    }

    /// An inferred (`var`) local variable declaration, e.g. `var x = 1;`.
    ///
    /// @param name the variable name, a valid Java identifier
    /// @param initializer the initializer expression
    record Inferred(String name, Expr initializer) implements LocalVarDeclStmt {
        public Inferred {
            name = Identifiers.requireValid(name);
        }
    }
}
