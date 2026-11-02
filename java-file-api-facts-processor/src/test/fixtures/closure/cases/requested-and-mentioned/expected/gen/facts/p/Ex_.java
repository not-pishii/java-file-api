package gen.facts.p;

import gen.facts.p.Ex_.Canonical;
import gen.facts.p.Ex_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Ex;

/// The token-only metamodel of [Ex]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Ex]: it is only mentioned in the signatures of [p.A]. For the facts of its members add `Ex.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Ex.class, fingerprint = "84aa8b2e49ad433773a4d8353434b5f4f3919e023746f7e81ca21f30b7f8d135", complete = false, format = 20)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Ex_ {
    /// The shape of [Ex] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Ex] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Ex_"), "84aa8b2e49ad433773a4d8353434b5f4f3919e023746f7e81ca21f30b7f8d135", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Ex"), List.of(), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Ex")), Set.of(), Map.ofEntries(Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("finalize"), List.of(new Thrown(ClassDesc.of("java.lang.Throwable"), List.of(ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Ex], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Ex].
        static final String TEXT = """
        javafile-facts-canonical 13
        type p.Ex open-class sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses java.lang.Exception; java.lang.Throwable; java.lang.Object
        interfaces java.io.Serializable
        supertypes -
        enum -
        record -
        members none
        table abstract -
        table concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); getCause(); getClass(); getLocalizedMessage(); getMessage(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setStackTrace(java.lang.StackTraceElement[]); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Ex()
        """;

        private Canonical() {
        }
    }

    /// The token of [Ex].
    public static final OpenClassToken<Ex> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Ex_() {
    }
}
