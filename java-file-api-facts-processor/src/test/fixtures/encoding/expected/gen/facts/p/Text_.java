package gen.facts.p;

import gen.facts.p.Text_.Canonical;
import gen.facts.p.Text_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Char;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Text;

/// The full metamodel of [Text], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Text] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Text.class, fingerprint = "7f57a1699b617a429cfe880b8dc922279c6b63d646507f9d332d86f6c1f01d53", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Text_ {
    /// The shape of [Text] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Text] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Text_"), "7f57a1699b617a429cfe880b8dc922279c6b63d646507f9d332d86f6c1f01d53", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Text"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Text"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Text], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Text].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Text open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int \u0447\u0438\u0441\u043b\u043e\nmember field static constant char CHAR = '\\u044f'\nmember field static constant char DELETE = '\\u007f'\nmember field static constant char LONE_CHAR = '\\ud800'\nmember field static constant java.lang.String CYRILLIC = \"\\u043f\\u0440\\u0438\\u0432\\u0435\\u0442 \\u00e9\\u007f\"\nmember field static constant java.lang.String ESCAPES = \"\\u005cu0041 \\u005c\\u00e9 \\u0022\\u00e9\\u0022\"\nmember field static constant java.lang.String LONE_HIGH = \"a\\ud800b\"\nmember field static constant java.lang.String LONE_LOW = \"\\udc00\"\nmember field static constant java.lang.String PAIR = \"\\ud83d\\ude00\"\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Text()\n";

        private Canonical() {
        }
    }

    /// The token of [Text].
    public static final OpenClassToken<Text> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Text#CHAR].
    public static final StaticFieldRef<Char> CHAR = UnsafeFacts.constantField(TOKEN, "CHAR", PrimitiveToken.CHAR, (char) 1103);

    /// The fact of [Text#CYRILLIC].
    public static final StaticFieldRef<String> CYRILLIC = UnsafeFacts.constantField(TOKEN, "CYRILLIC", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\u043f\u0440\u0438\u0432\u0435\u0442 \u00e9\u007f");

    /// The fact of [Text#DELETE].
    public static final StaticFieldRef<Char> DELETE = UnsafeFacts.constantField(TOKEN, "DELETE", PrimitiveToken.CHAR, (char) 127);

    /// The fact of [Text#ESCAPES].
    public static final StaticFieldRef<String> ESCAPES = UnsafeFacts.constantField(TOKEN, "ESCAPES", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\\u0041 \\\u00e9 \"\u00e9\"");

    /// The fact of [Text#LONE_CHAR].
    public static final StaticFieldRef<Char> LONE_CHAR = UnsafeFacts.constantField(TOKEN, "LONE_CHAR", PrimitiveToken.CHAR, (char) 55296);

    /// The fact of [Text#LONE_HIGH].
    public static final StaticFieldRef<String> LONE_HIGH = UnsafeFacts.constantField(TOKEN, "LONE_HIGH", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "a\ud800b");

    /// The fact of [Text#LONE_LOW].
    public static final StaticFieldRef<String> LONE_LOW = UnsafeFacts.constantField(TOKEN, "LONE_LOW", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\udc00");

    /// The fact of [Text#PAIR].
    public static final StaticFieldRef<String> PAIR = UnsafeFacts.constantField(TOKEN, "PAIR", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\ud83d\ude00");

    /// The fact of [Text#\u0447\u0438\u0441\u043b\u043e].
    public static final MutableFieldRef<Text, Int> \u0447\u0438\u0441\u043b\u043e = UnsafeFacts.mutableField(TOKEN, "\u0447\u0438\u0441\u043b\u043e", PrimitiveToken.INT);

    /// The fact of [Text#Text()].
    public static final CtorRef0<Text> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Text_() {
    }
}
