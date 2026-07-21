package me.supcheg.javafile.code;

import me.supcheg.javafile.model.TypeDecl;

/// A local class/interface/record/enum declaration used as a statement.
///
/// @param decl the declared local type
public record LocalTypeDeclStmt(TypeDecl decl) implements Stmt {}
