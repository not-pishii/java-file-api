import gen.facts.java.lang.Number_;
import gen.facts.java.lang.String_;
import gen.facts.p.Lists_;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.VoidMethodRef2;
import p.Lists;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// An array of a type parameter of the method is erased as the method declares it (to its bound), and an array of a
/// type parameter of the type is an array of the type argument.
public final class MethodArrays {
    private MethodArrays() {}

    public static void anArrayOfATypeParameterOfTheMethodIsErasedAsTheMethodDeclaresIt() {
        Lists_<String> lists = new Lists_<>(String_.TOKEN);
        VoidMethodRef2<Lists<String>, Number[], String[]> all = lists.all_UArray_TArray(Number_.TOKEN);

        assertThat(all.declared())
                .isEqualTo(Signature.of(
                        "all", Param.fixed(ClassDesc.of("java.lang.Number").arrayType()), Param.var(0, 1)));
        assertThat(all.signature())
                .isEqualTo(new MethodSignature(
                        "all",
                        List.of(
                                ClassDesc.of("java.lang.Number").arrayType(),
                                ClassDesc.of("java.lang.String").arrayType())));
    }
}
