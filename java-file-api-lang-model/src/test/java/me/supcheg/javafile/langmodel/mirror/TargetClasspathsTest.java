package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TargetType;
import me.supcheg.javafile.facts.TargetType.Difference;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/// The target classpath of a compilation: how a type is found there, what
/// of it cannot be read, and that the answer is that of [Conformance] for
/// the type the compilation sees.
class TargetClasspathsTest {
    private static final String SVC = "package p; public class Svc { public String m(String s) { return s; } }";
    private static final MethodTableTemplate GENERATED = new MethodTableTemplate(
            Set.of(), Set.of(Signature.of("m", Param.fixed(ConstantDescs.CD_String))), Set.of());

    /// The shape a metamodel generated from `canonical` holds; the reader
    /// looks at its type and its origin alone.
    private static TypeShape<DeclaredKind.OpenClass> metamodel(String binaryName, Canonical canonical) {
        return UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(
                        ClassDesc.of("gen.facts." + binaryName + "_"), canonical.fingerprint(), canonical::text),
                DeclaredKind.OPEN_CLASS,
                ClassDesc.of(binaryName),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                GENERATED,
                List.of(),
                false);
    }

    private static Canonical canonical(String name, String... sources) {
        return Harness.run(env -> Canonical.of(Harness.ok(env.full(name))), sources);
    }

    private static TargetType read(TypeShape<?> shape, Harness.Env env) {
        return TargetClasspaths.reader(env.elements(), env.types()).read(shape, (ShapeOrigin.Metamodel) shape.origin());
    }

    private static TargetType read(TypeShape<?> shape, String... target) {
        return Harness.run(env -> read(shape, env), target);
    }

    private static TargetType absent(String reason) {
        return new TargetType.Mismatched(List.of(new Difference.Absent(reason)));
    }

    @Test
    void theTypeOfTheCompilationIsComparedWithTheMetamodel() {
        TypeShape<?> svc = metamodel("p.Svc", canonical("p.Svc", SVC));

        assertThat(read(svc, SVC)).isSameAs(TargetType.UNCHANGED);
        assertThat(read(svc, SVC.replace("{ public", "{ public void added() {} public")))
                .isInstanceOfSatisfying(
                        TargetType.Changed.class,
                        changed -> assertThat(changed.methods().concreteMethods())
                                .contains(
                                        Signature.of("added"),
                                        Signature.of("m", Param.fixed(ConstantDescs.CD_String))));
        assertThat(read(svc, "package p; public class Svc {}"))
                .isEqualTo(new TargetType.Mismatched(List.of(new Difference.MissingFact(
                        "method public overridable m(java.lang.String) -> java.lang.String throws -", List.of()))));
    }

    @Test
    void aMemberDeprecatedOnTheTargetAloneIsAFactTheTargetDoesNotHold() {
        // code that names a deprecated member is code javac warns of: the target has no fact of it
        TypeShape<?> svc = metamodel("p.Svc", canonical("p.Svc", SVC));

        assertThat(read(svc, SVC.replace("{ public String m", "{ @Deprecated public String m")))
                .isEqualTo(new TargetType.Mismatched(List.of(new Difference.MissingFact(
                        "method public overridable m(java.lang.String) -> java.lang.String throws -", List.of()))));
    }

    @Test
    void aTypeDeprecatedOnTheTargetIsAbsent() {
        TypeShape<?> svc = metamodel("p.Svc", canonical("p.Svc", SVC));

        assertThat(read(svc, "package p; @Deprecated " + SVC.substring("package p; ".length())))
                .isEqualTo(absent("p.Svc is deprecated, and code that names it is code javac warns of"
                        + " (-Xlint:deprecation, removal)"));
    }

    @Test
    void aTypeThatIsNotThereIsAbsent() {
        TypeShape<?> svc = metamodel("p.Svc", canonical("p.Svc", SVC));

        assertThat(read(svc, "package p; public class Other {}"))
                .isEqualTo(absent("type p.Svc not found on the target classpath"));
    }

    /// A module `name` with the class `p.Svc`, in a package it does not export.
    private static Path module(Path root, String name) throws IOException {
        Path sources = Files.createDirectories(root.resolve(name + "-src").resolve("p"));
        Path info = Files.writeString(sources.resolveSibling("module-info.java"), "module " + name + " {}");
        Path svc = Files.writeString(sources.resolve("Svc.java"), SVC);
        Path classes = root.resolve(name);
        int status = ToolProvider.getSystemJavaCompiler()
                .run(null, null, null, "-d", classes.toString(), info.toString(), svc.toString());
        assertThat(status).isZero();
        return classes;
    }

    @Test
    void aTypeOfSeveralModulesOfTheTargetIsAbsent(@TempDir Path root) throws IOException {
        TypeShape<?> svc = metamodel("p.Svc", canonical("p.Svc", SVC));
        String modulePath = module(root, "one") + File.pathSeparator + module(root, "two");

        // neither module exports the package, so the two classes of one name are no clash to javac
        TargetType read = Harness.run(
                List.of("--module-path", modulePath, "--add-modules", "one,two"),
                env -> read(svc, env),
                "package q; class Empty {}");

        assertThat(read).isEqualTo(absent("type p.Svc is in several modules of the target: one, two"));
    }

    @Test
    void aTypeCodeOfAnotherPackageCannotNameIsAbsent() {
        TypeShape<?> svc = metamodel("p.Svc", canonical("p.Svc", SVC));
        TypeShape<?> inner = metamodel(
                "p.Outer$Inner",
                canonical("p.Outer.Inner", "package p; public class Outer { public static class Inner {} }"));

        assertThat(read(svc, SVC.replace("public class", "class"))).isEqualTo(absent("p.Svc is not public"));
        assertThat(read(inner, "package p; class Outer { public static class Inner {} }"))
                .isEqualTo(absent("p.Outer.Inner is nested in p.Outer, which is not public"));
        assertThat(read(svc, "package p; public @interface Svc {}"))
                .isEqualTo(absent("annotation interface p.Svc is not supported yet"));
    }

    @Test
    void aMemberTypeIsFoundByItsBinaryName() {
        String outer = "package p; public class Outer { public static class Inner { public void m() {} } }";
        TypeShape<?> inner = metamodel("p.Outer$Inner", canonical("p.Outer.Inner", outer));

        assertThat(read(inner, outer)).isSameAs(TargetType.UNCHANGED);
        // a top-level type of a package named like the outer type is another type
        assertThat(read(
                        metamodel("p.Outer$Inner", canonical("p.Outer.Inner", outer)),
                        "package p; public class Other {}"))
                .isEqualTo(absent("type p.Outer.Inner not found on the target classpath"));
    }

    @Test
    void aTypeThatMentionsATypeThatIsNotThereIsAbsent() {
        TypeShape<?> svc = metamodel("p.Svc", canonical("p.Svc", SVC));

        TargetType found = Harness.runUnresolved(
                env -> read(svc, env), "package p; public class Svc { public Gone m(String s) { return null; } }");

        assertThat(found).isEqualTo(absent("type p.Svc mentions Gone, which is not on the target classpath"));
    }

    @Test
    void theTypesOfThePlatformAreReadAsTheCompilationSeesThem() {
        Function<String, Canonical> jdk = name -> canonical(name);
        TypeShape<?> string = metamodel("java.lang.String", jdk.apply("java.lang.String"));
        TypeShape<?> entry = metamodel("java.util.Map$Entry", jdk.apply("java.util.Map.Entry"));

        // the same platform: the fast path, for a type of java.base and for a member type
        assertThat(read(string)).isSameAs(TargetType.UNCHANGED);
        assertThat(read(entry)).isSameAs(TargetType.UNCHANGED);

        // an older one: String had no indexOf(int, int, int) before 21
        TargetType older = Harness.run(List.of("--release", "17"), env -> read(string, env));
        assertThat(older)
                .isInstanceOfSatisfying(
                        TargetType.Mismatched.class,
                        mismatched -> assertThat(mismatched.differences())
                                .contains(
                                        new Difference.MissingFact(
                                                "method public final indexOf(int, int, int) -> int throws -",
                                                List.of(
                                                        "method public final indexOf(int) -> int throws -",
                                                        "method public final indexOf(int, int) -> int throws -",
                                                        "method public final indexOf(java.lang.String) -> int throws -",
                                                        "method public final indexOf(java.lang.String, int) -> int throws -"))));
    }

    @Test
    void theTargetClasspathOfAnEnvironmentChecksTheMetamodelsOfItsCompilation() {
        TypeShape<DeclaredKind.OpenClass> svc = metamodel("p.Svc", canonical("p.Svc", SVC));
        OpenClassToken<Object> token = UnsafeFacts.openClassToken(svc);

        Optional<String> held = Harness.run(env -> verified(TargetClasspaths.of(env.processing()), token), SVC);
        MethodTableTemplate methods = Harness.run(
                env -> TargetClasspaths.of(env.processing()).methods(svc),
                SVC.replace("{ public", "{ public String m(Object o) { return null; } public"));
        Optional<String> failed = Harness.run(
                env -> verified(TargetClasspaths.of(env.processing()), token), "package p; public class Svc {}");

        assertThat(held).isEmpty();
        assertThat(methods.concreteMethods())
                .contains(
                        Signature.of("m", Param.fixed(ConstantDescs.CD_String)),
                        Signature.of("m", Param.fixed(ConstantDescs.CD_Object)));
        assertThat(failed).hasValue("""
                        metamodel gen.facts.p.Svc_ does not match p.Svc on the target classpath:
                          missing: method public overridable m(java.lang.String) -> java.lang.String throws -
                        The generator was compiled against another p.Svc than this compilation has (another version\
                         of its library, or another --release). Generate the metamodels against this version: rebuild\
                         the generator against it, or align the versions.""");
    }

    /// The message of the mismatch of a token, if there is one.
    private static Optional<String> verified(TargetClasspath target, OpenClassToken<?> token) {
        try {
            target.verify(token);
            return Optional.empty();
        } catch (TargetClasspathMismatchException e) {
            return Optional.of(e.getMessage());
        }
    }
}
