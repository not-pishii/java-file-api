package gen.facts.p;

import gen.facts.p.Arr_.Canonical;
import gen.facts.p.Arr_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Arr;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Arr.class, fingerprint = "b7f8078b8a9f1d55233325f4a4908cc79f2ad9c3ca382305d39922003c36d40d", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Arr_<T> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Arr_"), "b7f8078b8a9f1d55233325f4a4908cc79f2ad9c3ca382305d39922003c36d40d", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Arr"), List.of(new TypeParam("T", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("m", Param.var(0, 1))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Arr interface sealed=no\ntparams #0\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract m(#0[]) -> #0[] throws -\nsam m(#0[]) -> #0[] throws -\ntable abstract m(#0[])\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Arr<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    public final InterfaceToken<Arr<T>> token;

    public final MethodRef1<Arr<T>, T[], T[]> m_TArray;

    public final Sam1<Arr<T>, T[], T[]> sam;

    public Arr_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.m_TArray = UnsafeFacts.method(token, "m", ArrayToken.of(t), UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(m_TArray);
    }
}
