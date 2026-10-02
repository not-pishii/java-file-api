package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.FieldModel;
import me.supcheg.javafile.langmodel.mirror.MemberModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.SamModel;
import me.supcheg.javafile.langmodel.mirror.Translation;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/// Which members of a requested type get a fact, and under which name
/// (mini-spec §2.2, §2.3, Q3, Q6, Q10).
///
/// Only members the type declares get one, and only `public` ones (Q3: no
/// `protected`); an inherited member is reached through the metamodel of the
/// supertype (Q6(b)). The exception is `sam`, which a functional interface
/// has whether it declares its abstract method or inherits it.
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
/// `public` member, with a fact or without: a member that gets its fact
/// later does not rename the others.
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
    record Fact(String name, MemberModel model) {}

    /// The fact of the single abstract method.
    ///
    /// @param method the method, as a member of the interface
    /// @param reuse the name of the fact of the same method if the interface declares it; empty for an
    ///     inherited method, whose fact the `sam` makes on the spot
    record SamFact(MethodModel method, Optional<String> reuse) {}

    /// Members that get no fact, or `sam`.
    ///
    /// @param member what it is, such as `method greet(p.Hidden)`
    /// @param reason why it has none, for a diagnostic
    record Skip(String member, String reason) {}

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
        List<Skip> skipped = new ArrayList<>(candidates.skipped());
        Set<Element> live = new LinkedHashSet<>(candidates.enumConstants());
        live.addAll(candidates.members().keySet());
        MetamodelNames.MemberNames names = MetamodelNames.members(candidates.named(), taken);
        names.conflicts().forEach((name, sharing) -> {
            if (sharing.stream().anyMatch(live::contains)) {
                sharing.forEach(live::remove);
                skipped.add(new Skip(
                        String.join(
                                ", ", sharing.stream().map(MemberPlan::describe).toList()),
                        "would all be named " + name));
            }
        });
        Map<Element, String> factNames = new LinkedHashMap<>();
        names.names().forEach((element, name) -> {
            if (!live.contains(element)) {
                return;
            }
            if (taken.contains(name)) {
                skipped.add(
                        new Skip(describe(element), "would be named " + name + ", a name the metamodel itself uses"));
                live.remove(element);
            } else {
                factNames.put(element, name);
            }
        });
        List<EnumFact> enums = candidates.enumConstants().stream()
                .filter(live::contains)
                .map(e -> new EnumFact(factNames.get(e), e.getSimpleName().toString()))
                .toList();
        List<Fact> members = new ArrayList<>();
        candidates.members().forEach((element, model) -> {
            if (live.contains(element)) {
                members.add(new Fact(factNames.get(element), model));
            }
        });
        Optional<SamFact> sam = sam(type, models, targets, members, skipped);
        return new MemberPlan(enums, members, sam, skipped);
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
        List<EnumFact> enums = new ArrayList<>();
        for (Element constant : candidates.enumConstants()) {
            enums.add(new EnumFact(
                    PROBE + "e" + enums.size(), constant.getSimpleName().toString()));
        }
        List<Fact> members = new ArrayList<>();
        candidates.members().forEach((element, model) -> members.add(new Fact(PROBE + members.size(), model)));
        return new MemberPlan(enums, members, sam(type, models, targets, members, new ArrayList<>()), List.of());
    }

    /// The names of the facts, `sam` among them if there is one.
    ///
    /// @return the names
    Set<String> names() {
        Set<String> names = new LinkedHashSet<>();
        enumConstants.forEach(fact -> names.add(fact.name()));
        members.forEach(fact -> names.add(fact.name()));
        sam.ifPresent(fact -> names.add(MetamodelNames.SAM));
        return names;
    }

    /// The classes and interfaces whose metamodels the facts refer to, the
    /// type itself among them if a signature mentions it.
    ///
    /// @return the binary names
    Set<String> mentionedTypes() {
        List<ClassDesc> found = new ArrayList<>();
        mentions(members.stream().map(Fact::model).toList(), found);
        sam.ifPresent(fact -> mentions(List.of(fact.method()), found));
        Set<String> names = new TreeSet<>();
        found.forEach(desc -> names.add(Models.binaryName(desc)));
        return names;
    }

    /// The declared members of a type that a fact can be made of.
    ///
    /// @param named every enum constant and `public` field, constructor and method, with a fact or
    ///     without: what the names are told apart among
    /// @param enumConstants the enum constants, in declaration order
    /// @param members the fields, constructors and methods a fact can be made of, in declaration order
    /// @param skipped the `public` members no fact can be made of, and why
    private record Candidates(
            List<Element> named, List<Element> enumConstants, Map<Element, MemberModel> members, List<Skip> skipped) {

        static Candidates of(TypeElement type, Models models, Targets targets) {
            List<Skip> skipped = new ArrayList<>();
            List<Element> named = new ArrayList<>();
            List<Element> enumConstants = new ArrayList<>();
            Map<Element, MemberModel> members = new LinkedHashMap<>();
            for (Element member : type.getEnclosedElements()) {
                switch (member.getKind()) {
                    case ENUM_CONSTANT -> {
                        enumConstants.add(member);
                        named.add(member);
                    }
                    case FIELD, CONSTRUCTOR, METHOD -> {
                        if (member.getModifiers().contains(Modifier.PUBLIC)) {
                            named.add(member);
                            candidate(type, member, models.translator(), targets, members, skipped);
                        }
                    }
                    default -> {
                        // member types have metamodels of their own; initializers are not members
                    }
                }
            }
            return new Candidates(named, enumConstants, members, skipped);
        }
    }

    private static void candidate(
            TypeElement type,
            Element member,
            MirrorTranslator translator,
            Targets targets,
            Map<Element, MemberModel> candidates,
            List<Skip> skipped) {
        switch (translator.member(type, member)) {
            case Translation.Ok<MemberModel>(MemberModel model) -> {
                Optional<String> unsupported = member.getKind() == ElementKind.CONSTRUCTOR && inner(type)
                        ? Optional.of("is the constructor of an inner class, which needs an enclosing instance")
                        : unsupported(model, targets);
                unsupported.ifPresentOrElse(
                        reason -> skipped.add(new Skip(describe(member), reason)), () -> candidates.put(member, model));
            }
            case Translation.Deferred<MemberModel>(String unresolved) ->
                throw new IllegalStateException(describe(member) + " was read, but mentions " + unresolved + " now");
            case Translation.Unrepresentable<MemberModel>(String reason) ->
                skipped.add(new Skip(describe(member), reason));
        }
    }

    private static boolean inner(TypeElement type) {
        return type.getNestingKind() == NestingKind.MEMBER
                && !type.getModifiers().contains(Modifier.STATIC);
    }

    private static Optional<SamFact> sam(
            TypeElement type, Models models, Targets targets, List<Fact> members, List<Skip> skipped) {
        return switch (models.sam(type)) {
            case Translation.Ok<Optional<SamModel>>(Optional<SamModel> sam) ->
                sam.flatMap(found -> {
                    MethodModel method = found.method();
                    Optional<String> unsupported = unsupported(method, targets);
                    if (unsupported.isPresent()) {
                        if (!found.declared()) {
                            skipped.add(new Skip("the single abstract method " + method.name(), unsupported.get()));
                        }
                        return Optional.empty();
                    }
                    Optional<String> declared = members.stream()
                            .filter(f -> f.model() instanceof MethodModel other
                                    && !other.isStatic()
                                    && other.typeParams().isEmpty()
                                    && other.name().equals(method.name())
                                    && other.params().equals(method.params()))
                            .map(Fact::name)
                            .findFirst();
                    return Optional.of(new SamFact(method, declared));
                });
            case Translation.Deferred<Optional<SamModel>>(String unresolved) ->
                throw new IllegalStateException("the single abstract method was read, but mentions " + unresolved);
            case Translation.Unrepresentable<Optional<SamModel>>(String reason) -> {
                skipped.add(new Skip("the single abstract method", reason));
                yield Optional.empty();
            }
        };
    }

    /// Why a member cannot have a fact, though the translator read it.
    private static Optional<String> unsupported(MemberModel model, Targets targets) {
        if (model instanceof CtorModel ctor && !ctor.typeParams().isEmpty()) {
            return Optional.of("is a generic constructor, whose type arguments a fact cannot give explicitly");
        }
        // a token is made of every type of the signature, and the bounds of a generic method are written
        // out: all of them are to be types a metamodel can name
        Set<ClassDesc> mentioned = new LinkedHashSet<>();
        mentions(List.of(model), mentioned);
        for (ClassDesc desc : mentioned) {
            if (targets.of(desc).isEmpty()) {
                return Optional.of(
                        "mentions " + Models.binaryName(desc) + ", which has no metamodel: " + targets.whyNone(desc));
            }
        }
        return Optional.empty();
    }

    /// A member as a diagnostic names it: `method greet(p.Hidden)`.
    static String describe(Element member) {
        return switch (member.getKind()) {
            case FIELD, ENUM_CONSTANT -> "field " + member.getSimpleName();
            case CONSTRUCTOR -> "constructor " + member;
            default -> "method " + member;
        };
    }

    /// The classes and interfaces the signatures of the members mention, the
    /// bounds of the type parameters of a generic method among them.
    ///
    /// @param members the members
    /// @param found where to add
    static void mentions(Collection<? extends MemberModel> members, Collection<ClassDesc> found) {
        for (MemberModel member : members) {
            switch (member) {
                case FieldModel field -> Mentions.of(field.type(), found);
                case CtorModel ctor -> {
                    ctor.params().forEach(p -> Mentions.of(p, found));
                    ctor.throwsTypes().forEach(t -> Mentions.of(t, found));
                }
                case MethodModel method -> {
                    Mentions.of(method.typeParams(), found);
                    method.result().ifPresent(r -> Mentions.of(r, found));
                    method.params().forEach(p -> Mentions.of(p, found));
                    method.throwsTypes().forEach(t -> Mentions.of(t, found));
                }
            }
        }
    }
}
