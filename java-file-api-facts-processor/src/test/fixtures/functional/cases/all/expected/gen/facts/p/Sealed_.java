package gen.facts.p;

import gen.facts.p.Sealed_.Canonical;
import gen.facts.p.Sealed_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Sealed;

/// The full metamodel of [Sealed], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sealed] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sealed.class, fingerprint = "b77a247085865596829af4b23adf693790e83033176d5c051183ec1ada7f1ae8", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Sealed_ {
    /// The shape of [Sealed] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Sealed] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sealed_"), "b77a247085865596829af4b23adf693790e83033176d5c051183ec1ada7f1ae8", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Sealed"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), true);

        private Data() {
        }
    }

    /// The canonical form of [Sealed], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Sealed].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Sealed interface sealed=yes\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract run() -> void throws -\ntable abstract run()\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Sealed].
    public static final InterfaceToken<Sealed> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Sealed#run()].
    public static final VoidMethodRef0<Sealed> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    private Sealed_() {
    }
}
