package gen.facts.p;

import gen.facts.p.Sorted_.Canonical;
import gen.facts.p.Sorted_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
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
import org.jspecify.annotations.NullMarked;
import p.Sorted;

/// The full metamodel of [Sorted], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sorted] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Sorted]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sorted.class, fingerprint = "653d299c00bd924b3cbd19ed285976e576830502dc927298e539e2e1cbe2b589", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Sorted_<T extends Comparable<T>> {
    /// The shape of [Sorted] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sorted] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sorted_"), "653d299c00bd924b3cbd19ed285976e576830502dc927298e539e2e1cbe2b589", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Sorted"), List.of(new TypeParam("T", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.typeVar("T"))))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("max"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("with", Param.var(0))), Set.of(), Set.of(Signature.of("Sorted", Param.var(0)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Sorted], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sorted].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Sorted open-class sealed=no
        tparams #0 extends java.lang.Comparable<#0>
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor(#0) throws -
        member method overridable max() -> #0 throws -
        member method overridable with(#0) -> p.Sorted<#0> throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); max(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); with(#0)
        table static -
        table ctor Sorted(#0)
        """;

        private Canonical() {
        }
    }

    /// The token of [Sorted] with a wildcard for every type argument.
    public static final OpenClassToken<Sorted<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Sorted] with the type arguments of this metamodel.
    public final OpenClassToken<Sorted<T>> token;

    /// The fact of [Sorted#Sorted(Comparable)].
    public final CtorRef1<Sorted<T>, T> new_T;

    /// The fact of [Sorted#max()].
    public final MethodRef0<Sorted<T>, T> max;

    /// The fact of [Sorted#with(Comparable)].
    public final MethodRef1<Sorted<T>, Sorted<T>, T> with_T;

    /// The metamodel of [Sorted] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Sorted_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_T = UnsafeFacts.ctor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL);
        this.max = UnsafeFacts.method(token, "max", t, MemberTraits.OVERRIDABLE);
        this.with_T = UnsafeFacts.method(token, "with", token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
    }
}
