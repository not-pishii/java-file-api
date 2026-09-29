package me.supcheg.javafile.facts;

/// The kind of a declared type, as its [TypeShape] records it. Each kind
/// decides the one token class a type of that kind gets: a
/// `TypeShape<DeclaredKind.FinalClass>` makes only [FinalClassToken]s, a
/// `TypeShape<DeclaredKind.Interface>` only [InterfaceToken]s, and so on, so
/// a shape and a token of different kinds do not compile together.
///
/// Each kind is a class of its own with a single instance, the constants of
/// this interface; the class is the phantom of [TypeShape], the instance its
/// runtime value. A `switch` over the kinds is exhaustive without `default`.
///
/// Records are final classes; annotation interfaces are not supported yet.
public sealed interface DeclaredKind
        permits DeclaredKind.FinalClass,
                DeclaredKind.OpenClass,
                DeclaredKind.AbstractClass,
                DeclaredKind.Interface,
                DeclaredKind.EnumClass {

    /// A `final` class, a record included.
    FinalClass FINAL_CLASS = new FinalClass();

    /// A class that is neither `abstract` nor `final`.
    OpenClass OPEN_CLASS = new OpenClass();

    /// An `abstract` class.
    AbstractClass ABSTRACT_CLASS = new AbstractClass();

    /// An interface.
    Interface INTERFACE = new Interface();

    /// An enum class (JLS 8.9).
    EnumClass ENUM_CLASS = new EnumClass();

    /// The kind of a `final` class, a record included.
    final class FinalClass implements DeclaredKind {
        private FinalClass() {}

        @Override
        public String toString() {
            return "final class";
        }
    }

    /// The kind of a class that is neither `abstract` nor `final`.
    final class OpenClass implements DeclaredKind {
        private OpenClass() {}

        @Override
        public String toString() {
            return "open class";
        }
    }

    /// The kind of an `abstract` class.
    final class AbstractClass implements DeclaredKind {
        private AbstractClass() {}

        @Override
        public String toString() {
            return "abstract class";
        }
    }

    /// The kind of an interface.
    final class Interface implements DeclaredKind {
        private Interface() {}

        @Override
        public String toString() {
            return "interface";
        }
    }

    /// The kind of an enum class.
    final class EnumClass implements DeclaredKind {
        private EnumClass() {}

        @Override
        public String toString() {
            return "enum class";
        }
    }
}
