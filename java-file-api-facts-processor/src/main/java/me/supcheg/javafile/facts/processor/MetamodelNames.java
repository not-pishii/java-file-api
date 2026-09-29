package me.supcheg.javafile.facts.processor;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.constant.ClassDesc;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/// The names in generated metamodels (mini-spec §2.2), as pure functions.
///
/// Only the names of a type are here yet: the metamodel class, its mirror
/// package and its type parameters. The names of member facts —
/// `charAt_int`, `new_String`, the escape of reserved names — come with the
/// full metamodels (plan steps 7–8) and are added here.
final class MetamodelNames {

    /// The static token of a type that is not generic.
    static final String TOKEN = "TOKEN";

    /// The token of an instance of a generic metamodel.
    static final String INSTANCE_TOKEN = "token";

    /// The token of a generic type with a wildcard for every argument.
    static final String ANY = "ANY";

    /// The nested class holding the shape of the type.
    static final String DATA = "Data";

    /// The field of [#DATA] holding the shape.
    static final String SHAPE = "SHAPE";

    /// The nested class holding the canonical form of the type.
    static final String CANONICAL = "Canonical";

    /// The field of [#CANONICAL] holding the canonical form.
    static final String TEXT = "TEXT";

    private MetamodelNames() {}

    /// The metamodel class of a type: `<base>.p.q.T_` for `p.q.T`, with a
    /// nested type's enclosing types joined by `_` — `Map_Entry_` for
    /// `java.util.Map.Entry`.
    ///
    /// @param base the base of the mirror packages
    /// @param type the type
    /// @param elements the element utilities of the compilation
    /// @return the metamodel class
    static ClassDesc metamodel(String base, TypeElement type, Elements elements) {
        Deque<String> chain = new ArrayDeque<>();
        Element current = type;
        while (current instanceof TypeElement element) {
            chain.addFirst(element.getSimpleName().toString());
            current = element.getEnclosingElement();
        }
        PackageElement pkg = elements.getPackageOf(type);
        String packageName = pkg.isUnnamed() ? base : base + "." + pkg.getQualifiedName();
        return ClassDesc.of(packageName, String.join("_", chain) + "_");
    }

    /// The type parameters of a generic metamodel: those of the type, with
    /// `_` appended to a name that is also a simple name the metamodel uses,
    /// which the type parameter would shadow.
    ///
    /// @param declared the type parameters of the type, in order
    /// @param taken the simple names the metamodel uses
    /// @return the type parameters of the metamodel, in order
    static List<String> typeParameters(List<String> declared, Set<String> taken) {
        Set<String> used = new HashSet<>(taken);
        used.addAll(declared);
        List<String> names = new ArrayList<>();
        for (String name : declared) {
            String unique = name;
            if (taken.contains(name)) {
                do {
                    unique += "_";
                } while (used.contains(unique));
                used.add(unique);
            }
            names.add(unique);
        }
        return names;
    }

    /// The parameters of the constructor of a generic metamodel, one
    /// token per type parameter: the type parameter's name with its first
    /// letter in lower case — `RefToken<E> e` — and `_` appended while it is
    /// a keyword or taken by an earlier parameter.
    ///
    /// @param typeParameters the type parameters of the metamodel, in order
    /// @return the parameter names, in order
    static List<String> witnesses(List<String> typeParameters) {
        Set<String> used = new HashSet<>();
        List<String> names = new ArrayList<>();
        for (String typeParameter : typeParameters) {
            String name = typeParameter.substring(0, 1).toLowerCase(Locale.ROOT) + typeParameter.substring(1);
            while (!SourceVersion.isName(name) || used.contains(name)) {
                name += "_";
            }
            used.add(name);
            names.add(name);
        }
        return names;
    }
}
