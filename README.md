# java-file-api

[![Maven Central](https://img.shields.io/maven-central/v/me.supcheg/java-file-api-core)](https://central.sonatype.com/artifact/me.supcheg/java-file-api-core)
[![Javadoc](https://javadoc.io/badge2/me.supcheg/java-file-api-core/javadoc.svg)](https://javadoc.io/doc/me.supcheg/java-file-api-core)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build](https://github.com/not-pishii/java-file-api/actions/workflows/build.yml/badge.svg)](https://github.com/not-pishii/java-file-api/actions/workflows/build.yml)

A Java source generation library with an API that mirrors the design of the JDK ClassFile API (`java.lang.classfile`).

## Installation

```kotlin
// core
implementation("me.supcheg:java-file-api-core:1.0.0")

// annotation processor integration
implementation("me.supcheg:java-file-api-lang-model:1.0.0")
```

```xml
<!--> core <-->
<dependency>
    <groupId>me.supcheg</groupId>
    <artifactId>java-file-api-core</artifactId>
    <version>1.0.0</version>
</dependency>

<!--> annotation processor integration <-->
<dependency>
    <groupId>me.supcheg</groupId>
    <artifactId>java-file-api-lang-model</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Design

### ClassFile API-style API

Types are addressed via `java.lang.constant.ClassDesc`, and file structure is described using builder consumers — the
same pattern as `ClassFile.build`:

```java
JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Messages"), cb -> cb
        .withModifiers(Modifier.FINAL)
        .withField("bundle", Types.of(BUNDLE), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
        .withConstructor(ctor -> ctor.withModifiers(Modifier.PUBLIC)
                .withParam("bundle", Types.of(BUNDLE))
                .withBody(b -> b.assign(b.field(b.field("this"), "bundle"), b.field("bundle"))))
        .withMethod("greeting", Types.of(STRING), mb -> mb.withParam("name", Types.of(STRING))
                .withBody(b -> b.return_(b.call(b.field("bundle"), "getString", b.literal("greeting"))))));

String source = file.render();
```

### Immutable Model

Builders assemble a model from records organized into sealed hierarchies: type declarations, members, statements,
expressions, and type references. The allowed members for each kind of declaration are enforced by the type system — for
example, an interface only accepts `InterfaceMember`, and a record only accepts `RecordMember`. Method bodies are built
from the same expressions and statements as an AST, without string concatenation. Diamond instantiation
(`new Impl<>(...)`) is modelled as a separate sealed new-target holding only the raw class, so explicit type arguments
cannot be combined with the diamond.

### Transformations

The mechanism mirrors `ClassTransform` from the ClassFile API: a transformation is a `(builder, member)` function
through which every member of an existing declaration is passed. It decides whether to forward the member unchanged,
replace it, drop it, or insert new members alongside it. The original model is not mutated — the result is a new
declaration assembled by a fresh builder.

### Generics

Declarations may declare type parameters (`class Box<T extends Comparable<T>>`), reference type variables
(`Types.typeVar("T")`), and extend or implement parameterized supertypes. Positions that Java restricts to class or
interface types — supertypes, type-parameter bounds, `throws` clauses — accept only `ClassOrInterfaceTypeRef`, so a
primitive or array there does not compile.

### Lambdas

Expressions cover lambdas with inferred (`(name) -> ...`) or explicitly typed (`(String name) -> ...`) parameters and
expression or block bodies. The two parameter forms are separate sealed cases, so a lambda mixing typed and untyped
parameters cannot be constructed.

### Imports

The renderer resolves types through an import manager that returns a simple name when it is not yet claimed by another
package, and a fully qualified name on collision. Types from `java.lang` and the current package are not imported. The
import block is assembled from actually used names after traversing the entire model.

### Annotation Processing Bridge

Two modules connect the library to `javax.annotation.processing`:

- writing a `JavaFile` via `Filer` with originating elements for incremental compilation;
- one-way conversion of `TypeMirror` / `TypeElement` to `ClassDesc` and type references, including generics and
  wildcards.

### Compilation Verification

In addition to unit tests for rendering, generated sources are run through javac (compile-testing): classes with fields
and constructors, sealed hierarchies, enums with constant bodies, control flow, generics with type variables, and
transformation results. Import manager invariants and string escaping are verified by property-based tests (jqwik).

## Structure

| Module                     | Purpose                                                    |
|----------------------------|------------------------------------------------------------|
| `java-file-api-core`       | model, builders, renderer, transformations                 |
| `java-file-api-lang-model` | bridge from `javax.lang.model` to the library's type model |
| `example`                  | end-to-end compilation check of generated code             |
| `aggregation`              | aggregated test and coverage reports                       |
