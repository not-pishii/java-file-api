package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.langmodel.mirror.FieldModel.Mutability;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.io.Serial;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// Translates what an annotation processor sees through `javax.lang.model`
/// into the model of generated code: a [TypeModel] of a declared type, or a
/// [TypeRef] of a type mirror. The one translator of the `@Facts`
/// processor, of the check of metamodels against the target classpath and
/// of the mirror source, so all three agree on what a type is and on its
/// [Canonical] form.
///
/// It makes no facts: its output is plain data. Every result is a
/// [Translation], [Translation.Deferred] while a mentioned type is not
/// generated yet (`TypeKind.ERROR`) and [Translation.Unrepresentable] for a
/// type the model cannot express:
///
/// | `TypeKind` | Translation |
/// |---|---|
/// | `BOOLEAN` … `DOUBLE` | [PrimitiveTypeRef] |
/// | `ARRAY` | [ArrayTypeRef] of the component |
/// | `DECLARED` | [ClassTypeRef], or [ParameterizedTypeRef] with type arguments, by binary name
/// (`java.util.Map$Entry`); a generic type without arguments stays raw; a member class of a parameterized type
/// (`Outer<T>.Inner`) is unrepresentable |
/// | `TYPEVAR` | [TypeVarRef] by its declared name, if in scope; unrepresentable otherwise |
/// | `WILDCARD` | a [TypeArg] within type arguments, `? extends Object` as `?`; unrepresentable elsewhere |
/// | `INTERSECTION` | the bounds of a type parameter; unrepresentable elsewhere |
/// | `VOID` | an empty [MethodModel#result()]; unrepresentable elsewhere |
/// | `ERROR` | deferred |
/// | others | unrepresentable |
///
/// Type-use annotations are left out.
public final class MirrorTranslator {
    /// The public methods of `Object` that an interface may redeclare
    /// abstract without asking its implementations for them, and without
    /// ceasing to be functional (JLS 9.8).
    private static final Set<MethodTableTemplate.Signature> OBJECT_METHODS = Set.of(
            MethodTableTemplate.Signature.of("equals", MethodTableTemplate.Param.fixed(ConstantDescs.CD_Object)),
            MethodTableTemplate.Signature.of("hashCode"),
            MethodTableTemplate.Signature.of("toString"));

    private static final String DOLLAR = "a class with $ in its simple name is not supported yet";

    private final Elements elements;
    private final javax.lang.model.util.Types types;

    /// A translator for one compilation.
    ///
    /// @param elements the element utilities of the compilation
    /// @param types the type utilities of the compilation
    public MirrorTranslator(Elements elements, javax.lang.model.util.Types types) {
        this.elements = elements;
        this.types = types;
    }

    /// Translates a type mirror. Types that are not `public` are translated
    /// like any others.
    ///
    /// @param mirror the type
    /// @param scope the type variables `mirror` may mention
    /// @return the type, or why there is none
    public Translation<TypeRef> typeRef(TypeMirror mirror, VarScope scope) {
        Reading reading = new Reading(scope);
        try {
            TypeRef type = type(mirror, reading);
            return reading.problems.isEmpty()
                    ? new Translation.Ok<>(type)
                    : new Translation.Unrepresentable<>(reading.problem());
        } catch (Unresolved unresolved) {
            return new Translation.Deferred<>(unresolved.type);
        }
    }

    /// Translates a declared type: its shape — kind, type parameters,
    /// superclasses, interfaces, parameterized supertypes, method table, enum
    /// constants, `sealed`, the single abstract method — and the members `filter` selects, as members
    /// of the type (`Types.asMemberOf`). The members are those of `filter`
    /// only, out of [#members(TypeElement)]; the method table is complete
    /// whatever the filter.
    ///
    /// A member whose signature cannot be translated, mentions a type that
    /// is not `public`, or has more than [MemberModel#MAX_ARITY] parameters
    /// is a [SkippedMember], not a failure of the type.
    ///
    /// @param element the class, interface, enum or record
    /// @param filter which members to translate
    /// @return the type, deferred if any part of it or of a selected member mentions a type
    ///         not generated yet, or unrepresentable for an annotation interface, an inner
    ///         class of a generic class, or a type whose shape cannot be expressed
    public Translation<TypeModel> type(TypeElement element, MemberFilter filter) {
        if (element.getKind() == ElementKind.ANNOTATION_TYPE) {
            return new Translation.Unrepresentable<>(
                    "annotation interface " + element.getQualifiedName() + " is not supported yet");
        }
        Optional<TypeElement> genericOuter = genericOuter(element);
        if (genericOuter.isPresent()) {
            return new Translation.Unrepresentable<>("inner class " + element.getQualifiedName()
                    + " of generic class " + genericOuter.get().getQualifiedName()
                    + " can mention its type parameters");
        }
        try {
            return model(element, filter);
        } catch (Unresolved unresolved) {
            return new Translation.Deferred<>(unresolved.type);
        }
    }

    /// The fields, constructors and methods a full metamodel of a type has
    /// facts of ([MemberFilter#DECLARED_ACCESSIBLE]): the accessible ones the
    /// type declares, in declaration order, and then the ones it adopts. A
    /// member is accessible if it is `public`, or `protected` and the type a
    /// class that can be extended ([#extendable(TypeElement)]): a subclass
    /// reaches it, and nothing else does.
    ///
    /// A type adopts the accessible fields and methods it inherits from a
    /// supertype that is not `public`: such a supertype has no metamodel, so
    /// its members are told as members of the nearest subtype that can have
    /// one, as javac sees them — `capacity()` of `java.lang.StringBuilder`,
    /// which the package-private `AbstractStringBuilder` declares. The
    /// supertypes a type adopts from are the ones it reaches through
    /// supertypes that are not `public` alone: what is beyond a `public`
    /// supertype is told by that supertype. Of their members the type adopts
    /// those it has:
    ///
    /// - not a method a nearer type overrides or implements, nor a field or
    ///   a `static` method a nearer type hides;
    /// - a field only if the type has no other field of its name: where a
    ///   `public` supertype has one too, or two hidden supertypes each have
    ///   one, the name is ambiguous in the type, and the field is a
    ///   [SkippedMember];
    /// - not a `static` method of an interface, which is not inherited;
    /// - of the abstract methods of one signature the type has — those of its
    ///   `public` supertypes among them — the one of the most specific
    ///   result, as for a `sam`, and none if a `public` supertype declares
    ///   that one: its metamodel tells the method as javac sees it on the
    ///   type, `String get()` of a `public` interface beside `Object get()`
    ///   of a hidden one;
    /// - each once, however many ways lead to the supertype that declares it;
    /// - not one a `public` supertype of the type has adopted, which the
    ///   type reaches through that supertype too — `X extends Mid`, where
    ///   both implement `Hidden`: the members of `Hidden` are told by `Mid`,
    ///   like the members `Mid` declares, and `X` has them from `Mid`. A
    ///   member is told by one type, the farthest that has it. `Mid` tells
    ///   them if a full metamodel can be made of it ([#refusal(TypeElement)],
    ///   [#rejection(TypeElement, TypeModel)]): which the type `Mid` alone
    ///   decides, not who asks for it or where its metamodel comes from, so
    ///   the members of `X`, and its [Canonical] form, are the same in
    ///   every compilation that has the same types. If none can, `X` adopts
    ///   them, or no fact would reach them. Whether `Mid` gets a fact of
    ///   each is not asked: a member it adopts and skips is reported for
    ///   `Mid`, as a member it declares and skips is.
    ///
    /// Constructors are not inherited, so none is adopted.
    ///
    /// @param element the class, interface, enum or record
    /// @return the members, each a field, constructor or method
    public List<Element> members(TypeElement element) {
        return Stream.concat(declared(element), adopted(element, (DeclaredType) element.asType()).stream())
                .toList();
    }

    /// The accessible fields, constructors and methods a type declares.
    private static Stream<Element> declared(TypeElement element) {
        return element.getEnclosedElements().stream()
                .filter(member -> access(member, element).isPresent())
                .<Element>map(member -> member)
                // enum constants are TypeModel.enumConstants; member types have models of their own
                .filter(member -> switch (member.getKind()) {
                    case FIELD, CONSTRUCTOR, METHOD -> true;
                    default -> false;
                });
    }

    /// The members a type adopts, see [#members(TypeElement)]: those of the nearer supertype first.
    private List<Element> adopted(TypeElement element, DeclaredType self) {
        List<? extends Element> all = elements.getAllMembers(element);
        List<Element> had = had(element, all);
        if (had.isEmpty()) {
            return List.of();
        }
        // what a public supertype has of them, it tells: the type has them from that supertype
        Set<Element> told = supertypesThrough(element, supertype -> true)
                .filter(this::tells)
                .flatMap(supertype -> had(supertype, elements.getAllMembers(supertype)).stream())
                .collect(Collectors.toSet());
        // the abstract methods of one signature that the type has, whoever declares them, are one member of it
        Map<MethodTableTemplate.Signature, List<ExecutableElement>> twins = ElementFilter.methodsIn(all).stream()
                .filter(method -> method.getModifiers().contains(Modifier.ABSTRACT))
                .collect(Collectors.groupingBy(method -> signature(method, element, self)));
        return had.stream()
                .filter(member -> switch (member) {
                    case ExecutableElement method
                    when method.getModifiers().contains(Modifier.ABSTRACT) -> {
                        List<ExecutableElement> same = twins.get(signature(method, element, self));
                        yield same.stream().noneMatch(told::contains)
                                && pick(same, self).equals(method);
                    }
                    default -> !told.contains(member);
                })
                .toList();
    }

    /// The accessible fields and methods of the supertypes that are not
    /// `public` which a type has through such supertypes alone: those of
    /// the nearer supertype first, and none that a nearer type replaces. A
    /// `public` supertype that has one of them too is not looked at here.
    ///
    /// @param all every member of `element`, as `Elements.getAllMembers` gives them
    private List<Element> had(TypeElement element, List<? extends Element> all) {
        List<TypeElement> hidden =
                supertypesThrough(element, supertype -> !isPublic(supertype)).toList();
        if (hidden.isEmpty()) {
            return List.of();
        }
        Map<String, List<Element>> inherited = all.stream()
                .<Element>map(member -> member)
                .filter(member -> member.getKind() == ElementKind.FIELD || member.getKind() == ElementKind.METHOD)
                .collect(Collectors.groupingBy(member -> member.getSimpleName().toString()));
        return hidden.stream()
                .<Element>flatMap(supertype -> supertype.getEnclosedElements().stream())
                .filter(member -> access(member, element).isPresent())
                .filter(member -> {
                    List<Element> namesakes =
                            inherited.getOrDefault(member.getSimpleName().toString(), List.of());
                    return namesakes.contains(member)
                            && namesakes.stream().noneMatch(other -> replaces(other, member, element));
                })
                .toList();
    }

    /// The access a fact of a member of a type is of: that of a `public`
    /// member, and of a `protected` one if the type is a class that can be
    /// extended; none for any other member, which code of another package
    /// cannot name.
    ///
    /// @param member a member of `type`, declared or inherited
    /// @param type the class, interface, enum or record
    /// @return the access of the fact, if the member can have one
    private static Optional<Access> access(Element member, TypeElement type) {
        Set<Modifier> modifiers = member.getModifiers();
        if (modifiers.contains(Modifier.PUBLIC)) {
            return Optional.of(Access.PUBLIC);
        }
        return modifiers.contains(Modifier.PROTECTED) && extendable(type)
                ? Optional.of(Access.PROTECTED)
                : Optional.empty();
    }

    /// Whether a type is a class a class of another package can extend, as
    /// its kind tells: neither `final`, nor a record, nor an enum, nor an
    /// interface.
    ///
    /// @param type the class, interface, enum or record
    /// @return `true` for a class whose token is an `ExtendableClassToken`
    public static boolean extendable(TypeElement type) {
        return switch (kind(type)) {
            case DeclaredKind.OpenClass _ -> true;
            case DeclaredKind.AbstractClass _ -> true;
            case DeclaredKind.FinalClass _ -> false;
            case DeclaredKind.Interface _ -> false;
            case DeclaredKind.EnumClass _ -> false;
        };
    }

    /// Whether a supertype tells the members it adopts: it is `public`, and
    /// a full metamodel can be made of it.
    private boolean tells(TypeElement supertype) {
        if (!isPublic(supertype) || refusal(supertype).isPresent()) {
            return false;
        }
        return switch (type(supertype, MemberFilter.NONE)) {
            case Translation.Ok<TypeModel>(TypeModel model) ->
                switch (rejection(supertype, model)) {
                    case Admission.Admitted _ -> true;
                    case Admission.Rejected _ -> false;
                    case Admission.Deferred(String unresolved) -> throw new Unresolved(unresolved);
                };
            case Translation.Deferred<TypeModel>(String unresolved) -> throw new Unresolved(unresolved);
            case Translation.Unrepresentable<TypeModel> _ -> false;
        };
    }

    /// Why no metamodel can be made of a type, whatever it declares: it is
    /// an annotation interface, it is not `public` or is nested in a type
    /// that is not, its simple name or that of a type it is nested in has a
    /// `$`, or it is in the unnamed package.
    ///
    /// @param type a class, interface, enum, record or annotation interface
    /// @return the reason, a sentence about the type; empty if a metamodel can be asked for
    public static Optional<String> refusal(TypeElement type) {
        if (type.getKind() == ElementKind.ANNOTATION_TYPE) {
            return Optional.of("annotation interface " + type.getQualifiedName() + " is not supported yet");
        }
        Optional<TypeElement> hidden = enclosing(type)
                .filter(element -> !element.getModifiers().contains(Modifier.PUBLIC))
                .findFirst();
        if (hidden.isPresent()) {
            return Optional.of(
                    hidden.get().equals(type)
                            ? type.getQualifiedName() + " is not public"
                            : type.getQualifiedName() + " is nested in "
                                    + hidden.get().getQualifiedName() + ", which is not public");
        }
        if (dollar(type)) {
            return Optional.of(type.getQualifiedName() + ": " + DOLLAR);
        }
        if (unnamedPackage(type)) {
            return Optional.of(type.getQualifiedName()
                    + " is in the unnamed package, which a metamodel in a named package cannot refer to");
        }
        return Optional.empty();
    }

    /// Why a type that has a model has no full metamodel all the same: a
    /// bound of its type parameters mentions a type a metamodel cannot
    /// declare the bound with — one that is not `public`, or that no
    /// metamodel can refer to. Without the bound javac would accept a token
    /// of a type argument the type does not.
    ///
    /// With [#refusal(TypeElement)] and a model that is
    /// [Translation.Unrepresentable] it is all that keeps a `public` type
    /// from a full metamodel, and all of it is read off the type: so the
    /// `@Facts` processor and [#members(TypeElement)] agree on which
    /// supertypes have one.
    ///
    /// @param type a class, interface, enum or record
    /// @param model its model, with whatever members
    /// @return whether a full metamodel can be made, with the reason, a sentence, if none can; deferred
    ///         if a bound mentions a type not generated yet
    public Admission rejection(TypeElement type, TypeModel model) {
        if (!model.nonPublicBoundTypes().isEmpty()) {
            return new Admission.Rejected("the bounds of the type parameters of " + type.getQualifiedName()
                    + " mention types that are not public: " + String.join(", ", model.nonPublicBoundTypes()));
        }
        try {
            return type.getTypeParameters().stream()
                    .flatMap(parameter -> parameter.getBounds().stream())
                    .flatMap(MirrorTranslator::declaredIn)
                    .filter(mentioned -> !mentioned.equals(type))
                    .collect(Collectors.toMap(
                            mentioned -> elements.getBinaryName(mentioned).toString(),
                            mentioned -> mentioned,
                            (first, second) -> first,
                            TreeMap::new))
                    .entrySet()
                    .stream()
                    .flatMap(mentioned -> unmentionable(mentioned.getValue())
                            .map(reason -> "the bounds of the type parameters of " + type.getQualifiedName()
                                    + " mention " + mentioned.getKey() + ", which has no metamodel: " + reason)
                            .stream())
                    .findFirst()
                    .<Admission>map(Admission.Rejected::new)
                    .orElseGet(Admission.Admitted::new);
        } catch (Unresolved unresolved) {
            return new Admission.Deferred(unresolved.type);
        }
    }

    /// Why no metamodel can refer to a type a signature mentions.
    private Optional<String> unmentionable(TypeElement type) {
        if (unnamedPackage(type)) {
            return Optional.of("a metamodel in a named package cannot refer to it");
        }
        if (dollar(type)) {
            return Optional.of(DOLLAR);
        }
        return switch (type(type, MemberFilter.NONE)) {
            case Translation.Ok<TypeModel> _ -> Optional.empty();
            case Translation.Deferred<TypeModel>(String unresolved) -> throw new Unresolved(unresolved);
            case Translation.Unrepresentable<TypeModel>(String reason) -> Optional.of(reason);
        };
    }

    /// The classes and interfaces a type mentions, itself and in its type arguments.
    private static Stream<TypeElement> declaredIn(TypeMirror mirror) {
        return switch (mirror.getKind()) {
            case DECLARED -> {
                DeclaredType declared = (DeclaredType) mirror;
                yield Stream.concat(
                        Stream.of((TypeElement) declared.asElement()),
                        declared.getTypeArguments().stream().flatMap(MirrorTranslator::declaredIn));
            }
            case ARRAY -> declaredIn(((ArrayType) mirror).getComponentType());
            case WILDCARD -> {
                WildcardType wildcard = (WildcardType) mirror;
                yield Stream.of(wildcard.getExtendsBound(), wildcard.getSuperBound())
                        .filter(bound -> bound != null)
                        .flatMap(MirrorTranslator::declaredIn);
            }
            // a type variable is a type parameter of the type, whose bounds are read where it is declared; the
            // bounds of a type that has a model are resolved, and each is a bound of its own
            default -> Stream.empty();
        };
    }

    /// Whether the simple name of the type or of an enclosing type has a
    /// `$`, which `java-file-api-core` reads as the separator of a member
    /// type in a [ClassDesc].
    private static boolean dollar(TypeElement type) {
        return enclosing(type)
                .anyMatch(element -> element.getSimpleName().toString().contains("$"));
    }

    private static boolean unnamedPackage(TypeElement type) {
        return Stream.<Element>iterate(type, Element::getEnclosingElement)
                .filter(PackageElement.class::isInstance)
                .map(PackageElement.class::cast)
                .findFirst()
                .orElseThrow()
                .isUnnamed();
    }

    /// `type` and the types that enclose it, the innermost first.
    private static Stream<TypeElement> enclosing(TypeElement type) {
        return Stream.<Element>iterate(type, element -> element instanceof TypeElement, Element::getEnclosingElement)
                .map(TypeElement.class::cast);
    }

    private ExecutableElement pick(List<ExecutableElement> twins, DeclaredType self) {
        return twins.size() == 1 ? twins.getFirst() : mostSpecific(twins, self);
    }

    /// The supertypes a type reaches through supertypes that are `through`
    /// alone, themselves `through`: each once, the nearer first.
    private static Stream<TypeElement> supertypesThrough(TypeElement type, Predicate<TypeElement> through) {
        return Hierarchy.beyond(
                List.of(type),
                nearer -> Stream.concat(Stream.of(nearer.getSuperclass()), nearer.getInterfaces().stream())
                        .filter(supertype -> supertype.getKind() == TypeKind.DECLARED)
                        .map(supertype -> (TypeElement) ((DeclaredType) supertype).asElement())
                        .filter(through));
    }

    /// Whether `type` has `other` in place of `member`: `other` hides it, or
    /// overrides or implements it as a member of `type`.
    private boolean replaces(Element other, Element member, TypeElement type) {
        return other != member
                && (elements.hides(other, member)
                        || other instanceof ExecutableElement overrider
                                && member instanceof ExecutableElement overridden
                                && elements.overrides(overrider, overridden, type));
    }

    /// Translates one member of a type, as [#type] does for each member its
    /// filter selects: as a member of the type (`Types.asMemberOf`), whatever its
    /// access. It lets a caller tell which element a [MemberModel] is of.
    ///
    /// @param owner the class, interface, enum or record
    /// @param member a field, constructor or method of `owner`, declared or inherited
    /// @return the member, or the reason it has no model, as [SkippedMember#reason()] tells it; deferred
    ///         if its signature mentions a type not generated yet
    /// @throws IllegalArgumentException if `member` is not a field, constructor or method
    public Translation<MemberModel> member(TypeElement owner, Element member) {
        DeclaredType self = (DeclaredType) owner.asType();
        try {
            return switch (read(owner, self, member)) {
                case Read.Made(MemberModel model) -> new Translation.Ok<>(model);
                case Read.Skipped(SkippedMember skip) -> new Translation.Unrepresentable<>(skip.reason());
            };
        } catch (Unresolved unresolved) {
            return new Translation.Deferred<>(unresolved.type);
        }
    }

    /// The single abstract method of a functional interface (JLS 9.8), as a
    /// member of the interface: the one abstract method left once the
    /// `public` methods of `Object` are set aside, declared by the interface
    /// or inherited from a superinterface.
    ///
    /// Where the interface inherits several override-equivalent abstract
    /// methods, the method is the function type of the interface (JLS 9.9):
    /// its result is the most specific of theirs, and it throws only what
    /// every one of them allows — the exceptions of their `throws` clauses
    /// that are a subtype of an exception of each clause.
    ///
    /// Empty for what is not a functional interface — a `sealed` interface
    /// is none —, for one whose method, or a method it is override-equivalent
    /// to, is generic, and for one that declares its method, or adopts it
    /// ([#members(TypeElement)]), but has no model of it, which [#type]
    /// reports as a [SkippedMember].
    ///
    /// @param element the type
    /// @return the method; empty if there is none to make a `sam` fact of; unrepresentable if
    ///         the method is inherited and its signature has no model, for the reason [#member] gives;
    ///         deferred if it mentions a type not generated yet
    public Translation<Optional<SamModel>> sam(TypeElement element) {
        try {
            return switch (readSam(element, () -> adopted(element, (DeclaredType) element.asType()))) {
                case SamRead.Found(Optional<SamModel> sam) -> new Translation.Ok<>(sam);
                case SamRead.NoModel(String reason) -> new Translation.Unrepresentable<>(reason);
            };
        } catch (Unresolved unresolved) {
            return new Translation.Deferred<>(unresolved.type);
        }
    }

    /// @param adopted the members the type adopts, see [#adopted]: asked for only where the method is
    ///     not one the type declares
    private SamRead readSam(TypeElement element, Supplier<List<Element>> adopted) {
        if (element.getKind() != ElementKind.INTERFACE || element.getModifiers().contains(Modifier.SEALED)) {
            return new SamRead.Found(Optional.empty());
        }
        DeclaredType self = (DeclaredType) element.asType();
        Set<MethodTableTemplate.Signature> abstracts = methods(element, self).abstractMethods();
        if (abstracts.size() != 1) {
            return new SamRead.Found(Optional.empty());
        }
        MethodTableTemplate.Signature signature = abstracts.iterator().next();
        List<ExecutableElement> candidates = ElementFilter.methodsIn(elements.getAllMembers(element)).stream()
                .filter(m -> m.getModifiers().contains(Modifier.ABSTRACT))
                .filter(m -> signature(m, element, self).equals(signature))
                .toList();
        if (candidates.stream().anyMatch(m -> !m.getTypeParameters().isEmpty())) {
            return new SamRead.Found(Optional.empty());
        }
        ExecutableElement method = mostSpecific(candidates, self);
        // declared, or adopted from a superinterface that is not public: a member the type has a fact of
        boolean declared = method.getEnclosingElement().equals(element)
                || adopted.get().stream()
                        .anyMatch(member -> member instanceof ExecutableElement adoptedMethod
                                && signature(adoptedMethod, element, self).equals(signature));
        return switch (method(element, self, method, functionThrows(candidates, self))) {
            case Read.Made(MemberModel model) ->
                new SamRead.Found(Optional.of(new SamModel((MethodModel) model, declared)));
            case Read.Skipped(SkippedMember skip) ->
                declared ? new SamRead.Found(Optional.empty()) : new SamRead.NoModel(skip.reason());
        };
    }

    /// The single abstract method of a type, as it was read.
    private sealed interface SamRead {
        /// The method, or that there is none to make a `sam` fact of.
        record Found(Optional<SamModel> sam) implements SamRead {}

        /// The method is inherited and its signature has no model.
        record NoModel(String reason) implements SamRead {}
    }

    /// The method whose result is a subtype of the results of the others, as
    /// members of the type: the one that is return-type-substitutable for
    /// every other (JLS 8.4.5). A result that is a subtype as it is written
    /// is preferred to one that is a subtype only once erased.
    private ExecutableElement mostSpecific(List<ExecutableElement> candidates, DeclaredType self) {
        return mostSpecific(candidates, self, false)
                .or(() -> mostSpecific(candidates, self, true))
                .orElseGet(candidates::getFirst);
    }

    private Optional<ExecutableElement> mostSpecific(
            List<ExecutableElement> candidates, DeclaredType self, boolean erased) {
        return candidates.stream()
                .filter(candidate -> {
                    TypeMirror result = ((ExecutableType) types.asMemberOf(self, candidate)).getReturnType();
                    requireResolved(result);
                    return candidates.stream().allMatch(other -> {
                        TypeMirror otherResult = ((ExecutableType) types.asMemberOf(self, other)).getReturnType();
                        requireResolved(otherResult);
                        if (result.getKind().isPrimitive() || result.getKind() == TypeKind.VOID) {
                            return result.getKind() == otherResult.getKind();
                        }
                        // of two results javac takes for one method, the other is a reference where this one is
                        return erased
                                ? types.isSubtype(types.erasure(result), types.erasure(otherResult))
                                : types.isSubtype(result, otherResult);
                    });
                })
                .findFirst();
    }

    /// What the function type of override-equivalent methods throws (JLS
    /// 9.9): every exception of one of their `throws` clauses that is a
    /// subtype of an exception of each clause.
    private List<TypeMirror> functionThrows(List<ExecutableElement> candidates, DeclaredType self) {
        List<List<? extends TypeMirror>> clauses = candidates.stream()
                .<List<? extends TypeMirror>>map(candidate -> {
                    List<? extends TypeMirror> clause =
                            ((ExecutableType) types.asMemberOf(self, candidate)).getThrownTypes();
                    clause.forEach(MirrorTranslator::requireResolved);
                    return clause;
                })
                .toList();
        List<TypeMirror> allowed = clauses.stream()
                .<TypeMirror>flatMap(List::stream)
                .filter(exception -> clauses.stream()
                        .allMatch(clause -> clause.stream().anyMatch(other -> types.isSubtype(exception, other))))
                .toList();
        // each once: the first of the exceptions that are the same type
        return IntStream.range(0, allowed.size())
                .filter(i -> IntStream.range(0, i).noneMatch(j -> types.isSameType(allowed.get(j), allowed.get(i))))
                .mapToObj(allowed::get)
                .toList();
    }

    private Translation<TypeModel> model(TypeElement element, MemberFilter filter) {
        DeclaredType self = (DeclaredType) element.asType();
        Reading bounds = new Reading(VarScope.of(element));
        List<TypeParam> typeParams = element.getTypeParameters().stream()
                .map(parameter -> typeParam((TypeVariable) parameter.asType(), bounds))
                .toList();
        Reading shape = new Reading(VarScope.of(element));
        List<ClassDesc> superclasses = superclasses(element);
        List<ClassDesc> interfaces = interfaces(self);
        Supertypes supertypes = supertypes(self, typeParams, shape);
        MethodTableTemplate methods = methods(element, self);
        List<String> enumConstants = element.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.ENUM_CONSTANT)
                .map(e -> e.getSimpleName().toString())
                .toList();
        // a model with members reads what the type adopts once, for the members and for the sam
        Supplier<List<Element>> adopted =
                switch (filter) {
                    case DECLARED_ACCESSIBLE -> {
                        List<Element> read = adopted(element, self);
                        yield () -> read;
                    }
                    case NONE -> () -> adopted(element, self);
                };
        List<Read> reads =
                switch (filter) {
                    case DECLARED_ACCESSIBLE ->
                        Stream.concat(declared(element), adopted.get().stream())
                                .map(member -> read(element, self, member))
                                .toList();
                    case NONE -> List.of();
                };
        Optional<MethodModel> sam =
                switch (readSam(element, adopted)) {
                    case SamRead.Found(Optional<SamModel> found) -> found.map(SamModel::method);
                    case SamRead.NoModel _ -> Optional.empty();
                };
        shape.problems.addAll(bounds.problems);
        if (!shape.problems.isEmpty()) {
            return new Translation.Unrepresentable<>("type " + element.getQualifiedName() + ": " + shape.problem());
        }
        return new Translation.Ok<>(new TypeModel(
                desc(element),
                kind(element),
                nesting(element),
                typeParams,
                List.copyOf(bounds.nonPublic),
                superclasses,
                interfaces,
                supertypes,
                methods,
                enumConstants,
                element.getModifiers().contains(Modifier.SEALED),
                sam,
                switch (filter) {
                    case DECLARED_ACCESSIBLE -> inheritance(element);
                    case NONE -> Heritage.UNTOLD;
                },
                filter,
                reads.stream().flatMap(Read::models).toList(),
                reads.stream().flatMap(Read::skips).toList()));
    }

    private static Optional<TypeElement> genericOuter(TypeElement element) {
        return Stream.iterate(
                        element,
                        type -> type.getNestingKind() == NestingKind.MEMBER
                                && !type.getModifiers().contains(Modifier.STATIC),
                        type -> (TypeElement) type.getEnclosingElement())
                .map(type -> (TypeElement) type.getEnclosingElement())
                .filter(outer -> !outer.getTypeParameters().isEmpty())
                .findFirst();
    }

    /// Where a type is declared, as a class that extends it has to know: an inner class is one that is a
    /// member of a class and not `static` — an interface, an enum and a record never are.
    private static Nesting nesting(TypeElement element) {
        if (element.getNestingKind() != NestingKind.MEMBER) {
            return Nesting.TOP_LEVEL;
        }
        return element.getKind() == ElementKind.CLASS && !element.getModifiers().contains(Modifier.STATIC)
                ? Nesting.INNER
                : Nesting.STATIC_MEMBER;
    }

    private static DeclaredKind kind(TypeElement element) {
        return switch (element.getKind()) {
            case ENUM -> DeclaredKind.ENUM_CLASS;
            case INTERFACE -> DeclaredKind.INTERFACE;
            case RECORD -> DeclaredKind.FINAL_CLASS;
            default -> {
                Set<Modifier> modifiers = element.getModifiers();
                if (modifiers.contains(Modifier.FINAL)) {
                    yield DeclaredKind.FINAL_CLASS;
                }
                yield modifiers.contains(Modifier.ABSTRACT) ? DeclaredKind.ABSTRACT_CLASS : DeclaredKind.OPEN_CLASS;
            }
        };
    }

    private List<ClassDesc> superclasses(TypeElement element) {
        return Stream.iterate(
                        element.getSuperclass(),
                        superclass -> superclass.getKind() != TypeKind.NONE,
                        superclass -> ((TypeElement) ((DeclaredType) superclass).asElement()).getSuperclass())
                .map(superclass -> {
                    requireResolved(superclass);
                    return desc((TypeElement) ((DeclaredType) superclass).asElement());
                })
                .toList();
    }

    /// The interfaces among the supertypes of a type, erased and sorted by binary name.
    private List<ClassDesc> interfaces(DeclaredType self) {
        return supertypes(self)
                .map(supertype -> (TypeElement) supertype.asElement())
                .filter(supertype -> supertype.getKind().isInterface())
                .map(supertype -> elements.getBinaryName(supertype).toString())
                .sorted()
                .map(ClassDesc::of)
                .toList();
    }

    /// Every supertype of a type, in its terms: each class and interface once, the nearer first.
    private Stream<DeclaredType> supertypes(DeclaredType self) {
        return Hierarchy.<Supertype>beyond(List.of(new Supertype(self)), supertype -> direct(supertype.type()))
                .map(Supertype::type);
    }

    private Supertypes supertypes(DeclaredType self, List<TypeParam> typeParams, Reading reading) {
        Map<String, ParameterizedTypeRef> found = supertypes(self)
                .flatMap(supertype -> declared(supertype, reading) instanceof ParameterizedTypeRef parameterized
                        ? Stream.of(Map.entry(binaryName(supertype), parameterized))
                        : Stream.empty())
                .collect(TreeMap::new, (map, entry) -> map.putIfAbsent(entry.getKey(), entry.getValue()), Map::putAll);
        return new Supertypes(
                typeParams.stream().map(p -> new TypeVarRef(p.name())).toList(), List.copyOf(found.values()));
    }

    /// The direct supertypes of a type, in its terms.
    private Stream<Supertype> direct(DeclaredType type) {
        // directSupertypes leaves out an interface not generated yet
        ((TypeElement) type.asElement()).getInterfaces().forEach(MirrorTranslator::requireResolved);
        return types.directSupertypes(type).stream().map(supertype -> new Supertype((DeclaredType) supertype));
    }

    /// A supertype as one type among the supertypes of a type: by its
    /// element, whatever its type arguments, so that each is met once.
    private record Supertype(DeclaredType type) {
        @Override
        public boolean equals(Object other) {
            return other instanceof Supertype(DeclaredType otherType)
                    && type.asElement().equals(otherType.asElement());
        }

        @Override
        public int hashCode() {
            return type.asElement().hashCode();
        }
    }

    private MethodTableTemplate methods(TypeElement element, DeclaredType self) {
        Map<MethodTableTemplate.Signature, List<ExecutableElement>> table =
                ElementFilter.methodsIn(elements.getAllMembers(element)).stream()
                        .filter(method -> !method.getModifiers().contains(Modifier.PRIVATE))
                        .collect(Collectors.groupingBy(method -> signature(method, element, self)));
        boolean isInterface = element.getKind() == ElementKind.INTERFACE;
        Map<Category, Set<MethodTableTemplate.Signature>> parts = table.entrySet().stream()
                .collect(Collectors.groupingBy(
                        // an interface that redeclares a public method of Object abstract does not ask its
                        // implementations for it: every class has it from Object
                        e -> {
                            Category category = Category.of(e.getValue());
                            return isInterface && category == Category.ABSTRACT && OBJECT_METHODS.contains(e.getKey())
                                    ? Category.CONCRETE
                                    : category;
                        },
                        Collectors.mapping(Map.Entry::getKey, Collectors.toUnmodifiableSet())));
        return new MethodTableTemplate(
                parts.getOrDefault(Category.ABSTRACT, Set.of()),
                parts.getOrDefault(Category.CONCRETE, Set.of()),
                parts.getOrDefault(Category.STATIC, Set.of()),
                ElementFilter.constructorsIn(element.getEnclosedElements()).stream()
                        .filter(constructor -> !constructor.getModifiers().contains(Modifier.PRIVATE))
                        .map(constructor -> new MethodTableTemplate.Signature(
                                desc(element).displayName(), declared(constructor, element, self)))
                        .collect(Collectors.toUnmodifiableSet()));
    }

    /// What a class that extends or implements a type inherits of it
    /// ([Heritage]), told by the positions of the type parameters:
    ///
    /// - every method that is not `private`, of the type and of every
    ///   supertype of it, that no other of them overrides or hides for the
    ///   type — whoever declares it, a supertype that is not `public`
    ///   included, and whatever its access and the types of its signature.
    ///   They are not read off `Elements.getAllMembers`, which leaves out
    ///   what the type does not inherit: a method with package access of a
    ///   superclass of another package is no member of the type, and javac
    ///   asks a subclass for it all the same if it is abstract;
    /// - the methods of one signature as one [Heritage.Method] with a
    ///   declaration each, see there for their order;
    /// - of an interface neither the methods of `Object`, nor a method it
    ///   declares again with the signature of a `public` one of `Object` — a
    ///   class that implements it has them from its superclass —, nor its
    ///   `static` methods, which nothing inherits (JLS 8.4.8);
    /// - every constructor the type declares that is not `private`;
    /// - every field and member type that is not `private`, of the type and
    ///   of every supertype of it.
    ///
    /// A method overrides another for the type if `Elements.overrides` tells
    /// so for the type or for the class that declares it, or — which
    /// `Elements` does not tell of a method the type does not inherit — it
    /// is a method of a subclass in the package of a method with package
    /// access, of a subsignature of it (JLS 8.4.8.1).
    ///
    /// @param element the class, interface, enum or record
    /// @return the heritage; [Heritage#UNTOLD] of a type that cannot be extended or implemented, and of
    ///         one with a member whose signature cannot be expressed
    /// @throws IllegalStateException if a member mentions a type not generated yet: ask [#type] first, which
    ///                               defers
    public Heritage heritage(TypeElement element) {
        try {
            return inheritance(element);
        } catch (Unresolved unresolved) {
            throw new IllegalStateException(
                    "a member of " + element.getQualifiedName() + " mentions " + unresolved.type
                            + ", which is not generated yet",
                    unresolved);
        }
    }

    private Heritage inheritance(TypeElement element) {
        if (!(kind(element) instanceof DeclaredKind.Inheritable)) {
            return Heritage.UNTOLD;
        }
        DeclaredType self = (DeclaredType) element.asType();
        boolean isInterface = element.getKind() == ElementKind.INTERFACE;
        TypeElement object = elements.getTypeElement("java.lang.Object");
        // the type, then its supertypes, the nearer first
        List<TypeElement> lineage = Stream.concat(
                        Stream.of(element), supertypes(self).map(supertype -> (TypeElement) supertype.asElement()))
                .toList();
        Map<String, List<ExecutableElement>> byName = lineage.stream()
                .flatMap(type -> ElementFilter.methodsIn(type.getEnclosedElements()).stream())
                .filter(method -> !method.getModifiers().contains(Modifier.PRIVATE))
                .filter(method -> !(method.getEnclosingElement().getKind().isInterface()
                        && method.getModifiers().contains(Modifier.STATIC)))
                .filter(method -> !isInterface
                        || !(method.getEnclosingElement().equals(object)
                                || OBJECT_METHODS.contains(signature(method, element, self))))
                .collect(Collectors.groupingBy(
                        method -> method.getSimpleName().toString(), LinkedHashMap::new, Collectors.toList()));
        Map<MethodTableTemplate.Signature, List<ExecutableElement>> bySignature = byName.values().stream()
                .flatMap(namesakes -> namesakes.stream()
                        .filter(method -> namesakes.stream()
                                .noneMatch(
                                        other -> elements.hides(other, method) || overrides(other, method, element))))
                .collect(Collectors.groupingBy(
                        method -> signature(method, element, self), LinkedHashMap::new, Collectors.toList()));
        List<Inherited<Heritage.Method<Heritage.Declaration>>> methods = bySignature.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
                .map(entry -> inherited(
                        element,
                        self,
                        entry.getKey(),
                        entry.getValue(),
                        byName.get(entry.getKey().name()),
                        lineage))
                .toList();
        List<Inherited<Heritage.Constructor>> constructors =
                ElementFilter.constructorsIn(element.getEnclosedElements()).stream()
                        .filter(constructor -> !constructor.getModifiers().contains(Modifier.PRIVATE))
                        .map(constructor -> constructorOf(element, self, constructor))
                        .sorted(Comparator.comparing(
                                constructor -> constructor.member().signature().toString()))
                        .toList();
        boolean expressible = Stream.concat(methods.stream(), constructors.stream())
                .allMatch(inherited -> inherited.problems().isEmpty());
        return expressible
                ? new Heritage.Told(
                        methods.stream().map(Inherited::member).toList(),
                        constructors.stream().map(Inherited::member).toList(),
                        named(lineage))
                : Heritage.UNTOLD;
    }

    /// The fields and member types of a type and its supertypes that are not `private`: each once, by
    /// name and then by the type that declares it.
    private List<Heritage.Named> named(List<TypeElement> lineage) {
        return lineage.stream()
                .flatMap(type -> type.getEnclosedElements().stream()
                        .filter(member -> !member.getModifiers().contains(Modifier.PRIVATE))
                        .<Heritage.Named>flatMap(member -> {
                            String name = member.getSimpleName().toString();
                            return switch (member.getKind()) {
                                case FIELD, ENUM_CONSTANT ->
                                    Stream.of(new Heritage.Named.Field(name, desc(type), visibility(member)));
                                case CLASS, INTERFACE, ENUM, RECORD, ANNOTATION_TYPE ->
                                    Stream.of(new Heritage.Named.MemberType(name, desc(type), visibility(member)));
                                default -> Stream.empty();
                            };
                        }))
                .sorted(Comparator.comparing(Heritage.Named::name)
                        .thenComparing(named -> named.declaredBy().descriptorString())
                        .thenComparing(named -> named.getClass().getSimpleName()))
                .toList();
    }

    /// Whether `other` overrides `method`, or implements it, for a type that has both among the methods
    /// of its supertypes (JLS 8.4.8.1): as the class that declares `other` has the two, or — the method
    /// of a superclass that implements one of an interface its class does not implement — as the type
    /// does. The latter `Elements.overrides` tells of any method with a body and any abstract one of
    /// its signature, so not of a method that `method` itself overrides: an abstract method a class
    /// declares again is not implemented by the one it overrides.
    private boolean overrides(ExecutableElement other, ExecutableElement method, TypeElement type) {
        return !other.equals(method)
                && (overridesDeclared(other, method)
                        || !overridesDeclared(method, other) && elements.overrides(other, method, type));
    }

    /// Whether `other` overrides `method` as the class or interface that declares `other` has the two.
    private boolean overridesDeclared(ExecutableElement other, ExecutableElement method) {
        TypeElement declarer = (TypeElement) other.getEnclosingElement();
        return elements.overrides(other, method, declarer) || overridesInPackage(other, declarer, method);
    }

    /// Whether `other`, a method of the class `declarer`, overrides `method`, one with package access of
    /// a superclass in its package — whatever classes of other packages are between the two, through
    /// which `declarer` does not inherit `method`, so that `Elements.overrides` denies it.
    private boolean overridesInPackage(ExecutableElement other, TypeElement declarer, ExecutableElement method) {
        TypeElement overridden = (TypeElement) method.getEnclosingElement();
        Set<Modifier> modifiers = method.getModifiers();
        if (visibility(method) != Heritage.Visibility.PACKAGE
                || overridden.getKind().isInterface()
                || declarer.getKind().isInterface()
                || modifiers.contains(Modifier.STATIC)
                || other.getModifiers().contains(Modifier.STATIC)
                || declarer.equals(overridden)
                || !elements.getPackageOf(declarer).equals(elements.getPackageOf(overridden))
                || !types.isSubtype(types.erasure(declarer.asType()), types.erasure(overridden.asType()))) {
            return false;
        }
        DeclaredType in = (DeclaredType) declarer.asType();
        return types.isSubsignature(
                (ExecutableType) types.asMemberOf(in, other), (ExecutableType) types.asMemberOf(in, method));
    }

    /// A member of a heritage as it is read, and what of its signature cannot be expressed.
    private record Inherited<M>(M member, List<String> problems) {}

    /// The methods a type has under one signature.
    ///
    /// @param standing the methods of the signature that none overrides or hides
    /// @param namesakes every method of the name among the supertypes of the type
    /// @param lineage the type and its supertypes, the nearer first
    private Inherited<Heritage.Method<Heritage.Declaration>> inherited(
            TypeElement element,
            DeclaredType self,
            MethodTableTemplate.Signature signature,
            List<ExecutableElement> standing,
            List<ExecutableElement> namesakes,
            List<TypeElement> lineage) {
        // the one whose result suits the others, among those a class of any package inherits
        List<ExecutableElement> reached = standing.stream()
                .filter(method -> method.getEnclosingElement().getKind().isInterface()
                        || visibility(method) != Heritage.Visibility.PACKAGE)
                .toList();
        Optional<ExecutableElement> suiting = reached.isEmpty() ? Optional.empty() : Optional.of(pick(reached, self));
        List<ExecutableElement> ordered = standing.stream()
                .sorted(Comparator.<ExecutableElement, Boolean>comparing(method -> !reached.contains(method))
                        .thenComparing(method -> !suiting.equals(Optional.of(method)))
                        .thenComparing(
                                method -> method.getEnclosingElement().getKind().isInterface())
                        .thenComparing(method -> lineage.indexOf((TypeElement) method.getEnclosingElement())))
                .toList();
        List<ExecutableElement> overridden = namesakes.stream()
                .filter(other -> !standing.contains(other)
                        && standing.stream().anyMatch(method -> overrides(method, other, element)))
                .toList();
        Reading reading = new Reading(VarScope.of(element));
        List<Heritage.Declaration> declarations = ordered.stream()
                .map(method -> {
                    ExecutableType type = (ExecutableType) types.asMemberOf(self, method);
                    Positions positions = new Positions(element, type);
                    TypeMirror returnType = type.getReturnType();
                    return new Heritage.Declaration(
                            declarer(method),
                            arity(method),
                            variables(positions, reading),
                            told(type.getParameterTypes(), positions, reading),
                            returnType.getKind() == TypeKind.VOID
                                    ? Heritage.Result.nothing()
                                    : new Heritage.Result.Of<>(told(returnType, positions, reading)),
                            told(type.getThrownTypes(), positions, reading));
                })
                .toList();
        return new Inherited<>(
                new Heritage.Method<>(
                        signature,
                        declarations,
                        Stream.concat(standing.stream(), overridden.stream())
                                .map(this::erasedParameters)
                                .collect(Collectors.toUnmodifiableSet()),
                        overridden.stream()
                                .map(other -> elements.getBinaryName((TypeElement) other.getEnclosingElement())
                                        .toString())
                                .distinct()
                                .sorted()
                                .map(ClassDesc::of)
                                .toList()),
                List.copyOf(reading.problems));
    }

    private Inherited<Heritage.Constructor> constructorOf(
            TypeElement element, DeclaredType self, ExecutableElement constructor) {
        Reading reading = new Reading(VarScope.of(element));
        ExecutableType type = (ExecutableType) types.asMemberOf(self, constructor);
        Positions positions = new Positions(element, type);
        return new Inherited<>(
                new Heritage.Constructor(
                        visibility(constructor),
                        new MethodTableTemplate.Signature(
                                desc(element).displayName(), declared(constructor, element, self)),
                        arity(constructor),
                        variables(positions, reading),
                        told(type.getParameterTypes(), positions, reading),
                        told(type.getThrownTypes(), positions, reading)),
                List.copyOf(reading.problems));
    }

    /// The type parameters a signature of a heritage may mention, each known by its position: those of
    /// the type the heritage is of, and those of the member as the type has it.
    ///
    /// @param type the type the heritage is of
    /// @param member the method or constructor as a member of `type`
    private record Positions(TypeElement type, ExecutableType member) {

        Optional<Heritage.Type> of(TypeVariable variable) {
            int own = IntStream.range(0, member.getTypeVariables().size())
                    .filter(i -> member.getTypeVariables().get(i).equals(variable)
                            || member.getTypeVariables().get(i).asElement().equals(variable.asElement()))
                    .findFirst()
                    .orElse(-1);
            if (own >= 0) {
                return Optional.of(new Heritage.Type.OfMember(own));
            }
            int ofType = type.getTypeParameters().indexOf(variable.asElement());
            return ofType >= 0 ? Optional.of(new Heritage.Type.OfType(ofType)) : Optional.empty();
        }
    }

    private List<Heritage.Variable> variables(Positions positions, Reading reading) {
        return positions.member().getTypeVariables().stream()
                .map(variable -> {
                    TypeMirror upper = variable.getUpperBound();
                    List<? extends TypeMirror> parts = upper.getKind() == TypeKind.INTERSECTION
                            ? ((IntersectionType) upper).getBounds()
                            : List.of(upper);
                    List<Heritage.Type> bounds = told(parts, positions, reading);
                    return new Heritage.Variable(
                            bounds.equals(List.of(new Heritage.Type.Fixed(Types.OBJECT))) ? List.of() : bounds);
                })
                .toList();
    }

    private List<Heritage.Type> told(List<? extends TypeMirror> mirrors, Positions positions, Reading reading) {
        return mirrors.stream().map(mirror -> told(mirror, positions, reading)).toList();
    }

    /// A type of a signature of a heritage: a type variable by its position, the rest as [#type] reads it.
    private Heritage.Type told(TypeMirror mirror, Positions positions, Reading reading) {
        return switch (mirror.getKind()) {
            case ARRAY -> Heritage.Type.array(told(((ArrayType) mirror).getComponentType(), positions, reading));
            case TYPEVAR ->
                positions
                        .of((TypeVariable) mirror)
                        .orElseGet(() -> new Heritage.Type.Fixed(
                                reading.unrepresentable("type variable " + mirror + " is out of scope")));
            case DECLARED -> {
                DeclaredType declared = (DeclaredType) mirror;
                // the class itself, with what reading it finds, apart from its type arguments
                ClassOrInterfaceTypeRef raw = declared((DeclaredType) types.erasure(declared), reading);
                List<? extends TypeMirror> arguments = declared.getTypeArguments();
                boolean outerArguments = Stream.iterate(
                                declared.getEnclosingType(),
                                outer -> outer.getKind() == TypeKind.DECLARED,
                                outer -> ((DeclaredType) outer).getEnclosingType())
                        .anyMatch(outer ->
                                !((DeclaredType) outer).getTypeArguments().isEmpty());
                yield arguments.isEmpty() || outerArguments || !(raw instanceof ClassTypeRef(ClassDesc desc, var _))
                        ? new Heritage.Type.Fixed(declared(declared, reading))
                        : Heritage.Type.parameterized(
                                desc,
                                arguments.stream()
                                        .map(argument -> toldArgument(argument, positions, reading))
                                        .toList());
            }
            default -> new Heritage.Type.Fixed(type(mirror, reading));
        };
    }

    private Heritage.Argument toldArgument(TypeMirror argument, Positions positions, Reading reading) {
        if (argument.getKind() != TypeKind.WILDCARD) {
            return new Heritage.Argument.Exact(told(argument, positions, reading));
        }
        WildcardType wildcard = (WildcardType) argument;
        TypeMirror upper = wildcard.getExtendsBound();
        if (upper != null) {
            Heritage.Type bound = told(upper, positions, reading);
            return bound.equals(new Heritage.Type.Fixed(Types.OBJECT))
                    ? new Heritage.Argument.Unbounded()
                    : new Heritage.Argument.Extends(bound);
        }
        TypeMirror lower = wildcard.getSuperBound();
        return lower != null
                ? new Heritage.Argument.Super(told(lower, positions, reading))
                : new Heritage.Argument.Unbounded();
    }

    /// The erasures of the parameters of a method as it is declared, whatever type it is a member of.
    private List<ClassDesc> erasedParameters(ExecutableElement method) {
        return method.getParameters().stream()
                .map(parameter -> erasure(types.erasure(parameter.asType())))
                .toList();
    }

    private static Heritage.Visibility visibility(Element member) {
        Set<Modifier> modifiers = member.getModifiers();
        if (modifiers.contains(Modifier.PUBLIC)) {
            return Heritage.Visibility.PUBLIC;
        }
        return modifiers.contains(Modifier.PROTECTED) ? Heritage.Visibility.PROTECTED : Heritage.Visibility.PACKAGE;
    }

    private static Heritage.Arity arity(ExecutableElement member) {
        return member.isVarArgs() ? Heritage.Arity.VARIABLE : Heritage.Arity.FIXED;
    }

    private Heritage.Declarer declarer(ExecutableElement method) {
        TypeElement type = (TypeElement) method.getEnclosingElement();
        Set<Modifier> modifiers = method.getModifiers();
        if (type.getKind().isInterface()) {
            return new Heritage.Declarer.OfInterface(
                    desc(type),
                    modifiers.contains(Modifier.DEFAULT)
                            ? Heritage.InterfaceMethod.DEFAULT
                            : Heritage.InterfaceMethod.ABSTRACT);
        }
        Heritage.ClassMethod what = modifiers.contains(Modifier.STATIC)
                ? Heritage.ClassMethod.STATIC
                : modifiers.contains(Modifier.ABSTRACT)
                        ? Heritage.ClassMethod.ABSTRACT
                        : modifiers.contains(Modifier.FINAL)
                                ? Heritage.ClassMethod.FINAL
                                : Heritage.ClassMethod.CONCRETE;
        return new Heritage.Declarer.OfClass(desc(type), visibility(method), what);
    }

    /// The signature of a method as a member of a type, in the template of
    /// the method table.
    private MethodTableTemplate.Signature signature(ExecutableElement method, TypeElement owner, DeclaredType self) {
        return new MethodTableTemplate.Signature(method.getSimpleName().toString(), declared(method, owner, self));
    }

    /// The parameters of a method or constructor as a member of a type, in
    /// the template of the method table.
    private List<MethodTableTemplate.Param> declared(ExecutableElement member, TypeElement owner, DeclaredType self) {
        ExecutableType type = (ExecutableType) types.asMemberOf(self, member);
        return type.getParameterTypes().stream().map(p -> param(p, owner)).toList();
    }

    /// The erasure of a parameter in the template of the method table: a
    /// type variable of the type itself, and an array of one, is erased with
    /// the type argument.
    private MethodTableTemplate.Param param(TypeMirror param, TypeElement owner) {
        return variable(param, owner, 0).orElseGet(() -> new MethodTableTemplate.Fixed(erasure(types.erasure(param))));
    }

    /// `type` as a type parameter of `owner` under `dimensions` array
    /// dimensions more than its own; empty if it is not one, nor an array of one.
    private static Optional<MethodTableTemplate.Param> variable(TypeMirror type, TypeElement owner, int dimensions) {
        return switch (type.getKind()) {
            case ARRAY -> variable(((ArrayType) type).getComponentType(), owner, dimensions + 1);
            case TYPEVAR ->
                Optional.of(owner.getTypeParameters().indexOf(((TypeVariable) type).asElement()))
                        .filter(index -> index >= 0)
                        .map(index -> new MethodTableTemplate.Var(index, dimensions));
            default -> Optional.empty();
        };
    }

    private ClassDesc erasure(TypeMirror erased) {
        return switch (erased.getKind()) {
            case BOOLEAN -> ConstantDescs.CD_boolean;
            case BYTE -> ConstantDescs.CD_byte;
            case SHORT -> ConstantDescs.CD_short;
            case INT -> ConstantDescs.CD_int;
            case LONG -> ConstantDescs.CD_long;
            case CHAR -> ConstantDescs.CD_char;
            case FLOAT -> ConstantDescs.CD_float;
            case DOUBLE -> ConstantDescs.CD_double;
            case ARRAY -> erasure(((ArrayType) erased).getComponentType()).arrayType();
            case ERROR -> throw new Unresolved(erased);
            default -> desc((TypeElement) ((DeclaredType) erased).asElement());
        };
    }

    private Read read(TypeElement owner, DeclaredType self, Element member) {
        return switch (member.getKind()) {
            case FIELD -> field(owner, self, (VariableElement) member);
            case CONSTRUCTOR -> constructor(owner, self, (ExecutableElement) member);
            case METHOD -> method(owner, self, (ExecutableElement) member);
            default -> throw new IllegalArgumentException("Not a field, constructor or method: " + member);
        };
    }

    private Read field(TypeElement owner, DeclaredType self, VariableElement field) {
        Reading reading = new Reading(VarScope.of(owner));
        TypeRef type = type(types.asMemberOf(self, field), reading);
        Set<Modifier> modifiers = field.getModifiers();
        boolean isStatic = modifiers.contains(Modifier.STATIC);
        Mutability mutability = !modifiers.contains(Modifier.FINAL)
                ? Mutability.MUTABLE
                : Optional.ofNullable(field.getConstantValue())
                        .filter(_ -> isStatic)
                        .<Mutability>map(Mutability.Constant::new)
                        .orElse(Mutability.FINAL);
        String name = field.getSimpleName().toString();
        return rival(owner, field)
                .<Read>map(rival -> new Read.Skipped(new SkippedMember(
                        "field " + name,
                        "is ambiguous in " + owner.getQualifiedName() + " with field " + name + " of "
                                + ((TypeElement) rival.getEnclosingElement()).getQualifiedName())))
                .orElseGet(() -> Read.of(
                        reading,
                        0,
                        "field " + name,
                        () -> new FieldModel(name, isStatic, type, mutability, accessOf(field))));
    }

    /// Another field of a type under the name of a field of it, which the
    /// field does not hide: the type inherits both — a constant of an
    /// interface beside a field of the superclass —, so javac takes the
    /// name for neither (JLS 8.3.3.3), and nor can a fact, which is read
    /// through the type.
    private Optional<VariableElement> rival(TypeElement owner, VariableElement field) {
        return ElementFilter.fieldsIn(elements.getAllMembers(owner)).stream()
                .filter(other -> !other.equals(field)
                        && other.getSimpleName().contentEquals(field.getSimpleName())
                        && !elements.hides(field, other))
                .findFirst();
    }

    private Read constructor(TypeElement owner, DeclaredType self, ExecutableElement constructor) {
        Reading reading = new Reading(VarScope.of(constructor));
        ExecutableType type = (ExecutableType) types.asMemberOf(self, constructor);
        List<TypeParam> typeParams = typeParams(type, reading);
        List<TypeRef> params = params(type, reading);
        List<ClassOrInterfaceTypeRef> throwsTypes = throwsTypes(type, reading);
        return Read.of(
                reading,
                params.size(),
                "constructor " + constructor,
                () -> new CtorModel(
                        typeParams, params, declared(constructor, owner, self), throwsTypes, accessOf(constructor)));
    }

    private Read method(TypeElement owner, DeclaredType self, ExecutableElement method) {
        List<? extends TypeMirror> thrown = ((ExecutableType) types.asMemberOf(self, method)).getThrownTypes();
        return method(owner, self, method, thrown);
    }

    /// A method that throws `thrown`, whatever its own `throws` clause says.
    private Read method(
            TypeElement owner, DeclaredType self, ExecutableElement method, List<? extends TypeMirror> thrown) {
        Reading reading = new Reading(VarScope.of(owner, method));
        ExecutableType type = (ExecutableType) types.asMemberOf(self, method);
        List<TypeParam> typeParams = typeParams(type, reading);
        TypeMirror returnType = type.getReturnType();
        Optional<TypeRef> result =
                returnType.getKind() == TypeKind.VOID ? Optional.empty() : Optional.of(type(returnType, reading));
        List<TypeRef> params = params(type, reading);
        List<ClassOrInterfaceTypeRef> throwsTypes =
                thrown.stream().map(t -> reference(t, reading)).toList();
        Set<Modifier> modifiers = method.getModifiers();
        boolean isStatic = modifiers.contains(Modifier.STATIC);
        boolean isFinal = isStatic
                || modifiers.contains(Modifier.FINAL)
                || owner.getModifiers().contains(Modifier.FINAL);
        Overridability overridability = isFinal
                ? Overridability.FINAL
                : modifiers.contains(Modifier.ABSTRACT) ? Overridability.ABSTRACT : Overridability.OVERRIDABLE;
        String name = method.getSimpleName().toString();
        return Read.of(
                reading,
                params.size(),
                "method " + method,
                () -> new MethodModel(
                        name,
                        isStatic,
                        typeParams,
                        result,
                        params,
                        declared(method, owner, self),
                        throwsTypes,
                        overridability,
                        accessOf(method)));
    }

    /// The access of a member that is read: only an accessible one is ([#access]), so one that is not
    /// `protected` is `public`.
    private static Access accessOf(Element member) {
        return member.getModifiers().contains(Modifier.PROTECTED) ? Access.PROTECTED : Access.PUBLIC;
    }

    private List<TypeParam> typeParams(ExecutableType type, Reading reading) {
        return type.getTypeVariables().stream()
                .map(variable -> typeParam(variable, reading))
                .toList();
    }

    private List<TypeRef> params(ExecutableType type, Reading reading) {
        return type.getParameterTypes().stream().map(p -> type(p, reading)).toList();
    }

    private List<ClassOrInterfaceTypeRef> throwsTypes(ExecutableType type, Reading reading) {
        return type.getThrownTypes().stream().map(t -> reference(t, reading)).toList();
    }

    private TypeParam typeParam(TypeVariable variable, Reading reading) {
        TypeMirror upper = variable.getUpperBound();
        List<? extends TypeMirror> parts =
                upper.getKind() == TypeKind.INTERSECTION ? ((IntersectionType) upper).getBounds() : List.of(upper);
        List<ClassOrInterfaceTypeRef> bounds =
                parts.stream().map(b -> reference(b, reading)).toList();
        return new TypeParam(
                variable.asElement().getSimpleName().toString(),
                bounds.equals(List.of(Types.OBJECT)) ? List.of() : bounds);
    }

    private TypeRef type(TypeMirror mirror, Reading reading) {
        return switch (mirror.getKind()) {
            case BOOLEAN -> PrimitiveTypeRef.BOOLEAN;
            case BYTE -> PrimitiveTypeRef.BYTE;
            case SHORT -> PrimitiveTypeRef.SHORT;
            case INT -> PrimitiveTypeRef.INT;
            case LONG -> PrimitiveTypeRef.LONG;
            case CHAR -> PrimitiveTypeRef.CHAR;
            case FLOAT -> PrimitiveTypeRef.FLOAT;
            case DOUBLE -> PrimitiveTypeRef.DOUBLE;
            case ARRAY -> new ArrayTypeRef(type(((ArrayType) mirror).getComponentType(), reading));
            default -> reference(mirror, reading);
        };
    }

    private ClassOrInterfaceTypeRef reference(TypeMirror mirror, Reading reading) {
        return switch (mirror.getKind()) {
            case DECLARED -> declared((DeclaredType) mirror, reading);
            case TYPEVAR -> variable((TypeVariable) mirror, reading);
            case ERROR -> throw new Unresolved(mirror);
            case WILDCARD -> reading.unrepresentable("wildcard " + mirror + " outside type arguments");
            case INTERSECTION ->
                reading.unrepresentable("intersection type " + mirror + " outside the bounds of a type parameter");
            default -> reading.unrepresentable(mirror.getKind() + " " + mirror + " is not a type of a value");
        };
    }

    private ClassOrInterfaceTypeRef declared(DeclaredType type, Reading reading) {
        TypeElement element = (TypeElement) type.asElement();
        if (!isPublic(element)) {
            reading.nonPublic.add(binaryName(type));
        }
        List<TypeArg> args =
                type.getTypeArguments().stream().map(a -> argument(a, reading)).toList();
        Optional<TypeMirror> parameterizedOuter = Stream.iterate(
                        type.getEnclosingType(),
                        outer -> outer.getKind() == TypeKind.DECLARED,
                        outer -> ((DeclaredType) outer).getEnclosingType())
                .filter(outer -> !((DeclaredType) outer).getTypeArguments().isEmpty())
                .findFirst();
        if (parameterizedOuter.isPresent()) {
            return reading.unrepresentable("member class " + element.getQualifiedName() + " of parameterized type "
                    + parameterizedOuter.get());
        }
        ClassDesc desc = desc(element);
        return args.isEmpty() ? new ClassTypeRef(desc) : new ParameterizedTypeRef(desc, args);
    }

    private TypeArg argument(TypeMirror argument, Reading reading) {
        if (argument.getKind() != TypeKind.WILDCARD) {
            return Types.exact(type(argument, reading));
        }
        WildcardType wildcard = (WildcardType) argument;
        TypeMirror upper = wildcard.getExtendsBound();
        if (upper != null) {
            TypeRef bound = type(upper, reading);
            return bound.equals(Types.OBJECT) ? Types.unbounded() : Types.extendsBound(bound);
        }
        TypeMirror lower = wildcard.getSuperBound();
        return lower != null ? Types.superBound(type(lower, reading)) : Types.unbounded();
    }

    private ClassOrInterfaceTypeRef variable(TypeVariable variable, Reading reading) {
        TypeParameterElement element = (TypeParameterElement) variable.asElement();
        if (!reading.scope.declares(element)) {
            return reading.unrepresentable("type variable " + variable + " of "
                    + element.getEnclosingElement().getSimpleName() + " is out of scope");
        }
        return new TypeVarRef(element.getSimpleName().toString());
    }

    /// Whether a type and every type that encloses it are `public`: only
    /// such a type can have a metamodel, which names it from another package.
    ///
    /// @param element a class, interface, enum, record or annotation interface
    /// @return `true` if code of any package can name the type
    public static boolean isPublic(TypeElement element) {
        return enclosing(element).allMatch(type -> type.getModifiers().contains(Modifier.PUBLIC));
    }

    private ClassDesc desc(TypeElement element) {
        return ClassDesc.of(elements.getBinaryName(element).toString());
    }

    private String binaryName(DeclaredType type) {
        return elements.getBinaryName((TypeElement) type.asElement()).toString();
    }

    private static void requireResolved(TypeMirror type) {
        if (type.getKind() == TypeKind.ERROR) {
            throw new Unresolved(type);
        }
    }

    /// What reading a member found: its model, or why it has none.
    private sealed interface Read {
        /// The model of a member, or the reason it is skipped, by what its reading found.
        static Read of(Reading reading, int arity, String member, Supplier<MemberModel> model) {
            return reading.skipReason(arity)
                    .<Read>map(reason -> new Skipped(new SkippedMember(member, reason)))
                    .orElseGet(() -> new Made(model.get()));
        }

        Stream<MemberModel> models();

        Stream<SkippedMember> skips();

        record Made(MemberModel model) implements Read {
            @Override
            public Stream<MemberModel> models() {
                return Stream.of(model);
            }

            @Override
            public Stream<SkippedMember> skips() {
                return Stream.empty();
            }
        }

        record Skipped(SkippedMember skipped) implements Read {
            @Override
            public Stream<MemberModel> models() {
                return Stream.empty();
            }

            @Override
            public Stream<SkippedMember> skips() {
                return Stream.of(skipped);
            }
        }
    }

    /// The part of a method table a method is in.
    private enum Category {
        STATIC,
        CONCRETE,
        ABSTRACT;

        /// The part of the methods `Elements.getAllMembers` lists under one signature (JLS 8.4.8,
        /// 8.4.8.4): the method of a class stands, whatever it is — an abstract one beside a default
        /// method of an interface leaves the method abstract, one with a body implements an abstract
        /// method of an interface the class that declares it does not implement. Among the methods of
        /// interfaces alone an abstract one stands.
        static Category of(List<ExecutableElement> twins) {
            List<Set<Modifier>> ofClasses = twins.stream()
                    .filter(twin -> !twin.getEnclosingElement().getKind().isInterface())
                    .map(Element::getModifiers)
                    .toList();
            List<Set<Modifier>> telling = ofClasses.isEmpty()
                    ? twins.stream().map(Element::getModifiers).toList()
                    : ofClasses;
            if (telling.stream().anyMatch(modifiers -> modifiers.contains(Modifier.STATIC))) {
                return STATIC;
            }
            return telling.stream().anyMatch(modifiers -> modifiers.contains(Modifier.ABSTRACT)) ? ABSTRACT : CONCRETE;
        }
    }

    /// What reading a signature found besides its types: the types that
    /// are not `public` and what cannot be expressed. An unrepresentable
    /// part reads as `Object`, so the rest is still read: a type not
    /// generated yet later in the signature defers it.
    private static final class Reading {
        private final VarScope scope;
        private final List<String> problems = new ArrayList<>();
        private final SortedSet<String> nonPublic = new TreeSet<>();

        Reading(VarScope scope) {
            this.scope = scope;
        }

        ClassTypeRef unrepresentable(String problem) {
            problems.add(problem);
            return Types.OBJECT;
        }

        String problem() {
            return String.join("; ", problems);
        }

        Optional<String> skipReason(int arity) {
            if (!problems.isEmpty()) {
                return Optional.of(problem());
            }
            if (!nonPublic.isEmpty()) {
                return Optional.of("mentions types that are not public: " + String.join(", ", nonPublic));
            }
            if (arity > MemberModel.MAX_ARITY) {
                return Optional.of("has " + arity + " parameters, more than " + MemberModel.MAX_ARITY);
            }
            return Optional.empty();
        }
    }

    /// A mention of a type not generated yet: defers the whole translation.
    private static final class Unresolved extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1L;

        private final String type;

        Unresolved(TypeMirror type) {
            this(type.toString());
        }

        Unresolved(String type) {
            super(type, null, false, false);
            this.type = type;
        }
    }
}
