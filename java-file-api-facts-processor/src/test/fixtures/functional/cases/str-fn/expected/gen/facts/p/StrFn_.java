package gen.facts.p;

import gen.facts.p.StrFn_.Canonical;
import gen.facts.p.StrFn_.Data;
import gen.facts.p.StrFn_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.StrFn;

/// The full metamodel of [StrFn], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [StrFn] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.util.function.Function_].
///
/// A generated class extends or implements [StrFn] by the proof its metamodel starts, with the stages of `StrFn_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = StrFn.class, fingerprint = "a0a569bc8e38c060113e87fdcf4bac7129fdace93606b4a7b21cc97c03557740", complete = true, format = 14)
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
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.StrFn_"), "a0a569bc8e38c060113e87fdcf4bac7129fdace93606b4a7b21cc97c03557740", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.StrFn"), List.of(), List.of(), List.of(ClassDesc.of("java.util.function.Function")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.String"))), Types.exact(Types.of(ClassDesc.of("java.lang.String"))))))), new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("andThen", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("compose", Param.fixed(ClassDesc.of("java.util.function.Function"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new StrFn_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [StrFn], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [StrFn].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.StrFn interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces java.util.function.Function
        supertypes java.util.function.Function<java.lang.String, java.lang.String>
        enum -
        members declared-accessible
        sam apply(java.lang.String) -> java.lang.String throws -
        inherit method andThen(java.util.function.Function) erased (java.util.function.Function) overrides - by interface default java.util.function.Function <^0> (java.util.function.Function<? super java.lang.String, ? extends ^0>) -> java.util.function.Function<java.lang.String, ^0> throws -
        inherit method apply(java.lang.String) erased (java.lang.Object) overrides - by interface abstract java.util.function.Function (java.lang.String) -> java.lang.String throws -
        inherit method compose(java.util.function.Function) erased (java.util.function.Function) overrides - by interface default java.util.function.Function <^0> (java.util.function.Function<? super ^0, ? extends java.lang.String>) -> java.util.function.Function<^0, java.lang.String> throws -
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

    /// Starts the proof that a class being declared implements [StrFn]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new StrFn_Heritage().implement(TOKEN, self);
    }
}
