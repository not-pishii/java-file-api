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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
    /// constants, `sealed` — and the members `filter` selects, as members
    /// of the type (`Types.asMemberOf`). The members are those of `filter`
    /// only; the method table is complete whatever the filter.
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

    /// Translates one member of a type, as [#type] does for each member its
    /// filter selects: as a member of the type (`Types.asMemberOf`), whatever its
    /// access. It lets a caller tell which element a [MemberModel] is of.
    ///
    /// @param owner the class, interface, enum or record
    /// @param member a field, constructor or method of `owner`
    /// @return the member, or the reason it has no model, as [SkippedMember#reason()] tells it; deferred
    ///         if its signature mentions a type not generated yet
    /// @throws IllegalArgumentException if `member` is not a field, constructor or method
    public Translation<MemberModel> member(TypeElement owner, Element member) {
        DeclaredType self = (DeclaredType) owner.asType();
        List<MemberModel> members = new ArrayList<>();
        List<SkippedMember> skipped = new ArrayList<>();
        try {
            switch (member.getKind()) {
                case FIELD -> field(owner, self, (VariableElement) member, members, skipped);
                case CONSTRUCTOR -> constructor(self, (ExecutableElement) member, members, skipped);
                case METHOD -> method(owner, self, (ExecutableElement) member, members, skipped);
                default -> throw new IllegalArgumentException("Not a field, constructor or method: " + member);
            }
        } catch (Unresolved unresolved) {
            return new Translation.Deferred<>(unresolved.type);
        }
        return members.isEmpty()
                ? new Translation.Unrepresentable<>(skipped.getFirst().reason())
                : new Translation.Ok<>(members.getFirst());
    }

    /// The single abstract method of a functional interface (JLS 9.8), as a
    /// member of the interface: the one abstract method left once the
    /// `public` methods of `Object` are set aside, declared by the interface
    /// or inherited from a superinterface.
    ///
    /// Empty for what is not a functional interface, for one whose method is
    /// generic, which a lambda cannot implement, and for one that declares
    /// its method but has no model of it, which [#type] reports as a
    /// [SkippedMember].
    ///
    /// @param element the type
    /// @return the method; empty if there is none to make a `sam` fact of; unrepresentable if
    ///         the method is inherited and its signature has no model, for the reason [#member] gives;
    ///         deferred if it mentions a type not generated yet
    public Translation<Optional<SamModel>> sam(TypeElement element) {
        if (element.getKind() != ElementKind.INTERFACE) {
            return new Translation.Ok<>(Optional.empty());
        }
        try {
            DeclaredType self = (DeclaredType) element.asType();
            Set<MethodTableTemplate.Signature> abstracts =
                    methods(element, self).abstractMethods();
            if (abstracts.size() != 1) {
                return new Translation.Ok<>(Optional.empty());
            }
            MethodTableTemplate.Signature signature = abstracts.iterator().next();
            List<ExecutableElement> candidates = ElementFilter.methodsIn(elements.getAllMembers(element)).stream()
                    .filter(m -> m.getModifiers().contains(Modifier.ABSTRACT))
                    .filter(m -> signature(m, element, self).equals(signature))
                    .toList();
            ExecutableElement method = mostSpecific(candidates, self);
            boolean declared = method.getEnclosingElement().equals(element);
            if (!method.getTypeParameters().isEmpty()) {
                return new Translation.Ok<>(Optional.empty());
            }
            return switch (member(element, method)) {
                case Translation.Ok<MemberModel>(MemberModel model) ->
                    new Translation.Ok<>(Optional.of(new SamModel((MethodModel) model, declared)));
                case Translation.Deferred<MemberModel>(String unresolved) -> new Translation.Deferred<>(unresolved);
                case Translation.Unrepresentable<MemberModel>(String reason) ->
                    declared ? new Translation.Ok<>(Optional.empty()) : new Translation.Unrepresentable<>(reason);
            };
        } catch (Unresolved unresolved) {
            return new Translation.Deferred<>(unresolved.type);
        }
    }

    /// The method whose result is a subtype of the results of the others, as
    /// members of the type. Override-equivalent methods of supertypes have
    /// results that are subtypes of one another, so there is one.
    private ExecutableElement mostSpecific(List<ExecutableElement> candidates, DeclaredType self) {
        return candidates.stream()
                .filter(candidate -> {
                    TypeMirror result = ((ExecutableType) types.asMemberOf(self, candidate)).getReturnType();
                    return candidates.stream().allMatch(other -> {
                        TypeMirror otherResult = ((ExecutableType) types.asMemberOf(self, other)).getReturnType();
                        return result.getKind().isPrimitive() || result.getKind() == TypeKind.VOID
                                ? result.getKind() == otherResult.getKind()
                                : types.isSubtype(types.erasure(result), types.erasure(otherResult));
                    });
                })
                .findFirst()
                .orElseGet(candidates::getFirst);
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
        List<MemberModel> members = new ArrayList<>();
        List<SkippedMember> skipped = new ArrayList<>();
        if (filter == MemberFilter.DECLARED_PUBLIC) {
            members(element, self, members, skipped);
        }
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
                filter,
                members,
                skipped));
    }

    private static Optional<TypeElement> genericOuter(TypeElement element) {
        TypeElement type = element;
        while (type.getNestingKind() == NestingKind.MEMBER
                && !type.getModifiers().contains(Modifier.STATIC)) {
            type = (TypeElement) type.getEnclosingElement();
            if (!type.getTypeParameters().isEmpty()) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
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
        List<ClassDesc> chain = new ArrayList<>();
        TypeMirror superclass = element.getSuperclass();
        while (superclass.getKind() != TypeKind.NONE) {
            requireResolved(superclass);
            TypeElement type = (TypeElement) ((DeclaredType) superclass).asElement();
            chain.add(desc(type));
            superclass = type.getSuperclass();
        }
        return chain;
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
        Map<MethodTableTemplate.Signature, Category> table = new HashMap<>();
        for (ExecutableElement method : ElementFilter.methodsIn(elements.getAllMembers(element))) {
            Set<Modifier> modifiers = method.getModifiers();
            if (modifiers.contains(Modifier.PRIVATE)) {
                continue;
            }
            MethodTableTemplate.Signature signature = signature(method, element, self);
            Category category = modifiers.contains(Modifier.STATIC)
                    ? Category.STATIC
                    : modifiers.contains(Modifier.ABSTRACT) ? Category.ABSTRACT : Category.CONCRETE;
            table.merge(signature, category, Category::stronger);
        }
        if (element.getKind() == ElementKind.INTERFACE) {
            // an interface that redeclares a public method of Object abstract does not ask its
            // implementations for it: every class has it from Object
            for (MethodTableTemplate.Signature signature : OBJECT_METHODS) {
                table.computeIfPresent(
                        signature, (key, category) -> category == Category.ABSTRACT ? Category.CONCRETE : category);
            }
        }
        return new MethodTableTemplate(
                signatures(table, Category.ABSTRACT),
                signatures(table, Category.CONCRETE),
                signatures(table, Category.STATIC));
    }

    /// The signature of a method as a member of a type, in the template of
    /// the method table.
    private MethodTableTemplate.Signature signature(ExecutableElement method, TypeElement owner, DeclaredType self) {
        ExecutableType type = (ExecutableType) types.asMemberOf(self, method);
        return new MethodTableTemplate.Signature(
                method.getSimpleName().toString(),
                type.getParameterTypes().stream().map(p -> param(p, owner)).toList());
    }

    private static Set<MethodTableTemplate.Signature> signatures(
            Map<MethodTableTemplate.Signature, Category> table, Category category) {
        return table.entrySet().stream()
                .filter(e -> e.getValue() == category)
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableSet());
    }

    /// The erasure of a parameter in the template of the method table: a
    /// type variable of the type itself is erased with the type argument.
    private MethodTableTemplate.Param param(TypeMirror param, TypeElement owner) {
        if (param.getKind() == TypeKind.TYPEVAR) {
            int index = owner.getTypeParameters().indexOf((TypeParameterElement) ((TypeVariable) param).asElement());
            if (index >= 0) {
                return new MethodTableTemplate.Var(index);
            }
        }
        return new MethodTableTemplate.Fixed(erasure(types.erasure(param)));
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

    private void members(
            TypeElement element, DeclaredType self, List<MemberModel> members, List<SkippedMember> skipped) {
        for (Element member : element.getEnclosedElements()) {
            if (!member.getModifiers().contains(Modifier.PUBLIC)) {
                continue;
            }
            switch (member.getKind()) {
                case FIELD -> field(element, self, (VariableElement) member, members, skipped);
                case CONSTRUCTOR -> constructor(self, (ExecutableElement) member, members, skipped);
                case METHOD -> method(element, self, (ExecutableElement) member, members, skipped);
                default -> {
                    // enum constants are TypeModel.enumConstants; member types have models of their own
                }
            }
        }
    }

    private void field(
            TypeElement owner,
            DeclaredType self,
            VariableElement field,
            List<MemberModel> members,
            List<SkippedMember> skipped) {
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
        reading.skipReason(0)
                .ifPresentOrElse(
                        reason -> skipped.add(new SkippedMember("field " + name, reason)),
                        () -> members.add(new FieldModel(name, isStatic, type, mutability)));
    }

    private void constructor(
            DeclaredType self, ExecutableElement constructor, List<MemberModel> members, List<SkippedMember> skipped) {
        Reading reading = new Reading(VarScope.of(constructor));
        ExecutableType type = (ExecutableType) types.asMemberOf(self, constructor);
        List<TypeParam> typeParams = typeParams(type, reading);
        List<TypeRef> params = params(type, reading);
        List<ClassOrInterfaceTypeRef> throwsTypes = throwsTypes(type, reading);
        reading.skipReason(params.size())
                .ifPresentOrElse(
                        reason -> skipped.add(new SkippedMember("constructor " + constructor, reason)),
                        () -> members.add(new CtorModel(typeParams, params, throwsTypes)));
    }

    private void method(
            TypeElement owner,
            DeclaredType self,
            ExecutableElement method,
            List<MemberModel> members,
            List<SkippedMember> skipped) {
        Reading reading = new Reading(VarScope.of(owner, method));
        ExecutableType type = (ExecutableType) types.asMemberOf(self, method);
        List<TypeParam> typeParams = typeParams(type, reading);
        TypeMirror returnType = type.getReturnType();
        Optional<TypeRef> result =
                returnType.getKind() == TypeKind.VOID ? Optional.empty() : Optional.of(type(returnType, reading));
        List<TypeRef> params = params(type, reading);
        List<ClassOrInterfaceTypeRef> throwsTypes = throwsTypes(type, reading);
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
        reading.skipReason(params.size())
                .ifPresentOrElse(
                        reason -> skipped.add(new SkippedMember("method " + method, reason)),
                        () -> members.add(new MethodModel(
                                name, isStatic, typeParams, result, params, throwsTypes, overridability)));
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
        for (TypeMirror outer = type.getEnclosingType();
                outer.getKind() == TypeKind.DECLARED;
                outer = ((DeclaredType) outer).getEnclosingType()) {
            if (!((DeclaredType) outer).getTypeArguments().isEmpty()) {
                return reading.unrepresentable(
                        "member class " + element.getQualifiedName() + " of parameterized type " + outer);
            }
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
        Element type = element;
        while (type instanceof TypeElement) {
            if (!type.getModifiers().contains(Modifier.PUBLIC)) {
                return false;
            }
            type = type.getEnclosingElement();
        }
        return true;
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
