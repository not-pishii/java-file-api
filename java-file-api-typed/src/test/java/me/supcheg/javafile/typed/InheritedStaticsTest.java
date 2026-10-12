package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Named;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.Comparator_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Stated_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.StaticPick_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.StaticTwin_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.Subclasses.began;
import static org.assertj.core.api.Assertions.assertThat;

/// The `static` methods a class has from its supertypes (JLS 8.4.8): those
/// of its superclass, which javac takes for candidates of a call on a value
/// of the class (JLS 15.12.2.1), and none of an interface. The rendered
/// classes are compiled under `-Xlint:all` and run: a call reaches the
/// method its fact names.
class InheritedStaticsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aMethodOfTheClassIsCalledWhateverStaticOverloadTheSuperclassHas() throws Throwable {
        // static String pick(String) of StaticPick is more specific than pick(Object) of the class
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(StaticPick_.extend(h.self()));
                var cb = began(sh, ctor -> ctor.super_(StaticPick_.new_, (b, self) -> b.end()));
                var pick = cb.method("pick", String_.TOKEN, Object_.TOKEN, (b, self, v) -> b.return_(literal("own")));
                cb.method("go", String_.TOKEN, (b, self) -> b.return_(call(self, pick, literal("s"))));
            }
        }));

        assertThat(compiled.source()).contains("return this.pick((Object) \"s\");");
        assertThat(compiled.invoke("go")).isEqualTo("own");
    }

    @Test
    void aMethodOfTheSuperclassIsCalledWhateverStaticOverloadItHas() throws Throwable {
        // String m(Object) and static String m(String) of StaticTwin
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(StaticTwin_.extend(h.self()));
                var cb = began(sh, ctor -> ctor.super_(StaticTwin_.new_, (b, self) -> b.end()));
                cb.method(
                        "go",
                        String_.TOKEN,
                        (b, self) -> b.return_(call(cb.asSuper().apply(self), StaticTwin_.m_Object, literal("s"))));
            }
        }));

        assertThat(compiled.source()).contains("return this.m((Object) \"s\");");
        assertThat(compiled.invoke("go")).isEqualTo("instance");
    }

    @Test
    void aStaticMethodOfAnInterfaceIsNoneOfTheClass() throws Throwable {
        // static reverseOrder() of Comparator: a class that implements it has no such method
        Comparator_<String> comparator = new Comparator_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var compare = h.overriding(comparator.compare_T_T);
                var oh = h.extendsObject();
                oh.implements_(comparator.implement(h.self()).compare_T_T(compare));
                var cb = oh.body();
                cb.constructor((b, self) -> b.end());
                cb.define(compare.method(), (b, self, l, r) -> b.return_(literal(0)));
                cb.method("reverseOrder", String_.TOKEN, (b, self) -> b.return_(literal("mine")));
            }
        }));

        assertThat(compiled.invoke("reverseOrder")).isEqualTo("mine");
    }

    @Test
    void aStaticMethodOfAnInterfaceDoesNotMeetTheDefaultOneOfAnother() throws Throwable {
        // static String label() of Stated, default String label() of Named
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var oh = h.extendsObject();
                oh.implements_(Stated_.implement(h.self()));
                Upcast<Self, Named> asNamed = oh.implements_(Named_.implement(h.self()));
                var cb = oh.body();
                cb.constructor((b, self) -> b.end());
                cb.method("go", String_.TOKEN, (b, self) -> b.return_(call(asNamed.apply(self), Named_.label)));
            }
        }));

        assertThat(compiled.invoke("go")).isEqualTo("named");
    }

    @Test
    void aMethodOfTheSignatureOfAStaticOneOfTheSuperclassIsNotDeclared() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = began(
                                h.extends_(Base_.extend(h.self())),
                                ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        cb.method("util", String_.TOKEN, (b, self) -> b.return_(literal("x")));
                    }
                }),
                "has the signature of the static method util() of me.supcheg.javafile.typed.fixtures.Base, which"
                        + " the class has from its superclass",
                "extends Base",
                "public String util() { return \"x\"; }",
                "overridden method is static");
    }
}
