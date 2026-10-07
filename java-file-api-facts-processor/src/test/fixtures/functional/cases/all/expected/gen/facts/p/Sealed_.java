package gen.facts.p;

import gen.facts.p.Sealed_.Canonical;
import gen.facts.p.Sealed_.Data;
import gen.facts.p.Sealed_.Data.Inherited;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Sealed;

/// The full metamodel of [Sealed], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sealed] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sealed.class, fingerprint = "62078dbbf79a8c80553953d51f15540e04a825664b8de4cd9f7fa2ad7c153862", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Sealed_ {
    /// The shape of [Sealed] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sealed] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sealed_"), "62078dbbf79a8c80553953d51f15540e04a825664b8de4cd9f7fa2ad7c153862", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Sealed"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), true, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Sealed] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Sealed] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Sealed"), Signature.of("run"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(), Set.of(List.of()), List.of());
            }
        }
    }

    /// The canonical form of [Sealed], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sealed].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Sealed interface sealed=yes
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract run() -> void throws -
        inherit method public abstract p.Sealed run() -> void throws - erased () overrides -
        table abstract run()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Sealed].
    public static final InterfaceToken<Sealed> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Sealed#run()].
    public static final VoidMethodRef0<Sealed> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    private Sealed_() {
    }
}
