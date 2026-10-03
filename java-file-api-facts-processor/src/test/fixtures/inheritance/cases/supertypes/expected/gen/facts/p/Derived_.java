package gen.facts.p;

import gen.facts.p.Derived_.Canonical;
import gen.facts.p.Derived_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Derived;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Derived.class, fingerprint = "015c7df7207eed2b4d2ddbf401217dc17b3fe59699ec83bcc780b5c3041a83dc", complete = true, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Derived_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Derived_"), "015c7df7207eed2b4d2ddbf401217dc17b3fe59699ec83bcc780b5c3041a83dc", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Derived"), List.of(), List.of(ClassDesc.of("p.Base"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("abs"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("f"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("inherited"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("over"), Signature.of("own"), Signature.of("pkg"), Signature.of("prot"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("dstatic"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sbase")), Set.of(Signature.of("Derived"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype p.Derived open-class sealed=no\ntparams -\nsuperclasses p.Base; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable abs() -> void throws -\nmember method overridable hidden(java.lang.Object) -> void throws -\nmember method overridable over() -> void throws -\nmember method overridable own() -> void throws -\nmember method static dstatic() -> void throws -\ntable abstract -\ntable concrete abs(); clone(); dflt(); equals(java.lang.Object); f(); finalize(); getClass(); hashCode(); hidden(java.lang.Object); inherited(); notify(); notifyAll(); over(); own(); pkg(); prot(); toString(); wait(); wait(long); wait(long, int)\ntable static dstatic(); hidden(java.lang.String); sbase()\ntable ctor Derived()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Derived> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final CtorRef0<Derived> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    public static final VoidMethodRef0<Derived> abs = UnsafeFacts.voidMethod(TOKEN, "abs", MemberTraits.OVERRIDABLE);

    public static final VoidStaticMethodRef0 dstatic = UnsafeFacts.voidStaticMethod(TOKEN, "dstatic", MemberTraits.FINAL);

    public static final VoidMethodRef1<Derived, Object> hidden_Object = UnsafeFacts.voidMethod(TOKEN, "hidden", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef0<Derived> over = UnsafeFacts.voidMethod(TOKEN, "over", MemberTraits.OVERRIDABLE);

    public static final VoidMethodRef0<Derived> own = UnsafeFacts.voidMethod(TOKEN, "own", MemberTraits.OVERRIDABLE);

    private Derived_() {
    }
}
