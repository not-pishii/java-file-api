package me.supcheg.javafile.doc;

import me.supcheg.javafile.code.NonEmptyList;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class DocModelTest {
    private static final ClassDesc GREETER = ClassDesc.of("com.example", "Greeter");

    @Test
    void aCommentSaysSomething() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new DocComment(List.of(), List.of()))
                .withMessageContaining("a description or a tag");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocComment.of(d -> {}))
                .withMessageContaining("a documentation comment must have a description or a tag");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocComment.of(""))
                .withMessageContaining("text must not be empty");
    }

    @Test
    void textAndCodeAreNotEmpty() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new DocInline.Text(""))
                .withMessageContaining("text must not be empty");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new DocInline.Code(""))
                .withMessageContaining("code must not be empty");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocText.of(""))
                .withMessageContaining("text must not be empty");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocText.of(t -> {}))
                .withMessageContaining("list must not be empty");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocText.of(t -> t.code("")))
                .withMessageContaining("code must not be empty");
    }

    @Test
    void aListHasAnItem() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocComment.of(d -> d.list(List.of())))
                .withMessageContaining("list must not be empty");
    }

    @Test
    void aReferenceIsToAClassOrAMemberOfOne() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.type(ConstantDescs.CD_int))
                .withMessageContaining("int");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.type(GREETER.arrayType()))
                .withMessageContaining("not a class or an interface: Greeter[]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.field(ConstantDescs.CD_int, "x"))
                .withMessageContaining("not a class or an interface: int");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.method(GREETER.arrayType(), "length"))
                .withMessageContaining("not a class or an interface: Greeter[]");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.constructor(ConstantDescs.CD_void))
                .withMessageContaining("not a class or an interface: void");
    }

    @Test
    void aReferenceNamesAMemberByAnIdentifier() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.field(GREETER, "not a name"))
                .withMessageContaining("not a valid Java identifier: 'not a name'");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.method(GREETER, "class"))
                .withMessageContaining("not a valid Java identifier: 'class'");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.method(GREETER, "m(int)"))
                .withMessageContaining("not a valid Java identifier: 'm(int)'");
    }

    @Test
    void aParameterOfAReferenceIsNotVoid() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.method(GREETER, "greet", ConstantDescs.CD_void))
                .withMessageContaining("void");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocRef.constructor(GREETER, ConstantDescs.CD_void))
                .withMessageContaining("void is not the type of a parameter");
    }

    @Test
    void aTagNamesAParameterByAnIdentifierAndAnExceptionByItsClass() {
        DocText text = DocText.of("text");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new DocTag.Param("a b", text))
                .withMessageContaining("not a valid Java identifier: 'a b'");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new DocTag.TypeParam("<T>", text))
                .withMessageContaining("not a valid Java identifier: '<T>'");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new DocTag.Throws(ConstantDescs.CD_int, text))
                .withMessageContaining("not a class: int");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocComment.of(d -> d.param("", "text")))
                .withMessageContaining("not a valid Java identifier: ''");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> DocComment.of(d -> d.returns("")))
                .withMessageContaining("text must not be empty");
    }

    @Test
    void theModelIsImmutable() {
        List<DocBlock> blocks = new java.util.ArrayList<>(List.of(new DocBlock.Paragraph(DocText.of("one"))));
        List<ClassDesc> params = new java.util.ArrayList<>(List.of(ConstantDescs.CD_int));
        DocComment comment = new DocComment(blocks, List.of());
        DocRef.Method method = new DocRef.Method(GREETER, "greet", params);
        DocRef.Constructor constructor = new DocRef.Constructor(GREETER, params);

        blocks.clear();
        params.clear();

        assertThat(comment.description()).hasSize(1);
        assertThat(method.params()).containsExactly(ConstantDescs.CD_int);
        assertThat(constructor.params()).containsExactly(ConstantDescs.CD_int);
    }

    @Test
    void theBuildersMakeWhatTheRecordsSay() {
        DocRef concat = DocRef.method(ConstantDescs.CD_String, "concat", ConstantDescs.CD_String);
        DocText linked = DocText.of(t -> t.text("see ").link(concat).part(new DocInline.Code("x")));
        DocComment built = DocComment.of(d -> d.paragraph("Greets.")
                .paragraph(linked)
                .list(List.of(DocText.of("an item")))
                .block(new DocBlock.Paragraph(DocText.of("more")))
                .param("name", "who")
                .param("other", linked)
                .typeParam("T", "what")
                .typeParam("U", linked)
                .returns("it")
                .returns(linked)
                .throws_(ClassDesc.of("java.io", "IOException"), "when")
                .throws_(ClassDesc.of("java.io", "IOException"), linked)
                .see(concat)
                .since("1.0")
                .deprecated("gone")
                .deprecated(linked)
                .tag(new DocTag.See(DocRef.type(GREETER))));

        assertThat(linked)
                .isEqualTo(DocText.of(new DocInline.Text("see "), new DocInline.Link(concat), new DocInline.Code("x")));
        assertThat(built)
                .isEqualTo(new DocComment(
                        List.of(
                                new DocBlock.Paragraph(DocText.of("Greets.")),
                                new DocBlock.Paragraph(linked),
                                new DocBlock.BulletList(new NonEmptyList<>(DocText.of("an item"), List.of())),
                                new DocBlock.Paragraph(DocText.of("more"))),
                        List.of(
                                new DocTag.Param("name", DocText.of("who")),
                                new DocTag.Param("other", linked),
                                new DocTag.TypeParam("T", DocText.of("what")),
                                new DocTag.TypeParam("U", linked),
                                new DocTag.Return(DocText.of("it")),
                                new DocTag.Return(linked),
                                new DocTag.Throws(ClassDesc.of("java.io", "IOException"), DocText.of("when")),
                                new DocTag.Throws(ClassDesc.of("java.io", "IOException"), linked),
                                new DocTag.See(concat),
                                new DocTag.Since(DocText.of("1.0")),
                                new DocTag.Deprecated(DocText.of("gone")),
                                new DocTag.Deprecated(linked),
                                new DocTag.See(new DocRef.Type(GREETER)))));
        assertThat(built).hasSameHashCodeAs(new DocComment(built.description(), built.tags()));
        assertThat(DocComment.of("Greets.").toString()).contains("Paragraph", "Greets.");
        assertThat(DocRef.field(GREETER, "name")).isEqualTo(new DocRef.Field(GREETER, "name"));
        assertThat(DocRef.constructor(GREETER)).isEqualTo(new DocRef.Constructor(GREETER, List.of()));
    }
}
