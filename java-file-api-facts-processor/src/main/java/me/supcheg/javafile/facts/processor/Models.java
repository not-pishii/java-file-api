package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.constant.ClassDesc;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/// The types of one round, as [MirrorTranslator] reads them, each read at
/// most once per member filter; and the elements of the types a model
/// mentions by [ClassDesc].
///
/// A new instance per round: a type deferred in one round may be complete
/// in the next.
final class Models {
    private final Elements elements;
    private final MirrorTranslator translator;
    private final Map<Key, Translation<TypeModel>> models = new HashMap<>();

    /// @param elements the element utilities of the compilation
    /// @param translator the translator of the compilation
    Models(Elements elements, MirrorTranslator translator) {
        this.elements = elements;
        this.translator = translator;
    }

    /// The model of a type.
    ///
    /// @param type the type
    /// @param filter which members to read
    /// @return the model, or why there is none
    Translation<TypeModel> of(TypeElement type, MemberFilter filter) {
        return models.computeIfAbsent(new Key(binaryName(type), filter), _ -> translator.type(type, filter));
    }

    /// The binary name of a type, `java.util.Map$Entry`.
    ///
    /// @param type the type
    /// @return the binary name
    String binaryName(TypeElement type) {
        return elements.getBinaryName(type).toString();
    }

    /// The element of a class or interface a model mentions, found by the
    /// canonical name its binary name gives with every `$` read as the
    /// separator of a member type — as `java-file-api-core` reads a
    /// [ClassDesc] too. A class with `$` in its simple name is not found:
    /// the generated code could not name it anyway.
    ///
    /// @param desc the class or interface
    /// @return the element, empty if the compilation does not have it by that name
    Optional<TypeElement> element(ClassDesc desc) {
        String binary = binaryName(desc);
        String packageName = desc.packageName();
        String prefix = packageName.isEmpty() ? "" : packageName + ".";
        return Optional.ofNullable(elements.getTypeElement(
                        prefix + binary.substring(prefix.length()).replace('$', '.')))
                .filter(type -> binaryName(type).equals(binary));
    }

    /// The binary name a [ClassDesc] of a class or interface stands for.
    ///
    /// @param desc the class or interface
    /// @return the binary name
    static String binaryName(ClassDesc desc) {
        String descriptor = desc.descriptorString();
        return descriptor.substring(1, descriptor.length() - 1).replace('/', '.');
    }

    private record Key(String binaryName, MemberFilter filter) {}
}
