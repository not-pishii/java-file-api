import gen.facts.p.ByDollar_;
import gen.facts.p.ByHidden_;
import gen.facts.p.ByMarker_;
import gen.facts.p.Mentions_;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.type.Types;
import p.ByHidden;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// `Mentions` mentions generic types whose bounds a metamodel cannot
/// write: `ByHidden<T extends Hidden>`, with a type that is not `public`,
/// and `ByDollar<T extends List<Dol$lar>>`, with one that has no
/// metamodel. Their token-only metamodels have no type parameters —
/// without the bound javac would take a token of any type — but the shape
/// is that of the generic type, and a signature applies it.
public final class MentionedBounds {
    private MentionedBounds() {}

    public static void theShapeIsThatOfTheGenericType() {
        assertThat(ByHidden_.Data.SHAPE.typeParameters()).hasSize(1);
        assertThat(ByDollar_.Data.SHAPE.typeParameters()).hasSize(1);
        assertThat(ByMarker_.Data.SHAPE.typeParameters()).hasSize(1);
    }

    public static void aSignatureAppliesTheShape() {
        DeclaredToken<ByHidden<?>> hidden = (DeclaredToken<ByHidden<?>>) Mentions_.hidden.result();

        assertThat(hidden.typeRef()).isEqualTo(Types.parameterized(ClassDesc.of("p.ByHidden"), Types.unbounded()));
        assertThat(hidden.shape()).isSameAs(ByHidden_.Data.SHAPE);
        assertThat(Mentions_.dollar.result().typeRef())
                .isEqualTo(Types.parameterized(ClassDesc.of("p.ByDollar"), Types.unbounded()));
        assertThat(Mentions_.marker.result().typeRef())
                .isEqualTo(Types.parameterized(ClassDesc.of("p.ByMarker"), Types.unbounded()));
    }
}
