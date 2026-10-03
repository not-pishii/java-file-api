package gen.facts.java.util;

import gen.facts.java.util.Map_Entry_.Canonical;
import gen.facts.java.util.Map_Entry_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map.Entry;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Entry.class, fingerprint = "d18d5ab4e61d9df8e5138f084d54c20ebf1865b2d28edfb435a910b7e7f44409", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Map_Entry_<K, V> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Map_Entry_"), "d18d5ab4e61d9df8e5138f084d54c20ebf1865b2d28edfb435a910b7e7f44409", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.Map$Entry"), List.of(new TypeParam("K", List.of()), new TypeParam("V", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("K"), Types.typeVar("V")), List.of()), new MethodTableTemplate(Set.of(Signature.of("getKey"), Signature.of("getValue"), Signature.of("setValue", Param.var(1))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("comparingByKey"), Signature.of("comparingByKey", Param.fixed(ClassDesc.of("java.util.Comparator"))), Signature.of("comparingByValue"), Signature.of("comparingByValue", Param.fixed(ClassDesc.of("java.util.Comparator"))), Signature.of("copyOf", Param.fixed(ClassDesc.of("java.util.Map$Entry")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.util.Map$Entry interface sealed=no\ntparams #0; #1\nsuperclasses -\nsupertypes -\nenum -\nmembers none\ntable abstract getKey(); getValue(); setValue(#1)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static comparingByKey(); comparingByKey(java.util.Comparator); comparingByValue(); comparingByValue(java.util.Comparator); copyOf(java.util.Map$Entry)\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Entry<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    public final InterfaceToken<Entry<K, V>> token;

    public Map_Entry_(RefToken<K> k, RefToken<V> v) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(k), TokenArg.exact(v));
    }
}
