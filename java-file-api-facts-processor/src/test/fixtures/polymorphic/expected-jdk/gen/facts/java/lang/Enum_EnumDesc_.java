package gen.facts.java.lang;

import gen.facts.java.lang.Enum_EnumDesc_.Canonical;
import gen.facts.java.lang.Enum_EnumDesc_.Data;
import java.lang.Enum.EnumDesc;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [EnumDesc]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [EnumDesc]: it is only mentioned in the signatures of [Enum]. For the facts of its members add `Enum.EnumDesc.class` to `@Facts`.
///
/// @param <E> a type argument of [EnumDesc]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = EnumDesc.class, fingerprint = "85457bfb3c17cfe3a495d220dc6b88f51bf74ec23fe869a2f13ff708a29e5770", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Enum_EnumDesc_<E extends Enum<E>> {
    /// The shape of [EnumDesc] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [EnumDesc] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Enum_EnumDesc_"), "85457bfb3c17cfe3a495d220dc6b88f51bf74ec23fe869a2f13ff708a29e5770", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Enum$EnumDesc"), List.of(new TypeParam("E", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.typeVar("E"))))))), List.of(ClassDesc.of("java.lang.constant.DynamicConstantDesc"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.constant.DynamicConstantDesc"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("bootstrapArgs"), Signature.of("bootstrapArgsList"), Signature.of("bootstrapMethod"), Signature.of("clone"), Signature.of("constantName"), Signature.of("constantType"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.constant.ClassDesc")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/constant/ConstantDesc;"))), Signature.of("ofCanonical", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.constant.ClassDesc")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/constant/ConstantDesc;"))), Signature.of("ofNamed", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.constant.ClassDesc")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/constant/ConstantDesc;")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [EnumDesc], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [EnumDesc].
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.Enum$EnumDesc final-class sealed=no\ntparams #0 extends java.lang.Enum<#0>\nsuperclasses java.lang.constant.DynamicConstantDesc; java.lang.Object\nsupertypes java.lang.constant.DynamicConstantDesc<#0>\nenum -\nmembers none\ntable abstract -\ntable concrete bootstrapArgs(); bootstrapArgsList(); bootstrapMethod(); clone(); constantName(); constantType(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); toString(); wait(); wait(long); wait(long, int)\ntable static of(java.lang.constant.ClassDesc, java.lang.String); of(java.lang.constant.DirectMethodHandleDesc); of(java.lang.constant.DirectMethodHandleDesc, java.lang.constant.ConstantDesc[]); ofCanonical(java.lang.constant.DirectMethodHandleDesc, java.lang.String, java.lang.constant.ClassDesc, java.lang.constant.ConstantDesc[]); ofNamed(java.lang.constant.DirectMethodHandleDesc, java.lang.String, java.lang.constant.ClassDesc, java.lang.constant.ConstantDesc[])\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [EnumDesc] with a wildcard for every type argument.
    public static final FinalClassToken<EnumDesc<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [EnumDesc] with the type arguments of this metamodel.
    public final FinalClassToken<EnumDesc<E>> token;

    /// The metamodel of [EnumDesc] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public Enum_EnumDesc_(RefToken<E> e) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(e));
    }
}
