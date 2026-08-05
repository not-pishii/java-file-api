/// Immutable value types describing method and constructor bodies.
///
/// [me.supcheg.javafile.code.Stmt] and [me.supcheg.javafile.code.Expr] are
/// the two sealed roots: statements form a [me.supcheg.javafile.code.CodeBody],
/// and expressions produce values within them. [me.supcheg.javafile.code.Exprs]
/// and the chaining methods on [me.supcheg.javafile.code.Expr] build
/// expressions; [me.supcheg.javafile.code.CodeBuilder] is the intended entry
/// point for assembling a body out of statements.
///
/// ```java
/// import static me.supcheg.javafile.code.Exprs.*;
///
/// CodeBuilder cb = new CodeBuilder();
/// cb.localVar("sum", add(literal(1), literal(2)));
/// cb.return_(field("sum"));
/// CodeBody body = cb.build();
/// ```
@NullMarked
package me.supcheg.javafile.code;

import org.jspecify.annotations.NullMarked;
