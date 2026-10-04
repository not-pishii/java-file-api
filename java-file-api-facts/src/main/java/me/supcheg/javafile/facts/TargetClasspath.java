package me.supcheg.javafile.facts;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.Stream;

/// The classpath the generated code is compiled against, as the typed layer
/// needs it (§5): the proof that the metamodels the generator uses hold
/// there.
///
/// A metamodel is generated when the generator is compiled, against one
/// version of a library; the generator runs in another compilation, which
/// may have another version — a method removed, an overload added, another
/// `throws` clause. Lowering therefore asks the target classpath about every
/// type it renders code of, and there is no lowering without one: the entry
/// of the typed layer takes it.
///
/// - [#verify(TypeToken)] checks every metamodel a type is made of, and
///   throws a [TargetClasspathMismatchException] for one that does not hold;
/// - [#methods(TypeShape)] gives the methods of a type as the target
///   classpath has them, which is what javac chooses an overload among.
///
/// Only a shape of origin [ShapeOrigin.Metamodel] is checked: the others
/// are vouched for by hand or read off the target itself. A shape is read
/// off the target once, when it is first asked about, and never if it is
/// not: a metamodel the generator does not use may well be stale.
///
/// A target classpath is good for one compilation. That of an annotation
/// processor is `TargetClasspaths.of(processingEnv)` of
/// `java-file-api-lang-model`, made anew in every `init`; one that checks
/// nothing is [UnsafeFacts#unverifiedClasspath()].
public final class TargetClasspath {
    private final TargetReader reader;
    private final Map<TypeShape<?>, TargetType> read = Collections.synchronizedMap(new IdentityHashMap<>());

    TargetClasspath(TargetReader reader) {
        this.reader = reader;
    }

    /// Checks the metamodels a type is made of against the target
    /// classpath: that of the type itself, of its type arguments and of
    /// the component of an array. A primitive type and a type variable
    /// have none.
    ///
    /// @param type the type
    /// @throws TargetClasspathMismatchException if a metamodel does not hold on the target classpath
    public void verify(TypeToken<?> type) {
        shapes(type).forEach(this::read);
    }

    /// The methods of a type as the target classpath has them: those of
    /// the shape, unless it is of a metamodel generated from another version
    /// of the type that still holds — then those of the target.
    ///
    /// @param shape the shape of the type
    /// @return the method table template, in terms of the type parameters of `shape`
    /// @throws TargetClasspathMismatchException if `shape` is of a metamodel that does not hold on the target
    ///                                          classpath
    /// @throws IllegalStateException if the methods of the type are not known yet: the type is still
    ///                               being declared
    public MethodTableTemplate methods(TypeShape<?> shape) {
        return switch (read(shape)) {
            case TargetType.Changed(MethodTableTemplate methods) -> methods;
            case TargetType.Unchanged _ -> shape.methods();
        };
    }

    /// What the target classpath has of the type of a shape, read once.
    ///
    /// @throws TargetClasspathMismatchException if the shape is of a metamodel that does not hold
    private TargetType.Holds read(TypeShape<?> shape) {
        return switch (shape.origin()) {
            case ShapeOrigin.Metamodel metamodel ->
                switch (read.computeIfAbsent(shape, _ -> checked(shape, reader.read(shape, metamodel)))) {
                    case TargetType.Mismatched mismatched ->
                        throw new TargetClasspathMismatchException(metamodel, shape.desc(), mismatched);
                    case TargetType.Holds holds -> holds;
                };
            case ShapeOrigin.Mirror _, ShapeOrigin.Unsafe _, ShapeOrigin.Builtin _ -> TargetType.UNCHANGED;
        };
    }

    /// What a reader found, if it can be of the shape.
    private static TargetType checked(TypeShape<?> shape, TargetType found) {
        if (found instanceof TargetType.Changed(MethodTableTemplate methods)
                && methods.typeParameterCount() > shape.typeParameters().size()) {
            throw new IllegalStateException("the method table of " + shape + " on the target classpath refers to type"
                    + " parameter #" + (methods.typeParameterCount() - 1) + ", but the type has "
                    + shape.typeParameters().size() + " type parameters");
        }
        return found;
    }

    /// The shapes a type is made of: its own, and those of its type
    /// arguments.
    private static Stream<TypeShape<?>> shapes(TypeToken<?> type) {
        return switch (type) {
            case PrimitiveToken<?, ?, ?> _ -> Stream.empty();
            case TypeVarToken<?> _ -> Stream.empty();
            case ArrayToken<?, ?> array -> shapes(array.component());
            case DeclaredToken<?> declared ->
                Stream.concat(
                        Stream.of(declared.shape()),
                        declared.typeArguments().stream().flatMap(argument -> switch (argument) {
                            case TokenArg.Exact(RefToken<?> exact) -> shapes(exact);
                            case TokenArg.Extends(RefToken<?> bound) -> shapes(bound);
                            case TokenArg.Super(RefToken<?> bound) -> shapes(bound);
                            case TokenArg.Unbounded _ -> Stream.empty();
                        }));
        };
    }
}
