package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclarationIdentity;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
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
    private final String kind;
    private final Inheritance.Heir heir;
    private final DeclarationIdentity identity;
    private Inheritance inherited;
    private DeclaredToken<Self> self;
    private TypeSignatureRegistry registry;
    private List<Member> members = List.of();
    private boolean membersBegan;
    private boolean complete;

    /// @param target the classpath the type is compiled against
    /// @param desc the type
    /// @param kind the keyword of the declaration, `interface`, `record` or `enum`
    TypeDeclarationSupport(TargetClasspath target, ClassDesc desc, String kind) {
        this.target = target;
        this.desc = desc;
        this.kind = kind;
        this.described =
                kind + " " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName();
        this.heir = new Inheritance.Heir.OfClass(described, desc.packageName());
        this.identity = UnsafeFacts.declarationIdentity(described);
        // Every type has the methods of Object: an interface implicitly declares them, a record and an
        // enum inherit them.
        this.inherited = Inheritance.of(heir, Inheritance.supertype(described, target, Object_.TOKEN));
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
        return self;
    }

    final List<Subtyping.Bounded> bounded() {
        return List.of();
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
        target.verify(supertype);
        inherited = inherited.with(Inheritance.supertype(described, target, supertype));
        return new Upcast<>(self, supertype);
    }

    final void requireHeader(String what) {
        if (membersBegan) {
            throw new IllegalStateException("cannot declare " + what + ": the members of " + described
                    + " have begun, and a type names its supertypes before them");
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

    final void requireDeclaring(String what) {
        if (complete) {
            throw new IllegalStateException(
                    "cannot declare or define " + what + ": the declaration of " + described + " is already complete");
        }
        if (membersBegan) {
            throw new IllegalStateException("cannot declare " + what + ": the declarations of " + described
                    + " have ended — bodies() began the bodies of its members");
        }
        Scopes.requireNoneOpen(what);
    }

    private TypeSignatureRegistry registry() {
        if (registry == null) {
            registry = new TypeSignatureRegistry(described, self, inherited, bounded());
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
        requireDeclaring(member.toString());
        TypeScope scope = scope(kind, "the signature of " + member);
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(type -> {
            Tokens.requireNotRaw(type);
            scope.require(type);
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
                scope(kind, "the body of " + member),
                registry().overrides(member.signature()),
                Site.here());
        declared(declared);
        declaredMember(declared);
        return member;
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
        if (!Tokens.sameType(up.self(), self)) {
            throw new IllegalArgumentException("the witness is of " + up.self() + ", and the type is " + self);
        }
        requireDeclaring(member.toString());
        TypeScope scope = scope(kind, "the signature of " + member);
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(type -> {
            Tokens.requireNotRaw(type);
            scope.require(type);
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
                scope(kind, "the body of " + member),
                true,
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
        requireDeclaring("field " + name + " of " + described);
        TypeScope scope = modifiers.contains(Modifier.STATIC)
                ? staticScope("the type of field " + name)
                : scope("the type of field " + name);
        Tokens.requireNotRaw(type);
        scope.require(type);
        registry().field(name);
        FieldRef<Self, T> field = UnsafeFacts.field(self, name, type);
        declared(new Member.Field(field, Set.copyOf(modifiers), annotations, Site.here()));
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
        requireDeclaring("constant " + name + " of " + described);
        Tokens.requireNotRaw(type);
        staticScope("the type of constant " + name).require(type);
        registry().field(name);
        StaticFieldRef<T> constant = UnsafeFacts.staticField(self, name, type);
        declared(new Member.Constant(constant, annotations, Site.here()));
        return constant;
    }

    private void declared(Member member) {
        members = Stream.concat(members.stream(), Stream.of(member)).toList();
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
        B bodies = make.apply(new TypedTypeBodies.Declared(self, inherited, members, this::table));
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
        if (!membersBegan) {
            throw new IllegalStateException(
                    "the specification of " + described + " returned bodies before its" + " declarations ended");
        }
        JavaFile file = file(bodies.complete());
        complete = true;
        return file;
    }

    /// The file of the type with its members, as its kind renders it.
    ///
    /// @param members the members, in declaration order
    /// @return the file
    abstract JavaFile file(List<?> members);

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
        /// @param site where it was declared
        record Callable(
                Invocable fact,
                Kind kind,
                List<ClassToken<? extends Throwable>> thrown,
                List<AnnotationRef<?>> annotations,
                List<List<AnnotationRef<?>>> parameterAnnotations,
                TypeScope scope,
                boolean overrides,
                Site site)
                implements Member {
            public Callable {
                thrown = List.copyOf(thrown);
                annotations = List.copyOf(annotations);
                parameterAnnotations =
                        parameterAnnotations.stream().map(List::copyOf).toList();
            }
        }

        /// A field, which the bodies of the type give its initializer.
        ///
        /// @param fact its fact
        /// @param modifiers its modifiers
        /// @param annotations the annotations it declares
        /// @param site where it was declared
        record Field(FieldRef<?, ?> fact, Set<Modifier> modifiers, List<AnnotationRef<?>> annotations, Site site)
                implements Member {
            public Field {
                modifiers = Set.copyOf(modifiers);
                annotations = List.copyOf(annotations);
            }
        }

        /// A constant, which the bodies of the type give its initializer.
        ///
        /// @param fact its fact
        /// @param annotations the annotations it declares
        /// @param site where it was declared
        record Constant(StaticFieldRef<?> fact, List<AnnotationRef<?>> annotations, Site site) implements Member {
            public Constant {
                annotations = List.copyOf(annotations);
            }
        }
    }
}
