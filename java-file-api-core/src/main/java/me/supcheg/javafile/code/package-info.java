/// Code inside methods, constructors, and lambdas: statements and expressions.
///
/// - [me.supcheg.javafile.code.CodeBuilder] adds statements; `withBody`
///   methods of the builders pass you one.
/// - [me.supcheg.javafile.code.Exprs] creates expressions; import its methods
///   statically. Continue an expression with the methods of
///   [me.supcheg.javafile.code.Expr], e.g. `field("list").call("size")`.
/// - [me.supcheg.javafile.code.Patterns] creates patterns for `instanceof`
///   and `switch`.
///
/// ```java
/// import static me.supcheg.javafile.code.Exprs.*;
///
/// mb.withBody(b -> b
///         .localVar("sum", Types.INT, add(field("a"), field("b")))
///         .return_(field("sum")));
/// ```
@NullMarked
package me.supcheg.javafile.code;

import org.jspecify.annotations.NullMarked;
