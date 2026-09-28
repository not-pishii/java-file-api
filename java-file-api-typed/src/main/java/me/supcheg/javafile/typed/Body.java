package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;

import java.util.List;

/// The body of a method or constructor defined in a [TypeHandle]; run once, before the symbol is returned.
@FunctionalInterface
public interface Body {

    /// Populates the body.
    ///
    /// @param code the builder of the body's statements
    /// @param params the parameters as expressions, in declaration order
    void accept(CodeBuilder code, List<Expr> params);
}
