package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.InCompilation;
import me.supcheg.javafile.langmodel.mirror.Admission;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.routine.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.constant.ClassDesc;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/// A type the translator cannot represent is mentioned as unavailable, and
/// has no target.
@ExtendWith(InCompilation.class)
@InCompilation.Sources({
    "package p; public class Outer<T> { public class Inner {} }",
    "package p; public class Uses { public Outer<String>.Inner field; }",
    "package p; public class Bounded<T extends Outer.Inner> {}"
})
class UnrepresentableMentionTest {

    @Test
    void aMentionOfAnUnrepresentableTypeIsUnavailable(Elements elements, Types types) {
        Models models = new Models(elements, types);

        assertThat(models.of(elements.getTypeElement("p.Outer.Inner"), MemberFilter.NONE))
                .isNotInstanceOf(Translation.Ok.class);
        Targets targets = new Targets(models, Map.of(), Map.of());
        assertThat(targets.of(ClassDesc.of("p.Outer$Inner"))).isEmpty();
    }

    @Test
    void aTargetOfAnUnrepresentableTypeIsReadAndHasNone(Elements elements, Types types) {
        Models models = new Models(elements, types);

        Targets targets = new Targets(models, Map.of("p.Outer$Inner", ClassDesc.of("gen", "Outer_Inner_")), Map.of());
        assertThat(targets.of(ClassDesc.of("p.Outer$Inner"))).isEmpty();
    }

    @Test
    void theClosureTellsAnUnrepresentableMentionAsUnavailable(Elements elements, Types types) {
        Models models = new Models(elements, types);

        Either<String, ?> mentioned = Closure.mention("p.Outer$Inner", models);
        assertThat(mentioned.right().map(Object::toString))
                .hasValueSatisfying(mention -> assertThat(mention).contains("p.Outer$Inner"));
    }

    @Test
    void aBoundThatMentionsAnUnrepresentableTypeIsRejected(Elements elements, Types types) {
        Models models = new Models(elements, types);
        TypeElement bounded = elements.getTypeElement("p.Bounded");
        TypeModel model =
                switch (models.of(bounded, MemberFilter.NONE)) {
                    case Translation.Ok<TypeModel>(TypeModel value) -> value;
                    case Translation.Deferred<TypeModel> deferred ->
                        throw new IllegalStateException(deferred.toString());
                    case Translation.Unrepresentable<TypeModel> none ->
                        throw new IllegalStateException(none.toString());
                };

        assertThat(models.translator().rejection(bounded, model))
                .isInstanceOfSatisfying(
                        Admission.Rejected.class,
                        rejected -> assertThat(rejected.reason()).contains("p.Outer$Inner", "which has no metamodel"));
    }
}
