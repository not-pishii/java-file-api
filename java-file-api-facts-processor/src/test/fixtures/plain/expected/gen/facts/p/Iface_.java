package gen.facts.p;

import gen.facts.p.Iface_.Canonical;
import gen.facts.p.Iface_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Iface;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Iface.class, fingerprint = "084c045c4bb16a889e73aa29d9bf8ca5ab0dcd0dba5ec1d4abe663a90ef1ffee", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Iface_ {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Iface_"), "084c045c4bb16a889e73aa29d9bf8ca5ab0dcd0dba5ec1d4abe663a90ef1ffee", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Iface"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("size"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("empty")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Iface interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember field static constant int LIMIT = 10\nmember field static constant java.lang.String NAME = \"n\"\nmember method abstract run() -> void throws -\nmember method overridable size() -> int throws -\nmember method static empty() -> p.Iface throws -\nsam run() -> void throws -\ntable abstract run()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); size(); toString(); wait(); wait(long); wait(long, int)\ntable static empty()\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Iface> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    public static final StaticFieldRef<Int> LIMIT = UnsafeFacts.constantField(TOKEN, "LIMIT", PrimitiveToken.INT, 10);

    public static final StaticFieldRef<String> NAME = UnsafeFacts.constantField(TOKEN, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "n");

    public static final StaticMethodRef0<Iface> empty = UnsafeFacts.staticMethod(TOKEN, "empty", TOKEN, MemberTraits.FINAL);

    public static final VoidMethodRef0<Iface> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    public static final MethodRef0<Iface, Int> size = UnsafeFacts.method(TOKEN, "size", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    public static final VoidSam0<Iface> sam = UnsafeFacts.voidSam(run);

    private Iface_() {
    }
}
