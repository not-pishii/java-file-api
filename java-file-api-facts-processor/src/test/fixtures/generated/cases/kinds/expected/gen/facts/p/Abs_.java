package gen.facts.p;

import gen.facts.p.Abs_.Canonical;
import gen.facts.p.Abs_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.AbstractClassToken;
import me.supcheg.javafile.facts.AbstractCtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.AbstractClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Abs;

/// The full metamodel of [Abs], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Abs] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Abs.class, fingerprint = "373ae4e6be2e76feb601740732623bc56e72a4e73f02cc62c15acf72e5acddf4", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Abs_ {
    /// The shape of [Abs] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Abs] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<AbstractClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Abs_"), "373ae4e6be2e76feb601740732623bc56e72a4e73f02cc62c15acf72e5acddf4", () -> Canonical.TEXT), DeclaredKind.ABSTRACT_CLASS, ClassDesc.of("p.Abs"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Abs"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Abs], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Abs].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Abs abstract-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Abs()\n";

        private Canonical() {
        }
    }

    /// The token of [Abs].
    public static final AbstractClassToken<Abs> TOKEN = UnsafeFacts.abstractClassToken(Data.SHAPE);

    /// The fact of [Abs#Abs()].
    public static final AbstractCtorRef0<Abs> super_ = UnsafeFacts.abstractCtor(TOKEN, MemberTraits.FINAL);

    private Abs_() {
    }
}
