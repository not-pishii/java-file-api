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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Grid;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Grid.class, fingerprint = "a666d528a145ac83746b79ded27db5d133aad3a027aa7f8a514a7b4bb0c4cb6e", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Grid_<A, B> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Grid_"), "a666d528a145ac83746b79ded27db5d133aad3a027aa7f8a514a7b4bb0c4cb6e", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Grid"), List.of(new TypeParam("A", List.of()), new TypeParam("B", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("A"), Types.typeVar("B")), List.of()), new MethodTableTemplate(Set.of(Signature.of("row", Param.var(1, 2))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Grid interface sealed=no\ntparams #0; #1\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract row(#1[][]) -> #0[] throws -\nsam row(#1[][]) -> #0[] throws -\ntable abstract row(#1[][])\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Grid<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    public final InterfaceToken<Grid<A, B>> token;

    public final MethodRef1<Grid<A, B>, A[], B[][]> row_BArrayArray;

    public final Sam1<Grid<A, B>, A[], B[][]> sam;

    public Grid_(RefToken<A> a, RefToken<B> b) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(a), TokenArg.exact(b));
        this.row_BArrayArray = UnsafeFacts.method(token, "row", ArrayToken.of(a), UnsafeFacts.param(ArrayToken.of(ArrayToken.of(b)), Param.var(1, 2)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(row_BArrayArray);
    }
}
