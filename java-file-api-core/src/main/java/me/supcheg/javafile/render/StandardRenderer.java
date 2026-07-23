package me.supcheg.javafile.render;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.model.TypeDecl;

import java.util.List;

/// Renders a top-level type declaration to complete Java source code.
public final class StandardRenderer implements SourceRenderer {

    private static final StandardRenderer INSTANCE = new StandardRenderer();

    private StandardRenderer() {}

    public static StandardRenderer instance() {
        return INSTANCE;
    }

    /// Renders a package declaration, its computed imports, and the given type
    /// declaration into a single Java source file's text.
    ///
    /// @param packageName the file's package
    /// @param decl the top-level type declaration to render
    /// @return the complete source text
    @Override
    public String render(String packageName, TypeDecl decl, Format format) {
        var imports = new ImportManager(packageName);
        Context ctx = Context.of(format, imports);
        String body = TypeDeclRenderer.renderTypeDecl(decl, ctx);

        StringBuilder out = new StringBuilder();
        out.append("package ")
                .append(packageName)
                .append(";")
                .append(ctx.newline())
                .append(ctx.newline());

        List<String> sortedImports = imports.sortedImports();
        if (!sortedImports.isEmpty()) {
            for (String imp : sortedImports) {
                out.append("import ").append(imp).append(";").append(ctx.newline());
            }
            out.append(ctx.newline());
        }
        out.append(body);
        return out.toString();
    }

    /// Renders a `package-info.java` file: optional package annotations followed
    /// by the package declaration and its computed imports.
    ///
    /// @param packageName the package being annotated
    /// @param annotations the package annotations, in order
    /// @param format the indentation and line-separator preferences to render with
    /// @return the complete source text
    public String renderPackageInfo(String packageName, List<AnnotationUse> annotations, Format format) {
        var imports = new ImportManager(packageName);
        Context ctx = Context.of(format, imports);
        String annotationsText = AnnotationRenderer.renderAnnotations(annotations, ctx);

        StringBuilder out = new StringBuilder();
        out.append(annotationsText);
        out.append("package ").append(packageName).append(";").append(ctx.newline());

        List<String> sortedImports = imports.sortedImports();
        if (!sortedImports.isEmpty()) {
            out.append(ctx.newline());
            for (String imp : sortedImports) {
                out.append("import ").append(imp).append(";").append(ctx.newline());
            }
        }
        return out.toString();
    }
}
