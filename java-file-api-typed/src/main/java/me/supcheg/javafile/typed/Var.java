package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// A variable of the generated code: a local, a parameter, a lambda
/// parameter, a loop variable, a pattern binding, or a caught exception
/// (§6.2).
///
/// A variable is only ever handed to the lambda that is its scope, so using
/// it before its declaration is a compile error of the generator. Every
/// variable knows the block that owns it — the block its declaration is in
/// scope for — and a statement that refers to it can be appended only to that
/// block or a block nested in it: a variable that escapes its lambda anyway
/// (stored in a field or a list and used later, in a sibling branch, after
/// its block, or in another method) is rejected when that statement is
/// built, with the stack pointing at the misuse. Names are assigned by
/// lowering, deterministically and without collisions.
///
/// A plain `Var` cannot be assigned, so it is effectively final and a lambda
/// may capture it; a [MutVar] can be assigned and cannot be captured.
///
/// @param <T> the Java type of the variable
public sealed class Var<T> extends Expr<T> permits MutVar {
    private final Node local;
    private final Role role;
    private final Block<?, ?> owner;

    Var(TypeToken<T> type, Role.Fixed role, Block<?, ?> owner) {
        this(type, (Role) role, owner);
    }

    /// For a [MutVar], of a role of its own.
    Var(TypeToken<T> type, Role role, Block<?, ?> owner) {
        super(type);
        this.local = new Node.Local(this);
        this.role = role;
        this.owner = owner;
    }

    static <T> Var<T> param(TypeToken<T> type, Block<?, ?> owner) {
        return new Var<>(type, Role.Fixed.PARAMETER, owner);
    }

    /// What a variable is in the code: one that cannot be assigned, or a [MutVar].
    sealed interface Role {
        /// How the role is told in a message.
        String described();

        /// The role of a variable that cannot be assigned.
        enum Fixed implements Role {
            PARAMETER("parameter"),
            /// The last parameter of a member declared `@SafeVarargs` whose array is not reifiable: the array
            /// is read, stored into and looped over, and leaves the member nowhere (JLS 9.6.4.7).
            SAFE_VARARGS_PARAMETER("parameter of variable arity of a @SafeVarargs member"),
            LAMBDA_PARAMETER("lambda parameter"),
            LOCAL("local variable"),
            RESOURCE("resource variable"),
            PATTERN_BINDING("pattern binding"),
            CAUGHT_EXCEPTION("caught exception"),
            /// The variable of a `for`-each loop.
            FOR_EACH_VARIABLE("loop variable");

            private final String described;

            Fixed(String described) {
                this.described = described;
            }

            @Override
            public String described() {
                return described;
            }
        }

        /// The role of a [MutVar].
        enum Mutable implements Role {
            LOCAL("mutable local variable"),
            /// The variable of a `for` loop, in scope in the condition and the update of the loop as
            /// in its body.
            FOR_VARIABLE("loop variable");

            private final String described;

            Mutable(String described) {
                this.described = described;
            }

            @Override
            public String described() {
                return described;
            }
        }
    }

    /// Whether this is the variable of a `for` loop, in scope in the condition and the update of the
    /// loop as in its body.
    boolean isVariableOfFor() {
        return role == Role.Mutable.FOR_VARIABLE;
    }

    /// Whether this is the parameter of variable arity of a `@SafeVarargs` member.
    boolean isSafeVarargsParameter() {
        return role == Role.Fixed.SAFE_VARARGS_PARAMETER;
    }

    /// The block this variable is in scope for.
    Block<?, ?> owner() {
        return owner;
    }

    @Override
    Node node() {
        return local;
    }

    @Override
    public String toString() {
        return role.described() + " of type " + type();
    }
}
