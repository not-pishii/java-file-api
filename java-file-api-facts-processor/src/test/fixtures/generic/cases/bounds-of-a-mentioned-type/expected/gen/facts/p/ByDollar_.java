package gen.facts.p;

import gen.facts.p.ByDollar_.Canonical;
import gen.facts.p.ByDollar_.Data;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.ByDollar;

/// The token-only metamodel of [ByDollar]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [ByDollar]: it is only mentioned in the signatures of [p.Mentions].
///
/// The metamodel has no type parameters, and its token is of the raw type: a bound of a type parameter of [ByDollar] mentions a type the metamodel cannot name, so no full metamodel can be made of it either.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ByDollar.class, fingerprint = "c0746821f70700c21f441bfed08b2d4b48f6102d32a1edd236e8523bd0eba189", complete = false, format = 6)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class ByDollar_ {
    /// The shape of [ByDollar] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [ByDollar] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.ByDollar_"), "c0746821f70700c21f441bfed08b2d4b48f6102d32a1edd236e8523bd0eba189", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.ByDollar"), List.of(new TypeParam("T", List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.List"), List.of(Types.exact(Types.of(ClassDesc.of("p.Dol$lar")))))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("ByDollar"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [ByDollar], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [ByDollar].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.ByDollar open-class sealed=no\ntparams #0 extends java.util.List<p.Dol$lar>\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor ByDollar()\n";

        private Canonical() {
        }
    }

    /// The token of the raw type [ByDollar].
    public static final OpenClassToken<ByDollar> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private ByDollar_() {
    }
}
