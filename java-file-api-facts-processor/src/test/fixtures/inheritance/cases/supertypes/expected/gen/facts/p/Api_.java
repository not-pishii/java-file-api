package gen.facts.p;

import gen.facts.p.Api_.Canonical;
import gen.facts.p.Api_.Data;
import gen.facts.p.Api_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Api;

/// The full metamodel of [Api]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Api]: it is here as a supertype of [p.Derived], whose inherited members are called through this metamodel.
///
/// A member [Api] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Api.class, fingerprint = "2bcf594db5ecbbb03b3abe54720438faa58d5bb990b3067c3252e6ec59ba80f8", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Api_ {
    /// The shape of [Api] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Api] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Api_"), "2bcf594db5ecbbb03b3abe54720438faa58d5bb990b3067c3252e6ec59ba80f8", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Api"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("abs")), Set.of(Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("iface")), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Api] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Api] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Api"), Signature.of("abs"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("p.Api"), Signature.of("dflt"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.Api"), Signature.of("iface"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }
        }
    }

    /// The canonical form of [Api], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Api].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Api interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract abs() -> void throws -
        member method public overridable dflt() -> void throws -
        member method public static iface() -> void throws -
        sam abs() -> void throws -
        inherit method public abstract p.Api abs() -> void throws - erased () overrides -
        inherit method public default p.Api dflt() -> void throws - erased () overrides -
        inherit method public static p.Api iface() -> void throws - erased () overrides -
        table abstract abs()
        table concrete dflt(); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static iface()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Api].
    public static final InterfaceToken<Api> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Api#abs()].
    public static final VoidMethodRef0<Api> abs = UnsafeFacts.voidMethod(TOKEN, "abs", MemberTraits.ABSTRACT);

    /// The fact of [Api#dflt()].
    public static final VoidMethodRef0<Api> dflt = UnsafeFacts.voidMethod(TOKEN, "dflt", MemberTraits.OVERRIDABLE);

    /// The fact of [Api#iface()].
    public static final VoidStaticMethodRef0 iface = UnsafeFacts.voidStaticMethod(TOKEN, "iface", MemberTraits.FINAL);

    /// The fact of the single abstract method [Api#abs()], which a lambda implements.
    public static final VoidSam0<Api> sam = UnsafeFacts.voidSam(abs);

    private Api_() {
    }
}
