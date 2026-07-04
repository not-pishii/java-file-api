package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// A local variable declaration.
///
/// @param type the declared variable type, or empty to infer it with `var`
/// @param name the variable name
/// @param initializer the initializer expression
public record LocalVarDeclStmt(Optional<TypeRef> type, String name, Expr initializer) implements Stmt {}
