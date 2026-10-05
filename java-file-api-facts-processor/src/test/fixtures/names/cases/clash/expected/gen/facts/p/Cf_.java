package gen.facts.p;

import gen.facts.p.Cf_.Canonical;
import gen.facts.p.Cf_.Data;
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
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Cf;

/// The full metamodel of [Cf], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Cf] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method x(), method x_()`, which would all be named x_
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Cf.class, fingerprint = "4f7dc02d40f1edec606bfa1dace0ef2120e35e0f6c6b934de24a2fc872e50931", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Cf_ {
    /// The shape of [Cf] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Cf] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Cf_"), "4f7dc02d40f1edec606bfa1dace0ef2120e35e0f6c6b934de24a2fc872e50931", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Cf"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x"), Signature.of("x_")), Set.of(), Set.of(Signature.of("Cf"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Cf], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Cf].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Cf open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member field instance mutable int x
        member method overridable other() -> void throws -
        member method overridable x() -> void throws -
        member method overridable x_() -> void throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); other(); toString(); wait(); wait(long); wait(long, int); x(); x_()
        table static -
        table ctor Cf()
        """;

        private Canonical() {
        }
    }

    /// The token of [Cf].
    public static final OpenClassToken<Cf> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Cf#x].
    public static final MutableFieldRef<Cf, Int> x = UnsafeFacts.mutableField(TOKEN, "x", PrimitiveToken.INT);

    /// The fact of [Cf#Cf()].
    public static final CtorRef0<Cf> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Cf#other()].
    public static final VoidMethodRef0<Cf> other = UnsafeFacts.voidMethod(TOKEN, "other", MemberTraits.OVERRIDABLE);

    private Cf_() {
    }
}
