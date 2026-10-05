package gen.facts.p;

import gen.facts.p.Fail_.Canonical;
import gen.facts.p.Fail_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
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
import p.Fail;

/// The full metamodel of [Fail], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fail] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <X> a type argument of [Fail]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fail.class, fingerprint = "88ab282e6defd1b2c25b9ad724445ce417adde35a68cc2a2d55c2beb27004936", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Fail_<X extends Exception> {
    /// The shape of [Fail] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Fail] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fail_"), "88ab282e6defd1b2c25b9ad724445ce417adde35a68cc2a2d55c2beb27004936", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Fail"), List.of(new TypeParam("X", List.of(Types.of(ClassDesc.of("java.lang.Exception"))))), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("X")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Fail"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Fail], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Fail].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Fail open-class sealed=no\ntparams #0 extends java.lang.Exception\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable run() -> void throws #0\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); run(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Fail()\n";

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
}
