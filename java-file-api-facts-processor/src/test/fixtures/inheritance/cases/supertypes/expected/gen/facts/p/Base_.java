package gen.facts.p;

import gen.facts.p.Base_.Canonical;
import gen.facts.p.Base_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.Complete;
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
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Base;

/// The full metamodel of [Base]: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// `@Facts` does not ask for [Base]: it is here as a supertype of [p.Derived], whose inherited members are called through this metamodel.
///
/// A member [Base] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Base] by the proof its metamodel starts, with the stages of `Base_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Base.class, fingerprint = "dc54c327840af68aaca018d16ece32cb73c50c8f6fd790049d47490d47f126bf", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Base_ {
    /// The shape of [Base] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Base] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Base_"), "dc54c327840af68aaca018d16ece32cb73c50c8f6fd790049d47490d47f126bf", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Base"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("f"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("inherited"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("over"), Signature.of("pkg"), Signature.of("prot"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sbase")), Set.of(Signature.of("Base"))), false, new Base_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Base], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Base].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Base open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method protected overridable prot() -> void throws -
        member method public overridable f() -> int throws -
        member method public overridable inherited() -> void throws -
        member method public overridable over() -> void throws -
        member method public static hidden(java.lang.String) -> void throws -
        member method public static sbase() -> int throws -
        inherit ctor public () throws -
        inherit method package concrete p.Base pkg() -> void throws - erased () overrides -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method protected concrete p.Base prot() -> void throws - erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.Base f() -> int throws - erased () overrides -
        inherit method public concrete p.Base inherited() -> void throws - erased () overrides -
        inherit method public concrete p.Base over() -> void throws - erased () overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        inherit method public static p.Base hidden(java.lang.String) -> void throws - erased (java.lang.String) overrides -
        inherit method public static p.Base sbase() -> int throws - erased () overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); f(); finalize(); getClass(); hashCode(); inherited(); notify(); notifyAll(); over(); pkg(); prot(); toString(); wait(); wait(long); wait(long, int)
        table static hidden(java.lang.String); sbase()
        table ctor Base()
        """;

        private Canonical() {
        }
    }

    /// The token of [Base].
    public static final OpenClassToken<Base> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Base#Base()].
    public static final CtorRef0<Base> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Base#f()].
    public static final MethodRef0<Base, Int> f = UnsafeFacts.method(TOKEN, "f", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [Base#hidden(String)].
    public static final VoidStaticMethodRef1<String> hidden_String = UnsafeFacts.voidStaticMethod(TOKEN, "hidden", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Base#inherited()].
    public static final VoidMethodRef0<Base> inherited = UnsafeFacts.voidMethod(TOKEN, "inherited", MemberTraits.OVERRIDABLE);

    /// The fact of [Base#over()].
    public static final VoidMethodRef0<Base> over = UnsafeFacts.voidMethod(TOKEN, "over", MemberTraits.OVERRIDABLE);

    /// The fact of [Base#prot()], which is `protected`: a subclass alone uses it.
    public static final Protected<Base, VoidMethodRef0<Base>> prot = UnsafeFacts.protected_(TOKEN, UnsafeFacts.voidMethod(TOKEN, "prot", MemberTraits.OVERRIDABLE.with(Access.PROTECTED)));

    /// The fact of [Base#sbase()].
    public static final StaticMethodRef0<Int> sbase = UnsafeFacts.staticMethod(TOKEN, "sbase", PrimitiveToken.INT, MemberTraits.FINAL);

    private Base_() {
    }

    /// Starts the proof that a class being declared extends [Base]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Base> extend(RefToken<Self> self) {
        return new Base_Heritage().extend(TOKEN, self);
    }
}
