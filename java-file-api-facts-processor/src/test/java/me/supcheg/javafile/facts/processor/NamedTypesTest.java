package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.facts.processor.harness.InCompilation;
import me.supcheg.javafile.langmodel.mirror.SamModel;
import me.supcheg.javafile.langmodel.mirror.Translation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/// How a type is found by its name and linked to in the code the processor writes, where the name is not the plain
/// one of a class of a package: a class of the unnamed package, which a class of a package cannot name, a class
/// with a `$` in its name, which the code generated reads as a member class, and the single abstract method of a
/// functional interface, which the comment of a fact links to.
@ExtendWith(InCompilation.class)
@InCompilation.Sources({
    "public class Plain { public void m() {} }",
    "package p; public class Named { public void m() {} }",
    "package p; public class Price$Tag { public void tag() {} }",
    "package p; public class Priced extends Price$Tag {}",
    "package p; public interface Task { void run(); }",
    "package p; public interface Job extends Task {}",
    "package p; public interface Duty { void run(); }",
    "package p; public interface Both extends Task, Duty {}",
    "package p; public interface Mixed extends Task { void run(); }"
})
class NamedTypesTest {

    @Test
    void aTypeIsFoundByItsBinaryNameWhetherOrNotItIsInAPackage(Elements elements, Types types) {
        Models models = new Models(elements, types);

        assertThat(models.element(ClassDesc.of("Plain"))).isPresent();
        assertThat(models.element(ClassDesc.of("p", "Named"))).isPresent();
        assertThat(models.element(ClassDesc.of("p", "Missing"))).isEmpty();
        // a `$` is the sign of a member class to the name the type is found by
        assertThat(models.element(ClassDesc.of("p", "Price$Tag"))).isEmpty();
    }

    @Test
    void aTypeCanBeNamedByCodeOfAnotherPackageIfItIsInAPackageAndFound(Elements elements, Types types) {
        Targets targets = new Targets(new Models(elements, types), Map.of(), Map.of());

        assertThat(targets.nameable(ClassDesc.of("p", "Named"))).isTrue();
        assertThat(targets.nameable(ClassDesc.of("Plain"))).isFalse();
        assertThat(targets.nameable(ClassDesc.of("p", "Missing"))).isFalse();
        assertThat(targets.nameable(ClassDesc.of("p", "Price$Tag"))).isFalse();
    }

    @Test
    void aMemberIsLinkedThroughTheTypeThatDeclaresItUnlessTheTypeCannotBeFound(Elements elements, Types types) {
        Models models = new Models(elements, types);
        TypeElement priced = elements.getTypeElement("p.Priced");
        ExecutableElement tag = ElementFilter.methodsIn(elements.getAllMembers(priced)).stream()
                .filter(method -> method.getSimpleName().contentEquals("tag"))
                .findFirst()
                .orElseThrow();
        ExecutableElement named = ElementFilter.methodsIn(
                        elements.getTypeElement("p.Named").getEnclosedElements())
                .getFirst();

        // the type with a `$` has no name a link resolves, and the member is linked through the type that inherits it
        assertThat(models.reference(priced, tag))
                .isEqualTo(new DocRef.Method(ClassDesc.of("p", "Priced"), "tag", List.of()));
        assertThat(models.reference(elements.getTypeElement("p.Named"), named))
                .isEqualTo(new DocRef.Method(ClassDesc.of("p", "Named"), "m", List.of()));
    }

    private static DocRef samReference(Models models, Elements elements, String type) {
        TypeElement element = elements.getTypeElement(type);
        SamModel sam =
                switch (models.sam(element)) {
                    case Translation.Ok<Optional<SamModel>>(Optional<SamModel> found) -> found.orElseThrow();
                    case Translation.Deferred<Optional<SamModel>> deferred -> throw new AssertionError(deferred);
                    case Translation.Unrepresentable<Optional<SamModel>> none -> throw new AssertionError(none);
                };
        return models.samReference(element, sam.method());
    }

    @Test
    void theSamIsLinkedThroughTheInterfaceThatDeclaresItOrElseThroughTheFirstByName(Elements elements, Types types) {
        Models models = new Models(elements, types);

        assertThat(samReference(models, elements, "p.Task"))
                .isEqualTo(new DocRef.Method(ClassDesc.of("p", "Task"), "run", List.of()));
        assertThat(samReference(models, elements, "p.Job"))
                .isEqualTo(new DocRef.Method(ClassDesc.of("p", "Task"), "run", List.of()));
        assertThat(samReference(models, elements, "p.Both"))
                .isEqualTo(new DocRef.Method(ClassDesc.of("p", "Duty"), "run", List.of()));
        assertThat(samReference(models, elements, "p.Mixed"))
                .isEqualTo(new DocRef.Method(ClassDesc.of("p", "Mixed"), "run", List.of()));
    }
}
