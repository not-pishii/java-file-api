package me.supcheg.javafile.facts;

/// Reads the types of a target classpath for a [TargetClasspath]: tells how
/// the type of a metamodel there compares with the one the metamodel was
/// generated from.
///
/// The reader of a compilation is
/// `me.supcheg.javafile.langmodel.mirror.TargetClasspaths` of
/// `java-file-api-lang-model`, which reads the types through
/// `javax.lang.model` with the translator the `@Facts` processor generated
/// the metamodel with. A [TargetClasspath] asks it once per shape.
///
/// A reader that answers [TargetType#UNCHANGED] without looking vouches for
/// the metamodel, as a factory of [UnsafeFacts] does: so a target classpath
/// is made of a reader only by [UnsafeFacts#targetClasspath(TargetReader)].
@FunctionalInterface
public interface TargetReader {

    /// Compares the type of a shape on the target classpath with the type
    /// its metamodel was generated from.
    ///
    /// @param shape the shape the metamodel holds
    /// @param origin the origin of `shape`: the metamodel, the fingerprint of the type and its canonical form
    /// @return what the target classpath has of the type
    TargetType read(TypeShape<?> shape, ShapeOrigin.Metamodel origin);
}
