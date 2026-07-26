package me.supcheg.javafile.type;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ClassDescNamesTest {

    @Test
    void topLevelTypeChainIsASingleElement() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Outer");

        assertThat(ClassDescNames.nestingChain(desc)).containsExactly("Outer");
        assertThat(ClassDescNames.leafSimpleName(desc)).isEqualTo("Outer");
        assertThat(ClassDescNames.qualifiedByDots(desc)).isEqualTo("Outer");
    }

    @Test
    void oneLevelOfNestingSplitsIntoTwoSegments() {
        ClassDesc desc = ClassDesc.of("me.supcheg.example", "Outer").nested("Inner");

        assertThat(ClassDescNames.nestingChain(desc)).containsExactly("Outer", "Inner");
        assertThat(ClassDescNames.leafSimpleName(desc)).isEqualTo("Inner");
        assertThat(ClassDescNames.qualifiedByDots(desc)).isEqualTo("Outer.Inner");
    }

    @Test
    void twoLevelsOfNestingSplitIntoThreeSegments() {
        ClassDesc desc =
                ClassDesc.of("me.supcheg.example", "Outer").nested("Middle").nested("Inner");

        assertThat(ClassDescNames.nestingChain(desc)).containsExactly("Outer", "Middle", "Inner");
        assertThat(ClassDescNames.leafSimpleName(desc)).isEqualTo("Inner");
        assertThat(ClassDescNames.qualifiedByDots(desc)).isEqualTo("Outer.Middle.Inner");
    }

    @Test
    void splitsTheRealJdkNestedTypeMapEntry() {
        ClassDesc desc = Map.Entry.class.describeConstable().orElseThrow();

        assertThat(ClassDescNames.nestingChain(desc)).containsExactly("Map", "Entry");
        assertThat(ClassDescNames.leafSimpleName(desc)).isEqualTo("Entry");
        assertThat(ClassDescNames.qualifiedByDots(desc)).isEqualTo("Map.Entry");
    }
}
