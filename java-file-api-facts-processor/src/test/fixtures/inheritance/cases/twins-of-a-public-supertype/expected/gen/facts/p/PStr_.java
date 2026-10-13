package gen.facts.p;

import gen.facts.p.PStr_.Canonical;
import gen.facts.p.PStr_.Data;
import gen.facts.p.PStr_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PStr;

/// The full metamodel of [PStr]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [PStr]: it is here as a supertype of [p.PubAbs] and [p.PubFn2], whose inherited members are called through this metamodel.
///
/// A member [PStr] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [PStr] by the proof its metamodel starts, with the stages of `PStr_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PStr.class, fingerprint = "0dd8e932530c75e0619e7cc640203936eadb4d7474dc654d98a1ebb656b6045c", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PStr_ {
    /// The shape of [PStr] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PStr] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PStr_"), "0dd8e932530c75e0619e7cc640203936eadb4d7474dc654d98a1ebb656b6045c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PStr"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new PStr_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PStr], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PStr].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.PStr interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract get() -> java.lang.String throws -
        sam get() -> java.lang.String throws -
        inherit method get() erased () overrides - by interface abstract p.PStr () -> java.lang.String throws -
        table abstract get()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [PStr].
    public static final InterfaceToken<PStr> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [PStr#get()].
    public static final MethodRef0<PStr, String> get = UnsafeInvocables.method(TOKEN, "get", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [PStr#get()], which a lambda implements.
    public static final Sam0<PStr, String> sam = UnsafeInvocables.sam(get);

    private PStr_() {
    }

    /// Starts the proof that a class being declared implements [PStr]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new PStr_Heritage().implement(TOKEN, self);
    }
}
