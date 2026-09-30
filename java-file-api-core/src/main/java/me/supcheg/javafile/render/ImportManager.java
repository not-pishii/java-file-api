package me.supcheg.javafile.render;

import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// A nested type is not visible by its simple name just because its package
// matches the file's, so nested types are always imported explicitly.
//
// A top-level type of java.lang or of the file's package is visible by its
// simple name without an import, and still claims the name: a nested type or
// another package's type of the same simple name, imported, would hide it.
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
        return sameScopeAsCurrentFile && chain.size() > 1 ? dotted : packageName + "." + dotted;
    }

    List<String> sortedImports() {
        return claims.values().stream()
                .filter(desc -> !visibleWithoutImport(desc))
                .map(desc -> desc.packageName() + "." + ClassDescNames.qualifiedByDots(desc))
                .sorted()
                .toList();
    }

    private boolean visibleWithoutImport(ClassDesc desc) {
        return ClassDescNames.nestingChain(desc).size() == 1
                && (desc.packageName().equals(currentPackage)
                        || desc.packageName().equals("java.lang"));
    }
}
