package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.annotation.SingleAnnotationValue;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.facts.meta.MetamodelFormat;
import me.supcheg.javafile.langmodel.mirror.Canonical;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.type.UnboundedTypeArg;
import org.jspecify.annotations.NullMarked;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// Writes a metamodel as a core [JavaFile] (mini-spec §2.3, §2.6, §2.7).
///
/// Every metamodel is a final class `T_` with
///
/// - `@Generated` and [GeneratedMetamodel] (`of`, `fingerprint`,
///   `complete`);
/// - a nested leaf class `Data` (marked [GeneratedMetamodelPart]) whose `SHAPE` is the
///   [me.supcheg.javafile.facts.TypeShape] of the type, made by
///   `UnsafeFacts.shape` from plain data — class descriptors and type
///   references, never another metamodel — so the class initialization of
///   metamodels that mention each other has no cycle;
/// - a nested class `Canonical` (marked the same) whose `TEXT` is the canonical form of the
///   type, loaded only when the target-classpath check needs it;
/// - the token: `TOKEN` for a type that is not generic, or used raw because a
///   bound of a type parameter mentions a type the metamodel cannot write —
///   one that is not public, or with `$` in its simple name; for a
///   generic type, `ANY` with a wildcard per argument and a public
///   constructor taking a token per type parameter, which sets `token`. The
///   type parameters of the metamodel are those of the type, with its bounds.
///
/// A token-only metamodel has nothing more. A full one has the facts of the
/// members [MemberPlan] chose (mini-spec §2.3, §2.4, §2.7), as [MemberFacts]
/// makes them: `public static final` fields after the token; in the
/// metamodel of a generic type also `public final` fields its constructor
/// assigns, in terms of the tokens it takes; and a method per generic
/// method, which takes a token per type parameter of that method. The type
/// parameters of a metamodel, and of such a method, have the bounds the
/// type declares, so javac rejects a token of a type argument out of bounds.
///
/// The nested classes come first: the `Data` of the metamodel has claimed
/// its simple name by the time a fact names the `Data` of another.
///
/// Every declaration of a metamodel has a documentation comment
/// ([MetamodelDocs], mini-spec Q14): that of the class tells why the
/// metamodel is full or token-only and which members have no fact, that of
/// a fact links to its member. The comments are Markdown ones: a metamodel
/// is compiled by the JDK that generates it.
///
/// The source is ASCII ([#source]): whatever `-encoding` it is compiled
/// with, a name or a constant is what the type has.
final class MetamodelEmitter {
    private static final String FACTS = "me.supcheg.javafile.facts";
    private static final String CORE_TYPE = "me.supcheg.javafile.type";
    private static final Pattern NESTING = Pattern.compile("\\$");

    private static final ClassDesc CD_GENERATED = ClassDesc.of("javax.annotation.processing.Generated");
    private static final ClassDesc CD_GENERATED_METAMODEL = ClassDesc.of(GeneratedMetamodel.class.getName());
    private static final ClassDesc CD_GENERATED_METAMODEL_PART = ClassDesc.of(GeneratedMetamodelPart.class.getName());
    private static final ClassDesc CD_SUPPRESS_WARNINGS = ClassDesc.of("java.lang.SuppressWarnings");
    private static final ClassDesc CD_NULL_MARKED = ClassDesc.of(NullMarked.class.getName());
    private static final ClassDesc CD_UNSAFE_FACTS = ClassDesc.of(FACTS, "UnsafeFacts");
    private static final ClassDesc CD_TYPE_SHAPE = ClassDesc.of(FACTS, "TypeShape");
    private static final ClassDesc CD_DECLARED_KIND = ClassDesc.of(FACTS, "DeclaredKind");
    private static final ClassDesc CD_METAMODEL_ORIGIN = ClassDesc.of(FACTS + ".ShapeOrigin$Metamodel");
    private static final ClassDesc CD_SUPERTYPES = ClassDesc.of(FACTS, "Supertypes");
    private static final ClassDesc CD_TEMPLATE = ClassDesc.of(FACTS, "MethodTableTemplate");
    private static final ClassDesc CD_HERITAGE = ClassDesc.of(FACTS, "Heritage");
    private static final ClassDesc CD_HERITAGE_TOLD = ClassDesc.of(FACTS + ".Heritage$Told");
    private static final ClassDesc CD_HERITAGE_METHOD = ClassDesc.of(FACTS + ".Heritage$Method");
    private static final ClassDesc CD_HERITAGE_CONSTRUCTOR = ClassDesc.of(FACTS + ".Heritage$Constructor");
    private static final ClassDesc CD_HERITAGE_VISIBILITY = ClassDesc.of(FACTS + ".Heritage$Visibility");
    private static final ClassDesc CD_HERITAGE_DISPATCH = ClassDesc.of(FACTS + ".Heritage$Dispatch");
    private static final ClassDesc CD_HERITAGE_ARITY = ClassDesc.of(FACTS + ".Heritage$Arity");
    private static final ClassDesc CD_HERITAGE_RESULT = ClassDesc.of(FACTS + ".Heritage$Result");
    private static final ClassDesc CD_HERITAGE_RESULT_OF = ClassDesc.of(FACTS + ".Heritage$Result$Of");
    private static final ClassDesc CD_SIGNATURE = ClassDesc.of(FACTS + ".MethodTableTemplate$Signature");
    private static final ClassDesc CD_PARAM = ClassDesc.of(FACTS + ".MethodTableTemplate$Param");
    private static final ClassDesc CD_TOKEN_ARG = ClassDesc.of(FACTS, "TokenArg");
    private static final ClassDesc CD_REF_TOKEN = ClassDesc.of(FACTS, "RefToken");
    private static final ClassDesc CD_CLASS_DESC = ClassDesc.of("java.lang.constant.ClassDesc");
    private static final ClassDesc CD_CONSTANT_DESCS = ClassDesc.of("java.lang.constant.ConstantDescs");
    private static final ClassDesc CD_LIST = ClassDesc.of("java.util.List");
    private static final ClassDesc CD_SET = ClassDesc.of("java.util.Set");
    private static final ClassDesc CD_TYPES = ClassDesc.of(CORE_TYPE, "Types");
    private static final ClassDesc CD_TYPE_PARAM = ClassDesc.of(CORE_TYPE, "TypeParam");
    private static final ClassDesc CD_PARAMETERIZED = ClassDesc.of(CORE_TYPE, "ParameterizedTypeRef");
    private static final ClassDesc CD_PRIMITIVE = ClassDesc.of(CORE_TYPE, "PrimitiveTypeRef");

    /// The longest part of the canonical form written as one string
    /// literal, in bytes of the modified UTF-8 a class file holds a constant
    /// in: far below the 65535 of a constant.
    static final int TEXT_PART = 15000;

    /// How a metamodel is written: its comments in Markdown.
    private static final SourceRenderer.Format FORMAT = SourceRenderer.standardFormat(DocStyle.MARKDOWN);

    /// The `value` of `@Generated`.
    static final String GENERATOR = "me.supcheg.javafile.facts.processor.FactsProcessor";

    private MetamodelEmitter() {}

    /// The token-only metamodel of a type: its shape and token, no facts of
    /// members.
    ///
    /// @param metamodel the metamodel class
    /// @param model the type, read with no members
    /// @param canonical the canonical form of `model`
    /// @param targets the types of the round, which tell a raw type in a bound of a type parameter
    /// @param about why the metamodel is token-only, for its comment
    /// @return the source file
    static JavaFile tokenOnly(
            ClassDesc metamodel, TypeModel model, Canonical canonical, Targets targets, MetamodelDocs.About about) {
        return emit(metamodel, model, canonical, targets, Optional.empty(), about)
                .file();
    }

    /// The full metamodel of a type: its shape and token, and a fact per
    /// member of the plan.
    ///
    /// @param metamodel the metamodel class
    /// @param model the type, read with its declared public members
    /// @param canonical the canonical form of `model`
    /// @param plan the members that get a fact, see [MemberPlan#of]
    /// @param targets the metamodels of the types the signatures mention
    /// @param taken the names the body of the metamodel starts a name with, see [#takenNames]: no
    ///     parameter is named so
    /// @param about why the metamodel is there and where the inherited members are, for its comment
    /// @return the source file
    static JavaFile full(
            ClassDesc metamodel,
            TypeModel model,
            Canonical canonical,
            MemberPlan plan,
            Targets targets,
            Set<String> taken,
            MetamodelDocs.About.Full about) {
        return emit(metamodel, model, canonical, targets, Optional.of(new Full(plan, Optional.of(taken))), about)
                .file();
    }

    /// The names a fact of the full metamodel of a type may not have, beyond
    /// those [MetamodelNames] reserves: every name its body starts a name
    /// with — a class, the first name of a qualified name, a field. A fact
    /// of such a name would hide what the name means, in the metamodel and
    /// in its nested classes.
    ///
    /// The names are read off the source of the metamodel itself, written
    /// with every candidate of a fact, so they are what the metamodel says,
    /// however the core renderer imports and qualifies. Its comments are
    /// written too and are no names ([SourceNames]). What is local to a
    /// method or to the constructor — a parameter, a type parameter of a
    /// method — is not among them: it hides no fact where a fact is read, and
    /// is itself named after the facts are.
    ///
    /// Nor are the type parameters of the metamodel: a type and a field of
    /// one name do not hide each other (JLS 6.4.2), and a type parameter is
    /// named where only a type can be. The fact of a field `T` of a `Box<T>`
    /// is `T`, whatever the type parameters of `Box` are called.
    ///
    /// @param metamodel the metamodel class
    /// @param model the type, read with its declared public members
    /// @param canonical the canonical form of `model`
    /// @param probe every member that may get a fact, see [MemberPlan#probe]
    /// @param targets the metamodels of the types the signatures mention
    /// @param about why the metamodel is there, for its comment
    /// @return the names
    static Set<String> takenNames(
            ClassDesc metamodel,
            TypeModel model,
            Canonical canonical,
            MemberPlan probe,
            Targets targets,
            MetamodelDocs.About.Full about) {
        Emitted emitted =
                emit(metamodel, model, canonical, targets, Optional.of(new Full(probe, Optional.empty())), about);
        return SourceNames.inBodyOf(metamodel.displayName(), emitted.file().render(FORMAT))
                .filter(name -> !name.startsWith(MemberPlan.PROBE))
                .filter(name -> !emitted.typeParameters().contains(name))
                .collect(Collectors.toUnmodifiableSet());
    }

    /// The source of a metamodel, in ASCII: every other character is a
    /// Unicode escape, which Java reads the same anywhere in a source file.
    /// The renderer leaves no `\` before such a character but in a string
    /// literal, where it writes them in pairs, so the escape is one (JLS 3.3):
    /// a comment has a `\` only before the `[` and `]` of an array in a link,
    /// which `,` or `)` follows. javac reads the escapes of a comment as it
    /// does those of the code, so a link to a member of such a name resolves.
    ///
    /// @param file the metamodel
    /// @return the source
    static String source(JavaFile file) {
        String rendered = file.render(FORMAT);
        StringBuilder ascii = new StringBuilder(rendered.length());
        for (int i = 0; i < rendered.length(); i++) {
            char c = rendered.charAt(i);
            if (c < 0x7f) {
                ascii.append(c);
            } else {
                ascii.append(String.format("\\u%04x", (int) c));
            }
        }
        return ascii.toString();
    }

    /// What a full metamodel has beyond a token-only one.
    ///
    /// @param plan the facts
    /// @param taken the names no parameter may have; empty for the source [#takenNames] reads them off,
    ///     where what is local has a name no member has
    private record Full(MemberPlan plan, Optional<Set<String>> taken) {}

    /// A metamodel as written.
    ///
    /// @param file the source file
    /// @param typeParameters the type parameters of the metamodel class, as it names them
    private record Emitted(JavaFile file, List<String> typeParameters) {}

    private static Emitted emit(
            ClassDesc metamodel,
            TypeModel model,
            Canonical canonical,
            Targets targets,
            Optional<Full> full,
            MetamodelDocs.About about) {
        Token token = Token.of(model.kind());
        // without its bounds a type parameter would take a token of any type: no type parameters then
        List<ClassDesc> inBounds = Mentions.ofBounds(model.typeParams()).toList();
        boolean raw = !model.typeParams().isEmpty()
                && (!model.nonPublicBoundTypes().isEmpty() || !inBounds.stream().allMatch(targets::nameable));
        List<TypeParam> typeParams = raw ? List.of() : model.typeParams();
        List<String> declared = typeParams.stream().map(TypeParam::name).toList();

        Set<ClassDesc> referenced = Stream.of(
                        Stream.of(model.desc()),
                        Mentions.ofBounds(typeParams),
                        full.stream().flatMap(f -> {
                            // what the facts refer to does not depend on how type parameters and parameters are named
                            MemberFacts.Facts unnamed = MemberFacts.of(
                                    MemberFacts.Self.of(model.desc(), model.kind(), declared, declared, declared),
                                    targets,
                                    new MemberFacts.Locals.Probe(),
                                    f.plan());
                            return Stream.concat(f.plan().models().flatMap(Mentions::of), unnamed.uses().stream());
                        }),
                        infrastructure(token).stream())
                .flatMap(Function.identity())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        // a type parameter hides a class of its name, and a package: `java.util.List` is then a
        // member `util` of the type parameter `java`
        var typeNames = Stream.of(
                        referenced.stream().flatMap(MetamodelEmitter::simpleNames),
                        Stream.concat(referenced.stream(), Stream.of(metamodel))
                                .flatMap(MetamodelEmitter::outermostPackage),
                        Stream.of(metamodel.displayName(), MetamodelNames.DATA, MetamodelNames.CANONICAL))
                .flatMap(Function.identity())
                .collect(Collectors.toSet());
        List<String> names = MetamodelNames.typeParameters(declared, typeNames);
        Map<String, String> renaming =
                IntStream.range(0, declared.size()).boxed().collect(Collectors.toMap(declared::get, names::get));
        List<String> witnesses = full.map(f -> f.taken()
                        .map(taken -> MetamodelNames.witnesses(
                                names,
                                Stream.concat(taken.stream(), f.plan().names().stream())
                                        .collect(Collectors.toSet())))
                        .orElseGet(() -> IntStream.range(0, names.size())
                                .mapToObj(i -> MemberPlan.PROBE + "c" + i)
                                .toList()))
                .orElseGet(() -> MetamodelNames.witnesses(names));
        Optional<MemberFacts.Facts> facts = full.map(f -> MemberFacts.of(
                MemberFacts.Self.of(model.desc(), model.kind(), declared, names, witnesses),
                targets,
                f.taken()
                        .<MemberFacts.Locals>map(taken -> new MemberFacts.Locals.Named(typeNames, taken))
                        .orElseGet(MemberFacts.Locals.Probe::new),
                f.plan()));
        List<MemberFacts.Spec> specs = facts.map(MemberFacts.Facts::specs).orElse(List.of());
        boolean rawSignatures = full.map(f ->
                                f.plan().models().flatMap(Mentions::signature).anyMatch(type -> raw(type, targets)))
                        .orElse(false)
                || typeParams.stream().flatMap(param -> param.bounds().stream()).anyMatch(b -> raw(b, targets));
        boolean keepWitnesses = facts.map(MemberFacts.Facts::instanceFactories).orElse(false);
        ClassDesc data = metamodel.nested(MetamodelNames.DATA);
        ClassDesc canonicalClass = metamodel.nested(MetamodelNames.CANONICAL);
        Expr shape = Exprs.staticField(data, MetamodelNames.SHAPE);
        MetamodelDocs.Generics generics = raw ? MetamodelDocs.Generics.RAW : MetamodelDocs.Generics.DECLARED;
        JavaFile file = JavaFile.class_(metamodel, cb -> {
            cb.withDoc(MetamodelDocs.type(model.desc(), names, generics, about, full.map(Full::plan)))
                    .withModifiers(Modifier.FINAL)
                    .withAnnotation(CD_GENERATED, ab -> ab.withMember("value", AnnotationValues.literal(GENERATOR)))
                    .withAnnotation(
                            CD_GENERATED_METAMODEL,
                            ab -> ab.withMember("of", AnnotationValues.classValue(model.desc()))
                                    .withMember("fingerprint", AnnotationValues.literal(canonical.fingerprint()))
                                    .withMember("complete", AnnotationValues.literal(full.isPresent()))
                                    .withMember("format", AnnotationValues.literal(MetamodelFormat.VERSION)));
            // a deprecated type is no concern of the metamodel that describes it; a raw type is meant
            List<SingleAnnotationValue> suppressed = Stream.concat(
                            Stream.of("rawtypes").filter(_ -> raw || rawSignatures),
                            Stream.of("deprecation", "removal"))
                    .<SingleAnnotationValue>map(warning -> AnnotationValues.literal(warning))
                    .toList();
            cb.withAnnotation(CD_SUPPRESS_WARNINGS, ab -> ab.withMember("value", AnnotationValues.array(suppressed)));
            cb.withAnnotation(CD_NULL_MARKED);
            for (TypeParam param : typeParams) {
                cb.withTypeParam(new TypeParam(
                        renaming.get(param.name()),
                        param.bounds().stream()
                                .map(b -> (ClassOrInterfaceTypeRef) rename(b, renaming))
                                .toList()));
            }
            ClassDesc inherited = data.nested(MetamodelNames.INHERITED);
            cb.withNestedClass(data, dc -> {
                dc.withDoc(MetamodelDocs.data(model.desc()))
                        .withAnnotation(CD_GENERATED_METAMODEL_PART)
                        .withModifiers(Modifier.STATIC, Modifier.FINAL)
                        .withField(
                                MetamodelNames.SHAPE,
                                Types.parameterized(CD_TYPE_SHAPE, Types.of(token.kindClass())),
                                fb -> fb.withDoc(MetamodelDocs.shape(model.desc()))
                                        .withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                        .withInitializer(
                                                shape(metamodel, model, canonical, token, canonicalClass, inherited)))
                        .withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE));
                switch (model.heritage()) {
                    case Heritage.Told told -> dc.withNestedClass(inherited, hc -> heritage(hc, told, model.desc()));
                    case Heritage.Untold _ -> {}
                }
            });
            cb.withNestedClass(
                    canonicalClass,
                    kc -> kc.withDoc(MetamodelDocs.canonical(model.desc()))
                            .withAnnotation(CD_GENERATED_METAMODEL_PART)
                            .withExactModifiers(Set.of(Modifier.STATIC, Modifier.FINAL))
                            .withField(
                                    MetamodelNames.TEXT,
                                    Types.STRING,
                                    fb -> fb.withDoc(MetamodelDocs.canonicalText(model.desc()))
                                            .withModifiers(Modifier.STATIC, Modifier.FINAL)
                                            .withInitializer(text(canonical)))
                            .withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE)));
            if (typeParams.isEmpty()) {
                cb.withField(
                        MetamodelNames.TOKEN,
                        Types.parameterized(token.tokenClass(), Types.of(model.desc())),
                        fb -> fb.withDoc(MetamodelDocs.token(model.desc(), generics))
                                .withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                .withInitializer(Exprs.staticCall(CD_UNSAFE_FACTS, token.factory(), shape)));
            } else {
                any(cb, model, token, names, shape);
            }
            for (MemberFacts.Spec spec : specs) {
                if (spec instanceof MemberFacts.Spec.Constant(String name, TypeRef type, Expr init, DocComment doc)) {
                    cb.withField(
                            name,
                            type,
                            fb -> fb.withDoc(doc)
                                    .withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                    .withInitializer(init));
                }
            }
            if (typeParams.isEmpty()) {
                cb.withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE));
            } else {
                instance(cb, model, token, names, witnesses, keepWitnesses, specs, shape);
            }
            for (MemberFacts.Spec spec : specs) {
                if (spec instanceof MemberFacts.Spec.Factory factory) {
                    cb.withMethod(factory.name(), factory.type(), mb -> {
                        mb.withDoc(factory.doc()).withModifiers(Modifier.PUBLIC);
                        if (factory.isStatic()) {
                            mb.withModifiers(Modifier.STATIC);
                        }
                        for (TypeParam param : factory.typeParams()) {
                            mb.withTypeParam(param.name(), param.bounds().toArray(ClassOrInterfaceTypeRef[]::new));
                        }
                        for (MemberFacts.Witness witness : factory.witnesses()) {
                            mb.withParam(witness.name(), witness.type());
                        }
                        mb.withBody(body -> body.return_(factory.result()));
                    });
                }
            }
        });
        return new Emitted(file, names);
    }

    /// Whether a type mentions a generic type without type arguments.
    private static boolean raw(TypeRef type, Targets targets) {
        return switch (type) {
            case ClassTypeRef cls -> targets.generic(cls.desc());
            case ParameterizedTypeRef parameterized ->
                parameterized.args().stream().anyMatch(arg -> switch (arg) {
                    case ExactTypeArg exact -> raw(exact.type(), targets);
                    case ExtendsTypeArg bound -> raw(bound.bound(), targets);
                    case SuperTypeArg bound -> raw(bound.bound(), targets);
                    case UnboundedTypeArg _ -> false;
                });
            case ArrayTypeRef array -> raw(array.component(), targets);
            case TypeVarRef _ -> false;
            case PrimitiveTypeRef _ -> false;
        };
    }

    /// `ANY`: the type with a wildcard for every type argument.
    private static void any(ClassBuilder cb, TypeModel model, Token token, List<String> names, Expr shape) {
        List<Expr> anyArgs = Stream.concat(
                        Stream.of(shape), names.stream().map(_ -> Exprs.staticCall(CD_TOKEN_ARG, "unbounded")))
                .toList();
        cb.withField(
                MetamodelNames.ANY,
                Types.parameterized(
                        token.tokenClass(),
                        new ParameterizedTypeRef(
                                model.desc(),
                                names.stream().map(_ -> Types.unbounded()).toList())),
                fb -> fb.withDoc(MetamodelDocs.any(model.desc()))
                        .withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .withInitializer(Exprs.staticCall(CD_UNSAFE_FACTS, token.factory(), anyArgs)));
    }

    /// What an instance of a generic metamodel holds: `token`, the facts in
    /// terms of the type parameters, and the constructor that takes a token
    /// per type parameter and makes them. The tokens are kept in fields of
    /// the names of the parameters if a method of the metamodel reads them.
    private static void instance(
            ClassBuilder cb,
            TypeModel model,
            Token token,
            List<String> names,
            List<String> witnesses,
            boolean keepWitnesses,
            List<MemberFacts.Spec> specs,
            Expr shape) {
        ParameterizedTypeRef self = new ParameterizedTypeRef(
                model.desc(),
                names.stream().map(n -> Types.exact(Types.typeVar(n))).toList());
        cb.withField(
                MetamodelNames.INSTANCE_TOKEN,
                Types.parameterized(token.tokenClass(), self),
                fb -> fb.withDoc(MetamodelDocs.instanceToken(model.desc()))
                        .withModifiers(Modifier.PUBLIC, Modifier.FINAL));
        List<MemberFacts.Spec.Assigned> assigned = specs.stream()
                .filter(MemberFacts.Spec.Assigned.class::isInstance)
                .map(MemberFacts.Spec.Assigned.class::cast)
                .toList();
        for (MemberFacts.Spec.Assigned field : assigned) {
            cb.withField(
                    field.name(),
                    field.type(),
                    fb -> fb.withDoc(field.doc()).withModifiers(Modifier.PUBLIC, Modifier.FINAL));
        }
        if (keepWitnesses) {
            for (int i = 0; i < names.size(); i++) {
                cb.withField(
                        witnesses.get(i),
                        Types.parameterized(CD_REF_TOKEN, Types.typeVar(names.get(i))),
                        fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));
            }
        }
        cb.withConstructor(ctor -> {
            ctor.withDoc(MetamodelDocs.constructor(model.desc(), names, witnesses))
                    .withModifiers(Modifier.PUBLIC);
            for (int i = 0; i < names.size(); i++) {
                ctor.withParam(witnesses.get(i), Types.parameterized(CD_REF_TOKEN, Types.typeVar(names.get(i))));
            }
            List<Expr> args = Stream.concat(
                            Stream.of(shape),
                            witnesses.stream()
                                    .map(witness -> Exprs.staticCall(CD_TOKEN_ARG, "exact", Exprs.field(witness))))
                    .toList();
            ctor.withBody(body -> {
                if (keepWitnesses) {
                    for (String witness : witnesses) {
                        body.assign(Exprs.this_().field(witness), Exprs.field(witness));
                    }
                }
                body.assign(
                        Exprs.this_().field(MetamodelNames.INSTANCE_TOKEN),
                        Exprs.staticCall(CD_UNSAFE_FACTS, token.factory(), args));
                for (MemberFacts.Spec.Assigned field : assigned) {
                    body.assign(Exprs.this_().field(field.name()), field.init());
                }
            });
        });
    }

    /// The shape of the type: with its heritage, which the class `inherited` holds, if the model tells one —
    /// a type that tells one is no enum, so it has no constants.
    private static Expr shape(
            ClassDesc metamodel,
            TypeModel model,
            Canonical canonical,
            Token token,
            ClassDesc canonicalClass,
            ClassDesc inherited) {
        Expr origin = Exprs.new_(
                CD_METAMODEL_ORIGIN,
                classDesc(metamodel),
                Exprs.literal(canonical.fingerprint()),
                Exprs.lambda(List.of(), Exprs.staticField(canonicalClass, MetamodelNames.TEXT)));
        Stream<Expr> type = Stream.of(
                origin,
                Exprs.staticField(CD_DECLARED_KIND, token.kindConstant()),
                classDesc(model.desc()),
                list(model.typeParams().stream()
                        .map(MetamodelEmitter::typeParam)
                        .toList()),
                list(model.superclasses().stream()
                        .map(MetamodelEmitter::classDesc)
                        .toList()),
                list(model.interfaces().stream()
                        .map(MetamodelEmitter::classDesc)
                        .toList()),
                supertypes(model.supertypes()),
                template(model.methods()));
        Stream<Expr> rest =
                switch (model.heritage()) {
                    case Heritage.Told _ ->
                        Stream.of(
                                Exprs.literal(model.sealed()),
                                Exprs.lambda(List.of(), Exprs.staticField(inherited, MetamodelNames.HERITAGE)));
                    case Heritage.Untold _ ->
                        Stream.of(
                                list(model.enumConstants().stream()
                                        .map(Exprs::literal)
                                        .toList()),
                                Exprs.literal(model.sealed()));
                };
        return Exprs.staticCall(
                CD_UNSAFE_FACTS, "shape", Stream.concat(type, rest).toList());
    }

    /// The class that holds the heritage of the type: a method per member, so that no initializer grows
    /// with the number of members of the type beyond what a method may hold.
    private static void heritage(ClassBuilder hc, Heritage.Told heritage, ClassDesc type) {
        List<String> methods = IntStream.range(0, heritage.methods().size())
                .mapToObj(i -> "m" + i)
                .toList();
        List<String> constructors = IntStream.range(0, heritage.constructors().size())
                .mapToObj(i -> "c" + i)
                .toList();
        hc.withDoc(MetamodelDocs.inherited(type))
                .withAnnotation(CD_GENERATED_METAMODEL_PART)
                .withExactModifiers(Set.of(Modifier.STATIC, Modifier.FINAL))
                .withField(
                        MetamodelNames.HERITAGE,
                        Types.of(CD_HERITAGE_TOLD),
                        fb -> fb.withDoc(MetamodelDocs.heritage(type))
                                .withModifiers(Modifier.STATIC, Modifier.FINAL)
                                .withInitializer(Exprs.new_(
                                        CD_HERITAGE_TOLD,
                                        list(methods.stream()
                                                .<Expr>map(Exprs::call)
                                                .toList()),
                                        list(constructors.stream()
                                                .<Expr>map(Exprs::call)
                                                .toList()))))
                .withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE));
        IntStream.range(0, methods.size())
                .forEach(i -> hc.withMethod(
                        methods.get(i),
                        Types.of(CD_HERITAGE_METHOD),
                        mb -> mb.withModifiers(Modifier.PRIVATE, Modifier.STATIC)
                                .withBody(b ->
                                        b.return_(inherited(heritage.methods().get(i))))));
        IntStream.range(0, constructors.size())
                .forEach(i -> hc.withMethod(
                        constructors.get(i),
                        Types.of(CD_HERITAGE_CONSTRUCTOR),
                        mb -> mb.withModifiers(Modifier.PRIVATE, Modifier.STATIC)
                                .withBody(b -> b.return_(
                                        inherited(heritage.constructors().get(i))))));
    }

    private static Expr inherited(Heritage.Method method) {
        return Exprs.new_(
                CD_HERITAGE_METHOD,
                Exprs.staticField(CD_HERITAGE_VISIBILITY, method.visibility().name()),
                Exprs.staticField(CD_HERITAGE_DISPATCH, method.dispatch().name()),
                classDesc(method.declaredBy()),
                signature(method.signature()),
                list(method.typeParams().stream()
                        .map(MetamodelEmitter::typeParam)
                        .toList()),
                list(method.params().stream().map(MetamodelEmitter::typeRef).toList()),
                Exprs.staticField(CD_HERITAGE_ARITY, method.arity().name()),
                switch (method.result()) {
                    case Heritage.Result.Nothing _ -> Exprs.staticField(CD_HERITAGE_RESULT, "NOTHING");
                    case Heritage.Result.Of(TypeRef type) -> Exprs.new_(CD_HERITAGE_RESULT_OF, typeRef(type));
                },
                list(method.throwsTypes().stream()
                        .map(MetamodelEmitter::typeRef)
                        .toList()),
                Exprs.staticCall(
                        CD_SET,
                        "of",
                        method.erasures().stream()
                                .sorted(Comparator.comparing(List::toString))
                                .map(erasure -> list(erasure.stream()
                                        .map(MetamodelEmitter::classDesc)
                                        .toList()))
                                .toList()),
                list(method.overrides().stream()
                        .map(MetamodelEmitter::classDesc)
                        .toList()));
    }

    private static Expr inherited(Heritage.Constructor constructor) {
        return Exprs.new_(
                CD_HERITAGE_CONSTRUCTOR,
                Exprs.staticField(
                        CD_HERITAGE_VISIBILITY, constructor.visibility().name()),
                signature(constructor.signature()),
                list(constructor.typeParams().stream()
                        .map(MetamodelEmitter::typeParam)
                        .toList()),
                list(constructor.params().stream()
                        .map(MetamodelEmitter::typeRef)
                        .toList()),
                Exprs.staticField(CD_HERITAGE_ARITY, constructor.arity().name()),
                list(constructor.throwsTypes().stream()
                        .map(MetamodelEmitter::typeRef)
                        .toList()));
    }

    private static Expr typeParam(TypeParam param) {
        return Exprs.new_(
                CD_TYPE_PARAM,
                Exprs.literal(param.name()),
                list(param.bounds().stream().map(MetamodelEmitter::typeRef).toList()));
    }

    private static Expr supertypes(Supertypes supertypes) {
        if (supertypes.equals(Supertypes.NONE)) {
            return Exprs.staticField(CD_SUPERTYPES, "NONE");
        }
        return Exprs.new_(
                CD_SUPERTYPES,
                list(supertypes.typeParameters().stream()
                        .map(MetamodelEmitter::typeRef)
                        .toList()),
                list(supertypes.supertypes().stream()
                        .map(MetamodelEmitter::typeRef)
                        .toList()));
    }

    private static Expr template(MethodTableTemplate template) {
        if (template.equals(MethodTableTemplate.EMPTY)) {
            return Exprs.staticField(CD_TEMPLATE, "EMPTY");
        }
        return Exprs.new_(
                CD_TEMPLATE,
                signatures(template.abstractMethods()),
                signatures(template.concreteMethods()),
                signatures(template.staticMethods()),
                signatures(template.constructors()));
    }

    private static Expr signatures(Collection<MethodTableTemplate.Signature> signatures) {
        List<Expr> items = signatures.stream()
                .sorted(Comparator.comparing(MethodTableTemplate.Signature::toString))
                .map(MetamodelEmitter::signature)
                .toList();
        return Exprs.staticCall(CD_SET, "of", items);
    }

    private static Expr signature(MethodTableTemplate.Signature signature) {
        return Exprs.staticCall(
                CD_SIGNATURE,
                "of",
                Stream.concat(
                                Stream.of(Exprs.literal(signature.name())),
                                signature.params().stream().map(MetamodelEmitter::param))
                        .toList());
    }

    /// A parameter of the method table template as an expression that makes it.
    static Expr param(MethodTableTemplate.Param param) {
        return switch (param) {
            case MethodTableTemplate.Fixed(ClassDesc erasure) ->
                Exprs.staticCall(CD_PARAM, "fixed", classDesc(erasure));
            case MethodTableTemplate.Var(int index, int dimensions)
            when dimensions == 0 -> Exprs.staticCall(CD_PARAM, "var", Exprs.literal(index));
            case MethodTableTemplate.Var(int index, int dimensions) ->
                Exprs.staticCall(CD_PARAM, "var", Exprs.literal(index), Exprs.literal(dimensions));
        };
    }

    /// A type reference as an expression that makes it.
    private static Expr typeRef(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> Exprs.staticField(CD_PRIMITIVE, primitive.name());
            case ArrayTypeRef array -> Exprs.staticCall(CD_TYPES, "array", typeRef(array.component()));
            case ClassTypeRef cls -> Exprs.staticCall(CD_TYPES, "of", classDesc(cls.desc()));
            case ParameterizedTypeRef parameterized ->
                Exprs.new_(
                        CD_PARAMETERIZED,
                        classDesc(parameterized.raw()),
                        list(parameterized.args().stream()
                                .map(MetamodelEmitter::typeArg)
                                .toList()));
            case TypeVarRef variable -> Exprs.staticCall(CD_TYPES, "typeVar", Exprs.literal(variable.name()));
        };
    }

    private static Expr typeArg(TypeArg arg) {
        return switch (arg) {
            case ExactTypeArg exact -> Exprs.staticCall(CD_TYPES, "exact", typeRef(exact.type()));
            case ExtendsTypeArg bound -> Exprs.staticCall(CD_TYPES, "extendsBound", typeRef(bound.bound()));
            case SuperTypeArg bound -> Exprs.staticCall(CD_TYPES, "superBound", typeRef(bound.bound()));
            case UnboundedTypeArg _ -> Exprs.staticCall(CD_TYPES, "unbounded");
        };
    }

    /// A class descriptor as an expression that makes it: `ClassDesc.of`
    /// with the binary name, a `ConstantDescs` constant for a primitive, and
    /// the descriptor for an array.
    private static Expr classDesc(ClassDesc desc) {
        if (desc.isPrimitive()) {
            return Exprs.staticField(CD_CONSTANT_DESCS, "CD_" + desc.displayName());
        }
        if (desc.isArray()) {
            return Exprs.staticCall(CD_CLASS_DESC, "ofDescriptor", Exprs.literal(desc.descriptorString()));
        }
        return Exprs.staticCall(CD_CLASS_DESC, "of", Exprs.literal(Models.binaryName(desc)));
    }

    private static Expr list(List<? extends Expr> items) {
        return Exprs.staticCall(CD_LIST, "of", items);
    }

    /// The canonical form as string literals: a text block would need
    /// escaping the core renderer does not do.
    ///
    /// A constant string of more than 65535 bytes does not compile, and the
    /// concatenation of literals is one constant: a long form is joined at
    /// run time from parts that are each short — whole lines where they fit,
    /// pieces of a line that is itself too long.
    private static Expr text(Canonical canonical) {
        List<Expr> parts = parts(canonical.text(), TEXT_PART).stream()
                .<Expr>map(Exprs::textBlock)
                .toList();
        if (parts.size() == 1) {
            return parts.getFirst();
        }
        return Exprs.staticCall(
                ConstantDescs.CD_String,
                "join",
                Stream.<Expr>concat(Stream.of(Exprs.literal("")), parts.stream())
                        .toList());
    }

    /// Splits a text into parts of at most `limit` bytes of modified UTF-8
    /// each, which joined are the text: after a line break where a line
    /// fits, within a line that does not, never within a surrogate pair.
    ///
    /// @param text the text
    /// @param limit the most bytes of a part, at least 6: those of a surrogate pair
    /// @return the parts, at least one
    static List<String> parts(String text, int limit) {
        List<String> parts = new ArrayList<>();
        StringBuilder part = new StringBuilder();
        int partBytes = 0;
        int lineStart = 0;
        while (lineStart < text.length()) {
            int lineBreak = text.indexOf('\n', lineStart);
            int lineEnd = lineBreak < 0 ? text.length() : lineBreak + 1;
            int lineBytes = bytes(text, lineStart, lineEnd);
            if (partBytes + lineBytes > limit && partBytes > 0) {
                parts.add(part.toString());
                part.setLength(0);
                partBytes = 0;
            }
            if (lineBytes <= limit) {
                part.append(text, lineStart, lineEnd);
                partBytes += lineBytes;
            } else {
                int i = lineStart;
                while (i < lineEnd) {
                    int next = i + Character.charCount(text.codePointAt(i));
                    int width = bytes(text, i, next);
                    if (partBytes + width > limit) {
                        parts.add(part.toString());
                        part.setLength(0);
                        partBytes = 0;
                    }
                    part.append(text, i, next);
                    partBytes += width;
                    i = next;
                }
            }
            lineStart = lineEnd;
        }
        if (partBytes > 0 || parts.isEmpty()) {
            parts.add(part.toString());
        }
        return parts;
    }

    /// The bytes of a part of a text in the modified UTF-8 of a class file.
    private static int bytes(String text, int from, int to) {
        return IntStream.range(from, to)
                .map(i -> {
                    char c = text.charAt(i);
                    return c != 0 && c < 0x80 ? 1 : c < 0x800 ? 2 : 3;
                })
                .sum();
    }

    private static TypeRef rename(TypeRef type, Map<String, String> renaming) {
        return switch (type) {
            case TypeVarRef variable -> Types.typeVar(renaming.getOrDefault(variable.name(), variable.name()));
            case ParameterizedTypeRef parameterized ->
                new ParameterizedTypeRef(
                        parameterized.raw(),
                        parameterized.args().stream()
                                .map(a -> rename(a, renaming))
                                .toList());
            case ArrayTypeRef array -> Types.array(rename(array.component(), renaming));
            case ClassTypeRef cls -> cls;
            case PrimitiveTypeRef primitive -> primitive;
        };
    }

    private static TypeArg rename(TypeArg arg, Map<String, String> renaming) {
        return switch (arg) {
            case ExactTypeArg exact -> Types.exact(rename(exact.type(), renaming));
            case ExtendsTypeArg bound -> Types.extendsBound(rename(bound.bound(), renaming));
            case SuperTypeArg bound -> Types.superBound(rename(bound.bound(), renaming));
            case UnboundedTypeArg unbounded -> unbounded;
        };
    }

    /// The classes a metamodel refers to by simple name, apart from the
    /// type and the bounds of its type parameters.
    private static List<ClassDesc> infrastructure(Token token) {
        return List.of(
                CD_GENERATED,
                CD_GENERATED_METAMODEL,
                CD_GENERATED_METAMODEL_PART,
                CD_SUPPRESS_WARNINGS,
                CD_NULL_MARKED,
                CD_UNSAFE_FACTS,
                CD_TYPE_SHAPE,
                CD_DECLARED_KIND,
                token.kindClass(),
                token.tokenClass(),
                CD_METAMODEL_ORIGIN,
                CD_SUPERTYPES,
                CD_TEMPLATE,
                CD_HERITAGE,
                CD_HERITAGE_TOLD,
                CD_HERITAGE_METHOD,
                CD_HERITAGE_CONSTRUCTOR,
                CD_HERITAGE_VISIBILITY,
                CD_HERITAGE_DISPATCH,
                CD_HERITAGE_ARITY,
                CD_HERITAGE_RESULT,
                CD_HERITAGE_RESULT_OF,
                CD_SIGNATURE,
                CD_PARAM,
                CD_TOKEN_ARG,
                CD_REF_TOKEN,
                CD_CLASS_DESC,
                CD_CONSTANT_DESCS,
                CD_LIST,
                CD_SET,
                CD_TYPES,
                CD_TYPE_PARAM,
                CD_PARAMETERIZED,
                CD_PRIMITIVE,
                ConstantDescs.CD_String);
    }

    /// The first name of the package of a class, which its qualified name starts with; none for
    /// a class of the unnamed package.
    private static Stream<String> outermostPackage(ClassDesc desc) {
        return Stream.of(desc.packageName())
                .filter(name -> !name.isEmpty())
                .map(name -> name.substring(0, name.indexOf('.') < 0 ? name.length() : name.indexOf('.')));
    }

    /// The simple names a class may be written by: its own and those of the classes it is nested in.
    private static Stream<String> simpleNames(ClassDesc desc) {
        return Pattern.compile("\\$").splitAsStream(desc.displayName());
    }

    /// The token family of a kind of type.
    private record Token(ClassDesc kindClass, String kindConstant, ClassDesc tokenClass, String factory) {

        static Token of(DeclaredKind kind) {
            return switch (kind) {
                case DeclaredKind.FinalClass _ ->
                    new Token(nestedKind("FinalClass"), "FINAL_CLASS", token("FinalClassToken"), "finalClassToken");
                case DeclaredKind.OpenClass _ ->
                    new Token(nestedKind("OpenClass"), "OPEN_CLASS", token("OpenClassToken"), "openClassToken");
                case DeclaredKind.AbstractClass _ ->
                    new Token(
                            nestedKind("AbstractClass"),
                            "ABSTRACT_CLASS",
                            token("AbstractClassToken"),
                            "abstractClassToken");
                case DeclaredKind.Interface _ ->
                    new Token(nestedKind("Interface"), "INTERFACE", token("InterfaceToken"), "interfaceToken");
                case DeclaredKind.EnumClass _ ->
                    new Token(nestedKind("EnumClass"), "ENUM_CLASS", token("EnumToken"), "enumToken");
            };
        }

        private static ClassDesc nestedKind(String name) {
            return CD_DECLARED_KIND.nested(name);
        }

        private static ClassDesc token(String name) {
            return ClassDesc.of(FACTS, name);
        }
    }
}
