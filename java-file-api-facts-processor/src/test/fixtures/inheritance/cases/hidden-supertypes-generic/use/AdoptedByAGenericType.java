import gen.facts.java.lang.String_;
import gen.facts.p.Gen_;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import p.Gen;

import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// `Gen<E> extends Near<E>`: the members it adopts are in terms of `E`.
public final class AdoptedByAGenericType {
    private AdoptedByAGenericType() {}

    public static void theAdoptedMembersAreInTermsOfTheTypeParameters() {
        Gen_<String> gen = new Gen_<>(String_.TOKEN);
        MethodRef0<Gen<String>, String> get = gen.get;
        VoidMethodRef1<Gen<String>, String> set = gen.set_E;
        MutableFieldRef<Gen<String>, String> item = gen.item;

        assertThat(get.result()).isSameAs(String_.TOKEN);
        assertThat(get.owner()).isSameAs(gen.token);
        assertThat(set.signature()).isEqualTo(new MethodSignature("set", List.of(ConstantDescs.CD_String)));
        assertThat(item.type()).isSameAs(String_.TOKEN);
    }

    public static void theAdoptedMethodsAreThoseOfTheFinalClassThatHasThem() {
        Gen_<String> gen = new Gen_<>(String_.TOKEN);

        assertThat(gen.near.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(gen.overridden.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(gen.dflt.traits()).isEqualTo(MemberTraits.FINAL);
    }

    public static void theAdoptedStaticMembersAreFactsOfTheClass() {
        StaticFieldRef<String> far = Gen_.FAR;
        StaticMethodRef0<String> snear = Gen_.snear;

        assertThat(far.owner()).isSameAs(Gen_.ANY);
        assertThat(snear.owner()).isSameAs(Gen_.ANY);
    }
}
