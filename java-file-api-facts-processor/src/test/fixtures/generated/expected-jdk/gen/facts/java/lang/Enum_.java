package gen.facts.java.lang;

import gen.facts.java.lang.Enum_.Canonical;
import gen.facts.java.lang.Enum_.Data;
import java.lang.Enum.EnumDesc;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Enum.class, fingerprint = "28970ae8c6f336b609f610ca9664806c0e2353f326419cf201eba24ba7c54d18", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Enum_<E extends Enum<E>> {
    public static final class Data {
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Enum_"), "28970ae8c6f336b609f610ca9664806c0e2353f326419cf201eba24ba7c54d18", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("java.lang.Enum"), List.of(new TypeParam("E", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.typeVar("E"))))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.var(0)), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Enum", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.lang.Enum abstract-class sealed=no\ntparams #0 extends java.lang.Enum<#0>\nsuperclasses java.lang.Object\nsupertypes java.lang.Comparable<#0>\nenum -\nmembers declared-public\nmember method final compareTo(#0) -> int throws -\nmember method final describeConstable() -> java.util.Optional<java.lang.Enum$EnumDesc<#0>> throws -\nmember method final equals(java.lang.Object) -> boolean throws -\nmember method final getDeclaringClass() -> java.lang.Class<#0> throws -\nmember method final hashCode() -> int throws -\nmember method final name() -> java.lang.String throws -\nmember method final ordinal() -> int throws -\nmember method overridable toString() -> java.lang.String throws -\nmember method static <^0 extends java.lang.Enum<^0>> valueOf(java.lang.Class<^0>, java.lang.String) -> ^0 throws -\ntable abstract -\ntable concrete clone(); compareTo(#0); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)\ntable static valueOf(java.lang.Class, java.lang.String)\ntable ctor Enum(java.lang.String, int)\n";

        private Canonical() {
        }
    }

    public static final AbstractClassToken<Enum<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    public final AbstractClassToken<Enum<E>> token;

    public final MethodRef1<Enum<E>, Int, E> compareTo_E;

    public final MethodRef0<Enum<E>, Optional<EnumDesc<E>>> describeConstable;

    public final MethodRef1<Enum<E>, Bool, Object> equals_Object;

    public final MethodRef0<Enum<E>, Class<E>> getDeclaringClass;

    public final MethodRef0<Enum<E>, Int> hashCode;

    public final MethodRef0<Enum<E>, String> name;

    public final MethodRef0<Enum<E>, Int> ordinal;

    public final MethodRef0<Enum<E>, String> toString;

    public Enum_(RefToken<E> e) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(e));
        this.compareTo_E = UnsafeFacts.method(token, "compareTo", PrimitiveToken.INT, UnsafeFacts.param(e, Param.var(0)), MemberTraits.FINAL);
        this.describeConstable = UnsafeFacts.method(token, "describeConstable", UnsafeFacts.<Optional<EnumDesc<E>>>finalClassToken(gen.facts.java.util.Optional_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<EnumDesc<E>>finalClassToken(Enum_EnumDesc_.Data.SHAPE, TokenArg.exact(e)))), MemberTraits.FINAL);
        this.equals_Object = UnsafeFacts.method(token, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(Object_.Data.SHAPE), MemberTraits.FINAL);
        this.getDeclaringClass = UnsafeFacts.method(token, "getDeclaringClass", UnsafeFacts.<Class<E>>finalClassToken(Class_.Data.SHAPE, TokenArg.exact(e)), MemberTraits.FINAL);
        this.hashCode = UnsafeFacts.method(token, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);
        this.name = UnsafeFacts.method(token, "name", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL);
        this.ordinal = UnsafeFacts.method(token, "ordinal", PrimitiveToken.INT, MemberTraits.FINAL);
        this.toString = UnsafeFacts.method(token, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.OVERRIDABLE);
    }

    public static <T extends Enum<T>> StaticMethodRef2<T, Class<T>, String> valueOf_Class_String(RefToken<T> t) {
        return UnsafeFacts.staticMethod(ANY, "valueOf", t, UnsafeFacts.<Class<T>>finalClassToken(Class_.Data.SHAPE, TokenArg.exact(t)), UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL.withTypeArgs(t));
    }
}
