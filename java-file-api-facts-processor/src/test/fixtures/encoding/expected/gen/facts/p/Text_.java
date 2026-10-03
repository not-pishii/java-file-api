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

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Text.class, fingerprint = "60b91bb483bf04820abc14c67b1aa1c74188700617322f9255eb4f31a49d8968", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Text_ {
    public static final class Data {
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Text_"), "60b91bb483bf04820abc14c67b1aa1c74188700617322f9255eb4f31a49d8968", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Text"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Text"))), List.of(), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Text open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int \u0447\u0438\u0441\u043b\u043e\nmember field static constant char CHAR = '\\u044f'\nmember field static constant char DELETE = '\\u007f'\nmember field static constant char LONE_CHAR = '\\ud800'\nmember field static constant java.lang.String CYRILLIC = \"\\u043f\\u0440\\u0438\\u0432\\u0435\\u0442 \\u00e9\\u007f\"\nmember field static constant java.lang.String ESCAPES = \"\\u005cu0041 \\u005c\\u00e9 \\u0022\\u00e9\\u0022\"\nmember field static constant java.lang.String LONE_HIGH = \"a\\ud800b\"\nmember field static constant java.lang.String LONE_LOW = \"\\udc00\"\nmember field static constant java.lang.String PAIR = \"\\ud83d\\ude00\"\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Text()\n";

        private Canonical() {
        }
    }

    public static final OpenClassToken<Text> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    public static final StaticFieldRef<Char> CHAR = UnsafeFacts.constantField(TOKEN, "CHAR", PrimitiveToken.CHAR, (char) 1103);

    public static final StaticFieldRef<String> CYRILLIC = UnsafeFacts.constantField(TOKEN, "CYRILLIC", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\u043f\u0440\u0438\u0432\u0435\u0442 \u00e9\u007f");

    public static final StaticFieldRef<Char> DELETE = UnsafeFacts.constantField(TOKEN, "DELETE", PrimitiveToken.CHAR, (char) 127);

    public static final StaticFieldRef<String> ESCAPES = UnsafeFacts.constantField(TOKEN, "ESCAPES", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\\u0041 \\\u00e9 \"\u00e9\"");

    public static final StaticFieldRef<Char> LONE_CHAR = UnsafeFacts.constantField(TOKEN, "LONE_CHAR", PrimitiveToken.CHAR, (char) 55296);

    public static final StaticFieldRef<String> LONE_HIGH = UnsafeFacts.constantField(TOKEN, "LONE_HIGH", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "a\ud800b");

    public static final StaticFieldRef<String> LONE_LOW = UnsafeFacts.constantField(TOKEN, "LONE_LOW", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\udc00");

    public static final StaticFieldRef<String> PAIR = UnsafeFacts.constantField(TOKEN, "PAIR", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "\ud83d\ude00");

    public static final MutableFieldRef<Text, Int> \u0447\u0438\u0441\u043b\u043e = UnsafeFacts.mutableField(TOKEN, "\u0447\u0438\u0441\u043b\u043e", PrimitiveToken.INT);

    public static final CtorRef0<Text> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private Text_() {
    }
}
