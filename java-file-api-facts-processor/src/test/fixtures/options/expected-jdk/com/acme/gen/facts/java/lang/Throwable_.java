package com.acme.gen.facts.java.lang;

import com.acme.gen.facts.java.lang.Throwable_.Canonical;
import com.acme.gen.facts.java.lang.Throwable_.Data;
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

/// The token-only metamodel of [Throwable]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Throwable]: it is only mentioned in the signatures of [Object]. For the facts of its members add `Throwable.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Throwable.class, fingerprint = "07439e55f4c5772c0e002e90a6946e5c91b98d6029ee5f40ce47b0854adb7fbf", complete = false, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Throwable_ {
    /// The shape of [Throwable] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Throwable] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("com.acme.gen.facts.java.lang.Throwable_"), "07439e55f4c5772c0e002e90a6946e5c91b98d6029ee5f40ce47b0854adb7fbf", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.lang.Throwable"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Throwable"), Signature.of("Throwable", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Throwable", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("Throwable", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Throwable")), Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("Throwable", Param.fixed(ClassDesc.of("java.lang.Throwable"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Throwable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Throwable].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.Throwable open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces java.io.Serializable
        supertypes -
        enum -
        members none
        table abstract -
        table concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); getCause(); getClass(); getLocalizedMessage(); getMessage(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setCause(java.lang.Throwable); setStackTrace(java.lang.StackTraceElement[]); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Throwable(); Throwable(java.lang.String); Throwable(java.lang.String, java.lang.Throwable); Throwable(java.lang.String, java.lang.Throwable, boolean, boolean); Throwable(java.lang.Throwable)
        """;

        private Canonical() {
        }
    }

    /// The token of [Throwable].
    public static final OpenClassToken<Throwable> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private Throwable_() {
    }
}
