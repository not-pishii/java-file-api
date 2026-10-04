package gen.facts.java.lang;

import gen.facts.java.lang.IllegalStateException_.Canonical;
import gen.facts.java.lang.IllegalStateException_.Data;
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

/// The token-only metamodel of [IllegalStateException]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [IllegalStateException]: it is only mentioned in the signatures of [p.Io]. For the facts of its members add `IllegalStateException.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = IllegalStateException.class, fingerprint = "ae7d190a6ec940de18fe5a6c309d61c4f80c403e3982de001b0f5ec512397e43", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class IllegalStateException_ {
    /// The shape of [IllegalStateException] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [IllegalStateException] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.IllegalStateException_"), "ae7d190a6ec940de18fe5a6c309d61c4f80c403e3982de001b0f5ec512397e43", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.lang.IllegalStateException"), List.of(), List.of(ClassDesc.of("java.lang.RuntimeException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("IllegalStateException"), Signature.of("IllegalStateException", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("IllegalStateException", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("IllegalStateException", Param.fixed(ClassDesc.of("java.lang.Throwable"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [IllegalStateException], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [IllegalStateException].
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.IllegalStateException open-class sealed=no\ntparams -\nsuperclasses java.lang.RuntimeException; java.lang.Exception; java.lang.Throwable; java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); getCause(); getClass(); getLocalizedMessage(); getMessage(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setCause(java.lang.Throwable); setStackTrace(java.lang.StackTraceElement[]); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor IllegalStateException(); IllegalStateException(java.lang.String); IllegalStateException(java.lang.String, java.lang.Throwable); IllegalStateException(java.lang.Throwable)\n";

        private Canonical() {
        }
    }

    /// The token of [IllegalStateException].
    public static final OpenClassToken<IllegalStateException> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private IllegalStateException_() {
    }
}
