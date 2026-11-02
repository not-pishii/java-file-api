package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.MethodTableTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/// What [MirrorTranslator] makes of the names a class file has and Java
/// source has not: `toString-impl` of a value class of Kotlin,
/// `elapsedNow-UwyO8pc`. The classes are written as class files, since no
/// Java source declares them.
class ForeignNamesTest {
    private static final String NO_IDENTIFIER = "has a name that is no identifier of Java, so no Java source names it";
    private static final MethodTypeDesc VOID = MethodTypeDesc.of(ConstantDescs.CD_void);
    private static final int PUBLIC_CLASS = ClassFile.ACC_PUBLIC | ClassFile.ACC_SUPER;
    private static final int PUBLIC_ABSTRACT_CLASS = PUBLIC_CLASS | ClassFile.ACC_ABSTRACT;
    private static final int INTERFACE = ClassFile.ACC_INTERFACE | ClassFile.ACC_ABSTRACT;
    private static final int PUBLIC_ABSTRACT = ClassFile.ACC_PUBLIC | ClassFile.ACC_ABSTRACT;

    @TempDir
    // JUnit fills the field with the directory of the test, so it cannot be final
    @SuppressWarnings("NonFinalStaticField")
    static Path classes;

    @BeforeAll
    static void writeTheClasses() {
        ClassDesc weird = ClassDesc.of("k.we-ird");
        write("k.Value", PUBLIC_CLASS, ConstantDescs.CD_Object, List.of(), cb -> {
            constructor(cb, ConstantDescs.CD_Object);
            cb.withField("a-b", ConstantDescs.CD_int, ClassFile.ACC_PUBLIC);
            cb.withField("plain", ConstantDescs.CD_int, ClassFile.ACC_PUBLIC);
            cb.withMethodBody("to-string", VOID, ClassFile.ACC_PUBLIC, CodeBuilder::return_);
            cb.withMethodBody("of-value", VOID, ClassFile.ACC_PUBLIC | ClassFile.ACC_STATIC, CodeBuilder::return_);
            cb.withMethodBody("named", VOID, ClassFile.ACC_PUBLIC, CodeBuilder::return_);
        });
        write("k.Mentions", PUBLIC_CLASS | ClassFile.ACC_FINAL, ConstantDescs.CD_Object, List.of(), cb -> {
            constructor(cb, ConstantDescs.CD_Object);
            cb.withMethodBody(
                    "odd",
                    MethodTypeDesc.of(weird),
                    ClassFile.ACC_PUBLIC,
                    code -> code.aconst_null().areturn());
        });
        write("k.Shape", PUBLIC_ABSTRACT_CLASS, ConstantDescs.CD_Object, List.of(), cb -> {
            constructor(cb, ConstantDescs.CD_Object);
            cb.withMethod("draw-impl", VOID, PUBLIC_ABSTRACT, method -> {});
            cb.withMethod("area", VOID, PUBLIC_ABSTRACT, method -> {});
        });
        write("k.Fn", ClassFile.ACC_PUBLIC | INTERFACE, ConstantDescs.CD_Object, List.of(), cb -> {
            cb.withMethod("apply", VOID, PUBLIC_ABSTRACT, method -> {});
            cb.withMethod("apply-x", VOID, PUBLIC_ABSTRACT, method -> {});
        });
        write("k.Logged", ClassFile.ACC_PUBLIC | INTERFACE, ConstantDescs.CD_Object, List.of(), cb -> {
            cb.withMethod("apply", VOID, PUBLIC_ABSTRACT, method -> {});
            cb.withMethodBody("log-it", VOID, ClassFile.ACC_PUBLIC, CodeBuilder::return_);
        });
        ClassDesc color = ClassDesc.of("k.Color");
        write(
                "k.Color",
                PUBLIC_CLASS | ClassFile.ACC_FINAL | ClassFile.ACC_ENUM,
                ConstantDescs.CD_Enum,
                List.of(),
                cb -> cb.withField(
                        "my-entry",
                        color,
                        ClassFile.ACC_PUBLIC | ClassFile.ACC_STATIC | ClassFile.ACC_FINAL | ClassFile.ACC_ENUM));
        write(
                "k.we-ird",
                PUBLIC_CLASS,
                ConstantDescs.CD_Object,
                List.of(),
                cb -> constructor(cb, ConstantDescs.CD_Object));
        ClassDesc hidden = ClassDesc.of("k.Hidden");
        write("k.Hidden", ClassFile.ACC_SUPER | ClassFile.ACC_ABSTRACT, ConstantDescs.CD_Object, List.of(), cb -> {
            constructor(cb, ConstantDescs.CD_Object);
            cb.withMethodBody("x-y", VOID, ClassFile.ACC_PUBLIC, CodeBuilder::return_);
            cb.withMethodBody("fine", VOID, ClassFile.ACC_PUBLIC, CodeBuilder::return_);
        });
        write("k.Sub", PUBLIC_CLASS, hidden, List.of(), cb -> constructor(cb, hidden));
        ClassDesc hiddenApi = ClassDesc.of("k.HiddenApi");
        write("k.HiddenApi", INTERFACE, ConstantDescs.CD_Object, List.of(), cb -> {
            cb.withMethod("q-r", VOID, PUBLIC_ABSTRACT, method -> {});
            cb.withMethod("asked", VOID, PUBLIC_ABSTRACT, method -> {});
        });
        write(
                "k.Impl",
                PUBLIC_ABSTRACT_CLASS,
                ConstantDescs.CD_Object,
                List.of(hiddenApi),
                cb -> constructor(cb, ConstantDescs.CD_Object));
    }

    private static void constructor(ClassBuilder cb, ClassDesc superclass) {
        cb.withMethodBody(
                ConstantDescs.INIT_NAME,
                VOID,
                ClassFile.ACC_PUBLIC,
                code -> code.aload(0)
                        .invokespecial(superclass, ConstantDescs.INIT_NAME, VOID)
                        .return_());
    }

    private static void write(
            String binaryName,
            int flags,
            ClassDesc superclass,
            List<ClassDesc> interfaces,
            Consumer<ClassBuilder> members) {
        byte[] bytes = ClassFile.of().build(ClassDesc.of(binaryName), cb -> {
            cb.withFlags(flags).withSuperclass(superclass).withInterfaceSymbols(interfaces);
            members.accept(cb);
        });
        Path file = classes.resolve(binaryName.replace('.', '/') + ".class");
        try {
            Files.createDirectories(file.getParent());
            Files.write(file, bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static <R> R read(Function<Harness.Env, R> action) {
        return Harness.run(List.of("-classpath", classes.toString()), action);
    }

    private static List<String> names(MethodTableTemplate table) {
        return table.methods().map(MethodTableTemplate.Signature::name).sorted().toList();
    }

    @Test
    void aMemberJavaSourceCannotNameIsSkippedAndIsInNoTableAndNoHeritage() {
        TypeModel value = read(env -> Harness.ok(env.full("k.Value")));

        assertThat(value.skipped())
                .containsExactly(
                        new SkippedMember("field a-b", NO_IDENTIFIER),
                        new SkippedMember("method to-string()", NO_IDENTIFIER),
                        new SkippedMember("method of-value()", NO_IDENTIFIER));
        assertThat(value.members())
                .extracting(member -> switch (member) {
                    case FieldModel field -> "field " + field.name();
                    case CtorModel _ -> "constructor";
                    case MethodModel method -> "method " + method.name();
                })
                .containsExactly("field plain", "constructor", "method named");
        assertThat(names(value.methods())).contains("named").doesNotContain("to-string", "of-value");
        assertThat(Canonical.heritage((Heritage.Told) value.heritage()))
                .anyMatch(line -> line.startsWith("inherit method named() "))
                .anyMatch(line -> line.equals("inherit field public k.Value plain"))
                .noneMatch(line -> line.contains("to-string") || line.contains("of-value") || line.contains("a-b"));
        assertThat(names(read(env -> Harness.ok(env.tokenOnly("k.Value"))).methods()))
                .contains("named")
                .doesNotContain("to-string", "of-value");
    }

    @Test
    void aTypeThatAsksForAMethodJavaSourceCannotNameTellsNoHeritage() {
        TypeModel shape = read(env -> Harness.ok(env.full("k.Shape")));

        assertThat(shape.heritage()).isSameAs(Heritage.UNTOLD);
        assertThat(shape.skipped()).containsExactly(new SkippedMember("method draw-impl()", NO_IDENTIFIER));
        assertThat(shape.methods().abstractMethods()).containsExactly(MethodTableTemplate.Signature.of("area"));
        assertThat(names(shape.methods())).doesNotContain("draw-impl");
    }

    @Test
    void anInterfaceWithAnAbstractMethodJavaSourceCannotNameIsNotFunctional() {
        TypeModel fn = read(env -> Harness.ok(env.full("k.Fn")));
        TypeModel logged = read(env -> Harness.ok(env.full("k.Logged")));

        // two abstract methods, of which Java source names one
        assertThat(fn.sam()).isEmpty();
        assertThat(fn.heritage()).isSameAs(Heritage.UNTOLD);
        Translation<Optional<SamModel>> sam = read(env -> env.translator().sam(env.element("k.Fn")));
        assertThat(sam).isEqualTo(new Translation.Ok<>(Optional.empty()));
        // one abstract method, and a default one no Java source calls
        assertThat(logged.sam()).map(MethodModel::name).contains("apply");
        assertThat(logged.heritage()).isInstanceOf(Heritage.Told.class);
        assertThat(logged.skipped()).containsExactly(new SkippedMember("method log-it()", NO_IDENTIFIER));
    }

    @Test
    void anEnumWithAConstantJavaSourceCannotNameHasNoModel() {
        Translation<TypeModel> color = read(env -> env.full("k.Color"));

        assertThat(color)
                .isEqualTo(new Translation.Unrepresentable<TypeModel>(
                        "type k.Color: enum constant my-entry has a name that is no identifier of Java"));
    }

    @Test
    void aTypeJavaSourceCannotNameIsNoTypeOfTheClassPath() {
        // javac lists no class file whose name is no identifier: the type is not found, and a mention of it
        // is of a type that is not there
        boolean found = read(env -> env.elements().getTypeElement("k.we-ird") != null);
        Translation<TypeModel> mentions = read(env -> env.full("k.Mentions"));

        assertThat(found).isFalse();
        assertThat(mentions).isEqualTo(new Translation.Deferred<TypeModel>("k.we-ird"));
    }

    @Test
    void aMemberOfAHiddenSupertypeThatJavaSourceCannotNameIsSkippedWhereItIsAdopted() {
        TypeModel sub = read(env -> Harness.ok(env.full("k.Sub")));
        TypeModel impl = read(env -> Harness.ok(env.full("k.Impl")));

        assertThat(sub.skipped()).containsExactly(new SkippedMember("method x-y()", NO_IDENTIFIER));
        assertThat(sub.members())
                .filteredOn(MethodModel.class::isInstance)
                .extracting(member -> ((MethodModel) member).name())
                .containsExactly("fine");
        assertThat(sub.heritage()).isInstanceOf(Heritage.Told.class);
        // an abstract one: the class adopts the method it is asked for, and tells no heritage
        assertThat(impl.skipped()).containsExactly(new SkippedMember("method q-r()", NO_IDENTIFIER));
        assertThat(impl.members())
                .filteredOn(MethodModel.class::isInstance)
                .extracting(member -> ((MethodModel) member).name())
                .containsExactly("asked");
        assertThat(impl.heritage()).isSameAs(Heritage.UNTOLD);
    }
}
