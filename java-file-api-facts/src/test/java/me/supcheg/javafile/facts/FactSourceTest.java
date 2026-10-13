package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.source.MemberKind;
import me.supcheg.javafile.facts.source.MemberQuery;
import me.supcheg.javafile.facts.source.Resolution;
import me.supcheg.javafile.facts.testfacts.java.lang.String_;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// A [FactSource] introduces a fact only for what its resolver proves, and a
/// constructor fact only in the family its owner allows (§3.1).
class FactSourceTest {

    private static final AbstractClassToken<Number> NUMBER =
            UnsafeFacts.abstractClassToken(Types.of(Number.class), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);

    private final List<MemberQuery> asked = new ArrayList<>();

    private <O> FactSource<O> source(DeclaredToken<O> token, Optional<Object> constant) {
        return UnsafeFacts.factSource(token, query -> {
            asked.add(query);
            return new Resolution(MemberTraits.DEFAULT, constant);
        });
    }

    @Test
    void abstractClassHasOnlySubclassConstructorFacts() {
        FactSource<Number> source = source(NUMBER, Optional.empty());

        SuperCtorRef0<Number> ctor = source.superCtor();

        assertThat(ctor.owner()).isSameAs(NUMBER);
        assertThat(ctor.kind()).isEqualTo(InvocableKind.CONSTRUCTOR);
        assertThat(ctor).hasToString("super java.lang.Number()");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(source::ctor)
                .withMessageContaining("abstract class java.lang.Number")
                .withMessageContaining("superCtor");
    }

    @Test
    void concreteClassHasOnlyNewConstructorFacts() {
        FactSource<String> source = source(String_.TOKEN, Optional.empty());

        CtorRef1<String, String> ctor = source.ctor(String_.TOKEN);

        assertThat(ctor.owner()).isSameAs(String_.TOKEN);
        assertThat(asked).containsExactly(MemberQuery.constructor(String_.TOKEN));
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(source::superCtor)
                .withMessageContaining("class java.lang.String");
    }

    @Test
    void interfaceHasNoConstructorFacts() {
        InterfaceToken<Runnable> runnable = UnsafeFacts.interfaceToken(Types.of(Runnable.class), MethodTable.EMPTY);
        FactSource<Runnable> source = source(runnable, Optional.empty());

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(source::ctor)
                .withMessageContaining("interface java.lang.Runnable");
        assertThat(asked).isEmpty();
    }

    @Test
    void anEnumHasNoConstructorFacts() {
        EnumToken<TimeUnit> state =
                UnsafeFacts.enumToken(Types.of(TimeUnit.class), List.of("SECONDS"), MethodTable.EMPTY);
        FactSource<TimeUnit> source = source(state, Optional.empty());

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(source::ctor)
                .withMessageContaining("enum java.util.concurrent.TimeUnit");
        assertThat(asked).isEmpty();
    }

    @Test
    void aFieldIsAskedForByItsKind() {
        FactSource<String> source = source(String_.TOKEN, Optional.empty());

        FieldRef<String, Prim.Int> field = source.field("hash", PrimitiveToken.INT);
        MutableFieldRef<String, Prim.Int> mutable = source.mutableField("hash", PrimitiveToken.INT);
        MutableStaticFieldRef<Prim.Int> mutableStatic = source.mutableStaticField("count", PrimitiveToken.INT);

        assertThat(asked)
                .containsExactly(
                        MemberQuery.field(MemberKind.FIELD, "hash", PrimitiveToken.INT),
                        MemberQuery.field(MemberKind.MUTABLE_FIELD, "hash", PrimitiveToken.INT),
                        MemberQuery.field(MemberKind.MUTABLE_STATIC_FIELD, "count", PrimitiveToken.INT));
        assertThat(List.of(field.name(), mutable.name(), mutableStatic.name()))
                .containsExactly("hash", "hash", "count");
        assertThat(List.of(field.owner(), mutable.owner(), mutableStatic.owner()))
                .containsOnly(String_.TOKEN);
        assertThat(List.of(field.access(), mutable.access(), mutableStatic.access()))
                .containsOnly(Access.PUBLIC);
    }

    @Test
    void failedResolutionIntroducesNothing() {
        FactSource<String> source = UnsafeFacts.factSource(String_.TOKEN, query -> {
            throw new FactLookupException(query.toString(), "test", List.of());
        });

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> source.method("nam", String_.TOKEN))
                .withMessageContaining("method java.lang.String nam()");
    }

    @Test
    void constantValueMustFitTheFieldType() {
        FactSource<String> intConstant = source(String_.TOKEN, Optional.of(42));

        StaticFieldRef<Prim.Int> field = intConstant.staticField("ANSWER", PrimitiveToken.INT);

        assertThat(field.constantValue()).contains(42);
        assertThat(asked).containsExactly(MemberQuery.field(MemberKind.STATIC_FIELD, "ANSWER", PrimitiveToken.INT));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> intConstant.staticField("ANSWER", PrimitiveToken.LONG))
                .withMessageContaining("not a constant value of type long");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> intConstant.staticField("ANSWER", String_.TOKEN))
                .withMessageContaining("not a constant value of type java.lang.String");
    }

    @Test
    void stringConstantIsAccepted() {
        StaticFieldRef<String> field = UnsafeFacts.constantField(String_.TOKEN, "EMPTY", String_.TOKEN, "");

        assertThat(field.constantValue()).contains("");
        assertThat(ClassDesc.of("java.lang.String")).isEqualTo(field.type().erasure());
    }

    @Test
    void aSourceGivesNoBareFactOfAProtectedFieldOrMethod() {
        MemberTraits ofProtected = new MemberTraits(List.of(), Overridability.OVERRIDABLE, List.of(), Access.PROTECTED);
        FactSource<Number> source =
                UnsafeFacts.factSource(NUMBER, query -> new Resolution(ofProtected, Optional.empty()));

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> source.method("intValue", PrimitiveToken.INT))
                .withMessageContaining("a protected member");
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> source.field("count", PrimitiveToken.INT))
                .withMessageContaining("a protected member");
        // the fact of a protected constructor is not held back: a subclass alone calls super(...)
        assertThat(source.superCtor().traits().access()).isEqualTo(Access.PROTECTED);
    }
}
