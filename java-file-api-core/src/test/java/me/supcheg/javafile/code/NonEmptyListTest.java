package me.supcheg.javafile.code;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NonEmptyListTest {

    @Test
    void toListReturnsHeadFollowedByTail() {
        NonEmptyList<String> list = new NonEmptyList<>("a", List.of("b", "c"));

        assertThat(list.toList()).containsExactly("a", "b", "c");
    }

    @Test
    void toListWorksWithEmptyTail() {
        NonEmptyList<String> list = new NonEmptyList<>("a", List.of());

        assertThat(list.toList()).containsExactly("a");
    }

    @Test
    void tailIsDefensivelyCopied() {
        var mutableTail = new java.util.ArrayList<String>();
        mutableTail.add("b");
        NonEmptyList<String> list = new NonEmptyList<>("a", mutableTail);
        mutableTail.add("c");

        assertThat(list.tail()).containsExactly("b");
    }

    @Test
    void copyOfBuildsFromNonEmptyList() {
        NonEmptyList<String> list = NonEmptyList.copyOf(List.of("a", "b", "c"));

        assertThat(list.head()).isEqualTo("a");
        assertThat(list.tail()).containsExactly("b", "c");
    }

    @Test
    void copyOfRejectsEmptyList() {
        assertThatThrownBy(() -> NonEmptyList.copyOf(List.of())).isInstanceOf(IllegalArgumentException.class);
    }
}
