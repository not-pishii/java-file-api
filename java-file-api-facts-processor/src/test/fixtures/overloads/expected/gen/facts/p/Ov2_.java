package gen.facts.p;

import gen.facts.p.Ov2_.Canonical;
import gen.facts.p.Ov2_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
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
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Ov2;

/// The full metamodel of [Ov2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Ov2] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ov2.class, fingerprint = "feb50944fd3171331513956154948a24685f2ad7747f8f012a1b65f36f25d9a0", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Ov2_ {
    /// The shape of [Ov2] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Ov2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ov2_"), "feb50944fd3171331513956154948a24685f2ad7747f8f012a1b65f36f25d9a0", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ov2"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("wrap", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("wrap", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("Ov2"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Ov2], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Ov2].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Ov2 open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public static <^0> wrap(^0) -> java.util.Optional<^0> throws -
        member method public static wrap(java.lang.String) -> java.lang.StringBuilder throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static wrap(java.lang.Object); wrap(java.lang.String)
        table ctor Ov2()
        """;

        private Canonical() {
        }
    }

    /// The token of [Ov2].
    public static final OpenClassToken<Ov2> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Ov2#Ov2()].
    public static final CtorRef0<Ov2> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Ov2#wrap(String)].
    public static final StaticMethodRef1<StringBuilder, String> wrap_String = UnsafeFacts.staticMethod(TOKEN, "wrap", UnsafeFacts.<StringBuilder>finalClassToken(gen.facts.java.lang.StringBuilder_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Ov2_() {
    }

    /// The fact of [Ov2#wrap(Object)], for the type arguments the tokens give.
    ///
    /// @param <T> a type argument of the method
    /// @param t the token of the type argument `T`
    /// @return the fact
    public static <T> StaticMethodRef1<Optional<T>, T> wrap_T(RefToken<T> t) {
        return UnsafeFacts.staticMethod(TOKEN, "wrap", UnsafeFacts.<Optional<T>>finalClassToken(gen.facts.java.util.Optional_.Data.SHAPE, TokenArg.exact(t)), UnsafeFacts.param(t, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.FINAL.withTypeArgs(t));
    }
}
