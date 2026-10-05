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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Iface;

/// The full metamodel of [Iface], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Iface] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Iface.class, fingerprint = "8b1591d074365f88bf02543592d852d7847a246a772182a97d455e085fbb72c2", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Iface_ {
    /// The shape of [Iface] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Iface] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Iface_"), "8b1591d074365f88bf02543592d852d7847a246a772182a97d455e085fbb72c2", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Iface"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("size"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("empty")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Iface], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Iface].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Iface interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember field static constant int LIMIT = 10\nmember field static constant java.lang.String NAME = \"n\"\nmember method abstract run() -> void throws -\nmember method overridable size() -> int throws -\nmember method static empty() -> p.Iface throws -\nsam run() -> void throws -\ntable abstract run()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); size(); toString(); wait(); wait(long); wait(long, int)\ntable static empty()\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Iface].
    public static final InterfaceToken<Iface> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Iface#LIMIT].
    public static final StaticFieldRef<Int> LIMIT = UnsafeFacts.constantField(TOKEN, "LIMIT", PrimitiveToken.INT, 10);

    /// The fact of [Iface#NAME].
    public static final StaticFieldRef<String> NAME = UnsafeFacts.constantField(TOKEN, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "n");

    /// The fact of [Iface#empty()].
    public static final StaticMethodRef0<Iface> empty = UnsafeFacts.staticMethod(TOKEN, "empty", TOKEN, MemberTraits.FINAL);

    /// The fact of [Iface#run()].
    public static final VoidMethodRef0<Iface> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    /// The fact of [Iface#size()].
    public static final MethodRef0<Iface, Int> size = UnsafeFacts.method(TOKEN, "size", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of the single abstract method [Iface#run()], which a lambda implements.
    public static final VoidSam0<Iface> sam = UnsafeFacts.voidSam(run);

    private Iface_() {
    }
}
