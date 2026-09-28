package me.supcheg.javafile.facts.source;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FactLookupException;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;

import java.util.List;

/// The contract of a runtime fact source (§3.8): given a type, prove members
/// of it by full signature, or fail fast.
///
/// An implementation provides [#token()] and [#resolve(MemberQuery)]; the
/// typed lookups — `method("greet", STRING, STRING)` returning a
/// `MethodRef1<O, String, String>`, and so on for every arity family — are
/// default methods built on them. [#resolve(MemberQuery)] throws
/// [FactLookupException] saying what was looked for, what similar members
/// exist, and where the type came from.
///
/// The only runtime source is the mirror source of `java-file-api-lang-model`.
/// Metamodels do not take part: they are plain Java code checked by javac.
///
/// @param <O> the type whose members are looked up
public interface FactSource<O> extends InvocableLookup<O> {

    /// Proves a readable instance field.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such field
    default <T> FieldRef<O, T> field(String name, TypeToken<T> type) {
        resolve(MemberQuery.field(MemberKind.FIELD, name, type));
        return FieldRef.introduce(token(), name, type);
    }

    /// Proves a non-`final` instance field.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such non-final field
    default <T> MutableFieldRef<O, T> mutableField(String name, TypeToken<T> type) {
        resolve(MemberQuery.field(MemberKind.MUTABLE_FIELD, name, type));
        return MutableFieldRef.introduce(token(), name, type);
    }

    /// Proves a readable static field, recording its value if it is a
    /// constant variable.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such static field
    default <T> StaticFieldRef<T> staticField(String name, TypeToken<T> type) {
        Resolution resolution = resolve(MemberQuery.field(MemberKind.STATIC_FIELD, name, type));
        return resolution
                .constantValue()
                .map(value -> StaticFieldRef.introduceConstant(token(), name, type, Sources.<T>constant(value)))
                .orElseGet(() -> StaticFieldRef.introduce(token(), name, type));
    }

    /// Proves a non-`final` static field.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the field fact
    /// @throws FactLookupException if the type has no such non-final static field
    default <T> MutableStaticFieldRef<T> mutableStaticField(String name, TypeToken<T> type) {
        resolve(MemberQuery.field(MemberKind.MUTABLE_STATIC_FIELD, name, type));
        return MutableStaticFieldRef.introduce(token(), name, type);
    }

    /// The class token of [#token()], for constructor lookups.
    ///
    /// @return the class token
    /// @throws FactLookupException if the type is an interface
    default ClassToken<O> classToken() {
        DeclaredToken<O> token = token();
        if (token instanceof ClassToken<O> cls) {
            return cls;
        }
        throw new FactLookupException("constructors", "interface " + token, List.of());
    }
}
