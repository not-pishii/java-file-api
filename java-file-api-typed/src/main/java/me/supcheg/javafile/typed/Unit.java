package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/// A set of generated types, each a [TypeHandle], assembled into files by [#build()].
///
/// The optional `header` of each factory configures the declaration itself,
/// e.g. modifiers or annotations; supertypes go through
/// [TypeHandle#extend(TypeSym)] and [TypeHandle#implement(TypeSym)].
///
/// ```java
/// Unit unit = new Unit(Env.of(loader));
/// TypeHandle shape = unit.interface_(SHAPE);
/// MethodSym area = shape.defineAbstractMethod("area", MethodTypeDesc.of(CD_double));
/// List<JavaFile> files = unit.build();
/// ```
public final class Unit {
    private final Env env;
    private final List<TypeHandle> handles = new ArrayList<>();
    private final Set<ClassDesc> descs = new HashSet<>();

    /// Creates a unit whose types may use types proven by `env`.
    ///
    /// @param env the environment for implicit supertypes such as `Object` and `Record`
    public Unit(Env env) {
        this.env = env;
    }

    /// Starts a `public` class.
    ///
    /// @param desc the class
    /// @return the handle
    public TypeHandle class_(ClassDesc desc) {
        return class_(desc, _ -> {});
    }

    /// Starts a class.
    ///
    /// @param desc the class
    /// @param header configures the class declaration
    /// @return the handle
    public TypeHandle class_(ClassDesc desc, Consumer<? super ClassBuilder> header) {
        return add(new TypeHandle(
                env,
                TypeHandle.Kind.CLASS,
                desc,
                new ClassBuilder(desc),
                b -> header.accept((ClassBuilder) b),
                List.of(),
                List.of()));
    }

    /// Starts a `public` interface.
    ///
    /// @param desc the interface
    /// @return the handle
    public TypeHandle interface_(ClassDesc desc) {
        return interface_(desc, _ -> {});
    }

    /// Starts an interface.
    ///
    /// @param desc the interface
    /// @param header configures the interface declaration
    /// @return the handle
    public TypeHandle interface_(ClassDesc desc, Consumer<? super InterfaceBuilder> header) {
        return add(new TypeHandle(
                env,
                TypeHandle.Kind.INTERFACE,
                desc,
                new InterfaceBuilder(desc),
                b -> header.accept((InterfaceBuilder) b),
                List.of(),
                List.of()));
    }

    /// Starts a `public` record; its components are fixed here.
    ///
    /// @param desc the record
    /// @param components the components, in order
    /// @return the handle
    public TypeHandle record(ClassDesc desc, Component... components) {
        return record(desc, _ -> {}, components);
    }

    /// Starts a record; its components are fixed here.
    ///
    /// @param desc the record
    /// @param header configures the record declaration
    /// @param components the components, in order
    /// @return the handle
    public TypeHandle record(ClassDesc desc, Consumer<? super RecordBuilder> header, Component... components) {
        return add(new TypeHandle(
                env,
                TypeHandle.Kind.RECORD,
                desc,
                new RecordBuilder(desc),
                b -> header.accept((RecordBuilder) b),
                List.of(components),
                List.of()));
    }

    /// Starts a `public` enum; its constants are fixed here.
    ///
    /// @param desc the enum
    /// @param constants the constant names, in order
    /// @return the handle
    public TypeHandle enum_(ClassDesc desc, String... constants) {
        return enum_(desc, _ -> {}, constants);
    }

    /// Starts an enum; its constants are fixed here.
    ///
    /// @param desc the enum
    /// @param header configures the enum declaration
    /// @param constants the constant names, in order
    /// @return the handle
    public TypeHandle enum_(ClassDesc desc, Consumer<? super EnumBuilder> header, String... constants) {
        return add(new TypeHandle(
                env,
                TypeHandle.Kind.ENUM,
                desc,
                new EnumBuilder(desc),
                b -> header.accept((EnumBuilder) b),
                List.of(),
                List.of(constants)));
    }

    /// Assembles one file per type, in creation order; the handles accept no more definitions.
    ///
    /// @return the files
    public List<JavaFile> build() {
        return handles.stream().map(TypeHandle::build).toList();
    }

    private TypeHandle add(TypeHandle handle) {
        if (!descs.add(handle.desc())) {
            throw new IllegalArgumentException(handle + " is already defined in this unit");
        }
        handles.add(handle);
        return handle;
    }
}
