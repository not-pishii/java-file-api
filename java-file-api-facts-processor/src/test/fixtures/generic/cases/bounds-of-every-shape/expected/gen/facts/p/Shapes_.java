package gen.facts.p;

import gen.facts.p.Shapes_.Canonical;
import gen.facts.p.Shapes_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Shapes;

/// The full metamodel of [Shapes], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Shapes] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// No generated class extends or implements [Shapes]: it is a final class, which no class extends.
///
/// @param <T> a type argument of [Shapes]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Shapes.class, fingerprint = "4aa3791766877f053147222f2f150b5201f136aa8463e9210c738bac090fbe5f", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Shapes_<T extends Comparable<? super T> & Function<? extends T[], int[]> & Iterable<?>> {
    /// The shape of [Shapes] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Shapes] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Shapes_"), "4aa3791766877f053147222f2f150b5201f136aa8463e9210c738bac090fbe5f", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Shapes"), List.of(new TypeParam("T", List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.superBound(Types.typeVar("T")))), new ParameterizedTypeRef(ClassDesc.of("java.util.function.Function"), List.of(Types.extendsBound(Types.array(Types.typeVar("T"))), Types.exact(Types.array(PrimitiveTypeRef.INT)))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Iterable"), List.of(Types.unbounded()))))), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Shapes"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Shapes], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Shapes].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Shapes final-class sealed=no nesting=top
        tparams #0 extends java.lang.Comparable<? super #0> & java.util.function.Function<? extends #0[], int[]> & java.lang.Iterable<?>
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Shapes()
        """;

        private Canonical() {
        }
    }

    /// The token of [Shapes] with a wildcard for every type argument.
    public static final FinalClassToken<Shapes<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Shapes] with the type arguments of this metamodel.
    public final FinalClassToken<Shapes<T>> token;

    /// The fact of [Shapes#Shapes()].
    public final CtorRef0<Shapes<T>> new_;

    /// The metamodel of [Shapes] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Shapes_(RefToken<T> t) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(t));
        this.new_ = UnsafeInvocables.ctor(token, MemberTraits.FINAL);
    }
}
