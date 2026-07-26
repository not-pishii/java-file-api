package me.supcheg.javafile.render;

import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/// Tracks which simple type names have claimed an import while rendering a single source file.
///
/// The first type to claim a simple name is imported; later types with the
/// same simple name from a different package are rendered fully qualified
/// instead. Types in `java.lang` or the file's own package are never
/// imported — but only top-level types: a nested type is never visible by
/// its bare simple name just because its enclosing type's package matches,
/// so it always goes through the same claim/import bookkeeping as any
/// other type.
final class ImportManager implements TypeContext {

    private final String currentPackage;
    private final Map<String, ClassDesc> claims = new LinkedHashMap<>();

    ImportManager(String currentPackage) {
        this.currentPackage = currentPackage;
    }

    @Override
    public String reference(ClassDesc desc) {
        List<String> chain = ClassDescNames.nestingChain(desc);
        String packageName = desc.packageName();
        boolean sameScopeAsCurrentFile = packageName.equals(currentPackage) || packageName.equals("java.lang");

        if (chain.size() == 1 && sameScopeAsCurrentFile) {
            return chain.getFirst();
        }

        String leafSimpleName = chain.getLast();
        ClassDesc existing = claims.get(leafSimpleName);
        if (existing == null) {
            claims.put(leafSimpleName, desc);
            return leafSimpleName;
        }
        if (existing.equals(desc)) {
            return leafSimpleName;
        }

        String dotted = ClassDescNames.qualifiedByDots(desc);
        return sameScopeAsCurrentFile ? dotted : packageName + "." + dotted;
    }

    List<String> sortedImports() {
        return claims.values().stream()
                .map(desc -> desc.packageName() + "." + ClassDescNames.qualifiedByDots(desc))
                .sorted()
                .toList();
    }
}
