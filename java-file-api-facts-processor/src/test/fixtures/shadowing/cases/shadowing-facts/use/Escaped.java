import gen.facts.p.List_;
import gen.facts.p.Supertypes_;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.type.Types;
import p.Other;
import p.Supertypes;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// Members named like what the metamodel uses (a class, a package, a nested class, a field of the metamodel) keep
/// their facts under an escaped name — one `_` more where the name is taken, and none where it is not.
public final class Escaped {
    private Escaped() {}

    private static void named(MutableFieldRef<Supertypes, Prim.Int> fact, String member) {
        assertThat(fact.name()).isEqualTo(member);
    }

    public static void aMemberNamedLikeAPackageTheMetamodelWritesAQualifiedNameByIsEscaped() {
        named(Supertypes_.java_, "java");
        named(Supertypes_.me_, "me");
        named(Supertypes_.gen_, "gen");
    }

    /// `p` is the package of the type, and is not one the metamodel starts a name with.
    public static void aMemberNamedLikeThePackageOfTheTypeIsNot() {
        named(Supertypes_.p, "p");
    }

    public static void aMemberNamedLikeAClassTheMetamodelUsesIsEscaped() {
        named(Supertypes_.List_, "List");
        named(Supertypes_.String_, "String");
        named(Supertypes_.UnsafeFacts_, "UnsafeFacts");
        named(Supertypes_.MemberTraits_, "MemberTraits");
        named(Supertypes_.PrimitiveToken_, "PrimitiveToken");
        // the heritage of the type is told with the type references of java-file-api-core
        named(Supertypes_.Types_, "Types");
    }

    public static void aMemberNamedLikeAClassTheMetamodelDoesNotUseIsNot() {
        named(Supertypes_.Prim, "Prim");
        named(Supertypes_.TypeParam, "TypeParam");
    }

    public static void aMemberNamedLikeANameOfTheMetamodelItselfIsEscaped() {
        named(Supertypes_.Data_, "Data");
        named(Supertypes_.Canonical_, "Canonical");
        named(Supertypes_.TOKEN_, "TOKEN");
        // Other_ is the metamodel of p.Other
        named(Supertypes_.Other__, "Other_");
    }

    public static void aConstantNamedLikeAClassOfTheJdkKeepsItsValue() {
        StaticFieldRef<Prim.Double> nan = Supertypes_.Double_;
        StaticFieldRef<Prim.Float> floatNan = Supertypes_.Float_;
        StaticFieldRef<String> name = Supertypes_.NAME;

        assertThat(nan.constantValue()).contains(Double.NaN);
        assertThat(floatNan.constantValue()).contains(Float.NaN);
        assertThat(name.constantValue()).contains("n");
    }

    public static void aMethodWithTypesTheMetamodelNamesInFullIsAFact() {
        MethodRef3<Supertypes, Other, java.awt.List, int[], String[]> other = Supertypes_.other_List_intArray_StringArray;

        assertThat(other.name()).isEqualTo("other");
    }

    /// `List` is the name of a type of the library: the metamodel of `java.util.List` is not the one named.
    public static void aTypeNamedLikeAClassTheMetamodelUsesHasAMetamodelAndItsFacts() {
        assertThat(List_.TOKEN.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.List")));
        assertThat(List_.java_.name()).isEqualTo("java");
        assertThat(List_.p_.name()).isEqualTo("p");
        assertThat(List_.Supertypes_.name()).isEqualTo("Supertypes");
    }
}
