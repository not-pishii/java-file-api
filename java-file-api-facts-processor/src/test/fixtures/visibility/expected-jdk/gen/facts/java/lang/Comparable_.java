package gen.facts.java.lang;

import gen.facts.java.lang.Comparable_.Canonical;
import gen.facts.java.lang.Comparable_.Data;
import gen.facts.java.lang.Comparable_.Data.Inherited;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Comparable]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Comparable]: it is here as a supertype of [Integer], whose inherited members are called through this metamodel.
///
/// A member [Comparable] inherits has its fact in the metamodel of the supertype that declares it: [Object_].
///
/// @param <T> a type argument of [Comparable]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Comparable.class, fingerprint = "82fd1d67ea4c047f1229c4d3b86d5bb2510fc6e8621fab45f99cf50337af2699", complete = true, format = 9)
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
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Comparable_"), "82fd1d67ea4c047f1229c4d3b86d5bb2510fc6e8621fab45f99cf50337af2699", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.Comparable"), List.of(new TypeParam("T", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.var(0))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Comparable] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Comparable] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("java.lang.Comparable"), Signature.of("compareTo", Param.var(0)), List.of(), List.of(Types.typeVar("T")), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }
        }
    }

    /// The canonical form of [Comparable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Comparable].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.lang.Comparable interface sealed=no
        tparams #0
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract compareTo(#0) -> int throws -
        sam compareTo(#0) -> int throws -
        inherit method public abstract java.lang.Comparable compareTo(#0) -> int throws - erased (java.lang.Object) overrides -
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

    /// The fact of [Comparable#compareTo(Object)].
    public final MethodRef1<Comparable<T>, Int, T> compareTo_T;

    /// The fact of the single abstract method [Comparable#compareTo(Object)], which a lambda implements.
    public final Sam1<Comparable<T>, Int, T> sam;

    /// The metamodel of [Comparable] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Comparable_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.compareTo_T = UnsafeFacts.method(token, "compareTo", PrimitiveToken.INT, UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(compareTo_T);
    }
}
