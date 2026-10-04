package gen.facts.java.util;

import gen.facts.java.util.List_.Canonical;
import gen.facts.java.util.List_.Data;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [List]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [List]: it is only mentioned in the signatures of [p.Mix]. For the facts of its members add `List.class` to `@Facts`.
///
/// @param <E> a type argument of [List]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = List.class, fingerprint = "99471724ed7206de50214c994706cc0d324bbc81d1e2b8cf7f8e6c6e6001b3ed", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class List_<E> {
    /// The shape of [List] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [List] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.List_"), "99471724ed7206de50214c994706cc0d324bbc81d1e2b8cf7f8e6c6e6001b3ed", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.List"), List.of(new TypeParam("E", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Iterable"), List.of(Types.exact(Types.typeVar("E")))), new ParameterizedTypeRef(ClassDesc.of("java.util.Collection"), List.of(Types.exact(Types.typeVar("E")))), new ParameterizedTypeRef(ClassDesc.of("java.util.SequencedCollection"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(Signature.of("add", Param.var(0)), Signature.of("add", Param.fixed(ConstantDescs.CD_int), Param.var(0)), Signature.of("addAll", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("clear"), Signature.of("contains", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("containsAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("get", Param.fixed(ConstantDescs.CD_int)), Signature.of("indexOf", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("isEmpty"), Signature.of("iterator"), Signature.of("lastIndexOf", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("listIterator"), Signature.of("listIterator", Param.fixed(ConstantDescs.CD_int)), Signature.of("remove", Param.fixed(ConstantDescs.CD_int)), Signature.of("remove", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("removeAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("retainAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("set", Param.fixed(ConstantDescs.CD_int), Param.var(0)), Signature.of("size"), Signature.of("subList", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toArray"), Signature.of("toArray", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;")))), Set.of(Signature.of("addFirst", Param.var(0)), Signature.of("addLast", Param.var(0)), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("forEach", Param.fixed(ClassDesc.of("java.util.function.Consumer"))), Signature.of("getClass"), Signature.of("getFirst"), Signature.of("getLast"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("parallelStream"), Signature.of("removeFirst"), Signature.of("removeIf", Param.fixed(ClassDesc.of("java.util.function.Predicate"))), Signature.of("removeLast"), Signature.of("replaceAll", Param.fixed(ClassDesc.of("java.util.function.UnaryOperator"))), Signature.of("reversed"), Signature.of("sort", Param.fixed(ClassDesc.of("java.util.Comparator"))), Signature.of("spliterator"), Signature.of("stream"), Signature.of("toArray", Param.fixed(ClassDesc.of("java.util.function.IntFunction"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("copyOf", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("of"), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("of", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [List], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [List].
        static final String TEXT = "javafile-facts-canonical 4\ntype java.util.List interface sealed=no\ntparams #0\nsuperclasses -\nsupertypes java.lang.Iterable<#0>; java.util.Collection<#0>; java.util.SequencedCollection<#0>\nenum -\nmembers none\ntable abstract add(#0); add(int, #0); addAll(int, java.util.Collection); addAll(java.util.Collection); clear(); contains(java.lang.Object); containsAll(java.util.Collection); get(int); indexOf(java.lang.Object); isEmpty(); iterator(); lastIndexOf(java.lang.Object); listIterator(); listIterator(int); remove(int); remove(java.lang.Object); removeAll(java.util.Collection); retainAll(java.util.Collection); set(int, #0); size(); subList(int, int); toArray(); toArray(java.lang.Object[])\ntable concrete addFirst(#0); addLast(#0); equals(java.lang.Object); forEach(java.util.function.Consumer); getClass(); getFirst(); getLast(); hashCode(); notify(); notifyAll(); parallelStream(); removeFirst(); removeIf(java.util.function.Predicate); removeLast(); replaceAll(java.util.function.UnaryOperator); reversed(); sort(java.util.Comparator); spliterator(); stream(); toArray(java.util.function.IntFunction); toString(); wait(); wait(long); wait(long, int)\ntable static copyOf(java.util.Collection); of(); of(java.lang.Object); of(java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object); of(java.lang.Object[])\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [List] with a wildcard for every type argument.
    public static final InterfaceToken<List<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [List] with the type arguments of this metamodel.
    public final InterfaceToken<List<E>> token;

    /// The metamodel of [List] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public List_(RefToken<E> e) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(e));
    }
}
