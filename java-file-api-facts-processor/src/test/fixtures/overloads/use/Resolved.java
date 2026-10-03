import gen.facts.java.lang.Integer_;
import gen.facts.java.lang.String_;
import gen.facts.java.util.Optional_;
import gen.facts.p.Ov2_;
import gen.facts.p.Ov_;
import gen.facts.p.SubOv_;
import gen.facts.p.T2_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Ov;
import p.SubOv;
import p.T2;

import java.util.Optional;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;

/// Overloads that erase alike under some type arguments — a generic method and one of the type it is given, a
/// method of a type parameter and one of the type argument, the same for constructors and `static` methods. A fact is
/// the member it was made of: the typed layer renders a call javac resolves to that member, which is compiled and
/// run here (what is rejected is in `Rejected`).
public final class Resolved {
    private Resolved() {}

    private static final T2_<Integer> INTEGERS = new T2_<>(Integer_.TOKEN);
    private static final T2_<String> STRINGS = new T2_<>(String_.TOKEN);

    // a generic method and its overload of the type it is given

    public static void aGenericMethodGivenAnotherTypeIsTheGenericMethod(Typed typed) {
        assertThat(typed.apply(
                        String_.TOKEN,
                        Ov_.TOKEN,
                        o -> call(o, Ov_.m_T(Integer_.TOKEN), literalNull(Integer_.TOKEN)),
                        new Ov()))
                .isEqualTo("generic");
    }

    public static void theOverloadOfATypeIsTheOverloadJavacResolves(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Ov_.TOKEN, o -> call(o, Ov_.m_String, literal("x")), new Ov()))
                .isEqualTo("string");
    }

    public static void aBoundedGenericMethodGivenAnotherTypeIsTheGenericMethod(Typed typed) {
        assertThat(typed.apply(
                        String_.TOKEN,
                        Ov_.TOKEN,
                        o -> call(o, Ov_.c_T(Integer_.TOKEN), literalNull(Integer_.TOKEN)),
                        new Ov()))
                .isEqualTo("generic");
    }

    public static void aGenericMethodWithoutAnOverloadTakesAnyTypeArgument(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Ov_.TOKEN, o -> call(o, Ov_.solo_T(String_.TOKEN), literal("x")), new Ov()))
                .isEqualTo("solo");
        assertThat(typed.render(String_.TOKEN, Ov_.TOKEN, o -> call(o, Ov_.solo_T(String_.TOKEN), literal("x"))))
                .contains(".<String>solo(\"x\")");
    }

    /// `SubOv` adds `solo(String)`, which javac would prefer for a `String`: the call is made through the owner.
    public static void anOverloadTheReceiversTypeAddsIsLeftOutByCallingThroughTheOwner(Typed typed) {
        assertThat(typed.render(
                        String_.TOKEN, SubOv_.TOKEN, o -> call(o, Ov_.solo_T(String_.TOKEN), literal("x"))))
                .contains("((Ov) v0).<String>solo(\"x\")");
        assertThat(typed.apply(
                        String_.TOKEN, SubOv_.TOKEN, o -> call(o, Ov_.solo_T(String_.TOKEN), literal("x")), new SubOv()))
                .isEqualTo("solo");
    }

    // static

    public static void aStaticGenericMethodGivenAnotherTypeIsTheGenericMethod(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, String_.TOKEN, s -> staticCall(Ov_.s_T(String_.TOKEN), s), "x"))
                .isEqualTo("generic");
        assertThat(typed.apply(
                        new Optional_<>(Integer_.TOKEN).token,
                        Integer_.TOKEN,
                        i -> staticCall(Ov2_.wrap_T(Integer_.TOKEN), i),
                        1))
                .isEqualTo(Optional.of(1));
    }

    public static void aStaticOverloadOfATypeIsTheOverloadJavacResolves(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Integer_.TOKEN, i -> staticCall(Ov_.s_Integer, i), 1))
                .isEqualTo("integer");
    }

    // a method of a type parameter and its overload of the type argument

    public static void underAnotherTypeArgumentTheMethodOfTheTypeParameterIsTheOneJavacResolves(Typed typed) {
        assertThat(typed.apply(
                        String_.TOKEN,
                        INTEGERS.token,
                        t -> call(t, INTEGERS.m_T, literalNull(Integer_.TOKEN)),
                        new T2<Integer>()))
                .isEqualTo("t");
        assertThat(typed.apply(String_.TOKEN, INTEGERS.token, t -> call(t, INTEGERS.m_String, literal("x")), new T2<Integer>()))
                .isEqualTo("string");
    }

    /// The only method of its name is found in the table: no cast.
    public static void theOnlyMethodOfItsNameNeedsNoCast(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, STRINGS.token, t -> call(t, STRINGS.one_T, literal("x")), new T2<String>()))
                .isEqualTo("one");
        assertThat(typed.render(String_.TOKEN, STRINGS.token, t -> call(t, STRINGS.one_T, literal("x"))))
                .contains("v0.one(\"x\")");
    }

    // constructors

    public static void aConstructorUnderAnotherTypeArgumentIsTheOneJavacResolves(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Integer_.TOKEN, i -> field(new_(INTEGERS.new_T, i), INTEGERS.made), 1))
                .isEqualTo("t");
        assertThat(typed.apply(String_.TOKEN, String_.TOKEN, s -> field(new_(INTEGERS.new_String, s), INTEGERS.made), "x"))
                .isEqualTo("string");
    }

    public static void aConstructorWithoutParametersIsTheOneJavacResolves(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, String_.TOKEN, s -> field(new_(STRINGS.new_), STRINGS.made), "x"))
                .isEqualTo("none");
    }
}
