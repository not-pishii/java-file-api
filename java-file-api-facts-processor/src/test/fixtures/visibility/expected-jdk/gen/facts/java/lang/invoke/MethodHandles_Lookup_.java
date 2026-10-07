package gen.facts.java.lang.invoke;

import gen.facts.java.lang.invoke.MethodHandles_Lookup_.Canonical;
import gen.facts.java.lang.invoke.MethodHandles_Lookup_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;

/// The token-only metamodel of [Lookup]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Lookup]: it is only mentioned in the signatures of [Integer] and [java.lang.constant.ConstantDesc]. For the facts of its members add `MethodHandles.Lookup.class` to `@Facts`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Lookup.class, fingerprint = "5ca41d3d53f4e061c29fbdbe99d67d5db492aab255b0936148493534154de047", complete = false, format = 9)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class MethodHandles_Lookup_ {
    /// The shape of [Lookup] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Lookup] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.invoke.MethodHandles_Lookup_"), "5ca41d3d53f4e061c29fbdbe99d67d5db492aab255b0936148493534154de047", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.invoke.MethodHandles$Lookup"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("accessClass", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("accessFailedMessage", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.invoke.MemberName"))), Signature.of("bind", Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.invoke.MethodType"))), Signature.of("checkAccess", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.invoke.MemberName"))), Signature.of("checkField", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.invoke.MemberName"))), Signature.of("checkMethod", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.invoke.MemberName"))), Signature.of("checkMethodName", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("checkSymbolicClass", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("clone"), Signature.of("defineClass", Param.fixed(ClassDesc.ofDescriptor("[B"))), Signature.of("defineHiddenClass", Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/invoke/MethodHandles$Lookup$ClassOption;"))), Signature.of("defineHiddenClassWithClassData", Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ClassDesc.of("java.lang.Object")), Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/invoke/MethodHandles$Lookup$ClassOption;"))), Signature.of("dropLookupMode", Param.fixed(ConstantDescs.CD_int)), Signature.of("ensureInitialized", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("findBoundCallerLookup", Param.fixed(ClassDesc.of("java.lang.invoke.MemberName"))), Signature.of("findClass", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("findConstructor", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.invoke.MethodType"))), Signature.of("findGetter", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("findSetter", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("findSpecial", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.invoke.MethodType")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("findStatic", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.invoke.MethodType"))), Signature.of("findStaticGetter", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("findStaticSetter", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("findStaticVarHandle", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("findVarHandle", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("findVirtual", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.invoke.MethodType"))), Signature.of("getClass"), Signature.of("hasFullPrivilegeAccess"), Signature.of("hasPrivateAccess"), Signature.of("hashCode"), Signature.of("in", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("isClassAccessible", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("linkMethodHandleConstant", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("lookupClass"), Signature.of("lookupModes"), Signature.of("makeClassDefiner", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ClassDesc.of("jdk.internal.util.ClassFileDumper"))), Signature.of("makeHiddenClassDefiner", Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ClassDesc.of("jdk.internal.util.ClassFileDumper"))), Signature.of("makeHiddenClassDefiner", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ClassDesc.of("jdk.internal.util.ClassFileDumper"))), Signature.of("makeHiddenClassDefiner", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ClassDesc.of("jdk.internal.util.ClassFileDumper")), Param.fixed(ConstantDescs.CD_int)), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("previousLookupClass"), Signature.of("resolveOrFail", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("resolveOrFail", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.invoke.MethodType"))), Signature.of("resolveOrFail", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.invoke.MemberName"))), Signature.of("resolveOrNull", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.of("java.lang.invoke.MethodType"))), Signature.of("resolveOrNull", Param.fixed(ConstantDescs.CD_byte), Param.fixed(ClassDesc.of("java.lang.invoke.MemberName"))), Signature.of("revealDirect", Param.fixed(ClassDesc.of("java.lang.invoke.MethodHandle"))), Signature.of("serializableConstructor", Param.fixed(ClassDesc.of("java.lang.Class")), Param.fixed(ClassDesc.of("java.lang.reflect.Constructor"))), Signature.of("toString"), Signature.of("unreflect", Param.fixed(ClassDesc.of("java.lang.reflect.Method"))), Signature.of("unreflectConstructor", Param.fixed(ClassDesc.of("java.lang.reflect.Constructor"))), Signature.of("unreflectGetter", Param.fixed(ClassDesc.of("java.lang.reflect.Field"))), Signature.of("unreflectSetter", Param.fixed(ClassDesc.of("java.lang.reflect.Field"))), Signature.of("unreflectSpecial", Param.fixed(ClassDesc.of("java.lang.reflect.Method")), Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("unreflectVarHandle", Param.fixed(ClassDesc.of("java.lang.reflect.Field"))), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("validateAndFindInternalName", Param.fixed(ClassDesc.ofDescriptor("[B")), Param.fixed(ClassDesc.of("java.lang.String")))), Set.of(Signature.of("MethodHandles$Lookup", Param.fixed(ClassDesc.of("java.lang.Class"))))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Lookup], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Lookup].
        static final String TEXT = """
        javafile-facts-canonical 7
        type java.lang.invoke.MethodHandles$Lookup final-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members none
        table abstract -
        table concrete accessClass(java.lang.Class); accessFailedMessage(java.lang.Class, java.lang.invoke.MemberName); bind(java.lang.Object, java.lang.String, java.lang.invoke.MethodType); checkAccess(byte, java.lang.Class, java.lang.invoke.MemberName); checkField(byte, java.lang.Class, java.lang.invoke.MemberName); checkMethod(byte, java.lang.Class, java.lang.invoke.MemberName); checkMethodName(byte, java.lang.String); checkSymbolicClass(java.lang.Class); clone(); defineClass(byte[]); defineHiddenClass(byte[], boolean, java.lang.invoke.MethodHandles$Lookup$ClassOption[]); defineHiddenClassWithClassData(byte[], java.lang.Object, boolean, java.lang.invoke.MethodHandles$Lookup$ClassOption[]); dropLookupMode(int); ensureInitialized(java.lang.Class); equals(java.lang.Object); finalize(); findBoundCallerLookup(java.lang.invoke.MemberName); findClass(java.lang.String); findConstructor(java.lang.Class, java.lang.invoke.MethodType); findGetter(java.lang.Class, java.lang.String, java.lang.Class); findSetter(java.lang.Class, java.lang.String, java.lang.Class); findSpecial(java.lang.Class, java.lang.String, java.lang.invoke.MethodType, java.lang.Class); findStatic(java.lang.Class, java.lang.String, java.lang.invoke.MethodType); findStaticGetter(java.lang.Class, java.lang.String, java.lang.Class); findStaticSetter(java.lang.Class, java.lang.String, java.lang.Class); findStaticVarHandle(java.lang.Class, java.lang.String, java.lang.Class); findVarHandle(java.lang.Class, java.lang.String, java.lang.Class); findVirtual(java.lang.Class, java.lang.String, java.lang.invoke.MethodType); getClass(); hasFullPrivilegeAccess(); hasPrivateAccess(); hashCode(); in(java.lang.Class); isClassAccessible(java.lang.Class); linkMethodHandleConstant(byte, java.lang.Class, java.lang.String, java.lang.Object); lookupClass(); lookupModes(); makeClassDefiner(java.lang.String, byte[], jdk.internal.util.ClassFileDumper); makeHiddenClassDefiner(byte[], jdk.internal.util.ClassFileDumper); makeHiddenClassDefiner(java.lang.String, byte[], jdk.internal.util.ClassFileDumper); makeHiddenClassDefiner(java.lang.String, byte[], jdk.internal.util.ClassFileDumper, int); notify(); notifyAll(); previousLookupClass(); resolveOrFail(byte, java.lang.Class, java.lang.String, java.lang.Class); resolveOrFail(byte, java.lang.Class, java.lang.String, java.lang.invoke.MethodType); resolveOrFail(byte, java.lang.invoke.MemberName); resolveOrNull(byte, java.lang.Class, java.lang.String, java.lang.invoke.MethodType); resolveOrNull(byte, java.lang.invoke.MemberName); revealDirect(java.lang.invoke.MethodHandle); serializableConstructor(java.lang.Class, java.lang.reflect.Constructor); toString(); unreflect(java.lang.reflect.Method); unreflectConstructor(java.lang.reflect.Constructor); unreflectGetter(java.lang.reflect.Field); unreflectSetter(java.lang.reflect.Field); unreflectSpecial(java.lang.reflect.Method, java.lang.Class); unreflectVarHandle(java.lang.reflect.Field); wait(); wait(long); wait(long, int)
        table static validateAndFindInternalName(byte[], java.lang.String)
        table ctor MethodHandles$Lookup(java.lang.Class)
        """;

        private Canonical() {
        }
    }

    /// The token of [Lookup].
    public static final FinalClassToken<Lookup> TOKEN = UnsafeFacts.finalClassToken(Data.SHAPE);

    private MethodHandles_Lookup_() {
    }
}
