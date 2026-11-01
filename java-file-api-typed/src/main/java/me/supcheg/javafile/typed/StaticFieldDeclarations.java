package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.model.Modifier;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

/// The declarations of the static fields of a record or an enum (§6.5): what a type that is not a `final`
/// class declares of them, and the declarations of its methods ([TypeMemberDeclarations]) with it. An
/// interface has none of these: a field of an interface is a constant, declared by
/// [TypeMemberDeclarations#declareConstant(String, TypeToken)].
///
/// Like a member declared by its name, a static field is declared here and given its initializer by the
/// bodies of the type ([TypedTypeBodies#initialize(StaticFieldRef, Expr)]), which a static initializer is of.
///
/// @param <Self> the brand of the type being declared
abstract class StaticFieldDeclarations<Self> extends TypeMemberDeclarations<Self> {

    StaticFieldDeclarations(TargetClasspath target, ClassDesc desc, String kind) {
        super(target, desc, kind);
    }

    /// Declares a `public static final` field of the type, to be given its initializer by its bodies.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter — which a `static` member has not
    ///     in scope (JLS 8.1.2) —, or the declarations have ended
    public final <T> StaticFieldRef<T> declareStaticField(String name, TypeToken<T> type) {
        return declareStaticField(name, type, new AnnotationRef<?>[0]);
    }

    /// Declares a `public static final` field of the type that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read but not assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, or an annotation type
    ///     cannot annotate a field
    /// @throws IllegalStateException if the type mentions a type parameter, or the declarations have ended
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> StaticFieldRef<T> declareStaticField(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        return declaredStatic(
                UnsafeFacts.staticField(self(), name, type),
                Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
                List.of(annotations));
    }

    /// Declares a `public static` field of the type, not `final`, to be given its initializer by its bodies
    /// and assigned by them later.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already
    /// @throws IllegalStateException if the type mentions a type parameter, or the declarations have ended
    public final <T> MutableStaticFieldRef<T> declareMutableStaticField(String name, TypeToken<T> type) {
        return declareMutableStaticField(name, type, new AnnotationRef<?>[0]);
    }

    /// Declares a `public static` field of the type, not `final`, that declares `annotations`.
    ///
    /// @param name the field name
    /// @param type the field type
    /// @param annotations the annotations, in order
    /// @param <T> the field type
    /// @return the fact of the field, which can be read and assigned
    /// @throws IllegalArgumentException if a field of that name is declared already, or an annotation type
    ///     cannot annotate a field
    /// @throws IllegalStateException if the type mentions a type parameter, or the declarations have ended
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final <T> MutableStaticFieldRef<T> declareMutableStaticField(
            String name, TypeToken<T> type, AnnotationRef<?>... annotations) {
        if (!mutableStaticFields()) {
            throw new IllegalArgumentException("a static field of " + described() + " cannot be mutable: Java"
                    + " lets a record declare one, and the typed layer renders a record's static field public"
                    + " static final alone — declare it final, or hold the changing value in an enum or a class");
        }
        return declaredStatic(
                UnsafeFacts.mutableStaticField(self(), name, type),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(annotations));
    }

    /// Whether a static field of the type may be one that is not `final`: a record's may not, since the typed
    /// layer renders it `public static final` alone.
    boolean mutableStaticFields() {
        return true;
    }
}
