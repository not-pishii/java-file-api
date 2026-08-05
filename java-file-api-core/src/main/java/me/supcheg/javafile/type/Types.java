package me.supcheg.javafile.type;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

/// Factory methods for constructing [TypeRef] and [TypeArg] values.
///
/// This is the intended entry point for building type references; the
/// permitted implementations of [TypeRef] and [TypeArg] are not meant to be
/// instantiated directly.
public final class Types {

    /// The `int` primitive type.
    public static final PrimitiveTypeRef INT = PrimitiveTypeRef.INT;

    /// The `long` primitive type.
    public static final PrimitiveTypeRef LONG = PrimitiveTypeRef.LONG;

    /// The `double` primitive type.
    public static final PrimitiveTypeRef DOUBLE = PrimitiveTypeRef.DOUBLE;

    /// The `float` primitive type.
    public static final PrimitiveTypeRef FLOAT = PrimitiveTypeRef.FLOAT;

    /// The `boolean` primitive type.
    public static final PrimitiveTypeRef BOOLEAN = PrimitiveTypeRef.BOOLEAN;

    /// The `byte` primitive type.
    public static final PrimitiveTypeRef BYTE = PrimitiveTypeRef.BYTE;

    /// The `short` primitive type.
    public static final PrimitiveTypeRef SHORT = PrimitiveTypeRef.SHORT;

    /// The `char` primitive type.
    public static final PrimitiveTypeRef CHAR = PrimitiveTypeRef.CHAR;

    /// The `java.lang.String` type.
    public static final ClassTypeRef STRING = of(ClassDesc.of("java.lang", "String"));

    /// The `java.lang.Object` type.
    public static final ClassTypeRef OBJECT = of(ClassDesc.of("java.lang", "Object"));

    /// The raw `java.util.List` type.
    public static final ClassTypeRef LIST = of(ClassDesc.of("java.util", "List"));

    /// The raw `java.util.Set` type.
    public static final ClassTypeRef SET = of(ClassDesc.of("java.util", "Set"));

    /// The raw `java.util.Map` type.
    public static final ClassTypeRef MAP = of(ClassDesc.of("java.util", "Map"));

    /// The raw `java.util.Collection` type.
    public static final ClassTypeRef COLLECTION = of(ClassDesc.of("java.util", "Collection"));

    /// The raw `java.util.Optional` type.
    public static final ClassTypeRef OPTIONAL = of(ClassDesc.of("java.util", "Optional"));

    private Types() {}

    /// Creates a reference to a non-generic class or interface type.
    ///
    /// @param desc the referenced class or interface
    /// @return a type reference wrapping `desc`
    public static ClassTypeRef of(ClassDesc desc) {
        return new ClassTypeRef(desc);
    }

    /// Creates a reference to a non-generic class or interface type from a
    /// runtime class.
    ///
    /// Requires `type` to be present on the generator's classpath — annotation
    /// processors usually cannot load the classes they are generating code
    /// for, and should use [#of(ClassDesc)] or the `java-file-api-lang-model`
    /// bridge instead.
    ///
    /// @param type the referenced class or interface; must be neither an
    ///             array nor a primitive type — use [#array(TypeRef)] or the
    ///             primitive constants (e.g. [#INT]) for those instead
    /// @return a type reference wrapping `type`
    /// @throws IllegalArgumentException if `type` is an array or a primitive type
    public static ClassTypeRef of(Class<?> type) {
        if (type.isArray() || type.isPrimitive()) {
            throw new IllegalArgumentException("expected a class or interface type, got: " + type);
        }
        return of(ClassDesc.ofDescriptor(type.descriptorString()));
    }

    /// Creates a reference to a non-generic class or interface type carrying
    /// type-use annotations (JLS 9.7.4), e.g. `@NonNull String`.
    ///
    /// @param desc the referenced class or interface
    /// @param annotations the type-use annotations on this reference
    /// @return a type reference wrapping `desc`
    public static ClassTypeRef of(ClassDesc desc, AnnotationUse... annotations) {
        return new ClassTypeRef(desc, List.of(annotations));
    }

    /// Creates a reference to an array type.
    ///
    /// @param component the type of the array's elements
    /// @return a type reference to an array of `component`
    public static ArrayTypeRef array(TypeRef component) {
        return new ArrayTypeRef(component);
    }

    /// Creates a reference to an array type carrying type-use annotations
    /// (JLS 9.7.4) on the array level itself, e.g. `String @NonNull []`.
    ///
    /// @param component the type of the array's elements
    /// @param annotations the type-use annotations on this array level
    /// @return a type reference to an array of `component`
    public static ArrayTypeRef array(TypeRef component, AnnotationUse... annotations) {
        return new ArrayTypeRef(component, List.of(annotations));
    }

    /// Creates a reference to a generic type applied to exact type arguments,
    /// e.g. `Map<String, Integer>`.
    ///
    /// @param raw the generic type's raw class or interface
    /// @param first the first type argument
    /// @param rest any further type arguments, in order
    /// @return a parameterized type reference
    public static ParameterizedTypeRef parameterized(ClassDesc raw, TypeRef first, TypeRef... rest) {
        List<TypeArg> args = new ArrayList<>(rest.length + 1);
        args.add(exact(first));
        for (TypeRef ref : rest) {
            args.add(exact(ref));
        }
        return new ParameterizedTypeRef(raw, args);
    }

    /// Creates a reference to a generic type applied to type arguments,
    /// e.g. `List<? extends Number>`.
    ///
    /// @param raw the generic type's raw class or interface
    /// @param first the first type argument
    /// @param rest any further type arguments, in order
    /// @return a parameterized type reference
    public static ParameterizedTypeRef parameterized(ClassDesc raw, TypeArg first, TypeArg... rest) {
        List<TypeArg> args = new ArrayList<>(rest.length + 1);
        args.add(first);
        args.addAll(List.of(rest));
        return new ParameterizedTypeRef(raw, args);
    }

    /// Creates a reference to a generic type applied to type arguments,
    /// carrying type-use annotations (JLS 9.7.4), e.g. `@NonNull List<String>`.
    ///
    /// @param raw the generic type's raw class or interface
    /// @param args the type arguments applied to `raw`, in order
    /// @param annotations the type-use annotations on this reference
    /// @return a parameterized type reference
    public static ParameterizedTypeRef parameterized(ClassDesc raw, List<TypeArg> args, AnnotationUse... annotations) {
        return new ParameterizedTypeRef(raw, args, List.of(annotations));
    }

    /// Creates a reference to a type variable declared by an enclosing
    /// generic declaration, e.g. `T`.
    ///
    /// @param name the type variable's name
    /// @return a type reference to the variable
    public static TypeVarRef typeVar(String name) {
        return new TypeVarRef(name);
    }

    /// Creates a reference to a type variable declared by an enclosing
    /// generic declaration, carrying type-use annotations (JLS 9.7.4),
    /// e.g. `@NonNull T`.
    ///
    /// @param name the type variable's name
    /// @param annotations the type-use annotations on this reference
    /// @return a type reference to the variable
    public static TypeVarRef typeVar(String name, AnnotationUse... annotations) {
        return new TypeVarRef(name, List.of(annotations));
    }

    /// Creates a type argument that is a concrete type with no wildcard.
    ///
    /// @param type the exact type argument
    /// @return a type argument wrapping `type`
    public static TypeArg exact(TypeRef type) {
        return new ExactTypeArg(type);
    }

    /// Creates an upper-bounded wildcard type argument, e.g. `? extends Number`.
    ///
    /// @param bound the upper bound of the wildcard
    /// @return an upper-bounded wildcard type argument
    public static TypeArg extendsBound(TypeRef bound) {
        return new ExtendsTypeArg(bound);
    }

    /// Creates a lower-bounded wildcard type argument, e.g. `? super Integer`.
    ///
    /// @param bound the lower bound of the wildcard
    /// @return a lower-bounded wildcard type argument
    public static TypeArg superBound(TypeRef bound) {
        return new SuperTypeArg(bound);
    }

    /// Returns the unbounded wildcard type argument `?`.
    ///
    /// @return the unbounded wildcard type argument
    public static TypeArg unbounded() {
        return UnboundedTypeArg.INSTANCE;
    }
}
