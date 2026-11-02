package gen.facts.p;

import gen.facts.p.Picker_.Canonical;
import gen.facts.p.Picker_.Data;
import gen.facts.p.Picker_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Picker;

/// The full metamodel of [Picker], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Picker] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Picker] by the proof its metamodel starts, with the stages of `Picker_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Picker.class, fingerprint = "8ce00f63e08abf2a0de079ec97c17142cf8271529ec8ec551eeb67318f1b96a3", complete = true, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Picker_ {
    /// The shape of [Picker] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Picker] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Picker_"), "8ce00f63e08abf2a0de079ec97c17142cf8271529ec8ec551eeb67318f1b96a3", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Picker"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("pick", Param.fixed(ClassDesc.of("java.lang.CharSequence")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), false, List.of(), List.of(), new Picker_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Picker], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Picker].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Picker interface sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        record -
        members declared-accessible
        member method public abstract <^0 extends java.lang.CharSequence> pick(^0) -> ^0 throws -
        inherit method pick(java.lang.CharSequence) erased (java.lang.CharSequence) overrides - by interface abstract p.Picker <^0 extends java.lang.CharSequence> (^0) -> ^0 throws -
        table abstract pick(java.lang.CharSequence)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Picker].
    public static final InterfaceToken<Picker> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Picker_() {
    }

    /// The fact of [Picker#pick(CharSequence)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T extends CharSequence> MethodRef1<Picker, T, T> pick_T(RefToken<T> t) {
        return UnsafeInvocables.method(TOKEN, "pick", t, UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.CharSequence"))), MemberTraits.ABSTRACT.withTypeArgs(t));
    }

    /// Starts the proof that a class being declared implements [Picker]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Picker_Heritage().implement(TOKEN, self);
    }
}
