package gen.facts.java.lang.annotation;

import gen.facts.java.lang.annotation.Annotation_.Canonical;
import gen.facts.java.lang.annotation.Annotation_.Data;
import gen.facts.java.lang.annotation.Annotation_Heritage.Implement;
import java.lang.annotation.Annotation;
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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Annotation]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Annotation]: it is here as a supertype of [p.Author], [p.Notes] and [p.Tag], whose inherited members are called through this metamodel.
///
/// A member [Annotation] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Annotation] by the proof its metamodel starts, with the stages of `Annotation_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Annotation.class, fingerprint = "367d7d239a73378cacdaf86df43011fdf573714a4ff5e2bc38c87b744b0d41b3", complete = true, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Annotation_ {
    /// The shape of [Annotation] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Annotation] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.annotation.Annotation_"), "367d7d239a73378cacdaf86df43011fdf573714a4ff5e2bc38c87b744b0d41b3", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.annotation.Annotation"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("annotationType")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), false, List.of(), List.of(), new Annotation_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Annotation], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Annotation].
        static final String TEXT = """
        javafile-facts-canonical 13
        type java.lang.annotation.Annotation interface sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        record -
        members declared-accessible
        member method public abstract annotationType() -> java.lang.Class<? extends java.lang.annotation.Annotation> throws -
        member method public abstract equals(java.lang.Object) -> boolean throws -
        member method public abstract hashCode() -> int throws -
        member method public abstract toString() -> java.lang.String throws -
        sam annotationType() -> java.lang.Class<? extends java.lang.annotation.Annotation> throws -
        inherit method annotationType() erased () overrides - by interface abstract java.lang.annotation.Annotation () -> java.lang.Class<? extends java.lang.annotation.Annotation> throws -
        table abstract annotationType()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Annotation].
    public static final InterfaceToken<Annotation> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Annotation#annotationType()].
    public static final MethodRef0<Annotation, Class<? extends Annotation>> annotationType = UnsafeInvocables.method(TOKEN, "annotationType", UnsafeFacts.<Class<? extends Annotation>>finalClassToken(gen.facts.java.lang.Class_.Data.SHAPE, TokenArg.extendsBound(TOKEN)), MemberTraits.ABSTRACT);

    /// The fact of [Annotation#equals(Object)].
    public static final MethodRef1<Annotation, Bool, Object> equals_Object = UnsafeInvocables.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of [Annotation#hashCode()].
    public static final MethodRef0<Annotation, Int> hashCode = UnsafeInvocables.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [Annotation#toString()].
    public static final MethodRef0<Annotation, String> toString = UnsafeInvocables.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Annotation#annotationType()], which a lambda implements.
    public static final Sam0<Annotation, Class<? extends Annotation>> sam = UnsafeInvocables.sam(annotationType);

    private Annotation_() {
    }

    /// Starts the proof that a class being declared implements [Annotation]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Annotation_Heritage().implement(TOKEN, self);
    }
}
