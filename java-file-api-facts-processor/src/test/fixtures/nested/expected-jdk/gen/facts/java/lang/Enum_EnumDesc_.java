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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = EnumDesc.class, fingerprint = "7ad1b8bc8b86bcd7dc9b43f9b45c9380a5143b2b6dee16eb7c25f2dec1cdfbc2", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Enum_EnumDesc_<E extends Enum<E>> {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Enum_EnumDesc_"), "7ad1b8bc8b86bcd7dc9b43f9b45c9380a5143b2b6dee16eb7c25f2dec1cdfbc2", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Enum$EnumDesc"), List.of(new TypeParam("E", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.typeVar("E"))))))), List.of(ClassDesc.of("java.lang.constant.DynamicConstantDesc"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.constant.DynamicConstantDesc"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("bootstrapArgs"), Signature.of("bootstrapArgsList"), Signature.of("bootstrapMethod"), Signature.of("clone"), Signature.of("constantName"), Signature.of("constantType"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("resolveConstantDesc", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of", Param.fixed(ClassDesc.of("java.lang.constant.ClassDesc")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/constant/ConstantDesc;"))), Signature.of("ofCanonical", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.constant.ClassDesc")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/constant/ConstantDesc;"))), Signature.of("ofNamed", Param.fixed(ClassDesc.of("java.lang.constant.DirectMethodHandleDesc")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.constant.ClassDesc")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/constant/ConstantDesc;")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.lang.Enum$EnumDesc final-class sealed=no\ntparams #0 extends java.lang.Enum<#0>\nsuperclasses java.lang.constant.DynamicConstantDesc; java.lang.Object\nsupertypes java.lang.constant.DynamicConstantDesc<#0>\nenum -\nmembers none\ntable abstract -\ntable concrete bootstrapArgs(); bootstrapArgsList(); bootstrapMethod(); clone(); constantName(); constantType(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); resolveConstantDesc(java.lang.invoke.MethodHandles$Lookup); toString(); wait(); wait(long); wait(long, int)\ntable static of(java.lang.constant.ClassDesc, java.lang.String); of(java.lang.constant.DirectMethodHandleDesc); of(java.lang.constant.DirectMethodHandleDesc, java.lang.constant.ConstantDesc[]); ofCanonical(java.lang.constant.DirectMethodHandleDesc, java.lang.String, java.lang.constant.ClassDesc, java.lang.constant.ConstantDesc[]); ofNamed(java.lang.constant.DirectMethodHandleDesc, java.lang.String, java.lang.constant.ClassDesc, java.lang.constant.ConstantDesc[])\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<EnumDesc<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    public final FinalClassToken<EnumDesc<E>> token;

    public Enum_EnumDesc_(RefToken<E> e) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(e));
    }
}
