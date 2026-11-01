# java-file-api

[![Maven Central](https://img.shields.io/maven-central/v/me.supcheg/java-file-api-core)](https://central.sonatype.com/artifact/me.supcheg/java-file-api-core)
[![Javadoc](https://javadoc.io/badge2/me.supcheg/java-file-api-core/javadoc.svg)](https://javadoc.io/doc/me.supcheg/java-file-api-core)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build](https://github.com/not-pishii/java-file-api/actions/workflows/build.yml/badge.svg)](https://github.com/not-pishii/java-file-api/actions/workflows/build.yml)

A Java source generation library with an API that mirrors the design of the JDK ClassFile API (`java.lang.classfile`).
Types are addressed via `java.lang.constant.ClassDesc`, declarations are described with builder consumers, and the
result is an immutable model rendered to source with imports resolved automatically.

On top of it sits a [typed layer](#typed-layer): the same files declared through facts of the types they use, so that
what is rendered is Java that javac accepts without a warning.

## Quick start

```java
import static me.supcheg.javafile.code.Exprs.*;

JavaFile file = JavaFile.class_(ClassDesc.of("com.example", "Greeter"), cb -> cb
        .withModifiers(Modifier.FINAL)
        .withField("greeting", Types.of(String.class), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
        .withConstructor(ctor -> ctor
                .withParam("greeting", Types.of(String.class))
                .withBody(b -> b.assign(this_().field("greeting"), field("greeting"))))
        .withMethod("greet", Types.of(String.class), mb -> mb
                .withModifiers(Modifier.PUBLIC)
                .withParam("name", Types.of(String.class))
                .withBody(b -> b.return_(
                        add(add(add(field("greeting"), literal(", ")), field("name")), literal("!"))))));

String source = file.render();
```

```java
package com.example;

public final class Greeter {
    private final String greeting;

    public Greeter(String greeting) {
        this.greeting = greeting;
    }

    public String greet(String name) {
        return greeting + ", " + name + "!";
    }
}
```

## Installation

```kotlin
// core
implementation("me.supcheg:java-file-api-core:LATEST_VERSION")

// annotation processor integration
implementation("me.supcheg:java-file-api-lang-model:LATEST_VERSION")
```

```xml
<!-- core -->
<dependency>
    <groupId>me.supcheg</groupId>
    <artifactId>java-file-api-core</artifactId>
    <version>LATEST_VERSION</version>
</dependency>

<!-- annotation processor integration -->
<dependency>
    <groupId>me.supcheg</groupId>
    <artifactId>java-file-api-lang-model</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
```

## Examples

The examples below assume `import static me.supcheg.javafile.code.Exprs.*;` and use constants such as
`STRING = ClassDesc.of("java.lang", "String")` for referenced types. Every output shown is what `render()` produces.

**Core API**

- [Records and sealed interfaces](#records-and-sealed-interfaces)
- [Enums with constant bodies](#enums-with-constant-bodies)
- [Generics](#generics)
- [Method bodies: try-with-resources, lambdas, pattern switch](#method-bodies-try-with-resources-lambdas-pattern-switch)
- [Annotations and annotation types](#annotations-and-annotation-types)
- [Transforming an existing declaration](#transforming-an-existing-declaration)
- [package-info.java and module-info.java](#package-infojava-and-module-infojava)
- [Inside an annotation processor](#inside-an-annotation-processor)

**Typed layer** ([installation, facts and the assumptions of its examples](#typed-layer))

- [Typed quick start](#typed-quick-start)
- [Extending and implementing](#extending-and-implementing)
- [Generic classes](#generic-classes)
- [Typed method bodies: try/catch, lambdas, instanceof, enum switch](#typed-method-bodies-trycatch-lambdas-instanceof-enum-switch)
- [Varargs and arrays](#varargs-and-arrays)
- [Annotations](#annotations)
- [Refusals at generation time](#refusals-at-generation-time)
- [Modifiers and sealed declarations](#modifiers-and-sealed-declarations)
- [What stays in the core API](#what-stays-in-the-core-api)

### Records and sealed interfaces

`withPermits` makes the interface `sealed`. Records get compact and canonical constructors.

```java
ClassDesc shape = ClassDesc.of("com.example.shapes", "Shape");
ClassDesc circle = ClassDesc.of("com.example.shapes", "Circle");

JavaFile shapeFile = JavaFile.interface_(shape, ib -> ib
        .withPermits(circle)
        .withAbstractMethod("area", PrimitiveTypeRef.DOUBLE)
        .withDefaultMethod("isLargerThan", PrimitiveTypeRef.BOOLEAN, mb -> mb
                .withParam("other", Types.of(shape))
                .withBody(b -> b.return_(gt(call("area"), field("other").call("area"))))));

JavaFile circleFile = JavaFile.record(circle, rb -> rb
        .withComponent("radius", PrimitiveTypeRef.DOUBLE)
        .withInterface(shape)
        .withCompactConstructor(b -> b.if_(lt(field("radius"), literal(0)), ib -> ib.then(then -> then
                .throw_(new_(ILLEGAL_ARGUMENT, literal("radius must be non-negative"))))))
        .withMethod("area", PrimitiveTypeRef.DOUBLE, mb -> mb
                .withAnnotation(OVERRIDE)
                .withModifiers(Modifier.PUBLIC)
                .withBody(b -> b.return_(
                        mul(mul(staticField(MATH, "PI"), field("radius")), field("radius"))))));
```

```java
package com.example.shapes;

public sealed interface Shape permits Circle {
    double area();

    default boolean isLargerThan(Shape other) {
        return area() > other.area();
    }
}
```

```java
package com.example.shapes;

public record Circle(double radius) implements Shape {
    public Circle {
        if (radius < 0) {
            throw new IllegalArgumentException("radius must be non-negative");
        }
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
```

### Enums with constant bodies

```java
var intType = PrimitiveTypeRef.INT;

JavaFile file = JavaFile.enum_(ClassDesc.of("com.example", "Operation"), eb -> eb
        .withConstant("PLUS", c -> c.withArgs(literal("+"))
                .withMethod("apply", intType, mb -> mb
                        .withAnnotation(OVERRIDE)
                        .withModifiers(Modifier.PUBLIC)
                        .withParam("a", intType)
                        .withParam("b", intType)
                        .withBody(b -> b.return_(add(field("a"), field("b"))))))
        .withConstant("MINUS", c -> c.withArgs(literal("-"))
                .withMethod("apply", intType, mb -> mb
                        .withAnnotation(OVERRIDE)
                        .withModifiers(Modifier.PUBLIC)
                        .withParam("a", intType)
                        .withParam("b", intType)
                        .withBody(b -> b.return_(sub(field("a"), field("b"))))))
        .withField("symbol", Types.of(STRING), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
        .withConstructor(ctor -> ctor
                .withParam("symbol", Types.of(STRING))
                .withBody(b -> b.assign(this_().field("symbol"), field("symbol"))))
        .withAbstractMethod("apply", intType, new Param("a", intType), new Param("b", intType))
        .withMethod("symbol", Types.of(STRING), mb -> mb
                .withModifiers(Modifier.PUBLIC)
                .withBody(b -> b.return_(field("symbol")))));
```

```java
package com.example;

public enum Operation {
    PLUS("+") {
        @Override
        public int apply(int a, int b) {
            return a + b;
        }
    }, MINUS("-") {
        @Override
        public int apply(int a, int b) {
            return a - b;
        }
    };

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public abstract int apply(int a, int b);

    public String symbol() {
        return symbol;
    }
}
```

### Generics

Type parameters are declared with `withTypeParam`, referenced with `Types.typeVar`, and wildcards are built with
`Types.extendsBound` / `superBound` / `unbounded`. `newDiamond` renders `new ArrayList<>(...)`.

```java
var t = Types.typeVar("T");
var listOfT = Types.parameterized(LIST, t);

JavaFile file = JavaFile.class_(ClassDesc.of("com.example", "Collections2"), cb -> cb
        .withModifiers(Modifier.FINAL)
        .withConstructor(ctor -> ctor.withModifiers(Modifier.PRIVATE))
        .withMethod("max", t, mb -> mb
                .withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .withTypeParam("T", Types.parameterized(COMPARABLE, t))
                .withParam("items", listOfT)
                .withBody(b -> b
                        .localVar("best", t, field("items").call("getFirst"))
                        .forEach(t, "item", field("items"), loop -> loop
                                .if_(gt(field("item").call("compareTo", field("best")), literal(0)),
                                        ib -> ib.then(then -> then.assign(field("best"), field("item")))))
                        .return_(field("best"))))
        .withMethod("copyOf", listOfT, mb -> mb
                .withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .withTypeParam("T")
                .withParam("items", Types.parameterized(LIST, Types.extendsBound(t)))
                .withBody(b -> b.return_(newDiamond(ARRAY_LIST, field("items"))))));
```

```java
package com.example;

import java.util.ArrayList;
import java.util.List;

public final class Collections2 {
    private Collections2() {
    }

    public static <T extends Comparable<T>> T max(List<T> items) {
        T best = items.getFirst();
        for (T item : items) {
            if (item.compareTo(best) > 0) {
                best = item;
            }
        }
        return best;
    }

    public static <T> List<T> copyOf(List<? extends T> items) {
        return new ArrayList<>(items);
    }
}
```

### Method bodies: try-with-resources, lambdas, pattern switch

`CodeBuilder` accumulates statements (`return_`, `if_`, `forEach`, `try_`, `switch_`, ...). Expressions come from
`Exprs` and chain left to right on `Expr` (`.field(...)`, `.call(...)`, `.instanceOf(...)`).

```java
JavaFile file = JavaFile.class_(ClassDesc.of("com.example", "Lines"), cb -> cb
        .withModifiers(Modifier.FINAL)
        .withMethod("readNonBlank", Types.parameterized(LIST, Types.of(STRING)), mb -> mb
                .withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .withParam("path", Types.of(PATH))
                .withBody(b -> b.try_(
                        body -> body.return_(field("reader").call("lines")
                                .call("filter", lambda(List.of("line"), not(field("line").call("isBlank"))))
                                .call("toList")),
                        tb -> tb.resource_("reader", Types.of(BUFFERED_READER),
                                        staticCall(FILES, "newBufferedReader", field("path")))
                                .catch_(List.of(Types.of(IO_EXCEPTION)), "e", c -> c
                                        .throw_(new_(UNCHECKED_IO_EXCEPTION, field("e")))))))
        .withMethod("describe", Types.of(STRING), mb -> mb
                .withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .withParam("value", Types.of(OBJECT))
                .withBody(b -> b.switch_(field("value"), sb -> sb
                        .caseTypeWithGuard(Types.of(INTEGER), "i", gt(field("i"), literal(0)),
                                c -> c.return_(literal("positive int")))
                        .caseType(Types.of(STRING), "s", c -> c.return_(add(literal("string of "),
                                field("s").call("length"))))
                        .default_(c -> c.return_(literal("something else")))))));
```

```java
package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class Lines {
    public static List<String> readNonBlank(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines().filter((line) -> !line.isBlank()).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String describe(Object value) {
        switch (value) {
            case Integer i when i > 0 -> {
                return "positive int";
            }
            case String s -> {
                return "string of " + s.length();
            }
            default -> {
                return "something else";
            }
        }
    }
}
```

### Annotations and annotation types

`JavaFile.annotationType` declares an `@interface`. Use-site annotations attach to any declaration, member,
parameter, or record component; values are built with `AnnotationValues` or the `AnnotationBuilder` shortcuts.

```java
ClassDesc route = ClassDesc.of("com.example.web", "Route");

JavaFile routeFile = JavaFile.annotationType(route, ab -> ab
        .withAnnotation(RETENTION, a -> a.withMember("value",
                AnnotationValues.enumValue(RETENTION_POLICY, "RUNTIME")))
        .withElement("value", Types.of(STRING))
        .withElement("methods", Types.array(Types.of(STRING)), AnnotationValues.array(
                AnnotationValues.literal("GET"))));

JavaFile controller = JavaFile.class_(ClassDesc.of("com.example.web", "UserController"), cb -> cb
        .withAnnotation(route, a -> a
                .withMember("value", AnnotationValues.literal("/users"))
                .withArrayMember("methods", arr -> arr.withLiteral("GET").withLiteral("POST")))
        .withVoidMethod("legacy", mb -> mb
                .withAnnotation(DEPRECATED, a -> a
                        .withMember("since", AnnotationValues.literal("2.0"))
                        .withMember("forRemoval", AnnotationValues.literal(true)))
                .withModifiers(Modifier.PUBLIC)));
```

```java
package com.example.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String value();
    String[] methods() default {
        "GET"
    };
}
```

```java
package com.example.web;

@Route(value = "/users", methods = {
    "GET",
    "POST"
})
public class UserController {
    @Deprecated(since = "2.0", forRemoval = true)
    public void legacy() {
    }
}
```

### Documentation comments

Every declaration builder takes a comment with `withDoc`. A `DocComment` holds what the comment says —
paragraphs and lists of text, code and links to program elements, block tags — not how it is written: the same
comment renders as a traditional comment (`/** ... */`, the default) or as a Markdown one (`///`, Java 23+).
Plain text is escaped for the syntax it is written in, so no text ends the comment or is read as markup.

```java
JavaFile greeter = JavaFile.class_(ClassDesc.of("com.example", "Greeter"), cb -> cb
        .withDoc(d -> d
                .paragraph("Greets people by name.")
                .paragraph(DocText.of(t -> t
                        .text("The greeting is formatted with ")
                        .link(DocRef.method(CD_String, "format", LOCALE, CD_String, CD_Object.arrayType()))
                        .text(", so ")
                        .code("%s")
                        .text(" & co. work in <templates>.")))
                .since("1.0"))
        .withModifiers(Modifier.FINAL)
        .withMethod("greet", Types.STRING, mb -> mb
                .withDoc(d -> d
                        .paragraph("Greets one person.")
                        .param("name", "who is greeted")
                        .returns(DocText.of(t -> t.text("the greeting, never ").code("null")))
                        .see(DocRef.type(LOCALE)))
                .withParam("name", Types.STRING)
                .withBody(b -> b.return_(literal("Hello, ").call("concat", field("name"))))));

String traditional = greeter.render();
String markdown = greeter.render(SourceRenderer.standardFormat(DocStyle.MARKDOWN));
```

<table>
<tr><th><code>greeter.render()</code></th><th><code>DocStyle.MARKDOWN</code></th></tr>
<tr><td>

```java
/**
 * Greets people by name.
 *
 * <p>The greeting is formatted with {@link String#format(java.util.Locale, String, Object[])}, so {@code %s} &amp; co. work in &lt;templates&gt;.
 *
 * @since 1.0
 */
public final class Greeter {
    /**
     * Greets one person.
     *
     * @param name who is greeted
     * @return the greeting, never {@code null}
     * @see java.util.Locale
     */
    public String greet(String name) {
        return "Hello, ".concat(name);
    }
}
```

</td><td>

```java
/// Greets people by name.
///
/// The greeting is formatted with [String#format(java.util.Locale, String, Object\[\])], so `%s` &amp; co. work in &lt;templates>.
///
/// @since 1.0
public final class Greeter {
    /// Greets one person.
    ///
    /// @param name who is greeted
    /// @return the greeting, never `null`
    /// @see java.util.Locale
    public String greet(String name) {
        return "Hello, ".concat(name);
    }
}
```

</td></tr>
</table>

A link names its target as the code of the file does: by the simple name where the file imports or declares the
type, qualified otherwise (`java.util.Locale` above). A comment never adds an import. The parameters of a method
reference are `ClassDesc`s — the erased parameter types, as javadoc matches overloads. Transforms keep the comments
of the declarations they pass on; `PackageInfoFile.withDoc` and `ModuleBuilder.withDoc` document a package and a
module.

### Transforming an existing declaration

A transform is a `(builder, member)` function applied to every member, like `ClassTransform` in the ClassFile API.
It forwards, replaces, or drops each member by calling (or not calling) `builder.accept`. The source file is not
modified; `transformClass` returns a new `JavaFile`.

```java
JavaFile original = JavaFile.class_(ClassDesc.of("com.example", "Config"), cb -> cb
        .withField("host", Types.of(STRING), literal("localhost"))
        .withField("port", PrimitiveTypeRef.INT, literal(8080))
        .withVoidMethod("debugDump", mb -> mb.withBody(b -> b.exprStatement(
                staticField(SYSTEM, "out").call("println", this_())))));

JavaFile cleaned = original.transformClass((builder, member) -> {
    switch (member) {
        case FieldDecl f -> builder.accept(new FieldDecl(
                f.name(), f.type(), f.annotations(), EnumSet.of(Modifier.PRIVATE, Modifier.FINAL),
                f.initializer(), f.doc()));
        case MethodDecl m when m.name().startsWith("debug") -> {} // drop
        default -> builder.accept(member);
    }
});
```

<table>
<tr><th>Before</th><th>After</th></tr>
<tr><td>

```java
package com.example;

public class Config {
    public String host = "localhost";

    public int port = 8080;

    public void debugDump() {
        System.out.println(this);
    }
}
```

</td><td>

```java
package com.example;

public class Config {
    private final String host = "localhost";

    private final int port = 8080;
}
```

</td></tr>
</table>

### package-info.java and module-info.java

```java
PackageInfoFile packageInfo = PackageInfoFile.of("com.example.api",
        new AnnotationBuilder(DEPRECATED).build());

ModuleFile module = ModuleFile.of("com.example.app", mb -> mb
        .withRequires("java.net.http")
        .withRequiresTransitive("java.sql")
        .withExports("com.example.api")
        .withOpensTo("com.example.internal", "com.fasterxml.jackson.databind")
        .withUses(ClassDesc.of("com.example.spi", "Plugin"))
        .withProvides(ClassDesc.of("com.example.spi", "Plugin"),
                ClassDesc.of("com.example.internal", "DefaultPlugin")));
```

```java
@Deprecated
package com.example.api;
```

```java
module com.example.app {
    requires java.net.http;
    requires transitive java.sql;
    exports com.example.api;
    opens com.example.internal to com.fasterxml.jackson.databind;
    uses com.example.spi.Plugin;
    provides com.example.spi.Plugin with com.example.internal.DefaultPlugin;
}
```

### Inside an annotation processor

`java-file-api-lang-model` converts `javax.lang.model` types into the library's model (`Descriptors`) and writes
files through the `Filer` with originating elements for incremental compilation (`JavaFileWriter`). A minimal
processor that generates a fluent `<Type>Builder` for each annotated class:

```java
@SupportedAnnotationTypes("com.example.GenerateBuilder")
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
        for (TypeElement annotation : annotations) {
            for (Element element : round.getElementsAnnotatedWith(annotation)) {
                TypeElement type = (TypeElement) element;
                ClassDesc target = Descriptors.toClassDesc(type);
                ClassDesc builderDesc = ClassDesc.of(target.packageName(), target.displayName() + "Builder");

                JavaFile file = JavaFile.class_(builderDesc, cb -> {
                    for (Element member : type.getEnclosedElements()) {
                        if (member.getKind() != ElementKind.FIELD) {
                            continue;
                        }
                        var name = member.getSimpleName().toString();
                        var fieldType = Descriptors.toTypeRef(((VariableElement) member).asType());
                        cb.withField(name, fieldType)
                                .withMethod(name, Types.of(builderDesc), mb -> mb
                                        .withParam(name, fieldType)
                                        .withBody(b -> b
                                                .assign(this_().field(name), field(name))
                                                .return_(this_())));
                    }
                });

                try {
                    JavaFileWriter.writeTo(file, processingEnv.getFiler(), type);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }
        return true;
    }
}
```

### More examples

The [`example`](example/src/test/java/me/supcheg/javafile/example) module contains compile tests for every supported
construct: anonymous and local classes, labeled loops, `synchronized`, `assert`, arrays, method references, record
patterns, type-use annotations, varargs, operator precedence, and more.

## Typed layer

`java-file-api-typed` is a type-safe DSL on top of the core model. A generator names the types it uses by their *facts*
(`java-file-api-facts`): typed handles on their members, generated from the JDK or from your own classes by the
`@Facts` annotation processor (`java-file-api-facts-processor`). The guarantee: whatever the facts and the DSL accept
renders Java that javac accepts without a warning under `-Xlint:all`. A call with the wrong argument types, a member the
type does not have, a method body that falls off its end do not compile in your generator; what type-checks but would
give javac an error or a warning (a division by a constant zero, a checked exception nothing catches, two overloads
javac cannot tell apart) is refused with an exception, at the call that says it. The only way around is the
`Unsafe*` API (`UnsafeFacts`, `UnsafeStmt`, `UnsafeMember`), which is named so.

The typed layer declares `public final` top-level classes, and the result is an ordinary core `JavaFile`: it is rendered
and written as any other, and [Inside an annotation processor](#inside-an-annotation-processor) applies to it unchanged.
What it does not declare is told in [What stays in the core API](#what-stays-in-the-core-api).

### Installing the typed layer

```kotlin
// typed layer
implementation("me.supcheg:java-file-api-typed:LATEST_VERSION")

// facts: tokens and facts of types (a dependency of the typed layer, named where @Facts is used)
implementation("me.supcheg:java-file-api-facts:LATEST_VERSION")

// facts of the types a generator uses, generated by @Facts
annotationProcessor("me.supcheg:java-file-api-facts-processor:LATEST_VERSION")
```

```xml
<!-- typed layer -->
<dependency>
    <groupId>me.supcheg</groupId>
    <artifactId>java-file-api-typed</artifactId>
    <version>LATEST_VERSION</version>
</dependency>

<!-- facts: tokens and facts of types (a dependency of the typed layer, named where @Facts is used) -->
<dependency>
    <groupId>me.supcheg</groupId>
    <artifactId>java-file-api-facts</artifactId>
    <version>LATEST_VERSION</version>
</dependency>

<!-- facts of the types a generator uses, generated by @Facts -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>me.supcheg</groupId>
                <artifactId>java-file-api-facts-processor</artifactId>
                <version>LATEST_VERSION</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

The generated metamodels are annotated `@Generated` and `@NullMarked`, which no processor claims: compile the generator
with `-Xlint:-processing` if it is compiled with `-Werror`.

### Facts of the types

`@Facts` on a class or on a `package-info.java` asks the processor for a metamodel `<Name>_` of each type it lists. The
metamodel of `p.q.T` is `<P>.facts.p.q.T_`, where `P` is the package of the annotation (`-Ajavafile.facts.package`
sets another base). The supertypes of a listed type and the types of its signatures come with it, and a class of your
own is no different from one of the JDK: `Signal` below is an enum of the example module.

`-Ajavafile.facts.visibility=package` makes the metamodels package-private, and `-Ajavafile.facts.mirror=false`
writes every one of them into the base package itself, `<P>.T_`, where the code of that package reaches them without
exposing them to a user of the library; the typed layer keeps its own JDK metamodels so. `-Ajavafile.facts.index=false`
keeps them out of the index the processors of other modules find reusable metamodels by.

```java
@Facts({
    AbstractList.class,
    ArrayList.class,
    Comparable.class,
    Consumer.class,
    Double.class,
    Function.class,
    Integer.class,
    List.class,
    Math.class,
    NumberFormatException.class,
    Predicate.class,
    Signal.class,
    String.class
})
package me.supcheg.javafile.example.typed;
```

`String_.TOKEN` is the type; its members are fields of `String_`: `String_.length`, `String_.startsWith_String`
(overloads are told by the simple names of the parameter types), and for constructors
`NumberFormatException_.new_String`. A generic type is instantiated with the tokens of its arguments,
`new List_<>(String_.TOKEN)`, and its members are the fields of that instance (`list.getFirst`, `list.token`). Enum
constants are `Signal_.RED`.

### Typed examples

The examples below assume `import static me.supcheg.javafile.typed.Expressions.*;` and the metamodels above imported
(`String_`, `List_`, ...). The first argument of a declaration is the `TargetClasspath` the generated code is compiled
against, which every fact the class uses is checked against. The examples pass `UnsafeFacts.unverifiedClasspath()`,
which checks nothing and is for where there is no compilation to check against; in an annotation processor it is
`TargetClasspaths.of(processingEnv)` from `java-file-api-lang-model`. Parameters are named `v0`, `v1`, ... in the
output. Every output shown is what `render()` produces; the tests compile it under `-Xlint:all` without a warning.

### Typed quick start

The typed twin of the [Quick start](#quick-start). A class is declared in stages, each a type that offers what can be
said at its point. The declarations (`declareField`, `declareMethod`, ...) return the fact of each member; `bodies()`
ends them; `initialize` gives a field its initializer and `define` a method its body, which is checked against the whole
class. The specification is an anonymous class, since a lambda cannot be generic, and `Self` brands everything that
belongs to the class being declared. Fields are `public final` with an initializer, methods are `public`.

```java
JavaFile file = TypedJavaFile.class_(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Greeter"),
        new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var greeting = cb.declareField("greeting", String_.TOKEN);
                var greet = cb.declareMethod("greet", String_.TOKEN, String_.TOKEN);

                var bodies = cb.bodies();
                bodies.initialize(greeting, literal("Hello"));
                bodies.define(
                        greet,
                        (b, self, name) -> b.return_(concat(
                                concat(concat(field(self, greeting), literal(", ")), name), literal("!"))));
                return bodies;
            }
        });
```

```java
package com.example;

public final class Greeter {
    public final String greeting = "Hello";

    public String greet(String v0) {
        return this.greeting + ", " + v0 + "!";
    }
}
```

### Typed static fields

A class, a record and an enum declare static fields too: `declareStaticField` makes a `public static final` one and
`declareMutableStaticField` a `public static` one, which `assignStaticField` assigns like any other mutable field. Each
is given its initializer by `initialize`, exactly once, by the fact its declaration returned. A static field is a static
context to the typed layer: a type parameter of the class is in scope in neither its type nor its initializer (JLS
8.1.2), and the initializer reads only the static fields declared before the one it initializes (JLS 8.3.3). An
interface has `declareConstant` instead, since every field of an interface is a constant; and no static field is a
constant variable to the typed layer, whatever initializes it. A record's static field is final alone.

```java
JavaFile file = TypedJavaFile.class_(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Counter"),
        new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var one = cb.declareStaticField("ONE", PrimitiveToken.INT);
                var total = cb.declareMutableStaticField("total", PrimitiveToken.INT);
                var bump = cb.declareVoidStaticMethod("bump");

                var bodies = cb.bodies();
                bodies.initialize(one, literal(1));
                bodies.initialize(total, literal(0));
                bodies.define(
                        bump,
                        b -> b.exec(assignStaticField(total, addInt(staticField(total), staticField(one))))
                                .end());
                return bodies;
            }
        });
```

```java
package com.example;

public final class Counter {
    public static final int ONE = 1;

    public static int total = 0;

    public static void bump() {
        Counter.total = Counter.total + Counter.ONE;
    }
}
```

### Extending and implementing

The typed class closest to [Records and sealed interfaces](#records-and-sealed-interfaces): it implements an existing
interface (a class is extended the same way, with `extending` and `extends_`). The header names a supertype by the proof
that the class has every abstract method of it: `implement(...)` is given the fact of each method the class overrides,
taken from the header with `overriding`.

```java
JavaFile file = TypedJavaFile.declareClass(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example.shapes", "Circle"),
        new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var comparable = new Comparable_<>(h.self());
                var compareTo = h.overriding(comparable.compareTo_T);
                var header = h.extendsObject();
                header.implements_(comparable.implement(h.self()).compareTo_T(compareTo));

                var cb = header.members();
                var radius = cb.declareField("radius", PrimitiveToken.DOUBLE);
                var area = cb.declareMethod("area", PrimitiveToken.DOUBLE);

                var bodies = cb.bodies();
                bodies.initialize(radius, literal(1.5));
                bodies.define(
                        area,
                        (b, self) -> b.return_(mulDouble(
                                mulDouble(staticField(Math_.PI), field(self, radius)), field(self, radius))));
                bodies.define(
                        compareTo.method(),
                        (b, self, other) -> b.return_(staticCall(
                                Double_.compare_double_double, call(self, area), call(other, area))));
                return bodies;
            }
        });
```

```java
package com.example.shapes;

public final class Circle implements Comparable<Circle> {
    @Override
    public int compareTo(Circle v0) {
        return Double.compare(this.area(), v0.area());
    }

    public final double radius = 1.5;

    public double area() {
        return Math.PI * this.radius * this.radius;
    }
}
```

### Generic classes

The typed twin of [Generics](#generics). A type parameter is a token the specification is given; `Self` is the class
with its own type parameters for its type arguments. A bound that mentions the parameter itself is a witness of the
header, `h.bound(...)`, and `asComparable.apply(item)` is the value of the type parameter seen as the bound.

```java
JavaFile file = TypedJavaFile.declareClass(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Collections2"),
        typeParam("T"),
        new TypedJavaFile.TypedGenericClassSpec1<>() {
            @Override
            public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                var comparable = new Comparable_<>(t);
                var asComparable = h.bound(t, comparable.token);
                var list = new List_<>(t);

                var cb = h.extendsObject().members();
                var max = cb.declareMethod("max", t, list.token);

                var bodies = cb.bodies();
                bodies.define(
                        max,
                        (b, self, items) -> b.letVar(
                                t,
                                call(items, list.getFirst),
                                best -> b.forEach(
                                                t,
                                                items,
                                                (loop, item, _) -> loop.if_(
                                                        gtInt(
                                                                call(
                                                                        asComparable.apply(item),
                                                                        comparable.compareTo_T,
                                                                        best),
                                                                literal(0)),
                                                        then -> then.exec(assign(best, item))))
                                        .return_(best)));
                return bodies;
            }
        });
```

```java
package com.example;

import java.util.List;

public final class Collections2<T extends Object & Comparable<T>> {
    public T max(List<T> v0) {
        T v1 = v0.getFirst();
        for (T v2 : v0) {
            if (v2.compareTo(v1) > 0) {
                v1 = v2;
            }
        }
        return v1;
    }
}
```

### Typed method bodies: try/catch, lambdas, instanceof, enum switch

The typed twin of [Method bodies](#method-bodies-try-with-resources-lambdas-pattern-switch). Statements are methods of
the body `b`. One that ends a block (`return_`, `throw_`) returns the proof that it ended, which a body must give back,
and a `switch_` over an enum has a case for every constant or a `default_`. A lambda is built from the functional
method (`sam`) of its interface, and a checked exception is caught by `catch_` or declared by `throwing`. There is no
`try`-with-resources and no pattern `switch` in the typed layer yet: `ifInstanceOf` tests a type, and a guard is an
`if_` inside.

```java
JavaFile file = TypedJavaFile.class_(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Signals"),
        new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var strings = new Predicate_<>(String_.TOKEN);
                var advice = cb.declareStaticMethod("advice", String_.TOKEN, Signal_.TOKEN);
                var parse = cb.declareStaticMethod("parse", PrimitiveToken.INT, String_.TOKEN);
                var describe = cb.declareStaticMethod("describe", String_.TOKEN, Object_.TOKEN);
                var startingWith = cb.declareStaticMethod("startingWith", strings.token, String_.TOKEN);

                var bodies = cb.bodies();
                bodies.define(
                        advice,
                        (b, signal) -> b.return_(switch_(
                                Signal_.TOKEN,
                                signal,
                                String_.TOKEN,
                                c -> c.case_(Signal_.RED, literal("stop"))
                                        .case_(Signal_.AMBER, literal("wait"))
                                        .case_(Signal_.GREEN, literal("go")))));
                bodies.define(
                        parse,
                        (b, text) -> b.tryTerminated(
                                h -> h.catch_(NumberFormatException_.TOKEN, (c, e) -> c.return_(literal(-1))),
                                t -> t.return_(staticCall(Integer_.parseInt_String, text))));
                bodies.define(
                        describe,
                        (b, value) -> b.ifInstanceOf(
                                        value,
                                        Integer_.TOKEN,
                                        (then, i) -> then.if_(
                                                gtInt(unbox(PrimitiveToken.INT, i), literal(0)),
                                                positive -> positive.return_(literal("positive int"))))
                                .ifInstanceOfElse(
                                        value,
                                        String_.TOKEN,
                                        (then, s) -> then.return_(concat(
                                                literal("string of "),
                                                staticCall(String_.valueOf_int, call(s, String_.length)))),
                                        other -> other.return_(literal("something else"))));
                bodies.define(
                        startingWith,
                        (b, prefix) -> b.return_(
                                lambda(strings.sam, text -> call(text, String_.startsWith_String, prefix))));
                return bodies;
            }
        });
```

```java
package com.example;

import java.util.function.Predicate;
import me.supcheg.javafile.example.typed.Signal;

public final class Signals {
    public static String advice(Signal v0) {
        return switch (v0) {
            case RED -> "stop";
            case AMBER -> "wait";
            case GREEN -> "go";
        };
    }

    public static int parse(String v0) {
        try {
            return Integer.parseInt(v0);
        } catch (NumberFormatException v1) {
            return -1;
        }
    }

    public static String describe(Object v0) {
        if (v0 instanceof Integer v1) {
            if (v1.intValue() > 0) {
                return "positive int";
            }
        }
        if (v0 instanceof String v2) {
            return "string of " + String.valueOf(v2.length());
        } else {
            return "something else";
        }
    }

    public static Predicate<String> startingWith(String v0) {
        return (String v1) -> v1.startsWith(v0);
    }
}
```

### Varargs and arrays

A member whose last parameter is of variable arity is declared with `variableArity()`, and its arguments are given one
by one with `varargs(...)`. They are written one by one where javac resolves the call to that method, `count("a")`;
where another method of the name could be taken for them (`count(String, String)` below), they are written as the
array. `newArrayOf` renders `{...}` where a variable tells the type, and `new String[] {...}` elsewhere. `varargs`,
`newArray`, `newArrayOf` and `throwing` also take their arguments as a `List` — one a stream gives — and render
exactly what the enumerated form does; an empty list is no argument.

```java
JavaFile file = TypedJavaFile.class_(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Arrays2"),
        new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var strings = ArrayToken.of(String_.TOKEN);
                var chars = ArrayToken.of(CharSequence_.TOKEN);
                var countAll = cb.variableArity().declareStaticMethod("count", PrimitiveToken.INT, strings);
                var countTwo =
                        cb.declareStaticMethod("count", PrimitiveToken.INT, String_.TOKEN, String_.TOKEN);
                var csv = cb.declareStaticMethod("csv", String_.TOKEN);
                var one = cb.declareStaticMethod("one", PrimitiveToken.INT);
                var two = cb.declareStaticMethod("two", PrimitiveToken.INT);
                var second = cb.declareStaticMethod("second", String_.TOKEN);

                var bodies = cb.bodies();
                bodies.define(countAll, (b, parts) -> b.return_(length(strings, parts)));
                bodies.define(countTwo, (b, first, last) -> b.return_(literal(0)));
                bodies.define(
                        csv,
                        b -> b.return_(staticCall(
                                String_.join_CharSequence_CharSequenceArray,
                                literal(", "),
                                varargs(chars, literal("a"), literal("b")))));
                bodies.define(one, b -> b.return_(staticCall(countAll, varargs(strings, literal("a")))));
                bodies.define(
                        two,
                        b -> b.return_(staticCall(countAll, varargs(strings, literal("a"), literal("b")))));
                bodies.define(
                        second,
                        b -> b.let(
                                strings,
                                newArrayOf(strings, literal("a"), literal("b")),
                                names -> b.return_(at(strings, names, literal(1)))));
                return bodies;
            }
        });
```

```java
package com.example;

public final class Arrays2 {
    public static int count(String... v0) {
        return v0.length;
    }

    public static int count(String v0, String v1) {
        return 0;
    }

    public static String csv() {
        return String.join(", ", "a", "b");
    }

    public static int one() {
        return Arrays2.count("a");
    }

    public static int two() {
        return Arrays2.count(new String[] {"a", "b"});
    }

    public static String second() {
        String[] v0 = {"a", "b"};
        return v0[1];
    }
}
```

### Annotations

An annotation type `@Facts` asks for has its elements and their defaults as a fact, and a type-state builder: a method
per element, and `build()` only after every element without a default is given a value. An element takes an
`AnnotationValue` of its type, so a value of another type does not compile; `AnnotationValues` makes literals, enum
constants, class literals, nested annotations and arrays. A declaration, a member, a parameter and a type use take the
use: `withAnnotation` on a header, `annotated` and `annotatedParameter` on declarations, the overload of
`declareField`, and `TypeUses.annotated`.

```java
AnnotationRef<Author> author = Author_.annotation()
        .name(AnnotationValues.string("Ada"))
        .year(AnnotationValues.int_(1815))
        .build();
AnnotationRef<Tag> tagged = Tag_.annotation().value(AnnotationValues.string("api")).build();

JavaFile file = TypedJavaFile.declareClass(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Annotated"),
        new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.withAnnotation(author).extendsObject().members();
                var name = cb.declareField("name", TypeUses.annotated(String_.TOKEN, tagged));
                var greet = cb.annotated(tagged)
                        .annotatedParameter(0, tagged)
                        .declareStaticMethod("greet", String_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies();
                bodies.initialize(name, literal("Ada"));
                bodies.define(greet, (b, who) -> b.return_(who));
                return bodies;
            }
        });
```

```java
package com.example;

@Author(name = "Ada", year = 1815)
public final class Annotated {
    public final @Tag("api") String name = "Ada";

    @Tag("api")
    public static String greet(@Tag("api") String v0) {
        return v0;
    }
}
```

A use of a required element is not expressible without it:
`Author_.annotation().build()` has no `build()` before `name` is given. The facts of the annotated type, its members and
its parameters carry their annotations too, so a generator compiled against a library sees them.

### Typed interfaces, records, enums and annotation types

The typed layer declares every top-level kind the core does: `TypedJavaFile.interface_`, `record`, `enum_` and
`annotationType` take the `TargetClasspath`, the name and a specification, and return an ordinary core file. The
declaration is in the same two stages as a class: the builder declares each member and gives its fact, and `bodies()`
ends the declarations and gives the stage that defines them. The methods of these kinds reach arity 4; their
constructors reach arity 12, as the class builder's forms do.

An interface has abstract, default, static and private methods, constants, `extends` of several interfaces and, with
`permits`, `sealed`. It has no constructor, and `extends_` takes an interface token alone:

```java
JavaFile file = TypedJavaFile.interface_(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Shape"),
        new TypedJavaFile.TypedInterfaceSpec() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib) {
                var area = ib.declareAbstractMethod("area", PrimitiveToken.DOUBLE);
                var describe = ib.declareDefaultMethod("describe", String_.TOKEN);
                var twice = ib.declarePrivateMethod("twice", PrimitiveToken.INT, PrimitiveToken.INT);
                var unit = ib.declareStaticMethod("unit", PrimitiveToken.DOUBLE);
                var radius = ib.declareConstant("RADIUS", PrimitiveToken.DOUBLE);
                var bodies = ib.bodies();
                bodies.initialize(radius, literal(1.5));
                bodies.define(twice, (b, self, x) -> b.return_(mulInt(x, literal(2))));
                bodies.define(describe, (b, self) -> b.return_(literal("shape")));
                bodies.define(unit, b -> b.return_(literal(1.0)));
                return bodies;
            }
        });
```

```java
public interface Shape {
    double area();

    default String describe() {
        return "shape";
    }

    private int twice(int v0) {
        return v0 * 2;
    }

    static double unit() {
        return 1.0;
    }

    double RADIUS = 1.5;
}
```

A record has components, a canonical constructor (compact or explicit), additional constructors that begin with
`this(...)`, accessors, methods and interfaces; it is final, so `extends` is not expressible. A component gives the fact
of its accessor and of its private field:

```java
JavaFile file = TypedJavaFile.record(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Point"),
        new TypedJavaFile.TypedRecordSpec() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb) {
                var x = rb.component("x", PrimitiveToken.INT);
                var y = rb.component("y", PrimitiveToken.INT);
                var compact = rb.declareCompactConstructor(PrimitiveToken.INT, PrimitiveToken.INT);
                var empty = rb.declareConstructor();
                var sum = rb.declareMethod("sum", PrimitiveToken.INT);
                var bodies = rb.bodies();
                bodies.define(compact, (b, self, x0, y0) -> b.end());
                bodies.defineDelegating(empty, List.of(literal(0), literal(0)), (b, self) -> b.end());
                bodies.define(sum, (b, self) -> b.return_(addInt(call(self, x.accessor()), call(self, y.accessor()))));
                return bodies;
            }
        });
```

```java
public record Point(int x, int y) {
    public Point {
    }

    public Point() {
        this(0, 0);
    }

    public int sum() {
        return this.x() + this.y();
    }
}
```

An enum has constants without arguments, with arguments, and with a class body that overrides the abstract methods;
interfaces, instance and static methods and fields; and constructors that are `private` and have no `CtorRef`, so
`new_` does not take one:

```java
JavaFile file = TypedJavaFile.enum_(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Op"),
        new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                var apply = eb.declareAbstractMethod("apply", PrimitiveToken.INT, PrimitiveToken.INT);
                eb.constructor(PrimitiveToken.INT, (b, self, value) -> b.end());
                eb.constant("ONE", List.of(literal(1)), cb -> cb.override(apply, (b, self, x) -> b.return_(x)));
                eb.constant("DOUBLE", List.of(literal(2)),
                        cb -> cb.override(apply, (b, self, x) -> b.return_(mulInt(x, literal(2)))));
                var twice = eb.declareMethod("twice", PrimitiveToken.INT, PrimitiveToken.INT);
                var bodies = eb.bodies();
                bodies.define(twice, (b, self, x) -> b.return_(mulInt(x, literal(2))));
                return bodies;
            }
        });
```

```java
public enum Op {
    ONE(1) {
        @Override
        public int apply(int v0) {
            return v0;
        }
    }, DOUBLE(2) {
        @Override
        public int apply(int v0) {
            return v0 * 2;
        }
    };

    public abstract int apply(int v0);

    public int twice(int v0) {
        return v0 * 2;
    }

    Op(int v0) {
    }
}
```

An annotation type has elements of the types JLS 9.6.1 allows, optional defaults, and meta-annotations through the
same annotation API: `@Retention`, `@Target`, `@Repeatable` and user annotations.

```java
JavaFile file = TypedJavaFile.annotationType(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Route"),
        new TypedJavaFile.TypedAnnotationTypeSpec() {
            @Override
            public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                ab.withAnnotation(Retention_.annotation()
                        .value(AnnotationValues.enum_(RetentionPolicy_.RUNTIME)).build());
                ab.withAnnotation(Target_.annotation()
                        .value(AnnotationValues.array(AnnotationValues.enum_(ElementType_.TYPE))).build());
                ab.element("value", String_.TOKEN);
                ab.element("priority", PrimitiveToken.INT, AnnotationValues.int_(0));
                return ab;
            }
        });
```

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.TYPE
})
public @interface Route {
    String value();
    int priority() default 0;
}
```

`TypedJavaFile.packageInfo` declares a `package-info.java` with annotations checked against their targets, and
`TypedJavaFile.module` declares a `module-info.java` with every directive the core supports: `requires` (plain,
`transitive`, `static`), `exports` and `opens` (plain or `to`), `uses` and `provides ... with ...`.

```java
PackageInfoFile info = TypedJavaFile.packageInfo(
        UnsafeFacts.unverifiedClasspath(), "com.example",
        pb -> pb.withAnnotation(PackageTag_.annotation().value(AnnotationValues.string("api")).build()));

ModuleFile module = TypedJavaFile.module(
        UnsafeFacts.unverifiedClasspath(), "com.example.app",
        mb -> mb.requires("java.base")
                .exports("com.example.api")
                .uses(ClassDesc.of("com.example.api.Plugin"))
                .provides(ClassDesc.of("com.example.api.Plugin"),
                        ClassDesc.of("com.example.impl.PluginImpl")));
```

### Generic interfaces and records, and member types

An interface and a record take type parameters as a class does: `interface_` and `record` have the same overloads as
`declareClass`, the token of each parameter is in scope in the header, the components and the members, and `Self` is
the type with its own type parameters for its type arguments. A bound is the token `typeParam` takes; a further bound
is said by `bound` on the interface or record builder, before the components or the members, as a class header says it:

```java
var comparable = new Comparable_<>(t);
ib.bound(t, comparable.token); // interface Box<T extends Number & Comparable<T>>
rb.bound(t, comparable.token); // record Box<T extends Number & Comparable<T>>(T value)
```

The checks of a further bound are those of a class: the bound is no raw type and no wildcard, it is not the leftmost
bound already, and the methods the bounds give the type variable together do not clash. A record's bound is said before
its components, and a type parameter of either kind is in scope in the member types it encloses.

```java
JavaFile file = TypedJavaFile.record(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Box"),
        typeParam("T", Number_.TOKEN),
        new TypedJavaFile.TypedGenericRecordSpec1<>() {
            @Override
            public <Self, T extends Number> TypedRecordBodies<Self> build(
                    TypedRecordBuilder<Self> rb, TypeVarToken<T> t) {
                rb.implements_(new Comparable_<>(t).token);
                var value = rb.component("value", t);
                var compareTo = rb.declareMethod("compareTo", PrimitiveToken.INT, t);
                var bodies = rb.bodies();
                bodies.define(compareTo, (b, self, other) -> b.return_(literal(0)));
                return bodies;
            }
        });
```

```java
public record Box<T extends Number>(T value) implements Comparable<T> {
    @Override
    public int compareTo(T v0) {
        return 0;
    }
}
```

An enum and an annotation type have no type parameters: javac refuses `enum E<T>` and `@interface A<T>` (JLS 8.9,
9.6), and so does the entry point. `enum_` and `annotationType` take none, and the overloads that would take one throw
`IllegalArgumentException` before the specification runs, naming the type and why.

A member type is declared through the declaration that encloses it, not as a file of its own. A class builder has
`declareNestedClass` and `declareInnerClass`, and every kind — an interface, a record, an enum, an annotation type and
an enum constant's body — has `declareNestedInterface`, `declareNestedRecord`, `declareNestedEnum` and
`declareNestedAnnotationType`; the class and interface builders declare a member class, and so does the annotation type
builder. A nested interface and record have the same generic stages 1 to 3 as a nested class does, with the same bounds;
a nested enum and annotation type take no type parameters, which javac rejects. The call runs a declaration of its own,
with its own brand and facts, and returns its token:

```java
var inner = cb.declareNestedClass("Inner", new TypedJavaFile.TypedClassHeaderSpec() {
    @Override
    public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
        var inner = h.extendsObject().members();
        var value = inner.declareMethod("value", String_.TOKEN);
        var bodies = inner.bodies();
        bodies.define(value, (b, self) -> b.return_(literal("inner")));
        return bodies;
    }
});
```

A member class of a class is `static` or inner; an inner one sees the type parameters of the class that encloses it,
and a static one does not (JLS 8.1.2, 8.1.3). A member class, interface, record, enum or annotation type of an
interface, a record, an enum or an annotation type is implicitly `static` and sees none. An annotation type declares
elements and member types, as Java allows (JLS 9.6.1); a `package-info.java` and a `module-info.java` declare no type,
so `nestedType` on their builders refuses a member type, as does a member annotation type of an enum constant's body.

`declareInnerClass` returns an [InnerClass]: the token of the inner class and the two expressions only an inner class
has. `outerThis()` is `Outer.this`, the enclosing instance its body reaches, and `new_(outer, ctor, args...)` is
`outer.new Inner(args...)`, which creates the inner instance from an outer one. The handle ties the expressions to the
declaration: a `static` member class has no such handle, and `new_` refuses the fact of a constructor of another class,
or one that is no constructor at all.

```java
var label = cb.declareField("label", String_.TOKEN);
var inner = cb.declareInnerClass("Inner", new TypedJavaFile.TypedInnerClassSpec<Self>() {
    @Override
    public <Inner> MemberBodies<Inner, ?> build(ClassHeader<Inner> h, InnerClass<Self, Inner> handle) {
        var members = h.extendsObject().members();
        var ctor = members.declareConstructor();
        var read = members.declareMethod("read", String_.TOKEN);
        var bodies = members.bodies();
        bodies.define(ctor, (b, self) -> b.end());
        bodies.define(read, (b, self) -> b.return_(field(handle.outerThis(), label)));
        return bodies;
    }
});
var make = cb.declareMethod("make", inner.token());
// return inner.new_(self, ctor) renders return this.new Inner();
```

The three generic declaration paths agree at three type parameters: `declareClass`, `interface_` and `record` offer
the same three stages, and a member class, interface and record offer the same. The member forms reach twelve because
they are generated as arity families; the declaration stages are hand-written specifications of the three kinds, and
three is the limit the three paths share rather than one of them standing at another number.

### Refusals at generation time

Java that javac accepts with a warning is not generated: the call that would produce it is refused, with a message that
says why. Dividing by a constant zero throws `ArithmeticException` wherever it is evaluated, and javac warns of it
(`-Xlint:divzero`):

```java
Expr<Prim.Int> ratio = divInt(literal(10), subInt(literal(1), literal(1)));
```

```text
java.lang.IllegalArgumentException: divInt by a constant expression of the value zero: the division throws ArithmeticException wherever it is evaluated, and javac warns of it (division by zero, -Xlint:divzero)
```

Two methods of one name that take unrelated functional interfaces make an implicitly typed lambda ambiguous, and javac
warns of the pair (`-Xlint:overloads`), so the second declaration is refused:

```java
TypedJavaFile.class_(
        UnsafeFacts.unverifiedClasspath(),
        ClassDesc.of("com.example", "Handlers"),
        new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var function = new Function_<>(String_.TOKEN, String_.TOKEN);
                var consumer = new Consumer_<>(String_.TOKEN);
                cb.declareVoidMethod("handle", function.token);
                cb.declareVoidMethod("handle", consumer.token);
                return cb.bodies();
            }
        });
```

```text
java.lang.IllegalArgumentException: the method void com.example.Handlers.handle(java.util.function.Consumer<java.lang.String>) of class com.example.Handlers is potentially ambiguous with void com.example.Handlers.handle(java.util.function.Function<java.lang.String, java.lang.String>): at a position each takes a functional interface of its own whose method has parameters, and javac warns of the pair (-Xlint:overloads) — name the method otherwise
```

### Modifiers and sealed declarations

Core renders the modifiers Java has in the order javac prints them: `public protected private abstract static final
transient volatile synchronized native`, then `non-sealed`. `sealed` is not a constant but the `permits` clause of a
declaration: a non-empty one makes it sealed. `default` is no modifier either — a `DefaultMethodDecl` is the method of
an interface that is rendered `default`, and nothing else is. `strictfp` is obsolete: javac warns of it under
`-Xlint:all` (`[strictfp]`, as of release 17 all floating-point expressions are evaluated strictly), so the model does
not emit it.

The typed layer exposes what its forms did not reach. A method is declared `synchronized`, or `native`:

```java
var spin = cb.modified(Modifier.SYNCHRONIZED).declareVoidMethod("spin");
var read = cb.modified(Modifier.NATIVE).declareMethod("read", PrimitiveToken.INT);
```

A `synchronized` method is defined by the bodies as any other. A `native` method is whole when it is declared: it has no
body, its fact is there to call, and the bodies neither define it nor wait for it. A constructor is neither
`synchronized` nor `native`, and a `native` method is never `abstract`; the declaration rejects the rest. A record and
an enum declare `synchronized` methods too, and an enum a `native` one; a record rejects `native`, which javac rejects
of a record (JLS 8.10.3), and an interface rejects both, its methods being abstract, default, static or private:

```java
var spin = rb.modified(Modifier.SYNCHRONIZED).declareVoidMethod("spin");
var read = eb.modified(Modifier.NATIVE).declareMethod("read", PrimitiveToken.INT);
```

An abstract class declares abstract methods, in the same declare-then-define stages as the rest: `declareAbstractMethod`
and `declareVoidAbstractMethod` take the name and the parameter types, and give a fact with no body, which the bodies
neither define nor wait for. An abstract method is legal in an abstract class alone — a class that is not abstract
implements every abstract method it inherits (JLS 8.4.3.1) — and javac's rules of `static`, `private`, `final`,
`native` and `synchronized` hold: the typed layer offers none of those on an abstract method.

A field is `transient` or `volatile` by the exact set of modifiers it is rendered with. A `final` field may be
`transient`; a mutable one may be `transient` or `volatile`. A `volatile` field is not `final` (JLS 8.3.1.4), and the
typed layer keeps both instance fields: a `static` one is declared by `declareStaticField` or
`declareMutableStaticField`.

```java
var cache = cb.declareField("cache", PrimitiveToken.INT,
        Set.of(Modifier.PUBLIC, Modifier.FINAL, Modifier.TRANSIENT));
var counter = cb.declareMutableField("counter", PrimitiveToken.INT,
        Set.of(Modifier.PUBLIC, Modifier.VOLATILE));
```

The header of a class is no longer `public final` alone. It is `abstract`, `non-sealed`, or `sealed` with the
subtypes it permits:

```java
JavaFile file = TypedJavaFile.declareClass(target, ClassDesc.of("com.example", "Node"),
        new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                h.permits(Leaf_.TOKEN);
                return h.extendsObject().members().bodies();
            }
        });
// public sealed class Node permits Leaf { }
```

`permits` takes a `ClassToken` and nothing else: only a token proves the permitted type a class, where a bare name
cannot, and a permitted subtype of a class is never an interface. `permits` makes the class sealed, so it is not final,
and `non-sealed` beside a `permits` clause is rejected. `nonSealed()` is allowed only where Java allows it: the class has to
extend a sealed class or implement a sealed interface, which the end of the header holds it to. An `abstract` class has
no constructor in the typed layer: nothing it declares is instantiated there. A sealed type has its facts, and its
shape tells the subtypes it permits: the typed layer extends or implements it exactly when the declared class is one of
them, and otherwise says what the type permits. A type variable may be bounded by a sealed type, as in Java (JLS 4.4).

### What stays in the core API

The typed layer declares every top-level kind: classes, interfaces, records, enums and annotation types, the
`package-info.java` and `module-info.java` files, and the member types of each of them. What stays in the core API is
what the typed layer does not model: [Transforming an existing declaration](#transforming-an-existing-declaration),
documentation comments, initializer blocks, and the `Unsafe*` escape hatches. A generator may still use the core API
for a declaration the typed layer does not express; the typed layer does not replace it.

The methods of the typed declarations of §6.5 reach arity 4; their constructors and the class builder's forms reach
arity 12. A record or enum that implements an interface names it with `implements_`, which gives a witness, and declares
each abstract method with `declareOverride(witness, fact)`, as a class does.
