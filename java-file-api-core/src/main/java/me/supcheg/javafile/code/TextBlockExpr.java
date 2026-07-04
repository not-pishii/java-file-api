package me.supcheg.javafile.code;

/// A text block literal, rendered with `"""` delimiters.
///
/// @param value the text block content
public record TextBlockExpr(String value) implements Expr {}
