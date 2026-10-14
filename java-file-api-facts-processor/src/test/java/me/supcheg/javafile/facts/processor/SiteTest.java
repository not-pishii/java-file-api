package me.supcheg.javafile.facts.processor;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

/// The order of the sites of `@Facts`, which the processor keeps in sorted sets.
class SiteTest {

    @Test
    void theOrderOfTheSitesAgreesWithTheirEquality() {
        Site type = new Site.TypeSite("a.b", "a");
        Site pkg = new Site.PackageSite("a.b");
        Site later = new Site.TypeSite("a.c", "a");

        // by name alone the two were one element of a sorted set: the second was dropped
        assertThat(type).isNotEqualTo(pkg);
        assertThat(type.compareTo(pkg)).isNegative();
        assertThat(pkg.compareTo(type)).isPositive();
        assertThat(type.compareTo(new Site.TypeSite("a.b", "a"))).isZero();
        assertThat(pkg.compareTo(new Site.PackageSite("a.b"))).isZero();
        assertThat(pkg.compareTo(later)).isNegative();
        assertThat(new TreeSet<>(List.of(later, pkg, type))).containsExactly(type, pkg, later);
    }
}
