package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.Diagnostic;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/// The token-only metamodels the processor writes (mini-spec §2.3, §2.6):
/// they compile without a warning under every lint, and their shapes and
/// tokens describe their types.
class GeneratedCodeTest {
    private static final String LIBRARY_KINDS = """
            package p;
            public final class Fin {}
            """;

    @TempDir
    Path lib;

    @TempDir
    Path out;

    private Compilation generate(String facts, String... library) {
        List<Path> classpath =
                library.length == 0 ? List.of() : List.of(ProcessorHarness.library(lib, List.of(), library));
        return ProcessorHarness.succeeded(ProcessorHarness.process(classpath, """
                package gen;

                import me.supcheg.javafile.facts.meta.Facts;

                @Facts({%s})
                class G {}
                """.formatted(facts)));
    }

    private ClassLoader load(Compilation compilation) {
        return ProcessorHarness.compileAndLoad(compilation, out, List.of(lib));
    }

    private static Object field(ClassLoader loader, String className, String field)
            throws ReflectiveOperationException {
        return loader.loadClass(className).getField(field).get(null);
    }

    private static TypeShape<?> shape(ClassLoader loader, String metamodel) throws ReflectiveOperationException {
        return (TypeShape<?>) field(loader, metamodel + "$Data", "SHAPE");
    }

    private static DeclaredToken<?> token(ClassLoader loader, String metamodel) throws ReflectiveOperationException {
        return (DeclaredToken<?>) field(loader, metamodel, "TOKEN");
    }

    @Test
    void aGenericInterfaceHasAnyAndAConstructorPerParameterization() throws Exception {
        Compilation compilation = generate("java.util.List.class");
        Map<String, String> sources = ProcessorHarness.generatedSources(compilation);
        ClassLoader loader = load(compilation);

        TypeShape<?> shape = shape(loader, "gen.facts.java.util.List_");
        assertThat(shape.kind()).isEqualTo(DeclaredKind.INTERFACE);
        assertThat(shape.desc()).isEqualTo(ConstantDescs.CD_List);
        assertThat(shape.typeParameters()).containsExactly(new TypeParam("E", List.of()));
        assertThat(shape.superclasses()).isEmpty();
        assertThat(shape.supertypes().supertype(ConstantDescs.CD_Collection))
                .contains(Types.parameterized(ConstantDescs.CD_Collection, Types.typeVar("E")));
        assertThat(shape.methods().instantiate(List.of(ConstantDescs.CD_String)).abstractMethods())
                .contains(new MethodSignature("add", List.of(ConstantDescs.CD_String)));
        assertThat(shape.methods().instantiate(List.of(ConstantDescs.CD_String)).staticMethods())
                .contains(new MethodSignature("of", List.of()));
        assertThat(shape.origin()).isInstanceOfSatisfying(ShapeOrigin.Metamodel.class, origin -> {
            assertThat(origin.metamodel()).isEqualTo(ClassDesc.of("gen.facts.java.util.List_"));
            assertThat(sha256(origin.canonical().get())).isEqualTo(origin.fingerprint());
            assertThat(sources.get("gen.facts.java.util.List_"))
                    .contains("@Generated(\"me.supcheg.javafile.facts.processor.FactsProcessor\")")
                    .contains("@GeneratedMetamodel(of = List.class, fingerprint = \"" + origin.fingerprint()
                            + "\", complete = false)");
        });

        Class<?> list = loader.loadClass("gen.facts.java.util.List_");
        DeclaredToken<?> any = (DeclaredToken<?>) list.getField("ANY").get(null);
        assertThat(any).isInstanceOf(InterfaceToken.class);
        assertThat(any.typeRef()).isEqualTo(Types.parameterized(ConstantDescs.CD_List, Types.unbounded()));
        assertThat(any.shape()).isSameAs(shape);
        Object ofIntegers = list.getConstructor(RefToken.class).newInstance(PrimitiveToken.INT.boxed());
        DeclaredToken<?> token = (DeclaredToken<?>) list.getField("token").get(ofIntegers);
        assertThat(token.typeRef())
                .isEqualTo(new ParameterizedTypeRef(
                        ConstantDescs.CD_List, List.of(Types.exact(Types.of(ConstantDescs.CD_Integer)))));
        assertThat(token.methods().abstractMethods())
                .contains(new MethodSignature("add", List.of(ConstantDescs.CD_Integer)));
    }

    @Test
    void everyKindGetsItsTokenClass() throws Exception {
        ClassLoader loader = load(generate(
                "p.Fin.class, p.Open.class, p.Abs.class, p.Iface.class, p.Day.class, p.Rec.class, p.Shape.class,"
                        + " p.Outer.Inner.class",
                LIBRARY_KINDS,
                "package p; public class Open {}",
                "package p; public abstract class Abs {}",
                "package p; public interface Iface {}",
                "package p; public enum Day { MON, TUE }",
                "package p; public record Rec(int x) {}",
                "package p; public sealed interface Shape permits Circle {}",
                "package p; public final class Circle implements Shape {}",
                "package p; public class Outer { public static class Inner {} }"));

        assertThat(token(loader, "gen.facts.p.Fin_")).isInstanceOf(FinalClassToken.class);
        assertThat(token(loader, "gen.facts.p.Fin_").typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Fin")));
        assertThat(token(loader, "gen.facts.p.Open_")).isInstanceOf(OpenClassToken.class);
        assertThat(token(loader, "gen.facts.p.Abs_")).isInstanceOf(AbstractClassToken.class);
        assertThat(token(loader, "gen.facts.p.Iface_")).isInstanceOf(InterfaceToken.class);
        assertThat(token(loader, "gen.facts.p.Day_")).isInstanceOf(EnumToken.class);
        assertThat(shape(loader, "gen.facts.p.Day_").enumConstants()).containsExactly("MON", "TUE");
        assertThat(shape(loader, "gen.facts.p.Day_").superclasses())
                .containsExactly(ClassDesc.of("java.lang.Enum"), ConstantDescs.CD_Object);
        assertThat(token(loader, "gen.facts.p.Rec_")).isInstanceOf(FinalClassToken.class);
        assertThat(shape(loader, "gen.facts.p.Shape_").sealed()).isTrue();
        assertThat(shape(loader, "gen.facts.p.Fin_").sealed()).isFalse();
        assertThat(token(loader, "gen.facts.p.Outer_Inner_").typeRef())
                .isEqualTo(Types.of(ClassDesc.of("p.Outer$Inner")));
    }

    @Test
    void theBoundsOfTypeParametersAreDeclaredByTheMetamodel() throws Exception {
        Compilation compilation = generate(
                "p.Box.class",
                "package p; public class Box<T extends Comparable<T>> { public T get() { return null; } }");
        assertThat(ProcessorHarness.generatedSources(compilation).get("gen.facts.p.Box_"))
                .contains("public final class Box_<T extends Comparable<T>> {")
                .contains("public Box_(RefToken<T> t) {");
        ClassLoader loader = load(compilation);
        assertThat(shape(loader, "gen.facts.p.Box_").typeParameters())
                .containsExactly(new TypeParam(
                        "T", List.of(Types.parameterized(ClassDesc.of("java.lang.Comparable"), Types.typeVar("T")))));
        Class<?> box = loader.loadClass("gen.facts.p.Box_");
        Object ofIntegers = box.getConstructor(RefToken.class).newInstance(PrimitiveToken.INT.boxed());
        assertThat(((DeclaredToken<?>) box.getField("token").get(ofIntegers)).typeRef())
                .isEqualTo(Types.parameterized(ClassDesc.of("p.Box"), Types.of(ConstantDescs.CD_Integer)));
        assertThat(field(loader, "gen.facts.java.lang.Comparable_", "ANY")).isInstanceOf(InterfaceToken.class);
    }

    @Test
    void aBoundMentioningATypeThatIsNotPublicMakesTheTokenRaw() throws Exception {
        Compilation compilation = generate(
                "p.Api.class",
                "package p; class Hidden {}",
                "package p; public class Raw<T extends Hidden> {}",
                "package p; public interface Api { Raw<?> raw(); }");
        String source = ProcessorHarness.generatedSources(compilation).get("gen.facts.p.Raw_");
        assertThat(source)
                .contains("@SuppressWarnings({")
                .contains("\"rawtypes\",")
                .contains("public final class Raw_ {")
                .contains("OpenClassToken<Raw> TOKEN");
        ClassLoader loader = load(compilation);
        assertThat(token(loader, "gen.facts.p.Raw_").typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Raw")));
        assertThat(shape(loader, "gen.facts.p.Raw_").typeParameters())
                .containsExactly(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Hidden")))));
    }

    @Test
    void aTypeParameterNamedLikeAClassTheMetamodelUsesIsRenamed() throws Exception {
        Compilation compilation = generate("p.Named.class", "package p; public interface Named<Data, String, Int> {}");
        assertThat(ProcessorHarness.generatedSources(compilation).get("gen.facts.p.Named_"))
                .contains("public final class Named_<Data_, String_, Int> {")
                .contains("InterfaceToken<Named<Data_, String_, Int>> token;")
                .contains("public Named_(RefToken<Data_> data_, RefToken<String_> string_, RefToken<Int> int_) {");
        ClassLoader loader = load(compilation);
        assertThat(shape(loader, "gen.facts.p.Named_").typeParameters())
                .extracting(TypeParam::name)
                .containsExactly("Data", "String", "Int");
    }

    @Test
    void aTypeNamedLikeANestedClassOfTheMetamodelIsWrittenQualified() throws Exception {
        Compilation compilation = generate(
                "p.Data.class, p.Canonical.class, p.Uses.class",
                "package p; public class Data {}",
                "package p; public interface Canonical<Data extends p.Data> {}",
                "package p; public class Uses<T extends Data & Canonical<Data>> {}");
        ClassLoader loader = load(compilation);

        assertThat(token(loader, "gen.facts.p.Data_").typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Data")));
        assertThat(ProcessorHarness.generatedSources(compilation).get("gen.facts.p.Data_"))
                .contains("OpenClassToken<p.Data> TOKEN")
                .contains("static final class Data {");
        assertThat(ProcessorHarness.generatedSources(compilation).get("gen.facts.p.Uses_"))
                .contains("final class Uses_<T extends p.Data & p.Canonical<p.Data>>");
        assertThat(shape(loader, "gen.facts.p.Canonical_").typeParameters())
                .extracting(TypeParam::name)
                .containsExactly("Data");
        assertThat(ProcessorHarness.messages(compilation, Diagnostic.Kind.ERROR))
                .isEmpty();
    }

    @Test
    void theOutputIsDeterministic() {
        String library = "package p; public interface Svc { java.util.Map<String, Integer> m(Comparable<?> c); }";
        Map<String, String> first = ProcessorHarness.generatedSources(generate("p.Svc.class", library));
        Map<String, String> second = ProcessorHarness.generatedSources(
                ProcessorHarness.succeeded(ProcessorHarness.process(List.of(lib), """
                        package gen;
                        @me.supcheg.javafile.facts.meta.Facts(p.Svc.class)
                        class G {}
                        """)));
        assertThat(second).isEqualTo(first);
    }

    @Test
    void aCanonicalFormTooLongForOneConstantIsJoinedFromParts() throws Exception {
        StringBuilder library = new StringBuilder("package p; public class Wide {");
        for (int i = 0; i < 700; i++) {
            library.append(" public void method").append(i).append("(int a, String b) {}");
        }
        library.append(" }");
        Compilation compilation = generate("p.Wide.class", library.toString());
        ClassLoader loader = load(compilation);

        TypeShape<?> shape = shape(loader, "gen.facts.p.Wide_");
        assertThat(shape.origin()).isInstanceOfSatisfying(ShapeOrigin.Metamodel.class, origin -> {
            String text = origin.canonical().get();
            assertThat(text).hasSizeGreaterThan(65535);
            assertThat(sha256(text)).isEqualTo(origin.fingerprint());
        });
        assertThat(ProcessorHarness.generatedSources(compilation).get("gen.facts.p.Wide_"))
                .contains("String.join(\"\", ");
    }

    private static String sha256(String text) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
