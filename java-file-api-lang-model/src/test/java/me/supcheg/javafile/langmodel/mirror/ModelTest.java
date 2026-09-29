package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.langmodel.mirror.FieldModel.Mutability;
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
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false,
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

    @Test
    void aModelWithoutMembersHasNone() {
        ClassDesc desc = ClassDesc.of("p.T");
        FieldModel field = new FieldModel("x", false, Types.INT, Mutability.MUTABLE);
        SkippedMember skipped = new SkippedMember("field y", "why");

        assertThatThrownBy(() -> type(desc, MemberFilter.NONE, List.of(field), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> type(desc, MemberFilter.NONE, List.of(), List.of(skipped)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(type(desc, MemberFilter.DECLARED_PUBLIC, List.of(field), List.of(skipped))
                        .members())
                .containsExactly(field);
    }

    @Test
    void methodsHaveAtMostTwelveParametersAndStaticOnesAreFinal() {
        assertThatThrownBy(() -> new MethodModel(
                        "m", false, List.of(), Optional.empty(), THIRTEEN, List.of(), Overridability.OVERRIDABLE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("method m has 13 parameters, more than 12");
        assertThatThrownBy(() -> new MethodModel(
                        "m", true, List.of(), Optional.empty(), List.of(), List.of(), Overridability.OVERRIDABLE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("static method m cannot be OVERRIDABLE");
        assertThatThrownBy(() -> new MethodModel(
                        "not a name", false, List.of(), Optional.empty(), List.of(), List.of(), Overridability.FINAL))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructorsHaveAtMostTwelveParameters() {
        assertThatThrownBy(() -> new CtorModel(List.of(), THIRTEEN, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("a constructor has 13 parameters, more than 12");
    }

    @Test
    void constantsAreStaticAndOfAConstantType() {
        assertThatThrownBy(() -> new FieldModel("x", false, Types.INT, new Mutability.Constant(1)))
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
