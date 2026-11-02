package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.AssignOp;
import me.supcheg.javafile.code.BinaryOp;
import me.supcheg.javafile.code.ConstantLiteral;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.IncDecOp;
import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.code.UnaryOp;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/// The untyped expression IR of the typed layer. Unlike core expressions it
/// refers to variables by identity; lowering assigns names.
sealed interface Node {

    /// Whether javac types `node`, lowered as it stands, by exactly the token
    /// of the typed [me.supcheg.javafile.typed.Expr] it is the node of.
    /// Every construct is typed so, except an instance call through a
    /// receiver of a subtype of the method's owner — the subtype may override
    /// the method with a narrower result (JLS 8.4.8.3) — and an element of an
    /// array so typed. Lowering pins such a node where its type matters, see
    /// [Lowering].
    ///
    /// @param node the node
    /// @return `true` if the static type javac infers is the token's
    static boolean isExact(Node node) {
        return switch (node) {
            case Call(var target, var method, var _, var _) ->
                isExact(target.node()) && Tokens.sameType(target.type(), method.owner());
            case ArrayAt(var array, var _) -> isExact(array);
            case ArrayClone(var array) -> isExact(array);
            // javac types the expression by what it is of, not by the supertype its token tells.
            case Upcast _ -> false;
            // a method reference has no type of its own to javac, only that of its context.
            case MethodRef _ -> false;
            // neither has a constructor reference.
            case ConstructorRef _ -> false;
            case Exact _ -> true;
        };
    }

    /// An expression javac types by its token whatever its operands are: every form but a call and
    /// an array access, which are of what their receiver is of, and a widening to a supertype.
    ///
    /// Among them: a lambda has no type but that of its context, and lowering renders it under a
    /// cast to its token or where the target type is exactly its token; the method of `super.m()`
    /// is the one the superclass has, with the result of the fact (`MemberBodies.superCall`); and
    /// every result of a switch is of its token, so javac types the switch by it (JLS 15.28.1).
    sealed interface Exact extends Node {}

    /// A literal, which is its value for constant folding.
    record Lit(ConstantLiteral literal) implements Exact {}

    /// A text block literal: a constant `String`, rendered with `"""` delimiters or, where its value
    /// cannot be written in a block, as an escaped string literal (the core renderer decides).
    ///
    /// @param value the text block content
    record TextBlock(String value) implements Exact {}

    /// `null` of a reference type, `((T) null)`: the bare `null` has no type of its own to javac, and is
    /// no constant expression (JLS 15.29). (A text block is a constant `String` and, once supported, must
    /// be a [Lit] so that [Constants] folds it as javac does.)
    ///
    /// @param type the type the `null` is of
    record Null(RefToken<?> type) implements Exact {}

    /// A reference to a local variable or parameter.
    record Local(Var<?> var) implements Exact {}

    /// `this`, handed to the body of an instance member (§6.5); `owner` is the
    /// root block of that body, where `this` is in scope.
    record This(Block<?, ?> owner) implements Exact {}

    /// `Outer.this`: the instance of the class that encloses the inner class
    /// whose body names it (JLS 15.8.4).
    ///
    /// @param outer the enclosing class
    record OuterThis(DeclaredToken<?> outer) implements Exact {}

    /// An instance creation qualified by an enclosing instance,
    /// `outer.new Inner(args)` (JLS 15.9.2): the constructor of an inner class
    /// takes the instance of the class that encloses it.
    record InnerNew(Operand enclosing, Invocable ctor, List<Operand> args, Passing passing) implements Exact {}

    /// Boxing of a primitive value, `Integer.valueOf(operand)`.
    record Box(PrimitiveToken<?, ?, ?> type, Node operand) implements Exact {}

    /// Unboxing of a box, `operand.intValue()`.
    record Unbox(PrimitiveToken<?, ?, ?> type, Node operand) implements Exact {}

    /// An instance method call; `method` is `void` or not. The receiver and
    /// the arguments keep their static types: lowering pins the member the
    /// fact names from them (§6.1).
    record Call(Operand target, Invocable method, List<Operand> args, Passing passing) implements Node {}

    /// `super.method(args)`, in a body where `this` is that of `owner`: the method `superclass` —
    /// the superclass of the class being declared — has as a member.
    ///
    /// @param owner the root block of the body whose `this` it is called on
    /// @param superclass the superclass, whose methods javac searches
    /// @param method the fact of the method
    /// @param args the arguments, with their static types
    /// @param passing how they meet the parameters of the method
    record SuperCall(
            Block<?, ?> owner, DeclaredToken<?> superclass, Invocable method, List<Operand> args, Passing passing)
            implements Exact {}

    /// A static method call.
    record StaticCall(Invocable method, List<Operand> args, Passing passing) implements Exact {}

    /// An instance creation, `new T(args)`; lowering renders `new T<>(args)`
    /// itself where the target type is exactly `T`.
    record New(Invocable ctor, List<Operand> args, Passing passing) implements Exact {}

    /// An instance field read.
    record FieldGet(Operand target, FieldRef<?, ?> field) implements Exact {}

    /// A static field read.
    record StaticFieldGet(StaticFieldRef<?> field) implements Exact {}

    /// An enum constant.
    record EnumConst(EnumConstant<?> constant) implements Exact {}

    /// An array element read.
    record ArrayAt(Node array, Node index) implements Node {}

    /// A copy of an array, `array.clone()` (JLS 10.7): of the type of the array javac types by.
    record ArrayClone(Node array) implements Node {}

    /// An array length read.
    record ArrayLength(Node array) implements Exact {}

    /// An array creation, `new T[length]`, or `new T[l1][l2]` of the array of arrays: `component` is what the
    /// array type is left once the dimensions that are given a length are taken off.
    record NewArray(TypeToken<?> component, NonEmptyList<Node> lengths) implements Exact {}

    /// An array creation with an initializer, `new T[] {e1, e2}`; lowering renders the `{e1, e2}`
    /// alone where the declaration it initializes tells the type.
    ///
    /// @param type the array type
    /// @param elements the elements, each assignable to the component of `type` without boxing
    record NewArrayOf(ArrayToken<?, ?> type, List<Node> elements) implements Exact {
        public NewArrayOf {
            elements = List.copyOf(elements);
        }
    }

    /// A conditional, `c ? a : b`.
    record Cond(Node condition, Node whenTrue, Node whenFalse) implements Exact {}

    /// A binary operator, `left op right`.
    record Binary(Operator op, Node left, Node right) implements Exact {}

    /// The binary operators of the typed layer: each is of the type of its operands, so what it
    /// folds to where they are constant ([Constants]) is told by the operator alone.
    enum Operator {
        ADD_INT(BinaryOp.ADD),
        SUB_INT(BinaryOp.SUB),
        MUL_INT(BinaryOp.MUL),
        DIV_INT(BinaryOp.DIV),
        MOD_INT(BinaryOp.MOD),
        EQ_INT(BinaryOp.EQ),
        NEQ_INT(BinaryOp.NEQ),
        LT_INT(BinaryOp.LT),
        LE_INT(BinaryOp.LE),
        GT_INT(BinaryOp.GT),
        GE_INT(BinaryOp.GE),
        ADD_LONG(BinaryOp.ADD),
        MUL_LONG(BinaryOp.MUL),
        ADD_DOUBLE(BinaryOp.ADD),
        MUL_DOUBLE(BinaryOp.MUL),
        BIT_AND_INT(BinaryOp.BIT_AND),
        BIT_OR_INT(BinaryOp.BIT_OR),
        BIT_XOR_INT(BinaryOp.BIT_XOR),
        BIT_AND_LONG(BinaryOp.BIT_AND),
        BIT_OR_LONG(BinaryOp.BIT_OR),
        BIT_XOR_LONG(BinaryOp.BIT_XOR),
        SHL_INT(BinaryOp.SHL),
        SHR_INT(BinaryOp.SHR),
        USHR_INT(BinaryOp.USHR),
        SHL_LONG(BinaryOp.SHL),
        SHR_LONG(BinaryOp.SHR),
        USHR_LONG(BinaryOp.USHR),
        /// `&&`.
        AND(BinaryOp.AND),
        /// `||`.
        OR(BinaryOp.OR),
        /// `==` of two references.
        EQ_REF(BinaryOp.EQ),
        /// `!=` of two references.
        NEQ_REF(BinaryOp.NEQ),
        /// `+` of two strings.
        CONCAT(BinaryOp.ADD);

        private final BinaryOp rendered;

        Operator(BinaryOp rendered) {
            this.rendered = rendered;
        }

        /// The operator as it is rendered.
        BinaryOp rendered() {
            return rendered;
        }
    }

    /// A logical complement, `!operand`.
    record Not(Node operand) implements Exact {}

    /// A unary numeric operator, `operator operand`: arithmetic negation or
    /// unary plus, which promote `byte`, `short` and `char` to `int`, or the
    /// bitwise complement of an integral type.
    ///
    /// @param op the operator
    /// @param operand the operand
    record Unary(UnaryOp op, Node operand) implements Exact {}

    /// A pre- or post-increment or decrement of a place, `++operand` or
    /// `operand--`; its type is that of the operand.
    ///
    /// @param op the operator
    /// @param target the place
    record Increment(IncDecOp op, Target target) implements Exact {}

    /// A class literal, `type.class`, of type `Class<T>`.
    ///
    /// @param type the type the literal is of
    record ClassLiteral(TypeToken<?> type) implements Exact {}

    /// A plain `instanceof` test, `operand instanceof type`, without a
    /// pattern binding. It is `false` for a `null` operand and never throws
    /// (JLS 15.20.2).
    ///
    /// @param operand the tested expression, with its static type
    /// @param type the tested type
    record InstanceOf(Operand operand, RefToken<?> type) implements Exact {}

    /// A conversion of a number to another primitive type, `(int) operand`.
    record Convert(Conversion conversion, Node operand) implements Exact {}

    /// The numeric conversions of the typed layer, each from the type it converts and to the one it
    /// converts to.
    // the tokens are the singletons of `PrimitiveToken`, which expose nothing that can change
    @SuppressWarnings("ImmutableEnumChecker")
    enum Conversion {
        INT_TO_LONG(PrimitiveToken.LONG),
        INT_TO_DOUBLE(PrimitiveToken.DOUBLE),
        LONG_TO_DOUBLE(PrimitiveToken.DOUBLE),
        LONG_TO_INT(PrimitiveToken.INT),
        DOUBLE_TO_INT(PrimitiveToken.INT);

        private final PrimitiveToken<?, ?, ?> type;

        Conversion(PrimitiveToken<?, ?, ?> type) {
            this.type = type;
        }

        /// The type the number is converted to.
        PrimitiveToken<?, ?, ?> type() {
            return type;
        }
    }

    /// A cast to a reference type.
    ///
    /// @param type the type cast to
    /// @param operand what is cast, with its static type: a cast of a value of a type variable may be
    ///     one javac warns of ([Lowering])
    record Cast(RefToken<?> type, Operand operand) implements Exact {}

    /// An expression seen as of a supertype by an [me.supcheg.javafile.typed.Upcast]: `inner` is
    /// rendered as it is, without a cast, so javac types it by `innerType`
    /// — the class being declared — though the token of the expression is
    /// `type`. Lowering goes by `innerType` where the type of the expression
    /// decides a member, and casts to `type` where the expression must be of
    /// exactly its token.
    ///
    /// @param inner the expression
    /// @param innerType the token of its static type, which javac sees
    /// @param type the supertype the witness is of
    record Upcast(Node inner, TypeToken<?> innerType, TypeToken<?> type) implements Node {}

    /// A lambda expression typed by a functional interface fact: `sam` is
    /// the single abstract method of the interface, its owner.
    record Lambda(Invocable sam, List<Var<?>> params, LambdaBody body) implements Exact {}

    /// A method reference typed by a functional interface fact: `receiver::method` where `receiver` is
    /// present, `type::method` where it is not. Like a lambda, it has no type of its own to javac, only
    /// that of its context.
    ///
    /// @param receiver the bound receiver, or empty for a type-qualified reference
    /// @param type the qualifying type, the owner of the method or a subtype of it
    /// @param method the fact of the referenced method
    /// @param sam the single abstract method of the functional interface the reference implements
    record MethodRef(Optional<Operand> receiver, DeclaredToken<?> type, Invocable method, Invocable sam)
            implements Node {}

    /// A constructor reference typed by a functional interface fact, `Type::new`, where `type` is the
    /// class the constructor belongs to. Like a lambda, it has no type of its own to javac, only that of
    /// its context.
    ///
    /// @param type the class the reference constructs, the owner of the constructor
    /// @param constructor the fact of the constructor
    /// @param sam the single abstract method of the functional interface the reference implements
    record ConstructorRef(DeclaredToken<?> type, Invocable constructor, Invocable sam) implements Node {}

    /// A `switch` expression over an enum, `switch (selector) { case A, B -> ...; default -> ... }`.
    /// Every result is of the type of the expression, see [Arm].
    ///
    /// @param selector the selector, with its static type: lowering casts it to `enumType` if it is of another
    /// @param enumType the enum
    /// @param cases the cases, in order: every constant in at most one, and in exactly one if there is no
    ///     `otherwise`
    /// @param otherwise the `default` case, or that there is none
    record Switch(Operand selector, EnumToken<?> enumType, List<Case> cases, Default otherwise) implements Exact {
        public Switch {
            cases = List.copyOf(cases);
        }

        /// What the cases are, the `default` one last.
        Stream<Arm> arms() {
            return Stream.concat(
                    cases.stream().map(Case::arm),
                    switch (otherwise) {
                        case Default.None _ -> Stream.<Arm>empty();
                        case Default.Of(var arm) -> Stream.of(arm);
                    });
        }
    }

    /// A case of a [Switch], `case A, B -> arm`.
    ///
    /// @param constants the constants of the label, at least one
    /// @param arm what the case is
    record Case(List<EnumConstant<?>> constants, Arm arm) {
        public Case {
            constants = List.copyOf(constants);
        }
    }

    /// The `default` case of a [Switch].
    sealed interface Default {
        /// There is none: the cases cover every constant of the enum.
        record None() implements Default {}

        /// `default -> arm`.
        record Of(Arm arm) implements Default {}
    }

    /// What a case of a [Switch] is.
    sealed interface Arm {
        /// `-> value`: `value` is of the type of the `switch`, cast to it where its own type is another.
        record Value(Node value) implements Arm {}

        /// `-> { ... }`: a block nested in the block the `switch` is built in, that ends by `yield`
        /// or cannot complete normally.
        record Block(YieldBody<?> block) implements Arm {}
    }

    /// An assignment, used as a statement.
    ///
    /// @param target the place assigned to
    /// @param op the assignment operator, `=` or a compound one
    /// @param value the assigned expression
    record Assign(Target target, AssignOp op, Node value) implements Exact {}

    /// An untyped core expression from `Unsafe`.
    record Raw(Expr expr) implements Exact {}

    /// A subexpression together with the token of its static type, where
    /// lowering needs the type to pin the member the fact names: the
    /// receiver and the arguments of a call, the receiver of a field.
    ///
    /// @param node the expression
    /// @param type the token of its static type
    record Operand(Node node, TypeToken<?> type) {}

    /// How the arguments of a call meet the parameters of the member it calls.
    sealed interface Passing {
        /// One argument for each parameter.
        Passing EACH = new Each();

        /// One argument for each parameter; that of a last parameter of variable arity is its array.
        record Each() implements Passing {}

        /// The arguments from the last parameter on are the elements of its array, `type`: `m(a, e1, e2)`
        /// for `m(A, E...)`. The member is of variable arity.
        ///
        /// @param type the type of the array of the last parameter
        record Spread(ArrayToken<?, ?> type) implements Passing {}
    }

    /// The type of the parameter that the argument at `index` of a call of `member` is passed to.
    ///
    /// @param member the member called
    /// @param passing how the arguments meet its parameters
    /// @param index the position of the argument
    /// @return the parameter type; for a spread argument, the component of the last parameter
    static TypeToken<?> parameter(Invocable member, Passing passing, int index) {
        List<TypeToken<?>> params = member.params();
        return switch (passing) {
            case Passing.Each _ -> params.get(index);
            case Passing.Spread(var type) -> index < params.size() - 1 ? params.get(index) : type.component();
        };
    }

    /// The body of a [Lambda]. Either way the lambda's parameters are owned by
    /// a lambda-boundary block (§6.2) nested in the block the lambda is
    /// built in, if it is built in one.
    sealed interface LambdaBody {
        /// An expression body, `x -> expr`; `scope` is the (statement-less)
        /// lambda-boundary block that owns the parameters and in which
        /// `value` is checked. For a `void` method `value` is a statement
        /// expression, an [Assign] among them.
        record Value(me.supcheg.javafile.typed.Block<?, ?> scope, Node value) implements LambdaBody {}

        /// A block body, `x -> { ... }`.
        record Block(me.supcheg.javafile.typed.Block<?, ?> block) implements LambdaBody {}
    }

    /// What an [Assign] assigns.
    sealed interface Target {
        /// A mutable local variable.
        record Local(MutVar<?> var) implements Target {}

        /// A non-final instance field.
        record Field(Operand target, MutableFieldRef<?, ?> field) implements Target {}

        /// A non-final static field.
        record StaticField(MutableStaticFieldRef<?> field) implements Target {}

        /// An array element.
        record Element(Node array, Node index) implements Target {}
    }
}
