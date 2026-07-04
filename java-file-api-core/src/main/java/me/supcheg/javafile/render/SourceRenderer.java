package me.supcheg.javafile.render;

import me.supcheg.javafile.model.TypeDecl;

import java.util.List;

/// Renders a top-level type declaration to complete Java source code.
public final class SourceRenderer {

    private SourceRenderer() {}

    /// Renders a package declaration, its computed imports, and the given type
    /// declaration into a single Java source file's text.
    ///
    /// @param packageName the file's package
    /// @param decl the top-level type declaration to render
    /// @return the complete source text
    public static String render(String packageName, TypeDecl decl) {
        ImportManager imports = new ImportManager(packageName);
        String body = TypeDeclRenderer.renderTypeDecl(decl, imports, 0);

        StringBuilder out = new StringBuilder();
        out.append("package ").append(packageName).append(";\n\n");

        List<String> sortedImports = imports.sortedImports();
        if (!sortedImports.isEmpty()) {
            for (String imp : sortedImports) {
                out.append("import ").append(imp).append(";\n");
            }
            out.append("\n");
        }
        out.append(body);
        return out.toString();
    }
}
