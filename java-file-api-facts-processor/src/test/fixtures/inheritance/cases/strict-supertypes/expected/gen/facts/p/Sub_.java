package gen.facts.p;

import gen.facts.p.Sub_.Canonical;
import gen.facts.p.Sub_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Sub;

/// The full metamodel of [Sub], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sub] inherits has its fact in the metamodel of the supertype that declares it: [GoneApi_] and [Sup_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sub.class, fingerprint = "59dc6e9c0cb6923541b25cd33e49c7838284b7a1b2e636e1a137838bbfc9c36c", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Sub_ {
    /// The shape of [Sub] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Sub] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sub_"), "59dc6e9c0cb6923541b25cd33e49c7838284b7a1b2e636e1a137838bbfc9c36c", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Sub"), List.of(), List.of(ClassDesc.of("p.Sup"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("gone", Param.fixed(ClassDesc.of("p.Secret"))), Signature.of("hashCode"), Signature.of("lost", Param.fixed(ClassDesc.of("p.Secret"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("sub"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Sub"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Sub], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Sub].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Sub open-class sealed=no\ntparams -\nsuperclasses p.Sup; java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable sub() -> void throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); gone(p.Secret); hashCode(); lost(p.Secret); notify(); notifyAll(); sub(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Sub()\n";

        private Canonical() {
        }
    }

    /// The token of [Sub].
    public static final OpenClassToken<Sub> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Sub#Sub()].
    public static final CtorRef0<Sub> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Sub#sub()].
    public static final VoidMethodRef0<Sub> sub = UnsafeFacts.voidMethod(TOKEN, "sub", MemberTraits.OVERRIDABLE);

    private Sub_() {
    }
}
