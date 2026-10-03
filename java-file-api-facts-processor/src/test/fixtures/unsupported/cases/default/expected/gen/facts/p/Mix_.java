package gen.facts.p;

import gen.facts.p.Mix_.Canonical;
import gen.facts.p.Mix_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Mix;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mix.class, fingerprint = "e3faff3ef14d4e4ead54fb841eab16e23e438c53e770edb940893c6b7dcfbf05", complete = true, format = 4)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
public final class Mix_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mix_"), "e3faff3ef14d4e4ead54fb841eab16e23e438c53e770edb940893c6b7dcfbf05", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mix"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("array"), Signature.of("clone"), Signature.of("dollar"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("id", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("marked", Param.fixed(ClassDesc.of("p.Marker"))), Signature.of("marker"), Signature.of("markers"), Signature.of("names"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("raw"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("wild", Param.fixed(ClassDesc.of("java.util.Map")))), Set.of(), Set.of(Signature.of("Mix", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ConstantDescs.CD_int)), Signature.of("Mix", Param.fixed(ClassDesc.of("java.util.Set"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Mix open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor <^0>(^0, int) throws -\nmember ctor(java.util.Set<java.lang.String>) throws -\nmember field instance mutable java.util.function.Function<java.lang.String, java.lang.String> field\nmember method overridable <^0 extends p.Marker> marked(^0) -> void throws -\nmember method overridable <^0> id(^0) -> ^0 throws -\nmember method overridable array() -> java.util.List<java.lang.String>[] throws -\nmember method overridable dollar() -> p.Dol$lar throws -\nmember method overridable marker() -> p.Marker throws -\nmember method overridable markers() -> java.util.List<p.Marker> throws -\nmember method overridable names() -> java.util.List<java.lang.String> throws -\nmember method overridable plain() -> java.lang.String throws -\nmember method overridable raw() -> java.util.List throws -\nmember method overridable wild(java.util.Map<?, ? extends java.lang.Number>) -> void throws -\ntable abstract -\ntable concrete array(); clone(); dollar(); equals(java.lang.Object); finalize(); getClass(); hashCode(); id(java.lang.Object); marked(p.Marker); marker(); markers(); names(); notify(); notifyAll(); plain(); raw(); toString(); wait(); wait(long); wait(long, int); wild(java.util.Map)\ntable static -\ntable ctor Mix(java.lang.Object, int); Mix(java.util.Set)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Mix> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final MutableFieldRef<Mix, Function<String, String>> field = UnsafeFacts.mutableField(TOKEN, "field", UnsafeFacts.<Function<String, String>>interfaceToken(gen.facts.java.util.function.Function_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)), TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))));

    public static final CtorRef1<Mix, Set<String>> new_Set = UnsafeFacts.ctor(TOKEN, UnsafeFacts.<Set<String>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.FINAL);

    public static final MethodRef0<Mix, List<String>[]> array = UnsafeFacts.method(TOKEN, "array", ArrayToken.of(UnsafeFacts.<List<String>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)))), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Mix, List<String>> names = UnsafeFacts.method(TOKEN, "names", UnsafeFacts.<List<String>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Mix, String> plain = UnsafeFacts.method(TOKEN, "plain", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final MethodRef0<Mix, List> raw = UnsafeFacts.method(TOKEN, "raw", UnsafeFacts.<List>interfaceToken(gen.facts.java.util.List_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef1<Mix, Map<?, ? extends Number>> wild_Map = UnsafeFacts.voidMethod(TOKEN, "wild", UnsafeFacts.<Map<?, ? extends Number>>interfaceToken(gen.facts.java.util.Map_.Data.SHAPE, TokenArg.unbounded(), TokenArg.extendsBound(UnsafeFacts.<Number>abstractClassToken(gen.facts.java.lang.Number_.Data.SHAPE))), MemberTraits.OVERRIDABLE);

    private Mix_() {
    }

    public static <T> MethodRef1<Mix, T, T> id_T(RefToken<T> t) {
        return UnsafeFacts.method(TOKEN, "id", t, UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(t));
    }
}
