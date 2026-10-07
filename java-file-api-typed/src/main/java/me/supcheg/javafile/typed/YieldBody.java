package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;
import org.jspecify.annotations.Nullable;

import java.util.List;

/// The block of a case of a `switch` expression, `case A -> { ... }` (§6.3):
/// it ends with [#yield_(Expr)], which gives the value of the `switch`, or
/// with a statement that cannot complete normally, such as
/// [Block#throw_(Expr)].
///
/// There is no `return_` here, in this block or in a block nested in it: a
/// `return` out of a `switch` expression is not Java (JLS 15.28.1), and so
/// it is not representable. Neither can the block `break_` or `continue_` a
/// loop around the `switch`, which is rejected where it is built; a loop
/// inside the block is broken and continued as anywhere. A lambda built in
/// the block has a body of its own, which returns.
///
/// A checked exception thrown here is one of the block the `switch` is
/// built in: it is caught by a `catch_` around it or declared by the member.
///
/// @param <Y> the type of the `switch` expression
public final class YieldBody<Y> extends Block<Y, YieldBody<Y>> {
    private final TypeToken<Y> type;

    private YieldBody(
            @Nullable Block<?, ?> parent,
            Nesting nesting,
            String what,
            ExceptionScope exceptionScope,
            TypeToken<Y> type) {
        super(parent, nesting, what, exceptionScope);
        this.type = type;
    }

    /// The block of a case of a `switch` expression built in `enclosing`.
    ///
    /// @param enclosing the block the `switch` is built in, or `null` outside of any body, where nothing
    ///     catches and nothing declares
    /// @param type the type of the `switch`
    /// @param what the case, for diagnostics
    static <Y> YieldBody<Y> ofCase(@Nullable Block<?, ?> enclosing, TypeToken<Y> type, String what) {
        return new YieldBody<>(
                enclosing,
                Nesting.SWITCH_BLOCK,
                what,
                enclosing == null
                        ? new ExceptionScope.Declares(ExceptionScope.Boundary.INITIALIZER, List.of())
                        : ExceptionScope.PASSES,
                type);
    }

    @Override
    YieldBody<Y> self() {
        return this;
    }

    @Override
    YieldBody<Y> child(String what, Nesting nesting, ExceptionScope exceptionScope) {
        return new YieldBody<>(this, nesting, what, exceptionScope, type);
    }

    /// Appends `yield value;` and ends this block: `value` is the value of
    /// the `switch` expression. As every result of the `switch`, it is cast
    /// to the type of the `switch` where its own type is another.
    ///
    /// @param value the yielded expression
    /// @return the proof that this block ended
    /// @throws IllegalArgumentException if `value` is primitive and the type of the `switch` is not, or
    ///     the reverse
    public Terminated<Y> yield_(Expr<? extends Y> value) {
        return appendFinal(new Instr.Yield(Expressions.result(value, type, Expressions.Choice.SWITCH)), "yield_");
    }
}
