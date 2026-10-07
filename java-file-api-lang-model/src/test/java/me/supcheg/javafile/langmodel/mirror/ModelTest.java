package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.langmodel.mirror.FieldModel.Mutability;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The models reject what the translator never makes.
class ModelTest {
    private static final List<TypeRef> THIRTEEN = Collections.nCopies(13, Types.INT);

    private static TypeModel type(
            ClassDesc desc, MemberFilter filter, List<MemberModel> members, List<SkippedMember> skipped) {
        return new TypeModel(
                desc,
                DeclaredKind.INTERFACE,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false,
                Optional.empty(),
                filter,
                members,
                skipped);
    }

    @Test
    void aTypeIsAClassOrInterface() {
        assertThatThrownBy(() -> type(ConstantDescs.CD_int, MemberFilter.NONE, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("a declared type is a class or interface, got int");
    }

    private static TypeModel functional(DeclaredKind kind, boolean sealed, MethodModel sam) {
        return new TypeModel(
                ClassDesc.of("p.T"),
                kind,
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                sealed,
                Optional.of(sam),
                MemberFilter.NONE,
                List.of(),
                List.of());
    }

    private static MethodModel run(boolean isStatic, List<TypeParam> typeParams, Overridability overridability) {
        return new MethodModel(
                "run",
                isStatic,
                typeParams,
                Optional.empty(),
                List.of(),
                List.of(),
                List.of(),
                overridability,
                Access.PUBLIC);
    }

    @Test
    void onlyAnInterfaceThatIsNotSealedHasASam() {
        MethodModel sam = run(false, List.of(), Overridability.ABSTRACT);

        assertThat(functional(DeclaredKind.INTERFACE, false, sam).sam()).contains(sam);
        assertThatThrownBy(() -> functional(DeclaredKind.INTERFACE, true, sam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("only an interface that is not sealed is functional, got T");
        assertThatThrownBy(() -> functional(DeclaredKind.ABSTRACT_CLASS, false, sam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("only an interface that is not sealed is functional, got T");
    }

    @Test
    void aSamIsAbstractAndNotGeneric() {
        for (MethodModel sam : List.of(
                run(true, List.of(), Overridability.FINAL),
                run(false, List.of(), Overridability.OVERRIDABLE),
                run(false, List.of(new TypeParam("X", List.of())), Overridability.ABSTRACT))) {
            assertThatThrownBy(() -> functional(DeclaredKind.INTERFACE, false, sam))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("a single abstract method is abstract and not generic, got run");
        }
    }

    @Test
    void aModelWithoutMembersHasNone() {
        ClassDesc desc = ClassDesc.of("p.T");
        FieldModel field = new FieldModel("x", false, Types.INT, Mutability.MUTABLE, Access.PUBLIC);
        SkippedMember skipped = new SkippedMember("field y", "why");

        assertThatThrownBy(() -> type(desc, MemberFilter.NONE, List.of(field), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> type(desc, MemberFilter.NONE, List.of(), List.of(skipped)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(type(desc, MemberFilter.DECLARED_ACCESSIBLE, List.of(field), List.of(skipped))
                        .members())
                .containsExactly(field);
    }

    @Test
    void methodsHaveAtMostTwelveParametersAndStaticOnesAreFinal() {
        assertThatThrownBy(() -> new MethodModel(
                        "m",
                        false,
                        List.of(),
                        Optional.empty(),
                        THIRTEEN,
                        List.of(),
                        List.of(),
                        Overridability.OVERRIDABLE,
                        Access.PUBLIC))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("method m has 13 parameters, more than 12");
        assertThatThrownBy(() -> new MethodModel(
                        "m",
                        true,
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Overridability.OVERRIDABLE,
                        Access.PUBLIC))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("static method m cannot be OVERRIDABLE");
        assertThatThrownBy(() -> new MethodModel(
                        "not a name",
                        false,
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Overridability.FINAL,
                        Access.PUBLIC))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new MethodModel(
                        "m",
                        false,
                        List.of(),
                        Optional.empty(),
                        List.of(Types.INT),
                        List.of(),
                        List.of(),
                        Overridability.FINAL,
                        Access.PUBLIC))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("method m has 1 parameters, but 0 are declared: []");
    }

    @Test
    void constructorsHaveAtMostTwelveParameters() {
        assertThatThrownBy(() -> new CtorModel(List.of(), THIRTEEN, List.of(), List.of(), Access.PUBLIC))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("a constructor has 13 parameters, more than 12");
        assertThatThrownBy(() -> new CtorModel(List.of(), List.of(Types.INT), List.of(), List.of(), Access.PUBLIC))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("a constructor has 1 parameters, but 0 are declared: []");
    }

    @Test
    void constantsAreStaticAndOfAConstantType() {
        assertThatThrownBy(() -> new FieldModel("x", false, Types.INT, new Mutability.Constant(1), Access.PUBLIC))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("instance field x cannot be a constant");
        assertThatThrownBy(() -> new Mutability.Constant(new Object()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("a constant is a String or a boxed primitive, got java.lang.Object");
        assertThat(List.of('c', (byte) 1, (short) 1, 1, 1L, 1f, 1d, true, "s"))
                .allSatisfy(value ->
                        assertThat(new Mutability.Constant(value).value()).isEqualTo(value));
    }
}
