package me.supcheg.javafile.typed;

/// Declares the members of a typed `final` class that extends `Object`
/// (§6.5), branded with `Self`: the stage between the header of the class
/// and the bodies of its members.
///
/// **Declaring is introducing.** Declaring a member returns its fact —
/// `MethodRef1<Self, R, A1>`, `CtorRef0<Self>`, `FieldRef<Self, T>` — the
/// only way to obtain a fact of `Self` without `UnsafeFacts`.
///
/// **Declare, then define.** Every member is declared here, which gives its
/// fact, and none has a body; [#bodies()] ends the declarations and gives the
/// stage that defines them ([TypedClassBodies]). Any body may use the fact of
/// any member, so recursion and mutual calls are expressible:
///
/// ```java
/// var isEven = cb.declareMethod("isEven", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
/// var isOdd = cb.declareMethod("isOdd", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
/// var bodies = cb.bodies();
/// bodies.define(isEven, (b, self, n) -> b.ifElse(eqInt(n, literal(0)),
///         t -> t.return_(literal(true)),
///         e -> e.return_(call(self, isOdd, subInt(n, literal(1))))));
/// bodies.define(isOdd, (b, self, n) -> ...);
/// ```
///
/// The two stages are two types, so the order is not a rule to keep but the
/// only one there is: a member cannot be declared once a body was built.
/// That is what lets a body be judged where it is given — against all the
/// methods of the class and all the names of its fields, which a member
/// declared later would change ([MemberBodies]).
///
/// **Declarations match their facts.** The class renders `public final`,
/// as its [FinalClassToken] says. A field of [#declareField] is `public
/// final` and has an initializer — a blank `final` field, definitely assigned
/// in every constructor, is not supported yet; one of [#declareMutableField]
/// is `public`, and only its [MutableFieldRef] can be assigned. Methods are
/// `public` and, the class being final, never overridden. Once the
/// declarations end, [FinalClassToken#methods()] of [#self()] lists the
/// declared methods and those inherited; asking earlier is rejected.
///
/// **Checked exceptions.** A method or constructor declares the exceptions
/// of its `throws` clause through [#throwing]: `cb.throwing(IOException_.TOKEN)`
/// declares methods and constructors as this builder does ([Declarations]),
/// each with `throws IOException`. The fact of such a member carries the clause, so a
/// call of it is checked like a call of any other fact, and its body may
/// throw what the clause covers; a body that throws a checked exception it
/// neither catches nor declares is rejected at the statement that throws
/// (see [Block]). A field initializer can throw no checked exception.
///
/// **Signatures.** A member that javac would reject is rejected when it is
/// declared: a duplicate by erasure, a method with the signature of one the
/// class inherits (§9.7; see [SignatureRegistry]).
///
/// **Overriding is by the fact.** A method the class inherits is overridden
/// by naming its fact, never by declaring a method of its name:
/// `cb.declareOverride(Object_.toString)` declares `@Override public String
/// toString()`. A method declared by name that would override or hide an
/// inherited one — `declareMethod("toString", ...)` — is rejected where it is
/// declared, so no method overrides by accident and none is rendered without
/// `@Override` that does. The override has the parameters and the result of
/// the method — or a narrower result, said by its token,
/// `cb.declareOverride(fact, token)` (JLS 8.4.5; [Subtyping]) — and a
/// `throws` clause — that of [#throwing] — the method allows (JLS 8.4.8.3).
/// What only a method of the class can do — settle the methods of two
/// supertypes that do not agree, answer `hashCode` where `equals` is
/// overridden — is held to when the declarations end, by [#bodies()].
///
/// Members are declared from the class specification only: doing so while a
/// method body is being built, or after the declarations ended, is rejected.
///
/// **`clone` and `finalize`.** The `protected` methods of `Object` are
/// overridden and called as those of any superclass are:
/// `cb.protected_(Object_.clone)` gives the fact, `declareOverride` and
/// `superCall` take it.
///
/// This builder is of a class that extends `Object`; one with another
/// superclass has a [TypedSubclassBuilder]. Methods and constructors are of
/// up to arity 2 (static methods up to 1); `abstract`/open classes come
/// later, as does a generator of the arity families analogous to
/// `java-file-api-facts`'s `FactsCodegen`.
///
/// @param <Self> the brand of the class being declared, unique to one
///     [TypedJavaFile#class_(TargetClasspath, java.lang.constant.ClassDesc, TypedJavaFile.TypedClassSpec)] call
public final class TypedClassBuilder<Self> extends MemberBuilder<Self, Object> {

    TypedClassBuilder(ClassDeclaration<Self> declared, Inheritance inherited, Superclass superclass) {
        super(declared, inherited, superclass);
    }

    /// The declarations of the class end and the bodies of its members begin.
    ///
    /// What can be judged of the declarations alone is judged here: the methods the class
    /// inherits under one signature and cannot have together are overridden, a class that
    /// overrides `equals` overrides `hashCode`, and every type the declarations name can be
    /// written in the file of the class.
    ///
    /// @return the stage that defines the declared members
    /// @throws IllegalStateException if the declarations have ended already, or leave undone what
    ///     only a method of the class can do ([SignatureRegistry#requireComplete()])
    public TypedClassBodies<Self> bodies() {
        return begin(TypedClassBodies::new);
    }
}
