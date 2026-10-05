package gen.facts.java.util.function;

import gen.facts.java.util.function.Function_.Canonical;
import gen.facts.java.util.function.Function_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [Function]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Function]: it is only mentioned in the signatures of [p.Mix]. For the facts of its members add `Function.class` to `@Facts`.
///
/// @param <T> a type argument of [Function]
/// @param <R> a type argument of [Function]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Function.class, fingerprint = "85b3cfaa031e949d2928a798aae54ed934f21f491a8437b910154ebedf6db9f1", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Function_<T, R> {
    /// The shape of [Function] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Function] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.function.Function_"), "85b3cfaa031e949d2928a798aae54ed934f21f491a8437b910154ebedf6db9f1", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.function.Function"), List.of(new TypeParam("T", List.of()), new TypeParam("R", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T"), Types.typeVar("R")), List.of()), new MethodTableTemplate(Set.of(Signature.of("apply", Param.var(0))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("identity")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Function], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Function].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.util.function.Function interface sealed=no\ntparams #0; #1\nsuperclasses -\ninterfaces -\nsupertypes -\nenum -\nmembers none\nsam apply(#0) -> #1 throws -\ntable abstract apply(#0)\ntable concrete andThen(java.util.function.Function); compose(java.util.function.Function); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static identity()\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Function] with a wildcard for every type argument.
    public static final InterfaceToken<Function<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Function] with the type arguments of this metamodel.
    public final InterfaceToken<Function<T, R>> token;

    /// The metamodel of [Function] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    /// @param r the token of the type argument `R`
    public Function_(RefToken<T> t, RefToken<R> r) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(r));
    }
}
