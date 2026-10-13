package gen.facts.p;

import gen.facts.p.WithDefault_.Canonical;
import gen.facts.p.WithDefault_.Data;
import gen.facts.p.WithDefault_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.WithDefault;

/// The full metamodel of [WithDefault], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [WithDefault] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [WithDefault] by the proof its metamodel starts, with the stages of `WithDefault_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = WithDefault.class, fingerprint = "4a9915ad2dfde8d455ccf40a5af9596b00178fa2f9e96054b73cb57728fb156a", complete = true, format = 14)
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
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.WithDefault_"), "4a9915ad2dfde8d455ccf40a5af9596b00178fa2f9e96054b73cb57728fb156a", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.WithDefault"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("f", Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("g", Param.fixed(ConstantDescs.CD_int)), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("id")), Set.of()), false, new WithDefault_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [WithDefault], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [WithDefault].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.WithDefault interface sealed=no nesting=top
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
        inherit method f(int) erased (int) overrides - by interface abstract p.WithDefault (int) -> int throws -
        inherit method g(int) erased (int) overrides - by interface default p.WithDefault (int) -> int throws -
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
    public static final MethodRef1<WithDefault, Int, Int> f_int = UnsafeInvocables.method(TOKEN, "f", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [WithDefault#g(int)].
    public static final MethodRef1<WithDefault, Int, Int> g_int = UnsafeInvocables.method(TOKEN, "g", PrimitiveToken.INT, PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [WithDefault#id()].
    public static final StaticMethodRef0<WithDefault> id = UnsafeInvocables.staticMethod(TOKEN, "id", TOKEN, MemberTraits.FINAL);

    /// The fact of the single abstract method [WithDefault#f(int)], which a lambda implements.
    public static final Sam1<WithDefault, Int, Int> sam = UnsafeInvocables.sam(f_int);

    private WithDefault_() {
    }

    /// Starts the proof that a class being declared implements [WithDefault]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new WithDefault_Heritage().implement(TOKEN, self);
    }
}
