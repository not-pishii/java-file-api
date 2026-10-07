package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.TargetType.Difference;
import me.supcheg.javafile.type.TypeParam;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A target classpath: which shapes it asks its reader about, how often,
/// and what it makes of the answers.
class TargetClasspathTest {
    private static final String FINGERPRINT = "0123456789abcdef".repeat(4);
    private static final MethodTableTemplate GENERATED = new MethodTableTemplate(
            Set.of(), Set.of(Signature.of("m", Param.fixed(ConstantDescs.CD_String))), Set.of());
    private static final MethodTableTemplate TARGET = new MethodTableTemplate(
            Set.of(),
            Set.of(
                    Signature.of("m", Param.fixed(ConstantDescs.CD_String)),
                    Signature.of("m", Param.fixed(ConstantDescs.CD_Object))),
            Set.of());

    private static final TypeShape<DeclaredKind.OpenClass> SVC = metamodel("Svc", List.of());
    private static final TypeShape<DeclaredKind.OpenClass> DEP = metamodel("Dep", List.of());
    private static final TypeShape<DeclaredKind.OpenClass> BOX =
            metamodel("Box", List.of(new TypeParam("T", List.of())));

    private static TypeShape<DeclaredKind.OpenClass> metamodel(String name, List<TypeParam> typeParameters) {
        return UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", name + "_"), FINGERPRINT, () -> "canonical"),
                DeclaredKind.OPEN_CLASS,
                ClassDesc.of("p", name),
                typeParameters,
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                GENERATED,
                List.of(),
                false);
    }

    /// A reader that answers by the shape and tells what it was asked.
    private record Asked(Map<TypeShape<?>, TargetType> answers, List<TypeShape<?>> shapes) implements TargetReader {
        Asked(Map<TypeShape<?>, TargetType> answers) {
            this(answers, new ArrayList<>());
        }

        @Override
        public TargetType read(TypeShape<?> shape, ShapeOrigin.Metamodel origin) {
            shapes.add(shape);
            return answers.getOrDefault(shape, TargetType.UNCHANGED);
        }
    }

    @Test
    void aShapeOfAMetamodelIsReadOnce() {
        Asked asked = new Asked(Map.of());
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);
        OpenClassToken<Object> svc = UnsafeFacts.openClassToken(SVC);

        target.verify(svc);
        target.verify(UnsafeFacts.openClassToken(SVC));
        target.verify(ArrayToken.of(svc));
        assertThat(target.methods(SVC)).isSameAs(GENERATED);

        assertThat(asked.shapes()).containsExactly(SVC);
    }

    @Test
    void aShapeNothingAsksAboutIsNotRead() {
        Asked asked = new Asked(Map.of(DEP, new TargetType.Mismatched(List.of(new Difference.Absent("gone")))));
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);

        target.verify(UnsafeFacts.openClassToken(SVC));

        assertThat(asked.shapes()).containsExactly(SVC);
    }

    @Test
    void theTypeArgumentsOfATypeAreVerifiedWithIt() {
        Asked asked = new Asked(Map.of());
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);
        OpenClassToken<Object> boxOfSvc =
                UnsafeFacts.openClassToken(BOX, TokenArg.extendsBound(ArrayToken.of(UnsafeFacts.openClassToken(SVC))));
        OpenClassToken<Object> boxOfBoxOfDep = UnsafeFacts.openClassToken(
                BOX,
                TokenArg.exact(UnsafeFacts.openClassToken(BOX, TokenArg.superBound(UnsafeFacts.openClassToken(DEP)))));

        target.verify(boxOfSvc);
        assertThat(asked.shapes()).containsExactly(BOX, SVC);
        target.verify(boxOfBoxOfDep);
        assertThat(asked.shapes()).containsExactly(BOX, SVC, DEP);
        target.verify(UnsafeFacts.openClassToken(BOX, TokenArg.unbounded()));
        target.verify(UnsafeFacts.openClassToken(BOX));
        assertThat(asked.shapes()).containsExactly(BOX, SVC, DEP);
        assertThat(boxOfSvc.typeArguments()).hasSize(1);
        assertThat(UnsafeFacts.openClassToken(BOX).typeArguments()).isEmpty();
    }

    @Test
    void onlyAShapeOfAMetamodelIsRead() {
        Asked asked = new Asked(Map.of());
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);

        FinalClassToken<Object> madeByHand = UnsafeFacts.finalClassToken(UnsafeFacts.shape(
                DeclaredKind.FINAL_CLASS,
                ClassDesc.of("p", "ByHand"),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false));

        // made by hand, built in, of no declared type at all
        target.verify(madeByHand);
        target.verify(PrimitiveToken.INT.boxed());
        target.verify(PrimitiveToken.INT);
        target.verify(PrimitiveToken.INT.array());
        target.verify(UnsafeFacts.typeVarToken(me.supcheg.javafile.type.Types.typeVar("T"), ConstantDescs.CD_Object));

        assertThat(asked.shapes()).isEmpty();
        assertThat(target.methods(madeByHand.shape()))
                .isSameAs(madeByHand.shape().methods());
    }

    @Test
    void theMethodsOfAChangedTypeAreThoseOfTheTarget() {
        TargetClasspath target = UnsafeFacts.targetClasspath(new Asked(Map.of(SVC, new TargetType.Changed(TARGET))));

        target.verify(UnsafeFacts.openClassToken(SVC));

        assertThat(target.methods(SVC)).isSameAs(TARGET);
        assertThat(target.methods(DEP)).isSameAs(GENERATED);
    }

    @Test
    void aMethodTableOfTheTargetIsInTermsOfTheTypeParametersOfTheShape() {
        MethodTableTemplate ofAGenericType =
                new MethodTableTemplate(Set.of(Signature.of("m", Param.var(0))), Set.of(), Set.of());
        TargetClasspath target =
                UnsafeFacts.targetClasspath(new Asked(Map.of(SVC, new TargetType.Changed(ofAGenericType))));

        assertThatIllegalStateException()
                .isThrownBy(() -> target.methods(SVC))
                .withMessageContaining("type parameter #0")
                .withMessageContaining("0 type parameters");
    }

    @Test
    void aMismatchIsThrownEveryTimeTheMetamodelIsMetAndReadOnce() {
        Asked asked = new Asked(Map.of(
                SVC,
                new TargetType.Mismatched(List.of(
                        new Difference.MissingFact("method final m(int) -> void throws -", List.of()),
                        new Difference.MissingFact(
                                "method final n() -> void throws -", List.of("method final n(int) -> void throws -")),
                        new Difference.ChangedFact(
                                "method final o() -> void throws -",
                                "method final o() -> void throws java.io.IOException"),
                        new Difference.ChangedData("superclasses", "java.lang.Object", "p.Base; java.lang.Object"),
                        new Difference.MissingInterface("java.lang.Runnable"),
                        new Difference.ChangedSupertype(
                                "java.lang.Comparable<java.lang.String>", "java.lang.Comparable<java.lang.Object>")))));
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);
        OpenClassToken<Object> svc = UnsafeFacts.openClassToken(SVC);

        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> target.verify(svc))
                .satisfies(e -> {
                    assertThat(e.metamodel()).isSameAs(SVC.origin());
                    assertThat(e.type()).isEqualTo(ClassDesc.of("p", "Svc"));
                    assertThat(e.differences()).hasSize(6);
                })
                .withMessage("""
                        metamodel gen.facts.p.Svc_ does not match p.Svc on the target classpath:
                          missing: method final m(int) -> void throws -
                          missing: method final n() -> void throws -
                            similar: method final n(int) -> void throws -
                          changed: method final o() -> void throws -
                            found: method final o() -> void throws java.io.IOException
                          changed: superclasses
                            generated against: java.lang.Object
                            target: p.Base; java.lang.Object
                          missing: interface java.lang.Runnable
                          changed: supertype java.lang.Comparable<java.lang.String>
                            found: java.lang.Comparable<java.lang.Object>
                        The generator was compiled against another p.Svc than this compilation has (another version\
                         of its library, or another --release). Generate the metamodels against this version: rebuild\
                         the generator against it, or align the versions.""");
        assertThatExceptionOfType(TargetClasspathMismatchException.class).isThrownBy(() -> target.methods(SVC));
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> target.verify(UnsafeFacts.openClassToken(BOX, TokenArg.exact(svc))));

        assertThat(asked.shapes()).containsExactly(SVC, BOX);
    }

    @Test
    void aTypeThatIsNotThereAndAMetamodelOfAnotherFormatAreMismatches() {
        TargetClasspath target = UnsafeFacts.targetClasspath(new Asked(Map.of(
                SVC,
                new TargetType.Mismatched(
                        List.of(new Difference.Absent("type p.Svc not found on the target classpath"))),
                DEP,
                new TargetType.Mismatched(List.of(new Difference.OtherFormat("canonical 1", "canonical 2"))))));

        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> target.verify(UnsafeFacts.openClassToken(SVC)))
                .withMessageContaining("\n  type p.Svc not found on the target classpath\n");
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> target.verify(UnsafeFacts.openClassToken(DEP)))
                .withMessageContaining("\n  format: generated as canonical 1, the target is read as canonical 2\n");
    }

    @Test
    void aMismatchHasADifference() {
        assertThatIllegalArgumentException().isThrownBy(() -> new TargetType.Mismatched(List.of()));
    }

    @Test
    void anUnverifiedClasspathTakesEveryMetamodelForTrue() {
        TargetClasspath target = UnsafeFacts.unverifiedClasspath();

        target.verify(UnsafeFacts.openClassToken(BOX, TokenArg.exact(UnsafeFacts.openClassToken(SVC))));

        assertThat(target.methods(SVC)).isSameAs(GENERATED);
    }
}
