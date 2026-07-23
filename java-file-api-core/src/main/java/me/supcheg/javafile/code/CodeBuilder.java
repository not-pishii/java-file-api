package me.supcheg.javafile.code;

import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeRef;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/// A mutable builder for a [CodeBody], accumulating statements in call order.
///
/// Each `Expr`-returning method constructs an expression value without
/// recording it; each `Stmt`-adding method (`return_`, `assign`, `if_`, ...)
/// appends to the body being built. [#build()] snapshots the accumulated
/// statements into an immutable [CodeBody], so a builder may be reused after
/// building.
///
/// Implements `Consumer<Stmt>` so that transforms and other producers can
/// feed pre-built statements directly via [#accept(Stmt)].
///
/// Instances are not thread-safe.
public final class CodeBuilder implements Consumer<Stmt> {

    private final List<Stmt> statements = new ArrayList<>();

    /// Appends the given statement to the body being built.
    ///
    /// @param stmt the statement to append
    @Override
    public void accept(Stmt stmt) {
        statements.add(stmt);
    }

    /// Appends a `return` statement with a value.
    ///
    /// @param value the returned expression
    /// @return this builder
    public CodeBuilder return_(Expr value) {
        statements.add(new ReturnStmt(Optional.of(value)));
        return this;
    }

    /// Appends a bare `return` statement with no value.
    ///
    /// @return this builder
    public CodeBuilder return_() {
        statements.add(new ReturnStmt(Optional.empty()));
        return this;
    }

    /// Appends a statement consisting of a bare expression evaluated for its
    /// side effects, e.g. a method call.
    ///
    /// @param expr the expression to evaluate
    /// @return this builder
    public CodeBuilder exprStatement(StatementExpr expr) {
        statements.add(new ExprStmt(expr));
        return this;
    }

    /// Appends a plain assignment statement, a convenient synonym for
    /// [#assign(AssignTarget,AssignOp,Expr)] with [AssignOp#ASSIGN].
    ///
    /// @param target the assignment target
    /// @param value the assigned expression
    /// @return this builder
    public CodeBuilder assign(AssignTarget target, Expr value) {
        return assign(target, AssignOp.ASSIGN, value);
    }

    /// Appends an assignment statement using the given operator, e.g. `target += value`.
    ///
    /// @param target the assignment target
    /// @param op the assignment operator
    /// @param value the assigned expression
    /// @return this builder
    public CodeBuilder assign(AssignTarget target, AssignOp op, Expr value) {
        statements.add(new AssignStmt(target, op, value));
        return this;
    }

    /// Creates an unqualified field access, e.g. `name`.
    ///
    /// @param name the field name
    /// @return a field access expression
    public FieldAccessExpr field(String name) {
        return new FieldAccessExpr(Optional.empty(), name);
    }

    /// Creates a field access qualified by a target expression, e.g. `target.name`.
    ///
    /// @param target the expression owning the field
    /// @param name the field name
    /// @return a field access expression
    public FieldAccessExpr field(Expr target, String name) {
        return new FieldAccessExpr(Optional.of(target), name);
    }

    /// Creates a static field access, e.g. `type.name`.
    ///
    /// @param type the type declaring the field
    /// @param name the field name
    /// @return a static field access expression
    public StaticFieldAccessExpr staticField(ClassOrInterfaceTypeRef type, String name) {
        return new StaticFieldAccessExpr(type, name);
    }

    /// Creates an array access, e.g. `array[index]`.
    ///
    /// @param array the accessed array expression
    /// @param index the index expression
    /// @return an array access expression
    public ArrayAccessExpr arrayAccess(Expr array, Expr index) {
        return new ArrayAccessExpr(array, index);
    }

    /// Creates an array creation by dimension, e.g. `new componentType[dim1][dim2]...`.
    ///
    /// @param componentType the array's component type
    /// @param firstDimension the outermost dimension's size expression
    /// @param restDimensions any further dimensions' size expressions, outermost first
    /// @return an array creation expression
    public ArrayCreationExpr newArray(TypeRef componentType, Expr firstDimension, Expr... restDimensions) {
        return new ArrayCreationExpr(componentType, new NonEmptyList<>(firstDimension, List.of(restDimensions)));
    }

    /// Creates an array creation with an initializer, e.g. `new componentType[]{e1, e2, ...}`.
    ///
    /// @param componentType the array's component type
    /// @param elements the initializer elements, in order
    /// @return an array initializer expression
    public ArrayInitializerExpr newArrayOf(TypeRef componentType, Expr... elements) {
        return new ArrayInitializerExpr(componentType, List.of(elements));
    }

    /// Creates an unqualified method call, e.g. `method(args)`.
    ///
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    public MethodCallExpr call(String method, Expr... args) {
        return new MethodCallExpr(Optional.empty(), method, List.of(args));
    }

    /// Creates a method call qualified by a target expression, e.g. `target.method(args)`.
    ///
    /// @param target the expression owning the method
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a method call expression
    public MethodCallExpr call(Expr target, String method, Expr... args) {
        return new MethodCallExpr(Optional.of(target), method, List.of(args));
    }

    /// Creates a static method call, e.g. `type.method(args)`.
    ///
    /// @param type the type declaring the method
    /// @param method the method name
    /// @param args the call arguments, in order
    /// @return a static method call expression
    public StaticMethodCallExpr callStatic(ClassOrInterfaceTypeRef type, String method, Expr... args) {
        return new StaticMethodCallExpr(type, method, List.of(args));
    }

    /// Creates the `this` expression, e.g. as the target of `field`/`call` to
    /// express `this.name`/`this.method(args)`.
    ///
    /// @return a `this` expression
    public ThisExpr this_() {
        return new ThisExpr();
    }

    /// Creates the `super` expression, e.g. as the target of `field`/`call` to
    /// express `super.name`/`super.method(args)`.
    ///
    /// @return a `super` expression
    public SuperExpr super_() {
        return new SuperExpr();
    }

    /// Creates a string literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public LiteralExpr literal(String value) {
        return new StringLiteral(value);
    }

    /// Creates an `int` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public LiteralExpr literal(int value) {
        return new IntLiteral(value);
    }

    /// Creates a `long` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public LiteralExpr literal(long value) {
        return new LongLiteral(value);
    }

    /// Creates a `double` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public LiteralExpr literal(double value) {
        return new DoubleLiteral(value);
    }

    /// Creates a `boolean` literal.
    ///
    /// @param value the literal value
    /// @return a literal expression
    public LiteralExpr literal(boolean value) {
        return new BooleanLiteral(value);
    }

    /// Creates the `null` literal.
    ///
    /// @return a literal expression
    public LiteralExpr literalNull() {
        return new NullLiteral();
    }

    /// Creates a text block literal, rendered with `"""` delimiters.
    ///
    /// @param value the text block content
    /// @return an expression
    public Expr textBlock(String value) {
        return new TextBlockExpr(value);
    }

    /// Creates an addition expression, `left + right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr add(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.ADD, right);
    }

    /// Creates a subtraction expression, `left - right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr sub(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.SUB, right);
    }

    /// Creates a multiplication expression, `left * right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr mul(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.MUL, right);
    }

    /// Creates a division expression, `left / right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr div(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.DIV, right);
    }

    /// Creates a modulo expression, `left % right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr mod(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.MOD, right);
    }

    /// Creates an equality expression, `left == right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr eq(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.EQ, right);
    }

    /// Creates an inequality expression, `left != right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr neq(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.NEQ, right);
    }

    /// Creates a less-than expression, `left < right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr lt(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.LT, right);
    }

    /// Creates a less-than-or-equal expression, `left <= right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr le(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.LE, right);
    }

    /// Creates a greater-than expression, `left > right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr gt(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.GT, right);
    }

    /// Creates a greater-than-or-equal expression, `left >= right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr ge(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.GE, right);
    }

    /// Creates a logical AND expression, `left && right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr and(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.AND, right);
    }

    /// Creates a logical OR expression, `left || right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr or(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.OR, right);
    }

    /// Creates a bitwise/logical AND expression, `left & right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr bitAnd(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.BIT_AND, right);
    }

    /// Creates a bitwise/logical OR expression, `left | right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr bitOr(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.BIT_OR, right);
    }

    /// Creates a bitwise/logical XOR expression, `left ^ right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr bitXor(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.BIT_XOR, right);
    }

    /// Creates a left shift expression, `left << right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr shl(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.SHL, right);
    }

    /// Creates a signed right shift expression, `left >> right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr shr(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.SHR, right);
    }

    /// Creates an unsigned right shift expression, `left >>> right`.
    ///
    /// @param left the left operand
    /// @param right the right operand
    /// @return a binary expression
    public Expr ushr(Expr left, Expr right) {
        return new BinaryExpr(left, BinaryOp.USHR, right);
    }

    /// Creates a logical negation expression, `!operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr not(Expr operand) {
        return new UnaryExpr(UnaryOp.NOT, operand);
    }

    /// Creates an arithmetic negation expression, `-operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr neg(Expr operand) {
        return new UnaryExpr(UnaryOp.NEG, operand);
    }

    /// Creates a bitwise complement expression, `~operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr bitNot(Expr operand) {
        return new UnaryExpr(UnaryOp.BIT_NOT, operand);
    }

    /// Creates a unary plus expression, `+operand`.
    ///
    /// @param operand the operand
    /// @return a unary expression
    public Expr unaryPlus(Expr operand) {
        return new UnaryExpr(UnaryOp.UNARY_PLUS, operand);
    }

    /// Creates a pre-increment expression, `++operand`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public IncDecExpr preIncrement(Expr operand) {
        return new IncDecExpr(IncDecOp.PRE_INC, operand);
    }

    /// Creates a pre-decrement expression, `--operand`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public IncDecExpr preDecrement(Expr operand) {
        return new IncDecExpr(IncDecOp.PRE_DEC, operand);
    }

    /// Creates a post-increment expression, `operand++`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public IncDecExpr postIncrement(Expr operand) {
        return new IncDecExpr(IncDecOp.POST_INC, operand);
    }

    /// Creates a post-decrement expression, `operand--`.
    ///
    /// @param operand the operand
    /// @return an increment/decrement expression
    public IncDecExpr postDecrement(Expr operand) {
        return new IncDecExpr(IncDecOp.POST_DEC, operand);
    }

    /// Creates a cast expression, `(type) operand`.
    ///
    /// @param type the target type
    /// @param operand the cast operand
    /// @return a cast expression
    public CastExpr cast(TypeRef type, Expr operand) {
        return new CastExpr(type, operand);
    }

    /// Creates a ternary conditional expression, `condition ? whenTrue : whenFalse`.
    ///
    /// @param condition the tested condition
    /// @param whenTrue the result when `condition` is true
    /// @param whenFalse the result when `condition` is false
    /// @return a conditional expression
    public ConditionalExpr cond(Expr condition, Expr whenTrue, Expr whenFalse) {
        return new ConditionalExpr(condition, whenTrue, whenFalse);
    }

    /// Creates a class literal, `type.class`.
    ///
    /// @param type the referenced type
    /// @return a class literal expression
    public ClassLiteralExpr classLiteral(TypeRef type) {
        return new ClassLiteralExpr(type);
    }

    /// Creates a type-qualified method reference, e.g. `Type::method`.
    ///
    /// @param type the qualifying type
    /// @param method the referenced method name
    /// @return a method reference expression
    public MethodRefExpr methodRef(TypeRef type, String method) {
        return new MethodRefExpr(new TypeMethodRefTarget(type), method);
    }

    /// Creates an instance-bound method reference, e.g. `expr::method`.
    ///
    /// @param instance the bound instance expression
    /// @param method the referenced method name
    /// @return a method reference expression
    public MethodRefExpr methodRef(Expr instance, String method) {
        return new MethodRefExpr(new ExprMethodRefTarget(instance), method);
    }

    /// Creates a constructor reference, e.g. `Type::new`.
    ///
    /// @param type the referenced type
    /// @return a constructor reference expression
    public ConstructorRefExpr constructorRef(TypeRef type) {
        return new ConstructorRefExpr(type);
    }

    /// Creates an `instanceof` test with no pattern binding, e.g. `target instanceof type`.
    ///
    /// @param target the tested expression
    /// @param type the tested type
    /// @return an `instanceof` expression
    public Expr instanceOf(Expr target, TypeRef type) {
        return new InstanceOfExpr(target, new TypePattern(type, Optional.empty()));
    }

    /// Creates an `instanceof` pattern match that binds the matched value to a name,
    /// e.g. `target instanceof type bindingName`.
    ///
    /// @param target the tested expression
    /// @param type the tested type
    /// @param bindingName the name bound to the matched value
    /// @return an `instanceof` expression
    public Expr instanceOf(Expr target, TypeRef type, String bindingName) {
        return new InstanceOfExpr(target, new TypePattern(type, Optional.of(bindingName)));
    }

    /// Creates an `instanceof` test against an arbitrary pattern, e.g. a
    /// [RecordPattern] deconstruction: `target instanceof Type(component, ...)`.
    ///
    /// @param target the tested expression
    /// @param pattern the matched pattern
    /// @return an `instanceof` expression
    public Expr instanceOfPattern(Expr target, Pattern pattern) {
        return new InstanceOfExpr(target, pattern);
    }

    /// Creates a flat type pattern, e.g. `Type bindingName`.
    ///
    /// @param type the matched type
    /// @param bindingName the name bound to the matched value
    /// @return a type pattern
    public Pattern typePattern(TypeRef type, String bindingName) {
        return new TypePattern(type, Optional.of(bindingName));
    }

    /// Creates a record deconstruction pattern, e.g. `Point(int x, int y)`.
    ///
    /// @param recordType the deconstructed record type
    /// @param componentPatterns the per-component patterns, in declaration order
    /// @return a record pattern
    public Pattern recordPattern(TypeRef recordType, Pattern... componentPatterns) {
        return new RecordPattern(recordType, List.of(componentPatterns));
    }

    /// Creates an object creation expression, `new type(args)`.
    ///
    /// @param type the instantiated type
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public NewExpr new_(TypeRef type, Expr... args) {
        return new NewExpr(new TypedNewTarget(type), List.of(args));
    }

    /// Creates a diamond object creation expression, `new rawType<>(args)`,
    /// leaving the type arguments to be inferred.
    ///
    /// @param rawType the instantiated generic class, without type arguments
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public NewExpr newDiamond(ClassDesc rawType, Expr... args) {
        return new NewExpr(new DiamondNewTarget(rawType), List.of(args));
    }

    /// Creates an object creation expression with an anonymous class body,
    /// `new type(args) { ... }`.
    ///
    /// @param type the instantiated type
    /// @param body the anonymous subclass's body members
    /// @param args the constructor arguments, in order
    /// @return a `new` expression
    public NewExpr newAnonymous(TypeRef type, List<me.supcheg.javafile.model.EnumConstantMember> body, Expr... args) {
        return new NewExpr(new TypedNewTarget(type), List.of(args), Optional.of(body));
    }

    /// Appends a typed local variable declaration with an initializer.
    ///
    /// @param name the variable name
    /// @param type the declared variable type
    /// @param initializer the initializer expression
    /// @return this builder
    public CodeBuilder localVar(String name, TypeRef type, Expr initializer) {
        statements.add(new LocalVarDeclStmt.Typed(type, name, Optional.of(initializer)));
        return this;
    }

    /// Appends a typed local variable declaration with no initializer.
    ///
    /// @param name the variable name
    /// @param type the declared variable type
    /// @return this builder
    public CodeBuilder localVar(String name, TypeRef type) {
        statements.add(new LocalVarDeclStmt.Typed(type, name, Optional.empty()));
        return this;
    }

    /// Appends an inferred (`var`) local variable declaration.
    ///
    /// @param name the variable name
    /// @param initializer the initializer expression
    /// @return this builder
    public CodeBuilder localVar(String name, Expr initializer) {
        statements.add(new LocalVarDeclStmt.Inferred(name, initializer));
        return this;
    }

    /// Appends an `if` statement, optionally followed by `else if` and `else` clauses.
    ///
    /// @param condition the `if` condition
    /// @param spec receives the builder to populate the `then`/`else if`/`else` bodies
    /// @return this builder
    public CodeBuilder if_(Expr condition, Consumer<IfBuilder> spec) {
        IfBuilder ib = new IfBuilder(condition);
        spec.accept(ib);
        statements.add(ib.build());
        return this;
    }

    /// Appends a `while` loop.
    ///
    /// @param condition the loop condition
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder while_(Expr condition, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new WhileStmt(condition, cb.build()));
        return this;
    }

    /// Appends a `do`-`while` loop.
    ///
    /// @param condition the loop condition, evaluated after the body
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder doWhile_(Expr condition, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new DoWhileStmt(cb.build(), condition));
        return this;
    }

    /// Appends a classic `for` loop. Any of `init`, `condition`, or `update` may be
    /// `null` to omit that clause.
    ///
    /// @param init the initializer statement, or `null` to omit it
    /// @param condition the loop condition, or `null` to omit it
    /// @param update the per-iteration update statement, or `null` to omit it
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder for_(
            @Nullable LocalVarDeclStmt init,
            @Nullable Expr condition,
            @Nullable Stmt update,
            Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new ForStmt(
                Optional.ofNullable(init), Optional.ofNullable(condition), Optional.ofNullable(update), cb.build()));
        return this;
    }

    /// Appends an enhanced `for` loop, e.g. `for (elementType varName : iterable)`.
    ///
    /// @param elementType the declared type of the loop variable
    /// @param varName the loop variable name
    /// @param iterable the iterated expression
    /// @param spec receives the builder to populate the loop body
    /// @return this builder
    public CodeBuilder forEach(TypeRef elementType, String varName, Expr iterable, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new EnhancedForStmt(elementType, varName, iterable, cb.build()));
        return this;
    }

    /// Appends a `switch` statement.
    ///
    /// @param selector the switch selector expression
    /// @param spec receives the builder to populate the switch cases
    /// @return this builder
    public CodeBuilder switch_(Expr selector, Consumer<SwitchBuilder> spec) {
        SwitchBuilder sb = new SwitchBuilder();
        spec.accept(sb);
        statements.add(new SwitchStmt(selector, sb.build()));
        return this;
    }

    /// Appends a `try` statement — `try`-`catch`, `try`-`finally`,
    /// `try`-`catch`-`finally`, or a try-with-resources variant of any of
    /// those.
    ///
    /// @param block receives the builder to populate the try block's body
    /// @param spec receives the builder to populate resources, `catch` clauses, and the `finally` block
    /// @return this builder
    /// @throws IllegalArgumentException if `spec` calls neither `catch_` nor `finally_`
    public CodeBuilder try_(Consumer<CodeBuilder> block, Consumer<TryBuilder> spec) {
        CodeBuilder blockBuilder = new CodeBuilder();
        block.accept(blockBuilder);
        TryBuilder tb = new TryBuilder();
        spec.accept(tb);
        statements.add(tb.build(blockBuilder.build()));
        return this;
    }

    /// Creates a `switch` expression.
    ///
    /// @param selector the switch selector expression
    /// @param spec receives the builder to populate the switch cases
    /// @return a switch expression
    public Expr switchExpr(Expr selector, Consumer<SwitchBuilder> spec) {
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
    public Expr lambda(List<String> params, Expr result) {
        return new LambdaExpr(new InferredLambdaParams(params), new ExprLambdaBody(result));
    }

    /// Creates a lambda expression with inferred parameter types and a block
    /// body, e.g. `(name) -> { ... }`.
    ///
    /// @param params the parameter names, in order
    /// @param spec receives the builder to populate the lambda body
    /// @return a lambda expression
    public Expr lambda(List<String> params, Consumer<CodeBuilder> spec) {
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
    public Expr typedLambda(List<Param> params, Expr result) {
        return new LambdaExpr(new TypedLambdaParams(params), new ExprLambdaBody(result));
    }

    /// Creates a lambda expression with explicitly typed parameters and a
    /// block body, e.g. `(String name) -> { ... }`.
    ///
    /// @param params the parameters, in order
    /// @param spec receives the builder to populate the lambda body
    /// @return a lambda expression
    public Expr typedLambda(List<Param> params, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        return new LambdaExpr(new TypedLambdaParams(params), new BlockLambdaBody(cb.build()));
    }

    /// Appends a `yield` statement, for use inside a `switch` expression's block case.
    ///
    /// @param value the yielded expression
    /// @return this builder
    public CodeBuilder yield_(Expr value) {
        statements.add(new YieldStmt(value));
        return this;
    }

    /// Appends a `throw` statement.
    ///
    /// @param exception the thrown expression
    /// @return this builder
    public CodeBuilder throw_(Expr exception) {
        statements.add(new ThrowStmt(exception));
        return this;
    }

    /// Appends a `break` statement.
    ///
    /// @return this builder
    public CodeBuilder break_() {
        statements.add(new BreakStmt(Optional.empty()));
        return this;
    }

    /// Appends a `break` statement targeting an enclosing [LabeledStmt].
    ///
    /// @param label the targeted label
    /// @return this builder
    public CodeBuilder break_(String label) {
        statements.add(new BreakStmt(Optional.of(label)));
        return this;
    }

    /// Appends a `continue` statement.
    ///
    /// @return this builder
    public CodeBuilder continue_() {
        statements.add(new ContinueStmt(Optional.empty()));
        return this;
    }

    /// Appends a `continue` statement targeting an enclosing [LabeledStmt].
    ///
    /// @param label the targeted label
    /// @return this builder
    public CodeBuilder continue_(String label) {
        statements.add(new ContinueStmt(Optional.of(label)));
        return this;
    }

    /// Appends a labeled statement, `label: statement`, wrapping exactly the
    /// single statement `spec` appends.
    ///
    /// @param label the statement's label
    /// @param spec receives the builder to append exactly one statement to label
    /// @return this builder
    /// @throws IllegalArgumentException if `spec` appends zero or more than one statement
    public CodeBuilder labeled(String label, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        List<Stmt> built = cb.build().statements();
        if (built.size() != 1) {
            throw new IllegalArgumentException(
                    "labeled statement must wrap exactly one statement, got " + built.size());
        }
        statements.add(new LabeledStmt(label, built.get(0)));
        return this;
    }

    /// Appends a `synchronized (lock) { ... }` block.
    ///
    /// @param lock the monitor expression
    /// @param spec receives the builder to populate the synchronized block's body
    /// @return this builder
    public CodeBuilder synchronized_(Expr lock, Consumer<CodeBuilder> spec) {
        CodeBuilder cb = new CodeBuilder();
        spec.accept(cb);
        statements.add(new SynchronizedStmt(lock, cb.build()));
        return this;
    }

    /// Appends an `assert` statement with no diagnostic message.
    ///
    /// @param condition the asserted condition
    /// @return this builder
    public CodeBuilder assert_(Expr condition) {
        statements.add(new AssertStmt(condition, Optional.empty()));
        return this;
    }

    /// Appends an `assert` statement with a diagnostic message.
    ///
    /// @param condition the asserted condition
    /// @param message the diagnostic message expression
    /// @return this builder
    public CodeBuilder assert_(Expr condition, Expr message) {
        statements.add(new AssertStmt(condition, Optional.of(message)));
        return this;
    }

    /// Appends the empty statement, `;`.
    ///
    /// @return this builder
    public CodeBuilder empty() {
        statements.add(new EmptyStmt());
        return this;
    }

    /// Appends a local class/interface/record/enum declaration used as a statement.
    ///
    /// @param decl the declared local type
    /// @return this builder
    public CodeBuilder localType(me.supcheg.javafile.model.TypeDecl decl) {
        statements.add(new LocalTypeDeclStmt(decl));
        return this;
    }

    /// Snapshots the accumulated statements into an immutable [CodeBody].
    ///
    /// @return the finished body
    public CodeBody build() {
        return new CodeBody(List.copyOf(statements));
    }
}
