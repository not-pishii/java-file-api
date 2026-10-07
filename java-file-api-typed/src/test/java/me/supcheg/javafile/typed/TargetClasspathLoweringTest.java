package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetClasspathMismatchException;
import me.supcheg.javafile.facts.TargetReader;
import me.supcheg.javafile.facts.TargetType;
import me.supcheg.javafile.facts.TargetType.Difference;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/// Lowering and the target classpath (§5): there is no lowering without
/// one, a metamodel is checked when lowering first meets it and only then,
/// once, and the method table that decides a cast is that of the target.
///
/// The metamodels here are shapes made by hand under the origin of a
/// metamodel, and the target is a reader that answers by the shape: what a
/// compilation answers is the business of `java-file-api-lang-model`.
class TargetClasspathLoweringTest {
    /// The String metamodel of the typed layer itself, which a class is lowered with beside that of the test.
    private static final TypeShape<?> TYPED_STRING =
            me.supcheg.javafile.typed.jdk.facts.java.lang.String_.TOKEN.shape();

    private static final String FINGERPRINT = "0123456789abcdef".repeat(4);

    /// Phantom of `fixtures.Svc`, and of `fixtures.Box<T>`.
    interface SvcP {}

    interface BoxP<T> {}

    private static final Signature M_OF_STRING = Signature.of("m", Param.fixed(ConstantDescs.CD_String));
    private static final Signature M_OF_OBJECT = Signature.of("m", Param.fixed(ConstantDescs.CD_Object));

    private static <K extends DeclaredKind> TypeShape<K> metamodel(
            K kind, String name, List<TypeParam> typeParameters, Set<Signature> methods) {
        return UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.fixtures", name + "_"), FINGERPRINT, () -> ""),
                kind,
                ClassDesc.of("fixtures", name),
                typeParameters,
                List.of(ConstantDescs.CD_Object),
                Supertypes.NONE,
                new MethodTableTemplate(Set.of(), methods, Set.of()),
                List.of(),
                false);
    }

    private static final TypeShape<DeclaredKind.OpenClass> SVC =
            metamodel(DeclaredKind.OPEN_CLASS, "Svc", List.of(), Set.of(M_OF_STRING));
    private static final TypeShape<DeclaredKind.OpenClass> UNUSED =
            metamodel(DeclaredKind.OPEN_CLASS, "Unused", List.of(), Set.of());
    private static final TypeShape<DeclaredKind.OpenClass> BOX =
            metamodel(DeclaredKind.OPEN_CLASS, "Box", List.of(new TypeParam("T", List.of())), Set.of());

    private static final OpenClassToken<SvcP> SVC_TOKEN = UnsafeFacts.openClassToken(SVC);

    /// `String m(String)` of `Svc`.
    private static final MethodRef1<SvcP, String, String> M =
            UnsafeFacts.method(SVC_TOKEN, "m", String_.TOKEN, String_.TOKEN, MemberTraits.DEFAULT);

    /// A reader that answers by the shape and tells what it was asked.
    private record Asked(Map<TypeShape<?>, TargetType> answers, List<TypeShape<?>> shapes) implements TargetReader {
        Asked(Map<TypeShape<?>, TargetType> answers) {
            this(answers, new ArrayList<>());
        }

        @Override
        public TargetType read(TypeShape<?> shape, ShapeOrigin.Metamodel origin) {
            shapes.add(shape);
            return answers.getOrDefault(shape, TargetType.UNCHANGED);
        }
    }

    /// Renders `static String go(P p) { return <body>; }` against `target`.
    private static <P> String render(
            TargetClasspath target, RefToken<P> parameter, Function<Expr<P>, Expr<String>> body) {
        return TypedJavaFile.class_(target, ClassDesc.of("out", "Out"), new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        cb.staticMethod("go", String_.TOKEN, parameter, (b, p) -> b.return_(body.apply(p)));
                        cb.staticMethod("again", String_.TOKEN, parameter, (b, p) -> b.return_(body.apply(p)));
                    }
                })
                .render();
    }

    private static Expr<String> callM(Expr<SvcP> svc) {
        return call(svc, M, literal("a"));
    }

    @Test
    void theEntryOfTheTypedLayerDoesNotCompileWithoutATargetClasspath() {
        List<File> classpath = Arrays.stream(
                        System.getProperty("java.class.path").split(File.pathSeparator))
                .map(File::new)
                .toList();
        String spec = """
                new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {}
                }""";
        String source = """
                package fixtures;

                import java.lang.constant.ClassDesc;
                import me.supcheg.javafile.JavaFile;
                import me.supcheg.javafile.facts.UnsafeFacts;
                import me.supcheg.javafile.typed.TypedClassBuilder;
                import me.supcheg.javafile.typed.TypedJavaFile;

                class Entry {
                    JavaFile file = TypedJavaFile.class_(%sClassDesc.of("fixtures", "Generated"), %s);
                }
                """;

        Compilation without = javac().withClasspath(classpath)
                .compile(JavaFileObjects.forSourceString("fixtures.Entry", source.formatted("", spec)));
        Compilation with = javac().withClasspath(classpath)
                .compile(JavaFileObjects.forSourceString(
                        "fixtures.Entry", source.formatted("UnsafeFacts.unverifiedClasspath(), ", spec)));

        assertThat(without).failed();
        assertThat(without).hadErrorContaining("required: me.supcheg.javafile.facts.TargetClasspath,");
        assertThat(with).succeeded();
    }

    @Test
    void anUnverifiedClasspathRendersOfAnyMetamodel() {
        assertThat(render(UnsafeFacts.unverifiedClasspath(), SVC_TOKEN, TargetClasspathLoweringTest::callM))
                .contains("return v0.m(\"a\");");
    }

    @Test
    void aMetamodelIsCheckedOnceHoweverOftenLoweringMeetsIt() {
        Asked asked = new Asked(Map.of());
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);

        // two methods of one class, each with a parameter, a receiver and an owner of the type
        render(target, SVC_TOKEN, TargetClasspathLoweringTest::callM);
        render(target, SVC_TOKEN, TargetClasspathLoweringTest::callM);

        // the String the class returns is a metamodel too, that of the test and that of the typed layer, which renders
        // a
        // literal, and each is asked once like the others
        assertThat(asked.shapes()).containsExactlyInAnyOrder(String_.TOKEN.shape(), TYPED_STRING, SVC);
    }

    @Test
    void aMetamodelThatIsNotUsedIsNotChecked() {
        Asked asked = new Asked(
                Map.of(UNUSED, new TargetType.Mismatched(List.of(new Difference.Absent("type fixtures.Unused gone")))));
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);
        // a token made of a metamodel is not a use of it
        OpenClassToken<Object> unused = UnsafeFacts.openClassToken(UNUSED);

        assertThat(render(target, SVC_TOKEN, TargetClasspathLoweringTest::callM))
                .contains("v0.m(\"a\")");

        assertThat(asked.shapes()).containsExactlyInAnyOrder(String_.TOKEN.shape(), TYPED_STRING, SVC);
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> render(target, unused, _ -> literal("a")))
                .withMessageContaining("type fixtures.Unused gone");
    }

    @Test
    void aMetamodelThatDoesNotHoldFailsTheClass() {
        TargetClasspath target = UnsafeFacts.targetClasspath(new Asked(Map.of(
                SVC,
                new TargetType.Mismatched(List.of(new Difference.MissingFact(
                        "method overridable m(java.lang.String) -> java.lang.String throws -", List.of()))))));

        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> render(target, SVC_TOKEN, TargetClasspathLoweringTest::callM))
                .withMessageStartingWith(
                        "metamodel gen.facts.fixtures.Svc_ does not match fixtures.Svc on the target classpath:\n"
                                + "  missing: method overridable m(java.lang.String) -> java.lang.String throws -\n")
                .satisfies(e -> assertThat(e.type()).isEqualTo(ClassDesc.of("fixtures", "Svc")));
    }

    @Test
    void aTypeArgumentIsCheckedWithItsType() {
        Asked asked = new Asked(
                Map.of(SVC, new TargetType.Mismatched(List.of(new Difference.Absent("type fixtures.Svc gone")))));
        TargetClasspath target = UnsafeFacts.targetClasspath(asked);
        OpenClassToken<BoxP<SvcP>> boxOfSvc = UnsafeFacts.openClassToken(BOX, TokenArg.exact(SVC_TOKEN));

        // the class names Svc only as a type argument of its parameter
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> render(target, boxOfSvc, _ -> literal("a")))
                .withMessageContaining("type fixtures.Svc gone");
        assertThat(asked.shapes()).containsExactlyInAnyOrder(String_.TOKEN.shape(), BOX, SVC);
    }

    @Test
    void anOverloadTheTargetAddsKeepsTheCastOfTheArgument() {
        // generated against a Svc whose only m is m(Object): the one candidate, so no cast
        Function<Expr<SvcP>, Expr<String>> ofAString = svc -> call(svc, objectTaking(), literal("a"));
        assertThat(render(UnsafeFacts.targetClasspath(new Asked(Map.of())), OBJECT_TAKING.owner(), ofAString))
                .contains("return v0.m(\"a\");");

        // the target has m(String) too, which javac would choose for a String
        TargetClasspath target = UnsafeFacts.targetClasspath(new Asked(Map.of(
                OBJECT_TAKING.owner().shape(),
                new TargetType.Changed(
                        new MethodTableTemplate(Set.of(), Set.of(M_OF_STRING, M_OF_OBJECT), Set.of())))));

        assertThat(render(target, OBJECT_TAKING.owner(), ofAString)).contains("return v0.m((Object) \"a\");");
    }

    /// `String m(Object)` of `Svc`, of a metamodel generated against a `Svc`
    /// without `m(String)`: its only `m`.
    private static final MethodRef1<SvcP, String, Object> OBJECT_TAKING = UnsafeFacts.method(
            UnsafeFacts.<SvcP>openClassToken(metamodel(DeclaredKind.OPEN_CLASS, "Svc", List.of(), Set.of(M_OF_OBJECT))),
            "m",
            String_.TOKEN,
            Object_.TOKEN,
            MemberTraits.DEFAULT);

    private static MethodRef1<SvcP, String, Object> objectTaking() {
        return OBJECT_TAKING;
    }

    /// Phantom of the enum `fixtures.Light`.
    interface LightP {}

    @Test
    void anEnumThatHasGotAConstantFailsTheSwitchThatWasExhaustive() {
        TypeShape<DeclaredKind.EnumClass> light = UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.fixtures", "Light_"), FINGERPRINT, () -> ""),
                DeclaredKind.ENUM_CLASS,
                ClassDesc.of("fixtures", "Light"),
                List.of(),
                List.of(ConstantDescs.CD_Enum, ConstantDescs.CD_Object),
                Supertypes.NONE,
                new MethodTableTemplate(Set.of(), Set.of(), Set.of()),
                List.of("ON", "OFF"),
                false);
        EnumToken<LightP> token = UnsafeFacts.enumToken(light);
        // exhaustive by the facts: a case of ON and one of OFF, no default
        Function<Expr<LightP>, Expr<String>> exhaustive = l -> switch_(
                l,
                token,
                String_.TOKEN,
                c -> c.case_(token.constant("ON"), literal("on")).case_(token.constant("OFF"), literal("off")));
        Function<Expr<LightP>, Expr<String>> withDefault =
                l -> switch_(l, token, String_.TOKEN, c -> c.default_(literal("any")));

        assertThat(render(UnsafeFacts.targetClasspath(new Asked(Map.of())), token, exhaustive))
                .contains("case ON -> \"on\";");

        // what the check of java-file-api-lang-model answers for `enum Light { ON, OFF, DIM }`
        TargetClasspath target = UnsafeFacts.targetClasspath(new Asked(Map.of(
                light,
                new TargetType.Mismatched(List.of(new Difference.ChangedData("enum", "ON; OFF", "ON; OFF; DIM"))))));

        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> render(target, token, exhaustive))
                .withMessageContaining("ON; OFF; DIM");
        // the facts of the enum are those of the target or none, whether the switch leans on its constants or not
        assertThatExceptionOfType(TargetClasspathMismatchException.class)
                .isThrownBy(() -> render(target, token, withDefault));
    }

    @Test
    void aTwinTheTargetAddsIsRejectedByItsTable() {
        // m(T) of a Box<String> is fine while Box has no m(String); the target has one
        Signature mOfT = Signature.of("m", Param.var(0));
        TypeShape<DeclaredKind.OpenClass> box =
                metamodel(DeclaredKind.OPEN_CLASS, "Box", List.of(new TypeParam("T", List.of())), Set.of(mOfT));
        OpenClassToken<BoxP<String>> boxOfString = UnsafeFacts.openClassToken(box, TokenArg.exact(String_.TOKEN));
        MethodRef1<BoxP<String>, String, String> m = UnsafeFacts.method(
                boxOfString, "m", String_.TOKEN, UnsafeFacts.param(String_.TOKEN, Param.var(0)), MemberTraits.DEFAULT);
        Function<Expr<BoxP<String>>, Expr<String>> body = b -> call(b, m, literal("a"));

        assertThat(render(UnsafeFacts.targetClasspath(new Asked(Map.of())), boxOfString, body))
                .contains("return v0.m(\"a\");");

        TargetClasspath target = UnsafeFacts.targetClasspath(new Asked(Map.of(
                box, new TargetType.Changed(new MethodTableTemplate(Set.of(), Set.of(mOfT, M_OF_STRING), Set.of())))));
        assertThatExceptionOfType(FactLookupException.class)
                .isThrownBy(() -> render(target, boxOfString, body))
                .withMessageContaining("m(java.lang.String)");
    }
}
