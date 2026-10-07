package gen.facts.p;

import gen.facts.p.WithDefault_.Canonical;
import gen.facts.p.WithDefault_.Data;
import gen.facts.p.WithDefault_.Data.Inherited;
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
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.WithDefault;

/// The full metamodel of [WithDefault], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [WithDefault] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = WithDefault.class, fingerprint = "f47a6982f9071913aacb72308858114d15074157e0168927e38a1ec853faced4", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class WithDefault_ {
    /// The shape of [WithDefault] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [WithDefault] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.WithDefault_"), "f47a6982f9071913aacb72308858114d15074157e0168927e38a1ec853faced4", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.WithDefault"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("f", Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("g", Param.fixed(ConstantDescs.CD_int)), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("id")), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [WithDefault] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [WithDefault] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.WithDefault"), Signature.of("f", Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.INT), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of(ConstantDescs.CD_int)), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("p.WithDefault"), Signature.of("g", Param.fixed(ConstantDescs.CD_int)), List.of(), List.of(PrimitiveTypeRef.INT), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of(ConstantDescs.CD_int)), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.STATIC, ClassDesc.of("p.WithDefault"), Signature.of("id"), List.of(), List.of(), Arity.FIXED, new Of(Types.of(ClassDesc.of("p.WithDefault"))), List.of(), Set.of(List.of()), List.of());
            }
        }
    }

    /// The canonical form of [WithDefault], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [WithDefault].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.WithDefault interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract f(int) -> int throws -
        member method public overridable g(int) -> int throws -
        member method public static id() -> p.WithDefault throws -
        sam f(int) -> int throws -
        inherit method public abstract p.WithDefault f(int) -> int throws - erased (int) overrides -
        inherit method public default p.WithDefault g(int) -> int throws - erased (int) overrides -
        inherit method public static p.WithDefault id() -> p.WithDefault throws - erased () overrides -
        table abstract f(int)
        table concrete equals(java.lang.Object); g(int); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static id()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [WithDefault].
    public static final InterfaceToken<WithDefault> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [WithDefault#f(int)].
    public static final MethodRef1<WithDefault, Int, Int> f_int = UnsafeFacts.method(TOKEN, "f", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [WithDefault#g(int)].
    public static final MethodRef1<WithDefault, Int, Int> g_int = UnsafeFacts.method(TOKEN, "g", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [WithDefault#id()].
    public static final StaticMethodRef0<WithDefault> id = UnsafeFacts.staticMethod(TOKEN, "id", TOKEN, MemberTraits.FINAL);

    /// The fact of the single abstract method [WithDefault#f(int)], which a lambda implements.
    public static final Sam1<WithDefault, Int, Int> sam = UnsafeFacts.sam(f_int);

    private WithDefault_() {
    }
}
