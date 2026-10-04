package gen.facts.p;

import gen.facts.p.Redecl_.Canonical;
import gen.facts.p.Redecl_.Data;
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
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Redecl;

/// The full metamodel of [Redecl], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Redecl] inherits has its fact in the metamodel of the supertype that declares it: [Fn_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Redecl.class, fingerprint = "a347c49f78cc942e188db96cdbc50b2936495a5afdd1b2b8faf950f0eb0101bf", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Redecl_ {
    /// The shape of [Redecl] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Redecl] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Redecl_"), "a347c49f78cc942e188db96cdbc50b2936495a5afdd1b2b8faf950f0eb0101bf", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Redecl"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Redecl], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Redecl].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Redecl interface sealed=no\ntparams -\nsuperclasses -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract apply(java.lang.String) -> java.lang.String throws -\nsam apply(java.lang.String) -> java.lang.String throws -\ntable abstract apply(java.lang.String)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Redecl].
    public static final InterfaceToken<Redecl> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Redecl#apply(String)].
    public static final MethodRef1<Redecl, String, String> apply_String = UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Redecl#apply(String)], which a lambda implements.
    public static final Sam1<Redecl, String, String> sam = UnsafeFacts.sam(apply_String);

    private Redecl_() {
    }
}
