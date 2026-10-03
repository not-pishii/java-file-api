package gen.facts.p;

import gen.facts.p.Tok_.Canonical;
import gen.facts.p.Tok_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.EnumClass;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import p.Tok;

@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Tok.class, fingerprint = "4a8666431fdfd1ad09d269b6e23a30ebe4cf6d50cb1db74cf945270b90e570c8", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Tok_ {
    public static final class Data {
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Tok_"), "4a8666431fdfd1ad09d269b6e23a30ebe4cf6d50cb1db74cf945270b90e570c8", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Tok"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Tok"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Tok"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Tok"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("TOKEN", "sam", "token"), false);

        private Data() {
        }
    }

    static final class Canonical {
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Tok enum sealed=no\ntparams -\nsuperclasses java.lang.Enum; java.lang.Object\nsupertypes java.lang.Comparable<p.Tok>; java.lang.Enum<p.Tok>\nenum TOKEN; sam; token\nmembers declared-public\nmember method final run() -> void throws -\nmember method static valueOf(java.lang.String) -> p.Tok throws -\nmember method static values() -> p.Tok[] throws -\ntable abstract -\ntable concrete clone(); compareTo(p.Tok); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); run(); toString(); wait(); wait(long); wait(long, int)\ntable static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()\ntable ctor -\n";

        private Canonical() {
        }
    }

    public static final EnumToken<Tok> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    public static final EnumConstant<Tok> TOKEN_ = TOKEN.constant("TOKEN");

    public static final EnumConstant<Tok> sam_ = TOKEN.constant("sam");

    public static final EnumConstant<Tok> token_ = TOKEN.constant("token");

    public static final VoidMethodRef0<Tok> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.FINAL);

    public static final StaticMethodRef1<Tok, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    public static final StaticMethodRef0<Tok[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Tok_() {
    }
}
