package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// Verifies every fact of the hand-written metamodel against the running JDK.
class JdkMetamodelTest {

    private static final List<Object> METAMODELS = List.of(
            Object_.class,
            String_.class,
            Integer_.class,
            Math_.class,
            System_.class,
            PrintStream_.class,
            StringBuilder_.class,
            CharSequence_.class,
            Objects_.class,
            Runnable_.class,
            Throwable_.class,
            Exception_.class,
            RuntimeException_.class,
            IllegalArgumentException_.class,
            IllegalStateException_.class,
            NumberFormatException_.class,
            IOException_.class,
            new List_<>(String_.TOKEN),
            new ArrayList_<>(String_.TOKEN),
            new Stream_<>(String_.TOKEN),
            Function_.of(String_.TOKEN, Integer_.TOKEN),
            Supplier_.of(String_.TOKEN),
            Consumer_.of(String_.TOKEN),
            Predicate_.of(String_.TOKEN),
            Comparable_.of(String_.TOKEN));

    @TestFactory
    Stream<DynamicTest> everyFactExistsInTheJdk() throws ReflectiveOperationException {
        List<DynamicTest> tests = new ArrayList<>();
        for (Object metamodel : METAMODELS) {
            for (Object fact : facts(metamodel)) {
                tests.add(DynamicTest.dynamicTest(fact.toString(), () -> verify(fact)));
            }
        }
        tests.add(DynamicTest.dynamicTest("generic methods", () -> {
            verify(new Stream_<>(String_.TOKEN).map(Integer_.TOKEN));
            verify(Objects_.requireNonNull(String_.TOKEN));
        }));
        return tests.stream();
    }

    private static List<Object> facts(Object metamodel) throws IllegalAccessException {
        Class<?> type = metamodel instanceof Class<?> c ? c : metamodel.getClass();
        List<Object> facts = new ArrayList<>();
        for (Field field : type.getFields()) {
            Object value = field.get(metamodel instanceof Class<?> ? null : metamodel);
            if (value instanceof Invocable || value instanceof FieldRef<?, ?> || value instanceof StaticFieldRef<?>) {
                facts.add(value);
            }
        }
        assertThat(facts).as(type.getName()).isNotEmpty();
        return facts;
    }

    private static void verify(Object fact) throws ReflectiveOperationException {
        switch (fact) {
            case Invocable invocable -> verifyInvocable(invocable);
            case StaticFieldRef<?> field -> {
                Field reflected = load(field.owner()).getField(field.name());
                assertThat(Modifier.isStatic(reflected.getModifiers())).isTrue();
                assertThat(erasure(field.type())).isEqualTo(reflected.getType());
                if (field.constantValue().isPresent()) {
                    assertThat(reflected.get(null))
                            .isEqualTo(field.constantValue().get());
                }
            }
            default -> throw new AssertionError(fact);
        }
    }

    private static void verifyInvocable(Invocable invocable) throws ReflectiveOperationException {
        Class<?> owner = load(invocable.owner());
        Executable executable;
        if (invocable.kind() == InvocableKind.CONSTRUCTOR) {
            executable = findConstructor(owner, invocable.params().size(), invocable);
        } else {
            executable = findMethod(owner, invocable);
            Method method = (Method) executable;
            assertThat(Modifier.isStatic(method.getModifiers()))
                    .as("static-ness of " + invocable)
                    .isEqualTo(invocable.kind() == InvocableKind.STATIC_METHOD);
            if (invocable.resultType().isEmpty()) {
                assertThat(method.getReturnType()).isEqualTo(void.class);
            } else if (!(method.getGenericReturnType() instanceof TypeVariable<?>)) {
                assertThat(method.getReturnType())
                        .isEqualTo(erasure(invocable.resultType().get()));
            }
            Overridability expected = Modifier.isAbstract(method.getModifiers())
                    ? Overridability.ABSTRACT
                    : Modifier.isFinal(method.getModifiers())
                                    || Modifier.isFinal(owner.getModifiers())
                                    || Modifier.isStatic(method.getModifiers())
                            ? Overridability.FINAL
                            : Overridability.OVERRIDABLE;
            assertThat(invocable.traits().overridability())
                    .as("overridability of " + invocable)
                    .isEqualTo(expected);
            assertThat(invocable.traits().typeArgs()).hasSize(method.getTypeParameters().length);
        }
        Set<Class<?>> declared = Set.of(executable.getExceptionTypes());
        Set<Class<?>> ours = invocable.traits().throwsTypes().stream()
                .map(JdkMetamodelTest::load)
                .collect(Collectors.toSet());
        assertThat(ours).as("throws of " + invocable).isEqualTo(declared);
    }

    private static Method findMethod(Class<?> owner, Invocable invocable) {
        return Stream.of(owner.getMethods())
                .filter(m -> !m.isBridge() && m.getName().equals(invocable.name()))
                .filter(m -> matches(m.getGenericParameterTypes(), m.getParameterTypes(), invocable.params()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("no " + invocable));
    }

    private static Constructor<?> findConstructor(Class<?> owner, int arity, Invocable invocable) {
        return Stream.of(owner.getConstructors())
                .filter(c -> c.getParameterCount() == arity)
                .filter(c -> matches(c.getGenericParameterTypes(), c.getParameterTypes(), invocable.params()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("no " + invocable));
    }

    private static boolean matches(Type[] generic, Class<?>[] erased, List<TypeToken<?>> params) {
        if (erased.length != params.size()) {
            return false;
        }
        for (int i = 0; i < erased.length; i++) {
            if (!(generic[i] instanceof TypeVariable<?>) && !erased[i].equals(erasure(params.get(i)))) {
                return false;
            }
        }
        return true;
    }

    private static Class<?> erasure(TypeToken<?> token) {
        try {
            return (Class<?>) token.erasure().resolveConstantDesc(MethodHandles.lookup());
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private static Class<?> load(TypeToken<?> token) {
        return erasure(token);
    }
}
