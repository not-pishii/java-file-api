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

/// The full metamodel of [Fn2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fn2] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fn2.class, fingerprint = "c0a8a7e447b489c0a1eaa9b99ddc457781bc5e674a931b6ec825ff879f48c135", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Fn2_ {
    /// The shape of [Fn2] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Fn2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fn2_"), "c0a8a7e447b489c0a1eaa9b99ddc457781bc5e674a931b6ec825ff879f48c135", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Fn2"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("call", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Fn2], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Fn2].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Fn2 interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract call(java.lang.String, int, long) -> void throws -\nsam call(java.lang.String, int, long) -> void throws -\ntable abstract call(java.lang.String, int, long)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Fn2].
    public static final InterfaceToken<Fn2> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Fn2#call(String, int, long)].
    public static final VoidMethodRef3<Fn2, String, Int, Long> call_String_int_long = UnsafeFacts.voidMethod(TOKEN, "call", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), PrimitiveToken.INT, PrimitiveToken.LONG, MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Fn2#call(String, int, long)], which a lambda implements.
    public static final VoidSam3<Fn2, String, Int, Long> sam = UnsafeFacts.voidSam(call_String_int_long);

    private Fn2_() {
    }
}
