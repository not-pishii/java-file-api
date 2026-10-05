package gen.facts.p;

import gen.facts.p.Two_.Canonical;
import gen.facts.p.Two_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Two;

/// The full metamodel of [Two], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Two] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Two.class, fingerprint = "9b7972680b711419fe2f7532bfb6f68d85ca72526147e7401eb26c6c3929b83d", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Two_ {
    /// The shape of [Two] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Two] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Two_"), "9b7972680b711419fe2f7532bfb6f68d85ca72526147e7401eb26c6c3929b83d", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Two"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("a"), Signature.of("b")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Two], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Two].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Two interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-public
        member method abstract a() -> void throws -
        member method abstract b() -> void throws -
        table abstract a(); b()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Two].
    public static final InterfaceToken<Two> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Two#a()].
    public static final VoidMethodRef0<Two> a = UnsafeFacts.voidMethod(TOKEN, "a", MemberTraits.ABSTRACT);

    /// The fact of [Two#b()].
    public static final VoidMethodRef0<Two> b = UnsafeFacts.voidMethod(TOKEN, "b", MemberTraits.ABSTRACT);

    private Two_() {
    }
}
