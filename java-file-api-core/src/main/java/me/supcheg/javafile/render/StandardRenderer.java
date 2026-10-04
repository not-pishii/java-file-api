package me.supcheg.javafile.render;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.RenderableFile;
import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.ModuleDirective;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.TypeDecl;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/// The built-in [SourceRenderer]. Adds imports automatically: a type is
/// imported unless another type with the same simple name already is, in
/// which case it is written fully qualified. `java.lang` and same-package
/// types are never imported.
///
/// A documentation comment is written in the syntax of the format
/// ([SourceRenderer.Format#docStyle()]) and adds no import: it names a type
/// by its simple name where the code of the file imports or declares the
/// type or the type needs no import, and by its qualified name otherwise.
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
        List<ClassDesc> declared = new ArrayList<>();
        collectDeclared(meta.typeDecl(), declared);
        var imports = new ImportManager(meta.packageName(), declared);
        Context ctx = Context.of(format, imports);
        String first = TypeDeclRenderer.renderTypeDecl(meta.typeDecl(), ctx);
        // how a comment names a type depends on what the code after it claims: see ImportManager
        String body = imports.mentioned() ? TypeDeclRenderer.renderTypeDecl(meta.typeDecl(), ctx) : first;

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

    /// The types a file declares: `decl` and the types nested in it, at any depth.
    private static void collectDeclared(TypeDecl decl, List<ClassDesc> found) {
        List<?> members =
                switch (decl) {
                    case ClassDecl c -> {
                        found.add(c.desc());
                        yield c.members();
                    }
                    case InterfaceDecl i -> {
                        found.add(i.desc());
                        yield i.members();
                    }
                    case RecordDecl r -> {
                        found.add(r.desc());
                        yield r.members();
                    }
                    case EnumDecl e -> {
                        found.add(e.desc());
                        yield Stream.concat(
                                        e.constants().stream().flatMap(c -> c.body().stream()), e.members().stream())
                                .toList();
                    }
                    case AnnotationTypeDecl a -> {
                        found.add(a.desc());
                        yield List.of();
                    }
                };
        for (Object member : members) {
            if (member instanceof TypeDecl nested) {
                collectDeclared(nested, found);
            }
        }
    }

    private String renderPackageInfo(PackageInfoFile.Meta meta, Format format) {
        var imports = new ImportManager(meta.packageName());
        Context ctx = Context.of(format, imports);
        String annotationsText = AnnotationRenderer.renderAnnotations(meta.annotations(), ctx);

        StringBuilder out = new StringBuilder();
        // after the annotations, whose types alone are imported: the comment names a type as they do
        out.append(DocRenderer.render(meta.doc(), ctx));
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
        // a module declaration has no imports: a comment names every type outside java.lang qualified
        StringBuilder sb = new StringBuilder(DocRenderer.render(meta.doc(), Context.of(format, new ImportManager(""))));
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
