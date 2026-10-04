package gen.facts.p;

import gen.facts.p.Spread_.Canonical;
import gen.facts.p.Spread_.Data;
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
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Spread;

/// The full metamodel of [Spread], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Spread] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Spread]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Spread.class, fingerprint = "435eb6f5cf9c17bae99bef0237cad404f555a5a889523ffabce037b02e587316", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Spread_<T> {
    /// The shape of [Spread] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Spread] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Spread_"), "435eb6f5cf9c17bae99bef0237cad404f555a5a889523ffabce037b02e587316", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Spread"), List.of(new TypeParam("T", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("accept", Param.var(0, 1))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Spread], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Spread].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Spread interface sealed=no\ntparams #0\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract accept(#0[]) -> void throws -\nsam accept(#0[]) -> void throws -\ntable abstract accept(#0[])\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Spread] with a wildcard for every type argument.
    public static final InterfaceToken<Spread<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Spread] with the type arguments of this metamodel.
    public final InterfaceToken<Spread<T>> token;

    /// The fact of [Spread#accept(Object\[\])].
    public final VoidMethodRef1<Spread<T>, T[]> accept_TArray;

    /// The fact of the single abstract method [Spread#accept(Object\[\])], which a lambda implements.
    public final VoidSam1<Spread<T>, T[]> sam;

    /// The metamodel of [Spread] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Spread_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.accept_TArray = UnsafeFacts.voidMethod(token, "accept", UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.voidSam(accept_TArray);
    }
}
