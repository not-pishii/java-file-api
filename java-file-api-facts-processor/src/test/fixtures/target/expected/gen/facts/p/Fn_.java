package gen.facts.p;

import gen.facts.p.Fn_.Canonical;
import gen.facts.p.Fn_.Data;
import gen.facts.p.Fn_.Data.Inherited;
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
import me.supcheg.javafile.facts.Heritage.Result.Of;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.Fn;

/// The full metamodel of [Fn], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Fn] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Fn.class, fingerprint = "e97325ecbe53e1e192e93d3c12163623312d22a80a4d97fabf4c7dcfc06a6897", complete = true, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Fn_ {
    /// The shape of [Fn] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Fn] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Fn_"), "e97325ecbe53e1e192e93d3c12163623312d22a80a4d97fabf4c7dcfc06a6897", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Fn"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, () -> Inherited.HERITAGE);

        private Data() {
        }

        /// What a class that extends or implements [Fn] inherits, loaded only when one is declared.
        @GeneratedMetamodelPart
        static final class Inherited {
            /// Every constructor and method of [Fn] that is not private, as a member of it, whoever declares it.
            static final Told HERITAGE = new Told(List.of(m0()), List.of());

            private Inherited() {
            }

            private static Method m0() {
                return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.Fn"), Signature.of("apply", Param.fixed(ClassDesc.of("java.lang.String"))), List.of(), List.of(Types.of(ClassDesc.of("java.lang.String"))), Arity.FIXED, new Of(Types.of(ClassDesc.of("java.lang.String"))), List.of(), Set.of(List.of(ClassDesc.of("java.lang.String"))), List.of());
            }
        }
    }

    /// The canonical form of [Fn], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Fn].
        static final String TEXT = """
        javafile-facts-canonical 7
        type p.Fn interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract apply(java.lang.String) -> java.lang.String throws -
        sam apply(java.lang.String) -> java.lang.String throws -
        inherit method public abstract p.Fn apply(java.lang.String) -> java.lang.String throws - erased (java.lang.String) overrides -
        table abstract apply(java.lang.String)
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Fn].
    public static final InterfaceToken<Fn> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Fn#apply(String)].
    public static final MethodRef1<Fn, String, String> apply_String = UnsafeFacts.method(TOKEN, "apply", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Fn#apply(String)], which a lambda implements.
    public static final Sam1<Fn, String, String> sam = UnsafeFacts.sam(apply_String);

    private Fn_() {
    }
}
