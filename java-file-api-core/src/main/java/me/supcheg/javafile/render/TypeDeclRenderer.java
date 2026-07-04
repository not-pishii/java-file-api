package me.supcheg.javafile.render;

import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumDecl;
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

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.stream.Collectors;

/// Renders a [TypeDecl] and its members to their Java source-code form.
final class TypeDeclRenderer {

    private TypeDeclRenderer() {}

    static String renderTypeDecl(TypeDecl decl, ImportManager imports, int indent) {
        return switch (decl) {
            case ClassDecl c -> renderClass(c, imports, indent);
            case InterfaceDecl i -> renderInterface(i, imports, indent);
            case RecordDecl r -> renderRecord(r, imports, indent);
            case EnumDecl e -> renderEnum(e, imports, indent);
        };
    }

    private static String renderClass(ClassDecl decl, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()));
        if (!decl.permits().isEmpty()) {
            sb.append("sealed ");
        }
        sb.append("class ").append(decl.desc().displayName());
        decl.superclass().ifPresent(sc -> sb.append(" extends ").append(imports.reference(sc)));
        if (!decl.interfaces().isEmpty()) {
            sb.append(" implements ")
                    .append(decl.interfaces().stream().map(imports::reference).collect(Collectors.joining(", ")));
        }
        if (!decl.permits().isEmpty()) {
            sb.append(" permits ")
                    .append(decl.permits().stream().map(imports::reference).collect(Collectors.joining(", ")));
        }
        sb.append(" {\n");
        sb.append(renderClassMembers(
                decl.members(), imports, indent + 1, decl.desc().displayName(), false));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderClassMembers(
            List<ClassMember> members, ImportManager imports, int indent, String ownerSimpleName) {
        return renderClassMembers(members, imports, indent, ownerSimpleName, false);
    }

    private static String renderClassMembers(
            List<ClassMember> members, ImportManager imports, int indent, String ownerSimpleName, boolean insideEnum) {
        return members.stream()
                .map(member -> switch (member) {
                    case FieldDecl f -> renderField(f, imports, indent);
                    case MethodDecl m -> renderMethod(m, imports, indent);
                    case ConstructorDecl c ->
                        insideEnum
                                ? renderEnumConstructor(c, imports, indent, ownerSimpleName)
                                : renderConstructor(c, imports, indent, ownerSimpleName);
                    case AbstractMethodDecl a -> renderAbstractMethodInClass(a, imports, indent);
                    case TypeDecl ignored ->
                        throw new UnsupportedOperationException(
                                "nested type declarations are not supported in this MVP");
                })
                .collect(Collectors.joining("\n"));
    }

    private static String renderAbstractMethodInClass(AbstractMethodDecl m, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        String returnType =
                m.returnType().map(t -> TypeRefRenderer.renderType(t, imports)).orElse("void");
        return pad
                + TypeRefRenderer.renderModifiers(m.modifiers())
                + returnType
                + " "
                + m.name()
                + "("
                + TypeRefRenderer.renderParams(m.params(), imports)
                + ");\n";
    }

    private static String renderField(FieldDecl f, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(f.modifiers()));
        sb.append(TypeRefRenderer.renderType(f.type(), imports)).append(' ').append(f.name());
        f.initializer().ifPresent(init -> sb.append(" = ").append(ExprRenderer.renderExpr(init, imports, indent)));
        sb.append(";\n");
        return sb.toString();
    }

    private static String renderMethod(MethodDecl m, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(m.modifiers()));
        sb.append(m.returnType()
                        .map(t -> TypeRefRenderer.renderType(t, imports))
                        .orElse("void"))
                .append(' ');
        sb.append(m.name())
                .append('(')
                .append(TypeRefRenderer.renderParams(m.params(), imports))
                .append(')')
                .append(renderThrows(m.throwsTypes(), imports))
                .append(" {\n");
        sb.append(renderStatements(m.body().statements(), imports, indent + 1));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderConstructor(
            ConstructorDecl c, ImportManager imports, int indent, String ownerSimpleName) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(c.modifiers()));
        sb.append(ownerSimpleName)
                .append('(')
                .append(TypeRefRenderer.renderParams(c.params(), imports))
                .append(')')
                .append(renderThrows(c.throwsTypes(), imports))
                .append(" {\n");
        sb.append(renderStatements(c.body().statements(), imports, indent + 1));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderEnumConstructor(
            ConstructorDecl c, ImportManager imports, int indent, String ownerSimpleName) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(ownerSimpleName)
                .append('(')
                .append(TypeRefRenderer.renderParams(c.params(), imports))
                .append(')')
                .append(renderThrows(c.throwsTypes(), imports))
                .append(" {\n");
        sb.append(renderStatements(c.body().statements(), imports, indent + 1));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderInterface(InterfaceDecl decl, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()));
        if (!decl.permits().isEmpty()) {
            sb.append("sealed ");
        }
        sb.append("interface ").append(decl.desc().displayName());
        if (!decl.extendsInterfaces().isEmpty()) {
            sb.append(" extends ")
                    .append(decl.extendsInterfaces().stream()
                            .map(imports::reference)
                            .collect(Collectors.joining(", ")));
        }
        if (!decl.permits().isEmpty()) {
            sb.append(" permits ")
                    .append(decl.permits().stream().map(imports::reference).collect(Collectors.joining(", ")));
        }
        sb.append(" {\n");
        sb.append(renderInterfaceMembers(decl.members(), imports, indent + 1));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderInterfaceMembers(List<InterfaceMember> members, ImportManager imports, int indent) {
        return members.stream()
                .map(member -> switch (member) {
                    case AbstractMethodDecl a -> renderAbstractMethod(a, imports, indent);
                    case DefaultMethodDecl d ->
                        renderDefaultOrStaticMethod(
                                d.name(),
                                d.returnType(),
                                d.params(),
                                d.body(),
                                d.throwsTypes(),
                                "default",
                                imports,
                                indent);
                    case StaticMethodDecl s ->
                        renderDefaultOrStaticMethod(
                                s.name(),
                                s.returnType(),
                                s.params(),
                                s.body(),
                                s.throwsTypes(),
                                "static",
                                imports,
                                indent);
                    case ConstantDecl c -> renderConstant(c, imports, indent);
                    case TypeDecl ignored ->
                        throw new UnsupportedOperationException(
                                "nested type declarations are not supported in this MVP");
                })
                .collect(Collectors.joining("\n"));
    }

    private static String renderAbstractMethod(AbstractMethodDecl m, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        String returnType =
                m.returnType().map(t -> TypeRefRenderer.renderType(t, imports)).orElse("void");
        return pad
                + returnType
                + " "
                + m.name()
                + "("
                + TypeRefRenderer.renderParams(m.params(), imports)
                + ")"
                + renderThrows(m.throwsTypes(), imports)
                + ";\n";
    }

    private static String renderDefaultOrStaticMethod(
            String name,
            java.util.Optional<me.supcheg.javafile.type.TypeRef> returnType,
            List<Param> params,
            me.supcheg.javafile.code.CodeBody body,
            List<ClassDesc> throwsTypes,
            String keyword,
            ImportManager imports,
            int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(keyword).append(' ');
        sb.append(returnType.map(t -> TypeRefRenderer.renderType(t, imports)).orElse("void"))
                .append(' ');
        sb.append(name)
                .append('(')
                .append(TypeRefRenderer.renderParams(params, imports))
                .append(')')
                .append(renderThrows(throwsTypes, imports))
                .append(" {\n");
        sb.append(renderStatements(body.statements(), imports, indent + 1));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderThrows(List<ClassDesc> throwsTypes, ImportManager imports) {
        if (throwsTypes.isEmpty()) {
            return "";
        }
        return " throws " + throwsTypes.stream().map(imports::reference).collect(Collectors.joining(", "));
    }

    private static String renderConstant(ConstantDecl c, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        return pad
                + TypeRefRenderer.renderType(c.type(), imports)
                + " "
                + c.name()
                + " = "
                + ExprRenderer.renderExpr(c.initializer(), imports, indent)
                + ";\n";
    }

    private static String renderRecord(RecordDecl decl, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()))
                .append("record ")
                .append(decl.desc().displayName());
        sb.append('(')
                .append(decl.components().stream()
                        .map(c -> TypeRefRenderer.renderType(c.type(), imports) + " " + c.name())
                        .collect(Collectors.joining(", ")))
                .append(')');
        if (!decl.interfaces().isEmpty()) {
            sb.append(" implements ")
                    .append(decl.interfaces().stream().map(imports::reference).collect(Collectors.joining(", ")));
        }
        sb.append(" {\n");
        sb.append(renderRecordMembers(
                decl.members(), imports, indent + 1, decl.desc().displayName()));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderRecordMembers(
            List<RecordMember> members, ImportManager imports, int indent, String ownerSimpleName) {
        return members.stream()
                .map(member -> switch (member) {
                    case CompactConstructorDecl cc -> renderCompactConstructor(cc, imports, indent, ownerSimpleName);
                    case MethodDecl m -> renderMethod(m, imports, indent);
                    case StaticFieldDecl sf -> renderStaticField(sf, imports, indent);
                    case TypeDecl ignored ->
                        throw new UnsupportedOperationException(
                                "nested type declarations are not supported in this MVP");
                })
                .collect(Collectors.joining("\n"));
    }

    private static String renderCompactConstructor(
            CompactConstructorDecl cc, ImportManager imports, int indent, String ownerSimpleName) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(cc.modifiers()))
                .append(ownerSimpleName)
                .append(renderThrows(cc.throwsTypes(), imports))
                .append(" {\n");
        sb.append(renderStatements(cc.body().statements(), imports, indent + 1));
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderStaticField(StaticFieldDecl sf, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        return pad
                + "public static final "
                + TypeRefRenderer.renderType(sf.type(), imports)
                + " "
                + sf.name()
                + " = "
                + ExprRenderer.renderExpr(sf.initializer(), imports, indent)
                + ";\n";
    }

    private static String renderEnum(EnumDecl decl, ImportManager imports, int indent) {
        String pad = "    ".repeat(indent);
        StringBuilder sb = new StringBuilder(pad);
        sb.append(TypeRefRenderer.renderModifiers(decl.modifiers()))
                .append("enum ")
                .append(decl.desc().displayName());
        if (!decl.interfaces().isEmpty()) {
            sb.append(" implements ")
                    .append(decl.interfaces().stream().map(imports::reference).collect(Collectors.joining(", ")));
        }
        sb.append(" {\n");
        String pad1 = "    ".repeat(indent + 1);
        if (!decl.constants().isEmpty() || !decl.members().isEmpty()) {
            sb.append(pad1)
                    .append(decl.constants().stream()
                            .map(c -> renderEnumConstant(c, imports, indent + 1))
                            .collect(Collectors.joining(", ")))
                    .append(";\n");
        }
        if (!decl.members().isEmpty()) {
            sb.append("\n");
            sb.append(renderClassMembers(
                    decl.members(), imports, indent + 1, decl.desc().displayName(), true));
        }
        sb.append(pad).append("}\n");
        return sb.toString();
    }

    private static String renderEnumConstant(EnumConstant constant, ImportManager imports, int indent) {
        StringBuilder sb = new StringBuilder(constant.name());
        if (!constant.args().isEmpty() || !constant.body().isEmpty()) {
            String argsStr = constant.args().stream()
                    .map(a -> ExprRenderer.renderExpr(a, imports, indent))
                    .collect(Collectors.joining(", "));
            sb.append('(').append(argsStr).append(')');
        }
        if (!constant.body().isEmpty()) {
            String pad = "    ".repeat(indent);
            sb.append(" {\n");
            sb.append(renderClassMembers(constant.body(), imports, indent + 1, constant.name(), false));
            sb.append(pad).append('}');
        }
        return sb.toString();
    }

    private static String renderStatements(
            List<me.supcheg.javafile.code.Stmt> statements, ImportManager imports, int indent) {
        return ExprRenderer.renderBlock(new me.supcheg.javafile.code.CodeBody(statements), imports, indent);
    }
}
