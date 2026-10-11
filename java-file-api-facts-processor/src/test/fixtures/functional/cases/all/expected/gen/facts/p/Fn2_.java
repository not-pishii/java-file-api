package gen.facts.p;

import gen.facts.p.Fn2_.Canonical;
import gen.facts.p.Fn2_.Data;
import gen.facts.p.Fn2_Heritage.Implement;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidSam3;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Fn2;

/// The full metamodel of [Fn2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fn2] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Fn2] by the proof its metamodel starts, with the stages of `Fn2_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fn2.class, fingerprint = "65a52da47d551d4f5cb7b45d8c51244ce1f765d387e8f64d0fb18c81ba922a85", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Fn2_ {
    /// The shape of [Fn2] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Fn2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fn2_"), "65a52da47d551d4f5cb7b45d8c51244ce1f765d387e8f64d0fb18c81ba922a85", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Fn2"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("call", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Fn2_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Fn2], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Fn2].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Fn2 interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract call(java.lang.String, int, long) -> void throws -
        sam call(java.lang.String, int, long) -> void throws -
        inherit method public abstract p.Fn2 call(java.lang.String, int, long) -> void throws - erased (java.lang.String, int, long) overrides -
        table abstract call(java.lang.String, int, long)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

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

    /// Starts the proof that a class being declared implements [Fn2]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Fn2_Heritage().implement(TOKEN, self);
    }
}
