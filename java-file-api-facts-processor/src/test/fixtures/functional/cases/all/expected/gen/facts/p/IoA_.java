package gen.facts.p;

import gen.facts.p.IoA_.Canonical;
import gen.facts.p.IoA_.Data;
import gen.facts.p.IoA_.Data.Inherited;
import java.io.IOException;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.IoA;

/// The full metamodel of [IoA]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [IoA]: it is here as a supertype of [p.Disjoint], [p.Nested], [p.OneWithout] and 1 more requested type, whose inherited members are called through this metamodel.
///
/// A member [IoA] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = IoA.class, fingerprint = "b281256d8dfc0f724d4f5630844d8d7f24c053d1a30767a891fa2de47f5e0529", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class IoA_ {
    /// The shape of [IoA] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [IoA] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.IoA_"), "b281256d8dfc0f724d4f5630844d8d7f24c053d1a30767a891fa2de47f5e0529", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.IoA"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [IoA] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [IoA] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.IoA"), Signature.of("m"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.io.IOException"))), Set.of(List.of()), List.of());
            }
        }
    }

    /// The canonical form of [IoA], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [IoA].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.IoA interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract m() -> void throws java.io.IOException
        sam m() -> void throws java.io.IOException
        inherit method public abstract p.IoA m() -> void throws java.io.IOException erased () overrides -
        table abstract m()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [IoA].
    public static final InterfaceToken<IoA> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [IoA#m()].
    public static final VoidMethodRef0<IoA> m = UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    /// The fact of the single abstract method [IoA#m()], which a lambda implements.
    public static final VoidSam0<IoA> sam = UnsafeFacts.voidSam(m);

    private IoA_() {
    }
}
