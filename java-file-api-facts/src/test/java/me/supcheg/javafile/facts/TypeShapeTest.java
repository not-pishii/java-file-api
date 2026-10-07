package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.testfacts.java.lang.Integer_;
import me.supcheg.javafile.facts.testfacts.java.lang.String_;
import me.supcheg.javafile.facts.testfacts.java.util.List_;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A shape — the data of a type apart from its type arguments — and the
/// tokens made from it.
class TypeShapeTest {

    private static final ClassDesc BOX = ClassDesc.of("fixtures", "Box");
    private static final ClassDesc CD_ENUM = ClassDesc.of("java.lang", "Enum");
    private static final ClassDesc CD_COMPARABLE = ClassDesc.of("java.lang", "Comparable");
    private static final String FINGERPRINT = "0123456789abcdef".repeat(4);

    // interface Box<T extends Comparable<T>> extends Comparable<Box<T>> { void put(T); T get(int); }
    private static final TypeShape<DeclaredKind.Interface> BOX_SHAPE = UnsafeFacts.shape(
            DeclaredKind.INTERFACE,
            BOX,
            List.of(new TypeParam("T", List.of(Types.parameterized(CD_COMPARABLE, Types.typeVar("T"))))),
            List.of(),
            List.of(),
            new Supertypes(
                    List.of(Types.typeVar("T")),
                    List.of(Types.parameterized(CD_COMPARABLE, Types.parameterized(BOX, Types.typeVar("T"))))),
            new MethodTableTemplate(
                    Set.of(Signature.of("put", Param.var(0)), Signature.of("get", Param.fixed(ConstantDescs.CD_int))),
                    Set.of(),
                    Set.of()),
            List.of(),
            false);

    @Test
    void aTokenAppliesItsShapeToTypeArguments() {
        InterfaceToken<Object> boxOfString = UnsafeFacts.interfaceToken(BOX_SHAPE, TokenArg.exact(String_.TOKEN));

        assertThat(boxOfString.shape()).isSameAs(BOX_SHAPE);
        assertThat(boxOfString.typeRef()).isEqualTo(Types.parameterized(BOX, Types.STRING));
        assertThat(boxOfString.erasure()).isEqualTo(BOX);
        assertThat(boxOfString.supertypes()).isEqualTo(BOX_SHAPE.supertypes());
        assertThat(boxOfString.methods().abstractMethods())
                .contains(new MethodSignature("put", List.of(ConstantDescs.CD_String)));
        assertThat(boxOfString)
                .isEqualTo(UnsafeFacts.interfaceToken(
                        Types.parameterized(BOX, Types.STRING), BOX_SHAPE.supertypes(), MethodTable.EMPTY));
    }

    @Test
    void theMethodTableIsErasedByTheTypeArgumentsOrTheBoundOfTheTypeParameter() {
        assertThat(put(TokenArg.exact(String_.TOKEN))).isEqualTo(ConstantDescs.CD_String);
        assertThat(put(TokenArg.extendsBound(Integer_.TOKEN))).isEqualTo(ConstantDescs.CD_Integer);
        assertThat(put(TokenArg.superBound(String_.TOKEN))).isEqualTo(CD_COMPARABLE);
        assertThat(put(TokenArg.unbounded())).isEqualTo(CD_COMPARABLE);
        TypeVarToken<Object> t = UnsafeFacts.typeVarToken(Types.typeVar("X"), ConstantDescs.CD_Number);
        assertThat(put(TokenArg.exact(t))).isEqualTo(ConstantDescs.CD_Number);
        assertThat(put(TokenArg.exact(ArrayToken.of(String_.TOKEN)))).isEqualTo(ConstantDescs.CD_String.arrayType());
    }

    private static ClassDesc put(TokenArg arg) {
        return UnsafeFacts.interfaceToken(BOX_SHAPE, arg).methods().abstractMethods().stream()
                .filter(s -> s.name().equals("put"))
                .findFirst()
                .orElseThrow()
                .params()
                .getFirst();
    }

    @Test
    void theRawTypeHasErasedMethodsAndNoSupertypes() {
        InterfaceToken<Object> raw = UnsafeFacts.interfaceToken(BOX_SHAPE);

        assertThat(raw.typeRef()).isEqualTo(Types.of(BOX));
        assertThat(raw.supertypes()).isEqualTo(Supertypes.NONE);
        assertThat(raw.methods().abstractMethods()).contains(new MethodSignature("put", List.of(CD_COMPARABLE)));
    }

    @Test
    void aTokenTakesOneTypeArgumentPerTypeParameterOrNone() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.interfaceToken(
                        BOX_SHAPE, TokenArg.exact(String_.TOKEN), TokenArg.exact(String_.TOKEN)))
                .withMessage("fixtures.Box has 1 type parameters, but 2 type arguments are given");
        TypeShape<DeclaredKind.FinalClass> plain = finalClass(ClassDesc.of("fixtures", "Plain"));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.finalClassToken(plain, TokenArg.exact(String_.TOKEN)))
                .withMessage("fixtures.Plain has 0 type parameters, but 1 type arguments are given");
    }

    @Test
    void theBoundOfATypeParameterBoundedByAnotherIsTheOthers() {
        // class Pair<A extends Number, B extends A>
        TypeShape<DeclaredKind.FinalClass> pair = UnsafeFacts.shape(
                DeclaredKind.FINAL_CLASS,
                ClassDesc.of("fixtures", "Pair"),
                List.of(
                        new TypeParam("A", List.of(Types.of(ConstantDescs.CD_Number))),
                        new TypeParam("B", List.of(Types.typeVar("A")))),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                new MethodTableTemplate(Set.of(), Set.of(Signature.of("set", Param.var(0), Param.var(1))), Set.of()),
                List.of(),
                false);

        assertThat(UnsafeFacts.finalClassToken(pair).methods().concreteMethods())
                .containsExactly(new MethodSignature("set", List.of(ConstantDescs.CD_Number, ConstantDescs.CD_Number)));
    }

    @Test
    void typeParametersAreDistinctDeclaredAndNotCircular() {
        assertInvalid(
                List.of(new TypeParam("T", List.of()), new TypeParam("T", List.of())), "duplicate type parameter T");
        assertInvalid(
                List.of(new TypeParam("T", List.of(Types.parameterized(CD_COMPARABLE, Types.typeVar("U"))))),
                "the bound of type parameter T names undeclared type variable U");
        assertInvalid(
                List.of(
                        new TypeParam("T", List.of(Types.typeVar("U"), Types.of(CD_COMPARABLE))),
                        new TypeParam("U", List.of())),
                "type parameter T is bounded by type variable U and other types (JLS 4.4)");
        assertInvalid(
                List.of(
                        new TypeParam("T", List.of(Types.typeVar("U"))),
                        new TypeParam("U", List.of(Types.typeVar("T")))),
                "type parameter T is bounded by itself");
    }

    private static void assertInvalid(List<TypeParam> typeParameters, String message) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.shape(
                        DeclaredKind.INTERFACE,
                        BOX,
                        typeParameters,
                        List.of(),
                        List.of(),
                        Supertypes.NONE,
                        MethodTableTemplate.EMPTY,
                        List.of(),
                        false))
                .withMessage(message);
    }

    @Test
    void theSupertypesAndTheTemplateReferToTheTypeParametersOfTheShape() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.shape(
                        DeclaredKind.INTERFACE,
                        BOX,
                        List.of(new TypeParam("T", List.of())),
                        List.of(),
                        List.of(),
                        new Supertypes(List.of(Types.typeVar("E")), List.of()),
                        MethodTableTemplate.EMPTY,
                        List.of(),
                        false))
                .withMessage("the supertypes of interface fixtures.Box<T> are given for type parameters [E],"
                        + " but the type has [T]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.shape(
                        DeclaredKind.INTERFACE,
                        BOX,
                        List.of(new TypeParam("T", List.of())),
                        List.of(),
                        List.of(),
                        Supertypes.NONE,
                        new MethodTableTemplate(Set.of(Signature.of("put", Param.var(1))), Set.of(), Set.of()),
                        List.of(),
                        false))
                .withMessage("the method table of interface fixtures.Box<T> refers to type parameter #1,"
                        + " but the type has 1 type parameters");
    }

    @Test
    void theSuperclassChainFitsTheKind() {
        assertInvalidShape(
                DeclaredKind.INTERFACE,
                BOX,
                List.of(ConstantDescs.CD_Object),
                List.of(),
                false,
                "interface fixtures.Box has no superclasses, got [java.lang.Object]");
        assertInvalidShape(
                DeclaredKind.OPEN_CLASS,
                ConstantDescs.CD_Object,
                List.of(ConstantDescs.CD_Object),
                List.of(),
                false,
                "java.lang.Object has no superclasses, got [java.lang.Object]");
        assertInvalidShape(
                DeclaredKind.OPEN_CLASS,
                BOX,
                List.of(),
                List.of(),
                false,
                "the superclass chain of open class fixtures.Box must end in java.lang.Object, got []");
        assertInvalidShape(
                DeclaredKind.OPEN_CLASS,
                BOX,
                List.of(BOX, ConstantDescs.CD_Object),
                List.of(),
                false,
                "the superclass chain of open class fixtures.Box repeats a class: [fixtures.Box, java.lang.Object]");
        assertInvalidShape(
                DeclaredKind.ENUM_CLASS,
                BOX,
                List.of(ConstantDescs.CD_Object),
                List.of(),
                false,
                "the superclass chain of enum class fixtures.Box must be java.lang.Enum, java.lang.Object,"
                        + " got [java.lang.Object]");
    }

    @Test
    void enumConstantsAndSealedFitTheKind() {
        assertInvalidShape(
                DeclaredKind.FINAL_CLASS,
                BOX,
                List.of(ConstantDescs.CD_Object),
                List.of("A"),
                false,
                "final class fixtures.Box is not an enum, but has enum constants [A]");
        assertInvalidShape(
                DeclaredKind.FINAL_CLASS,
                BOX,
                List.of(ConstantDescs.CD_Object),
                List.of(),
                true,
                "a final class cannot be sealed: final class fixtures.Box");
        assertInvalidShape(
                DeclaredKind.ENUM_CLASS,
                BOX,
                List.of(CD_ENUM, ConstantDescs.CD_Object),
                List.of("A", "A"),
                false,
                "duplicate enum constants in enum class fixtures.Box: [A, A]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.shape(
                        DeclaredKind.ENUM_CLASS,
                        BOX,
                        List.of(new TypeParam("T", List.of())),
                        List.of(CD_ENUM, ConstantDescs.CD_Object),
                        List.of(),
                        Supertypes.NONE,
                        MethodTableTemplate.EMPTY,
                        List.of(),
                        false))
                .withMessage("an enum class cannot be generic: enum class fixtures.Box<T>");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> finalClass(ConstantDescs.CD_int))
                .withMessage("a declared type is a class or interface, got int");
    }

    private static void assertInvalidShape(
            DeclaredKind kind,
            ClassDesc desc,
            List<ClassDesc> superclasses,
            List<String> enumConstants,
            boolean sealed,
            String message) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> UnsafeFacts.shape(
                        kind,
                        desc,
                        List.of(),
                        superclasses,
                        List.of(),
                        Supertypes.NONE,
                        MethodTableTemplate.EMPTY,
                        enumConstants,
                        sealed))
                .withMessage(message);
    }

    @Test
    void aClassTokenCarriesTheSuperclassesOfItsShape() {
        TypeShape<DeclaredKind.AbstractClass> failure = UnsafeFacts.shape(
                DeclaredKind.ABSTRACT_CLASS,
                ClassDesc.of("fixtures", "Failure"),
                List.of(),
                List.of(
                        ClassDesc.of("java.io", "IOException"),
                        ConstantDescs.CD_Exception,
                        ConstantDescs.CD_Throwable,
                        ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                true);

        AbstractClassToken<Object> token = UnsafeFacts.abstractClassToken(failure);

        assertThat(token.superclasses()).isEqualTo(failure.superclasses());
        assertThat(token.isCheckedException()).isTrue();
        assertThat(token.methods()).isEqualTo(MethodTable.EMPTY);
    }

    @Test
    void aShapeTellsTheInterfacesOfItsType() {
        ClassDesc runnable = ClassDesc.of("java.lang", "Runnable");
        ClassDesc closeable = ClassDesc.of("java.lang", "AutoCloseable");

        assertThat(withInterfaces(List.of(closeable, runnable)).interfaces()).containsExactly(closeable, runnable);
        assertThat(finalClass(ClassDesc.of("fixtures", "Plain")).interfaces()).isEmpty();
        // made of a type reference, a shape records none
        assertThat(UnsafeFacts.interfaceToken(Types.of(runnable), MethodTable.EMPTY)
                        .shape()
                        .interfaces())
                .isEmpty();
        // the metamodel of a type tells them all, those of its superclasses and superinterfaces included
        assertThat(new me.supcheg.javafile.facts.testfacts.java.util.ArrayList_<>(String_.TOKEN)
                        .token
                        .shape()
                        .interfaces())
                .contains(
                        ClassDesc.of("java.util", "List"),
                        ClassDesc.of("java.util", "Collection"),
                        ClassDesc.of("java.lang", "Iterable"),
                        ClassDesc.of("java.util", "RandomAccess"));
    }

    @Test
    void theInterfacesOfAShapeAreInterfacesEachOnceAndNoSuperclass() {
        ClassDesc runnable = ClassDesc.of("java.lang", "Runnable");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> withInterfaces(List.of(ConstantDescs.CD_int)))
                .withMessage("an interface of final class fixtures.Impl is a class or interface type, got int");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> withInterfaces(List.of(runnable, runnable)))
                .withMessage("the interfaces of final class fixtures.Impl repeat one:"
                        + " [java.lang.Runnable, java.lang.Runnable]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> withInterfaces(List.of(ClassDesc.of("fixtures", "Impl"))))
                .withMessage("the interfaces of final class fixtures.Impl repeat one: [fixtures.Impl]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> withInterfaces(List.of(ConstantDescs.CD_Object)))
                .withMessage("final class fixtures.Impl has [java.lang.Object] both as a superclass and as an"
                        + " interface");
    }

    private static TypeShape<DeclaredKind.FinalClass> withInterfaces(List<ClassDesc> interfaces) {
        return UnsafeFacts.shape(
                DeclaredKind.FINAL_CLASS,
                ClassDesc.of("fixtures", "Impl"),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                interfaces,
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false);
    }

    @Test
    void anEnumTokenCarriesTheConstantsOfItsShape() {
        TypeShape<DeclaredKind.EnumClass> color = UnsafeFacts.shape(
                DeclaredKind.ENUM_CLASS,
                ClassDesc.of("fixtures", "Color"),
                List.of(),
                List.of(CD_ENUM, ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of("RED", "GREEN"),
                false);

        EnumToken<Object> token = UnsafeFacts.enumToken(color);

        assertThat(token.constants()).containsExactly("RED", "GREEN");
        assertThat(token.superclasses()).containsExactly(CD_ENUM, ConstantDescs.CD_Object);
        assertThat(token.constant("RED").name()).isEqualTo("RED");
    }

    @Test
    void aMetamodelShapeKeepsItsOriginAndLoadsTheCanonicalFormOnlyWhenAsked() {
        AtomicInteger loads = new AtomicInteger();
        ShapeOrigin.Metamodel origin =
                new ShapeOrigin.Metamodel(ClassDesc.of("fixtures.facts", "Plain_"), FINGERPRINT, () -> {
                    loads.incrementAndGet();
                    return "javafile-facts-canonical 1";
                });

        TypeShape<DeclaredKind.OpenClass> shape = UnsafeFacts.shape(
                origin,
                DeclaredKind.OPEN_CLASS,
                ClassDesc.of("fixtures", "Plain"),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                true);

        assertThat(shape.origin()).isSameAs(origin);
        assertThat(shape.kind()).isSameAs(DeclaredKind.OPEN_CLASS);
        assertThat(shape.sealed()).isTrue();
        assertThat(UnsafeFacts.openClassToken(shape).shape()).isSameAs(shape);
        assertThat(loads).hasValue(0);
        assertThat(origin.canonical().get()).isEqualTo("javafile-facts-canonical 1");
        assertThat(origin).hasToString("metamodel fixtures.facts.Plain_ (" + FINGERPRINT + ")");
    }

    @Test
    void aMetamodelOriginNamesAClassAndASha256() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ShapeOrigin.Metamodel(ClassDesc.of("a.B"), "ABC", () -> ""))
                .withMessage("a fingerprint is a SHA-256 as 64 lowercase hex digits, got \"ABC\"");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ShapeOrigin.Metamodel(ConstantDescs.CD_int, FINGERPRINT, () -> ""))
                .withMessage("a metamodel is a class, got I");
    }

    @Test
    void everyOtherShapeHasTheOriginOfWhoMadeIt() {
        assertThat(finalClass(ClassDesc.of("fixtures", "Plain")).origin()).isSameAs(ShapeOrigin.UNSAFE);
        assertThat(UnsafeFacts.interfaceToken(Types.of(BOX), MethodTable.EMPTY)
                        .shape()
                        .origin())
                .isSameAs(ShapeOrigin.UNSAFE);
        assertThat(PrimitiveToken.INT.boxed().shape().origin()).isSameAs(ShapeOrigin.BUILTIN);
        assertThat(PrimitiveToken.INT.boxed().shape().kind()).isSameAs(DeclaredKind.FINAL_CLASS);
        assertThat(List.of(ShapeOrigin.MIRROR, ShapeOrigin.UNSAFE, ShapeOrigin.BUILTIN))
                .extracting(Object::toString)
                .containsExactly("mirror", "unsafe", "builtin");
    }

    @Test
    void aShapeOfTheJdkMetamodelIsSharedByItsTokens() {
        assertThat(new List_<>(String_.TOKEN).token.shape()).isSameAs(new List_<>(Integer_.TOKEN).token.shape());
        assertThat(new List_<>(String_.TOKEN).token.shape().typeParameters())
                .containsExactly(new TypeParam("E", List.of()));
        assertThat(String_.TOKEN.shape()).hasToString("final class java.lang.String");
        assertThat(String_.TOKEN.shape().origin()).isInstanceOf(ShapeOrigin.Metamodel.class);
    }

    @Test
    void aTableKnownOnlyLaterIsAskedUntilItIsKnown() {
        AtomicInteger asked = new AtomicInteger();
        MethodTable table = new MethodTable(Set.of(), Set.of(new MethodSignature("run", List.of())), Set.of());
        ClassOrInterfaceTypeRef self = Types.of(ClassDesc.of("fixtures", "Self"));
        FinalClassToken<Object> token = UnsafeFacts.finalClassToken(self, List.of(ConstantDescs.CD_Object), () -> {
            if (asked.incrementAndGet() == 1) {
                throw new IllegalStateException("not yet");
            }
            return table;
        });

        assertThatIllegalStateException().isThrownBy(token::methods).withMessage("not yet");
        assertThat(token.methods()).isEqualTo(table);
        assertThat(token.methods()).isEqualTo(table);
        assertThat(asked).hasValue(2);
    }

    private static TypeShape<DeclaredKind.FinalClass> finalClass(ClassDesc desc) {
        return UnsafeFacts.shape(
                DeclaredKind.FINAL_CLASS,
                desc,
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false);
    }
}
