package gen.facts.java.util;

import gen.facts.java.util.Optional_.Canonical;
import gen.facts.java.util.Optional_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Optional.class, fingerprint = "4c23fadbaf34ec678101a21b738508137487e91a60cefdd4ace1275d28dbfea9", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Optional_<T> {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Optional_"), "4c23fadbaf34ec678101a21b738508137487e91a60cefdd4ace1275d28dbfea9", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.util.Optional"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("filter", Param.fixed(ClassDesc.of("java.util.function.Predicate"))), Signature.of("finalize"), Signature.of("flatMap", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("ifPresent", Param.fixed(ClassDesc.of("java.util.function.Consumer"))), Signature.of("ifPresentOrElse", Param.fixed(ClassDesc.of("java.util.function.Consumer")), Param.fixed(ClassDesc.of("java.lang.Runnable"))), Signature.of("isEmpty"), Signature.of("isPresent"), Signature.of("map", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("or", Param.fixed(ClassDesc.of("java.util.function.Supplier"))), Signature.of("orElse", Param.var(0)), Signature.of("orElseGet", Param.fixed(ClassDesc.of("java.util.function.Supplier"))), Signature.of("orElseThrow"), Signature.of("orElseThrow", Param.fixed(ClassDesc.of("java.util.function.Supplier"))), Signature.of("stream"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("empty"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("ofNullable", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.util.Optional final-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); filter(java.util.function.Predicate); finalize(); flatMap(java.util.function.Function); get(); getClass(); hashCode(); ifPresent(java.util.function.Consumer); ifPresentOrElse(java.util.function.Consumer, java.lang.Runnable); isEmpty(); isPresent(); map(java.util.function.Function); notify(); notifyAll(); or(java.util.function.Supplier); orElse(#0); orElseGet(java.util.function.Supplier); orElseThrow(); orElseThrow(java.util.function.Supplier); stream(); toString(); wait(); wait(long); wait(long, int)\ntable static empty(); of(java.lang.Object); ofNullable(java.lang.Object)\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Optional<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    public final FinalClassToken<Optional<T>> token;

    public Optional_(RefToken<T> t) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(t));
    }
}
