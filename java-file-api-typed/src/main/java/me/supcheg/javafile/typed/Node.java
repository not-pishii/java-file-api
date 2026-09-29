package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.BinaryOp;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// The untyped expression IR of the typed layer. Unlike core expressions it
/// refers to variables by identity; lowering assigns names.
sealed interface Node {

    /// A literal, with its value for constant folding.
    record Lit(Expr literal, Object value) implements Node {}

    /// A literal that is not a constant expression: `null`. (A text block is
    /// a constant `String` (JLS 15.29) and, once supported, must be a [Lit]
    /// so that [Constants] folds it as javac does.)
    record RawLit(Expr literal) implements Node {}

    /// A reference to a local variable or parameter.
    record Local(Var<?> var) implements Node {}

    /// `this`, handed to the body of an instance member (§6.5); `owner` is the
    /// root block of that body, where `this` is in scope.
    record This(Block<?, ?> owner) implements Node {}

    /// Boxing of a primitive value, `Integer.valueOf(operand)`.
    record Box(PrimitiveToken<?, ?, ?> type, Node operand) implements Node {}

    /// Unboxing of a box, `operand.intValue()`.
    record Unbox(PrimitiveToken<?, ?, ?> type, Node operand) implements Node {}

    /// An instance method call; `method` is `void` or not.
    record Call(Node target, Invocable method, List<Node> args) implements Node {}

    /// A static method call.
    record StaticCall(Invocable method, List<Node> args) implements Node {}

    /// An instance creation, `new T(args)` or `new T<>(args)`.
    record New(Invocable ctor, List<Node> args, boolean diamond) implements Node {}

    /// An instance field read.
    record FieldGet(Node target, FieldRef<?, ?> field) implements Node {}

    /// A static field read.
    record StaticFieldGet(StaticFieldRef<?> field) implements Node {}

    /// An enum constant.
    record EnumConst(EnumConstant<?> constant) implements Node {}

    /// An array element read.
    record ArrayAt(Node array, Node index) implements Node {}

    /// An array length read.
    record ArrayLength(Node array) implements Node {}

    /// An array creation, `new T[length]`.
    record NewArray(TypeToken<?> component, Node length) implements Node {}

    /// A conditional, `c ? a : b`.
    record Cond(Node condition, Node whenTrue, Node whenFalse) implements Node {}

    /// A binary operator.
    record Binary(BinaryOp op, Node left, Node right, TypeToken<?> type) implements Node {}

    /// A unary operator.
    record Unary(UnaryOp op, Node operand, TypeToken<?> type) implements Node {}

    /// A cast.
    record Cast(TypeRef type, Node operand) implements Node {}

    /// An `instanceof` test without a binding.
    record InstanceOf(Node operand, TypeRef type) implements Node {}

    /// A lambda expression typed by a functional interface fact.
    record Lambda(TypeRef iface, Invocable sam, List<Var<?>> params, LambdaBody body) implements Node {}

    /// A `switch` expression over an enum.
    record Switch(Node selector, EnumToken<?> enumType, List<Case> cases, Optional<Node> otherwise) implements Node {}

    /// A case of a [Switch].
    record Case(EnumConstant<?> constant, Node value) {}

    /// An assignment, used as a statement.
    record Assign(Target target, Node value) implements Node {}

    /// An untyped core expression from `Unsafe`.
    record Raw(Expr expr) implements Node {}

    /// The body of a [Lambda]. Either way the lambda's parameters are owned by
    /// a lambda-boundary block (§6.2) nested in the block the lambda is
    /// built in.
    sealed interface LambdaBody {
        /// An expression body, `x -> expr`; `scope` is the (statement-less)
        /// lambda-boundary block that owns the parameters and in which
        /// `value` is checked.
        record Value(me.supcheg.javafile.typed.Block<?, ?> scope, Node value) implements LambdaBody {}

        /// A block body, `x -> { ... }`.
        record Block(me.supcheg.javafile.typed.Block<?, ?> block) implements LambdaBody {}
    }

    /// What an [Assign] assigns.
    sealed interface Target {
        /// A mutable local variable.
        record Local(MutVar<?> var) implements Target {}

        /// A non-final instance field.
        record Field(Node target, MutableFieldRef<?, ?> field) implements Target {}

        /// A non-final static field.
        record StaticField(MutableStaticFieldRef<?> field) implements Target {}

        /// An array element.
        record Element(Node array, Node index) implements Target {}

        /// The initialization of a blank `final` field of `this` in a constructor.
        record Init(FieldRef<?, ?> field) implements Target {}
    }
}
