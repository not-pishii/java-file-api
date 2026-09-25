package me.supcheg.javafile.render;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.RenderableFile;
import me.supcheg.javafile.model.ModuleDirective;

import java.util.List;

/// The built-in [SourceRenderer]. Adds imports automatically: a type is
/// imported unless another type with the same simple name already is, in
/// which case it is written fully qualified. `java.lang` and same-package
/// types are never imported.
public final class StandardRenderer implements SourceRenderer {

    private static final StandardRenderer INSTANCE = new StandardRenderer();

    private StandardRenderer() {}

    /// Returns the renderer.
    ///
    /// @return the shared instance
    public static StandardRenderer instance() {
        return INSTANCE;
    }

    @Override
    public String render(RenderableFile.Meta meta, Format format) {
        return switch (meta) {
            case JavaFile.Meta javaFile -> renderClassFile(javaFile, format);
            case ModuleFile.Meta moduleFile -> renderModuleInfo(moduleFile, format);
            case PackageInfoFile.Meta packageFile -> renderPackageInfo(packageFile, format);
        };
    }

    private String renderClassFile(JavaFile.Meta meta, Format format) {
        var imports = new ImportManager(meta.packageName());
        Context ctx = Context.of(format, imports);
        String body = TypeDeclRenderer.renderTypeDecl(meta.typeDecl(), ctx);

        StringBuilder out = new StringBuilder();
        out.append("package ")
                .append(meta.packageName())
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

    private String renderPackageInfo(PackageInfoFile.Meta meta, Format format) {
        var imports = new ImportManager(meta.packageName());
        Context ctx = Context.of(format, imports);
        String annotationsText = AnnotationRenderer.renderAnnotations(meta.annotations(), ctx);

        StringBuilder out = new StringBuilder();
        out.append(annotationsText);
        out.append("package ").append(meta.packageName()).append(";").append(ctx.newline());

        List<String> sortedImports = imports.sortedImports();
        if (!sortedImports.isEmpty()) {
            out.append(ctx.newline());
            for (String imp : sortedImports) {
                out.append("import ").append(imp).append(";").append(ctx.newline());
            }
        }
        return out.toString();
    }

    private String renderModuleInfo(ModuleFile.Meta meta, Format format) {
        StringBuilder sb = new StringBuilder();
        if (meta.open()) {
            sb.append("open ");
        }
        sb.append("module ").append(meta.moduleName()).append(" {").append(format.newline());
        var innerCtx = format.withIncreasedPad();
        for (ModuleDirective directive : meta.directives()) {
            sb.append(innerCtx.pad())
                    .append(ModuleDirectiveRenderer.renderDirective(directive))
                    .append(innerCtx.newline());
        }
        sb.append("}").append(format.newline());
        return sb.toString();
    }
}
