package me.supcheg.javafile.facts.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Optional;

/// An element annotated with `@Facts`, by name (mini-spec §8): javac does
/// not pass an annotated element again in a later round, and an element of
/// an earlier round is not meant to be used in a later one, so the
/// processor remembers where `@Facts` is and looks the element up again in
/// every round.
sealed interface Site extends Comparable<Site> {

    /// The site of an annotated element.
    ///
    /// @param element a class, interface, enum, record or package
    /// @param elements the element utilities of the compilation
    /// @return the site
    static Site of(Element element, Elements elements) {
        return switch (element) {
            case PackageElement pkg -> new PackageSite(pkg.getQualifiedName().toString());
            case TypeElement type ->
                new TypeSite(
                        type.getQualifiedName().toString(),
                        elements.getPackageOf(type).getQualifiedName().toString());
            default -> throw new IllegalArgumentException("@Facts is on types and packages only, got " + element);
        };
    }

    /// The qualified name of the type or package.
    ///
    /// @return the name
    String name();

    /// The package the site is in.
    ///
    /// @return the package name, `""` for the unnamed package
    String packageName();

    /// Looks the element up in the current round.
    ///
    /// @param elements the element utilities of the compilation
    /// @return the element, empty if it no longer exists
    Optional<? extends Element> resolve(Elements elements);

    @Override
    default int compareTo(Site other) {
        return name().compareTo(other.name());
    }

    /// A class, interface, enum or record annotated with `@Facts`.
    ///
    /// @param name the canonical name
    /// @param packageName the package of the type
    record TypeSite(String name, String packageName) implements Site {
        @Override
        public Optional<TypeElement> resolve(Elements elements) {
            return Optional.ofNullable(elements.getTypeElement(name));
        }
    }

    /// A `package-info` annotated with `@Facts`.
    ///
    /// @param name the package name
    record PackageSite(String name) implements Site {
        @Override
        public String packageName() {
            return name;
        }

        @Override
        public Optional<PackageElement> resolve(Elements elements) {
            return Optional.ofNullable(elements.getPackageElement(name));
        }
    }
}
