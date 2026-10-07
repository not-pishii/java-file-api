package gen.facts.p;

import gen.facts.p.RawBound_.Canonical;
import gen.facts.p.RawBound_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
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
import p.RawBound;

/// The full metamodel of [RawBound], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [RawBound] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [RawBound]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = RawBound.class, fingerprint = "82667fe895595f914a014731ff27e6c92ce4da840539006cb930b0e0d710684e", complete = true, format = 8)
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class RawBound_<T extends Comparable> {
    /// The shape of [RawBound] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [RawBound] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.RawBound_"), "82667fe895595f914a014731ff27e6c92ce4da840539006cb930b0e0d710684e", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.RawBound"), List.of(new TypeParam("T", List.of(Types.of(ClassDesc.of("java.lang.Comparable"))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("get"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("RawBound"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [RawBound], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [RawBound].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.RawBound open-class sealed=no
        tparams #0 extends java.lang.Comparable
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable get() -> #0 throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); get(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor RawBound()
        """;

        private Canonical() {
        }
    }

    /// The token of [RawBound] with a wildcard for every type argument.
    public static final OpenClassToken<RawBound<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [RawBound] with the type arguments of this metamodel.
    public final OpenClassToken<RawBound<T>> token;

    /// The fact of [RawBound#RawBound()].
    public final CtorRef0<RawBound<T>> new_;

    /// The fact of [RawBound#get()].
    public final MethodRef0<RawBound<T>, T> get;

    /// The metamodel of [RawBound] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public RawBound_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.get = UnsafeFacts.method(token, "get", t, MemberTraits.OVERRIDABLE);
    }
}
