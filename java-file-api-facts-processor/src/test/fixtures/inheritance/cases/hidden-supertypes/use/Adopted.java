import gen.facts.p.Pub_;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.type.Types;
import p.Pub;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The members of the supertypes of `Pub` that are not `public` — `Near`,
/// `Far` and `HiddenApi` — are facts of `Pub`.
public final class Adopted {
    private Adopted() {}

    /// `HID` is a constant of `Near` and of `Far`: `Pub.HID` is that of `Near`.
    public static void aHiddenFieldIsTheNearerDeclaration() {
        StaticFieldRef<String> hid = Pub_.HID;
        StaticFieldRef<String> ofTheInterface = Pub_.CONST;

        assertThat(hid.constantValue()).contains("near");
        assertThat(ofTheInterface.constantValue()).contains("const");
    }

    public static void theOwnerOfAnAdoptedMemberIsTheSubtype() {
        assertThat(Stream.<Invocable>of(
                                Pub_.near, Pub_.get, Pub_.set_String, Pub_.sfar, Pub_.snear, Pub_.dflt, Pub_.overridden)
                        .map(Invocable::owner))
                .containsOnly(Pub_.TOKEN);
        assertThat(Pub_.FAR.owner()).isEqualTo(Pub_.TOKEN);
    }

    /// `Pub extends Near<String>`: `T` of `Far<T>` is `String`.
    public static void theSignatureOfAnAdoptedMemberIsInTheTermsOfTheSubtype() {
        MethodRef0<Pub, String> get = Pub_.get;
        VoidMethodRef1<Pub, String> set = Pub_.set_String;
        MutableFieldRef<Pub, String> item = Pub_.item;

        assertThat(get.result().typeRef()).isEqualTo(Types.STRING);
        assertThat(set.params()).extracting(TypeToken::typeRef).containsExactly(Types.STRING);
        assertThat(item.type().typeRef()).isEqualTo(Types.STRING);
    }
}
