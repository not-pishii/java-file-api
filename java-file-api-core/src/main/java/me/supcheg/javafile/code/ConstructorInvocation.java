package me.supcheg.javafile.code;

import java.util.List;

/// An explicit constructor invocation: `super(args);` or `this(args);`
/// (JLS 8.8.7.1). A constructor has at most one, and nothing else has any, so
/// it is no [Stmt]: it is a part of a [ConstructorBody].
public sealed interface ConstructorInvocation permits ConstructorInvocation.Super, ConstructorInvocation.This {

    /// The arguments of the invoked constructor.
    ///
    /// @return the arguments, in order
    List<Expr> args();

    /// An invocation of a constructor of the direct superclass, `super(args);`.
    ///
    /// @param args the arguments of the superclass constructor
    record Super(List<Expr> args) implements ConstructorInvocation {
        /// Creates an invocation of a superclass constructor.
        public Super {
            args = List.copyOf(args);
        }
    }

    /// An invocation of another constructor of the same class, `this(args);`.
    ///
    /// @param args the arguments of the other constructor
    record This(List<Expr> args) implements ConstructorInvocation {
        /// Creates an invocation of another constructor of the class.
        public This {
            args = List.copyOf(args);
        }
    }
}
