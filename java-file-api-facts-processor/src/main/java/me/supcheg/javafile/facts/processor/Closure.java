package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.FieldModel;
import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.MemberModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.langmodel.mirror.SamModel;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;

import javax.lang.model.element.TypeElement;
import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/// What the full metamodel of a requested type needs (mini-spec §3): the
/// model of the type with its declared `public` members (Q6), and a
/// token-only metamodel of every class or interface in the signatures of
/// those members — parameters, results, fields, `throws`, type arguments,
/// the bounds of the type parameters of the type and of its members.
///
/// A functional interface that is not generic has a fact of its single
/// abstract method whether it declares the method or inherits it, so the
/// types of that signature are in the closure too.
///
/// The closure has depth 1: a token-only metamodel needs only the shape of
/// its type, which describes supertypes and methods by descriptors, not by
/// tokens, so nothing further is needed.
final class Closure {
    private Closure() {}

    /// The closure of one requested type.
    ///
    /// @param requested the type
    /// @param models the models of the round
    /// @return the closure, or why there is none yet or at all
    static Outcome of(TypeElement requested, Models models) {
        TypeModel full;
        switch (models.of(requested, MemberFilter.DECLARED_PUBLIC)) {
            case Translation.Ok<TypeModel>(TypeModel value) -> full = value;
            case Translation.Deferred<TypeModel>(String unresolved) -> {
                return new Outcome.Waiting(unresolved);
            }
            case Translation.Unrepresentable<TypeModel>(String reason) -> {
                return new Outcome.Rejected(reason);
            }
        }
        if (!full.nonPublicBoundTypes().isEmpty()) {
            return new Outcome.Rejected("the bounds of the type parameters of " + requested.getQualifiedName()
                    + " mention types that are not public: " + String.join(", ", full.nonPublicBoundTypes()));
        }
        List<ClassDesc> signatureTypes = signatureTypes(full);
        if (full.typeParams().isEmpty()) {
            // the sam of a functional interface is a fact even where it is inherited: its signature is mentioned too
            switch (models.sam(requested)) {
                case Translation.Ok<Optional<SamModel>>(Optional<SamModel> sam) ->
                    sam.ifPresent(found -> MemberPlan.mentions(List.of(found.method()), signatureTypes));
                case Translation.Deferred<Optional<SamModel>>(String unresolved) -> {
                    return new Outcome.Waiting(unresolved);
                }
                case Translation.Unrepresentable<Optional<SamModel>> ignored -> {}
            }
        }
        TreeSet<String> mentioned = new TreeSet<>();
        for (ClassDesc desc : signatureTypes) {
            mentioned.add(Models.binaryName(desc));
        }
        mentioned.remove(Models.binaryName(full.desc()));
        SortedMap<String, TypeElement> types = new TreeMap<>();
        List<Unavailable> unavailable = new ArrayList<>();
        for (String binaryName : mentioned) {
            ClassDesc desc = ClassDesc.of(binaryName);
            if (desc.packageName().isEmpty()) {
                unavailable.add(new Unavailable(binaryName, "a metamodel in a named package cannot refer to it"));
                continue;
            }
            Optional<TypeElement> element = models.element(desc);
            if (element.isEmpty()) {
                unavailable.add(new Unavailable(binaryName, "a class with $ in its simple name is not supported yet"));
                continue;
            }
            switch (models.of(element.get(), MemberFilter.NONE)) {
                case Translation.Ok<TypeModel> ignored -> types.put(binaryName, element.get());
                case Translation.Deferred<TypeModel>(String unresolved) -> {
                    return new Outcome.Waiting(unresolved);
                }
                case Translation.Unrepresentable<TypeModel>(String reason) ->
                    unavailable.add(new Unavailable(binaryName, reason));
            }
        }
        return new Outcome.Ready(full, types, unavailable);
    }

    private static List<ClassDesc> signatureTypes(TypeModel model) {
        List<ClassDesc> found = new ArrayList<>();
        Mentions.of(model.typeParams(), found);
        for (MemberModel member : model.members()) {
            switch (member) {
                case MethodModel method -> {
                    Mentions.of(method.typeParams(), found);
                    method.result().ifPresent(result -> Mentions.of(result, found));
                    method.params().forEach(param -> Mentions.of(param, found));
                    method.throwsTypes().forEach(thrown -> Mentions.of(thrown, found));
                }
                case CtorModel ctor -> {
                    Mentions.of(ctor.typeParams(), found);
                    ctor.params().forEach(param -> Mentions.of(param, found));
                    ctor.throwsTypes().forEach(thrown -> Mentions.of(thrown, found));
                }
                case FieldModel field -> Mentions.of(field.type(), found);
            }
        }
        return found;
    }

    /// The closure of a requested type.
    sealed interface Outcome {

        /// The full metamodel can be generated.
        ///
        /// @param full the model of the type with its declared public members
        /// @param signatureTypes the classes and interfaces the signatures mention, by binary name
        /// @param unavailable the mentioned types no metamodel can be made of
        record Ready(TypeModel full, SortedMap<String, TypeElement> signatureTypes, List<Unavailable> unavailable)
                implements Outcome {}

        /// The type or a type it mentions is not generated yet.
        ///
        /// @param unresolved the missing type, as javac names it
        record Waiting(String unresolved) implements Outcome {}

        /// No full metamodel can be made of the type.
        ///
        /// @param reason why, for a diagnostic
        record Rejected(String reason) implements Outcome {}
    }

    /// A type the signatures mention that no metamodel can be made of, such
    /// as an annotation interface: the members that mention it get no
    /// facts.
    ///
    /// @param type the binary name
    /// @param reason why
    record Unavailable(String type, String reason) {}
}
