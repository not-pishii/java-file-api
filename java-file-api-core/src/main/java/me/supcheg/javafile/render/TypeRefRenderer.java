package me.supcheg.javafile.render;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/// Renders [TypeRef]s, [TypeArg]s, and [Modifier] sets to their Java source-code form.
final class TypeRefRenderer {

    private TypeRefRenderer() {}

    static String renderType(TypeRef ref, Context ctx) {
        return switch (ref) {
            case ClassTypeRef(var desc, var annotations) ->
                renderTypeAnnotations(annotations, ctx) + ctx.reference(desc);
            case ParameterizedTypeRef(var raw, var args, var annotations) -> {
                String rawName = ctx.reference(raw);
                String argsStr = args.stream().map(a -> renderTypeArg(a, ctx)).collect(Collectors.joining(", "));
                yield renderTypeAnnotations(annotations, ctx) + rawName + "<" + argsStr + ">";
            }
            case TypeVarRef(var name, var annotations) -> renderTypeAnnotations(annotations, ctx) + name;
            case ArrayTypeRef(var component, var annotations) -> {
                String renderedAnnotations = renderTypeAnnotations(annotations, ctx);
                yield renderType(component, ctx) + (renderedAnnotations.isEmpty() ? "" : " " + renderedAnnotations)
                        + "[]";
            }
            case PrimitiveTypeRef p -> p.sourceName();
        };
    }

    /// Renders type-use annotations (JLS 9.7.4) preceding the type they qualify,
    /// each followed by a trailing space; renders to an empty string when there
    /// are none, so callers never need a conditional.
    private static String renderTypeAnnotations(List<AnnotationUse> annotations, Context ctx) {
        StringBuilder sb = new StringBuilder();
        for (AnnotationUse use : annotations) {
            sb.append(AnnotationRenderer.renderUse(use, ctx)).append(' ');
        }
        return sb.toString();
    }

    static String renderTypeArg(TypeArg arg, Context ctx) {
        return switch (arg) {
            case ExactTypeArg(var type) -> renderType(type, ctx);
            case ExtendsTypeArg(var bound) -> "? extends " + renderType(bound, ctx);
            case SuperTypeArg(var bound) -> "? super " + renderType(bound, ctx);
            case UnboundedTypeArg ignored -> "?";
        };
    }

    static String renderModifiers(Set<Modifier> modifiers) {
        if (modifiers.isEmpty()) {
            return "";
        }
        return modifiers.stream()
                .sorted(Comparator.comparingInt(Enum::ordinal))
                .map(TypeRefRenderer::renderModifierKeyword)
                .collect(Collectors.joining(" ", "", " "));
    }

    private static String renderModifierKeyword(Modifier modifier) {
        if (modifier == Modifier.NON_SEALED) {
            return "non-sealed";
        }
        return modifier.name().toLowerCase(Locale.ROOT);
    }

    static String renderParams(List<Param> params, Context ctx) {
        return params.stream()
                .map(p -> AnnotationRenderer.renderInlineAnnotations(p.annotations(), ctx)
                        + renderType(p.type(), ctx)
                        + (p.varargs() ? "... " : " ")
                        + p.name())
                .collect(Collectors.joining(", "));
    }

    static String renderTypeParams(List<TypeParam> typeParams, Context ctx) {
        if (typeParams.isEmpty()) {
            return "";
        }
        return typeParams.stream().map(p -> renderTypeParam(p, ctx)).collect(Collectors.joining(", ", "<", ">"));
    }

    private static String renderTypeParam(TypeParam param, Context ctx) {
        String annotations = renderTypeAnnotations(param.annotations(), ctx);
        if (param.bounds().isEmpty()) {
            return annotations + param.name();
        }
        return annotations + param.name() + " extends "
                + param.bounds().stream().map(b -> renderType(b, ctx)).collect(Collectors.joining(" & "));
    }
}
