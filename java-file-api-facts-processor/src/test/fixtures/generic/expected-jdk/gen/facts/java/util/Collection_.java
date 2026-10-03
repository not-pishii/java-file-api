package gen.facts.java.util;

import gen.facts.java.util.Collection_.Canonical;
import gen.facts.java.util.Collection_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.Collection;
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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Collection.class, fingerprint = "f94db68f38291310c0af91b424f5df42be3d2962a7e63daad5f00256a8c5362f", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Collection_<E> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Collection_"), "f94db68f38291310c0af91b424f5df42be3d2962a7e63daad5f00256a8c5362f", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.Collection"), List.of(new TypeParam("E", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("E")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Iterable"), List.of(Types.exact(Types.typeVar("E")))))), new MethodTableTemplate(Set.of(Signature.of("add", Param.var(0)), Signature.of("addAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("clear"), Signature.of("contains", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("containsAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("isEmpty"), Signature.of("iterator"), Signature.of("remove", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("removeAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("retainAll", Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("size"), Signature.of("toArray"), Signature.of("toArray", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("forEach", Param.fixed(ClassDesc.of("java.util.function.Consumer"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("parallelStream"), Signature.of("removeIf", Param.fixed(ClassDesc.of("java.util.function.Predicate"))), Signature.of("spliterator"), Signature.of("stream"), Signature.of("toArray", Param.fixed(ClassDesc.of("java.util.function.IntFunction"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.util.Collection interface sealed=no\ntparams #0\nsuperclasses -\nsupertypes java.lang.Iterable<#0>\nenum -\nmembers none\ntable abstract add(#0); addAll(java.util.Collection); clear(); contains(java.lang.Object); containsAll(java.util.Collection); isEmpty(); iterator(); remove(java.lang.Object); removeAll(java.util.Collection); retainAll(java.util.Collection); size(); toArray(); toArray(java.lang.Object[])\ntable concrete equals(java.lang.Object); forEach(java.util.function.Consumer); getClass(); hashCode(); notify(); notifyAll(); parallelStream(); removeIf(java.util.function.Predicate); spliterator(); stream(); toArray(java.util.function.IntFunction); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Collection<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    public final InterfaceToken<Collection<E>> token;

    public Collection_(RefToken<E> e) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(e));
    }
}
