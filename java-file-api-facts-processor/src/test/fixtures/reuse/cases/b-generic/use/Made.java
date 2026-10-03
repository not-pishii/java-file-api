import a.facts.p.Box_;
import b.facts.p.Uses_;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodSignature;
import p.Box;
import p.Uses;

import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The generic metamodel of another module is reused for the types made of it: `Box<String>` of this module's
/// `Uses` is of the shape of `a.facts.p.Box_`.
public final class Made {
    private Made() {}

    public static void aGenericFullMetamodelOfAnotherModuleIsReusedForTheTypesMadeOfIt() {
        MethodRef0<Uses, Box<String>> strings = Uses_.strings;
        DeclaredToken<?> made = (DeclaredToken<?>) strings.resultType().orElseThrow();

        assertThat(made.shape()).isSameAs(Box_.Data.SHAPE);
        // the table of the other module's shape, under the type argument of this one
        assertThat(made.methods().concreteMethods())
                .contains(new MethodSignature("all", List.of(ConstantDescs.CD_String.arrayType())));
    }
}
