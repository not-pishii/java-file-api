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
import org.jspecify.annotations.NullMarked;
import p.Iface;

/// The full metamodel of [Iface], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Iface] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Iface.class, fingerprint = "a4e508afc13c3beef9915b2509acd210a54566ddfac1da4c9561817f479fd693", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Iface_ {
    /// The shape of [Iface] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Iface] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Iface_"), "a4e508afc13c3beef9915b2509acd210a54566ddfac1da4c9561817f479fd693", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Iface"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("size"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("empty")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Iface], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Iface].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Iface interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member field public static constant int LIMIT = 10
        member field public static constant java.lang.String NAME = "n"
        member method public abstract run() -> void throws -
        member method public overridable size() -> int throws -
        member method public static empty() -> p.Iface throws -
        sam run() -> void throws -
        table abstract run()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); size(); toString(); wait(); wait(long); wait(long, int)
        table static empty()
        table ctor -
        """;

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
