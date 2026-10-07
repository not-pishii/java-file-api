package gen.facts.p;

import gen.facts.p.GoneApi_.Canonical;
import gen.facts.p.GoneApi_.Data;
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
import p.GoneApi;

/// The full metamodel of [GoneApi]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [GoneApi]: it is here as a supertype of [p.Sub], whose inherited members are called through this metamodel.
///
/// A member [GoneApi] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method gone(p.Secret)`, which mentions types that are not public: p.Secret
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = GoneApi.class, fingerprint = "9409b31dd2f40def6954305bef69becdc681ac2e87abb379bbc34059f8d3a546", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class GoneApi_ {
    /// The shape of [GoneApi] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [GoneApi] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.GoneApi_"), "9409b31dd2f40def6954305bef69becdc681ac2e87abb379bbc34059f8d3a546", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.GoneApi"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("gone", Param.fixed(ClassDesc.of("p.Secret"))), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [GoneApi], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [GoneApi].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.GoneApi interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        table abstract -
        table concrete equals(java.lang.Object); getClass(); gone(p.Secret); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [GoneApi].
    public static final InterfaceToken<GoneApi> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private GoneApi_() {
    }
}
