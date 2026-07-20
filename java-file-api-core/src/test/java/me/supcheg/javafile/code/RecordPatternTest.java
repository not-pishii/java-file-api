package me.supcheg.javafile.code;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecordPatternTest {

    private static final ClassDesc POINT = ClassDesc.of("geom", "Point");

    @Test
    void flatTypePatternBindsAName() {
        Pattern pattern = new TypePattern(PrimitiveTypeRef.INT, Optional.of("n"));
        InstanceOfExpr expr = new InstanceOfExpr(new FieldAccessExpr(Optional.empty(), "value"), pattern);
        assertThat(expr.pattern()).isEqualTo(pattern);
    }

    @Test
    void recordPatternDeconstructsComponents() {
        Pattern pattern = new RecordPattern(
                Types.of(POINT),
                List.of(
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("x")),
                        new TypePattern(PrimitiveTypeRef.INT, Optional.of("y"))));
        InstanceOfExpr expr = new InstanceOfExpr(new FieldAccessExpr(Optional.empty(), "shape"), pattern);
        assertThat(((RecordPattern) expr.pattern()).componentPatterns()).hasSize(2);
    }

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

    @Test
    void patternLabelAcceptsBoundTypePattern() {
        PatternLabel label =
                new PatternLabel(new TypePattern(PrimitiveTypeRef.INT, Optional.of("n")), Optional.empty());
        assertThat(label.pattern()).isEqualTo(new TypePattern(PrimitiveTypeRef.INT, Optional.of("n")));
    }
}
