package gen.facts.p;

import gen.facts.p.Supertypes_.Canonical;
import gen.facts.p.Supertypes_.Data;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim.Double;
import me.supcheg.javafile.facts.Prim.Float;
import me.supcheg.javafile.facts.Prim.Int;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import p.Other;
import p.Supertypes;

/// The full metamodel of [Supertypes], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Supertypes] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Supertypes.class, fingerprint = "4c8e29081a51cab688086e92237982776ea0773bc4dd1cf4484fb753daeab010", complete = true, format = 6)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Supertypes_ {
    /// The shape of [Supertypes] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Supertypes] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Supertypes_"), "4c8e29081a51cab688086e92237982776ea0773bc4dd1cf4484fb753daeab010", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Supertypes"), List.of(), List.of(ClassDesc.of("java.lang.Object")), me.supcheg.javafile.facts.Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("other", Param.fixed(ClassDesc.of("java.awt.List")), Param.fixed(ClassDesc.ofDescriptor("[I")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/String;"))), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Supertypes"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Supertypes], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Supertypes].
        static final String TEXT = "javafile-facts-canonical 5\ntype p.Supertypes open-class sealed=no\ntparams -\nsuperclasses java.lang.Object\ninterfaces -\nsupertypes -\nenum -\nmembers declared-public\nmember ctor() throws -\nmember field instance mutable int ArrayToken\nmember field instance mutable int Canonical\nmember field instance mutable int ClassDesc\nmember field instance mutable int ConstantDescs\nmember field instance mutable int Data\nmember field instance mutable int DeclaredKind\nmember field instance mutable int Int\nmember field instance mutable int List\nmember field instance mutable int MemberTraits\nmember field instance mutable int Metamodel\nmember field instance mutable int MethodTableTemplate\nmember field instance mutable int MutableFieldRef\nmember field instance mutable int OpenClassToken\nmember field instance mutable int Other_\nmember field instance mutable int Param\nmember field instance mutable int Prim\nmember field instance mutable int PrimitiveToken\nmember field instance mutable int SHAPE\nmember field instance mutable int Set\nmember field instance mutable int Signature\nmember field instance mutable int String\nmember field instance mutable int TEXT\nmember field instance mutable int TOKEN\nmember field instance mutable int TypeParam\nmember field instance mutable int TypeShape\nmember field instance mutable int Types\nmember field instance mutable int UnsafeFacts\nmember field instance mutable int gen\nmember field instance mutable int java\nmember field instance mutable int me\nmember field instance mutable int p\nmember field static constant double Double = NaN\nmember field static constant float Float = NaNf\nmember field static constant java.lang.String NAME = \"n\"\nmember method overridable other(java.awt.List, int[], java.lang.String[]) -> p.Other throws java.io.IOException\ntable abstract -\ntable concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); other(java.awt.List, int[], java.lang.String[]); toString(); wait(); wait(long); wait(long, int)\ntable static -\ntable ctor Supertypes()\n";

        private Canonical() {
        }
    }

    /// The token of [Supertypes].
    public static final OpenClassToken<Supertypes> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Supertypes#ArrayToken].
    public static final MutableFieldRef<Supertypes, Int> ArrayToken_ = UnsafeFacts.mutableField(TOKEN, "ArrayToken", PrimitiveToken.INT);

    /// The fact of [Supertypes#Canonical].
    public static final MutableFieldRef<Supertypes, Int> Canonical_ = UnsafeFacts.mutableField(TOKEN, "Canonical", PrimitiveToken.INT);

    /// The fact of [Supertypes#ClassDesc].
    public static final MutableFieldRef<Supertypes, Int> ClassDesc_ = UnsafeFacts.mutableField(TOKEN, "ClassDesc", PrimitiveToken.INT);

    /// The fact of [Supertypes#ConstantDescs].
    public static final MutableFieldRef<Supertypes, Int> ConstantDescs_ = UnsafeFacts.mutableField(TOKEN, "ConstantDescs", PrimitiveToken.INT);

    /// The fact of [Supertypes#Data].
    public static final MutableFieldRef<Supertypes, Int> Data_ = UnsafeFacts.mutableField(TOKEN, "Data", PrimitiveToken.INT);

    /// The fact of [Supertypes#DeclaredKind].
    public static final MutableFieldRef<Supertypes, Int> DeclaredKind_ = UnsafeFacts.mutableField(TOKEN, "DeclaredKind", PrimitiveToken.INT);

    /// The fact of [Supertypes#Double].
    public static final StaticFieldRef<Double> Double_ = UnsafeFacts.constantField(TOKEN, "Double", PrimitiveToken.DOUBLE, java.lang.Double.longBitsToDouble(9221120237041090560L));

    /// The fact of [Supertypes#Float].
    public static final StaticFieldRef<Float> Float_ = UnsafeFacts.constantField(TOKEN, "Float", PrimitiveToken.FLOAT, java.lang.Float.intBitsToFloat(2143289344));

    /// The fact of [Supertypes#Int].
    public static final MutableFieldRef<Supertypes, Int> Int_ = UnsafeFacts.mutableField(TOKEN, "Int", PrimitiveToken.INT);

    /// The fact of [Supertypes#List].
    public static final MutableFieldRef<Supertypes, Int> List_ = UnsafeFacts.mutableField(TOKEN, "List", PrimitiveToken.INT);

    /// The fact of [Supertypes#MemberTraits].
    public static final MutableFieldRef<Supertypes, Int> MemberTraits_ = UnsafeFacts.mutableField(TOKEN, "MemberTraits", PrimitiveToken.INT);

    /// The fact of [Supertypes#Metamodel].
    public static final MutableFieldRef<Supertypes, Int> Metamodel_ = UnsafeFacts.mutableField(TOKEN, "Metamodel", PrimitiveToken.INT);

    /// The fact of [Supertypes#MethodTableTemplate].
    public static final MutableFieldRef<Supertypes, Int> MethodTableTemplate_ = UnsafeFacts.mutableField(TOKEN, "MethodTableTemplate", PrimitiveToken.INT);

    /// The fact of [Supertypes#MutableFieldRef].
    public static final MutableFieldRef<Supertypes, Int> MutableFieldRef_ = UnsafeFacts.mutableField(TOKEN, "MutableFieldRef", PrimitiveToken.INT);

    /// The fact of [Supertypes#NAME].
    public static final StaticFieldRef<String> NAME = UnsafeFacts.constantField(TOKEN, "NAME", UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE), "n");

    /// The fact of [Supertypes#OpenClassToken].
    public static final MutableFieldRef<Supertypes, Int> OpenClassToken_ = UnsafeFacts.mutableField(TOKEN, "OpenClassToken", PrimitiveToken.INT);

    /// The fact of [Supertypes#Other_].
    public static final MutableFieldRef<Supertypes, Int> Other__ = UnsafeFacts.mutableField(TOKEN, "Other_", PrimitiveToken.INT);

    /// The fact of [Supertypes#Param].
    public static final MutableFieldRef<Supertypes, Int> Param_ = UnsafeFacts.mutableField(TOKEN, "Param", PrimitiveToken.INT);

    /// The fact of [Supertypes#Prim].
    public static final MutableFieldRef<Supertypes, Int> Prim = UnsafeFacts.mutableField(TOKEN, "Prim", PrimitiveToken.INT);

    /// The fact of [Supertypes#PrimitiveToken].
    public static final MutableFieldRef<Supertypes, Int> PrimitiveToken_ = UnsafeFacts.mutableField(TOKEN, "PrimitiveToken", PrimitiveToken.INT);

    /// The fact of [Supertypes#SHAPE].
    public static final MutableFieldRef<Supertypes, Int> SHAPE_ = UnsafeFacts.mutableField(TOKEN, "SHAPE", PrimitiveToken.INT);

    /// The fact of [Supertypes#Set].
    public static final MutableFieldRef<Supertypes, Int> Set_ = UnsafeFacts.mutableField(TOKEN, "Set", PrimitiveToken.INT);

    /// The fact of [Supertypes#Signature].
    public static final MutableFieldRef<Supertypes, Int> Signature_ = UnsafeFacts.mutableField(TOKEN, "Signature", PrimitiveToken.INT);

    /// The fact of [Supertypes#String].
    public static final MutableFieldRef<Supertypes, Int> String_ = UnsafeFacts.mutableField(TOKEN, "String", PrimitiveToken.INT);

    /// The fact of [Supertypes#TEXT].
    public static final MutableFieldRef<Supertypes, Int> TEXT_ = UnsafeFacts.mutableField(TOKEN, "TEXT", PrimitiveToken.INT);

    /// The fact of [Supertypes#TOKEN].
    public static final MutableFieldRef<Supertypes, Int> TOKEN_ = UnsafeFacts.mutableField(TOKEN, "TOKEN", PrimitiveToken.INT);

    /// The fact of [Supertypes#TypeParam].
    public static final MutableFieldRef<Supertypes, Int> TypeParam = UnsafeFacts.mutableField(TOKEN, "TypeParam", PrimitiveToken.INT);

    /// The fact of [Supertypes#TypeShape].
    public static final MutableFieldRef<Supertypes, Int> TypeShape_ = UnsafeFacts.mutableField(TOKEN, "TypeShape", PrimitiveToken.INT);

    /// The fact of [Supertypes#Types].
    public static final MutableFieldRef<Supertypes, Int> Types = UnsafeFacts.mutableField(TOKEN, "Types", PrimitiveToken.INT);

    /// The fact of [Supertypes#UnsafeFacts].
    public static final MutableFieldRef<Supertypes, Int> UnsafeFacts_ = UnsafeFacts.mutableField(TOKEN, "UnsafeFacts", PrimitiveToken.INT);

    /// The fact of [Supertypes#gen].
    public static final MutableFieldRef<Supertypes, Int> gen_ = UnsafeFacts.mutableField(TOKEN, "gen", PrimitiveToken.INT);

    /// The fact of [Supertypes#java].
    public static final MutableFieldRef<Supertypes, Int> java_ = UnsafeFacts.mutableField(TOKEN, "java", PrimitiveToken.INT);

    /// The fact of [Supertypes#me].
    public static final MutableFieldRef<Supertypes, Int> me_ = UnsafeFacts.mutableField(TOKEN, "me", PrimitiveToken.INT);

    /// The fact of [Supertypes#p].
    public static final MutableFieldRef<Supertypes, Int> p = UnsafeFacts.mutableField(TOKEN, "p", PrimitiveToken.INT);

    /// The fact of [Supertypes#Supertypes()].
    public static final CtorRef0<Supertypes> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Supertypes#other(java.awt.List, int\[\], String\[\])].
    public static final MethodRef3<Supertypes, Other, java.awt.List, int[], String[]> other_List_intArray_StringArray = UnsafeFacts.method(TOKEN, "other", UnsafeFacts.<Other>openClassToken(Other_.Data.SHAPE), UnsafeFacts.<java.awt.List>openClassToken(gen.facts.java.awt.List_.Data.SHAPE), PrimitiveToken.INT.array(), ArrayToken.of(UnsafeFacts.<String>finalClassToken(gen.facts.java.lang.String_.Data.SHAPE)), MemberTraits.OVERRIDABLE.throwing(UnsafeFacts.<IOException>openClassToken(gen.facts.java.io.IOException_.Data.SHAPE)));

    private Supertypes_() {
    }
}
