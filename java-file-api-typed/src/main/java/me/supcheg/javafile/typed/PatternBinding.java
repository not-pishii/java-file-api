package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;
import org.jspecify.annotations.Nullable;

/// The binding of a type pattern, `T name`, before the pattern it belongs to
/// is matched: the variable is made when the branch the pattern opens is
/// built, and is in scope there only.
///
/// A binding is used once: a pattern is matched where it is written, so the
/// same binding cannot stand in two of them. [#var()] gives the variable,
/// and is called in the branch the pattern is matched in, once it is built:
///
/// ```java
/// PatternBinding<Prim.Int> x = Patterns.binding(PrimitiveToken.INT);
/// b.ifInstanceOf(shape, Patterns.record(Point_.TOKEN, Patterns.type(x)), then -> then.return_(x.var()));
/// ```
///
/// A variable asked for before the pattern is matched, or after the branch
/// it belongs to, is rejected: a pattern binding is in scope in the branch
/// only (JLS 6.3), and a statement of another block that refers to it is
/// rejected where it is appended ([ScopeCheck]).
///
/// @param <T> the type of the value the pattern binds
public final class PatternBinding<T> {
    private final TypeToken<T> type;
    private @Nullable Var<T> binding;

    PatternBinding(TypeToken<T> type) {
        this.type = type;
    }

    /// The variable the pattern binds.
    ///
    /// @return the variable
    /// @throws IllegalStateException if the pattern the binding is of is not matched yet
    public Var<T> var() {
        if (binding == null) {
            throw new IllegalStateException("the binding of the pattern " + this
                    + " is asked for before the pattern is matched: a pattern binding is in scope in the branch"
                    + " it opens, and nowhere else (JLS 6.3)");
        }
        return binding;
    }

    /// The type of the value the pattern binds.
    ///
    /// @return the type
    public TypeToken<T> type() {
        return type;
    }

    /// Makes the variable of the pattern, in `scope`: the branch the pattern opens.
    ///
    /// @param scope the block the binding is in scope in
    /// @return the variable
    /// @throws IllegalStateException if the binding was made already: a pattern is matched once
    Var<T> bind(Block<?, ?> scope) {
        if (binding != null) {
            throw new IllegalStateException("the binding of the pattern " + this + " is made twice: a pattern is"
                    + " matched where it is written, so a binding is the binding of one pattern");
        }
        binding = new Var<>(type, Var.Role.Fixed.PATTERN_BINDING, scope);
        return binding;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
