package com.acme.gen.facts.java.lang;

import com.acme.gen.facts.java.lang.CloneNotSupportedException_.Canonical;
import com.acme.gen.facts.java.lang.CloneNotSupportedException_.Data;
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

/// The token-only metamodel of [CloneNotSupportedException]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [CloneNotSupportedException]: it is only mentioned in the signatures of [Object]. For the facts of its members add `CloneNotSupportedException.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = CloneNotSupportedException.class, fingerprint = "d72a2bc1f03b941d0b0968076c08ad717e58d392270565d6868a62069fa5367f", complete = false, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class CloneNotSupportedException_ {
    /// The shape of [CloneNotSupportedException] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [CloneNotSupportedException] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("com.acme.gen.facts.java.lang.CloneNotSupportedException_"), "d72a2bc1f03b941d0b0968076c08ad717e58d392270565d6868a62069fa5367f", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("addSuppressed", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("fillInStackTrace"), Signature.of("finalize"), Signature.of("getCause"), Signature.of("getClass"), Signature.of("getLocalizedMessage"), Signature.of("getMessage"), Signature.of("getStackTrace"), Signature.of("getSuppressed"), Signature.of("hashCode"), Signature.of("initCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("printStackTrace"), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintStream"))), Signature.of("printStackTrace", Param.fixed(ClassDesc.of("java.io.PrintWriter"))), Signature.of("setCause", Param.fixed(ClassDesc.of("java.lang.Throwable"))), Signature.of("setStackTrace", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/StackTraceElement;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("CloneNotSupportedException"), Signature.of("CloneNotSupportedException", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [CloneNotSupportedException], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [CloneNotSupportedException].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.CloneNotSupportedException open-class sealed=no
        tparams -
        superclasses java.lang.Exception; java.lang.Throwable; java.lang.Object
        interfaces java.io.Serializable
        supertypes -
        enum -
        members none
        table abstract -
        table concrete addSuppressed(java.lang.Throwable); clone(); equals(java.lang.Object); fillInStackTrace(); finalize(); getCause(); getClass(); getLocalizedMessage(); getMessage(); getStackTrace(); getSuppressed(); hashCode(); initCause(java.lang.Throwable); notify(); notifyAll(); printStackTrace(); printStackTrace(java.io.PrintStream); printStackTrace(java.io.PrintWriter); setCause(java.lang.Throwable); setStackTrace(java.lang.StackTraceElement[]); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor CloneNotSupportedException(); CloneNotSupportedException(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [CloneNotSupportedException].
    public static final OpenClassToken<CloneNotSupportedException> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    private CloneNotSupportedException_() {
    }
}
