package gen.facts.java.sql;

import gen.facts.java.sql.SQLException_.Canonical;
import gen.facts.java.sql.SQLException_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.sql.SQLException;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [SQLException]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [SQLException]: it is only mentioned in the signatures of [p.SqlB]. For the facts of its members add `SQLException.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = SQLException.class, fingerprint = "06b8d8b9948ee4d21fcec7ef121a5774b5e16c90f2a6d320f8e10f55d7d3f292", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class SQLException_ {
    /// The shape of [SQLException] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [SQLException] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.sql.SQLException_"), "06b8d8b9948ee4d21fcec7ef121a5774b5e16c90f2a6d320f8e10f55d7d3f292", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.sql.SQLException"), List.of(), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Iterable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Throwable"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("forEach", Param.fixed(ClassDesc.of("java.util.function.Consumer"))), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getErrorCode"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getNextException"), Signature.of("getSQLState"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("iterator"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setNextException", Param.fixed(ClassDesc.of("java.sql.SQLException"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("spliterator"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("SQLException"), Signature.of("SQLException", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("SQLException", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("SQLException", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("SQLException", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("SQLException", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("SQLException", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("SQLException", Param.fixed(ClassDesc.of("java.lang.Throwable"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [SQLException], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [SQLException].
        static final String TEXT = "javafile-facts-canonical 5\ntype java.sql.SQLException open-class sealed=no\ntparams -\nsuperclasses java.lang.Exception; java.lang.Throwable; java.lang.Object\ninterfaces java.io.Serializable; java.lang.Iterable\nsupertypes java.lang.Iterable<java.lang.Throwable>\nenum -\nmembers none\ntable abstract -\ntable concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); forEach(java.util.function.Consumer); getCause(); getClass(); getErrorCode(); getLocalizedMessage(); getMessage(); getNextException(); getSQLState(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); iterator(); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setNextException(java.sql.SQLException); setStackTrace(java.lang.StackTraceElement[]); spliterator(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor SQLException(); SQLException(java.lang.String); SQLException(java.lang.String, java.lang.String); SQLException(java.lang.String, java.lang.String, int); SQLException(java.lang.String, java.lang.String, int, java.lang.Throwable); SQLException(java.lang.String, java.lang.String, java.lang.Throwable); SQLException(java.lang.String, java.lang.Throwable); SQLException(java.lang.Throwable)\n";

        private Canonical() {
        }
    }

    /// The token of [SQLException].
    public static final OpenClassToken<SQLException> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private SQLException_() {
    }
}
