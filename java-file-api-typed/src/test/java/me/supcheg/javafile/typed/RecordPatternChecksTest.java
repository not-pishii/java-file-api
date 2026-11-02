package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Bagged_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Blank_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Bounded_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Boxed_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Disc_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Line_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Piece_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Point_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wrapped_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.function.Function;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.JavacVerdict.asJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Construction-time checks of a record pattern (JLS 14.30.1), told against
/// javac ([JavacVerdict]): the record deconstructs into one pattern per
/// component, each of a type the component has, and the type is a record.
class RecordPatternChecksTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile declare(Function<TypedClassBuilder<Object>, TypedClassBodies<Object>> members) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, Unbranded.spec(members));
    }

    /// Declares `static int m(Object o)` whose body is `body`, given the then-branch and the operand.
    private static JavaFile intMethod(java.util.function.BiConsumer<Body<Prim.Int>, Var<Object>> body) {
        return declare(cb -> {
            var m = cb.declareStaticMethod("m", PrimitiveToken.INT, Object_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(m, (b, o) -> {
                body.accept(b, o);
                return b.return_(literal(0));
            });
            return bodies;
        });
    }

    @Test
    void aRecordPatternHasOnePatternPerComponent() {
        var x = Patterns.binding(PrimitiveToken.INT);
        classAsJavac(
                IllegalArgumentException.class,
                () -> intMethod((b, o) -> b.ifInstanceOf(
                        o, Patterns.record(Point_.TOKEN, Patterns.type(x)), then -> then.return_(x.var()))),
                "a record pattern of me.supcheg.javafile.typed.fixtures.Point has 1 component pattern(s), but the"
                        + " record declares 2: x, y",
                "",
                "static int m(Object o) { if (o instanceof Point(int x)) { return x; } return 0; }",
                "incorrect number of nested patterns");
    }

    @Test
    void aComponentPatternMatchesTheTypeOfItsComponent() {
        var text = Patterns.binding(String_.TOKEN);
        var y = Patterns.binding(PrimitiveToken.INT);
        classAsJavac(
                IllegalArgumentException.class,
                () -> intMethod((b, o) -> b.ifInstanceOf(
                        o,
                        Patterns.record(Point_.TOKEN, Patterns.type(text), Patterns.type(y)),
                        then -> then.return_(y.var()))),
                "matches the component 0, x, of the type int with a pattern of java.lang.String",
                "",
                "static int m(Object o) { if (o instanceof Point(String x, int y)) { return y; } return 0; }",
                "incompatible types: int cannot be converted to java.lang.String");
    }

    @Test
    void aWildcardOfAComponentTypeIsSubstitutedInItsBound() {
        var upper =
                Patterns.binding(UnsafeFacts.interfaceToken(List_.Data.SHAPE, TokenArg.extendsBound(String_.TOKEN)));
        var lower = Patterns.binding(UnsafeFacts.interfaceToken(List_.Data.SHAPE, TokenArg.superBound(String_.TOKEN)));
        var either = Patterns.binding(UnsafeFacts.interfaceToken(List_.Data.SHAPE, TokenArg.unbounded()));
        assertThat(Patterns.record(
                        new Bounded_<>(String_.TOKEN).token,
                        Patterns.type(upper),
                        Patterns.type(lower),
                        Patterns.type(either)))
                .isInstanceOf(TypedPattern.Deconstruction.class);
    }

    @Test
    void aRecordPatternNamesARecord() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> intMethod(
                        (b, o) -> b.ifInstanceOf(o, Patterns.record(Signal_.TOKEN), then -> then.return_(literal(1)))),
                "a record pattern names a record, but me.supcheg.javafile.typed.fixtures.Signal is not one",
                "",
                "static int m(Object o) { if (o instanceof Signal()) { return 1; } return 0; }",
                "deconstruction patterns can only be applied to records");
    }

    @Test
    void aNestedPatternMatchesTheTypeOfItsComponent() {
        var r = Patterns.binding(PrimitiveToken.INT);
        var p = Patterns.binding(Point_.TOKEN);
        classAsJavac(
                IllegalArgumentException.class,
                () -> intMethod((b, o) -> b.ifInstanceOf(
                        o,
                        Patterns.record(Line_.TOKEN, Patterns.record(Disc_.TOKEN, Patterns.type(r)), Patterns.type(p)),
                        then -> then.return_(r.var()))),
                "matches the component 0, start, of the type me.supcheg.javafile.typed.fixtures.Point with a"
                        + " pattern of me.supcheg.javafile.typed.fixtures.Disc",
                "",
                "static int m(Object o) { if (o instanceof Line(Disc(int r), Point p)) { return r; } return 0; }",
                "incompatible types: me.supcheg.javafile.typed.fixtures.Point cannot be converted to me.supcheg.javafile.typed.fixtures.Disc");
    }

    @Test
    void aTypeArgumentThatDoesNotFitTheComponentPatternIsRefused() {
        var text = Patterns.binding(String_.TOKEN);
        classAsJavac(
                IllegalArgumentException.class,
                () -> intMethod((b, o) -> b.ifInstanceOf(
                        o,
                        Patterns.record(new Boxed_<>(Integer_.TOKEN).token, Patterns.type(text)),
                        then -> then.return_(literal(1)))),
                "matches the component 0, value, of the type java.lang.Integer with a pattern of java.lang.String",
                "",
                "static int m(Object o) { if (o instanceof Boxed<Integer>(String s)) { return 1; } return 0; }",
                "incompatible types: java.lang.Integer cannot be converted to java.lang.String");
    }

    @Test
    void aWildcardTypeArgumentLeavesTheComponentUndetermined() {
        var value = Patterns.binding(Object_.TOKEN);
        classUnlikeJavac(
                IllegalArgumentException.class,
                () -> intMethod((b, o) -> b.ifInstanceOf(
                        o, Patterns.record(Boxed_.ANY, Patterns.type(value)), then -> then.return_(literal(1)))),
                "the type argument #0 of its token is a wildcard",
                "",
                "static int m(Object o) { if (o instanceof Boxed<?>(Object x)) { return 1; } return 0; }");
    }

    @Test
    void aRawTokenLeavesTheComponentUndetermined() {
        var value = Patterns.binding(Object_.TOKEN);
        classUnlikeJavac(
                IllegalArgumentException.class,
                () -> intMethod((b, o) -> b.ifInstanceOf(
                        o,
                        Patterns.record(UnsafeFacts.finalClassToken(Boxed_.Data.SHAPE), Patterns.type(value)),
                        then -> then.return_(literal(1)))),
                "the record is generic, and its token carries no type argument",
                "",
                "static int m(Object o) { if (o instanceof Boxed(Object x)) { return 1; } return 0; }");
    }

    @Test
    void aComponentOfATypeVariableIsMatchedByItsTypeAlone() {
        var variable = UnsafeFacts.typeVarToken(Types.typeVar("T"), ConstantDescs.CD_Object);
        var value = Patterns.binding(Object_.TOKEN);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Patterns.record(new Boxed_<>(variable).token, Patterns.type(value)))
                .withMessageContaining("matches the component 0, value, of the type T");
    }

    @Test
    void aBindingIsInScopeInTheBranchThePatternOpens() {
        var x = Patterns.binding(PrimitiveToken.INT);
        assertThatIllegalStateException()
                .isThrownBy(x::var)
                .withMessageContaining("is asked for before the pattern is matched");
    }

    @Test
    void aPrimitivePatternIsNoTypeOfAnInstanceofTest() {
        var number = Patterns.binding(PrimitiveToken.INT);
        asJavac(
                IllegalArgumentException.class,
                () -> intMethod((b, o) -> b.ifInstanceOf(o, Patterns.type(number), then -> then.return_(number.var()))),
                "matches the primitive type int, which no value is tested against",
                "static int m(Object o) { if (o instanceof int i) { return i; } return 0; }",
                "primitive patterns are a preview feature");
    }

    @Test
    void aComponentOfAnArrayIsMatchedByItsExactType() {
        var tags = Patterns.binding(String_.TOKEN);
        var notes = Patterns.binding(List_.ANY);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Patterns.record(Bagged_.TOKEN, Patterns.type(tags), Patterns.type(notes)))
                .withMessageContaining("matches the component 0, tags, of the type java.lang.String[]");
    }

    @Test
    void aComponentThatIsAParameterizationIsMatchedByItsExactType() {
        var tags = Patterns.binding(ArrayToken.of(String_.TOKEN));
        var notes = Patterns.binding(Object_.TOKEN);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Patterns.record(Bagged_.TOKEN, Patterns.type(tags), Patterns.type(notes)))
                .withMessageContaining("matches the component 1, notes");
    }

    @Test
    void aPatternThatIsNoDeclaredTypeIsNoSubtypeOfOne() {
        assertThat(Patterns.isSubtype(String_.TOKEN, PrimitiveToken.INT)).isFalse();
        assertThat(Patterns.coversPermitted(PrimitiveToken.INT, java.lang.constant.ConstantDescs.CD_Object))
                .isFalse();
    }

    @Test
    void aClassComponentIsMatchedByItsTypeOrASubtypeOfIt() {
        var number = Patterns.binding(PrimitiveToken.INT);
        var disc = Patterns.binding(Disc_.TOKEN);
        var mid = Patterns.binding(Mid_.TOKEN);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Patterns.record(Piece_.TOKEN, Patterns.type(number)))
                .withMessageContaining("matches the component 0, at");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Patterns.record(Piece_.TOKEN, Patterns.type(disc)))
                .withMessageContaining("matches the component 0, at, of the type"
                        + " me.supcheg.javafile.typed.fixtures.Point with a pattern of"
                        + " me.supcheg.javafile.typed.fixtures.Disc");
        assertThat(Patterns.record(Wrapped_.TOKEN, Patterns.type(mid))).isInstanceOf(TypedPattern.Deconstruction.class);
    }

    @Test
    void aRecordThatDeclaresNoComponentHasNoPatternOfOne() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Patterns.record(Blank_.TOKEN, Patterns.type(Patterns.binding(PrimitiveToken.INT))))
                .withMessageContaining("the record declares 0: none");
    }

    @Test
    void aBindingIsTheBindingOfOnePattern() {
        var x = Patterns.binding(PrimitiveToken.INT);
        var y = Patterns.binding(PrimitiveToken.INT);
        var pattern = Patterns.record(Point_.TOKEN, Patterns.type(x), Patterns.type(y));
        assertThatIllegalStateException()
                .isThrownBy(() -> intMethod((b, o) -> b.ifInstanceOf(o, pattern, then -> then.return_(literal(1)))
                        .ifInstanceOf(o, pattern, then -> then.return_(literal(2)))))
                .withMessageContaining("is made twice: a pattern is matched where it is written");
    }
}
