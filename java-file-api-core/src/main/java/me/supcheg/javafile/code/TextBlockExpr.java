package me.supcheg.javafile.code;

/// A text block literal, rendered with `"""` delimiters.
///
/// A value that holds `\u0085`, `\u2028` or `\u2029` is rendered as an ordinary string literal
/// instead: javac's white space check splits a text block on them, though the source does not
/// read them as line terminators, and no escape writes them into a block.
///
/// @param value the text block content
public record TextBlockExpr(String value) implements Expr {}
