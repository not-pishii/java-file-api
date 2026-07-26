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

    @Test
    void nestedTypeFirstToClaimALeafNameGetsTheBareLeafNameAndAFullyQualifiedImport() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        ClassDesc entry = ClassDesc.of("java.util", "Map").nested("Entry");

        String ref = imports.reference(entry);

        assertThat(ref).isEqualTo("Entry");
        assertThat(imports.sortedImports()).containsExactly("java.util.Map.Entry");
    }

    @Test
    void nestedTypeInTheCurrentPackageStillNeedsAnImportUnlikeATopLevelType() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        ClassDesc nested = ClassDesc.of("me.supcheg.example", "Outer").nested("Inner");

        String ref = imports.reference(nested);

        assertThat(ref).isEqualTo("Inner");
        assertThat(imports.sortedImports()).containsExactly("me.supcheg.example.Outer.Inner");
    }

    @Test
    void nestedTypeInJavaLangStillNeedsAnImportUnlikeATopLevelJavaLangType() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        ClassDesc threadState = ClassDesc.of("java.lang", "Thread").nested("State");

        String ref = imports.reference(threadState);

        assertThat(ref).isEqualTo("State");
        assertThat(imports.sortedImports()).containsExactly("java.lang.Thread.State");
    }

    @Test
    void nestedTypeCollidingWithATopLevelTypeOfTheSameLeafNameFallsBackToTheDottedForm() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        String topLevel = imports.reference(ClassDesc.of("java.util", "Entry"));
        String nested = imports.reference(ClassDesc.of("java.util", "Map").nested("Entry"));

        assertThat(topLevel).isEqualTo("Entry");
        assertThat(nested).isEqualTo("java.util.Map.Entry");
        assertThat(imports.sortedImports()).containsExactly("java.util.Entry");
    }

    @Test
    void twoNestedTypesWithTheSameLeafNameButDifferentOwnersCollide() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        ClassDesc firstInner = ClassDesc.of("pkg.a", "A").nested("Inner");
        ClassDesc secondInner = ClassDesc.of("pkg.b", "B").nested("Inner");

        String first = imports.reference(firstInner);
        String second = imports.reference(secondInner);

        assertThat(first).isEqualTo("Inner");
        assertThat(second).isEqualTo("pkg.b.B.Inner");
        assertThat(imports.sortedImports()).containsExactly("pkg.a.A.Inner");
    }

    @Test
    void collidingNestedTypeFromTheCurrentPackageDropsThePackagePrefixInTheFallback() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        imports.reference(ClassDesc.of("pkg.other", "Inner"));
        String nested =
                imports.reference(ClassDesc.of("me.supcheg.example", "Outer").nested("Inner"));

        assertThat(nested).isEqualTo("Outer.Inner");
    }
}
