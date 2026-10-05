package gen.facts.p;

import gen.facts.p.Op_.Canonical;
import gen.facts.p.Op_.Data;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Op;

/// The full metamodel of [Op], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Op] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.util.function.Function_].
///
/// @param <T> a type argument of [Op]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Op.class, fingerprint = "53686e087f81c54b0214acf5ecf4c214ac59cd987a6fe4bf36b41b44e03bbf42", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Op_<T> {
    /// The shape of [Op] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Op] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Op_"), "53686e087f81c54b0214acf5ecf4c214ac59cd987a6fe4bf36b41b44e03bbf42", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Op"), List.of(new TypeParam("T", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("T")), Types.exact(Types.typeVar("T")))))), new MethodTableTemplate(Set.of(Signature.of("apply", Param.var(0))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Op], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Op].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Op interface sealed=no\ntparams #0\nsuperclasses -\ninterfaces java.util.function.Function\nsupertypes java.util.function.Function<#0, #0>\nenum -\nmembers declared-public\nsam apply(#0) -> #0 throws -\ntable abstract apply(#0)\ntable concrete andThen(java.util.function.Function); compose(java.util.function.Function); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Op] with a wildcard for every type argument.
    public static final InterfaceToken<Op<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Op] with the type arguments of this metamodel.
    public final InterfaceToken<Op<T>> token;

    /// The fact of the single abstract method [java.util.function.Function#apply(Object)], which a lambda implements.
    public final Sam1<Op<T>, T, T> sam;

    /// The metamodel of [Op] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Op_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.sam = UnsafeFacts.sam(UnsafeFacts.method(token, "apply", t, UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT));
    }
}
