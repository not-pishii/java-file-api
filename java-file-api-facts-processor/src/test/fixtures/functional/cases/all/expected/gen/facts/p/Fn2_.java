package gen.facts.p;

import gen.facts.p.Fn2_.Canonical;
import gen.facts.p.Fn2_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidSam3;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Fn2;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fn2.class, fingerprint = "11f18e01e563ed08e5ec48c991eca6d9e6f9c518c357aae7bb6846f34690f373", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Fn2_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fn2_"), "11f18e01e563ed08e5ec48c991eca6d9e6f9c518c357aae7bb6846f34690f373", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Fn2"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("call", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Fn2 interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract call(java.lang.String, int, long) -> void throws -\nsam call(java.lang.String, int, long) -> void throws -\ntable abstract call(java.lang.String, int, long)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Fn2> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final VoidMethodRef3<Fn2, String, Int, Long> call_String_int_long = UnsafeFacts.voidMethod(TOKEN, "call", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), PrimitiveToken.INT, PrimitiveToken.LONG, MemberTraits.ABSTRACT);

    public static final VoidSam3<Fn2, String, Int, Long> sam = UnsafeFacts.voidSam(call_String_int_long);

    private Fn2_() {
    }
}
