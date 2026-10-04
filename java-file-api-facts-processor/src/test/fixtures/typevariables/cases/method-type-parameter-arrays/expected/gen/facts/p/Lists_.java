package gen.facts.p;

import gen.facts.p.Lists_.Canonical;
import gen.facts.p.Lists_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
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
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Lists;

/// The full metamodel of [Lists], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Lists] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Lists]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Lists.class, fingerprint = "8c21b16ee9b0c6213070b54509da1d2561fe8776786ddf722080991a421ea6e6", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Lists_<T> {
    /// The shape of [Lists] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Lists] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Lists_"), "8c21b16ee9b0c6213070b54509da1d2561fe8776786ddf722080991a421ea6e6", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Lists"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Number;")), Param.var(0, 1)), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Lists"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Lists], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Lists].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Lists open-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable <^0 extends java.lang.Number> all(^0[], #0[]) -> void throws -\ntable abstract -\ntable concrete all(java.lang.Number[], #0[]); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Lists()\n";

        private Canonical() {
        }
    }

    /// The token of [Lists] with a wildcard for every type argument.
    public static final OpenClassToken<Lists<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Lists] with the type arguments of this metamodel.
    public final OpenClassToken<Lists<T>> token;

    /// The fact of [Lists#Lists()].
    public final CtorRef0<Lists<T>> new_;

    private final RefToken<T> t;

    /// The metamodel of [Lists] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Lists_(RefToken<T> t) {
        this.t = t;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
    }

    /// The fact of [Lists#all(Number\[\], Object\[\])], for the type arguments the tokens give.
    ///
    /// @param <U> a type argument of the method
    /// @param u the token of the type argument `U`
    /// @return the fact
    public <U extends Number> VoidMethodRef2<Lists<T>, U[], T[]> all_UArray_TArray(RefToken<U> u) {
        return UnsafeFacts.voidMethod(token, "all", UnsafeFacts.param(ArrayToken.of(u), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Number;"))), UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.OVERRIDABLE.withTypeArgs(u));
    }
}
