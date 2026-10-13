package me.supcheg.javafile.render;

import me.supcheg.javafile.InheritedName;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.RenderableFile;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.ModuleDirective;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.StaticMethodDecl;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.TypeParam;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
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
        ImportManager.Known known = new ImportManager.Known(
                new LinkedHashSet<>(declared),
                typeVariables(meta.typeDecl()).map(TypeParam::name).collect(Collectors.toSet()),
                meta.inherited().stream()
                        .flatMap(name -> switch (name) {
                            case InheritedName.MemberType(String memberType) -> Stream.of(memberType);
                            case InheritedName.Field _ -> Stream.<String>empty();
                        })
                        .collect(Collectors.toSet()),
                meta.inherited().stream()
                        .flatMap(name -> switch (name) {
                            case InheritedName.Field(String field) -> Stream.of(field);
                            case InheritedName.MemberType _ -> Stream.<String>empty();
                        })
                        .collect(Collectors.toSet()));
        // a name written before the variable or the local class that takes it was met is written anew,
        // knowing what the rendering learned: see ImportManager. What a file declares is the same in
        // every rendering, so the second knows all of it.
        ImportManager learning = new ImportManager(meta.packageName(), known);
        String learned = TypeDeclRenderer.renderTypeDecl(meta.typeDecl(), Context.of(format, learning));
        ImportManager imports = learning.stale() ? new ImportManager(meta.packageName(), learning.learned()) : learning;
        Context ctx = Context.of(format, imports);
        String first = learning.stale() ? TypeDeclRenderer.renderTypeDecl(meta.typeDecl(), ctx) : learned;
        // how a comment names a type depends on what the code after it claims: see ImportManager
        String body = imports.mentioned() ? TypeDeclRenderer.renderTypeDecl(meta.typeDecl(), ctx) : first;

        StringBuilder out = new StringBuilder();
        // a compilation unit of the unnamed package has no package declaration (JLS 7.4.2)
        if (!meta.packageName().isEmpty()) {
            out.append("package ")
                    .append(meta.packageName())
                    .append(";")
                    .append(ctx.newline())
                    .append(ctx.newline());
        }

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
    static void collectDeclared(TypeDecl decl, List<ClassDesc> found) {
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

    /// The type parameters a file declares: those of `decl`, of its methods and of the types nested in
    /// it, at any depth. A method of an anonymous class is not looked into, nor is a body: a class
    /// declared in one tells its own as it is rendered ([ImportManager#localType]).
    static Stream<TypeParam> typeVariables(TypeDecl decl) {
        List<TypeParam> own =
                switch (decl) {
                    case ClassDecl c -> c.typeParams();
                    case InterfaceDecl i -> i.typeParams();
                    case RecordDecl r -> r.typeParams();
                    case EnumDecl _, AnnotationTypeDecl _ -> List.of();
                };
        List<?> members =
                switch (decl) {
                    case ClassDecl c -> c.members();
                    case InterfaceDecl i -> i.members();
                    case RecordDecl r -> r.members();
                    case EnumDecl e ->
                        Stream.concat(e.constants().stream().flatMap(c -> c.body().stream()), e.members().stream())
                                .toList();
                    case AnnotationTypeDecl _ -> List.of();
                };
        return Stream.concat(own.stream(), members.stream().flatMap(member -> switch (member) {
            case TypeDecl nested -> typeVariables(nested);
            case MethodDecl method -> method.typeParams().stream();
            case AbstractMethodDecl method -> method.typeParams().stream();
            case DefaultMethodDecl method -> method.typeParams().stream();
            case StaticMethodDecl method -> method.typeParams().stream();
            default -> Stream.<TypeParam>empty();
        }));
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
