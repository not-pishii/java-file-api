package gen.facts.p;

import gen.facts.p.Names_.Canonical;
import gen.facts.p.Names_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Names;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Names.class, fingerprint = "96e4b42d753694669b4c1c917de0e671619905c3f1526f21c0d4e03ebebb621f", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Names_<List__, UnsafeFacts_, Token> {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Names_"), "96e4b42d753694669b4c1c917de0e671619905c3f1526f21c0d4e03ebebb621f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Names"), List.of(new TypeParam("List", List.of()), new TypeParam("UnsafeFacts", List.of()), new TypeParam("Token", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("List"), Types.typeVar("UnsafeFacts"), Types.typeVar("Token")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all"), Signature.of("clone"), Signature.of("data", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Names"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Names open-class sealed=no\ntparams #0; #1; #2\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable #0 first\nmember field instance mutable #2 token\nmember method overridable <^0> data(^0) -> ^0 throws -\nmember method overridable all() -> java.util.List<#1> throws -\ntable abstract -\ntable concrete all(); clone(); data(java.lang.Object); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Names()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Names<?, ?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded(), TokenArg.unbounded());

    public final OpenClassToken<Names<List__, UnsafeFacts_, Token>> token;

    public final MutableFieldRef<Names<List__, UnsafeFacts_, Token>, List__> first;

    public final MutableFieldRef<Names<List__, UnsafeFacts_, Token>, Token> token_;

    public final CtorRef0<Names<List__, UnsafeFacts_, Token>> new_;

    public final MethodRef0<Names<List__, UnsafeFacts_, Token>, List<UnsafeFacts_>> all;

    private final RefToken<List__> list__;

    private final RefToken<UnsafeFacts_> unsafeFacts_;

    private final RefToken<Token> token__;

    public Names_(RefToken<List__> list__, RefToken<UnsafeFacts_> unsafeFacts_, RefToken<Token> token__) {
        this.list__ = list__;
        this.unsafeFacts_ = unsafeFacts_;
        this.token__ = token__;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(list__), TokenArg.exact(unsafeFacts_), TokenArg.exact(token__));
        this.first = UnsafeFacts.mutableField(token, "first", list__);
        this.token_ = UnsafeFacts.mutableField(token, "token", token__);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.all = UnsafeFacts.method(token, "all", UnsafeFacts.<List<UnsafeFacts_>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(unsafeFacts_)), MemberTraits.OVERRIDABLE);
    }

    public <Data_> MethodRef1<Names<List__, UnsafeFacts_, Token>, Data_, Data_> data_Data(RefToken<Data_> data_) {
        return UnsafeFacts.method(token, "data", data_, UnsafeFacts.param(data_, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(data_));
    }
}
