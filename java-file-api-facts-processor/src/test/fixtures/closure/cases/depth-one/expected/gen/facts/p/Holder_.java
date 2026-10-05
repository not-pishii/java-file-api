package gen.facts.p;

import gen.facts.p.Holder_.Canonical;
import gen.facts.p.Holder_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Holder;

/// The token-only metamodel of [Holder]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Holder]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Holder.class` to `@Facts`.
///
/// @param <E> a type argument of [Holder]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Holder.class, fingerprint = "f754da7b3c6c6b6706f4711ead8fe1eb7c9a99d75fabbcc2aa067d97c2b626a4", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Holder_<E> {
    /// The shape of [Holder] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Holder] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Holder_"), "f754da7b3c6c6b6706f4711ead8fe1eb7c9a99d75fabbcc2aa067d97c2b626a4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Holder"), List.of(new TypeParam("E", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("E")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Holder"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Holder], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Holder].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Holder open-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Holder()\n";

        private Canonical() {
        }
    }

    /// The token of [Holder] with a wildcard for every type argument.
    public static final OpenClassToken<Holder<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Holder] with the type arguments of this metamodel.
    public final OpenClassToken<Holder<E>> token;

    /// The metamodel of [Holder] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public Holder_(RefToken<E> e) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(e));
    }
}
