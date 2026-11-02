package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.FactsArity;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Number_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The stages of a generic declaration reach [FactsArity#MAX_ARITY] type parameters (8g): a class, an interface and a
/// record are declared with one to [FactsArity#MAX_ARITY], with a bound on the type parameters and a further bound on
/// the header, rendered, compiled under `-Xlint:all` and run where there is something to run. A type
/// parameter repeated, a raw bound and a type parameter that names the type are refused at every arity.
class GenericDeclarationAritiesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Box");
    private static final TargetClasspath TARGET = UnsafeFacts.unverifiedClasspath();

    /// `<T1, T2, ..., Tn>`: the type parameters as the declaration writes them.
    private static String variables(int n) {
        return "<" + IntStream.rangeClosed(1, n).mapToObj(i -> "T" + i).collect(Collectors.joining(", ")) + ">";
    }

    @Test
    void theEntryPointsOfGenericDeclarationsAreConstructed() throws ReflectiveOperationException {
        var constructor = TypedJavaFile.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThat(constructor.newInstance()).isInstanceOf(TypedJavaFile.class);
    }

    // ------------------------------------------------------------------
    // One to the widest arity of type parameters
    // ------------------------------------------------------------------

    @Test
    void aClassAnInterfaceAndARecordDeclareEveryArityOfTypeParameters() {
        for (int n = 1; n <= FactsArity.MAX_ARITY; n++) {
            assertThat(classOf(n).render())
                    .contains("public final class Box" + variables(n) + " {")
                    .contains("public T" + n + " last() {");
            assertThat(interfaceOf(n).render())
                    .contains("public interface Box" + variables(n) + " {")
                    .contains("T" + n + " last();");
            assertThat(recordOf(n).render()).contains("public record Box" + variables(n) + "(" + components(n) + ") {");
        }
    }

    @Test
    void aClassOfEveryArityOfTypeParametersCompilesAndRuns() throws Throwable {
        for (int n : new int[] {1, 2, 3, 4, 8, 12, 13, 14, 15, FactsArity.MAX_ARITY}) {
            CompiledClasses compiled = CompiledClasses.of(classOf(n));
            Object box = compiled.instantiate("me.supcheg.example.Box");
            assertThat(box.getClass().getMethod("last").invoke(box)).isNull();
        }
    }

    @Test
    void anInterfaceOfEveryArityOfTypeParametersCompiles() {
        for (int n : new int[] {1, 2, 3, 4, 8, 12, 13, 14, 15, FactsArity.MAX_ARITY}) {
            CompiledClasses compiled = CompiledClasses.of(interfaceOf(n));
            assertThat(compiled.generated().isInterface()).isTrue();
        }
    }

    @Test
    void aRecordOfEveryArityOfTypeParametersCompilesAndRuns() throws Throwable {
        for (int n : new int[] {1, 2, 3, 4, 8, 12, 13, 14, 15, FactsArity.MAX_ARITY}) {
            CompiledClasses compiled = CompiledClasses.of(recordOf(n));
            Class<?>[] parameters = new Class<?>[n];
            Arrays.fill(parameters, Object.class);
            Object box = compiled.generated().getConstructor(parameters).newInstance(new Object[n]);
            assertThat(box.getClass().getMethod("c1").invoke(box)).isNull();
        }
    }

    // ------------------------------------------------------------------
    // Bounds
    // ------------------------------------------------------------------

    @Test
    void aBoundAndAFurtherBoundAreDeclaredAtTheNewArities() {
        JavaFile aClass = TypedJavaFile.declareClass(
                TARGET,
                DESC,
                typeParam("T1"),
                typeParam("T2"),
                typeParam("T3"),
                typeParam("T4", Number_.TOKEN),
                new GenericDeclarationArities.TypedGenericClassSpec4<Object, Object, Object, Number>() {
                    @Override
                    public <Self, T1, T2, T3, T4 extends Number> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h,
                            TypeVarToken<T1> t1,
                            TypeVarToken<T2> t2,
                            TypeVarToken<T3> t3,
                            TypeVarToken<T4> t4) {
                        h.bound(t4, new Comparable_<>(t4).token);
                        return h.extendsObject().members().bodies();
                    }
                });
        assertThat(aClass.render())
                .contains("public final class Box<T1, T2, T3, T4 extends Number & Comparable<T4>> {");
        CompiledClasses.of(aClass);

        JavaFile twelve = TypedJavaFile.record(
                TARGET,
                DESC,
                typeParam("T1"),
                typeParam("T2"),
                typeParam("T3"),
                typeParam("T4"),
                typeParam("T5"),
                typeParam("T6"),
                typeParam("T7"),
                typeParam("T8"),
                typeParam("T9"),
                typeParam("T10"),
                typeParam("T11"),
                typeParam("T12", new Comparable_<>(String_.TOKEN).token),
                new GenericDeclarationArities.TypedGenericRecordSpec12<
                        Object,
                        Object,
                        Object,
                        Object,
                        Object,
                        Object,
                        Object,
                        Object,
                        Object,
                        Object,
                        Object,
                        Comparable<String>>() {
                    @Override
                    public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 extends Comparable<String>>
                            TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9,
                                    TypeVarToken<T10> t10,
                                    TypeVarToken<T11> t11,
                                    TypeVarToken<T12> t12) {
                        rb.component("value", t12);
                        return rb.bodies();
                    }
                });
        assertThat(twelve.render())
                .contains("public record Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11,"
                        + " T12 extends Comparable<String>>(T12 value) {");
        CompiledClasses.of(twelve);

        JavaFile anInterface = TypedJavaFile.interface_(
                TARGET,
                DESC,
                typeParam("T1"),
                typeParam("T2"),
                typeParam("T3"),
                typeParam("T4"),
                typeParam("T5"),
                typeParam("T6"),
                typeParam("T7"),
                typeParam("T8", Number_.TOKEN),
                new GenericDeclarationArities.TypedGenericInterfaceSpec8<
                        Object, Object, Object, Object, Object, Object, Object, Number>() {
                    @Override
                    public <Self, T1, T2, T3, T4, T5, T6, T7, T8 extends Number> TypedInterfaceBodies<Self> build(
                            TypedInterfaceBuilder<Self> ib,
                            TypeVarToken<T1> t1,
                            TypeVarToken<T2> t2,
                            TypeVarToken<T3> t3,
                            TypeVarToken<T4> t4,
                            TypeVarToken<T5> t5,
                            TypeVarToken<T6> t6,
                            TypeVarToken<T7> t7,
                            TypeVarToken<T8> t8) {
                        ib.bound(t8, new Comparable_<>(t8).token);
                        ib.declareAbstractMethod("last", t8);
                        return ib.bodies();
                    }
                });
        assertThat(anInterface.render())
                .contains("public interface Box<T1, T2, T3, T4, T5, T6, T7, T8 extends Number & Comparable<T8>> {");
        CompiledClasses.of(anInterface);
    }

    @Test
    void aDeclarationOfTwelveTypeParametersRefusesARepeatedName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T1"),
                        new GenericDeclarationArities.TypedGenericClassSpec12<
                                Object,
                                Object,
                                Object,
                                Object,
                                Object,
                                Object,
                                Object,
                                Object,
                                Object,
                                Object,
                                Object,
                                Object>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
                                    MemberBodies<Self, ?> build(
                                            ClassHeader<Self> h,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("has two type parameters T1");
    }

    @Test
    void aDeclarationOfFourTypeParametersRefusesARawBound() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4", UnsafeFacts.<Comparable<?>>interfaceToken(Comparable_.Data.SHAPE)),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec4<
                                Object, Object, Object, Comparable<?>>() {
                            @Override
                            public <Self, T1, T2, T3, T4 extends Comparable<?>> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("cannot have the raw bound java.lang.Comparable");
    }

    @Test
    void aDeclarationOfFourTypeParametersRefusesAParameterNamedAsTheType() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("Box"),
                        typeParam("T4"),
                        new GenericDeclarationArities.TypedGenericRecordSpec4<Object, Object, Object, Object>() {
                            @Override
                            public <Self, T1, T2, T3, T4> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4) {
                                return Unreached.here();
                            }
                        }))
                .withMessageContaining("cannot have the type parameter Box");
    }

    // ------------------------------------------------------------------
    // The declarations of the arities
    // ------------------------------------------------------------------

    private static String components(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(i -> "T" + i + " c" + i).collect(Collectors.joining(", "));
    }

    private static JavaFile classOf(int n) {
        return switch (n) {
            case 1 ->
                TypedJavaFile.declareClass(
                        TARGET, DESC, typeParam("T1"), new GenericDeclarationArities.TypedGenericClassSpec1<>() {
                            @Override
                            public <Self, T1> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1) {
                                return last(h, t1);
                            }
                        });
            case 2 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        new GenericDeclarationArities.TypedGenericClassSpec2<>() {
                            @Override
                            public <Self, T1, T2> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2) {
                                return last(h, t2);
                            }
                        });
            case 3 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        new GenericDeclarationArities.TypedGenericClassSpec3<>() {
                            @Override
                            public <Self, T1, T2, T3> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3) {
                                return last(h, t3);
                            }
                        });
            case 4 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        new GenericDeclarationArities.TypedGenericClassSpec4<>() {
                            @Override
                            public <Self, T1, T2, T3, T4> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4) {
                                return last(h, t4);
                            }
                        });
            case 5 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        new GenericDeclarationArities.TypedGenericClassSpec5<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5) {
                                return last(h, t5);
                            }
                        });
            case 6 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        new GenericDeclarationArities.TypedGenericClassSpec6<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6) {
                                return last(h, t6);
                            }
                        });
            case 7 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        new GenericDeclarationArities.TypedGenericClassSpec7<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7) {
                                return last(h, t7);
                            }
                        });
            case 8 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        new GenericDeclarationArities.TypedGenericClassSpec8<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8) {
                                return last(h, t8);
                            }
                        });
            case 9 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        new GenericDeclarationArities.TypedGenericClassSpec9<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9) {
                                return last(h, t9);
                            }
                        });
            case 10 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        new GenericDeclarationArities.TypedGenericClassSpec10<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9,
                                    TypeVarToken<T10> t10) {
                                return last(h, t10);
                            }
                        });
            case 11 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        new GenericDeclarationArities.TypedGenericClassSpec11<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MemberBodies<Self, ?> build(
                                    ClassHeader<Self> h,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9,
                                    TypeVarToken<T10> t10,
                                    TypeVarToken<T11> t11) {
                                return last(h, t11);
                            }
                        });
            case 12 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        new GenericDeclarationArities.TypedGenericClassSpec12<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
                                    MemberBodies<Self, ?> build(
                                            ClassHeader<Self> h,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12) {
                                return last(h, t12);
                            }
                        });
            case 13 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        new GenericDeclarationArities.TypedGenericClassSpec13<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
                                    MemberBodies<Self, ?> build(
                                            ClassHeader<Self> h,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13) {
                                return last(h, t13);
                            }
                        });
            case 14 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        new GenericDeclarationArities.TypedGenericClassSpec14<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
                                    MemberBodies<Self, ?> build(
                                            ClassHeader<Self> h,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14) {
                                return last(h, t14);
                            }
                        });
            case 15 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        typeParam("T15"),
                        new GenericDeclarationArities.TypedGenericClassSpec15<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
                                    MemberBodies<Self, ?> build(
                                            ClassHeader<Self> h,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14,
                                            TypeVarToken<T15> t15) {
                                return last(h, t15);
                            }
                        });
            case 16 ->
                TypedJavaFile.declareClass(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        typeParam("T15"),
                        typeParam("T16"),
                        new GenericDeclarationArities.TypedGenericClassSpec16<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
                                    MemberBodies<Self, ?> build(
                                            ClassHeader<Self> h,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14,
                                            TypeVarToken<T15> t15,
                                            TypeVarToken<T16> t16) {
                                return last(h, t16);
                            }
                        });
            default -> throw new IllegalArgumentException("arity " + n);
        };
    }

    /// A class whose last type parameter is `last`, with `public T last()`: a member that names the token.
    private static <Self, T> MemberBodies<Self, ?> last(ClassHeader<Self> h, TypeVarToken<T> last) {
        var members = h.extendsObject().members();
        var method = members.declareMethod("last", last);
        var bodies = members.bodies();
        bodies.define(method, (b, self) -> b.return_(literalNull(last)));
        return bodies;
    }

    private static JavaFile interfaceOf(int n) {
        return switch (n) {
            case 1 ->
                TypedJavaFile.interface_(
                        TARGET, DESC, typeParam("T1"), new GenericDeclarationArities.TypedGenericInterfaceSpec1<>() {
                            @Override
                            public <Self, T1> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1) {
                                return last(ib, t1);
                            }
                        });
            case 2 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec2<>() {
                            @Override
                            public <Self, T1, T2> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2) {
                                return last(ib, t2);
                            }
                        });
            case 3 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec3<>() {
                            @Override
                            public <Self, T1, T2, T3> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3) {
                                return last(ib, t3);
                            }
                        });
            case 4 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec4<>() {
                            @Override
                            public <Self, T1, T2, T3, T4> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4) {
                                return last(ib, t4);
                            }
                        });
            case 5 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec5<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5) {
                                return last(ib, t5);
                            }
                        });
            case 6 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec6<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6) {
                                return last(ib, t6);
                            }
                        });
            case 7 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec7<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7) {
                                return last(ib, t7);
                            }
                        });
            case 8 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec8<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8) {
                                return last(ib, t8);
                            }
                        });
            case 9 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec9<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9) {
                                return last(ib, t9);
                            }
                        });
            case 10 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec10<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TypedInterfaceBodies<Self> build(
                                    TypedInterfaceBuilder<Self> ib,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9,
                                    TypeVarToken<T10> t10) {
                                return last(ib, t10);
                            }
                        });
            case 11 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec11<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
                                    TypedInterfaceBodies<Self> build(
                                            TypedInterfaceBuilder<Self> ib,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11) {
                                return last(ib, t11);
                            }
                        });
            case 12 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec12<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
                                    TypedInterfaceBodies<Self> build(
                                            TypedInterfaceBuilder<Self> ib,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12) {
                                return last(ib, t12);
                            }
                        });
            case 13 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec13<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
                                    TypedInterfaceBodies<Self> build(
                                            TypedInterfaceBuilder<Self> ib,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13) {
                                return last(ib, t13);
                            }
                        });
            case 14 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec14<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
                                    TypedInterfaceBodies<Self> build(
                                            TypedInterfaceBuilder<Self> ib,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14) {
                                return last(ib, t14);
                            }
                        });
            case 15 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        typeParam("T15"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec15<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
                                    TypedInterfaceBodies<Self> build(
                                            TypedInterfaceBuilder<Self> ib,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14,
                                            TypeVarToken<T15> t15) {
                                return last(ib, t15);
                            }
                        });
            case 16 ->
                TypedJavaFile.interface_(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        typeParam("T15"),
                        typeParam("T16"),
                        new GenericDeclarationArities.TypedGenericInterfaceSpec16<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
                                    TypedInterfaceBodies<Self> build(
                                            TypedInterfaceBuilder<Self> ib,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14,
                                            TypeVarToken<T15> t15,
                                            TypeVarToken<T16> t16) {
                                return last(ib, t16);
                            }
                        });
            default -> throw new IllegalArgumentException("arity " + n);
        };
    }

    /// An interface whose last type parameter is `last`, with `T last()`.
    private static <Self, T> TypedInterfaceBodies<Self> last(TypedInterfaceBuilder<Self> ib, TypeVarToken<T> last) {
        ib.declareAbstractMethod("last", last);
        return ib.bodies();
    }

    private static JavaFile recordOf(int n) {
        return switch (n) {
            case 1 ->
                TypedJavaFile.record(
                        TARGET, DESC, typeParam("T1"), new GenericDeclarationArities.TypedGenericRecordSpec1<>() {
                            @Override
                            public <Self, T1> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1) {
                                return components(rb, List.of(t1));
                            }
                        });
            case 2 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        new GenericDeclarationArities.TypedGenericRecordSpec2<>() {
                            @Override
                            public <Self, T1, T2> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2) {
                                return components(rb, List.of(t1, t2));
                            }
                        });
            case 3 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        new GenericDeclarationArities.TypedGenericRecordSpec3<>() {
                            @Override
                            public <Self, T1, T2, T3> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3) {
                                return components(rb, List.of(t1, t2, t3));
                            }
                        });
            case 4 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        new GenericDeclarationArities.TypedGenericRecordSpec4<>() {
                            @Override
                            public <Self, T1, T2, T3, T4> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4) {
                                return components(rb, List.of(t1, t2, t3, t4));
                            }
                        });
            case 5 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        new GenericDeclarationArities.TypedGenericRecordSpec5<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5) {
                                return components(rb, List.of(t1, t2, t3, t4, t5));
                            }
                        });
            case 6 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        new GenericDeclarationArities.TypedGenericRecordSpec6<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6));
                            }
                        });
            case 7 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        new GenericDeclarationArities.TypedGenericRecordSpec7<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6, t7));
                            }
                        });
            case 8 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        new GenericDeclarationArities.TypedGenericRecordSpec8<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8));
                            }
                        });
            case 9 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        new GenericDeclarationArities.TypedGenericRecordSpec9<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9));
                            }
                        });
            case 10 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        new GenericDeclarationArities.TypedGenericRecordSpec10<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9,
                                    TypeVarToken<T10> t10) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
                            }
                        });
            case 11 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        new GenericDeclarationArities.TypedGenericRecordSpec11<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> TypedRecordBodies<Self> build(
                                    TypedRecordBuilder<Self> rb,
                                    TypeVarToken<T1> t1,
                                    TypeVarToken<T2> t2,
                                    TypeVarToken<T3> t3,
                                    TypeVarToken<T4> t4,
                                    TypeVarToken<T5> t5,
                                    TypeVarToken<T6> t6,
                                    TypeVarToken<T7> t7,
                                    TypeVarToken<T8> t8,
                                    TypeVarToken<T9> t9,
                                    TypeVarToken<T10> t10,
                                    TypeVarToken<T11> t11) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));
                            }
                        });
            case 12 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        new GenericDeclarationArities.TypedGenericRecordSpec12<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
                                    TypedRecordBodies<Self> build(
                                            TypedRecordBuilder<Self> rb,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12));
                            }
                        });
            case 13 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        new GenericDeclarationArities.TypedGenericRecordSpec13<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
                                    TypedRecordBodies<Self> build(
                                            TypedRecordBuilder<Self> rb,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13) {
                                return components(rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));
                            }
                        });
            case 14 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        new GenericDeclarationArities.TypedGenericRecordSpec14<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>
                                    TypedRecordBodies<Self> build(
                                            TypedRecordBuilder<Self> rb,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14) {
                                return components(
                                        rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14));
                            }
                        });
            case 15 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        typeParam("T15"),
                        new GenericDeclarationArities.TypedGenericRecordSpec15<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
                                    TypedRecordBodies<Self> build(
                                            TypedRecordBuilder<Self> rb,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14,
                                            TypeVarToken<T15> t15) {
                                return components(
                                        rb, List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
                            }
                        });
            case 16 ->
                TypedJavaFile.record(
                        TARGET,
                        DESC,
                        typeParam("T1"),
                        typeParam("T2"),
                        typeParam("T3"),
                        typeParam("T4"),
                        typeParam("T5"),
                        typeParam("T6"),
                        typeParam("T7"),
                        typeParam("T8"),
                        typeParam("T9"),
                        typeParam("T10"),
                        typeParam("T11"),
                        typeParam("T12"),
                        typeParam("T13"),
                        typeParam("T14"),
                        typeParam("T15"),
                        typeParam("T16"),
                        new GenericDeclarationArities.TypedGenericRecordSpec16<>() {
                            @Override
                            public <Self, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
                                    TypedRecordBodies<Self> build(
                                            TypedRecordBuilder<Self> rb,
                                            TypeVarToken<T1> t1,
                                            TypeVarToken<T2> t2,
                                            TypeVarToken<T3> t3,
                                            TypeVarToken<T4> t4,
                                            TypeVarToken<T5> t5,
                                            TypeVarToken<T6> t6,
                                            TypeVarToken<T7> t7,
                                            TypeVarToken<T8> t8,
                                            TypeVarToken<T9> t9,
                                            TypeVarToken<T10> t10,
                                            TypeVarToken<T11> t11,
                                            TypeVarToken<T12> t12,
                                            TypeVarToken<T13> t13,
                                            TypeVarToken<T14> t14,
                                            TypeVarToken<T15> t15,
                                            TypeVarToken<T16> t16) {
                                return components(
                                        rb,
                                        List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16));
                            }
                        });
            default -> throw new IllegalArgumentException("arity " + n);
        };
    }

    private static <Self> TypedRecordBodies<Self> components(
            TypedRecordBuilder<Self> rb, List<TypeVarToken<?>> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            rb.component("c" + (i + 1), tokens.get(i));
        }
        return rb.bodies();
    }
}
