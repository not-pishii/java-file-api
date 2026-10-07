package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.typed.fixtures.Signal;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/// A construction-time check of the typed layer told against javac, by the
/// Java the typed code stands for: [#asJavac] where javac rejects that Java
/// too — else the test could pass for a reason of its own —, [#unlikeJavac]
/// where the typed layer is stricter and javac accepts it, [#asJavacWarns]
/// where javac accepts it with a warning, and [#asJavacAccepts] for what
/// both accept.
///
/// The Java is given as the members of a class `Probe`, compiled under
/// every lint, as the rendered code of the tests is, with `java.io`,
/// `java.util`, `java.util.function`, `java.util.concurrent` and [Signal]
/// imported. What the typed layer accepts is told against its own render
/// too: javac accepts that without a warning.
final class JavacVerdict {
    private JavacVerdict() {}

    /// The on-demand imports are assembled so that the wildcard lint of this source does not trip.
    private static final String IMPORTS =
            Stream.of("java.io", "java.util", "java.util.concurrent", "java.util.function")
                            .map(on -> "import " + on + ".*;\n")
                            .collect(Collectors.joining())
                    + "import " + Signal.class.getCanonicalName() + ";\n";

    private static Compilation compiled(String members) {
        return javac().withOptions("-Xlint:all")
                .compile(JavaFileObjects.forSourceString("Probe", IMPORTS + "class Probe {\n" + members + "\n}\n"));
    }

    /// The typed layer rejects `typed` with an exception of `rejection` and `message`, and javac rejects
    /// the Java it stands for with `error`.
    static void asJavac(
            Class<? extends RuntimeException> rejection,
            ThrowingCallable typed,
            String message,
            String java,
            String error) {
        assertThatExceptionOfType(rejection).isThrownBy(typed).withMessageContaining(message);
        Compilation compilation = compiled(java);
        CompilationSubject.assertThat(compilation).failed();
        CompilationSubject.assertThat(compilation).hadErrorContaining(error);
    }

    /// As [#asJavac(Class, ThrowingCallable, String, String, String)], rejected by an [IllegalStateException].
    static void asJavac(ThrowingCallable typed, String message, String java, String error) {
        asJavac(IllegalStateException.class, typed, message, java, error);
    }

    /// The typed layer rejects `typed` with an exception of `rejection` and `message` though javac accepts
    /// the Java it stands for.
    static void unlikeJavac(
            Class<? extends RuntimeException> rejection, ThrowingCallable typed, String message, String java) {
        assertThatExceptionOfType(rejection).isThrownBy(typed).withMessageContaining(message);
        CompilationSubject.assertThat(compiled(java)).succeeded();
    }

    /// As [#unlikeJavac(Class, ThrowingCallable, String, String)], rejected by an [IllegalStateException].
    static void unlikeJavac(ThrowingCallable typed, String message, String java) {
        unlikeJavac(IllegalStateException.class, typed, message, java);
    }

    /// The typed layer rejects `typed` with `message` where javac accepts the Java it stands for and warns
    /// of it with `warning`: the typed layer renders no code javac warns of.
    static void asJavacWarns(ThrowingCallable typed, String message, String java, String warning) {
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(typed).withMessageContaining(message);
        javacWarns(java, warning);
    }

    /// javac accepts `java` and warns of it with `warning`.
    static void javacWarns(String java, String warning) {
        Compilation compilation = compiled(java);
        CompilationSubject.assertThat(compilation).succeeded();
        CompilationSubject.assertThat(compilation).hadWarningContaining(warning);
    }

    /// The typed layer accepts `typed`, javac the class it renders, under every lint and without a warning,
    /// and the Java it stands for.
    static void asJavacAccepts(Supplier<JavaFile> typed, String java) {
        JavaFile file = typed.get();
        CompilationSubject.assertThat(javac().withOptions("-Xlint:all")
                        .compile(JavaFileObjects.forSourceString(file.qualifiedName(), file.render())))
                .succeededWithoutWarnings();
        CompilationSubject.assertThat(compiled(java)).succeeded();
    }
}
