package me.supcheg.javafile.code;

import java.util.List;

/// An instance creation qualified by an enclosing instance, `outer.new Inner(args)` (JLS
/// 15.9.2): the constructor of an inner class takes the instance of the class that encloses it.
///
/// @param enclosing the instance of the enclosing class
/// @param target the instantiated inner class
/// @param args the constructor arguments, in order
public record InnerNewExpr(Expr enclosing, NewTarget target, List<Expr> args) implements Expr {
    public InnerNewExpr {
        args = List.copyOf(args);
    }
}
