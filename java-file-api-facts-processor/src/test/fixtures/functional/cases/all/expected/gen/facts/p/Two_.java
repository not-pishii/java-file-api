package gen.facts.p;

import gen.facts.p.Two_.Canonical;
import gen.facts.p.Two_.Data;
import gen.facts.p.Two_Heritage.Implement;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Two;

/// The full metamodel of [Two], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Two] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Two] by the proof its metamodel starts, with the stages of `Two_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Two.class, fingerprint = "4cf32b579b3a27526931b4972b85943d357d66d3a9e51b2c23175c1ba75cc2b9", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Two_ {
    /// The shape of [Two] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Two] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Two_"), "4cf32b579b3a27526931b4972b85943d357d66d3a9e51b2c23175c1ba75cc2b9", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Two"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("a"), Signature.of("b")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Two_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Two], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Two].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Two interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract a() -> void throws -
        member method public abstract b() -> void throws -
        inherit method a() erased () overrides - by interface abstract p.Two () -> void throws -
        inherit method b() erased () overrides - by interface abstract p.Two () -> void throws -
        table abstract a(); b()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Two].
    public static final InterfaceToken<Two> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Two#a()].
    public static final VoidMethodRef0<Two> a = UnsafeInvocables.voidMethod(TOKEN, "a", MemberTraits.ABSTRACT);

    /// The fact of [Two#b()].
    public static final VoidMethodRef0<Two> b = UnsafeInvocables.voidMethod(TOKEN, "b", MemberTraits.ABSTRACT);

    private Two_() {
    }

    /// Starts the proof that a class being declared implements [Two]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Two_Heritage().implement(TOKEN, self);
    }
}
