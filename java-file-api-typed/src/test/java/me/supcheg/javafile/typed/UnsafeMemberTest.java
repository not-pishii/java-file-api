package me.supcheg.javafile.typed;

import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// The escape hatch of the members of a class ([Unsafe#member]): a member
/// of the untyped core is a member of the class as it is, among the declared
/// ones and in the order of the declarations.
class UnsafeMemberTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    @Test
    void anUnsafeMemberIsAMemberOfTheClassAsItIs() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var before = cb.declareMethod("before", PrimitiveToken.INT);
                        cb.add(Unsafe.member(new FieldDecl(
                                "raw",
                                Types.INT,
                                List.of(),
                                Set.of(Modifier.PUBLIC),
                                Optional.of(Exprs.literal(7)),
                                Optional.empty())));
                        var after = cb.declareMethod("after", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(after, (b, _) -> b.return_(literal(2)));
                        bodies.define(before, (b, _) -> b.return_(literal(1)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .containsSubsequence("public int before() {", "public int raw = 7;", "public int after() {");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getField("raw").getInt(probe)).isEqualTo(7);
        assertThat(compiled.invoke("before")).isEqualTo(1);
        assertThat(compiled.invoke("after")).isEqualTo(2);
    }
}
