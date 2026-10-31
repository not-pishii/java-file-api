package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.InCompilation;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.Translation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
    "package p; public class Uses { public Outer<String>.Inner field; }"
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
}
