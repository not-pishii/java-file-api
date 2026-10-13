package gen.facts.p;

import gen.facts.p.Selfish_.Canonical;
import gen.facts.p.Selfish_.Data;
import gen.facts.p.Selfish_Heritage.Implement;
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
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidSam3;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Self;
import p.Selfish;

/// The full metamodel of [Selfish], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Selfish] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Selfish] by the proof its metamodel starts, with the stages of `Selfish_Heritage`.
///
/// @param <Self__> a type argument of [Selfish]
/// @param <Self___> a type argument of [Selfish]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Selfish.class, fingerprint = "184b7bec8e1082aae513f1fc944b280ee5a6b43fcfe2c02580fac474d289a2c5", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Selfish_<Self__, Self___> {
    /// The shape of [Selfish] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Selfish] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Selfish_"), "184b7bec8e1082aae513f1fc944b280ee5a6b43fcfe2c02580fac474d289a2c5", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Selfish"), List.of(new TypeParam("Self", List.of()), new TypeParam("Self_", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("Self"), Types.typeVar("Self_")), List.of()), new MethodTableTemplate(Set.of(Signature.of("take", Param.fixed(ClassDesc.of("p.Self")), Param.var(0), Param.var(1))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Selfish_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Selfish], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Selfish].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Selfish interface sealed=no nesting=top
        tparams #0; #1
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract take(p.Self, #0, #1) -> void throws -
        sam take(p.Self, #0, #1) -> void throws -
        inherit method take(p.Self, #0, #1) erased (p.Self, java.lang.Object, java.lang.Object) overrides - by interface abstract p.Selfish (p.Self, #0, #1) -> void throws -
        table abstract take(p.Self, #0, #1)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Selfish] with a wildcard for every type argument.
    public static final InterfaceToken<Selfish<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Selfish] with the type arguments of this metamodel.
    public final InterfaceToken<Selfish<Self__, Self___>> token;

    /// The fact of [Selfish#take(Self, Object, Object)].
    public final VoidMethodRef3<Selfish<Self__, Self___>, Self, Self__, Self___> take_Self_Self_Self_;

    /// The fact of the single abstract method [Selfish#take(Self, Object, Object)], which a lambda implements.
    public final VoidSam3<Selfish<Self__, Self___>, Self, Self__, Self___> sam;

    /// The metamodel of [Selfish] with the type arguments the tokens give.
    ///
    /// @param self__ the token of the type argument `Self__`
    /// @param self___ the token of the type argument `Self___`
    public Selfish_(RefToken<Self__> self__, RefToken<Self___> self___) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(self__), TokenArg.exact(self___));
        this.take_Self_Self_Self_ = UnsafeInvocables.voidMethod(token, "take", UnsafeFacts.<Self>openClassToken(Self_.Data.SHAPE), UnsafeFacts.param(self__, Param.var(0)), UnsafeFacts.param(self___, Param.var(1)), MemberTraits.ABSTRACT);
        this.sam = UnsafeInvocables.voidSam(take_Self_Self_Self_);
    }

    /// Starts the proof that a class being declared implements [Selfish]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self____> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self____> Implement<Self__, Self___, Self____> implement(RefToken<Self____> self) {
        return new Selfish_Heritage().implement(this.token, self);
    }
}
