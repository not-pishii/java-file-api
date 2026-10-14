package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;

import java.util.List;

/// `super(args);`, the explicit invocation a constructor of a class with a superclass begins with: no
/// statement of a block (JLS 8.8.7.1), and kept apart from the statements of the body that follow it.
///
/// @param ctor the fact of the constructor of the superclass
/// @param args the arguments, with their static types
record SuperInvocation(Invocable ctor, List<Node.Operand> args) {
    SuperInvocation {
        args = List.copyOf(args);
    }
}
