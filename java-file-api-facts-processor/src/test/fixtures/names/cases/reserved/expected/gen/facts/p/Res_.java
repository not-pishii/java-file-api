package gen.facts.p;

import gen.facts.p.Res_.Canonical;
import gen.facts.p.Res_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Res;

/// The full metamodel of [Res], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Res] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Res.class, fingerprint = "442ce7030fff0be05b825e5a50c6ee5d2bb63d3268892a8e4d1609463c414075", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Res_ {
    /// The shape of [Res] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Res] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Res_"), "442ce7030fff0be05b825e5a50c6ee5d2bb63d3268892a8e4d1609463c414075", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Res"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("count"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("sam", Param.fixed(ConstantDescs.CD_int)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Res"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Res], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Res].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Res open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member field instance mutable int ANY
        member field instance mutable int Canonical
        member field instance mutable int Data
        member field instance mutable int TOKEN
        member field instance mutable int count
        member field instance mutable int sam
        member field instance mutable int switch_
        member field instance mutable int token
        member method overridable count() -> int throws -
        member method overridable sam(int) -> void throws -
        table abstract -
        table concrete clone(); count(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); sam(int); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Res()
        """;

        private Canonical() {
        }
    }

    /// The token of [Res].
    public static final OpenClassToken<Res> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Res#ANY].
    public static final MutableFieldRef<Res, Int> ANY_ = UnsafeFacts.mutableField(TOKEN, "ANY", PrimitiveToken.INT);

    /// The fact of [Res#Canonical].
    public static final MutableFieldRef<Res, Int> Canonical_ = UnsafeFacts.mutableField(TOKEN, "Canonical", PrimitiveToken.INT);

    /// The fact of [Res#Data].
    public static final MutableFieldRef<Res, Int> Data_ = UnsafeFacts.mutableField(TOKEN, "Data", PrimitiveToken.INT);

    /// The fact of [Res#TOKEN].
    public static final MutableFieldRef<Res, Int> TOKEN_ = UnsafeFacts.mutableField(TOKEN, "TOKEN", PrimitiveToken.INT);

    /// The fact of [Res#count].
    public static final MutableFieldRef<Res, Int> count = UnsafeFacts.mutableField(TOKEN, "count", PrimitiveToken.INT);

    /// The fact of [Res#sam].
    public static final MutableFieldRef<Res, Int> sam_ = UnsafeFacts.mutableField(TOKEN, "sam", PrimitiveToken.INT);

    /// The fact of [Res#switch_].
    public static final MutableFieldRef<Res, Int> switch__ = UnsafeFacts.mutableField(TOKEN, "switch_", PrimitiveToken.INT);

    /// The fact of [Res#token].
    public static final MutableFieldRef<Res, Int> token_ = UnsafeFacts.mutableField(TOKEN, "token", PrimitiveToken.INT);

    /// The fact of [Res#Res()].
    public static final CtorRef0<Res> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Res#count()].
    public static final MethodRef0<Res, Int> count_ = UnsafeFacts.method(TOKEN, "count", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [Res#sam(int)].
    public static final VoidMethodRef1<Res, Int> sam_int = UnsafeFacts.voidMethod(TOKEN, "sam", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private Res_() {
    }
}
