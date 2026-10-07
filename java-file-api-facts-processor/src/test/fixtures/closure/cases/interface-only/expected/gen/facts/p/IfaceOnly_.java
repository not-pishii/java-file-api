package gen.facts.p;

import gen.facts.p.IfaceOnly_.Canonical;
import gen.facts.p.IfaceOnly_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.IfaceOnly;

/// The full metamodel of [IfaceOnly], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [IfaceOnly] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = IfaceOnly.class, fingerprint = "1c602c7ab31696527811607d3f5c23bba5ecc7051efff66299674b85fac3ad07", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class IfaceOnly_ {
    /// The shape of [IfaceOnly] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [IfaceOnly] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.IfaceOnly_"), "1c602c7ab31696527811607d3f5c23bba5ecc7051efff66299674b85fac3ad07", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.IfaceOnly"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("name")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [IfaceOnly], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [IfaceOnly].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.IfaceOnly interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract name() -> java.lang.String throws -
        sam name() -> java.lang.String throws -
        table abstract name()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [IfaceOnly].
    public static final InterfaceToken<IfaceOnly> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [IfaceOnly#name()].
    public static final MethodRef0<IfaceOnly, String> name = UnsafeFacts.method(TOKEN, "name", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [IfaceOnly#name()], which a lambda implements.
    public static final Sam0<IfaceOnly, String> sam = UnsafeFacts.sam(name);

    private IfaceOnly_() {
    }
}
