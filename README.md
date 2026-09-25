# java-file-api

[![Maven Central](https://img.shields.io/maven-central/v/me.supcheg/java-file-api-core)](https://central.sonatype.com/artifact/me.supcheg/java-file-api-core)
[![Javadoc](https://javadoc.io/badge2/me.supcheg/java-file-api-core/javadoc.svg)](https://javadoc.io/doc/me.supcheg/java-file-api-core)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build](https://github.com/not-pishii/java-file-api/actions/workflows/build.yml/badge.svg)](https://github.com/not-pishii/java-file-api/actions/workflows/build.yml)

A Java source generation library with an API that mirrors the design of the JDK ClassFile API (`java.lang.classfile`).
Types are addressed via `java.lang.constant.ClassDesc`, declarations are described with builder consumers, and the
result is an immutable model rendered to source with imports resolved automatically.

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

- [Records and sealed interfaces](#records-and-sealed-interfaces)
- [Enums with constant bodies](#enums-with-constant-bodies)
- [Generics](#generics)
- [Method bodies: try-with-resources, lambdas, pattern switch](#method-bodies-try-with-resources-lambdas-pattern-switch)
- [Annotations and annotation types](#annotations-and-annotation-types)
- [Transforming an existing declaration](#transforming-an-existing-declaration)
- [package-info.java and module-info.java](#package-infojava-and-module-infojava)
- [Inside an annotation processor](#inside-an-annotation-processor)

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
                f.initializer()));
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
