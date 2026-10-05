package gen.facts.p;

import gen.facts.p.Api_.Canonical;
import gen.facts.p.Api_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Api;
import p.Raw;

/// The full metamodel of [Api], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Api] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Api.class, fingerprint = "4cd47e00310d6bd7cae81b9484bb48c072ea8c05592b9ea96a667b972ea06ae0", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Api_ {
    /// The shape of [Api] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Api] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Api_"), "4cd47e00310d6bd7cae81b9484bb48c072ea8c05592b9ea96a667b972ea06ae0", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Api"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("raw")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Api], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Api].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Api interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-public
        member method abstract raw() -> p.Raw<?> throws -
        sam raw() -> p.Raw<?> throws -
        table abstract raw()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Api].
    public static final InterfaceToken<Api> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Api#raw()].
    public static final MethodRef0<Api, Raw<?>> raw = UnsafeFacts.method(TOKEN, "raw", UnsafeFacts.<Raw<?>>openClassToken(Raw_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Api#raw()], which a lambda implements.
    public static final Sam0<Api, Raw<?>> sam = UnsafeFacts.sam(raw);

    private Api_() {
    }
}
