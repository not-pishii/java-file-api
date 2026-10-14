package me.supcheg.javafile.code;

/// The body of a constructor of a class: its statements, with or without an
/// explicit invocation of another constructor among them (JLS 8.8.7).
///
/// The invocation has a place of its own here and is no statement, so
/// `super(...)` cannot be said in a method, twice, or in a loop.
public sealed interface ConstructorBody permits ConstructorBody.Implicit, ConstructorBody.Explicit {

    /// The body with no statements, which begins with the implicit `super();`.
    ConstructorBody EMPTY = new Implicit(CodeBody.EMPTY);

    /// A body without an explicit constructor invocation: the compiler begins
    /// it with `super();`.
    ///
    /// @param statements the statements of the body
    record Implicit(CodeBody statements) implements ConstructorBody {}

    /// A body with an explicit constructor invocation.
    ///
    /// @param prologue the statements before the invocation, which Java 25
    ///                 allows (JLS 8.8.7, "flexible constructor bodies"); they
    ///                 run before the object is initialized
    /// @param invocation the `super(...)` or `this(...)` invocation
    /// @param epilogue the statements after the invocation
    record Explicit(CodeBody prologue, ConstructorInvocation invocation, CodeBody epilogue)
            implements ConstructorBody {}
}
