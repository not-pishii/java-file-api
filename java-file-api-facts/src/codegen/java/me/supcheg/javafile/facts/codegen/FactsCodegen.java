package me.supcheg.javafile.facts.codegen;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.code.CodeBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.constant.ClassDesc;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.supcheg.javafile.code.Exprs.call;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.newDiamond;
import static me.supcheg.javafile.code.Exprs.staticCall;
import static me.supcheg.javafile.code.Exprs.staticField;
import static me.supcheg.javafile.code.Exprs.this_;

/// Generates the arity families of `java-file-api-facts` with
/// `java-file-api-core` itself.
///
/// Every generated type is marked `@Generated`. The families have
/// package-private constructors: facts are introduced only through
/// `UnsafeFacts`, which inherits the generated factories of
/// `InvocableFactories`, and through the checked lookups of `FactSource`,
/// which inherits the generated `InvocableLookup` (§3.1).
///
/// Usage: `FactsCodegen <output dir> write|check`. `check` fails when the
/// sources on disk differ from what would be generated.
public final class FactsCodegen {
    /// The largest generated arity.
    public static final int MAX_ARITY = 12;

    private static final String FACTS = "me.supcheg.javafile.facts";

    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang.Override");
    private static final ClassDesc GENERATED = ClassDesc.of("javax.annotation.processing.Generated");
    private static final ClassDesc OPTIONAL = ClassDesc.of("java.util.Optional");
    private static final ClassDesc LIST = ClassDesc.of("java.util.List");

    private static final ClassDesc TYPE_TOKEN = ClassDesc.of(FACTS, "TypeToken");
    private static final ClassDesc DECLARED_TOKEN = ClassDesc.of(FACTS, "DeclaredToken");
    private static final ClassDesc CONCRETE_CLASS_TOKEN = ClassDesc.of(FACTS, "ConcreteClassToken");
    private static final ClassDesc ABSTRACT_CLASS_TOKEN = ClassDesc.of(FACTS, "AbstractClassToken");
    private static final ClassDesc INTERFACE_TOKEN = ClassDesc.of(FACTS, "InterfaceToken");
    private static final ClassDesc INVOCABLE = ClassDesc.of(FACTS, "Invocable");
    private static final ClassDesc INVOCABLES = ClassDesc.of(FACTS, "Invocables");
    private static final ClassDesc INVOCABLE_KIND = ClassDesc.of(FACTS, "InvocableKind");
    private static final ClassDesc MEMBER_TRAITS = ClassDesc.of(FACTS, "MemberTraits");
    private static final ClassDesc FACT_PARAM = ClassDesc.of(FACTS, "FactParam");
    private static final ClassDesc TEMPLATE_PARAM = ClassDesc.of(FACTS + ".MethodTableTemplate$Param");
    private static final String DECLARED_PARAMS = "declaredParams";
    private static final ClassDesc MEMBER_QUERY = ClassDesc.of(FACTS + ".source", "MemberQuery");
    private static final ClassDesc RESOLUTION = ClassDesc.of(FACTS + ".source", "Resolution");

    private FactsCodegen() {}

    /// Runs the generator.
    ///
    /// @param args the output directory and the mode, `write` or `check`
    public static void main(String[] args) {
        Path out = Path.of(args[0]);
        Map<Path, String> files = new TreeMap<>();
        for (JavaFile file : generate()) {
            files.put(out.resolve(file.pathSuffix()), file.render());
        }
        if (args[1].equals("write")) {
            write(out, files);
        } else {
            check(out, files);
        }
    }

    /// Generates every file.
    ///
    /// @return the files
    public static List<JavaFile> generate() {
        List<JavaFile> files = new ArrayList<>();
        for (int n = 0; n <= MAX_ARITY; n++) {
            for (Family family : Family.values()) {
                files.add(family.isSam() ? sam(family, n) : ref(family, n));
            }
        }
        files.add(factories());
        files.add(lookup());
        return files;
    }

    private static void write(Path out, Map<Path, String> files) {
        try {
            if (Files.isDirectory(out)) {
                try (Stream<Path> existing = Files.walk(out)) {
                    for (Path path : existing.filter(Files::isRegularFile).toList()) {
                        Files.delete(path);
                    }
                }
            }
            for (Map.Entry<Path, String> file : files.entrySet()) {
                Files.createDirectories(file.getKey().getParent());
                Files.writeString(file.getKey(), file.getValue(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void check(Path out, Map<Path, String> files) {
        try {
            List<Path> onDisk;
            try (Stream<Path> existing = Files.walk(out)) {
                onDisk = existing.filter(Files::isRegularFile).sorted().toList();
            }
            if (!onDisk.equals(List.copyOf(files.keySet()))) {
                throw new IllegalStateException("generated file set differs; run generateArities");
            }
            for (Map.Entry<Path, String> file : files.entrySet()) {
                String onDiskText =
                        Files.readString(file.getKey(), StandardCharsets.UTF_8).replace("\r\n", "\n");
                if (!onDiskText.equals(file.getValue().replace("\r\n", "\n"))) {
                    throw new IllegalStateException(file.getKey() + " is stale; run generateArities");
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// The generated families of member facts.
    enum Family {
        METHOD("MethodRef", "method", true, true, "INSTANCE_METHOD"),
        VOID_METHOD("VoidMethodRef", "voidMethod", true, false, "INSTANCE_METHOD"),
        STATIC_METHOD("StaticMethodRef", "staticMethod", false, true, "STATIC_METHOD"),
        VOID_STATIC_METHOD("VoidStaticMethodRef", "voidStaticMethod", false, false, "STATIC_METHOD"),
        CTOR("CtorRef", "ctor", true, false, "CONSTRUCTOR"),
        ABSTRACT_CTOR("AbstractCtorRef", "abstractCtor", true, false, "CONSTRUCTOR"),
        SAM("Sam", "sam", true, true, ""),
        VOID_SAM("VoidSam", "voidSam", true, false, "");

        final String prefix;
        final String factory;
        final boolean hasOwner;
        final boolean hasResult;
        final String kind;

        Family(String prefix, String factory, boolean hasOwner, boolean hasResult, String kind) {
            this.prefix = prefix;
            this.factory = factory;
            this.hasOwner = hasOwner;
            this.hasResult = hasResult;
            this.kind = kind;
        }

        boolean isSam() {
            return this == SAM || this == VOID_SAM;
        }

        boolean isCtor() {
            return this == CTOR || this == ABSTRACT_CTOR;
        }

        String ownerVar() {
            return isSam() ? "F" : "O";
        }

        List<String> typeVars(int n) {
            List<String> vars = new ArrayList<>();
            if (hasOwner) {
                vars.add(ownerVar());
            }
            if (hasResult) {
                vars.add("R");
            }
            vars.addAll(params(n));
            return vars;
        }

        ClassDesc desc(int n) {
            return ClassDesc.of(FACTS, prefix + n);
        }

        TypeRef type(int n) {
            return applied(desc(n), typeVars(n));
        }
    }

    private static List<String> params(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(i -> "A" + i).toList();
    }

    private static TypeRef var(String name) {
        return Types.typeVar(name);
    }

    private static TypeRef applied(ClassDesc raw, List<String> vars) {
        if (vars.isEmpty()) {
            return Types.of(raw);
        }
        return Types.parameterized(
                raw, vars.stream().map(v -> Types.exact(var(v))).toList(), new AnnotationUse[0]);
    }

    private static Expr instantiate(ClassDesc desc, List<String> typeVars, List<Expr> args) {
        return typeVars.isEmpty() ? Exprs.new_(desc, args) : newDiamond(desc, args);
    }

    private static TypeRef token(String var) {
        return Types.parameterized(TYPE_TOKEN, var(var));
    }

    private static TypeRef wildcardToken() {
        return Types.parameterized(TYPE_TOKEN, Types.unbounded());
    }

    private static TypeRef ownerType(Family family) {
        return switch (family) {
            case METHOD, VOID_METHOD -> Types.parameterized(DECLARED_TOKEN, var("O"));
            case STATIC_METHOD, VOID_STATIC_METHOD -> Types.parameterized(DECLARED_TOKEN, Types.unbounded());
            case CTOR -> Types.parameterized(CONCRETE_CLASS_TOKEN, var("O"));
            case ABSTRACT_CTOR -> Types.parameterized(ABSTRACT_CLASS_TOKEN, var("O"));
            case SAM, VOID_SAM -> Types.parameterized(INTERFACE_TOKEN, var("F"));
        };
    }

    /// The token a lookup of the family introduces its fact for.
    private static String lookupOwner(Family family) {
        return switch (family) {
            case CTOR -> "concreteClassToken";
            case ABSTRACT_CTOR -> "abstractClassToken";
            default -> "token";
        };
    }

    /// Fields and constructor parameters of a ref: owner, name?, result?, params, traits,
    /// and the parameters as the member declares them.
    private record Slot(String name, TypeRef type) {}

    /// The parameters of a factory of `UnsafeFacts`: those of the constructor of the ref, but
    /// each parameter is a `FactParam`, which says how it is declared too.
    private static List<Param> factoryParams(Family family, int n) {
        return slots(family, n).stream()
                .filter(s -> !s.name().equals(DECLARED_PARAMS))
                .map(s -> new Param(
                        s.name(),
                        s.name().startsWith("param")
                                ? Types.parameterized(
                                        FACT_PARAM, var("A" + s.name().substring("param".length())))
                                : s.type()))
                .toList();
    }

    /// `Invocables.declared(owner, param1, ...)`: the parameters as the member declares them.
    private static Expr declaredParams(Expr owner, int n) {
        return staticCall(
                INVOCABLES,
                "declared",
                Stream.concat(Stream.of(owner), IntStream.rangeClosed(1, n).mapToObj(i -> field("param" + i)))
                        .toList());
    }

    private static List<Slot> slots(Family family, int n) {
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("owner", ownerType(family)));
        if (!family.isCtor()) {
            slots.add(new Slot("name", Types.STRING));
        }
        if (family.hasResult) {
            slots.add(new Slot("result", token("R")));
        }
        for (int i = 1; i <= n; i++) {
            slots.add(new Slot("param" + i, token("A" + i)));
        }
        slots.add(new Slot("traits", Types.of(MEMBER_TRAITS)));
        slots.add(new Slot(DECLARED_PARAMS, Types.parameterized(LIST, Types.of(TEMPLATE_PARAM))));
        return slots;
    }

    private static TypeRef samMethodType(Family family, int n) {
        Family method = family == Family.SAM ? Family.METHOD : Family.VOID_METHOD;
        return applied(method.desc(n), family.typeVars(n));
    }

    private static void generated(ClassBuilder cb) {
        cb.withAnnotation(
                GENERATED, ab -> ab.withMember("value", AnnotationValues.literal(FactsCodegen.class.getName())));
    }

    /// Adds a package-private constructor, which core's builder cannot
    /// declare: the facts are introduced only by the generated factories.
    private static void packagePrivateConstructor(ClassBuilder cb, List<Param> params, Consumer<CodeBuilder> body) {
        CodeBuilder code = new CodeBuilder();
        body.accept(code);
        cb.accept(new ConstructorDecl(List.of(), Set.of(), params, code.build(), List.of()));
    }

    private static JavaFile ref(Family family, int n) {
        List<Slot> slots = slots(family, n);
        return JavaFile.class_(family.desc(n), cb -> {
            generated(cb);
            cb.withModifiers(Modifier.FINAL);
            family.typeVars(n).forEach(v -> cb.withTypeParam(v));
            cb.withInterface(INVOCABLE);
            for (Slot slot : slots) {
                cb.withField(slot.name(), slot.type(), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));
            }
            packagePrivateConstructor(
                    cb, slots.stream().map(s -> new Param(s.name(), s.type())).toList(), b -> {
                        for (Slot slot : slots) {
                            Expr value = slot.name().equals("name")
                                    ? staticCall(INVOCABLES, "requireMethodName", field("name"))
                                    : field(slot.name());
                            b.assign(this_().field(slot.name()), value);
                        }
                    });
            override(cb, "kind", Types.of(INVOCABLE_KIND), staticField(INVOCABLE_KIND, family.kind));
            override(cb, "owner", ownerType(family), this_().field("owner"));
            override(
                    cb,
                    "name",
                    Types.STRING,
                    family.isCtor()
                            ? this_().field("owner").call("erasure").call("displayName")
                            : this_().field("name"));
            if (family.hasResult) {
                getter(cb, "result", token("R"));
            }
            for (int i = 1; i <= n; i++) {
                getter(cb, "param" + i, token("A" + i));
            }
            override(
                    cb,
                    "resultType",
                    Types.parameterized(OPTIONAL, wildcardToken()),
                    family.hasResult
                            ? staticCall(OPTIONAL, "of", this_().field("result"))
                            : staticCall(OPTIONAL, "empty"));
            override(
                    cb,
                    "params",
                    Types.parameterized(LIST, wildcardToken()),
                    staticCall(
                            LIST,
                            "of",
                            IntStream.rangeClosed(1, n)
                                    .mapToObj(i -> (Expr) this_().field("param" + i))
                                    .toList()));
            override(cb, "traits", Types.of(MEMBER_TRAITS), this_().field("traits"));
            override(
                    cb,
                    DECLARED_PARAMS,
                    Types.parameterized(LIST, Types.of(TEMPLATE_PARAM)),
                    this_().field(DECLARED_PARAMS));
            override(cb, "toString", Types.STRING, staticCall(INVOCABLES, "describe", this_()));
        });
    }

    private static JavaFile sam(Family family, int n) {
        TypeRef methodType = samMethodType(family, n);
        return JavaFile.class_(family.desc(n), cb -> {
            generated(cb);
            cb.withModifiers(Modifier.FINAL);
            family.typeVars(n).forEach(v -> cb.withTypeParam(v));
            cb.withField("owner", ownerType(family), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));
            cb.withField("method", methodType, fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));
            packagePrivateConstructor(
                    cb,
                    List.of(new Param("method", methodType)),
                    b -> b.assign(
                                    this_().field("owner"),
                                    staticCall(
                                            INVOCABLES,
                                            "requireSam",
                                            field("method").call("owner"),
                                            field("method")))
                            .assign(this_().field("method"), field("method")));
            getter(cb, "owner", ownerType(family));
            getter(cb, "method", methodType);
            if (family.hasResult) {
                cb.withMethod(
                        "result",
                        token("R"),
                        mb -> mb.withModifiers(Modifier.PUBLIC)
                                .withBody(b -> b.return_(this_().field("method").call("result"))));
            }
            for (int i = 1; i <= n; i++) {
                int index = i;
                cb.withMethod(
                        "param" + i,
                        token("A" + i),
                        mb -> mb.withModifiers(Modifier.PUBLIC)
                                .withBody(b -> b.return_(this_().field("method").call("param" + index))));
            }
            cb.withMethod(
                    "toString",
                    Types.STRING,
                    mb -> mb.withAnnotation(OVERRIDE)
                            .withModifiers(Modifier.PUBLIC)
                            .withBody(b -> b.return_(this_().field("method").call("toString"))));
        });
    }

    /// The package-private superclass of `UnsafeFacts` holding its generated
    /// member factories, one per family and arity.
    private static JavaFile factories() {
        return JavaFile.class_(ClassDesc.of(FACTS, "InvocableFactories"), cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of(Modifier.ABSTRACT));
            for (int n = 0; n <= MAX_ARITY; n++) {
                for (Family family : Family.values()) {
                    factory(cb, family, n);
                }
            }
        });
    }

    private static void factory(ClassBuilder cb, Family family, int n) {
        List<Param> params =
                family.isSam() ? List.of(new Param("method", samMethodType(family, n))) : factoryParams(family, n);
        List<Expr> args = Stream.concat(
                        params.stream()
                                .map(p -> p.name().startsWith("param")
                                        ? staticCall(INVOCABLES, "token", field(p.name()))
                                        : field(p.name())),
                        family.isSam() ? Stream.empty() : Stream.of(declaredParams(field("owner"), n)))
                .toList();
        cb.withMethod(family.factory, family.type(n), mb -> {
            mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC);
            family.typeVars(n).forEach(v -> mb.withTypeParam(v));
            params.forEach(mb::withParam);
            mb.withBody(b -> b.return_(instantiate(family.desc(n), family.typeVars(n), args)));
        });
    }

    /// The package-private superclass of `FactSource` holding its generated
    /// checked lookups, one per member family and arity.
    private static JavaFile lookup() {
        return JavaFile.class_(ClassDesc.of(FACTS, "InvocableLookup"), cb -> {
            generated(cb);
            cb.withExactModifiers(Set.of(Modifier.ABSTRACT));
            cb.withTypeParam("O");
            cb.withAbstractMethod("token", Types.parameterized(DECLARED_TOKEN, var("O")));
            packagePrivateAbstract(cb, "resolve", Types.of(RESOLUTION), new Param("query", Types.of(MEMBER_QUERY)));
            packagePrivateAbstract(cb, "concreteClassToken", Types.parameterized(CONCRETE_CLASS_TOKEN, var("O")));
            packagePrivateAbstract(cb, "abstractClassToken", Types.parameterized(ABSTRACT_CLASS_TOKEN, var("O")));
            for (int n = 0; n <= MAX_ARITY; n++) {
                lookupMethod(cb, Family.METHOD, "method", n);
                lookupMethod(cb, Family.VOID_METHOD, "voidMethod", n);
                lookupMethod(cb, Family.STATIC_METHOD, "staticMethod", n);
                lookupMethod(cb, Family.VOID_STATIC_METHOD, "voidStaticMethod", n);
                lookupMethod(cb, Family.CTOR, "constructor", n);
                lookupMethod(cb, Family.ABSTRACT_CTOR, "constructor", n);
            }
        });
    }

    private static void packagePrivateAbstract(ClassBuilder cb, String name, TypeRef type, Param... params) {
        cb.withAbstractMethod(name, type, amb -> {
            amb.withExactModifiers(Set.of(Modifier.ABSTRACT));
            for (Param param : params) {
                amb.withParam(param);
            }
        });
    }

    private static void lookupMethod(ClassBuilder cb, Family family, String query, int n) {
        List<String> vars = new ArrayList<>(family.typeVars(n));
        vars.remove("O");
        cb.withMethod(family.factory, family.type(n), mb -> {
            vars.forEach(v -> mb.withTypeParam(v));
            List<Expr> queryArgs = new ArrayList<>();
            List<Expr> args = new ArrayList<>();
            args.add(call(lookupOwner(family)));
            if (!family.isCtor()) {
                mb.withParam("name", Types.STRING);
                queryArgs.add(field("name"));
                args.add(field("name"));
            }
            if (family.hasResult) {
                mb.withParam("result", token("R"));
                queryArgs.add(field("result"));
                args.add(field("result"));
            }
            for (int i = 1; i <= n; i++) {
                mb.withParam("param" + i, token("A" + i));
                queryArgs.add(field("param" + i));
                args.add(field("param" + i));
            }
            args.add(call("resolve", staticCall(MEMBER_QUERY, query, queryArgs)).call("traits"));
            args.add(declaredParams(call(lookupOwner(family)), n));
            mb.withBody(b -> b.return_(instantiate(family.desc(n), family.typeVars(n), args)));
        });
    }

    private static void getter(ClassBuilder cb, String name, TypeRef type) {
        cb.withMethod(
                name, type, mb -> mb.withModifiers(Modifier.PUBLIC).withBody(b -> b.return_(this_().field(name))));
    }

    private static void override(ClassBuilder cb, String name, TypeRef type, Expr value) {
        cb.withMethod(
                name,
                type,
                mb -> mb.withAnnotation(OVERRIDE).withModifiers(Modifier.PUBLIC).withBody(b -> b.return_(value)));
    }
}
