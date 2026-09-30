package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.annotation.SingleAnnotationValue;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.langmodel.mirror.Canonical;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.javafile.model.Modifier;
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

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/// Writes a metamodel as a core [JavaFile] (mini-spec §2.3, §2.6, §2.7).
///
/// Every metamodel is a final class `T_` with
///
/// - `@Generated` and [GeneratedMetamodel] (`of`, `fingerprint`,
///   `complete`);
/// - a nested leaf class `Data` whose `SHAPE` is the
///   [me.supcheg.javafile.facts.TypeShape] of the type, made by
///   `UnsafeFacts.shape` from plain data — class descriptors and type
///   references, never another metamodel — so the class initialization of
///   metamodels that mention each other has no cycle;
/// - a nested class `Canonical` whose `TEXT` is the canonical form of the
///   type, loaded only when the target-classpath check needs it;
/// - the token: `TOKEN` for a type that is not generic, or used raw because a
///   bound of a type parameter mentions a type that is not public; for a
///   generic type, `ANY` with a wildcard per argument and a public
///   constructor taking a token per type parameter, which sets `token`.
///
/// A token-only metamodel has nothing more. A full one, of a type that is
/// not generic, has the facts of the members [MemberPlan] chose after the
/// token (mini-spec §2.3, §2.7), each a `public static final` field; the
/// full metamodels of generic types come with plan step 9.
final class MetamodelEmitter {
    private static final String FACTS = "me.supcheg.javafile.facts";
    private static final String CORE_TYPE = "me.supcheg.javafile.type";

    private static final ClassDesc CD_GENERATED = ClassDesc.of("javax.annotation.processing.Generated");
    private static final ClassDesc CD_GENERATED_METAMODEL = ClassDesc.of(GeneratedMetamodel.class.getName());
    private static final ClassDesc CD_SUPPRESS_WARNINGS = ClassDesc.of("java.lang.SuppressWarnings");
    private static final ClassDesc CD_UNSAFE_FACTS = ClassDesc.of(FACTS, "UnsafeFacts");
    private static final ClassDesc CD_TYPE_SHAPE = ClassDesc.of(FACTS, "TypeShape");
    private static final ClassDesc CD_DECLARED_KIND = ClassDesc.of(FACTS, "DeclaredKind");
    private static final ClassDesc CD_METAMODEL_ORIGIN = ClassDesc.of(FACTS + ".ShapeOrigin$Metamodel");
    private static final ClassDesc CD_SUPERTYPES = ClassDesc.of(FACTS, "Supertypes");
    private static final ClassDesc CD_TEMPLATE = ClassDesc.of(FACTS, "MethodTableTemplate");
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
    /// literal, in characters: far below the limit of a constant, whatever
    /// the encoding.
    private static final int TEXT_PART = 15000;

    /// The `value` of `@Generated`.
    static final String GENERATOR = "me.supcheg.javafile.facts.processor.FactsProcessor";

    private MetamodelEmitter() {}

    /// The token-only metamodel of a type: its shape and token, no facts of
    /// members.
    ///
    /// @param metamodel the metamodel class
    /// @param model the type, read with no members
    /// @param canonical the canonical form of `model`
    /// @return the source file, or why none can be written
    static Emission tokenOnly(ClassDesc metamodel, TypeModel model, Canonical canonical) {
        return emit(metamodel, model, canonical, Optional.empty());
    }

    /// The full metamodel of a type that is not generic: its shape and
    /// token, and a fact per member of the plan.
    ///
    /// @param metamodel the metamodel class
    /// @param model the type, read with its declared public members
    /// @param canonical the canonical form of `model`
    /// @param plan the members that get a fact, see [MemberPlan#of]
    /// @param targets the metamodels of the types the signatures mention
    /// @return the source file, or why none can be written
    static Emission full(ClassDesc metamodel, TypeModel model, Canonical canonical, MemberPlan plan, Targets targets) {
        return emit(metamodel, model, canonical, Optional.of(new Full(plan, targets)));
    }

    /// The simple names a fact of the metamodel of a type may not have,
    /// beyond those [MetamodelNames] reserves: the classes an expression
    /// starts with, the metamodels of the types mentioned, whose nested
    /// `Data` class it names, and the first name of the qualified name of
    /// that class.
    ///
    /// @param model the type, read with its declared public members
    /// @param targets the metamodels of the types the signatures mention
    /// @return the names
    static Set<String> takenNames(TypeModel model, Targets targets) {
        Set<String> taken = new HashSet<>(MemberFacts.QUALIFIERS);
        List<ClassDesc> mentioned = new ArrayList<>();
        MemberPlan.mentions(model.members(), mentioned);
        for (ClassDesc desc : mentioned) {
            targets.of(desc).ifPresent(target -> {
                taken.add(target.metamodel().displayName());
                String packageName = target.metamodel().packageName();
                taken.add(packageName.substring(
                        0, packageName.indexOf('.') < 0 ? packageName.length() : packageName.indexOf('.')));
            });
        }
        return taken;
    }

    private record Full(MemberPlan plan, Targets targets) {}

    private static Emission emit(ClassDesc metamodel, TypeModel model, Canonical canonical, Optional<Full> full) {
        Token token = Token.of(model.kind());
        boolean raw =
                !model.typeParams().isEmpty() && !model.nonPublicBoundTypes().isEmpty();
        List<TypeParam> typeParams = raw ? List.of() : model.typeParams();
        Optional<MemberFacts> facts = full.map(f -> new MemberFacts(model.desc(), model.kind(), f.targets()));
        List<MemberFacts.Spec> specs =
                full.flatMap(f -> facts.map(x -> x.specs(f.plan()))).orElse(List.of());
        Set<ClassDesc> mentioned = new LinkedHashSet<>();
        mentioned.add(model.desc());
        Mentions.of(typeParams, mentioned);
        facts.ifPresent(f -> mentioned.addAll(f.signatureTypes()));
        Set<ClassDesc> referenced = new LinkedHashSet<>(mentioned);
        facts.ifPresent(f -> referenced.addAll(f.uses()));
        referenced.addAll(infrastructure(token));
        List<String> clashes = clashes(metamodel, mentioned);
        if (!clashes.isEmpty()) {
            return new Emission.Clash("the metamodel " + metamodel.packageName() + "." + metamodel.displayName()
                    + " of " + Models.binaryName(model.desc()) + " cannot be written yet: "
                    + String.join("; ", clashes));
        }
        Set<String> taken = referenced.stream().map(MetamodelEmitter::leaf).collect(Collectors.toSet());
        taken.add(metamodel.displayName());
        taken.add(MetamodelNames.DATA);
        taken.add(MetamodelNames.CANONICAL);
        List<String> declared = typeParams.stream().map(TypeParam::name).toList();
        List<String> names = MetamodelNames.typeParameters(declared, taken);
        Map<String, String> renaming = new HashMap<>();
        IntStream.range(0, declared.size()).forEach(i -> renaming.put(declared.get(i), names.get(i)));
        ClassDesc data = metamodel.nested(MetamodelNames.DATA);
        ClassDesc canonicalClass = metamodel.nested(MetamodelNames.CANONICAL);
        Expr shape = Exprs.staticField(data, MetamodelNames.SHAPE);
        return new Emission.Written(JavaFile.class_(metamodel, cb -> {
            cb.withModifiers(Modifier.FINAL)
                    .withAnnotation(CD_GENERATED, ab -> ab.withMember("value", AnnotationValues.literal(GENERATOR)))
                    .withAnnotation(
                            CD_GENERATED_METAMODEL,
                            ab -> ab.withMember("of", AnnotationValues.classValue(model.desc()))
                                    .withMember("fingerprint", AnnotationValues.literal(canonical.fingerprint()))
                                    .withMember("complete", AnnotationValues.literal(full.isPresent())));
            // a deprecated type is no concern of the metamodel that describes it; a raw token is meant
            List<SingleAnnotationValue> suppressed = new ArrayList<>();
            if (raw) {
                suppressed.add(AnnotationValues.literal("rawtypes"));
            }
            suppressed.add(AnnotationValues.literal("deprecation"));
            suppressed.add(AnnotationValues.literal("removal"));
            cb.withAnnotation(CD_SUPPRESS_WARNINGS, ab -> ab.withMember("value", AnnotationValues.array(suppressed)));
            for (TypeParam param : typeParams) {
                cb.withTypeParam(new TypeParam(
                        renaming.get(param.name()),
                        param.bounds().stream()
                                .map(b -> (ClassOrInterfaceTypeRef) rename(b, renaming))
                                .toList()));
            }
            cb.withNestedClass(
                    data,
                    dc -> dc.withModifiers(Modifier.STATIC, Modifier.FINAL)
                            .withField(
                                    MetamodelNames.SHAPE,
                                    Types.parameterized(CD_TYPE_SHAPE, Types.of(token.kindClass())),
                                    fb -> fb.withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                            .withInitializer(shape(metamodel, model, canonical, token, canonicalClass)))
                            .withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE)));
            cb.withNestedClass(
                    canonicalClass,
                    kc -> kc.withExactModifiers(Set.of(Modifier.STATIC, Modifier.FINAL))
                            .withField(
                                    MetamodelNames.TEXT,
                                    Types.STRING,
                                    fb -> fb.withModifiers(Modifier.STATIC, Modifier.FINAL)
                                            .withInitializer(text(canonical)))
                            .withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE)));
            if (typeParams.isEmpty()) {
                cb.withField(
                        MetamodelNames.TOKEN,
                        Types.parameterized(token.tokenClass(), Types.of(model.desc())),
                        fb -> fb.withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                .withInitializer(Exprs.staticCall(CD_UNSAFE_FACTS, token.factory(), shape)));
            } else {
                generic(cb, model, token, names, shape);
            }
            for (MemberFacts.Spec spec : specs) {
                cb.withField(
                        spec.name(),
                        spec.type(),
                        fb -> fb.withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                .withInitializer(spec.init()));
            }
            if (typeParams.isEmpty()) {
                cb.withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE));
            }
        }));
    }

    private static void generic(ClassBuilder cb, TypeModel model, Token token, List<String> names, Expr shape) {
        List<Expr> wildcards = names.stream()
                .<Expr>map(_ -> Exprs.staticCall(CD_TOKEN_ARG, "unbounded"))
                .toList();
        List<Expr> anyArgs = new ArrayList<>(List.of(shape));
        anyArgs.addAll(wildcards);
        cb.withField(
                MetamodelNames.ANY,
                Types.parameterized(
                        token.tokenClass(),
                        new ParameterizedTypeRef(
                                model.desc(),
                                names.stream().map(_ -> Types.unbounded()).toList())),
                fb -> fb.withModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .withInitializer(Exprs.staticCall(CD_UNSAFE_FACTS, token.factory(), anyArgs)));
        ParameterizedTypeRef self = new ParameterizedTypeRef(
                model.desc(),
                names.stream().map(n -> Types.exact(Types.typeVar(n))).toList());
        cb.withField(
                MetamodelNames.INSTANCE_TOKEN,
                Types.parameterized(token.tokenClass(), self),
                fb -> fb.withModifiers(Modifier.PUBLIC, Modifier.FINAL));
        List<String> witnesses = MetamodelNames.witnesses(names);
        cb.withConstructor(ctor -> {
            List<Expr> args = new ArrayList<>(List.of(shape));
            for (int i = 0; i < names.size(); i++) {
                ctor.withParam(witnesses.get(i), Types.parameterized(CD_REF_TOKEN, Types.typeVar(names.get(i))));
                args.add(Exprs.staticCall(CD_TOKEN_ARG, "exact", Exprs.field(witnesses.get(i))));
            }
            ctor.withBody(body -> body.assign(
                    Exprs.this_().field(MetamodelNames.INSTANCE_TOKEN),
                    Exprs.staticCall(CD_UNSAFE_FACTS, token.factory(), args)));
        });
    }

    private static Expr shape(
            ClassDesc metamodel, TypeModel model, Canonical canonical, Token token, ClassDesc canonicalClass) {
        Expr origin = Exprs.new_(
                CD_METAMODEL_ORIGIN,
                classDesc(metamodel),
                Exprs.literal(canonical.fingerprint()),
                Exprs.lambda(List.of(), Exprs.staticField(canonicalClass, MetamodelNames.TEXT)));
        return Exprs.staticCall(
                CD_UNSAFE_FACTS,
                "shape",
                origin,
                Exprs.staticField(CD_DECLARED_KIND, token.kindConstant()),
                classDesc(model.desc()),
                list(model.typeParams().stream()
                        .map(MetamodelEmitter::typeParam)
                        .toList()),
                list(model.superclasses().stream()
                        .map(MetamodelEmitter::classDesc)
                        .toList()),
                supertypes(model.supertypes()),
                template(model.methods()),
                list(model.enumConstants().stream().map(Exprs::literal).toList()),
                Exprs.literal(model.sealed()));
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
                signatures(template.staticMethods()));
    }

    private static Expr signatures(Collection<MethodTableTemplate.Signature> signatures) {
        List<Expr> items = signatures.stream()
                .sorted(Comparator.comparing(MethodTableTemplate.Signature::toString))
                .map(MetamodelEmitter::signature)
                .toList();
        return Exprs.staticCall(CD_SET, "of", items);
    }

    private static Expr signature(MethodTableTemplate.Signature signature) {
        List<Expr> args = new ArrayList<>();
        args.add(Exprs.literal(signature.name()));
        for (MethodTableTemplate.Param param : signature.params()) {
            args.add(
                    switch (param) {
                        case MethodTableTemplate.Fixed(ClassDesc erasure) ->
                            Exprs.staticCall(CD_PARAM, "fixed", classDesc(erasure));
                        case MethodTableTemplate.Var(int index) ->
                            Exprs.staticCall(CD_PARAM, "var", Exprs.literal(index));
                    });
        }
        return Exprs.staticCall(CD_SIGNATURE, "of", args);
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
            case UnboundedTypeArg ignored -> Exprs.staticCall(CD_TYPES, "unbounded");
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

    /// The canonical form, a string literal per line: a text block would
    /// need escaping the core renderer does not do.
    ///
    /// A constant string of more than 65535 bytes does not compile, and the
    /// concatenation of literals is one constant: a long form is joined at
    /// run time from parts that are each short.
    private static Expr text(Canonical canonical) {
        List<Expr> parts = new ArrayList<>();
        StringBuilder part = new StringBuilder();
        for (String line : canonical.text().lines().toList()) {
            if (part.length() + line.length() > TEXT_PART) {
                parts.add(Exprs.literal(part.toString()));
                part.setLength(0);
            }
            part.append(line).append('\n');
        }
        parts.add(Exprs.literal(part.toString()));
        if (parts.size() == 1) {
            return parts.getFirst();
        }
        List<Expr> args = new ArrayList<>(List.of(Exprs.literal("")));
        args.addAll(parts);
        return Exprs.staticCall(ConstantDescs.CD_String, "join", args);
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
                CD_SUPPRESS_WARNINGS,
                CD_UNSAFE_FACTS,
                CD_TYPE_SHAPE,
                CD_DECLARED_KIND,
                token.kindClass(),
                token.tokenClass(),
                CD_METAMODEL_ORIGIN,
                CD_SUPERTYPES,
                CD_TEMPLATE,
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

    /// The simple names the rendered metamodel would get wrong: the core
    /// renderer imports a class by its simple name unless another class
    /// took the name first, but it does not know that a nested class of the
    /// metamodel — `Data`, `Canonical` — or the metamodel itself is visible
    /// by that name in its own body.
    private static List<String> clashes(ClassDesc metamodel, Set<ClassDesc> mentioned) {
        Set<String> reserved = Set.of(MetamodelNames.DATA, MetamodelNames.CANONICAL, metamodel.displayName());
        List<String> clashes = new ArrayList<>();
        for (ClassDesc desc : mentioned) {
            if (reserved.contains(leaf(desc))) {
                clashes.add(Models.binaryName(desc) + " has the simple name of " + leaf(desc) + " in the metamodel");
            }
        }
        return clashes;
    }

    private static String leaf(ClassDesc desc) {
        String name = desc.displayName();
        return name.substring(name.lastIndexOf('$') + 1);
    }

    /// The source of a metamodel, or why it cannot be written.
    sealed interface Emission {

        /// The source is written.
        ///
        /// @param file the source file
        record Written(JavaFile file) implements Emission {}

        /// The core renderer would get a name of the metamodel wrong.
        ///
        /// @param reason which names, for a diagnostic
        record Clash(String reason) implements Emission {}
    }

    /// The token family of a kind of type.
    private record Token(ClassDesc kindClass, String kindConstant, ClassDesc tokenClass, String factory) {

        static Token of(DeclaredKind kind) {
            return switch (kind) {
                case DeclaredKind.FinalClass ignored ->
                    new Token(nestedKind("FinalClass"), "FINAL_CLASS", token("FinalClassToken"), "finalClassToken");
                case DeclaredKind.OpenClass ignored ->
                    new Token(nestedKind("OpenClass"), "OPEN_CLASS", token("OpenClassToken"), "openClassToken");
                case DeclaredKind.AbstractClass ignored ->
                    new Token(
                            nestedKind("AbstractClass"),
                            "ABSTRACT_CLASS",
                            token("AbstractClassToken"),
                            "abstractClassToken");
                case DeclaredKind.Interface ignored ->
                    new Token(nestedKind("Interface"), "INTERFACE", token("InterfaceToken"), "interfaceToken");
                case DeclaredKind.EnumClass ignored ->
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
