package gen.facts.p;

import gen.facts.p.PubBase_.Canonical;
import gen.facts.p.PubBase_.Data;
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
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.PubBase;

/// The full metamodel of [PubBase]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [PubBase]: it is here as a supertype of [p.Impl2], whose inherited members are called through this metamodel.
///
/// A member [PubBase] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = PubBase.class, fingerprint = "245debffc1d558e0289910efab81a0b8ce73ca0d7ad7e41f4f35fe323bfcfb4d", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class PubBase_ {
    /// The shape of [PubBase] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [PubBase] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.PubBase_"), "245debffc1d558e0289910efab81a0b8ce73ca0d7ad7e41f4f35fe323bfcfb4d", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.PubBase"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("PubBase"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [PubBase], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [PubBase].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.PubBase open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant java.lang.String K = "public"
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor PubBase()
        """;

        private Canonical() {
        }
    }

    /// The token of [PubBase].
    public static final OpenClassToken<PubBase> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [PubBase#K].
    public static final StaticFieldRef<String> K = UnsafeFacts.constantField(TOKEN, "K", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "public");

    /// The fact of [PubBase#PubBase()].
    public static final CtorRef0<PubBase> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    private PubBase_() {
    }
}
