package me.supcheg.javafile.typed;

/// The first stage of the header of a class ([Header]): the class has a
/// name, and says what it extends.
///
/// @param <Self> the brand of the class being declared
public final class ClassHeader<Self> extends Header<Self> {

    ClassHeader(ClassDeclaration<Self> declaration) {
        super(declaration);
    }

    /// The class extends `Object`: it inherits the methods of `Object`, as the target classpath
    /// has them.
    ///
    /// @return the stage that names the superinterfaces of the class
    /// @throws IllegalArgumentException if a method the class said it overrides cannot override
    ///     the one of `Object` of its signature
    /// @throws IllegalStateException if the class has its superclass already
    public ObjectHeader<Self> extendsObject() {
        declaration().extendsObject();
        return new ObjectHeader<>(declaration());
    }
}
