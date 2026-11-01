package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec13;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec14;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec15;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec16;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec9;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec13;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec14;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec15;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec16;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec9;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec13;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec14;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec15;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec16;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec9;
import me.supcheg.javafile.typed.TypedJavaFile.TypedAnnotationTypeSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedEnumSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedInterfaceSpec;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The member types of every hierarchy at four to twelve type parameters: a nested class, an interface and a record inside a class, an interface, an annotation type and an enum constant body, each rendered, compiled under `-Xlint:all` and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
class NestedGenericAritiesTest {
    @Test
    public void nestedTypesOfFourTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec4<Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t4, t4);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec4<Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4) {
                ib.declareAbstractMethod("first", t4);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec4<Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4) {
                rb.component("first", t4);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested4Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested4Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested4Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested4Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4> {").contains("public T4 first(T4 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4> {").contains("T4 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4>(T4 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4> {").contains("public T4 first(T4 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4> {").contains("T4 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4>(T4 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4> {").contains("public T4 first(T4 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4> {").contains("T4 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4>(T4 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4> {").contains("public T4 first(T4 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4> {").contains("T4 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4>(T4 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested4Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested4Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested4Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested4Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfFiveTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec5<Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t5, t5);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec5<Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5) {
                ib.declareAbstractMethod("first", t5);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec5<Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5) {
                rb.component("first", t5);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested5Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested5Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested5Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested5Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5> {").contains("public T5 first(T5 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5> {").contains("T5 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5>(T5 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5> {").contains("public T5 first(T5 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5> {").contains("T5 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5>(T5 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5> {").contains("public T5 first(T5 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5> {").contains("T5 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5>(T5 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5> {").contains("public T5 first(T5 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5> {").contains("T5 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5>(T5 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested5Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested5Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested5Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested5Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfSixTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec6<Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t6, t6);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec6<Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6) {
                ib.declareAbstractMethod("first", t6);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec6<Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6) {
                rb.component("first", t6);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested6Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested6Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested6Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested6Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6> {").contains("public T6 first(T6 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6> {").contains("T6 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6>(T6 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6> {").contains("public T6 first(T6 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6> {").contains("T6 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6>(T6 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6> {").contains("public T6 first(T6 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6> {").contains("T6 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6>(T6 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6> {").contains("public T6 first(T6 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6> {").contains("T6 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6>(T6 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested6Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested6Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested6Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested6Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfSevenTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec7<Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t7, t7);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec7<Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7) {
                ib.declareAbstractMethod("first", t7);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec7<Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7) {
                rb.component("first", t7);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested7Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested7Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested7Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested7Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7> {").contains("public T7 first(T7 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7> {").contains("T7 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7>(T7 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7> {").contains("public T7 first(T7 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7> {").contains("T7 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7>(T7 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7> {").contains("public T7 first(T7 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7> {").contains("T7 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7>(T7 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7> {").contains("public T7 first(T7 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7> {").contains("T7 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7>(T7 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested7Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested7Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested7Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested7Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfEightTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec8<Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t8, t8);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec8<Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8) {
                ib.declareAbstractMethod("first", t8);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec8<Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8) {
                rb.component("first", t8);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested8Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested8Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested8Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested8Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("public T8 first(T8 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("T8 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8>(T8 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("public T8 first(T8 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("T8 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8>(T8 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("public T8 first(T8 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("T8 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8>(T8 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("public T8 first(T8 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8> {").contains("T8 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8>(T8 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested8Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested8Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested8Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested8Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfNineTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec9<Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t9, t9);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec9<Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9) {
                ib.declareAbstractMethod("first", t9);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec9<Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9) {
                rb.component("first", t9);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested9Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested9Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested9Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested9Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("public T9 first(T9 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("T9 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9>(T9 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("public T9 first(T9 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("T9 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9>(T9 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("public T9 first(T9 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("T9 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9>(T9 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("public T9 first(T9 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9> {").contains("T9 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9>(T9 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested9Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested9Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested9Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested9Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfTenTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t10, t10);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10) {
                ib.declareAbstractMethod("first", t10);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10) {
                rb.component("first", t10);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested10Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested10Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested10Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested10Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("public T10 first(T10 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("T10 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(T10 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("public T10 first(T10 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("T10 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(T10 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("public T10 first(T10 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("T10 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(T10 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("public T10 first(T10 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {").contains("T10 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(T10 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested10Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested10Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested10Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested10Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfElevenTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec11<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t11, t11);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec11<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11) {
                ib.declareAbstractMethod("first", t11);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec11<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11) {
                rb.component("first", t11);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested11Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested11Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested11Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested11Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("public T11 first(T11 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("T11 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(T11 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("public T11 first(T11 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("T11 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(T11 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("public T11 first(T11 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("T11 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(T11 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("public T11 first(T11 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {").contains("T11 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(T11 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested11Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested11Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested11Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested11Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfTwelveTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec12<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t12, t12);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec12<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12) {
                ib.declareAbstractMethod("first", t12);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec12<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12) {
                rb.component("first", t12);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested12Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested12Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested12Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested12Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("public T12 first(T12 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("T12 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(T12 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("public T12 first(T12 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("T12 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(T12 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("public T12 first(T12 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("T12 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(T12 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("public T12 first(T12 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {").contains("T12 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(T12 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested12Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested12Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested12Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested12Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfThirteenTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t13, t13);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13) {
                ib.declareAbstractMethod("first", t13);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13) {
                rb.component("first", t13);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested13Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested13Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested13Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested13Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("public T13 first(T13 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("T13 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(T13 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("public T13 first(T13 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("T13 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(T13 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("public T13 first(T13 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("T13 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(T13 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("public T13 first(T13 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> {").contains("T13 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(T13 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested13Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested13Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested13Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested13Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfFourteenTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t14, t14);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14) {
                ib.declareAbstractMethod("first", t14);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14) {
                rb.component("first", t14);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested14Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested14Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested14Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested14Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("public T14 first(T14 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("T14 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(T14 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("public T14 first(T14 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("T14 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(T14 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("public T14 first(T14 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("T14 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(T14 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("public T14 first(T14 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> {").contains("T14 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(T14 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested14Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested14Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested14Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested14Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfFifteenTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14, TypeVarToken<T15> t15) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t15, t15);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14, TypeVarToken<T15> t15) {
                ib.declareAbstractMethod("first", t15);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14, TypeVarToken<T15> t15) {
                rb.component("first", t15);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested15Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested15Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested15Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested15Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("public T15 first(T15 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("T15 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(T15 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("public T15 first(T15 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("T15 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(T15 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("public T15 first(T15 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("T15 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(T15 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("public T15 first(T15 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> {").contains("T15 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(T15 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested15Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested15Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested15Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested15Enum$1$Box", "x")).isEqualTo("x");
    }

    @Test
    public void nestedTypesOfSixteenTypeParametersInEveryHierarchyRenderCompileAndRun() throws Throwable {
        var classSpec = new TypedGenericClassSpec16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <N, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MemberBodies<N, ?> build(ClassHeader<N> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14, TypeVarToken<T15> t15, TypeVarToken<T16> t16) {
                var members = h.extendsObject().members();
                var first = members.declareMethod("first", t16, t16);
                var bodies = members.bodies();
                bodies.define(first, (b, self, value) -> b.return_(value));
                return bodies;
            }
        };
        var interfaceSpec = new TypedGenericInterfaceSpec16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <I, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> TypedInterfaceBodies<I> build(TypedInterfaceBuilder<I> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14, TypeVarToken<T15> t15, TypeVarToken<T16> t16) {
                ib.declareAbstractMethod("first", t16);
                return ib.bodies();
            }
        };
        var recordSpec = new TypedGenericRecordSpec16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>() {
            @Override
            public <R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> TypedRecordBodies<R> build(TypedRecordBuilder<R> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12, TypeVarToken<T13> t13, TypeVarToken<T14> t14, TypeVarToken<T15> t15, TypeVarToken<T16> t16) {
                rb.component("first", t16);
                return rb.bodies();
            }
        };
        var aClass = TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested16Class"), new TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var members = h.extendsObject().members();
                members.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), classSpec);
                members.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), interfaceSpec);
                members.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), recordSpec);
                return members.bodies();
            }
        });
        var anInterface = TypedJavaFile.interface_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested16Interface"), new TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                ib.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), classSpec);
                ib.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), interfaceSpec);
                ib.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), recordSpec);
                return ib.bodies();
            }
        });
        var anAnnotationType = TypedJavaFile.annotationType(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested16Annotation"), new TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), classSpec);
                ab.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), interfaceSpec);
                ab.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), recordSpec);
                return ab;
            }
        });
        var anEnum = TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", "Nested16Enum"), new TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A", (body) -> body.declareNestedClass("Box", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), classSpec));
                eb.constant("B", (body) -> body.declareNestedInterface("One", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), interfaceSpec));
                eb.constant("C", (body) -> body.declareNestedRecord("Pair", TypedJavaFile.typeParam("T1"), TypedJavaFile.typeParam("T2"), TypedJavaFile.typeParam("T3"), TypedJavaFile.typeParam("T4"), TypedJavaFile.typeParam("T5"), TypedJavaFile.typeParam("T6"), TypedJavaFile.typeParam("T7"), TypedJavaFile.typeParam("T8"), TypedJavaFile.typeParam("T9"), TypedJavaFile.typeParam("T10"), TypedJavaFile.typeParam("T11"), TypedJavaFile.typeParam("T12"), TypedJavaFile.typeParam("T13"), TypedJavaFile.typeParam("T14"), TypedJavaFile.typeParam("T15"), TypedJavaFile.typeParam("T16"), recordSpec));
                return eb.bodies();
            }
        });
        Assertions.assertThat(aClass.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("public T16 first(T16 v0) {");
        Assertions.assertThat(aClass.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("T16 first();");
        Assertions.assertThat(aClass.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(T16 first) {");
        Assertions.assertThat(anInterface.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("public T16 first(T16 v0) {");
        Assertions.assertThat(anInterface.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("T16 first();");
        Assertions.assertThat(anInterface.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(T16 first) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("public T16 first(T16 v0) {");
        Assertions.assertThat(anAnnotationType.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("T16 first();");
        Assertions.assertThat(anAnnotationType.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(T16 first) {");
        Assertions.assertThat(anEnum.render()).contains("public static final class Box<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("public T16 first(T16 v0) {");
        Assertions.assertThat(anEnum.render()).contains("public interface One<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> {").contains("T16 first();");
        Assertions.assertThat(anEnum.render()).contains("public record Pair<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(T16 first) {");
        var compiled = CompiledClasses.ofAll(List.of(aClass, anInterface, anAnnotationType, anEnum));
        Assertions.assertThat(NestedForms.first(compiled.get(0), "me.supcheg.example.Nested16Class$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(1), "me.supcheg.example.Nested16Interface$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(2), "me.supcheg.example.Nested16Annotation$Box", "x")).isEqualTo("x");
        Assertions.assertThat(NestedForms.first(compiled.get(3), "me.supcheg.example.Nested16Enum$1$Box", "x")).isEqualTo("x");
    }
}
