package gen.facts.p;

import gen.facts.p.Wide_.Canonical;
import gen.facts.p.Wide_.Data;
import gen.facts.p.Wide_Heritage.Implement;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Wide;

/// The full metamodel of [Wide], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Wide] inherits has its fact in the metamodel of the supertype that declares it: [Two_].
///
/// A generated class extends or implements [Wide] by the proof its metamodel starts, with the stages of `Wide_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Wide.class, fingerprint = "bb800f3ea85e809e45cf1020177317d77ea00d99899d7a0082cee8d7c206490d", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Wide_ {
    /// The shape of [Wide] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Wide] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Wide_"), "bb800f3ea85e809e45cf1020177317d77ea00d99899d7a0082cee8d7c206490d", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Wide"), List.of(), List.of(), List.of(ClassDesc.of("p.Two")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("b")), Set.of(Signature.of("a"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Wide_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Wide], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Wide].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Wide interface sealed=no nesting=top
        tparams -
        superclasses -
        interfaces p.Two
        supertypes -
        enum -
        members declared-accessible
        member method public overridable a() -> void throws -
        sam b() -> void throws -
        inherit method a() erased () overrides p.Two by interface default p.Wide () -> void throws -
        inherit method b() erased () overrides - by interface abstract p.Two () -> void throws -
        table abstract b()
        table concrete a(); equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Wide].
    public static final InterfaceToken<Wide> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Wide#a()].
    public static final VoidMethodRef0<Wide> a = UnsafeFacts.voidMethod(TOKEN, "a", MemberTraits.OVERRIDABLE);

    /// The fact of the single abstract method [p.Two#b()], which a lambda implements.
    public static final VoidSam0<Wide> sam = UnsafeFacts.voidSam(UnsafeFacts.voidMethod(TOKEN, "b", MemberTraits.ABSTRACT));

    private Wide_() {
    }

    /// Starts the proof that a class being declared implements [Wide]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Implement<Self> implement(RefToken<Self> self) {
        return new Wide_Heritage().implement(TOKEN, self);
    }
}
