package gen.facts.p;

import gen.facts.p.Pair_.Canonical;
import gen.facts.p.Pair_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
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
import p.Pair;

/// The full metamodel of [Pair], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Pair] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <A> a type argument of [Pair]
/// @param <B> a type argument of [Pair]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Pair.class, fingerprint = "d72968332e4cd71dd3c957d0f7a29cda88888f49c6210bbd31844ed959e6b3e1", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Pair_<A, B> {
    /// The shape of [Pair] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Pair] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Pair_"), "d72968332e4cd71dd3c957d0f7a29cda88888f49c6210bbd31844ed959e6b3e1", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Pair"), List.of(new TypeParam("A", List.of()), new TypeParam("B", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("A"), Types.typeVar("B")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("entry"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("swap"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Pair", Param.var(0), Param.var(1)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Pair], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Pair].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Pair final-class sealed=no
        tparams #0; #1
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor(#0, #1) throws -
        member field instance final #0 first
        member field instance final #1 second
        member method final entry() -> java.util.Map$Entry<#0, #1> throws -
        member method final swap() -> p.Pair<#1, #0> throws -
        table abstract -
        table concrete clone(); entry(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); swap(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Pair(#0, #1)
        """;

        private Canonical() {
        }
    }

    /// The token of [Pair] with a wildcard for every type argument.
    public static final FinalClassToken<Pair<?, ?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Pair] with the type arguments of this metamodel.
    public final FinalClassToken<Pair<A, B>> token;

    /// The fact of [Pair#first].
    public final FieldRef<Pair<A, B>, A> first;

    /// The fact of [Pair#second].
    public final FieldRef<Pair<A, B>, B> second;

    /// The fact of [Pair#Pair(Object, Object)].
    public final CtorRef2<Pair<A, B>, A, B> new_A_B;

    /// The fact of [Pair#entry()].
    public final MethodRef0<Pair<A, B>, Entry<A, B>> entry;

    /// The fact of [Pair#swap()].
    public final MethodRef0<Pair<A, B>, Pair<B, A>> swap;

    /// The metamodel of [Pair] with the type arguments the tokens give.
    ///
    /// @param a the token of the type argument `A`
    /// @param b the token of the type argument `B`
    public Pair_(RefToken<A> a, RefToken<B> b) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(a), TokenArg.exact(b));
        this.first = UnsafeFacts.field(token, "first", a);
        this.second = UnsafeFacts.field(token, "second", b);
        this.new_A_B = UnsafeFacts.ctor(token, UnsafeFacts.param(a, Param.var(0)), UnsafeFacts.param(b, Param.var(1)), MemberTraits.FINAL);
        this.entry = UnsafeFacts.method(token, "entry", UnsafeFacts.<Entry<A, B>>interfaceToken(gen.facts.java.util.Map_Entry_.Data.SHAPE, TokenArg.exact(a), TokenArg.exact(b)), MemberTraits.FINAL);
        this.swap = UnsafeFacts.method(token, "swap", UnsafeFacts.<Pair<B, A>>finalClassToken(Data.SHAPE, TokenArg.exact(b), TokenArg.exact(a)), MemberTraits.FINAL);
    }
}
