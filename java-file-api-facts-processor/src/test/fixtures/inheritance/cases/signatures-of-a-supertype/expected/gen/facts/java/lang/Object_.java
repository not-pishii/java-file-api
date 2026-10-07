package gen.facts.java.lang;

import gen.facts.java.lang.Object_.Canonical;
import gen.facts.java.lang.Object_.Data;
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
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Bool;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.Prim.Long;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Object]: a fact of every `public` and every `protected` member the type declares, the latter held back for a subclass.
///
/// `@Facts` does not ask for [Object]: it is here as a supertype of [p.Low], whose inherited members are called through this metamodel.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Object.class, fingerprint = "c7165ff1e7ba8865fed43dc79df639f739c894ba7789b4c1ff47a0531c014d66", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Object_ {
    /// The shape of [Object] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Object] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Object_"), "c7165ff1e7ba8865fed43dc79df639f739c894ba7789b4c1ff47a0531c014d66", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("java.lang.Object"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Object"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Object], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Object].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.Object open-class sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        member method protected overridable clone() -> java.lang.Object throws java.lang.CloneNotSupportedException
        member method protected overridable finalize() -> void throws java.lang.Throwable
        member method public final getClass() -> java.lang.Class<?> throws -
        member method public final notify() -> void throws -
        member method public final notifyAll() -> void throws -
        member method public final wait() -> void throws java.lang.InterruptedException
        member method public final wait(long) -> void throws java.lang.InterruptedException
        member method public final wait(long, int) -> void throws java.lang.InterruptedException
        member method public overridable equals(java.lang.Object) -> boolean throws -
        member method public overridable hashCode() -> int throws -
        member method public overridable toString() -> java.lang.String throws -
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Object()
        """;

        private Canonical() {
        }
    }

    /// The token of [Object].
    public static final OpenClassToken<Object> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Object#Object()].
    public static final CtorRef0<Object> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Object#clone()], which is `protected`: a subclass alone uses it.
    public static final Protected<Object, MethodRef0<Object, Object>> clone = UnsafeFacts.protected_(TOKEN, UnsafeFacts.method(TOKEN, "clone", TOKEN, MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<CloneNotSupportedException>openClassToken(CloneNotSupportedException_.Data.SHAPE)).with(Access.PROTECTED)));

    /// The fact of [Object#equals(Object)].
    public static final MethodRef1<Object, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, TOKEN, MemberTraits.OVERRIDABLE);

    /// The fact of [Object#finalize()], which is `protected`: a subclass alone uses it.
    public static final Protected<Object, VoidMethodRef0<Object>> finalize = UnsafeFacts.protected_(TOKEN, UnsafeFacts.voidMethod(TOKEN, "finalize", MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<Throwable>openClassToken(Throwable_.Data.SHAPE)).with(Access.PROTECTED)));

    /// The fact of [Object#getClass()].
    public static final MethodRef0<Object, Class<?>> getClass = UnsafeFacts.method(TOKEN, "getClass", UnsafeFacts.<Class<?>>finalClassToken(Class_.Data.SHAPE, TokenArg.unbounded()), MemberTraits.FINAL);

    /// The fact of [Object#hashCode()].
    public static final MethodRef0<Object, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [Object#notify()].
    public static final VoidMethodRef0<Object> notify = UnsafeFacts.voidMethod(TOKEN, "notify", MemberTraits.FINAL);

    /// The fact of [Object#notifyAll()].
    public static final VoidMethodRef0<Object> notifyAll = UnsafeFacts.voidMethod(TOKEN, "notifyAll", MemberTraits.FINAL);

    /// The fact of [Object#toString()].
    public static final MethodRef0<Object, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [Object#wait()].
    public static final VoidMethodRef0<Object> wait = UnsafeFacts.voidMethod(TOKEN, "wait", MemberTraits.FINAL.throwing(UnsafeFacts.<InterruptedException>openClassToken(InterruptedException_.Data.SHAPE)));

    /// The fact of [Object#wait(long)].
    public static final VoidMethodRef1<Object, Long> wait_long = UnsafeFacts.voidMethod(TOKEN, "wait", PrimitiveToken.LONG, MemberTraits.FINAL.throwing(UnsafeFacts.<InterruptedException>openClassToken(InterruptedException_.Data.SHAPE)));

    /// The fact of [Object#wait(long, int)].
    public static final VoidMethodRef2<Object, Long, Int> wait_long_int = UnsafeFacts.voidMethod(TOKEN, "wait", PrimitiveToken.LONG, PrimitiveToken.INT, MemberTraits.FINAL.throwing(UnsafeFacts.<InterruptedException>openClassToken(InterruptedException_.Data.SHAPE)));

    private Object_() {
    }
}
