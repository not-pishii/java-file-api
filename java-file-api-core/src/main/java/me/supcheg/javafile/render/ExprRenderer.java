package me.supcheg.javafile.render;

import me.supcheg.javafile.code.AssignStmt;
import me.supcheg.javafile.code.BinaryExpr;
import me.supcheg.javafile.code.BlockCaseBody;
import me.supcheg.javafile.code.BlockLambdaBody;
import me.supcheg.javafile.code.BooleanLiteral;
import me.supcheg.javafile.code.BreakStmt;
import me.supcheg.javafile.code.CaseBody;
import me.supcheg.javafile.code.CaseLabel;
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
import me.supcheg.javafile.code.InferredLambdaParams;
import me.supcheg.javafile.code.InstanceOfExpr;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LambdaExpr;
import me.supcheg.javafile.code.LocalVarDeclStmt;
import me.supcheg.javafile.code.LongLiteral;
import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.NewExpr;
import me.supcheg.javafile.code.NullLiteral;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.code.StringLiteral;
import me.supcheg.javafile.code.SwitchCase;
import me.supcheg.javafile.code.SwitchExpr;
import me.supcheg.javafile.code.SwitchStmt;
import me.supcheg.javafile.code.TextBlockExpr;
import me.supcheg.javafile.code.ThrowCaseBody;
import me.supcheg.javafile.code.ThrowStmt;
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

    static String renderExpr(Expr expr, ImportManager imports, int indent) {
        return switch (expr) {
            case FieldAccessExpr(var target, var name) ->
                target.map(t -> renderExpr(t, imports, indent) + "." + name).orElse(name);
            case MethodCallExpr(var target, var method, var args) -> {
                String prefix =
                        target.map(t -> renderExpr(t, imports, indent) + ".").orElse("");
                String argsStr =
                        args.stream().map(a -> renderExpr(a, imports, indent)).collect(Collectors.joining(", "));
                yield prefix + method + "(" + argsStr + ")";
            }
            case StringLiteral(var value) -> "\"" + JavaStrings.escape(value) + "\"";
            case IntLiteral(var value) -> Integer.toString(value);
            case LongLiteral(var value) -> value + "L";
            case DoubleLiteral(var value) -> Double.toString(value);
            case BooleanLiteral(var value) -> Boolean.toString(value);
            case NullLiteral ignored -> "null";
            case TextBlockExpr(var value) -> renderTextBlock(value, indent);
            case BinaryExpr(var left, var op, var right) ->
                renderExpr(left, imports, indent) + " " + op.symbol() + " " + renderExpr(right, imports, indent);
            case UnaryExpr(var op, var operand) ->
                switch (op) {
                    case NOT -> "!" + renderExpr(operand, imports, indent);
                    case NEG -> "-" + renderExpr(operand, imports, indent);
                    case PRE_INC -> "++" + renderExpr(operand, imports, indent);
                    case PRE_DEC -> "--" + renderExpr(operand, imports, indent);
                    case POST_INC -> renderExpr(operand, imports, indent) + "++";
                    case POST_DEC -> renderExpr(operand, imports, indent) + "--";
                };
            case InstanceOfExpr(var target, var type, var bindingName) ->
                renderExpr(target, imports, indent)
                        + " instanceof "
                        + TypeRefRenderer.renderType(type, imports)
                        + bindingName.map(n -> " " + n).orElse("");
            case NewExpr(var target, var args) -> {
                String argsStr =
                        args.stream().map(a -> renderExpr(a, imports, indent)).collect(Collectors.joining(", "));
                String targetStr =
                        switch (target) {
                            case TypedNewTarget(var type) -> TypeRefRenderer.renderType(type, imports);
                            case DiamondNewTarget(var raw) -> imports.reference(raw) + "<>";
                        };
                yield "new " + targetStr + "(" + argsStr + ")";
            }
            case SwitchExpr(var selector, var cases) -> {
                String pad = "    ".repeat(indent);
                yield "switch ("
                        + renderExpr(selector, imports, indent)
                        + ") {\n"
                        + renderSwitchCases(cases, imports, indent + 1)
                        + pad
                        + "}";
            }
            case LambdaExpr(var params, var body) -> {
                String header =
                        switch (params) {
                                    case InferredLambdaParams(var names) -> "(" + String.join(", ", names) + ")";
                                    case TypedLambdaParams(var typed) ->
                                        "(" + TypeRefRenderer.renderParams(typed, imports) + ")";
                                }
                                + " -> ";
                yield switch (body) {
                    case ExprLambdaBody(var result) -> header + renderExpr(result, imports, indent);
                    case BlockLambdaBody(var block) -> {
                        String pad = "    ".repeat(indent);
                        yield header + "{\n" + renderBlock(block, imports, indent + 1) + pad + "}";
                    }
                };
            }
        };
    }

    static String renderStmt(Stmt stmt, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        return switch (stmt) {
            case ReturnStmt(var value) ->
                pad + "return"
                        + value.map(v -> " " + renderExpr(v, imports, indent)).orElse("") + ";";
            case ExprStmt(var expr) -> pad + renderExpr(expr, imports, indent) + ";";
            case AssignStmt(var target, var value) ->
                pad + renderExpr(target, imports, indent) + " = " + renderExpr(value, imports, indent) + ";";
            case LocalVarDeclStmt(var type, var name, var initializer) ->
                pad
                        + type.map(t -> TypeRefRenderer.renderType(t, imports)).orElse("var")
                        + " "
                        + name
                        + " = "
                        + renderExpr(initializer, imports, indent)
                        + ";";
            case IfStmt(var condition, var thenBody, var elseIfClauses, var elseBody) -> {
                StringBuilder sb = new StringBuilder(pad)
                        .append("if (")
                        .append(renderExpr(condition, imports, indent))
                        .append(") {\n")
                        .append(renderBlock(thenBody, imports, indent + 1))
                        .append(pad)
                        .append("}");
                for (ElseIfClause clause : elseIfClauses) {
                    sb.append(" else if (")
                            .append(renderExpr(clause.condition(), imports, indent))
                            .append(") {\n")
                            .append(renderBlock(clause.body(), imports, indent + 1))
                            .append(pad)
                            .append("}");
                }
                elseBody.ifPresent(b -> sb.append(" else {\n")
                        .append(renderBlock(b, imports, indent + 1))
                        .append(pad)
                        .append("}"));
                yield sb.toString();
            }
            case WhileStmt(var condition, var body) ->
                pad
                        + "while ("
                        + renderExpr(condition, imports, indent)
                        + ") {\n"
                        + renderBlock(body, imports, indent + 1)
                        + pad
                        + "}";
            case DoWhileStmt(var body, var condition) ->
                pad
                        + "do {\n"
                        + renderBlock(body, imports, indent + 1)
                        + pad
                        + "} while ("
                        + renderExpr(condition, imports, indent)
                        + ");";
            case ForStmt(var init, var condition, var update, var body) -> {
                String initStr = init.map(i -> stripTrailingSemicolon(renderStmt(i, imports, 0)))
                        .orElse("");
                String conditionStr =
                        condition.map(c -> renderExpr(c, imports, indent)).orElse("");
                String updateStr = update.map(u -> stripTrailingSemicolon(renderStmt(u, imports, 0)))
                        .orElse("");
                yield pad
                        + "for ("
                        + initStr
                        + "; "
                        + conditionStr
                        + "; "
                        + updateStr
                        + ") {\n"
                        + renderBlock(body, imports, indent + 1)
                        + pad
                        + "}";
            }
            case EnhancedForStmt(var elementType, var varName, var iterable, var body) ->
                pad
                        + "for ("
                        + TypeRefRenderer.renderType(elementType, imports)
                        + " "
                        + varName
                        + " : "
                        + renderExpr(iterable, imports, indent)
                        + ") {\n"
                        + renderBlock(body, imports, indent + 1)
                        + pad
                        + "}";
            case SwitchStmt(var selector, var cases) ->
                pad
                        + "switch ("
                        + renderExpr(selector, imports, indent)
                        + ") {\n"
                        + renderSwitchCases(cases, imports, indent + 1)
                        + pad
                        + "}";
            case YieldStmt(var value) -> pad + "yield " + renderExpr(value, imports, indent) + ";";
            case ThrowStmt(var exception) -> pad + "throw " + renderExpr(exception, imports, indent) + ";";
            case BreakStmt ignored -> pad + "break;";
            case ContinueStmt ignored -> pad + "continue;";
        };
    }

    static String renderBlock(CodeBody body, ImportManager imports, int indent) {
        return body.statements().stream()
                .map(s -> renderStmt(s, imports, indent) + "\n")
                .collect(Collectors.joining());
    }

    private static String renderSwitchCase(SwitchCase c, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        boolean isDefault = c.labels().size() == 1 && c.labels().get(0) instanceof DefaultLabel;
        String header = isDefault
                ? "default"
                : "case "
                        + c.labels().stream()
                                .map(l -> renderCaseLabel(l, imports, indent))
                                .collect(Collectors.joining(", "));
        return pad + header + " -> " + renderCaseBody(c.body(), imports, indent);
    }

    private static String renderCaseLabel(CaseLabel label, ImportManager imports, int indent) {
        return switch (label) {
            case ConstantLabel(var value) -> renderExpr(value, imports, indent);
            case TypePatternLabel(var type, var bindingName, var guard) ->
                TypeRefRenderer.renderType(type, imports)
                        + " "
                        + bindingName
                        + guard.map(g -> " when " + renderExpr(g, imports, indent))
                                .orElse("");
            case DefaultLabel ignored -> "default";
        };
    }

    private static String renderCaseBody(CaseBody body, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        return switch (body) {
            case ExprCaseBody(var expr) -> renderExpr(expr, imports, indent) + ";";
            case ThrowCaseBody(var exception) -> "throw " + renderExpr(exception, imports, indent) + ";";
            case BlockCaseBody(var block) -> "{\n" + renderBlock(block, imports, indent + 1) + pad + "}";
        };
    }

    private static String renderSwitchCases(List<SwitchCase> cases, ImportManager imports, int indent) {
        return cases.stream()
                .map(c -> renderSwitchCase(c, imports, indent) + "\n")
                .collect(Collectors.joining());
    }

    private static String renderTextBlock(String value, int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder("\"\"\"\n");
        for (String line : value.split("\n", -1)) {
            sb.append(pad).append(line).append("\n");
        }
        sb.append(pad).append("\"\"\"");
        return sb.toString();
    }

    private static String stripTrailingSemicolon(String rendered) {
        return rendered.endsWith(";") ? rendered.substring(0, rendered.length() - 1) : rendered;
    }
}
