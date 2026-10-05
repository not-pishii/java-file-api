package gen.facts.java.util;

import gen.facts.java.util.Locale_.Canonical;
import gen.facts.java.util.Locale_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Locale;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Locale]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Locale]: it is only mentioned in the signatures of [p.Greeter]. For the facts of its members add `Locale.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Locale.class, fingerprint = "8791ec3568f1b6ffed00547c78e26d044c0ecd25866e872862f7d4a98255c1f5", complete = false, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Locale_ {
    /// The shape of [Locale] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Locale] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Locale_"), "8791ec3568f1b6ffed00547c78e26d044c0ecd25866e872862f7d4a98255c1f5", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.util.Locale"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getBaseLocale"), Signature.of("getClass"), Signature.of("getCountry"), Signature.of("getDisplayCountry"), Signature.of("getDisplayCountry", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayLanguage"), Signature.of("getDisplayLanguage", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayName"), Signature.of("getDisplayName", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayScript"), Signature.of("getDisplayScript", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayVariant"), Signature.of("getDisplayVariant", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getExtension", Param.fixed(ConstantDescs.CD_char)), Signature.of("getExtensionKeys"), Signature.of("getISO3Country"), Signature.of("getISO3Language"), Signature.of("getLanguage"), Signature.of("getLocaleExtensions"), Signature.of("getScript"), Signature.of("getUnicodeLocaleAttributes"), Signature.of("getUnicodeLocaleKeys"), Signature.of("getUnicodeLocaleType", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getVariant"), Signature.of("hasExtensions"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("stripExtensions"), Signature.of("toLanguageTag"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("availableLocales"), Signature.of("caseFoldLanguageTag", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("filter", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("filter", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection")), Param.fixed(ClassDesc.of("java.util.Locale$FilteringMode"))), Signature.of("filterTags", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("filterTags", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection")), Param.fixed(ClassDesc.of("java.util.Locale$FilteringMode"))), Signature.of("forLanguageTag", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getAvailableLocales"), Signature.of("getDefault"), Signature.of("getDefault", Param.fixed(ClassDesc.of("java.util.Locale$Category"))), Signature.of("getISOCountries"), Signature.of("getISOCountries", Param.fixed(ClassDesc.of("java.util.Locale$IsoCountryCode"))), Signature.of("getISOLanguages"), Signature.of("getInstance", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getInstance", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("sun.util.locale.LocaleExtensions"))), Signature.of("getInstance", Param.fixed(ClassDesc.of("sun.util.locale.BaseLocale")), Param.fixed(ClassDesc.of("sun.util.locale.LocaleExtensions"))), Signature.of("lookup", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("lookupTag", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("setDefault", Param.fixed(ClassDesc.of("java.util.Locale$Category")), Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("setDefault", Param.fixed(ClassDesc.of("java.util.Locale")))), Set.of(Signature.of("Locale", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Locale", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Locale", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Locale], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Locale].
        static final String TEXT = """
        javafile-facts-canonical 5
        type java.util.Locale final-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces java.io.Serializable; java.lang.Cloneable
        supertypes -
        enum -
        members none
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getBaseLocale(); getClass(); getCountry(); getDisplayCountry(); getDisplayCountry(java.util.Locale); getDisplayLanguage(); getDisplayLanguage(java.util.Locale); getDisplayName(); getDisplayName(java.util.Locale); getDisplayScript(); getDisplayScript(java.util.Locale); getDisplayVariant(); getDisplayVariant(java.util.Locale); getExtension(char); getExtensionKeys(); getISO3Country(); getISO3Language(); getLanguage(); getLocaleExtensions(); getScript(); getUnicodeLocaleAttributes(); getUnicodeLocaleKeys(); getUnicodeLocaleType(java.lang.String); getVariant(); hasExtensions(); hashCode(); notify(); notifyAll(); stripExtensions(); toLanguageTag(); toString(); wait(); wait(long); wait(long, int)
        table static availableLocales(); caseFoldLanguageTag(java.lang.String); filter(java.util.List, java.util.Collection); filter(java.util.List, java.util.Collection, java.util.Locale$FilteringMode); filterTags(java.util.List, java.util.Collection); filterTags(java.util.List, java.util.Collection, java.util.Locale$FilteringMode); forLanguageTag(java.lang.String); getAvailableLocales(); getDefault(); getDefault(java.util.Locale$Category); getISOCountries(); getISOCountries(java.util.Locale$IsoCountryCode); getISOLanguages(); getInstance(java.lang.String, java.lang.String, java.lang.String); getInstance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, sun.util.locale.LocaleExtensions); getInstance(sun.util.locale.BaseLocale, sun.util.locale.LocaleExtensions); lookup(java.util.List, java.util.Collection); lookupTag(java.util.List, java.util.Collection); of(java.lang.String); of(java.lang.String, java.lang.String); of(java.lang.String, java.lang.String, java.lang.String); setDefault(java.util.Locale$Category, java.util.Locale); setDefault(java.util.Locale)
        table ctor Locale(java.lang.String); Locale(java.lang.String, java.lang.String); Locale(java.lang.String, java.lang.String, java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [Locale].
    public static final FinalClassToken<Locale> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Locale_() {
    }
}
