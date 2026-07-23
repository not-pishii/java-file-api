package me.supcheg.javafile.render;

import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

import static me.supcheg.javafile.render.SourceRenderer.standardFormat;
import static org.assertj.core.api.Assertions.assertThat;

class TypeRefRendererTest {

    @Test
    void classTypeRendersAsSimpleNameAndRegistersImport() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        String rendered = TypeRefRenderer.renderType(
                Types.of(ClassDesc.of("java.util", "List")), Context.of(standardFormat(), imports));

        assertThat(rendered).isEqualTo("List");
        assertThat(imports.sortedImports()).containsExactly("java.util.List");
    }

    @Test
    void parameterizedTypeRendersGenericArgs() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        ClassDesc list = ClassDesc.of("java.util", "List");
        ClassDesc string = ClassDesc.of("java.lang", "String");

        String rendered = TypeRefRenderer.renderType(
                new ParameterizedTypeRef(list, List.of(Types.exact(Types.of(string)))),
                Context.of(standardFormat(), imports));

        assertThat(rendered).isEqualTo("List<String>");
    }

    @Test
    void arrayTypeAppendsBrackets() {
        ImportManager imports = new ImportManager("me.supcheg.example");

        String rendered = TypeRefRenderer.renderType(
                new ArrayTypeRef(PrimitiveTypeRef.INT), Context.of(standardFormat(), imports));

        assertThat(rendered).isEqualTo("int[]");
    }

    @Test
    void wildcardTypeArgsRenderExtendsSuperAndUnbounded() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        Context ctx = Context.of(standardFormat(), imports);
        var number = Types.of(ClassDesc.of("java.lang", "Number"));

        assertThat(TypeRefRenderer.renderTypeArg(Types.extendsBound(number), ctx))
                .isEqualTo("? extends Number");
        assertThat(TypeRefRenderer.renderTypeArg(Types.superBound(number), ctx)).isEqualTo("? super Number");
        assertThat(TypeRefRenderer.renderTypeArg(Types.unbounded(), ctx)).isEqualTo("?");
    }

    @Test
    void modifiersRenderInCanonicalOrderRegardlessOfInputOrder() {
        String rendered = TypeRefRenderer.renderModifiers(Set.of(Modifier.FINAL, Modifier.PUBLIC));

        assertThat(rendered).isEqualTo("public final ");
    }

    @Test
    void noModifiersRendersEmptyString() {
        assertThat(TypeRefRenderer.renderModifiers(Set.of())).isEqualTo("");
    }

    @Test
    void paramsAreJoinedWithTypeBeforeName() {
        Context ctx = Context.of(standardFormat(), new ImportManager("me.supcheg.example"));
        List<Param> params = List.of(new Param("name", Types.of(ClassDesc.of("java.lang", "String"))));

        assertThat(TypeRefRenderer.renderParams(params, ctx)).isEqualTo("String name");
    }

    @Test
    void varargsParamRendersWithEllipsisBeforeName() {
        Context ctx = Context.of(standardFormat(), new ImportManager("me.supcheg.example"));
        List<Param> params = List.of(new Param("values", PrimitiveTypeRef.INT, List.of(), true));

        assertThat(TypeRefRenderer.renderParams(params, ctx)).isEqualTo("int... values");
    }

    @Test
    void annotatedParamRendersInlineAnnotationBeforeType() {
        Context ctx = Context.of(standardFormat(), new ImportManager("me.supcheg.example"));
        ClassDesc nullable = ClassDesc.of("me.supcheg.example", "Nullable");
        List<Param> params = List.of(new Param(
                "name",
                Types.of(ClassDesc.of("java.lang", "String")),
                List.of(new me.supcheg.javafile.annotation.AnnotationUse(nullable, List.of()))));

        assertThat(TypeRefRenderer.renderParams(params, ctx)).isEqualTo("@Nullable String name");
    }

    @Test
    void rendersAbstractAndStaticAndFinalInJlsOrder() {
        String rendered = TypeRefRenderer.renderModifiers(
                Set.of(Modifier.FINAL, Modifier.STATIC, Modifier.ABSTRACT, Modifier.PUBLIC));

        assertThat(rendered).isEqualTo("public abstract static final ");
    }

    @Test
    void rendersNonSealedWithAHyphenNotAnUnderscore() {
        String rendered = TypeRefRenderer.renderModifiers(Set.of(Modifier.NON_SEALED));

        assertThat(rendered).isEqualTo("non-sealed ");
    }

    @Test
    void typeVarRendersAsBareNameWithoutImport() {
        ImportManager imports = new ImportManager("p");

        assertThat(TypeRefRenderer.renderType(Types.typeVar("T"), Context.of(standardFormat(), imports)))
                .isEqualTo("T");
        assertThat(imports.sortedImports()).isEmpty();
    }

    @Test
    void typeVarInsideParameterizedTypeRendersAsArgument() {
        ImportManager imports = new ImportManager("p");
        var listOfT = Types.parameterized(ClassDesc.of("java.util", "List"), Types.exact(Types.typeVar("T")));

        assertThat(TypeRefRenderer.renderType(listOfT, Context.of(standardFormat(), imports)))
                .isEqualTo("List<T>");
        assertThat(imports.sortedImports()).containsExactly("java.util.List");
    }

    @Test
    void typeParamsRenderEmptyForNoParams() {
        assertThat(TypeRefRenderer.renderTypeParams(
                        java.util.List.of(), Context.of(standardFormat(), new ImportManager("p"))))
                .isEmpty();
    }

    @Test
    void typeParamsRenderNamesAndBounds() {
        ImportManager imports = new ImportManager("p");
        var params = java.util.List.of(
                new me.supcheg.javafile.type.TypeParam("T", java.util.List.of()),
                new me.supcheg.javafile.type.TypeParam(
                        "U",
                        java.util.List.of(
                                Types.of(ClassDesc.of("java.io", "Serializable")),
                                Types.parameterized(
                                        ClassDesc.of("java.lang", "Comparable"), Types.exact(Types.typeVar("U"))))));

        assertThat(TypeRefRenderer.renderTypeParams(params, Context.of(standardFormat(), imports)))
                .isEqualTo("<T, U extends Serializable & Comparable<U>>");
        assertThat(imports.sortedImports()).containsExactly("java.io.Serializable");
    }

    @Test
    void classTypeUseAnnotationRendersBeforeTheTypeName() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        Context ctx = Context.of(standardFormat(), imports);
        ClassDesc nonnull = ClassDesc.of("javax.annotation", "Nonnull");
        var annotated = Types.of(
                ClassDesc.of("java.lang", "String"),
                new me.supcheg.javafile.annotation.AnnotationUse(nonnull, List.of()));

        assertThat(TypeRefRenderer.renderType(annotated, ctx)).isEqualTo("@Nonnull String");
    }

    @Test
    void arrayTypeUseAnnotationRendersOnTheArrayLevelBeforeTheBrackets() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        Context ctx = Context.of(standardFormat(), imports);
        ClassDesc nonnull = ClassDesc.of("javax.annotation", "Nonnull");
        var annotated = Types.array(
                Types.of(ClassDesc.of("java.lang", "String")),
                new me.supcheg.javafile.annotation.AnnotationUse(nonnull, List.of()));

        assertThat(TypeRefRenderer.renderType(annotated, ctx)).isEqualTo("String @Nonnull []");
    }

    @Test
    void plainArrayTypeStillRendersIdenticallyToBeforeTypeUseAnnotationSupport() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        Context ctx = Context.of(standardFormat(), imports);

        assertThat(TypeRefRenderer.renderType(Types.array(Types.of(ClassDesc.of("java.lang", "String"))), ctx))
                .isEqualTo("String[]");
    }

    @Test
    void typeParamBoundTypeUseAnnotationRendersBeforeTheBoundName() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        Context ctx = Context.of(standardFormat(), imports);
        ClassDesc nonnull = ClassDesc.of("javax.annotation", "Nonnull");
        var annotatedBound = Types.of(
                ClassDesc.of("java.lang", "Number"),
                new me.supcheg.javafile.annotation.AnnotationUse(nonnull, List.of()));
        var param = new me.supcheg.javafile.type.TypeParam("T", java.util.List.of(annotatedBound));

        assertThat(TypeRefRenderer.renderTypeParams(java.util.List.of(param), ctx))
                .isEqualTo("<T extends @Nonnull Number>");
    }

    @Test
    void typeParamOwnAnnotationRendersBeforeTheParamName() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        Context ctx = Context.of(standardFormat(), imports);
        ClassDesc nonnull = ClassDesc.of("javax.annotation", "Nonnull");
        var param = new me.supcheg.javafile.type.TypeParam(
                "T",
                java.util.List.of(),
                List.of(new me.supcheg.javafile.annotation.AnnotationUse(nonnull, List.of())));

        assertThat(TypeRefRenderer.renderTypeParams(java.util.List.of(param), ctx))
                .isEqualTo("<@Nonnull T>");
    }

    @Test
    void plainClassTypeStillRendersIdenticallyToBeforeTypeUseAnnotationSupport() {
        ImportManager imports = new ImportManager("me.supcheg.example");
        Context ctx = Context.of(standardFormat(), imports);

        assertThat(TypeRefRenderer.renderType(Types.of(ClassDesc.of("java.util", "List")), ctx))
                .isEqualTo("List");
    }
}
