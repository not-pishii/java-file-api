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
import p.Failure;
import p.Io;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Io.class, fingerprint = "8da4c6314706e275fac35c7d6182ce73b6daf863a34a0be02a597accb82397ce", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Io_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Io_"), "8da4c6314706e275fac35c7d6182ce73b6daf863a34a0be02a597accb82397ce", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Io"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("custom"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("generic"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("locked"), Signature.of("multi"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("read"), Signature.of("toString"), Signature.of("unchecked"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("util")), Set.of(Signature.of("Io"), Signature.of("Io", Param.fixed(ConstantDescs.CD_int)), Signature.of("Io", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Io open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws java.io.IOException\nmember ctor(int) throws -\nmember method final locked() -> void throws p.Failure\nmember method overridable <^0 extends java.lang.Throwable> generic() -> void throws ^0\nmember method overridable custom() -> void throws p.Failure\nmember method overridable multi() -> void throws java.io.IOException, java.lang.IllegalStateException, java.lang.InterruptedException\nmember method overridable read() -> void throws java.io.IOException\nmember method overridable unchecked() -> int throws java.lang.IllegalArgumentException\nmember method static util() -> void throws java.lang.Exception\ntable abstract -\ntable concrete clone(); custom(); equals(java.lang.Object); finalize(); generic(); getClass(); hashCode(); hidden(); locked(); multi(); notify(); notifyAll(); read(); toString(); unchecked(); wait(); wait(long); wait(long, int)\ntable static util()\ntable ctor Io(); Io(int); Io(java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Io> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Io> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    public static final CtorRef1<Io, Int> new_int = UnsafeFacts.ctor(TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    public static final VoidMethodRef0<Io> custom = UnsafeFacts.voidMethod(TOKEN, "custom", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<Failure>openClassToken(Failure_.Data.SHAPE)));

    public static final VoidMethodRef0<Io> locked = UnsafeFacts.voidMethod(TOKEN, "locked", MemberTraits.FINAL.throwing(UnsafeFacts.<Failure>openClassToken(Failure_.Data.SHAPE)));

    public static final VoidMethodRef0<Io> multi = UnsafeFacts.voidMethod(TOKEN, "multi", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE), UnsafeFacts.<InterruptedException>openClassToken(gen.facts.java.lang.InterruptedException_.Data.SHAPE), UnsafeFacts.<IllegalStateException>openClassToken(gen.facts.java.lang.IllegalStateException_.Data.SHAPE)));

    public static final VoidMethodRef0<Io> read = UnsafeFacts.voidMethod(TOKEN, "read", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    public static final MethodRef0<Io, Int> unchecked = UnsafeFacts.method(TOKEN, "unchecked", PrimitiveToken.INT, MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IllegalArgumentException>openClassToken(gen.facts.java.lang.IllegalArgumentException_.Data.SHAPE)));

    public static final VoidStaticMethodRef0 util = UnsafeFacts.voidStaticMethod(TOKEN, "util", MemberTraits.FINAL.throwing(UnsafeFacts.<Exception>openClassToken(gen.facts.java.lang.Exception_.Data.SHAPE)));

    private Io_() {
    }

    public static <X extends Throwable> VoidMethodRef0<Io> generic(RefToken<X> x) {
        return UnsafeFacts.voidMethod(TOKEN, "generic", MemberTraits.OVERRIDABLE.throwing(x).withTypeArgs(x));
    }
}
