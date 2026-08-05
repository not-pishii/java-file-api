package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.NewExpr;
import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AnonymousClassBuilderTest {

    private static final ClassDesc RUNNABLE = ClassDesc.of("java.lang", "Runnable");

    private static final ClassDesc NESTED_CLASS = ClassDesc.of("com.example", "Outer$Impl");
    private static final ClassDesc NESTED_INTERFACE = ClassDesc.of("com.example", "Outer$Api");
    private static final ClassDesc NESTED_RECORD = ClassDesc.of("com.example", "Outer$Point");
    private static final ClassDesc NESTED_ENUM = ClassDesc.of("com.example", "Outer$Kind");
    private static final ClassDesc NESTED_ANNOTATION = ClassDesc.of("com.example", "Outer$Marker");

    private static final List<Class<? extends TypeDecl>> EXPECTED_KINDS =
            List.of(ClassDecl.class, InterfaceDecl.class, RecordDecl.class, EnumDecl.class, AnnotationTypeDecl.class);

    @Test
    void bodyMembersAreCollectedInOrder() {
        Expr expr = Exprs.newAnonymous(
                Types.of(RUNNABLE), List.of(), b -> b.withVoidMethod("run", mb -> mb.withBody(cb -> cb.empty())));

        NewExpr newExpr = (NewExpr) expr;
        assertThat(newExpr.anonymousBody()).isPresent();
        assertThat(newExpr.anonymousBody().orElseThrow()).hasSize(1).allMatch(MethodDecl.class::isInstance);
    }

    @Test
    void constructorArgumentsArePreserved() {
        ClassDesc thread = ClassDesc.of("java.lang", "Thread");
        NewExpr newExpr = Exprs.newAnonymous(Types.of(thread), List.of(Exprs.literal("worker")), b -> {});

        assertThat(newExpr.args()).containsExactly(Exprs.literal("worker"));
    }

    @Test
    void withFieldAddsAFieldMember() {
        List<EnumConstantMember> body = new AnonymousClassBuilder()
                .withField("seen", PrimitiveTypeRef.INT, fb -> {})
                .build();

        assertThat(body).hasSize(1).allMatch(FieldDecl.class::isInstance);
    }

    @Test
    void withMethodAddsAMethodWithTheGivenReturnType() {
        List<EnumConstantMember> body = new AnonymousClassBuilder()
                .withMethod("get", Types.of(ClassDesc.of("java.lang", "String")), mb -> {})
                .build();

        assertThat(body).hasSize(1);
        MethodDecl method = (MethodDecl) body.get(0);
        assertThat(method.name()).isEqualTo("get");
        assertThat(method.returnType()).isPresent();
    }

    @Test
    void acceptAppendsAPrebuiltMember() {
        FieldDecl field = new FieldDecl(
                "seen", PrimitiveTypeRef.INT, List.of(), java.util.Set.of(Modifier.PRIVATE), Optional.empty());

        AnonymousClassBuilder acb = new AnonymousClassBuilder();
        acb.accept(field);

        assertThat(acb.build()).containsExactly(field);
    }

    @Test
    void supportsAllFiveNestedTypeKinds() {
        List<EnumConstantMember> body = new AnonymousClassBuilder()
                .withNestedClass(NESTED_CLASS, c -> {})
                .withNestedInterface(NESTED_INTERFACE, i -> {})
                .withNestedRecord(NESTED_RECORD, r -> {})
                .withNestedEnum(NESTED_ENUM, e -> {})
                .withNestedAnnotationType(NESTED_ANNOTATION, a -> {})
                .build();

        assertThat(body).hasSize(EXPECTED_KINDS.size());
        for (int i = 0; i < EXPECTED_KINDS.size(); i++) {
            assertThat(body.get(i)).isInstanceOf(EXPECTED_KINDS.get(i));
        }
    }
}
