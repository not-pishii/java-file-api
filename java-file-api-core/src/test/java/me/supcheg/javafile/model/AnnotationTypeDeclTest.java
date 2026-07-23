package me.supcheg.javafile.model;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationTypeDeclTest {

    @Test
    void holdsElementsWithAndWithoutDefaults() {
        AnnotationElementDecl value = new AnnotationElementDecl("value", PrimitiveTypeRef.INT, Optional.empty());
        AnnotationTypeDecl decl = new AnnotationTypeDecl(
                ClassDesc.of("me.supcheg.example", "MaxLength"), List.of(), Set.of(Modifier.PUBLIC), List.of(value));

        assertThat(decl.elements()).containsExactly(value);
        assertThat((TypeDecl) decl).isInstanceOf(JavaFileElement.class);
    }
}
