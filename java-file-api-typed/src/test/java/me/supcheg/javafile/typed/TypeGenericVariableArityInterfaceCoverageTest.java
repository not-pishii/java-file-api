package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.BiFunction;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Every form of a generic method of variable arity of an interface, at every parameter arity from one
/// to sixteen: the abstract, `default`, `private` and `static` methods are declared, so that the arity
/// family of the generic methods of variable arity of a type is exercised as that of a class is. The
/// members are left undefined: the entry reports them, and the declaration of each form has been made
/// by then.
class TypeGenericVariableArityInterfaceCoverageTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// The specification of a generic method of one type parameter, whose fact is left an `Invocable`: the
    /// test declares the method, and does not name the family of its fact.
    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, Invocable, Object> spec(
            BiFunction<TypeGenericMethodDeclaration<Self>, TypeVarToken<Object>, Invocable> build) {
        return build::apply;
    }

    @Test
    void everyFormOfAVariableArityGenericMethodOfAnInterfaceIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var t = typeParam("T");
                                var strings = ArrayToken.of(String_.TOKEN);
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod("static1", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static2", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static3",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static10",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareStaticMethod(
                                                        "static16",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.variableArity().declareVoidStaticMethod("voidStatic1", strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod("voidStatic2", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic3", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic10",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidStaticMethod(
                                                        "voidStatic16",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod("abstract1", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract2", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract3",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract10",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareAbstractMethod(
                                                        "abstract16",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.variableArity().declareVoidAbstractMethod("voidAbstract1", strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod("voidAbstract2", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract3", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract10",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidAbstractMethod(
                                                        "voidAbstract16",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod("default1", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default2", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default3",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default10",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareDefaultMethod(
                                                        "default16",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.variableArity().declareVoidDefaultMethod("voidDefault1", strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod("voidDefault2", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault3", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault10",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidDefaultMethod(
                                                        "voidDefault16",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod("private1", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private2", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private3",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private10",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declarePrivateMethod(
                                                        "private16",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.variableArity().declareVoidPrivateMethod("voidPrivate1", strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod("voidPrivate2", String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate3", String_.TOKEN, String_.TOKEN, strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate4",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate5",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate6",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate7",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate8",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate9",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate10",
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        String_.TOKEN,
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate11",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate12",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate13",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate14",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate15",
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
                                                        strings)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.variableArity()
                                                .declareVoidPrivateMethod(
                                                        "voidPrivate16",
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
                                                        strings)));
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("never defined");
    }
}
