package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The heritage of a type: what it holds, which shapes tell one, and which
/// one a target classpath gives.
class HeritageTest {
    private static final String FINGERPRINT = "0123456789abcdef".repeat(4);
    private static final ClassDesc BASE = ClassDesc.of("p", "Base");

    private static Heritage.Method method(String name, Dispatch dispatch) {
        return new Heritage.Method(
                Visibility.PUBLIC,
                dispatch,
                BASE,
                Signature.of(name),
                List.of(),
                List.of(),
                Arity.FIXED,
                Result.NOTHING,
                List.of(),
                Set.of(List.of()),
                List.of());
    }

    private static final Heritage.Told GENERATED = new Heritage.Told(
            List.of(method("run", Dispatch.ABSTRACT)),
            List.of(new Heritage.Constructor(
                    Visibility.PROTECTED, Signature.of("Base"), List.of(), List.of(), Arity.FIXED, List.of())));
    private static final Heritage.Told OF_TARGET =
            new Heritage.Told(List.of(method("run", Dispatch.ABSTRACT), method("stop", Dispatch.ABSTRACT)), List.of());

    private static TypeShape<DeclaredKind.AbstractClass> withHeritage(String name) {
        return UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", name + "_"), FINGERPRINT, () -> "canonical"),
                DeclaredKind.ABSTRACT_CLASS,
                ClassDesc.of("p", name),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                false,
                () -> GENERATED);
    }

    private static TypeShape<DeclaredKind.AbstractClass> withoutHeritage(String name) {
        return UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", name + "_"), FINGERPRINT, () -> "canonical"),
                DeclaredKind.ABSTRACT_CLASS,
                ClassDesc.of("p", name),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false);
    }

    @Test
    void aMethodOfAHeritageIsAMemberOfItsTypeWithAllThatHoldsOfIt() {
        TypeRef strings = Types.array(Types.STRING);
        Heritage.Method format = new Heritage.Method(
                Visibility.PROTECTED,
                Dispatch.CONCRETE,
                BASE,
                Signature.of("format", Param.fixed(ConstantDescs.CD_String.arrayType())),
                List.of(),
                List.of(strings),
                Arity.VARIABLE,
                new Result.Of(Types.STRING),
                List.of(Types.of(Exception.class)),
                Set.of(List.of(ConstantDescs.CD_String.arrayType()), List.of(ConstantDescs.CD_Object.arrayType())),
                List.of(ClassDesc.of("p", "Api")));

        assertThat(format.name()).isEqualTo("format");
        assertThat(format.result()).isEqualTo(new Result.Of(Types.STRING));
        assertThat(format.erasures()).hasSize(2);
        assertThat(List.<Heritage.Member>of(format, GENERATED.constructors().getFirst()))
                .extracting(Heritage.Member::visibility)
                .containsExactly(Visibility.PROTECTED, Visibility.PROTECTED);
        assertThat(List.<Heritage.Member>of(format, GENERATED.constructors().getFirst()))
                .extracting(member -> member.signature().name())
                .containsExactly("format", "Base");
    }

    @Test
    void aHeritageTellsAMethodOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Told(
                        List.of(method("run", Dispatch.ABSTRACT), method("run", Dispatch.FINAL)), List.of()))
                .withMessage("a heritage tells a method once, got [run(), run()]");
    }

    @Test
    void aMemberOfAHeritageIsConsistent() {
        Signature unary = Signature.of("m", Param.fixed(ConstantDescs.CD_int));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Method(
                        Visibility.PUBLIC,
                        Dispatch.FINAL,
                        ConstantDescs.CD_int,
                        Signature.of("m"),
                        List.of(),
                        List.of(),
                        Arity.FIXED,
                        Result.NOTHING,
                        List.of(),
                        Set.of(List.of()),
                        List.of()))
                .withMessage("a member is declared by a class or interface, got int");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Method(
                        Visibility.PUBLIC,
                        Dispatch.FINAL,
                        BASE,
                        unary,
                        List.of(),
                        List.of(),
                        Arity.FIXED,
                        Result.NOTHING,
                        List.of(),
                        Set.of(List.of()),
                        List.of()))
                .withMessage("method m(int) of p.Base has 0 parameters");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Method(
                        Visibility.PUBLIC,
                        Dispatch.FINAL,
                        BASE,
                        unary,
                        List.of(),
                        List.of(Types.INT),
                        Arity.FIXED,
                        Result.NOTHING,
                        List.of(),
                        Set.of(),
                        List.of()))
                .withMessage("method m(int) of p.Base erases to its 1 parameters, got []");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Method(
                        Visibility.PUBLIC,
                        Dispatch.FINAL,
                        BASE,
                        unary,
                        List.of(),
                        List.of(Types.INT),
                        Arity.VARIABLE,
                        Result.NOTHING,
                        List.of(),
                        Set.of(List.of(ConstantDescs.CD_int)),
                        List.of()))
                .withMessage("method m(int) of p.Base is of variable arity, so its last parameter is an array");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Constructor(
                        Visibility.PUBLIC, Signature.of("Base"), List.of(), List.of(), Arity.VARIABLE, List.of()))
                .withMessage("constructor Base() is of variable arity, so its last parameter is an array");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Constructor(
                        Visibility.PUBLIC, Signature.of("Base"), List.of(), List.of(Types.INT), Arity.FIXED, List.of()))
                .withMessage("constructor Base() has 1 parameters");
    }

    @Test
    void onlyAShapeMadeWithAHeritageTellsOne() {
        assertThat(withHeritage("Told").heritage()).isSameAs(GENERATED);
        assertThat(withoutHeritage("Untold").heritage()).isSameAs(Heritage.UNTOLD);
        assertThat(PrimitiveToken.INT.boxed().shape().heritage()).isSameAs(Heritage.UNTOLD);
    }

    @Test
    void theHeritageOfAShapeIsLoadedWhenItIsAskedFor() {
        java.util.concurrent.atomic.AtomicInteger loads = new java.util.concurrent.atomic.AtomicInteger();
        TypeShape<DeclaredKind.Interface> shape = UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", "Api_"), FINGERPRINT, () -> "canonical"),
                DeclaredKind.INTERFACE,
                ClassDesc.of("p", "Api"),
                List.of(),
                List.of(),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                false,
                () -> {
                    loads.incrementAndGet();
                    return GENERATED;
                });

        assertThat(loads).hasValue(0);
        assertThat(shape.heritage()).isSameAs(GENERATED);
        assertThat(loads).hasValue(1);
    }

    @Test
    void aTargetClasspathGivesTheHeritageOfTheTypeItHas() {
        TypeShape<DeclaredKind.AbstractClass> same = withHeritage("Same");
        TypeShape<DeclaredKind.AbstractClass> changed = withHeritage("Changed");
        TypeShape<DeclaredKind.AbstractClass> untold = withoutHeritage("Untold");
        Map<TypeShape<?>, TargetType> answers = Map.of(
                same, TargetType.UNCHANGED,
                changed, new TargetType.Changed(MethodTableTemplate.EMPTY, OF_TARGET),
                untold, new TargetType.Changed(MethodTableTemplate.EMPTY, OF_TARGET));
        TargetClasspath target = UnsafeFacts.targetClasspath((shape, _) -> answers.get(shape));

        assertThat(target.heritage(same)).isSameAs(GENERATED);
        // another version of the type, which every fact still holds of: what a subclass inherits is of that one
        assertThat(target.heritage(changed)).isSameAs(OF_TARGET);
        // a shape that tells none has none on the target either
        assertThat(target.heritage(untold)).isSameAs(Heritage.UNTOLD);
    }

    @Test
    void aClasspathThatChecksNothingGivesTheHeritageOfTheMetamodel() {
        TargetClasspath unverified = UnsafeFacts.unverifiedClasspath();

        assertThat(unverified.heritage(withHeritage("Told"))).isSameAs(GENERATED);
        assertThat(unverified.heritage(withoutHeritage("Untold"))).isSameAs(Heritage.UNTOLD);
    }
}
