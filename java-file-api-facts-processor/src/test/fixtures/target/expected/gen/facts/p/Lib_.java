package gen.facts.p;

import gen.facts.p.Lib_.Canonical;
import gen.facts.p.Lib_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Lib;
import p.Marker;

/// The full metamodel of [Lib], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Lib] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Lib.class, fingerprint = "dc02911aa4399529dc8684648c2f33a7318b4d05b7af6d2dfb00222e461d151e", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Lib_ {
    /// The shape of [Lib] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Lib] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Lib_"), "dc02911aa4399529dc8684648c2f33a7318b4d05b7af6d2dfb00222e461d151e", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Lib"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("take", Param.fixed(ClassDesc.of("p.Marker")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Lib], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Lib].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Lib final-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public static take(p.Marker) -> java.lang.String throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static take(p.Marker)
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Lib].
    public static final FinalClassToken<Lib> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [Lib#take(Marker)].
    public static final StaticMethodRef1<String, Marker> take_Marker = UnsafeFacts.staticMethod(TOKEN, "take", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Marker>interfaceToken(Marker_.Data.SHAPE), MemberTraits.FINAL);

    private Lib_() {
    }
}
