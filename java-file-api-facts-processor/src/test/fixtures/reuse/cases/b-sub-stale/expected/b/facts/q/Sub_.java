package b.facts.q;

import b.facts.q.Sub_.Canonical;
import b.facts.q.Sub_.Data;
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
import q.Sub;

/// The full metamodel of [Sub], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sub] inherits has its fact in the metamodel of the supertype that declares it: [b.facts.p.Dep_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sub.class, fingerprint = "026be0905910df092cd6b93e682c36c58a7485a4701064e91da84882caa0e747", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Sub_ {
    /// The shape of [Sub] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Sub] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("b.facts.q.Sub_"), "026be0905910df092cd6b93e682c36c58a7485a4701064e91da84882caa0e747", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("q.Sub"), List.of(), List.of(ClassDesc.of("p.Dep"), ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("sub"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x"), Signature.of("y")), Set.of(), Set.of(Signature.of("Sub"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Sub], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Sub].
        static final String TEXT = """
        javafile-facts-canonical 6
        type q.Sub open-class sealed=no
        tparams -
        superclasses p.Dep; java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method public overridable sub() -> void throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); sub(); toString(); wait(); wait(long); wait(long, int); x(); y()
        table static -
        table ctor Sub()
        """;

        private Canonical() {
        }
    }

    /// The token of [Sub].
    public static final OpenClassToken<Sub> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Sub#Sub()].
    public static final CtorRef0<Sub> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Sub#sub()].
    public static final VoidMethodRef0<Sub> sub = UnsafeFacts.voidMethod(TOKEN, "sub", MemberTraits.OVERRIDABLE);

    private Sub_() {
    }
}
