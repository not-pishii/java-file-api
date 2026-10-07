package gen.facts.p;

import gen.facts.p.Up_.Canonical;
import gen.facts.p.Up_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
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
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Up;

/// The full metamodel of [Up], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Up] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <Gen> a type argument of [Up]
/// @param <Java> a type argument of [Up]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Up.class, fingerprint = "f55d8ea817f01abac92913e737ba2ad278139e9479b15aed8511a932f6772d55", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Up_<Gen, Java> {
    /// The shape of [Up] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Up] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Up_"), "f55d8ea817f01abac92913e737ba2ad278139e9479b15aed8511a932f6772d55", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Up"), List.of(new TypeParam("Gen", List.of()), new TypeParam("Java", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("Gen"), Types.typeVar("Java")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all", Param.var(1)), Signature.of("clone"), Signature.of("each", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Up"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Up], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Up].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Up open-class sealed=no
        tparams #0; #1
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable <^0> each(^0) -> java.util.Set<^0> throws -
        member method public overridable all(#1) -> java.util.Set<#0> throws -
        table abstract -
        table concrete all(#1); clone(); each(java.lang.Object); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Up()
        """;

        private Canonical() {
        }
    }

    /// The token of [Up] with a wildcard for every type argument.
    public static final OpenClassToken<Up<?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Up] with the type arguments of this metamodel.
    public final OpenClassToken<Up<Gen, Java>> token;

    /// The fact of [Up#Up()].
    public final CtorRef0<Up<Gen, Java>> new_;

    /// The fact of [Up#all(Object)].
    public final MethodRef1<Up<Gen, Java>, Set<Gen>, Java> all_Java;

    private final RefToken<Gen> gen_;

    private final RefToken<Java> java;

    /// The metamodel of [Up] with the type arguments the tokens give.
    ///
    /// @param gen_ the token of the type argument `Gen`
    /// @param java the token of the type argument `Java`
    public Up_(RefToken<Gen> gen_, RefToken<Java> java) {
        this.gen_ = gen_;
        this.java = java;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(gen_), TokenArg.exact(java));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.all_Java = UnsafeFacts.method(token, "all", UnsafeFacts.<Set<Gen>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(gen_)), UnsafeFacts.param(java, Param.var(1)), MemberTraits.OVERRIDABLE);
    }

    /// The fact of [Up#each(Object)], for the type arguments the tokens give.
    ///
    /// @param <Me> a type argument of the method
    /// @param me the token of the type argument `Me`
    /// @return the fact
    public <Me> MethodRef1<Up<Gen, Java>, Set<Me>, Me> each_Me(RefToken<Me> me) {
        return UnsafeFacts.method(token, "each", UnsafeFacts.<Set<Me>>interfaceToken(gen.facts.java.util.Set_.Data.SHAPE, TokenArg.exact(me)), UnsafeFacts.param(me, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(me));
    }
}
