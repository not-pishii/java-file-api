/// Immutable value types describing method and constructor bodies.
///
/// [me.supcheg.javafile.code.Stmt] and [me.supcheg.javafile.code.Expr] are
/// the two sealed roots: statements form a [me.supcheg.javafile.code.CodeBody],
/// and expressions produce values within them. [me.supcheg.javafile.code.CodeBuilder]
/// is the intended entry point for assembling a body.
///
/// ```java
/// CodeBuilder cb = new CodeBuilder();
/// cb.localVar("sum", cb.add(cb.literal(1), cb.literal(2)));
/// cb.return_(cb.field("sum"));
/// CodeBody body = cb.build();
/// ```
@NullMarked
package me.supcheg.javafile.code;

import org.jspecify.annotations.NullMarked;
