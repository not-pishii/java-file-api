package gen.facts.java.util;

import gen.facts.java.util.Set_.Canonical;
import gen.facts.java.util.Set_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [Set]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Set]: it is only mentioned in the signatures of [p.Low] and [p.Up]. For the facts of its members add `Set.class` to `@Facts`.
///
/// @param <E> a type argument of [Set]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Set.class, fingerprint = "59a06eafb1e9ab45d39e7e534d87946065f66007512e1c6f10154e48ee5e3836", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Set_<E> {
    /// The shape of [Set] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Set] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Set_"), "59a06eafb1e9ab45d39e7e534d87946065f66007512e1c6f10154e48ee5e3836", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.Set"), List.of(new TypeParam("E", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Iterable"), List.of(Types.exact(Types.typeVar("E")))), new ParameterizedTypeRef(ClassDesc.of("java.util.Collection"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(Signature.of("add", Param.var(0)), Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("clear"), Signature.of("contains", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("containsAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("isEmpty"), Signature.of("iterator"), Signature.of("remove", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("removeAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("retainAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("size"), Signature.of("toArray"), Signature.of("toArray", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("forEach", Param.fixed(ClassDesc.of("java.util.function.Consumer"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("parallelStream"), Signature.of("removeIf", Param.fixed(ClassDesc.of("java.util.function.Predicate"))), Signature.of("spliterator"), Signature.of("stream"), Signature.of("toArray", Param.fixed(ClassDesc.of("java.util.function.IntFunction"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("copyOf", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("of"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Set], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Set].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.util.Set interface sealed=no\ntparams #0\nsuperclasses -\ninterfaces java.lang.Iterable; java.util.Collection\nsupertypes java.lang.Iterable<#0>; java.util.Collection<#0>\nenum -\nmembers none\ntable abstract add(#0); addAll(java.util.Collection); clear(); contains(java.lang.Object); containsAll(java.util.Collection); isEmpty(); iterator(); remove(java.lang.Object); removeAll(java.util.Collection); retainAll(java.util.Collection); size(); toArray(); toArray(java.lang.Object[])\ntable concrete equals(java.lang.Object); forEach(java.util.function.Consumer); getClass(); hashCode(); notify(); notifyAll(); parallelStream(); removeIf(java.util.function.Predicate); spliterator(); stream(); toArray(java.util.function.IntFunction); toString(); wait(); wait(long); wait(long, int)\ntable static copyOf(java.util.Collection); of(); of(java.lang.Object); of(java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object[])\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Set] with a wildcard for every type argument.
    public static final InterfaceToken<Set<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Set] with the type arguments of this metamodel.
    public final InterfaceToken<Set<E>> token;

    /// The metamodel of [Set] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public Set_(RefToken<E> e) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(e));
    }
}
