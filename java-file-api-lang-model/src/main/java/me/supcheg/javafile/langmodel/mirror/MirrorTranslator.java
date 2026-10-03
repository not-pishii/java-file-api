package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.DeclaredKind;
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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
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
    /// superclasses, parameterized supertypes, method table, enum
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
    /// facts of ([MemberFilter#DECLARED_PUBLIC]): the `public` ones the type
    /// declares, in declaration order, and then the ones it adopts.
    ///
    /// A type adopts the `public` fields and methods it inherits from a
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
    /// - not a `static` method of an interface, which is not inherited;
    /// - of several abstract methods of the same signature one, that of the
    ///   most specific result, as for a `sam`;
    /// - each once, however many ways lead to the supertype that declares it;
    /// - not one a `public` supertype of the type has adopted, which the
    ///   type reaches through that supertype too — `X extends Mid`, where
    ///   both implement `Hidden`: the members of `Hidden` are told by `Mid`,
    ///   like the members `Mid` declares, and `X` has them from `Mid`. A
    ///   member is told by one type, the farthest that has it.
    ///
    /// Constructors are not inherited, so none is adopted.
    ///
    /// @param element the class, interface, enum or record
    /// @return the members, each a field, constructor or method
    public List<Element> members(TypeElement element) {
        DeclaredType self = (DeclaredType) element.asType();
        return Stream.<Element>concat(
                        element.getEnclosedElements().stream()
                                .filter(member -> member.getModifiers().contains(Modifier.PUBLIC))
                                .filter(member -> switch (member.getKind()) {
                                    // enum constants are TypeModel.enumConstants; member types have models of
                                    // their own
                                    case FIELD, CONSTRUCTOR, METHOD -> true;
                                    default -> false;
                                }),
                        adopted(element, self))
                .toList();
    }

    /// The members a type adopts, see [#members(TypeElement)]: those of the nearer supertype first.
    private Stream<Element> adopted(TypeElement element, DeclaredType self) {
        List<Element> had = had(element);
        if (had.isEmpty()) {
            return Stream.empty();
        }
        // what a public supertype has of them, it tells: the type has them from that supertype
        Set<Element> told = supertypesThrough(List.of(element), Set.of(), supertype -> true)
                .filter(MirrorTranslator::isPublic)
                .flatMap(supertype -> had(supertype).stream())
                .collect(Collectors.toSet());
        // abstract methods of one signature that do not override each other are one member of the type
        Map<MethodTableTemplate.Signature, List<ExecutableElement>> twins = had.stream()
                .filter(member -> member.getKind() == ElementKind.METHOD)
                .map(ExecutableElement.class::cast)
                .collect(Collectors.groupingBy(method -> signature(method, element, self)));
        return had.stream().filter(member -> switch (member) {
            case ExecutableElement method -> {
                List<ExecutableElement> same = twins.get(signature(method, element, self));
                yield same.stream().noneMatch(told::contains)
                        && pick(same, self).equals(method);
            }
            default -> !told.contains(member);
        });
    }

    /// The `public` fields and methods of the supertypes that are not
    /// `public` which a type has through such supertypes alone: those of
    /// the nearer supertype first, and none that a nearer type replaces. A
    /// `public` supertype that has one of them too is not looked at here.
    private List<Element> had(TypeElement element) {
        List<TypeElement> hidden = supertypesThrough(List.of(element), Set.of(), supertype -> !isPublic(supertype))
                .toList();
        if (hidden.isEmpty()) {
            return List.of();
        }
        Map<String, List<Element>> inherited = elements.getAllMembers(element).stream()
                .<Element>map(member -> member)
                .filter(member -> member.getKind() == ElementKind.FIELD || member.getKind() == ElementKind.METHOD)
                .collect(Collectors.groupingBy(member -> member.getSimpleName().toString()));
        return hidden.stream()
                .<Element>flatMap(supertype -> supertype.getEnclosedElements().stream())
                .filter(member -> member.getModifiers().contains(Modifier.PUBLIC))
                .filter(member -> {
                    List<Element> namesakes =
                            inherited.getOrDefault(member.getSimpleName().toString(), List.of());
                    return namesakes.contains(member)
                            && namesakes.stream().noneMatch(other -> replaces(other, member, element));
                })
                .toList();
    }

    private ExecutableElement pick(List<ExecutableElement> twins, DeclaredType self) {
        return twins.size() == 1 ? twins.getFirst() : mostSpecific(twins, self);
    }

    /// The supertypes `nearer` reach through supertypes that are `through`
    /// alone, themselves `through`: each once, the nearer first.
    private static Stream<TypeElement> supertypesThrough(
            List<TypeElement> nearer, Set<TypeElement> seen, Predicate<TypeElement> through) {
        List<TypeElement> next = nearer.stream()
                .flatMap(type -> Stream.concat(Stream.of(type.getSuperclass()), type.getInterfaces().stream()))
                .filter(supertype -> supertype.getKind() == TypeKind.DECLARED)
                .map(supertype -> (TypeElement) ((DeclaredType) supertype).asElement())
                .filter(through)
                .distinct()
                .filter(supertype -> !seen.contains(supertype))
                .toList();
        return next.isEmpty()
                ? Stream.empty()
                : Stream.concat(
                        next.stream(),
                        supertypesThrough(
                                next,
                                Stream.concat(seen.stream(), next.stream()).collect(Collectors.toSet()),
                                through));
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
            return readSam(element);
        } catch (Unresolved unresolved) {
            return new Translation.Deferred<>(unresolved.type);
        }
    }

    private Translation<Optional<SamModel>> readSam(TypeElement element) {
        if (element.getKind() != ElementKind.INTERFACE || element.getModifiers().contains(Modifier.SEALED)) {
            return new Translation.Ok<>(Optional.empty());
        }
        DeclaredType self = (DeclaredType) element.asType();
        Set<MethodTableTemplate.Signature> abstracts = methods(element, self).abstractMethods();
        if (abstracts.size() != 1) {
            return new Translation.Ok<>(Optional.empty());
        }
        MethodTableTemplate.Signature signature = abstracts.iterator().next();
        List<ExecutableElement> candidates = ElementFilter.methodsIn(elements.getAllMembers(element)).stream()
                .filter(m -> m.getModifiers().contains(Modifier.ABSTRACT))
                .filter(m -> signature(m, element, self).equals(signature))
                .toList();
        if (candidates.stream().anyMatch(m -> !m.getTypeParameters().isEmpty())) {
            return new Translation.Ok<>(Optional.empty());
        }
        ExecutableElement method = mostSpecific(candidates, self);
        // declared, or adopted from a superinterface that is not public: a member the type has a fact of
        boolean declared = method.getEnclosingElement().equals(element)
                || adopted(element, self)
                        .anyMatch(member -> member instanceof ExecutableElement adopted
                                && signature(adopted, element, self).equals(signature));
        return switch (method(element, self, method, functionThrows(candidates, self))) {
            case Read.Made(MemberModel model) ->
                new Translation.Ok<>(Optional.of(new SamModel((MethodModel) model, declared)));
            case Read.Skipped(SkippedMember skip) ->
                declared ? new Translation.Ok<>(Optional.empty()) : new Translation.Unrepresentable<>(skip.reason());
        };
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
                        if (otherResult.getKind().isPrimitive() || otherResult.getKind() == TypeKind.VOID) {
                            return false;
                        }
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
        List<TypeMirror> thrown = new ArrayList<>();
        for (List<? extends TypeMirror> clause : clauses) {
            for (TypeMirror exception : clause) {
                boolean everyClauseAllows = clauses.stream()
                        .allMatch(other -> other.stream().anyMatch(allowed -> types.isSubtype(exception, allowed)));
                if (everyClauseAllows && thrown.stream().noneMatch(found -> types.isSameType(found, exception))) {
                    thrown.add(exception);
                }
            }
        }
        return thrown;
    }

    private Translation<TypeModel> model(TypeElement element, MemberFilter filter) {
        DeclaredType self = (DeclaredType) element.asType();
        Reading bounds = new Reading(VarScope.of(element));
        List<TypeParam> typeParams = element.getTypeParameters().stream()
                .map(parameter -> typeParam((TypeVariable) parameter.asType(), bounds))
                .toList();
        Reading shape = new Reading(VarScope.of(element));
        List<ClassDesc> superclasses = superclasses(element);
        Supertypes supertypes = supertypes(self, typeParams, shape);
        MethodTableTemplate methods = methods(element, self);
        List<String> enumConstants = element.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.ENUM_CONSTANT)
                .map(e -> e.getSimpleName().toString())
                .toList();
        List<Read> reads = filter == MemberFilter.DECLARED_PUBLIC
                ? members(element).stream()
                        .map(member -> read(element, self, member))
                        .toList()
                : List.of();
        Optional<MethodModel> sam =
                switch (readSam(element)) {
                    case Translation.Ok<Optional<SamModel>>(Optional<SamModel> found) -> found.map(SamModel::method);
                    case Translation.Deferred<Optional<SamModel>>(String unresolved) ->
                        throw new IllegalStateException("a deferred sam is thrown, not returned: " + unresolved);
                    case Translation.Unrepresentable<Optional<SamModel>> _ -> Optional.empty();
                };
        shape.problems.addAll(bounds.problems);
        if (!shape.problems.isEmpty()) {
            return new Translation.Unrepresentable<>("type " + element.getQualifiedName() + ": " + shape.problem());
        }
        return new Translation.Ok<>(new TypeModel(
                desc(element),
                kind(element),
                typeParams,
                List.copyOf(bounds.nonPublic),
                superclasses,
                supertypes,
                methods,
                enumConstants,
                element.getModifiers().contains(Modifier.SEALED),
                sam,
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

    private Supertypes supertypes(DeclaredType self, List<TypeParam> typeParams, Reading reading) {
        Map<String, ParameterizedTypeRef> found = new TreeMap<>();
        Set<Element> seen = new HashSet<>(Set.of(self.asElement()));
        Deque<DeclaredType> pending = new ArrayDeque<>(List.of(self));
        while (!pending.isEmpty()) {
            DeclaredType type = pending.removeFirst();
            // directSupertypes leaves out an interface not generated yet
            ((TypeElement) type.asElement()).getInterfaces().forEach(MirrorTranslator::requireResolved);
            for (TypeMirror supertype : types.directSupertypes(type)) {
                DeclaredType declared = (DeclaredType) supertype;
                if (seen.add(declared.asElement())) {
                    if (declared(declared, reading) instanceof ParameterizedTypeRef parameterized) {
                        found.put(binaryName(declared), parameterized);
                    }
                    pending.add(declared);
                }
            }
        }
        return new Supertypes(
                typeParams.stream().map(p -> new TypeVarRef(p.name())).toList(), List.copyOf(found.values()));
    }

    private MethodTableTemplate methods(TypeElement element, DeclaredType self) {
        Map<MethodTableTemplate.Signature, Category> table =
                ElementFilter.methodsIn(elements.getAllMembers(element)).stream()
                        .filter(method -> !method.getModifiers().contains(Modifier.PRIVATE))
                        .collect(Collectors.toMap(
                                method -> signature(method, element, self),
                                method -> category(method.getModifiers()),
                                Category::stronger));
        boolean isInterface = element.getKind() == ElementKind.INTERFACE;
        Map<Category, Set<MethodTableTemplate.Signature>> parts = table.entrySet().stream()
                .collect(Collectors.groupingBy(
                        // an interface that redeclares a public method of Object abstract does not ask its
                        // implementations for it: every class has it from Object
                        e -> isInterface && e.getValue() == Category.ABSTRACT && OBJECT_METHODS.contains(e.getKey())
                                ? Category.CONCRETE
                                : e.getValue(),
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

    private static Category category(Set<Modifier> modifiers) {
        return modifiers.contains(Modifier.STATIC)
                ? Category.STATIC
                : modifiers.contains(Modifier.ABSTRACT) ? Category.ABSTRACT : Category.CONCRETE;
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
        Object constant = field.getConstantValue();
        Mutability mutability;
        if (!modifiers.contains(Modifier.FINAL)) {
            mutability = Mutability.MUTABLE;
        } else if (isStatic && constant != null) {
            mutability = new Mutability.Constant(constant);
        } else {
            mutability = Mutability.FINAL;
        }
        String name = field.getSimpleName().toString();
        return Read.of(reading, 0, "field " + name, () -> new FieldModel(name, isStatic, type, mutability));
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
                () -> new CtorModel(typeParams, params, declared(constructor, owner, self), throwsTypes));
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
        Overridability overridability;
        if (isStatic
                || modifiers.contains(Modifier.FINAL)
                || owner.getModifiers().contains(Modifier.FINAL)) {
            overridability = Overridability.FINAL;
        } else if (modifiers.contains(Modifier.ABSTRACT)) {
            overridability = Overridability.ABSTRACT;
        } else {
            overridability = Overridability.OVERRIDABLE;
        }
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
                        overridability));
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

    private static boolean isPublic(TypeElement element) {
        return Stream.<Element>iterate(element, type -> type instanceof TypeElement, Element::getEnclosingElement)
                .allMatch(type -> type.getModifiers().contains(Modifier.PUBLIC));
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

    /// The part of a method table a method is in. Where getAllMembers lists
    /// one signature in two parts, the stronger wins: a static method, or a
    /// concrete one that implements an inherited abstract one.
    private enum Category {
        STATIC,
        CONCRETE,
        ABSTRACT;

        Category stronger(Category other) {
            return compareTo(other) <= 0 ? this : other;
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
            super(type.toString(), null, false, false);
            this.type = type.toString();
        }
    }
}
