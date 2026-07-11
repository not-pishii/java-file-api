package me.supcheg.javafile.render;

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
}
