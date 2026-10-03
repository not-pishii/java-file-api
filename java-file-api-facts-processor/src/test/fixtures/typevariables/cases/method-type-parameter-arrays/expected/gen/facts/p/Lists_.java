package gen.facts.p;

import gen.facts.p.Lists_.Canonical;
import gen.facts.p.Lists_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Lists;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Lists.class, fingerprint = "97d388233021cf8873d62b056993d281f4f8238ef02b3d06c4a817ccb35c81fe", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Lists_<T> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Lists_"), "97d388233021cf8873d62b056993d281f4f8238ef02b3d06c4a817ccb35c81fe", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Lists"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Number;")), Param.var(0, 1)), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Lists"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Lists open-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable <^0 extends java.lang.Number> all(^0[], #0[]) -> void throws -\ntable abstract -\ntable concrete all(java.lang.Number[], #0[]); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Lists()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Lists<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    public final OpenClassToken<Lists<T>> token;

    public final CtorRef0<Lists<T>> new_;

    private final RefToken<T> t;

    public Lists_(RefToken<T> t) {
        this.t = t;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
    }

    public <U extends Number> VoidMethodRef2<Lists<T>, U[], T[]> all_UArray_TArray(RefToken<U> u) {
        return UnsafeFacts.voidMethod(token, "all", UnsafeFacts.param(ArrayToken.of(u), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Number;"))), UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.OVERRIDABLE.withTypeArgs(u));
    }
}
