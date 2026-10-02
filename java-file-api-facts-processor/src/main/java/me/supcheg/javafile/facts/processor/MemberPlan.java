package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.FieldModel;
import me.supcheg.javafile.langmodel.mirror.MemberModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.SamModel;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;

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
/// - its signature is generic — a type variable, a parameterized or raw
///   generic type, type parameters of its own — which comes with the
///   generic metamodels (plan step 9);
/// - its name would be that of another member, or, even with `_` appended,
///   a name the metamodel itself starts a name with: it would hide that.
///
/// The names are those of [MetamodelNames#members] over every declared
/// `public` member, with a fact or without: a member that gets its fact
/// later, as the generic ones will, does not rename the others.
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
    /// @param type the type, a class, interface, enum or record that is not generic
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
    /// @param type the type, a class, interface, enum or record that is not generic
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

    /// Why a member cannot have a fact yet, though the translator read it.
    private static Optional<String> unsupported(MemberModel model, Targets targets) {
        List<TypeRef> types = new ArrayList<>();
        switch (model) {
            case FieldModel field -> types.add(field.type());
            case CtorModel ctor -> {
                if (!ctor.typeParams().isEmpty()) {
                    return Optional.of("is generic, and facts of generic members are not supported yet");
                }
                types.addAll(ctor.params());
                types.addAll(ctor.throwsTypes());
            }
            case MethodModel method -> {
                if (!method.typeParams().isEmpty()) {
                    return Optional.of("is generic, and facts of generic members are not supported yet");
                }
                method.result().ifPresent(types::add);
                types.addAll(method.params());
                types.addAll(method.throwsTypes());
            }
        }
        for (TypeRef type : types) {
            Optional<String> reason = unsupported(type, targets);
            if (reason.isPresent()) {
                return reason;
            }
        }
        return Optional.empty();
    }

    private static Optional<String> unsupported(TypeRef type, Targets targets) {
        return switch (type) {
            case PrimitiveTypeRef ignored -> Optional.empty();
            case ArrayTypeRef array -> unsupported(array.component(), targets);
            case TypeVarRef variable ->
                Optional.of("mentions the type variable " + variable.name()
                        + ", and facts of generic members are not supported yet");
            case ParameterizedTypeRef parameterized ->
                Optional.of("mentions the parameterized type " + Models.binaryName(parameterized.raw())
                        + ", and facts of generic types are not supported yet");
            case ClassTypeRef cls -> {
                if (targets.generic(cls.desc())) {
                    yield Optional.of("mentions the raw type " + Models.binaryName(cls.desc())
                            + ", and facts of generic types are not supported yet");
                }
                yield targets.of(cls.desc()).isPresent()
                        ? Optional.empty()
                        : Optional.of("mentions " + Models.binaryName(cls.desc()) + ", which has no metamodel: "
                                + targets.whyNone(cls.desc()));
            }
        };
    }

    /// A member as a diagnostic names it: `method greet(p.Hidden)`.
    static String describe(Element member) {
        return switch (member.getKind()) {
            case FIELD, ENUM_CONSTANT -> "field " + member.getSimpleName();
            case CONSTRUCTOR -> "constructor " + member;
            default -> "method " + member;
        };
    }

    /// The classes and interfaces the signatures of the members mention.
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
                    method.result().ifPresent(r -> Mentions.of(r, found));
                    method.params().forEach(p -> Mentions.of(p, found));
                    method.throwsTypes().forEach(t -> Mentions.of(t, found));
                }
            }
        }
    }
}
