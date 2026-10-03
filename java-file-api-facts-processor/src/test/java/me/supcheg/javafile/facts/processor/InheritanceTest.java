package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.InCompilation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// What a type extends and implements, and whether a type that is not
/// there yet may extend or implement it.
@ExtendWith(InCompilation.class)
class InheritanceTest {
    private static final String TYPES = """
            package p;
            public class Types {
                public static class Open {}
                public static final class Final {}
                public abstract static class Abstract {}
                public interface Iface {}
                public enum Enumerated { A { } }
                public record Rec(int x) {}
                public @interface Marker {}
                public sealed interface Closed permits ClosedA, ClosedB {}
                public record ClosedA() implements Closed {}
                public static final class ClosedB implements Closed {}
                public sealed interface Leaky permits LeakyA, LeakyB {}
                public record LeakyA() implements Leaky {}
                public static sealed class LeakyB implements Leaky permits LeakyC {}
                public static non-sealed class LeakyC extends LeakyB {}
            }
            """;

    private static List<String> open(Elements elements, String... names) {
        return Stream.of(names)
                .filter(name -> Inheritance.open(elements.getTypeElement("p.Types." + name)))
                .toList();
    }

    private static List<String> names(Stream<TypeElement> types) {
        return types.map(type -> type.getQualifiedName().toString()).toList();
    }

    @Test
    @InCompilation.Sources(TYPES)
    void aTypeThatIsNotThereYetMayExtendWhatIsNotFinal(Elements elements) {
        assertThat(open(elements, "Open", "Final", "Abstract", "Iface", "Enumerated", "Rec", "Marker"))
                .containsExactly("Open", "Abstract", "Iface");
    }

    @Test
    @InCompilation.Sources(TYPES)
    void aSealedTypeIsOpenThroughASubtypeItPermitsThatIs(Elements elements) {
        assertThat(open(elements, "Closed", "Leaky", "LeakyB", "LeakyC")).containsExactly("Leaky", "LeakyB", "LeakyC");
    }

    @Test
    @InCompilation.Sources(TYPES)
    void objectIsASupertypeOfAnInterfaceThatExtendsNone(Elements elements) {
        assertThat(names(Inheritance.direct(elements.getTypeElement("p.Types.Iface"), elements)))
                .containsExactly("java.lang.Object");
        assertThat(names(Inheritance.supertypes(elements.getTypeElement("java.util.Comparator"), elements)))
                .containsExactly("java.lang.Object");
        // through the interface that extends none
        assertThat(names(Inheritance.supertypes(elements.getTypeElement("java.util.List"), elements)))
                .containsExactly(
                        "java.util.SequencedCollection",
                        "java.util.Collection",
                        "java.lang.Iterable",
                        "java.lang.Object");
    }
}
