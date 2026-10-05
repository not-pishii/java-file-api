package me.supcheg.javafile.facts;

import java.io.Serial;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// Thrown by a [TargetClasspath] for a metamodel that does not hold on the
/// target classpath (§5.4): the generator was compiled against another
/// version of the type than the compilation it runs in has.
///
/// It comes out of the entry of the typed layer, as lowering finds the
/// metamodel in use. The message tells every difference of the one
/// metamodel, in the order the canonical form has them, and what to do:
///
/// ```
/// metamodel com.acme.facts.p.Svc_ does not match p.Svc on the target classpath:
///   missing: method overridable greet(java.lang.String) -> java.lang.String throws -
///     similar: method overridable greet(java.lang.CharSequence) -> java.lang.String throws -
///   changed: method overridable close() -> void throws -
///     found: method overridable close() -> void throws java.io.IOException
///   changed: superclasses
///     generated against: java.lang.Object
///     target: p.Base; java.lang.Object
/// The generator was compiled against another p.Svc than this compilation has (another version of
/// its library, or another --release). Generate the metamodels against this version: rebuild the
/// generator against it, or align the versions.
/// ```
///
/// A generator that is an annotation processor reports it as an error of
/// the compilation — `messager.printMessage(Kind.ERROR, e.getMessage(), element)`
/// — instead of letting it out of `process`, which javac prints as a crash
/// of the processor; [#differences()] is there for one that tells them its
/// own way. One that catches [FactException] reports a fact lowering
/// rejects, a [FactLookupException], the same way.
public final class TargetClasspathMismatchException extends FactException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final transient ShapeOrigin.Metamodel metamodel;
    private final transient ClassDesc type;
    private final transient List<TargetType.Difference> differences;

    /// @param metamodel the metamodel that does not hold
    /// @param type the type it is the metamodel of
    /// @param mismatched what the target classpath has otherwise
    TargetClasspathMismatchException(
            ShapeOrigin.Metamodel metamodel, ClassDesc type, TargetType.Mismatched mismatched) {
        super(message(metamodel, type, mismatched.differences()));
        this.metamodel = metamodel;
        this.type = type;
        this.differences = mismatched.differences();
    }

    private static String message(ShapeOrigin.Metamodel metamodel, ClassDesc type, List<TargetType.Difference> all) {
        String name = TypeNames.describe(type);
        return Stream.of(
                        Stream.of("metamodel " + TypeNames.describe(metamodel.metamodel()) + " does not match " + name
                                + " on the target classpath:"),
                        all.stream()
                                .flatMap(difference -> difference.lines().stream())
                                .map(line -> "  " + line),
                        Stream.of("The generator was compiled against another " + name + " than this compilation has"
                                + " (another version of its library, or another --release). Generate the metamodels"
                                + " against this version: rebuild the generator against it, or align the versions."))
                .flatMap(lines -> lines)
                .collect(Collectors.joining("\n"));
    }

    /// The metamodel that does not hold: its class, and the fingerprint of
    /// the type it was generated from.
    ///
    /// @return the origin of the shape of the metamodel
    public ShapeOrigin.Metamodel metamodel() {
        return metamodel;
    }

    /// The type the metamodel is of.
    ///
    /// @return the class or interface
    public ClassDesc type() {
        return type;
    }

    /// What the metamodel says that the target classpath does not.
    ///
    /// @return the differences, in the order of the message; not empty
    public List<TargetType.Difference> differences() {
        return differences;
    }
}
