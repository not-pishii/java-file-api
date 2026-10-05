package gen.facts.p;

import gen.facts.p.Grid_.Canonical;
import gen.facts.p.Grid_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
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
import p.Grid;

/// The full metamodel of [Grid], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Grid] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <A> a type argument of [Grid]
/// @param <B> a type argument of [Grid]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Grid.class, fingerprint = "c7df1554ad04547216248cf9edea63637862290be7cffc6132ad79a7d4e1557b", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Grid_<A, B> {
    /// The shape of [Grid] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Grid] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Grid_"), "c7df1554ad04547216248cf9edea63637862290be7cffc6132ad79a7d4e1557b", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Grid"), List.of(new TypeParam("A", List.of()), new TypeParam("B", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("A"), Types.typeVar("B")), List.of()), new MethodTableTemplate(Set.of(Signature.of("row", Param.var(1, 2))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Grid], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Grid].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Grid interface sealed=no
        tparams #0; #1
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-public
        member method abstract row(#1[][]) -> #0[] throws -
        sam row(#1[][]) -> #0[] throws -
        table abstract row(#1[][])
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Grid] with a wildcard for every type argument.
    public static final InterfaceToken<Grid<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Grid] with the type arguments of this metamodel.
    public final InterfaceToken<Grid<A, B>> token;

    /// The fact of [Grid#row(Object\[\]\[\])].
    public final MethodRef1<Grid<A, B>, A[], B[][]> row_BArrayArray;

    /// The fact of the single abstract method [Grid#row(Object\[\]\[\])], which a lambda implements.
    public final Sam1<Grid<A, B>, A[], B[][]> sam;

    /// The metamodel of [Grid] with the type arguments the tokens give.
    ///
    /// @param a the token of the type argument `A`
    /// @param b the token of the type argument `B`
    public Grid_(RefToken<A> a, RefToken<B> b) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(a), TokenArg.exact(b));
        this.row_BArrayArray = UnsafeFacts.method(token, "row", ArrayToken.of(a), UnsafeFacts.param(ArrayToken.of(ArrayToken.of(b)), Param.var(1, 2)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(row_BArrayArray);
    }
}
