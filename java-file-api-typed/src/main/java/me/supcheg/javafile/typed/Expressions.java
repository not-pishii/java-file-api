package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.code.IncDecOp;
import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
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
/// - a primitive receiver — `literal(1).call(Object_.toString)` — when the
///   call is built;
/// - the initializer of a variable or a field, an assigned value, the value
///   of an expression lambda, when it is built;
/// - an argument — `list.call(remove_Object, literal(1))` — and a returned
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
/// **Arities.** `call` and `voidCall` are methods of [Expr] of any number of arguments, 0 to 12: one overload for
/// each `MethodRefN` of the facts. `staticCall`, `voidStaticCall`, `new_`, `lambda` and `lambdaBlock` take the fact
/// of a member likewise. They are generated (`TypedCodegen`) into the superclasses of [Expr] and of this class, and
/// inherited, so that an `import static` of them finds them.
///
/// **Variable arity.** The last parameter of a member of variable arity is given an array the code has, as an
/// argument of its array type, or its elements one by one, `varargs(type, e1, e2)` as the last argument of a call.
/// The elements of a list are given by the same form, `varargs(type, elements)`.
///
/// **Arrays.** `newArrayOf(type, e1, e2)` creates an array with its elements, `{e1, e2}` where a declaration of
/// exactly its type tells the type; `newArray(type, length, ...)` one by its lengths, `cloneArray` copies an array
/// without a cast, `array.arrayAccess(type, index)`, `array.length(type)` and `assignAt` read, measure and store
/// into one, and `forEach` of a block loops over its elements.
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

    /// Reads a static field, `Owner.field`.
    ///
    /// @param field the field
    /// @param <T> the field type
    /// @return the field read
    public static <T> Expr<T> staticField(StaticFieldRef<T> field) {
        return Expr.of(new Node.StaticFieldGet(field), field.type());
    }

    // ------------------------------------------------------------------
    // Calls and instance creation: `staticCall`, `voidStaticCall` and `new_`, of the fact of a member of any
    // number of parameters, and the same with the arguments of a last parameter of variable arity one by one
    // ([#varargs]). They are the same code at every arity, generated into the superclass of this class
    // (ExpressionArities, see TypedCodegen), and inherited as static methods. The instance `call` and `voidCall`
    // continue a [Expr], whose generated superclass (ExprArities) holds them.
    //
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
    /// parameter of a member of variable arity takes. Pass it as the last argument of [Expr#call],
    /// [Expr#voidCall], [#staticCall], [#voidStaticCall] or [#new_] (and of `superCall` and `super_`); to pass
    /// an array the code has, pass it as an expression.
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

    /// The unary numeric promotion of `byte`, `short` and `char`: each
    /// arithmetic or bitwise complement operator on one is of type `int`
    /// (JLS 5.6).
    private static <P> Expr<Prim.Int> promoted(UnaryOp op, Expr<P> operand) {
        return Expr.of(new Node.Unary(op, operand.node()), PrimitiveToken.INT);
    }

    /// `-operand` of an `int`.
    ///
    /// @param operand the operand
    /// @return the negation
    public static Expr<Prim.Int> negInt(Expr<Prim.Int> operand) {
        return Expr.of(new Node.Unary(UnaryOp.NEG, operand.node()), PrimitiveToken.INT);
    }

    /// `-operand` of a `long`.
    ///
    /// @param operand the operand
    /// @return the negation
    public static Expr<Prim.Long> negLong(Expr<Prim.Long> operand) {
        return Expr.of(new Node.Unary(UnaryOp.NEG, operand.node()), PrimitiveToken.LONG);
    }

    /// `-operand` of a `float`.
    ///
    /// @param operand the operand
    /// @return the negation
    public static Expr<Prim.Float> negFloat(Expr<Prim.Float> operand) {
        return Expr.of(new Node.Unary(UnaryOp.NEG, operand.node()), PrimitiveToken.FLOAT);
    }

    /// `-operand` of a `double`.
    ///
    /// @param operand the operand
    /// @return the negation
    public static Expr<Prim.Double> negDouble(Expr<Prim.Double> operand) {
        return Expr.of(new Node.Unary(UnaryOp.NEG, operand.node()), PrimitiveToken.DOUBLE);
    }

    /// `-operand` of a `byte`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the negation, as an `int`
    public static Expr<Prim.Int> negByte(Expr<Prim.Byte> operand) {
        return promoted(UnaryOp.NEG, operand);
    }

    /// `-operand` of a `short`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the negation, as an `int`
    public static Expr<Prim.Int> negShort(Expr<Prim.Short> operand) {
        return promoted(UnaryOp.NEG, operand);
    }

    /// `-operand` of a `char`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the negation, as an `int`
    public static Expr<Prim.Int> negChar(Expr<Prim.Char> operand) {
        return promoted(UnaryOp.NEG, operand);
    }

    /// `+operand` of an `int`.
    ///
    /// @param operand the operand
    /// @return the operand, as an `int`
    public static Expr<Prim.Int> unaryPlusInt(Expr<Prim.Int> operand) {
        return Expr.of(new Node.Unary(UnaryOp.UNARY_PLUS, operand.node()), PrimitiveToken.INT);
    }

    /// `+operand` of a `long`.
    ///
    /// @param operand the operand
    /// @return the operand, as a `long`
    public static Expr<Prim.Long> unaryPlusLong(Expr<Prim.Long> operand) {
        return Expr.of(new Node.Unary(UnaryOp.UNARY_PLUS, operand.node()), PrimitiveToken.LONG);
    }

    /// `+operand` of a `float`.
    ///
    /// @param operand the operand
    /// @return the operand, as a `float`
    public static Expr<Prim.Float> unaryPlusFloat(Expr<Prim.Float> operand) {
        return Expr.of(new Node.Unary(UnaryOp.UNARY_PLUS, operand.node()), PrimitiveToken.FLOAT);
    }

    /// `+operand` of a `double`.
    ///
    /// @param operand the operand
    /// @return the operand, as a `double`
    public static Expr<Prim.Double> unaryPlusDouble(Expr<Prim.Double> operand) {
        return Expr.of(new Node.Unary(UnaryOp.UNARY_PLUS, operand.node()), PrimitiveToken.DOUBLE);
    }

    /// `+operand` of a `byte`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the operand, as an `int`
    public static Expr<Prim.Int> unaryPlusByte(Expr<Prim.Byte> operand) {
        return promoted(UnaryOp.UNARY_PLUS, operand);
    }

    /// `+operand` of a `short`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the operand, as an `int`
    public static Expr<Prim.Int> unaryPlusShort(Expr<Prim.Short> operand) {
        return promoted(UnaryOp.UNARY_PLUS, operand);
    }

    /// `+operand` of a `char`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the operand, as an `int`
    public static Expr<Prim.Int> unaryPlusChar(Expr<Prim.Char> operand) {
        return promoted(UnaryOp.UNARY_PLUS, operand);
    }

    /// `~operand` of an `int`.
    ///
    /// @param operand the operand
    /// @return the complement
    public static Expr<Prim.Int> bitNotInt(Expr<Prim.Int> operand) {
        return Expr.of(new Node.Unary(UnaryOp.BIT_NOT, operand.node()), PrimitiveToken.INT);
    }

    /// `~operand` of a `long`.
    ///
    /// @param operand the operand
    /// @return the complement
    public static Expr<Prim.Long> bitNotLong(Expr<Prim.Long> operand) {
        return Expr.of(new Node.Unary(UnaryOp.BIT_NOT, operand.node()), PrimitiveToken.LONG);
    }

    /// `~operand` of a `byte`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the complement, as an `int`
    public static Expr<Prim.Int> bitNotByte(Expr<Prim.Byte> operand) {
        return promoted(UnaryOp.BIT_NOT, operand);
    }

    /// `~operand` of a `short`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the complement, as an `int`
    public static Expr<Prim.Int> bitNotShort(Expr<Prim.Short> operand) {
        return promoted(UnaryOp.BIT_NOT, operand);
    }

    /// `~operand` of a `char`, of type `int` (JLS 5.6).
    ///
    /// @param operand the operand
    /// @return the complement, as an `int`
    public static Expr<Prim.Int> bitNotChar(Expr<Prim.Char> operand) {
        return promoted(UnaryOp.BIT_NOT, operand);
    }

    /// `l & r` of two `int`s.
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @return the conjunction
    public static Expr<Prim.Int> bitAndInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.BIT_AND_INT, l, r, PrimitiveToken.INT);
    }

    /// `l | r` of two `int`s.
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @return the disjunction
    public static Expr<Prim.Int> bitOrInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.BIT_OR_INT, l, r, PrimitiveToken.INT);
    }

    /// `l ^ r` of two `int`s.
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @return the exclusive disjunction
    public static Expr<Prim.Int> bitXorInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(Node.Operator.BIT_XOR_INT, l, r, PrimitiveToken.INT);
    }

    /// `l & r` of two `long`s.
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @return the conjunction
    public static Expr<Prim.Long> bitAndLong(Expr<Prim.Long> l, Expr<Prim.Long> r) {
        return binary(Node.Operator.BIT_AND_LONG, l, r, PrimitiveToken.LONG);
    }

    /// `l | r` of two `long`s.
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @return the disjunction
    public static Expr<Prim.Long> bitOrLong(Expr<Prim.Long> l, Expr<Prim.Long> r) {
        return binary(Node.Operator.BIT_OR_LONG, l, r, PrimitiveToken.LONG);
    }

    /// `l ^ r` of two `long`s.
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @return the exclusive disjunction
    public static Expr<Prim.Long> bitXorLong(Expr<Prim.Long> l, Expr<Prim.Long> r) {
        return binary(Node.Operator.BIT_XOR_LONG, l, r, PrimitiveToken.LONG);
    }

    /// `l << r`, of type `int`. The distance is `int` or `long`, and Java
    /// masks it with `0x1f` (JLS 15.19): `1 << 32` is `1`.
    ///
    /// @param l the shifted value
    /// @param r the distance
    /// @return the shift
    public static Expr<Prim.Int> shlInt(Expr<Prim.Int> l, Expr<? extends Prim.Integral> r) {
        return binary(Node.Operator.SHL_INT, l, r, PrimitiveToken.INT);
    }

    /// `l >> r`, of type `int`, with the sign of `l` kept. The distance is
    /// `int` or `long`, and Java masks it with `0x1f` (JLS 15.19).
    ///
    /// @param l the shifted value
    /// @param r the distance
    /// @return the shift
    public static Expr<Prim.Int> shrInt(Expr<Prim.Int> l, Expr<? extends Prim.Integral> r) {
        return binary(Node.Operator.SHR_INT, l, r, PrimitiveToken.INT);
    }

    /// `l >>> r`, of type `int`, with the vacated high bits zeroed. The
    /// distance is `int` or `long`, and Java masks it with `0x1f` (JLS 15.19).
    ///
    /// @param l the shifted value
    /// @param r the distance
    /// @return the shift
    public static Expr<Prim.Int> ushrInt(Expr<Prim.Int> l, Expr<? extends Prim.Integral> r) {
        return binary(Node.Operator.USHR_INT, l, r, PrimitiveToken.INT);
    }

    /// `l << r`, of type `long`. The distance is `int` or `long`, and Java
    /// masks it with `0x3f` (JLS 15.19).
    ///
    /// @param l the shifted value
    /// @param r the distance
    /// @return the shift
    public static Expr<Prim.Long> shlLong(Expr<Prim.Long> l, Expr<? extends Prim.Integral> r) {
        return binary(Node.Operator.SHL_LONG, l, r, PrimitiveToken.LONG);
    }

    /// `l >> r`, of type `long`, with the sign of `l` kept. The distance is
    /// `int` or `long`, and Java masks it with `0x3f` (JLS 15.19).
    ///
    /// @param l the shifted value
    /// @param r the distance
    /// @return the shift
    public static Expr<Prim.Long> shrLong(Expr<Prim.Long> l, Expr<? extends Prim.Integral> r) {
        return binary(Node.Operator.SHR_LONG, l, r, PrimitiveToken.LONG);
    }

    /// `l >>> r`, of type `long`, with the vacated high bits zeroed. The
    /// distance is `int` or `long`, and Java masks it with `0x3f` (JLS 15.19).
    ///
    /// @param l the shifted value
    /// @param r the distance
    /// @return the shift
    public static Expr<Prim.Long> ushrLong(Expr<Prim.Long> l, Expr<? extends Prim.Integral> r) {
        return binary(Node.Operator.USHR_LONG, l, r, PrimitiveToken.LONG);
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
    // Class literals
    // ------------------------------------------------------------------

    /// A class literal, `type.class`, of type `Class<T>`.
    ///
    /// @param type the type
    /// @param <T> the type
    /// @return the class literal
    /// @throws IllegalStateException if `type` is written off the scope of its type parameters
    public static <T> Expr<Class<T>> classLiteral(RefToken<T> type) {
        return Expr.of(new Node.ClassLiteral(type), new Class_<>(type).token);
    }

    /// A class literal of a primitive type, `int.class`, of type
    /// `Class<Integer>`: Java boxes the primitive for the type argument
    /// (JLS 15.8.2), so the literal is of the token of its box, which the
    /// primitive token holds ([PrimitiveToken#boxed()]).
    ///
    /// @param type the primitive type
    /// @param <P> the marker of the primitive type
    /// @param <B> the box of the primitive type
    /// @param <A> the array type of the primitive type
    /// @return the class literal
    public static <P, B, A> Expr<Class<B>> classLiteral(PrimitiveToken<P, B, A> type) {
        return Expr.of(new Node.ClassLiteral(type), new Class_<>(type.boxed()).token);
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

    /// A constructor reference, `Type::new`, of the functional interface `samType` whose method `sam`
    /// takes the parameters of `ctor` and has the constructed class as its result. Like a lambda, it has
    /// no type of its own to javac, only that of the interface it is used as, so the fact of that
    /// interface is given too.
    ///
    /// The overloads of the arity families take a `CtorRefN` and name its class; this form takes a
    /// constructor the facts know generically, and the class it must construct.
    ///
    /// @param type the class the reference constructs, the owner of `ctor`
    /// @param ctor the fact of the constructor
    /// @param samType the functional interface the reference implements
    /// @param sam the single abstract method of `samType`
    /// @param <F> the functional interface
    /// @return the constructor reference
    /// @throws IllegalArgumentException if `ctor` is no constructor, `type` is another class than its
    ///     owner, `type` is an interface or an abstract class, or the parameters or result of `ctor` are
    ///     not those of `sam`
    public static <F> Expr<F> constructorRef(
            DeclaredToken<?> type, Invocable ctor, InterfaceToken<F> samType, Invocable sam) {
        ConstructorReferences.check(type, ctor, samType, sam);
        return Expr.of(new Node.ConstructorRef(type, ctor, sam), samType);
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
        return new Assignment(new Node.Assign(new Node.Target.Local(var), AssignOp.ASSIGN, value.node()));
    }

    /// `target.field = value;`
    ///
    /// @throws IllegalArgumentException if `target` is of a primitive type
    public static <O, T> Assignment assignField(
            Expr<? extends O> target, MutableFieldRef<O, T> field, Expr<? extends T> value) {
        Tokens.requireNoBoxing(value.type(), field.type(), "assignField");
        return new Assignment(
                new Node.Assign(new Node.Target.Field(receiver(target, field), field), AssignOp.ASSIGN, value.node()));
    }

    /// `Owner.field = value;`
    public static <T> Assignment assignStaticField(MutableStaticFieldRef<T> field, Expr<? extends T> value) {
        Tokens.requireNoBoxing(value.type(), field.type(), "assignStaticField");
        return new Assignment(new Node.Assign(new Node.Target.StaticField(field), AssignOp.ASSIGN, value.node()));
    }

    /// `array[index] = value;`
    ///
    /// The array is of exactly the type `type` names, as that of [Expr#arrayAccess(ArrayToken, Expr)] is:
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
        return new Assignment(
                new Node.Assign(new Node.Target.Element(array.node(), index.node()), AssignOp.ASSIGN, value.node()));
    }

    // ------------------------------------------------------------------
    // Compound assignment (JLS 15.26.2): no implicit conversion
    // ------------------------------------------------------------------

    /// `var op= value;`, a compound assignment. Java would convert `value` to
    /// the type of `var` and cast the result back implicitly; the typed layer
    /// writes no implicit conversion, so `value` must be of exactly the type
    /// of the place, and the operator must be one that type takes: `int +=
    /// int`, `long &= long`, `String += String`.
    ///
    /// @param var the place
    /// @param op the assignment operator, a compound one
    /// @param value the assigned value, of the type of `var`
    /// @param <T> the type of the place
    /// @return the assignment
    /// @throws IllegalArgumentException if `op` is plain `=`, the value is of another type, or
    ///     the type has no such operator
    public static <T> Assignment assign(MutVar<T> var, AssignOp op, Expr<? extends T> value) {
        compound(op, var.type(), value, "assign");
        return new Assignment(new Node.Assign(new Node.Target.Local(var), op, value.node()));
    }

    /// `target.field op= value;`, a compound assignment; see
    /// [#assign(MutVar, AssignOp, Expr)].
    ///
    /// @param target the receiver
    /// @param field the field
    /// @param op the assignment operator, a compound one
    /// @param value the assigned value, of the type of the field
    /// @param <O> the owner type
    /// @param <T> the field type
    /// @return the assignment
    /// @throws IllegalArgumentException if `op` is plain `=`, the value is of another type, or
    ///     the type has no such operator
    public static <O, T> Assignment assignField(
            Expr<? extends O> target, MutableFieldRef<O, T> field, AssignOp op, Expr<? extends T> value) {
        compound(op, field.type(), value, "assignField");
        return new Assignment(new Node.Assign(new Node.Target.Field(receiver(target, field), field), op, value.node()));
    }

    /// `Owner.field op= value;`, a compound assignment; see
    /// [#assign(MutVar, AssignOp, Expr)].
    ///
    /// @param field the static field
    /// @param op the assignment operator, a compound one
    /// @param value the assigned value, of the type of the field
    /// @param <T> the field type
    /// @return the assignment
    /// @throws IllegalArgumentException if `op` is plain `=`, the value is of another type, or
    ///     the type has no such operator
    public static <T> Assignment assignStaticField(
            MutableStaticFieldRef<T> field, AssignOp op, Expr<? extends T> value) {
        compound(op, field.type(), value, "assignStaticField");
        return new Assignment(new Node.Assign(new Node.Target.StaticField(field), op, value.node()));
    }

    /// `array[index] op= value;`, a compound assignment; see
    /// [#assign(MutVar, AssignOp, Expr)].
    ///
    /// @param type the array type
    /// @param array the array
    /// @param index the index
    /// @param op the assignment operator, a compound one
    /// @param value the assigned value, of the element type
    /// @param <A> the array type
    /// @param <T> the element type
    /// @return the assignment
    /// @throws IllegalArgumentException if `op` is plain `=`, the value is of another type, or
    ///     the type has no such operator
    public static <A, T> Assignment assignAt(
            ArrayToken<A, T> type, Expr<A> array, Expr<Prim.Int> index, AssignOp op, Expr<? extends T> value) {
        compound(op, type.component(), value, "assignAt");
        return new Assignment(new Node.Assign(new Node.Target.Element(array.node(), index.node()), op, value.node()));
    }

    /// Rejects a compound assignment the typed layer cannot write without an
    /// implicit conversion.
    private static void compound(AssignOp op, TypeToken<?> place, Expr<?> value, String where) {
        boolean taken = takes(op, place);
        if (op == AssignOp.ASSIGN) {
            throw new IllegalArgumentException(
                    where + " with `=` is the plain assignment; use the form without an AssignOp");
        }
        if (!taken) {
            throw new IllegalArgumentException(where + " with " + op.symbol() + " on a place of " + place
                    + ": the type has no such compound assignment (JLS 15.26.2)");
        }
    }

    private static final java.util.Set<PrimitiveToken<?, ?, ?>> NUMERIC = java.util.Set.of(
            PrimitiveToken.BYTE,
            PrimitiveToken.SHORT,
            PrimitiveToken.CHAR,
            PrimitiveToken.INT,
            PrimitiveToken.LONG,
            PrimitiveToken.FLOAT,
            PrimitiveToken.DOUBLE);
    private static final java.util.Set<PrimitiveToken<?, ?, ?>> INTEGRAL = java.util.Set.of(
            PrimitiveToken.BYTE, PrimitiveToken.SHORT, PrimitiveToken.CHAR, PrimitiveToken.INT, PrimitiveToken.LONG);
    private static final java.util.Set<PrimitiveToken<?, ?, ?>> INTEGRAL_OR_BOOLEAN = java.util.Set.of(
            PrimitiveToken.BYTE,
            PrimitiveToken.SHORT,
            PrimitiveToken.CHAR,
            PrimitiveToken.INT,
            PrimitiveToken.LONG,
            PrimitiveToken.BOOLEAN);

    /// Whether a type has the compound assignment `op`.
    private static boolean takes(AssignOp op, TypeToken<?> type) {
        if (type instanceof PrimitiveToken<?, ?, ?> primitive) {
            return switch (op) {
                case ASSIGN -> true;
                case SHL_ASSIGN, SHR_ASSIGN, USHR_ASSIGN -> INTEGRAL.contains(primitive);
                case AND_ASSIGN, OR_ASSIGN, XOR_ASSIGN -> INTEGRAL_OR_BOOLEAN.contains(primitive);
                case ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN -> NUMERIC.contains(primitive);
            };
        }
        return op == AssignOp.ADD_ASSIGN && type.erasure().equals(java.lang.constant.ConstantDescs.CD_String);
    }

    // ------------------------------------------------------------------
    // Increment and decrement (JLS 15.14.2, 15.15.1)
    // ------------------------------------------------------------------

    /// `++var`, of the type of `var`.
    public static <T> Increment<T> preIncrement(MutVar<T> var) {
        return increment(IncDecOp.PRE_INC, new Node.Target.Local(var), var.type());
    }

    /// `--var`, of the type of `var`.
    public static <T> Increment<T> preDecrement(MutVar<T> var) {
        return increment(IncDecOp.PRE_DEC, new Node.Target.Local(var), var.type());
    }

    /// `var++`, of the type of `var`.
    public static <T> Increment<T> postIncrement(MutVar<T> var) {
        return increment(IncDecOp.POST_INC, new Node.Target.Local(var), var.type());
    }

    /// `var--`, of the type of `var`.
    public static <T> Increment<T> postDecrement(MutVar<T> var) {
        return increment(IncDecOp.POST_DEC, new Node.Target.Local(var), var.type());
    }

    /// `++target.field`, of the type of the field.
    public static <O, T> Increment<T> preIncrement(Expr<? extends O> target, MutableFieldRef<O, T> field) {
        return increment(IncDecOp.PRE_INC, new Node.Target.Field(receiver(target, field), field), field.type());
    }

    /// `--target.field`, of the type of the field.
    public static <O, T> Increment<T> preDecrement(Expr<? extends O> target, MutableFieldRef<O, T> field) {
        return increment(IncDecOp.PRE_DEC, new Node.Target.Field(receiver(target, field), field), field.type());
    }

    /// `target.field++`, of the type of the field.
    public static <O, T> Increment<T> postIncrement(Expr<? extends O> target, MutableFieldRef<O, T> field) {
        return increment(IncDecOp.POST_INC, new Node.Target.Field(receiver(target, field), field), field.type());
    }

    /// `target.field--`, of the type of the field.
    public static <O, T> Increment<T> postDecrement(Expr<? extends O> target, MutableFieldRef<O, T> field) {
        return increment(IncDecOp.POST_DEC, new Node.Target.Field(receiver(target, field), field), field.type());
    }

    /// `++Owner.field`, of the type of the static field.
    public static <T> Increment<T> preIncrement(MutableStaticFieldRef<T> field) {
        return increment(IncDecOp.PRE_INC, new Node.Target.StaticField(field), field.type());
    }

    /// `--Owner.field`, of the type of the static field.
    public static <T> Increment<T> preDecrement(MutableStaticFieldRef<T> field) {
        return increment(IncDecOp.PRE_DEC, new Node.Target.StaticField(field), field.type());
    }

    /// `Owner.field++`, of the type of the static field.
    public static <T> Increment<T> postIncrement(MutableStaticFieldRef<T> field) {
        return increment(IncDecOp.POST_INC, new Node.Target.StaticField(field), field.type());
    }

    /// `Owner.field--`, of the type of the static field.
    public static <T> Increment<T> postDecrement(MutableStaticFieldRef<T> field) {
        return increment(IncDecOp.POST_DEC, new Node.Target.StaticField(field), field.type());
    }

    /// `++array[index]`, of the type of the element.
    public static <A, T> Increment<T> preIncrement(ArrayToken<A, T> type, Expr<A> array, Expr<Prim.Int> index) {
        return increment(IncDecOp.PRE_INC, new Node.Target.Element(array.node(), index.node()), type.component());
    }

    /// `--array[index]`, of the type of the element.
    public static <A, T> Increment<T> preDecrement(ArrayToken<A, T> type, Expr<A> array, Expr<Prim.Int> index) {
        return increment(IncDecOp.PRE_DEC, new Node.Target.Element(array.node(), index.node()), type.component());
    }

    /// `array[index]++`, of the type of the element.
    public static <A, T> Increment<T> postIncrement(ArrayToken<A, T> type, Expr<A> array, Expr<Prim.Int> index) {
        return increment(IncDecOp.POST_INC, new Node.Target.Element(array.node(), index.node()), type.component());
    }

    /// `array[index]--`, of the type of the element.
    public static <A, T> Increment<T> postDecrement(ArrayToken<A, T> type, Expr<A> array, Expr<Prim.Int> index) {
        return increment(IncDecOp.POST_DEC, new Node.Target.Element(array.node(), index.node()), type.component());
    }

    /// An increment or decrement of a place of a numeric primitive type: Java
    /// has none of a boolean or of a reference, where it would unbox.
    private static <T> Increment<T> increment(IncDecOp op, Node.Target target, TypeToken<T> type) {
        if (!(type instanceof PrimitiveToken<?, ?, ?> primitive) || primitive == PrimitiveToken.BOOLEAN) {
            throw new IllegalArgumentException("++ and -- are of a numeric primitive type, and the place is of "
                    + type + ": Java would unbox a box, and the typed layer writes no implicit conversion;"
                    + " unbox it explicitly, or use assign with addInt and friends");
        }
        return new Increment<>(new Node.Increment(op, target), type);
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
