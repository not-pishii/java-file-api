package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/// A type known to exist: loaded by an [Env] or defined in a [Unit].
///
/// Its lookup methods return a symbol only for a member the type declares or
/// inherits, and throw [NoSuchSymbolException] otherwise. Methods and fields
/// are searched in supertypes too, except static interface methods;
/// constructors only in the type itself.
public abstract sealed class TypeSym permits ExternalType, TypeHandle {
    private final ClassDesc desc;

    TypeSym(ClassDesc desc) {
        this.desc = desc;
    }

    /// The type's descriptor.
    ///
    /// @return the class or interface descriptor
    public final ClassDesc desc() {
        return desc;
    }

    /// Whether this type is an interface.
    ///
    /// @return `true` for an interface
    public abstract boolean isInterface();

    abstract List<TypeSym> supertypes();

    abstract List<Decl> declared();

    void ctorExposed() {}

    /// Proves an instance method.
    ///
    /// @param name the method name
    /// @param type the erased return and parameter types
    /// @return the method symbol
    /// @throws NoSuchSymbolException if the type has no such instance method
    public final MethodSym method(String name, MethodTypeDesc type) {
        return method(name, type, false);
    }

    /// Proves a static method.
    ///
    /// @param name the method name
    /// @param type the erased return and parameter types
    /// @return the method symbol
    /// @throws NoSuchSymbolException if the type has no such static method
    public final MethodSym staticMethod(String name, MethodTypeDesc type) {
        return method(name, type, true);
    }

    /// Proves an instance field.
    ///
    /// @param name the field name
    /// @param type the erased field type
    /// @return the field symbol
    /// @throws NoSuchSymbolException if the type has no such instance field
    public final FieldSym field(String name, ClassDesc type) {
        return field(name, type, false);
    }

    /// Proves a static field.
    ///
    /// @param name the field name
    /// @param type the erased field type
    /// @return the field symbol
    /// @throws NoSuchSymbolException if the type has no such static field
    public final FieldSym staticField(String name, ClassDesc type) {
        return field(name, type, true);
    }

    /// Proves a constructor declared by this type.
    ///
    /// @param params the erased parameter types
    /// @return the constructor symbol
    /// @throws NoSuchSymbolException if the type declares no such constructor
    public final CtorSym ctor(ClassDesc... params) {
        MethodTypeDesc type = MethodTypeDesc.of(ConstantDescs.CD_void, params);
        require(Decl.Kind.CTOR, "<init>", type.descriptorString(), false, Descs.ctor(desc, type));
        ctorExposed();
        return new CtorSym(this, type);
    }

    private MethodSym method(String name, MethodTypeDesc type, boolean isStatic) {
        require(Decl.Kind.METHOD, name, type.descriptorString(), isStatic, Descs.method(desc, name, type, isStatic));
        return new MethodSym(this, name, type, isStatic);
    }

    private FieldSym field(String name, ClassDesc type, boolean isStatic) {
        require(Decl.Kind.FIELD, name, type.descriptorString(), isStatic, Descs.field(desc, name, type, isStatic));
        return new FieldSym(this, name, type, isStatic);
    }

    private void require(Decl.Kind kind, String name, String descriptor, boolean isStatic, String display) {
        List<String> similar = new ArrayList<>();
        Deque<TypeSym> queue = new ArrayDeque<>(List.of(this));
        Set<ClassDesc> seen = new HashSet<>(Set.of(desc));
        while (!queue.isEmpty()) {
            TypeSym type = queue.removeFirst();
            for (Decl decl : type.declared()) {
                boolean hidden = kind == Decl.Kind.METHOD && decl.isStatic() && type.isInterface() && type != this;
                if (decl.kind() != kind || !decl.name().equals(name) || hidden) {
                    continue;
                }
                if (!decl.descriptor().equals(descriptor)) {
                    similar.add(decl.describe(type.desc));
                } else if (decl.isStatic() != isStatic) {
                    throw new NoSuchSymbolException(display + " does not exist, found " + decl.describe(type.desc));
                } else {
                    return;
                }
            }
            if (kind != Decl.Kind.CTOR) {
                for (TypeSym supertype : type.supertypes()) {
                    if (seen.add(supertype.desc)) {
                        queue.addLast(supertype);
                    }
                }
            }
        }
        throw new NoSuchSymbolException(
                display + " does not exist" + (similar.isEmpty() ? "" : ", found " + String.join(", ", similar)));
    }

    @Override
    public String toString() {
        return Descs.name(desc);
    }

    record Decl(Kind kind, String name, String descriptor, boolean isStatic) {
        enum Kind {
            METHOD,
            FIELD,
            CTOR
        }

        String describe(ClassDesc owner) {
            return switch (kind) {
                case METHOD -> Descs.method(owner, name, MethodTypeDesc.ofDescriptor(descriptor), isStatic);
                case FIELD -> Descs.field(owner, name, ClassDesc.ofDescriptor(descriptor), isStatic);
                case CTOR -> Descs.ctor(owner, MethodTypeDesc.ofDescriptor(descriptor));
            };
        }
    }
}
