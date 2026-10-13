package gen.facts.java.lang;

import gen.facts.java.lang.Iterable_.Canonical;
import gen.facts.java.lang.Iterable_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
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
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Iterable]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Iterable]: it is only mentioned in the signatures of [p.Shapes]. For the facts of its members add `Iterable.class` to `@Facts`.
///
/// @param <T> a type argument of [Iterable]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Iterable.class, fingerprint = "22b43fc5da02931bc9847c5cd41053914267980278110ded2851933dd05e92b9", complete = false, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Iterable_<T> {
    /// The shape of [Iterable] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Iterable] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Iterable_"), "22b43fc5da02931bc9847c5cd41053914267980278110ded2851933dd05e92b9", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.Iterable"), List.of(new TypeParam("T", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("iterator")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("forEach", Param.fixed(ClassDesc.of("java.util.function.Consumer"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("spliterator"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Iterable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Iterable].
        static final String TEXT = """
        javafile-facts-canonical 8
        type java.lang.Iterable interface sealed=no nesting=top
        tparams #0
        superclasses -
        interfaces -
        supertypes -
        enum -
        members none
        sam iterator() -> java.util.Iterator<#0> throws -
        table abstract iterator()
        table concrete equals(java.lang.Object); forEach(java.util.function.Consumer); getClass(); hashCode(); notify(); notifyAll(); spliterator(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Iterable] with a wildcard for every type argument.
    public static final InterfaceToken<Iterable<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Iterable] with the type arguments of this metamodel.
    public final InterfaceToken<Iterable<T>> token;

    /// The metamodel of [Iterable] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Iterable_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
    }
}
