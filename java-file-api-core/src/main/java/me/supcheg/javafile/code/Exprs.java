package me.supcheg.javafile.code;

import me.supcheg.javafile.builder.AnonymousClassBuilder;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// Creates expressions: literals, names, calls, `new`, operators, casts,
/// lambdas, `switch` expressions, and so on.
///
/// Import the methods statically. Member access, calls on an object,
/// indexing, and `instanceof` continue an existing expression, so they are
/// methods of [Expr]:
///
/// ```java
/// import static me.supcheg.javafile.code.Exprs.*;
///
/// literal("hi")                                  // "hi"
/// field("name")                                  // name
/// this_().field("name")                          // this.name
/// call("compute", literal(1))                    // compute(1)
/// staticCall(MATH, "max", field("a"), field("b"))  // Math.max(a, b)
/// new_(ARRAY_LIST)                               // new ArrayList()
/// add(field("a"), literal(1))                    // a + 1
/// lambda(List.of("x"), mul(field("x"), literal(2)))  // (x) -> x * 2
/// ```
///
/// Parentheses are added automatically where operator precedence requires
/// them.
public final class Exprs {

    private Exprs() {}

    /// Creates a string literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public static LiteralExpr literal(String value) {
        return new StringLiteral(value);
    }

    /// Creates an `int` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public static LiteralExpr literal(int value) {
        return new IntLiteral(value);
    }

    /// Creates a `long` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public static LiteralExpr literal(long value) {
        return new LongLiteral(value);
    }

    /// Creates a `double` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public static LiteralExpr literal(double value) {
        return new DoubleLiteral(value);
    }

    /// Creates a `boolean` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public static LiteralExpr literal(boolean value) {
        return new BooleanLiteral(value);
    }

    /// Creates the `null` literal.
    ///
    /// @return a literal expression
    public static LiteralExpr literalNull() {
        return new NullLiteral();
    }

    /// Creates a text block literal, rendered with `"""` delimiters.
    ///
    /// @param value the text block content
    /// @return an expression
    public static Expr textBlock(String value) {
        return new TextBlockExpr(value);
    }

    /// Creates the `this` expression, e.g. as the target of `field`/`call` to
    /// express `this.name`/`this.method(args)`.
    ///
    /// @return a `this` expression
    public static ThisExpr this_() {
        return new ThisExpr();
    }

    /// Creates the `super` expression, e.g. as the target of `field`/`call` to
    /// express `super.name`/`super.method(args)`.
    ///
    /// @return a `super` expression
    public static SuperExpr super_() {
        return new SuperExpr();
    }

    /// Creates an unqualified field access, e.g. `name`.
    ///
    /// @param name the field name
    /// @return a field access expression
    public static FieldAccessExpr field(String name) {
        return new FieldAccessExpr(Optional.empty(), name);
    }

    /// Creates a static field access, e.g. `type.name`.
    ///
    /// @param type the type declaring the field
    /// @param name the field name
    /// @return a static field access expression
    public static StaticFieldAccessExpr staticField(ClassOrInterfaceTypeRef type, String name) {
        return new StaticFieldAccessExpr(type, name);
    }

    /// Creates a static field access, e.g. `type.name`.
    ///
    /// @param type the type declaring the field
    /// @param name the field name
    /// @return a static field access expression
    public static StaticFieldAccessExpr staticField(ClassDesc type, String name) {
        return staticField(Types.of(type), name);
    }

    /// Creates an unqualified method call, e.g. `method(args)`.
    ///
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    public static MethodCallExpr call(String method, Expr... args) {
        return new MethodCallExpr(Optional.empty(), method, List.of(args));
    }

    /// Creates an unqualified method call, e.g. `method(args)`.
    ///
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    public static MethodCallExpr call(String method, List<Expr> args) {
        return new MethodCallExpr(Optional.empty(), method, List.copyOf(args));
    }

    /// Creates a static method call, e.g. `type.method(args)`.
    ///
    /// @param type the type declaring the method
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a static method call expression
    public static StaticMethodCallExpr staticCall(ClassOrInterfaceTypeRef type, String method, Expr... args) {
        return new StaticMethodCallExpr(type, method, List.of(args));
    }

    /// Creates a static method call, e.g. `type.method(args)`.
    ///
    /// @param type the type declaring the method
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a static method call expression
    public static StaticMethodCallExpr staticCall(ClassOrInterfaceTypeRef type, String method, List<Expr> args) {
        return new StaticMethodCallExpr(type, method, List.copyOf(args));
    }

    /// Creates a static method call, e.g. `type.method(args)`.
    ///
    /// @param type the type declaring the method
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a static method call expression
    public static StaticMethodCallExpr staticCall(ClassDesc type, String method, Expr... args) {
        return staticCall(Types.of(type), method, args);
    }

    /// Creates a static method call, e.g. `type.method(args)`.
    ///
    /// @param type the type declaring the method
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a static method call expression
    public static StaticMethodCallExpr staticCall(ClassDesc type, String method, List<Expr> args) {
        return staticCall(Types.of(type), method, args);
    }

    /// Creates an object creation expression, `new type(args)`.
    ///
    /// @param type the instantiated type
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public static NewExpr new_(ClassOrInterfaceTypeRef type, Expr... args) {
        return new NewExpr(new TypedNewTarget(type), List.of(args));
    }

    /// Creates an object creation expression, `new type(args)`.
    ///
    /// @param type the instantiated type
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public static NewExpr new_(ClassOrInterfaceTypeRef type, List<Expr> args) {
        return new NewExpr(new TypedNewTarget(type), List.copyOf(args));
    }

    /// Creates an object creation expression, `new type(args)`.
    ///
    /// @param type the instantiated type
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public static NewExpr new_(ClassDesc type, Expr... args) {
        return new_(Types.of(type), args);
    }

    /// Creates an object creation expression, `new type(args)`.
    ///
    /// @param type the instantiated type
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public static NewExpr new_(ClassDesc type, List<Expr> args) {
        return new_(Types.of(type), args);
    }

    /// Creates a diamond object creation expression, `new rawType<>(args)`,
    /// leaving the type arguments to be inferred.
    ///
    /// @param rawType the instantiated generic class, without type arguments
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public static NewExpr newDiamond(ClassDesc rawType, Expr... args) {
        return new NewExpr(new DiamondNewTarget(rawType), List.of(args));
    }

    /// Creates a diamond object creation expression, `new rawType<>(args)`,
    /// leaving the type arguments to be inferred.
    ///
    /// @param rawType the instantiated generic class, without type arguments
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public static NewExpr newDiamond(ClassDesc rawType, List<Expr> args) {
        return new NewExpr(new DiamondNewTarget(rawType), List.copyOf(args));
    }

    /// Creates an object creation expression with an anonymous class body,
    /// `new type(args) { ... }`.
    ///
    /// @param type the instantiated type
    /// @param args the constructor arguments, in order
    /// @param spec receives the builder to populate the anonymous class body
    /// @return a `new` expression
    public static NewExpr newAnonymous(
            ClassOrInterfaceTypeRef type, List<Expr> args, Consumer<AnonymousClassBuilder> spec) {
        AnonymousClassBuilder acb = new AnonymousClassBuilder();
        spec.accept(acb);
        return new NewExpr(new TypedNewTarget(type), List.copyOf(args), Optional.of(acb.build()));
    }

    /// Creates an array creation by dimension, e.g. `new componentType[dim1][dim2]...`.
    ///
    /// @param componentType the array's component type
    /// @param firstDimension the outermost dimension's size expression
    /// @param restDimensions any further dimensions' size expressions, outermost first
    /// @return an array creation expression
    public static ArrayCreationExpr newArray(TypeRef componentType, Expr firstDimension, Expr... restDimensions) {
        return new ArrayCreationExpr(componentType, new NonEmptyList<>(firstDimension, List.of(restDimensions)));
    }

    /// Creates an array creation with an initializer, e.g. `new componentType[]{e1, e2, ...}`.
    ///
    /// @param componentType the array's component type
    /// @param elements the initializer elements, in order
    /// @return an array initializer expression
    public static ArrayInitializerExpr newArrayOf(TypeRef componentType, Expr... elements) {
        return new ArrayInitializerExpr(componentType, List.of(elements));
    }

    /// Creates an array creation with an initializer, e.g. `new componentType[]{e1, e2, ...}`.
    ///
    /// @param componentType the array's component type
    /// @param elements the initializer elements, in order
    /// @return an array initializer expression
    public static ArrayInitializerExpr newArrayOf(TypeRef componentType, List<Expr> elements) {
        return new ArrayInitializerExpr(componentType, List.copyOf(elements));
    }

    /// Creates a class literal, `type.class`.
    ///
    /// @param type the referenced type
    /// @return a class literal expression
    public static ClassLiteralExpr classLiteral(TypeRef type) {
        return new ClassLiteralExpr(type);
    }

    /// Creates a class literal, `type.class`.
    ///
    /// @param type the referenced type
    /// @return a class literal expression
    public static ClassLiteralExpr classLiteral(ClassDesc type) {
        return classLiteral(Types.of(type));
    }

    /// Creates a type-qualified method reference, e.g. `Type::method`.
    ///
    /// @param type the qualifying type
    /// @param method the referenced method name
    /// @return a method reference expression
    public static MethodRefExpr methodRef(TypeRef type, String method) {
        return new MethodRefExpr(new TypeMethodRefTarget(type), method);
    }

    /// Creates a type-qualified method reference, e.g. `Type::method`.
    ///
    /// @param type the qualifying type
    /// @param method the referenced method name
    /// @return a method reference expression
    public static MethodRefExpr methodRef(ClassDesc type, String method) {
        return methodRef(Types.of(type), method);
    }

    /// Creates a constructor reference, e.g. `Type::new`.
    ///
    /// @param type the referenced type
    /// @return a constructor reference expression
    public static ConstructorRefExpr constructorRef(TypeRef type) {
        return new ConstructorRefExpr(type);
    }

    /// Creates a constructor reference, e.g. `Type::new`.
    ///
    /// @param type the referenced type
    /// @return a constructor reference expression
    public static ConstructorRefExpr constructorRef(ClassDesc type) {
        return constructorRef(Types.of(type));
    }

    /// Creates a cast expression, `(type) operand`.
    ///
    /// @param type the target type
    /// @param operand the cast operand
    /// @return a cast expression
    public static CastExpr cast(TypeRef type, Expr operand) {
        return new CastExpr(type, operand);
    }

    /// Creates a ternary conditional expression, `condition ? whenTrue : whenFalse`.
    ///
    /// @param condition the tested condition
    /// @param whenTrue the result when `condition` is true
    /// @param whenFalse the result when `condition` is false
    /// @return a conditional expression
    public static ConditionalExpr cond(Expr condition, Expr whenTrue, Expr whenFalse) {
        return new ConditionalExpr(condition, whenTrue, whenFalse);
    }

    /// Creates an addition expression, `left + right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr add(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.ADD, right);
    }

    /// Creates a subtraction expression, `left - right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr sub(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.SUB, right);
    }

    /// Creates a multiplication expression, `left * right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr mul(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.MUL, right);
    }

    /// Creates a division expression, `left / right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr div(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.DIV, right);
    }

    /// Creates a modulo expression, `left % right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr mod(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.MOD, right);
    }

    /// Creates an equality expression, `left == right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr eq(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.EQ, right);
    }

    /// Creates an inequality expression, `left != right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr neq(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.NEQ, right);
    }

    /// Creates a less-than expression, `left < right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr lt(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.LT, right);
    }

    /// Creates a less-than-or-equal expression, `left <= right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr le(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.LE, right);
    }

    /// Creates a greater-than expression, `left > right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr gt(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.GT, right);
    }

    /// Creates a greater-than-or-equal expression, `left >= right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr ge(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.GE, right);
    }

    /// Creates a logical AND expression, `left && right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr and(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.AND, right);
    }

    /// Creates a logical OR expression, `left || right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr or(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.OR, right);
    }

    /// Creates a bitwise/logical AND expression, `left & right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr bitAnd(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.BIT_AND, right);
    }

    /// Creates a bitwise/logical OR expression, `left | right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr bitOr(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.BIT_OR, right);
    }

    /// Creates a bitwise/logical XOR expression, `left ^ right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr bitXor(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.BIT_XOR, right);
    }

    /// Creates a left shift expression, `left << right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr shl(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.SHL, right);
    }

    /// Creates a signed right shift expression, `left >> right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr shr(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.SHR, right);
    }

    /// Creates an unsigned right shift expression, `left >>> right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public static Expr ushr(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.USHR, right);
    }

    /// Creates a logical negation expression, `!operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public static Expr not(Expr operand) {
        return new UnaryExpr(UnaryOp.NOT, operand);
    }

    /// Creates an arithmetic negation expression, `-operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public static Expr neg(Expr operand) {
        return new UnaryExpr(UnaryOp.NEG, operand);
    }

    /// Creates a bitwise complement expression, `~operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public static Expr bitNot(Expr operand) {
        return new UnaryExpr(UnaryOp.BIT_NOT, operand);
    }

    /// Creates a unary plus expression, `+operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public static Expr unaryPlus(Expr operand) {
        return new UnaryExpr(UnaryOp.UNARY_PLUS, operand);
    }

    /// Creates a pre-increment expression, `++operand`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public static IncDecExpr preIncrement(Expr operand) {
        return new IncDecExpr(IncDecOp.PRE_INC, operand);
    }

    /// Creates a pre-decrement expression, `--operand`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public static IncDecExpr preDecrement(Expr operand) {
        return new IncDecExpr(IncDecOp.PRE_DEC, operand);
    }

    /// Creates a post-increment expression, `operand++`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public static IncDecExpr postIncrement(Expr operand) {
        return new IncDecExpr(IncDecOp.POST_INC, operand);
    }

    /// Creates a post-decrement expression, `operand--`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public static IncDecExpr postDecrement(Expr operand) {
        return new IncDecExpr(IncDecOp.POST_DEC, operand);
    }

    /// Creates a `switch` expression.
    ///
    /// @param selector the switch selector expression
    /// @param spec receives the builder to populate the switch cases
    /// @return a switch expression
    public static Expr switchExpr(Expr selector, Consumer<SwitchBuilder> spec) {
        SwitchBuilder sb = new SwitchBuilder();
        spec.accept(sb);
        return new SwitchExpr(selector, sb.build());
    }

    /// Creates a lambda expression with inferred parameter types and a
    /// single-expression body, e.g. `(name) -> result`.
    ///
    /// @param params the parameter names, in order
    /// @param result the expression the lambda evaluates to
    /// @return a lambda expression
    public static Expr lambda(List<String> params, Expr result) {
        return new LambdaExpr(new InferredLambdaParams(params), new ExprLambdaBody(result));
    }

    /// Creates a lambda expression with inferred parameter types and a block
    /// body, e.g. `(name) -> { ... }`.
    ///
    /// @param params the parameter names, in order
    /// @param spec receives the builder to populate the lambda body
    /// @return a lambda expression
    public static Expr lambda(List<String> params, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        return new LambdaExpr(new InferredLambdaParams(params), new BlockLambdaBody(cb.build()));
    }

    /// Creates a lambda expression with explicitly typed parameters and a
    /// single-expression body, e.g. `(String name) -> result`.
    ///
    /// @param params the parameters, in order
    /// @param result the expression the lambda evaluates to
    /// @return a lambda expression
    public static Expr typedLambda(List<Param> params, Expr result) {
        return new LambdaExpr(new TypedLambdaParams(params), new ExprLambdaBody(result));
    }

    /// Creates a lambda expression with explicitly typed parameters and a
    /// block body, e.g. `(String name) -> { ... }`.
    ///
    /// @param params the parameters, in order
    /// @param spec receives the builder to populate the lambda body
    /// @return a lambda expression
    public static Expr typedLambda(List<Param> params, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        return new LambdaExpr(new TypedLambdaParams(params), new BlockLambdaBody(cb.build()));
    }
}
