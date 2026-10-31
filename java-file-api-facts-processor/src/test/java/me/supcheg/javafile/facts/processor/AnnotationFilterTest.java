package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.InCompilation;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static org.assertj.core.api.Assertions.assertThat;

/// The annotations of a type are the ones code of another package can
/// name: an annotation of the JDK implementation is none, and one that is
/// not public is read in a later round.
@ExtendWith(InCompilation.class)
@InCompilation.Sources({
    "package sun.a; public @interface SunAnno {}",
    "package com.sun.b; public @interface ComAnno {}",
    "package p; @interface HiddenAnno {}",
    "package p; @sun.a.SunAnno @com.sun.b.ComAnno public class Uses {}",
    "package p; @HiddenAnno public class Hidden {}"
})
class AnnotationFilterTest {

    private static TypeModel model(Models models, Elements elements, String name) {
        return switch (models.of(elements.getTypeElement(name), MemberFilter.NONE)) {
            case Translation.Ok<TypeModel>(TypeModel value) -> value;
            case Translation.Deferred<TypeModel> deferred -> throw new IllegalStateException(deferred.toString());
            case Translation.Unrepresentable<TypeModel> none -> throw new IllegalStateException(none.toString());
        };
    }

    @Test
    void anAnnotationOfTheJdkImplementationIsNoAnnotationOfTheApi(Elements elements, Types types) {
        Models models = new Models(elements, types);

        assertThat(model(models, elements, "p.Uses").annotations()).isEmpty();
    }

    @Test
    void anAnnotationThatIsNotPublicDefersTheTypeToALaterRound(Elements elements, Types types) {
        Models models = new Models(elements, types);
        TypeElement hidden = elements.getTypeElement("p.Hidden");

        assertThat(models.of(hidden, MemberFilter.NONE)).isInstanceOf(Translation.Deferred.class);
    }
}
