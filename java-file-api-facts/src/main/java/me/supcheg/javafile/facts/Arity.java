package me.supcheg.javafile.facts;

/// Whether the last parameter of a method or constructor is of variable
/// arity (JLS 8.4.1), and if it is, whether the declaration vouches for the
/// array javac makes of the arguments.
///
/// A fact tells it ([MemberTraits#arity()]), and so does a declaration of a
/// heritage ([Heritage.Declared#arity()]): `m(String[])` and `m(String...)`
/// are one signature and two methods to the code that calls them.
public sealed interface Arity permits Arity.Fixed, Arity.Variable {

    /// Every parameter is a single argument.
    Arity FIXED = new Fixed();

    /// The last parameter takes the arguments that are left, and nothing is said of the array.
    Arity VARIABLE = new Variable(Safety.UNCHECKED);

    /// The last parameter takes the arguments that are left, of a method declared `@SafeVarargs`.
    Arity SAFE_VARARGS = new Variable(Safety.SAFE_VARARGS);

    /// Every parameter is a single argument.
    record Fixed() implements Arity {}

    /// The last parameter, an array, takes the arguments that are left: `T...`.
    ///
    /// @param safety whether the declaration is `@SafeVarargs`
    record Variable(Safety safety) implements Arity {}

    /// What a declaration of variable arity says of the array of its last parameter.
    enum Safety {
        /// Nothing: a call that passes arguments of a type that is not reifiable for the last
        /// parameter is an unchecked generic array creation, which javac warns of (JLS 15.12.4.2).
        UNCHECKED,
        /// The declaration is `@SafeVarargs`: it does nothing unsafe with the array, and javac does
        /// not warn of such a call (JLS 9.6.4.7).
        SAFE_VARARGS
    }
}
