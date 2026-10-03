package gen.facts.p;

import gen.facts.p.Uses_.Canonical;
import gen.facts.p.Uses_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Box;
import p.Pair;
import p.Sorted;
import p.Uses;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Uses.class, fingerprint = "ddb7802c1b808d2809e6375b93a1f48b20a3bd93335bac71d4948920502e9d66", complete = true, format = 4)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class Uses_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Uses_"), "ddb7802c1b808d2809e6375b93a1f48b20a3bd93335bac71d4948920502e9d66", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Uses"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("any"), Signature.of("arrays"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("lists"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("raw"), Signature.of("strings"), Signature.of("take", Param.fixed(ClassDesc.of("p.Sorted"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Uses"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Uses open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable java.util.Map<java.lang.String, ? extends java.lang.Number> wild\nmember method overridable any() -> java.util.List<?> throws -\nmember method overridable arrays() -> p.Pair<int[], java.lang.String[]> throws -\nmember method overridable lists() -> java.util.List<? super java.lang.Integer>[] throws -\nmember method overridable raw() -> p.Box throws -\nmember method overridable strings() -> p.Box<java.lang.String> throws -\nmember method overridable take(p.Sorted<java.lang.Integer>) -> void throws -\ntable abstract -\ntable concrete any(); arrays(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); lists(); notify(); notifyAll(); raw(); strings(); take(p.Sorted); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Uses()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Uses> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<Uses, Map<String, ? extends Number>> wild = UnsafeFacts.mutableField(TOKEN, "wild", UnsafeFacts.<Map<String, ? extends Number>>interfaceToken(gen.facts.java.util.Map_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)), TokenArg.extendsBound(UnsafeFacts.<Number>abstractClassToken(gen.facts.java.lang.Number_.Data.SHAPE))));

    public static final CtorRef0<Uses> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final MethodRef0<Uses, List<?>> any = UnsafeFacts.method(TOKEN, "any", UnsafeFacts.<List<?>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Uses, Pair<int[], String[]>> arrays = UnsafeFacts.method(TOKEN, "arrays", UnsafeFacts.<Pair<int[], String[]>>finalClassToken(Pair_.Data.SHAPE, TokenArg.exact(PrimitiveToken.INT.array()), TokenArg.exact(ArrayToken.of(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)))), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Uses, List<? super Integer>[]> lists = UnsafeFacts.method(TOKEN, "lists", ArrayToken.of(UnsafeFacts.<List<? super Integer>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.superBound(UnsafeFacts.<Integer>finalClassToken(gen.facts.java.lang.Integer_.Data.SHAPE)))), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Uses, Box> raw = UnsafeFacts.method(TOKEN, "raw", UnsafeFacts.<Box>openClassToken(Box_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Uses, Box<String>> strings = UnsafeFacts.method(TOKEN, "strings", UnsafeFacts.<Box<String>>openClassToken(Box_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<Uses, Sorted<Integer>> take_Sorted = UnsafeFacts.voidMethod(TOKEN, "take", UnsafeFacts.<Sorted<Integer>>openClassToken(Sorted_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<Integer>finalClassToken(gen.facts.java.lang.Integer_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    private Uses_() {
    }
}
