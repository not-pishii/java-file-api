package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The names of member facts (mini-spec §2.2) on real elements; fixture
/// `overloads` (§9.2).
class MetamodelNamesTest {

    /// The members of the top-level type `p.Fixture` of `sources`, and what the
    /// processor makes of them, run inside the compilation.
    private static void inCompilation(List<String> sources, Consumer<Context> action) {
        Compilation compilation = javac().withProcessors(new AbstractProcessor() {
                    @Override
                    public Set<String> getSupportedAnnotationTypes() {
                        return Set.of("*");
                    }

                    @Override
                    public SourceVersion getSupportedSourceVersion() {
                        return SourceVersion.latestSupported();
                    }

                    @Override
                    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
                        if (!round.processingOver()) {
                            action.accept(new Context(round, processingEnv));
                        }
                        return false;
                    }
                })
                .compile(sources.stream().map(ProcessorHarness::source).toList());
        assertThat(compilation.status()).as("%s", compilation.diagnostics()).isEqualTo(Compilation.Status.SUCCESS);
    }

    private record Context(RoundEnvironment round, javax.annotation.processing.ProcessingEnvironment env) {
        TypeElement type(String name) {
            return env.getElementUtils().getTypeElement(name);
        }

        /// The declared fields, constants, methods and constructors of a type
        /// (not the implicit ones of an enum).
        List<Element> members(String type) {
            return new ArrayList<Element>(type(type).getEnclosedElements())
                    .stream()
                            .filter(e -> switch (e.getKind()) {
                                case FIELD, ENUM_CONSTANT, METHOD, CONSTRUCTOR -> true;
                                default -> false;
                            })
                            .filter(e -> !(e.getKind() == ElementKind.METHOD
                                    && type(type).getKind() == ElementKind.ENUM
                                    && Set.of("values", "valueOf")
                                            .contains(e.getSimpleName().toString())))
                            .toList();
        }
    }

    /// `source of the member → name`, with the source as `getSimpleName(parameters)`.
    private static Map<String, String> named(MetamodelNames.MemberNames names) {
        Map<String, String> byMember = new LinkedHashMap<>();
        names.names().forEach((element, name) -> byMember.put(describe(element), name));
        return byMember;
    }

    private static String describe(Element element) {
        return element.getEnclosingElement().getSimpleName() + "#" + element;
    }

    private static final String MAP = "package p; public class Map { public static class Entry {} }";

    private static final String OVERLOADS = """
            package p;
            public abstract class Fixture<E> {
                public int count;
                public int count() { return 0; }
                public int TOKEN;
                public void token() {}
                public void sam() {}
                public void switch_() {}
                public void ANY(String s) {}
                public void Data() {}
                public void Canonical() {}
                public void length() {}
                public void charAt(int i) {}
                public void substring(int i) {}
                public void substring(int i, int j) {}
                public void getString(String s, java.util.Locale l) {}
                public void entry(java.util.Map.Entry<String, String> e) {}
                public void ints(int[] a) {}
                public void intss(int[][] a) {}
                public void varargs(String... a) {}
                public void elements(E e) {}
                public void elements(E[] e) {}
                public void m(java.util.List<String> l) {}
                public void m(java.awt.List l) {}
                public void m(int i) {}
                public void n(java.util.List<String> l, String s) {}
                public void n(java.awt.List l, String s) {}
                public void o(java.util.List<String>[] l, Object x) {}
                public void o(java.awt.List[] l, Object x) {}
                public void o(java.awt.List[] l) {}
                public void q(java.util.Map.Entry<String, String> e) {}
                public void q(Map.Entry e) {}
                public <List> void w(List l) {}
                public void w(java.util.List<String> l) {}
                public static final int CONST = 1;
                public Fixture() {}
                public Fixture(String s) {}
                public Fixture(int i, java.util.Map.Entry<String, String> e) {}
                public Fixture(java.util.List<String> l) {}
                public Fixture(java.awt.List l) {}
            }
            """;

    @Test
    void theRuleOfTheNames() {
        inCompilation(List.of(OVERLOADS, MAP), context -> {
            MetamodelNames.MemberNames names = MetamodelNames.members(context.members("p.Fixture"));
            assertThat(names.conflicts()).isEmpty();
            assertThat(named(names))
                    .containsAllEntriesOf(Map.ofEntries(
                            Map.entry("Fixture#length()", "length"),
                            Map.entry("Fixture#charAt(int)", "charAt_int"),
                            Map.entry("Fixture#substring(int)", "substring_int"),
                            Map.entry("Fixture#substring(int,int)", "substring_int_int"),
                            Map.entry(
                                    "Fixture#getString(java.lang.String,java.util.Locale)", "getString_String_Locale"),
                            Map.entry(
                                    "Fixture#entry(java.util.Map.Entry<java.lang.String,java.lang.String>)",
                                    "entry_Map_Entry"),
                            Map.entry("Fixture#ints(int[])", "ints_intArray"),
                            Map.entry("Fixture#intss(int[][])", "intss_intArrayArray"),
                            Map.entry("Fixture#varargs(java.lang.String...)", "varargs_StringArray"),
                            Map.entry("Fixture#elements(E)", "elements_E"),
                            Map.entry("Fixture#elements(E[])", "elements_EArray"),
                            Map.entry("Fixture#m(int)", "m_int"),
                            Map.entry("Fixture#CONST", "CONST")));
        });
    }

    @Test
    void constructorsAreNewOrSuperInAnAbstractClass() {
        inCompilation(List.of(OVERLOADS, MAP, """
                package p;
                public class Concrete {
                    public Concrete() {}
                    public Concrete(int i, String s) {}
                }
                """), context -> {
            assertThat(named(MetamodelNames.members(context.members("p.Concrete"))))
                    .containsValues("new_", "new_int_String");
            Map<String, String> abstractOnes = named(MetamodelNames.members(context.members("p.Fixture")));
            assertThat(abstractOnes)
                    .containsEntry("Fixture#Fixture()", "super_")
                    .containsEntry("Fixture#Fixture(java.lang.String)", "super_String")
                    .containsEntry(
                            "Fixture#Fixture(int,java.util.Map.Entry<java.lang.String,java.lang.String>)",
                            "super_int_Map_Entry");
        });
    }

    @Test
    void fieldsAndEnumConstantsAreNamedAsTheyAre() {
        inCompilation(
                List.of("package p; public enum Color { RED, GREEN; public int shade; Color() {} }"),
                context -> assertThat(MetamodelNames.members(context.members("p.Color"))
                                .names()
                                .values())
                        .containsExactly("RED", "GREEN", "shade", "new_"));
    }

    @Test
    void namesWithTheSameSimpleNamesAreToldApartByTheQualifiedOnesAndOnlyThere() {
        inCompilation(List.of(OVERLOADS, MAP), context -> {
            Map<String, String> names = named(MetamodelNames.members(context.members("p.Fixture")));
            assertThat(names)
                    .containsEntry("Fixture#m(java.util.List<java.lang.String>)", "m_java_util_List")
                    .containsEntry("Fixture#m(java.awt.List)", "m_java_awt_List")
                    .containsEntry(
                            "Fixture#n(java.util.List<java.lang.String>,java.lang.String)", "n_java_util_List_String")
                    .containsEntry("Fixture#n(java.awt.List,java.lang.String)", "n_java_awt_List_String")
                    .containsEntry(
                            "Fixture#o(java.util.List<java.lang.String>[],java.lang.Object)",
                            "o_java_util_ListArray_Object")
                    .containsEntry("Fixture#o(java.awt.List[],java.lang.Object)", "o_java_awt_ListArray_Object")
                    .containsEntry("Fixture#o(java.awt.List[])", "o_ListArray")
                    .containsEntry(
                            "Fixture#q(java.util.Map.Entry<java.lang.String,java.lang.String>)",
                            "q_java_util_Map_Entry")
                    .containsEntry("Fixture#q(p.Map.Entry)", "q_p_Map_Entry")
                    .containsEntry("Fixture#<List>w(List)", "w_List")
                    .containsEntry("Fixture#w(java.util.List<java.lang.String>)", "w_java_util_List")
                    .containsEntry("Fixture#Fixture(java.util.List<java.lang.String>)", "super_java_util_List")
                    .containsEntry("Fixture#Fixture(java.awt.List)", "super_java_awt_List");
        });
    }

    @Test
    void aClassInTheUnnamedPackageIsQualifiedByItsNameAlone() {
        inCompilation(
                List.of(
                        "public class List {}",
                        "public class Unnamed { public void r(List a) {} public void r(java.util.List<String> a) {} }"),
                context -> assertThat(MetamodelNames.members(context.members("Unnamed"))
                                .names()
                                .values())
                        .containsExactlyInAnyOrder("r_List", "r_java_util_List", "new_"));
    }

    @Test
    void reservedNamesAndAMethodNamedLikeAFieldGetAnUnderscore() {
        inCompilation(List.of(OVERLOADS, MAP), context -> {
            Map<String, String> names = named(MetamodelNames.members(context.members("p.Fixture")));
            assertThat(names)
                    .containsEntry("Fixture#TOKEN", "TOKEN_")
                    .containsEntry("Fixture#token()", "token_")
                    .containsEntry("Fixture#sam()", "sam_")
                    .containsEntry("Fixture#switch_()", "switch__")
                    .containsEntry("Fixture#Data()", "Data_")
                    .containsEntry("Fixture#Canonical()", "Canonical_")
                    .containsEntry("Fixture#ANY(java.lang.String)", "ANY_String")
                    .containsEntry("Fixture#count", "count")
                    .containsEntry("Fixture#count()", "count_");
        });
    }

    @Test
    void aReservedNameThatIsAMethodWithParametersNeedsNoEscape() {
        inCompilation(
                List.of("package p; public class R { public void token(int i) {} public void ANY() {} }"),
                context -> assertThat(MetamodelNames.members(context.members("p.R"))
                                .names()
                                .values())
                        .containsExactlyInAnyOrder("token_int", "ANY_", "new_"));
    }

    @Test
    void membersThatShareANameAreReportedAndNotNamed() {
        inCompilation(List.of("""
                package p;
                public class Clash<T> {
                    public int a_int;
                    public void a(int i) {}
                    public <T extends Number> void c(T t) {}
                    public <T extends CharSequence> void c(T t) {}
                    public void fine() {}
                }
                """), context -> {
            MetamodelNames.MemberNames names = MetamodelNames.members(context.members("p.Clash"));
            assertThat(names.names().values()).containsExactly("new_", "fine");
            assertThat(names.conflicts().keySet()).containsExactly("a_int", "c_T");
            assertThat(names.conflicts().get("a_int")).hasSize(2);
            assertThat(names.conflicts().get("c_T")).hasSize(2);
        });
    }

    @Test
    void theNamesDoNotDependOnTheOrderOfTheMembers() {
        inCompilation(List.of(OVERLOADS, MAP), context -> {
            List<Element> members = new ArrayList<>(context.members("p.Fixture"));
            Map<String, String> forward = named(MetamodelNames.members(members));
            java.util.Collections.reverse(members);
            assertThat(named(MetamodelNames.members(members))).isEqualTo(forward);
        });
    }

    @Test
    void anElementThatIsNoMemberWithAFactIsRejected() {
        inCompilation(List.of(OVERLOADS, MAP), context -> {
            TypeElement fixture = context.type("p.Fixture");
            assertThatThrownBy(() -> MetamodelNames.members(List.of(fixture)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("p.Fixture");
        });
    }

    @Test
    void aTypeThatIsNoParameterTypeIsRejected() {
        inCompilation(List.of(OVERLOADS, MAP), context -> {
            TypeMirror none = context.env().getTypeUtils().getNoType(TypeKind.VOID);
            assertThatThrownBy(() -> MetamodelNames.typeSuffix(none, false))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("void");
        });
    }
}
