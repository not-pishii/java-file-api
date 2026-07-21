package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CanonicalConstructorDeclTest {

    @Test
    void paramsMustMatchComponentsExactly() {
        RecordComponent x = new RecordComponent("x", PrimitiveTypeRef.INT);
        CanonicalConstructorDecl ctor = new CanonicalConstructorDecl(
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(new Param("x", PrimitiveTypeRef.INT)),
                CodeBody.EMPTY,
                List.of());
        RecordDecl decl = new RecordDecl(
                ClassDesc.of("geom", "Point"),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                List.of(x),
                List.of(),
                List.of(ctor));

        assertThat(decl.members()).containsExactly(ctor);
    }

    @Test
    void mismatchedParamsAreRejectedAtRender() {
        RecordComponent x = new RecordComponent("x", PrimitiveTypeRef.INT);
        CanonicalConstructorDecl ctor = new CanonicalConstructorDecl(
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(new Param("wrongName", PrimitiveTypeRef.INT)),
                CodeBody.EMPTY,
                List.of());
        RecordDecl decl = new RecordDecl(
                ClassDesc.of("geom", "Point"),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                List.of(x),
                List.of(),
                List.of(ctor));

        assertThatThrownBy(() -> me.supcheg.javafile.render.StandardRenderer.instance()
                        .render("geom", decl, me.supcheg.javafile.render.SourceRenderer.standardFormat()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
