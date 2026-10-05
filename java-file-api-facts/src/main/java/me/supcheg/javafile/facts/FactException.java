package me.supcheg.javafile.facts;

import java.io.Serial;

/// Thrown when what a generator says of the target code is not so there:
/// a fact it asks for or renders code of does not hold of the types the
/// code is compiled against. Not an error of the code of the generator, as
/// an `IllegalArgumentException` of the typed layer is, but of the
/// classpath it runs with — another version of a library, another type
/// behind a mirror.
///
/// - [FactLookupException] — one fact: a member a source cannot prove, an
///   enum constant the enum does not have, a member javac cannot be made
///   to resolve among the overloads of its owner.
/// - [TargetClasspathMismatchException] — a whole metamodel: the type on
///   the target classpath is not the one it was generated from, and what
///   the metamodel tells of it no longer holds.
///
/// A generator that tells both the same way catches this one, and tells
/// them apart by a `switch` that the compiler checks for every case:
///
/// ```java
/// try {
///     JavaFile file = TypedJavaFile.class_(target, desc, spec);
///     JavaFileWriter.writeTo(file, processingEnv.getFiler(), element);
/// } catch (FactException e) {
///     processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
/// }
/// ```
///
/// ```java
/// String hint = switch (e) {
///     case FactLookupException lookup -> "did you mean " + lookup.similar();
///     case TargetClasspathMismatchException mismatch -> "regenerate " + mismatch.metamodel().metamodel();
/// };
/// ```
public abstract sealed class FactException extends RuntimeException
        permits FactLookupException, TargetClasspathMismatchException {
    @Serial
    private static final long serialVersionUID = 1L;

    /// @param message what does not hold, of which type, and where it was looked for
    FactException(String message) {
        super(message);
    }
}
