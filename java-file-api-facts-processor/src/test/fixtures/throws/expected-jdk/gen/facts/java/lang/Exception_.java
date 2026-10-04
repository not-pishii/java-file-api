package gen.facts.java.lang;

import gen.facts.java.lang.Exception_.Canonical;
import gen.facts.java.lang.Exception_.Data;
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

/// The token-only metamodel of [Exception]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Exception]: it is only mentioned in the signatures of [p.Io]. For the facts of its members add `Exception.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Exception.class, fingerprint = "550f525e054984b148384634324464d00f273fde107c018209fa57bb72d02dcf", complete = false, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Exception_ {
    /// The shape of [Exception] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Exception] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Exception_"), "550f525e054984b148384634324464d00f273fde107c018209fa57bb72d02dcf", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.lang.Exception"), List.of(), List.of(ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Exception"), Signature.of("Exception", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Exception", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("Exception", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable")), Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("Exception", Param.fixed(ClassDesc.of("java.lang.Throwable"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Exception], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Exception].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.lang.Exception open-class sealed=no\ntparams -\nsuperclasses java.lang.Throwable; java.lang.Object\ninterfaces java.io.Serializable\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); getCause(); getClass(); getLocalizedMessage(); getMessage(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setCause(java.lang.Throwable); setStackTrace(java.lang.StackTraceElement[]); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Exception(); Exception(java.lang.String); Exception(java.lang.String, java.lang.Throwable); Exception(java.lang.String, java.lang.Throwable, boolean, boolean); Exception(java.lang.Throwable)\n";

        private Canonical() {
        }
    }

    /// The token of [Exception].
    public static final OpenClassToken<Exception> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Exception_() {
    }
}
