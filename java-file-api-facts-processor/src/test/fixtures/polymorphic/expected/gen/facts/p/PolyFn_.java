package gen.facts.p;

import gen.facts.p.PolyFn_.Canonical;
import gen.facts.p.PolyFn_.Data;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PolyFn;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PolyFn.class, fingerprint = "3b85a15bb46c17c8fcbd4bc9e74ebfc2a259fc774d0b0089e41939be89fc1a09", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PolyFn_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PolyFn_"), "3b85a15bb46c17c8fcbd4bc9e74ebfc2a259fc774d0b0089e41939be89fc1a09", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PolyFn"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.PolyFn interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract <^0> apply(^0) -> ^0 throws -\ntable abstract apply(java.lang.Object)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<PolyFn> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private PolyFn_() {
    }

    public static <T> MethodRef1<PolyFn, T, T> apply_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "apply", t, UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.ABSTRACT.withTypeArgs(t));
    }
}
