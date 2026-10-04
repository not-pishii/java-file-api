package gen.facts.p;

import gen.facts.p.Def_.Canonical;
import gen.facts.p.Def_.Data;
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
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Def;

/// The full metamodel of [Def], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Def] inherits has its fact in the metamodel of the supertype that declares it: [Fn_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Def.class, fingerprint = "9d0ed9d0248d8a8c83557c78f026e21e338b419622392acd249e96a4e53bb0de", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Def_ {
    /// The shape of [Def] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Def] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Def_"), "9d0ed9d0248d8a8c83557c78f026e21e338b419622392acd249e96a4e53bb0de", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Def"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Def], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Def].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Def interface sealed=no\ntparams -\nsuperclasses -\ninterfaces p.Fn\nsupertypes -\nenum -\nmembers declared-public\nmember method overridable apply(java.lang.String) -> java.lang.String throws -\ntable abstract -\ntable concrete apply(java.lang.String); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Def].
    public static final InterfaceToken<Def> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Def#apply(String)].
    public static final MethodRef1<Def, String, String> apply_String = UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Def_() {
    }
}
