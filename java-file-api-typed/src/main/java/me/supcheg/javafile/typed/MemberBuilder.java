package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MemberFact;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/// The builder of the members of a class being declared (§6.5), branded
/// with `Self`: the stage of its declarations. What [TypedClassBuilder] — of
/// a class that extends `Object` — and [TypedSubclassBuilder] — of a class
/// with another superclass — have in common, which is everything but how the
/// stage ends.
///
/// **Every member is declared here, and none has a body.** A field, a method,
/// a constructor, an override is declared, which gives its fact; the stage
/// ends in the bodies of the class ([MemberBodies]), where each declared
/// member is defined. The two are distinct types, so no member is declared
/// after a body was built: when the first body is, the methods of the class
/// and the names of its fields are all there, and the body is lowered where
/// it is defined — what is wrong with it is rejected by the call that gave
/// it.
///
/// A stage is a value a generator may keep: this one used after it ended is
/// rejected, which the types cannot.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public abstract sealed class MemberBuilder<Self, S> extends Declarations<Self, S>
        permits TypedClassBuilder, TypedSubclassBuilder {

    private final String described;
    private final FinalClassToken<Self> self;
    private final Superclass superclass;
    private final SignatureRegistry registry;
    private final ClassDeclaration<Self> owner;
    private List<Member> members = List.of();

    MemberBuilder(ClassDeclaration<Self> declared, Inheritance inherited, Superclass superclass) {
        this.superclass = superclass;
        this.described = declared.described();
        this.self = declared.self();
        this.registry = new SignatureRegistry(described, inherited, declared.bounded());
        this.owner = declared;
    }

    /// The fact of the class being declared: a [FinalClassToken], usable as
    /// the type of expressions, variables and members of the class. Its
    /// [FinalClassToken#methods()] is known once the declarations of the class end.
    ///
    /// @return the self token
    public final FinalClassToken<Self> self() {
        return self;
    }

    @Override
    final MemberBuilder<Self, S> builder() {
        return this;
    }

    @Override
    final List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }

    // ------------------------------------------------------------------
    // The throws clause
    // ------------------------------------------------------------------

    /// The declarations of the methods and constructors of this class
    /// that declare `exceptions` in their `throws` clause:
    ///
    /// ```java
    /// var read = cb.throwing(IOException_.TOKEN).declareMethod("read", PrimitiveToken.INT);
    /// var twice = cb.throwing(IOException_.TOKEN).declareMethod("twice", PrimitiveToken.INT);
    /// var bodies = cb.bodies();
    /// bodies.define(read, (b, self) -> b.return_(call(field(self, in), InputStream_.read)));
    /// bodies.define(twice, (b, self) -> b.return_(call(self, read)));
    /// ```
    ///
    /// The clause is part of the fact the declaration returns: a call of
    /// the member is accepted only where each of its checked exceptions is
    /// caught or declared, and the body the member is defined with may
    /// throw them and their subclasses. Unchecked exceptions may be
    /// declared, and change nothing.
    ///
    /// The result declares methods and constructors and nothing else — a
    /// field has no `throws` clause.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the declarations of the members that declare `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingDeclarations<Self, S> throwing(ClassToken<? extends Throwable>... exceptions) {
        return new ThrowingDeclarations<>(this, List.of(exceptions));
    }

    // ------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------

    /// Declares a `public final` field, to be given its initializer by the bodies of the class
    /// ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    public final <T> FieldRef<Self, T> declareField(String name, TypeToken<T> type) {
        return declared(UnsafeFacts.field(self, name, type), Set.of(Modifier.PUBLIC, Modifier.FINAL));
    }

    /// Declares a `public` field, not `final`, to be given its initializer by the bodies of the
    /// class ([MemberBodies#initialize]).
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter of another class, or the
    ///     declarations of the class have ended
    public final <T> MutableFieldRef<Self, T> declareMutableField(String name, TypeToken<T> type) {
        return declared(UnsafeFacts.mutableField(self, name, type), Set.of(Modifier.PUBLIC));
    }

    private <F extends FieldRef<Self, ?>> F declared(F field, Set<Modifier> modifiers) {
        String name = field.name();
        Site site = Site.here();
        owner.requireDeclaring(this, "field " + name + " of " + described);
        owner.scope("the type of field " + name).require(field.type());
        registry.field(name);
        declared(new Member.Field(field, modifiers, site));
        return field;
    }

    // ------------------------------------------------------------------
    // What a class has of its superclass
    // ------------------------------------------------------------------

    /// The fact of a `protected` member of the superclass or of a class above it, taken out of
    /// its wrapper: to override the method, here; to call it or to reach the field, in a body
    /// ([MemberBodies#protected_]).
    ///
    /// @param held the fact, held back
    /// @param <F> the fact
    /// @return the fact
    /// @throws IllegalArgumentException if the class is no subclass of the owner of the member on the
    ///     target classpath
    public final <F extends MemberFact> F protected_(Protected<? super S, F> held) {
        return opened(self, described, held);
    }

    /// The fact of a `protected` member of a class `self` is a subclass of.
    static <F extends MemberFact> F opened(FinalClassToken<?> self, String described, Protected<?, F> held) {
        if (!self.isSubclassOf(held.owner().erasure())) {
            throw new IllegalArgumentException(described + " cannot reach " + held + ": it is no subclass of "
                    + held.owner() + ", and a protected member is reached by a subclass alone (JLS 6.6.2)");
        }
        return UnsafeFacts.open(held);
    }

    // ------------------------------------------------------------------
    // Escape hatch
    // ------------------------------------------------------------------

    /// Adds a raw, untyped member from [Unsafe#member(me.supcheg.javafile.model.ClassMember)].
    /// It is not checked against the signatures of the typed members.
    ///
    /// @param member the member
    /// @return this builder
    /// @throws IllegalStateException if the declarations of the class have ended
    public final MemberBuilder<Self, S> add(UnsafeMember member) {
        owner.requireDeclaring(this, "an unsafe member of " + described);
        declared(new Member.Given(member.member()));
        return this;
    }

    // ------------------------------------------------------------------
    // Declarations
    // ------------------------------------------------------------------

    /// The scope of the types a member mentions: of an instance member, or of a `static` one (JLS 8.1.2).
    private TypeScope scope(InvocableKind kind, String where) {
        return switch (kind) {
            case CONSTRUCTOR, INSTANCE_METHOD -> owner.scope(where);
            case STATIC_METHOD -> owner.staticScope(where);
        };
    }

    /// Holds the types of the signature of a member to its scope, where it is declared.
    private void requireInScope(Invocable member) {
        TypeScope scope = scope(member.kind(), "the signature of " + member);
        Stream.concat(member.params().stream(), member.resultType().stream()).forEach(scope::require);
    }

    /// Declares a method or constructor of the class.
    ///
    /// @param member its fact
    /// @param thrown the exception classes of its `throws` clause, as its fact has them
    /// @return the fact, `member`
    <F extends Invocable> F declare(F member, List<ClassToken<? extends Throwable>> thrown) {
        return declare(member, thrown, Site.here());
    }

    /// Declares a method or constructor of the class, said at `site`.
    <F extends Invocable> F declare(F member, List<ClassToken<? extends Throwable>> thrown, Site site) {
        owner.requireDeclaring(this, member.toString());
        requireInScope(member);
        registry.member(member);
        declared(new Member.Callable(
                member,
                thrown,
                Member.Kind.DECLARES,
                Heritage.Arity.FIXED,
                scope(member.kind(), "the body of " + member),
                site));
        return member;
    }

    /// Declares a method of the class that overrides the methods of its signature the class
    /// inherits; rendered with `@Override`.
    ///
    /// @param own its fact
    /// @param thrown the exception classes of its `throws` clause, as its fact has them
    /// @return the fact, `own`
    /// @throws IllegalArgumentException if the class inherits no such method, or cannot override it
    <F extends Invocable> F declareOverride(F own, List<ClassToken<? extends Throwable>> thrown) {
        return declareOverride(own, thrown, Site.here());
    }

    /// Declares a method of the class that overrides, said at `site`.
    <F extends Invocable> F declareOverride(F own, List<ClassToken<? extends Throwable>> thrown, Site site) {
        owner.requireDeclaring(this, own.toString());
        requireInScope(own);
        // What a result is a subtype of is told by its metamodel, which has to hold on the target.
        own.resultType().ifPresent(owner.target()::verify);
        declared(new Member.Callable(
                own,
                thrown,
                Member.Kind.OVERRIDES,
                registry.override(own),
                scope(own.kind(), "the body of " + own),
                site));
        return own;
    }

    private void declared(Member member) {
        members = Stream.concat(members.stream(), Stream.of(member)).toList();
    }

    // ------------------------------------------------------------------
    // The end of the declarations
    // ------------------------------------------------------------------

    /// The declarations end and the bodies begin: what only a method of the class can do is done
    /// by one it declared ([SignatureRegistry#requireComplete()]), and the methods of the class are
    /// known from here on.
    ///
    /// @param make makes the stage of the bodies, of what the class declared
    /// @param <B> the stage of the bodies
    /// @return the stage of the bodies
    /// @throws IllegalStateException if the declarations have ended already, or leave undone what
    ///     only a method of the class can do
    final <B extends MemberBodies<Self, S>> B begin(Function<MemberBodies.Declared<Self>, B> make) {
        owner.requireDeclaring(this, "the bodies of " + described);
        registry.requireComplete();
        MethodTable methods = registry.table();
        return owner.bodies(methods, () -> make.apply(new MemberBodies.Declared<>(owner, superclass, members)));
    }

    /// A member of the class as it is declared, in the order it was.
    sealed interface Member {

        /// Whether a method overrides those the class inherits: rendered with `@Override`.
        enum Kind {
            /// A method or constructor declared by its name.
            DECLARES,
            /// A method that overrides.
            OVERRIDES
        }

        /// A method or constructor, which the bodies of the class define.
        ///
        /// @param fact its fact
        /// @param thrown the exception classes of its `throws` clause
        /// @param kind whether it overrides
        /// @param arity whether its last parameter is of variable arity: as that of the methods it
        ///     overrides is, and never of a method declared by its name
        /// @param scope the scope of the types its body mentions
        /// @param site where it was declared
        record Callable(
                Invocable fact,
                List<ClassToken<? extends Throwable>> thrown,
                Kind kind,
                Heritage.Arity arity,
                TypeScope scope,
                Site site)
                implements Member {
            public Callable {
                thrown = List.copyOf(thrown);
            }
        }

        /// A field, which the bodies of the class give its initializer.
        ///
        /// @param fact its fact
        /// @param modifiers its modifiers
        /// @param site where it was declared
        record Field(FieldRef<?, ?> fact, Set<Modifier> modifiers, Site site) implements Member {
            public Field {
                modifiers = Set.copyOf(modifiers);
            }
        }

        /// A member that is there whole: an unsafe member.
        ///
        /// @param member the member
        record Given(ClassMember member) implements Member {}
    }
}
