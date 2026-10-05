package me.supcheg.javafile.typed.golden;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.jdk.Integer_;
import me.supcheg.javafile.facts.jdk.Object_;
import me.supcheg.javafile.facts.jdk.String_;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The golden comparison of Q12 (mini-spec §9.1): the metamodels the `@Facts` processor generates for the types of the
/// hand-written `facts/jdk` (`me.supcheg.javafile.typed.generated`) against the
/// hand-written ones, until those are deleted.
///
/// For every type the token (its class, `typeRef`, type parameters, kind, superclasses, supertypes and the method table
/// the shape holds) and every hand-written fact are compared with the generated ones. The pair of a hand-written fact
/// is
/// the generated one of the same member, found by its kind and the erasures of its parameters, not by its name: the
/// generated one is `substring_int`, the hand-written `substring`. A generic metamodel is instantiated, hand-written
/// and
/// generated, by the same tokens first. The shape's `ShapeOrigin` and fingerprint differ by their nature and are not
/// compared, nor are the facts and the method table entries only the generated metamodel has: the hand-written one
/// has a few members of a type, the generated one all of them.
///
/// Every difference is listed, not the first one. Each is either a [#KNOWN] one, with the reason, or fails the test,
/// and
/// a known one that did not come up fails it too.
class GoldenTest {
    private static final String HAND_WRITTEN = "me.supcheg.javafile.facts.jdk.";
    private static final String GENERATED = "me.supcheg.javafile.typed.testfacts.";

    /// The types of the hand-written `facts/jdk`.
    private static final List<Class<?>> TYPES = List.of(
            java.util.ArrayList.class,
            CharSequence.class,
            Comparable.class,
            Consumer.class,
            Exception.class,
            Function.class,
            IOException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            Integer.class,
            java.util.List.class,
            Math.class,
            NumberFormatException.class,
            Object.class,
            Objects.class,
            Predicate.class,
            PrintStream.class,
            Runnable.class,
            RuntimeException.class,
            Stream.class,
            String.class,
            StringBuilder.class,
            Supplier.class,
            System.class,
            Throwable.class);

    /// What stands for the type arguments of a generic metamodel, and for the type arguments of a generic method.
    private static final List<RefToken<?>> WITNESSES = List.of(String_.TOKEN, Integer_.TOKEN, Object_.TOKEN);

    private static final String HAND_RECORDS_NO_SUPERTYPES =
            "hand-written: Jdk reads the supertypes of a generic type alone, a type that is not generic records none, the"
                    + " generated one has Comparable<T>";
    private static final String HAND_HAS_THE_BRIDGE =
            "hand-written: Jdk reads getMethods(), whose compareTo(Object) is the bridge method of compareTo(T), which is"
                    + " not a member in source";
    private static final String HAND_OWNS_STATICS_BY_THE_INSTANCE =
            "hand-written: the owner of a static method is the instantiated token List<String>; a static method has no"
                    + " receiver, the generated one is owned by List<?>";

    /// The differences that are understood, by their [Finding#id()], with the reason. A difference that is an error of
    /// the hand-written metamodel is left as it is (it goes away with it); one that is an error of the processor is
    /// listed with a description for the report on the step, and none is.
    private static final Map<String, String> KNOWN = Map.of(
            "java.lang.Integer <token> supertypes",
            HAND_RECORDS_NO_SUPERTYPES,
            "java.lang.String <token> supertypes",
            HAND_RECORDS_NO_SUPERTYPES,
            "java.lang.StringBuilder <token> supertypes",
            HAND_RECORDS_NO_SUPERTYPES,
            "java.lang.String <method table> concrete methods",
            HAND_HAS_THE_BRIDGE,
            "java.lang.StringBuilder <method table> concrete methods",
            HAND_HAS_THE_BRIDGE,
            "java.util.List INSTANCE_METHOD stream() no generated fact",
            "hand-written: List_.stream is an inherited member, which Q6(b) puts into the metamodel of the type that declares it, Collection_",
            "java.util.List STATIC_METHOD of() owner",
            HAND_OWNS_STATICS_BY_THE_INSTANCE,
            "java.util.List STATIC_METHOD of(java.lang.String) owner",
            HAND_OWNS_STATICS_BY_THE_INSTANCE);

    @Test
    void theGeneratedMetamodelsAreTheHandWrittenOnes() {
        List<Finding> findings = TYPES.stream().flatMap(GoldenTest::compare).toList();

        Map<Boolean, List<Finding>> byKnown =
                findings.stream().collect(Collectors.partitioningBy(finding -> KNOWN.containsKey(finding.id())));
        List<String> stale = KNOWN.keySet().stream()
                .filter(id ->
                        findings.stream().noneMatch(finding -> finding.id().equals(id)))
                .toList();

        assertThat(byKnown.get(false).stream().map(Finding::toString))
                .as("the differences between the hand-written and the generated metamodels that nobody has explained")
                .isEmpty();
        assertThat(stale).as("the known differences that are no more").isEmpty();
    }

    /// A comparison of nothing passes: the facts of every hand-written metamodel are found.
    @Test
    void everyHandWrittenFactIsFound() {
        Map<String, Long> found = TYPES.stream()
                .collect(Collectors.toMap(
                        Class::getName,
                        jdk -> facts(open(HAND_WRITTEN + jdk.getSimpleName() + "_", jdk.getTypeParameters().length))
                                .count()));
        assertThat(found).hasSize(TYPES.size());
        assertThat(found.values().stream().mapToLong(Long::longValue).sum()).isEqualTo(64);
    }

    /// A difference: a member of a type (`<token>` for the token), the aspect of it and what each side has.
    private record Finding(String type, String member, String aspect, String hand, String generated) {
        String id() {
            return type + " " + member + " " + aspect;
        }

        @Override
        public String toString() {
            return id() + ": hand-written " + hand + ", generated " + generated;
        }
    }

    /// A fact of a metamodel, with the field or method that holds it.
    private record Fact(String key, String holder, Object value) {}

    /// A metamodel class and, for a generic one, its instantiation.
    private record Metamodel(Class<?> type, Object instance) {}

    @FunctionalInterface
    private interface Reflective<T> {
        T get() throws ReflectiveOperationException;
    }

    private static <T> T call(Reflective<T> action) {
        try {
            return action.get();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private static Stream<Finding> compare(Class<?> jdk) {
        String name = jdk.getSimpleName() + "_";
        int arity = jdk.getTypeParameters().length;
        Metamodel hand = open(HAND_WRITTEN + name, arity);
        Metamodel generated = open(GENERATED + jdk.getPackageName() + "." + name, arity);
        String type = jdk.getName();

        Map<String, Fact> generatedFacts = facts(generated)
                .collect(Collectors.toMap(Fact::key, fact -> fact, (first, _) -> first, LinkedHashMap::new));
        return Stream.concat(
                compareTokens(type, token(hand), token(generated)),
                facts(hand)
                        .flatMap(fact -> Optional.ofNullable(generatedFacts.get(fact.key()))
                                .map(pair -> compareFacts(type, fact, pair))
                                .orElseGet(() -> Stream.of(
                                        new Finding(type, fact.key(), "no generated fact", fact.holder(), "none")))));
    }

    private static Metamodel open(String className, int arity) {
        Class<?> type = call(() -> Class.forName(className));
        return new Metamodel(type, arity == 0 ? null : instantiate(type, WITNESSES.subList(0, arity)));
    }

    /// The metamodel of a generic type, hand-written: by its factory `of`; generated: by its constructor.
    private static Object instantiate(Class<?> type, List<RefToken<?>> tokens) {
        Object[] arguments = tokens.toArray();
        return Stream.concat(
                        Stream.of(type.getConstructors())
                                .filter(constructor -> takesTokens(constructor.getParameterTypes(), arguments.length))
                                .map(constructor -> call(() -> constructor.newInstance(arguments))),
                        Stream.of(type.getMethods())
                                .filter(method -> method.getName().equals("of")
                                        && Modifier.isStatic(method.getModifiers())
                                        && takesTokens(method.getParameterTypes(), arguments.length))
                                .map(method -> call(() -> method.invoke(null, arguments))))
                .findFirst()
                .orElseThrow();
    }

    private static boolean takesTokens(Class<?>[] parameters, int count) {
        return parameters.length == count && Stream.of(parameters).allMatch(RefToken.class::isAssignableFrom);
    }

    private static Object token(Metamodel metamodel) {
        return call(() -> {
            Field field = metamodel.type().getField(metamodel.instance() == null ? "TOKEN" : "token");
            return field.get(metamodel.instance());
        });
    }

    /// Every fact the metamodel holds, in a field or as the result of a factory that takes a token for each type
    /// parameter of the method, given a token for each.
    private static Stream<Fact> facts(Metamodel metamodel) {
        Stream<Fact> fields = Stream.of(metamodel.type().getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .filter(field -> Modifier.isStatic(field.getModifiers()) == (metamodel.instance() == null))
                .flatMap(field -> fact(field.getName(), call(() -> field.get(metamodel.instance()))));
        Stream<Fact> factories = Stream.of(metamodel.type().getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .filter(method -> Invocable.class.isAssignableFrom(method.getReturnType()))
                .filter(method -> takesTokens(method.getParameterTypes(), method.getParameterCount()))
                .filter(method -> method.getParameterCount() > 0)
                .flatMap(method -> witnessed(method, metamodel.instance()));
        return Stream.concat(fields, factories);
    }

    private static Stream<Fact> witnessed(Method method, Object instance) {
        Object[] arguments = WITNESSES.stream()
                .limit(method.getParameterCount())
                .map(Object.class::cast)
                .toArray();
        try {
            return fact(method.getName(), method.invoke(instance, arguments));
        } catch (InvocationTargetException | IllegalAccessException e) {
            return Stream.empty();
        }
    }

    private static Stream<Fact> fact(String holder, Object value) {
        return switch (value) {
            case Invocable invocable ->
                Stream.of(new Fact(invocable.kind() + " " + invocable.signature(), holder, value));
            case FieldRef<?, ?> field -> Stream.of(new Fact("field " + field.name(), holder, value));
            case StaticFieldRef<?> field -> Stream.of(new Fact("static field " + field.name(), holder, value));
            case Object other
            when other.getClass().getSimpleName().matches("Sam\\d+") ->
                Stream.of(new Fact(
                        "sam",
                        holder,
                        call(() -> other.getClass().getMethod("method").invoke(other))));
            default -> Stream.empty();
        };
    }

    private static Stream<Finding> compareTokens(String type, Object hand, Object generated) {
        DeclaredToken<?> h = (DeclaredToken<?>) hand;
        DeclaredToken<?> g = (DeclaredToken<?>) generated;
        TypeShape<?> hs = h.shape();
        TypeShape<?> gs = g.shape();
        MethodTableTemplate ht = hs.methods();
        MethodTableTemplate gt = gs.methods();
        return Stream.of(
                        difference(
                                type,
                                "<token>",
                                "class",
                                h.getClass().getSimpleName(),
                                g.getClass().getSimpleName()),
                        difference(type, "<token>", "typeRef", h.typeRef(), g.typeRef()),
                        difference(type, "<token>", "type parameters", hs.typeParameters(), gs.typeParameters()),
                        difference(type, "<token>", "kind", hs.kind(), gs.kind()),
                        difference(type, "<token>", "superclasses", hs.superclasses(), gs.superclasses()),
                        difference(
                                type,
                                "<token>",
                                "supertype type parameters",
                                hs.supertypes().typeParameters(),
                                gs.supertypes().typeParameters()),
                        difference(
                                type,
                                "<token>",
                                "supertypes",
                                Set.copyOf(hs.supertypes().supertypes()),
                                Set.copyOf(gs.supertypes().supertypes())),
                        difference(type, "<token>", "sealed", hs.sealed(), gs.sealed()),
                        difference(type, "<token>", "enum constants", hs.enumConstants(), gs.enumConstants()),
                        missing(type, "abstract methods", ht.abstractMethods(), gt.abstractMethods()),
                        missing(type, "concrete methods", ht.concreteMethods(), gt.concreteMethods()),
                        missing(type, "static methods", ht.staticMethods(), gt.staticMethods()))
                .flatMap(Optional::stream);
    }

    /// The signatures of the hand-written method table that the generated one does not have.
    private static Optional<Finding> missing(
            String type, String aspect, Set<Signature> hand, Set<Signature> generated) {
        List<String> absent = hand.stream()
                .filter(s -> !generated.contains(s))
                .map(Signature::toString)
                .sorted()
                .toList();
        return absent.isEmpty()
                ? Optional.empty()
                : Optional.of(new Finding(type, "<method table>", aspect, absent.toString(), "none of them"));
    }

    private static Stream<Finding> compareFacts(String type, Fact hand, Fact generated) {
        return switch (hand.value()) {
            case Invocable h -> compareInvocables(type, hand.key(), h, (Invocable) generated.value());
            case FieldRef<?, ?> h -> {
                FieldRef<?, ?> g = (FieldRef<?, ?>) generated.value();
                yield Stream.of(
                                difference(
                                        type,
                                        hand.key(),
                                        "owner",
                                        h.owner().typeRef(),
                                        g.owner().typeRef()),
                                difference(
                                        type,
                                        hand.key(),
                                        "type",
                                        h.type().typeRef(),
                                        g.type().typeRef()))
                        .flatMap(Optional::stream);
            }
            case StaticFieldRef<?> h -> {
                StaticFieldRef<?> g = (StaticFieldRef<?>) generated.value();
                yield Stream.of(
                                difference(
                                        type,
                                        hand.key(),
                                        "owner",
                                        h.owner().typeRef(),
                                        g.owner().typeRef()),
                                difference(
                                        type,
                                        hand.key(),
                                        "type",
                                        h.type().typeRef(),
                                        g.type().typeRef()),
                                difference(type, hand.key(), "constant value", h.constantValue(), g.constantValue()))
                        .flatMap(Optional::stream);
            }
            default -> throw new AssertionError(hand);
        };
    }

    private static Stream<Finding> compareInvocables(String type, String member, Invocable hand, Invocable generated) {
        return Stream.of(
                        difference(
                                type,
                                member,
                                "owner",
                                hand.owner().typeRef(),
                                generated.owner().typeRef()),
                        difference(type, member, "name", hand.name(), generated.name()),
                        difference(
                                type,
                                member,
                                "result",
                                hand.resultType().map(TypeToken::typeRef),
                                generated.resultType().map(TypeToken::typeRef)),
                        difference(type, member, "parameters", typeRefs(hand.params()), typeRefs(generated.params())),
                        difference(
                                type, member, "declared parameters", hand.declaredParams(), generated.declaredParams()),
                        difference(
                                type,
                                member,
                                "overridability",
                                hand.traits().overridability(),
                                generated.traits().overridability()),
                        difference(
                                type,
                                member,
                                "throws",
                                typeRefs(hand.traits().throwsTypes()),
                                typeRefs(generated.traits().throwsTypes())),
                        difference(
                                type,
                                member,
                                "type arguments",
                                typeRefs(hand.traits().typeArgs()),
                                typeRefs(generated.traits().typeArgs())))
                .flatMap(Optional::stream);
    }

    private static List<?> typeRefs(List<? extends TypeToken<?>> tokens) {
        return tokens.stream().map(TypeToken::typeRef).toList();
    }

    private static Optional<Finding> difference(
            String type, String member, String aspect, Object hand, Object generated) {
        return Objects.equals(hand, generated)
                ? Optional.empty()
                : Optional.of(new Finding(type, member, aspect, String.valueOf(hand), String.valueOf(generated)));
    }
}
