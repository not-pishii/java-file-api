package gen.facts.p;

import gen.facts.p.Noted_.Canonical;
import gen.facts.p.Noted_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
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
import p.Noted;

/// The full metamodel of [Noted], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Noted] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method note(java.lang.Deprecated)`, which mentions java.lang.Deprecated, which has no metamodel: annotation interface java.lang.Deprecated is not supported yet
///
/// No generated class extends or implements [Noted]: its abstract method note(java.lang.Deprecated) has no fact in the metamodel of the type, so no class says it overrides it.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Noted.class, fingerprint = "a1e4519838bf58e73a5b9bd3a8846149c87885a379979efa5b67a89c9d4f6725", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Noted_ {
    /// The shape of [Noted] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Noted] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Noted_"), "a1e4519838bf58e73a5b9bd3a8846149c87885a379979efa5b67a89c9d4f6725", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Noted"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("note", Param.fixed(ClassDesc.of("java.lang.Deprecated")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Noted_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Noted], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Noted].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Noted interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract note(java.lang.Deprecated) -> void throws -
        sam note(java.lang.Deprecated) -> void throws -
        inherit method note(java.lang.Deprecated) erased (java.lang.Deprecated) overrides - by interface abstract p.Noted (java.lang.Deprecated) -> void throws -
        table abstract note(java.lang.Deprecated)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Noted].
    public static final InterfaceToken<Noted> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private Noted_() {
    }
}
