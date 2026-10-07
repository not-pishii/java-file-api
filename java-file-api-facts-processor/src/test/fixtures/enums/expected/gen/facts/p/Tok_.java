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
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Tok;

/// The full metamodel of [Tok], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Tok] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Enum_] and [gen.facts.java.lang.Runnable_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Tok.class, fingerprint = "914fcbdb08ae7d19b3b3f2be479466448744dbcf24fb761331f6db47cc776666", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Tok_ {
    /// The shape of [Tok] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Tok] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<EnumClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Tok_"), "914fcbdb08ae7d19b3b3f2be479466448744dbcf24fb761331f6db47cc776666", () -> Canonical.TEXT), DeclaredKind.ENUM_CLASS, ClassDesc.of("p.Tok"), List.of(), List.of(ClassDesc.of("java.lang.Enum"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("java.io.Serializable"), ClassDesc.of("java.lang.Comparable"), ClassDesc.of("java.lang.Runnable"), ClassDesc.of("java.lang.constant.Constable")), new Supertypes(List.of(), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.Comparable"), List.of(Types.exact(Types.of(ClassDesc.of("p.Tok"))))), new ParameterizedTypeRef(ClassDesc.of("java.lang.Enum"), List.of(Types.exact(Types.of(ClassDesc.of("p.Tok"))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("compareTo", Param.fixed(ClassDesc.of("p.Tok"))), Signature.of("describeConstable"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("getDeclaringClass"), Signature.of("hashCode"), Signature.of("name"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("ordinal"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("valueOf", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("values")), Set.of()), List.of("TOKEN", "sam", "token"), false);

        private Data() {
        }
    }

    /// The canonical form of [Tok], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Tok].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Tok enum sealed=no
        tparams -
        superclasses java.lang.Enum; java.lang.Object
        interfaces java.io.Serializable; java.lang.Comparable; java.lang.Runnable; java.lang.constant.Constable
        supertypes java.lang.Comparable<p.Tok>; java.lang.Enum<p.Tok>
        enum TOKEN; sam; token
        members declared-accessible
        member method public final run() -> void throws -
        member method public static valueOf(java.lang.String) -> p.Tok throws -
        member method public static values() -> p.Tok[] throws -
        table abstract -
        table concrete clone(); compareTo(p.Tok); describeConstable(); equals(java.lang.Object); finalize(); getClass(); getDeclaringClass(); hashCode(); name(); notify(); notifyAll(); ordinal(); run(); toString(); wait(); wait(long); wait(long, int)
        table static valueOf(java.lang.Class, java.lang.String); valueOf(java.lang.String); values()
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Tok].
    public static final EnumToken<Tok> TOKEN = UnsafeFacts.enumToken(Data.SHAPE);

    /// The fact of [Tok#TOKEN].
    public static final EnumConstant<Tok> TOKEN_ = TOKEN.constant("TOKEN");

    /// The fact of [Tok#sam].
    public static final EnumConstant<Tok> sam_ = TOKEN.constant("sam");

    /// The fact of [Tok#token].
    public static final EnumConstant<Tok> token_ = TOKEN.constant("token");

    /// The fact of [Tok#run()].
    public static final VoidMethodRef0<Tok> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.FINAL);

    /// The fact of [Tok#valueOf(String)].
    public static final StaticMethodRef1<Tok, String> valueOf_String = UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Tok#values()].
    public static final StaticMethodRef0<Tok[]> values = UnsafeFacts.staticMethod(TOKEN, "values", ArrayToken.of(TOKEN), MemberTraits.FINAL);

    private Tok_() {
    }
}
