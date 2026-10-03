import gen.facts.java.lang.Integer_;
import gen.facts.java.lang.String_;
import gen.facts.java.util.Optional_;
import gen.facts.p.Abs_;
import gen.facts.p.Ov2_;
import gen.facts.p.Ov_;
import gen.facts.p.T2_;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.processor.harness.Typed;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/// Where Java has no call that resolves to the member a fact was made of — javac would call its overload — the
/// typed layer rejects the fact instead of rendering a call that does something else.
public final class Rejected {
    private Rejected() {}

    private static final T2_<String> STRINGS = new T2_<>(String_.TOKEN);

    public static void aGenericMethodGivenTheTypeOfItsOverloadIsRejected(Typed typed) {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, Ov_.TOKEN, o -> call(o, Ov_.m_T(String_.TOKEN), literal("x"))))
                .withMessageContaining("declared m(java.lang.Object) and is m(java.lang.String) here")
                .withMessageContaining("as the overload declared m(java.lang.String) is in p.Ov");
    }

    public static void aBoundedGenericMethodGivenTheTypeOfItsOverloadIsRejected(Typed typed) {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, Ov_.TOKEN, o -> call(o, Ov_.c_T(String_.TOKEN), literal("x"))))
                .withMessageContaining("declared c(java.lang.Comparable) and is c(java.lang.String) here");
    }

    public static void aStaticGenericMethodGivenTheTypeOfItsOverloadIsRejected(Typed typed) {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, Integer_.TOKEN, i -> staticCall(Ov_.s_T(Integer_.TOKEN), i)))
                .withMessageContaining("declared s(java.lang.Object) and is s(java.lang.Integer) here");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        new Optional_<>(String_.TOKEN).token,
                        String_.TOKEN,
                        s -> staticCall(Ov2_.wrap_T(String_.TOKEN), s)))
                .withMessageContaining("declared wrap(java.lang.Object) and is wrap(java.lang.String) here")
                .withMessageContaining("in p.Ov2");
    }

    public static void aMethodOfATypeParameterAndTheOverloadOfItsArgumentAreRejectedBothWays(Typed typed) {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, STRINGS.token, t -> call(t, STRINGS.m_T, literal("x"))))
                .withMessageContaining("declared m(#0) and is m(java.lang.String) here")
                .withMessageContaining("as the overload declared m(java.lang.String) is in p.T2<java.lang.String>");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, STRINGS.token, t -> call(t, STRINGS.m_String, literal("x"))))
                .withMessageContaining("as the overload declared m(#0) is in p.T2<java.lang.String>");
    }

    /// javac would call the concrete `m(String)` for the abstract `m(T)`.
    public static void anAbstractMethodOfATypeParameterIsRejectedToo(Typed typed) {
        Abs_<String> strings = new Abs_<>(String_.TOKEN);

        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, strings.token, t -> call(t, strings.m_T, literal("x"))))
                .withMessageContaining("declared m(#0) and is m(java.lang.String) here");
    }

    public static void aConstructorOfATypeParameterAndTheOverloadOfItsArgumentAreRejectedBothWays(Typed typed) {
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, String_.TOKEN, s -> field(new_(STRINGS.new_T, s), STRINGS.made)))
                .withMessageContaining("declared T2(#0) and is T2(java.lang.String) here")
                .withMessageContaining("as the overload declared T2(java.lang.String) is in p.T2<java.lang.String>");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> typed.render(
                        String_.TOKEN, String_.TOKEN, s -> field(new_(STRINGS.new_String, s), STRINGS.made)))
                .withMessageContaining("as the overload declared T2(#0) is");
    }
}
