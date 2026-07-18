package me.supcheg.javafile.filer;

import me.supcheg.javafile.JavaFile;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.StringWriter;
import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.captor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JavaFileWriterTest {

    @Test
    void writesTheQualifiedNameAndRenderedSourceThroughTheFiler() throws Exception {
        var file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Empty"), cb -> {});

        var buffer = new StringWriter();

        var sourceFile = mock(JavaFileObject.class);
        when(sourceFile.openWriter()).thenReturn(buffer);

        var filer = mock(Filer.class);
        ArgumentCaptor<CharSequence> qualifiedNameCaptor = captor();
        when(filer.createSourceFile(qualifiedNameCaptor.capture())).thenReturn(sourceFile);

        JavaFileWriter.writeTo(file, filer);

        assertThat(qualifiedNameCaptor.getValue()).isEqualTo("me.supcheg.example.Empty");
        assertThat(buffer.toString()).isEqualTo(file.render());
    }
}
