package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.source.Resolution;
import me.supcheg.javafile.facts.testfacts.java.lang.Object_;
import me.supcheg.javafile.facts.testfacts.java.lang.String_;
import me.supcheg.javafile.facts.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The access of a member fact, and what it means for who gets the fact
/// (JLS 6.6.2): that of a `protected` member is held back in a [Protected],
/// and a constructor a subclass alone calls is a `SuperCtorRefN`.
class ProtectedTest {

    /// Phantom of `fixtures.Base`.
    interface BaseP {}

    private static final OpenClassToken<BaseP> BASE = UnsafeFacts.openClassToken(
            Types.of(ClassDesc.of("fixtures", "Base")), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
    private static final AbstractClassToken<Number> NUMBER =
            UnsafeFacts.abstractClassToken(Types.of(Number.class), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);

    private static final MemberTraits PROTECTED = MemberTraits.OVERRIDABLE.with(Access.PROTECTED);

    @Test
    void aFactIsPublicUnlessItIsToldOtherwise() {
        assertThat(MemberTraits.DEFAULT.access()).isEqualTo(Access.PUBLIC);
        assertThat(MemberTraits.FINAL.access()).isEqualTo(Access.PUBLIC);
        assertThat(MemberTraits.ABSTRACT.access()).isEqualTo(Access.PUBLIC);
        assertThat(String_.length.access()).isEqualTo(Access.PUBLIC);
        assertThat(UnsafeFacts.field(BASE, "f", PrimitiveToken.INT).access()).isEqualTo(Access.PUBLIC);
        assertThat(UnsafeFacts.mutableField(BASE, "f", PrimitiveToken.INT).access())
                .isEqualTo(Access.PUBLIC);
        assertThat(UnsafeFacts.staticField(BASE, "f", PrimitiveToken.INT).access())
                .isEqualTo(Access.PUBLIC);
        assertThat(UnsafeFacts.mutableStaticField(BASE, "f", PrimitiveToken.INT).access())
                .isEqualTo(Access.PUBLIC);
        assertThat(UnsafeFacts.constantField(BASE, "K", PrimitiveToken.INT, 1).access())
                .isEqualTo(Access.PUBLIC);
        assertThat(UnsafeFacts.constantField(BASE, "S", String_.TOKEN, "s").access())
                .isEqualTo(Access.PUBLIC);
    }

    @Test
    void theAccessOfTheTraitsStaysAsTheOtherTraitsChange() {
        MemberTraits traits = PROTECTED
                .with(Overridability.FINAL)
                .throwing(UnsafeFacts.<Exception>openClassToken(
                        Types.of(Exception.class),
                        List.of(ConstantDescs.CD_Throwable, ConstantDescs.CD_Object),
                        MethodTable.EMPTY))
                .withTypeArgs(String_.TOKEN);

        assertThat(traits.access()).isEqualTo(Access.PROTECTED);
        assertThat(traits.overridability()).isEqualTo(Overridability.FINAL);
        assertThat(traits.with(Access.PUBLIC).access()).isEqualTo(Access.PUBLIC);
        assertThat(UnsafeFacts.method(BASE, "m", String_.TOKEN, traits).access())
                .isEqualTo(Access.PROTECTED);
    }

    @Test
    void theFactOfAProtectedMemberIsHeldBackByItsClass() {
        MethodRef0<BaseP, String> hook = UnsafeFacts.method(BASE, "hook", String_.TOKEN, PROTECTED);
        Protected<BaseP, MethodRef0<BaseP, String>> held = UnsafeFacts.protected_(BASE, hook);

        assertThat(held.owner()).isSameAs(BASE);
        assertThat(held.fact()).isSameAs(hook);
        assertThat(held).hasToString("protected java.lang.String fixtures.Base.hook()");
    }

    @Test
    void theFieldsOfEveryKindAreHeldBack() {
        FieldRef<BaseP, Prim.Int> field = UnsafeFacts.field(BASE, "f", PrimitiveToken.INT, Access.PROTECTED);
        MutableFieldRef<BaseP, Prim.Int> mutable =
                UnsafeFacts.mutableField(BASE, "m", PrimitiveToken.INT, Access.PROTECTED);
        StaticFieldRef<Prim.Int> staticField = UnsafeFacts.staticField(BASE, "s", PrimitiveToken.INT, Access.PROTECTED);
        MutableStaticFieldRef<Prim.Int> mutableStatic =
                UnsafeFacts.mutableStaticField(BASE, "ms", PrimitiveToken.INT, Access.PROTECTED);
        StaticFieldRef<Prim.Int> constant =
                UnsafeFacts.constantField(BASE, "K", PrimitiveToken.INT, 7, Access.PROTECTED);
        StaticFieldRef<String> text = UnsafeFacts.constantField(BASE, "S", String_.TOKEN, "s", Access.PROTECTED);

        assertThat(List.<MemberFact>of(field, mutable, staticField, mutableStatic, constant, text))
                .allSatisfy(fact -> {
                    assertThat(fact.access()).isEqualTo(Access.PROTECTED);
                    assertThat(UnsafeFacts.protected_(BASE, fact).fact()).isSameAs(fact);
                });
        assertThat(constant.constantValue()).contains(7);
        assertThat(text.constantValue()).contains("s");
        assertThat(UnsafeFacts.protected_(BASE, field)).hasToString("protected int fixtures.Base.f");
        assertThat(UnsafeFacts.protected_(BASE, staticField)).hasToString("protected static int fixtures.Base.s");
    }

    @Test
    void aFactThatIsNotOfAProtectedMemberIsNotHeldBack() {
        MethodRef0<BaseP, String> publicOne = UnsafeFacts.method(BASE, "m", String_.TOKEN, MemberTraits.DEFAULT);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.protected_(BASE, publicOne))
                .withMessage("java.lang.String fixtures.Base.m() is not a protected member: its fact tells it is"
                        + " public");
    }

    @Test
    void aFactIsHeldBackByTheClassThatOwnsItAlone() {
        MethodRef0<Number, String> ofNumber = UnsafeFacts.method(NUMBER, "m", String_.TOKEN, PROTECTED);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.protected_(BASE, ofNumber))
                .withMessage("java.lang.String java.lang.Number.m() is a member of java.lang.Number, not of"
                        + " fixtures.Base");
    }

    @Test
    void theProtectedMembersOfObjectAreHeldBackInItsMetamodel() {
        Protected<Object, MethodRef0<Object, Object>> clone = Object_.clone;

        assertThat(clone.owner()).isSameAs(Object_.TOKEN);
        assertThat(clone.fact().traits().throwsTypes()).hasSize(1);
        assertThat(clone).hasToString("protected java.lang.Object java.lang.Object.clone()");
    }

    @Test
    void aConstructorForASubclassIsOfAClassThatCanBeExtendedAbstractOrNot() {
        SuperCtorRef0<BaseP> ofAnOpenClass = UnsafeFacts.superCtor(BASE, MemberTraits.FINAL.with(Access.PROTECTED));
        SuperCtorRef1<Number, String> ofAnAbstractClass =
                UnsafeFacts.superCtor(NUMBER, String_.TOKEN, MemberTraits.FINAL);

        assertThat(ofAnOpenClass.owner()).isSameAs(BASE);
        assertThat(ofAnOpenClass.access()).isEqualTo(Access.PROTECTED);
        assertThat(ofAnOpenClass).hasToString("super fixtures.Base()");
        assertThat(ofAnAbstractClass.access()).isEqualTo(Access.PUBLIC);
        assertThat(ofAnAbstractClass).hasToString("super java.lang.Number(java.lang.String)");
        assertThat(UnsafeFacts.ctor(BASE, MemberTraits.FINAL)).hasToString("new fixtures.Base()");
    }

    @Test
    void aSourceGivesAConstructorForASubclassOfAClassThatCanBeExtendedAlone() {
        FactSource<BaseP> open = UnsafeFacts.factSource(BASE, _ -> new Resolution(PROTECTED, Optional.empty()));
        FactSource<String> finalClass =
                UnsafeFacts.factSource(String_.TOKEN, _ -> new Resolution(MemberTraits.DEFAULT, Optional.empty()));

        assertThat(open.superCtor().owner()).isSameAs(BASE);
        assertThat(open.superCtor().access()).isEqualTo(Access.PROTECTED);
        assertThat(open.ctor().owner()).isSameAs(BASE);
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(finalClass::superCtor)
                .withMessageContaining("constructors for a subclass")
                .withMessageContaining("class java.lang.String");
    }

    @Test
    void theClassesThatCanBeExtendedAreTheOpenAndTheAbstractOnes() {
        List<ClassToken<?>> tokens = List.of(BASE, NUMBER, String_.TOKEN, new ArrayList_<>(String_.TOKEN).token);

        assertThat(tokens)
                .extracting(token -> switch (token) {
                    case ExtendableClassToken<?> _ -> "extendable";
                    case FinalClassToken<?> _ -> "final";
                    case EnumToken<?> _ -> "enum";
                })
                .containsExactly("extendable", "extendable", "final", "extendable");
    }
}
