package me.supcheg.javafile.render;

import java.lang.constant.ClassDesc;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/// Tracks which simple type names have claimed an import while rendering a single source file.
///
/// The first type to claim a simple name is imported; later types with the
/// same simple name from a different package are rendered fully qualified
/// instead. Types in `java.lang` or the file's own package are never
/// imported.
final class ImportManager implements TypeContext {

    private final String currentPackage;
    private final Map<String, ClassDesc> claims = new LinkedHashMap<>();

    ImportManager(String currentPackage) {
        this.currentPackage = currentPackage;
    }

    @Override
    public String reference(ClassDesc desc) {
        String simpleName = desc.displayName();
        String packageName = desc.packageName();

        if (packageName.equals(currentPackage) || packageName.equals("java.lang")) {
            return simpleName;
        }

        ClassDesc existing = claims.get(simpleName);
        if (existing == null) {
            claims.put(simpleName, desc);
            return simpleName;
        }
        if (existing.equals(desc)) {
            return simpleName;
        }
        return packageName + "." + simpleName;
    }

    List<String> sortedImports() {
        return claims.values().stream()
                .map(desc -> desc.packageName() + "." + desc.displayName())
                .sorted()
                .toList();
    }
}
