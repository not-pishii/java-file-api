package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/// Combinators for typed expressions and effects (§6.1): literals, member
/// access, calls, `new`, arrays, `cond`, per-primitive operators, explicit
/// conversions, assignments, enum constants, `switch` expressions over an
/// enum ([#switch_]) and lambdas (`lambda`, `lambdaBlock`).
///
/// Primitive values are typed by their [Prim] markers: `literal(1)` is an
/// `Expr<Prim.Int>`, never an `Expr<Integer>`. Boxing and unboxing are
/// explicit — [#box(PrimitiveToken, Expr)] and [#unbox(PrimitiveToken, Expr)]
/// — so passing an `int` where an `Integer` is expected, or the reverse, is
/// a compile error of the generator. A marker is still a subtype of
/// `Object`, so where a reference is taken a primitive value is refused at
/// run time, with a hint to box it — Java would box it implicitly:
///
/// - a primitive receiver — `call(literal(1), Object_.toString)` — when the
///   call is built;
/// - the initializer of a variable or a field, an assigned value, the value
///   of an expression lambda, when it is built;
/// - an argument — `call(list, remove_Object, literal(1))` — and a returned
///   value, where the body is defined.
///
/// **The fact chosen is the member called** (§6.1, §10). A fact names one
/// overload, one field; lowering makes javac select exactly it, whatever
/// else the receiver type declares:
///
/// - an argument whose static type is not the parameter's is cast to it,
///   `out.println((Object) s)`, unless the method table of the receiver type
///   proves the fact is its only method of that name and arity;
/// - a field read or assigned through a receiver whose static type is not
///   the field's owner is qualified by a cast, `((Base) sub).f`, so a field
///   of the subtype that hides it is never picked;
/// - `null` is always typed, `((String) null)`, see [#literalNull(RefToken)];
/// - a static member of a generic type is qualified by the raw type, and a
///   generic method gets the type arguments of its fact as explicit
///   witnesses, `List.<String>of()`, `stream.<R>map(f)`;
/// - `new` never infers: `new ArrayList<String>()`, with the diamond only
///   where the declared type of the variable, field or returned value is
///   exactly the constructed type — `ArrayList<String> v = new ArrayList<>()`.
///
/// **Arities.** `call`, `voidCall`, `staticCall`, `voidStaticCall`, `new_`, `lambda` and `lambdaBlock` take the fact
/// of a member of any number of parameters, 0 to 12: one overload for each `MethodRefN`, `CtorRefN`, `SamN` of the
/// facts. They are generated (`TypedCodegen`) into the superclass of this class, and inherited as static methods, so
/// that an `import static` of them finds them.
///
/// **Variable arity.** The last parameter of a member of variable arity is given an array the code has, as an
/// argument of its array type, or its elements one by one, `varargs(type, e1, e2)` as the last argument of a call.
/// The elements of a list are given by the same form, `varargs(type, elements)`.
///
/// **Arrays.** `newArrayOf(type, e1, e2)` creates an array with its elements, `{e1, e2}` where a declaration of
/// exactly its type tells the type; `newArray(type, length, ...)` one by its lengths, `cloneArray` copies an array
/// without a cast, `at`, `length` and `assignAt` read, measure and store into one, and `forEach` of a block loops
/// over its elements.
///
/// Import the methods statically.
public final class Expressions extends ExpressionArities {

    private Expressions() {}

    // ------------------------------------------------------------------
    // Literals
    // ------------------------------------------------------------------

    /// Creates a `String` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<String> literal(String value) {
        return Expr.of(new Node.Lit(new me.supcheg.javafile.code.StringLiteral(value)), String_.TOKEN);
    }

    /// Creates an `int` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Int> literal(int value) {
        return Expr.of(new Node.Lit(new me.supcheg.javafile.code.IntLiteral(value)), PrimitiveToken.INT);
    }

    /// Creates a `long` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Long> literal(long value) {
        return Expr.of(new Node.Lit(new me.supcheg.javafile.code.LongLiteral(value)), PrimitiveToken.LONG);
    }

    /// Creates a `double` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Double> literal(double value) {
        return Expr.of(new Node.Lit(new me.supcheg.javafile.code.DoubleLiteral(value)), PrimitiveToken.DOUBLE);
    }

    /// Creates a `boolean` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Bool> literal(boolean value) {
        return Expr.of(new Node.Lit(new me.supcheg.javafile.code.BooleanLiteral(value)), PrimitiveToken.BOOLEAN);
    }

    /// Creates a text block literal of type `String`, rendered with `"""` delimiters, or as an escaped
    /// string literal where the value cannot be written in a block (a `U+0085`, `U+2028` or `U+2029` in it).
    ///
    /// @param value the text block content
    /// @return the literal
    public static Expr<String> textBlock(String value) {
        return Expr.of(new Node.TextBlock(value), String_.TOKEN);
    }

    /// Creates the `null` literal at the given reference type, rendered as
    /// `((T) null)`: the bare `null` has no type of its own to javac, so
    /// `null.length()`, `null + null` or a call of an overloaded method with
    /// `null` would not compile or would pick another overload. A primitive
    /// type has no `null`, so a [PrimitiveToken] is not accepted.
    ///
    /// @param type the declared static type of the literal
    /// @param <T> the type
    /// @return the literal
    public static <T> Expr<T> literalNull(RefToken<T> type) {
        return Expr.of(new Node.Null(type), type);
    }

    // ------------------------------------------------------------------
    // Boxing (§6.1): explicit only
    // ------------------------------------------------------------------

    /// Boxes a primitive value, `Integer.valueOf(value)`.
    ///
    /// @param type the primitive type
    /// @param value the primitive value
    /// @param <P> the marker of the primitive type
    /// @param <B> the box
    /// @return the boxed value
    public static <P, B> Expr<B> box(PrimitiveToken<P, B, ?> type, Expr<P> value) {
        return Expr.of(new Node.Box(type, value.node()), type.boxed());
    }

    /// Unboxes a box, `value.intValue()`; it throws `NullPointerException`
    /// at run time for `null`, which the explicit call makes visible.
    ///
    /// @param type the primitive type
    /// @param value the boxed value
    /// @param <P> the marker of the primitive type
    /// @param <B> the box
    /// @return the primitive value
    public static <P, B> Expr<P> unbox(PrimitiveToken<P, B, ?> type, Expr<? extends B> value) {
        return Expr.of(new Node.Unbox(type, value.node()), type);
    }

    // ------------------------------------------------------------------
    // Member access
    // ------------------------------------------------------------------

    /// Reads an instance field, `target.field`.
    ///
    /// @param target the receiver
    /// @param field the field
    /// @param <O> the owner type
    /// @param <T> the field type
    /// @return the field read
    /// @throws IllegalArgumentException if `target` is of a primitive type
    public static <O, T> Expr<T> field(Expr<? extends O> target, FieldRef<O, T> field) {
        return Expr.of(new Node.FieldGet(receiver(target, field), field), field.type());
    }

    /// Reads a static field, `Owner.field`.
    ///
    /// @param field the field
    /// @param <T> the field type
    /// @return the field read
    public static <T> Expr<T> staticField(StaticFieldRef<T> field) {
        return Expr.of(new Node.StaticFieldGet(field), field.type());
    }

    // ------------------------------------------------------------------
    // Calls and instance creation: `call`, `voidCall`, `staticCall`, `voidStaticCall` and `new_`, of the fact of
    // a member of any number of parameters, and the same with the arguments of a last parameter of variable
    // arity one by one ([#varargs]). They are the same code at every arity, generated into the superclass of this
    // class (ExpressionArities, see TypedCodegen), and inherited as static methods: `Expressions.call` and an
    // `import static` of it find them.
    //
    // - `call` and `voidCall` throw IllegalArgumentException for a receiver of a primitive type;
    // - `new_` takes a `CtorRefN` only, the constructor of an instantiable class: the constructor of an abstract
    //   class is a `SuperCtorRefN`, which `new_` does not take (§3.1). The type arguments are always those of the
    //   fact: lowering renders the diamond itself where the target type is exactly the constructed one. It throws
    //   IllegalArgumentException for a class token with a wildcard type argument, which `new` cannot instantiate
    //   (JLS 15.9).
    // ------------------------------------------------------------------

    // ------------------------------------------------------------------
    // Calls of members of variable arity, with the arguments of the last parameter one by one:
    // `m(a, e1, e2)`. The last argument is a Varargs; an array the code has is passed as an
    // expression, as by the forms above.
    // ------------------------------------------------------------------

    /// The arguments of a parameter of variable arity, `m(a, e1, e2)`: the elements of the array that the last
    /// parameter of a member of variable arity takes. Pass it as the last argument of [#call], [#voidCall],
    /// [#staticCall], [#voidStaticCall] or [#new_] (and of `superCall` and `super_`); to pass an array the code
    /// has, pass it as an expression.
    ///
    /// No element is boxed implicitly, as no argument of any call is. Where another method of the name could
    /// apply to the arguments, javac would choose it (JLS 15.12.2: a method applicable without variable arity
    /// is chosen first), and the call is rendered with the array of the arguments written, `m(a, new E[] {e1,
    /// e2})`; an array of an element type that is not reifiable cannot be written, and the call is refused.
    ///
    /// @param type the array type of the parameter
    /// @param elements the elements, in order
    /// @param <A> the array type of the parameter
    /// @param <E> the element type
    /// @return the arguments
    /// @throws IllegalArgumentException if an element is of a primitive type where the element type is not
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <A, E> Varargs<A> varargs(ArrayToken<A, E> type, Expr<? extends E>... elements) {
        return varargs(type, List.of(elements));
    }

    /// The arguments of a parameter of variable arity as a list, which an element of a stream gives:
    /// [#varargs(ArrayToken, Expr[])] renders the same. An empty list is no element, `m()`.
    ///
    /// @param type the array type of the parameter
    /// @param elements the elements, in order
    /// @param <A> the array type of the parameter
    /// @param <E> the element type
    /// @return the arguments
    /// @throws IllegalArgumentException if an element is of a primitive type where the element type is not
    public static <A, E> Varargs<A> varargs(ArrayToken<A, E> type, List<? extends Expr<? extends E>> elements) {
        List<Expr<? extends E>> given = List.copyOf(elements);
        given.forEach(element -> Tokens.requireNoBoxing(element.type(), type.component(), "varargs"));
        return new Varargs<>(type, List.copyOf(given));
    }

    /// The arguments of a call that passes `rest` to the parameter of variable arity of `member`: those
    /// before it, then its elements.
    ///
    /// @throws IllegalArgumentException if `member` is not of variable arity, or is not `@SafeVarargs` and
    ///     the elements are of a type that is not reifiable: javac warns of the generic array it creates
    static List<Node.Operand> spread(Invocable member, Varargs<?> rest, List<Node.Operand> leading) {
        switch (member.arity()) {
            case Arity.Fixed _ ->
                throw new IllegalArgumentException("varargs gives the arguments of a parameter of variable arity,"
                        + " and the last parameter of " + member + " takes an array of " + rest.type.component()
                        + ": pass one, e.g. newArrayOf(...)");
            case Arity.Variable(var safety) -> {
                if (safety == Arity.Safety.UNCHECKED && !Tokens.isReifiable(rest.type.component())) {
                    throw new IllegalArgumentException(member + " is of variable arity and not @SafeVarargs, and the"
                            + " arguments of its last parameter are of " + rest.type.component()
                            + ", which is not reifiable: javac warns of the unchecked generic array creation of"
                            + " every such call (-Xlint:unchecked, JLS 15.12.4.2); pass an array the code has");
                }
            }
        }
        return Stream.concat(leading.stream(), rest.elements.stream().map(Expressions::operand))
                .toList();
    }

    static Node.Passing spreading(Varargs<?> rest) {
        return new Node.Passing.Spread(rest.type);
    }

    static Node callNode(Expr<?> target, Invocable method, Varargs<?> rest, List<Node.Operand> leading) {
        return new Node.Call(receiver(target, method), method, spread(method, rest, leading), spreading(rest));
    }

    static Node staticCallNode(Invocable method, Varargs<?> rest, List<Node.Operand> leading) {
        return new Node.StaticCall(method, spread(method, rest, leading), spreading(rest));
    }

    static Node newNode(Invocable ctor, Varargs<?> rest, List<Node.Operand> leading) {
        return instantiate(ctor, spread(ctor, rest, leading), spreading(rest));
    }

    // ------------------------------------------------------------------
    // Arrays: the ArrayToken witnesses that the expression is an array and
    // what its element type is — `ArrayToken.of(String_.TOKEN)` for
    // `String[]`, `PrimitiveToken.INT.array()` for `int[]`
    // ------------------------------------------------------------------

    /// Reads an array element, `array[index]`.
    ///
    /// The array is of exactly the type `type` names: an array of a subtype of its component is no
    /// array of the component here, as it is to Java (JLS 10.10), for javac types the element by the
    /// array it reads — a `String` of a `String[]`, whatever supertype the generator took it for. Bind
    /// the array to a variable of the wider array type first to read it as one.
    ///
    /// @param type the array type
    /// @param array the array
    /// @param index the index
    /// @param <A> the array type
    /// @param <T> the element type
    /// @return the element read
    public static <A, T> Expr<T> at(ArrayToken<A, T> type, Expr<A> array, Expr<Prim.Int> index) {
        return Expr.of(new Node.ArrayAt(array.node(), index.node()), type.component());
    }

    /// Reads `array.length`.
    ///
    /// @param type the array type
    /// @param array the array
    /// @param <A> the array type
    /// @return the length
    public static <A> Expr<Prim.Int> length(ArrayToken<A, ?> type, Expr<? extends A> array) {
        return Expr.of(new Node.ArrayLength(array.node()), PrimitiveToken.INT);
    }

    /// Creates an array, `new T[length]`, or one of arrays with the lengths of its outer dimensions,
    /// `new T[l1][l2]`; a dimension without a length is left out, `new T[l1][]`. The element type must be
    /// reifiable: `new List<String>[n]` and `new T[n]` are generic array creation, which Java rejects
    /// (JLS 15.10.1).
    ///
    /// @param type the array type
    /// @param length the length of the outer dimension
    /// @param inner the lengths of the dimensions inside it, in order
    /// @param <A> the array type
    /// @return the new array
    /// @throws IllegalArgumentException if the element type is not reifiable, or there are more lengths than
    ///     the array type has dimensions
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <A> Expr<A> newArray(ArrayToken<A, ?> type, Expr<Prim.Int> length, Expr<Prim.Int>... inner) {
        return newArray(type, length, List.of(inner));
    }

    /// Creates an array with the lengths of its inside dimensions as a list, which an element of a stream
    /// gives: [#newArray(ArrayToken, Expr, Expr[])] renders the same. An empty list is no inside dimension,
    /// `new T[length]`.
    ///
    /// @param type the array type
    /// @param length the length of the outer dimension
    /// @param inner the lengths of the dimensions inside it, in order
    /// @param <A> the array type
    /// @return the new array
    /// @throws IllegalArgumentException if the element type is not reifiable, or there are more lengths than
    ///     the array type has dimensions
    public static <A> Expr<A> newArray(ArrayToken<A, ?> type, Expr<Prim.Int> length, List<Expr<Prim.Int>> inner) {
        int given = 1 + inner.size();
        NonEmptyList<Node> lengths =
                new NonEmptyList<>(length.node(), inner.stream().map(Expr::node).toList());
        TypeToken<?> component = Tokens.peeled(type, given)
                .orElseThrow(() -> new IllegalArgumentException("newArray of " + type + " takes at most "
                        + Tokens.dimensions(type) + " length" + (Tokens.dimensions(type) == 1 ? "" : "s")
                        + ", one for each of its dimensions, and is given " + given));
        Tokens.requireReifiableComponent(component, "newArray");
        return Expr.of(new Node.NewArray(component, lengths), type);
    }

    /// Copies an array, `array.clone()` (JLS 10.7): a new array of the same type and the same elements, with no
    /// cast. Not named `clone`: every class has a method of that name, which hides a static import of it.
    ///
    /// @param type the array type
    /// @param array the array
    /// @param <A> the array type
    /// @return the copy
    public static <A> Expr<A> cloneArray(ArrayToken<A, ?> type, Expr<A> array) {
        return Expr.of(new Node.ArrayClone(array.node()), type);
    }

    /// Creates an array with its elements, `new E[] {e1, e2}`; where it initializes a variable or a
    /// field of exactly its type the elements stand alone, `E[] v = {e1, e2}`. The element type must be
    /// reifiable, as that of [#newArray] is, and an element of an array of arrays that is an array of
    /// that type is the `{...}` of its own.
    ///
    /// No element is boxed implicitly: a value of a primitive type is an element of an array of that
    /// primitive type alone.
    ///
    /// @param type the array type
    /// @param elements the elements
    /// @param <A> the array type
    /// @param <E> the element type
    /// @return the new array
    /// @throws IllegalArgumentException if the element type is not reifiable, or an element is of a
    ///     primitive type where the element type is not
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <A, E> Expr<A> newArrayOf(ArrayToken<A, E> type, Expr<? extends E>... elements) {
        return newArrayOf(type, List.of(elements));
    }

    /// Creates an array with the elements of a list, which an element of a stream gives:
    /// [#newArrayOf(ArrayToken, Expr[])] renders the same. An empty list is an empty array, `new E[] {}`.
    ///
    /// @param type the array type
    /// @param elements the elements
    /// @param <A> the array type
    /// @param <E> the element type
    /// @return the new array
    /// @throws IllegalArgumentException if the element type is not reifiable, or an element is of a
    ///     primitive type where the element type is not
    public static <A, E> Expr<A> newArrayOf(ArrayToken<A, E> type, List<? extends Expr<? extends E>> elements) {
        Tokens.requireReifiableComponent(type.component(), "newArrayOf");
        List<Expr<? extends E>> given = List.copyOf(elements);
        given.forEach(element -> Tokens.requireNoBoxing(element.type(), type.component(), "newArrayOf"));
        return Expr.of(new Node.NewArrayOf(type, given.stream().map(Expr::node).toList()), type);
    }

    // ------------------------------------------------------------------
    // Conditional
    // ------------------------------------------------------------------

    /// `condition ? whenTrue : whenFalse`, of exactly the type `T`. The
    /// result type must be given explicitly: the two branches may have
    /// different, only jointly related, static types.
    ///
    /// Java would type the conditional by its branches (JLS 15.25) — `true
    /// ? 1 : 2.0` is a `double`, `c ? 1 : (Integer) null` unboxes and throws
    /// — so a branch whose type is not `T` is cast to it, `c ? (T) a : b`,
    /// and primitive and reference branches never mix.
    ///
    /// @param condition the condition
    /// @param whenTrue the value if `condition` holds
    /// @param whenFalse the value otherwise
    /// @param type the type of the conditional
    /// @param <T> the type of the conditional
    /// @return the conditional
    /// @throws IllegalArgumentException if a branch is primitive and `T` is not
    public static <T> Expr<T> cond(
            Expr<Prim.Bool> condition, Expr<? extends T> whenTrue, Expr<? extends T> whenFalse, TypeToken<T> type) {
        return Expr.of(
                new Node.Cond(
                        condition.node(), result(whenTrue, type, Choice.COND), result(whenFalse, type, Choice.COND)),
                type);
    }

    // ------------------------------------------------------------------
    // `int` operators
    // ------------------------------------------------------------------

    public static Expr<Prim.Int> addInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.ADD_INT, l, r, PrimitiveToken.INT);
    }

    public static Expr<Prim.Int> subInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.SUB_INT, l, r, PrimitiveToken.INT);
    }

    public static Expr<Prim.Int> mulInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.MUL_INT, l, r, PrimitiveToken.INT);
    }

    /// `l / r`.
    ///
    /// @param l the dividend
    /// @param r the divisor
    /// @return the quotient
    /// @throws IllegalArgumentException if `r` is a constant expression of the value zero (JLS 15.29)
    public static Expr<Prim.Int> divInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.DIV_INT, l, divisor(r, "divInt"), PrimitiveToken.INT);
    }

    /// `l % r`.
    ///
    /// @param l the dividend
    /// @param r the divisor
    /// @return the remainder
    /// @throws IllegalArgumentException if `r` is a constant expression of the value zero (JLS 15.29)
    public static Expr<Prim.Int> modInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.MOD_INT, l, divisor(r, "modInt"), PrimitiveToken.INT);
    }

    /// A divisor that is no constant zero: the division would throw `ArithmeticException` wherever it
    /// is evaluated, and javac warns of it (`[divzero]`).
    private static Expr<Prim.Int> divisor(Expr<Prim.Int> divisor, String combinator) {
        if (Constants.isZero(divisor.node())) {
            throw new IllegalArgumentException(combinator + " by a constant expression of the value zero: the"
                    + " division throws ArithmeticException wherever it is evaluated, and javac warns of it"
                    + " (division by zero, -Xlint:divzero)");
        }
        return divisor;
    }

    public static Expr<Prim.Bool> eqInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.EQ_INT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> neqInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.NEQ_INT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> ltInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.LT_INT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> leInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.LE_INT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> gtInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.GT_INT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> geInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.GE_INT, l, r, PrimitiveToken.BOOLEAN);
    }

    // ------------------------------------------------------------------
    // `long` / `double` arithmetic (representative subset)
    // ------------------------------------------------------------------

    public static Expr<Prim.Long> addLong(Expr<Prim.Long> l, Expr<Prim.Long> r) {
        return binary(Node.Operator.ADD_LONG, l, r, PrimitiveToken.LONG);
    }

    public static Expr<Prim.Long> mulLong(Expr<Prim.Long> l, Expr<Prim.Long> r) {
        return binary(Node.Operator.MUL_LONG, l, r, PrimitiveToken.LONG);
    }

    public static Expr<Prim.Double> addDouble(Expr<Prim.Double> l, Expr<Prim.Double> r) {
        return binary(Node.Operator.ADD_DOUBLE, l, r, PrimitiveToken.DOUBLE);
    }

    public static Expr<Prim.Double> mulDouble(Expr<Prim.Double> l, Expr<Prim.Double> r) {
        return binary(Node.Operator.MUL_DOUBLE, l, r, PrimitiveToken.DOUBLE);
    }

    // ------------------------------------------------------------------
    // Boolean logic
    // ------------------------------------------------------------------

    public static Expr<Prim.Bool> and(Expr<Prim.Bool> l, Expr<Prim.Bool> r) {
        return binary(Node.Operator.AND, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> or(Expr<Prim.Bool> l, Expr<Prim.Bool> r) {
        return binary(Node.Operator.OR, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> not(Expr<Prim.Bool> operand) {
        return Expr.of(new Node.Not(operand.node()), PrimitiveToken.BOOLEAN);
    }

    /// Reference identity, `l == r`: never `equals`, and never numeric
    /// equality — compare primitives with [#eqInt(Expr,Expr)] and friends.
    ///
    /// The static type of `r` is a subtype of that of `l`, so the operands
    /// are comparable (JLS 15.21.3).
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @param <T> the static type of `l`
    /// @return the comparison
    /// @throws IllegalArgumentException if an operand is of a primitive type
    public static <T> Expr<Prim.Bool> eqRef(Expr<T> l, Expr<? extends T> r) {
        return binary(Node.Operator.EQ_REF, reference(l, "eqRef"), reference(r, "eqRef"), PrimitiveToken.BOOLEAN);
    }

    /// Reference non-identity, `l != r`; see [#eqRef(Expr, Expr)].
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @param <T> the static type of `l`
    /// @return the comparison
    /// @throws IllegalArgumentException if an operand is of a primitive type
    public static <T> Expr<Prim.Bool> neqRef(Expr<T> l, Expr<? extends T> r) {
        return binary(Node.Operator.NEQ_REF, reference(l, "neqRef"), reference(r, "neqRef"), PrimitiveToken.BOOLEAN);
    }

    // ------------------------------------------------------------------
    // Strings
    // ------------------------------------------------------------------

    /// Explicit string concatenation, `l + r` as `String`. Never an overload
    /// of `add`: mixing arithmetic and concatenation under one name is a
    /// classic Java footgun the typed layer refuses to reproduce.
    public static Expr<String> concat(Expr<String> l, Expr<String> r) {
        return binary(Node.Operator.CONCAT, l, r, String_.TOKEN);
    }

    // ------------------------------------------------------------------
    // Explicit numeric conversions (no promotion, §6.1/§10)
    // ------------------------------------------------------------------

    /// Widening, `(long) operand`; it never loses information.
    public static Expr<Prim.Long> widenIntToLong(Expr<Prim.Int> operand) {
        return Expr.of(new Node.Convert(Node.Conversion.INT_TO_LONG, operand.node()), PrimitiveToken.LONG);
    }

    /// Widening, `(double) operand`; it never loses information.
    public static Expr<Prim.Double> widenIntToDouble(Expr<Prim.Int> operand) {
        return Expr.of(new Node.Convert(Node.Conversion.INT_TO_DOUBLE, operand.node()), PrimitiveToken.DOUBLE);
    }

    /// Widening, `(double) operand`; it may round a large value (JLS 5.1.2).
    public static Expr<Prim.Double> widenLongToDouble(Expr<Prim.Long> operand) {
        return Expr.of(new Node.Convert(Node.Conversion.LONG_TO_DOUBLE, operand.node()), PrimitiveToken.DOUBLE);
    }

    /// Checked narrowing, `Math.toIntExact(operand)`: throws
    /// `ArithmeticException` at run time for a value out of the `int` range.
    public static Expr<Prim.Int> narrowCheckedLongToInt(Expr<Prim.Long> operand) {
        return staticCall(Math_.toIntExact_long, operand);
    }

    /// Truncating narrowing, `(int) operand`: keeps the low 32 bits of a
    /// value out of the `int` range, silently (JLS 5.1.3).
    public static Expr<Prim.Int> narrowTruncatingLongToInt(Expr<Prim.Long> operand) {
        return Expr.of(new Node.Convert(Node.Conversion.LONG_TO_INT, operand.node()), PrimitiveToken.INT);
    }

    /// Truncating narrowing, `(int) operand`: rounds toward zero and clamps
    /// a value out of the `int` range, silently (JLS 5.1.3).
    public static Expr<Prim.Int> narrowTruncatingDoubleToInt(Expr<Prim.Double> operand) {
        return Expr.of(new Node.Convert(Node.Conversion.DOUBLE_TO_INT, operand.node()), PrimitiveToken.INT);
    }

    // ------------------------------------------------------------------
    // Checked reference cast (§6.1)
    // ------------------------------------------------------------------

    /// A checked cast, `(T) operand`. Only representable when `T` and the
    /// operand's static type are in a subtype relation one way or the other —
    /// enforced by `? super T`, so an unrelated cast does not compile — and
    /// when the cast is checked (JLS 5.1.6.2), so the run-time check covers
    /// all of `T`: either `T` is reifiable, or the operand's type is a
    /// parameterized supertype of `T` that determines its type arguments.
    /// `(List<?>) o` and `(ArrayList<String>) listOfStrings` are accepted,
    /// `(List<String>) o` and `(ArrayList<Integer>) listOfStrings` are not.
    ///
    /// @param type the target type
    /// @param operand the operand
    /// @param <T> the target type
    /// @return the cast, or `operand` itself if its type is already `T`
    /// @throws IllegalArgumentException if the cast would be unchecked
    public static <T> Expr<T> castChecked(RefToken<T> type, Expr<? super T> operand) {
        Tokens.requireCheckedCast(operand.type(), type, "castChecked");
        if (Tokens.sameType(operand.type(), type)) {
            // `(T) operand` would be a redundant cast (javac -Xlint:cast); lowering pins the operand
            // where the type javac gives it matters.
            return Expr.of(operand.node(), type);
        }
        return Expr.of(new Node.Cast(type, operand(operand)), type);
    }

    // ------------------------------------------------------------------
    // Enum constants and `switch`
    // ------------------------------------------------------------------

    /// An enum constant, `Day.MON`.
    ///
    /// @param constant the constant
    /// @param <E> the enum
    /// @return the constant
    public static <E> Expr<E> enumConstant(EnumConstant<E> constant) {
        return Expr.of(new Node.EnumConst(constant), constant.owner());
    }

    /// A `switch` expression over an enum, of exactly the type `R`:
    ///
    /// ```java
    /// switch_(Day_.TOKEN, day, String_.TOKEN, c -> c
    ///         .case_(Day_.MON, literal("mon"))
    ///         .case_(List.of(Day_.TUE, Day_.WED), y -> y.yield_(literal("other"))));
    /// ```
    ///
    /// The selector is evaluated first, then the one case of its value: a
    /// value, or a block that yields one ([YieldBody]). A selector that is
    /// `null` at run time is a `NullPointerException`, as in Java.
    ///
    /// The cases are checked as they are added and when they are complete
    /// ([EnumSwitchCases]): a constant has one case, and without a `default_`
    /// every constant of the enum has one — the constants are those of the
    /// facts of `enumType`, which hold of the target classpath or lowering
    /// fails.
    ///
    /// As with [#cond(Expr, Expr, Expr, TypeToken)], the result type is
    /// given explicitly and a result whose type is not `R` is cast to it,
    /// so javac types the `switch` by `R` and never by its results (JLS
    /// 15.28.1); primitive and reference results never mix.
    ///
    /// **Where it is used.** The block of a case is nested in the block the
    /// `switch` is built in, and its statements are checked there as they
    /// are built: the variables in scope, the exceptions caught or
    /// declared. A `switch` that has such a block is therefore used in the
    /// block it is built in, or in a block nested in that one and on the
    /// same side of every lambda boundary. One built outside of any body,
    /// for a field initializer, uses no variable and throws no checked
    /// exception. Where this is stricter than Java, which has no expression
    /// apart from where it stands, is listed with the cases
    /// ([EnumSwitchCases]).
    ///
    /// @param enumType the enum of the selector, the witness of its constants
    /// @param selector the selector
    /// @param type the type of the `switch`
    /// @param cases adds the cases, in the order of the code
    /// @param <E> the enum
    /// @param <R> the type of the `switch`
    /// @return the `switch` expression
    /// @throws IllegalStateException if the cases are not exhaustive, or none has a result
    public static <E, R> Expr<R> switch_(
            EnumToken<E> enumType,
            Expr<? extends E> selector,
            TypeToken<R> type,
            Consumer<? super EnumSwitchCases<E, R>> cases) {
        EnumSwitchCases<E, R> collected = new EnumSwitchCases<>(enumType, type, Scopes.enclosing());
        cases.accept(collected);
        return Expr.of(collected.close(operand(selector)), type);
    }

    // ------------------------------------------------------------------
    // Lambdas (§6.4): typed by the `sam` fact of a functional interface,
    // `lambda` with an expression body and `lambdaBlock` with a block.
    //
    // The parameters and the result are those of the fact, so a body of
    // another arity or of other types does not compile. The lambda is an
    // expression of the functional interface, usable wherever one is: an
    // argument, the initializer of a variable or a field, a returned value,
    // a receiver.
    //
    // The body is a scope of its own, nested in the block the lambda is
    // built in — the innermost one being built, none for a field
    // initializer — and checked where it is built:
    //
    // - it captures the variables of that block that are effectively final:
    //   a `Var`, `this`. A `MutVar` is assignable and is rejected, read or
    //   assigned (JLS 15.27.2); copy it into a `let` first. A `LoopCtl` of a
    //   loop around the lambda is rejected too;
    // - a checked exception thrown in it is caught in it or declared by the
    //   method of the functional interface (JLS 11.2.3): the `catch_`
    //   clauses and the `throws` clause around the lambda cover nothing of
    //   it.
    //
    // Stricter than javac, each of these rejects code javac accepts:
    //
    // - a `MutVar` that is never assigned is effectively final to javac and
    //   captured; here it is rejected, read or not — declare it by `let`;
    // - a lambda is used in the block it is built in, or in one nested in
    //   it: its body was checked there, and elsewhere the variables it
    //   captures may be out of scope. Java has no lambda apart from where it
    //   stands, so the same lambda built where it is used is accepted;
    // - a lambda built in the condition or the update of a `for_` is a
    //   block of the code around the loop: it does not see the loop
    //   variable, which javac would only refuse it for being assigned.
    //
    // Of any arity 0..12.
    // ------------------------------------------------------------------

    /// The parameters and the body of an expression lambda.
    record LambdaValue(List<Var<?>> params, Node value) {}

    /// The value of an expression lambda of `sam`, which has a result: no primitive one of a reference
    /// result, which Java would box.
    static Node returned(Expr<?> value, Invocable sam) {
        sam.resultType().ifPresent(result -> Tokens.requireNoBoxing(value.type(), result, "the lambda of " + sam));
        return value.node();
    }

    static <T> Var<T> lambdaParameter(TypeToken<T> type, Block<?, ?> scope) {
        return new Var<>(type, Var.Role.Fixed.LAMBDA_PARAMETER, scope);
    }

    /// A method reference bound to a receiver, `receiver::method`, of the functional interface `samType`
    /// whose method `sam` has the parameters and result of `method`.
    ///
    /// @param receiver the bound receiver
    /// @param method the fact of the referenced method, an instance method of the receiver's type
    /// @param samType the functional interface the reference implements
    /// @param sam the single abstract method of `samType`
    /// @param <O> the receiver type
    /// @param <F> the functional interface
    /// @return the method reference
    /// @throws IllegalArgumentException if `method` is not an instance method of the receiver's type, or its
    ///     parameters or result are not those of `sam`
    public static <O, F> Expr<F> methodRef(
            Expr<? extends O> receiver, Invocable method, InterfaceToken<F> samType, Invocable sam) {
        Node.Operand target = receiver(receiver, method);
        MethodReferences.bound(target.type(), method, samType, sam);
        return Expr.of(new Node.MethodRef(Optional.of(target), method.owner(), method, sam), samType);
    }

    /// A method reference qualified by a type, `Type::method`, of the functional interface `samType`
    /// whose method `sam` has the parameters and result of `method`: the unbound instance reference
    /// `Type::instanceMethod`, whose first parameter the receiver is, or the static `Type::staticMethod`.
    ///
    /// @param type the qualifying type, the owner of the method or a subtype of it
    /// @param method the fact of the referenced method
    /// @param samType the functional interface the reference implements
    /// @param sam the single abstract method of `samType`
    /// @param <F> the functional interface
    /// @return the method reference
    /// @throws IllegalArgumentException if `method` is a constructor, `type` is not the owner or a subtype
    ///     of it, or the parameters or result are not those of `sam`
    public static <F> Expr<F> methodRef(
            DeclaredToken<?> type, Invocable method, InterfaceToken<F> samType, Invocable sam) {
        MethodReferences.onType(type, method, samType, sam);
        return Expr.of(new Node.MethodRef(Optional.empty(), type, method, sam), samType);
    }

    /// Builds an expression lambda in the innermost block being built:
    /// `body` runs with the lambda's own scope innermost, so a builder of
    /// the block around it cannot be used inside, and what it gives is
    /// checked against that scope right here.
    static <F> Expr<F> expressionLambda(
            InterfaceToken<F> type, Invocable sam, Function<? super Block<?, ?>, LambdaValue> body) {
        Body<Object> scope = Body.lambdaBody(Scopes.enclosing(), sam.traits().throwsTypes());
        LambdaValue made = Scopes.within(scope, () -> body.apply(scope));
        ScopeCheck.lambdaValue(made.value(), scope);
        Exceptions.lambdaValue(made.value(), scope);
        return Expr.of(new Node.Lambda(sam, made.params(), new Node.LambdaBody.Value(scope, made.value())), type);
    }

    static <F, R, B extends Block<R, B>> Expr<F> blockLambda(
            InterfaceToken<F> type,
            Invocable sam,
            B block,
            List<Var<?>> params,
            Function<? super B, Terminated<R>> body) {
        Block.build(block, body);
        return Expr.of(new Node.Lambda(sam, params, new Node.LambdaBody.Block(block)), type);
    }

    // ------------------------------------------------------------------
    // Assignments
    // ------------------------------------------------------------------

    /// `var = value;`
    public static <T> Assignment assign(MutVar<T> var, Expr<? extends T> value) {
        Tokens.requireNoBoxing(value.type(), var.type(), "assign");
        return new Assignment(new Node.Assign(new Node.Target.Local(var), value.node()));
    }

    /// `target.field = value;`
    ///
    /// @throws IllegalArgumentException if `target` is of a primitive type
    public static <O, T> Assignment assignField(
            Expr<? extends O> target, MutableFieldRef<O, T> field, Expr<? extends T> value) {
        Tokens.requireNoBoxing(value.type(), field.type(), "assignField");
        return new Assignment(new Node.Assign(new Node.Target.Field(receiver(target, field), field), value.node()));
    }

    /// `Owner.field = value;`
    public static <T> Assignment assignStaticField(MutableStaticFieldRef<T> field, Expr<? extends T> value) {
        Tokens.requireNoBoxing(value.type(), field.type(), "assignStaticField");
        return new Assignment(new Node.Assign(new Node.Target.StaticField(field), value.node()));
    }

    /// `array[index] = value;`
    ///
    /// The array is of exactly the type `type` names, as that of [#at(ArrayToken, Expr, Expr)] is:
    /// javac takes a store into a `String[]` for one of a `String`, whatever supertype of it the
    /// generator took the array for (JLS 15.26.1). An array a variable of a wider array type holds is
    /// one of that type, and storing a value its array does not take through it throws
    /// `ArrayStoreException` at run time, as in Java.
    ///
    /// @param type the array type
    /// @param array the array
    /// @param index the index
    /// @param value the stored value
    /// @param <A> the array type
    /// @param <T> the element type
    /// @return the assignment
    public static <A, T> Assignment assignAt(
            ArrayToken<A, T> type, Expr<A> array, Expr<Prim.Int> index, Expr<? extends T> value) {
        Tokens.requireNoBoxing(value.type(), type.component(), "assignAt");
        return new Assignment(new Node.Assign(new Node.Target.Element(array.node(), index.node()), value.node()));
    }

    // ------------------------------------------------------------------

    private static <A, B, R> Expr<R> binary(Node.Operator op, Expr<A> l, Expr<B> r, TypeToken<R> type) {
        return Expr.of(new Node.Binary(op, l.node(), r.node()), type);
    }

    /// A primitive has no members: `5.toString()` is not Java. A marker is a
    /// subtype of `Object`, so javac lets an `Expr<Prim.Int>` through where
    /// the owner is `Object`; this is where it is caught (§6.1, §9).
    static Node.Operand receiver(Expr<?> target, Object member) {
        if (target.type() instanceof PrimitiveToken<?, ?, ?> primitive) {
            throw new IllegalArgumentException("cannot access " + member + " on an expression of the primitive type "
                    + primitive + ": a primitive has no members; box it first, e.g. box(PrimitiveToken."
                    + primitive.typeRef().sourceName().toUpperCase(Locale.ROOT) + ", expr)");
        }
        return operand(target);
    }

    private static <T> Expr<T> reference(Expr<T> operand, String combinator) {
        if (operand.type() instanceof PrimitiveToken<?, ?, ?> primitive) {
            throw new IllegalArgumentException(combinator + " compares references, but an operand is of the primitive"
                    + " type " + primitive + "; compare primitives with the per-type comparisons, e.g. eqInt");
        }
        return operand;
    }

    static Node.Operand operand(Expr<?> expr) {
        return new Node.Operand(expr.node(), expr.type());
    }

    /// The arguments of a call with their static types, from which lowering
    /// pins the overload the fact names (§6.1).
    static List<Node.Operand> arguments(Expr<?>... args) {
        return Stream.of(args).map(Expressions::operand).toList();
    }

    static Node instantiate(Invocable ctor, List<Node.Operand> args, Node.Passing passing) {
        if (ctor.owner().typeRef() instanceof ParameterizedTypeRef parameterized
                && !parameterized.args().stream().allMatch(ExactTypeArg.class::isInstance)) {
            throw new IllegalArgumentException("cannot instantiate " + ctor.owner()
                    + ": `new` needs exact type arguments, not wildcards (JLS 15.9); construct the class through"
                    + " the facts of an exact parameterization");
        }
        return new Node.New(ctor, args, passing);
    }

    /// The expressions that choose among results and are of an explicit type,
    /// and what a result of theirs is called.
    enum Choice {
        /// [#cond(Expr, Expr, Expr, TypeToken)].
        COND("cond", "branch", "branches"),
        /// [#switch_(EnumToken, Expr, TypeToken, Consumer)].
        SWITCH("switch_", "result", "results");

        private final String form;
        private final String one;
        private final String many;

        Choice(String form, String one, String many) {
            this.form = form;
            this.one = one;
            this.many = many;
        }
    }

    /// A result of a `cond` or of a `switch_` of type `type`, cast to it
    /// where its own type differs.
    ///
    /// @param of the expression the result is one of
    /// @throws IllegalArgumentException if the result is primitive and `type` is not
    static Node result(Expr<?> result, TypeToken<?> type, Choice of) {
        return switch (type) {
            // A primitive type has no subtype ([Prim]): a result of it is of exactly it.
            case PrimitiveToken<?, ?, ?> _ -> result.node();
            case RefToken<?> reference -> {
                if (result.type() instanceof PrimitiveToken<?, ?, ?>) {
                    throw new IllegalArgumentException(of.form + " of type " + type + " has a " + of.one
                            + " of type " + result.type() + ": " + of.form + " does not mix primitive and reference "
                            + of.many + ", which Java would box, unbox or promote implicitly (JLS 15.25, 15.28.1);"
                            + " box or unbox the " + of.one + " explicitly");
                }
                yield Tokens.sameType(result.type(), reference)
                        ? result.node()
                        : new Node.Cast(reference, operand(result));
            }
        };
    }
}
