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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Locale.class, fingerprint = "7dff9d3ec08be2d0cb38f4b062b421a51dd8e6d03cbbf52ff02225b2a52ae8e2", complete = false, format = 4)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Locale_ {
    public static final class Data {
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.util.Locale_"), "7dff9d3ec08be2d0cb38f4b062b421a51dd8e6d03cbbf52ff02225b2a52ae8e2", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.util.Locale"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getBaseLocale"), Signature.of("getClass"), Signature.of("getCountry"), Signature.of("getDisplayCountry"), Signature.of("getDisplayCountry", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayLanguage"), Signature.of("getDisplayLanguage", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayName"), Signature.of("getDisplayName", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayScript"), Signature.of("getDisplayScript", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getDisplayVariant"), Signature.of("getDisplayVariant", Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("getExtension", Param.fixed(ConstantDescs.CD_char)), Signature.of("getExtensionKeys"), Signature.of("getISO3Country"), Signature.of("getISO3Language"), Signature.of("getLanguage"), Signature.of("getLocaleExtensions"), Signature.of("getScript"), Signature.of("getUnicodeLocaleAttributes"), Signature.of("getUnicodeLocaleKeys"), Signature.of("getUnicodeLocaleType", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getVariant"), Signature.of("hasExtensions"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("stripExtensions"), Signature.of("toLanguageTag"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("availableLocales"), Signature.of("caseFoldLanguageTag", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("filter", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("filter", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection")), Param.fixed(ClassDesc.of("java.util.Locale$FilteringMode"))), Signature.of("filterTags", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("filterTags", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection")), Param.fixed(ClassDesc.of("java.util.Locale$FilteringMode"))), Signature.of("forLanguageTag", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getAvailableLocales"), Signature.of("getDefault"), Signature.of("getDefault", Param.fixed(ClassDesc.of("java.util.Locale$Category"))), Signature.of("getISOCountries"), Signature.of("getISOCountries", Param.fixed(ClassDesc.of("java.util.Locale$IsoCountryCode"))), Signature.of("getISOLanguages"), Signature.of("getInstance", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getInstance", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("sun.util.locale.LocaleExtensions"))), Signature.of("getInstance", Param.fixed(ClassDesc.of("sun.util.locale.BaseLocale")), Param.fixed(ClassDesc.of("sun.util.locale.LocaleExtensions"))), Signature.of("lookup", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("lookupTag", Param.fixed(ClassDesc.of("java.util.List")), Param.fixed(ClassDesc.of("java.util.Collection"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("of", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("setDefault", Param.fixed(ClassDesc.of("java.util.Locale$Category")), Param.fixed(ClassDesc.of("java.util.Locale"))), Signature.of("setDefault", Param.fixed(ClassDesc.of("java.util.Locale")))), Set.of(Signature.of("Locale", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Locale", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("Locale", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.String"))))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 3\ntype java.util.Locale final-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers none\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getBaseLocale(); getClass(); getCountry(); getDisplayCountry(); getDisplayCountry(java.util.Locale); getDisplayLanguage(); getDisplayLanguage(java.util.Locale); getDisplayName(); getDisplayName(java.util.Locale); getDisplayScript(); getDisplayScript(java.util.Locale); getDisplayVariant(); getDisplayVariant(java.util.Locale); getExtension(char); getExtensionKeys(); getISO3Country(); getISO3Language(); getLanguage(); getLocaleExtensions(); getScript(); getUnicodeLocaleAttributes(); getUnicodeLocaleKeys(); getUnicodeLocaleType(java.lang.String); getVariant(); hasExtensions(); hashCode(); notify(); notifyAll(); stripExtensions(); toLanguageTag(); toString(); wait(); wait(long); wait(long, int)\ntable static availableLocales(); caseFoldLanguageTag(java.lang.String); filter(java.util.List, java.util.Collection); filter(java.util.List, java.util.Collection, java.util.Locale$FilteringMode); filterTags(java.util.List, java.util.Collection); filterTags(java.util.List, java.util.Collection, java.util.Locale$FilteringMode); forLanguageTag(java.lang.String); getAvailableLocales(); getDefault(); getDefault(java.util.Locale$Category); getISOCountries(); getISOCountries(java.util.Locale$IsoCountryCode); getISOLanguages(); getInstance(java.lang.String, java.lang.String, java.lang.String); getInstance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, sun.util.locale.LocaleExtensions); getInstance(sun.util.locale.BaseLocale, sun.util.locale.LocaleExtensions); lookup(java.util.List, java.util.Collection); lookupTag(java.util.List, java.util.Collection); of(java.lang.String); of(java.lang.String, java.lang.String); of(java.lang.String, java.lang.String, java.lang.String); setDefault(java.util.Locale$Category, java.util.Locale); setDefault(java.util.Locale)\ntable ctor Locale(java.lang.String); Locale(java.lang.String, java.lang.String); Locale(java.lang.String, java.lang.String, java.lang.String)\n";

        private Canonical() {
        }
    }

    public static final FinalClassToken<Locale> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private Locale_() {
    }
}
