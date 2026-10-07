package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.code.NonEmptyList;
import me.supcheg.javafile.doc.DocBlock;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocInline;
import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.doc.DocTag;
import me.supcheg.javafile.doc.DocText;
import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The documentation comments of a generated metamodel (mini-spec Q14): the
/// reader of the source is told why the metamodel is what it is, and which
/// member each fact is of.
///
/// The comment of the metamodel class says whose metamodel it is and whether
/// it is full or token-only, and why:
///
/// - a full one `@Facts` asks for, or is there for the requested types that
///   extend or implement the type ([TypeGraph.Reason.Supertype]);
/// - a token-only one is of a type signatures only mention
///   ([TypeGraph.Reason.Mentioned]), or of a supertype no full metamodel can
///   be made of ([About.Declined]);
/// - one without the type parameters of its type has a bound it cannot write.
///
/// A full one goes on with where the facts of the inherited members are —
/// the metamodels of the supertypes ([About.Supertype]) —, with the
/// supertypes whose members it adopts, and with the members that have no
/// fact and why: what [MemberPlan#skipped()] holds, which is what the
/// processor warns of, in the same words.
///
/// The comment of a fact links to its member — through the type itself for
/// a member adopted from a supertype that is not `public`, to which no link
/// from another package resolves ([Models#reference]).
///
/// The types a comment names for a reason are named up to [#NAMED] of them,
/// in the order of the graph — sorted by binary name —, and the rest are
/// counted: the comment is the same whatever order the types were found in.
final class MetamodelDocs {
    /// How many of the types a metamodel is there for its comment names.
    static final int NAMED = 3;

    private MetamodelDocs() {}

    /// Why a metamodel is what it is: what its comment tells beyond what the metamodel holds.
    sealed interface About {

        /// Why the type is in the graph of the round, see [TypeGraph#reasons].
        ///
        /// @return the reasons
        List<TypeGraph.Reason> reasons();

        /// A full metamodel.
        ///
        /// @param reasons why the type is in the graph of the round
        /// @param supertypes the `public` supertypes nearest to the type, sorted by name: where the facts
        ///     of the members it inherits are
        /// @param protectedMembers whether the type declares `protected` members that have no facts
        record Full(List<TypeGraph.Reason> reasons, List<Supertype> supertypes, Protected protectedMembers)
                implements About {
            /// Copies the lists.
            public Full {
                reasons = List.copyOf(reasons);
                supertypes = List.copyOf(supertypes);
            }
        }

        /// The token-only metamodel of a type signatures mention.
        ///
        /// @param reasons why the type is in the graph of the round
        record Mentioned(List<TypeGraph.Reason> reasons) implements About {
            /// Copies the list.
            public Mentioned {
                reasons = List.copyOf(reasons);
            }
        }

        /// The token-only metamodel of a type a requested type extends or implements, which no full
        /// one can be made of ([TypeGraph.Node.Declined]).
        ///
        /// @param reasons why the type is in the graph of the round
        /// @param reason why there is no full metamodel, a sentence about the type
        record Declined(List<TypeGraph.Reason> reasons, String reason) implements About {
            /// Copies the list.
            public Declined {
                reasons = List.copyOf(reasons);
            }
        }

        /// A `public` supertype of a type with a full metamodel.
        sealed interface Supertype {

            /// The supertype has a full metamodel: the members inherited from it are called through that.
            ///
            /// @param metamodel the metamodel class
            record Full(ClassDesc metamodel) implements Supertype {}

            /// The supertype has no full metamodel: the members inherited from it have no facts.
            ///
            /// @param name the binary name of the supertype
            /// @param reason why it has none, a sentence about the type
            record None(String name, String reason) implements Supertype {}
        }

        /// Whether a type declares `protected` members that have no facts: those of a type that cannot be
        /// extended, which no subclass reaches.
        enum Protected {
            /// It cannot be extended and declares some.
            SOME,
            /// It declares none, or can be extended: they have facts.
            NONE
        }
    }

    // ---- the metamodel class

    /// Whether a metamodel has the type parameters of its type.
    enum Generics {
        /// It has them, or the type has none.
        DECLARED,
        /// The type is generic, but the metamodel is of the raw type: a bound cannot be written.
        RAW
    }

    /// The comment of a metamodel class.
    ///
    /// @param type the type the metamodel is of
    /// @param typeParams the type parameters of the metamodel class, as it names them
    /// @param generics whether the metamodel has the type parameters of its type
    /// @param about why the metamodel is what it is
    /// @param plan the facts of a full metamodel and the members without one; empty for a token-only one
    /// @return the comment
    static DocComment type(
            ClassDesc type, List<String> typeParams, Generics generics, About about, Optional<MemberPlan> plan) {
        Stream<DocBlock> description =
                switch (about) {
                    case About.Full full -> full(type, full, plan.orElseThrow());
                    case About.Mentioned(List<TypeGraph.Reason> reasons) ->
                        Stream.of(
                                        tokenOnly(type),
                                        // what to do about it follows the reason, in its paragraph
                                        paragraph(
                                                mentioned(type, reasons),
                                                Stream.of(generics)
                                                        .filter(Generics.DECLARED::equals)
                                                        .flatMap(_ -> Stream.of(
                                                                words("For the facts of its members add "),
                                                                code(ClassDescNames.qualifiedByDots(type) + ".class"),
                                                                words(" to "),
                                                                code("@Facts"),
                                                                words(".")))))
                                .flatMap(Function.identity());
                    case About.Declined(List<TypeGraph.Reason> reasons, String reason) ->
                        Stream.of(
                                        tokenOnly(type),
                                        paragraph(
                                                Stream.of(link(type), words(" is a supertype of ")),
                                                named(supertypeOf(reasons), "requested type"),
                                                Stream.of(words(", but has no full metamodel: " + reason
                                                        + ". The members inherited from it have no facts."))),
                                        paragraph(mentioned(type, reasons)))
                                .flatMap(Function.identity());
                };
        Stream<DocBlock> raw =
                switch (generics) {
                    case DECLARED -> Stream.empty();
                    case RAW ->
                        paragraph(Stream.of(
                                words("The metamodel has no type parameters, and its token is of the raw type: a"
                                        + " bound of a type parameter of "),
                                link(type),
                                words(" mentions a type the metamodel cannot name"
                                        + (about instanceof About.Mentioned
                                                ? ", so no full metamodel can be made of it either."
                                                : "."))));
                };
        return new DocComment(
                Stream.concat(description, raw).toList(),
                typeParams.stream()
                        .<DocTag>map(param ->
                                new DocTag.TypeParam(param, text(Stream.of(words("a type argument of "), link(type)))))
                        .toList());
    }

    private static Stream<DocBlock> tokenOnly(ClassDesc type) {
        return paragraph(Stream.of(
                words("The token-only metamodel of "),
                link(type),
                words(": its shape and its token, no facts of its members.")));
    }

    /// That signatures mention the type, and whose; nothing if none does.
    private static Stream<DocInline> mentioned(ClassDesc type, List<TypeGraph.Reason> reasons) {
        List<ClassDesc> by = reasons.stream()
                .flatMap(reason -> reason instanceof TypeGraph.Reason.Mentioned(String name)
                        ? Stream.of(ClassDesc.of(name))
                        : Stream.empty())
                .toList();
        return by.isEmpty()
                ? Stream.empty()
                : Stream.of(
                                Stream.of(
                                        code("@Facts"),
                                        words(" does not ask for "),
                                        link(type),
                                        words(": it is only mentioned in the signatures of ")),
                                named(by, "type"),
                                Stream.of(words(". ")))
                        .flatMap(Function.identity());
    }

    /// The requested types the type is a supertype of.
    private static List<ClassDesc> supertypeOf(List<TypeGraph.Reason> reasons) {
        return reasons.stream()
                .flatMap(reason -> reason instanceof TypeGraph.Reason.Supertype(String of)
                        ? Stream.of(ClassDesc.of(of))
                        : Stream.empty())
                .toList();
    }

    /// Links to the first [#NAMED] of the types, and how many more there are: `[A], [B], [C] and 2
    /// more types`.
    private static Stream<DocInline> named(List<ClassDesc> types, String noun) {
        List<DocInline> shown =
                types.stream().limit(NAMED).map(MetamodelDocs::link).toList();
        int more = types.size() - shown.size();
        return more == 0
                ? joined(shown)
                : Stream.concat(
                        commas(shown), Stream.of(words(" and " + more + " more " + noun + (more == 1 ? "" : "s"))));
    }

    /// `a`, `a and b`, `a, b and c`.
    private static Stream<DocInline> joined(List<DocInline> items) {
        return IntStream.range(0, items.size())
                .boxed()
                .flatMap(i -> Stream.of(words(i == items.size() - 1 ? " and " : ", "), items.get(i))
                        .skip(i == 0 ? 1 : 0));
    }

    /// `a`, `a, b`, `a, b, c`.
    private static Stream<DocInline> commas(List<DocInline> items) {
        return IntStream.range(0, items.size())
                .boxed()
                .flatMap(i -> Stream.of(words(", "), items.get(i)).skip(i == 0 ? 1 : 0));
    }

    private static Stream<DocBlock> full(ClassDesc type, About.Full about, MemberPlan plan) {
        boolean asked = about.reasons().stream().anyMatch(TypeGraph.Reason.Asked.class::isInstance);
        List<DocInline> through = about.supertypes().stream()
                .flatMap(supertype -> supertype instanceof About.Supertype.Full(ClassDesc metamodel)
                        ? Stream.of(link(metamodel))
                        : Stream.empty())
                .toList();
        List<DocInline> adopted = Stream.concat(
                        plan.members().stream().map(MemberPlan.Fact::origin),
                        plan.skipped().stream().map(MemberPlan.Skip::origin))
                .flatMap(origin ->
                        origin instanceof MemberPlan.Origin.Adopted(String from) ? Stream.of(from) : Stream.empty())
                .distinct()
                .sorted()
                .map(MetamodelDocs::code)
                .toList();
        boolean one = adopted.size() == 1;
        boolean held = plan.members().stream()
                .anyMatch(fact -> switch (fact.model().access()) {
                    case PUBLIC -> false;
                    case PROTECTED -> true;
                });
        return Stream.of(
                        paragraph(
                                Stream.of(words("The full metamodel of "), link(type)),
                                asked
                                        ? Stream.of(words(", which "), code("@Facts"), words(" asks for"))
                                        : Stream.empty(),
                                Stream.concat(
                                        Stream.of(words(": a fact of every "), code("public")),
                                        held
                                                ? Stream.of(
                                                        words(" and every "),
                                                        code("protected"),
                                                        words(" member the type declares, the latter held back"
                                                                + " for a subclass."))
                                                : Stream.of(words(" member the type declares.")))),
                        asked
                                ? Stream.<DocBlock>empty()
                                : paragraph(
                                        Stream.of(
                                                code("@Facts"),
                                                words(" does not ask for "),
                                                link(type),
                                                words(": it is here as a supertype of ")),
                                        named(supertypeOf(about.reasons()), "requested type"),
                                        Stream.of(words(
                                                ", whose inherited members are called through this" + " metamodel."))),
                        through.isEmpty()
                                ? Stream.<DocBlock>empty()
                                : paragraph(
                                        Stream.of(
                                                words("A member "),
                                                link(type),
                                                words(" inherits has its fact in the metamodel of the supertype"
                                                        + " that declares it: ")),
                                        joined(through),
                                        Stream.of(words("."))),
                        about.supertypes().stream()
                                .flatMap(supertype -> supertype
                                                instanceof About.Supertype.None(String name, String reason)
                                        ? paragraph(Stream.of(
                                                words("The members inherited from "),
                                                code(name),
                                                words(", which has no full metamodel, have no facts: " + reason + ".")))
                                        : Stream.empty()),
                        adopted.isEmpty()
                                ? Stream.<DocBlock>empty()
                                : paragraph(
                                        joined(adopted),
                                        Stream.of(
                                                words(
                                                        one
                                                                ? ", a supertype that is not "
                                                                : ", supertypes that are not "),
                                                code("public"),
                                                words(one ? ", has no metamodel: the " : ", have no metamodels: the "),
                                                code("public"),
                                                words(" members inherited from " + (one ? "it" : "them")
                                                        + " are facts of this one."))),
                        plan.skipped().isEmpty()
                                ? Stream.<DocBlock>empty()
                                : Stream.concat(
                                        paragraph(Stream.of(words("These members have no fact:"))),
                                        Stream.of(new DocBlock.BulletList(NonEmptyList.copyOf(plan.skipped().stream()
                                                .map(MetamodelDocs::skipped)
                                                .toList())))),
                        switch (about.protectedMembers()) {
                            case SOME ->
                                paragraph(Stream.of(
                                        words("The "),
                                        code("protected"),
                                        words(" members have no facts: the type cannot be extended, and"
                                                + " only a subclass reaches them.")));
                            case NONE -> Stream.<DocBlock>empty();
                        })
                .flatMap(Function.identity());
    }

    /// A member without a fact, as the warning of the processor tells it ([MemberPlan.Skip#told()]):
    /// the member in code, but for the single abstract method, which is told in words.
    private static DocText skipped(MemberPlan.Skip skip) {
        return switch (skip.origin()) {
            case MemberPlan.Origin.Inherited _ -> DocText.of(skip.told());
            case MemberPlan.Origin.Declared _, MemberPlan.Origin.Adopted _ ->
                DocText.of(code(skip.member()), words(MemberPlan.Skip.WHICH + skip.reason()));
        };
    }

    // ---- what every metamodel has

    /// The comment of the nested class `Data`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment data(ClassDesc type) {
        return sentence(
                words("The shape of "),
                link(type),
                words(" as plain data: initializing it touches no other metamodel."));
    }

    /// The comment of `Data.SHAPE`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment shape(ClassDesc type) {
        return sentence(
                words("What "),
                link(type),
                words(" was when this metamodel was generated. Its tokens are made from it."));
    }

    /// The comment of the nested class `Data.Inherited`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment inherited(ClassDesc type) {
        return sentence(
                words("What a class that extends or implements "),
                link(type),
                words(" inherits, loaded only when one is declared."));
    }

    /// The comment of `Data.Inherited.HERITAGE`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment heritage(ClassDesc type) {
        return sentence(
                words("Every constructor and method of "),
                link(type),
                words(" that is not private, as a member of it, whoever declares it."));
    }

    /// The comment of the nested class `Canonical`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment canonical(ClassDesc type) {
        return sentence(
                words("The canonical form of "),
                link(type),
                words(", loaded only to compare the type with the one on the target classpath."));
    }

    /// The comment of `Canonical.TEXT`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment canonicalText(ClassDesc type) {
        return sentence(words("The canonical form of "), link(type), words("."));
    }

    /// The comment of `TOKEN`.
    ///
    /// @param type the type the metamodel is of
    /// @param generics whether the token is of a generic type used raw
    /// @return the comment
    static DocComment token(ClassDesc type, Generics generics) {
        return sentence(
                words(
                        switch (generics) {
                            case DECLARED -> "The token of ";
                            case RAW -> "The token of the raw type ";
                        }),
                link(type),
                words("."));
    }

    /// The comment of `ANY`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment any(ClassDesc type) {
        return sentence(words("The token of "), link(type), words(" with a wildcard for every type argument."));
    }

    /// The comment of `token`.
    ///
    /// @param type the type the metamodel is of
    /// @return the comment
    static DocComment instanceToken(ClassDesc type) {
        return sentence(words("The token of "), link(type), words(" with the type arguments of this metamodel."));
    }

    /// The comment of the constructor of a generic metamodel.
    ///
    /// @param type the type the metamodel is of
    /// @param typeParams the type parameters of the metamodel, as it names them
    /// @param witnesses the parameters that take their tokens, one per type parameter
    /// @return the comment
    static DocComment constructor(ClassDesc type, List<String> typeParams, List<String> witnesses) {
        return new DocComment(
                paragraph(Stream.of(
                                words("The metamodel of "),
                                link(type),
                                words(" with the type arguments the tokens give.")))
                        .toList(),
                witnesses(typeParams, witnesses).toList());
    }

    /// `@param e the token of the type argument E`, for each parameter that takes a token.
    private static Stream<DocTag> witnesses(List<String> typeParams, List<String> witnesses) {
        return IntStream.range(0, witnesses.size())
                .mapToObj(i -> new DocTag.Param(
                        witnesses.get(i),
                        text(Stream.of(words("the token of the type argument "), code(typeParams.get(i))))));
    }

    // ---- facts

    /// The comment of the fact of an enum constant.
    ///
    /// @param type the enum
    /// @param constant the name of the constant
    /// @return the comment
    static DocComment enumConstant(ClassDesc type, String constant) {
        return sentence(words("The fact of "), new DocInline.Link(DocRef.field(type, constant)), words("."));
    }

    /// The comment of the fact of a field, a constructor or a method that is not generic.
    ///
    /// @param fact the fact
    /// @return the comment
    static DocComment fact(MemberPlan.Fact fact) {
        return new DocComment(paragraph(factOf(fact), Stream.of(words("."))).toList(), List.of());
    }

    /// The comment of the method that makes the fact of a generic method.
    ///
    /// @param fact the fact
    /// @param typeParams the type parameters of the method, as the metamodel names them
    /// @param witnesses the parameters that take their tokens, one per type parameter
    /// @return the comment
    static DocComment factory(MemberPlan.Fact fact, List<String> typeParams, List<String> witnesses) {
        return new DocComment(
                paragraph(factOf(fact), Stream.of(words(", for the type arguments the tokens give.")))
                        .toList(),
                Stream.of(
                                typeParams.stream()
                                        .<DocTag>map(param -> new DocTag.TypeParam(
                                                param, DocText.of("a type argument of the method"))),
                                witnesses(typeParams, witnesses),
                                Stream.<DocTag>of(new DocTag.Return(DocText.of("the fact"))))
                        .flatMap(Function.identity())
                        .toList());
    }

    /// The comment of `sam`.
    ///
    /// @param sam the fact of the single abstract method
    /// @return the comment
    static DocComment sam(MemberPlan.SamFact sam) {
        return sentence(
                words("The fact of the single abstract method "),
                new DocInline.Link(sam.member()),
                words(", which a lambda implements."));
    }

    /// `The fact of [member]`, and where the member is declared if the link is not through that type.
    private static Stream<DocInline> factOf(MemberPlan.Fact fact) {
        return Stream.<Stream<DocInline>>of(
                        Stream.of(words("The fact of "), new DocInline.Link(fact.member())),
                        switch (fact.origin()) {
                            case MemberPlan.Origin.Declared _, MemberPlan.Origin.Inherited _ -> Stream.empty();
                            case MemberPlan.Origin.Adopted(String from) ->
                                Stream.of(
                                        words(", declared in "), code(from), words(", which is not "), code("public"));
                        },
                        switch (fact.model().access()) {
                            case PUBLIC -> Stream.empty();
                            case PROTECTED ->
                                Stream.of(words(", which is "), code("protected"), words(": a subclass alone uses it"));
                        })
                .flatMap(Function.identity());
    }

    // ---- text

    private static DocInline words(String text) {
        return new DocInline.Text(text);
    }

    private static DocInline code(String code) {
        return new DocInline.Code(code);
    }

    private static DocInline link(ClassDesc type) {
        return new DocInline.Link(DocRef.type(type));
    }

    private static DocText text(Stream<DocInline> parts) {
        return new DocText(NonEmptyList.copyOf(parts.toList()));
    }

    /// A paragraph of the parts; none if there are no parts.
    private static Stream<DocBlock> paragraph(Stream<DocInline> parts) {
        List<DocInline> all = parts.toList();
        return all.isEmpty() ? Stream.empty() : Stream.of(new DocBlock.Paragraph(text(all.stream())));
    }

    /// A paragraph of the parts, one after another; none if there are no parts.
    private static Stream<DocBlock> paragraph(Stream<DocInline> first, Stream<DocInline> second) {
        return paragraph(Stream.concat(first, second));
    }

    /// A paragraph of the parts, one after another; none if there are no parts.
    private static Stream<DocBlock> paragraph(
            Stream<DocInline> first, Stream<DocInline> second, Stream<DocInline> third) {
        return paragraph(Stream.concat(first, Stream.concat(second, third)));
    }

    /// A comment of one paragraph.
    private static DocComment sentence(DocInline... parts) {
        return new DocComment(paragraph(Stream.of(parts)).toList(), List.of());
    }
}
