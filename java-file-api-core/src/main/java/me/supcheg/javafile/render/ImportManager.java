package me.supcheg.javafile.render;

import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// A nested type is not visible by its simple name just because its package
// matches the file's, so nested types are always imported explicitly.
//
// A top-level type of java.lang or of the file's package is visible by its
// simple name without an import, and still claims the name: a nested type or
// another package's type of the same simple name, imported, would hide it.
//
// A type the file itself declares, top-level or nested, claims its simple name
// before anything is rendered: within the file the name means that type, so
// another type of the same simple name is written qualified. It is imported
// only if something refers to it.
//
// A documentation comment claims nothing (`mention`): it names a type by its
// simple name only where the code of the file gives the name that meaning, so
// a comment adds no import — an import of a type that is not accessible does
// not compile — and never makes the code write another type qualified. What
// the code claims is known once the file is rendered, and a comment comes
// before the code it documents, so a file with a mention is rendered twice:
// the second time over the claims of the first, which the code repeats as
// they are.
final class ImportManager implements TypeContext {

    private final String currentPackage;
    private final Map<String, ClassDesc> claims = new LinkedHashMap<>();

    private final Set<ClassDesc> unreferencedDeclared = new HashSet<>();
    private boolean mentioned;

    ImportManager(String currentPackage) {
        this(currentPackage, List.of());
    }

    ImportManager(String currentPackage, Collection<ClassDesc> declared) {
        this.currentPackage = currentPackage;
        for (ClassDesc desc : declared) {
            if (claims.putIfAbsent(ClassDescNames.leafSimpleName(desc), desc) == null) {
                unreferencedDeclared.add(desc);
            }
        }
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
            unreferencedDeclared.remove(desc);
            return leafSimpleName;
        }

        String dotted = ClassDescNames.qualifiedByDots(desc);
        return sameScopeAsCurrentFile && chain.size() > 1 ? dotted : packageName + "." + dotted;
    }

    @Override
    public String mention(ClassDesc desc) {
        mentioned = true;
        ClassDesc claimed = claims.get(ClassDescNames.leafSimpleName(desc));
        boolean bySimpleName = claimed == null ? visibleWithoutImport(desc) : claimed.equals(desc);
        return bySimpleName ? ClassDescNames.leafSimpleName(desc) : qualified(desc);
    }

    // Whether a documentation comment named a type: the name depends on what the whole file claims.
    boolean mentioned() {
        return mentioned;
    }

    private String qualified(ClassDesc desc) {
        String dotted = ClassDescNames.qualifiedByDots(desc);
        String packageName = desc.packageName();
        boolean sameScopeAsCurrentFile = packageName.equals(currentPackage) || packageName.equals("java.lang");
        return sameScopeAsCurrentFile && ClassDescNames.nestingChain(desc).size() > 1 || packageName.isEmpty()
                ? dotted
                : packageName + "." + dotted;
    }

    List<String> sortedImports() {
        return claims.values().stream()
                .filter(desc -> !unreferencedDeclared.contains(desc) && !visibleWithoutImport(desc))
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
