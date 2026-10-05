package gen.facts.p;

import gen.facts.p.Mentions_.Canonical;
import gen.facts.p.Mentions_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.ByDollar;
import p.ByHidden;
import p.ByMarker;
import p.Mentions;

/// The full metamodel of [Mentions], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Mentions] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Mentions.class, fingerprint = "50dfdf7e14bf31485a9368a42c6b7c2ea07a196f6b838daf88316b92789c7fa5", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Mentions_ {
    /// The shape of [Mentions] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Mentions] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Mentions_"), "50dfdf7e14bf31485a9368a42c6b7c2ea07a196f6b838daf88316b92789c7fa5", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Mentions"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("dollar"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("marker"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Mentions"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Mentions], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Mentions].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Mentions open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member method overridable dollar() -> p.ByDollar<?> throws -
        member method overridable hidden() -> p.ByHidden<?> throws -
        member method overridable marker() -> p.ByMarker<?> throws -
        table abstract -
        table concrete clone(); dollar(); equals(java.lang.Object); finalize(); getClass(); hashCode(); hidden(); marker(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Mentions()
        """;

        private Canonical() {
        }
    }

    /// The token of [Mentions].
    public static final OpenClassToken<Mentions> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Mentions#Mentions()].
    public static final CtorRef0<Mentions> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Mentions#dollar()].
    public static final MethodRef0<Mentions, ByDollar<?>> dollar = UnsafeFacts.method(TOKEN, "dollar", UnsafeFacts.<ByDollar<?>>openClassToken(ByDollar_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    /// The fact of [Mentions#hidden()].
    public static final MethodRef0<Mentions, ByHidden<?>> hidden = UnsafeFacts.method(TOKEN, "hidden", UnsafeFacts.<ByHidden<?>>openClassToken(ByHidden_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    /// The fact of [Mentions#marker()].
    public static final MethodRef0<Mentions, ByMarker<?>> marker = UnsafeFacts.method(TOKEN, "marker", UnsafeFacts.<ByMarker<?>>openClassToken(ByMarker_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.OVERRIDABLE);

    private Mentions_() {
    }
}
