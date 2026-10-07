package gen.facts.p;

import gen.facts.p.Fn_.Canonical;
import gen.facts.p.Fn_.Data;
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
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Fn;

/// The full metamodel of [Fn], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fn] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fn.class, fingerprint = "fc1852aede7e7d1a1dc570c8a134ff8fe2748d38c354c3759c3a9eec7088b7cd", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Fn_ {
    /// The shape of [Fn] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Fn] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fn_"), "fc1852aede7e7d1a1dc570c8a134ff8fe2748d38c354c3759c3a9eec7088b7cd", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Fn"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Fn], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Fn].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Fn interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract apply(java.lang.String) -> java.lang.String throws -
        sam apply(java.lang.String) -> java.lang.String throws -
        table abstract apply(java.lang.String)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Fn].
    public static final InterfaceToken<Fn> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Fn#apply(String)].
    public static final MethodRef1<Fn, String, String> apply_String = UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Fn#apply(String)], which a lambda implements.
    public static final Sam1<Fn, String, String> sam = UnsafeFacts.sam(apply_String);

    private Fn_() {
    }
}
