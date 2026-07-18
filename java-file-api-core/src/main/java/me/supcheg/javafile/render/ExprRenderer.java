package me.supcheg.javafile.render;

import me.supcheg.javafile.code.AssignStmt;
import me.supcheg.javafile.code.BinaryExpr;
import me.supcheg.javafile.code.BlockCaseBody;
import me.supcheg.javafile.code.BlockLambdaBody;
import me.supcheg.javafile.code.BooleanLiteral;
import me.supcheg.javafile.code.BreakStmt;
import me.supcheg.javafile.code.CaseBody;
import me.supcheg.javafile.code.CaseLabel;
import me.supcheg.javafile.code.CatchClause;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstantLabel;
import me.supcheg.javafile.code.ContinueStmt;
import me.supcheg.javafile.code.DefaultLabel;
import me.supcheg.javafile.code.DiamondNewTarget;
import me.supcheg.javafile.code.DoWhileStmt;
import me.supcheg.javafile.code.DoubleLiteral;
import me.supcheg.javafile.code.ElseIfClause;
import me.supcheg.javafile.code.EnhancedForStmt;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.ExprCaseBody;
import me.supcheg.javafile.code.ExprLambdaBody;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.FieldAccessExpr;
import me.supcheg.javafile.code.ForStmt;
import me.supcheg.javafile.code.IfStmt;
import me.supcheg.javafile.code.IncDecExpr;
import me.supcheg.javafile.code.InferredLambdaParams;
import me.supcheg.javafile.code.InstanceOfExpr;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LabeledStmt;
import me.supcheg.javafile.code.LambdaExpr;
import me.supcheg.javafile.code.LocalVarDeclStmt;
import me.supcheg.javafile.code.LongLiteral;
import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.NewExpr;
import me.supcheg.javafile.code.NullLiteral;
import me.supcheg.javafile.code.Resource;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.code.StringLiteral;
import me.supcheg.javafile.code.SuperExpr;
import me.supcheg.javafile.code.SwitchCase;
import me.supcheg.javafile.code.SwitchExpr;
import me.supcheg.javafile.code.SwitchStmt;
import me.supcheg.javafile.code.TextBlockExpr;
import me.supcheg.javafile.code.ThisExpr;
import me.supcheg.javafile.code.ThrowCaseBody;
import me.supcheg.javafile.code.ThrowStmt;
import me.supcheg.javafile.code.TryStmt;
import me.supcheg.javafile.code.TypePatternLabel;
import me.supcheg.javafile.code.TypedLambdaParams;
import me.supcheg.javafile.code.TypedNewTarget;
import me.supcheg.javafile.code.UnaryExpr;
import me.supcheg.javafile.code.WhileStmt;
import me.supcheg.javafile.code.YieldStmt;

import java.util.List;
import java.util.stream.Collectors;

/// Renders [Expr]s and [Stmt]s to their Java source-code form.
final class ExprRenderer {

    private ExprRenderer() {}

    static String renderExpr(Expr expr, Context ctx) {
        return switch (expr) {
            case FieldAccessExpr(var target, var name) ->
                target.map(t -> renderExpr(t, ctx) + "." + name).orElse(name);
            case MethodCallExpr(var target, var method, var args) -> {
                String prefix = target.map(t -> renderExpr(t, ctx) + ".").orElse("");
                String argsStr = args.stream().map(a -> renderExpr(a, ctx)).collect(Collectors.joining(", "));
                yield prefix + method + "(" + argsStr + ")";
            }
            case StringLiteral(var value) -> "\"" + JavaStrings.escape(value) + "\"";
            case IntLiteral(var value) -> Integer.toString(value);
            case LongLiteral(var value) -> value + "L";
            case DoubleLiteral(var value) -> Double.toString(value);
            case BooleanLiteral(var value) -> Boolean.toString(value);
            case NullLiteral ignored -> "null";
            case TextBlockExpr(var value) -> renderTextBlock(value, ctx);
            case BinaryExpr(var left, var op, var right) ->
                renderExpr(left, ctx) + " " + op.symbol() + " " + renderExpr(right, ctx);
            case UnaryExpr(var op, var operand) ->
                switch (op) {
                    case NOT -> "!" + renderExpr(operand, ctx);
                    case NEG -> "-" + renderExpr(operand, ctx);
                };
            case IncDecExpr(var op, var operand) ->
                switch (op) {
                    case PRE_INC -> "++" + renderExpr(operand, ctx);
                    case PRE_DEC -> "--" + renderExpr(operand, ctx);
                    case POST_INC -> renderExpr(operand, ctx) + "++";
                    case POST_DEC -> renderExpr(operand, ctx) + "--";
                };
            case InstanceOfExpr(var target, var type, var bindingName) ->
                renderExpr(target, ctx)
                        + " instanceof "
                        + TypeRefRenderer.renderType(type, ctx)
                        + bindingName.map(n -> " " + n).orElse("");
            case NewExpr(var target, var args) -> {
                String argsStr = args.stream().map(a -> renderExpr(a, ctx)).collect(Collectors.joining(", "));
                String targetStr =
                        switch (target) {
                            case TypedNewTarget(var type) -> TypeRefRenderer.renderType(type, ctx);
                            case DiamondNewTarget(var raw) -> ctx.reference(raw) + "<>";
                        };
                yield "new " + targetStr + "(" + argsStr + ")";
            }
            case SwitchExpr(var selector, var cases) ->
                "switch ("
                        + renderExpr(selector, ctx)
                        + ") {" + ctx.newline()
                        + renderSwitchCases(cases, ctx.withIncreasedPad())
                        + ctx.pad()
                        + "}";
            case LambdaExpr(var params, var body) -> {
                String header =
                        switch (params) {
                                    case InferredLambdaParams(var names) -> "(" + String.join(", ", names) + ")";
                                    case TypedLambdaParams(var typed) ->
                                        "(" + TypeRefRenderer.renderParams(typed, ctx) + ")";
                                }
                                + " -> ";
                yield switch (body) {
                    case ExprLambdaBody(var result) -> header + renderExpr(result, ctx);
                    case BlockLambdaBody(var block) ->
                        header + "{" + ctx.newline() + renderBlock(block, ctx.withIncreasedPad()) + ctx.pad() + "}";
                };
            }
            case ThisExpr ignored -> "this";
            case SuperExpr ignored -> "super";
        };
    }

    static String renderStmt(Stmt stmt, Context ctx) {
        return switch (stmt) {
            case ReturnStmt(var value) ->
                ctx.pad() + "return" + value.map(v -> " " + renderExpr(v, ctx)).orElse("") + ";";
            case ExprStmt(var expr) -> ctx.pad() + renderExpr(expr, ctx) + ";";
            case AssignStmt(var target, var value) -> {
                String targetStr =
                        switch (target) {
                            case FieldAccessExpr fieldAccess -> renderExpr(fieldAccess, ctx);
                        };
                yield ctx.pad() + targetStr + " = " + renderExpr(value, ctx) + ";";
            }
            case LocalVarDeclStmt(var type, var name, var initializer) ->
                ctx.pad()
                        + type.map(t -> TypeRefRenderer.renderType(t, ctx)).orElse("var")
                        + " "
                        + name
                        + " = "
                        + renderExpr(initializer, ctx)
                        + ";";
            case IfStmt(var condition, var thenBody, var elseIfClauses, var elseBody) -> {
                StringBuilder sb = new StringBuilder(ctx.pad())
                        .append("if (")
                        .append(renderExpr(condition, ctx))
                        .append(") {")
                        .append(ctx.newline())
                        .append(renderBlock(thenBody, ctx.withIncreasedPad()))
                        .append(ctx.pad())
                        .append("}");
                for (ElseIfClause clause : elseIfClauses) {
                    sb.append(" else if (")
                            .append(renderExpr(clause.condition(), ctx))
                            .append(") {")
                            .append(ctx.newline())
                            .append(renderBlock(clause.body(), ctx.withIncreasedPad()))
                            .append(ctx.pad())
                            .append("}");
                }
                elseBody.ifPresent(b -> sb.append(" else {")
                        .append(ctx.newline())
                        .append(renderBlock(b, ctx.withIncreasedPad()))
                        .append(ctx.pad())
                        .append("}"));
                yield sb.toString();
            }
            case WhileStmt(var condition, var body) ->
                ctx.pad()
                        + "while ("
                        + renderExpr(condition, ctx)
                        + ") {" + ctx.newline()
                        + renderBlock(body, ctx.withIncreasedPad())
                        + ctx.pad()
                        + "}";
            case DoWhileStmt(var body, var condition) ->
                ctx.pad()
                        + "do {" + ctx.newline()
                        + renderBlock(body, ctx.withIncreasedPad())
                        + ctx.pad()
                        + "} while ("
                        + renderExpr(condition, ctx)
                        + ");";
            case ForStmt(var init, var condition, var update, var body) -> {
                String initStr = init.map(i -> stripTrailingSemicolon(renderStmt(i, ctx.withoutPad())))
                        .orElse("");
                String conditionStr = condition.map(c -> renderExpr(c, ctx)).orElse("");
                String updateStr = update.map(u -> stripTrailingSemicolon(renderStmt(u, ctx.withoutPad())))
                        .orElse("");
                yield ctx.pad()
                        + "for ("
                        + initStr
                        + "; "
                        + conditionStr
                        + "; "
                        + updateStr
                        + ") {" + ctx.newline()
                        + renderBlock(body, ctx.withIncreasedPad())
                        + ctx.pad()
                        + "}";
            }
            case EnhancedForStmt(var elementType, var varName, var iterable, var body) ->
                ctx.pad()
                        + "for ("
                        + TypeRefRenderer.renderType(elementType, ctx)
                        + " "
                        + varName
                        + " : "
                        + renderExpr(iterable, ctx)
                        + ") {" + ctx.newline()
                        + renderBlock(body, ctx.withIncreasedPad())
                        + ctx.pad()
                        + "}";
            case TryStmt.WithFinally(var resources, var block, var catches, var finallyBody) -> {
                StringBuilder sb = new StringBuilder(ctx.pad())
                        .append("try ")
                        .append(renderResources(resources, ctx))
                        .append("{")
                        .append(ctx.newline())
                        .append(renderBlock(block, ctx.withIncreasedPad()))
                        .append(ctx.pad())
                        .append("}");
                for (CatchClause clause : catches) {
                    sb.append(renderCatchClause(clause, ctx));
                }
                sb.append(" finally {")
                        .append(ctx.newline())
                        .append(renderBlock(finallyBody, ctx.withIncreasedPad()))
                        .append(ctx.pad())
                        .append("}");
                yield sb.toString();
            }
            case TryStmt.CatchOnly(var resources, var block, var catches) -> {
                StringBuilder sb = new StringBuilder(ctx.pad())
                        .append("try ")
                        .append(renderResources(resources, ctx))
                        .append("{")
                        .append(ctx.newline())
                        .append(renderBlock(block, ctx.withIncreasedPad()))
                        .append(ctx.pad())
                        .append("}");
                for (CatchClause clause : catches.toList()) {
                    sb.append(renderCatchClause(clause, ctx));
                }
                yield sb.toString();
            }
            case SwitchStmt(var selector, var cases) ->
                ctx.pad()
                        + "switch ("
                        + renderExpr(selector, ctx)
                        + ") {" + ctx.newline()
                        + renderSwitchCases(cases, ctx.withIncreasedPad())
                        + ctx.pad()
                        + "}";
            case YieldStmt(var value) -> ctx.pad() + "yield " + renderExpr(value, ctx) + ";";
            case ThrowStmt(var exception) -> ctx.pad() + "throw " + renderExpr(exception, ctx) + ";";
            case BreakStmt(var label) ->
                ctx.pad() + "break" + label.map(l -> " " + l).orElse("") + ";";
            case ContinueStmt(var label) ->
                ctx.pad() + "continue" + label.map(l -> " " + l).orElse("") + ";";
            case LabeledStmt(var label, var statement) ->
                ctx.pad() + label + ": "
                        + renderStmt(statement, ctx.withoutPad()).stripLeading();
        };
    }

    static String renderBlock(CodeBody body, Context ctx) {
        return body.statements().stream()
                .map(s -> renderStmt(s, ctx) + ctx.newline())
                .collect(Collectors.joining());
    }

    private static String renderResources(List<Resource> resources, Context ctx) {
        if (resources.isEmpty()) {
            return "";
        }
        String joined = resources.stream().map(r -> renderResource(r, ctx)).collect(Collectors.joining("; "));
        return "(" + joined + ") ";
    }

    private static String renderResource(Resource resource, Context ctx) {
        return switch (resource) {
            case Resource.Declared(var type, var name, var initializer) ->
                type.map(t -> TypeRefRenderer.renderType(t, ctx)).orElse("var")
                        + " "
                        + name
                        + " = "
                        + renderExpr(initializer, ctx);
            case Resource.Existing(var name) -> name;
        };
    }

    private static String renderCatchClause(CatchClause clause, Context ctx) {
        String types = clause.exceptionTypes().toList().stream()
                .map(t -> TypeRefRenderer.renderType(t, ctx))
                .collect(Collectors.joining(" | "));
        return " catch ("
                + types
                + " "
                + clause.paramName()
                + ") {" + ctx.newline()
                + renderBlock(clause.body(), ctx.withIncreasedPad())
                + ctx.pad()
                + "}";
    }

    private static String renderSwitchCase(SwitchCase c, Context ctx) {
        List<CaseLabel> labels = c.labels().toList();
        boolean isDefault = labels.size() == 1 && labels.get(0) instanceof DefaultLabel;
        String header = isDefault
                ? "default"
                : "case " + labels.stream().map(l -> renderCaseLabel(l, ctx)).collect(Collectors.joining(", "));
        return ctx.pad() + header + " -> " + renderCaseBody(c.body(), ctx);
    }

    private static String renderCaseLabel(CaseLabel label, Context ctx) {
        return switch (label) {
            case ConstantLabel(var value) -> renderExpr(value, ctx);
            case TypePatternLabel(var type, var bindingName, var guard) ->
                TypeRefRenderer.renderType(type, ctx)
                        + " "
                        + bindingName
                        + guard.map(g -> " when " + renderExpr(g, ctx)).orElse("");
            case DefaultLabel ignored -> "default";
        };
    }

    private static String renderCaseBody(CaseBody body, Context ctx) {
        return switch (body) {
            case ExprCaseBody(var expr) -> renderExpr(expr, ctx) + ";";
            case ThrowCaseBody(var exception) -> "throw " + renderExpr(exception, ctx) + ";";
            case BlockCaseBody(var block) ->
                "{" + ctx.newline() + renderBlock(block, ctx.withIncreasedPad()) + ctx.pad() + "}";
        };
    }

    private static String renderSwitchCases(List<SwitchCase> cases, Context ctx) {
        return cases.stream().map(c -> renderSwitchCase(c, ctx) + ctx.newline()).collect(Collectors.joining());
    }

    private static String renderTextBlock(String value, Context ctx) {
        StringBuilder sb = new StringBuilder("\"\"\"" + ctx.newline());
        for (String line : value.split("\n", -1)) {
            sb.append(ctx.pad()).append(line).append(ctx.newline());
        }
        sb.append(ctx.pad()).append("\"\"\"");
        return sb.toString();
    }

    private static String stripTrailingSemicolon(String rendered) {
        return rendered.endsWith(";") ? rendered.substring(0, rendered.length() - 1) : rendered;
    }
}
