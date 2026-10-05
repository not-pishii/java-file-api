package gen.facts.java.util;

import gen.facts.java.util.Map_.Canonical;
import gen.facts.java.util.Map_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
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

/// The token-only metamodel of [Map]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Map]: it is only mentioned in the signatures of [p.Mix]. For the facts of its members add `Map.class` to `@Facts`.
///
/// @param <K> a type argument of [Map]
/// @param <V> a type argument of [Map]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Map.class, fingerprint = "fa626c70d8290ddb2635be7c9f99db77b875d31f75899504b115274f5d35ca13", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Map_<K, V> {
    /// The shape of [Map] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Map] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Map_"), "fa626c70d8290ddb2635be7c9f99db77b875d31f75899504b115274f5d35ca13", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.Map"), List.of(new TypeParam("K", List.of()), new TypeParam("V", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("K"), Types.typeVar("V")), List.of()), new MethodTableTemplate(Set.of(Signature.of("clear"), Signature.of("containsKey", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("containsValue", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("entrySet"), Signature.of("get", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("isEmpty"), Signature.of("keySet"), Signature.of("put", Param.var(0), Param.var(1)), Signature.of("putAll", Param.fixed(ClassDesc.of("java.util.Map"))), Signature.of("remove", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("size"), Signature.of("values")), Set.of(Signature.of("compute", Param.var(0), Param.fixed(ClassDesc.of("java.util.function.BiFunction"))), Signature.of("computeIfAbsent", Param.var(0), Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("computeIfPresent", Param.var(0), Param.fixed(ClassDesc.of("java.util.function.BiFunction"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("forEach", Param.fixed(ClassDesc.of("java.util.function.BiConsumer"))), Signature.of("getClass"), Signature.of("getOrDefault", Param.fixed(ClassDesc.of("java.lang.Object")), Param.var(1)), Signature.of("hashCode"), Signature.of("merge", Param.var(0), Param.var(1), Param.fixed(ClassDesc.of("java.util.function.BiFunction"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("putIfAbsent", Param.var(0), Param.var(1)), Signature.of("remove", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("replace", Param.var(0), Param.var(1)), Signature.of("replace", Param.var(0), Param.var(1), Param.var(1)), Signature.of("replaceAll", Param.fixed(ClassDesc.of("java.util.function.BiFunction"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("copyOf", Param.fixed(ClassDesc.of("java.util.Map"))), Signature.of("entry", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("ofEntries", Param.fixed(ClassDesc.ofDescriptor("[Ljava/util/Map$Entry;")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Map], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Map].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.util.Map interface sealed=no\ntparams #0; #1\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers none\ntable abstract clear(); containsKey(java.lang.Object); containsValue(java.lang.Object); entrySet(); get(java.lang.Object); isEmpty(); keySet(); put(#0, #1); putAll(java.util.Map); remove(java.lang.Object); size(); values()\ntable concrete compute(#0, java.util.function.BiFunction); computeIfAbsent(#0, java.util.function.Function); computeIfPresent(#0, java.util.function.BiFunction); equals(java.lang.Object); forEach(java.util.function.BiConsumer); getClass(); getOrDefault(java.lang.Object, #1); hashCode(); merge(#0, #1, java.util.function.BiFunction); notify(); notifyAll(); putIfAbsent(#0, #1); remove(java.lang.Object, java.lang.Object); replace(#0, #1); replace(#0, #1, #1); replaceAll(java.util.function.BiFunction); toString(); wait(); wait(long); wait(long, int)\ntable static copyOf(java.util.Map); entry(java.lang.Object, java.lang.Object); of(); of(java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); ofEntries(java.util.Map$Entry[])\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Map] with a wildcard for every type argument.
    public static final InterfaceToken<Map<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Map] with the type arguments of this metamodel.
    public final InterfaceToken<Map<K, V>> token;

    /// The metamodel of [Map] with the type arguments the tokens give.
    ///
    /// @param k the token of the type argument `K`
    /// @param v the token of the type argument `V`
    public Map_(RefToken<K> k, RefToken<V> v) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(k), TokenArg.exact(v));
    }
}
