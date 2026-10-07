package gen.facts.p;

import gen.facts.p.Source_.Canonical;
import gen.facts.p.Source_.Data;
import gen.facts.p.Source_.Data.Inherited;
import java.io.IOException;
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
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;
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
import p.Source;

/// The full metamodel of [Source], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Source] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Source]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Source.class, fingerprint = "f561a33ccfab8ec9d30a2399d5478ea8663573a31463702256261114e2b3262f", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Source_<T> {
    /// The shape of [Source] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Source] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Source_"), "f561a33ccfab8ec9d30a2399d5478ea8663573a31463702256261114e2b3262f", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Source"), List.of(new TypeParam("T", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("next")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Source] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Source] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Source"), Signature.of("next"), List.of(), List.of(), Arity.FIXED, new Of(Types.typeVar("T")), List.of(Types.of(ClassDesc.of("java.io.IOException"))), Set.of(List.of()), List.of());
            }
        }
    }

    /// The canonical form of [Source], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Source].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Source interface sealed=no
        tparams #0
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract next() -> #0 throws java.io.IOException
        sam next() -> #0 throws java.io.IOException
        inherit method public abstract p.Source next() -> #0 throws java.io.IOException erased () overrides -
        table abstract next()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Source] with a wildcard for every type argument.
    public static final InterfaceToken<Source<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Source] with the type arguments of this metamodel.
    public final InterfaceToken<Source<T>> token;

    /// The fact of [Source#next()].
    public final MethodRef0<Source<T>, T> next;

    /// The fact of the single abstract method [Source#next()], which a lambda implements.
    public final Sam0<Source<T>, T> sam;

    /// The metamodel of [Source] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Source_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.next = UnsafeFacts.method(token, "next", t, MemberTraits.ABSTRACT.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));
        this.sam = UnsafeFacts.sam(next);
    }
}
