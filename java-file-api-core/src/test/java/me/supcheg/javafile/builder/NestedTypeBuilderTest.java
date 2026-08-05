package me.supcheg.javafile.builder;

import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NestedTypeBuilderTest {

    private static final ClassDesc OUTER = ClassDesc.of("com.example", "Outer");
    private static final ClassDesc POINT = ClassDesc.of("com.example", "Outer$Point");

    private static final ClassDesc NESTED_CLASS = ClassDesc.of("com.example", "Outer$Impl");
    private static final ClassDesc NESTED_INTERFACE = ClassDesc.of("com.example", "Outer$Api");
    private static final ClassDesc NESTED_RECORD = ClassDesc.of("com.example", "Outer$Point2");
    private static final ClassDesc NESTED_ENUM = ClassDesc.of("com.example", "Outer$Kind");
    private static final ClassDesc NESTED_ANNOTATION = ClassDesc.of("com.example", "Outer$Marker");

    private static final List<Class<? extends TypeDecl>> EXPECTED_KINDS =
            List.of(ClassDecl.class, InterfaceDecl.class, RecordDecl.class, EnumDecl.class, AnnotationTypeDecl.class);

    @Test
    void nestedRecordMatchesManuallyAcceptedDeclaration() {
        ClassDecl viaShortcut = new ClassBuilder(OUTER)
                .withNestedRecord(POINT, r -> r.withComponent("x", PrimitiveTypeRef.INT))
                .build();

        ClassBuilder manual = new ClassBuilder(OUTER);
        manual.accept(new RecordBuilder(POINT)
                .withComponent("x", PrimitiveTypeRef.INT)
                .build());

        assertThat(viaShortcut).isEqualTo(manual.build());
    }

    @Test
    void nestedDeclarationIsAppendedAsAMember() {
        ClassDecl decl = new ClassBuilder(OUTER)
                .withNestedRecord(POINT, r -> r.withComponent("x", PrimitiveTypeRef.INT))
                .build();

        assertThat(decl.members()).hasSize(1).allMatch(RecordDecl.class::isInstance);
    }

    @Test
    void allFourBuildersAcceptNestedTypes() {
        ClassDesc iface = ClassDesc.of("com.example", "Outer$Api");
        assertThat(new ClassBuilder(OUTER)
                        .withNestedInterface(iface, i -> {})
                        .build()
                        .members())
                .hasSize(1);
        assertThat(new InterfaceBuilder(OUTER)
                        .withNestedInterface(iface, i -> {})
                        .build()
                        .members())
                .hasSize(1);
        assertThat(new RecordBuilder(OUTER)
                        .withNestedInterface(iface, i -> {})
                        .build()
                        .members())
                .hasSize(1);
        assertThat(new EnumBuilder(OUTER)
                        .withNestedInterface(iface, i -> {})
                        .build()
                        .members())
                .hasSize(1);
    }

    @Test
    void classBuilderSupportsAllFiveNestedKinds() {
        ClassDecl decl = new ClassBuilder(OUTER)
                .withNestedClass(NESTED_CLASS, c -> {})
                .withNestedInterface(NESTED_INTERFACE, i -> {})
                .withNestedRecord(NESTED_RECORD, r -> {})
                .withNestedEnum(NESTED_ENUM, e -> {})
                .withNestedAnnotationType(NESTED_ANNOTATION, a -> {})
                .build();

        assertMembersMatchExpectedKinds(decl.members());
    }

    @Test
    void interfaceBuilderSupportsAllFiveNestedKinds() {
        InterfaceDecl decl = new InterfaceBuilder(OUTER)
                .withNestedClass(NESTED_CLASS, c -> {})
                .withNestedInterface(NESTED_INTERFACE, i -> {})
                .withNestedRecord(NESTED_RECORD, r -> {})
                .withNestedEnum(NESTED_ENUM, e -> {})
                .withNestedAnnotationType(NESTED_ANNOTATION, a -> {})
                .build();

        assertMembersMatchExpectedKinds(decl.members());
    }

    @Test
    void recordBuilderSupportsAllFiveNestedKinds() {
        RecordDecl decl = new RecordBuilder(OUTER)
                .withNestedClass(NESTED_CLASS, c -> {})
                .withNestedInterface(NESTED_INTERFACE, i -> {})
                .withNestedRecord(NESTED_RECORD, r -> {})
                .withNestedEnum(NESTED_ENUM, e -> {})
                .withNestedAnnotationType(NESTED_ANNOTATION, a -> {})
                .build();

        assertMembersMatchExpectedKinds(decl.members());
    }

    @Test
    void enumBuilderSupportsAllFiveNestedKinds() {
        EnumDecl decl = new EnumBuilder(OUTER)
                .withNestedClass(NESTED_CLASS, c -> {})
                .withNestedInterface(NESTED_INTERFACE, i -> {})
                .withNestedRecord(NESTED_RECORD, r -> {})
                .withNestedEnum(NESTED_ENUM, e -> {})
                .withNestedAnnotationType(NESTED_ANNOTATION, a -> {})
                .build();

        assertMembersMatchExpectedKinds(decl.members());
    }

    private static void assertMembersMatchExpectedKinds(List<?> members) {
        assertThat(members).hasSize(EXPECTED_KINDS.size());
        for (int i = 0; i < EXPECTED_KINDS.size(); i++) {
            assertThat(members.get(i)).isInstanceOf(EXPECTED_KINDS.get(i));
        }
    }
}
