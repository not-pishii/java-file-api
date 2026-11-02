package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Picking_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.PlainPicking_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static me.supcheg.javafile.type.Types.typeVar;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// A generic method of an interface, implemented by a generic method of the class: the step of the proof
/// declares the type parameters of the method, and the class names them with the type variables its fact
/// is instantiated with — the names may differ, the bounds may not (JLS 8.4.8.3).
class GenericMethodImplementsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final ClassDesc CD_CHAR_SEQUENCE = ClassDesc.of("java.lang", "CharSequence");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    private static TypeVarToken<CharSequence> witness() {
        return UnsafeFacts.typeVarToken(typeVar("K"), CD_CHAR_SEQUENCE);
    }

    @Test
    void aGenericMethodOfAnInterfaceIsImplementedByAGenericMethodOfTheClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var k = witness();
                var pick = h.overriding(Picking_.pick_T(k));
                var hold = h.overriding(Picking_.hold_List(k));
                var oh = h.extendsObject();
                oh.implements_(Picking_.implement(h.self()).hold_List(hold).pick_T(pick));
                var cb = oh.members();
                var bodies = cb.bodies();
                bodies.define(pick.method(), (b, self, value) -> b.return_(value));
                bodies.define(hold.method(), (b, self, values) -> b.end());
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Probe implements Picking {")
                .contains("@Override\n    public <K extends CharSequence> K pick(K v0) {")
                .contains("@Override\n    public <K extends CharSequence> void hold(List<K> v0) {");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("pick", CharSequence.class).invoke(probe, "held"))
                .isEqualTo("held");
        assertThat(compiled.generated().getMethod("hold", List.class).invoke(probe, List.of("held")))
                .isNull();
    }

    @Test
    void aFactWithoutTypeParametersImplementsNoGenericMethod() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var k = witness();
                        var pick = h.overriding(PlainPicking_.pick_CharSequence);
                        var hold = h.overriding(Picking_.hold_List(k));
                        var oh = h.extendsObject();
                        oh.implements_(
                                Picking_.implement(h.self()).hold_List(hold).pick_T(pick));
                        var cb = oh.members();
                        var bodies = cb.bodies();
                        bodies.define(pick.method(), (b, self, value) -> b.return_(value));
                        bodies.define(hold.method(), (b, self, values) -> b.end());
                        return bodies;
                    }
                }))
                .withMessageContaining("the method names 0 type parameters");
    }
}
