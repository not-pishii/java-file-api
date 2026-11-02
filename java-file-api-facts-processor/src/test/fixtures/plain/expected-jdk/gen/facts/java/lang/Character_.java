package gen.facts.java.lang;

import gen.facts.java.lang.Character_.Canonical;
import gen.facts.java.lang.Character_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Character]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Character]: it is only mentioned in the signatures of [p.Boxes]. For the facts of its members add `Character.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Character.class, fingerprint = "0e73294f9ab6a8d11474c23feedc1bcef6081931756459fc1becef7dcb5930b6", complete = false, format = 20)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Character_ {
    /// The shape of [Character] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Character] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Character_"), "0e73294f9ab6a8d11474c23feedc1bcef6081931756459fc1becef7dcb5930b6", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Character"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("java.lang.Character")))), List.of()))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("charValue"), Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("java.lang.Character"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("charCount", Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointAt", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointAt", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointAt", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointAtImpl", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointBefore", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointBefore", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointBefore", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointBeforeImpl", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointCount", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointCount", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointCountImpl", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("codePointOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("compare", Param.fixed(ConstantDescs.CD_char), Param.fixed(ConstantDescs.CD_char)), Signature.of("digit", Param.fixed(ConstantDescs.CD_char), Param.fixed(ConstantDescs.CD_int)), Signature.of("digit", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("forDigit", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("getDirectionality", Param.fixed(ConstantDescs.CD_char)), Signature.of("getDirectionality", Param.fixed(ConstantDescs.CD_int)), Signature.of("getName", Param.fixed(ConstantDescs.CD_int)), Signature.of("getNumericValue", Param.fixed(ConstantDescs.CD_char)), Signature.of("getNumericValue", Param.fixed(ConstantDescs.CD_int)), Signature.of("getType", Param.fixed(ConstantDescs.CD_char)), Signature.of("getType", Param.fixed(ConstantDescs.CD_int)), Signature.of("hashCode", Param.fixed(ConstantDescs.CD_char)), Signature.of("highSurrogate", Param.fixed(ConstantDescs.CD_int)), Signature.of("isAlphabetic", Param.fixed(ConstantDescs.CD_int)), Signature.of("isBmpCodePoint", Param.fixed(ConstantDescs.CD_int)), Signature.of("isDefined", Param.fixed(ConstantDescs.CD_char)), Signature.of("isDefined", Param.fixed(ConstantDescs.CD_int)), Signature.of("isDigit", Param.fixed(ConstantDescs.CD_char)), Signature.of("isDigit", Param.fixed(ConstantDescs.CD_int)), Signature.of("isEmoji", Param.fixed(ConstantDescs.CD_int)), Signature.of("isEmojiComponent", Param.fixed(ConstantDescs.CD_int)), Signature.of("isEmojiModifier", Param.fixed(ConstantDescs.CD_int)), Signature.of("isEmojiModifierBase", Param.fixed(ConstantDescs.CD_int)), Signature.of("isEmojiPresentation", Param.fixed(ConstantDescs.CD_int)), Signature.of("isExtendedPictographic", Param.fixed(ConstantDescs.CD_int)), Signature.of("isHighSurrogate", Param.fixed(ConstantDescs.CD_char)), Signature.of("isISOControl", Param.fixed(ConstantDescs.CD_char)), Signature.of("isISOControl", Param.fixed(ConstantDescs.CD_int)), Signature.of("isIdentifierIgnorable", Param.fixed(ConstantDescs.CD_char)), Signature.of("isIdentifierIgnorable", Param.fixed(ConstantDescs.CD_int)), Signature.of("isIdeographic", Param.fixed(ConstantDescs.CD_int)), Signature.of("isJavaIdentifierPart", Param.fixed(ConstantDescs.CD_char)), Signature.of("isJavaIdentifierPart", Param.fixed(ConstantDescs.CD_int)), Signature.of("isJavaIdentifierStart", Param.fixed(ConstantDescs.CD_char)), Signature.of("isJavaIdentifierStart", Param.fixed(ConstantDescs.CD_int)), Signature.of("isJavaLetter", Param.fixed(ConstantDescs.CD_char)), Signature.of("isJavaLetterOrDigit", Param.fixed(ConstantDescs.CD_char)), Signature.of("isLetter", Param.fixed(ConstantDescs.CD_char)), Signature.of("isLetter", Param.fixed(ConstantDescs.CD_int)), Signature.of("isLetterOrDigit", Param.fixed(ConstantDescs.CD_char)), Signature.of("isLetterOrDigit", Param.fixed(ConstantDescs.CD_int)), Signature.of("isLowSurrogate", Param.fixed(ConstantDescs.CD_char)), Signature.of("isLowerCase", Param.fixed(ConstantDescs.CD_char)), Signature.of("isLowerCase", Param.fixed(ConstantDescs.CD_int)), Signature.of("isMirrored", Param.fixed(ConstantDescs.CD_char)), Signature.of("isMirrored", Param.fixed(ConstantDescs.CD_int)), Signature.of("isSpace", Param.fixed(ConstantDescs.CD_char)), Signature.of("isSpaceChar", Param.fixed(ConstantDescs.CD_char)), Signature.of("isSpaceChar", Param.fixed(ConstantDescs.CD_int)), Signature.of("isSupplementaryCodePoint", Param.fixed(ConstantDescs.CD_int)), Signature.of("isSurrogate", Param.fixed(ConstantDescs.CD_char)), Signature.of("isSurrogatePair", Param.fixed(ConstantDescs.CD_char), Param.fixed(ConstantDescs.CD_char)), Signature.of("isTitleCase", Param.fixed(ConstantDescs.CD_char)), Signature.of("isTitleCase", Param.fixed(ConstantDescs.CD_int)), Signature.of("isUnicodeIdentifierPart", Param.fixed(ConstantDescs.CD_char)), Signature.of("isUnicodeIdentifierPart", Param.fixed(ConstantDescs.CD_int)), Signature.of("isUnicodeIdentifierStart", Param.fixed(ConstantDescs.CD_char)), Signature.of("isUnicodeIdentifierStart", Param.fixed(ConstantDescs.CD_int)), Signature.of("isUpperCase", Param.fixed(ConstantDescs.CD_char)), Signature.of("isUpperCase", Param.fixed(ConstantDescs.CD_int)), Signature.of("isValidCodePoint", Param.fixed(ConstantDescs.CD_int)), Signature.of("isWhitespace", Param.fixed(ConstantDescs.CD_char)), Signature.of("isWhitespace", Param.fixed(ConstantDescs.CD_int)), Signature.of("lowSurrogate", Param.fixed(ConstantDescs.CD_int)), Signature.of("offsetByCodePoints", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("offsetByCodePoints", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("offsetByCodePointsImpl", Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int)), Signature.of("reverseBytes", Param.fixed(ConstantDescs.CD_char)), Signature.of("toChars", Param.fixed(ConstantDescs.CD_int)), Signature.of("toChars", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int)), Signature.of("toCodePoint", Param.fixed(ConstantDescs.CD_char), Param.fixed(ConstantDescs.CD_char)), Signature.of("toLowerCase", Param.fixed(ConstantDescs.CD_char)), Signature.of("toLowerCase", Param.fixed(ConstantDescs.CD_int)), Signature.of("toString", Param.fixed(ConstantDescs.CD_char)), Signature.of("toString", Param.fixed(ConstantDescs.CD_int)), Signature.of("toSurrogates", Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int)), Signature.of("toTitleCase", Param.fixed(ConstantDescs.CD_char)), Signature.of("toTitleCase", Param.fixed(ConstantDescs.CD_int)), Signature.of("toUpperCase", Param.fixed(ConstantDescs.CD_char)), Signature.of("toUpperCase", Param.fixed(ConstantDescs.CD_int)), Signature.of("toUpperCaseCharArray", Param.fixed(ConstantDescs.CD_int)), Signature.of("toUpperCaseEx", Param.fixed(ConstantDescs.CD_int)), Signature.of("valueOf", Param.fixed(ConstantDescs.CD_char))), Set.of(Signature.of("Character", Param.fixed(ConstantDescs.CD_char))), Set.of(), Map.ofEntries(Map.entry(Signature.of("clone"), List.of(new Thrown(ClassDesc.of("java.lang.CloneNotSupportedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("finalize"), List.of(new Thrown(ClassDesc.of("java.lang.Throwable"), List.of(ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(new AnnotationUse(ClassDesc.of("jdk.internal.ValueBased"), List.of())), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [Character], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Character].
        static final String TEXT = """
        javafile-facts-canonical 13
        type java.lang.Character final-class sealed=no nesting=top
        annotations @jdk.internal.ValueBased
        annotation -
        tparams -
        superclasses java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.constant.Constable
        supertypes java.lang.Comparable<java.lang.Character>
        enum -
        record -
        members none
        table abstract -
        table concrete charValue(); clone(); compareTo(java.lang.Character); describeConstable(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static charCount(int); codePointAt(char[], int); codePointAt(char[], int, int); codePointAt(java.lang.CharSequence, int); codePointAtImpl(char[], int, int); codePointBefore(char[], int); codePointBefore(char[], int, int); codePointBefore(java.lang.CharSequence, int); codePointBeforeImpl(char[], int, int); codePointCount(char[], int, int); codePointCount(java.lang.CharSequence, int, int); codePointCountImpl(char[], int, int); codePointOf(java.lang.String); compare(char, char); digit(char, int); digit(int, int); forDigit(int, int); getDirectionality(char); getDirectionality(int); getName(int); getNumericValue(char); getNumericValue(int); getType(char); getType(int); hashCode(char); highSurrogate(int); isAlphabetic(int); isBmpCodePoint(int); isDefined(char); isDefined(int); isDigit(char); isDigit(int); isEmoji(int); isEmojiComponent(int); isEmojiModifier(int); isEmojiModifierBase(int); isEmojiPresentation(int); isExtendedPictographic(int); isHighSurrogate(char); isISOControl(char); isISOControl(int); isIdentifierIgnorable(char); isIdentifierIgnorable(int); isIdeographic(int); isJavaIdentifierPart(char); isJavaIdentifierPart(int); isJavaIdentifierStart(char); isJavaIdentifierStart(int); isJavaLetter(char); isJavaLetterOrDigit(char); isLetter(char); isLetter(int); isLetterOrDigit(char); isLetterOrDigit(int); isLowSurrogate(char); isLowerCase(char); isLowerCase(int); isMirrored(char); isMirrored(int); isSpace(char); isSpaceChar(char); isSpaceChar(int); isSupplementaryCodePoint(int); isSurrogate(char); isSurrogatePair(char, char); isTitleCase(char); isTitleCase(int); isUnicodeIdentifierPart(char); isUnicodeIdentifierPart(int); isUnicodeIdentifierStart(char); isUnicodeIdentifierStart(int); isUpperCase(char); isUpperCase(int); isValidCodePoint(int); isWhitespace(char); isWhitespace(int); lowSurrogate(int); offsetByCodePoints(char[], int, int, int, int); offsetByCodePoints(java.lang.CharSequence, int, int); offsetByCodePointsImpl(char[], int, int, int, int); reverseBytes(char); toChars(int); toChars(int, char[], int); toCodePoint(char, char); toLowerCase(char); toLowerCase(int); toString(char); toString(int); toSurrogates(int, char[], int); toTitleCase(char); toTitleCase(int); toUpperCase(char); toUpperCase(int); toUpperCaseCharArray(int); toUpperCaseEx(int); valueOf(char)
        table ctor Character(char)
        """;

        private Canonical() {
        }
    }

    /// The token of [Character].
    public static final FinalClassToken<Character> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Character_() {
    }
}
