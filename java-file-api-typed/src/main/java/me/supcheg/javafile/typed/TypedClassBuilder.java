package me.supcheg.javafile.typed;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Stmt;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;

import java.util.function.BiFunction;
import java.util.function.Function;

/// Builds a typed class declaration (§6.5): a self-branded typed mirror of
/// [me.supcheg.javafile.builder.ClassBuilder]. Declaring a member both adds
/// it to the class and returns its fact — declaration and fact introduction
/// are the same act, so a method can call a sibling declared earlier in the
/// same class through the fact it returned (recursion and mutual calls
/// within the type are typebound).
///
/// This phase-1 slice supports non-generic `final` classes, plain fields,
/// methods/constructors up to arity 2, and one level of self-reference; it
/// does not yet cover `extends`/`implements`, type parameters, or
/// `abstract`/`open` classes (see the phase-1 report's open questions) — a
/// generator analogous to `java-file-api-facts`'s `FactsCodegen` is the
/// natural way to extend it to the full arity range and to the other
/// declaration shapes.
///
/// @param <Self> the brand of the class being declared, unique to one
///     [TypedJavaFile#class_(java.lang.constant.ClassDesc, TypedJavaFile.TypedClassSpec)] call
public final class TypedClassBuilder<Self> {

    private final ClassBuilder core;
    private final FinalClassToken<Self> self;

    TypedClassBuilder(ClassBuilder core, FinalClassToken<Self> self) {
        this.core = core;
        this.self = self;
    }

    /// The fact of the class being declared, usable to call its own members
    /// recursively from within its own bodies.
    public FinalClassToken<Self> self() {
        return self;
    }

    // ------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------

    /// Declares a `public final` field with an initializer.
    public <T> FieldRef<Self, T> field(String name, TypeToken<T> type, Expr<? extends T> initializer) {
        core.withField(name, type.typeRef(), lowerExpr(initializer));
        return UnsafeFacts.field(self, name, type);
    }

    /// Declares a `public` mutable field with an initializer.
    public <T> MutableFieldRef<Self, T> mutableField(String name, TypeToken<T> type, Expr<? extends T> initializer) {
        core.withField(name, type.typeRef(), lowerExpr(initializer));
        return UnsafeFacts.mutableField(self, name, type);
    }

    // ------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------

    public <R> MethodRef0<Self, R> method(String name, TypeToken<R> result, Function<Body<R>, Terminated<R>> body) {
        MethodRef0<Self, R> ref =
                UnsafeFacts.method(self, name, result, MemberTraits.DEFAULT.with(Overridability.FINAL));
        core.withMethod(name, result.typeRef(), mb -> mb.withBody(cb -> attachBody(cb, new Lowering(), body)));
        return ref;
    }

    public <R, A1> MethodRef1<Self, R, A1> method(
            String name, TypeToken<R> result, TypeToken<A1> p1, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        MethodRef1<Self, R, A1> ref =
                UnsafeFacts.method(self, name, result, p1, MemberTraits.DEFAULT.with(Overridability.FINAL));
        Var<A1> param = Var.param(p1);
        Lowering lowering = new Lowering();
        String paramName = lowering.declareUpfront(param);
        core.withMethod(
                name,
                result.typeRef(),
                mb -> mb.withParam(paramName, p1.typeRef())
                        .withBody(cb -> this.<R>attachBody(cb, lowering, b -> body.apply(b, param))));
        return ref;
    }

    public <R, A1, A2> MethodRef2<Self, R, A1, A2> method(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TwoParamBody<R, A1, A2> body) {
        MethodRef2<Self, R, A1, A2> ref =
                UnsafeFacts.method(self, name, result, p1, p2, MemberTraits.DEFAULT.with(Overridability.FINAL));
        Var<A1> param1 = Var.param(p1);
        Var<A2> param2 = Var.param(p2);
        Lowering lowering = new Lowering();
        String name1 = lowering.declareUpfront(param1);
        String name2 = lowering.declareUpfront(param2);
        core.withMethod(
                name,
                result.typeRef(),
                mb -> mb.withParam(name1, p1.typeRef())
                        .withParam(name2, p2.typeRef())
                        .withBody(cb -> this.<R>attachBody(cb, lowering, b -> body.build(b, param1, param2))));
        return ref;
    }

    public <A1> VoidMethodRef1<Self, A1> voidMethod(
            String name, TypeToken<A1> p1, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        VoidMethodRef1<Self, A1> ref =
                UnsafeFacts.voidMethod(self, name, p1, MemberTraits.DEFAULT.with(Overridability.FINAL));
        Var<A1> param = Var.param(p1);
        Lowering lowering = new Lowering();
        String paramName = lowering.declareUpfront(param);
        core.withVoidMethod(
                name,
                mb -> mb.withParam(paramName, p1.typeRef())
                        .withBody(cb -> this.attachVoidBody(cb, lowering, b -> body.apply(b, param))));
        return ref;
    }

    public VoidMethodRef0<Self> voidMethod(String name, Function<VoidBody, Terminated<Void>> body) {
        VoidMethodRef0<Self> ref = UnsafeFacts.voidMethod(self, name, MemberTraits.DEFAULT.with(Overridability.FINAL));
        core.withVoidMethod(name, mb -> mb.withBody(cb -> attachVoidBody(cb, new Lowering(), body)));
        return ref;
    }

    // ------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------

    public CtorRef0<Self> constructor(Function<VoidBody, Terminated<Void>> body) {
        CtorRef0<Self> ref = UnsafeFacts.ctor(self, MemberTraits.DEFAULT);
        core.withConstructor(cb -> cb.withBody(b -> attachVoidBody(b, new Lowering(), body)));
        return ref;
    }

    public <A1> CtorRef1<Self, A1> constructor(TypeToken<A1> p1, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        CtorRef1<Self, A1> ref = UnsafeFacts.ctor(self, p1, MemberTraits.DEFAULT);
        Var<A1> param = Var.param(p1);
        Lowering lowering = new Lowering();
        String paramName = lowering.declareUpfront(param);
        core.withConstructor(cb -> cb.withParam(paramName, p1.typeRef())
                .withBody(b -> attachVoidBody(b, lowering, vb -> body.apply(vb, param))));
        return ref;
    }

    // ------------------------------------------------------------------
    // Escape hatch
    // ------------------------------------------------------------------

    /// Adds a raw, untyped member from [Unsafe#member(me.supcheg.javafile.model.ClassMember)].
    public TypedClassBuilder<Self> add(UnsafeMember member) {
        core.accept(member.member());
        return this;
    }

    // ------------------------------------------------------------------

    private <R> void attachBody(CodeBuilder cb, Lowering lowering, Function<Body<R>, Terminated<R>> bodyFn) {
        Body<R> body = new Body<>();
        Terminated<R> terminated = Scopes.within(body, () -> bodyFn.apply(body));
        Block.requireIssuedBy(terminated, body);
        for (Stmt stmt : lowering.lowerBlock(body.instrs()).statements()) {
            cb.accept(stmt);
        }
    }

    private void attachVoidBody(CodeBuilder cb, Lowering lowering, Function<VoidBody, Terminated<Void>> bodyFn) {
        VoidBody body = new VoidBody();
        Terminated<Void> terminated = Scopes.within(body, () -> bodyFn.apply(body));
        Block.requireIssuedBy(terminated, body);
        for (Stmt stmt : lowering.lowerBlock(body.instrs()).statements()) {
            cb.accept(stmt);
        }
    }

    private <T> me.supcheg.javafile.code.Expr lowerExpr(Expr<? extends T> expr) {
        return new Lowering().lowerExpr(expr.node());
    }

    /// The body of a two-parameter method (§6.5 HOAS params). A named
    /// interface, not `java.util.function.BiFunction`-of-a-`BiFunction`,
    /// because both parameters must be visible to the same call.
    @FunctionalInterface
    public interface TwoParamBody<R, A1, A2> {
        Terminated<R> build(Body<R> body, Var<A1> p1, Var<A2> p2);
    }
}
