package gen.facts.p;

import gen.facts.p.List_.Canonical;
import gen.facts.p.List_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
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
import p.List;

/// The full metamodel of [List], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [List] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = List.class, fingerprint = "fc0a4f8999b9036fd7ca7e62f28294a39cd6843d264535231d0fa5ab451c5592", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class List_ {
    /// The shape of [List] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [List] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.List_"), "fc0a4f8999b9036fd7ca7e62f28294a39cd6843d264535231d0fa5ab451c5592", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.List"), java.util.List.of(), java.util.List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("canonical", Param.fixed(ClassDesc.of("p.Data"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("load"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("List"))), java.util.List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [List], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [List].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.List open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int Supertypes\nmember field instance mutable int java\nmember field instance mutable int p\nmember method overridable canonical(p.Data) -> p.Canonical throws -\nmember method overridable load() -> p.Data throws -\ntable abstract -\ntable concrete canonical(p.Data); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); load(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor List()\n";

        private Canonical() {
        }
    }

    /// The token of [List].
    public static final OpenClassToken<List> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [List#Supertypes].
    public static final MutableFieldRef<List, Int> Supertypes_ = UnsafeFacts.mutableField(TOKEN, "Supertypes", PrimitiveToken.INT);

    /// The fact of [List#java].
    public static final MutableFieldRef<List, Int> java_ = UnsafeFacts.mutableField(TOKEN, "java", PrimitiveToken.INT);

    /// The fact of [List#p].
    public static final MutableFieldRef<List, Int> p_ = UnsafeFacts.mutableField(TOKEN, "p", PrimitiveToken.INT);

    /// The fact of [List#List()].
    public static final CtorRef0<List> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [List#canonical(p.Data)].
    public static final MethodRef1<List, p.Canonical, p.Data> canonical_Data = UnsafeFacts.method(TOKEN, "canonical", UnsafeFacts.<p.Canonical>openClassToken(Canonical_.Data.SHAPE), UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [List#load()].
    public static final MethodRef0<List, p.Data> load = UnsafeFacts.method(TOKEN, "load", UnsafeFacts.<p.Data>openClassToken(Data_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private List_() {
    }
}
