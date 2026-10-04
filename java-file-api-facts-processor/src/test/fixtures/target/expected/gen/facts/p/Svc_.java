package gen.facts.p;

import gen.facts.p.Svc_.Canonical;
import gen.facts.p.Svc_.Data;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Dep;
import p.Svc;

/// The full metamodel of [Svc], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Svc] inherits has its fact in the metamodel of the supertype that declares it: [Base_] and [Marker_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Svc.class, fingerprint = "b6ace64b4edfe0d029c336d4fd3bbd7c2040785b5d1990a23439e7e6d649d299", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Svc_ {
    /// The shape of [Svc] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Svc] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Svc_"), "b6ace64b4edfe0d029c336d4fd3bbd7c2040785b5d1990a23439e7e6d649d299", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Svc"), List.of(), List.of(ClassDesc.of("p.Base"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("close"), Signature.of("dep"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("inherited"), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("only", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Svc"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Svc], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Svc].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Svc open-class sealed=no\ntparams -\nsuperclasses p.Base; java.lang.Object\ninterfaces p.Marker\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field static constant int LIMIT = 3\nmember method overridable close() -> void throws -\nmember method overridable dep() -> p.Dep throws -\nmember method overridable m(java.lang.String) -> java.lang.String throws -\nmember method overridable only(java.lang.Object) -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); close(); dep(); equals(java.lang.Object); finalize(); getClass(); hashCode(); inherited(); m(java.lang.String); notify(); notifyAll(); only(java.lang.Object); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Svc()\n";

        private Canonical() {
        }
    }

    /// The token of [Svc].
    public static final OpenClassToken<Svc> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Svc#LIMIT].
    public static final StaticFieldRef<Int> LIMIT = UnsafeFacts.constantField(TOKEN, "LIMIT", PrimitiveToken.INT, 3);

    /// The fact of [Svc#Svc()].
    public static final CtorRef0<Svc> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Svc#close()].
    public static final VoidMethodRef0<Svc> close = UnsafeFacts.voidMethod(TOKEN, "close", MemberTraits.OVERRIDABLE);

    /// The fact of [Svc#dep()].
    public static final MethodRef0<Svc, Dep> dep = UnsafeFacts.method(TOKEN, "dep", UnsafeFacts.<Dep>openClassToken(Dep_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Svc#m(String)].
    public static final MethodRef1<Svc, String, String> m_String = UnsafeFacts.method(TOKEN, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Svc#only(Object)].
    public static final MethodRef1<Svc, String, Object> only_Object = UnsafeFacts.method(TOKEN, "only", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Svc_() {
    }
}
