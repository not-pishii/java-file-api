package gen.facts.java.lang;

import gen.facts.java.lang.Comparable_.Canonical;
import gen.facts.java.lang.Comparable_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Comparable]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Comparable]: it is only mentioned in the signatures of [p.Both], [p.RawBound] and [p.Sorted]. For the facts of its members add `Comparable.class` to `@Facts`.
///
/// @param <T> a type argument of [Comparable]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Comparable.class, fingerprint = "c87c7be05408e91cb1b3935e474e90ad3c4a92f9589b465ef355ca63757d7925", complete = false, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Comparable_<T> {
    /// The shape of [Comparable] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Comparable] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Comparable_"), "c87c7be05408e91cb1b3935e474e90ad3c4a92f9589b465ef355ca63757d7925", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.Comparable"), List.of(new TypeParam("T", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.var(0))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Comparable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Comparable].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.Comparable interface sealed=no
        tparams #0
        superclasses -
        interfaces -
        supertypes -
        enum -
        members none
        sam compareTo(#0) -> int throws -
        table abstract compareTo(#0)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Comparable] with a wildcard for every type argument.
    public static final InterfaceToken<Comparable<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Comparable] with the type arguments of this metamodel.
    public final InterfaceToken<Comparable<T>> token;

    /// The metamodel of [Comparable] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Comparable_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
    }
}
