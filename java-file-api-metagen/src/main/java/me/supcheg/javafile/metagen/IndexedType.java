package me.supcheg.javafile.metagen;

import me.supcheg.javafile.facts.FactLookupException;
import org.jspecify.annotations.Nullable;

import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassModel;
import java.lang.classfile.FieldModel;
import java.lang.classfile.MethodModel;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/// A class or interface read from its class file.
///
/// Its lookups return a member only if the type declares or inherits it, and
/// otherwise throw [FactLookupException] naming the similar members found —
/// the same diagnostics a fact source gives (§3.8). Methods and fields are
/// searched in supertypes too, except static interface methods; constructors
/// only in the type itself. Private and synthetic members are invisible.
final class IndexedType {
    private final ClassFileIndex index;
    private final ClassDesc desc;
    private final ClassModel model;
    private final List<IndexedMember> declared;
    private @Nullable List<IndexedType> supertypes;

    IndexedType(ClassFileIndex index, ClassDesc desc, ClassModel model) {
        this.index = index;
        this.desc = desc;
        this.model = model;
        this.declared = declared(desc, model);
    }

    /// The type's descriptor.
    ///
    /// @return the class or interface descriptor
    ClassDesc desc() {
        return desc;
    }

    /// Whether this type is an interface.
    ///
    /// @return `true` for an interface
    boolean isInterface() {
        return model.flags().has(AccessFlag.INTERFACE);
    }

    /// The direct superclass and superinterfaces.
    ///
    /// @return the supertypes
    List<IndexedType> supertypes() {
        if (supertypes == null) {
            supertypes = Stream.concat(model.superclass().stream(), model.interfaces().stream())
                    .map(entry -> index.type(entry.asSymbol()))
                    .toList();
        }
        return supertypes;
    }

    /// Finds an instance method.
    ///
    /// @param name the method name
    /// @param type the erased return and parameter types
    /// @return the member
    /// @throws FactLookupException if the type has no such instance method
    IndexedMember method(String name, MethodTypeDesc type) {
        return require(IndexedMember.Kind.METHOD, name, type.descriptorString(), false);
    }

    /// Finds a static method.
    ///
    /// @param name the method name
    /// @param type the erased return and parameter types
    /// @return the member
    /// @throws FactLookupException if the type has no such static method
    IndexedMember staticMethod(String name, MethodTypeDesc type) {
        return require(IndexedMember.Kind.METHOD, name, type.descriptorString(), true);
    }

    /// Finds an instance field.
    ///
    /// @param name the field name
    /// @param type the erased field type
    /// @return the member
    /// @throws FactLookupException if the type has no such instance field
    IndexedMember field(String name, ClassDesc type) {
        return require(IndexedMember.Kind.FIELD, name, type.descriptorString(), false);
    }

    /// Finds a static field.
    ///
    /// @param name the field name
    /// @param type the erased field type
    /// @return the member
    /// @throws FactLookupException if the type has no such static field
    IndexedMember staticField(String name, ClassDesc type) {
        return require(IndexedMember.Kind.FIELD, name, type.descriptorString(), true);
    }

    /// Finds a constructor declared by this type.
    ///
    /// @param params the erased parameter types
    /// @return the member
    /// @throws FactLookupException if the type declares no such constructor
    IndexedMember ctor(ClassDesc... params) {
        MethodTypeDesc type = MethodTypeDesc.of(ConstantDescs.CD_void, params);
        return require(IndexedMember.Kind.CTOR, "<init>", type.descriptorString(), false);
    }

    private IndexedMember require(IndexedMember.Kind kind, String name, String descriptor, boolean isStatic) {
        List<String> similar = new ArrayList<>();
        Deque<IndexedType> queue = new ArrayDeque<>(List.of(this));
        Set<ClassDesc> seen = new HashSet<>(Set.of(desc));
        while (!queue.isEmpty()) {
            IndexedType type = queue.removeFirst();
            for (IndexedMember member : type.declared) {
                boolean hidden =
                        kind == IndexedMember.Kind.METHOD && member.isStatic() && type.isInterface() && type != this;
                if (member.kind() != kind || !member.name().equals(name) || hidden) {
                    continue;
                }
                if (member.descriptor().equals(descriptor) && member.isStatic() == isStatic) {
                    return new IndexedMember(desc, kind, name, descriptor, isStatic);
                }
                similar.add(member.toString());
            }
            if (kind != IndexedMember.Kind.CTOR) {
                for (IndexedType supertype : type.supertypes()) {
                    if (seen.add(supertype.desc)) {
                        queue.addLast(supertype);
                    }
                }
            }
        }
        throw new FactLookupException(
                Descs.describe(desc, kind, name, descriptor, isStatic), "the class path", similar);
    }

    private static List<IndexedMember> declared(ClassDesc owner, ClassModel model) {
        List<IndexedMember> declared = new ArrayList<>();
        for (MethodModel method : model.methods()) {
            String name = method.methodName().stringValue();
            AccessFlags flags = method.flags();
            if (hidden(flags) || flags.has(AccessFlag.BRIDGE) || name.equals("<clinit>")) {
                continue;
            }
            IndexedMember.Kind kind = name.equals("<init>") ? IndexedMember.Kind.CTOR : IndexedMember.Kind.METHOD;
            declared.add(new IndexedMember(
                    owner, kind, name, method.methodType().stringValue(), flags.has(AccessFlag.STATIC)));
        }
        for (FieldModel field : model.fields()) {
            if (!hidden(field.flags())) {
                declared.add(new IndexedMember(
                        owner,
                        IndexedMember.Kind.FIELD,
                        field.fieldName().stringValue(),
                        field.fieldType().stringValue(),
                        field.flags().has(AccessFlag.STATIC)));
            }
        }
        return List.copyOf(declared);
    }

    private static boolean hidden(AccessFlags flags) {
        return flags.has(AccessFlag.PRIVATE) || flags.has(AccessFlag.SYNTHETIC);
    }

    @Override
    public String toString() {
        return Descs.name(desc);
    }
}
