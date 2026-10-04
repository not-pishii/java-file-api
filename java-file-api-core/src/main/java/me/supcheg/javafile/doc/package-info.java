/// Documentation comments of declarations.
///
/// A [me.supcheg.javafile.doc.DocComment] is what a comment says, not how it
/// is written: paragraphs and lists of text, code and links to program
/// elements, and block tags. The renderer writes it as a traditional comment
/// (`/** ... */`) or as a Markdown one (`///`, Java 23 and later), see
/// [me.supcheg.javafile.doc.DocStyle], and escapes the text for the syntax
/// it writes, so that no text ends a comment or is read as markup.
///
/// ```java
/// DocComment doc = DocComment.of(d -> d
///         .paragraph(DocText.of(t -> t
///                 .text("Greets like ")
///                 .link(DocRef.method(ConstantDescs.CD_String, "concat", ConstantDescs.CD_String))
///                 .text(".")))
///         .param("name", "who is greeted")
///         .returns("the greeting"));
/// ```
///
/// Every declaration builder takes one with `withDoc`.
@NullMarked
package me.supcheg.javafile.doc;

import org.jspecify.annotations.NullMarked;
