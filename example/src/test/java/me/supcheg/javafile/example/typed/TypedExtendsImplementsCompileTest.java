package me.supcheg.javafile.example.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.example.typed.facts.java.lang.Comparable_;
import me.supcheg.javafile.example.typed.facts.java.lang.String_;
import me.supcheg.javafile.example.typed.facts.java.util.AbstractCollection_;
import me.supcheg.javafile.example.typed.facts.java.util.AbstractList_;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.ClassHeader;
import me.supcheg.javafile.typed.TypedJavaFile;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.subInt;
import static org.assertj.core.api.Assertions.assertThat;

/// A class with a superclass and a superinterface, declared through the
/// typed layer: `final class Names extends AbstractList<String> implements
/// Comparable<Names>`.
///
/// The class is declared in three stages, each a type that gives the next.
/// The header says what the class extends and implements, by proofs that it
/// has every abstract method of each. The builder of the members declares
/// every member, which gives its fact. Their bodies define each: a body is
/// built and checked by the call that gives it, against all the class
/// declared.
class TypedExtendsImplementsCompileTest {

    private static final ClassDesc NAMES = ClassDesc.of("me.supcheg.example", "Names");

    @Test
    void aClassThatExtendsAndImplementsCompiles() {
        JavaFile names = TypedJavaFile.declareClass(
                // where there is no compilation to check the facts against; an annotation processor gives
                // TargetClasspaths.of(processingEnv) of java-file-api-lang-model
                UnsafeFacts.unverifiedClasspath(), NAMES, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var list = new AbstractList_<>(String_.TOKEN);
                        var collection = new AbstractCollection_<>(String_.TOKEN);
                        var comparable = new Comparable_<>(h.self());

                        // the header: the methods the supertypes leave abstract are overridden, and each
                        // supertype is named by the proof that the class has them all
                        var get = h.overriding(list.get_int);
                        var size = h.overriding(collection.size);
                        var compareTo = h.overriding(comparable.compareTo_T);
                        var sh = h.extends_(list.extend(h.self()).get_int(get).size(size));
                        sh.implements_(comparable.implement(h.self()).compareTo_T(compareTo));

                        // the declarations: every member of the class, none with a body
                        var cb = sh.members();
                        var constructor = cb.declareConstructor();
                        var toString = cb.declareOverride(collection.toString);
                        var first = cb.declareMethod("first", String_.TOKEN);

                        // the bodies: they begin with that of a constructor, which calls one of the superclass
                        var bodies = cb.bodies(constructor, ctor -> ctor.super_(list.super_, (b, self) -> b.end()));
                        bodies.define(get.method(), (b, self, index) -> b.return_(literal("name")));
                        bodies.define(size.method(), (b, self) -> b.return_(literal(2)));
                        bodies.define(
                                compareTo.method(),
                                (b, self, other) ->
                                        b.return_(subInt(call(self, size.method()), call(other, size.method()))));
                        bodies.define(
                                toString,
                                (b, self) -> b.return_(
                                        concat(literal("names"), bodies.superCall(self, collection.toString))));
                        bodies.define(first, (b, self) -> b.return_(call(self, get.method(), literal(0))));
                    }
                });

        String source = names.render();
        assertThat(source)
                .contains("public final class Names extends AbstractList<String> implements Comparable<Names> {")
                .contains("public Names() {\n        super();\n    }")
                .contains("@Override\n    public String get(int v0) {")
                .contains("@Override\n    public int compareTo(Names v0) {")
                .contains("return \"names\" + super.toString();")
                .contains("return this.get(0);");

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(names.qualifiedName(), source));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
