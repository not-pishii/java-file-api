package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;

/// The declarations of the methods and constructors whose last parameter is of variable arity, `T... v` (JLS
/// 8.4.1): the body sees the parameter as the array `T[]`, and calls of the member give the arguments of the parameter
/// one by one ([Expressions#varargs(ArrayToken, Expr[])]) or the array.
///
/// What javac says of the declaration is told by the type of the elements, so the declaration does not have to:
/// where it is not reifiable, javac warns of possible heap pollution (`-Xlint:unchecked`), and the member is declared
/// `@SafeVarargs` — which javac accepts of a `static` method, of a constructor and of a `final` method alone, and
/// which the methods here are —, and the array of its parameter is held to what the annotation vouches for: it is
/// read by `at`, measured by `length`, stored into by `assignAt` and looped over by `forEach`, and leaves the member
/// nowhere. Where the elements are reifiable javac would warn of the annotation as redundant (`-Xlint:varargs`), and
/// there is none.
///
/// Made by [Declarations#variableArity()], so `throwing(...)` composes with it.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class VariableArityDeclarations<Self, S> {
    private final Declarations<Self, S> declarations;

    VariableArityDeclarations(Declarations<Self, S> declarations) {
        this.declarations = declarations;
    }

    /// The traits of a member of variable arity: `traits`, with the arity its last parameter has.
    private MemberTraits traits(MemberTraits traits, ArrayToken<?, ?> last) {
        return declarations.traits(
                traits.with(Tokens.isReifiable(last.component()) ? Arity.VARIABLE : Arity.SAFE_VARARGS));
    }

    /// Declares a `public` constructor whose only parameter is of variable arity.
    ///
    /// @param last the array type of the parameter
    /// @param <A> the array type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if javac would reject the constructor
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A> CtorRef1<Self, A> declareConstructor(ArrayToken<A, ?> last) {
        return declarations.declare(
                UnsafeInvocables.ctor(declarations.builder().self(), last, traits(MemberTraits.DEFAULT, last)));
    }

    /// Declares a `public` constructor with a parameter before the one of variable arity.
    ///
    /// @param p1 the first parameter type
    /// @param last the array type of the last parameter
    /// @param <A1> the first parameter type
    /// @param <A> the array type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if javac would reject the constructor
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1, A> CtorRef2<Self, A1, A> declareConstructor(TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declarations.declare(
                UnsafeInvocables.ctor(declarations.builder().self(), p1, last, traits(MemberTraits.DEFAULT, last)));
    }

    /// Declares a `public` method returning `R` whose only parameter is of variable arity.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param last the array type of the parameter
    /// @param <R> the result type
    /// @param <A> the array type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A> MethodRef1<Self, R, A> declareMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declarations.declare(UnsafeInvocables.method(
                declarations.builder().self(), name, result, last, traits(MemberTraits.FINAL, last)));
    }

    /// Declares a `public` method with a parameter before the one of variable arity.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param last the array type of the last parameter
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A> the array type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1, A> MethodRef2<Self, R, A1, A> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declarations.declare(UnsafeInvocables.method(
                declarations.builder().self(), name, result, p1, last, traits(MemberTraits.FINAL, last)));
    }

    /// Declares a `public void` method whose only parameter is of variable arity.
    ///
    /// @param name the method name
    /// @param last the array type of the parameter
    /// @param <A> the array type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A> VoidMethodRef1<Self, A> declareVoidMethod(String name, ArrayToken<A, ?> last) {
        return declarations.declare(UnsafeInvocables.voidMethod(
                declarations.builder().self(), name, last, traits(MemberTraits.FINAL, last)));
    }

    /// Declares a `public void` method with a parameter before the one of variable arity.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param last the array type of the last parameter
    /// @param <A1> the first parameter type
    /// @param <A> the array type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1, A> VoidMethodRef2<Self, A1, A> declareVoidMethod(String name, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declarations.declare(UnsafeInvocables.voidMethod(
                declarations.builder().self(), name, p1, last, traits(MemberTraits.FINAL, last)));
    }

    /// Declares a `public static` method whose only parameter is of variable arity.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param last the array type of the parameter
    /// @param <R> the result type
    /// @param <A> the array type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A> StaticMethodRef1<R, A> declareStaticMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declarations.declare(UnsafeInvocables.staticMethod(
                declarations.builder().self(), name, result, last, traits(MemberTraits.FINAL, last)));
    }

    /// Declares a `public static void` method whose only parameter is of variable arity.
    ///
    /// @param name the method name
    /// @param last the array type of the parameter
    /// @param <A> the array type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A> VoidStaticMethodRef1<A> declareVoidStaticMethod(String name, ArrayToken<A, ?> last) {
        return declarations.declare(UnsafeInvocables.voidStaticMethod(
                declarations.builder().self(), name, last, traits(MemberTraits.FINAL, last)));
    }
}
