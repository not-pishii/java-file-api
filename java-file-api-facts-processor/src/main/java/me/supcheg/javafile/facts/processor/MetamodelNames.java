package me.supcheg.javafile.facts.processor;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import java.lang.constant.ClassDesc;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/// The names in generated metamodels (mini-spec §2.2), as pure functions:
/// those of a type — the metamodel class and its type parameters — and those
/// of the facts of its members — `length`, `charAt_int`, `new_String` — with
/// the escape of reserved names.
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

    /// The names a member fact may not have: those of the tokens, the
    /// functional interface's `sam`, the reserve for an exhaustive switch and
    /// the nested classes.
    private static final Set<String> RESERVED = Set.of(TOKEN, INSTANCE_TOKEN, ANY, "sam", "switch_", DATA, CANONICAL);

    private MetamodelNames() {}

    /// The names of member facts, and the members that got none.
    ///
    /// @param names the name of every member that has one, in the order of the input
    /// @param conflicts the members that would share a name, by that name; none of them is in `names`
    record MemberNames(Map<Element, String> names, Map<String, List<Element>> conflicts) {}

    /// A member and what its name is made of.
    ///
    /// @param element the member
    /// @param prefix the name of a field, enum constant or method, `new` or `super` for a constructor
    /// @param parameters the declared types of the parameters, empty for a field
    private record Member(Element element, String prefix, List<TypeMirror> parameters) {
        /// Whether the name is the prefix alone: that of a field, a constant, a method without parameters.
        boolean bare() {
            return parameters.isEmpty() && element.getKind() != ElementKind.CONSTRUCTOR;
        }

        boolean field() {
            return element.getKind().isField();
        }

        /// The name, with the types of `qualified` positions in the qualified form.
        String name(boolean[] qualified) {
            if (bare()) {
                return prefix;
            }
            StringBuilder name = new StringBuilder(prefix);
            for (int i = 0; i < parameters.size(); i++) {
                name.append('_').append(typeSuffix(parameters.get(i), qualified.length > i && qualified[i]));
            }
            return parameters.isEmpty() ? name.append('_').toString() : name.toString();
        }
    }

    /// The names of the facts of members (mini-spec §2.2): a field, an enum
    /// constant and a method without parameters are named as they are, a
    /// method with parameters `name_T1_T2`, a constructor `new_T1_T2` — or
    /// `super_T1_T2` in an abstract class. `Ti` is [#typeSuffix] of the declared
    /// type of a parameter.
    ///
    /// Members whose names match are told apart by the qualified name of the
    /// declared types that differ (`m_java_util_List`), and only there. A
    /// reserved name gets `_` appended, and so does a method without
    /// parameters that a field is named after. Members that still share a
    /// name are not named, but reported as conflicts.
    ///
    /// The name of a member does not depend on the order of `members`, but on
    /// which members there are: pass every member that may ever get a fact,
    /// whether it gets one now or not, so that a name stays when a member
    /// that had none gets its fact.
    ///
    /// @param members fields, enum constants, methods and constructors
    /// @return the names
    /// @throws IllegalArgumentException if an element is of another kind, or a parameter type is unsupported
    static MemberNames members(List<? extends Element> members) {
        return members(members, Set.of());
    }

    /// The names of the facts of members, as [#members(List)] gives them,
    /// in a metamodel that uses some names itself: such a name gets `_`
    /// appended, as a reserved one does. The escaped name may be taken too:
    /// the caller is to check.
    ///
    /// @param members fields, enum constants, methods and constructors
    /// @param taken the names the metamodel starts a name with, see [MetamodelEmitter#takenNames]
    /// @return the names
    /// @throws IllegalArgumentException if an element is of another kind, or a parameter type is unsupported
    static MemberNames members(List<? extends Element> members, Set<String> taken) {
        List<Member> parsed = members.stream().map(MetamodelNames::parse).toList();
        Map<String, List<Member>> overloads = new HashMap<>();
        for (Member member : parsed) {
            overloads
                    .computeIfAbsent(member.name(new boolean[0]), key -> new ArrayList<>())
                    .add(member);
        }

        Map<Member, String> escaped = new LinkedHashMap<>();
        Set<String> fields = new HashSet<>();
        for (Member member : parsed) {
            String name = member.name(qualified(member, overloads.get(member.name(new boolean[0]))));
            if (RESERVED.contains(name) || taken.contains(name)) {
                name += "_";
            }
            escaped.put(member, name);
            if (member.field()) {
                fields.add(name);
            }
        }

        Map<Member, String> finalNames = new LinkedHashMap<>();
        Map<String, List<Member>> byName = new TreeMap<>();
        escaped.forEach((member, name) -> {
            String finalName = !member.field() && member.bare() && fields.contains(name) ? name + "_" : name;
            finalNames.put(member, finalName);
            byName.computeIfAbsent(finalName, key -> new ArrayList<>()).add(member);
        });

        Map<Element, String> names = new LinkedHashMap<>();
        Map<String, List<Element>> conflicts = new TreeMap<>();
        finalNames.forEach((member, name) -> {
            if (byName.get(name).size() == 1) {
                names.put(member.element(), name);
            }
        });
        byName.forEach((name, sharing) -> {
            if (sharing.size() > 1) {
                conflicts.put(name, sharing.stream().map(Member::element).toList());
            }
        });
        return new MemberNames(names, conflicts);
    }

    private static Member parse(Element element) {
        return switch (element.getKind()) {
            case FIELD, ENUM_CONSTANT ->
                new Member(element, element.getSimpleName().toString(), List.of());
            case METHOD -> new Member(element, element.getSimpleName().toString(), parameters(element));
            case CONSTRUCTOR ->
                new Member(
                        element,
                        element.getEnclosingElement().getModifiers().contains(Modifier.ABSTRACT) ? "super" : "new",
                        parameters(element));
            default -> throw new IllegalArgumentException("Not a member with a fact: " + element);
        };
    }

    private static List<TypeMirror> parameters(Element executable) {
        return ((ExecutableElement) executable)
                .getParameters().stream().map(VariableElement::asType).toList();
    }

    /// The positions of the parameters of `member` whose declared types are
    /// told from those of the other members of `overloads` — the members
    /// with the same name by simple names — by their qualified names.
    private static boolean[] qualified(Member member, List<Member> overloads) {
        boolean[] qualified = new boolean[member.parameters().size()];
        for (Member other : overloads) {
            if (other == member || other.parameters().size() != qualified.length) {
                continue;
            }
            for (int i = 0; i < qualified.length; i++) {
                if (!typeSuffix(member.parameters().get(i), true)
                        .equals(typeSuffix(other.parameters().get(i), true))) {
                    qualified[i] = true;
                }
            }
        }
        return qualified;
    }

    /// The part of a member's name that stands for a parameter of a type: the
    /// keyword of a primitive (`int`), the simple name of a class with the
    /// names of its enclosing classes joined by `_` (`Map_Entry`), the name of a
    /// type variable (`E`), and `Array` appended for an array or varargs
    /// (`intArray`, `StringArray`, `EArray`). Type arguments do not count. A
    /// type that is not resolved stands as javac names it, its dots turned
    /// into `_`.
    ///
    /// @param type the declared type of the parameter
    /// @param qualified whether a class is preceded by its package, its dots
    ///     turned into `_` (`java_util_Map_Entry`)
    /// @return the part
    /// @throws IllegalArgumentException if the type cannot be that of a parameter
    static String typeSuffix(TypeMirror type, boolean qualified) {
        return switch (type.getKind()) {
            case BOOLEAN, BYTE, SHORT, INT, LONG, CHAR, FLOAT, DOUBLE ->
                type.getKind().name().toLowerCase(Locale.ROOT);
            case DECLARED -> declaredSuffix(((DeclaredType) type).asElement(), qualified);
            case TYPEVAR -> ((TypeVariable) type).asElement().getSimpleName().toString();
            case ARRAY -> typeSuffix(((ArrayType) type).getComponentType(), qualified) + "Array";
            case ERROR -> type.toString().replaceAll("[^\\p{javaJavaIdentifierPart}]+", "_");
            default -> throw new IllegalArgumentException("Not a parameter type: " + type);
        };
    }

    private static String declaredSuffix(Element type, boolean qualified) {
        Deque<String> chain = new ArrayDeque<>();
        Element current = type;
        while (current instanceof TypeElement element) {
            chain.addFirst(element.getSimpleName().toString());
            current = element.getEnclosingElement();
        }
        String name = String.join("_", chain);
        if (qualified && current instanceof PackageElement pkg && !pkg.isUnnamed()) {
            return pkg.getQualifiedName().toString().replace('.', '_') + "_" + name;
        }
        return name;
    }

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
