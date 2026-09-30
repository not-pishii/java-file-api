package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;

import javax.lang.model.element.TypeElement;
import java.lang.constant.ClassDesc;
import java.util.Map;
import java.util.Optional;

/// What the facts of a full metamodel need to know of the types their
/// signatures mention: the metamodel a token of the type is made from, and
/// whether the type is generic (mini-spec §2.7: a token of another type is
/// made on the spot from `Y_.Data.SHAPE`).
///
/// A type has no target if no metamodel of it is on the classpath or written
/// in this compilation: an annotation interface, a class with `$` in its
/// simple name, a class in the unnamed package.
final class Targets {
    private final Models models;
    private final Map<String, ClassDesc> metamodels;
    private final Map<String, String> unavailable;

    /// @param models the models of the round
    /// @param metamodels the metamodel of every type that has one, by binary name of the type
    /// @param unavailable why a type has none, by binary name, for the types the closure found none for
    Targets(Models models, Map<String, ClassDesc> metamodels, Map<String, String> unavailable) {
        this.models = models;
        this.metamodels = Map.copyOf(metamodels);
        this.unavailable = Map.copyOf(unavailable);
    }

    /// The metamodel of a type and the kind of the type.
    ///
    /// @param desc the class or interface
    /// @return the target, empty if the type has no metamodel
    Optional<Target> of(ClassDesc desc) {
        ClassDesc metamodel = metamodels.get(Models.binaryName(desc));
        if (metamodel == null) {
            return Optional.empty();
        }
        return model(desc).map(model -> new Target(metamodel, model.kind()));
    }

    /// Whether a type has type parameters, so that a mention of it without
    /// type arguments is a raw type.
    ///
    /// @param desc the class or interface
    /// @return `true` if generic; `false` if not, or if the compilation does not have the type
    boolean generic(ClassDesc desc) {
        return model(desc).map(model -> !model.typeParams().isEmpty()).orElse(false);
    }

    /// Why a type that has no metamodel has none.
    ///
    /// @param desc the class or interface
    /// @return the reason, or a general one if the closure did not give one
    String whyNone(ClassDesc desc) {
        return unavailable.getOrDefault(Models.binaryName(desc), "it is not part of the closure");
    }

    private Optional<TypeModel> model(ClassDesc desc) {
        return models.element(desc).flatMap(this::read);
    }

    private Optional<TypeModel> read(TypeElement element) {
        return switch (models.of(element, MemberFilter.NONE)) {
            case Translation.Ok<TypeModel>(TypeModel model) -> Optional.of(model);
            case Translation.Deferred<TypeModel> ignored -> Optional.empty();
            case Translation.Unrepresentable<TypeModel> ignored -> Optional.empty();
        };
    }

    /// The metamodel of a type.
    ///
    /// @param metamodel the metamodel class, whose nested `Data` holds the shape
    /// @param kind the kind of the type, which decides the token class
    record Target(ClassDesc metamodel, DeclaredKind kind) {}
}
