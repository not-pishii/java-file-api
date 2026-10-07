package gen.facts.p;

import gen.facts.p.X_.Canonical;
import gen.facts.p.X_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.X;

/// The full metamodel of [X], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [X] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_] and [Mid_].
///
/// `p.Own`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = X.class, fingerprint = "78cb0c2d07a56fcbd78eeb77cb2d3529939c44e262f5243553d20c4adab18531", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class X_ {
    /// The shape of [X] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [X] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.X_"), "78cb0c2d07a56fcbd78eeb77cb2d3529939c44e262f5243553d20c4adab18531", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.X"), List.of(), List.of(ClassDesc.of("p.Mid"), ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.HiddenI"), ClassDesc.of("p.Own")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("mid"), Signature.of("more"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("own"), Signature.of("run"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("X"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [X], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [X].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.X open-class sealed=no
        tparams -
        superclasses p.Mid; java.lang.Object
        interfaces p.HiddenI; p.Own
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant int OWN = 2
        member method public overridable more() -> java.lang.String throws -
        member method public overridable own() -> void throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); mid(); more(); notify(); notifyAll(); own(); run(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor X()
        """;

        private Canonical() {
        }
    }

    /// The token of [X].
    public static final OpenClassToken<X> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [X#OWN], declared in `p.Own`, which is not `public`.
    public static final StaticFieldRef<Int> OWN = UnsafeFacts.constantField(TOKEN, "OWN", PrimitiveToken.INT, 2);

    /// The fact of [X#X()].
    public static final CtorRef0<X> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [X#more()].
    public static final MethodRef0<X, String> more = UnsafeFacts.method(TOKEN, "more", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [X#own()], declared in `p.Own`, which is not `public`.
    public static final VoidMethodRef0<X> own = UnsafeFacts.voidMethod(TOKEN, "own", MemberTraits.OVERRIDABLE);

    private X_() {
    }
}
