package me.supcheg.javafile.metagen;

import me.supcheg.javafile.facts.FactLookupException;

import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// Reads class files with the ClassFile API (`java.lang.classfile`) and
/// indexes the types and members they declare — the input side of metagen
/// (§5). Parsed classes are cached.
///
/// ```java
/// ClassFileIndex index = new ClassFileIndex(ClassSource.jrt());
/// IndexedMember charAt = index.type(CD_String).method("charAt", MethodTypeDesc.of(CD_char, CD_int));
/// ```
///
/// Not thread-safe.
final class ClassFileIndex {
    private final ClassSource source;
    private final Map<ClassDesc, IndexedType> cache = new HashMap<>();

    /// Creates an index reading class files from `source`.
    ///
    /// @param source where class files are read from
    ClassFileIndex(ClassSource source) {
        this.source = source;
    }

    /// Returns an indexed class or interface.
    ///
    /// @param desc the class or interface
    /// @return the indexed type
    /// @throws FactLookupException if no class file is found for `desc`
    /// @throws IllegalArgumentException if `desc` is a primitive or array type
    IndexedType type(ClassDesc desc) {
        if (!desc.isClassOrInterface()) {
            throw new IllegalArgumentException("expected a class or interface, got " + Descs.name(desc));
        }
        IndexedType type = cache.get(desc);
        if (type == null) {
            byte[] bytes = source.read(desc)
                    .orElseThrow(
                            () -> new FactLookupException("type " + Descs.name(desc), "the class path", List.of()));
            type = new IndexedType(this, desc, ClassFile.of().parse(bytes));
            cache.put(desc, type);
        }
        return type;
    }
}
