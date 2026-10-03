package gen.facts.java.lang;

import gen.facts.java.lang.Comparable_.Canonical;
import gen.facts.java.lang.Comparable_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Comparable.class, fingerprint = "627af1e361cd72c3f6d0fb669479b0bced1dd9417907d34c74a01e1057192b1e", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Comparable_<T> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Comparable_"), "627af1e361cd72c3f6d0fb669479b0bced1dd9417907d34c74a01e1057192b1e", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.Comparable"), List.of(new TypeParam("T", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.var(0))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.lang.Comparable interface sealed=no\ntparams #0\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract compareTo(#0) -> int throws -\nsam compareTo(#0) -> int throws -\ntable abstract compareTo(#0)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Comparable<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    public final InterfaceToken<Comparable<T>> token;

    public final MethodRef1<Comparable<T>, Int, T> compareTo_T;

    public final Sam1<Comparable<T>, Int, T> sam;

    public Comparable_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.compareTo_T = UnsafeFacts.method(token, "compareTo", PrimitiveToken.INT, UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(compareTo_T);
    }
}
