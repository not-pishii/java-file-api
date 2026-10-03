package gen.facts.p;

import gen.facts.p.Over_.Canonical;
import gen.facts.p.Over_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Bounded;
import p.Over;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Over.class, fingerprint = "65d716ae64043a05f3bdff6df171f97b057ca81ffe95b1f01bf5a5f82b65a5f7", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Over_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Over_"), "65d716ae64043a05f3bdff6df171f97b057ca81ffe95b1f01bf5a5f82b65a5f7", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Over"), List.of(), List.of(ClassDesc.of("p.Bounded"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Bounded"), List.of(Types.exact(Types.of(ClassDesc.of("p.Secret"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("lost"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("same"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Over"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Over open-class sealed=no\ntparams -\nsuperclasses p.Bounded; java.lang.Object\nsupertypes p.Bounded<p.Secret>\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable same() -> p.Bounded<?> throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); lost(); notify(); notifyAll(); same(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Over()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Over> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Over> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Over, Bounded<?>> same = UnsafeFacts.method(TOKEN, "same", UnsafeFacts.<Bounded<?>>openClassToken(Bounded_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    private Over_() {
    }
}
