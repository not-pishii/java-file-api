package gen.facts.p;

import gen.facts.p.Fn_.Canonical;
import gen.facts.p.Fn_.Data;
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
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Fn;

/// The full metamodel of [Fn], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fn] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fn.class, fingerprint = "e4b48b31908dd5c4fe0b1ff664e9dc39f27e8c09f701308a086768ad1dbd0b1c", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Fn_ {
    /// The shape of [Fn] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Fn] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fn_"), "e4b48b31908dd5c4fe0b1ff664e9dc39f27e8c09f701308a086768ad1dbd0b1c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Fn"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Fn], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Fn].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Fn interface sealed=no\ntparams -\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember method abstract apply(java.lang.String) -> java.lang.String throws -\nmember method abstract equals(java.lang.Object) -> boolean throws -\nsam apply(java.lang.String) -> java.lang.String throws -\ntable abstract apply(java.lang.String)\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Fn].
    public static final InterfaceToken<Fn> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Fn#apply(String)].
    public static final MethodRef1<Fn, String, String> apply_String = UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of [Fn#equals(Object)].
    public static final MethodRef1<Fn, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Fn#apply(String)], which a lambda implements.
    public static final Sam1<Fn, String, String> sam = UnsafeFacts.sam(apply_String);

    private Fn_() {
    }
}
