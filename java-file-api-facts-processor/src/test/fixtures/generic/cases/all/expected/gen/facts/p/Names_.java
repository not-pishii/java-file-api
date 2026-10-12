package gen.facts.p;

import gen.facts.p.Names_.Canonical;
import gen.facts.p.Names_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
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
import p.Names;

/// The full metamodel of [Names], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Names] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Names] by the proof its metamodel starts, with the stages of `Names_Heritage`.
///
/// @param <List__> a type argument of [Names]
/// @param <UnsafeFacts_> a type argument of [Names]
/// @param <Token> a type argument of [Names]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Names.class, fingerprint = "cc9e058fcb2d3fbaa22842e10200c3d8cb53d46d8955363e88e8b179ed4bd3eb", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Names_<List__, UnsafeFacts_, Token> {
    /// The shape of [Names] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Names] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Names_"), "cc9e058fcb2d3fbaa22842e10200c3d8cb53d46d8955363e88e8b179ed4bd3eb", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Names"), List.of(new TypeParam("List", List.of()), new TypeParam("UnsafeFacts", List.of()), new TypeParam("Token", List.of())), List.of(ClassDesc.of("java.lang.Object")), List.of(), new Supertypes(List.of(Types.typeVar("List"), Types.typeVar("UnsafeFacts"), Types.typeVar("Token")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("all"), Signature.of("clone"), Signature.of("data", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Names"))), false, new Names_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Names], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Names].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Names open-class sealed=no nesting=top
        tparams #0; #1; #2
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member field public instance mutable #0 first
        member field public instance mutable #2 token
        member method public overridable <^0> data(^0) -> ^0 throws -
        member method public overridable all() -> java.util.List<#1> throws -
        inherit ctor Names() public () throws -
        inherit field public p.Names first
        inherit field public p.Names token
        inherit method all() erased () overrides - by class public concrete p.Names () -> java.util.List<#1> throws -
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method data(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete p.Names <^0> (^0) -> ^0 throws -
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete all(); clone(); data(java.lang.Object); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Names()
        """;

        private Canonical() {
        }
    }

    /// The token of [Names] with a wildcard for every type argument.
    public static final OpenClassToken<Names<?, ?, ?>> ANY = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.unbounded(), TokenArg.unbounded(), TokenArg.unbounded());

    /// The token of [Names] with the type arguments of this metamodel.
    public final OpenClassToken<Names<List__, UnsafeFacts_, Token>> token;

    /// The fact of [Names#first].
    public final MutableFieldRef<Names<List__, UnsafeFacts_, Token>, List__> first;

    /// The fact of [Names#token].
    public final MutableFieldRef<Names<List__, UnsafeFacts_, Token>, Token> token_;

    /// The fact of [Names#Names()].
    public final CtorRef0<Names<List__, UnsafeFacts_, Token>> new_;

    /// The fact of [Names#all()].
    public final MethodRef0<Names<List__, UnsafeFacts_, Token>, List<UnsafeFacts_>> all;

    private final RefToken<List__> list__;

    private final RefToken<UnsafeFacts_> unsafeFacts_;

    private final RefToken<Token> token__;

    /// The metamodel of [Names] with the type arguments the tokens give.
    ///
    /// @param list__ the token of the type argument `List__`
    /// @param unsafeFacts_ the token of the type argument `UnsafeFacts_`
    /// @param token__ the token of the type argument `Token`
    public Names_(RefToken<List__> list__, RefToken<UnsafeFacts_> unsafeFacts_, RefToken<Token> token__) {
        this.list__ = list__;
        this.unsafeFacts_ = unsafeFacts_;
        this.token__ = token__;
        this.token = UnsafeFacts.openClassToken(Data.SHAPE, TokenArg.exact(list__), TokenArg.exact(unsafeFacts_), TokenArg.exact(token__));
        this.first = UnsafeFacts.mutableField(token, "first", list__);
        this.token_ = UnsafeFacts.mutableField(token, "token", token__);
        this.new_ = UnsafeFacts.ctor(token, MemberTraits.FINAL);
        this.all = UnsafeFacts.method(token, "all", UnsafeFacts.<List<UnsafeFacts_>>interfaceToken(gen.facts.java.util.List_.Data.SHAPE, TokenArg.exact(unsafeFacts_)), MemberTraits.OVERRIDABLE);
    }

    /// The fact of [Names#data(Object)], for the type arguments the tokens give.
    ///
    /// @param <Data_> a type argument of the method
    /// @param data_ the token of the type argument `Data_`
    /// @return the fact
    public <Data_> MethodRef1<Names<List__, UnsafeFacts_, Token>, Data_, Data_> data_Data(RefToken<Data_> data_) {
        return UnsafeFacts.method(token, "data", data_, UnsafeFacts.param(data_, Param.fixed(ClassDesc.of("java.lang.Object"))), MemberTraits.OVERRIDABLE.withTypeArgs(data_));
    }

    /// Starts the proof that a class being declared extends [Names]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Complete<Self, Names<List__, UnsafeFacts_, Token>> extend(RefToken<Self> self) {
        return new Names_Heritage().extend(this.token, self);
    }
}
