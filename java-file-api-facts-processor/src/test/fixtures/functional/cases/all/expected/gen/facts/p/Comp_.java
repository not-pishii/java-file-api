package gen.facts.p;

import gen.facts.p.Comp_.Canonical;
import gen.facts.p.Comp_.Data;
import gen.facts.p.Comp_.Data.Inherited;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Comp;

/// The full metamodel of [Comp], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Comp] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Comp.class, fingerprint = "8a827708b845a541b86b2169e2351e07be3f0d8f63be4f3b44c60c66704d3324", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Comp_ {
    /// The shape of [Comp] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Comp] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Comp_"), "8a827708b845a541b86b2169e2351e07be3f0d8f63be4f3b44c60c66704d3324", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Comp"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Comp")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Comp] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Comp] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Comp"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Comp"))), List.of(), List.of(Types.of(ClassDesc.of("p.Comp"))), Arity.FIXED, new Of(PrimitiveTypeRef.INT), List.of(), Set.of(List.of(ClassDesc.of("p.Comp"))), List.of());
            }
        }
    }

    /// The canonical form of [Comp], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Comp].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Comp interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract compareTo(p.Comp) -> int throws -
        member method public abstract hashCode() -> int throws -
        member method public abstract toString() -> java.lang.String throws -
        sam compareTo(p.Comp) -> int throws -
        inherit method public abstract p.Comp compareTo(p.Comp) -> int throws - erased (p.Comp) overrides -
        table abstract compareTo(p.Comp)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Comp].
    public static final InterfaceToken<Comp> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Comp#compareTo(Comp)].
    public static final MethodRef1<Comp, Int, Comp> compareTo_Comp = UnsafeFacts.method(TOKEN, "compareTo", PrimitiveToken.INT, TOKEN, MemberTraits.ABSTRACT);

    /// The fact of [Comp#hashCode()].
    public static final MethodRef0<Comp, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    /// The fact of [Comp#toString()].
    public static final MethodRef0<Comp, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Comp#compareTo(Comp)], which a lambda implements.
    public static final Sam1<Comp, Int, Comp> sam = UnsafeFacts.sam(compareTo_Comp);

    private Comp_() {
    }
}
