package me.supcheg.javafile.render;

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

    static String renderType(TypeRef ref, TypeContext ctx) {
        return switch (ref) {
            case ClassTypeRef(var desc) -> ctx.reference(desc);
            case ParameterizedTypeRef(var raw, var args) -> {
                String rawName = ctx.reference(raw);
                String argsStr = args.stream().map(a -> renderTypeArg(a, ctx)).collect(Collectors.joining(", "));
                yield rawName + "<" + argsStr + ">";
            }
            case TypeVarRef(var name) -> name;
            case ArrayTypeRef(var component) -> renderType(component, ctx) + "[]";
            case PrimitiveTypeRef p -> p.sourceName();
        };
    }

    static String renderTypeArg(TypeArg arg, TypeContext ctx) {
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
                        + " "
                        + p.name())
                .collect(Collectors.joining(", "));
    }

    static String renderTypeParams(List<TypeParam> typeParams, TypeContext ctx) {
        if (typeParams.isEmpty()) {
            return "";
        }
        return typeParams.stream().map(p -> renderTypeParam(p, ctx)).collect(Collectors.joining(", ", "<", ">"));
    }

    private static String renderTypeParam(TypeParam param, TypeContext ctx) {
        if (param.bounds().isEmpty()) {
            return param.name();
        }
        return param.name() + " extends "
                + param.bounds().stream().map(b -> renderType(b, ctx)).collect(Collectors.joining(" & "));
    }
}
