package gen.facts.java.io;

import gen.facts.java.io.FileNotFoundException_.Canonical;
import gen.facts.java.io.FileNotFoundException_.Data;
import java.io.FileNotFoundException;
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

/// The token-only metamodel of [FileNotFoundException]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [FileNotFoundException]: it is only mentioned in the signatures of [p.Nested] and [p.NotFoundB]. For the facts of its members add `FileNotFoundException.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = FileNotFoundException.class, fingerprint = "35296b15332d58166171cc69eaa8f4c8041878a231b2cdba2559caeb85a7e2b4", complete = false, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class FileNotFoundException_ {
    /// The shape of [FileNotFoundException] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [FileNotFoundException] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.io.FileNotFoundException_"), "35296b15332d58166171cc69eaa8f4c8041878a231b2cdba2559caeb85a7e2b4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.io.FileNotFoundException"), List.of(), List.of(ClassDesc.of("java.io.IOException"), ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("FileNotFoundException"), Signature.of("FileNotFoundException", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [FileNotFoundException], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [FileNotFoundException].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.io.FileNotFoundException open-class sealed=no\ntparams -\nsuperclasses java.io.IOException; java.lang.Exception; java.lang.Throwable; java.lang.Object\ninterfaces java.io.Serializable\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); getCause(); getClass(); getLocalizedMessage(); getMessage(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setStackTrace(java.lang.StackTraceElement[]); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor FileNotFoundException(); FileNotFoundException(java.lang.String)\n";

        private Canonical() {
        }
    }

    /// The token of [FileNotFoundException].
    public static final OpenClassToken<FileNotFoundException> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private FileNotFoundException_() {
    }
}
