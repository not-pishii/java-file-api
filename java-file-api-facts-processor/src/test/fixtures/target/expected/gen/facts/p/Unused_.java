package gen.facts.p;

import gen.facts.p.Unused_.Canonical;
import gen.facts.p.Unused_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Unused;

/// The full metamodel of [Unused], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Unused] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Unused.class, fingerprint = "64b2523e5da72074586b6b42ca8ca6fe8181d04bc23b1da4e62e360cefe55530", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Unused_ {
    /// The shape of [Unused] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Unused] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Unused_"), "64b2523e5da72074586b6b42ca8ca6fe8181d04bc23b1da4e62e360cefe55530", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Unused"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("gone"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Unused"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Unused], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Unused].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Unused open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member method overridable gone() -> void throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); gone(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Unused()
        """;

        private Canonical() {
        }
    }

    /// The token of [Unused].
    public static final OpenClassToken<Unused> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Unused#Unused()].
    public static final CtorRef0<Unused> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Unused#gone()].
    public static final VoidMethodRef0<Unused> gone = UnsafeFacts.voidMethod(TOKEN, "gone", MemberTraits.OVERRIDABLE);

    private Unused_() {
    }
}
