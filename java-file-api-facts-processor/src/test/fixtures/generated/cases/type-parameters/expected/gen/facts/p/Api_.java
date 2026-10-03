package gen.facts.p;

import gen.facts.p.Api_.Canonical;
import gen.facts.p.Api_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Api;
import p.Raw;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Api.class, fingerprint = "06b9063bbb17b4a9183f52e67ba6a52b0147230ef65238d9b1a376bb911bac4d", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Api_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Api_"), "06b9063bbb17b4a9183f52e67ba6a52b0147230ef65238d9b1a376bb911bac4d", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Api"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("raw")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Api interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract raw() -> p.Raw<?> throws -\nsam raw() -> p.Raw<?> throws -\ntable abstract raw()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Api> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final MethodRef0<Api, Raw<?>> raw = UnsafeFacts.method(TOKEN, "raw", UnsafeFacts.<Raw<?>>openClassToken(Raw_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.ABSTRACT);

    public static final Sam0<Api, Raw<?>> sam = UnsafeFacts.sam(raw);

    private Api_() {
    }
}
