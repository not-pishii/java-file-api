import gen.facts.out.OtherSealed_;
import gen.facts.out.SealedClass_;
import gen.facts.out.SealedInterface_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.typed.ClassHeader;
import me.supcheg.javafile.typed.MemberBodies;
import me.supcheg.javafile.typed.TypedJavaFile;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// A sealed supertype in the typed layer: a permitted subtype extends or implements it, and no other.
public final class SealedTyped {
    private SealedTyped() {}

    public static void aPermittedSubclassOfASealedClassIsRendered(Typed typed) {
        String source = typed.declare(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var sh = h.extends_(SealedClass_.extend(h.self()));
                var cb = sh.members();
                var ctor = cb.declareConstructor();
                return cb.bodies(ctor, c -> c.super_(SealedClass_.new_, (b, self) -> b.end()));
            }
        });

        assertThat(source).contains("public final class Out extends SealedClass").contains("public Out()");
    }

    public static void aPermittedImplementationOfASealedInterfaceIsRendered(Typed typed) {
        String source = typed.declare(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var sides = h.overriding(SealedInterface_.sides);
                var oh = h.extendsObject();
                oh.implements_(SealedInterface_.implement(h.self()).sides(sides));
                var cb = oh.members();
                var bodies = cb.bodies();
                bodies.define(sides.method(), (b, self) -> b.return_(literal(7)));
                return bodies;
            }
        });

        assertThat(source)
                .contains("public final class Out implements SealedInterface")
                .contains("public int sides()");
    }

    public static void aSubtypeAnotherSealedClassDoesNotPermitIsRefused(Typed typed) {
        assertThatThrownBy(() -> typed.declare(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(OtherSealed_.extend(h.self())).members();
                        var ctor = cb.declareConstructor();
                        return cb.bodies(ctor, c -> c.super_(OtherSealed_.new_, (b, self) -> b.end()));
                    }
                }))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("out.OtherSealed")
                .hasMessageContaining("out.OtherOut");
    }

    public static void aNonSealedSubclassOfASealedClassIsRendered(Typed typed) {
        String source = typed.declare(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                h.nonSealed();
                var sh = h.extends_(SealedClass_.extend(h.self()));
                var cb = sh.members();
                var ctor = cb.declareConstructor();
                return cb.bodies(ctor, c -> c.super_(SealedClass_.new_, (b, self) -> b.end()));
            }
        });

        assertThat(source).contains("public non-sealed class Out extends SealedClass");
    }
}
