package gen.facts.p;

import gen.facts.p.Canonical_.Canonical;
import gen.facts.p.Canonical_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;

/// The full metamodel of [p.Canonical], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [p.Canonical] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [p.Canonical] by the proof its metamodel starts, with the stages of `Canonical_Heritage`.
///
/// @param <Data_> a type argument of [p.Canonical]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = p.Canonical.class, fingerprint = "06d1a829566c7eda905a52cbaf3aa959397037c77fe2fed12852062ba183bb37", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Canonical_<Data_ extends p.Data> {
    /// The shape of [p.Canonical] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [p.Canonical] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Canonical_"), "06d1a829566c7eda905a52cbaf3aa959397037c77fe2fed12852062ba183bb37", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.Canonical"), List.of(new TypeParam("Data", List.of(Types.of(ClassDesc.of("p.Data"))))), List.of(), List.of(), new Supertypes(List.of(Types.typeVar("Data")), List.of()), new MethodTableTemplate(Set.of(), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), false, new Canonical_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [p.Canonical], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [p.Canonical].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Canonical interface sealed=no nesting=top
        tparams #0 extends p.Data
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        table abstract -
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [p.Canonical] with a wildcard for every type argument.
    public static final InterfaceToken<p.Canonical<?>> ANY = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [p.Canonical] with the type arguments of this metamodel.
    public final InterfaceToken<p.Canonical<Data_>> token;

    /// The metamodel of [p.Canonical] with the type arguments the tokens give.
    ///
    /// @param data_ the token of the type argument `Data_`
    public Canonical_(RefToken<Data_> data_) {
        this.token = UnsafeFacts.interfaceToken(Data.SHAPE, TokenArg.exact(data_));
    }

    /// Starts the proof that a class being declared implements [p.Canonical]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public <Self> Complete<Self, p.Canonical<Data_>> implement(RefToken<Self> self) {
        return new Canonical_Heritage().implement(this.token, self);
    }
}
