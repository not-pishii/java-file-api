package gen.facts.p;

import gen.facts.p.Diamond_.Canonical;
import gen.facts.p.Diamond_.Data;
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
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Diamond;

/// The full metamodel of [Diamond], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Diamond] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.Root`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Diamond.class, fingerprint = "1f60d317abc9676450d4f6f97dbe62879a90370478ec02a0b2de6e18a062efec", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Diamond_ {
    /// The shape of [Diamond] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Diamond] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Diamond_"), "1f60d317abc9676450d4f6f97dbe62879a90370478ec02a0b2de6e18a062efec", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Diamond"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("root"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Diamond"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Diamond], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Diamond].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Diamond open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant int ROOT = 1\nmember method overridable root() -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); root(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Diamond()\n";

        private Canonical() {
        }
    }

    /// The token of [Diamond].
    public static final OpenClassToken<Diamond> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Diamond#ROOT], declared in `p.Root`, which is not `public`.
    public static final StaticFieldRef<Int> ROOT = UnsafeFacts.constantField(TOKEN, "ROOT", PrimitiveToken.INT, 1);

    /// The fact of [Diamond#Diamond()].
    public static final CtorRef0<Diamond> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Diamond#root()], declared in `p.Root`, which is not `public`.
    public static final MethodRef0<Diamond, String> root = UnsafeFacts.method(TOKEN, "root", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Diamond_() {
    }
}
