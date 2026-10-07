package gen.facts.p;

import gen.facts.p.Fail_.Canonical;
import gen.facts.p.Fail_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Fail;

/// The full metamodel of [Fail], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fail] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Fail] by the proof its metamodel starts, with the stages of `Fail_Heritage`.
///
/// @param <X> a type argument of [Fail]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fail.class, fingerprint = "556492e061931e60f8ce32e6ad2fd0687184bbd9a6bbe78f6ead90c127e66a59", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Fail_<X extends Exception> {
    /// The shape of [Fail] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Fail] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fail_"), "556492e061931e60f8ce32e6ad2fd0687184bbd9a6bbe78f6ead90c127e66a59", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Fail"), List.of(new TypeParam("X", List.of(Types.of(ClassDesc.of("java.lang.Exception"))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("X")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Fail"))), false, new Fail_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Fail], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Fail].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Fail open-class sealed=no
        tparams #0 extends java.lang.Exception
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable run() -> void throws #0
        inherit ctor public () throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Fail run() -> void throws #0 erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Fail()
        """;

        private Canonical() {
        }
    }

    /// The token of [Fail] with a wildcard for every type argument.
    public static final OpenClassToken<Fail<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Fail] with the type arguments of this metamodel.
    public final OpenClassToken<Fail<X>> token;

    /// The fact of [Fail#Fail()].
    public final CtorRef0<Fail<X>> new_;

    /// The fact of [Fail#run()].
    public final VoidMethodRef0<Fail<X>> run;

    /// The metamodel of [Fail] with the type arguments the tokens give.
    ///
    /// @param x the token of the type argument `X`
    public Fail_(RefToken<X> x) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(x));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.run = UnsafeFacts.voidMethod(token, "run", MemberTraits.OVERRIDABLE.throwing(x));
    }

    /// Starts the proof that a class being declared extends [Fail]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Complete<Self, Fail<X>> extend(RefToken<Self> self) {
        return new Fail_Heritage().extend(this.token, self);
    }
}
