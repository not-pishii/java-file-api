package gen.facts.p;

import gen.facts.p.StrFn_.Canonical;
import gen.facts.p.StrFn_.Data;
import gen.facts.p.StrFn_.Data.Inherited;
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
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.StrFn;

/// The full metamodel of [StrFn], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [StrFn] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.util.function.Function_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = StrFn.class, fingerprint = "2671fe7b5dc70b9f83d5eb74ca8fe83f6d327871c75c0691a7a7c346d4bba206", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class StrFn_ {
    /// The shape of [StrFn] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [StrFn] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.StrFn_"), "2671fe7b5dc70b9f83d5eb74ca8fe83f6d327871c75c0691a7a7c346d4bba206", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.StrFn"), List.of(), List.of(), List.of(ClassDesc.of("java.util.function.Function")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))), Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [StrFn] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [StrFn] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0(), m1(), m2()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.of(ClassDesc.of("java.lang.String"))), Types.extendsBound(Types.typeVar("V"))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))), Types.exact(Types.typeVar("V"))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
            }

            private static Method m1() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("java.util.function.Function"), Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.Object"))), List.of());
            }

            private static Method m2() {
                return new Method(Visibility.PUBLIC, Dispatch.DEFAULT, ClassDesc.of("java.util.function.Function"), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), List.of(new TypeParam("V", List.of())), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.superBound(Types.typeVar("V")), Types.extendsBound(Types.of(ClassDesc.of("java.lang.String")))))), Arity.FIXED, new Of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.typeVar("V")), Types.exact(Types.of(ClassDesc.of("java.lang.String")))))), List.of(), Set.of(List.of(ClassDesc.of("java.util.function.Function"))), List.of());
            }
        }
    }

    /// The canonical form of [StrFn], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [StrFn].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.StrFn interface sealed=no
        tparams -
        superclasses -
        interfaces java.util.function.Function
        supertypes java.util.function.Function<java.lang.String, java.lang.String>
        enum -
        members declared-accessible
        sam apply(java.lang.String) -> java.lang.String throws -
        inherit method public abstract java.util.function.Function apply(java.lang.String) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public default java.util.function.Function <^0> andThen(java.util.function.Function<? super java.lang.String, ? extends ^0>) -> java.util.function.Function<java.lang.String, ^0> throws - erased (java.util.function.Function) overrides -
        inherit method public default java.util.function.Function <^0> compose(java.util.function.Function<? super ^0, ? extends java.lang.String>) -> java.util.function.Function<^0, java.lang.String> throws - erased (java.util.function.Function) overrides -
        table abstract apply(java.lang.String)
        table concrete andThen(java.util.function.Function); compose(java.util.function.Function); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [StrFn].
    public static final InterfaceToken<StrFn> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of the single abstract method [java.util.function.Function#apply(Object)], which a lambda implements.
    public static final Sam1<StrFn, String, String> sam = UnsafeFacts.sam(UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT));

    private StrFn_() {
    }
}
