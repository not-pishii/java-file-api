package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.model.ClassMember;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassTypeRef;
import org.jspecify.annotations.Nullable;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/// Builds a typed `final` class declaration (§6.5), branded with `Self`.
///
/// **Declaring is introducing.** Declaring a member returns its fact —
/// `MethodRef1<Self, R, A1>`, `CtorRef0<Self>`, `FieldRef<Self, T>` — the
/// only way to obtain a fact of `Self` without `UnsafeFacts`.
///
/// **Declare, then define.** A method or constructor is declared first,
/// which gives its fact, and defined later with a body; any body may use the
/// fact of any member declared before it is built, so recursion and mutual
/// calls are expressible:
///
/// ```java
/// var isEven = cb.declareMethod("isEven", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
/// var isOdd = cb.declareMethod("isOdd", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
/// cb.define(isEven, (b, self, n) -> b.ifElse(eqInt(n, literal(0)),
///         t -> t.return_(literal(true)),
///         e -> e.return_(call(self, isOdd, subInt(n, literal(1))))));
/// cb.define(isOdd, (b, self, n) -> ...);
/// ```
///
/// When nothing needs the fact before the body, `method`, `voidMethod`,
/// `staticMethod`, `voidStaticMethod` and `constructor` declare and define in
/// one call. Every declared member must be defined exactly once: defining it
/// twice is rejected at the second `define`, and a member left undefined is
/// rejected when the class declaration completes. `define` takes the fact of
/// a member of `Self` only, so the fact of another class does not compile; a
/// static member's fact carries no owner brand, and one not declared by this
/// builder is rejected when defined.
///
/// **`this` is a parameter.** The body of an instance method or constructor
/// receives `this` as an `Expr<Self>` HOAS parameter, next to its block; a
/// static method body does not. `this` is in scope in that body only —
/// lambdas in it included — and using it anywhere else, e.g. smuggled into
/// the body of another member or a field initializer, is rejected when the
/// statement is built (§6.2).
///
/// **Declarations match their facts.** The class renders `public final`,
/// as its [FinalClassToken] says. A [#field] is `public final` and has an
/// initializer — a blank `final` field, definitely assigned in every
/// constructor, is not supported yet; a [#mutableField] is `public`, and
/// only its [MutableFieldRef] can be assigned. Methods are `public` and,
/// the class being final, never overridden. Once the declaration completes,
/// [FinalClassToken#methods()] of [#self()] lists the declared instance
/// methods and those inherited from `Object`; asking earlier is rejected.
///
/// **Signatures.** A member that javac would reject is rejected when it is
/// declared: a duplicate by erasure, an override of a `final` method of
/// `Object`, an override of `toString`/`hashCode`/`equals` with an
/// incompatible return type, a static method hiding one of `Object`
/// (§9.7; see [SignatureRegistry]).
///
/// Members are declared and defined from the class specification only:
/// doing so while a method body is being built, or after the declaration
/// completed, is rejected.
///
/// This phase covers non-generic `final` classes, methods and constructors
/// up to arity 2 (static methods up to 1); `extends`/`implements`, type
/// parameters and `abstract`/open classes come later, as does a generator of
/// the arity families analogous to `java-file-api-facts`'s `FactsCodegen`.
///
/// @param <Self> the brand of the class being declared, unique to one
///     [TypedJavaFile#class_(java.lang.constant.ClassDesc, TypedJavaFile.TypedClassSpec)] call
public final class TypedClassBuilder<Self> {
    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang", "Override");
    private static final MemberTraits FINAL = MemberTraits.DEFAULT.with(Overridability.FINAL);

    private final String described;
    private final FinalClassToken<Self> self;
    private final SignatureRegistry registry;
    private final List<Slot> slots = new ArrayList<>();
    private final Map<Object, Slot> slotsByFact = new IdentityHashMap<>();
    private @Nullable MethodTable methods;
    private boolean complete;

    TypedClassBuilder(ClassDesc desc) {
        this.described = "class " + (desc.packageName().isEmpty() ? "" : desc.packageName() + ".") + desc.displayName();
        this.self = UnsafeFacts.finalClassToken(
                new ClassTypeRef(desc), List.of(ConstantDescs.CD_Object), this::methodTable);
        this.registry = new SignatureRegistry(described);
    }

    /// The fact of the class being declared: a [FinalClassToken], usable as
    /// the type of expressions, variables and members of the class. Its
    /// [FinalClassToken#methods()] is known once the declaration completes.
    ///
    /// @return the self token
    public FinalClassToken<Self> self() {
        return self;
    }

    private MethodTable methodTable() {
        if (methods == null) {
            throw new IllegalStateException("the methods of " + described + " are known only once its declaration"
                    + " is complete: the class is still being declared");
        }
        return methods;
    }

    // ------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------

    /// Declares a `public final` field with an initializer. The initializer
    /// is not in the scope of any body, so it cannot use `this` or a variable.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param initializer the initializer
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    public <T> FieldRef<Self, T> field(String name, TypeToken<T> type, Expr<? extends T> initializer) {
        FieldRef<Self, T> field = UnsafeFacts.field(self, name, type);
        declareField(field, name, type, Set.of(Modifier.PUBLIC, Modifier.FINAL), initializer);
        return field;
    }

    /// Declares a `public` field, not `final`, with an initializer.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param initializer the initializer
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    public <T> MutableFieldRef<Self, T> mutableField(String name, TypeToken<T> type, Expr<? extends T> initializer) {
        MutableFieldRef<Self, T> field = UnsafeFacts.mutableField(self, name, type);
        declareField(field, name, type, Set.of(Modifier.PUBLIC), initializer);
        return field;
    }

    private void declareField(
            FieldRef<Self, ?> field, String name, TypeToken<?> type, Set<Modifier> modifiers, Expr<?> initializer) {
        requireOpen("field " + name + " of " + described);
        registry.field(name);
        ScopeCheck.standalone(initializer.node(), "initializer of field " + name);
        Slot slot = slot(field, null, "field " + field);
        slot.define(new FieldDecl(
                name, type.typeRef(), List.of(), modifiers, Optional.of(new Lowering().lowerExpr(initializer.node()))));
    }

    // ------------------------------------------------------------------
    // Declarations
    // ------------------------------------------------------------------

    /// Declares a `public` method returning `R`, to be defined with
    /// [#define(MethodRef0, InstanceBody0)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declare(UnsafeFacts.method(self, name, result, FINAL));
    }

    /// Declares a `public` method, to be defined with [#define(MethodRef1, InstanceBody1)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeFacts.method(self, name, result, p1, FINAL));
    }

    /// Declares a `public` method, to be defined with [#define(MethodRef2, InstanceBody2)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeFacts.method(self, name, result, p1, p2, FINAL));
    }

    /// Declares a `public void` method, to be defined with [#define(VoidMethodRef0, InstanceBody0)].
    ///
    /// @param name the method name
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declare(UnsafeFacts.voidMethod(self, name, FINAL));
    }

    /// Declares a `public void` method, to be defined with [#define(VoidMethodRef1, InstanceBody1)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeFacts.voidMethod(self, name, p1, FINAL));
    }

    /// Declares a `public void` method, to be defined with [#define(VoidMethodRef2, InstanceBody2)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeFacts.voidMethod(self, name, p1, p2, FINAL));
    }

    /// Declares a `public static` method, to be defined with [#define(StaticMethodRef0, Function)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declare(UnsafeFacts.staticMethod(self, name, result, FINAL));
    }

    /// Declares a `public static` method, to be defined with [#define(StaticMethodRef1, BiFunction)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeFacts.staticMethod(self, name, result, p1, FINAL));
    }

    /// Declares a `public static void` method, to be defined with
    /// [#define(VoidStaticMethodRef0, Function)].
    ///
    /// @param name the method name
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declare(UnsafeFacts.voidStaticMethod(self, name, FINAL));
    }

    /// Declares a `public static void` method, to be defined with
    /// [#define(VoidStaticMethodRef1, BiFunction)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeFacts.voidStaticMethod(self, name, p1, FINAL));
    }

    /// Declares a `public` constructor, to be defined with [#define(CtorRef0, InstanceBody0)].
    ///
    /// @return the fact of the constructor, which `new_` accepts
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    public CtorRef0<Self> declareConstructor() {
        return declare(UnsafeFacts.ctor(self, MemberTraits.DEFAULT));
    }

    /// Declares a `public` constructor, to be defined with [#define(CtorRef1, InstanceBody1)].
    ///
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    public <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        return declare(UnsafeFacts.ctor(self, p1, MemberTraits.DEFAULT));
    }

    /// Declares a `public` constructor, to be defined with [#define(CtorRef2, InstanceBody2)].
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    public <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeFacts.ctor(self, p1, p2, MemberTraits.DEFAULT));
    }

    // ------------------------------------------------------------------
    // Definitions: each throws IllegalStateException if the member is
    // defined already, or is not a member declared by this builder
    // ------------------------------------------------------------------

    /// Defines a declared method; the body gets `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and `this`
    /// @param <R> the result type
    public <R> void define(MethodRef0<Self, R> method, InstanceBody0<Self, Body<R>, R> body) {
        Slot slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name());
        Expr<Self> self = thisOf(root);
        slot.define(member(slot, root, List.of(), b -> body.build(b, self)));
    }

    /// Defines a declared method; the body gets `this` and the parameter.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    public <R, A1> void define(MethodRef1<Self, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        Slot slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name());
        Expr<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        slot.define(member(slot, root, List.of(p1), b -> body.build(b, self, p1)));
    }

    /// Defines a declared method; the body gets `this` and the parameters.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    public <R, A1, A2> void define(MethodRef2<Self, R, A1, A2> method, InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        Slot slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name());
        Expr<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        slot.define(member(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2)));
    }

    /// Defines a declared `void` method; the body gets `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and `this`
    public void define(VoidMethodRef0<Self> method, InstanceBody0<Self, VoidBody, Void> body) {
        Slot slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name());
        Expr<Self> self = thisOf(root);
        slot.define(member(slot, root, List.of(), b -> body.build(b, self)));
    }

    /// Defines a declared `void` method; the body gets `this` and the parameter.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    public <A1> void define(VoidMethodRef1<Self, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name());
        Expr<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        slot.define(member(slot, root, List.of(p1), b -> body.build(b, self, p1)));
    }

    /// Defines a declared `void` method; the body gets `this` and the parameters.
    ///
    /// @param method the declared method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    public <A1, A2> void define(VoidMethodRef2<Self, A1, A2> method, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name());
        Expr<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        slot.define(member(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2)));
    }

    /// Defines a declared static method; the body gets no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block
    /// @param <R> the result type
    public <R> void define(StaticMethodRef0<R> method, Function<Body<R>, Terminated<R>> body) {
        Slot slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name());
        slot.define(member(slot, root, List.of(), body));
    }

    /// Defines a declared static method; the body gets the parameter, no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    public <R, A1> void define(StaticMethodRef1<R, A1> method, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        Slot slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name());
        Var<A1> p1 = Var.param(method.param1(), root);
        slot.define(member(slot, root, List.of(p1), b -> body.apply(b, p1)));
    }

    /// Defines a declared static `void` method; the body gets no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block
    public void define(VoidStaticMethodRef0 method, Function<VoidBody, Terminated<Void>> body) {
        Slot slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name());
        slot.define(member(slot, root, List.of(), body));
    }

    /// Defines a declared static `void` method; the body gets the parameter, no `this`.
    ///
    /// @param method the declared method
    /// @param body the body, given its block and the parameter
    /// @param <A1> the parameter type
    public <A1> void define(VoidStaticMethodRef1<A1> method, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        Slot slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name());
        Var<A1> p1 = Var.param(method.param1(), root);
        slot.define(member(slot, root, List.of(p1), b -> body.apply(b, p1)));
    }

    /// Defines a declared constructor; the body gets `this`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block and `this`
    public void define(CtorRef0<Self> ctor, InstanceBody0<Self, VoidBody, Void> body) {
        Slot slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor");
        Expr<Self> self = thisOf(root);
        slot.define(member(slot, root, List.of(), b -> body.build(b, self)));
    }

    /// Defines a declared constructor; the body gets `this` and the parameter.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    public <A1> void define(CtorRef1<Self, A1> ctor, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Slot slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor");
        Expr<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        slot.define(member(slot, root, List.of(p1), b -> body.build(b, self, p1)));
    }

    /// Defines a declared constructor; the body gets `this` and the parameters.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    public <A1, A2> void define(CtorRef2<Self, A1, A2> ctor, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Slot slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor");
        Expr<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        slot.define(member(slot, root, List.of(p1, p2), b -> body.build(b, self, p1, p2)));
    }

    // ------------------------------------------------------------------
    // Declare and define in one call
    // ------------------------------------------------------------------

    /// Declares and defines a method, see [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param body the body, given its block and `this`
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> MethodRef0<Self, R> method(String name, TypeToken<R> result, InstanceBody0<Self, Body<R>, R> body) {
        MethodRef0<Self, R> method = declareMethod(name, result);
        define(method, body);
        return method;
    }

    /// Declares and defines a method, see [#declareMethod(String, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> MethodRef1<Self, R, A1> method(
            String name, TypeToken<R> result, TypeToken<A1> p1, InstanceBody1<Self, Body<R>, R, A1> body) {
        MethodRef1<Self, R, A1> method = declareMethod(name, result, p1);
        define(method, body);
        return method;
    }

    /// Declares and defines a method, see [#declareMethod(String, TypeToken, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> method(
            String name,
            TypeToken<R> result,
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        MethodRef2<Self, R, A1, A2> method = declareMethod(name, result, p1, p2);
        define(method, body);
        return method;
    }

    /// Declares and defines a `void` method, see [#declareVoidMethod(String)].
    ///
    /// @param name the method name
    /// @param body the body, given its block and `this`
    /// @return the fact of the method
    public VoidMethodRef0<Self> voidMethod(String name, InstanceBody0<Self, VoidBody, Void> body) {
        VoidMethodRef0<Self> method = declareVoidMethod(name);
        define(method, body);
        return method;
    }

    /// Declares and defines a `void` method, see [#declareVoidMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidMethodRef1<Self, A1> voidMethod(
            String name, TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        VoidMethodRef1<Self, A1> method = declareVoidMethod(name, p1);
        define(method, body);
        return method;
    }

    /// Declares and defines a `void` method, see [#declareVoidMethod(String, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <A1, A2> VoidMethodRef2<Self, A1, A2> voidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        VoidMethodRef2<Self, A1, A2> method = declareVoidMethod(name, p1, p2);
        define(method, body);
        return method;
    }

    /// Declares and defines a static method, see [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param body the body, given its block
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> StaticMethodRef0<R> staticMethod(
            String name, TypeToken<R> result, Function<Body<R>, Terminated<R>> body) {
        StaticMethodRef0<R> method = declareStaticMethod(name, result);
        define(method, body);
        return method;
    }

    /// Declares and defines a static method, see [#declareStaticMethod(String, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param body the body, given its block and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> StaticMethodRef1<R, A1> staticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        StaticMethodRef1<R, A1> method = declareStaticMethod(name, result, p1);
        define(method, body);
        return method;
    }

    /// Declares and defines a static `void` method, see [#declareVoidStaticMethod(String)].
    ///
    /// @param name the method name
    /// @param body the body, given its block
    /// @return the fact of the method
    public VoidStaticMethodRef0 voidStaticMethod(String name, Function<VoidBody, Terminated<Void>> body) {
        VoidStaticMethodRef0 method = declareVoidStaticMethod(name);
        define(method, body);
        return method;
    }

    /// Declares and defines a static `void` method, see [#declareVoidStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param body the body, given its block and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidStaticMethodRef1<A1> voidStaticMethod(
            String name, TypeToken<A1> p1, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        VoidStaticMethodRef1<A1> method = declareVoidStaticMethod(name, p1);
        define(method, body);
        return method;
    }

    /// Declares and defines a constructor, see [#declareConstructor()].
    ///
    /// @param body the body, given its block and `this`
    /// @return the fact of the constructor
    public CtorRef0<Self> constructor(InstanceBody0<Self, VoidBody, Void> body) {
        CtorRef0<Self> ctor = declareConstructor();
        define(ctor, body);
        return ctor;
    }

    /// Declares and defines a constructor, see [#declareConstructor(TypeToken)].
    ///
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    public <A1> CtorRef1<Self, A1> constructor(TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        CtorRef1<Self, A1> ctor = declareConstructor(p1);
        define(ctor, body);
        return ctor;
    }

    /// Declares and defines a constructor, see [#declareConstructor(TypeToken, TypeToken)].
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    public <A1, A2> CtorRef2<Self, A1, A2> constructor(
            TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        CtorRef2<Self, A1, A2> ctor = declareConstructor(p1, p2);
        define(ctor, body);
        return ctor;
    }

    // ------------------------------------------------------------------
    // Escape hatch
    // ------------------------------------------------------------------

    /// Adds a raw, untyped member from [Unsafe#member(me.supcheg.javafile.model.ClassMember)].
    /// It is not checked against the signatures of the typed members.
    ///
    /// @param member the member
    /// @return this builder
    public TypedClassBuilder<Self> add(UnsafeMember member) {
        requireOpen("an unsafe member of " + described);
        Slot slot = slot(member, null, "an unsafe member");
        slot.define(member.member());
        return this;
    }

    // ------------------------------------------------------------------
    // Completion
    // ------------------------------------------------------------------

    /// Completes the declaration: every declared member must be defined.
    ///
    /// @return the members, in declaration order
    /// @throws IllegalStateException if a declared member is not defined
    List<ClassMember> complete() {
        List<String> undefined =
                slots.stream().filter(s -> s.member == null).map(s -> s.what).toList();
        if (!undefined.isEmpty()) {
            throw new IllegalStateException("the declaration of " + described
                    + " is complete, but these declared members are never defined: "
                    + String.join(", ", undefined) + "; define each declared member exactly once");
        }
        complete = true;
        methods = registry.table();
        return slots.stream().map(Slot::member).toList();
    }

    // ------------------------------------------------------------------

    private void requireOpen(String what) {
        if (complete) {
            throw new IllegalStateException("cannot declare or define " + what + ": the declaration of " + described
                    + " is already complete; declare and define members from its TypedClassSpec only");
        }
        Scopes.requireNoneOpen(what);
    }

    private <F extends Invocable> F declare(F member) {
        String what = member.toString();
        requireOpen(what);
        slot(member, member, what).overrides = registry.member(member);
        return member;
    }

    private Slot slot(Object key, @Nullable Invocable fact, String what) {
        Slot slot = new Slot(what, fact);
        slots.add(slot);
        slotsByFact.put(key, slot);
        return slot;
    }

    private Slot definable(Invocable member) {
        String what = member.toString();
        requireOpen(what);
        Slot slot = slotsByFact.get(member);
        if (slot == null) {
            throw new IllegalStateException("cannot define " + what + ": it was not declared by the builder of "
                    + described + "; define only the members declared through its declare* methods");
        }
        if (slot.member != null) {
            throw new IllegalStateException(
                    what + " of " + described + " is already defined; each declared member is defined exactly once");
        }
        return slot;
    }

    private Expr<Self> thisOf(Block<?, ?> root) {
        return Expr.of(new Node.This(root), self);
    }

    /// Builds `root` — the root of its scope tree — from `bodyFn` and lowers
    /// the member it is the body of.
    private static <R, B extends Block<R, B>> ClassMember member(
            Slot slot, B root, List<Var<?>> params, Function<? super B, Terminated<R>> bodyFn) {
        Invocable fact = slot.fact();
        Lowering lowering = new Lowering();
        List<Param> coreParams = new ArrayList<>(params.size());
        for (Var<?> param : params) {
            coreParams.add(
                    new Param(lowering.declareUpfront(param), param.type().typeRef()));
        }
        Terminated<R> terminated = Scopes.within(root, () -> bodyFn.apply(root));
        Block.requireIssuedBy(terminated, root);
        CodeBody code = lowering.lowerBlock(root.instrs());
        List<AnnotationUse> annotations = slot.overrides ? List.of(new AnnotationUse(OVERRIDE, List.of())) : List.of();
        return switch (fact.kind()) {
            case CONSTRUCTOR -> new ConstructorDecl(List.of(), Set.of(Modifier.PUBLIC), coreParams, code, List.of());
            case INSTANCE_METHOD ->
                new MethodDecl(
                        fact.name(),
                        fact.resultType().map(TypeToken::typeRef),
                        annotations,
                        Set.of(Modifier.PUBLIC),
                        List.of(),
                        coreParams,
                        code,
                        List.of());
            case STATIC_METHOD ->
                new MethodDecl(
                        fact.name(),
                        fact.resultType().map(TypeToken::typeRef),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        List.of(),
                        coreParams,
                        code,
                        List.of());
        };
    }

    /// A declared member, defined or not yet.
    private static final class Slot {
        final String what;
        final @Nullable Invocable fact;

        @Nullable
        ClassMember member;

        boolean overrides;

        Slot(String what, @Nullable Invocable fact) {
            this.what = what;
            this.fact = fact;
        }

        Invocable fact() {
            return Objects.requireNonNull(fact);
        }

        ClassMember member() {
            return Objects.requireNonNull(member);
        }

        void define(ClassMember member) {
            this.member = member;
        }
    }

    // ------------------------------------------------------------------
    // Bodies of instance members (§6.5 HOAS: the block, `this`, the parameters)
    // ------------------------------------------------------------------

    /// The body of an instance method or constructor without parameters.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    @FunctionalInterface
    public interface InstanceBody0<Self, B, R> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @return the proof that the body ended
        Terminated<R> build(B body, Expr<Self> self);
    }

    /// The body of an instance method or constructor with one parameter.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    /// @param <A1> the parameter type
    @FunctionalInterface
    public interface InstanceBody1<Self, B, R, A1> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @param p1 the parameter
        /// @return the proof that the body ended
        Terminated<R> build(B body, Expr<Self> self, Var<A1> p1);
    }

    /// The body of an instance method or constructor with two parameters.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    @FunctionalInterface
    public interface InstanceBody2<Self, B, R, A1, A2> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @param p1 the first parameter
        /// @param p2 the second parameter
        /// @return the proof that the body ended
        Terminated<R> build(B body, Expr<Self> self, Var<A1> p1, Var<A2> p2);
    }
}
