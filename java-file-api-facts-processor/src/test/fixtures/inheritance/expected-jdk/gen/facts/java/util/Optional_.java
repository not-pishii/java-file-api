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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Optional]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Optional]: it is only mentioned in the signatures of [Enum] and [java.lang.constant.Constable]. For the facts of its members add `Optional.class` to `@Facts`.
///
/// @param <T> a type argument of [Optional]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Optional.class, fingerprint = "73ed6f60333955d3076846828ae56c0dfee450109a9327eff9510f626935799c", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Optional_<T> {
    /// The shape of [Optional] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Optional] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Optional_"), "73ed6f60333955d3076846828ae56c0dfee450109a9327eff9510f626935799c", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.util.Optional"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("filter", Param.fixed(ClassDesc.of("java.util.function.Predicate"))), Signature.of("finalize"), Signature.of("flatMap", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("ifPresent", Param.fixed(ClassDesc.of("java.util.function.Consumer"))), Signature.of("ifPresentOrElse", Param.fixed(ClassDesc.of("java.util.function.Consumer")), Param.fixed(ClassDesc.of("java.lang.Runnable"))), Signature.of("isEmpty"), Signature.of("isPresent"), Signature.of("map", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("or", Param.fixed(ClassDesc.of("java.util.function.Supplier"))), Signature.of("orElse", Param.var(0)), Signature.of("orElseGet", Param.fixed(ClassDesc.of("java.util.function.Supplier"))), Signature.of("orElseThrow"), Signature.of("orElseThrow", Param.fixed(ClassDesc.of("java.util.function.Supplier"))), Signature.of("stream"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("empty"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("ofNullable", Param.fixed(ClassDesc.of("java.lang.Object")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Optional], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Optional].
        static final String TEXT = """
        javafile-facts-canonical 5
        type java.util.Optional final-class sealed=no
        tparams #0
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members none
        table abstract -
        table concrete clone(); equals(java.lang.Object); filter(java.util.function.Predicate); finalize(); flatMap(java.util.function.Function); get(); getClass(); hashCode(); ifPresent(java.util.function.Consumer); ifPresentOrElse(java.util.function.Consumer, java.lang.Runnable); isEmpty(); isPresent(); map(java.util.function.Function); notify(); notifyAll(); or(java.util.function.Supplier); orElse(#0); orElseGet(java.util.function.Supplier); orElseThrow(); orElseThrow(java.util.function.Supplier); stream(); toString(); wait(); wait(long); wait(long, int)
        table static empty(); of(java.lang.Object); ofNullable(java.lang.Object)
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Optional] with a wildcard for every type argument.
    public static final FinalClassToken<Optional<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Optional] with the type arguments of this metamodel.
    public final FinalClassToken<Optional<T>> token;

    /// The metamodel of [Optional] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Optional_(RefToken<T> t) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(t));
    }
}
