package me.supcheg.javafile.filer;

import me.supcheg.javafile.JavaFile;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class JavaFileWriterTest {

    @Test
    void writesTheQualifiedNameAndRenderedSourceThroughTheFiler() throws Exception {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Empty"), cb -> {});
        FakeFiler filer = new FakeFiler();

        JavaFileWriter.writeTo(file, filer);

        assertThat(filer.lastQualifiedName).isEqualTo("me.supcheg.example.Empty");
        assertThat(filer.written.toString()).isEqualTo(file.render());
    }
}
