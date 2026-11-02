package gen.facts.p;

import gen.facts.p.Noted_.Canonical;
import gen.facts.p.Noted_.Data;
import gen.facts.p.Noted_Heritage.Implement;
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
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Noted;

/// The full metamodel of [Noted], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Noted] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Noted] by the proof its metamodel starts, with the stages of `Noted_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Noted.class, fingerprint = "a5f9a1a1a73504c4ad9f619e87e5b76a4c89ade150902568203c435a0d90449c", complete = true, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Noted_ {
    /// The shape of [Noted] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Noted] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Noted_"), "a5f9a1a1a73504c4ad9f619e87e5b76a4c89ade150902568203c435a0d90449c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Noted"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("note", Param.fixed(ClassDesc.of("java.lang.Deprecated")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), false, List.of(), List.of(), new Noted_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Noted], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Noted].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Noted interface sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        record -
        members declared-accessible
        member method public abstract note(java.lang.Deprecated) -> void throws -
        sam note(java.lang.Deprecated) -> void throws -
        inherit method note(java.lang.Deprecated) erased (java.lang.Deprecated) overrides - by interface abstract p.Noted (java.lang.Deprecated) -> void throws -
        table abstract note(java.lang.Deprecated)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Noted].
    public static final InterfaceToken<Noted> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Noted#note(Deprecated)].
    public static final VoidMethodRef1<Noted, Deprecated> note_Deprecated = UnsafeInvocables.voidMethod(TOKEN, "note", UnsafeFacts.<Deprecated>interfaceToken(gen.facts.java.lang.Deprecated_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Noted#note(Deprecated)], which a lambda implements.
    public static final VoidSam1<Noted, Deprecated> sam = UnsafeInvocables.voidSam(note_Deprecated);

    private Noted_() {
    }

    /// Starts the proof that a class being declared implements [Noted]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Noted_Heritage().implement(TOKEN, self);
    }
}
