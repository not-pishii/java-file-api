package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/// The declarations of the generic methods of a type that is not a class ([TypeMemberDeclarations]): an
/// interface, a record or an enum. It is the stage in which the type parameters of the member are declared
/// ([TypeGenericMethodDeclaration]), and every stage after it adds one aspect of the member — its `throws`
/// clause ([TypeGenericThrowingDeclarations]), its annotations ([TypeGenericAnnotatedDeclarations]), its
/// documentation ([TypeGenericDocumentedDeclarations]) or its modifiers
/// ([TypeGenericModifiedDeclarations]) — before the member itself is declared.
///
/// **Declaring is introducing.** A member is declared here, which gives its fact; the bodies of the type
/// ([TypedTypeBodies]) then define it. The type parameters of the member are in scope in the signature and
/// the body of that member alone (JLS 6.3).
///
/// @param <Self> the brand of the type being declared
public abstract sealed class TypeGenericDeclarations<Self> extends TypeGenericMethodDeclarationArities<Self>
        permits TypeGenericMethodDeclaration,
                TypeGenericThrowingDeclarations,
                TypeGenericAnnotatedDeclarations,
                TypeGenericDocumentedDeclarations,
                TypeGenericModifiedDeclarations {

    TypeGenericDeclarations() {}

    /// The declarations of the members of the type, which the member declared here is added to.
    ///
    /// @return the declarations
    @Override
    abstract TypeMemberDeclarations<Self> declarations();

    /// The exception classes of the `throws` clause of the member declared here.
    ///
    /// @return the exception classes, in order
    @Override
    abstract List<ClassToken<? extends Throwable>> thrown();

    /// The annotations the member declared here declares.
    ///
    /// @return the annotations, in order
    @Override
    abstract List<AnnotationRef<?>> annotations();

    /// The annotations of the parameters of the member declared here, one list per parameter of its
    /// arity.
    ///
    /// @return the annotations of each parameter
    @Override
    abstract List<List<AnnotationRef<?>>> parameterAnnotations();

    /// The modifiers the member declared here is rendered with, beside those its form fixes.
    ///
    /// @return the modifiers
    @Override
    abstract Set<Modifier> modifiers();

    /// The documentation comment the member declared here carries.
    ///
    /// @return the comment, or empty
    @Override
    abstract Optional<DocComment> doc();

    /// The type parameters of the member declared here, as the declaration writes them.
    ///
    /// @return the type parameters, in declaration order
    @Override
    abstract List<MethodTypeParameter> typeParams();

    /// The traits of a member declared here: `traits`, with the `throws` clause and the type parameters of
    /// its own.
    @Override
    MemberTraits traits(MemberTraits traits) {
        return new MemberTraits(
                List.copyOf(thrown()),
                traits.overridability(),
                traits.typeArgs(),
                traits.access(),
                traits.arity(),
                traits.annotations(),
                traits.parameterAnnotations(),
                typeParams());
    }

    /// Declares a member whose fact is whole already, with every aspect of the stage: the type parameters
    /// the fact carries are in scope in its signature and its body, and the kind fixes the modifiers it is
    /// rendered with.
    @Override
    <F extends Invocable> F declared(F member, Kind kind) {
        return declarations()
                .declaredAs(member, kind, thrown(), annotations(), parameterAnnotations(), modifiers(), doc());
    }

    /// The declarations of the members that declare `exceptions` in their `throws` clause, of this member:
    /// `m.throwing(IOException_.TOKEN).declareMethod("read", String_.TOKEN)`. The clause is part of the fact,
    /// as it is for a member declared by [MemberBuilder#throwing].
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the declarations of the members that declare `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final TypeGenericThrowingDeclarations<Self> throwing(ClassToken<? extends Throwable>... exceptions) {
        return new TypeGenericThrowingDeclarations<>(this, List.of(exceptions));
    }

    /// The declarations of the members that declare `annotations`, the methods of any arity: a callable
    /// is rendered with them, and a parameter with those [#annotatedParameter(int, AnnotationRef...)] gives
    /// it.
    ///
    /// ```java
    /// var read = m.annotated(Deprecated_.annotation().build()).declareMethod("read", String_.TOKEN);
    /// ```
    ///
    /// @param annotations the annotations, in order
    /// @return the declarations that annotate the members they declare
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final TypeGenericAnnotatedDeclarations<Self> annotated(AnnotationRef<?>... annotations) {
        return new TypeGenericAnnotatedDeclarations<>(this, List.of(annotations), Map.of());
    }

    /// The declarations of the members whose parameters declare the annotations of a list: a parameter of
    /// an arity of the member is rendered with those of its index.
    ///
    /// @param parameter the index of the parameter
    /// @param annotations the annotations of the parameter, in order
    /// @return the declarations that annotate the parameters of the members they declare
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final TypeGenericAnnotatedDeclarations<Self> annotatedParameter(
            int parameter, AnnotationRef<?>... annotations) {
        return new TypeGenericAnnotatedDeclarations<>(this, List.of(), Map.of(parameter, List.of(annotations)));
    }

    /// The declarations of the members that carry a documentation comment:
    ///
    /// ```java
    /// var greet = m.documented("Greets the world.").declareStaticMethod("greet", String_.TOKEN);
    /// ```
    ///
    /// @param doc the comment
    /// @return the declarations that document the members they declare
    /// @throws IllegalArgumentException if the text of `doc` contains `*/`
    public final TypeGenericDocumentedDeclarations<Self> documented(DocComment doc) {
        return new TypeGenericDocumentedDeclarations<>(
                this, Docs.requireSafe(doc, declarations().described()));
    }

    /// The declarations of the members that carry a comment of one paragraph of plain text.
    ///
    /// @param text the text
    /// @return the declarations that document the members they declare
    /// @throws IllegalArgumentException if `text` is empty, or contains `*/`
    public final TypeGenericDocumentedDeclarations<Self> documented(String text) {
        return documented(Docs.of(text, declarations().described()));
    }

    /// The declarations of the methods of the type that are rendered with `modifiers` beside those their
    /// form fixes:
    ///
    /// ```java
    /// var spin = m.modified(Modifier.SYNCHRONIZED).declareVoidMethod("spin");
    /// var read = m.modified(Modifier.NATIVE).declareMethod("read", PrimitiveToken.INT);
    /// ```
    ///
    /// A `synchronized` method has a body, as any method declared here, and the bodies define it. A
    /// `native` method has none: the declaration is whole, its fact is there to call, and the bodies
    /// neither define it nor wait for it. What a method can be is held to its form when it is declared: an
    /// interface has neither `synchronized` nor `native` method, a record has no `native` one (JLS
    /// 8.10.3), and an abstract method is neither.
    ///
    /// @param modifiers the modifiers to add, in any order
    /// @return the declarations that render the members they declare with them
    /// @throws IllegalArgumentException if a modifier cannot be added to a method of the type
    /// @throws IllegalStateException if the declarations have ended
    public final TypeGenericModifiedDeclarations<Self> modified(Modifier... modifiers) {
        return new TypeGenericModifiedDeclarations<>(this, declarations().requireMethodModifiers(modifiers));
    }

    /// The declarations whose last parameter is of variable arity, `T... v`; the body sees it as the array
    /// `T[]`, as a member of a class declared by [Declarations#variableArity()] does.
    ///
    /// @return the declarations of the members of variable arity
    public final TypeGenericVariableArityDeclarations<Self> variableArity() {
        return new TypeGenericVariableArityDeclarations<>(this);
    }
}
