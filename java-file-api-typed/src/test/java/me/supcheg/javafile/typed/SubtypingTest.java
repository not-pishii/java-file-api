package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.type.UnboundedTypeArg;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.typed.testfacts.java.util.Collection_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

import static org.assertj.core.api.Assertions.assertThat;

/// The proof of subtyping on the data of the facts ([Subtyping]): what it
/// proves, and what it refuses though javac would take it — a refusal is a
/// class the typed layer does not declare, a false proof one javac rejects.
class SubtypingTest {
    private static final ClassDesc SELF = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Subtyping.Declaring NOTHING_NAMED = new Subtyping.Declaring(SELF, List.of());
    private static final DeclaredToken<?> STRINGS = new ArrayList_<>(String_.TOKEN).token;
    private static final ClassDesc COLLECTION = ClassDesc.of(Collection.class.getName());

    /// The token of the class being declared, which tells neither its superclass nor its methods.
    private static final FinalClassToken<Object> SELF_TOKEN = UnsafeFacts.declaredClassToken(
            Types.of(SELF),
            () -> {
                throw new IllegalStateException("the superclass chain of the class is not asked for");
            },
            () -> {
                throw new IllegalStateException("the methods of the class are not asked for");
            });

    private static boolean proven(TypeToken<?> sub, TypeRef sup) {
        return proven(NOTHING_NAMED, sub, sup);
    }

    private static boolean proven(Subtyping.Declaring declaring, TypeToken<?> sub, TypeRef sup) {
        return switch (Subtyping.prove(declaring, sub, sup)) {
            case Subtyping.Verdict.Proven _ -> true;
            case Subtyping.Verdict.Unproven(var why) -> {
                assertThat(why).isNotBlank();
                yield false;
            }
        };
    }

    private static TypeRef collectionOf(TypeArg argument) {
        return new ParameterizedTypeRef(COLLECTION, List.of(argument), List.of());
    }

    private static TypeRef type(Class<?> type) {
        return Types.of(ClassDesc.of(type.getName()));
    }

    @Test
    void aTypeIsASubtypeOfItselfAndAReferenceOfObject() {
        assertThat(proven(String_.TOKEN, String_.TOKEN.typeRef())).isTrue();
        assertThat(proven(PrimitiveToken.INT, PrimitiveTypeRef.INT)).isTrue();
        assertThat(proven(List_.ANY, List_.ANY.typeRef())).isTrue();
        assertThat(proven(String_.TOKEN, Object_.TOKEN.typeRef())).isTrue();
        assertThat(proven(STRINGS, Object_.TOKEN.typeRef())).isTrue();
        assertThat(proven(PrimitiveToken.INT.array(), Object_.TOKEN.typeRef())).isTrue();
        assertThat(proven(SELF_TOKEN, Object_.TOKEN.typeRef())).isTrue();
    }

    @Test
    void aClassIsOfItsSuperclassesAndOfTheInterfacesItsShapeRecords() {
        assertThat(proven(Integer_.TOKEN, type(Number.class))).isTrue();
        assertThat(proven(String_.TOKEN, CharSequence_.TOKEN.typeRef())).isTrue();
        // a generic supertype named without type arguments is the raw one
        assertThat(proven(STRINGS, type(AbstractList.class))).isTrue();
        assertThat(proven(STRINGS, type(Collection.class))).isTrue();
        assertThat(proven(STRINGS, type(java.util.ArrayList.class))).isTrue();

        assertThat(proven(String_.TOKEN, Integer_.TOKEN.typeRef())).isFalse();
        assertThat(proven(Object_.TOKEN, String_.TOKEN.typeRef())).isFalse();
        assertThat(proven(CharSequence_.TOKEN, String_.TOKEN.typeRef())).isFalse();
    }

    @Test
    void aParameterizationIsOfTheSupertypeItsArgumentsGive() {
        assertThat(proven(STRINGS, new Collection_<>(String_.TOKEN).token.typeRef()))
                .isTrue();
        assertThat(proven(STRINGS, new List_<>(String_.TOKEN).token.typeRef())).isTrue();
        assertThat(proven(String_.TOKEN, new Comparable_<>(String_.TOKEN).token.typeRef()))
                .isTrue();
        // type arguments are invariant
        assertThat(proven(STRINGS, new Collection_<>(Object_.TOKEN).token.typeRef()))
                .isFalse();
        assertThat(proven(STRINGS, new Collection_<>(CharSequence_.TOKEN).token.typeRef()))
                .isFalse();
        assertThat(proven(String_.TOKEN, new Comparable_<>(Object_.TOKEN).token.typeRef()))
                .isFalse();
    }

    @Test
    void aWildcardContainsWhatIsWrittenAsItsBoundAndNothingItHasToBeToldOf() {
        TypeRef string = String_.TOKEN.typeRef();
        assertThat(proven(STRINGS, collectionOf(UnboundedTypeArg.INSTANCE))).isTrue();
        assertThat(proven(STRINGS, collectionOf(new ExtendsTypeArg(string)))).isTrue();
        assertThat(proven(STRINGS, collectionOf(new SuperTypeArg(string)))).isTrue();
        assertThat(proven(STRINGS, collectionOf(new ExtendsTypeArg(Object_.TOKEN.typeRef()))))
                .isTrue();

        // javac takes these two: the bound is a written type, which tells no supertypes
        assertThat(proven(STRINGS, collectionOf(new ExtendsTypeArg(CharSequence_.TOKEN.typeRef()))))
                .isFalse();
        assertThat(proven(new ArrayList_<>(Object_.TOKEN).token, collectionOf(new SuperTypeArg(string))))
                .isFalse();
        // and javac does not take these
        assertThat(proven(STRINGS, collectionOf(new ExtendsTypeArg(Integer_.TOKEN.typeRef()))))
                .isFalse();
        assertThat(proven(STRINGS, collectionOf(new SuperTypeArg(Object_.TOKEN.typeRef()))))
                .isFalse();
    }

    @Test
    void aTypeWithAWildcardOrARawOneIsOfNoOtherParameterization() {
        // capture conversion: List<?> is a Collection<?> to javac
        assertThat(proven(List_.ANY, collectionOf(UnboundedTypeArg.INSTANCE))).isFalse();
        // of the same generic type the arguments are contained as they are written
        assertThat(proven(
                        List_.ANY,
                        new ParameterizedTypeRef(
                                List_.ANY.erasure(), List.of(new ExactTypeArg(String_.TOKEN.typeRef())), List.of())))
                .isFalse();
        assertThat(proven(new List_<>(String_.TOKEN).token, List_.ANY.typeRef()))
                .isTrue();
    }

    @Test
    void theArgumentsOfOneGenericTypeAreContainedAsTheyAreWritten() {
        ClassDesc map = ClassDesc.of("java.util.Map");
        TypeRef string = String_.TOKEN.typeRef();
        TypeRef strings = new List_<>(String_.TOKEN).token.typeRef();
        // a Map of the first argument and of String, asked whether it is a Map of the second and of anything
        BiPredicate<TypeArg, TypeArg> contained = (sub, sup) -> proven(
                UnsafeFacts.interfaceToken(
                        new ParameterizedTypeRef(map, List.of(sub, new ExactTypeArg(string)), List.of()),
                        MethodTable.EMPTY),
                new ParameterizedTypeRef(map, List.of(sup, UnboundedTypeArg.INSTANCE), List.of()));

        assertThat(contained.test(new ExactTypeArg(string), new ExtendsTypeArg(string)))
                .isTrue();
        assertThat(contained.test(new ExtendsTypeArg(string), new ExtendsTypeArg(string)))
                .isTrue();
        assertThat(contained.test(new ExactTypeArg(strings), new ExtendsTypeArg(strings)))
                .isTrue();
        assertThat(contained.test(new ExtendsTypeArg(strings), new ExtendsTypeArg(string)))
                .isFalse();
        assertThat(contained.test(new SuperTypeArg(string), new ExtendsTypeArg(string)))
                .isFalse();
        assertThat(contained.test(UnboundedTypeArg.INSTANCE, new ExtendsTypeArg(string)))
                .isFalse();

        assertThat(contained.test(new ExactTypeArg(string), new SuperTypeArg(string)))
                .isTrue();
        assertThat(contained.test(new SuperTypeArg(string), new SuperTypeArg(string)))
                .isTrue();
        assertThat(contained.test(new SuperTypeArg(strings), new SuperTypeArg(string)))
                .isFalse();
        assertThat(contained.test(new ExtendsTypeArg(string), new SuperTypeArg(string)))
                .isFalse();
        assertThat(contained.test(UnboundedTypeArg.INSTANCE, new SuperTypeArg(string)))
                .isFalse();

        assertThat(contained.test(new ExtendsTypeArg(string), new ExactTypeArg(string)))
                .isFalse();
        // a type of other type arguments in number is none javac would have let be written
        assertThat(proven(
                        UnsafeFacts.interfaceToken(
                                new ParameterizedTypeRef(
                                        map, List.of(new ExactTypeArg(string), new ExactTypeArg(string)), List.of()),
                                MethodTable.EMPTY),
                        new ParameterizedTypeRef(map, List.of(UnboundedTypeArg.INSTANCE), List.of())))
                .isFalse();
    }

    @Test
    void aTypeThatIsNotGenericIsOfTheParameterizationsItsShapeRecordsAndARawOneOfNone() {
        assertThat(proven(String_.TOKEN, new Comparable_<>(String_.TOKEN).token.typeRef()))
                .isTrue();
        assertThat(proven(String_.TOKEN, new Comparable_<>(Integer_.TOKEN).token.typeRef()))
                .isFalse();
        // raw List: a Collection<String> to javac with an unchecked warning
        assertThat(proven(
                        UnsafeFacts.interfaceToken(List_.Data.SHAPE), new Collection_<>(String_.TOKEN).token.typeRef()))
                .isFalse();
    }

    @Test
    void aTypeParameterOfTheClassBeingDeclaredIsOfWhatItsOwnBoundsAreOf() {
        var t = UnsafeFacts.typeVarToken(new TypeVarRef("T", List.of()), ConstantDescs.CD_Object);
        var u = UnsafeFacts.typeVarToken(new TypeVarRef("U", List.of()), ConstantDescs.CD_Object);
        Subtyping.Declaring declaring = new Subtyping.Declaring(
                SELF,
                List.of(),
                List.of(
                        new Subtyping.Bounded(t, List.of(STRINGS)),
                        new Subtyping.Bounded(u, List.of(Runnable_.TOKEN))));

        assertThat(proven(declaring, t, new Collection_<>(String_.TOKEN).token.typeRef()))
                .isTrue();
        assertThat(proven(declaring, u, Runnable_.TOKEN.typeRef())).isTrue();
        // the bound of another type parameter is none of its own
        assertThat(proven(declaring, t, Runnable_.TOKEN.typeRef())).isFalse();
        assertThat(proven(declaring, u, STRINGS.typeRef())).isFalse();
    }

    @Test
    void aSupertypeThatIsNotRecordedIsNeitherProvenNorTakenForNone() {
        // a token vouched for by a type reference records no interface and no supertype
        DeclaredToken<?> unrecorded = UnsafeFacts.interfaceToken(
                new ParameterizedTypeRef(
                        ClassDesc.of(List.class.getName()),
                        List.of(new ExactTypeArg(String_.TOKEN.typeRef())),
                        List.of()),
                new MethodTable(Set.of(), Set.of(), Set.of()));

        assertThat(proven(unrecorded, type(Collection.class))).isFalse();
        assertThat(proven(unrecorded, new Collection_<>(String_.TOKEN).token.typeRef()))
                .isFalse();
        assertThat(proven(unrecorded, unrecorded.typeRef())).isTrue();
        assertThat(proven(unrecorded, Object_.TOKEN.typeRef())).isTrue();
    }

    @Test
    void anArrayOfReferencesIsOfAnArrayOfASupertypeOfItsComponent() {
        ArrayToken<String[], String> strings = ArrayToken.of(String_.TOKEN);
        assertThat(proven(strings, ArrayToken.of(Object_.TOKEN).typeRef())).isTrue();
        assertThat(proven(strings, ArrayToken.of(CharSequence_.TOKEN).typeRef()))
                .isTrue();
        assertThat(proven(
                        ArrayToken.of(strings),
                        ArrayToken.of(ArrayToken.of(Object_.TOKEN)).typeRef()))
                .isTrue();
        assertThat(proven(ArrayToken.of(strings), ArrayToken.of(Object_.TOKEN).typeRef()))
                .isTrue();
        assertThat(proven(strings, type(Cloneable.class))).isTrue();
        assertThat(proven(strings, type(Serializable.class))).isTrue();

        assertThat(proven(strings, ArrayToken.of(Integer_.TOKEN).typeRef())).isFalse();
        assertThat(proven(strings, type(Runnable.class))).isFalse();
        assertThat(proven(strings, new Collection_<>(String_.TOKEN).token.typeRef()))
                .isFalse();
        assertThat(proven(String_.TOKEN, strings.typeRef())).isFalse();
    }

    @Test
    void aPrimitiveAndAnArrayOfPrimitivesAreOfNoOtherType() {
        assertThat(proven(PrimitiveToken.INT, PrimitiveTypeRef.LONG)).isFalse();
        assertThat(proven(PrimitiveToken.INT, Integer_.TOKEN.typeRef())).isFalse();
        assertThat(proven(PrimitiveToken.INT, Object_.TOKEN.typeRef())).isFalse();
        assertThat(proven(Integer_.TOKEN, PrimitiveTypeRef.INT)).isFalse();
        assertThat(proven(
                        PrimitiveToken.INT.array(), ArrayToken.of(Object_.TOKEN).typeRef()))
                .isFalse();
        assertThat(proven(PrimitiveToken.INT.array(), new ArrayTypeRef(PrimitiveTypeRef.LONG, List.of())))
                .isFalse();
        assertThat(proven(PrimitiveToken.INT.array(), PrimitiveToken.INT.array().typeRef()))
                .isTrue();
    }

    @Test
    void aTypeVariableIsOfItselfAndOfObjectAlone() {
        TypeVarRef t = new TypeVarRef("T", List.of());
        TypeToken<?> variable = UnsafeFacts.typeVarToken(t, String_.TOKEN.erasure());

        assertThat(proven(variable, t)).isTrue();
        assertThat(proven(variable, Object_.TOKEN.typeRef())).isTrue();
        // its bound, to javac: the token tells its erasure and not its bounds
        assertThat(proven(variable, String_.TOKEN.typeRef())).isFalse();
        assertThat(proven(variable, new Comparable_<>(String_.TOKEN).token.typeRef()))
                .isFalse();
        assertThat(proven(variable, ArrayToken.of(Object_.TOKEN).typeRef())).isFalse();
        assertThat(proven(String_.TOKEN, t)).isFalse();
    }

    @Test
    void theClassBeingDeclaredIsOfWhatTheSupertypesItHasNamedAreOf() {
        Subtyping.Declaring declaring = new Subtyping.Declaring(SELF, List.of(Object_.TOKEN, STRINGS));

        assertThat(proven(declaring, SELF_TOKEN, STRINGS.typeRef())).isTrue();
        assertThat(proven(declaring, SELF_TOKEN, new Collection_<>(String_.TOKEN).token.typeRef()))
                .isTrue();
        assertThat(proven(declaring, SELF_TOKEN, type(AbstractList.class))).isTrue();
        assertThat(proven(
                        declaring,
                        ArrayToken.of(SELF_TOKEN),
                        ArrayToken.of(STRINGS).typeRef()))
                .isTrue();
        assertThat(proven(declaring, SELF_TOKEN, SELF_TOKEN.typeRef())).isTrue();

        assertThat(proven(declaring, SELF_TOKEN, Runnable_.TOKEN.typeRef())).isFalse();
        assertThat(proven(declaring, SELF_TOKEN, new Collection_<>(Object_.TOKEN).token.typeRef()))
                .isFalse();
        // a supertype it has not named yet is none
        assertThat(proven(NOTHING_NAMED, SELF_TOKEN, STRINGS.typeRef())).isFalse();
        assertThat(proven(declaring, STRINGS, SELF_TOKEN.typeRef())).isFalse();
    }
}
