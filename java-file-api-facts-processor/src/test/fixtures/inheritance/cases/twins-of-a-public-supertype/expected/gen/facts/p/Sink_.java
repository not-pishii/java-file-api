package gen.facts.p;

import gen.facts.p.Sink_.Canonical;
import gen.facts.p.Sink_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Sink;

/// The full metamodel of [Sink], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Sink] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Sink.class, fingerprint = "94fec02c2f6a6f753e7d65acca036396779841fda59965af881ac108358a2d6e", complete = true, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Sink_ {
    /// The shape of [Sink] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Sink] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Sink_"), "94fec02c2f6a6f753e7d65acca036396779841fda59965af881ac108358a2d6e", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("p.Sink"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("take", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("take", Param.fixed(ClassDesc.of("java.lang.String")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Sink], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Sink].
        static final String TEXT = "javafile-facts-canonical 4\ntype p.Sink final-class sealed=no\ntparams -\nsuperclasses java.lang.Object\nsupertypes -\nenum -\nmembers declared-public\nmember method static take(java.lang.Object) -> java.lang.String throws -\nmember method static take(java.lang.String) -> java.lang.String throws -\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)\ntable static take(java.lang.Object); take(java.lang.String)\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Sink].
    public static final FinalClassToken<Sink> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    /// The fact of [Sink#take(Object)].
    public static final StaticMethodRef1<String, Object> take_Object = UnsafeFacts.staticMethod(TOKEN, "take", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<Object>openClassToken(gen.facts.java.lang.Object_.Data.SHAPE), MemberTraits.FINAL);

    /// The fact of [Sink#take(String)].
    public static final StaticMethodRef1<String, String> take_String = UnsafeFacts.staticMethod(TOKEN, "take", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), MemberTraits.FINAL);

    private Sink_() {
    }
}
