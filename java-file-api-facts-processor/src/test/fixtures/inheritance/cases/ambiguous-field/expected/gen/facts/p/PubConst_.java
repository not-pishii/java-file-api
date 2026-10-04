package gen.facts.p;

import gen.facts.p.PubConst_.Canonical;
import gen.facts.p.PubConst_.Data;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.PubConst;

/// The full metamodel of [PubConst]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [PubConst]: it is here as a supertype of [p.Impl], whose inherited members are called through this metamodel.
///
/// A member [PubConst] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubConst.class, fingerprint = "24c3c6b7e6522d6c7cd9a0c179f2cb26433b9d7f404a3523dd6d68c350ce8d40", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class PubConst_ {
    /// The shape of [PubConst] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [PubConst] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubConst_"), "24c3c6b7e6522d6c7cd9a0c179f2cb26433b9d7f404a3523dd6d68c350ce8d40", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.PubConst"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [PubConst], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [PubConst].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.PubConst interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember field static constant java.lang.String K = \"public\"\nmember field static constant java.lang.String name = \"public\"\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [PubConst].
    public static final InterfaceToken<PubConst> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [PubConst#K].
    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "public");

    /// The fact of [PubConst#name].
    public static final StaticFieldRef<String> name = UnsafeFacts.constantField(TOKEN, "name", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "public");

    private PubConst_() {
    }
}
