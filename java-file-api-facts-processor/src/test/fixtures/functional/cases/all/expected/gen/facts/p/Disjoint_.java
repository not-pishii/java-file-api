package gen.facts.p;

import gen.facts.p.Disjoint_.Canonical;
import gen.facts.p.Disjoint_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Disjoint;

/// The full metamodel of [Disjoint], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Disjoint] inherits has its fact in the metamodel of the supertype that declares it: [IoA_] and [SqlB_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Disjoint.class, fingerprint = "ecaf8ab6a958912aa7769c76f8dde071af6e1624469524053c44670a1d355fbb", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Disjoint_ {
    /// The shape of [Disjoint] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Disjoint] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Disjoint_"), "ecaf8ab6a958912aa7769c76f8dde071af6e1624469524053c44670a1d355fbb", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Disjoint"), List.of(), List.of(), List.of(ClassDesc.of("p.IoA"), ClassDesc.of("p.SqlB")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Disjoint], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Disjoint].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Disjoint interface sealed=no
        tparams -
        superclasses -
        interfaces p.IoA; p.SqlB
        supertypes -
        enum -
        members declared-accessible
        sam m() -> void throws -
        table abstract m()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Disjoint].
    public static final InterfaceToken<Disjoint> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of the single abstract method [p.IoA#m()], which a lambda implements.
    public static final VoidSam0<Disjoint> sam = UnsafeFacts.voidSam(UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT));

    private Disjoint_() {
    }
}
