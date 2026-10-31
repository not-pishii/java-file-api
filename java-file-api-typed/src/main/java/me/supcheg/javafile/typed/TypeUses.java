package me.supcheg.javafile.typed;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import java.util.List;

/// Type uses of the typed layer: a reference type written with annotations
/// on it, `@NonNull String` (JLS 9.7.4).
///
/// The token is the one it was, with the annotations on the type it is
/// written as: its facts, its erasure and its place in a signature are
/// unchanged, and javac reads the annotations exactly as written. A
/// primitive type has no such form.
///
/// ```java
/// RefToken<String> nonNull = TypeUses.annotated(String_.TOKEN, NonNull_.annotation().build());
/// ```
public final class TypeUses {
    private TypeUses() {}

    /// A reference type with type-use annotations.
    ///
    /// @param type the type
    /// @param annotations the annotations, in order
    /// @param <T> the type
    /// @return the type, as it is written
    /// @throws IllegalArgumentException if `type` is a primitive type
    public static <T> RefToken<T> annotated(RefToken<T> type, AnnotationRef<?>... annotations) {
        return UnsafeFacts.annotated(
                type, List.of(annotations).stream().map(AnnotationRef::toUse).toArray(AnnotationUse[]::new));
    }

    /// A reference type with the type-use annotations of a list.
    ///
    /// @param type the type
    /// @param annotations the annotations, in order
    /// @param <T> the type
    /// @return the type, as it is written
    /// @throws IllegalArgumentException if `type` is a primitive type
    public static <T> RefToken<T> annotated(RefToken<T> type, List<? extends AnnotationRef<?>> annotations) {
        return annotated(type, annotations.toArray(AnnotationRef[]::new));
    }
}
