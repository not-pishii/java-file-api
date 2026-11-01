package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Closing;
import me.supcheg.javafile.typed.testfacts.java.io.FileNotFoundException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Closing_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The refusals of try-with-resources (6.3): a resource that is no `AutoCloseable`, more than one
/// existing variable, a mutable one, and a try without a resource are refused by the typed layer.
class TryResourcesChecksTest {

    @Test
    void aResourceThatIsNoAutoCloseableIsRefused() {
        assertThatThrownBy(() -> new Resources(Body.root("body")).resource_(String_.TOKEN, literal("text")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no subtype of java.lang.AutoCloseable");
    }

    @Test
    void atMostOneExistingVariableAndNoMutableOne() {
        Body<Prim.Int> body = Body.root("body");
        Var<Closing> first = new Var<>(Closing_.TOKEN, Var.Role.Fixed.PARAMETER, body);
        assertThatThrownBy(() -> {
                    Resources resources = new Resources(body);
                    resources.resource_(first);
                    resources.resource_(first);
                })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("at most one resource");
        Body<Prim.Int> other = Body.root("other");
        MutVar<Prim.Int> mutable = new MutVar<>(PrimitiveToken.INT, Var.Role.Mutable.LOCAL, other);
        assertThatThrownBy(() -> new Resources(other).resource_(mutable))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is mutable");
    }

    @Test
    void aTryWithoutAResourceIsRefused() {
        assertThatThrownBy(() -> new Resources(Body.root("body")).close())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("at least one resource");
    }

    @Test
    void aResourceIsNotDeclaredAfterTheTryIsBuilt() {
        Resources resources = new Resources(Body.<Prim.Int>root("body"));
        resources.resource_(Closing_.TOKEN, new_(Closing_.new_boolean, literal(false)));
        assertThat(resources.close()).hasSize(1);

        assertThatThrownBy(() -> resources.resource_(Closing_.TOKEN, new_(Closing_.new_boolean, literal(false))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("resources of the try are complete");
    }

    @Test
    void anUntypedResourceInitializerLeavesTheCatchClausesToTheAuthor() {
        assertThatCode(() -> {
                    JavaFile ignored = TypedJavaFile.class_(
                            UnsafeFacts.unverifiedClasspath(),
                            ClassDesc.of("me.supcheg.example", "RawResource"),
                            new TypedJavaFile.TypedClassSpec() {
                                @Override
                                public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                    var method = cb.declareVoidStaticMethod("method");
                                    var bodies = cb.bodies();
                                    bodies.define(
                                            method,
                                            b -> b.tryWithResources(
                                                            h -> h.catch_(
                                                                    FileNotFoundException_.TOKEN,
                                                                    (c, _) -> c.exec(staticCall(Closing_.closes))),
                                                            (t, resources) -> {
                                                                resources.resource_(
                                                                        Closing_.TOKEN,
                                                                        Unsafe.expr(
                                                                                me.supcheg.javafile.code.Exprs.literal(
                                                                                        "raw"),
                                                                                Closing_.TOKEN));
                                                                t.exec(staticCall(Closing_.closes));
                                                            })
                                                    .end());
                                    return bodies;
                                }
                            });
                })
                .doesNotThrowAnyException();
    }

    @Test
    void theResourcesAreThoseOfTheBodyInOrder() {
        Resources resources = new Resources(Body.<Prim.Int>root("body"));
        resources.resource_(Closing_.TOKEN, new_(Closing_.new_boolean, literal(false)));
        List<Instr.Resource> made = resources.close();

        assertThat(made).hasSize(1);
        assertThat(made.getFirst().init()).isPresent();
    }
}
