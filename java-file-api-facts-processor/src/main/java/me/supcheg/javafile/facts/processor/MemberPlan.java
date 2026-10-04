package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.MemberModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.SamModel;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.routine.Either;
import me.supcheg.routine.EitherCollectors;
import me.supcheg.routine.Pair;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// Which members of a requested type get a fact, and under which name
/// (mini-spec §2.2, §2.3, Q3, Q6, Q10).
///
/// Only members the type declares get one, and only `public` ones (Q3: no
/// `protected`); an inherited member is reached through the metamodel of the
/// supertype (Q6(b)). A supertype that is not `public` has no metamodel, so
/// the `public` fields and methods the type inherits from it are adopted:
/// they get a fact here, as members of the type, in its terms (Q13,
/// [me.supcheg.javafile.langmodel.mirror.MirrorTranslator#members]). The
/// other exception is `sam`, which a functional interface has whether it
/// declares its abstract method or inherits it.
///
/// A member gets no fact, and is reported as [Skip], if
///
/// - its signature mentions a type that is not `public`, cannot be
///   expressed, or has more than 12 parameters — the translator's verdict;
/// - its signature mentions a type with no metamodel: an annotation
///   interface, a class with `$` in its simple name;
/// - it is a constructor of an inner class, which needs an enclosing
///   instance no `CtorRefN` takes;
/// - it is a generic constructor: `new` gives a constructor no explicit
///   type arguments in the generated code, and a fact never leaves them to
///   inference;
/// - its name would be that of another member, or, even with `_` appended,
///   a name the metamodel itself starts a name with: it would hide that.
///
/// The names are those of [MetamodelNames#members] over every declared
/// or adopted `public` member, with a fact or without: a member that gets
/// its fact later does not rename the others. An adopted method is named
/// after its parameters as the type has them.
///
/// @param enumConstants the enum constants that get a fact, in declaration order
/// @param members the fields, constructors and methods that get a fact
/// @param sam the fact of the single abstract method of a functional interface
/// @param skipped the members without a fact and why
record MemberPlan(List<EnumFact> enumConstants, List<Fact> members, Optional<SamFact> sam, List<Skip> skipped) {

    /// A fact of an enum constant.
    ///
    /// @param name the name of the fact
    /// @param constant the name of the constant
    record EnumFact(String name, String constant) {}

    /// A fact of a field, constructor or method.
    ///
    /// @param name the name of the fact
    /// @param model the member
    /// @param origin where the type has the member from: it declares it, or adopts it
    /// @param member the member as the comment of the fact links to it, see [Models#reference]
    record Fact(String name, MemberModel model, Origin origin, DocRef member) {}

    /// The fact of the single abstract method.
    ///
    /// @param method the method, as a member of the interface
    /// @param reuse the name of the fact of the same method if the interface declares it; empty for an
    ///     inherited method, whose fact the `sam` makes on the spot
    /// @param member the method as the comment of `sam` links to it, in the type that declares it, see
    ///     [Models#samReference]
    record SamFact(MethodModel method, Optional<String> reuse, DocRef member) {}

    /// Members that get no fact, or `sam`.
    ///
    /// @param member what it is, such as `method greet(p.Hidden)`
    /// @param reason why it has none, for a diagnostic
    /// @param origin where the type has the member from; of several members that would share a name,
    ///     that the type declares one if it does, and else where it adopts the first from
    record Skip(String member, String reason, Origin origin) {
        /// What joins the member and the reason: `method greet(p.Hidden), which mentions …`.
        static final String WHICH = ", which ";

        /// The member and why it has no fact, as the warning of the processor and the comment of the
        /// metamodel tell it.
        ///
        /// @return `member`, [#WHICH], `reason`
        String told() {
            return member + WHICH + reason;
        }
    }

    /// Where a type has a member from. Only a member the type declares itself is one its author can
    /// do something about, so only the lack of a fact of such a member is an error under `strict`;
    /// and a fact tells where its member is declared.
    sealed interface Origin {

        /// Where a type has a field, constructor or method [MirrorTranslator#members] gives for it.
        ///
        /// @param type the type
        /// @param member the member, declared or adopted
        /// @return the origin
        static Origin of(TypeElement type, Element member) {
            return member.getEnclosingElement().equals(type)
                    ? new Declared()
                    : new Adopted(((TypeElement) member.getEnclosingElement())
                            .getQualifiedName()
                            .toString());
        }

        /// The type declares the member.
        record Declared() implements Origin {}

        /// The type adopts the member from a supertype that is not `public`, which has no metamodel
        /// to tell it.
        ///
        /// @param from the canonical name of the supertype that declares the member
        record Adopted(String from) implements Origin {}

        /// The type inherits the member from a supertype that has a metamodel of its own: the single
        /// abstract method of a functional interface, the one inherited member a type has a fact of.
        record Inherited() implements Origin {}
    }

    /// The start of the names of the facts of a [#probe]: no member has such a name.
    static final String PROBE = "fact$";

    /// Plans the facts of a type.
    ///
    /// @param type the type, a class, interface, enum or record
    /// @param models the models of the round
    /// @param targets the metamodels of the types the signatures mention
    /// @param taken the names the metamodel starts a name with, see [MetamodelEmitter#takenNames]: a
    ///     fact of such a name would hide what the name means, so it gets `_` appended
    /// @return the plan
    static MemberPlan of(TypeElement type, Models models, Targets targets, Set<String> taken) {
        Candidates candidates = Candidates.of(type, models, targets);
        Set<Element> live = Stream.concat(candidates.enumConstants().stream(), candidates.members().keySet().stream())
                .collect(Collectors.toSet());
        MetamodelNames.MemberNames names = MetamodelNames.members(
                candidates.named().stream()
                        .map(member -> member.getEnclosingElement().equals(type)
                                ? MetamodelNames.Named.declared(member)
                                : new MetamodelNames.Named(member, models.parameters(type, member)))
                        .toList(),
                taken);
        // a conflict is skipped as a whole once one of its members is a candidate; none is in `names`
        List<Skip> conflicts = names.conflicts().entrySet().stream()
                .filter(conflict -> conflict.getValue().stream().anyMatch(live::contains))
                .map(conflict -> new Skip(
                        conflict.getValue().stream()
                                .map(member -> describe(type, member))
                                .collect(Collectors.joining(", ")),
                        "would all be named " + conflict.getKey(),
                        conflict.getValue().stream()
                                .map(member -> Origin.of(type, member))
                                .filter(Origin.Declared.class::isInstance)
                                .findFirst()
                                .orElseGet(() ->
                                        Origin.of(type, conflict.getValue().getFirst()))))
                .toList();
        Pair<List<Skip>, Map<Element, String>> named = names.names().entrySet().stream()
                .filter(entry -> live.contains(entry.getKey()))
                .<Either<Skip, Map.Entry<Element, String>>>map(entry -> taken.contains(entry.getValue())
                        ? Either.left(new Skip(
                                describe(type, entry.getKey()),
                                "would be named " + entry.getValue() + ", a name the metamodel itself uses",
                                Origin.of(type, entry.getKey())))
                        : Either.right(entry))
                .collect(EitherCollectors.groupingTo(
                        Collectors.toList(),
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (first, second) -> second,
                                LinkedHashMap::new)));
        Map<Element, String> factNames = named.right();
        List<EnumFact> enums = candidates.enumConstants().stream()
                .filter(factNames::containsKey)
                .map(e -> new EnumFact(factNames.get(e), e.getSimpleName().toString()))
                .toList();
        List<Fact> members = candidates.members().entrySet().stream()
                .filter(entry -> factNames.containsKey(entry.getKey()))
                .map(entry -> new Fact(
                        factNames.get(entry.getKey()),
                        entry.getValue(),
                        Origin.of(type, entry.getKey()),
                        models.reference(type, entry.getKey())))
                .toList();
        Optional<Either<Skip, SamFact>> sam = sam(type, models, targets, members);
        return new MemberPlan(
                enums,
                members,
                sam.flatMap(either -> either.right()),
                Stream.of(
                                candidates.skipped().stream(),
                                conflicts.stream(),
                                named.left().stream(),
                                sam.flatMap(either -> either.left()).stream())
                        .flatMap(Function.identity())
                        .toList());
    }

    /// Every member of a type that may get a fact, whatever its name, under
    /// a name no member has: what the metamodel is written with to find the
    /// names it uses itself ([MetamodelEmitter#takenNames]) before the facts
    /// are named.
    ///
    /// @param type the type, a class, interface, enum or record
    /// @param models the models of the round
    /// @param targets the metamodels of the types the signatures mention
    /// @return the plan, with nothing skipped
    static MemberPlan probe(TypeElement type, Models models, Targets targets) {
        Candidates candidates = Candidates.of(type, models, targets);
        List<EnumFact> enums = IntStream.range(0, candidates.enumConstants().size())
                .mapToObj(i -> new EnumFact(
                        PROBE + "e" + i,
                        candidates.enumConstants().get(i).getSimpleName().toString()))
                .toList();
        List<Map.Entry<Element, MemberModel>> found =
                List.copyOf(candidates.members().entrySet());
        List<Fact> members = IntStream.range(0, found.size())
                .mapToObj(i -> new Fact(
                        PROBE + i,
                        found.get(i).getValue(),
                        Origin.of(type, found.get(i).getKey()),
                        models.reference(type, found.get(i).getKey())))
                .toList();
        return new MemberPlan(
                enums, members, sam(type, models, targets, members).flatMap(either -> either.right()), List.of());
    }

    /// The names of the facts, `sam` among them if there is one.
    ///
    /// @return the names
    Set<String> names() {
        return Stream.of(
                        enumConstants.stream().map(EnumFact::name),
                        members.stream().map(Fact::name),
                        sam.stream().map(_ -> MetamodelNames.SAM))
                .flatMap(Function.identity())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /// The members the facts are of, the single abstract method among them if there is one.
    ///
    /// @return the members
    Stream<MemberModel> models() {
        return Stream.concat(members.stream().map(Fact::model), sam.stream().map(SamFact::method));
    }

    /// The classes and interfaces whose metamodels the facts refer to, the
    /// type itself among them if a signature mentions it.
    ///
    /// @return the binary names
    Set<String> mentionedTypes() {
        return models().flatMap(Mentions::of).map(Models::binaryName).collect(Collectors.toCollection(TreeSet::new));
    }

    /// The members of a type that a fact can be made of: those it declares and those it adopts.
    ///
    /// @param named every enum constant and `public` field, constructor and method, with a fact or
    ///     without: what the names are told apart among
    /// @param enumConstants the enum constants, in declaration order
    /// @param members the fields, constructors and methods a fact can be made of, the declared ones in
    ///     declaration order and then the adopted ones
    /// @param skipped the `public` members no fact can be made of, and why
    private record Candidates(
            List<Element> named, List<Element> enumConstants, Map<Element, MemberModel> members, List<Skip> skipped) {

        static Candidates of(TypeElement type, Models models, Targets targets) {
            List<Element> enclosed = List.copyOf(type.getEnclosedElements());
            // member types have metamodels of their own; initializers are not members
            Predicate<Element> isConstant = member -> member.getKind() == ElementKind.ENUM_CONSTANT;
            List<Element> enumConstants = enclosed.stream().filter(isConstant).toList();
            List<Element> publicMembers = models.members(type);
            Set<Element> withFacts = Set.copyOf(publicMembers);
            // the constants and the declared members as they are declared, then the adopted members
            List<Element> named = Stream.concat(
                            enclosed.stream().filter(isConstant.or(withFacts::contains)),
                            publicMembers.stream()
                                    .filter(member ->
                                            !member.getEnclosingElement().equals(type)))
                    .toList();
            Pair<List<Skip>, Map<Element, MemberModel>> read = publicMembers.stream()
                    .map(member -> candidate(type, member, models.translator(), targets))
                    .collect(EitherCollectors.groupingTo(
                            Collectors.toList(),
                            Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (first, second) -> second,
                                    LinkedHashMap::new)));
            return new Candidates(named, enumConstants, read.right(), read.left());
        }
    }

    /// A `public` field, constructor or method: the member and its model, or why it has no fact.
    private static Either<Skip, Map.Entry<Element, MemberModel>> candidate(
            TypeElement type, Element member, MirrorTranslator translator, Targets targets) {
        return switch (translator.member(type, member)) {
            case Translation.Ok<MemberModel>(MemberModel model) -> {
                Optional<String> unsupported = member.getKind() == ElementKind.CONSTRUCTOR && inner(type)
                        ? Optional.of("is the constructor of an inner class, which needs an enclosing instance")
                        : unsupported(model, targets);
                yield unsupported
                        .<Either<Skip, Map.Entry<Element, MemberModel>>>map(reason ->
                                Either.left(new Skip(describe(type, member), reason, Origin.of(type, member))))
                        .orElseGet(() -> Either.right(Map.entry(member, model)));
            }
            case Translation.Deferred<MemberModel>(String unresolved) ->
                throw new IllegalStateException(
                        describe(type, member) + " was read, but mentions " + unresolved + " now");
            case Translation.Unrepresentable<MemberModel>(String reason) ->
                Either.left(new Skip(describe(type, member), reason, Origin.of(type, member)));
        };
    }

    private static boolean inner(TypeElement type) {
        return type.getNestingKind() == NestingKind.MEMBER
                && !type.getModifiers().contains(Modifier.STATIC);
    }

    /// The fact of the single abstract method, or why it has none; empty if there is no such method, or
    /// the interface declares it and it has no fact, as is reported for the member.
    private static Optional<Either<Skip, SamFact>> sam(
            TypeElement type, Models models, Targets targets, List<Fact> members) {
        return switch (models.sam(type)) {
            case Translation.Ok<Optional<SamModel>>(Optional<SamModel> sam) ->
                sam.flatMap(found -> samFact(found, targets, members, () -> models.samReference(type, found.method())));
            case Translation.Deferred<Optional<SamModel>>(String unresolved) ->
                throw new IllegalStateException("the single abstract method was read, but mentions " + unresolved);
            case Translation.Unrepresentable<Optional<SamModel>>(String reason) ->
                Optional.of(Either.left(new Skip("the single abstract method", reason, new Origin.Inherited())));
        };
    }

    /// @param member the method as the comment of `sam` links to it, asked for only if there is a fact
    private static Optional<Either<Skip, SamFact>> samFact(
            SamModel found, Targets targets, List<Fact> members, Supplier<DocRef> member) {
        MethodModel method = found.method();
        Optional<String> unsupported = unsupported(method, targets);
        if (unsupported.isPresent()) {
            return found.declared()
                    ? Optional.empty()
                    : Optional.of(Either.left(new Skip(
                            "the single abstract method " + method.name(), unsupported.get(), new Origin.Inherited())));
        }
        Optional<String> declared = members.stream()
                .filter(f -> f.model() instanceof MethodModel other
                        && !other.isStatic()
                        && other.typeParams().isEmpty()
                        && other.name().equals(method.name())
                        && other.params().equals(method.params()))
                .map(Fact::name)
                .findFirst();
        return Optional.of(Either.right(new SamFact(method, declared, member.get())));
    }

    /// Why a member cannot have a fact, though the translator read it.
    private static Optional<String> unsupported(MemberModel model, Targets targets) {
        if (model instanceof CtorModel ctor && !ctor.typeParams().isEmpty()) {
            return Optional.of("is a generic constructor, whose type arguments a fact cannot give explicitly");
        }
        // a token is made of every type of the signature, and the bounds of a generic method are written
        // out: all of them are to be types a metamodel can name
        return Mentions.of(model)
                .distinct()
                .filter(desc -> targets.of(desc).isEmpty())
                .findFirst()
                .map(desc ->
                        "mentions " + Models.binaryName(desc) + ", which has no metamodel: " + targets.whyNone(desc));
    }

    /// A member of a type as a diagnostic names it: `method greet(p.Hidden)`, and, for one the type
    /// adopts, `method capacity() of java.lang.AbstractStringBuilder`.
    static String describe(TypeElement type, Element member) {
        String described =
                switch (member.getKind()) {
                    case FIELD, ENUM_CONSTANT -> "field " + member.getSimpleName();
                    case CONSTRUCTOR -> "constructor " + member;
                    default -> "method " + member;
                };
        return switch (Origin.of(type, member)) {
            case Origin.Declared _, Origin.Inherited _ -> described;
            case Origin.Adopted(String from) -> described + " of " + from;
        };
    }
}
