package gen.facts.p;

import gen.facts.p.Sealed_.Canonical;
import gen.facts.p.Sealed_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Sealed;

/// The full metamodel of [Sealed], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sealed] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sealed.class, fingerprint = "0a4906cba4c095858f85ec9d687c9a279c7545e16d228514518d20eeb1e59537", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Sealed_ {
    /// The shape of [Sealed] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sealed] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sealed_"), "0a4906cba4c095858f85ec9d687c9a279c7545e16d228514518d20eeb1e59537", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Sealed"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("run")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), true);

        private Data() {
        }
    }

    /// The canonical form of [Sealed], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sealed].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Sealed interface sealed=yes
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-public
        member method abstract run() -> void throws -
        table abstract run()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Sealed].
    public static final InterfaceToken<Sealed> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Sealed#run()].
    public static final VoidMethodRef0<Sealed> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    private Sealed_() {
    }
}
