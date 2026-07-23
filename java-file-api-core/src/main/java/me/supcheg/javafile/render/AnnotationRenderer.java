package me.supcheg.javafile.render;

import me.supcheg.javafile.annotation.AnnotationMember;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValue;
import me.supcheg.javafile.annotation.ArrayValue;
import me.supcheg.javafile.annotation.ClassValue;
import me.supcheg.javafile.annotation.EnumValue;
import me.supcheg.javafile.annotation.LiteralValue;
import me.supcheg.javafile.annotation.NestedAnnotationValue;
import me.supcheg.javafile.annotation.SingleAnnotationValue;

import java.util.List;
import java.util.stream.Collectors;

/// Renders [AnnotationUse]s to their Java source-code form.
final class AnnotationRenderer {

    private AnnotationRenderer() {}

    /// Renders each annotation on its own padded line, e.g. for a declaration.
    /// Renders to an empty string for an empty list.
    ///
    /// @param annotations the annotations to render
    /// @param ctx the render context
    /// @return the rendered annotations, one per line
    static String renderAnnotations(List<AnnotationUse> annotations, Context ctx) {
        StringBuilder sb = new StringBuilder();
        for (AnnotationUse use : annotations) {
            sb.append(ctx.pad()).append(renderUse(use, ctx)).append(ctx.newline());
        }
        return sb.toString();
    }

    /// Renders annotations space-separated on a single line, each followed by a
    /// trailing space, e.g. for a parameter or record component. Renders to an
    /// empty string for an empty list.
    ///
    /// @param annotations the annotations to render
    /// @param ctx the render context
    /// @return the rendered annotations, space-separated with a trailing space
    static String renderInlineAnnotations(List<AnnotationUse> annotations, Context ctx) {
        StringBuilder sb = new StringBuilder();
        for (AnnotationUse use : annotations) {
            sb.append(renderUse(use, ctx)).append(' ');
        }
        return sb.toString();
    }

    static String renderUse(AnnotationUse use, Context ctx) {
        String name = "@" + ctx.reference(use.type());
        List<AnnotationMember> members = use.members();
        if (members.isEmpty()) {
            return name;
        }
        if (members.size() == 1 && members.get(0).name().equals("value")) {
            return name + "(" + renderValue(members.get(0).value(), ctx) + ")";
        }
        return name + "("
                + members.stream()
                        .map(m -> m.name() + " = " + renderValue(m.value(), ctx))
                        .collect(Collectors.joining(", "))
                + ")";
    }

    static String renderValue(AnnotationValue value, Context ctx) {
        return switch (value) {
            case LiteralValue(var literal) -> ExprRenderer.renderExpr(literal, ctx);
            case ClassValue(var type) -> ctx.reference(type) + ".class";
            case EnumValue(var enumType, var constant) -> ctx.reference(enumType) + "." + constant;
            case NestedAnnotationValue(var annotation) -> renderUse(annotation, ctx);
            case ArrayValue(var elements) -> renderArray(elements, ctx);
        };
    }

    private static String renderArray(List<SingleAnnotationValue> elements, Context ctx) {
        if (elements.isEmpty()) {
            return "{}";
        }
        Context innerCtx = ctx.withIncreasedPad();
        StringBuilder sb = new StringBuilder("{").append(ctx.newline());
        for (int i = 0; i < elements.size(); i++) {
            sb.append(innerCtx.pad()).append(renderValue(elements.get(i), innerCtx));
            if (i < elements.size() - 1) {
                sb.append(",");
            }
            sb.append(ctx.newline());
        }
        sb.append(ctx.pad()).append("}");
        return sb.toString();
    }
}
