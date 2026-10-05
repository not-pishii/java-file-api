import gen.facts.java.lang.String_;
import gen.facts.p.Base_;
import gen.facts.p.Box_;
import gen.facts.p.Dep_;
import gen.facts.p.Fn_;
import gen.facts.p.Lib_;
import gen.facts.p.Svc_;
import java.lang.constant.ClassDesc;
import java.util.function.Function;
import me.supcheg.javafile.facts.FactException;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TargetType.Difference;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.typed.Expr;
import me.supcheg.javafile.typed.TypedClassBuilder;
import me.supcheg.javafile.typed.TypedJavaFile;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/// The metamodels are generated from `lib/`; the typed layer renders
/// against `lib/` itself, or against a version of `targets/`, as a generator
/// compiled against one version of a library and run in a compilation that
/// has another does. Each check is a change of the library and what comes of
/// it: the metamodel holds, holds with the method table of the version, or
/// the class is not rendered.
public final class Versions {
    private Versions() {}

    /// `new Svc().m(s)`.
    private static Expr<String> m(Expr<String> s) {
        return call(new_(Svc_.new_), Svc_.m_String, s);
    }

    /// `new Svc().only(s)`: a `String` for the `Object` of `only(Object)`.
    private static Expr<String> only(Expr<String> s) {
        return call(new_(Svc_.new_), Svc_.only_Object, s);
    }

    private static String rendered(Typed typed, Function<Expr<String>, Expr<String>> body) {
        return typed.render(String_.TOKEN, String_.TOKEN, body);
    }

    private static Object applied(Typed typed, Function<Expr<String>, Expr<String>> body) {
        return typed.apply(String_.TOKEN, String_.TOKEN, body, "a");
    }

    public static void theVersionTheMetamodelsWereGeneratedFromIsUnchangedAndReadOnce(Typed typed) {
        // the result and the parameter, then the receiver, the owner of the constructor and of the method
        assertThat(typed.verified(String_.TOKEN, String_.TOKEN, Versions::m))
                .containsExactly("java.lang.String: unchanged", "p.Svc: unchanged");
        assertThat(applied(typed, Versions::m)).isEqualTo("m(String) a");
    }

    public static void aMethodOfAnotherNameIsAddedAndTheMetamodelHolds(Typed typed) {
        Typed added = typed.against("added-method");

        assertThat(added.verified(String_.TOKEN, String_.TOKEN, Versions::m))
                .containsExactly("java.lang.String: unchanged", "p.Svc: changed");
        assertThat(applied(added, Versions::m)).isEqualTo("m(String) a");
    }

    public static void anOverloadIsAddedAndTheArgumentIsCastToTheParameterOfTheFact(Typed typed) {
        Typed added = typed.against("added-overload");

        // only(Object) is the one candidate where the metamodel was generated, so the String needs no cast
        assertThat(rendered(typed, Versions::only)).contains("new Svc().only(v0)");
        assertThat(applied(typed, Versions::only)).isEqualTo("only(Object) a");
        // the version has only(String) too, which javac would choose for a String: the table is the version's
        assertThat(rendered(added, Versions::only)).contains("new Svc().only((Object) v0)");
        assertThat(applied(added, Versions::only)).isEqualTo("only(Object) a");
    }

    public static void aRemovedMethodIsMissing(Typed typed) {
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("removed-method"), Versions::m))
                .withMessage(
                        """
                        metamodel gen.facts.p.Svc_ does not match p.Svc on the target classpath:
                          missing: method overridable m(java.lang.String) -> java.lang.String throws -
                        The generator was compiled against another p.Svc than this compilation has (another version\
                         of its library, or another --release). Generate the metamodels against this version: rebuild\
                         the generator against it, or align the versions.""")
                .satisfies(e -> {
                    assertThat(e.type()).isEqualTo(ClassDesc.of("p", "Svc"));
                    assertThat(e.metamodel().metamodel()).isEqualTo(ClassDesc.of("gen.facts.p", "Svc_"));
                    assertThat(e.differences())
                            .containsExactly(new Difference.MissingFact(
                                    "method overridable m(java.lang.String) -> java.lang.String throws -",
                                    java.util.List.of()));
                });
    }

    public static void theMetamodelIsCheckedWholeSoAFactThatIsNotUsedFailsItToo(Typed typed) {
        // only(Object) is there in the version; m(String), which Svc_ has a fact of too, is not
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("removed-method"), Versions::only))
                .withMessageContaining("missing: method overridable m(java.lang.String)");
    }

    public static void aParameterOfAnotherTypeIsAChangedMethod(Typed typed) {
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("changed-parameter"), Versions::m))
                .withMessageContaining(
                        """
                          changed: method overridable m(java.lang.String) -> java.lang.String throws -
                            found: method overridable m(java.lang.CharSequence) -> java.lang.String throws -
                        """);
    }

    public static void aCheckedExceptionAddedIsAChangedMethod(Typed typed) {
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("added-exception"), Versions::m))
                .withMessageContaining(
                        """
                          changed: method overridable close() -> void throws -
                            found: method overridable close() -> void throws java.io.IOException
                        """);
    }

    public static void aClassThatIsAbstractIsOfAnotherKind(Typed typed) {
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("abstract-class"), Versions::m))
                .withMessageContaining(
                        """
                          changed: type
                            generated against: p.Svc open-class sealed=no
                            target: p.Svc abstract-class sealed=no
                        """);
    }

    public static void aFunctionalInterfaceWithAnotherAbstractMethodHasNoSuchSam(Typed typed) {
        Function<Expr<p.Fn>, Expr<String>> applied = fn -> call(fn, Fn_.apply_String, literal("a"));

        assertThat(typed.render(String_.TOKEN, Fn_.TOKEN, applied)).contains("v0.apply(\"a\")");
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> typed.against("second-abstract-method").render(String_.TOKEN, Fn_.TOKEN, applied))
                .withMessageContaining("metamodel gen.facts.p.Fn_ does not match p.Fn on the target classpath:\n"
                        + "  missing: sam apply(java.lang.String) -> java.lang.String throws -\n");
    }

    public static void aTypeThatIsRemovedIsNotFound(Typed typed) {
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("removed-type"), Versions::m))
                .withMessageContaining("metamodel gen.facts.p.Svc_ does not match p.Svc on the target classpath:\n"
                        + "  type p.Svc not found on the target classpath\n");
    }

    public static void aSuperclassThatIsNoLongerOneFailsTheCallOfWhatWasInherited(Typed typed) {
        Function<Expr<String>, Expr<String>> inherited = s -> call(new_(Svc_.new_), Base_.inherited);

        assertThat(applied(typed, inherited)).isEqualTo("inherited");
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("changed-superclass"), inherited))
                .withMessageContaining(
                        """
                          changed: superclasses
                            generated against: p.Base; java.lang.Object
                            target: java.lang.Object
                        """);
    }

    public static void anInterfaceThatIsNoLongerImplementedFailsTheArgumentOfItsType(Typed typed) {
        // javac took a Svc for a Marker when the generator was compiled; nothing else of Svc tells it is one
        Function<Expr<String>, Expr<String>> taken = s -> staticCall(Lib_.take_Marker, new_(Svc_.new_));

        assertThat(applied(typed, taken)).isEqualTo("taken");
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("removed-interface"), taken))
                .withMessageStartingWith(
                        """
                        metamodel gen.facts.p.Svc_ does not match p.Svc on the target classpath:
                          missing: interface p.Marker
                        The generator""");
    }

    public static void anInterfaceThatIsImplementedBesideLeavesTheMetamodelToHold(Typed typed) {
        Function<Expr<String>, Expr<String>> taken = s -> staticCall(Lib_.take_Marker, new_(Svc_.new_));
        Typed added = typed.against("added-interface");

        // a Svc is a Marker there as it was: what it is besides takes nothing of the metamodel away
        assertThat(added.verified(String_.TOKEN, String_.TOKEN, taken)).contains("p.Svc: changed");
        assertThat(applied(added, taken)).isEqualTo("taken");
        assertThat(applied(added, Versions::m)).isEqualTo("m(String) a");
    }

    public static void anOverloadOfAnInterfaceThatIsImplementedBesideIsACandidateOfTheVersion(Typed typed) {
        Typed added = typed.against("added-interface");

        // the interface has a default only(String), which javac would choose for a String
        assertThat(rendered(typed, Versions::only)).contains("new Svc().only(v0)");
        assertThat(rendered(added, Versions::only)).contains("new Svc().only((Object) v0)");
        assertThat(applied(added, Versions::only)).isEqualTo("only(Object) a");
    }

    public static void anInterfaceThatIsNowThatOfAnInterfaceTheTypeImplementsIsThereAsBefore(Typed typed) {
        Function<Expr<String>, Expr<String>> taken = s -> staticCall(Lib_.take_Marker, new_(Svc_.new_));
        Typed inherited = typed.against("inherited-interface");

        assertThat(inherited.verified(String_.TOKEN, String_.TOKEN, taken)).contains("p.Svc: changed");
        assertThat(applied(inherited, taken)).isEqualTo("taken");
    }

    public static void aConstantOfAnotherValueIsAChangedField(Typed typed) {
        assertThat(typed.apply(PrimitiveToken.INT, String_.TOKEN, s -> staticField(Svc_.LIMIT), "a"))
                .isEqualTo(3);
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> typed.against("changed-constant")
                        .render(PrimitiveToken.INT, String_.TOKEN, s -> staticField(Svc_.LIMIT)))
                .withMessageContaining(
                        """
                          changed: field static constant int LIMIT = 3
                            found: field static constant int LIMIT = 4
                        """);
    }

    public static void anOverloadAddedThatErasesAsTheFactDoesIsRejectedByTheTableOfTheVersion(Typed typed) {
        Box_<String> box = new Box_<>(String_.TOKEN);
        Function<Expr<String>, Expr<String>> put = s -> call(new_(box.new_), box.put_T, s);
        Typed added = typed.against("added-twin");

        assertThat(applied(typed, put)).isEqualTo("put(T) a");
        // Box_ holds of the version: put(T) is there. But a Box<String> has put(String) too there
        assertThat(added.verified(String_.TOKEN, String_.TOKEN, put)).contains("p.Box: changed");
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> rendered(added, put))
                .withMessageContaining("put(java.lang.String)");
    }

    public static void aMismatchAndARejectedFactAreCaughtAsOneAndToldApart(Typed typed) {
        Box_<String> box = new Box_<>(String_.TOKEN);
        Function<Expr<String>, Expr<String>> put = s -> call(new_(box.new_), box.put_T, s);

        assertThat(caught(() -> rendered(typed.against("removed-method"), Versions::m)))
                .isEqualTo("mismatch of Svc: 1");
        assertThat(caught(() -> rendered(typed.against("added-twin"), put)))
                .isEqualTo("lookup: [put(java.lang.String)]");
        assertThat(caught(() -> rendered(typed, Versions::m))).isEqualTo("rendered");
    }

    /// What a generator that catches the one exception of the facts makes
    /// of each kind of it: the switch has no default.
    private static String caught(Runnable rendering) {
        try {
            rendering.run();
            return "rendered";
        } catch (FactException e) {
            return switch (e) {
                case FactLookupException lookup -> "lookup: " + lookup.similar();
                case TargetClasspathMismatchException mismatch ->
                    "mismatch of " + mismatch.type().displayName() + ": "
                            + mismatch.differences().size();
            };
        }
    }

    public static void aMetamodelThatIsNotUsedIsNotChecked(Typed typed) {
        // the version has no Unused, and another Fn: Unused_ and Fn_ do not hold, and nobody asks
        Typed version = typed.against("unused-changes");

        assertThat(version.verified(String_.TOKEN, String_.TOKEN, Versions::m))
                .containsExactly("java.lang.String: unchanged", "p.Svc: unchanged");
        assertThat(applied(version, Versions::m)).isEqualTo("m(String) a");
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> version.render(String_.TOKEN, Fn_.TOKEN, fn -> literal("a")))
                .withMessageContaining("metamodel gen.facts.p.Fn_ does not match p.Fn");
    }

    public static void aTypeArgumentIsCheckedWithItsType(Typed typed) {
        Box_<p.Dep> box = new Box_<>(Dep_.TOKEN);

        assertThat(typed.verified(String_.TOKEN, box.token, b -> literal("a")))
                .containsExactly("java.lang.String: unchanged", "p.Box: unchanged", "p.Dep: unchanged");
        // the rendered class names Dep only as the type argument of its parameter
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> typed.against("final-dep").render(String_.TOKEN, box.token, b -> literal("a")))
                .withMessageContaining("metamodel gen.facts.p.Dep_ does not match p.Dep on the target classpath:\n"
                        + "  changed: type\n"
                        + "    generated against: p.Dep open-class sealed=no\n"
                        + "    target: p.Dep final-class sealed=no\n");
    }

    public static void anUnverifiedClasspathRendersOfAMetamodelWhateverTheVersion(Typed typed) {
        // what a generator renders with it does not compile against the version, and nothing tells
        String source = TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), ClassDesc.of("out", "Out"), new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> void build(TypedClassBuilder<Self> cb) {
                                cb.staticMethod("go", String_.TOKEN, String_.TOKEN, (b, s) -> b.return_(m(s)));
                            }
                        })
                .render();

        assertThat(source).contains("return new Svc().m(v0);");
        assertThat(source).isEqualTo(rendered(typed, Versions::m));
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> rendered(typed.against("removed-method"), Versions::m));
    }
}
