package gen.facts.java.lang;

import gen.facts.java.lang.CharSequence_.Canonical;
import gen.facts.java.lang.CharSequence_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RecordShape;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.Thrown;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [CharSequence]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [CharSequence]: it is only mentioned in the signatures of [p.Picker]. For the facts of its members add `CharSequence.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = CharSequence.class, fingerprint = "78d453ef6496ce51ddbe163ad2cddfa7034cf63e18ea6369345d69c2b2aa2cac", complete = false, format = 21)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class CharSequence_ {
    /// The shape of [CharSequence] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [CharSequence] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.CharSequence_"), "78d453ef6496ce51ddbe163ad2cddfa7034cf63e18ea6369345d69c2b2aa2cac", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("java.lang.CharSequence"), List.of(), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("charAt", Param.fixed(ConstantDescs.CD_int)), Signature.of("length"), Signature.of("subSequence", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("chars"), Signature.of("codePoints"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getChars", Param.fixed(ConstantDescs.CD_int), Param.fixed(ConstantDescs.CD_int), Param.fixed(ClassDesc.ofDescriptor("[C")), Param.fixed(ConstantDescs.CD_int)), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("isEmpty"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("compare", Param.fixed(ClassDesc.of("java.lang.CharSequence")), Param.fixed(ClassDesc.of("java.lang.CharSequence")))), Set.of(), Set.of(), Map.ofEntries(Map.entry(Signature.of("wait"), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))), Map.entry(Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int)), List.of(new Thrown(ClassDesc.of("java.lang.InterruptedException"), List.of(ClassDesc.of("java.lang.Exception"), ClassDesc.of("java.lang.Throwable"), ClassDesc.of("java.lang.Object")), Optional.empty()))))), List.of(), false, List.of(), List.of(), RecordShape.NONE);

        private Data() {
        }
    }

    /// The canonical form of [CharSequence], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [CharSequence].
        static final String TEXT = """
        javafile-facts-canonical 13
        type java.lang.CharSequence interface sealed=no nesting=top
        annotations -
        annotation -
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        record -
        members none
        table abstract charAt(int); length(); subSequence(int, int)
        table concrete chars(); codePoints(); equals(java.lang.Object); getChars(int, int, char[], int); getClass(); hashCode(); isEmpty(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static compare(java.lang.CharSequence, java.lang.CharSequence)
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [CharSequence].
    public static final InterfaceToken<CharSequence> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    private CharSequence_() {
    }
}
