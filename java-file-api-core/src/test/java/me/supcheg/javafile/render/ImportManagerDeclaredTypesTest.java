package me.supcheg.javafile.render;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ImportManagerDeclaredTypesTest {
    private static final ClassDesc TOP = ClassDesc.of("me.supcheg.example", "Registry");
    private static final ClassDesc ENTRY = TOP.nested("Entry");
    private static final ClassDesc MAP_ENTRY = ClassDesc.of("java.util", "Map$Entry");

    @Test
    void aDeclaredTypeKeepsItsSimpleNameWhateverIsReferencedFirst() {
        ImportManager imports = new ImportManager("me.supcheg.example", List.of(TOP, ENTRY));

        assertThat(imports.reference(MAP_ENTRY)).isEqualTo("java.util.Map.Entry");
        assertThat(imports.reference(ENTRY)).isEqualTo("Entry");
        assertThat(imports.reference(ClassDesc.of("other", "Registry"))).isEqualTo("other.Registry");
        assertThat(imports.reference(TOP)).isEqualTo("Registry");
        assertThat(imports.sortedImports()).containsExactly("me.supcheg.example.Registry.Entry");
    }

    @Test
    void aDeclaredTypeNothingRefersToIsNotImported() {
        ImportManager imports = new ImportManager("me.supcheg.example", List.of(TOP, ENTRY));

        assertThat(imports.reference(MAP_ENTRY)).isEqualTo("java.util.Map.Entry");
        assertThat(imports.sortedImports()).isEmpty();
    }

    @Test
    void theFirstOfTwoDeclaredTypesOfOneSimpleNameClaimsIt() {
        ClassDesc deeper = TOP.nested("Group").nested("Entry");
        ImportManager imports = new ImportManager("me.supcheg.example", List.of(TOP, ENTRY, deeper));

        assertThat(imports.reference(deeper)).isEqualTo("Registry.Group.Entry");
        assertThat(imports.sortedImports()).isEmpty();
    }

    @Test
    void aFileWithANestedTypeWritesAnotherTypeOfThatNameQualified() {
        JavaFile file = JavaFile.class_(
                TOP,
                cb -> cb.withField(
                                "first",
                                Types.of(MAP_ENTRY),
                                fb -> fb.withModifiers(Modifier.PRIVATE).withInitializer(Exprs.literalNull()))
                        .withField("own", Types.of(ENTRY), fb -> fb.withModifiers(Modifier.PRIVATE))
                        .withNestedClass(ENTRY, nested -> nested.withModifiers(Modifier.STATIC)));

        assertThat(file.render()).isEqualTo("""
                package me.supcheg.example;

                import me.supcheg.example.Registry.Entry;

                public class Registry {
                    private java.util.Map.Entry first = null;

                    private Entry own;

                    public static class Entry {
                    }
                }
                """);
    }

    @Test
    void aTypeNestedInAnEnumConstantBodyOrANestedTypeClaimsItsNameToo() {
        ClassDesc deep = TOP.nested("Group").nested("List");
        JavaFile file = JavaFile.class_(
                TOP,
                cb -> cb.withField(
                                "items",
                                Types.of(ClassDesc.of("java.util", "List")),
                                fb -> fb.withModifiers(Modifier.PRIVATE))
                        .withNestedClass(TOP.nested("Group"), group -> group.withNestedClass(deep, _ -> {})));

        assertThat(file.render()).contains("private java.util.List items;").doesNotContain("import");
    }

    @Test
    void aTypeOfTheNameOfATypeParameterIsWrittenQualified() {
        ImportManager imports = new ImportManager("me.supcheg.example", List.of(TOP), List.of("T", "String"));

        assertThat(imports.reference(ClassDesc.of("other", "T"))).isEqualTo("other.T");
        assertThat(imports.reference(ClassDesc.of("me.supcheg.example", "T"))).isEqualTo("me.supcheg.example.T");
        assertThat(imports.reference(ClassDesc.of("java.lang", "String"))).isEqualTo("java.lang.String");
        assertThat(imports.mention(ClassDesc.of("other", "T"))).isEqualTo("other.T");
        assertThat(imports.reference(TOP)).isEqualTo("Registry");
        assertThat(imports.sortedImports()).isEmpty();
    }

    @Test
    void aTypeIsNotNamedWhereATypeParameterObscuresItsQualifiedName() {
        ImportManager imports = new ImportManager("me.supcheg.example", List.of(TOP), List.of("other", "T"));

        org.assertj.core.api.Assertions.assertThatIllegalStateException()
                .isThrownBy(() -> imports.reference(ClassDesc.of("other", "T")))
                .withMessageContaining("cannot name other.T in a file that declares the type parameter other");
        assertThat(imports.reference(ClassDesc.of("other", "U"))).isEqualTo("U");
    }
}
