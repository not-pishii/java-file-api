package me.supcheg.javafile.render;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.model.EnumConstructorDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.EnumMember;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.InterfaceMember;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.RecordMember;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.model.StaticMethodDecl;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;

import java.util.List;
import java.util.stream.Collectors;

/// Renders a [TypeDecl] and its members to their Java source-code form.
final class TypeDeclRenderer {

    private TypeDeclRenderer() {}

    static String renderTypeDecl(TypeDecl decl, Context ctx) {
        return switch (decl) {
            case ClassDecl c -> renderClass(c, ctx);
            case InterfaceDecl i -> renderInterface(i, ctx);
            case RecordDecl r -> renderRecord(r, ctx);
            case EnumDecl e -> renderEnum(e, ctx);
        };
    }

    private static String renderClass(ClassDecl decl, Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderAnnotations(decl.annotations(), ctx));
        sb.append(ctx.pad());
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()));
        if (!decl.permits().isEmpty()) {
            sb.append("sealed ");
        }
        sb.append("class ").append(decl.desc().displayName());
        sb.append(TypeRefRenderer.renderTypeParams(decl.typeParams(), ctx));
        decl.superclass().ifPresent(sc -> sb.append(" extends ").append(TypeRefRenderer.renderType(sc, ctx)));
        if (!decl.interfaces().isEmpty()) {
            sb.append(" implements ")
                    .append(decl.interfaces().stream()
                            .map(i -> TypeRefRenderer.renderType(i, ctx))
                            .collect(Collectors.joining(", ")));
        }
        if (!decl.permits().isEmpty()) {
            sb.append(" permits ")
                    .append(decl.permits().stream().map(ctx::reference).collect(Collectors.joining(", ")));
        }
        sb.append(" {").append(ctx.newline());
        sb.append(renderClassMembers(
                decl.members(), ctx.withIncreasedPad(), decl.desc().displayName()));
        sb.append(ctx.pad()).append("}").append(ctx.newline());
        return sb.toString();
    }

    private static String renderEnumMembers(List<EnumMember> members, Context ctx, String ownerSimpleName) {
        return members.stream()
                .map(member -> switch (member) {
                    case FieldDecl f -> renderField(f, ctx);
                    case MethodDecl m -> renderMethod(m, ctx);
                    case EnumConstructorDecl c -> renderEnumConstructor(c, ctx, ownerSimpleName);
                    case AbstractMethodDecl a -> renderAbstractMethodInClass(a, ctx);
                    case TypeDecl t -> renderTypeDecl(t, ctx);
                })
                .collect(Collectors.joining(ctx.newline()));
    }

    private static String renderClassMembers(List<ClassMember> members, Context ctx, String ownerSimpleName) {
        return members.stream()
                .map(member -> switch (member) {
                    case FieldDecl f -> renderField(f, ctx);
                    case MethodDecl m -> renderMethod(m, ctx);
                    case ConstructorDecl c -> renderConstructor(c, ctx, ownerSimpleName);
                    case AbstractMethodDecl a -> renderAbstractMethodInClass(a, ctx);
                    case TypeDecl t -> renderTypeDecl(t, ctx);
                })
                .collect(Collectors.joining(ctx.newline()));
    }

    private static String renderAbstractMethodInClass(AbstractMethodDecl m, Context ctx) {
        String returnType =
                m.returnType().map(t -> TypeRefRenderer.renderType(t, ctx)).orElse("void");
        String typeParams = TypeRefRenderer.renderTypeParams(m.typeParams(), ctx);
        return AnnotationRenderer.renderAnnotations(m.annotations(), ctx)
                + ctx.pad()
                + TypeRefRenderer.renderModifiers(m.modifiers())
                + (typeParams.isEmpty() ? "" : typeParams + " ")
                + returnType
                + " "
                + m.name()
                + "("
                + TypeRefRenderer.renderParams(m.params(), ctx)
                + ");" + ctx.newline();
    }

    private static String renderField(FieldDecl f, Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderAnnotations(f.annotations(), ctx));
        sb.append(ctx.pad());
        sb.append(TypeRefRenderer.renderModifiers(f.modifiers()));
        sb.append(TypeRefRenderer.renderType(f.type(), ctx)).append(' ').append(f.name());
        f.initializer().ifPresent(init -> sb.append(" = ").append(ExprRenderer.renderExpr(init, ctx)));
        sb.append(";").append(ctx.newline());
        return sb.toString();
    }

    private static String renderMethod(MethodDecl m, Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderAnnotations(m.annotations(), ctx));
        sb.append(ctx.pad());
        sb.append(TypeRefRenderer.renderModifiers(m.modifiers()));
        String typeParams = TypeRefRenderer.renderTypeParams(m.typeParams(), ctx);
        if (!typeParams.isEmpty()) {
            sb.append(typeParams).append(' ');
        }
        sb.append(m.returnType().map(t -> TypeRefRenderer.renderType(t, ctx)).orElse("void"))
                .append(' ');
        sb.append(m.name())
                .append('(')
                .append(TypeRefRenderer.renderParams(m.params(), ctx))
                .append(')')
                .append(renderThrows(m.throwsTypes(), ctx))
                .append(" {")
                .append(ctx.newline());
        sb.append(renderStatements(m.body().statements(), ctx.withIncreasedPad()));
        sb.append(ctx.pad()).append("}").append(ctx.newline());
        return sb.toString();
    }

    private static String renderConstructor(ConstructorDecl c, Context ctx, String ownerSimpleName) {
        return AnnotationRenderer.renderAnnotations(c.annotations(), ctx)
                + ctx.pad() + TypeRefRenderer.renderModifiers(c.modifiers()) + ownerSimpleName
                + '('
                + TypeRefRenderer.renderParams(c.params(), ctx)
                + ')'
                + renderThrows(c.throwsTypes(), ctx)
                + " {" + ctx.newline()
                + renderStatements(c.body().statements(), ctx.withIncreasedPad())
                + ctx.pad()
                + "}" + ctx.newline();
    }

    private static String renderEnumConstructor(EnumConstructorDecl c, Context ctx, String ownerSimpleName) {
        return AnnotationRenderer.renderAnnotations(c.annotations(), ctx)
                + ctx.pad() + ownerSimpleName + '('
                + TypeRefRenderer.renderParams(c.params(), ctx)
                + ')'
                + renderThrows(c.throwsTypes(), ctx)
                + " {" + ctx.newline()
                + renderStatements(c.body().statements(), ctx.withIncreasedPad())
                + ctx.pad()
                + "}" + ctx.newline();
    }

    private static String renderInterface(InterfaceDecl decl, Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderAnnotations(decl.annotations(), ctx));
        sb.append(ctx.pad());
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()));
        if (!decl.permits().isEmpty()) {
            sb.append("sealed ");
        }
        sb.append("interface ").append(decl.desc().displayName());
        sb.append(TypeRefRenderer.renderTypeParams(decl.typeParams(), ctx));
        if (!decl.extendsInterfaces().isEmpty()) {
            sb.append(" extends ")
                    .append(decl.extendsInterfaces().stream()
                            .map(i -> TypeRefRenderer.renderType(i, ctx))
                            .collect(Collectors.joining(", ")));
        }
        if (!decl.permits().isEmpty()) {
            sb.append(" permits ")
                    .append(decl.permits().stream().map(ctx::reference).collect(Collectors.joining(", ")));
        }
        sb.append(" {").append(ctx.newline());
        sb.append(renderInterfaceMembers(decl.members(), ctx.withIncreasedPad()));
        sb.append(ctx.pad()).append("}").append(ctx.newline());
        return sb.toString();
    }

    private static String renderInterfaceMembers(List<InterfaceMember> members, Context ctx) {
        return members.stream()
                .map(member -> switch (member) {
                    case AbstractMethodDecl a -> renderAbstractMethod(a, ctx);
                    case DefaultMethodDecl d ->
                        renderDefaultOrStaticMethod(
                                d.name(),
                                d.returnType(),
                                d.annotations(),
                                d.typeParams(),
                                d.params(),
                                d.body(),
                                d.throwsTypes(),
                                "default",
                                ctx);
                    case StaticMethodDecl s ->
                        renderDefaultOrStaticMethod(
                                s.name(),
                                s.returnType(),
                                s.annotations(),
                                s.typeParams(),
                                s.params(),
                                s.body(),
                                s.throwsTypes(),
                                "static",
                                ctx);
                    case ConstantDecl c -> renderConstant(c, ctx);
                    case TypeDecl t -> renderTypeDecl(t, ctx);
                })
                .collect(Collectors.joining(ctx.newline()));
    }

    private static String renderAbstractMethod(AbstractMethodDecl m, Context ctx) {
        String typeParams = TypeRefRenderer.renderTypeParams(m.typeParams(), ctx);
        String returnType =
                m.returnType().map(t -> TypeRefRenderer.renderType(t, ctx)).orElse("void");
        return AnnotationRenderer.renderAnnotations(m.annotations(), ctx)
                + ctx.pad()
                + (typeParams.isEmpty() ? "" : typeParams + " ")
                + returnType
                + " "
                + m.name()
                + "("
                + TypeRefRenderer.renderParams(m.params(), ctx)
                + ")"
                + renderThrows(m.throwsTypes(), ctx)
                + ";" + ctx.newline();
    }

    private static String renderDefaultOrStaticMethod(
            String name,
            java.util.Optional<me.supcheg.javafile.type.TypeRef> returnType,
            List<AnnotationUse> annotations,
            List<TypeParam> typeParams,
            List<Param> params,
            me.supcheg.javafile.code.CodeBody body,
            List<ClassOrInterfaceTypeRef> throwsTypes,
            String keyword,
            Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderAnnotations(annotations, ctx));
        sb.append(ctx.pad());
        sb.append(keyword).append(' ');
        String renderedTypeParams = TypeRefRenderer.renderTypeParams(typeParams, ctx);
        if (!renderedTypeParams.isEmpty()) {
            sb.append(renderedTypeParams).append(' ');
        }
        sb.append(returnType.map(t -> TypeRefRenderer.renderType(t, ctx)).orElse("void"))
                .append(' ');
        sb.append(name)
                .append('(')
                .append(TypeRefRenderer.renderParams(params, ctx))
                .append(')')
                .append(renderThrows(throwsTypes, ctx))
                .append(" {")
                .append(ctx.newline());
        sb.append(renderStatements(body.statements(), ctx.withIncreasedPad()));
        sb.append(ctx.pad()).append("}").append(ctx.newline());
        return sb.toString();
    }

    private static String renderThrows(List<ClassOrInterfaceTypeRef> throwsTypes, TypeContext ctx) {
        if (throwsTypes.isEmpty()) {
            return "";
        }
        return " throws "
                + throwsTypes.stream()
                        .map(t -> TypeRefRenderer.renderType(t, ctx))
                        .collect(Collectors.joining(", "));
    }

    private static String renderConstant(ConstantDecl c, Context ctx) {
        return AnnotationRenderer.renderAnnotations(c.annotations(), ctx)
                + ctx.pad()
                + TypeRefRenderer.renderType(c.type(), ctx)
                + " "
                + c.name()
                + " = "
                + ExprRenderer.renderExpr(c.initializer(), ctx)
                + ";" + ctx.newline();
    }

    private static String renderRecord(RecordDecl decl, Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderAnnotations(decl.annotations(), ctx));
        sb.append(ctx.pad());
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()))
                .append("record ")
                .append(decl.desc().displayName());
        sb.append(TypeRefRenderer.renderTypeParams(decl.typeParams(), ctx));
        sb.append('(')
                .append(decl.components().stream()
                        .map(c -> AnnotationRenderer.renderInlineAnnotations(c.annotations(), ctx)
                                + TypeRefRenderer.renderType(c.type(), ctx) + " " + c.name())
                        .collect(Collectors.joining(", ")))
                .append(')');
        if (!decl.interfaces().isEmpty()) {
            sb.append(" implements ")
                    .append(decl.interfaces().stream()
                            .map(i -> TypeRefRenderer.renderType(i, ctx))
                            .collect(Collectors.joining(", ")));
        }
        sb.append(" {").append(ctx.newline());
        sb.append(renderRecordMembers(
                decl.members(), ctx.withIncreasedPad(), decl.desc().displayName()));
        sb.append(ctx.pad()).append("}").append(ctx.newline());
        return sb.toString();
    }

    private static String renderRecordMembers(List<RecordMember> members, Context ctx, String ownerSimpleName) {
        return members.stream()
                .map(member -> switch (member) {
                    case CompactConstructorDecl cc -> renderCompactConstructor(cc, ctx, ownerSimpleName);
                    case MethodDecl m -> renderMethod(m, ctx);
                    case StaticFieldDecl sf -> renderStaticField(sf, ctx);
                    case TypeDecl t -> renderTypeDecl(t, ctx);
                })
                .collect(Collectors.joining(ctx.newline()));
    }

    private static String renderCompactConstructor(CompactConstructorDecl cc, Context ctx, String ownerSimpleName) {
        return AnnotationRenderer.renderAnnotations(cc.annotations(), ctx)
                + ctx.pad() + TypeRefRenderer.renderModifiers(cc.modifiers()) + ownerSimpleName
                + renderThrows(cc.throwsTypes(), ctx)
                + " {"
                + ctx.newline()
                + renderStatements(cc.body().statements(), ctx.withIncreasedPad())
                + ctx.pad()
                + "}" + ctx.newline();
    }

    private static String renderStaticField(StaticFieldDecl sf, Context ctx) {
        return AnnotationRenderer.renderAnnotations(sf.annotations(), ctx)
                + ctx.pad()
                + "public static final "
                + TypeRefRenderer.renderType(sf.type(), ctx)
                + " "
                + sf.name()
                + " = "
                + ExprRenderer.renderExpr(sf.initializer(), ctx)
                + ";" + ctx.newline();
    }

    private static String renderEnum(EnumDecl decl, Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderAnnotations(decl.annotations(), ctx));
        sb.append(ctx.pad());
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()))
                .append("enum ")
                .append(decl.desc().displayName());
        if (!decl.interfaces().isEmpty()) {
            sb.append(" implements ")
                    .append(decl.interfaces().stream()
                            .map(i -> TypeRefRenderer.renderType(i, ctx))
                            .collect(Collectors.joining(", ")));
        }
        sb.append(" {").append(ctx.newline());
        Context inner = ctx.withIncreasedPad();
        if (!decl.constants().isEmpty() || !decl.members().isEmpty()) {
            sb.append(inner.pad())
                    .append(decl.constants().stream()
                            .map(c -> renderEnumConstant(c, inner))
                            .collect(Collectors.joining(", ")))
                    .append(";")
                    .append(ctx.newline());
        }
        if (!decl.members().isEmpty()) {
            sb.append(ctx.newline());
            sb.append(renderEnumMembers(decl.members(), inner, decl.desc().displayName()));
        }
        sb.append(ctx.pad()).append("}").append(ctx.newline());
        return sb.toString();
    }

    private static String renderEnumConstant(EnumConstant constant, Context ctx) {
        StringBuilder sb = new StringBuilder(AnnotationRenderer.renderInlineAnnotations(constant.annotations(), ctx));
        sb.append(constant.name());
        if (!constant.args().isEmpty() || !constant.body().isEmpty()) {
            String argsStr = constant.args().stream()
                    .map(a -> ExprRenderer.renderExpr(a, ctx))
                    .collect(Collectors.joining(", "));
            sb.append('(').append(argsStr).append(')');
        }
        if (!constant.body().isEmpty()) {
            sb.append(" {").append(ctx.newline());
            sb.append(renderEnumConstantMembers(constant.body(), ctx.withIncreasedPad()));
            sb.append(ctx.pad()).append('}');
        }
        return sb.toString();
    }

    static String renderEnumConstantMembers(List<EnumConstantMember> members, Context ctx) {
        return members.stream()
                .map(member -> switch (member) {
                    case FieldDecl f -> renderField(f, ctx);
                    case MethodDecl m -> renderMethod(m, ctx);
                    case TypeDecl t -> renderTypeDecl(t, ctx);
                })
                .collect(Collectors.joining(ctx.newline()));
    }

    private static String renderStatements(List<me.supcheg.javafile.code.Stmt> statements, Context ctx) {
        return ExprRenderer.renderBlock(new me.supcheg.javafile.code.CodeBody(statements), ctx);
    }
}
