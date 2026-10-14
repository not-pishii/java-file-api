package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.facts.UnsafeFacts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The class a declaration declares is one Java source writes as it is: a top-level class whose package and
/// simple name are identifiers that can name a package and a type. A name that cannot be is refused by the
/// call that says it, before the specification of the class runs, as javac refuses the source.
class ClassNamesTest {

    /// A specification that is not to run: the class is refused before it does.
    private static final TypedJavaFile.TypedClassSpec NEVER = new TypedJavaFile.TypedClassSpec() {
        @Override
        public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
            throw new AssertionError("the specification of a refused class ran");
        }
    };

    private static Compilation javacOf(String packageName, String simpleName, String header) {
        String qualified = packageName.isEmpty() ? simpleName : packageName + "." + simpleName;
        return javac().withOptions("-Xlint:all")
                .compile(JavaFileObjects.forSourceString(
                        qualified,
                        (packageName.isEmpty() ? "" : "package " + packageName + ";\n") + "public final class " + header
                                + " {}\n"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"var", "record", "yield", "sealed", "permits"})
    void aClassIsNotNamedByAContextualKeyword(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(
                        () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("gen", name), NEVER))
                .withMessageContaining("cannot declare the class gen." + name)
                .withMessageContaining("not a valid Java type identifier: '" + name + "'");
        assertThat(javacOf("gen", name, name)).failed();
    }

    @ParameterizedTest
    @ValueSource(strings = {"int", "_", "class"})
    void aClassIsNotNamedByAKeyword(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(
                        () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("gen", name), NEVER))
                .withMessageContaining("cannot declare the class gen." + name)
                .withMessageContaining("not a valid Java identifier: '" + name + "'");
        assertThat(javacOf("gen", name, name)).failed();
    }

    @Test
    void aClassIsNotOfAPackageThatIsNoName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("gen.int", "Made"), NEVER))
                .withMessageContaining("cannot declare the class gen.int.Made")
                .withMessageContaining("not a valid Java package name: 'gen.int'");
        assertThat(javacOf("gen.int", "Made", "Made")).failed();
    }

    @Test
    void aClassIsNotOfAPackageOfThePlatform() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), ClassDesc.of("java.lang", "Made"), NEVER))
                .withMessageContaining(
                        "cannot declare the class java.lang.Made: a package of java is one of the" + " platform");
        assertThat(javacOf("java.lang", "Made", "Made")).hadErrorContaining("package exists in another module");
    }

    @Test
    void aNestedClassIsNotDeclaredAsATopLevelOne() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), ClassDesc.of("gen", "Outer$Inner"), NEVER))
                .withMessageContaining(
                        "cannot declare the class gen.Outer$Inner: its name is that of a nested" + " class");
    }

    @Test
    void aGenericClassIsRefusedBeforeItsSpecificationRuns() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        ClassDesc.of("gen", "var"),
                        TypedJavaFile.typeParam("T"),
                        new TypedJavaFile.TypedGenericClassSpec1<Object>() {
                            @Override
                            public <Self, T1> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h, me.supcheg.javafile.facts.TypeVarToken<T1> t1) {
                                throw new AssertionError("the specification of a refused class ran");
                            }
                        }))
                .withMessageContaining("cannot declare the class gen.var");
    }

    @ParameterizedTest
    @ValueSource(strings = {"var", "yield", "record", "sealed", "permits", "int", "_"})
    void aTypeParameterIsNamedByATypeIdentifier(String name) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.typeParam(name))
                .withMessageContaining("a type parameter cannot be named '" + name + "'");
        assertThat(javacOf("gen", "Made", "Made<" + name + ">")).failed();
    }
}
