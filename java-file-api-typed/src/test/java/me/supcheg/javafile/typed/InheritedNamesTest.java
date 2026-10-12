package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Op;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Nesting_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Obscuring_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Op_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Planted_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Rooted_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.enumConstant;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.Subclasses.began;
import static org.assertj.core.api.Assertions.assertThat;

/// The simple names the fields and member types of a class take in its body
/// (JLS 6.4.1, 6.4.2): those it declares and those it inherits, which its
/// declaration does not show. A type one of them hides is written by its
/// qualified name, and a class in which that name cannot be written either is
/// rejected when it is declared — not when it is rendered. The rendered
/// classes are compiled under `-Xlint:all` and run.
class InheritedNamesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final String OP = "me.supcheg.javafile.typed.fixtures.Op";

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aTypeNamedAsAnInheritedMemberTypeIsWrittenQualified() throws Throwable {
        // Nesting has the member class Op: in the body of a subclass Op is that class
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(Nesting_.extend(h.self()));
                var cb = began(sh, ctor -> ctor.super_(Nesting_.new_, (b, self) -> b.end()));
                var member1 = cb.declareMethod("which", Op_.TOKEN);
                cb.define(member1, (b, self) -> b.return_(enumConstant(Op_.SUB)));
            }
        }));

        assertThat(compiled.source())
                .doesNotContain("import " + OP + ";")
                .contains("public " + OP + " which() {")
                .contains("return " + OP + ".SUB;");
        assertThat(compiled.invoke("which")).isEqualTo(Op.SUB);
    }

    @Test
    void aTypeNamedAsAnInheritedFieldIsWrittenQualifiedBeforeAMember() throws Throwable {
        // Obscuring has the field Op: where an expression may stand Op is the field
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(Obscuring_.extend(h.self()));
                var cb = began(sh, ctor -> ctor.super_(Obscuring_.new_, (b, self) -> b.end()));
                var member2 = cb.declareMethod("which", Op_.TOKEN);
                cb.define(member2, (b, self) -> b.return_(enumConstant(Op_.SUB)));
            }
        }));

        assertThat(compiled.source()).contains("public Op which() {").contains("return " + OP + ".SUB;");
        assertThat(compiled.invoke("which")).isEqualTo(Op.SUB);
    }

    @Test
    void aTypeNamedAsAFieldOfTheClassIsWrittenQualifiedBeforeAMember() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var cb = h.extendsObject().body();
                var member3 = cb.declareConstructor();
                cb.field("Op", PrimitiveToken.INT, literal(1));
                var member4 = cb.declareMethod("which", Op_.TOKEN);
                cb.define(member3, (b, self) -> b.end());
                cb.define(member4, (b, self) -> b.return_(enumConstant(Op_.SUB)));
            }
        }));

        assertThat(compiled.source()).contains("public Op which() {").contains("return " + OP + ".SUB;");
        assertThat(compiled.invoke("which")).isEqualTo(Op.SUB);
    }

    @Test
    void aQualifiedNameThatBeginsWithAnInheritedFieldIsRejectedWhereTheClassIsDeclared() {
        // Rooted has the field me, and the field Op of the class makes the enum Op be written qualified
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var sh = h.extends_(Rooted_.extend(h.self()));
                        var cb = began(sh, ctor -> ctor.super_(Rooted_.new_, (b, self) -> b.end()));
                        cb.field("Op", PrimitiveToken.INT, literal(1));
                        var member5 = cb.declareMethod("which", Op_.TOKEN);
                        cb.define(member5, (b, self) -> b.return_(enumConstant(Op_.SUB)));
                    }
                }),
                "class me.supcheg.example.Probe cannot be written: cannot name " + OP + " before a member in a file"
                        + " that declares the variable me",
                "extends Rooted",
                "public final int Op = 1; public " + OP + " which() { return " + OP + ".SUB; }",
                "int cannot be dereferenced");
    }

    @Test
    void aQualifiedNameThatBeginsWithAFieldOfTheClassIsRejectedWhereTheClassIsDeclared() {
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().body();
                        cb.field("Op", PrimitiveToken.INT, literal(1));
                        cb.field("me", PrimitiveToken.INT, literal(2));
                        var member6 = cb.declareMethod("which", Op_.TOKEN);
                        cb.define(member6, (b, self) -> b.return_(enumConstant(Op_.SUB)));
                    }
                }),
                "class me.supcheg.example.Probe cannot be written: cannot name " + OP + " before a member in a file"
                        + " that declares the variable me",
                "",
                "public final int Op = 1; public final int me = 2; public " + OP + " which() { return " + OP
                        + ".SUB; }",
                "int cannot be dereferenced");
    }

    @Test
    void aQualifiedNameThatBeginsWithAnInheritedMemberTypeIsRejectedWhereTheClassIsDeclared() {
        // Planted has the member classes Op and me
        classAsJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var sh = h.extends_(Planted_.extend(h.self()));
                        var cb = began(sh, ctor -> ctor.super_(Planted_.new_, (b, self) -> b.end()));
                        var member7 = cb.declareMethod("which", Op_.TOKEN);
                        cb.define(member7, (b, self) -> b.return_(enumConstant(Op_.SUB)));
                    }
                }),
                "class me.supcheg.example.Probe cannot be written: cannot name " + OP + " in a file whose type"
                        + " inherits the member type me",
                "extends Planted",
                "public " + OP + " which() { return " + OP + ".SUB; }",
                "cannot find symbol");
    }
}
