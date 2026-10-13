package gen.facts.p;

import gen.facts.p.Text_.Canonical;
import gen.facts.p.Text_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
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
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Text;

/// The full metamodel of [Text], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Text] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Text] by the proof its metamodel starts, with the stages of `Text_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Text.class, fingerprint = "66a364ca3741cbed57d66f405c8e4b9dce5e6ca6da49d61e1d586d6db1926563", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Text_ {
    /// The shape of [Text] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Text] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Text_"), "66a364ca3741cbed57d66f405c8e4b9dce5e6ca6da49d61e1d586d6db1926563", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Text"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Text"))), false, new Text_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Text], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Text].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Text open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable int \u0447\u0438\u0441\u043b\u043e
        member field public static constant char CHAR = '\\u044f'
        member field public static constant char DELETE = '\\u007f'
        member field public static constant char LONE_CHAR = '\\ud800'
        member field public static constant java.lang.String CYRILLIC = "\\u043f\\u0440\\u0438\\u0432\\u0435\\u0442 \\u00e9\\u007f"
        member field public static constant java.lang.String ESCAPES = "\\u005cu0041 \\u005c\\u00e9 \\u0022\\u00e9\\u0022"
        member field public static constant java.lang.String LONE_HIGH = "a\\ud800b"
        member field public static constant java.lang.String LONE_LOW = "\\udc00"
        member field public static constant java.lang.String PAIR = "\\ud83d\\ude00"
        inherit ctor Text() public () throws -
        inherit field public p.Text CHAR
        inherit field public p.Text CYRILLIC
        inherit field public p.Text DELETE
        inherit field public p.Text ESCAPES
        inherit field public p.Text LONE_CHAR
        inherit field public p.Text LONE_HIGH
        inherit field public p.Text LONE_LOW
        inherit field public p.Text PAIR
        inherit field public p.Text \u0447\u0438\u0441\u043b\u043e
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Text()
        """;

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
    public static final CtorRef0<Text> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    private Text_() {
    }

    /// Starts the proof that a class being declared extends [Text]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Text> extend(RefToken<Self> self) {
        return new Text_Heritage().extend(TOKEN, self);
    }
}
