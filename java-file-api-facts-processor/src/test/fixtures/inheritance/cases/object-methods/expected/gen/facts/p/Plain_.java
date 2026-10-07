package gen.facts.p;

import gen.facts.p.Plain_.Canonical;
import gen.facts.p.Plain_.Data;
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
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Plain;

/// The full metamodel of [Plain], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Plain] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Plain.class, fingerprint = "22abc26efefa60ffb3bb63dfa7a29cef1908860cb14f8181dc14f278a0768981", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Plain_ {
    /// The shape of [Plain] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Plain] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Plain_"), "22abc26efefa60ffb3bb63dfa7a29cef1908860cb14f8181dc14f278a0768981", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Plain"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Plain"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Plain], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Plain].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Plain open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable plain() -> void throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); plain(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Plain()
        """;

        private Canonical() {
        }
    }

    /// The token of [Plain].
    public static final OpenClassToken<Plain> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Plain#Plain()].
    public static final CtorRef0<Plain> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Plain#plain()].
    public static final VoidMethodRef0<Plain> plain = UnsafeFacts.voidMethod(TOKEN, "plain", MemberTraits.OVERRIDABLE);

    private Plain_() {
    }
}
