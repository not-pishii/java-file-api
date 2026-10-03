package gen.facts.p;

import gen.facts.p.Fail_.Canonical;
import gen.facts.p.Fail_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Fail;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fail.class, fingerprint = "22446bd6de9d985fa61b3f14b0408a7b351bbee0b8252835c85cd2bb85013582", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Fail_<X extends Exception> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fail_"), "22446bd6de9d985fa61b3f14b0408a7b351bbee0b8252835c85cd2bb85013582", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Fail"), List.of(new TypeParam("X", List.of(Types.of(ClassDesc.of("java.lang.Exception"))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("X")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Fail"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Fail open-class sealed=no\ntparams #0 extends java.lang.Exception\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable run() -> void throws #0\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Fail()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Fail<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    public final OpenClassToken<Fail<X>> token;

    public final CtorRef0<Fail<X>> new_;

    public final VoidMethodRef0<Fail<X>> run;

    public Fail_(RefToken<X> x) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(x));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.run = UnsafeFacts.voidMethod(token, "run", MemberTraits.OVERRIDABLE.throwing(x));
    }
}
