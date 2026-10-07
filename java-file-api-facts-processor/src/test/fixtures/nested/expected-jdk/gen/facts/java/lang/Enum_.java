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
import me.supcheg.javafile.facts.Access;
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
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.SuperCtorRef2;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Enum]: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// `@Facts` does not ask for [Enum]: it is here as a supertype of [p.Outer.E], whose inherited members are called through this metamodel.
///
/// A member [Enum] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.io.Serializable_], [Comparable_], [Object_] and [gen.facts.java.lang.constant.Constable_].
///
/// @param <E> a type argument of [Enum]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Enum.class, fingerprint = "1e9fdf9ff7985f7a31a5518aeef1a717344f4bb1789f82657d766ce6896fe22f", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Enum_<E extends Enum<E>> {
    /// The shape of [Enum] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Enum] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Enum_"), "1e9fdf9ff7985f7a31a5518aeef1a717344f4bb1789f82657d766ce6896fe22f", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("java.lang.Enum"), List.of(new TypeParam("E", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.typeVar("E"))))))), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.var(0)), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Enum", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Enum], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Enum].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.Enum abstract-class sealed=no
        tparams #0 extends java.lang.Enum<#0>
        superclasses java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<#0>
        enum -
        members declared-accessible
        member ctor protected (java.lang.String, int) throws -
        member method protected final clone() -> java.lang.Object throws java.lang.CloneNotSupportedException
        member method protected final finalize() -> void throws -
        member method public final compareTo(#0) -> int throws -
        member method public final describeConstable() -> java.util.Optional<java.lang.Enum$EnumDesc<#0>> throws -
        member method public final equals(java.lang.Object) -> boolean throws -
        member method public final getDeclaringClass() -> java.lang.Class<#0> throws -
        member method public final hashCode() -> int throws -
        member method public final name() -> java.lang.String throws -
        member method public final ordinal() -> int throws -
        member method public overridable toString() -> java.lang.String throws -
        member method public static <^0 extends java.lang.Enum<^0>> valueOf(java.lang.Class<^0>, java.lang.String) -> ^0 throws -
        table abstract -
        table concrete clone(); compareTo(#0); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); toString(); wait(); wait(long); wait(long, int)
        table static valueOf(java.lang.Class, java.lang.String)
        table ctor Enum(java.lang.String, int)
        """;

        private Canonical() {
        }
    }

    /// The token of [Enum] with a wildcard for every type argument.
    public static final AbstractClassToken<Enum<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Enum] with the type arguments of this metamodel.
    public final AbstractClassToken<Enum<E>> token;

    /// The fact of [Enum#Enum(String, int)], which is `protected`: a subclass alone uses it.
    public final SuperCtorRef2<Enum<E>, String, Int> super_String_int;

    /// The fact of [Enum#clone()], which is `protected`: a subclass alone uses it.
    public final Protected<Enum<E>, MethodRef0<Enum<E>, Object>> clone;

    /// The fact of [Enum#compareTo(Enum)].
    public final MethodRef1<Enum<E>, Int, E> compareTo_E;

    /// The fact of [Enum#describeConstable()].
    public final MethodRef0<Enum<E>, Optional<EnumDesc<E>>> describeConstable;

    /// The fact of [Enum#equals(Object)].
    public final MethodRef1<Enum<E>, Bool, Object> equals_Object;

    /// The fact of [Enum#finalize()], which is `protected`: a subclass alone uses it.
    public final Protected<Enum<E>, VoidMethodRef0<Enum<E>>> finalize;

    /// The fact of [Enum#getDeclaringClass()].
    public final MethodRef0<Enum<E>, Class<E>> getDeclaringClass;

    /// The fact of [Enum#hashCode()].
    public final MethodRef0<Enum<E>, Int> hashCode;

    /// The fact of [Enum#name()].
    public final MethodRef0<Enum<E>, String> name;

    /// The fact of [Enum#ordinal()].
    public final MethodRef0<Enum<E>, Int> ordinal;

    /// The fact of [Enum#toString()].
    public final MethodRef0<Enum<E>, String> toString;

    /// The metamodel of [Enum] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public Enum_(RefToken<E> e) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(e));
        this.super_String_int = UnsafeFacts.superCtor(token, UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), PrimitiveToken.INT, MemberTraits.FINAL.with(Access.PROTECTED));
        this.clone = UnsafeFacts.protected_(token, UnsafeFacts.method(token, "clone", UnsafeFacts.<Object>openClassToken(Object_.Data.SHAPE), MemberTraits.FINAL.throwing(UnsafeFacts.<CloneNotSupportedException>openClassToken(CloneNotSupportedException_.Data.SHAPE)).with(Access.PROTECTED)));
        this.compareTo_E = UnsafeFacts.method(token, "compareTo", PrimitiveToken.INT, UnsafeFacts.param(e, Param.var(0)), MemberTraits.FINAL);
        this.describeConstable = UnsafeFacts.method(token, "describeConstable", UnsafeFacts.<Optional<EnumDesc<E>>>finalClassToken(gen.facts.java.util.Optional_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<EnumDesc<E>>finalClassToken(Enum_EnumDesc_.Data.SHAPE, TokenArg.exact(e)))), MemberTraits.FINAL);
        this.equals_Object = UnsafeFacts.method(token, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(Object_.Data.SHAPE), MemberTraits.FINAL);
        this.finalize = UnsafeFacts.protected_(token, UnsafeFacts.voidMethod(token, "finalize", MemberTraits.FINAL.with(Access.PROTECTED)));
        this.getDeclaringClass = UnsafeFacts.method(token, "getDeclaringClass", UnsafeFacts.<Class<E>>finalClassToken(Class_.Data.SHAPE, TokenArg.exact(e)), MemberTraits.FINAL);
        this.hashCode = UnsafeFacts.method(token, "hashCode", PrimitiveToken.INT, MemberTraits.FINAL);
        this.name = UnsafeFacts.method(token, "name", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL);
        this.ordinal = UnsafeFacts.method(token, "ordinal", PrimitiveToken.INT, MemberTraits.FINAL);
        this.toString = UnsafeFacts.method(token, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.OVERRIDABLE);
    }

    /// The fact of [Enum#valueOf(Class, String)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T extends Enum<T>> StaticMethodRef2<T, Class<T>, String> valueOf_Class_String(RefToken<T> t) {
        return UnsafeFacts.staticMethod(ANY, "valueOf", t, UnsafeFacts.<Class<T>>finalClassToken(Class_.Data.SHAPE, TokenArg.exact(t)), UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.FINAL.withTypeArgs(t));
    }
}
