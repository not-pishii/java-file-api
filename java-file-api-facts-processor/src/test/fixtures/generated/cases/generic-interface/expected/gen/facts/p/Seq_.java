package gen.facts.p;

import gen.facts.p.Seq_.Canonical;
import gen.facts.p.Seq_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Seq;

/// The full metamodel of [Seq], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Seq] inherits has its fact in the metamodel of the supertype that declares it: [Coll_].
///
/// @param <E> a type argument of [Seq]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Seq.class, fingerprint = "e9c7b2923250241dbe31d907e2cea85746f0eb75cd8f4e7c1859bd40e719f2bc", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Seq_<E> {
    /// The shape of [Seq] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Seq] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Seq_"), "e9c7b2923250241dbe31d907e2cea85746f0eb75cd8f4e7c1859bd40e719f2bc", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Seq"), List.of(new TypeParam("E", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("p.Coll"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(Signature.of("add", Param.var(0))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("of")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Seq], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Seq].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Seq interface sealed=no\ntparams #0\nsuperclasses -\nsupertypes p.Coll<#0>\nenum -\nmembers declared-public\nmember method static <^0> of() -> p.Seq<^0> throws -\nsam add(#0) -> boolean throws -\ntable abstract add(#0)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static of()\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Seq] with a wildcard for every type argument.
    public static final InterfaceToken<Seq<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Seq] with the type arguments of this metamodel.
    public final InterfaceToken<Seq<E>> token;

    /// The fact of the single abstract method [p.Coll#add(Object)], which a lambda implements.
    public final Sam1<Seq<E>, Bool, E> sam;

    /// The metamodel of [Seq] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public Seq_(RefToken<E> e) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(e));
        this.sam = UnsafeFacts.sam(UnsafeFacts.method(token, "add", PrimitiveToken.BOOLEAN, UnsafeFacts.param(e, Param.var(0)), MemberTraits.ABSTRACT));
    }

    /// The fact of [Seq#of()], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> StaticMethodRef0<Seq<T>> of(RefToken<T> t) {
        return UnsafeFacts.staticMethod(ANY, "of", UnsafeFacts.<Seq<T>>interfaceToken(Data.SHAPE, TokenArg.exact(t)), MemberTraits.FINAL.withTypeArgs(t));
    }
}
