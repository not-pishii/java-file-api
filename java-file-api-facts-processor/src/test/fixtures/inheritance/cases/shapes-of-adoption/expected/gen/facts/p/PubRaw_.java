package gen.facts.p;

import gen.facts.p.PubRaw_.Canonical;
import gen.facts.p.PubRaw_.Data;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PubRaw;

/// The full metamodel of [PubRaw], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [PubRaw] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.HG`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubRaw.class, fingerprint = "ad38f4ff63336d3060dd7741f6d3d9495a27ea3e4d51abb8ad065430e0adf3b6", complete = true, format = 6)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class PubRaw_ {
    /// The shape of [PubRaw] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [PubRaw] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubRaw_"), "ad38f4ff63336d3060dd7741f6d3d9495a27ea3e4d51abb8ad065430e0adf3b6", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubRaw"), List.of(), List.of(ClassDesc.of("p.HG"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("all"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("put", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubRaw"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [PubRaw], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [PubRaw].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.PubRaw open-class sealed=no\ntparams -\nsuperclasses p.HG; java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable java.lang.Object value\nmember method overridable all() -> java.util.List throws -\nmember method overridable get() -> java.lang.Object throws -\nmember method overridable put(java.lang.Object) -> void throws -\ntable abstract -\ntable concrete all(); clone(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); notify(); notifyAll(); put(java.lang.Object); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor PubRaw()\n";

        private Canonical() {
        }
    }

    /// The token of [PubRaw].
    public static final OpenClassToken<PubRaw> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubRaw#value], declared in `p.HG`, which is not `public`.
    public static final MutableFieldRef<PubRaw, Object> value = UnsafeFacts.mutableField(TOKEN, "value", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE));

    /// The fact of [PubRaw#PubRaw()].
    public static final CtorRef0<PubRaw> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [PubRaw#all()], declared in `p.HG`, which is not `public`.
    public static final MethodRef0<PubRaw, List> all = UnsafeFacts.method(TOKEN, "all", UnsafeFacts.<List>interfaceToken(gen.facts.java.util.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubRaw#get()], declared in `p.HG`, which is not `public`.
    public static final MethodRef0<PubRaw, Object> get = UnsafeFacts.method(TOKEN, "get", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [PubRaw#put(Object)], declared in `p.HG`, which is not `public`.
    public static final VoidMethodRef1<PubRaw, Object> put_Object = UnsafeFacts.voidMethod(TOKEN, "put", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private PubRaw_() {
    }
}
