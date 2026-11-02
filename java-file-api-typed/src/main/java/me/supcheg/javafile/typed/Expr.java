package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeToken;

import java.util.Locale;
import java.util.Optional;

/// An expression of the generated code whose Java type is `T` (§6.1).
///
/// `T` is a phantom: javac checks every use of an expression against the
/// facts it is combined with, and lowering erases it. Subtyping comes for free
/// through wildcards — a combinator taking `Expr<? extends CharSequence>`
/// accepts an `Expr<String>`.
///
/// An expression also carries the token of its static type, from which
/// lowering learns what javac will: whether it is primitive, what exception
/// type a `throw` throws.
///
/// Start an expression with a static method of [Expressions], then continue it
/// with the methods of this class, which read left to right like Java:
///
/// ```java
/// self.field(greeting).call(String_.trim)          // this.greeting.trim()
/// items.arrayAccess(strings, literal(0))           // items[0]
/// ```
///
/// Expressions are created by [Expressions], by the statements that bind
/// variables, and — untyped —
/// by [Unsafe#expr(me.supcheg.javafile.code.Expr, TypeToken)]. There is no
/// public constructor.
///
/// @param <T> the Java type of the expression
public abstract sealed class Expr<T> extends ExprArities<T> permits Expr.Built, Var, Invocation, This, Increment {
    private final TypeToken<T> type;

    Expr(TypeToken<T> type) {
        this.type = type;
    }

    /// The token of the expression's static type.
    ///
    /// @return the type token
    public final TypeToken<T> type() {
        return type;
    }

    /// Reads an instance field, `target.field`.
    ///
    /// The field is declared by the static type of this expression or by a supertype of it, so a field
    /// the type does not have does not compile.
    ///
    /// @param field the field
    /// @param <F> the field type
    /// @return the field read
    /// @throws IllegalArgumentException if this expression is of a primitive type
    public <F> Expr<F> field(FieldRef<? super T, F> field) {
        return Expr.of(new Node.FieldGet(Expressions.receiver(this, field), field), field.type());
    }

    /// Reads an array element, `array[index]`.
    ///
    /// The array is of exactly the type `type` names: an array of a subtype of its component is no
    /// array of the component here, as it is to Java (JLS 10.10), for javac types the element by the
    /// array it reads — a `String` of a `String[]`, whatever supertype the generator took it for. Bind
    /// the array to a variable of the wider array type first to read it as one.
    ///
    /// @param type the array type
    /// @param index the index
    /// @param <E> the element type
    /// @return the element read
    public <E> Expr<E> arrayAccess(ArrayToken<T, E> type, Expr<Prim.Int> index) {
        return Expr.of(new Node.ArrayAt(node(), index.node()), type.component());
    }

    /// Reads `array.length`.
    ///
    /// @param type the array type
    /// @return the length
    public Expr<Prim.Int> length(ArrayToken<T, ?> type) {
        return Expr.of(new Node.ArrayLength(node()), PrimitiveToken.INT);
    }

    /// A checked cast, `(T) operand`. The cast type is a subtype of the static type of this expression,
    /// so an unrelated cast does not compile, and it must be checked (JLS 5.1.6.2), so the run-time
    /// check covers all of it: either the cast type is reifiable, or this expression's type is a
    /// parameterized supertype of it that determines its type arguments. `listOfStrings.cast(ArrayList)`
    /// is accepted, `object.cast(List<String>)` is not.
    ///
    /// @param type the target type
    /// @param <U> the target type
    /// @return the cast, or this expression if its type is already `U`
    /// @throws IllegalArgumentException if the cast would be unchecked
    public <U extends T> Expr<U> cast(RefToken<U> type) {
        Tokens.requireCheckedCast(this.type, type, "cast");
        if (Tokens.sameType(this.type, type)) {
            // `(U) this` would be a redundant cast (javac -Xlint:cast); lowering pins the operand
            // where the type javac gives it matters.
            return Expr.of(node(), type);
        }
        return Expr.of(new Node.Cast(type, Expressions.operand(this)), type);
    }

    /// A plain `instanceof` test, `operand instanceof type`, without a pattern binding. It is `false`
    /// for a `null` operand and never throws (JLS 15.20.2).
    ///
    /// The test must be a checked cast (JLS 15.20.2, 5.1.6.2): `type` is reifiable, or the static type
    /// of this expression is a parameterized supertype of it that determines its type arguments.
    /// `object.instanceOf(List<String>)` is rejected, `listOfStrings.instanceOf(ArrayList<String>)`
    /// accepted.
    ///
    /// @param type the tested type
    /// @param <U> the tested type
    /// @return the test
    /// @throws IllegalArgumentException if this expression is of a primitive type, or the test would
    ///     be unchecked
    public <U> Expr<Prim.Bool> instanceOf(RefToken<U> type) {
        if (this.type instanceof PrimitiveToken<?, ?, ?> primitive) {
            throw new IllegalArgumentException("instanceOf tests a reference, and the operand is of the primitive"
                    + " type " + primitive + "; box it first, e.g. box(PrimitiveToken."
                    + primitive.typeRef().sourceName().toUpperCase(Locale.ROOT) + ", expr)");
        }
        Tokens.requireCheckedCast(this.type, type, "an instanceof test");
        return Expr.of(new Node.InstanceOf(Expressions.operand(this), type), PrimitiveToken.BOOLEAN);
    }

    /// A method reference bound to this expression, `receiver::method`, of the functional interface
    /// `samType` whose method `sam` has the parameters and result of `method`.
    ///
    /// @param method the fact of the referenced method, an instance method of this expression's type
    /// @param samType the functional interface the reference implements
    /// @param sam the single abstract method of `samType`
    /// @param <F> the functional interface
    /// @return the method reference
    /// @throws IllegalArgumentException if `method` is not an instance method of this expression's type,
    ///     or its parameters or result are not those of `sam`
    public <F> Expr<F> methodRef(Invocable method, InterfaceToken<F> samType, Invocable sam) {
        Node.Operand target = Expressions.receiver(this, method);
        MethodReferences.bound(target.type(), method, samType, sam);
        return Expr.of(new Node.MethodRef(Optional.of(target), method.owner(), method, sam), samType);
    }

    /// The expression, as the typed layer represents it.
    abstract Node node();

    /// This expression, as its own type: the hook the generated forms of [ExprArities] continue.
    ///
    /// @return this expression
    @Override
    final Expr<T> self() {
        return this;
    }

    static <T> Expr<T> of(Node node, TypeToken<T> type) {
        return new Built<>(node, type);
    }

    /// An expression a combinator built of its operands.
    ///
    /// @param <T> the Java type of the expression
    static final class Built<T> extends Expr<T> {
        private final Node node;

        private Built(Node node, TypeToken<T> type) {
            super(type);
            this.node = node;
        }

        @Override
        Node node() {
            return node;
        }
    }
}
