package gen.facts.java.util.function;

import gen.facts.java.util.function.Function_.Canonical;
import gen.facts.java.util.function.Function_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Function.class, fingerprint = "67bb2dd13034b267a3cedcfd62424ec5d0c3dc1177280700f8f33ca47b5edbfc", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Function_<T, R> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.function.Function_"), "67bb2dd13034b267a3cedcfd62424ec5d0c3dc1177280700f8f33ca47b5edbfc", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.function.Function"), List.of(new TypeParam("T", List.of()), new TypeParam("R", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T"), Types.typeVar("R")), List.of()), new MethodTableTemplate(Set.of(Signature.of("apply", Param.var(0))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("identity")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.util.function.Function interface sealed=no\ntparams #0; #1\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract apply(#0) -> #1 throws -\nmember method overridable <^0> andThen(java.util.function.Function<? super #1, ? extends ^0>) -> java.util.function.Function<#0, ^0> throws -\nmember method overridable <^0> compose(java.util.function.Function<? super ^0, ? extends #0>) -> java.util.function.Function<^0, #1> throws -\nmember method static <^0> identity() -> java.util.function.Function<^0, ^0> throws -\nsam apply(#0) -> #1 throws -\ntable abstract apply(#0)\ntable concrete andThen(java.util.function.Function); compose(java.util.function.Function); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static identity()\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Function<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    public final InterfaceToken<Function<T, R>> token;

    public final MethodRef1<Function<T, R>, R, T> apply_T;

    public final Sam1<Function<T, R>, R, T> sam;

    private final RefToken<T> t;

    private final RefToken<R> r;

    public Function_(RefToken<T> t, RefToken<R> r) {
        this.t = t;
        this.r = r;
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(r));
        this.apply_T = UnsafeFacts.method(token, "apply", r, UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(apply_T);
    }

    public <V> MethodRef1<Function<T, R>, Function<T, V>, Function<? super R, ? extends V>> andThen_Function(RefToken<V> v) {
        return UnsafeFacts.method(token, "andThen", UnsafeFacts.<Function<T, V>>interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(v)), UnsafeFacts.<Function<? super R, ? extends V>>interfaceToken(Data.SHAPE, TokenArg.superBound(r), TokenArg.extendsBound(v)), MemberTraits.OVERRIDABLE.withTypeArgs(v));
    }

    public <V> MethodRef1<Function<T, R>, Function<V, R>, Function<? super V, ? extends T>> compose_Function(RefToken<V> v) {
        return UnsafeFacts.method(token, "compose", UnsafeFacts.<Function<V, R>>interfaceToken(Data.SHAPE, TokenArg.exact(v), TokenArg.exact(r)), UnsafeFacts.<Function<? super V, ? extends T>>interfaceToken(Data.SHAPE, TokenArg.superBound(v), TokenArg.extendsBound(t)), MemberTraits.OVERRIDABLE.withTypeArgs(v));
    }

    public static <T> StaticMethodRef0<Function<T, T>> identity(RefToken<T> t) {
        return UnsafeFacts.staticMethod(ANY, "identity", UnsafeFacts.<Function<T, T>>interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(t)), MemberTraits.FINAL.withTypeArgs(t));
    }
}
