package gen.facts.p;

import gen.facts.p.Comp_.Canonical;
import gen.facts.p.Comp_.Data;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Comp;

/// The full metamodel of [Comp], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Comp] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Comp.class, fingerprint = "32faefce2ee15c976fae68fbc42cdfd7cf0125affba0a50a1b7b92e6a1555f1d", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Comp_ {
    /// The shape of [Comp] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Comp] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Comp_"), "32faefce2ee15c976fae68fbc42cdfd7cf0125affba0a50a1b7b92e6a1555f1d", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Comp"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Comp")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Comp], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Comp].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Comp interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract compareTo(p.Comp) -> int throws -\nmember method abstract hashCode() -> int throws -\nmember method abstract toString() -> java.lang.String throws -\nsam compareTo(p.Comp) -> int throws -\ntable abstract compareTo(p.Comp)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Comp].
    public static final InterfaceToken<Comp> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Comp#compareTo(Comp)].
    public static final MethodRef1<Comp, Int, Comp> compareTo_Comp = UnsafeFacts.method(TOKEN, "compareTo", PrimitiveToken.INT, TOKEN, MemberTraits.ABSTRACT);

    /// The fact of [Comp#hashCode()].
    public static final MethodRef0<Comp, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [Comp#toString()].
    public static final MethodRef0<Comp, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Comp#compareTo(Comp)], which a lambda implements.
    public static final Sam1<Comp, Int, Comp> sam = UnsafeFacts.sam(compareTo_Comp);

    private Comp_() {
    }
}
