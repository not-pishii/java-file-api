package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Base;
import me.supcheg.javafile.typed.fixtures.Sealing;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Captioned_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Faulty_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Labelled_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Local_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Maker_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Risking_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Sealing_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Titled_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Useful_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// The bounds of a type parameter are held to what a class of those
/// supertypes is (JLS 4.9): javac makes a class of them and rejects the type
/// parameter where it would reject the class — a method of the class among
/// the bounds that cannot implement one of an interface, two interfaces
/// that give one method a body each, or one a body and the other none. Each
/// against javac.
class BoundsAsAClassTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Class<IllegalArgumentException> AT_THE_PLACE = IllegalArgumentException.class;

    /// The class `Probe<T extends first & further>`, which declares nothing.
    private static <B> JavaFile bounded(DeclaredToken<B> first, InterfaceToken<?> further) {
        return TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", first),
                new TypedJavaFile.TypedGenericClassSpec1<B>() {
                    @Override
                    public <Self, T extends B> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        h.bound(t, further);
                        return h.extendsObject().members().bodies();
                    }
                });
    }

    @Test
    void theMethodOfTheClassDeclaresNoExceptionTheOneOfTheInterfaceDoesNot() {
        // void risk() throws IOException of Mid, void risk() of Risking
        classAsJavac(
                AT_THE_PLACE,
                () -> bounded(Mid_.TOKEN, Risking_.TOKEN),
                "the type parameter T of class me.supcheg.example.Probe cannot have the bound"
                        + " me.supcheg.javafile.typed.fixtures.Risking",
                "<T extends Mid & Risking>",
                "",
                "overridden method does not throw java.io.IOException");
    }

    @Test
    void theMethodOfTheClassIsNotOfPackageAccess() {
        // String local() of Base, with package access; default String local() of Local
        classAsJavac(
                AT_THE_PLACE,
                () -> bounded(Base_.TOKEN, Local_.TOKEN),
                "has package access",
                "<T extends Base & Local>",
                "",
                "attempting to assign weaker access privileges; was public");
    }

    @Test
    void theMethodOfTheClassIsNotProtected() {
        // protected RuntimeException fault() of Maker, RuntimeException fault() of Faulty
        classAsJavac(
                AT_THE_PLACE,
                () -> bounded(Maker_.TOKEN, Faulty_.TOKEN),
                "is not public",
                "<T extends Maker & Faulty>",
                "",
                "attempting to assign weaker access privileges; was public");
    }

    @Test
    void twoInterfacesGiveOneMethodNoBodyEach() {
        // default String label() of Named and of Titled
        classAsJavac(
                AT_THE_PLACE,
                () -> bounded(Named_.TOKEN, Titled_.TOKEN),
                "a body each",
                "<T extends Named & Titled>",
                "",
                "inherits unrelated defaults for label()");
    }

    @Test
    void oneInterfaceGivesAMethodABodyAndAnotherLeavesItAbstract() {
        // default String label() of Named, String label() of Captioned
        classAsJavac(
                AT_THE_PLACE,
                () -> bounded(Named_.TOKEN, Captioned_.TOKEN),
                "one gives a body and another leaves abstract",
                "<T extends Named & Captioned>",
                "",
                "inherits abstract and default for label()");
    }

    @Test
    void aStaticMethodOfTheClassIsNoneOfAnInterface() {
        // static String util() of Base, default String util() of Useful
        classAsJavac(
                AT_THE_PLACE,
                () -> bounded(Base_.TOKEN, Useful_.TOKEN),
                "one is static and the other an instance method",
                "<T extends Base & Useful>",
                "",
                "overriding method is static");
    }

    @Test
    void theMethodOfTheClassStandsForTheOneOfTheInterface() throws Throwable {
        // final String sealed() of Base, default String sealed() of Sealing: the class wins
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", Base_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<Base>() {
                    @Override
                    public <Self, T extends Base> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        Upcast<T, Sealing> asSealing = h.bound(t, Sealing_.TOKEN);
                        var cb = h.extendsObject().members();
                        var constructor = cb.declareConstructor();
                        var go = cb.declareMethod("go", String_.TOKEN, t);
                        var bodies = cb.bodies();
                        bodies.define(constructor, (b, self) -> b.end());
                        bodies.define(go, (b, self, v) -> b.return_(call(asSealing.apply(v), Sealing_.sealed)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("public final class Probe<T extends Base & Sealing> {");
    }

    @Test
    void methodsTheInterfacesLeaveAbstractHaveOneResult() {
        // stricter than javac, which takes String label() of Captioned for CharSequence label() of Labelled
        classUnlikeJavac(
                AT_THE_PLACE,
                () -> bounded(Labelled_.TOKEN, Captioned_.TOKEN),
                "of one signature and of different results",
                "<T extends Labelled & Captioned>",
                "");
    }
}
