package gen.facts.p;

import gen.facts.p.SealedSub_.Canonical;
import gen.facts.p.SealedSub_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
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
import p.SealedSub;

/// The full metamodel of [SealedSub], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [SealedSub] inherits has its fact in the metamodel of the supertype that declares it: [Run_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = SealedSub.class, fingerprint = "52775fd1f668736acc3626990ce929ff6170674feabfd70eb9d36f54404e4a8c", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class SealedSub_ {
    /// The shape of [SealedSub] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [SealedSub] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.SealedSub_"), "52775fd1f668736acc3626990ce929ff6170674feabfd70eb9d36f54404e4a8c", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.SealedSub"), List.of(), List.of(), List.of(ClassDesc.of("p.Run")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), true);

        private Data() {
        }
    }

    /// The canonical form of [SealedSub], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [SealedSub].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.SealedSub interface sealed=yes
        tparams -
        superclasses -
        interfaces p.Run
        supertypes -
        enum -
        members declared-accessible
        table abstract run()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [SealedSub].
    public static final InterfaceToken<SealedSub> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private SealedSub_() {
    }
}
