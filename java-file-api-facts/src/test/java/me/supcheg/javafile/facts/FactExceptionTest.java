package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.TargetType.Difference;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/// The one exception of the facts: a generator catches a fact that does
/// not hold and a metamodel that does not hold as one, and tells them apart
/// by a switch the compiler checks.
class FactExceptionTest {
    private static final TypeShape<DeclaredKind.EnumClass> COLOR = UnsafeFacts.shape(
            new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", "Color_"), "0123456789abcdef".repeat(4), () -> ""),
            DeclaredKind.ENUM_CLASS,
            ClassDesc.of("p", "Color"),
            List.of(),
            List.of(ConstantDescs.CD_Enum, ConstantDescs.CD_Object),
            List.of(),
            Supertypes.NONE,
            MethodTableTemplate.EMPTY,
            List.of("RED", "GREEN"),
            false);

    /// What a generator that catches the one exception makes of each kind
    /// of it: the switch has no default.
    private static String caught(Runnable use) {
        try {
            use.run();
            return "holds";
        } catch (FactException e) {
            return switch (e) {
                case FactLookupException lookup -> "no " + lookup.sought() + ", but " + lookup.similar();
                case TargetClasspathMismatchException mismatch ->
                    TypeNames.describe(mismatch.type()) + " differs: "
                            + mismatch.differences().stream()
                                    .flatMap(difference -> difference.lines().stream())
                                    .toList();
            };
        }
    }

    @Test
    void aFactThatDoesNotHoldAndAMetamodelThatDoesNotHoldAreCaughtAsOne() {
        EnumToken<Object> color = UnsafeFacts.enumToken(COLOR);
        TargetClasspath withAnotherColor = UnsafeFacts.targetClasspath((shape, origin) ->
                new TargetType.Mismatched(List.of(new Difference.MissingInterface("java.lang.Runnable"))));

        assertThat(caught(() -> color.constant("RED"))).isEqualTo("holds");
        assertThat(caught(() -> color.constant("BLUE"))).isEqualTo("no enum constant p.Color.BLUE, but [RED, GREEN]");
        assertThat(caught(() -> UnsafeFacts.unverifiedClasspath().verify(color)))
                .isEqualTo("holds");
        assertThat(caught(() -> withAnotherColor.verify(color)))
                .isEqualTo("p.Color differs: [missing: interface java.lang.Runnable]");
    }

    @Test
    void theKindsOfItAreTheTwoAndNoOther() {
        assertThat(FactException.class).isAbstract().isNotFinal();
        assertThat(Stream.of(FactException.class.getPermittedSubclasses()))
                .containsExactlyInAnyOrder(FactLookupException.class, TargetClasspathMismatchException.class)
                .allSatisfy(kind -> assertThat(kind).isFinal());
        assertThat(FactException.class.getSuperclass()).isEqualTo(RuntimeException.class);
    }

    @Test
    void aMessageOfAKindIsThatOfTheOneException() {
        assertThatExceptionOfType(FactException.class)
                .isThrownBy(() -> UnsafeFacts.enumToken(COLOR).constant("BLUE"))
                .isInstanceOf(FactLookupException.class)
                .withMessage("no enum constant p.Color.BLUE in the enum token; similar: RED, GREEN");
    }
}
