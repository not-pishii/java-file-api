package gen.facts.p;

import gen.facts.p.Ov_.Canonical;
import gen.facts.p.Ov_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Ov;

/// The full metamodel of [Ov], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Ov] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ov.class, fingerprint = "510c2c24ade23cebb10c7373727b75d7ab186ccc114c6bd31a9de850ccefeb3d", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Ov_ {
    /// The shape of [Ov] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Ov] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ov_"), "510c2c24ade23cebb10c7373727b75d7ab186ccc114c6bd31a9de850ccefeb3d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ov"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("c", Param.fixed(ClassDesc.of("java.lang.Comparable"))), Signature.of("c", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("solo", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Integer"))), Signature.of("s", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of(Signature.of("Ov"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Ov], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Ov].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Ov open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable <^0 extends java.lang.Comparable<^0>> c(^0) -> java.lang.String throws -\nmember method overridable <^0> m(^0) -> java.lang.String throws -\nmember method overridable <^0> solo(^0) -> java.lang.String throws -\nmember method overridable c(java.lang.String) -> java.lang.String throws -\nmember method overridable m(java.lang.String) -> java.lang.String throws -\nmember method static <^0> s(^0) -> java.lang.String throws -\nmember method static s(java.lang.Integer) -> java.lang.String throws -\ntable abstract -\ntable concrete c(java.lang.Comparable); c(java.lang.String); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(java.lang.Object); m(java.lang.String); notify(); notifyAll(); solo(java.lang.Object); toString(); wait(); wait(long); wait(long, int)\ntable static s(java.lang.Integer); s(java.lang.Object)\ntable ctor Ov()\n";

        private Canonical() {
        }
    }

    /// The token of [Ov].
    public static final OpenClassToken<Ov> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Ov#Ov()].
    public static final CtorRef0<Ov> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Ov#c(String)].
    public static final MethodRef1<Ov, String, String> c_String = UnsafeFacts.method(TOKEN, "c", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#m(String)].
    public static final MethodRef1<Ov, String, String> m_String = UnsafeFacts.method(TOKEN, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Ov#s(Integer)].
    public static final StaticMethodRef1<String, Integer> s_Integer = UnsafeFacts.staticMethod(TOKEN, "s", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Integer>finalClassToken(gen.facts.java.lang.Integer_.Data.SHAPE), MemberTraits.FINAL);

    private Ov_() {
    }

    /// The fact of [Ov#c(Comparable)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T extends Comparable<T>> MethodRef1<Ov, String, T> c_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "c", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Comparable"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Ov#m(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef1<Ov, String, T> m_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }

    /// The fact of [Ov#s(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> StaticMethodRef1<String, T> s_T(RefToken<T> t) {
        return UnsafeFacts.staticMethod(TOKEN, "s", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(t));
    }

    /// The fact of [Ov#solo(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> MethodRef1<Ov, String, T> solo_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "solo", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }
}
