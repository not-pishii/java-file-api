package gen.facts.p;

import gen.facts.p.Plan_.Canonical;
import gen.facts.p.Plan_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Mode;
import p.Plan;

/// The full metamodel of [Plan], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Plan] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Plan.class, fingerprint = "c15efd85d54d1ecb8e703d360b71b5f49a16fcd22e570a4c3b31ae3287f4a0bb", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Plan_ {
    /// The shape of [Plan] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Plan] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Plan_"), "c15efd85d54d1ecb8e703d360b71b5f49a16fcd22e570a4c3b31ae3287f4a0bb", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Plan"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("mode", Param.fixed(ConstantDescs.CD_boolean))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Plan], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Plan].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Plan final-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public static mode(boolean) -> p.Mode throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static mode(boolean)
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Plan].
    public static final FinalClassToken<Plan> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [Plan#mode(boolean)].
    public static final StaticMethodRef1<Mode, Bool> mode_boolean = UnsafeFacts.staticMethod(TOKEN, "mode", UnsafeFacts.<Mode>enumToken(Mode_.Data.SHAPE), PrimitiveToken.BOOLEAN, MemberTraits.FINAL);

    private Plan_() {
    }
}
