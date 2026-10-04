package me.supcheg.javafile.doc;

import me.supcheg.javafile.code.NonEmptyList;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

/// Builds a [DocComment]: the blocks of its description and its block tags,
/// each in the order they are added.
///
/// Obtained from [DocComment#of(java.util.function.Consumer)] or the
/// `withDoc` method of a declaration builder. A method that takes a `String`
/// takes plain text, written as it is; for code and links build a [DocText]
/// with [DocText#of(java.util.function.Consumer)].
///
/// Instances are not thread-safe.
public final class DocCommentBuilder {

    private final List<DocBlock> description = new ArrayList<>();
    private final List<DocTag> tags = new ArrayList<>();

    DocCommentBuilder() {}

    /// Adds a paragraph of plain text.
    ///
    /// @param text the text
    /// @return this builder
    /// @throws IllegalArgumentException if `text` is empty
    public DocCommentBuilder paragraph(String text) {
        return paragraph(DocText.of(text));
    }

    /// Adds a paragraph.
    ///
    /// @param text the text
    /// @return this builder
    public DocCommentBuilder paragraph(DocText text) {
        return block(new DocBlock.Paragraph(text));
    }

    /// Adds a bulleted list.
    ///
    /// @param items the items, in order
    /// @return this builder
    /// @throws IllegalArgumentException if `items` is empty
    public DocCommentBuilder list(List<? extends DocText> items) {
        return block(new DocBlock.BulletList(NonEmptyList.copyOf(items)));
    }

    /// Adds a pre-built block to the description.
    ///
    /// @param block the block
    /// @return this builder
    public DocCommentBuilder block(DocBlock block) {
        description.add(block);
        return this;
    }

    /// Adds `@param name`, for a parameter or a record component.
    ///
    /// @param name the name of the parameter
    /// @param description what it is, as plain text
    /// @return this builder
    /// @throws IllegalArgumentException if `name` is not an identifier or `description` is empty
    public DocCommentBuilder param(String name, String description) {
        return param(name, DocText.of(description));
    }

    /// Adds `@param name`, for a parameter or a record component.
    ///
    /// @param name the name of the parameter
    /// @param description what it is
    /// @return this builder
    /// @throws IllegalArgumentException if `name` is not an identifier
    public DocCommentBuilder param(String name, DocText description) {
        return tag(new DocTag.Param(name, description));
    }

    /// Adds `@param <T>`, for a type parameter.
    ///
    /// @param name the name of the type parameter
    /// @param description what it is, as plain text
    /// @return this builder
    /// @throws IllegalArgumentException if `name` is not an identifier or `description` is empty
    public DocCommentBuilder typeParam(String name, String description) {
        return typeParam(name, DocText.of(description));
    }

    /// Adds `@param <T>`, for a type parameter.
    ///
    /// @param name the name of the type parameter
    /// @param description what it is
    /// @return this builder
    /// @throws IllegalArgumentException if `name` is not an identifier
    public DocCommentBuilder typeParam(String name, DocText description) {
        return tag(new DocTag.TypeParam(name, description));
    }

    /// Adds `@return`.
    ///
    /// @param description what is returned, as plain text
    /// @return this builder
    /// @throws IllegalArgumentException if `description` is empty
    public DocCommentBuilder returns(String description) {
        return returns(DocText.of(description));
    }

    /// Adds `@return`.
    ///
    /// @param description what is returned
    /// @return this builder
    public DocCommentBuilder returns(DocText description) {
        return tag(new DocTag.Return(description));
    }

    /// Adds `@throws`.
    ///
    /// @param exception the class of the exception
    /// @param description when it is thrown, as plain text
    /// @return this builder
    /// @throws IllegalArgumentException if `exception` is a primitive or an array, or `description` is empty
    public DocCommentBuilder throws_(ClassDesc exception, String description) {
        return throws_(exception, DocText.of(description));
    }

    /// Adds `@throws`.
    ///
    /// @param exception the class of the exception
    /// @param description when it is thrown
    /// @return this builder
    /// @throws IllegalArgumentException if `exception` is a primitive or an array
    public DocCommentBuilder throws_(ClassDesc exception, DocText description) {
        return tag(new DocTag.Throws(exception, description));
    }

    /// Adds `@see`.
    ///
    /// @param target the program element to look at
    /// @return this builder
    public DocCommentBuilder see(DocRef target) {
        return tag(new DocTag.See(target));
    }

    /// Adds `@since`.
    ///
    /// @param version the version, as plain text
    /// @return this builder
    /// @throws IllegalArgumentException if `version` is empty
    public DocCommentBuilder since(String version) {
        return tag(new DocTag.Since(DocText.of(version)));
    }

    /// Adds `@deprecated`.
    ///
    /// @param description why, and what to use instead, as plain text
    /// @return this builder
    /// @throws IllegalArgumentException if `description` is empty
    public DocCommentBuilder deprecated(String description) {
        return deprecated(DocText.of(description));
    }

    /// Adds `@deprecated`.
    ///
    /// @param description why, and what to use instead
    /// @return this builder
    public DocCommentBuilder deprecated(DocText description) {
        return tag(new DocTag.Deprecated(description));
    }

    /// Adds a pre-built block tag.
    ///
    /// @param tag the tag
    /// @return this builder
    public DocCommentBuilder tag(DocTag tag) {
        tags.add(tag);
        return this;
    }

    DocComment build() {
        return new DocComment(description, tags);
    }
}
