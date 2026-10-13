package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.testfacts.java.lang.Integer_;
import me.supcheg.javafile.facts.testfacts.java.lang.String_;
import me.supcheg.javafile.facts.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.facts.testfacts.java.util.function.Function_;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The parameterized supertypes a token records, and their substitution.
class SupertypesTest {

    private static final ClassDesc ABSTRACT_LIST = ClassDesc.of("java.util", "AbstractList");
    private static final ClassDesc ITERABLE = ClassDesc.of("java.lang", "Iterable");

    @Test
    void theJdkMetamodelRecordsEveryParameterizedSupertypeInTermsOfTheTypeParameters() {
        Supertypes supertypes = new ArrayList_<>(String_.TOKEN).token.supertypes();

        assertThat(supertypes.typeParameters()).containsExactly(Types.typeVar("E"));
        assertThat(supertypes.supertypes())
                .contains(
                        Types.parameterized(ABSTRACT_LIST, Types.typeVar("E")),
                        Types.parameterized(ConstantDescs.CD_List, Types.typeVar("E")),
                        Types.parameterized(ConstantDescs.CD_Collection, Types.typeVar("E")),
                        Types.parameterized(ITERABLE, Types.typeVar("E")));
        assertThat(supertypes.supertype(ConstantDescs.CD_List, List.of(Types.STRING)))
                .contains(Types.parameterized(ConstantDescs.CD_List, Types.STRING));
        assertThat(supertypes.supertype(ConstantDescs.CD_Map, List.of(Types.STRING)))
                .isEmpty();
        assertThat(new Function_<>(String_.TOKEN, Integer_.TOKEN)
                        .token
                        .supertypes()
                        .typeParameters())
                .containsExactly(Types.typeVar("T"), Types.typeVar("R"));
        assertThat(String_.TOKEN.supertypes().supertypes())
                .containsExactly(Types.parameterized(ClassDesc.of("java.lang", "Comparable"), Types.STRING));
    }

    @Test
    void theTypeArgumentsStandForTheTypeParametersWhereverASupertypeMentionsThem() {
        ClassDesc function = ClassDesc.of("java.util.function", "Function");
        ClassDesc comparable = ClassDesc.of("java.lang", "Comparable");
        Supertypes supertypes = new Supertypes(
                List.of(Types.typeVar("T")),
                List.of(
                        // Function<? super T, ? extends T[]>
                        Types.parameterized(
                                function,
                                Types.superBound(Types.typeVar("T")),
                                Types.extendsBound(Types.array(Types.typeVar("T")))),
                        // Map<?, int[]>
                        Types.parameterized(
                                ConstantDescs.CD_Map, Types.unbounded(), Types.exact(Types.array(Types.INT))),
                        Types.parameterized(comparable, Types.STRING)));

        assertThat(supertypes.supertype(function, List.of(Types.STRING)))
                .contains(Types.parameterized(
                        function, Types.superBound(Types.STRING), Types.extendsBound(Types.array(Types.STRING))));
        assertThat(supertypes.supertype(ConstantDescs.CD_Map, List.of(Types.STRING)))
                .contains(Types.parameterized(
                        ConstantDescs.CD_Map, Types.unbounded(), Types.exact(Types.array(Types.INT))));
        assertThat(supertypes.supertype(comparable, List.of(Types.OBJECT)))
                .contains(Types.parameterized(comparable, Types.STRING));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> supertypes.supertype(function, List.of()))
                .withMessage("expected 1 type arguments, got 0");
    }

    @Test
    void supertypesNameOnlyDeclaredTypeParameters() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Supertypes(List.of(Types.typeVar("E"), Types.typeVar("E")), List.of()))
                .withMessage("duplicate type parameter E");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Supertypes(
                        List.of(Types.typeVar("E")),
                        List.of(Types.parameterized(ConstantDescs.CD_List, Types.typeVar("F")))))
                .withMessage("a supertype names undeclared type variable F");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Supertypes(
                        List.of(Types.typeVar("E")),
                        List.of(
                                Types.parameterized(ConstantDescs.CD_List, Types.typeVar("E")),
                                Types.parameterized(ConstantDescs.CD_List, Types.STRING))))
                .withMessage("two supertypes of generic type List");
    }

    @Test
    void aTokenRecordsSupertypesForAsManyTypeParametersAsItHasTypeArguments() {
        Supertypes ofOneParameter = new Supertypes(List.of(Types.typeVar("E")), List.of());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.interfaceToken(
                        Types.parameterized(ConstantDescs.CD_Map, Types.STRING, Types.STRING),
                        ofOneParameter,
                        MethodTable.EMPTY))
                .withMessageContaining("are given for 1 type parameters, but it has 2 type arguments");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.interfaceToken(Types.LIST, ofOneParameter, MethodTable.EMPTY))
                .withMessageContaining("but it has 0 type arguments");
    }
}
