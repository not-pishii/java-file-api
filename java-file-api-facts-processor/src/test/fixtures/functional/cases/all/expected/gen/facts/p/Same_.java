package gen.facts.p;

import gen.facts.p.Same_.Canonical;
import gen.facts.p.Same_.Data;
import gen.facts.p.Same_.Data.Inherited;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Same;

/// The full metamodel of [Same], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Same] inherits has its fact in the metamodel of the supertype that declares it: [IoA_] and [IoB_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Same.class, fingerprint = "949ff0e76e762109d052201062039cf046099c81f0ccbb0400916f4cc9cb6274", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Same_ {
    /// The shape of [Same] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Same] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Same_"), "949ff0e76e762109d052201062039cf046099c81f0ccbb0400916f4cc9cb6274", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Same"), List.of(), List.of(), List.of(ClassDesc.of("p.IoA"), ClassDesc.of("p.IoB")), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Same] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Same] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.IoB"), Signature.of("m"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.io.IOException"))), Set.of(List.of()), List.of(ClassDesc.of("p.IoA")));
            }
        }
    }

    /// The canonical form of [Same], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Same].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Same interface sealed=no
        tparams -
        superclasses -
        interfaces p.IoA; p.IoB
        supertypes -
        enum -
        members declared-accessible
        sam m() -> void throws java.io.IOException
        inherit method public abstract p.IoB m() -> void throws java.io.IOException erased () overrides p.IoA
        table abstract m()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Same].
    public static final InterfaceToken<Same> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of the single abstract method [p.IoA#m()], which a lambda implements.
    public static final VoidSam0<Same> sam = UnsafeFacts.voidSam(UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE))));

    private Same_() {
    }
}
