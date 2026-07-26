package me.supcheg.javafile.render;

import me.supcheg.javafile.model.ExportsDirective;
import me.supcheg.javafile.model.ModuleDirective;
import me.supcheg.javafile.model.OpensDirective;
import me.supcheg.javafile.model.ProvidesDirective;
import me.supcheg.javafile.model.RequiresDirective;
import me.supcheg.javafile.model.UsesDirective;
import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.stream.Collectors;

final class ModuleDirectiveRenderer {

    private ModuleDirectiveRenderer() {}

    static String renderDirective(ModuleDirective directive) {
        return switch (directive) {
            case RequiresDirective(var name, var transitive, var isStatic) ->
                "requires " + (transitive ? "transitive " : "") + (isStatic ? "static " : "") + name + ";";
            case ExportsDirective(var packageName, var to) ->
                "exports " + packageName + (to.isEmpty() ? "" : " to " + String.join(", ", to)) + ";";
            case OpensDirective(var packageName, var to) ->
                "opens " + packageName + (to.isEmpty() ? "" : " to " + String.join(", ", to)) + ";";
            case UsesDirective(var service) -> "uses " + qualifiedName(service) + ";";
            case ProvidesDirective(var service, var implementations) ->
                "provides "
                        + qualifiedName(service)
                        + " with "
                        + implementations.toList().stream()
                                .map(ModuleDirectiveRenderer::qualifiedName)
                                .collect(Collectors.joining(", "))
                        + ";";
        };
    }

    private static String qualifiedName(ClassDesc desc) {
        String dotted = ClassDescNames.qualifiedByDots(desc);
        return desc.packageName().isEmpty() ? dotted : desc.packageName() + "." + dotted;
    }
}
