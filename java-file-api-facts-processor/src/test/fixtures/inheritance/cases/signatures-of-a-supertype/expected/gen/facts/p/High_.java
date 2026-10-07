package gen.facts.p;

import gen.facts.p.High_.Canonical;
import gen.facts.p.High_.Data;
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
import p.High;
import p.Seen;

/// The full metamodel of [High]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [High]: it is here as a supertype of [p.Low], whose inherited members are called through this metamodel.
///
/// A member [High] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = High.class, fingerprint = "c719dfdee43b8d89cbe30e1d6f3e3a2efc7fb29b0e3a24bcd886b7d5751b960a", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class High_ {
    /// The shape of [High] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [High] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.High_"), "c719dfdee43b8d89cbe30e1d6f3e3a2efc7fb29b0e3a24bcd886b7d5751b960a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.High"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("seen"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("High"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [High], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [High].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.High open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable seen() -> p.Seen throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); seen(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor High()
        """;

        private Canonical() {
        }
    }

    /// The token of [High].
    public static final OpenClassToken<High> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [High#High()].
    public static final CtorRef0<High> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [High#seen()].
    public static final MethodRef0<High, Seen> seen = UnsafeFacts.method(TOKEN, "seen", UnsafeFacts.<Seen>openClassToken(Seen_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private High_() {
    }
}
