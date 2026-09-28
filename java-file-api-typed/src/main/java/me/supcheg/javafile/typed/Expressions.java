package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.BinaryOp;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
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
import me.supcheg.javafile.facts.jdk.String_;

import java.util.List;

/// Combinators for typed expressions and effects (§6.1): literals, member
/// access, calls, `new`, arrays, `cond`, per-primitive operators, and
/// assignments.
///
/// Call/`new` combinators are shown here for arity 0..3; arity 4..12 follow
/// the identical pattern (one overload per [me.supcheg.javafile.facts]
/// `*RefN` family member) and are a mechanical extension — see the open
/// questions in the phase-1 report for hoisting this into a generator like
/// `java-file-api-facts`'s `FactsCodegen`.
///
/// Import the methods statically.
public final class Expressions {

    private Expressions() {}

    // ------------------------------------------------------------------
    // Literals
    // ------------------------------------------------------------------

    /// Creates a `String` literal.
    public static Expr<String> literal(String value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), String_.TOKEN);
    }

    /// Creates an `int` literal.
    public static Expr<Integer> literal(int value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.INT);
    }

    /// Creates a `long` literal.
    public static Expr<Long> literal(long value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.LONG);
    }

    /// Creates a `double` literal.
    public static Expr<Double> literal(double value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.DOUBLE);
    }

    /// Creates a `boolean` literal.
    public static Expr<Boolean> literal(boolean value) {
        return Expr.of(new Node.Lit(me.supcheg.javafile.code.Exprs.literal(value), value), PrimitiveToken.BOOLEAN);
    }

    /// Creates the `null` literal at the given reference type.
    ///
    /// @param type the declared static type of the literal
    public static <T> Expr<T> literalNull(RefToken<T> type) {
        return Expr.of(new Node.RawLit(me.supcheg.javafile.code.Exprs.literalNull()), type);
    }

    /// `this`, at the given self type — the natural companion of
    /// [TypedClassBuilder#self()] inside an instance method/constructor body.
    public static <T> Expr<T> this_(TypeToken<T> type) {
        return Expr.of(new Node.This(), type);
    }

    // ------------------------------------------------------------------
    // Member access
    // ------------------------------------------------------------------

    /// Reads an instance field, `target.field`.
    public static <O, T> Expr<T> field(Expr<? extends O> target, FieldRef<O, T> field) {
        return Expr.of(new Node.FieldGet(target.node(), field), field.type());
    }

    /// Reads a static field, `Owner.field`.
    public static <T> Expr<T> staticField(StaticFieldRef<T> field) {
        return Expr.of(new Node.StaticFieldGet(field), field.type());
    }

    // ------------------------------------------------------------------
    // Instance calls
    // ------------------------------------------------------------------

    public static <O, R> Invocation<R> call(Expr<? extends O> target, MethodRef0<O, R> method) {
        return new Invocation<>(new Node.Call(target.node(), method, List.of()), method.result());
    }

    public static <O, R, A1> Invocation<R> call(
            Expr<? extends O> target, MethodRef1<O, R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(new Node.Call(target.node(), method, List.of(a1.node())), method.result());
    }

    public static <O, R, A1, A2> Invocation<R> call(
            Expr<? extends O> target, MethodRef2<O, R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new Node.Call(target.node(), method, List.of(a1.node(), a2.node())), method.result());
    }

    public static <O, R, A1, A2, A3> Invocation<R> call(
            Expr<? extends O> target,
            MethodRef3<O, R, A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new Invocation<>(
                new Node.Call(target.node(), method, List.of(a1.node(), a2.node(), a3.node())), method.result());
    }

    public static <O> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef0<O> method) {
        return new VoidInvocation(new Node.Call(target.node(), method, List.of()));
    }

    public static <O, A1> VoidInvocation voidCall(
            Expr<? extends O> target, VoidMethodRef1<O, A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(new Node.Call(target.node(), method, List.of(a1.node())));
    }

    public static <O, A1, A2> VoidInvocation voidCall(
            Expr<? extends O> target, VoidMethodRef2<O, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(new Node.Call(target.node(), method, List.of(a1.node(), a2.node())));
    }

    public static <O, A1, A2, A3> VoidInvocation voidCall(
            Expr<? extends O> target,
            VoidMethodRef3<O, A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new VoidInvocation(new Node.Call(target.node(), method, List.of(a1.node(), a2.node(), a3.node())));
    }

    // ------------------------------------------------------------------
    // Static calls
    // ------------------------------------------------------------------

    public static <R> Invocation<R> staticCall(StaticMethodRef0<R> method) {
        return new Invocation<>(new Node.StaticCall(method, List.of()), method.result());
    }

    public static <R, A1> Invocation<R> staticCall(StaticMethodRef1<R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(new Node.StaticCall(method, List.of(a1.node())), method.result());
    }

    public static <R, A1, A2> Invocation<R> staticCall(
            StaticMethodRef2<R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new Node.StaticCall(method, List.of(a1.node(), a2.node())), method.result());
    }

    public static <R, A1, A2, A3> Invocation<R> staticCall(
            StaticMethodRef3<R, A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new Invocation<>(new Node.StaticCall(method, List.of(a1.node(), a2.node(), a3.node())), method.result());
    }

    public static VoidInvocation voidStaticCall(VoidStaticMethodRef0 method) {
        return new VoidInvocation(new Node.StaticCall(method, List.of()));
    }

    public static <A1> VoidInvocation voidStaticCall(VoidStaticMethodRef1<A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(new Node.StaticCall(method, List.of(a1.node())));
    }

    public static <A1, A2> VoidInvocation voidStaticCall(
            VoidStaticMethodRef2<A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(new Node.StaticCall(method, List.of(a1.node(), a2.node())));
    }

    public static <A1, A2, A3> VoidInvocation voidStaticCall(
            VoidStaticMethodRef3<A1, A2, A3> method,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return new VoidInvocation(new Node.StaticCall(method, List.of(a1.node(), a2.node(), a3.node())));
    }

    // ------------------------------------------------------------------
    // Instance creation
    // ------------------------------------------------------------------

    public static <O> Invocation<O> new_(CtorRef0<O> ctor) {
        return new Invocation<>(new Node.New(ctor, List.of(), false), ctor.owner());
    }

    public static <O, A1> Invocation<O> new_(CtorRef1<O, A1> ctor, Expr<? extends A1> a1) {
        return new Invocation<>(new Node.New(ctor, List.of(a1.node()), false), ctor.owner());
    }

    public static <O, A1, A2> Invocation<O> new_(
            CtorRef2<O, A1, A2> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new Node.New(ctor, List.of(a1.node(), a2.node()), false), ctor.owner());
    }

    public static <O, A1, A2, A3> Invocation<O> new_(
            CtorRef3<O, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new Invocation<>(new Node.New(ctor, List.of(a1.node(), a2.node(), a3.node()), false), ctor.owner());
    }

    /// Like [#new_(CtorRef0)], but rendered with a diamond, `new Owner<>()`.
    public static <O> Invocation<O> newDiamond(CtorRef0<O> ctor) {
        return new Invocation<>(new Node.New(ctor, List.of(), true), ctor.owner());
    }

    /// Like [#new_(CtorRef1,Expr)], but rendered with a diamond, `new Owner<>(a1)`.
    public static <O, A1> Invocation<O> newDiamond(CtorRef1<O, A1> ctor, Expr<? extends A1> a1) {
        return new Invocation<>(new Node.New(ctor, List.of(a1.node()), true), ctor.owner());
    }

    // ------------------------------------------------------------------
    // Arrays
    // ------------------------------------------------------------------

    /// Reads an array element, `array[index]`.
    public static <T> Expr<T> at(Expr<T[]> array, Expr<Integer> index) {
        return Expr.of(new Node.ArrayAt(array.node(), index.node()), componentOf(array.type()));
    }

    @SuppressWarnings("unchecked")
    private static <T> TypeToken<T> componentOf(TypeToken<T[]> arrayType) {
        return (TypeToken<T>) ((ArrayToken<T[]>) arrayType).component();
    }

    /// Reads `array.length`.
    public static Expr<Integer> length(Expr<?> array) {
        return Expr.of(new Node.ArrayLength(array.node()), PrimitiveToken.INT);
    }

    /// Creates `new T[length]`.
    public static <T> Expr<T[]> newArray(RefToken<T> component, Expr<Integer> length) {
        return Expr.of(new Node.NewArray(component, length.node()), ArrayToken.of(component));
    }

    // ------------------------------------------------------------------
    // Conditional
    // ------------------------------------------------------------------

    /// `condition ? whenTrue : whenFalse`. The result type must be given
    /// explicitly: the two branches may have different, only jointly related,
    /// static types.
    public static <T> Expr<T> cond(
            Expr<Boolean> condition, Expr<? extends T> whenTrue, Expr<? extends T> whenFalse, TypeToken<T> type) {
        return Expr.of(new Node.Cond(condition.node(), whenTrue.node(), whenFalse.node()), type);
    }

    // ------------------------------------------------------------------
    // `int` operators
    // ------------------------------------------------------------------

    public static Expr<Integer> addInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.ADD, l, r, PrimitiveToken.INT);
    }

    public static Expr<Integer> subInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.SUB, l, r, PrimitiveToken.INT);
    }

    public static Expr<Integer> mulInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.MUL, l, r, PrimitiveToken.INT);
    }

    public static Expr<Integer> divInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.DIV, l, r, PrimitiveToken.INT);
    }

    public static Expr<Integer> modInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.MOD, l, r, PrimitiveToken.INT);
    }

    public static Expr<Boolean> eqInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.EQ, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Boolean> neqInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.NEQ, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Boolean> ltInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.LT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Boolean> leInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.LE, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Boolean> gtInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.GT, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Boolean> geInt(Expr<Integer> l, Expr<Integer> r) {
        return binary(BinaryOp.GE, l, r, PrimitiveToken.BOOLEAN);
    }

    // ------------------------------------------------------------------
    // `long` / `double` arithmetic (representative subset)
    // ------------------------------------------------------------------

    public static Expr<Long> addLong(Expr<Long> l, Expr<Long> r) {
        return binary(BinaryOp.ADD, l, r, PrimitiveToken.LONG);
    }

    public static Expr<Long> mulLong(Expr<Long> l, Expr<Long> r) {
        return binary(BinaryOp.MUL, l, r, PrimitiveToken.LONG);
    }

    public static Expr<Double> addDouble(Expr<Double> l, Expr<Double> r) {
        return binary(BinaryOp.ADD, l, r, PrimitiveToken.DOUBLE);
    }

    public static Expr<Double> mulDouble(Expr<Double> l, Expr<Double> r) {
        return binary(BinaryOp.MUL, l, r, PrimitiveToken.DOUBLE);
    }

    // ------------------------------------------------------------------
    // Boolean logic
    // ------------------------------------------------------------------

    public static Expr<Boolean> and(Expr<Boolean> l, Expr<Boolean> r) {
        return binary(BinaryOp.AND, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Boolean> or(Expr<Boolean> l, Expr<Boolean> r) {
        return binary(BinaryOp.OR, l, r, PrimitiveToken.BOOLEAN);
    }

    public static Expr<Boolean> not(Expr<Boolean> operand) {
        return Expr.of(new Node.Unary(UnaryOp.NOT, operand.node(), PrimitiveToken.BOOLEAN), PrimitiveToken.BOOLEAN);
    }

    /// Reference equality, `l == r`. Distinct from [#eqInt(Expr,Expr)] and
    /// friends: there is no numeric promotion, and for reference types this
    /// is identity, never `equals`.
    public static <T> Expr<Boolean> eqRef(Expr<T> l, Expr<T> r) {
        return binary(BinaryOp.EQ, l, r, PrimitiveToken.BOOLEAN);
    }

    public static <T> Expr<Boolean> neqRef(Expr<T> l, Expr<T> r) {
        return binary(BinaryOp.NEQ, l, r, PrimitiveToken.BOOLEAN);
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

    public static Expr<Long> widenIntToLong(Expr<Integer> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.LONG.typeRef(), operand.node()), PrimitiveToken.LONG);
    }

    public static Expr<Double> widenIntToDouble(Expr<Integer> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.DOUBLE.typeRef(), operand.node()), PrimitiveToken.DOUBLE);
    }

    public static Expr<Double> widenLongToDouble(Expr<Long> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.DOUBLE.typeRef(), operand.node()), PrimitiveToken.DOUBLE);
    }

    /// Checked narrowing, `(int) operand`. Unlike widening this can lose
    /// information; the name says so.
    public static Expr<Integer> narrowLongToInt(Expr<Long> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.INT.typeRef(), operand.node()), PrimitiveToken.INT);
    }

    public static Expr<Integer> narrowDoubleToInt(Expr<Double> operand) {
        return Expr.of(new Node.Cast(PrimitiveToken.INT.typeRef(), operand.node()), PrimitiveToken.INT);
    }

    // ------------------------------------------------------------------
    // Checked reference cast (§6.1)
    // ------------------------------------------------------------------

    /// A checked cast, `(T) operand`. Only representable when `T` and the
    /// operand's static type are in a subtype relation one way or the other —
    /// enforced by `? super T`, so an unrelated cast does not compile.
    public static <T> Expr<T> castChecked(RefToken<T> type, Expr<? super T> operand) {
        Tokens.requireReifiable(type);
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
    public static <O, T> Assignment assignField(
            Expr<? extends O> target, MutableFieldRef<O, T> field, Expr<? extends T> value) {
        return new Assignment(new Node.Assign(new Node.Target.Field(target.node(), field), value.node()));
    }

    /// `Owner.field = value;`
    public static <T> Assignment assignStaticField(MutableStaticFieldRef<T> field, Expr<? extends T> value) {
        return new Assignment(new Node.Assign(new Node.Target.StaticField(field), value.node()));
    }

    /// `array[index] = value;`
    public static <T> Assignment assignAt(Expr<T[]> array, Expr<Integer> index, Expr<? extends T> value) {
        return new Assignment(new Node.Assign(new Node.Target.Element(array.node(), index.node()), value.node()));
    }

    // ------------------------------------------------------------------

    private static <A, B, R> Expr<R> binary(BinaryOp op, Expr<A> l, Expr<B> r, TypeToken<R> type) {
        return Expr.of(new Node.Binary(op, l.node(), r.node(), type), type);
    }
}
