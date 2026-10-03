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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Function.class, fingerprint = "0975e62f3a5355d9335b9adcd1f83512a62afc490b6dd1af3b12dee0ed01d271", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Function_<T, R> {
    public static final class Data {
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.function.Function_"), "0975e62f3a5355d9335b9adcd1f83512a62afc490b6dd1af3b12dee0ed01d271", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.util.function.Function"), List.of(new TypeParam("T", List.of()), new TypeParam("R", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T"), Types.typeVar("R")), List.of()), new MethodTableTemplate(Set.of(Signature.of("apply", Param.var(0))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("identity")), Set.of()), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.util.function.Function interface sealed=no\ntparams #0; #1\nsuperclasses -\nsupertypes -\nenum -\nmembers none\nsam apply(#0) -> #1 throws -\ntable abstract apply(#0)\ntable concrete andThen(java.util.function.Function); compose(java.util.function.Function); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static identity()\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final InterfaceToken<Function<?, ?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    public final InterfaceToken<Function<T, R>> token;

    public Function_(RefToken<T> t, RefToken<R> r) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t), TokenArg.exact(r));
    }
}
