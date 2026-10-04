package gen.facts.p;

import gen.facts.p.Raw_.Canonical;
import gen.facts.p.Raw_.Data;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Raw;

/// The token-only metamodel of [Raw]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Raw]: it is only mentioned in the signatures of [p.Api].
///
/// The metamodel has no type parameters, and its token is of the raw type: a bound of a type parameter of [Raw] mentions a type the metamodel cannot name, so no full metamodel can be made of it either.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Raw.class, fingerprint = "f1682738b0b07e36465db5583002df979d6bae6de05f512d360ed99c76af5ff9", complete = false, format = 5)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class Raw_ {
    /// The shape of [Raw] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Raw] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Raw_"), "f1682738b0b07e36465db5583002df979d6bae6de05f512d360ed99c76af5ff9", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Raw"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Hidden"))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Raw"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Raw], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Raw].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Raw open-class sealed=no\ntparams #0 extends p.Hidden\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Raw()\n";

        private Canonical() {
        }
    }

    /// The token of the raw type [Raw].
    public static final OpenClassToken<Raw> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Raw_() {
    }
}
