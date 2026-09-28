package me.supcheg.javafile.facts.codegen;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.MethodBuilder;
import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.model.Modifier;
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
/// Usage: `FactsCodegen <output dir> write|check`. `check` fails when the
/// sources on disk differ from what would be generated.
public final class FactsCodegen {
    /// The largest generated arity.
    public static final int MAX_ARITY = 12;

    private static final String FACTS = "me.supcheg.javafile.facts";
    private static final String SOURCE = "me.supcheg.javafile.facts.source";

    private static final ClassDesc OVERRIDE = ClassDesc.of("java.lang.Override");
    private static final ClassDesc OPTIONAL = ClassDesc.of("java.util.Optional");
    private static final ClassDesc LIST = ClassDesc.of("java.util.List");

    private static final ClassDesc TYPE_TOKEN = ClassDesc.of(FACTS, "TypeToken");
    private static final ClassDesc DECLARED_TOKEN = ClassDesc.of(FACTS, "DeclaredToken");
    private static final ClassDesc CLASS_TOKEN = ClassDesc.of(FACTS, "ClassToken");
    private static final ClassDesc INTERFACE_TOKEN = ClassDesc.of(FACTS, "InterfaceToken");
    private static final ClassDesc INVOCABLE = ClassDesc.of(FACTS, "Invocable");
    private static final ClassDesc INVOCABLES = ClassDesc.of(FACTS, "Invocables");
    private static final ClassDesc INVOCABLE_KIND = ClassDesc.of(FACTS, "InvocableKind");
    private static final ClassDesc MEMBER_TRAITS = ClassDesc.of(FACTS, "MemberTraits");
    private static final ClassDesc MEMBER_QUERY = ClassDesc.of(SOURCE, "MemberQuery");
    private static final ClassDesc RESOLUTION = ClassDesc.of(SOURCE, "Resolution");

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
                files.add(family == Family.SAM || family == Family.VOID_SAM ? sam(family, n) : ref(family, n));
            }
        }
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
                if (!Files.readString(file.getKey(), StandardCharsets.UTF_8).equals(file.getValue())) {
                    throw new IllegalStateException(file.getKey() + " is stale; run generateArities");
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /// The generated families of member facts.
    enum Family {
        METHOD("MethodRef", true, true, "INSTANCE_METHOD"),
        VOID_METHOD("VoidMethodRef", true, false, "INSTANCE_METHOD"),
        STATIC_METHOD("StaticMethodRef", false, true, "STATIC_METHOD"),
        VOID_STATIC_METHOD("VoidStaticMethodRef", false, false, "STATIC_METHOD"),
        CTOR("CtorRef", true, false, "CONSTRUCTOR"),
        SAM("Sam", true, true, ""),
        VOID_SAM("VoidSam", true, false, "");

        final String prefix;
        final boolean hasOwner;
        final boolean hasResult;
        final String kind;

        Family(String prefix, boolean hasOwner, boolean hasResult, String kind) {
            this.prefix = prefix;
            this.hasOwner = hasOwner;
            this.hasResult = hasResult;
            this.kind = kind;
        }

        String ownerVar() {
            return this == SAM || this == VOID_SAM ? "F" : "O";
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
                raw,
                vars.stream().map(v -> Types.exact(var(v))).toList(),
                new me.supcheg.javafile.annotation.AnnotationUse[0]);
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
            case CTOR -> Types.parameterized(CLASS_TOKEN, var("O"));
            case SAM, VOID_SAM -> Types.parameterized(INTERFACE_TOKEN, var("F"));
        };
    }

    /// Fields and introduce parameters of a ref: owner, name?, result?, params, traits.
    private record Slot(String name, TypeRef type) {}

    private static List<Slot> slots(Family family, int n) {
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("owner", ownerType(family)));
        if (family != Family.CTOR) {
            slots.add(new Slot("name", Types.STRING));
        }
        if (family.hasResult) {
            slots.add(new Slot("result", token("R")));
        }
        for (int i = 1; i <= n; i++) {
            slots.add(new Slot("param" + i, token("A" + i)));
        }
        slots.add(new Slot("traits", Types.of(MEMBER_TRAITS)));
        return slots;
    }

    private static JavaFile ref(Family family, int n) {
        List<Slot> slots = slots(family, n);
        return JavaFile.class_(family.desc(n), cb -> {
            cb.withModifiers(Modifier.FINAL);
            family.typeVars(n).forEach(v -> cb.withTypeParam(v));
            cb.withInterface(INVOCABLE);
            for (Slot slot : slots) {
                cb.withField(slot.name(), slot.type(), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));
            }
            cb.withConstructor(ctor -> {
                ctor.withModifiers(Modifier.PRIVATE);
                slots.forEach(s -> ctor.withParam(s.name(), s.type()));
                ctor.withBody(b -> {
                    for (Slot slot : slots) {
                        Expr value = slot.name().equals("name")
                                ? staticCall(INVOCABLES, "requireMethodName", field("name"))
                                : field(slot.name());
                        b.assign(this_().field(slot.name()), value);
                    }
                });
            });
            cb.withMethod("introduce", family.type(n), mb -> {
                mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC);
                family.typeVars(n).forEach(v -> mb.withTypeParam(v));
                List<Expr> args = new ArrayList<>();
                for (Slot slot : slots) {
                    mb.withParam(slot.name(), slot.type());
                    args.add(field(slot.name()));
                }
                mb.withBody(b -> b.return_(
                        family.typeVars(n).isEmpty()
                                ? Exprs.new_(family.desc(n), args)
                                : newDiamond(family.desc(n), args)));
            });
            override(cb, "kind", Types.of(INVOCABLE_KIND), staticField(INVOCABLE_KIND, family.kind));
            override(cb, "owner", ownerType(family), this_().field("owner"));
            override(
                    cb,
                    "name",
                    Types.STRING,
                    family == Family.CTOR
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
            override(cb, "toString", Types.STRING, staticCall(INVOCABLES, "describe", this_()));
        });
    }

    private static JavaFile sam(Family family, int n) {
        Family method = family == Family.SAM ? Family.METHOD : Family.VOID_METHOD;
        TypeRef methodType = applied(method.desc(n), family.typeVars(n));
        return JavaFile.class_(family.desc(n), cb -> {
            cb.withModifiers(Modifier.FINAL);
            family.typeVars(n).forEach(v -> cb.withTypeParam(v));
            cb.withField("owner", ownerType(family), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));
            cb.withField("method", methodType, fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));
            cb.withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE)
                    .withParam("method", methodType)
                    .withBody(b -> b.assign(
                                    this_().field("owner"),
                                    staticCall(
                                            INVOCABLES,
                                            "requireSam",
                                            field("method").call("owner"),
                                            field("method")))
                            .assign(this_().field("method"), field("method"))));
            cb.withMethod("introduce", family.type(n), mb -> {
                mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC);
                family.typeVars(n).forEach(v -> mb.withTypeParam(v));
                mb.withParam("method", methodType)
                        .withBody(b -> b.return_(newDiamond(family.desc(n), field("method"))));
            });
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

    private static JavaFile lookup() {
        ClassDesc desc = ClassDesc.of(SOURCE, "InvocableLookup");
        return JavaFile.interface_(desc, ib -> {
            ib.withTypeParam("O");
            ib.withAbstractMethod("token", Types.parameterized(DECLARED_TOKEN, var("O")));
            ib.withAbstractMethod("classToken", Types.parameterized(CLASS_TOKEN, var("O")));
            ib.withAbstractMethod(
                    "resolve",
                    Types.of(RESOLUTION),
                    new me.supcheg.javafile.model.Param("query", Types.of(MEMBER_QUERY)));
            for (int n = 0; n <= MAX_ARITY; n++) {
                lookupMethod(ib, Family.METHOD, "method", "method", n);
                lookupMethod(ib, Family.VOID_METHOD, "voidMethod", "voidMethod", n);
                lookupMethod(ib, Family.STATIC_METHOD, "staticMethod", "staticMethod", n);
                lookupMethod(ib, Family.VOID_STATIC_METHOD, "voidStaticMethod", "voidStaticMethod", n);
                lookupMethod(ib, Family.CTOR, "ctor", "constructor", n);
            }
        });
    }

    private static void lookupMethod(InterfaceBuilder ib, Family family, String name, String query, int n) {
        List<String> vars = new ArrayList<>(family.typeVars(n));
        vars.remove("O");
        TypeRef result = family.type(n);
        Consumer<MethodBuilder> spec = mb -> {
            vars.forEach(v -> mb.withTypeParam(v));
            List<Expr> queryArgs = new ArrayList<>();
            List<Expr> introduceArgs = new ArrayList<>();
            introduceArgs.add(call(family == Family.CTOR ? "classToken" : "token"));
            if (family != Family.CTOR) {
                mb.withParam("name", Types.STRING);
                queryArgs.add(field("name"));
                introduceArgs.add(field("name"));
            }
            if (family.hasResult) {
                mb.withParam("result", token("R"));
                queryArgs.add(field("result"));
                introduceArgs.add(field("result"));
            }
            for (int i = 1; i <= n; i++) {
                mb.withParam("param" + i, token("A" + i));
                queryArgs.add(field("param" + i));
                introduceArgs.add(field("param" + i));
            }
            introduceArgs.add(
                    call("resolve", staticCall(MEMBER_QUERY, query, queryArgs)).call("traits"));
            mb.withBody(b -> b.return_(staticCall(family.desc(n), "introduce", introduceArgs)));
        };
        ib.withDefaultMethod(name, result, spec);
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
