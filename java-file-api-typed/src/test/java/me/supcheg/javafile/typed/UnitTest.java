package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.List;

import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_char;
import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_int;
import static me.supcheg.javafile.code.Exprs.div;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.mul;
import static me.supcheg.javafile.code.Exprs.this_;
import static me.supcheg.javafile.typed.Syms.call;
import static me.supcheg.javafile.typed.Syms.new_;
import static me.supcheg.javafile.typed.Syms.staticCall;
import static me.supcheg.javafile.typed.Syms.staticField;
import static org.assertj.core.api.Assertions.assertThat;

class UnitTest {
    private static final ClassDesc CHAR_SEQUENCE = ClassDesc.of("java.lang.CharSequence");
    private static final ClassDesc STRING_BUILDER = ClassDesc.of("java.lang.StringBuilder");
    private static final ClassDesc SHAPE = ClassDesc.of("com.example", "Shape");
    private static final ClassDesc CIRCLE = ClassDesc.of("com.example", "Circle");
    private static final ClassDesc GEOMETRY = ClassDesc.of("com.example", "Geometry");
    private static final MethodTypeDesc DOUBLE = MethodTypeDesc.of(CD_double);

    private final Env env = Env.of(getClass().getClassLoader());

    @Test
    void generatesCodeUsingJdkMembers() {
        MethodSym charAt = env.method(CD_String, "charAt", MethodTypeDesc.of(CD_char, CD_int));
        MethodSym length = env.method(CHAR_SEQUENCE, "length", MethodTypeDesc.of(CD_int));
        MethodSym valueOf = env.staticMethod(CD_String, "valueOf", MethodTypeDesc.of(CD_String, CD_int));
        CtorSym newBuilder = env.ctor(STRING_BUILDER, CD_String);
        MethodSym append = env.method(STRING_BUILDER, "append", MethodTypeDesc.of(STRING_BUILDER, CHAR_SEQUENCE));

        Unit unit = new Unit(env);
        TypeHandle strings = unit.class_(ClassDesc.of("com.example", "Strings"));
        strings.defineStaticMethod(
                "first",
                MethodTypeDesc.of(CD_char, CD_String),
                List.of("s"),
                (code, p) -> code.return_(call(p.getFirst(), charAt, literal(0))));
        MethodSym size = strings.defineStaticMethod(
                "size",
                MethodTypeDesc.of(CD_int, CD_String),
                List.of("s"),
                (code, p) -> code.return_(call(p.getFirst(), length)));
        strings.defineStaticMethod(
                "describe",
                MethodTypeDesc.of(STRING_BUILDER, CD_String),
                List.of("s"),
                (code, p) -> code.return_(call(
                        new_(newBuilder, p.getFirst()), append, staticCall(valueOf, staticCall(size, p.getFirst())))));
        List<JavaFile> files = unit.build();

        assertThat(files.getFirst().render())
                .contains("public static char first(String s) {")
                .contains("return s.charAt(0);")
                .contains("return s.length();")
                .contains("return new StringBuilder(s).append(String.valueOf(Strings.size(s)));");
        Compilations.assertCompiles(files);
    }

    @Test
    void generatesTypesUsingEachOther() {
        Unit unit = new Unit(env);

        TypeHandle shape = unit.interface_(SHAPE);
        MethodSym area = shape.defineAbstractMethod("area", DOUBLE);

        TypeHandle circle =
                unit.record(CIRCLE, new Component("radius", CD_double)).implement(shape);
        MethodSym radius = circle.method("radius", DOUBLE);
        FieldSym pi = env.staticField(ClassDesc.of("java.lang.Math"), "PI", CD_double);
        MethodSym circleArea = circle.defineMethod(
                "area",
                DOUBLE,
                (code, p) -> code.return_(mul(staticField(pi), mul(call(this_(), radius), call(this_(), radius)))));
        CtorSym newCircle = circle.ctor(CD_double);

        TypeHandle geometry = unit.class_(GEOMETRY);
        MethodSym ratio = geometry.defineMethod(
                "ratio",
                MethodTypeDesc.of(CD_double, CIRCLE),
                List.of("c"),
                (code, p) -> code.return_(div(call(p.getFirst(), circleArea), call(p.getFirst(), radius))));
        geometry.defineStaticMethod(
                "example",
                DOUBLE,
                (code, p) -> code.return_(call(new_(geometry.ctor()), ratio, new_(newCircle, literal(2.0)))));
        geometry.defineStaticMethod(
                "areaOf",
                MethodTypeDesc.of(CD_double, SHAPE),
                List.of("shape"),
                (code, p) -> code.return_(call(p.getFirst(), area)));

        List<JavaFile> files = unit.build();

        assertThat(circle.method("area", DOUBLE)).isEqualTo(circleArea);
        assertThat(files).extracting(JavaFile::simpleName).containsExactly("Shape", "Circle", "Geometry");
        assertThat(files.get(0).render()).contains("double area();");
        assertThat(files.get(1).render()).contains("public record Circle(double radius) implements Shape {");
        assertThat(files.get(2).render())
                .contains("return c.area() / c.radius();")
                .contains("return new Geometry().ratio(new Circle(2.0));");
        Compilations.assertCompiles(files);
    }

    @Test
    void inheritedMembersAreProvenOnSubtypes() {
        Unit unit = new Unit(env);
        TypeHandle shape = unit.interface_(SHAPE);
        shape.defineAbstractMethod("area", DOUBLE);
        TypeHandle circle =
                unit.record(CIRCLE, new Component("radius", CD_double)).implement(shape);

        assertThat(circle.method("area", DOUBLE)).hasToString("double com.example.Circle.area()");
        assertThat(circle.method("hashCode", MethodTypeDesc.of(CD_int))).isNotNull();
        assertThat(shape.method("toString", MethodTypeDesc.of(CD_String))).isNotNull();
    }

    @Test
    void enumHasImplicitMembers() {
        ClassDesc color = ClassDesc.of("com.example", "Color");
        Unit unit = new Unit(env);
        TypeHandle handle = unit.enum_(color, "RED", "GREEN");

        FieldSym red = handle.staticField("RED", color);
        MethodSym values = handle.staticMethod("values", MethodTypeDesc.of(color.arrayType()));
        MethodSym ordinal = handle.method("ordinal", MethodTypeDesc.of(CD_int));
        handle.defineStaticMethod(
                "count", MethodTypeDesc.of(CD_int), (code, p) -> code.return_(call(staticField(red), ordinal)));

        assertThat(values).hasToString("static com.example.Color[] com.example.Color.values()");
        Compilations.assertCompiles(unit.build());
    }
}
