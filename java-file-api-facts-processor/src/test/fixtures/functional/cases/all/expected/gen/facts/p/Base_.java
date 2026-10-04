package gen.facts.p;

import gen.facts.p.Base_.Canonical;
import gen.facts.p.Base_.Data;
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
import p.Base;

/// The full metamodel of [Base]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Base]: it is here as a supertype of [p.Sub3], whose inherited members are called through this metamodel.
///
/// A member [Base] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Base.class, fingerprint = "48de98e8b0ae119210f1a5ec3c53c7e5ff991627a1da156b3ac86420ba048bfb", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Base_ {
    /// The shape of [Base] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Base] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Base_"), "48de98e8b0ae119210f1a5ec3c53c7e5ff991627a1da156b3ac86420ba048bfb", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Base"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("get")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Base], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Base].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Base interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract get() -> java.lang.Object throws -\nsam get() -> java.lang.Object throws -\ntable abstract get()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Base].
    public static final InterfaceToken<Base> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Base#get()].
    public static final MethodRef0<Base, Object> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Base#get()], which a lambda implements.
    public static final Sam0<Base, Object> sam = UnsafeFacts.sam(get);

    private Base_() {
    }
}
