package gen.facts.p;

import gen.facts.p.Def_.Canonical;
import gen.facts.p.Def_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Def;

/// The full metamodel of [Def], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Def] inherits has its fact in the metamodel of the supertype that declares it: [Fn_].
///
/// A generated class extends or implements [Def] by the proof its metamodel starts, with the stages of `Def_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Def.class, fingerprint = "fa0e2a0f2b6379291b0c50e2bf744e15e968f228c4495f6909454d553e01793b", complete = true, format = 19)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Def_ {
    /// The shape of [Def] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Def] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Def_"), "fa0e2a0f2b6379291b0c50e2bf744e15e968f228c4495f6909454d553e01793b", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Def"), List.of(), List.of(), List.of(ClassDesc.of("p.Fn")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), false, List.of(), List.of(), new Def_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Def], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Def].
        static final String TEXT = """
        javafile-facts-canonical 12
        type p.Def interface sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces p.Fn
        supertypes -
        enum -
        members declared-accessible
        member method public overridable apply(java.lang.String) -> java.lang.String throws -
        inherit method apply(java.lang.String) erased (java.lang.String) overrides p.Fn by interface default p.Def (java.lang.String) -> java.lang.String throws -
        table abstract -
        table concrete apply(java.lang.String); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Def].
    public static final InterfaceToken<Def> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Def#apply(String)].
    public static final MethodRef1<Def, String, String> apply_String = UnsafeInvocables.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Def_() {
    }

    /// Starts the proof that a class being declared implements [Def]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Def> implement(RefToken<Self> self) {
        return new Def_Heritage().implement(TOKEN, self);
    }
}
