package gen.facts.p;

import gen.facts.p.WithDefault_.Canonical;
import gen.facts.p.WithDefault_.Data;
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
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.WithDefault;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = WithDefault.class, fingerprint = "dc3247c5e7cdb410651fb84b04f23b8ef1cb8fb42463ed20f2d9e8608a92080e", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class WithDefault_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.WithDefault_"), "dc3247c5e7cdb410651fb84b04f23b8ef1cb8fb42463ed20f2d9e8608a92080e", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.WithDefault"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("f", Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("g", Param.fixed(ConstantDescs.CD_int)), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("id")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.WithDefault interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract f(int) -> int throws -\nmember method overridable g(int) -> int throws -\nmember method static id() -> p.WithDefault throws -\nsam f(int) -> int throws -\ntable abstract f(int)\ntable concrete equals(java.lang.Object); g(int); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static id()\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<WithDefault> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final MethodRef1<WithDefault, Int, Int> f_int = UnsafeFacts.method(TOKEN, "f", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.ABSTRACT);

    public static final MethodRef1<WithDefault, Int, Int> g_int = UnsafeFacts.method(TOKEN, "g", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    public static final StaticMethodRef0<WithDefault> id = UnsafeFacts.staticMethod(TOKEN, "id", TOKEN, MemberTraits.FINAL);

    public static final Sam1<WithDefault, Int, Int> sam = UnsafeFacts.sam(f_int);

    private WithDefault_() {
    }
}
