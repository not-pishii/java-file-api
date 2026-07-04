package me.supcheg.javafile.render;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class ImportManagerTest {

    @Test
    void sameNameFromSamePackageNeedsNoImport() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        String ref = imports.reference(ClassDesc.of("me.supcheg.example", "Sibling"));

        assertThat(ref).isEqualTo("Sibling");
        assertThat(imports.sortedImports()).isEmpty();
    }

    @Test
    void javaLangNeedsNoImport() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        String ref = imports.reference(ClassDesc.of("java.lang", "String"));

        assertThat(ref).isEqualTo("String");
        assertThat(imports.sortedImports()).isEmpty();
    }

    @Test
    void firstUseOfASimpleNameGetsImported() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        String ref = imports.reference(ClassDesc.of("java.util", "List"));

        assertThat(ref).isEqualTo("List");
        assertThat(imports.sortedImports()).containsExactly("java.util.List");
    }

    @Test
    void repeatedReferenceToSameTypeReusesTheImport() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        ClassDesc list = ClassDesc.of("java.util", "List");

        imports.reference(list);
        String second = imports.reference(list);

        assertThat(second).isEqualTo("List");
        assertThat(imports.sortedImports()).containsExactly("java.util.List");
    }

    @Test
    void collidingSimpleNamesFallBackToFqnForTheLoser() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        String first = imports.reference(ClassDesc.of("java.util", "Date"));
        String second = imports.reference(ClassDesc.of("java.sql", "Date"));

        assertThat(first).isEqualTo("Date");
        assertThat(second).isEqualTo("java.sql.Date");
        assertThat(imports.sortedImports()).containsExactly("java.util.Date");
    }

    @Test
    void importsAreSortedAlphabetically() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        imports.reference(ClassDesc.of("java.util", "Set"));
        imports.reference(ClassDesc.of("java.util", "List"));

        assertThat(imports.sortedImports()).containsExactly("java.util.List", "java.util.Set");
    }
}
