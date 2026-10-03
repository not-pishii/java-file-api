package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.MemberFilter;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.SamModel;
import me.supcheg.javafile.langmodel.mirror.Translation;
import me.supcheg.javafile.langmodel.mirror.TypeModel;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.constant.ClassDesc;
import java.util.HashMap;
import java.util.List;
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
    private final Types types;
    private final MirrorTranslator translator;
    private final Map<Key, Translation<TypeModel>> models = new HashMap<>();
    private final Map<String, Translation<Optional<SamModel>>> sams = new HashMap<>();

    /// @param elements the element utilities of the compilation
    /// @param types the type utilities of the compilation
    Models(Elements elements, Types types) {
        this.elements = elements;
        this.types = types;
        this.translator = new MirrorTranslator(elements, types);
    }

    /// The translator of the compilation.
    ///
    /// @return the translator
    MirrorTranslator translator() {
        return translator;
    }

    /// The single abstract method of a functional interface.
    ///
    /// @param type the interface
    /// @return the method, see [MirrorTranslator#sam(TypeElement)]
    Translation<Optional<SamModel>> sam(TypeElement type) {
        return sams.computeIfAbsent(binaryName(type), _ -> translator.sam(type));
    }

    /// The model of a type.
    ///
    /// @param type the type
    /// @param filter which members to read
    /// @return the model, or why there is none
    Translation<TypeModel> of(TypeElement type, MemberFilter filter) {
        return models.computeIfAbsent(new Key(binaryName(type), filter), _ -> translator.type(type, filter));
    }

    /// The fields, constructors and methods a full metamodel of a type has
    /// facts of: the `public` ones it declares, then the ones it adopts from
    /// its supertypes that are not `public`.
    ///
    /// @param type the type
    /// @return the members, see [MirrorTranslator#members(TypeElement)]
    List<Element> members(TypeElement type) {
        return translator.members(type);
    }

    /// The types of the parameters of a method or constructor as a member of
    /// a type, which for a member the type inherits are in terms of the
    /// type: `String`, where `Hidden<T>` declares `set(T)` and the type
    /// extends `Hidden<String>`.
    ///
    /// @param owner the type
    /// @param member a member of `owner`, declared or inherited
    /// @return the types, empty for a field
    List<? extends TypeMirror> parameters(TypeElement owner, Element member) {
        return member instanceof ExecutableElement
                ? ((ExecutableType) types.asMemberOf((DeclaredType) owner.asType(), member)).getParameterTypes()
                : List.of();
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
