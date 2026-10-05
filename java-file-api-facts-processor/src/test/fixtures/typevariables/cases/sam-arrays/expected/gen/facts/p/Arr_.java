package gen.facts.p;

import gen.facts.p.Arr_.Canonical;
import gen.facts.p.Arr_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
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
import p.Arr;

/// The full metamodel of [Arr], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Arr] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <T> a type argument of [Arr]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Arr.class, fingerprint = "1f4cd0dd8690e92db529d9c26c0166c02add4f7974c88408c388789985f31c2e", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Arr_<T> {
    /// The shape of [Arr] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Arr] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Arr_"), "1f4cd0dd8690e92db529d9c26c0166c02add4f7974c88408c388789985f31c2e", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Arr"), List.of(new TypeParam("T", List.of())), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(Signature.of("m", Param.var(0, 1))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Arr], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Arr].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Arr interface sealed=no
        tparams #0
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-public
        member method abstract m(#0[]) -> #0[] throws -
        sam m(#0[]) -> #0[] throws -
        table abstract m(#0[])
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Arr] with a wildcard for every type argument.
    public static final InterfaceToken<Arr<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Arr] with the type arguments of this metamodel.
    public final InterfaceToken<Arr<T>> token;

    /// The fact of [Arr#m(Object\[\])].
    public final MethodRef1<Arr<T>, T[], T[]> m_TArray;

    /// The fact of the single abstract method [Arr#m(Object\[\])], which a lambda implements.
    public final Sam1<Arr<T>, T[], T[]> sam;

    /// The metamodel of [Arr] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Arr_(RefToken<T> t) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(t));
        this.m_TArray = UnsafeFacts.method(token, "m", ArrayToken.of(t), UnsafeFacts.param(ArrayToken.of(t), Param.var(0, 1)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(m_TArray);
    }
}
