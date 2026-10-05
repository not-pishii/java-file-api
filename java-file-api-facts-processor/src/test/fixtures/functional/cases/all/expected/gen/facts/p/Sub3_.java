package gen.facts.p;

import gen.facts.p.Sub3_.Canonical;
import gen.facts.p.Sub3_.Data;
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
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Sub3;

/// The full metamodel of [Sub3], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sub3] inherits has its fact in the metamodel of the supertype that declares it: [Base_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sub3.class, fingerprint = "0bba92d8290fbb89b331220127b3213bcf45959b9b4e4c391e4ddc714ba5591e", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Sub3_ {
    /// The shape of [Sub3] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sub3] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sub3_"), "0bba92d8290fbb89b331220127b3213bcf45959b9b4e4c391e4ddc714ba5591e", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Sub3"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Sub3], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sub3].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Sub3 interface sealed=no\ntparams -\nsuperclasses -\ninterfaces p.Base\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract get() -> java.lang.String throws -\nsam get() -> java.lang.String throws -\ntable abstract get()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Sub3].
    public static final InterfaceToken<Sub3> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Sub3#get()].
    public static final MethodRef0<Sub3, String> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Sub3#get()], which a lambda implements.
    public static final Sam0<Sub3, String> sam = UnsafeFacts.sam(get);

    private Sub3_() {
    }
}
