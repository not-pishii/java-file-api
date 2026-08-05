package me.supcheg.javafile.code;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PatternsTest {

    private static final ClassDesc POINT = ClassDesc.of("com.example", "Point");

    @Test
    void typePatternBindsTheMatchedValue() {
        assertThat(Patterns.typePattern(Types.of(POINT), "p"))
                .isEqualTo(new TypePattern(Types.of(POINT), Optional.of("p")));
    }

    @Test
    void recordPatternHoldsComponentPatternsInOrder() {
        Pattern x = Patterns.typePattern(PrimitiveTypeRef.INT, "x");
        assertThat(Patterns.recordPattern(Types.of(POINT), x))
                .isEqualTo(new RecordPattern(Types.of(POINT), List.of(x)));
    }
}
