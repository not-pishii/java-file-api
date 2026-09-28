package me.supcheg.javafile.typed;

import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.HashMap;
import java.util.Map;

/// Proves types and members that exist outside the generated code by reading
/// their class files with the ClassFile API. Parsed classes are cached.
///
/// ```java
/// Env env = Env.of(getClass().getClassLoader());
/// MethodSym charAt = env.method(ConstantDescs.CD_String, "charAt",
///         MethodTypeDesc.of(ConstantDescs.CD_char, ConstantDescs.CD_int));
/// ```
///
/// Not thread-safe.
public final class Env {
    private final ClassSource source;
    private final Map<ClassDesc, TypeSym> cache = new HashMap<>();

    /// Creates an environment reading class files from `source`.
    ///
    /// @param source where class files are read from
    public Env(ClassSource source) {
        this.source = source;
    }

    /// Creates an environment reading class files as resources of `loader`,
    /// which includes the JDK's classes.
    ///
    /// @param loader the class loader to read resources from
    /// @return the environment
    public static Env of(ClassLoader loader) {
        return new Env(ClassSource.of(loader));
    }

    /// Proves that a class or interface exists.
    ///
    /// @param desc the class or interface
    /// @return the type
    /// @throws NoSuchSymbolException if no class file is found for `desc`
    /// @throws IllegalArgumentException if `desc` is a primitive or array type
    public TypeSym type(ClassDesc desc) {
        if (!desc.isClassOrInterface()) {
            throw new IllegalArgumentException("expected a class or interface, got " + Descs.name(desc));
        }
        TypeSym type = cache.get(desc);
        if (type == null) {
            byte[] bytes = source.read(desc)
                    .orElseThrow(() -> new NoSuchSymbolException("type " + Descs.name(desc) + " does not exist"));
            type = new ExternalType(this, desc, ClassFile.of().parse(bytes));
            cache.put(desc, type);
        }
        return type;
    }

    /// Proves an instance method, see [TypeSym#method(String, MethodTypeDesc)].
    ///
    /// @param owner the type declaring or inheriting the method
    /// @param name the method name
    /// @param type the erased return and parameter types
    /// @return the method symbol
    /// @throws NoSuchSymbolException if the type or the method does not exist
    public MethodSym method(ClassDesc owner, String name, MethodTypeDesc type) {
        return type(owner).method(name, type);
    }

    /// Proves a static method, see [TypeSym#staticMethod(String, MethodTypeDesc)].
    ///
    /// @param owner the type declaring or inheriting the method
    /// @param name the method name
    /// @param type the erased return and parameter types
    /// @return the method symbol
    /// @throws NoSuchSymbolException if the type or the method does not exist
    public MethodSym staticMethod(ClassDesc owner, String name, MethodTypeDesc type) {
        return type(owner).staticMethod(name, type);
    }

    /// Proves an instance field, see [TypeSym#field(String, ClassDesc)].
    ///
    /// @param owner the type declaring or inheriting the field
    /// @param name the field name
    /// @param type the erased field type
    /// @return the field symbol
    /// @throws NoSuchSymbolException if the type or the field does not exist
    public FieldSym field(ClassDesc owner, String name, ClassDesc type) {
        return type(owner).field(name, type);
    }

    /// Proves a static field, see [TypeSym#staticField(String, ClassDesc)].
    ///
    /// @param owner the type declaring or inheriting the field
    /// @param name the field name
    /// @param type the erased field type
    /// @return the field symbol
    /// @throws NoSuchSymbolException if the type or the field does not exist
    public FieldSym staticField(ClassDesc owner, String name, ClassDesc type) {
        return type(owner).staticField(name, type);
    }

    /// Proves a constructor, see [TypeSym#ctor(ClassDesc...)].
    ///
    /// @param owner the class declaring the constructor
    /// @param params the erased parameter types
    /// @return the constructor symbol
    /// @throws NoSuchSymbolException if the type or the constructor does not exist
    public CtorSym ctor(ClassDesc owner, ClassDesc... params) {
        return type(owner).ctor(params);
    }
}
