package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.BinaryOp;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.jdk.Math_;
import me.supcheg.javafile.facts.jdk.String_;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/// Combinators for typed expressions and effects (§6.1): literals, member
/// access, calls, `new`, arrays, `cond`, per-primitive operators, explicit
/// conversions, and assignments.
///
/// Primitive values are typed by their [Prim] markers: `literal(1)` is an
/// `Expr<Prim.Int>`, never an `Expr<Integer>`. Boxing and unboxing are
/// explicit — [#box(PrimitiveToken, Expr)] and [#unbox(PrimitiveToken, Expr)]
/// — so passing an `int` where an `Integer` is expected, or the reverse, is
/// a compile error of the generator. A marker is still a subtype of
/// `Object`, which leaves two cases to run time:
///
/// - a primitive receiver — `call(literal(1), Object_.toString)` — is
///   rejected when the call is built, with a hint to box it;
/// - a primitive argument of an `Object` parameter —
///   `call(list, remove_Object, literal(1))` — is allowed and rendered with
///   an explicit cast, `list.remove((Object) 1)`.
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
/// Call/`new` combinators are shown here for arity 0..3; arity 4..12 follow
/// the identical pattern (one overload per [me.supcheg.javafile.facts]
/// `*RefN` family member) and are a mechanical extension.
///
/// Import the methods statically.
public final class Expressions {

    private Expressions() {}

    // ------------------------------------------------------------------
    // Literals
    // ------------------------------------------------------------------

    /// Creates a `String` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<String> literal(String value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), String_.TOKEN);
    }

    /// Creates an `int` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Int> literal(int value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.INT);
    }

    /// Creates a `long` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Long> literal(long value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.LONG);
    }

    /// Creates a `double` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Double> literal(double value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.DOUBLE);
    }

    /// Creates a `boolean` literal.
    ///
    /// @param value the value
    /// @return the literal
    public static Expr<Prim.Bool> literal(boolean value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.BOOLEAN);
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
        return Expr.of(
                new Node.Cast(type.typeRef(), new Node.RawLit(me.supcheg.javafile.code.Exprs.literalNull())), type);
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
    // Instance calls; each throws IllegalArgumentException for a
    // receiver of a primitive type
    // ------------------------------------------------------------------

    public static <O, R> Invocation<R> call(Expr<? extends O> target, MethodRef0<O, R> method) {
        return new Invocation<>(new Node.Call(receiver(target, method), method, List.of()), method.result());
    }

    public static <O, R, A1> Invocation<R> call(
            Expr<? extends O> target, MethodRef1<O, R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(new Node.Call(receiver(target, method), method, arguments(a1)), method.result());
    }

    public static <O, R, A1, A2> Invocation<R> call(
            Expr<? extends O> target, MethodRef2<O, R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new Node.Call(receiver(target, method), method, arguments(a1, a2)), method.result());
    }

    public static <O, R, A1, A2, A3> Invocation<R> call(
            Expr<? extends O> target,
            MethodRef3<O, R, A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new Invocation<>(
                new Node.Call(receiver(target, method), method, arguments(a1, a2, a3)), method.result());
    }

    public static <O> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef0<O> method) {
        return new VoidInvocation(new Node.Call(receiver(target, method), method, List.of()));
    }

    public static <O, A1> VoidInvocation voidCall(
            Expr<? extends O> target, VoidMethodRef1<O, A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(new Node.Call(receiver(target, method), method, arguments(a1)));
    }

    public static <O, A1, A2> VoidInvocation voidCall(
            Expr<? extends O> target, VoidMethodRef2<O, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(new Node.Call(receiver(target, method), method, arguments(a1, a2)));
    }

    public static <O, A1, A2, A3> VoidInvocation voidCall(
            Expr<? extends O> target,
            VoidMethodRef3<O, A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new VoidInvocation(new Node.Call(receiver(target, method), method, arguments(a1, a2, a3)));
    }

    // ------------------------------------------------------------------
    // Static calls
    // ------------------------------------------------------------------

    public static <R> Invocation<R> staticCall(StaticMethodRef0<R> method) {
        return new Invocation<>(new Node.StaticCall(method, List.of()), method.result());
    }

    public static <R, A1> Invocation<R> staticCall(StaticMethodRef1<R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(new Node.StaticCall(method, arguments(a1)), method.result());
    }

    public static <R, A1, A2> Invocation<R> staticCall(
            StaticMethodRef2<R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new Node.StaticCall(method, arguments(a1, a2)), method.result());
    }

    public static <R, A1, A2, A3> Invocation<R> staticCall(
            StaticMethodRef3<R, A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new Invocation<>(new Node.StaticCall(method, arguments(a1, a2, a3)), method.result());
    }

    public static VoidInvocation voidStaticCall(VoidStaticMethodRef0 method) {
        return new VoidInvocation(new Node.StaticCall(method, List.of()));
    }

    public static <A1> VoidInvocation voidStaticCall(VoidStaticMethodRef1<A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(new Node.StaticCall(method, arguments(a1)));
    }

    public static <A1, A2> VoidInvocation voidStaticCall(
            VoidStaticMethodRef2<A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(new Node.StaticCall(method, arguments(a1, a2)));
    }

    public static <A1, A2, A3> VoidInvocation voidStaticCall(
            VoidStaticMethodRef3<A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new VoidInvocation(new Node.StaticCall(method, arguments(a1, a2, a3)));
    }

    // ------------------------------------------------------------------
    // Instance creation: only a `CtorRefN` — the constructor of an
    // instantiable class — is accepted; the constructor of an abstract
    // class is an `AbstractCtorRefN`, which `new_` does not take (§3.1).
    // The type arguments are always those of the fact: lowering renders the
    // diamond itself where the target type is exactly the constructed one.
    // Each throws IllegalArgumentException for a class token with a
    // wildcard type argument, which `new` cannot instantiate (JLS 15.9).
    // ------------------------------------------------------------------

    public static <O> Invocation<O> new_(CtorRef0<O> ctor) {
        return new Invocation<>(instantiate(ctor, List.of()), ctor.owner());
    }

    public static <O, A1> Invocation<O> new_(CtorRef1<O, A1> ctor, Expr<? extends A1> a1) {
        return new Invocation<>(instantiate(ctor, arguments(a1)), ctor.owner());
    }

    public static <O, A1, A2> Invocation<O> new_(
            CtorRef2<O, A1, A2> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(instantiate(ctor, arguments(a1, a2)), ctor.owner());
    }

    public static <O, A1, A2, A3> Invocation<O> new_(
            CtorRef3<O, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new Invocation<>(instantiate(ctor, arguments(a1, a2, a3)), ctor.owner());
    }

    // ------------------------------------------------------------------
    // Arrays: the ArrayToken witnesses that the expression is an array and
    // what its element type is — `ArrayToken.of(String_.TOKEN)` for
    // `String[]`, `PrimitiveToken.INT.array()` for `int[]`
    // ------------------------------------------------------------------

    /// Reads an array element, `array[index]`.
    ///
    /// @param type the array type
    /// @param array the array
    /// @param index the index
    /// @param <A> the array type
    /// @param <T> the element type
    /// @return the element read
    public static <A, T> Expr<T> at(ArrayToken<A, T> type, Expr<? extends A> array, Expr<Prim.Int> index) {
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

    /// Creates an array, `new T[length]`. The element type must be
    /// reifiable: `new List<String>[n]` and `new T[n]` are generic array
    /// creation, which Java rejects (JLS 15.10.1).
    ///
    /// @param type the array type
    /// @param length the length
    /// @param <A> the array type
    /// @return the new array
    /// @throws IllegalArgumentException if the element type is not reifiable
    public static <A> Expr<A> newArray(ArrayToken<A, ?> type, Expr<Prim.Int> length) {
        Tokens.requireReifiable(type.component(), "newArray");
        return Expr.of(new Node.NewArray(type.component(), length.node()), type);
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
    /// @throws IllegalArgumentException if a branch is primitive and `T` is not, or the reverse
    public static <T> Expr<T> cond(
            Expr<Prim.Bool> condition, Expr<? extends T> whenTrue, Expr<? extends T> whenFalse, TypeToken<T> type) {
        return Expr.of(new Node.Cond(condition.node(), branch(whenTrue, type), branch(whenFalse, type)), type);
    }

    // ------------------------------------------------------------------
    // `int` operators
    // ------------------------------------------------------------------

    public static Expr<Prim.Int> addInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.ADD, l, r, PrimitiveToken.INT);
    }

    public static Expr<Prim.Int> subInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.SUB, l, r, PrimitiveToken.INT);
    }

    public static Expr<Prim.Int> mulInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.MUL, l, r, PrimitiveToken.INT);
    }

    public static Expr<Prim.Int> divInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.DIV, l, r, PrimitiveToken.INT);
    }

    public static Expr<Prim.Int> modInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.MOD, l, r, PrimitiveToken.INT);
    }

    public static Expr<Prim.Bool> eqInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.EQ, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> neqInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.NEQ, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> ltInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.LT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> leInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.LE, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> gtInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.GT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> geInt(Expr<Prim.Int> l, Expr<Prim.Int> r) {
        return binary(BinaryOp.GE, l, r, PrimitiveToken.BOOLEAN);
    }

    // ------------------------------------------------------------------
    // `long` / `double` arithmetic (representative subset)
    // ------------------------------------------------------------------

    public static Expr<Prim.Long> addLong(Expr<Prim.Long> l, Expr<Prim.Long> r) {
        return binary(BinaryOp.ADD, l, r, PrimitiveToken.LONG);
    }

    public static Expr<Prim.Long> mulLong(Expr<Prim.Long> l, Expr<Prim.Long> r) {
        return binary(BinaryOp.MUL, l, r, PrimitiveToken.LONG);
    }

    public static Expr<Prim.Double> addDouble(Expr<Prim.Double> l, Expr<Prim.Double> r) {
        return binary(BinaryOp.ADD, l, r, PrimitiveToken.DOUBLE);
    }

    public static Expr<Prim.Double> mulDouble(Expr<Prim.Double> l, Expr<Prim.Double> r) {
        return binary(BinaryOp.MUL, l, r, PrimitiveToken.DOUBLE);
    }

    // ------------------------------------------------------------------
    // Boolean logic
    // ------------------------------------------------------------------

    public static Expr<Prim.Bool> and(Expr<Prim.Bool> l, Expr<Prim.Bool> r) {
        return binary(BinaryOp.AND, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> or(Expr<Prim.Bool> l, Expr<Prim.Bool> r) {
        return binary(BinaryOp.OR, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Prim.Bool> not(Expr<Prim.Bool> operand) {
        return Expr.of(new Node.Unary(UnaryOp.NOT, operand.node(), PrimitiveToken.BOOLEAN), PrimitiveToken.BOOLEAN);
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
        return binary(BinaryOp.EQ, reference(l, "eqRef"), reference(r, "eqRef"), PrimitiveToken.BOOLEAN);
    }

    /// Reference non-identity, `l != r`; see [#eqRef(Expr, Expr)].
    ///
    /// @param l the left operand
    /// @param r the right operand
    /// @param <T> the static type of `l`
    /// @return the comparison
    /// @throws IllegalArgumentException if an operand is of a primitive type
    public static <T> Expr<Prim.Bool> neqRef(Expr<T> l, Expr<? extends T> r) {
        return binary(BinaryOp.NEQ, reference(l, "neqRef"), reference(r, "neqRef"), PrimitiveToken.BOOLEAN);
    }

    // ------------------------------------------------------------------
    // Strings
    // ------------------------------------------------------------------

    /// Explicit string concatenation, `l + r` as `String`. Never an overload
    /// of `add`: mixing arithmetic and concatenation under one name is a
    /// classic Java footgun the typed layer refuses to reproduce.
    public static Expr<String> concat(Expr<String> l, Expr<String> r) {
        return binary(BinaryOp.ADD, l, r, String_.TOKEN);
    }

    // ------------------------------------------------------------------
    // Explicit numeric conversions (no promotion, §6.1/§10)
    // ------------------------------------------------------------------

    /// Widening, `(long) operand`; it never loses information.
    public static Expr<Prim.Long> widenIntToLong(Expr<Prim.Int> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.LONG.typeRef(), operand.node()), PrimitiveToken.LONG);
    }

    /// Widening, `(double) operand`; it never loses information.
    public static Expr<Prim.Double> widenIntToDouble(Expr<Prim.Int> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.DOUBLE.typeRef(), operand.node()), PrimitiveToken.DOUBLE);
    }

    /// Widening, `(double) operand`; it may round a large value (JLS 5.1.2).
    public static Expr<Prim.Double> widenLongToDouble(Expr<Prim.Long> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.DOUBLE.typeRef(), operand.node()), PrimitiveToken.DOUBLE);
    }

    /// Checked narrowing, `Math.toIntExact(operand)`: throws
    /// `ArithmeticException` at run time for a value out of the `int` range.
    public static Expr<Prim.Int> narrowCheckedLongToInt(Expr<Prim.Long> operand) {
        return staticCall(Math_.toIntExact, operand);
    }

    /// Truncating narrowing, `(int) operand`: keeps the low 32 bits of a
    /// value out of the `int` range, silently (JLS 5.1.3).
    public static Expr<Prim.Int> narrowTruncatingLongToInt(Expr<Prim.Long> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.INT.typeRef(), operand.node()), PrimitiveToken.INT);
    }

    /// Truncating narrowing, `(int) operand`: rounds toward zero and clamps
    /// a value out of the `int` range, silently (JLS 5.1.3).
    public static Expr<Prim.Int> narrowTruncatingDoubleToInt(Expr<Prim.Double> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.INT.typeRef(), operand.node()), PrimitiveToken.INT);
    }

    // ------------------------------------------------------------------
    // Checked reference cast (§6.1)
    // ------------------------------------------------------------------

    /// A checked cast, `(T) operand`. Only representable when `T` and the
    /// operand's static type are in a subtype relation one way or the other —
    /// enforced by `? super T`, so an unrelated cast does not compile — and
    /// when `T` is reifiable, so the cast is checked at run time for all of
    /// `T`: `(List<String>) o` is rejected, `(List<?>) o` is not.
    ///
    /// @param type the target type
    /// @param operand the operand
    /// @param <T> the target type
    /// @return the cast, or `operand` itself if its type is already `T`
    /// @throws IllegalArgumentException if `type` is not reifiable
    public static <T> Expr<T> castChecked(RefToken<T> type, Expr<? super T> operand) {
        Tokens.requireReifiable(type, "castChecked");
        if (Tokens.sameType(operand.type(), type) && Node.isExact(operand.node())) {
            // `(T) operand` would be a redundant cast (javac -Xlint:cast).
            return Expr.of(operand.node(), type);
        }
        return Expr.of(new Node.Cast(type.typeRef(), operand.node()), type);
    }

    // ------------------------------------------------------------------
    // Assignments
    // ------------------------------------------------------------------

    /// `var = value;`
    public static <T> Assignment assign(MutVar<T> var, Expr<? extends T> value) {
        return new Assignment(new Node.Assign(new Node.Target.Local(var), value.node()));
    }

    /// `target.field = value;`
    ///
    /// @throws IllegalArgumentException if `target` is of a primitive type
    public static <O, T> Assignment assignField(
            Expr<? extends O> target, MutableFieldRef<O, T> field, Expr<? extends T> value) {
        return new Assignment(new Node.Assign(new Node.Target.Field(receiver(target, field), field), value.node()));
    }

    /// `Owner.field = value;`
    public static <T> Assignment assignStaticField(MutableStaticFieldRef<T> field, Expr<? extends T> value) {
        return new Assignment(new Node.Assign(new Node.Target.StaticField(field), value.node()));
    }

    /// `array[index] = value;`
    ///
    /// Arrays are covariant: storing through an `Object[]` view of a
    /// `String[]` throws `ArrayStoreException` at run time, as in Java.
    ///
    /// @param type the array type
    /// @param array the array
    /// @param index the index
    /// @param value the stored value
    /// @param <A> the array type
    /// @param <T> the element type
    /// @return the assignment
    public static <A, T> Assignment assignAt(
            ArrayToken<A, T> type, Expr<? extends A> array, Expr<Prim.Int> index, Expr<? extends T> value) {
        return new Assignment(new Node.Assign(new Node.Target.Element(array.node(), index.node()), value.node()));
    }

    // ------------------------------------------------------------------

    private static <A, B, R> Expr<R> binary(BinaryOp op, Expr<A> l, Expr<B> r, TypeToken<R> type) {
        return Expr.of(new Node.Binary(op, l.node(), r.node(), type), type);
    }

    /// A primitive has no members: `5.toString()` is not Java. A marker is a
    /// subtype of `Object`, so javac lets an `Expr<Prim.Int>` through where
    /// the owner is `Object`; this is where it is caught (§6.1, §9).
    private static Node.Operand receiver(Expr<?> target, Object member) {
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

    private static Node.Operand operand(Expr<?> expr) {
        return new Node.Operand(expr.node(), expr.type());
    }

    /// The arguments of a call with their static types, from which lowering
    /// pins the overload the fact names (§6.1).
    private static List<Node.Operand> arguments(Expr<?>... args) {
        List<Node.Operand> operands = new ArrayList<>(args.length);
        for (Expr<?> arg : args) {
            operands.add(operand(arg));
        }
        return List.copyOf(operands);
    }

    private static Node instantiate(Invocable ctor, List<Node.Operand> args) {
        if (ctor.owner().typeRef() instanceof ParameterizedTypeRef parameterized
                && !parameterized.args().stream().allMatch(ExactTypeArg.class::isInstance)) {
            throw new IllegalArgumentException("cannot instantiate " + ctor.owner()
                    + ": `new` needs exact type arguments, not wildcards (JLS 15.9); construct the class through"
                    + " the facts of an exact parameterization");
        }
        return new Node.New(ctor, args);
    }

    /// A branch of [#cond(Expr, Expr, Expr, TypeToken)], cast to the type of
    /// the conditional where its own type differs.
    private static Node branch(Expr<?> branch, TypeToken<?> type) {
        boolean primitive = type instanceof PrimitiveToken<?, ?, ?>;
        if ((branch.type() instanceof PrimitiveToken<?, ?, ?>) != primitive) {
            throw new IllegalArgumentException("cond of type " + type + " has a branch of type " + branch.type()
                    + ": cond does not mix primitive and reference branches, which Java would box, unbox or"
                    + " promote implicitly (JLS 15.25); box or unbox the branch explicitly");
        }
        return Tokens.sameType(branch.type(), type) ? branch.node() : new Node.Cast(type.typeRef(), branch.node());
    }
}
