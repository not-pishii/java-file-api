package gen.facts.java.lang;

import gen.facts.java.lang.StringBuilder_.Canonical;
import gen.facts.java.lang.StringBuilder_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [StringBuilder]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [StringBuilder]: it is only mentioned in the signatures of [p.Ov2]. For the facts of its members add `StringBuilder.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = StringBuilder.class, fingerprint = "f28be54a5ff50b3509ae5b7decd189881f2af9c08e3964a83bbd328b0870632d", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class StringBuilder_ {
    /// The shape of [StringBuilder] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [StringBuilder] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.StringBuilder_"), "f28be54a5ff50b3509ae5b7decd189881f2af9c08e3964a83bbd328b0870632d", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.StringBuilder"), List.of(), List.of(ClassDesc.of("java.lang.AbstractStringBuilder"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.StringBuilder"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("append", Param.fixed(ConstantDescs.CD_boolean)), Signature.of("append", Param.fixed(ConstantDescs.CD_char)), Signature.of("append", Param.fixed(ClassDesc.ofDescriptor("[C"))), Signature.of("append", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("append", Param.fixed(ConstantDescs.CD_double)), Signature.of("append", Param.fixed(ConstantDescs.CD_float)), Signature.of("append", Param.fixed(ConstantDescs.CD_int)), Signature.of("append", Param.fixed(ClassDesc.of("java.lang.AbstractStringBuilder"))), Signature.of("append", Param.fixed(ClassDesc.of("java.lang.CharSequence"))), Signature.of("append", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("append", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("append", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("append", Param.fixed(ClassDesc.of("java.lang.StringBuffer"))), Signature.of("append", Param.fixed(ConstantDescs.CD_long)), Signature.of("appendCodePoint", Param.fixed(ConstantDescs.CD_int)), Signature.of("capacity"), Signature.of("charAt", Param.fixed(ConstantDescs.CD_int)), Signature.of("chars"), Signature.of("clone"), Signature.of("codePointAt", Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointBefore", Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointCount", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePoints"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.AbstractStringBuilder"))), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.StringBuilder"))), Signature.of("delete", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("deleteCharAt", Param.fixed(ConstantDescs.CD_int)), Signature.of("ensureCapacity", Param.fixed(ConstantDescs.CD_int)), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getBytes", Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_byte)), Signature.of("getChars", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int)), Signature.of("getClass"), Signature.of("getCoder"), Signature.of("getValue"), Signature.of("hashCode"), Signature.of("indexOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("indexOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("initBytes", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_boolean)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_char)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.ofDescriptor("[C"))), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_double)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_float)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.CharSequence"))), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("insert", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_long)), Signature.of("isEmpty"), Signature.of("isLatin1"), Signature.of("lastIndexOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("lastIndexOf", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_int)), Signature.of("length"), Signature.of("mix", Param.fixed(ConstantDescs.CD_long)), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("offsetByCodePoints", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("prepend", Param.fixed(ConstantDescs.CD_long), Param.fixed(ClassDesc.ofDescriptor("[B"))), Signature.of("repeat", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("repeat", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int)), Signature.of("replace", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("reverse"), Signature.of("setCharAt", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_char)), Signature.of("setLength", Param.fixed(ConstantDescs.CD_int)), Signature.of("subSequence", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("substring", Param.fixed(ConstantDescs.CD_int)), Signature.of("substring", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("toString"), Signature.of("trimToSize"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("StringBuilder"), Signature.of("StringBuilder", Param.fixed(ConstantDescs.CD_int)), Signature.of("StringBuilder", Param.fixed(ClassDesc.of("java.lang.CharSequence"))), Signature.of("StringBuilder", Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [StringBuilder], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [StringBuilder].
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.StringBuilder final-class sealed=no\ntparams -\nsuperclasses java.lang.AbstractStringBuilder; java.lang.Object\nsupertypes java.lang.Comparable<java.lang.StringBuilder>\nenum -\nmembers none\ntable abstract -\ntable concrete append(boolean); append(char); append(char[]); append(char[], int, int); append(double); append(float); append(int); append(java.lang.AbstractStringBuilder); append(java.lang.CharSequence); append(java.lang.CharSequence, int, int); append(java.lang.Object); append(java.lang.String); append(java.lang.StringBuffer); append(long); appendCodePoint(int); capacity(); charAt(int); chars(); clone(); codePointAt(int); codePointBefore(int); codePointCount(int, int); codePoints(); compareTo(java.lang.AbstractStringBuilder); compareTo(java.lang.StringBuilder); delete(int, int); deleteCharAt(int); ensureCapacity(int); equals(java.lang.Object); finalize(); getBytes(byte[], int, byte); getChars(int, int, char[], int); getClass(); getCoder(); getValue(); hashCode(); indexOf(java.lang.String); indexOf(java.lang.String, int); initBytes(char[], int, int); insert(int, boolean); insert(int, char); insert(int, char[]); insert(int, char[], int, int); insert(int, double); insert(int, float); insert(int, int); insert(int, java.lang.CharSequence); insert(int, java.lang.CharSequence, int, int); insert(int, java.lang.Object); insert(int, java.lang.String); insert(int, long); isEmpty(); isLatin1(); lastIndexOf(java.lang.String); lastIndexOf(java.lang.String, int); length(); mix(long); notify(); notifyAll(); offsetByCodePoints(int, int); prepend(long, byte[]); repeat(int, int); repeat(java.lang.CharSequence, int); replace(int, int, java.lang.String); reverse(); setCharAt(int, char); setLength(int); subSequence(int, int); substring(int); substring(int, int); toString(); trimToSize(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor StringBuilder(); StringBuilder(int); StringBuilder(java.lang.CharSequence); StringBuilder(java.lang.String)\n";

        private Canonical() {
        }
    }

    /// The token of [StringBuilder].
    public static final FinalClassToken<StringBuilder> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private StringBuilder_() {
    }
}
