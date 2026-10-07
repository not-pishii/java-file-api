package gen.facts.p;

import gen.facts.p.Base_.Canonical;
import gen.facts.p.Base_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Access;
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
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Base.class, fingerprint = "0026bdacdf9c6a33cb270a59b902ea3c6c49c045e38f8f2fdff5c88cda5a75c4", complete = true, format = 8)
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
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Base_"), "0026bdacdf9c6a33cb270a59b902ea3c6c49c045e38f8f2fdff5c88cda5a75c4", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Base"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("f"), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("inherited"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("over"), Signature.of("pkg"), Signature.of("prot"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("hidden", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("sbase")), Set.of(Signature.of("Base"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Base], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Base].
        static final String TEXT = """
        javafile-facts-canonical 6
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
}
