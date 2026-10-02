package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

/// Names of the type that are names of the metamodel too: a type named like
/// a nested class of the metamodel or like a class the metamodel uses, and
/// members named like a class, a package or a field an expression of the
/// metamodel starts with. The metamodel compiles under every lint, and no
/// member loses its fact.
class ShadowingFixtureTest extends FixtureSupport {
    private static final List<String> SHADOWING = List.of(
            "java",
            "me",
            "p",
            "gen",
            "List",
            "Set",
            "String",
            "ClassDesc",
            "Types",
            "ConstantDescs",
            "Signature",
            "Param",
            "DeclaredKind",
            "MethodTableTemplate",
            "Metamodel",
            "UnsafeFacts",
            "MemberTraits",
            "PrimitiveToken",
            "ArrayToken",
            "TypeShape",
            "TypeParam",
            "OpenClassToken",
            "MutableFieldRef",
            "Prim",
            "Int",
            "Data",
            "Canonical",
            "SHAPE",
            "TEXT",
            "TOKEN",
            "Other_");

    private static String[] library() {
        StringBuilder fields = new StringBuilder();
        for (String name : SHADOWING) {
            fields.append("    public int ").append(name).append(";\n");
        }
        return new String[] {
            // its own name is that of a class of the facts: the metamodel names that one in full
            """
            package p;
            public class Supertypes {
                public static final double Double = 0.0 / 0.0;
                public static final float Float = 0.0f / 0.0f;
                public static final String NAME = "n";
            %s
                public Other other(java.awt.List l, int[] is, String[] ss) throws java.io.IOException {
                    return null;
                }
            }
            """.formatted(fields),
            // List is taken by this type: java.util.List is named in full where the shape is made
            """
            package p;
            public class List {
                public int java;
                public int p;
                public int Supertypes;
                public Data load() { return null; }
                public Canonical canonical(Data data) { return null; }
            }
            """,
            "package p; public class Data { public Data self() { return this; } public Canonical canonical; }",
            "package p; public class Canonical { public Data data; }",
            "package p; public class Other {}",
        };
    }

    /// The facts of the fields of a metamodel, by the name of the field of the type.
    private static Map<String, String> fieldFacts(ClassLoader loader, String metamodel) throws Exception {
        Map<String, String> facts = new TreeMap<>();
        for (Field field : loader.loadClass(metamodel).getFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Object fact = field.get(null);
            if (fact instanceof FieldRef<?, ?> ref) {
                facts.put(ref.name(), field.getName());
            } else if (fact instanceof StaticFieldRef<?> ref) {
                facts.put(ref.name(), field.getName());
            }
        }
        return facts;
    }

    @Test
    void membersNamedLikeWhatTheMetamodelUsesKeepTheirFactsUnderAnEscapedName() throws Exception {
        Compilation compilation = generate("p.Supertypes.class, p.List.class", library());
        ClassLoader loader = load(compilation);

        Map<String, String> facts = fieldFacts(loader, "gen.facts.p.Supertypes_");
        List<String> expected = new ArrayList<>(SHADOWING);
        expected.addAll(List.of("Double", "Float", "NAME"));
        assertThat(facts.keySet()).containsExactlyInAnyOrderElementsOf(expected);
        assertThat(facts).allSatisfy((member, fact) -> assertThat(fact).isIn(member, member + "_"));
        assertThat(facts)
                .containsEntry("java", "java_")
                .containsEntry("me", "me_")
                .containsEntry("gen", "gen_")
                .containsEntry("p", "p")
                .containsEntry("List", "List_")
                .containsEntry("String", "String_")
                .containsEntry("UnsafeFacts", "UnsafeFacts_")
                .containsEntry("MemberTraits", "MemberTraits_")
                .containsEntry("PrimitiveToken", "PrimitiveToken_")
                .containsEntry("Double", "Double_")
                .containsEntry("Float", "Float_")
                .containsEntry("Data", "Data_")
                .containsEntry("Canonical", "Canonical_")
                .containsEntry("TOKEN", "TOKEN_")
                // Other_ is the metamodel of p.Other
                .containsEntry("Other_", "Other__")
                .containsEntry("NAME", "NAME");
        assertThat(((StaticFieldRef<?>) fact(loader, "gen.facts.p.Supertypes_", "Double_")).constantValue())
                .contains(Double.NaN);
        assertThat(((StaticFieldRef<?>) fact(loader, "gen.facts.p.Supertypes_", "Float_")).constantValue())
                .contains(Float.NaN);
        assertThat(((Invocable) fact(loader, "gen.facts.p.Supertypes_", "other_List_intArray_StringArray")).name())
                .isEqualTo("other");
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void aTypeNamedLikeAClassTheMetamodelUsesMakesTheMetamodelNameThatClassInFull() throws Exception {
        Compilation compilation = generate("p.Supertypes.class, p.List.class", library());
        ClassLoader loader = load(compilation);

        assertThat(sources(compilation).get("gen.facts.p.Supertypes_"))
                .contains("me.supcheg.javafile.facts.Supertypes.NONE");
        assertThat(sources(compilation).get("gen.facts.p.List_")).contains("java.util.List.of(");
        assertThat(fieldFacts(loader, "gen.facts.p.List_"))
                .containsEntry("java", "java_")
                .containsEntry("p", "p_")
                .containsEntry("Supertypes", "Supertypes_")
                .hasSize(3);
        assertThat(token(loader, "gen.facts.p.List_").typeRef()).isEqualTo(Types.of(ClassDesc.of("p.List")));
    }

    @Test
    void aTypeNamedLikeANestedClassOfTheMetamodelIsWrittenQualified() throws Exception {
        Compilation compilation = generate("p.List.class, p.Data.class, p.Canonical.class", library());
        ClassLoader loader = load(compilation);

        // in a signature of another type
        assertThat(fact(loader, "gen.facts.p.List_", "load")).isInstanceOf(MethodRef0.class);
        assertThat(((Invocable) fact(loader, "gen.facts.p.List_", "canonical_Data")).name())
                .isEqualTo("canonical");
        assertThat(sources(compilation).get("gen.facts.p.List_"))
                .contains("MethodRef0<List, p.Data> load")
                .contains("UnsafeFacts.<p.Canonical>openClassToken(Canonical_.Data.SHAPE)");
        // and as the type of the metamodel itself
        assertThat(token(loader, "gen.facts.p.Data_").typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Data")));
        assertThat(token(loader, "gen.facts.p.Canonical_").typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Canonical")));
        assertThat(sources(compilation).get("gen.facts.p.Data_"))
                .contains("MethodRef0<p.Data, p.Data> self")
                .contains("MutableFieldRef<p.Data, p.Canonical> canonical");
        assertThat(fieldFacts(loader, "gen.facts.p.Data_")).containsEntry("canonical", "canonical");
        assertThat(fieldFacts(loader, "gen.facts.p.Canonical_")).containsEntry("data", "data");
        assertThat(errors(compilation)).isEmpty();
        assertThat(warnings(compilation)).isEmpty();
    }

    @Test
    void aMemberWhoseEscapedNameIsTakenTooGetsNoFactAndAWarning() throws Exception {
        Compilation compilation = generate("p.Tk.class, p.Other.class", """
                package p;
                public class Tk {
                    public int Other;
                    public Other other() { return null; }
                    public int fine;
                }
                """, "package p; public class Other {}");
        ClassLoader loader = load(compilation);

        // Other is the type of a signature, and Other_ its metamodel
        assertThat(factNames(loader, "gen.facts.p.Tk_")).containsExactly("fine", "new_", "other");
        assertThat(warnings(compilation))
                .containsExactly(
                        "p.Tk: no fact of field Other, which would be named Other_, a name the metamodel itself uses");
    }
}
