package gen.facts.java.lang;

import gen.facts.java.lang.IllegalArgumentException_.Canonical;
import gen.facts.java.lang.IllegalArgumentException_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
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

/// The token-only metamodel of [IllegalArgumentException]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [IllegalArgumentException]: it is only mentioned in the signatures of [p.Io]. For the facts of its members add `IllegalArgumentException.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = IllegalArgumentException.class, fingerprint = "4a90957d0bad279881403da9f43a4d967923a7b75fe4f4981810983253e448d7", complete = false, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class IllegalArgumentException_ {
    /// The shape of [IllegalArgumentException] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [IllegalArgumentException] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.IllegalArgumentException_"), "4a90957d0bad279881403da9f43a4d967923a7b75fe4f4981810983253e448d7", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.lang.IllegalArgumentException"), List.of(), List.of(ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("IllegalArgumentException"), Signature.of("IllegalArgumentException", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("IllegalArgumentException", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("IllegalArgumentException", Param.fixed(ClassDesc.of("java.lang.Throwable"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [IllegalArgumentException], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [IllegalArgumentException].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.IllegalArgumentException open-class sealed=no
        tparams -
        superclasses java.lang.RuntimeException; java.lang.Exception; java.lang.Throwable; java.lang.Object
        interfaces java.io.Serializable
        supertypes -
        enum -
        members none
        table abstract -
        table concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); getCause(); getClass(); getLocalizedMessage(); getMessage(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setCause(java.lang.Throwable); setStackTrace(java.lang.StackTraceElement[]); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor IllegalArgumentException(); IllegalArgumentException(java.lang.String); IllegalArgumentException(java.lang.String, java.lang.Throwable); IllegalArgumentException(java.lang.Throwable)
        """;

        private Canonical() {
        }
    }

    /// The token of [IllegalArgumentException].
    public static final OpenClassToken<IllegalArgumentException> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private IllegalArgumentException_() {
    }
}
