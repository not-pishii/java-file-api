package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.classfile.ClassBuilder;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/// The metamodels of the types of a library whose class files name members by what is no identifier of
/// Java, as a value class of Kotlin does (`toString-impl`): the processor makes them of what Java
/// source can name, and javac compiles them.
class ForeignNamesTest {
    private static final MethodTypeDesc VOID = MethodTypeDesc.of(ConstantDescs.CD_void);
    private static final int PUBLIC_ABSTRACT = ClassFile.ACC_PUBLIC | ClassFile.ACC_ABSTRACT;

    @TempDir
    Path lib;

    private void write(String binaryName, int flags, Consumer<ClassBuilder> members) throws IOException {
        byte[] bytes = ClassFile.of().build(ClassDesc.of(binaryName), cb -> {
            cb.withFlags(flags).withSuperclass(ConstantDescs.CD_Object);
            cb.withMethodBody(
                    ConstantDescs.INIT_NAME,
                    VOID,
                    ClassFile.ACC_PUBLIC,
                    code -> code.aload(0)
                            .invokespecial(ConstantDescs.CD_Object, ConstantDescs.INIT_NAME, VOID)
                            .return_());
            members.accept(cb);
        });
        Path file = lib.resolve(binaryName.replace('.', '/') + ".class");
        Files.createDirectories(file.getParent());
        Files.write(file, bytes);
    }

    @Test
    void aMemberJavaSourceCannotNameHasNoFactAndATypeThatAsksForOneHasNoEntry() throws IOException {
        write("k.Value", ClassFile.ACC_PUBLIC | ClassFile.ACC_SUPER, cb -> {
            cb.withField("a-b", ConstantDescs.CD_int, ClassFile.ACC_PUBLIC);
            cb.withMethodBody("toString-impl", VOID, ClassFile.ACC_PUBLIC, CodeBuilder::return_);
            cb.withMethodBody("named", VOID, ClassFile.ACC_PUBLIC, CodeBuilder::return_);
        });
        write("k.Shape", PUBLIC_ABSTRACT | ClassFile.ACC_SUPER, cb -> {
            cb.withMethod("draw-impl", VOID, PUBLIC_ABSTRACT, method -> {});
            cb.withMethod("area", VOID, PUBLIC_ABSTRACT, method -> {});
        });

        // it was an IllegalArgumentException of the processor: "not a valid Java identifier: 'toString-impl'"
        Compiled compiled = Javac.facts().classpath(lib).compile("""
                        package gen;

                        import me.supcheg.javafile.facts.meta.Facts;

                        @Facts({k.Value.class, k.Shape.class})
                        class G {}
                        """).orFail();

        assertThat(compiled.warnings())
                .anyMatch(warning -> warning.contains("field a-b")
                        && warning.contains("has a name that is no identifier of Java, so no Java source names it"))
                .anyMatch(warning -> warning.contains("method toString-impl()")
                        && warning.contains("has a name that is no identifier of Java, so no Java source names it"))
                .anyMatch(warning -> warning.contains("method draw-impl()")
                        && warning.contains("has a name that is no identifier of Java, so no Java source names it"));
        assertThat(compiled.sources().get("gen.facts.k.Value_"))
                .contains(" named")
                .contains("- `method toString-impl()`, which has a name that is no identifier of Java")
                .doesNotContain("\"toString-impl\"")
                .doesNotContain("\"a-b\"");
        assertThat(compiled.sources().get("gen.facts.k.Value_Heritage")).contains("extend(");
        // a class of Java source cannot implement `draw-impl`, so none extends Shape
        assertThat(compiled.sources())
                .doesNotContainKey("gen.facts.k.Shape_Heritage")
                .extractingByKey("gen.facts.k.Shape_")
                .asString()
                .contains("No generated class extends or implements [Shape]: a member a class would inherit of it"
                        + " has a signature that cannot be told.");
        // and what was generated is Java
        assertThat(Javac.plain().classpath(lib).compile(compiled.generated()).errors())
                .isEmpty();
    }
}
