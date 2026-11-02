package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.BiFunction;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// Every form of [TypeGenericMethodDeclaration] of an interface, at every parameter arity: the abstract,
/// `default`, `private` and `static` methods, each with none to sixteen parameters, is declared, so that
/// the arity family of the generic methods of a type is exercised as that of a class is. The members are
/// left undefined: the entry reports them, and the declaration of each form has been made by then.
class TypeGenericMethodInterfaceCoverageTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// The specification of a generic method of one type parameter, whose fact is left an `Invocable`: the
    /// test declares the method, and does not name the family of its fact.
    private static <Self> GenericMethodArities.TypeMethodSpec1<Self, Invocable, Object> spec(
            BiFunction<TypeGenericMethodDeclaration<Self>, TypeVarToken<Object>, Invocable> build) {
        return build::apply;
    }

    @Test
    void everyFormOfAGenericMethodOfAnInterfaceIsDeclared() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedInterfaceSpec() {
                            @Override
                            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                                var t = typeParam("T");
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareStaticMethod("static0", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod("static1", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static2", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static3",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static7",
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
                                        spec((m, x) -> m.declareStaticMethod(
                                                "static8",
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
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(t, spec((m, x) -> m.declareVoidStaticMethod("voidStatic0")));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareVoidStaticMethod("voidStatic1", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic2", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic3", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic8",
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
                                        spec((m, x) -> m.declareVoidStaticMethod(
                                                "voidStatic9",
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
                                        spec((m, x) -> m.declareVoidStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidStaticMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareAbstractMethod("abstract0", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.declareAbstractMethod("abstract1", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
                                                "abstract2", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
                                                "abstract3",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
                                                "abstract4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
                                                "abstract5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
                                                "abstract6",
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
                                                "abstract7",
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
                                                "abstract8",
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareVoidAbstractMethod("voidAbstract0")));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareVoidAbstractMethod("voidAbstract1", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract2", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract3", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract8",
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
                                        spec((m, x) -> m.declareVoidAbstractMethod(
                                                "voidAbstract9",
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
                                        spec((m, x) -> m.declareVoidAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidAbstractMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareDefaultMethod("default0", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.declareDefaultMethod("default1", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
                                                "default2", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
                                                "default3",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
                                                "default4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
                                                "default5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
                                                "default6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
                                                "default7",
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
                                        spec((m, x) -> m.declareDefaultMethod(
                                                "default8",
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
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(t, spec((m, x) -> m.declareVoidDefaultMethod("voidDefault0")));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareVoidDefaultMethod("voidDefault1", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault2", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault3", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault8",
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
                                        spec((m, x) -> m.declareVoidDefaultMethod(
                                                "voidDefault9",
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
                                        spec((m, x) -> m.declareVoidDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidDefaultMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declarePrivateMethod("private0", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) ->
                                                m.declarePrivateMethod("private1", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
                                                "private2", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
                                                "private3",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
                                                "private4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
                                                "private5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
                                                "private6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
                                                "private7",
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
                                        spec((m, x) -> m.declarePrivateMethod(
                                                "private8",
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
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declarePrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(t, spec((m, x) -> m.declareVoidPrivateMethod("voidPrivate0")));
                                ib.declareGenericMethod(
                                        t, spec((m, x) -> m.declareVoidPrivateMethod("voidPrivate1", String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate2", String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate3", String_.TOKEN, String_.TOKEN, String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate4",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate5",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate6",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate7",
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN,
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate8",
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
                                        spec((m, x) -> m.declareVoidPrivateMethod(
                                                "voidPrivate9",
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
                                        spec((m, x) -> m.declareVoidPrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
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
                                                String_.TOKEN)));
                                ib.declareGenericMethod(
                                        t,
                                        spec((m, x) -> m.declareVoidPrivateMethod(
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
                                                String_.TOKEN)));
                                return ib.bodies();
                            }
                        }))
                .withMessageContaining("never defined");
    }
}
