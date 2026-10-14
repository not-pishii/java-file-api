package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TargetReader;
import me.supcheg.javafile.facts.TargetType;
import me.supcheg.javafile.facts.TargetType.Difference;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/// The target classpath of a compilation (§5): what an annotation processor
/// that generates code with the typed layer gives to its entry.
///
/// ```java
/// @Facts({Greeter.class})
/// public final class MyGenerator extends AbstractProcessor {
///     private TargetClasspath target;
///
///     @Override
///     public synchronized void init(ProcessingEnvironment env) {
///         super.init(env);
///         target = TargetClasspaths.of(env);
///     }
///
///     @Override
///     public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
///         …
///         try {
///             JavaFile file = TypedJavaFile.class_(target, desc, spec);
///             JavaFileWriter.writeTo(file, processingEnv.getFiler(), element);
///         } catch (FactException e) {
///             // a metamodel that does not hold of this compilation, or a fact lowering rejects there
///             processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
///         }
///         …
///     }
/// }
/// ```
///
/// The metamodels of `MyGenerator` were generated when it was compiled,
/// against the `Greeter` of its own classpath; the compilation it runs in
/// has its own. A type is read here through `javax.lang.model` by the
/// [MirrorTranslator] the `@Facts` processor read it with, so what the two
/// make of one type is the same, [Canonical] form and fingerprint included,
/// and compared by [Conformance]: the type the compilation sees — whatever
/// its class path, module path and `--release` make of it — against the one
/// the metamodel was generated from.
///
/// A target classpath is of one compilation: make it in `init`, not once
/// per class loader — a build tool may keep the classes of a processor for
/// the next compilation, whose types are others.
public final class TargetClasspaths {
    private TargetClasspaths() {}

    /// The target classpath of the compilation of a processing environment.
    ///
    /// @param env the environment of the annotation processor
    /// @return the target classpath, which reads each type once
    public static TargetClasspath of(ProcessingEnvironment env) {
        return UnsafeFacts.targetClasspath(reader(env.getElementUtils(), env.getTypeUtils()));
    }

    /// The reader [#of] makes its target classpath of: for a target
    /// classpath that tells what it read, or reads some types otherwise,
    /// made of a reader of its own by [UnsafeFacts#targetClasspath(TargetReader)].
    ///
    /// @param elements the element utilities of the compilation
    /// @param types the type utilities of the compilation
    /// @return the reader, which reads a type anew every time it is asked
    public static TargetReader reader(Elements elements, Types types) {
        MirrorTranslator translator = new MirrorTranslator(elements, types);
        return (shape, origin) -> read(elements, translator, shape, origin);
    }

    private static TargetType read(
            Elements elements, MirrorTranslator translator, TypeShape<?> shape, ShapeOrigin.Metamodel origin) {
        return switch (find(elements, shape.desc())) {
            case Found.Type(TypeElement type) ->
                MirrorTranslator.refusal(type).map(TargetClasspaths::absent).orElseGet(() -> switch (translator.type(
                        type, MemberFilter.DECLARED_ACCESSIBLE)) {
                    case Translation.Ok<TypeModel>(TypeModel model) ->
                        Conformance.of(origin.fingerprint(), origin.canonical(), model);
                    case Translation.Deferred<TypeModel>(String unresolved) ->
                        absent("type " + type.getQualifiedName() + " mentions " + unresolved
                                + ", which is not on the target classpath");
                    case Translation.Unrepresentable<TypeModel>(String reason) -> absent(reason);
                });
            case Found.Nowhere(String reason) -> absent(reason);
        };
    }

    private static TargetType absent(String reason) {
        return new TargetType.Mismatched(List.of(new Difference.Absent(reason)));
    }

    /// The type of a binary name in the compilation: in whichever module
    /// has it, if only one does.
    private static Found find(Elements elements, ClassDesc desc) {
        String binaryName = desc.descriptorString()
                .substring(1, desc.descriptorString().length() - 1)
                .replace('/', '.');
        // no simple name of a type with a metamodel has a $: it is the separator of a member type
        String name = binaryName.replace('$', '.');
        TypeElement only = elements.getTypeElement(name);
        Set<? extends TypeElement> all = only != null ? Set.of(only) : elements.getAllTypeElements(name);
        List<? extends TypeElement> named = all.stream()
                .filter(type -> elements.getBinaryName(type).contentEquals(binaryName))
                .toList();
        return switch (named.size()) {
            case 0 -> new Found.Nowhere("type " + name + " not found on the target classpath");
            case 1 -> new Found.Type(named.getFirst());
            default ->
                new Found.Nowhere("type " + name + " is in several modules of the target: "
                        + named.stream()
                                .map(elements::getModuleOf)
                                .map(ModuleElement::getQualifiedName)
                                .map(Object::toString)
                                .sorted()
                                .collect(Collectors.joining(", ")));
        };
    }

    /// The type of a name, or why there is none to read.
    private sealed interface Found {
        record Type(TypeElement type) implements Found {}

        record Nowhere(String reason) implements Found {}
    }
}
