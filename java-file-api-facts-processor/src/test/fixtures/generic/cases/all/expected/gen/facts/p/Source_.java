package gen.facts.p;

import gen.facts.p.Source_.Canonical;
import gen.facts.p.Source_.Data;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Source;

/// The full metamodel of [Source], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Source] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Source]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Source.class, fingerprint = "4c15e05ad30f3d3e04255db5066334facef322bcbf27c7cd088c9740b1e38308", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Source_<T> {
    /// The shape of [Source] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Source] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Source_"), "4c15e05ad30f3d3e04255db5066334facef322bcbf27c7cd088c9740b1e38308", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Source"), List.of(new TypeParam("T", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("next")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Source], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Source].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Source interface sealed=no\ntparams #0\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract next() -> #0 throws java.io.IOException\nsam next() -> #0 throws java.io.IOException\ntable abstract next()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Source] with a wildcard for every type argument.
    public static final InterfaceToken<Source<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Source] with the type arguments of this metamodel.
    public final InterfaceToken<Source<T>> token;

    /// The fact of [Source#next()].
    public final MethodRef0<Source<T>, T> next;

    /// The fact of the single abstract method [Source#next()], which a lambda implements.
    public final Sam0<Source<T>, T> sam;

    /// The metamodel of [Source] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Source_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.next = UnsafeFacts.method(token, "next", t, MemberTraits.ABSTRACT.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));
        this.sam = UnsafeFacts.sam(next);
    }
}
