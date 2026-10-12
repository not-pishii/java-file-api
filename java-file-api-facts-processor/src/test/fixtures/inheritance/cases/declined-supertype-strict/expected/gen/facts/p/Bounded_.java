package gen.facts.p;

import gen.facts.p.Bounded_.Canonical;
import gen.facts.p.Bounded_.Data;
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
import p.Bounded;

/// The token-only metamodel of [Bounded]: its shape and its token, no facts of its members.
///
/// [Bounded] is a supertype of [p.Over], but has no full metamodel: the bounds of the type parameters of p.Bounded mention types that are not public: p.Secret. The members inherited from it have no facts.
///
/// `@Facts` does not ask for [Bounded]: it is only mentioned in the signatures of [p.Over].
///
/// The metamodel has no type parameters, and its token is of the raw type: a bound of a type parameter of [Bounded] mentions a type the metamodel cannot name.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Bounded.class, fingerprint = "1a7bf79108dc8a0b6aefd7911e05c97cb310453e60755812921a160414ffd277", complete = false, format = 14)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class Bounded_ {
    /// The shape of [Bounded] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Bounded] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Bounded_"), "1a7bf79108dc8a0b6aefd7911e05c97cb310453e60755812921a160414ffd277", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Bounded"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Secret"))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("lost"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Bounded"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Bounded], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Bounded].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Bounded open-class sealed=no nesting=top
        tparams #0 extends p.Secret
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members none
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); lost(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Bounded()
        """;

        private Canonical() {
        }
    }

    /// The token of the raw type [Bounded].
    public static final OpenClassToken<Bounded> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Bounded_() {
    }
}
