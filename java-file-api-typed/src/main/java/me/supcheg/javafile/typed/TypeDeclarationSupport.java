package me.supcheg.javafile.typed;

import me.supcheg.javafile.Invariants;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeVarRef;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The declaration of one type that is not a `final` class (§6.5): an
/// interface, a record or an enum. It holds what the kinds have in common —
/// the type's token, the scope of its members, the signatures it declares,
/// the members declared before any body is built — and leaves to its kinds
/// how the header ends and what a member is rendered as.
///
/// **Declaring is introducing.** A member is declared here, which gives its
/// fact; [TypedTypeBodies] then defines it. The two stages are distinct
/// values, so no member is declared after the first body is built.
///
/// @param <Self> the brand of the type being declared
abstract class TypeDeclarationSupport<Self> {
    private final TargetClasspath target;
    private final ClassDesc desc;
    private final String described;
    private final Inheritance.Heir.OfClass heir;
    private final DeclarationIdentity identity;
    private List<Variable> variables = List.of();
    private Inheritance inherited;
    private @Nullable DeclaredToken<Self> self;
    private @Nullable TypeSignatureRegistry registry;
    private List<Member> members = List.of();
    private List<me.supcheg.javafile.model.TypeDecl> nested = List.of();
    private Optional<me.supcheg.javafile.doc.DocComment> pendingDoc = Optional.empty();
    private boolean membersDeclared;
    private boolean membersBegan;
    private boolean complete;

    /// @param target the classpath the type is compiled against
    /// @param desc the type
    /// @param kind the keyword of the declaration, `interface`, `record` or `enum`
    TypeDeclarationSupport(TargetClasspath target, ClassDesc desc, String kind) {
        this.target = target;
        this.desc = desc;
        this.described =
                kind + " " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName();
        this.heir = new Inheritance.Heir.OfClass(described, desc);
        this.identity = UnsafeFacts.declarationIdentity(described);
        // Every type has the methods of Object: an interface implicitly declares them, a record and an
        // enum inherit them.
        this.inherited = Inheritance.of(heir, Inheritance.supertype(heir, target, Object_.TOKEN));
    }

    /// Gives the type its type parameters. A member type of an interface, a record or an enum is
    /// implicitly `static` (JLS 9.5), so none of them has the type parameters of an enclosing type in
    /// scope: a kind that may have none calls this with an empty list.
    ///
    /// @param parameters the type parameters, in order
    /// @throws IllegalArgumentException if a type parameter has the name of another or of the type, or
    ///     its bound is a raw type
    final void declareParameters(List<TypeParameter<?>> parameters) {
        IntStream.range(0, parameters.size()).forEach(i -> {
            TypeParameter<?> parameter = parameters.get(i);
            if (parameters.subList(0, i).stream()
                    .anyMatch(before -> before.name().equals(parameter.name()))) {
                throw new IllegalArgumentException(described + " has two type parameters " + parameter.name()
                        + ": a type parameter has a name of its own (JLS 6.4)");
            }
            if (parameter.name().equals(desc.displayName())) {
                throw new IllegalArgumentException(described + " cannot have the type parameter " + parameter.name()
                        + ": in the type the name would mean the type parameter, and the type is named there by"
                        + " its simple name");
            }
            DeclaredToken<?> bound = Tokens.requireDeclaredBound(parameter.name(), parameter.bound());
            requireNotRaw(bound, parameter.name());
            scope("the header").require(bound);
            target.verify(bound);
        });
        this.variables = parameters.stream()
                .map(parameter -> {
                    DeclaredToken<?> bound = Tokens.requireDeclaredBound(parameter.name(), parameter.bound());
                    return new Variable(
                            UnsafeFacts.declaredTypeVarToken(
                                    new TypeVarRef(parameter.name()), bound.erasure(), identity),
                            bound,
                            List.of());
                })
                .toList();
    }

    private void requireNotRaw(DeclaredToken<?> bound, String variable) {
        if (bound.typeRef() instanceof ClassTypeRef
                && !bound.shape().typeParameters().isEmpty()) {
            throw new IllegalArgumentException("the type parameter " + variable + " of " + described
                    + " cannot have the raw bound " + bound + ": the type is generic, name it with its type"
                    + " arguments");
        }
    }

    /// The token of a type parameter of the type.
    ///
    /// @param index its index
    /// @return the token
    final TypeVarToken<?> variable(int index) {
        return variables.get(index).token();
    }

    /// The type variables of the type parameters, in order.
    ///
    /// @return the variables, in order
    final List<TypeVarToken<?>> variables() {
        return variables.stream().map(Variable::token).toList();
    }

    /// The type parameters of the type as they are rendered, see [ClassDeclaration#typeParams()].
    ///
    /// @return the type parameters, in order
    final List<TypeParam> typeParams() {
        return variables.stream()
                .map(variable -> new TypeParam(
                        variable.token().typeRef().name(),
                        variable.more().isEmpty()
                                ? leftmost(variable.first())
                                : Stream.concat(Stream.of(variable.first()), variable.more().stream())
                                        .<ClassOrInterfaceTypeRef>map(DeclaredToken::typeRef)
                                        .toList()))
                .toList();
    }

    private static List<ClassOrInterfaceTypeRef> leftmost(DeclaredToken<?> first) {
        return first.erasure().equals(ConstantDescs.CD_Object) ? List.of() : List.of(first.typeRef());
    }

    /// A type parameter of the type.
    ///
    /// @param token its token
    /// @param first its leftmost bound, `Object` for none
    /// @param more its further bounds, said by witnesses in the header, in order
    private record Variable(TypeVarToken<?> token, DeclaredToken<?> first, List<InterfaceToken<?>> more) {
        Variable {
            more = List.copyOf(more);
        }
    }

    /// Makes the token of the type, once its kind knows how to make it.
    ///
    /// @param self the token
    final void self(DeclaredToken<Self> self) {
        this.self = self;
    }

    final TargetClasspath target() {
        return target;
    }

    final ClassDesc desc() {
        return desc;
    }

    final String described() {
        return described;
    }

    final Inheritance inherited() {
        return inherited;
    }

    final DeclaredToken<Self> self() {
        // the constructor of a subclass gives the token before anything else is declared
        return Objects.requireNonNull(self);
    }

    final List<Subtyping.Bounded> bounded() {
        return variables.stream()
                .map(variable -> new Subtyping.Bounded(
                        variable.token(),
                        Stream.concat(Stream.of(variable.first()), variable.more().stream())
                                .toList()))
                .toList();
    }

    final TypeScope scope(String where) {
        return new TypeScope.Instance(identity, where + " of " + described);
    }

    final TypeScope staticScope(String where) {
        return new TypeScope.Static(identity, where + " of " + described);
    }

    /// Whether the type may leave the abstract methods of its supertypes abstract: an interface may.
    boolean allowAbstract() {
        return false;
    }

    // ------------------------------------------------------------------
    // The header
    // ------------------------------------------------------------------

    /// Names a supertype of the type: an interface it extends or implements.
    ///
    /// @param supertype the supertype, with its type arguments
    /// @param <X> the supertype
    /// @return the witness that the type is of the supertype
    /// @throws IllegalArgumentException if the supertype cannot be one, or is named already
    /// @throws IllegalStateException if the members have begun
    final <X> Upcast<Self, X> addSupertype(DeclaredToken<X> supertype) {
        requireHeader("a supertype of " + described);
        scope("a supertype of " + described).require(supertype);
        target.verify(supertype);
        inherited = inherited.with(Inheritance.supertype(heir, target, supertype));
        return new Upcast<>(self(), supertype);
    }

    /// Gives a type parameter of the type one more bound, an interface: `<T extends A & B>`. The
    /// leftmost bound is the one the token `typeParam` was made with; every other is said here,
    /// before the members begin, and gives the witness by which an expression of the type parameter
    /// is one of the interface, as [ClassHeader#bound] does of a class.
    ///
    /// @param variable the token of the type parameter, as the specification was given it
    /// @param bound the interface, with its type arguments
    /// @param <T> the type parameter
    /// @param <B> the interface
    /// @return the witness that the type parameter is of the interface
    /// @throws IllegalArgumentException if `variable` is no type parameter of this type; `bound` is
    ///     raw, has a wildcard type argument or tells no heritage; is a bound of the type parameter
    ///     already, or a generic supertype of two bounds with other type arguments; or the methods of
    ///     the bounds clash
    /// @throws IllegalStateException if the members have begun, or `bound` mentions a type parameter
    ///     of another type
    final <T, B> Upcast<T, B> addBound(TypeVarToken<T> variable, InterfaceToken<B> bound) {
        requireHeader("a bound of a type parameter of " + described);
        requireNoMemberDeclared("a bound of a type parameter of " + described);
        Variable bounded = variables.stream()
                .filter(mine -> mine.token() == variable)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        variable + " is no type parameter of " + described + ": a type bounds its own"));
        String name = variable.typeRef().name();
        requireNotRaw(bound, name);
        scope("the header").require(bound);
        target.verify(bound);
        String owner = "the type parameter " + name + " of " + described;
        // What a type variable of several bounds has is what a class of those supertypes inherits
        // (JLS 4.9): javac makes that class and rejects the type parameter where it would reject the
        // class. A bound twice, two parameterizations of one interface and methods that clash are
        // rejected by the merge; methods the class could not have together without one of its own,
        // which it has none of, here.
        Inheritance.Supertype first;
        try {
            first = Inheritance.supertype(owner, target, bounded.first());
        } catch (IllegalArgumentException untold) {
            throw new IllegalArgumentException(
                    owner + " cannot have the bound " + bound + " beside " + bounded.first()
                            + ": what the two give the type variable together is not known, for "
                            + untold.getMessage(),
                    untold);
        }
        Inheritance merged = Stream.concat(bounded.more().stream(), Stream.of(bound))
                // a sequential stream: its partial results are never combined
                .reduce(
                        Inheritance.of(new Inheritance.Heir.OfVariable(owner), first),
                        (had, next) -> had.with(Inheritance.supertype(owner, target, next)),
                        Invariants::unique);
        merged.groups().flatMap(group -> group.conflict().stream()).findFirst().ifPresent(why -> {
            throw new IllegalArgumentException(owner + " cannot have the bound " + bound
                    + ": a type variable is of the class its bounds make (JLS 4.9), and " + why);
        });
        merged.groups()
                .filter(group -> group.entries().stream()
                        .anyMatch(entry -> !entry.method()
                                .sameResult(group.entries().getFirst().method())))
                .findFirst()
                .ifPresent(group -> {
                    throw new IllegalArgumentException(owner + " cannot have the bound " + bound + ": with it the"
                            + " bounds have the methods "
                            + group.entries().stream().map(Object::toString).collect(Collectors.joining(" and "))
                            + " of one signature and of"
                            + " different results — one has to be return-type-substitutable for the others"
                            + " (JLS 8.4.5, 4.9), which the typed layer holds to: of the same result");
                });
        variables = variables.stream()
                .map(mine -> mine.equals(bounded)
                        ? new Variable(
                                mine.token(),
                                mine.first(),
                                Stream.concat(mine.more().stream(), Stream.of(bound))
                                        .toList())
                        : mine)
                .toList();
        return new Upcast<>(variable, bound);
    }

    final void requireHeader(String what) {
        if (membersBegan) {
            throw new IllegalStateException("cannot declare " + what + ": the members of " + described
                    + " have begun, and a type names its supertypes before them");
        }
    }

    /// Holds a bound of a type parameter to the header: it is said before the first member.
    ///
    /// @param what what is said, for messages
    /// @throws IllegalStateException if a member is declared already
    private void requireNoMemberDeclared(String what) {
        if (membersDeclared) {
            throw new IllegalStateException("cannot declare " + what + ": the members of " + described
                    + " have begun, and a type names its bounds before them");
        }
    }

    // ------------------------------------------------------------------
    // Declarations
    // ------------------------------------------------------------------

    final void requireDefining(String what) {
        if (complete) {
            throw new IllegalStateException(
                    "cannot declare or define " + what + ": the declaration of " + described + " is already complete");
        }
        Scopes.requireNoneOpen(what);
    }

    /// Marks the header of a record as begun by a component: a bound is said before the components
    /// as it is said before the members.
    final void componentDeclared() {
        membersDeclared = true;
    }

    final void requireDeclaring(String what) {
        if (complete) {
            throw new IllegalStateException(
                    "cannot declare or define " + what + ": the declaration of " + described + " is already complete");
        }
        if (membersBegan) {
            throw new IllegalStateException("cannot declare " + what + ": the declarations of " + described
                    + " have ended - bodies() began the bodies of its members");
        }
        Scopes.requireNoneOpen(what);
    }

    /// Holds a documentation comment for the member declared next.
    ///
    /// @param doc the comment
    /// @throws IllegalArgumentException if the text of `doc` contains `*/`
    final void document(me.supcheg.javafile.doc.DocComment doc) {
        pendingDoc = Optional.of(Docs.requireSafe(doc, described));
    }

    /// The documentation comment of the member declared next, which it takes.
    ///
    /// @return the comment, or empty
    final Optional<me.supcheg.javafile.doc.DocComment> takeDoc() {
        Optional<me.supcheg.javafile.doc.DocComment> taken = pendingDoc;
        pendingDoc = Optional.empty();
        return taken;
    }

    private TypeSignatureRegistry registry() {
        if (registry == null) {
            registry = new TypeSignatureRegistry(described, self(), inherited, bounded());
        }
        return registry;
    }

    private TypeScope scope(Member.Kind kind, String where) {
        return switch (kind) {
            case ABSTRACT, DEFAULT, METHOD, PRIVATE, CONSTRUCTOR, CANONICAL, COMPACT, ACCESSOR -> scope(where);
            case STATIC, PRIVATE_STATIC -> staticScope(where);
        };
    }

    /// Declares a method of the type, with the given kind and annotations.
    ///
    /// @param member the fact of the method
    /// @param kind how the method is rendered
    /// @param thrown the exception classes of its `throws` clause
    /// @param annotations the annotations it declares
    /// @param parameterAnnotations the annotations of each parameter
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations have ended
    final <F extends Invocable> F declare(
            F member,
            Member.Kind kind,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations) {
        return declare(member, kind, thrown, annotations, parameterAnnotations, Set.of());
    }

    /// Declares a method of the type, rendered with `modifiers` beside those its kind fixes.
    ///
    /// @param member the fact of the method
    /// @param kind how the method is rendered
    /// @param thrown the exception classes of its `throws` clause
    /// @param annotations the annotations it declares
    /// @param parameterAnnotations the annotations of each parameter
    /// @param modifiers the modifiers it is rendered with, `synchronized` or `native`
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations have ended
    final <F extends Invocable> F declare(
            F member,
            Member.Kind kind,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Set<Modifier> modifiers) {
        return declare(member, kind, thrown, annotations, parameterAnnotations, modifiers, takeDoc());
    }

    /// Declares a method of the type, rendered with `modifiers` beside those its kind fixes and with a
    /// documentation comment.
    ///
    /// @param member the fact of the method
    /// @param kind how the method is rendered
    /// @param thrown the exception classes of its `throws` clause
    /// @param annotations the annotations it declares
    /// @param parameterAnnotations the annotations of each parameter
    /// @param modifiers the modifiers it is rendered with, `synchronized` or `native`
    /// @param doc the documentation comment, if any
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations have ended
    final <F extends Invocable> F declare(
            F member,
            Member.Kind kind,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Set<Modifier> modifiers,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        requireDeclaring(member.toString());
        Set<Modifier> rendered = requireModifiers(kind, member, modifiers);
        TypeScope scope = scope(kind, "the signature of " + member);
        List<me.supcheg.javafile.facts.MethodTypeParameter> typeParams =
                member.traits().typeParams();
        List<TypeVarToken<?>> methodVariables = typeParams.stream()
                .<TypeVarToken<?>>map(me.supcheg.javafile.facts.MethodTypeParameter::token)
                .toList();
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(type -> {
            Tokens.requireNotRaw(type);
            scope.method(methodVariables, "the signature of " + member).require(type);
        });
        if (kind == Member.Kind.CONSTRUCTOR || kind == Member.Kind.CANONICAL || kind == Member.Kind.COMPACT) {
            registry().constructor(member);
        } else {
            registry().method(member);
        }
        Member.Callable declared = new Member.Callable(
                member,
                kind,
                thrown,
                annotations,
                parameterAnnotations,
                scope(kind, "the body of " + member).method(methodVariables, "the body of " + member),
                registry().overrides(member.signature()),
                rendered,
                doc,
                Site.here());
        declared(declared);
        declaredMember(declared);
        return member;
    }

    /// The modifiers a method of the kind is rendered with beside those its kind fixes, held to what
    /// Java allows: an abstract method has neither `synchronized` nor `native`, and a member of variable
    /// arity whose elements are not reifiable is `@SafeVarargs`, which a `default` or an abstract method
    /// of an interface cannot carry — an instance method of a record or an enum is declared `final` for
    /// it, as one of a class is.
    private Set<Modifier> requireModifiers(Member.Kind kind, Invocable member, Set<Modifier> modifiers) {
        Set<Modifier> held = requireModifiers(modifiers);
        if (!held.isEmpty() && kind == Member.Kind.ABSTRACT) {
            throw new IllegalArgumentException("the abstract method " + member + " cannot be " + held
                    + ": an abstract method is neither synchronized nor native (JLS 8.4.3.6)");
        }
        if (!(member.arity() instanceof Arity.Variable(var safety)) || safety != Arity.Safety.SAFE_VARARGS) {
            return held;
        }
        return switch (kind) {
            case STATIC, PRIVATE, PRIVATE_STATIC, CONSTRUCTOR, CANONICAL, COMPACT -> held;
            case METHOD ->
                Stream.concat(held.stream(), Stream.of(Modifier.FINAL)).collect(Collectors.toUnmodifiableSet());
            case ABSTRACT, DEFAULT, ACCESSOR ->
                throw new IllegalArgumentException("the method " + member + " of " + described
                        + " cannot be of variable arity with elements that are not reifiable: it would be"
                        + " `@SafeVarargs`, which javac accepts of a static, private or final method alone"
                        + " (JLS 9.6.4.7), and " + kind + " is none");
        };
    }

    /// The modifiers a method of the kind is rendered with beside those its kind fixes, held to what
    /// Java allows: an interface has no `synchronized` or `native` method, and a constructor is
    /// neither.
    private static Set<Modifier> requireModifiers(Set<Modifier> modifiers) {
        return modifiers.isEmpty() ? Set.of() : Set.copyOf(modifiers);
    }

    /// Called for each member declared by name, after it is registered: an enum records its abstract
    /// methods, which its constant bodies override.
    void declaredMember(Member.Callable member) {}

    /// Declares a method that overrides the one of a supertype the type has by `up`.
    ///
    /// @param up the witness that the type is of the supertype
    /// @param member the fact of the method
    /// @param kind how the method is rendered
    /// @param thrown the exception classes of its `throws` clause
    /// @param annotations the annotations it declares
    /// @param parameterAnnotations the annotations of each parameter
    /// @param <O> the supertype
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if the type inherits no such method, or cannot override it
    /// @throws IllegalStateException if the declarations have ended
    final <O, F extends Invocable> F declareOverride(
            Upcast<Self, ? extends O> up,
            F member,
            Member.Kind kind,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations) {
        return declareOverride(up, member, kind, thrown, annotations, parameterAnnotations, takeDoc());
    }

    /// Declares a documented method that overrides the one of a supertype the type has by `up`.
    ///
    /// @param up the witness that the type is of the supertype
    /// @param member the fact of the method
    /// @param kind how the method is rendered
    /// @param thrown the exception classes of its `throws` clause
    /// @param annotations the annotations it declares
    /// @param parameterAnnotations the annotations of each parameter
    /// @param doc the documentation comment, if any
    /// @param <O> the supertype
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if the type inherits no such method, or cannot override it
    /// @throws IllegalStateException if the declarations have ended
    final <O, F extends Invocable> F declareOverride(
            Upcast<Self, ? extends O> up,
            F member,
            Member.Kind kind,
            List<ClassToken<? extends Throwable>> thrown,
            List<AnnotationRef<?>> annotations,
            List<List<AnnotationRef<?>>> parameterAnnotations,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        if (!Tokens.sameType(up.self(), self())) {
            throw new IllegalArgumentException("the witness is of " + up.self() + ", and the type is " + self());
        }
        requireDeclaring(member.toString());
        TypeScope scope = scope(kind, "the signature of " + member);
        List<me.supcheg.javafile.facts.MethodTypeParameter> typeParams =
                member.traits().typeParams();
        List<TypeVarToken<?>> methodVariables = typeParams.stream()
                .<TypeVarToken<?>>map(me.supcheg.javafile.facts.MethodTypeParameter::token)
                .toList();
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(type -> {
            Tokens.requireNotRaw(type);
            scope.method(methodVariables, "the signature of " + member).require(type);
        });
        if (registry().inherited(member.signature()).isEmpty()) {
            throw new IllegalArgumentException("the method " + member + " of " + described + " overrides nothing:"
                    + " the supertype " + up.supertype() + " has no method of its signature");
        }
        registry().method(member);
        declared(new Member.Callable(
                member,
                kind,
                thrown,
                annotations,
                parameterAnnotations,
                scope(kind, "the body of " + member).method(methodVariables, "the body of " + member),
                true,
                Set.of(),
                doc,
                Site.here()));
        return member;
    }

    /// Declares a field of the type.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param modifiers the modifiers of the field
    /// @param annotations the annotations it declares
    /// @param <T> the field type
    /// @return the fact of the field
    /// @throws IllegalArgumentException if a field of the name is declared already, or the type is out of scope
    final <T> FieldRef<Self, T> declareField(
            String name, TypeToken<T> type, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations) {
        return declareField(name, type, modifiers, annotations, takeDoc());
    }

    /// Declares a documented field of the type.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param modifiers the modifiers of the field
    /// @param annotations the annotations it declares
    /// @param doc the documentation comment, if any
    /// @param <T> the field type
    /// @return the fact of the field
    /// @throws IllegalArgumentException if a field of the name is declared already, or the type is out of scope
    final <T> FieldRef<Self, T> declareField(
            String name,
            TypeToken<T> type,
            Set<Modifier> modifiers,
            List<AnnotationRef<?>> annotations,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        requireDeclaring("field " + name + " of " + described);
        TypeScope scope = modifiers.contains(Modifier.STATIC)
                ? staticScope("the type of field " + name)
                : scope("the type of field " + name);
        Tokens.requireNotRaw(type);
        scope.require(type);
        registry().field(name);
        FieldRef<Self, T> field = UnsafeFacts.field(self(), name, type);
        declared(new Member.Field(field, Set.copyOf(modifiers), annotations, doc, Site.here()));
        return field;
    }

    /// Declares a constant field of the type: implicitly `public static final`.
    ///
    /// @param name the constant name
    /// @param type the constant type
    /// @param annotations the annotations it declares
    /// @param <T> the constant type
    /// @return the fact of the constant
    /// @throws IllegalArgumentException if a field of the name is declared already, or the type is out of scope
    final <T> StaticFieldRef<T> declareConstant(String name, TypeToken<T> type, List<AnnotationRef<?>> annotations) {
        return declareConstant(name, type, annotations, takeDoc());
    }

    /// Declares a documented constant field of the type: implicitly `public static final`.
    ///
    /// @param name the constant name
    /// @param type the constant type
    /// @param annotations the annotations it declares
    /// @param doc the documentation comment, if any
    /// @param <T> the constant type
    /// @return the fact of the constant
    /// @throws IllegalArgumentException if a field of the name is declared already, or the type is out of scope
    final <T> StaticFieldRef<T> declareConstant(
            String name,
            TypeToken<T> type,
            List<AnnotationRef<?>> annotations,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        requireDeclaring("constant " + name + " of " + described);
        Tokens.requireNotRaw(type);
        staticScope("the type of constant " + name).require(type);
        registry().field(name);
        StaticFieldRef<T> constant = UnsafeFacts.staticField(self(), name, type);
        declared(new Member.Constant(constant, annotations, doc, Site.here()));
        return constant;
    }

    /// Declares a static field of the type, which the bodies of the type give its initializer.
    ///
    /// @param field its fact
    /// @param modifiers its modifiers
    /// @param annotations the annotations it declares
    /// @param <F> the fact
    /// @return the fact, `field`
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter - which a `static` member has not
    ///     in scope (JLS 8.1.2) -, or the declarations have ended
    final <F extends StaticFieldRef<?>> F declaredStatic(
            F field, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations) {
        return declaredStatic(field, modifiers, annotations, takeDoc());
    }

    /// Declares a documented static field of the type, which the bodies of the type give its initializer.
    ///
    /// @param field its fact
    /// @param modifiers its modifiers
    /// @param annotations the annotations it declares
    /// @param doc the documentation comment, if any
    /// @param <F> the fact
    /// @return the fact, `field`
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter - which a `static` member has not
    ///     in scope (JLS 8.1.2) -, or the declarations have ended
    final <F extends StaticFieldRef<?>> F declaredStatic(
            F field,
            Set<Modifier> modifiers,
            List<AnnotationRef<?>> annotations,
            Optional<me.supcheg.javafile.doc.DocComment> doc) {
        String name = field.name();
        requireDeclaring("static field " + name + " of " + described);
        Tokens.requireNotRaw(field.type());
        staticScope("the type of static field " + name).require(field.type());
        registry().field(name);
        annotations.forEach(annotation -> target().verify(annotation.type().token()));
        declared(new Member.StaticField(field, Set.copyOf(modifiers), annotations, doc, Site.here()));
        return field;
    }

    /// Declares an instance initializer block, which the bodies of the type define exactly once.
    ///
    /// @return the handle of the declared block
    /// @throws IllegalStateException if the declarations have ended
    final InstanceInitializerBlock initializerBlock() {
        requireDeclaring("an instance initializer block of " + described);
        InstanceInitializerBlock block = new InstanceInitializerBlock();
        declared(new Member.Initializer(false, block, Site.here()));
        return block;
    }

    /// Declares a static initializer block, which the bodies of the type define exactly once.
    ///
    /// @return the handle of the declared block
    /// @throws IllegalStateException if the declarations have ended
    final StaticInitializerBlock staticInitializerBlock() {
        requireDeclaring("a static initializer block of " + described);
        StaticInitializerBlock block = new StaticInitializerBlock();
        declared(new Member.Initializer(true, block, Site.here()));
        return block;
    }

    private void declared(Member member) {
        members = Stream.concat(members.stream(), Stream.of(member)).toList();
        membersDeclared = true;
    }

    /// Adds a member type of the type, which its file renders after its other members.
    ///
    /// @param type the member type
    /// @throws IllegalStateException if the declarations have ended
    final void declaredNested(me.supcheg.javafile.model.TypeDecl type) {
        requireDeclaring("a member type of " + described);
        nested = Stream.concat(nested.stream(), Stream.of(type)).toList();
        membersDeclared = true;
    }

    /// The member types of the type, in declaration order.
    ///
    /// @return the member types
    final List<me.supcheg.javafile.model.TypeDecl> declaredNested() {
        return nested;
    }

    /// The declarations of the members end and their bodies begin.
    ///
    /// @param make makes the bodies of this kind
    /// @param <B> the bodies
    /// @return the bodies
    /// @throws IllegalStateException if the declarations have ended, or leave undone what only the type can do
    final <B extends TypedTypeBodies<Self, ?>> B bodies(java.util.function.Function<TypedTypeBodies.Declared, B> make) {
        requireDeclaring("the bodies of " + described);
        registry().requireComplete(allowAbstract());
        B bodies = make.apply(new TypedTypeBodies.Declared(self(), inherited, members, this::table));
        membersBegan = true;
        return bodies;
    }

    /// The methods of the type, asked once its declarations have ended.
    ///
    /// @return the method table template
    final me.supcheg.javafile.facts.MethodTableTemplate table() {
        if (!membersBegan) {
            throw new IllegalStateException("the methods of " + described + " are known only once its members are"
                    + " declared: bodies() of the builder of its members ends that");
        }
        return registry().table();
    }

    final List<Member> declaredMembers() {
        return members;
    }

    /// Completes the declaration with the bodies its specification ended in.
    ///
    /// @param bodies the bodies the specification returned
    /// @return the file of the type
    /// @throws IllegalStateException if a declared member is not defined, or the bodies are not those of this
    ///     declaration
    final JavaFile complete(TypedTypeBodies<Self, ?> bodies) {
        requireBodies();
        JavaFile file = file(bodies.complete());
        complete = true;
        return file;
    }

    /// Completes the declaration of a member type with the bodies its specification ended in.
    ///
    /// @param bodies the bodies the specification returned
    /// @return the declaration of the member type
    /// @throws IllegalStateException if the declarations have not ended
    final me.supcheg.javafile.model.TypeDecl completeNested(TypedTypeBodies<Self, ?> bodies) {
        requireBodies();
        me.supcheg.javafile.model.TypeDecl type = nestedDecl(bodies.complete());
        complete = true;
        return type;
    }

    private void requireBodies() {
        if (!membersBegan) {
            throw new IllegalStateException(
                    "the specification of " + described + " returned bodies before its" + " declarations ended");
        }
    }

    /// The file of the type with its members, as its kind renders it.
    ///
    /// @param members the members, in declaration order
    /// @return the file
    abstract JavaFile file(List<?> members);

    /// The declaration of a member type with its members, as its kind renders it.
    ///
    /// @param members the members, in declaration order
    /// @return the declaration
    abstract me.supcheg.javafile.model.TypeDecl nestedDecl(List<?> members);

    /// A member of the type as it is declared, before its body or initializer is given.
    sealed interface Member {

        /// How the member is rendered.
        enum Kind {
            /// A `public` instance method of a record or an enum.
            METHOD,
            /// An abstract method of an interface or an enum.
            ABSTRACT,
            /// A `default` method of an interface.
            DEFAULT,
            /// A `public static` method.
            STATIC,
            /// A `private` method of an interface.
            PRIVATE,
            /// A `private static` method of an interface.
            PRIVATE_STATIC,
            /// A constructor.
            CONSTRUCTOR,
            /// A canonical constructor of a record.
            CANONICAL,
            /// A compact constructor of a record.
            COMPACT,
            /// An accessor of a record component.
            ACCESSOR
        }

        /// A method or constructor, which the bodies of the type define.
        ///
        /// @param fact its fact
        /// @param kind how it is rendered
        /// @param thrown the exception classes of its `throws` clause
        /// @param annotations the annotations it declares
        /// @param parameterAnnotations the annotations of each parameter
        /// @param scope the scope of the types its body mentions
        /// @param overrides whether it overrides a method of a supertype, and is rendered `@Override`
        /// @param modifiers the modifiers it is rendered with beside those its kind fixes, `synchronized`
        ///     or `native`
        /// @param site where it was declared
        record Callable(
                Invocable fact,
                Kind kind,
                List<ClassToken<? extends Throwable>> thrown,
                List<AnnotationRef<?>> annotations,
                List<List<AnnotationRef<?>>> parameterAnnotations,
                TypeScope scope,
                boolean overrides,
                Set<Modifier> modifiers,
                Optional<me.supcheg.javafile.doc.DocComment> doc,
                Site site)
                implements Member {
            public Callable {
                thrown = List.copyOf(thrown);
                annotations = List.copyOf(annotations);
                parameterAnnotations =
                        parameterAnnotations.stream().map(List::copyOf).toList();
                modifiers = Set.copyOf(modifiers);
            }
        }

        /// A field, which the bodies of the type give its initializer.
        ///
        /// @param fact its fact
        /// @param modifiers its modifiers
        /// @param annotations the annotations it declares
        /// @param doc the documentation comment, if any
        /// @param site where it was declared
        record Field(
                FieldRef<?, ?> fact,
                Set<Modifier> modifiers,
                List<AnnotationRef<?>> annotations,
                Optional<me.supcheg.javafile.doc.DocComment> doc,
                Site site)
                implements Member {
            public Field {
                modifiers = Set.copyOf(modifiers);
                annotations = List.copyOf(annotations);
            }
        }

        /// An initializer block, which the bodies of the type define.
        ///
        /// @param isStatic whether it is a `static { ... }` block
        /// @param handle the handle the declaration returned
        /// @param site where it was declared
        record Initializer(boolean isStatic, Object handle, Site site) implements Member {
            /// The block, for messages.
            ///
            /// @return `instance initializer block` or `static initializer block`
            String described() {
                return (isStatic ? "static" : "instance") + " initializer block";
            }
        }

        /// A constant, which the bodies of the type give its initializer.
        ///
        /// @param fact its fact
        /// @param annotations the annotations it declares
        /// @param doc the documentation comment, if any
        /// @param site where it was declared
        record Constant(
                StaticFieldRef<?> fact,
                List<AnnotationRef<?>> annotations,
                Optional<me.supcheg.javafile.doc.DocComment> doc,
                Site site)
                implements Member {
            public Constant {
                annotations = List.copyOf(annotations);
            }
        }

        /// A static field of a record or an enum, which the bodies of the type give its initializer.
        ///
        /// @param fact its fact
        /// @param modifiers its modifiers
        /// @param annotations the annotations it declares
        /// @param doc the documentation comment, if any
        /// @param site where it was declared
        record StaticField(
                StaticFieldRef<?> fact,
                Set<Modifier> modifiers,
                List<AnnotationRef<?>> annotations,
                Optional<me.supcheg.javafile.doc.DocComment> doc,
                Site site)
                implements Member {
            public StaticField {
                modifiers = Set.copyOf(modifiers);
                annotations = List.copyOf(annotations);
            }
        }
    }
}
