package gen.facts.p;

import gen.facts.p.Coll_.Canonical;
import gen.facts.p.Coll_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.PrimitiveToken;
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
import p.Coll;

/// The full metamodel of [Coll]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Coll]: it is here as a supertype of [p.Seq], whose inherited members are called through this metamodel.
///
/// A member [Coll] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// @param <E> a type argument of [Coll]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Coll.class, fingerprint = "943f6b8afad132eb7f78431832b8fe67ed92e8a43acd06177b155d326bb93102", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Coll_<E> {
    /// The shape of [Coll] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Coll] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Coll_"), "943f6b8afad132eb7f78431832b8fe67ed92e8a43acd06177b155d326bb93102", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Coll"), List.of(new TypeParam("E", List.of())), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("E")), List.of()), new MethodTableTemplate(Set.of(Signature.of("add", Param.var(0))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Coll], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Coll].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Coll interface sealed=no
        tparams #0
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract add(#0) -> boolean throws -
        sam add(#0) -> boolean throws -
        table abstract add(#0)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Coll] with a wildcard for every type argument.
    public static final InterfaceToken<Coll<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Coll] with the type arguments of this metamodel.
    public final InterfaceToken<Coll<E>> token;

    /// The fact of [Coll#add(Object)].
    public final MethodRef1<Coll<E>, Bool, E> add_E;

    /// The fact of the single abstract method [Coll#add(Object)], which a lambda implements.
    public final Sam1<Coll<E>, Bool, E> sam;

    /// The metamodel of [Coll] with the type arguments the tokens give.
    ///
    /// @param e the token of the type argument `E`
    public Coll_(RefToken<E> e) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(e));
        this.add_E = UnsafeFacts.method(token, "add", PrimitiveToken.BOOLEAN, UnsafeFacts.param(e, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(add_E);
    }
}
