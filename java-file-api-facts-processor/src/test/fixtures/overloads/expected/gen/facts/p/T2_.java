package gen.facts.p;

import gen.facts.p.T2_.Canonical;
import gen.facts.p.T2_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.T2;

/// The full metamodel of [T2], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [T2] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [T2] by the proof its metamodel starts, with the stages of `T2_Heritage`.
///
/// @param <T> a type argument of [T2]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = T2.class, fingerprint = "7635c4c03a05592359822e8826d5358cacafb66a81815fff2aa849b17e60a603", complete = true, format = 12)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class T2_<T> {
    /// The shape of [T2] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [T2] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.T2_"), "7635c4c03a05592359822e8826d5358cacafb66a81815fff2aa849b17e60a603", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.T2"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("T")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("m", Param.var(0)), Signature.of("m", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("one", Param.var(0)), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("T2", Param.var(0)), Signature.of("T2"), Signature.of("T2", Param.fixed(ClassDesc.of("java.lang.String"))))), false, new T2_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [T2], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [T2].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.T2 open-class sealed=no
        tparams #0
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public (#0) throws -
        member ctor public () throws -
        member ctor public (java.lang.String) throws -
        member field public instance final java.lang.String made
        member method public overridable m(#0) -> java.lang.String throws -
        member method public overridable m(java.lang.String) -> java.lang.String throws -
        member method public overridable one(#0) -> java.lang.String throws -
        inherit ctor public (#0) throws -
        inherit ctor public () throws -
        inherit ctor public (java.lang.String) throws -
        inherit method protected concrete java.lang.Object clone() -> java.lang.Object throws java.lang.CloneNotSupportedException erased () overrides -
        inherit method protected concrete java.lang.Object finalize() -> void throws java.lang.Throwable erased () overrides -
        inherit method public concrete java.lang.Object equals(java.lang.Object) -> boolean throws - erased (java.lang.Object) overrides -
        inherit method public concrete java.lang.Object hashCode() -> int throws - erased () overrides -
        inherit method public concrete java.lang.Object toString() -> java.lang.String throws - erased () overrides -
        inherit method public concrete p.T2 m(#0) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public concrete p.T2 m(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        inherit method public concrete p.T2 one(#0) -> java.lang.String throws - erased (java.lang.Object) overrides -
        inherit method public final java.lang.Object getClass() -> java.lang.Class<?> throws - erased () overrides -
        inherit method public final java.lang.Object notify() -> void throws - erased () overrides -
        inherit method public final java.lang.Object notifyAll() -> void throws - erased () overrides -
        inherit method public final java.lang.Object wait() -> void throws java.lang.InterruptedException erased () overrides -
        inherit method public final java.lang.Object wait(long) -> void throws java.lang.InterruptedException erased (long) overrides -
        inherit method public final java.lang.Object wait(long, int) -> void throws java.lang.InterruptedException erased (long, int) overrides -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); m(#0); m(java.lang.String); notify(); notifyAll(); one(#0); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor T2(#0); T2(); T2(java.lang.String)
        """;

        private Canonical() {
        }
    }

    /// The token of [T2] with a wildcard for every type argument.
    public static final OpenClassToken<T2<?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [T2] with the type arguments of this metamodel.
    public final OpenClassToken<T2<T>> token;

    /// The fact of [T2#made].
    public final FieldRef<T2<T>, String> made;

    /// The fact of [T2#T2()].
    public final CtorRef0<T2<T>> new_;

    /// The fact of [T2#T2(String)].
    public final CtorRef1<T2<T>, String> new_String;

    /// The fact of [T2#T2(Object)].
    public final CtorRef1<T2<T>, T> new_T;

    /// The fact of [T2#m(String)].
    public final MethodRef1<T2<T>, String, String> m_String;

    /// The fact of [T2#m(Object)].
    public final MethodRef1<T2<T>, String, T> m_T;

    /// The fact of [T2#one(Object)].
    public final MethodRef1<T2<T>, String, T> one_T;

    /// The metamodel of [T2] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public T2_(RefToken<T> t) {
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(t));
        this.made = UnsafeFacts.field(token, "made", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE));
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.new_String = UnsafeFacts.ctor(token, UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);
        this.new_T = UnsafeFacts.ctor(token, UnsafeFacts.param(t, Param.var(0)), MemberTraits.FINAL);
        this.m_String = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);
        this.m_T = UnsafeFacts.method(token, "m", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
        this.one_T = UnsafeFacts.method(token, "one", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.param(t, Param.var(0)), MemberTraits.OVERRIDABLE);
    }

    /// Starts the proof that a class being declared extends [T2]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Complete<Self, T2<T>> extend(RefToken<Self> self) {
        return new T2_Heritage().extend(this.token, self);
    }
}
