package gen.facts.p;

import gen.facts.p.Canonical_.Canonical;
import gen.facts.p.Canonical_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = p.Canonical.class, fingerprint = "2ad3f9dbd90b859182a38130d8bdd5358fd40310efc105b426ee2482263f3f32", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Canonical_<Data_ extends p.Data> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Canonical_"), "2ad3f9dbd90b859182a38130d8bdd5358fd40310efc105b426ee2482263f3f32", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Canonical"), List.of(new TypeParam("Data", List.of(Types.of(ClassDesc.of("p.Data"))))), List.of(), new Supertypes(List.of(Types.typeVar("Data")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Canonical interface sealed=no\ntparams #0 extends p.Data\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<p.Canonical<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    public final InterfaceToken<p.Canonical<Data_>> token;

    public Canonical_(RefToken<Data_> data_) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(data_));
    }
}
