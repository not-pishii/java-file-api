package me.supcheg.javafile.code;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecordPatternTest {

    private static final ClassDesc POINT = ClassDesc.of("geom", "Point");

    @Test
    void recordPatternRejectsUnboundComponent() {
        assertThatThrownBy(() -> new RecordPattern(
                        Types.of(POINT), List.of(new TypePattern(PrimitiveTypeRef.INT, Optional.empty()))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void patternLabelRejectsUnboundTypePattern() {
        assertThatThrownBy(() ->
                        new PatternLabel(new TypePattern(PrimitiveTypeRef.INT, Optional.empty()), Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
