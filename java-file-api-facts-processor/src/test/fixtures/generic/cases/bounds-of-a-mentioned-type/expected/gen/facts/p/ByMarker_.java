package gen.facts.p;

import gen.facts.p.ByMarker_.Canonical;
import gen.facts.p.ByMarker_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.ByMarker;
import p.Marker;

/// The token-only metamodel of [ByMarker]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [ByMarker]: it is only mentioned in the signatures of [p.Mentions]. For the facts of its members add `ByMarker.class` to `@Facts`.
///
/// @param <T> a type argument of [ByMarker]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ByMarker.class, fingerprint = "1e6e3d994eb52f106fe65c559548ccf8a06ed88ad7e95990a11822aaae82a6a0", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class ByMarker_<T extends Marker> {
    /// The shape of [ByMarker] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [ByMarker] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.ByMarker_"), "1e6e3d994eb52f106fe65c559548ccf8a06ed88ad7e95990a11822aaae82a6a0", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.ByMarker"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Marker"))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("ByMarker"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [ByMarker], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [ByMarker].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.ByMarker open-class sealed=no\ntparams #0 extends p.Marker\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor ByMarker()\n";

        private Canonical() {
        }
    }

    /// The token of [ByMarker] with a wildcard for every type argument.
    public static final OpenClassToken<ByMarker<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [ByMarker] with the type arguments of this metamodel.
    public final OpenClassToken<ByMarker<T>> token;

    /// The metamodel of [ByMarker] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public ByMarker_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
    }
}
