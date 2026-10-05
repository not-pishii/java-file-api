package gen.facts.p;

import gen.facts.p.Other_.Canonical;
import gen.facts.p.Other_.Data;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import p.Greeter;
import p.Other;

/// The full metamodel of [Other], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Other] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Other.class, fingerprint = "376fca0a4c52932a31897d2ce8766957af8dfd23d196901873e25530aa1c5f5a", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Other_ {
    /// The shape of [Other] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Other] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Other_"), "376fca0a4c52932a31897d2ce8766957af8dfd23d196901873e25530aa1c5f5a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Other"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("greeter"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other", Param.fixed(ClassDesc.of("p.Greeter"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Other"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Other], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Other].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Other open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable greeter() -> p.Greeter throws -\nmember method overridable other(p.Greeter) -> p.Other throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); greeter(); hashCode(); notify(); notifyAll(); other(p.Greeter); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Other()\n";

        private Canonical() {
        }
    }

    /// The token of [Other].
    public static final OpenClassToken<Other> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Other#Other()].
    public static final CtorRef0<Other> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Other#greeter()].
    public static final MethodRef0<Other, Greeter> greeter = UnsafeFacts.method(TOKEN, "greeter", UnsafeFacts.<Greeter>openClassToken(Greeter_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Other#other(Greeter)].
    public static final MethodRef1<Other, Other, Greeter> other_Greeter = UnsafeFacts.method(TOKEN, "other", TOKEN, UnsafeFacts.<Greeter>openClassToken(Greeter_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Other_() {
    }
}
