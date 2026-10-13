package gen.facts.p;

import gen.facts.p.PubFn3_.Canonical;
import gen.facts.p.PubFn3_.Data;
import gen.facts.p.PubFn3_Heritage.Implement;
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
import p.PubFn3;

/// The full metamodel of [PubFn3], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubFn3] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [PObj_].
///
/// `p.HStr`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
///
/// A generated class extends or implements [PubFn3] by the proof its metamodel starts, with the stages of `PubFn3_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubFn3.class, fingerprint = "546c4b7cf7e6d0d8360ff11a9032539872f26a80312438d6d35631113d85e6dd", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubFn3_ {
    /// The shape of [PubFn3] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubFn3] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubFn3_"), "546c4b7cf7e6d0d8360ff11a9032539872f26a80312438d6d35631113d85e6dd", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PubFn3"), List.of(), List.of(), List.of(ClassDesc.of("p.HStr"), ClassDesc.of("p.PObj")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new PubFn3_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [PubFn3], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubFn3].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.PubFn3 interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces p.HStr; p.PObj
        supertypes -
        enum -
        members declared-accessible
        member method public abstract get() -> java.lang.String throws -
        sam get() -> java.lang.String throws -
        inherit method get() erased () overrides - by interface abstract p.HStr () -> java.lang.String throws - | interface abstract p.PObj () -> java.lang.Object throws -
        table abstract get()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [PubFn3].
    public static final InterfaceToken<PubFn3> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [PubFn3#get()], declared in `p.HStr`, which is not `public`.
    public static final MethodRef0<PubFn3, String> get = UnsafeInvocables.method(TOKEN, "get", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [PubFn3#get()], which a lambda implements.
    public static final Sam0<PubFn3, String> sam = UnsafeInvocables.sam(get);

    private PubFn3_() {
    }

    /// Starts the proof that a class being declared implements [PubFn3]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new PubFn3_Heritage().implement(TOKEN, self);
    }
}
