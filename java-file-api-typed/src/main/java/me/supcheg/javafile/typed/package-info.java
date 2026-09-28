/// Generating code from symbols that are proven to exist.
///
/// A [me.supcheg.javafile.typed.MethodSym], [me.supcheg.javafile.typed.FieldSym],
/// or [me.supcheg.javafile.typed.CtorSym] has no public constructor: it comes from
/// an [me.supcheg.javafile.typed.Env], which reads class files, or from a
/// [me.supcheg.javafile.typed.TypeHandle] of a [me.supcheg.javafile.typed.Unit],
/// which returns it only after the member is fully defined. A false member
/// fails where it is asked for, with [me.supcheg.javafile.typed.NoSuchSymbolException].
/// [me.supcheg.javafile.typed.Syms] turns symbols into core expressions.
///
/// ```java
/// Env env = Env.of(getClass().getClassLoader());
/// MethodSym charAt = env.method(CD_String, "charAt", MethodTypeDesc.of(CD_char, CD_int));
///
/// Unit unit = new Unit(env);
/// unit.class_(ClassDesc.of("com.example", "Strings")).defineStaticMethod(
///         "first", MethodTypeDesc.of(CD_char, CD_String), List.of("s"),
///         (code, p) -> code.return_(Syms.call(p.getFirst(), charAt, Exprs.literal(0))));
/// List<JavaFile> files = unit.build();
/// ```
///
/// Guarantee boundary: the proofs hold for code built only from symbols.
/// Raw core expressions such as `Exprs.call("name")`, `header`/`config`
/// consumers that add members or change static-ness, and `transform*` on the
/// built files are not checked and can break them. Signatures are erased, so
/// generic types and argument types are not checked.
@NullMarked
package me.supcheg.javafile.typed;

import org.jspecify.annotations.NullMarked;
