package gen.facts.p;

import gen.facts.p.ProtFinal_.Canonical;
import gen.facts.p.ProtFinal_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.ProtFinal;

/// The full metamodel of [ProtFinal], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [ProtFinal] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// The `protected` members have no facts: the type cannot be extended, and only a subclass reaches them.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ProtFinal.class, fingerprint = "8d46fbe84cfa3368c8929f6d2dae23e49dca81ef3ee5631b76e0a2b0f0a143f4", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class ProtFinal_ {
    /// The shape of [ProtFinal] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [ProtFinal] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.ProtFinal_"), "8d46fbe84cfa3368c8929f6d2dae23e49dca81ef3ee5631b76e0a2b0f0a143f4", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.ProtFinal"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("method"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("ProtFinal"), Signature.of("ProtFinal", Param.fixed(ConstantDescs.CD_int)))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [ProtFinal], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [ProtFinal].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.ProtFinal final-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); method(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor ProtFinal(); ProtFinal(int)
        """;

        private Canonical() {
        }
    }

    /// The token of [ProtFinal].
    public static final FinalClassToken<ProtFinal> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [ProtFinal#ProtFinal()].
    public static final CtorRef0<ProtFinal> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private ProtFinal_() {
    }
}
