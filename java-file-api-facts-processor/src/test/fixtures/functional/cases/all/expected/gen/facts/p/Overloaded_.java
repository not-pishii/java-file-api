package gen.facts.p;

import gen.facts.p.Overloaded_.Canonical;
import gen.facts.p.Overloaded_.Data;
import gen.facts.p.Overloaded_Heritage.Implement;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Overloaded;

/// The full metamodel of [Overloaded], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Overloaded] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Overloaded] by the proof its metamodel starts, with the stages of `Overloaded_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Overloaded.class, fingerprint = "ccee249ad16828ead925585bc8a9cfccb3af96cd2d3a109bb39f2896c9a78d14", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Overloaded_ {
    /// The shape of [Overloaded] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Overloaded] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Overloaded_"), "ccee249ad16828ead925585bc8a9cfccb3af96cd2d3a109bb39f2896c9a78d14", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Overloaded"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run", Param.fixed(ConstantDescs.CD_long))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run", Param.fixed(ConstantDescs.CD_int)), Signature.of("run", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ConstantDescs.CD_int)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Overloaded_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Overloaded], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Overloaded].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Overloaded interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract run(long) -> void throws -
        member method public overridable <^0> run(^0, int) -> void throws -
        member method public overridable run(int) -> void throws -
        sam run(long) -> void throws -
        inherit method run(int) erased (int) overrides - by interface default p.Overloaded (int) -> void throws -
        inherit method run(java.lang.Object, int) erased (java.lang.Object, int) overrides - by interface default p.Overloaded <^0> (^0, int) -> void throws -
        inherit method run(long) erased (long) overrides - by interface abstract p.Overloaded (long) -> void throws -
        table abstract run(long)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); run(int); run(java.lang.Object, int); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Overloaded].
    public static final InterfaceToken<Overloaded> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Overloaded#run(int)].
    public static final VoidMethodRef1<Overloaded, Int> run_int = UnsafeInvocables.voidMethod(TOKEN, "run", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [Overloaded#run(long)].
    public static final VoidMethodRef1<Overloaded, Long> run_long = UnsafeInvocables.voidMethod(TOKEN, "run", PrimitiveToken.LONG, MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Overloaded#run(long)], which a lambda implements.
    public static final VoidSam1<Overloaded, Long> sam = UnsafeInvocables.voidSam(run_long);

    private Overloaded_() {
    }

    /// The fact of [Overloaded#run(Object, int)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> VoidMethodRef2<Overloaded, T, Int> run_T_int(RefToken<T> t) {
        return UnsafeInvocables.voidMethod(TOKEN, "run", UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), PrimitiveToken.INT, MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// Starts the proof that a class being declared implements [Overloaded]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Overloaded_Heritage().implement(TOKEN, self);
    }
}
