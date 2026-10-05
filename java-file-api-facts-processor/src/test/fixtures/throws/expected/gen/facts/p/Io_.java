package gen.facts.p;

import gen.facts.p.Io_.Canonical;
import gen.facts.p.Io_.Data;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Failure;
import p.Io;

/// The full metamodel of [Io], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Io] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method hidden()`, which mentions types that are not public: p.Secret
/// - `constructor Io(java.lang.String)`, which mentions types that are not public: p.Secret
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Io.class, fingerprint = "1e1fa2d02a8f92fc747213e0a7c7bbdecd9c3ccdd0c315b7cd53ffb78e7fa276", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Io_ {
    /// The shape of [Io] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Io] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Io_"), "1e1fa2d02a8f92fc747213e0a7c7bbdecd9c3ccdd0c315b7cd53ffb78e7fa276", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Io"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("custom"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("generic"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("locked"), Signature.of("multi"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("read"), Signature.of("toString"), Signature.of("unchecked"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("util")), Set.of(Signature.of("Io"), Signature.of("Io", Param.fixed(ConstantDescs.CD_int)), Signature.of("Io", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Io], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Io].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Io open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws java.io.IOException\nmember ctor(int) throws -\nmember method final locked() -> void throws p.Failure\nmember method overridable <^0 extends java.lang.Throwable> generic() -> void throws ^0\nmember method overridable custom() -> void throws p.Failure\nmember method overridable multi() -> void throws java.io.IOException, java.lang.IllegalStateException, java.lang.InterruptedException\nmember method overridable read() -> void throws java.io.IOException\nmember method overridable unchecked() -> int throws java.lang.IllegalArgumentException\nmember method static util() -> void throws java.lang.Exception\ntable abstract -\ntable concrete clone(); custom(); equals(java.lang.Object); finalize(); generic(); getClass(); hashCode(); hidden(); locked(); multi(); notify(); notifyAll(); read(); toString(); unchecked(); wait(); wait(long); wait(long, int)\ntable static util()\ntable ctor Io(); Io(int); Io(java.lang.String)\n";

        private Canonical() {
        }
    }

    /// The token of [Io].
    public static final OpenClassToken<Io> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Io#Io()].
    public static final CtorRef0<Io> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    /// The fact of [Io#Io(int)].
    public static final CtorRef1<Io, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// The fact of [Io#custom()].
    public static final VoidMethodRef0<Io> custom = UnsafeFacts.voidMethod(TOKEN, "custom", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<Failure>openClassToken(Failure_.Data.SHAPE)));

    /// The fact of [Io#locked()].
    public static final VoidMethodRef0<Io> locked = UnsafeFacts.voidMethod(TOKEN, "locked", MemberTraits.FINAL.throwing(UnsafeFacts.<Failure>openClassToken(Failure_.Data.SHAPE)));

    /// The fact of [Io#multi()].
    public static final VoidMethodRef0<Io> multi = UnsafeFacts.voidMethod(TOKEN, "multi", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE), UnsafeFacts.<InterruptedException>openClassToken(gen.facts.java.lang.InterruptedException_.Data.SHAPE), UnsafeFacts.<IllegalStateException>openClassToken(gen.facts.java.lang.IllegalStateException_.Data.SHAPE)));

    /// The fact of [Io#read()].
    public static final VoidMethodRef0<Io> read = UnsafeFacts.voidMethod(TOKEN, "read", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    /// The fact of [Io#unchecked()].
    public static final MethodRef0<Io, Int> unchecked = UnsafeFacts.method(TOKEN, "unchecked", PrimitiveToken.INT, MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IllegalArgumentException>openClassToken(gen.facts.java.lang.IllegalArgumentException_.Data.SHAPE)));

    /// The fact of [Io#util()].
    public static final VoidStaticMethodRef0 util = UnsafeFacts.voidStaticMethod(TOKEN, "util", MemberTraits.FINAL.throwing(UnsafeFacts.<Exception>openClassToken(gen.facts.java.lang.Exception_.Data.SHAPE)));

    private Io_() {
    }

    /// The fact of [Io#generic()], for the type arguments the tokens give.
    ///
    /// @param <X> a type argument of the method
    /// @param x the token of the type argument `X`
    /// @return the fact
    public static <X extends Throwable> VoidMethodRef0<Io> generic(RefToken<X> x) {
        return UnsafeFacts.voidMethod(TOKEN, "generic", MemberTraits.OVERRIDABLE.throwing(x).withTypeArgs(x));
    }
}
