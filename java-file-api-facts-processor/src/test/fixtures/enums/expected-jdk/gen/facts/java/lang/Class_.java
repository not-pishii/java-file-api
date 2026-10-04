package gen.facts.java.lang;

import gen.facts.java.lang.Class_.Canonical;
import gen.facts.java.lang.Class_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.FinalClass;
import me.supcheg.javafile.facts.FinalClassToken;
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
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;

/// The token-only metamodel of [Class]: its shape and its token, no facts of its members.
///
/// `@Facts` does not ask for [Class]: it is only mentioned in the signatures of [Enum] and [Object]. For the facts of its members add `Class.class` to `@Facts`.
///
/// @param <T> a type argument of [Class]
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Class.class, fingerprint = "b99ec6f17b1c7afef8bd158a50c1a7453c73f9525c9e1dcd0dde16f1aec44794", complete = false, format = 5)
@SuppressWarnings({
    "deprecation",
    "removal"
})
public final class Class_<T> {
    /// The shape of [Class] as plain data: initializing it touches no other metamodel.
    public static final class Data {
        /// What [Class] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<FinalClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.java.lang.Class_"), "b99ec6f17b1c7afef8bd158a50c1a7453c73f9525c9e1dcd0dde16f1aec44794", () -> Canonical.TEXT), DeclaredKind.FINAL_CLASS, ClassDesc.of("java.lang.Class"), List.of(new TypeParam("T", List.of())), List.of(ClassDesc.of("java.lang.Object")), new Supertypes(List.of(Types.typeVar("T")), List.of(new ParameterizedTypeRef(ClassDesc.of("java.lang.invoke.TypeDescriptor$OfField"), List.of(Types.exact(new ParameterizedTypeRef(ClassDesc.of("java.lang.Class"), List.of(Types.unbounded()))))))), new MethodTableTemplate(Set.of(), Set.of(Signature.of("accessFlags"), Signature.of("arrayType"), Signature.of("asSubclass", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("casAnnotationType", Param.fixed(ClassDesc.of("sun.reflect.annotation.AnnotationType")), Param.fixed(ClassDesc.of("sun.reflect.annotation.AnnotationType"))), Signature.of("cast", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("clone"), Signature.of("componentType"), Signature.of("describeConstable"), Signature.of("descriptorString"), Signature.of("desiredAssertionStatus"), Signature.of("enumConstantDirectory"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("findMethod", Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Class;"))), Signature.of("getAnnotatedInterfaces"), Signature.of("getAnnotatedSuperclass"), Signature.of("getAnnotation", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("getAnnotationType"), Signature.of("getAnnotations"), Signature.of("getAnnotationsByType", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("getCanonicalName"), Signature.of("getClass"), Signature.of("getClassData"), Signature.of("getClassFileVersion"), Signature.of("getClassLoader"), Signature.of("getClassLoader0"), Signature.of("getClasses"), Signature.of("getComponentType"), Signature.of("getConstantPool"), Signature.of("getConstructor", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Class;"))), Signature.of("getConstructors"), Signature.of("getDeclaredAnnotation", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("getDeclaredAnnotationMap"), Signature.of("getDeclaredAnnotations"), Signature.of("getDeclaredAnnotationsByType", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("getDeclaredClasses"), Signature.of("getDeclaredConstructor", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Class;"))), Signature.of("getDeclaredConstructors"), Signature.of("getDeclaredField", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getDeclaredFields"), Signature.of("getDeclaredMethod", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Class;"))), Signature.of("getDeclaredMethods"), Signature.of("getDeclaredPublicMethods", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Class;"))), Signature.of("getDeclaringClass"), Signature.of("getEnclosingClass"), Signature.of("getEnclosingConstructor"), Signature.of("getEnclosingMethod"), Signature.of("getEnumConstants"), Signature.of("getEnumConstantsShared"), Signature.of("getField", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getFields"), Signature.of("getGenericInterfaces"), Signature.of("getGenericSuperclass"), Signature.of("getInterfaces"), Signature.of("getMethod", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Class;"))), Signature.of("getMethods"), Signature.of("getModifiers"), Signature.of("getModule"), Signature.of("getName"), Signature.of("getNestHost"), Signature.of("getNestMembers"), Signature.of("getPackage"), Signature.of("getPackageName"), Signature.of("getPermittedSubclasses"), Signature.of("getProtectionDomain"), Signature.of("getRawAnnotations"), Signature.of("getRawTypeAnnotations"), Signature.of("getRecordComponents"), Signature.of("getResource", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getResourceAsStream", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getSigners"), Signature.of("getSimpleName"), Signature.of("getSuperclass"), Signature.of("getTypeName"), Signature.of("getTypeParameters"), Signature.of("hashCode"), Signature.of("isAnnotation"), Signature.of("isAnnotationPresent", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("isAnonymousClass"), Signature.of("isArray"), Signature.of("isAssignableFrom", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("isEnum"), Signature.of("isHidden"), Signature.of("isInstance", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("isInterface"), Signature.of("isLocalClass"), Signature.of("isMemberClass"), Signature.of("isNestmateOf", Param.fixed(ClassDesc.of("java.lang.Class"))), Signature.of("isPrimitive"), Signature.of("isRecord"), Signature.of("isSealed"), Signature.of("isSynthetic"), Signature.of("newInstance"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("setSigners", Param.fixed(ClassDesc.ofDescriptor("[Ljava/lang/Object;"))), Signature.of("toGenericString"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(Signature.of("forName", Param.fixed(ClassDesc.of("java.lang.Module")), Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("forName", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("forName", Param.fixed(ClassDesc.of("java.lang.String")), Param.fixed(ConstantDescs.CD_boolean), Param.fixed(ClassDesc.of("java.lang.ClassLoader"))), Signature.of("forPrimitiveName", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("getExecutableTypeAnnotationBytes", Param.fixed(ClassDesc.of("java.lang.reflect.Executable"))), Signature.of("getPrimitiveClass", Param.fixed(ClassDesc.of("java.lang.String"))), Signature.of("typeVarBounds", Param.fixed(ClassDesc.of("java.lang.reflect.TypeVariable")))), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Class], loaded only to compare the type with the one on the target classpath.
    static final class Canonical {
        /// The canonical form of [Class].
        static final String TEXT = "javafile-facts-canonical 4\ntype java.lang.Class final-class sealed=no\ntparams #0\nsuperclasses java.lang.Object\nsupertypes java.lang.invoke.TypeDescriptor$OfField<java.lang.Class<?>>\nenum -\nmembers none\ntable abstract -\ntable concrete accessFlags(); arrayType(); asSubclass(java.lang.Class); casAnnotationType(sun.reflect.annotation.AnnotationType, sun.reflect.annotation.AnnotationType); cast(java.lang.Object); clone(); componentType(); describeConstable(); descriptorString(); desiredAssertionStatus(); enumConstantDirectory(); equals(java.lang.Object); finalize(); findMethod(boolean, java.lang.String, java.lang.Class[]); getAnnotatedInterfaces(); getAnnotatedSuperclass(); getAnnotation(java.lang.Class); getAnnotationType(); getAnnotations(); getAnnotationsByType(java.lang.Class); getCanonicalName(); getClass(); getClassData(); getClassFileVersion(); getClassLoader(); getClassLoader0(); getClasses(); getComponentType(); getConstantPool(); getConstructor(java.lang.Class[]); getConstructors(); getDeclaredAnnotation(java.lang.Class); getDeclaredAnnotationMap(); getDeclaredAnnotations(); getDeclaredAnnotationsByType(java.lang.Class); getDeclaredClasses(); getDeclaredConstructor(java.lang.Class[]); getDeclaredConstructors(); getDeclaredField(java.lang.String); getDeclaredFields(); getDeclaredMethod(java.lang.String, java.lang.Class[]); getDeclaredMethods(); getDeclaredPublicMethods(java.lang.String, java.lang.Class[]); getDeclaringClass(); getEnclosingClass(); getEnclosingConstructor(); getEnclosingMethod(); getEnumConstants(); getEnumConstantsShared(); getField(java.lang.String); getFields(); getGenericInterfaces(); getGenericSuperclass(); getInterfaces(); getMethod(java.lang.String, java.lang.Class[]); getMethods(); getModifiers(); getModule(); getName(); getNestHost(); getNestMembers(); getPackage(); getPackageName(); getPermittedSubclasses(); getProtectionDomain(); getRawAnnotations(); getRawTypeAnnotations(); getRecordComponents(); getResource(java.lang.String); getResourceAsStream(java.lang.String); getSigners(); getSimpleName(); getSuperclass(); getTypeName(); getTypeParameters(); hashCode(); isAnnotation(); isAnnotationPresent(java.lang.Class); isAnonymousClass(); isArray(); isAssignableFrom(java.lang.Class); isEnum(); isHidden(); isInstance(java.lang.Object); isInterface(); isLocalClass(); isMemberClass(); isNestmateOf(java.lang.Class); isPrimitive(); isRecord(); isSealed(); isSynthetic(); newInstance(); notify(); notifyAll(); setSigners(java.lang.Object[]); toGenericString(); toString(); wait(); wait(long); wait(long, int)\ntable static forName(java.lang.Module, java.lang.String); forName(java.lang.String); forName(java.lang.String, boolean, java.lang.ClassLoader); forPrimitiveName(java.lang.String); getExecutableTypeAnnotationBytes(java.lang.reflect.Executable); getPrimitiveClass(java.lang.String); typeVarBounds(java.lang.reflect.TypeVariable)\ntable ctor -\n";

        private Canonical() {
        }
    }

    /// The token of [Class] with a wildcard for every type argument.
    public static final FinalClassToken<Class<?>> ANY = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.unbounded());

    /// The token of [Class] with the type arguments of this metamodel.
    public final FinalClassToken<Class<T>> token;

    /// The metamodel of [Class] with the type arguments the tokens give.
    ///
    /// @param t the token of the type argument `T`
    public Class_(RefToken<T> t) {
        this.token = UnsafeFacts.finalClassToken(Data.SHAPE, TokenArg.exact(t));
    }
}
