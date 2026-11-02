package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Generic_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;

import static org.assertj.core.api.Assertions.assertThat;

/// A generic method of a fact, overridden by a generic method of the class (§8g): the override declares
/// the type parameters of the method it overrides, whatever they are called, and the class renders,
/// compiles under `-Xlint:all` and runs — by `overriding(fact)` in the header, and by `declareOverride`.
class GenericMethodOverrideTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Child");

    /// A type that is `Comparable` of itself: the phantom of the type parameter of
    /// `<K extends Comparable<K>> K max(K, K)`.
    interface Ranked extends Comparable<Ranked> {}

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aMethodOverridesAGenericMethodOfItsSuperclassByAnIntentionOfTheHeader() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var k = UnsafeFacts.<Object>typeVarToken(
                        me.supcheg.javafile.type.Types.typeVar("K"), ConstantDescs.CD_Object);
                var id = h.overriding(Generic_.id_K(k));
                var sh = h.extends_(Generic_.extend(h.self()));
                var cb = sh.members();
                var ctor = cb.declareConstructor();
                var call = cb.declareMethod("call", Object_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies(ctor, c -> c.super_(Generic_.new_, (b, self) -> b.end()));
                bodies.define(id.method(), (b, self, value) -> b.return_(value));
                bodies.define(
                        call,
                        (b, self, value) -> b.return_(self.call(id.method().withTypeArgs(String_.TOKEN), value)));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Child extends Generic {")
                .contains("public <K> K id(K v0) {")
                .contains("public Object call(String v0) {")
                .contains("return this.<String>id(v0);");
        Object child = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("call", String.class).invoke(child, "held"))
                .isEqualTo("held");
    }

    @Test
    void aMethodOverridesAGenericMethodWhoseBoundMentionsItsTypeParameter() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var sh = h.extends_(Generic_.extend(h.self()));
                var cb = sh.members();
                // `<K extends Comparable<K>>`: the fact is instantiated with the type variable `K`
                var k = UnsafeFacts.<Ranked>typeVarToken(
                        me.supcheg.javafile.type.Types.typeVar("K"), ClassDesc.of("java.lang", "Comparable"));
                var ctor = cb.declareConstructor();
                var max = cb.declareOverride(Generic_.max_K_K(k));
                var bodies = cb.bodies(ctor, c -> c.super_(Generic_.new_, (b, self) -> b.end()));
                bodies.define(max, (b, self, left, right) -> b.return_(left));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public <K extends Comparable<K>> K max(K v0, K v1) {")
                .contains("@Override");
    }

    @Test
    void aMethodOverridesAGenericMethodOfItsSuperclassByTheFactOfTheMember() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var k = UnsafeFacts.<CharSequence>typeVarToken(
                        me.supcheg.javafile.type.Types.typeVar("K"), ClassDesc.of("java.lang", "CharSequence"));
                var sh = h.extends_(Generic_.extend(h.self()));
                var cb = sh.members();
                var ctor = cb.declareConstructor();
                var length = cb.declareOverride(Generic_.length_K(k));
                var bodies = cb.bodies(ctor, c -> c.super_(Generic_.new_, (b, self) -> b.end()));
                bodies.define(length, (b, self, value) -> b.return_(value));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public <K extends CharSequence> CharSequence length(K v0) {")
                .contains("@Override");
        Object child = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("length", CharSequence.class).invoke(child, "held"))
                .isEqualTo("held");
    }
}
