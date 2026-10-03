package gen.facts.p;

import gen.facts.p.Ov2_.Canonical;
import gen.facts.p.Ov2_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
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
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Ov2;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ov2.class, fingerprint = "398775f14b48cd564d137e71072608588a53d690bf08df964dfa11a567ead71b", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Ov2_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ov2_"), "398775f14b48cd564d137e71072608588a53d690bf08df964dfa11a567ead71b", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ov2"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("wrap", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("wrap", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Ov2"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Ov2 open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method static <^0> wrap(^0) -> java.util.Optional<^0> throws -\nmember method static wrap(java.lang.String) -> java.lang.StringBuilder throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static wrap(java.lang.Object); wrap(java.lang.String)\ntable ctor Ov2()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Ov2> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Ov2> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final StaticMethodRef1<StringBuilder, String> wrap_String = UnsafeFacts.staticMethod(TOKEN, "wrap", UnsafeFacts.<StringBuilder>finalClassToken(gen.facts.java.lang.StringBuilder_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Ov2_() {
    }

    public static <T> StaticMethodRef1<Optional<T>, T> wrap_T(RefToken<T> t) {
        return UnsafeFacts.staticMethod(TOKEN, "wrap", UnsafeFacts.<Optional<T>>finalClassToken(gen.facts.java.util.Optional_.Data.SHAPE, TokenArg.exact(t)), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(t));
    }
}
