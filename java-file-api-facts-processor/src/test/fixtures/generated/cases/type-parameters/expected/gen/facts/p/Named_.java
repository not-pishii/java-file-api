package gen.facts.p;

import gen.facts.p.Named_.Canonical;
import gen.facts.p.Named_.Data;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Named;

/// The full metamodel of [Named], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Named] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <Data_> a type argument of [Named]
/// @param <String_> a type argument of [Named]
/// @param <Int> a type argument of [Named]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Named.class, fingerprint = "9e0dd569d0b67662be2f9e3e8bd3c950e0274e326373630ee10481ae5f7a2bcc", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Named_<Data_, String_, Int> {
    /// The shape of [Named] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Named] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Named_"), "9e0dd569d0b67662be2f9e3e8bd3c950e0274e326373630ee10481ae5f7a2bcc", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Named"), List.of(new TypeParam("Data", List.of()), new TypeParam("String", List.of()), new TypeParam("Int", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("Data"), Types.typeVar("String"), Types.typeVar("Int")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Named], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Named].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Named interface sealed=no\ntparams #0; #1; #2\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\ntable abstract -\ntable concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Named] with a wildcard for every type argument.
    public static final InterfaceToken<Named<?, ?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Named] with the type arguments of this metamodel.
    public final InterfaceToken<Named<Data_, String_, Int>> token;

    /// The metamodel of [Named] with the type arguments the tokens give.
    ///
    /// @param data_ the token of the type argument `Data_`
    /// @param string_ the token of the type argument `String_`
    /// @param int_ the token of the type argument `Int`
    public Named_(RefToken<Data_> data_, RefToken<String_> string_, RefToken<Int> int_) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(data_), TokenArg.exact(string_), TokenArg.exact(int_));
    }
}
