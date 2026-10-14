package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;

/// A name with a character javac ignores in an identifier (`Character.isIdentifierIgnorable`) is another
/// name to the typed layer than to javac, which drops the character: `to​String` is `toString`. The
/// checks of the names a class declares — against each other and against what it inherits — go by the
/// name as it is given, so such a name is refused by the call that declares it.
class IgnorableNamesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final String ZERO_WIDTH_SPACE = "​";

    @Test
    void aMethodIsNotNamedWithAnIgnorableCharacter() {
        // to<ZWSP>String is toString to javac, which an int result cannot override
        classAsJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareMethod("to" + ZERO_WIDTH_SPACE + "String", PrimitiveToken.INT);
                        throw new AssertionError("a name with an ignorable character was declared");
                    }
                }),
                "not a valid Java identifier: 'to" + ZERO_WIDTH_SPACE + "String'",
                "",
                "public int to" + ZERO_WIDTH_SPACE + "String() { return 1; }",
                "toString() in me.supcheg.example.Probe cannot override toString() in java.lang.Object");
    }

    @Test
    void aFieldIsNotNamedWithAnIgnorableCharacter() {
        // na<ZWSP>me is name to javac, which the class declares already
        classAsJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareField("name", String_.TOKEN);
                        cb.declareField("na" + ZERO_WIDTH_SPACE + "me", String_.TOKEN);
                        throw new AssertionError("a name with an ignorable character was declared");
                    }
                }),
                "not a valid Java identifier: 'na" + ZERO_WIDTH_SPACE + "me'",
                "",
                "public final String name = \"\"; public final String na" + ZERO_WIDTH_SPACE + "me = \"\";",
                "variable name is already defined in class me.supcheg.example.Probe");
    }

    @Test
    void aStaticMethodIsNotNamedWithAnIgnorableCharacter() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareVoidStaticMethod("run");
                        cb.declareVoidStaticMethod("r" + ZERO_WIDTH_SPACE + "un");
                        throw new AssertionError("a name with an ignorable character was declared");
                    }
                }),
                "not a valid Java identifier: 'r" + ZERO_WIDTH_SPACE + "un'",
                "",
                "public static void run() {} public static void r" + ZERO_WIDTH_SPACE + "un() {}",
                "method run() is already defined in class me.supcheg.example.Probe");
    }
}
