package gen.facts.p;

import gen.facts.p.B_.Canonical;
import gen.facts.p.B_.Data;
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
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.A;
import p.B;

/// The full metamodel of [B], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [B] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = B.class, fingerprint = "c93abdaeff327b4fa3e2e961eb4aef0a9b1ea81b15e95bbc62886062933254d4", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class B_ {
    /// The shape of [B] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [B] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.B_"), "c93abdaeff327b4fa3e2e961eb4aef0a9b1ea81b15e95bbc62886062933254d4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.B"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("a"), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("B"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [B], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [B].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.B open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member method overridable a() -> p.A throws -
        table abstract -
        table concrete a(); clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor B()
        """;

        private Canonical() {
        }
    }

    /// The token of [B].
    public static final OpenClassToken<B> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [B#B()].
    public static final CtorRef0<B> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [B#a()].
    public static final MethodRef0<B, A> a = UnsafeFacts.method(TOKEN, "a", UnsafeFacts.<A>openClassToken(A_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private B_() {
    }
}
