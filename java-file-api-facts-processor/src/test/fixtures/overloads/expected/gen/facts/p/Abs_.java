package gen.facts.p;

import gen.facts.p.Abs_.Canonical;
import gen.facts.p.Abs_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Abs;

/// The full metamodel of [Abs], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Abs] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Abs]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Abs.class, fingerprint = "71c88900976101206c5e9556e0c4ceec3fcc7221af260457add92b343e4f2f8c", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Abs_<T> {
    /// The shape of [Abs] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Abs] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Abs_"), "71c88900976101206c5e9556e0c4ceec3fcc7221af260457add92b343e4f2f8c", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Abs"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("m", Param.var(0))), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Abs"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Abs], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Abs].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Abs abstract-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method abstract m(#0) -> java.lang.String throws -\nmember method overridable m(java.lang.String) -> java.lang.String throws -\ntable abstract m(#0)\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(java.lang.String); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Abs()\n";

        private Canonical() {
        }
    }

    /// The token of [Abs] with a wildcard for every type argument.
    public static final AbstractClassToken<Abs<?>> ANY = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Abs] with the type arguments of this metamodel.
    public final AbstractClassToken<Abs<T>> token;

    /// The fact of [Abs#Abs()].
    public final AbstractCtorRef0<Abs<T>> super_;

    /// The fact of [Abs#m(String)].
    public final MethodRef1<Abs<T>, String, String> m_String;

    /// The fact of [Abs#m(Object)].
    public final MethodRef1<Abs<T>, String, T> m_T;

    /// The metamodel of [Abs] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Abs_(RefToken<T> t) {
        this.token = UnsafeFacts.abstractClassToken(Data.SHAPE, TokenArg.exact(t));
        this.super_ = UnsafeFacts.abstractCtor(token, MemberTraits.FINAL);
        this.m_String = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.m_T = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT);
    }
}
