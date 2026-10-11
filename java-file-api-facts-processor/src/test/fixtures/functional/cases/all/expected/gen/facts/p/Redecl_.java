package gen.facts.p;

import gen.facts.p.Redecl_.Canonical;
import gen.facts.p.Redecl_.Data;
import gen.facts.p.Redecl_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Redecl;

/// The full metamodel of [Redecl], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Redecl] inherits has its fact in the metamodel of the supertype that declares it: [Fn_].
///
/// A generated class extends or implements [Redecl] by the proof its metamodel starts, with the stages of `Redecl_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Redecl.class, fingerprint = "f3952533fcdfe719b5afe39f1b7c4dd72a4fb4793993d2ada66ada3fb4e8fe6e", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Redecl_ {
    /// The shape of [Redecl] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Redecl] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Redecl_"), "f3952533fcdfe719b5afe39f1b7c4dd72a4fb4793993d2ada66ada3fb4e8fe6e", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Redecl"), List.of(), List.of(), List.of(ClassDesc.of("p.Fn")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Redecl_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Redecl], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Redecl].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Redecl interface sealed=no
        tparams -
        superclasses -
        interfaces p.Fn
        supertypes -
        enum -
        members declared-accessible
        member method public abstract apply(java.lang.String) -> java.lang.String throws -
        sam apply(java.lang.String) -> java.lang.String throws -
        inherit method public abstract p.Redecl apply(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides p.Fn
        table abstract apply(java.lang.String)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Redecl].
    public static final InterfaceToken<Redecl> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Redecl#apply(String)].
    public static final MethodRef1<Redecl, String, String> apply_String = UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Redecl#apply(String)], which a lambda implements.
    public static final Sam1<Redecl, String, String> sam = UnsafeFacts.sam(apply_String);

    private Redecl_() {
    }

    /// Starts the proof that a class being declared implements [Redecl]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Redecl_Heritage().implement(TOKEN, self);
    }
}
