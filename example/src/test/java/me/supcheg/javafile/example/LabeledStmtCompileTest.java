package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class LabeledStmtCompileTest {

    @Test
    void labeledLoopWithLabeledBreakCompiles() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Search"),
                cb -> cb.withVoidMethod("scan", mb -> mb.withParam("items", Types.array(PrimitiveTypeRef.INT))
                        .withBody(b -> b.labeled("outer", outer -> {
                            outer.forEach(
                                    PrimitiveTypeRef.INT,
                                    "i",
                                    outer.field("items"),
                                    inner -> inner.if_(
                                            inner.eq(inner.field("i"), inner.literal(1)),
                                            ib -> ib.then(t -> t.break_("outer"))));
                        }))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
