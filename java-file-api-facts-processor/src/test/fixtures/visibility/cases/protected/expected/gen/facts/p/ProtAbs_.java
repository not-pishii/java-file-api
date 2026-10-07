package gen.facts.p;

import gen.facts.p.ProtAbs_.Canonical;
import gen.facts.p.ProtAbs_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.ProtAbs;

/// The full metamodel of [ProtAbs], which `@Facts` asks for: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// A member [ProtAbs] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = ProtAbs.class, fingerprint = "75b0df878cb7517fba03bc73cb41cb5093e051a6eebef4c3dec3b739c803ab97", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class ProtAbs_ {
    /// The shape of [ProtAbs] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [ProtAbs] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.ProtAbs_"), "75b0df878cb7517fba03bc73cb41cb5093e051a6eebef4c3dec3b739c803ab97", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.ProtAbs"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("hook", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("ProtAbs"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [ProtAbs], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [ProtAbs].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.ProtAbs abstract-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor protected () throws -
        member method protected abstract hook(java.lang.String) -> java.lang.String throws -
        table abstract hook(java.lang.String)
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor ProtAbs()
        """;

        private Canonical() {
        }
    }

    /// The token of [ProtAbs].
    public static final AbstractClassToken<ProtAbs> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [ProtAbs#ProtAbs()], which is `protected`: a subclass alone uses it.
    public static final SuperCtorRef0<ProtAbs> super_ = UnsafeFacts.superCtor(TOKEN, MemberTraits.FINAL.with(Access.PROTECTED));

    /// The fact of [ProtAbs#hook(String)], which is `protected`: a subclass alone uses it.
    public static final Protected<ProtAbs, MethodRef1<ProtAbs, String, String>> hook_String = UnsafeFacts.protected_(TOKEN, UnsafeFacts.method(TOKEN, "hook", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT.with(Access.PROTECTED)));

    private ProtAbs_() {
    }
}
