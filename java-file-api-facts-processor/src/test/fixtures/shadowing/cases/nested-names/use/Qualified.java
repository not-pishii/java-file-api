import gen.facts.p.Canonical_;
import gen.facts.p.Data_;
import gen.facts.p.List_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.type.Types;
import p.Canonical;
import p.Data;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// A type named like a nested class of the metamodel (`Data`, `Canonical`) is written qualified there, in a
/// signature of another type and as the type of the metamodel itself.
public final class Qualified {
    private Qualified() {}

    public static void aSignatureOfAnotherTypeMentionsTheTypeByItsMetamodel() {
        MethodRef0<p.List, Data> load = List_.load;
        MethodRef1<p.List, Canonical, Data> canonical = List_.canonical_Data;

        assertThat(load.name()).isEqualTo("load");
        assertThat(canonical.name()).isEqualTo("canonical");
        assertThat(load.resultType().orElseThrow()).isEqualTo(Data_.TOKEN);
        assertThat(canonical.resultType().orElseThrow()).isEqualTo(Canonical_.TOKEN);
    }

    public static void theMetamodelOfSuchATypeIsOfThatType() {
        assertThat(Data_.TOKEN.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Data")));
        assertThat(Canonical_.TOKEN.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Canonical")));
    }

    public static void theFactsOfSuchATypeKeepTheirNames() {
        MethodRef0<Data, Data> self = Data_.self;
        MutableFieldRef<Data, Canonical> canonical = Data_.canonical;
        MutableFieldRef<Canonical, Data> data = Canonical_.data;

        assertThat(self.name()).isEqualTo("self");
        assertThat(canonical.name()).isEqualTo("canonical");
        assertThat(data.name()).isEqualTo("data");
    }
}
