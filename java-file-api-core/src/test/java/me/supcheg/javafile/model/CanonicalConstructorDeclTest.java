package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.ArrayTypeRef;
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

    @Test
    void arityMismatchIsRejectedAtRender() {
        RecordComponent x = new RecordComponent("x", PrimitiveTypeRef.INT);
        CanonicalConstructorDecl ctor = new CanonicalConstructorDecl(
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(new Param("x", PrimitiveTypeRef.INT), new Param("y", PrimitiveTypeRef.INT)),
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
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("expected 1")
                .hasMessageContaining("but got 2");
    }

    @Test
    void pureTypeMismatchIsRejectedAtRenderEvenWhenNameMatchesAndParamIsNotVarargs() {
        RecordComponent x = new RecordComponent("x", PrimitiveTypeRef.INT);
        CanonicalConstructorDecl ctor = new CanonicalConstructorDecl(
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(new Param("x", PrimitiveTypeRef.LONG)),
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
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be 'INT x'")
                .hasMessageContaining("but was 'LONG x'");
    }

    /// A varargs canonical constructor parameter must always be rejected, even
    /// when its element type happens to equal the component's declared type
    /// directly (not merely when wrapped in an extra array dimension) — javac
    /// requires the exact declared type with no varargs sugar for a canonical
    /// constructor, since [RecordComponent] itself has no varargs-shaped
    /// declaration to legitimately match against. Confirmed against javac:
    /// `record Nums(int[] values) { public Nums(int... values) {...} }` fails to
    /// compile with "invalid canonical constructor... type and arity must match".
    @Test
    void varargsParamIsRejectedEvenWhenItsElementTypeMatchesTheComponentTypeDirectly() {
        ArrayTypeRef intArray = new ArrayTypeRef(PrimitiveTypeRef.INT);
        RecordComponent values = new RecordComponent("values", intArray);
        CanonicalConstructorDecl ctor = new CanonicalConstructorDecl(
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(new Param("values", intArray, List.of(), true)),
                CodeBody.EMPTY,
                List.of());
        RecordDecl decl = new RecordDecl(
                ClassDesc.of("geom", "Nums"),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                List.of(values),
                List.of(),
                List.of(ctor));

        assertThatThrownBy(() -> me.supcheg.javafile.render.StandardRenderer.instance()
                        .render("geom", decl, me.supcheg.javafile.render.SourceRenderer.standardFormat()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
