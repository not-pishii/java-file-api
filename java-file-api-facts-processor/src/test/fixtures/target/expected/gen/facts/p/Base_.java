package gen.facts.p;

import gen.facts.p.Base_.Canonical;
import gen.facts.p.Base_.Data;
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
import p.Base;

/// The full metamodel of [Base]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Base]: it is here as a supertype of [p.Svc], whose inherited members are called through this metamodel.
///
/// A member [Base] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Base.class, fingerprint = "1d9dd915648b69c5738abcc92c79a2bb92dfa5ae7353691891cc194ceb887e23", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Base_ {
    /// The shape of [Base] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Base] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Base_"), "1d9dd915648b69c5738abcc92c79a2bb92dfa5ae7353691891cc194ceb887e23", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Base"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("inherited"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Base"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Base], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Base].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Base open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable inherited() -> java.lang.String throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); inherited(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Base()
        """;

        private Canonical() {
        }
    }

    /// The token of [Base].
    public static final OpenClassToken<Base> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Base#Base()].
    public static final CtorRef0<Base> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Base#inherited()].
    public static final MethodRef0<Base, String> inherited = UnsafeFacts.method(TOKEN, "inherited", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Base_() {
    }
}
