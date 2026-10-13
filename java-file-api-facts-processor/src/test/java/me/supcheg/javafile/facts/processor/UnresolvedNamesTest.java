package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/// The names of member facts ([MetamodelNames]) where a parameter is of a type javac does not find: what
/// [MetamodelNamesTest] cannot have, as its sources compile.
class UnresolvedNamesTest {

    /// Names the parameters of the methods of `p.Unresolved` in its first round.
    private static final class Naming extends AbstractProcessor {
        private final List<String> suffixes = new ArrayList<>();

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
            if (suffixes.isEmpty()) {
                TypeElement type = processingEnv.getElementUtils().getTypeElement("p.Unresolved");
                ElementFilter.methodsIn(type.getEnclosedElements()).stream()
                        .flatMap(method -> method.getParameters().stream())
                        .forEach(parameter -> suffixes.add(parameter.getSimpleName() + " "
                                + MetamodelNames.typeSuffix(parameter.asType(), false) + " "
                                + MetamodelNames.typeSuffix(parameter.asType(), true)));
            }
            return false;
        }
    }

    @Test
    void aTypeThatIsNotResolvedStandsAsJavacNamesIt() {
        Naming naming = new Naming();

        Javac.plain().with(naming).compile("""
                package p;
                public class Unresolved {
                    public void take(gen.Missing one, gen.Missing[] all) {}
                }
                """);

        assertThat(naming.suffixes)
                .containsExactly("one gen_Missing gen_Missing", "all gen_MissingArray gen_MissingArray");
    }
}
