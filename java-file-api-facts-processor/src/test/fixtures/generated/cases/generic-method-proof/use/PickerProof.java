import gen.facts.p.Picker_;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.typed.ClassHeader;
import me.supcheg.javafile.typed.MemberBodies;
import me.supcheg.javafile.typed.TypedJavaFile;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// An interface with a generic abstract method has a proof: the step of the method declares its type
/// parameters, and an implementation names them with the type variables its fact is instantiated with.
public final class PickerProof {
    private PickerProof() {}

    private static final ClassDesc CD_CHAR_SEQUENCE = ClassDesc.of("java.lang", "CharSequence");

    public static void theFactOfTheGenericMethodTakesAWitness() {
        TypeVarToken<CharSequence> k = UnsafeFacts.typeVarToken(Types.typeVar("K"), CD_CHAR_SEQUENCE);
        var pick = Picker_.pick_T(k);

        assertThat(pick.name()).isEqualTo("pick");
        assertThat(pick.params()).containsExactly(k);
        assertThat(pick.result()).isSameAs(k);
    }

    public static void aGenericMethodOfTheClassImplementsIt(Typed typed) {
        String source = typed.declare(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                // the type parameter of the implementing method, bounded as the interface's is
                TypeVarToken<CharSequence> k = UnsafeFacts.typeVarToken(Types.typeVar("K"), CD_CHAR_SEQUENCE);
                var pick = h.overriding(Picker_.pick_T(k));
                var oh = h.extendsObject();
                oh.implements_(Picker_.implement(h.self()).pick_T(pick));
                var cb = oh.members();
                var bodies = cb.bodies();
                bodies.define(pick.method(), (b, self, value) -> b.return_(value));
                return bodies;
            }
        });

        assertThat(source)
                .contains("public final class Out implements Picker {")
                .contains("public <K extends CharSequence> K pick(K v0)")
                .contains("return v0;");
    }
}
