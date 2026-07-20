package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MethodRefExprTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");

    @Test
    void typeQualifiedMethodRef() {
        MethodRefExpr ref = new MethodRefExpr(new TypeMethodRefTarget(Types.of(STRING)), "valueOf");
        assertThat(ref.target()).isEqualTo(new TypeMethodRefTarget(new ClassTypeRef(STRING)));
    }

    @Test
    void boundInstanceMethodRef() {
        Expr instance = new FieldAccessExpr(Optional.empty(), "name");
        MethodRefExpr ref = new MethodRefExpr(new ExprMethodRefTarget(instance), "trim");
        assertThat(ref.method()).isEqualTo("trim");
    }

    @Test
    void constructorRef() {
        ConstructorRefExpr ref = new ConstructorRefExpr(Types.of(STRING));
        assertThat(ref.type()).isEqualTo(new ClassTypeRef(STRING));
    }
}
