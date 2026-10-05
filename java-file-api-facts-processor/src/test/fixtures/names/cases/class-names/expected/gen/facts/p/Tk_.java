package gen.facts.p;

import gen.facts.p.Tk_.Canonical;
import gen.facts.p.Tk_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Other;
import p.Tk;

/// The full metamodel of [Tk], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Tk] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Tk.class, fingerprint = "f5fbe7bd49ddedbc7cd7c6fc68760f88d1139e433be4c99d9897f8c3057f236c", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Tk_ {
    /// The shape of [Tk] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Tk] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Tk_"), "f5fbe7bd49ddedbc7cd7c6fc68760f88d1139e433be4c99d9897f8c3057f236c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Tk"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Tk"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Tk], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Tk].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Tk open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member field instance mutable int Float
        member field instance mutable int MemberTraits
        member field instance mutable int Other_
        member field instance mutable int UnsafeFacts
        member field instance mutable int fine
        member method overridable other() -> p.Other throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); other(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Tk()
        """;

        private Canonical() {
        }
    }

    /// The token of [Tk].
    public static final OpenClassToken<Tk> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Tk#Float].
    public static final MutableFieldRef<Tk, Int> Float = UnsafeFacts.mutableField(TOKEN, "Float", PrimitiveToken.INT);

    /// The fact of [Tk#MemberTraits].
    public static final MutableFieldRef<Tk, Int> MemberTraits_ = UnsafeFacts.mutableField(TOKEN, "MemberTraits", PrimitiveToken.INT);

    /// The fact of [Tk#Other_].
    public static final MutableFieldRef<Tk, Int> Other__ = UnsafeFacts.mutableField(TOKEN, "Other_", PrimitiveToken.INT);

    /// The fact of [Tk#UnsafeFacts].
    public static final MutableFieldRef<Tk, Int> UnsafeFacts_ = UnsafeFacts.mutableField(TOKEN, "UnsafeFacts", PrimitiveToken.INT);

    /// The fact of [Tk#fine].
    public static final MutableFieldRef<Tk, Int> fine = UnsafeFacts.mutableField(TOKEN, "fine", PrimitiveToken.INT);

    /// The fact of [Tk#Tk()].
    public static final CtorRef0<Tk> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Tk#other()].
    public static final MethodRef0<Tk, Other> other = UnsafeFacts.method(TOKEN, "other", UnsafeFacts.<Other>openClassToken(Other_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Tk_() {
    }
}
