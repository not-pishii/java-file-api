package me.supcheg.javafile.render;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static me.supcheg.javafile.render.SourceRenderer.standardFormat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The name of a type of the unnamed package, which has no qualified name to fall back on (JLS 7.4.2):
/// it is its simple name, or it cannot be written.
class UnnamedPackageNamesTest {
    private static final ClassDesc TOP = ClassDesc.of("Top");
    private static final ClassDesc NESTED = ClassDesc.of("Top$Inner");

    @Test
    void aTypeOfTheUnnamedPackageWhoseSimpleNameIsTakenHasNoName() {
        ImportManager imports = new ImportManager("p");

        assertThat(imports.reference(ClassDesc.of("a", "Top"))).isEqualTo("Top");
        assertThat(imports.reference(ClassDesc.of("a", "Inner"))).isEqualTo("Inner");
        // it was written `.Top`, the name of nothing: there is no package to qualify it by
        assertThatThrownBy(() -> imports.reference(TOP))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("cannot name Top of the unnamed package in a file where Top means a.Top: a type of the"
                        + " unnamed package has no qualified name");
        // a member type is named through the type it is nested in
        assertThat(imports.reference(NESTED)).isEqualTo("Top.Inner");
        assertThat(imports.sortedImports()).containsExactly("a.Inner", "a.Top");
    }

    @Test
    void aTypeParameterOfItsNameLeavesATypeOfTheUnnamedPackageNoName() {
        ImportManager imports = new ImportManager("p", List.of(), Set.of("Top"));

        assertThatThrownBy(() -> imports.reference(TOP))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("cannot name Top in a file that declares the type parameter Top: the name of the type"
                        + " parameter obscures the first name of the qualified one");
    }

    @Test
    void anInheritedMemberTypeOfItsNameLeavesATypeOfTheUnnamedPackageNoName() {
        ImportManager imports = new ImportManager(
                "p", new ImportManager.Known(new LinkedHashSet<>(), Set.of(), Set.of("Top", "Inner"), Set.of()));

        assertThatThrownBy(() -> imports.reference(TOP))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("cannot name Top in a file whose type inherits the member type Top: the name of the"
                        + " member type obscures the first name of the qualified one");
        // of a member type the simple name alone is taken: it is named through the type it is nested in
        assertThat(new ImportManager(
                                "p",
                                new ImportManager.Known(new LinkedHashSet<>(), Set.of(), Set.of("Inner"), Set.of()))
                        .reference(NESTED))
                .isEqualTo("Top.Inner");
    }

    @Test
    void aClassWithATypeUseAnnotationIsWrittenAsATypeBeforeAMember() {
        // `Outer.@A Inner.member` is a type with its annotation, where a variable of the name does not reach
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));
        ClassDesc marker = ClassDesc.of("a", "Marker");

        assertThat(TypeRefRenderer.renderQualifier(
                        Types.of(ClassDesc.of("a", "Box"), new AnnotationUse(marker, List.of())), ctx))
                .isEqualTo("@Marker Box");
        assertThat(TypeRefRenderer.renderQualifier(Types.of(ClassDesc.of("a", "Box")), ctx))
                .isEqualTo("Box");
    }
}
