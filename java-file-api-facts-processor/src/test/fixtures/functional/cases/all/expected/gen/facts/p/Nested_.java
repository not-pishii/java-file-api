package gen.facts.p;

import gen.facts.p.Nested_.Canonical;
import gen.facts.p.Nested_.Data;
import gen.facts.p.Nested_Heritage.Implement;
import java.io.FileNotFoundException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Nested;

/// The full metamodel of [Nested], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Nested] inherits has its fact in the metamodel of the supertype that declares it: [IoA_] and [NotFoundB_].
///
/// A generated class extends or implements [Nested] by the proof its metamodel starts, with the stages of `Nested_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Nested.class, fingerprint = "9d3e709b58c183c2ed0ac28478ef52179cdd09b61411f7139d46613166ad5a08", complete = true, format = 13)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Nested_ {
    /// The shape of [Nested] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Nested] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Nested_"), "9d3e709b58c183c2ed0ac28478ef52179cdd09b61411f7139d46613166ad5a08", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Nested"), List.of(), List.of(), List.of(ClassDesc.of("p.IoA"), ClassDesc.of("p.NotFoundB")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Nested_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Nested], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Nested].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Nested interface sealed=no
        tparams -
        superclasses -
        interfaces p.IoA; p.NotFoundB
        supertypes -
        enum -
        members declared-accessible
        sam m() -> void throws java.io.FileNotFoundException
        inherit method public abstract p.NotFoundB m() -> void throws java.io.FileNotFoundException erased () overrides p.IoA
        table abstract m()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Nested].
    public static final InterfaceToken<Nested> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of the single abstract method [p.IoA#m()], which a lambda implements.
    public static final VoidSam0<Nested> sam = UnsafeFacts.voidSam(UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<FileNotFoundException>openClassToken(gen.facts.java.io.FileNotFoundException_.Data.SHAPE))));

    private Nested_() {
    }

    /// Starts the proof that a class being declared implements [Nested]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Nested_Heritage().implement(TOKEN, self);
    }
}
