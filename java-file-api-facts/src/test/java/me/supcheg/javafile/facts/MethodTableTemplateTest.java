package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The method table of a type before its type arguments are known, and its
/// instantiation with their erasures.
class MethodTableTemplateTest {

    // interface Box<T, U> { void put(T); void put(int, U); String name(); boolean equals(Object) }
    private static final MethodTableTemplate BOX = new MethodTableTemplate(
            Set.of(
                    Signature.of("put", Param.var(0)),
                    Signature.of("put", Param.fixed(ConstantDescs.CD_int), Param.var(1)),
                    Signature.of("name")),
            Set.of(Signature.of("equals", Param.fixed(ConstantDescs.CD_Object))),
            Set.of(Signature.of("of", Param.var(1)), Signature.of("of", Param.fixed(ConstantDescs.CD_String))));

    @Test
    void instantiatingReplacesEachVariableByTheErasureAtItsIndex() {
        MethodTable table = BOX.instantiate(List.of(ConstantDescs.CD_String, ConstantDescs.CD_Integer));

        assertThat(table.abstractMethods())
                .containsExactlyInAnyOrder(
                        new MethodSignature("put", List.of(ConstantDescs.CD_String)),
                        new MethodSignature("put", List.of(ConstantDescs.CD_int, ConstantDescs.CD_Integer)),
                        new MethodSignature("name", List.of()));
        assertThat(table.concreteMethods())
                .containsExactly(new MethodSignature("equals", List.of(ConstantDescs.CD_Object)));
        assertThat(table.staticMethods())
                .containsExactlyInAnyOrder(
                        new MethodSignature("of", List.of(ConstantDescs.CD_Integer)),
                        new MethodSignature("of", List.of(ConstantDescs.CD_String)));
    }

    @Test
    void instantiatingMergesSignaturesThatEraseAlikeButTheTemplateKeepsThemApart() {
        MethodTable table = BOX.instantiate(List.of(ConstantDescs.CD_String, ConstantDescs.CD_String));

        assertThat(table.staticMethods()).containsExactly(new MethodSignature("of", List.of(ConstantDescs.CD_String)));
        assertThat(BOX.staticMethods()).hasSize(2);
    }

    @Test
    void theTemplateNeedsAnErasureForEveryTypeParameterItRefersTo() {
        assertThat(BOX.typeParameterCount()).isEqualTo(2);
        assertThat(MethodTableTemplate.EMPTY.typeParameterCount()).isZero();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> BOX.instantiate(List.of(ConstantDescs.CD_String)))
                .withMessage("the template refers to 2 type parameters, but 1 type arguments are given");
    }

    @Test
    void aTableMakesATemplateOfFixedParameters() {
        MethodTable table = new MethodTable(
                Set.of(new MethodSignature("run", List.of())),
                Set.of(new MethodSignature("accept", List.of(ConstantDescs.CD_String))),
                Set.of(new MethodSignature("of", List.of(ConstantDescs.CD_int))));

        MethodTableTemplate template = MethodTableTemplate.of(table);

        assertThat(template.concreteMethods())
                .containsExactly(Signature.of("accept", Param.fixed(ConstantDescs.CD_String)));
        assertThat(template.staticMethods()).containsExactly(Signature.of("of", Param.fixed(ConstantDescs.CD_int)));
        assertThat(template.typeParameterCount()).isZero();
        assertThat(template.instantiate(List.of())).isEqualTo(table);
    }

    @Test
    void anInvalidTemplateIsRejected() {
        Signature run = Signature.of("run");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new MethodTableTemplate(Set.of(run), Set.of(run), Set.of()))
                .withMessage("methods both abstract and concrete: [run()]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new MethodTableTemplate(Set.of(), Set.of(run), Set.of(run)))
                .withMessage("methods both static and instance: [run()]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Param.var(-1))
                .withMessage("a type parameter index cannot be negative, got -1");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Param.fixed(ConstantDescs.CD_void))
                .withMessage("a parameter cannot be void");
        assertThatIllegalArgumentException().isThrownBy(() -> Signature.of("not a name"));
    }

    @Test
    void signaturesReadAsDeclarations() {
        assertThat(Signature.of("put", Param.fixed(ClassDesc.of("java.lang.String")), Param.var(1)))
                .hasToString("put(java.lang.String, #1)");
    }
}
