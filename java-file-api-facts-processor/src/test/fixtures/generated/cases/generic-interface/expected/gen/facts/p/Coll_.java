package gen.facts.p;

import gen.facts.p.Coll_.Canonical;
import gen.facts.p.Coll_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Coll;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Coll.class, fingerprint = "08062df0267ee78a6d6affe6aeac00d42782995ec1cb8b8938a61403fb1ba20c", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Coll_<E> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Coll_"), "08062df0267ee78a6d6affe6aeac00d42782995ec1cb8b8938a61403fb1ba20c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Coll"), List.of(new TypeParam("E", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("E")), List.of()), new MethodTableTemplate(Set.of(Signature.of("add", Param.var(0))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Coll interface sealed=no\ntparams #0\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract add(#0) -> boolean throws -\nsam add(#0) -> boolean throws -\ntable abstract add(#0)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Coll<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    public final InterfaceToken<Coll<E>> token;

    public final MethodRef1<Coll<E>, Bool, E> add_E;

    public final Sam1<Coll<E>, Bool, E> sam;

    public Coll_(RefToken<E> e) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(e));
        this.add_E = UnsafeFacts.method(token, "add", PrimitiveToken.BOOLEAN, UnsafeFacts.param(e, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(add_E);
    }
}
