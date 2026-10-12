package me.supcheg.javafile.render;

import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.type.ClassDescNames;
import me.supcheg.javafile.type.TypeParam;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
// A type parameter of a type or of a method the file declares claims its name too,
// wherever in the file it is declared: where it is in scope the name means the type
// variable, so a type of that simple name is written qualified throughout the file.
//
// A member type the type of the file inherits claims its name in the same way, which the file is
// told (JavaFile#inheriting): the declaration does not show it.
//
// A variable takes its name where an expression may stand (`qualifier`): before `.member` and
// `::method` a simple name means a variable in scope before a type, and the first name of a
// qualified one a variable before a package (JLS 6.4.2). So a type named like a variable of the
// file — a field, declared or inherited, a parameter, a local variable — is written qualified
// there, and a qualified name whose first name is a variable cannot be written at all: the file
// is refused. Variables are of the whole file, like type parameters: where exactly each is in
// scope is not asked. They are learned as the file is rendered (`variable`), a local class with
// its type parameters too (`localType`); a name written before the variable or type that takes
// it was met makes the rendering stale, and the file is rendered again knowing what the first
// rendering learned (`learned`).
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
    private final Known known;
    private final Set<String> variables;
    private final Set<String> lateTypeVariables = new HashSet<>();
    private final Set<ClassDesc> lateDeclared = new HashSet<>();
    // the names written where an expression may stand, taken for no variable
    private final Set<String> takenForNoVariable = new HashSet<>();
    private boolean stale;
    private boolean mentioned;

    // What a file is known to declare before it is rendered.
    //
    // declared: the types the file declares
    // typeVariables: the type parameters the file declares
    // memberTypes: the member types the type of the file inherits
    // variables: the variables of the file: the fields its type inherits, and those a rendering learned
    record Known(Set<ClassDesc> declared, Set<String> typeVariables, Set<String> memberTypes, Set<String> variables) {
        Known {
            declared = Collections.unmodifiableSet(new LinkedHashSet<>(declared));
            typeVariables = Set.copyOf(typeVariables);
            memberTypes = Set.copyOf(memberTypes);
            variables = Set.copyOf(variables);
        }
    }

    ImportManager(String currentPackage) {
        this(currentPackage, List.of());
    }

    ImportManager(String currentPackage, Collection<ClassDesc> declared) {
        this(currentPackage, declared, Set.of());
    }

    ImportManager(String currentPackage, Collection<ClassDesc> declared, Collection<String> typeVariables) {
        this(currentPackage, new Known(new LinkedHashSet<>(declared), Set.copyOf(typeVariables), Set.of(), Set.of()));
    }

    ImportManager(String currentPackage, Known known) {
        this.currentPackage = currentPackage;
        this.known = known;
        this.variables = new HashSet<>(known.variables());
        for (ClassDesc desc : known.declared()) {
            if (claims.putIfAbsent(ClassDescNames.leafSimpleName(desc), desc) == null) {
                unreferencedDeclared.add(desc);
            }
        }
    }

    // Whether a name was written before what takes it was met: the file is to be rendered again.
    boolean stale() {
        return stale;
    }

    // What the file is known to declare after this rendering.
    Known learned() {
        return new Known(
                Stream.concat(known.declared().stream(), lateDeclared.stream())
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                Stream.concat(known.typeVariables().stream(), lateTypeVariables.stream())
                        .collect(Collectors.toSet()),
                known.memberTypes(),
                variables);
    }

    @Override
    public void variable(String name) {
        if (variables.add(name) && takenForNoVariable.contains(name)) {
            stale = true;
        }
    }

    @Override
    public void localType(TypeDecl decl) {
        List<ClassDesc> declared = new ArrayList<>();
        StandardRenderer.collectDeclared(decl, declared);
        List<String> typeVariables =
                StandardRenderer.typeVariables(decl).map(TypeParam::name).toList();
        boolean news = declared.stream()
                                .filter(desc -> !known.declared().contains(desc))
                                .count()
                        + typeVariables.stream()
                                .filter(name -> !known.typeVariables().contains(name))
                                .count()
                > 0;
        if (news) {
            lateDeclared.addAll(declared);
            lateTypeVariables.addAll(typeVariables);
            stale = true;
        }
    }

    // Whether the simple name means something else than a type the file imports, wherever a type is named.
    private boolean shadowed(String simpleName) {
        return known.typeVariables().contains(simpleName) || known.memberTypes().contains(simpleName);
    }

    @Override
    public String qualifier(ClassDesc desc) {
        String written = variables.contains(ClassDescNames.leafSimpleName(desc)) ? qualified(desc) : reference(desc);
        String first = written.indexOf('.') < 0 ? written : written.substring(0, written.indexOf('.'));
        if (variables.contains(first)) {
            // a simple name that could be a variable, a type or a package is the variable (JLS 6.4.2)
            throw new IllegalStateException("cannot name " + written + " before a member in a file that declares"
                    + " the variable " + first + ": where an expression may stand the name of the variable"
                    + " obscures the first name of the qualified one — rename the variable");
        }
        takenForNoVariable.add(first);
        return written;
    }

    @Override
    public String reference(ClassDesc desc) {
        List<String> chain = ClassDescNames.nestingChain(desc);
        String packageName = desc.packageName();
        boolean sameScopeAsCurrentFile = packageName.equals(currentPackage) || packageName.equals("java.lang");

        String leafSimpleName = chain.getLast();
        if (shadowed(leafSimpleName)) {
            return qualified(desc);
        }
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
        if (shadowed(ClassDescNames.leafSimpleName(desc))) {
            return qualified(desc);
        }
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
        String whole = packageName.isEmpty() ? dotted : packageName;
        String first = whole.indexOf('.') < 0 ? whole : whole.substring(0, whole.indexOf('.'));
        if (known.typeVariables().contains(first)) {
            // a simple name that could be a type or a package is the type (JLS 6.4.2)
            throw new IllegalStateException("cannot name " + (packageName.isEmpty() ? "" : packageName + ".")
                    + dotted + " in a file that declares the type parameter " + first
                    + ": the name of the type parameter obscures the first name of the qualified one");
        }
        if (known.memberTypes().contains(first)) {
            throw new IllegalStateException("cannot name " + (packageName.isEmpty() ? "" : packageName + ".")
                    + dotted + " in a file whose type inherits the member type " + first
                    + ": the name of the member type obscures the first name of the qualified one");
        }
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
