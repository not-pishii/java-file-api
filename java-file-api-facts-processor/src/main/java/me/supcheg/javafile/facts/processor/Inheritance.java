package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.Hierarchy;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/// What a type extends and implements (Q13): a requested type inherits
/// members from these types, so each of them gets a full metamodel too, and
/// the members are reached through it (Q6(b)).
///
/// `java.lang.Object` is among the supertypes of an interface as it is
/// among those of a class: an interface that extends no other has the
/// `public` methods of `Object` as its members (JLS 9.2), and a value of an
/// interface is an `Object` (JLS 4.10.2), so `toString()` on it is called
/// through `Object_` like on any class.
final class Inheritance {
    private static final String OBJECT = "java.lang.Object";

    private Inheritance() {}

    /// The superclass and the superinterfaces of a type, and theirs in turn:
    /// each once, the nearer first. A supertype that does not exist is left
    /// out.
    ///
    /// @param type a class, interface, enum or record
    /// @param elements the element utilities of the compilation
    /// @return the supertypes
    static Stream<TypeElement> supertypes(TypeElement type, Elements elements) {
        return Hierarchy.beyond(List.of(type), nearer -> direct(nearer, elements));
    }

    /// The superclass and the superinterfaces a type names itself, the
    /// superclass first, and `Object` for an interface that names none. A
    /// supertype that does not exist is left out, see
    /// [#missing(TypeElement)].
    ///
    /// @param type a class, interface, enum or record
    /// @param elements the element utilities of the compilation
    /// @return the direct supertypes
    static Stream<TypeElement> direct(TypeElement type, Elements elements) {
        return Stream.concat(
                named(type)
                        .filter(supertype -> supertype.getKind() == TypeKind.DECLARED)
                        .map(supertype -> (TypeElement) ((DeclaredType) supertype).asElement()),
                type.getKind().isInterface() && type.getInterfaces().isEmpty()
                        ? Stream.ofNullable(elements.getTypeElement(OBJECT))
                        : Stream.empty());
    }

    /// The superclass and the superinterfaces a type names itself that do
    /// not exist yet: another processor is to generate them. What they
    /// extend and implement in turn is not known before they are there.
    ///
    /// @param type a class, interface, enum or record
    /// @return the names of the missing supertypes, as javac gives them
    static Stream<String> missing(TypeElement type) {
        return named(type)
                .filter(supertype -> supertype.getKind() == TypeKind.ERROR)
                .map(TypeMirror::toString);
    }

    /// Whether a type that does not exist yet may extend or implement a
    /// type, directly or through others: not a `final` class, an enum or a
    /// record, and of a `sealed` type only through a subtype it permits that
    /// such a type may extend — or through one it permits that does not
    /// exist yet itself.
    ///
    /// @param type a class, interface, enum or record
    /// @return `true` if the type may yet turn out a supertype of a type another processor generates
    static boolean open(TypeElement type) {
        return switch (type.getKind()) {
            case ENUM, RECORD, ANNOTATION_TYPE -> false;
            default -> {
                Set<Modifier> modifiers = type.getModifiers();
                if (modifiers.contains(Modifier.FINAL)) {
                    yield false;
                }
                yield !modifiers.contains(Modifier.SEALED)
                        || type.getPermittedSubclasses().stream()
                                .anyMatch(permitted -> permitted.getKind() != TypeKind.DECLARED
                                        || open((TypeElement) ((DeclaredType) permitted).asElement()));
            }
        };
    }

    private static Stream<TypeMirror> named(TypeElement type) {
        return Stream.concat(Stream.of(type.getSuperclass()), type.getInterfaces().stream());
    }
}
