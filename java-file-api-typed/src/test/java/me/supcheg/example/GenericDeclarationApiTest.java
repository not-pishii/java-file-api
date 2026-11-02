package me.supcheg.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.ClassHeader;
import me.supcheg.javafile.typed.MemberBodies;
import me.supcheg.javafile.typed.TypedJavaFile;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// The generated stages of a generic declaration are the public API of [TypedJavaFile] from another
/// package: the entry point is its inherited static method, and the specification of four type
/// parameters is its inherited nested type.
@NullMarked
class GenericDeclarationApiTest {
    @Test
    void aClassOfFourTypeParametersIsDeclaredFromAnotherPackage() {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Box"),
                typeParam("T1"),
                typeParam("T2"),
                typeParam("T3"),
                typeParam("T4"),
                new TypedJavaFile.TypedGenericClassSpec4<>() {
                    @Override
                    public <Self, T1, T2, T3, T4> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h,
                            TypeVarToken<T1> t1,
                            TypeVarToken<T2> t2,
                            TypeVarToken<T3> t3,
                            TypeVarToken<T4> t4) {
                        return h.extendsObject().members().bodies();
                    }
                });

        assertThat(file.render()).contains("public final class Box<T1, T2, T3, T4> {");
    }
}
