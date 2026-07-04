package me.supcheg.javafile.render;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class ImportManagerProperties {

    @Property
    void referencingTheSameDescTwiceAlwaysReturnsTheSameSimpleName(
            @ForAll @AlphaChars @StringLength(min = 1, max = 20) String pkg,
            @ForAll @AlphaChars @StringLength(min = 1, max = 20) String simpleName) {
        ImportManager imports = new ImportManager("other.pkg");
        ClassDesc desc = ClassDesc.of(pkg, simpleName);

        String first = imports.reference(desc);
        String second = imports.reference(desc);

        assertThat(first).isEqualTo(second);
    }

    @Property
    void sortedImportsNeverContainsDuplicates(
            @ForAll @AlphaChars @StringLength(min = 1, max = 20) String pkg,
            @ForAll @AlphaChars @StringLength(min = 1, max = 20) String simpleName) {
        ImportManager imports = new ImportManager("other.pkg");
        ClassDesc desc = ClassDesc.of(pkg, simpleName);

        imports.reference(desc);
        imports.reference(desc);

        assertThat(imports.sortedImports()).doesNotHaveDuplicates();
    }
}
