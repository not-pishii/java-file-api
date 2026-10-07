package gen.facts.p;

import gen.facts.p.Diamond_.Canonical;
import gen.facts.p.Diamond_.Data;
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
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Diamond;

/// The full metamodel of [Diamond], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Diamond] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// `p.Root`, a supertype that is not `public`, has no metamodel: the `public` members inherited from it are facts of this one.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Diamond.class, fingerprint = "7daf824ff8a061e73e839bfce12394519358b5edf2d1e004c4d128058dd565e8", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Diamond_ {
    /// The shape of [Diamond] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Diamond] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Diamond_"), "7daf824ff8a061e73e839bfce12394519358b5edf2d1e004c4d128058dd565e8", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Diamond"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(ClassDesc.of("p.Left"), ClassDesc.of("p.Right"), ClassDesc.of("p.Root")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("root"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Diamond"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Diamond], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Diamond].
        static final String TEXT = """
        javafile-facts-canonical 6
        type p.Diamond open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces p.Left; p.Right; p.Root
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public static constant int ROOT = 1
        member method public overridable root() -> java.lang.String throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); root(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Diamond()
        """;

        private Canonical() {
        }
    }

    /// The token of [Diamond].
    public static final OpenClassToken<Diamond> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Diamond#ROOT], declared in `p.Root`, which is not `public`.
    public static final StaticFieldRef<Int> ROOT = UnsafeFacts.constantField(TOKEN, "ROOT", PrimitiveToken.INT, 1);

    /// The fact of [Diamond#Diamond()].
    public static final CtorRef0<Diamond> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Diamond#root()], declared in `p.Root`, which is not `public`.
    public static final MethodRef0<Diamond, String> root = UnsafeFacts.method(TOKEN, "root", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Diamond_() {
    }
}
