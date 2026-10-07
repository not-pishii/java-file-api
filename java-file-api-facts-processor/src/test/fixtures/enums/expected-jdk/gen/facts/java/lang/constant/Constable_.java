package gen.facts.java.lang.constant;

import gen.facts.java.lang.constant.Constable_.Canonical;
import gen.facts.java.lang.constant.Constable_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [Constable]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [Constable]: it is here as a supertype of [p.Day], [p.Op] and [p.Tok], whose inherited members are called through this metamodel.
///
/// A member [Constable] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Constable.class, fingerprint = "c665c1d4707c50700719bde101377aceb68ea77de07a41982b57ec60f73e8538", complete = true, format = 8)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Constable_ {
    /// The shape of [Constable] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Constable] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.constant.Constable_"), "c665c1d4707c50700719bde101377aceb68ea77de07a41982b57ec60f73e8538", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.constant.Constable"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("describeConstable")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Constable], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Constable].
        static final String TEXT = """
        javafile-facts-canonical 6
        type java.lang.constant.Constable interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member method public abstract describeConstable() -> java.util.Optional<? extends java.lang.constant.ConstantDesc> throws -
        sam describeConstable() -> java.util.Optional<? extends java.lang.constant.ConstantDesc> throws -
        table abstract describeConstable()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [Constable].
    public static final InterfaceToken<Constable> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [Constable#describeConstable()].
    public static final MethodRef0<Constable, Optional<? extends ConstantDesc>> describeConstable = UnsafeFacts.method(TOKEN, "describeConstable", UnsafeFacts.<Optional<? extends ConstantDesc>>finalClassToken(gen.facts.java.util.Optional_.Data.SHAPE, TokenArg.extendsBound(UnsafeFacts.<ConstantDesc>interfaceToken(ConstantDesc_.Data.SHAPE))), MemberTraits.ABSTRACT);

    /// The fact of the single abstract method [Constable#describeConstable()], which a lambda implements.
    public static final Sam0<Constable, Optional<? extends ConstantDesc>> sam = UnsafeFacts.sam(describeConstable);

    private Constable_() {
    }
}
