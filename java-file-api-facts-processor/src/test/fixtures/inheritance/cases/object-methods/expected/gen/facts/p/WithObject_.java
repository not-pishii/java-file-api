package gen.facts.p;

import gen.facts.p.WithObject_.Canonical;
import gen.facts.p.WithObject_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.WithObject;

/// The full metamodel of [WithObject], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [WithObject] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = WithObject.class, fingerprint = "1608db34426fdf645826fb5390f285f099274659bfb77aa9ff6970e569a10981", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class WithObject_ {
    /// The shape of [WithObject] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [WithObject] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.WithObject_"), "1608db34426fdf645826fb5390f285f099274659bfb77aa9ff6970e569a10981", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.WithObject"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("plain"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("WithObject"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [WithObject], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [WithObject].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.WithObject open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable equals(java.lang.Object) -> boolean throws -\nmember method overridable hashCode() -> int throws -\nmember method overridable plain() -> void throws -\nmember method overridable toString() -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); plain(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor WithObject()\n";

        private Canonical() {
        }
    }

    /// The token of [WithObject].
    public static final OpenClassToken<WithObject> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [WithObject#WithObject()].
    public static final CtorRef0<WithObject> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [WithObject#equals(Object)].
    public static final MethodRef1<WithObject, Bool, Object> equals_Object = UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    /// The fact of [WithObject#hashCode()].
    public static final MethodRef0<WithObject, Int> hashCode = UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    /// The fact of [WithObject#plain()].
    public static final VoidMethodRef0<WithObject> plain = UnsafeFacts.voidMethod(TOKEN, "plain", MemberTraits.OVERRIDABLE);

    /// The fact of [WithObject#toString()].
    public static final MethodRef0<WithObject, String> toString = UnsafeFacts.method(TOKEN, "toString", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private WithObject_() {
    }
}
