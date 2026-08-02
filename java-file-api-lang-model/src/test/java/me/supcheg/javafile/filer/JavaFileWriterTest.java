package me.supcheg.javafile.filer;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.RenderableFile;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.StringWriter;
import java.lang.constant.ClassDesc;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentCaptor.captor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JavaFileWriterTest {

    static Stream<Arguments> files() {
        record File(String expectedQualifiedName, RenderableFile file) {}

        return Stream.of(
                        new File(
                                "me.supcheg.example.Empty",
                                JavaFile.of(ClassDesc.of("me.supcheg.example", "Empty"), _ -> {})),
                        new File("me.supcheg.example.package-info", PackageInfoFile.of("me.supcheg.example")),
                        new File("module-info", ModuleFile.of("example", _ -> {})))
                .map(file -> arguments(file.expectedQualifiedName(), file.file()));
    }

    @MethodSource("files")
    @ParameterizedTest(name = "{0}")
    void writesTheQualifiedNameAndRenderedSourceThroughTheFiler(String expectedQualifiedName, RenderableFile file)
            throws Exception {
        var buffer = new StringWriter();

        var sourceFile = mock(JavaFileObject.class);
        when(sourceFile.openWriter()).thenReturn(buffer);

        var filer = mock(Filer.class);
        ArgumentCaptor<CharSequence> qualifiedNameCaptor = captor();
        when(filer.createSourceFile(qualifiedNameCaptor.capture())).thenReturn(sourceFile);

        JavaFileWriter.writeTo(file, filer);

        assertThat(qualifiedNameCaptor.getValue()).isEqualTo(expectedQualifiedName);
        assertThat(buffer.toString()).isEqualTo(file.render());
    }
}
