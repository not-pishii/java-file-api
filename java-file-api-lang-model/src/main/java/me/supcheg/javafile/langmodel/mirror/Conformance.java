package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.TargetType;
import me.supcheg.javafile.facts.TargetType.Difference;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/// Whether a metamodel holds of a type as a classpath has it: the
/// comparison of what the metamodel recorded — the fingerprint of its type
/// and its [Canonical] form — with the [TypeModel] of the type there. The
/// one comparison of the `@Facts` processor, which reuses a metamodel of
/// another module only for an unchanged type, and of the check against the
/// target classpath, which lets a changed type through where the metamodel
/// still holds.
///
/// **The fast path** ([#unchanged]): the fingerprint is that of the model.
/// Nothing of the metamodel but the fingerprint is read.
///
/// **The slow path** ([#of], where the fingerprint differs): the canonical
/// form of the metamodel is compared with that of the model, line by line:
///
/// | Lines | Compared | Why |
/// |---|---|---|
/// | the format | equal, or nothing else is compared | the lines of another format mean something else |
/// | `type`, `tparams`, `superclasses`, `interfaces`, `supertypes`, `enum` | equal | the data of the shape of the
/// metamodel, which the typed layer decided by: the token class, checked exceptions, checked casts, what an
/// expression of the type is assignable to |
/// | `member` | each of the metamodel is one of the type | a fact of the metamodel names the member; a member the
/// type has added takes none away |
/// | `sam`, of a full metamodel | that of the metamodel is that of the type | the `sam` fact; a token-only
/// metamodel has none |
/// | `table` | not compared: the table is taken from the type | an overload the type has added is a candidate
/// javac chooses among |
public final class Conformance {
    private static final List<String> DATA =
            List.of("type", "tparams", "superclasses", "interfaces", "supertypes", "enum");
    private static final String MEMBER = "member ";
    private static final String SAM = "sam ";
    private static final String FULL = "members declared-public";

    private Conformance() {}

    /// Whether a type is the one a metamodel was generated from: the fast
    /// path.
    ///
    /// @param fingerprint the fingerprint the metamodel records
    /// @param model the type, with the members of the metamodel: those of a full metamodel, or none for a
    ///              token-only one
    /// @return `true` if the fingerprint is that of `model`
    public static boolean unchanged(String fingerprint, TypeModel model) {
        return Canonical.of(model).fingerprint().equals(fingerprint);
    }

    /// Compares a metamodel with a type: the fast path, and the slow one
    /// if the fingerprint is not that of the type.
    ///
    /// @param fingerprint the fingerprint the metamodel records
    /// @param generated supplies the canonical form the metamodel records, asked only on the slow path
    /// @param target the type as the classpath has it, with its members ([MemberFilter#DECLARED_PUBLIC])
    /// @return [TargetType#UNCHANGED] for the type the metamodel was generated from, [TargetType.Changed]
    ///         with the methods of `target` for another one every fact of the metamodel holds of, or what
    ///         differs
    /// @throws IllegalArgumentException if `target` is a model without members
    public static TargetType of(String fingerprint, Supplier<String> generated, TypeModel target) {
        if (target.filter() != MemberFilter.DECLARED_PUBLIC) {
            throw new IllegalArgumentException("a type is compared with its members, got a model without");
        }
        // which of the two a metamodel is, its canonical form tells: the fingerprint is of one of them
        TypeModel tokenOnly = target.withoutMembers();
        if (unchanged(fingerprint, target) || unchanged(fingerprint, tokenOnly)) {
            return TargetType.UNCHANGED;
        }
        List<String> lines = generated.get().lines().toList();
        String format = lines.stream().findFirst().orElse("");
        if (!format.equals(Canonical.HEADER)) {
            return new TargetType.Mismatched(List.of(new Difference.OtherFormat(format, Canonical.HEADER)));
        }
        boolean full = lines.contains(FULL);
        List<String> targetLines =
                Canonical.of(full ? target : tokenOnly).text().lines().toList();
        List<Difference> differences = Stream.concat(
                        data(lines, targetLines), facts(facts(lines, full), facts(targetLines, full)))
                .toList();
        return differences.isEmpty()
                ? new TargetType.Changed(target.methods())
                : new TargetType.Mismatched(differences);
    }

    /// The data of the shape that differs.
    private static Stream<Difference> data(List<String> generated, List<String> target) {
        return DATA.stream().flatMap(what -> {
            String recorded = value(generated, what);
            String found = value(target, what);
            return recorded.equals(found)
                    ? Stream.empty()
                    : Stream.of(new Difference.ChangedData(what, recorded, found));
        });
    }

    /// The line of a form that tells `what`, without the word.
    private static String value(List<String> lines, String what) {
        return lines.stream()
                .filter(line -> line.startsWith(what + " "))
                .map(line -> line.substring(what.length() + 1))
                .findFirst()
                .orElse("");
    }

    /// The lines of a form that tell what a metamodel has facts of.
    private static List<String> facts(List<String> lines, boolean full) {
        return lines.stream()
                .filter(line -> line.startsWith(MEMBER) || (full && line.startsWith(SAM)))
                .toList();
    }

    /// The facts of a metamodel that the type does not have as they are:
    /// changed, where the type has exactly one member of the kind and name
    /// that the metamodel does not know and the metamodel misses no other
    /// of them; missing otherwise, with the members of the kind and name the
    /// type does have.
    private static Stream<Difference> facts(List<String> generated, List<String> target) {
        Set<String> known = Set.copyOf(generated);
        Set<String> there = Set.copyOf(target);
        List<String> missing =
                generated.stream().filter(fact -> !there.contains(fact)).toList();
        return missing.stream().map(fact -> {
            String name = name(fact);
            List<String> namesakes =
                    target.stream().filter(other -> name(other).equals(name)).toList();
            List<String> unknown =
                    namesakes.stream().filter(other -> !known.contains(other)).toList();
            boolean alone =
                    missing.stream().filter(other -> name(other).equals(name)).count() == 1;
            return alone && unknown.size() == 1
                    ? new Difference.ChangedFact(told(fact), told(unknown.getFirst()))
                    : new Difference.MissingFact(
                            told(fact),
                            namesakes.stream().map(Conformance::told).toList());
        });
    }

    /// A fact as a message tells it: its line without `member`.
    private static String told(String fact) {
        return fact.startsWith(MEMBER) ? fact.substring(MEMBER.length()) : fact;
    }

    /// What the members that may stand for one another share: the kind and
    /// the name of a field or method, `ctor`, `sam`. The lines are those
    /// [Canonical] writes:
    ///
    /// ```
    /// member ctor <^0>(^0) throws -
    /// member field static constant java.lang.String NAME = "a = b"
    /// member method static <^0 extends java.lang.Comparable<^0>> max(^0[]) -> ^0 throws -
    /// sam apply(#0) -> #1 throws -
    /// ```
    static String name(String fact) {
        if (fact.startsWith(SAM)) {
            return "sam";
        }
        String[] words = fact.split(" ", 4);
        return switch (words[1]) {
            case "method" -> {
                // the mode is words[2]; then the type parameters, if any, and the name up to its parameters
                String declaration = words[3].startsWith("<") ? afterTypeParameters(words[3]) : words[3];
                yield "method " + declaration.substring(0, declaration.indexOf('('));
            }
            case "field" -> {
                // static or instance is words[2]; then the mutability, the type, the name, and a constant's value
                String declaration = words[3].split(" = ", 2)[0];
                yield "field " + declaration.substring(declaration.lastIndexOf(' ') + 1);
            }
            default -> "ctor";
        };
    }

    /// What follows the type parameters `<…> ` a declaration starts with.
    private static String afterTypeParameters(String declaration) {
        int depth = 0;
        for (int i = 0; i < declaration.length(); i++) {
            switch (declaration.charAt(i)) {
                case '<' -> depth++;
                case '>' -> depth--;
                default -> {}
            }
            if (depth == 0) {
                return declaration.substring(i + 2);
            }
        }
        throw new IllegalArgumentException("the type parameters do not end: " + declaration);
    }

    /// The names of the lines of a form, for a test of [#name].
    static List<String> names(Canonical canonical) {
        return facts(canonical.text().lines().toList(), true).stream()
                .map(Conformance::name)
                .toList();
    }
}
