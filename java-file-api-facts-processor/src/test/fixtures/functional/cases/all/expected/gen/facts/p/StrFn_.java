package gen.facts.p;

import gen.facts.p.StrFn_.Canonical;
import gen.facts.p.StrFn_.Data;
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
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.StrFn;

/// The full metamodel of [StrFn], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [StrFn] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.util.function.Function_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = StrFn.class, fingerprint = "0b1dd942466184d52fbaa60cadc1643139e9d35655a796152485ac042539b3aa", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class StrFn_ {
    /// The shape of [StrFn] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [StrFn] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.StrFn_"), "0b1dd942466184d52fbaa60cadc1643139e9d35655a796152485ac042539b3aa", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.StrFn"), List.of(), List.of(), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))), Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [StrFn], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [StrFn].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.StrFn interface sealed=no\ntparams -\nsuperclasses -\ninterfaces java.util.function.Function\nsupertypes java.util.function.Function<java.lang.String, java.lang.String>\nenum -\nmembers declared-public\nsam apply(java.lang.String) -> java.lang.String throws -\ntable abstract apply(java.lang.String)\ntable concrete andThen(java.util.function.Function); compose(java.util.function.Function); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [StrFn].
    public static final InterfaceToken<StrFn> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of the single abstract method [java.util.function.Function#apply(Object)], which a lambda implements.
    public static final Sam1<StrFn, String, String> sam = UnsafeFacts.sam(UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT));

    private StrFn_() {
    }
}
