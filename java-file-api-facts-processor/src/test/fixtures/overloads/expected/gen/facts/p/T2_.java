package gen.facts.p;

import gen.facts.p.T2_.Canonical;
import gen.facts.p.T2_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.T2;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = T2.class, fingerprint = "3590e0bc8d5e9e28039cac37eda2ca474e398bdec309128d2d2968ccfc8a97c1", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class T2_<T> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.T2_"), "3590e0bc8d5e9e28039cac37eda2ca474e398bdec309128d2d2968ccfc8a97c1", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.T2"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.var(0)), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("one", Param.var(0)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("T2", Param.var(0)), Signature.of("T2"), Signature.of("T2", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.T2 open-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor(#0) throws -\nmember ctor() throws -\nmember ctor(java.lang.String) throws -\nmember field instance final java.lang.String made\nmember method overridable m(#0) -> java.lang.String throws -\nmember method overridable m(java.lang.String) -> java.lang.String throws -\nmember method overridable one(#0) -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(#0); m(java.lang.String); notify(); notifyAll(); one(#0); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor T2(#0); T2(); T2(java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<T2<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    public final OpenClassToken<T2<T>> token;

    public final FieldRef<T2<T>, String> made;

    public final CtorRef0<T2<T>> new_;

    public final CtorRef1<T2<T>, String> new_String;

    public final CtorRef1<T2<T>, T> new_T;

    public final MethodRef1<T2<T>, String, String> m_String;

    public final MethodRef1<T2<T>, String, T> m_T;

    public final MethodRef1<T2<T>, String, T> one_T;

    public T2_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.made = UnsafeFacts.field(token, "made", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.new_String = UnsafeFacts.ctor(token, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.new_T = UnsafeFacts.ctor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL);
        this.m_String = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.m_T = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
        this.one_T = UnsafeFacts.method(token, "one", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
    }
}
