package a.facts.p;

import a.facts.p.Dep_.Canonical;
import a.facts.p.Dep_.Data;
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
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Dep;

/// The full metamodel of [Dep], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Dep] inherits has its fact in the metamodel of the supertype that declares it: [a.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Dep.class, fingerprint = "b8584b3c2753fb92279a156250c8789c0949960763fb16fcef24bf0c988c315a", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Dep_ {
    /// The shape of [Dep] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Dep] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("a.facts.p.Dep_"), "b8584b3c2753fb92279a156250c8789c0949960763fb16fcef24bf0c988c315a", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Dep"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), Signature.of("x")), Set.of(), Set.of(Signature.of("Dep"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Dep], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Dep].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Dep open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember method overridable x() -> int throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int); x()\ntable static -\ntable ctor Dep()\n";

        private Canonical() {
        }
    }

    /// The token of [Dep].
    public static final OpenClassToken<Dep> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Dep#Dep()].
    public static final CtorRef0<Dep> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Dep#x()].
    public static final MethodRef0<Dep, Int> x = UnsafeFacts.method(TOKEN, "x", PrimitiveToken.INT, MemberTraits.OVERRIDABLE);

    private Dep_() {
    }
}
