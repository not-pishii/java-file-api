package gen.facts.java.lang;

import gen.facts.java.lang.Comparable_.Canonical;
import gen.facts.java.lang.Comparable_.Data;
import gen.facts.java.lang.Comparable_Heritage.Implement;
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
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Comparable]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Comparable]: it is here as a supertype of [p.Day], whose inherited members are called through this metamodel.
///
/// A member [Comparable] inherits has its fact in the metamodel of the supertype that declares it: [Object_].
///
/// A generated class extends or implements [Comparable] by the proof its metamodel starts, with the stages of `Comparable_Heritage`.
///
/// @param <T> a type argument of [Comparable]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Comparable.class, fingerprint = "927e7b78070f57ea905a32bc2439d67e22f1e4f6530715d17b5e13c69a24f3a9", complete = true, format = 14)
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
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Comparable_"), "927e7b78070f57ea905a32bc2439d67e22f1e4f6530715d17b5e13c69a24f3a9", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.Comparable"), List.of(new TypeParam("T", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.var(0))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Comparable_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Comparable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Comparable].
        static final String TEXT = """
        javafile-facts-canonical 8
        type java.lang.Comparable interface sealed=no nesting=top
        tparams #0
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract compareTo(#0) -> int throws -
        sam compareTo(#0) -> int throws -
        inherit method compareTo(#0) erased (java.lang.Object) overrides - by interface abstract java.lang.Comparable (#0) -> int throws -
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
        this.compareTo_T = UnsafeInvocables.method(token, "compareTo", PrimitiveToken.INT, UnsafeFacts.param(t, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeInvocables.sam(compareTo_T);
    }

    /// Starts the proof that a class being declared implements [Comparable]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Implement<T, Self> implement(RefToken<Self> self) {
        return new Comparable_Heritage().implement(this.token, self);
    }
}
