package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.BiFunction;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// A generic method of an interface of every parameter arity, from none to sixteen, as that of a class
/// reaches: each is declared in one interface, rendered, compiled under `-Xlint:all` and run.
class TypeGenericMethodArityTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// The specification of a generic method of one type parameter, whose fact is left an `Invocable`: the
    /// test declares the method, and does not name the family of its fact.
    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, Invocable, Object> spec(
            BiFunction<TypeGenericMethodDeclaration<Self>, TypeVarToken<Object>, Invocable> build) {
        return build::apply;
    }

    @Test
    void aGenericMethodOfAnInterfaceReachesEveryParameterArity() {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.interface_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                    @Override
                    public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                        var t = typeParam("T");
                        ib.declareGenericMethod(t, spec((m, x) -> m.declareAbstractMethod("a0", String_.TOKEN)));
                        ib.declareGenericMethod(
                                t, spec((m, x) -> m.declareAbstractMethod("a1", String_.TOKEN, String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) ->
                                        m.declareAbstractMethod("a2", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a3", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a4",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a5",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a6",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a7",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a8",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a9",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a10",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a11",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a12",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a13",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a14",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a15",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        ib.declareGenericMethod(
                                t,
                                spec((m, x) -> m.declareAbstractMethod(
                                        "a16",
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN,
                                        String_.TOKEN)));
                        return ib.bodies();
                    }
                }));

        assertThat(compiled.source())
                .contains("<T> String a0();")
                .contains("<T> String a16(")
                .contains("String v15);");
    }
}
