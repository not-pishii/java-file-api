package me.supcheg.javafile;

import me.supcheg.javafile.annotation.AnnotationMember;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.annotation.EnumValue;
import me.supcheg.javafile.code.CatchClause;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.EnhancedForStmt;
import me.supcheg.javafile.code.FieldAccessExpr;
import me.supcheg.javafile.code.InferredLambdaParams;
import me.supcheg.javafile.code.InstanceOfExpr;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LocalVarDeclStmt;
import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.code.Resource;
import me.supcheg.javafile.code.StringLiteral;
import me.supcheg.javafile.code.TypePatternLabel;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.RecordComponent;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.model.StaticMethodDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// Verifies that every name-bearing record in the model actually invokes
/// [Identifiers#requireValid] on construction.
///
/// [Identifiers] itself is exhaustively tested elsewhere ([IdentifiersTest],
/// [IdentifiersProperties]); this class instead exists so that a future edit
/// which silently drops a `requireValid` call from one of these ~24
/// construction sites is caught by the suite, rather than only being
/// noticed by manual inspection. Each case below constructs its record with
/// a single obviously-invalid identifier (a leading digit) and asserts that
/// construction throws; the exhaustive validity rules are not re-tested
/// here.
class IdentifierGuardWiringTest {

    private static final String BAD_NAME = "1bad";

    private static final ClassDesc STRING_DESC = ClassDesc.of("java.lang", "String");
    private static final ClassDesc IO_EXCEPTION_DESC = ClassDesc.of("java.io", "IOException");
    private static final ClassDesc LEVEL_DESC = ClassDesc.of("me.supcheg.meta", "Level");
    private static final TypeRef STRING_TYPE = Types.of(STRING_DESC);
    private static final ClassOrInterfaceTypeRef IO_EXCEPTION_TYPE = Types.of(IO_EXCEPTION_DESC);

    @ParameterizedTest(name = "{0}")
    @MethodSource("constructionSites")
    void rejectsInvalidIdentifierAtConstruction(String site, ThrowingCallable construction) {
        assertThatThrownBy(construction).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> constructionSites() {
        return Stream.of(
                Arguments.of("Param", (ThrowingCallable) () -> new Param(BAD_NAME, STRING_TYPE)),
                Arguments.of("FieldDecl", (ThrowingCallable) () ->
                        new FieldDecl(BAD_NAME, STRING_TYPE, List.of(), Set.of(Modifier.PRIVATE), Optional.empty())),
                Arguments.of("MethodDecl", (ThrowingCallable) () -> new MethodDecl(
                        BAD_NAME,
                        Optional.of(STRING_TYPE),
                        List.of(),
                        Set.of(Modifier.PUBLIC),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of())),
                Arguments.of("AbstractMethodDecl", (ThrowingCallable) () -> new AbstractMethodDecl(
                        BAD_NAME,
                        Optional.of(STRING_TYPE),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                        List.of())),
                Arguments.of("DefaultMethodDecl", (ThrowingCallable) () -> new DefaultMethodDecl(
                        BAD_NAME,
                        Optional.of(STRING_TYPE),
                        List.of(),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of())),
                Arguments.of("StaticMethodDecl", (ThrowingCallable) () -> new StaticMethodDecl(
                        BAD_NAME,
                        Optional.of(STRING_TYPE),
                        List.of(),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of())),
                Arguments.of("ConstantDecl", (ThrowingCallable)
                        () -> new ConstantDecl(BAD_NAME, STRING_TYPE, List.of(), new StringLiteral("x"))),
                Arguments.of("StaticFieldDecl", (ThrowingCallable)
                        () -> new StaticFieldDecl(BAD_NAME, STRING_TYPE, List.of(), new StringLiteral("x"))),
                Arguments.of("RecordComponent", (ThrowingCallable)
                        () -> new RecordComponent(BAD_NAME, PrimitiveTypeRef.INT)),
                Arguments.of("EnumConstant", (ThrowingCallable)
                        () -> new EnumConstant(BAD_NAME, List.of(), List.of(), List.of())),
                Arguments.of("CatchClause", (ThrowingCallable) () ->
                        new CatchClause(NonEmptyList.copyOf(List.of(IO_EXCEPTION_TYPE)), BAD_NAME, CodeBody.EMPTY)),
                Arguments.of("LocalVarDeclStmt", (ThrowingCallable)
                        () -> new LocalVarDeclStmt(Optional.empty(), BAD_NAME, new IntLiteral(0))),
                Arguments.of("EnhancedForStmt", (ThrowingCallable) () -> new EnhancedForStmt(
                        STRING_TYPE, BAD_NAME, new FieldAccessExpr(Optional.empty(), "items"), CodeBody.EMPTY)),
                Arguments.of("TypePatternLabel", (ThrowingCallable)
                        () -> new TypePatternLabel(STRING_TYPE, BAD_NAME, Optional.empty())),
                Arguments.of("InstanceOfExpr", (ThrowingCallable) () -> new InstanceOfExpr(
                        new FieldAccessExpr(Optional.empty(), "obj"), STRING_TYPE, Optional.of(BAD_NAME))),
                Arguments.of(
                        "FieldAccessExpr", (ThrowingCallable) () -> new FieldAccessExpr(Optional.empty(), BAD_NAME)),
                Arguments.of("MethodCallExpr", (ThrowingCallable)
                        () -> new MethodCallExpr(Optional.empty(), BAD_NAME, List.of())),
                Arguments.of("TypeVarRef", (ThrowingCallable) () -> new TypeVarRef(BAD_NAME)),
                Arguments.of("TypeParam", (ThrowingCallable) () -> new TypeParam(BAD_NAME, List.of())),
                Arguments.of("Resource.Declared", (ThrowingCallable)
                        () -> new Resource.Declared(Optional.empty(), BAD_NAME, new StringLiteral("x"))),
                Arguments.of("Resource.Existing", (ThrowingCallable) () -> new Resource.Existing(BAD_NAME)),
                Arguments.of(
                        "InferredLambdaParams", (ThrowingCallable) () -> new InferredLambdaParams(List.of(BAD_NAME))),
                Arguments.of("AnnotationMember", (ThrowingCallable)
                        () -> new AnnotationMember(BAD_NAME, AnnotationValues.literal(1))),
                Arguments.of("EnumValue", (ThrowingCallable) () -> new EnumValue(LEVEL_DESC, BAD_NAME)));
    }
}
