package gen.facts.p;

import gen.facts.p.ByHidden_.Canonical;
import gen.facts.p.ByHidden_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.ByHidden;

/// The token-only metamodel of [ByHidden]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [ByHidden]: it is only mentioned in the signatures of [p.Mentions].
///
/// The metamodel has no type parameters, and its token is of the raw type: a bound of a type parameter of [ByHidden] mentions a type the metamodel cannot name, so no full metamodel can be made of it either.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ByHidden.class, fingerprint = "1cf8e1615f1869fbc4b5f8aa7a33d43966aa70b8f2837142e9fb706497f1e9c6", complete = false, format = 9)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class ByHidden_ {
    /// The shape of [ByHidden] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [ByHidden] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.ByHidden_"), "1cf8e1615f1869fbc4b5f8aa7a33d43966aa70b8f2837142e9fb706497f1e9c6", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.ByHidden"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Hidden"))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("ByHidden"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [ByHidden], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [ByHidden].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.ByHidden open-class sealed=no
        tparams #0 extends p.Hidden
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members none
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor ByHidden()
        """;

        private Canonical() {
        }
    }

    /// The token of the raw type [ByHidden].
    public static final OpenClassToken<ByHidden> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private ByHidden_() {
    }
}
