package gen.facts.p;

import gen.facts.p.Derived_.Canonical;
import gen.facts.p.Derived_.Data;
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
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Derived;

/// The full metamodel of [Derived], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Derived] inherits has its fact in the metamodel of the supertype that declares it: [Api_] and [Base_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Derived.class, fingerprint = "5dd7c2d3c03af8a7f77d2d046b03039161658e38d55f40615cba0a7ee51dbff2", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Derived_ {
    /// The shape of [Derived] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Derived] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Derived_"), "5dd7c2d3c03af8a7f77d2d046b03039161658e38d55f40615cba0a7ee51dbff2", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Derived"), List.of(), List.of(ClassDesc.of("p.Base"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.Api")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("abs"), Signature.of("clone"), Signature.of("dflt"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("f"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("inherited"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("over"), Signature.of("own"), Signature.of("pkg"), Signature.of("prot"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("dstatic"), Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sbase")), Set.of(Signature.of("Derived"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Derived], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Derived].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Derived open-class sealed=no
        tparams -
        superclasses p.Base; java.lang.Object
        interfaces p.Api
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable abs() -> void throws -
        member method public overridable hidden(java.lang.Object) -> void throws -
        member method public overridable over() -> void throws -
        member method public overridable own() -> void throws -
        member method public static dstatic() -> void throws -
        table abstract -
        table concrete abs(); clone(); dflt(); equals(java.lang.Object); f(); finalize(); getClass(); hashCode(); hidden(java.lang.Object); inherited(); notify(); notifyAll(); over(); own(); pkg(); prot(); toString(); wait(); wait(long); wait(long, int)
        table static dstatic(); hidden(java.lang.String); sbase()
        table ctor Derived()
        """;

        private Canonical() {
        }
    }

    /// The token of [Derived].
    public static final OpenClassToken<Derived> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Derived#Derived()].
    public static final CtorRef0<Derived> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Derived#abs()].
    public static final VoidMethodRef0<Derived> abs = UnsafeFacts.voidMethod(TOKEN, "abs", MemberTraits.OVERRIDABLE);

    /// The fact of [Derived#dstatic()].
    public static final VoidStaticMethodRef0 dstatic = UnsafeFacts.voidStaticMethod(TOKEN, "dstatic", MemberTraits.FINAL);

    /// The fact of [Derived#hidden(Object)].
    public static final VoidMethodRef1<Derived, Object> hidden_Object = UnsafeFacts.voidMethod(TOKEN, "hidden", UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Derived#over()].
    public static final VoidMethodRef0<Derived> over = UnsafeFacts.voidMethod(TOKEN, "over", MemberTraits.OVERRIDABLE);

    /// The fact of [Derived#own()].
    public static final VoidMethodRef0<Derived> own = UnsafeFacts.voidMethod(TOKEN, "own", MemberTraits.OVERRIDABLE);

    private Derived_() {
    }
}
