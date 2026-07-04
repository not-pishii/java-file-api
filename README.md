# java-file-api

A Java source generation library with an API that mirrors the design of the JDK ClassFile API (`java.lang.classfile`).

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
from the same expressions and statements as an AST, without string concatenation.

### Transformations

The mechanism mirrors `ClassTransform` from the ClassFile API: a transformation is a `(builder, member)` function
through which every member of an existing declaration is passed. It decides whether to forward the member unchanged,
replace it, drop it, or insert new members alongside it. The original model is not mutated — the result is a new
declaration assembled by a fresh builder.

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
and constructors, sealed hierarchies, enums with constant bodies, control flow, and transformation results. Import
manager invariants and string escaping are verified by property-based tests (jqwik).

## Structure

| Module                     | Purpose                                                    |
|----------------------------|------------------------------------------------------------|
| `java-file-api-core`       | model, builders, renderer, transformations                 |
| `java-file-api-lang-model` | bridge from `javax.lang.model` to the library's type model |
| `example`                  | end-to-end compilation check of generated code             |
| `aggregation`              | aggregated test and coverage reports                       |