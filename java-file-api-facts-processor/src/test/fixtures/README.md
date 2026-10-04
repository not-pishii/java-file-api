# Fixtures of the `@Facts` processor

A fixture is a library, requests to the processor for facts of it, the output expected of each
request, and code that uses that output. `FixturesTest` runs every fixture of this directory; the
harness is `src/test/java/…/processor/harness` (`FixtureRun` makes the tests).

## Layout

```
<fixture>/
  lib/p/Greeter.java            the library: plain sources, packages as directories;
                                compiled by the test, once for the fixture, without the processor
  request/gen/G.java            the class with @Facts (any sources the processor runs on)
  request/options.txt           optional: javac options, one per line, # comments
  request/classpath.txt         optional: sibling cases whose output is on the classpath
  expected/…                    the snapshot of the output, written by -Pfixtures.update
  expected-jdk/…                the metamodels of JDK types, once for the fixture (see below)
  use/*.java                    code against the metamodels: compiled and run
  use-fails/*.java              code against the metamodels that must not compile
```

A fixture with several requests keeps one `lib/` and has a directory per request instead:

```
<fixture>/
  lib/…
  cases/<case>/request/…  expected/…  use/…  use-fails/…  [lib/…]
```

It is one or the other: `request/` next to `cases/` is an error. A case may have a `lib/` of its
own: its files replace those of the fixture's `lib/` with the same path, or add to it — another
version of the library, for this case alone. `classpath.txt` names cases of the same fixture
whose class output (compiled metamodels and `META-INF` index) is put on the classpath, as the jar
of another module would be; give the cases different packages (`a/G.java`, `b/G.java`), as the
package of the `@Facts` class is the base of the metamodels.

### `expected/`

Exactly what the processor wrote, nothing masked (fingerprints and the format number are real):

- `gen/facts/p/Greeter_.java` — every generated source, at the path of its class;
- `index.txt` — the resources of the class output, one per line, sorted:
  `META-INF/javafile/metamodel/full/p.Greeter -> gen.facts.p.Greeter_`;
- `diagnostics.txt` — what javac and the processor said, in order, as javac prints it:
  `gen/G.java:7:1: warning: p.Pub: no fact of method self() of p.Far, …` (file, line, column of
  the `@Facts` it is reported on; `error: …` alone for one that points nowhere).

A file that would be empty is absent: no `diagnostics.txt` means no diagnostics.

### `expected-jdk/`

The metamodels of the types of the JDK (`gen/facts/java/lang/String_.java`: every source under
`java/`, `javax/` or `jdk/` of the base package) are the same in most cases of a fixture, and a
full `String_` is 18 KB. They are kept **once per fixture**, at the same paths, in
`expected-jdk/` (a fixture with a single `request/` has it too, for the same layout); `index.txt`
of a case still tells which metamodels, JDK ones included, the case has. A case is compared so:

- a JDK file the case writes and `expected/` does not hold must equal the one of `expected-jdk/`;
- a JDK file `expected/` of the case holds is that case's own variant (a full `Object_` where the
  others have a token-only one, another base package) and takes priority — it must differ from the
  shared one, or it is "redundant";
- the shared file of a path is the text most cases write, the least of the texts on a tie, so
  `-Pfixtures.update` gives the same whatever the order of the cases;
- a file of `expected-jdk/` that is not the shared one fails the test `<fixture>: the metamodels of
  the JDK are expected-jdk/`, and `-Pfixtures.update` deletes it. Nothing a case writes is left
  unchecked.

With `-Pfixtures.update` every case of the fixture is run, also when `-Pfixtures.only` names one
case, as the shared files depend on all of them. If the processor
fails, javac keeps no output: `expected/` is `diagnostics.txt` alone, and the case has neither
`use/` nor `use-fails/`.

### `use/`

Java files in the unnamed package (`use/Fields.java`, no `package` line), compiled against the
metamodels, the library and the test classpath (facts, typed, AssertJ) under `-Xlint:all -Werror`.
**A check is a `public static void` method of a `public` class**, without parameters or with one
`Typed`; each is a test named `use/<Class>.<method>` and fails by throwing — use AssertJ.
Anything else (private methods, classes that are not `public`) is a helper.

```java
public final class Fields {
    public static void aFinalFieldIsNotMutable() {
        FieldRef<Greeter, Prim.Int> count = Greeter_.count;      // javac checks the name and the type
        assertThat(count.type()).isSameAs(PrimitiveToken.INT);    // the run checks the behaviour
    }

    public static void anAdoptedMethodIsCalled(Typed typed) {     // through the typed layer:
        assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> call(pub, Pub_.near), new Pub()))
                .isEqualTo("near");                               // rendered, compiled by javac, run
    }
}
```

`typed.apply(result, parameter, body, argument)` renders `static R go(P p) { return <body>; }`
with the typed layer, compiles it against the library alone under `-Xlint:all -Werror`, and calls
it with `argument`.
`typed.render(result, parameter, body)` gives that source without compiling or running it: for
a check on the source, and where the typed layer must reject a fact (`FactLookupException`). The
library is on the classpath of what runs, so an argument of the JDK (a `String`) may be given to a
class of the library.

### `use-fails/`

Each file is compiled on its own and must be rejected **for the reasons its comments tell**: a
line that must not compile ends with `// error: <part of javac's message>`. Every such line must
have an error containing that text, and there must be no error on any other line — a typo or a
missing import fails the test, it does not pass it.

```java
class ATokenOfATypeArgumentOutOfBounds {
    Object inferred = new Sorted_<>(Object_.TOKEN); // error: has incompatible bounds
}
```

## Running

```
./gradlew :java-file-api-facts-processor:test --tests '*FixturesTest'
./gradlew :java-file-api-facts-processor:test --tests '*FixturesTest' -Pfixtures.only=inheritance
./gradlew :java-file-api-facts-processor:test --tests '*FixturesTest' -Pfixtures.only=inheritance/supertypes
```

In the IDE run `FixturesTest` (working directory: the module) and re-run one fixture, case or
test from the tree. The tests of a case, in order: `the output of the processor is expected/`,
`the metamodels compile under -Xlint:all -Xdoclint:all/protected -Werror`, `use/ compiles …`, `use/<Class>.<method>` …,
`use-fails/<File>.java` ….

## Accepting a new output

When the output differs from `expected/`, the first test of the case fails with the files that
differ and, for each, the lines as `diff -u` prints them (`-` expected, `+` actual). If the new
output is right:

```
./gradlew :java-file-api-facts-processor:test --tests '*FixturesTest' -Pfixtures.update
```

rewrites `expected/` of every case (or of `-Pfixtures.only=…`): changed files are overwritten,
new ones added, the ones no longer written deleted. **Read `git diff` of `expected/` before
committing** — that review is the test. Without the flag a missing or an extra expected file
fails the test. Line ends: files are compared with `\n` and written with `\n`;
`.gitattributes` here keeps them so at checkout. Spotless does not touch this directory.

The comment of a metamodel class names the types it is there for — the requested types it is a supertype
of, the types whose signatures mention it —, so the metamodel of a JDK type (`Object_`, `String_`) differs
between the cases that ask for different types, and more cases keep a variant of their own in `expected/`.

To answer "did the output of any test change?" for the tests that keep no snapshot, dump the
output of every run of the processor before and after a change and compare:

```
./gradlew :java-file-api-facts-processor:test -Pfixtures.dump=build/dump/before
./gradlew :java-file-api-facts-processor:test -Pfixtures.dump=build/dump/after
diff -r java-file-api-facts-processor/build/dump/before java-file-api-facts-processor/build/dump/after
```

(only runs made through `harness.Javac` are dumped).

## What goes where

| A check of … | goes to |
|---|---|
| which metamodels are generated, full or token-only | `expected/` (the files and `index.txt`) |
| which facts a metamodel has, their names and order; anything asserted on the source text | `expected/` |
| what the comment of a metamodel says: why it is full or token-only, which members have no fact, the member a fact is of | `expected/` |
| every link of a comment resolves, nothing `public` is without a comment | the test `the metamodels compile under …` of every case: doclint is on |
| warnings and errors, their text and count; "no warnings" | `expected/diagnostics.txt` (or its absence) |
| the output is deterministic | `expected/` (it must match on every run) |
| the family and type arguments of a fact (`isInstanceOf(StaticFieldRef.class)`, casts) | `use/`: `StaticFieldRef<Prim.Int> n = Greeter_.N;` |
| what a fact says at run time: `constantValue()`, `typeRef()`, `owner()`, `traits()`, method tables, shapes | `use/` with AssertJ |
| end to end through the typed layer | `use/` with a `Typed` parameter |
| a member has no fact; a token out of bounds; a fact is not of a mutable family | `use-fails/` (the absence is in `expected/` too) |
| a rule of every generated source; two cases that must agree | `SnapshotsTest` |
| the metamodel of a type of the JDK, whose members are many | `JdkTypesTest` (no snapshot: it is tied to the JDK) |
| several rounds with another processor, the graph, options that are wrong, anything driven step by step | a scenario test on `harness.Javac` |
| a pure function over `javax.lang.model` elements | a unit test with `@ExtendWith(InCompilation.class)` |

A check never disappears: it is pinned by the snapshot, rewritten in `use/` or `use-fails/`, or
stays a test.

## Tests that are not fixtures

A scenario that is driven step by step — another processor that generates sources in a later
round, a `GraphProbe`, libraries compiled in the test — uses the same harness directly:

```java
Path lib = Javac.plain().compile(SVC, DEP).orFail().writeTo(directory);       // a library
Compiled compiled = Javac.facts()                                             // the processor
        .classpath(lib)
        .options("-Ajavafile.facts.package=com.acme")
        .with(new Generating())                                               // runs before it
        .compile(REQUEST);
assertThat(compiled.errors()).containsExactly("…");                           // or warnings()
assertThat(compiled.orFail().sources()).containsKey("com.acme.p.Svc_");       // or resources()
compiled.snapshot().verify(Path.of("src/test/snapshots/MultiroundTest/late"));  // honours -Pfixtures.update
```

`Javac` is an immutable description (`plain()` or `facts()`, then `classpath`, `options`,
`linted()` for `-Xlint:all -Werror`, `alone()` to drop the test classpath, `with(processors)`);
`Compiled` is a value (`succeeded`, `diagnostics`, `generated`, `classOutput`; `orFail()`,
`clean()`, `errors()`, `warnings()`, `sources()`, `resources()`, `snapshot()`, `writeTo(dir)`).
Snapshots of such tests live under `src/test/snapshots/`, never under `src/test/fixtures/`
(every directory there is a fixture).

A processor written in the test to get at `Elements` is a unit test with `@ExtendWith(InCompilation.class)` and
`@InCompilation.Sources(…)`; its parameters are `Elements`, `Types`, `ProcessingEnvironment`, `RoundEnvironment`.
A library the test builds from a file of a fixture — `Source.in(Path.of("src/test/fixtures/reuse/lib"))` — is
the way a scenario test and a fixture share sources instead of writing them twice.

## Adding a fixture or a case

1. Write `lib/` — ordinary Java, one top-level type per file.
2. Write `request/gen/G.java` with `@Facts(…)`; `options.txt` for options (a case per set of
   options: `-Ajavafile.facts.strict=true` is another case with the same `G.java`).
3. Run with `-Pfixtures.update -Pfixtures.only=<fixture>`, and read the `expected/` it wrote.
4. Write `use/` and `use-fails/` against the names in `expected/`; run without the flag.

## Writing the checks of a fixture

1. **Library.** Every type the checks talk about is a file of `lib/`. Libraries of different requests go into the
   one `lib/`; where two declare a type of the same name, rename one — names in messages change with it, behaviour
   does not. A case that needs another version of a type has a `lib/` of its own with that file.
2. **Cases.** One case per distinct request: the same types asked for with the same options are one case. Do not
   merge requests when a check is on the whole output of one (exact sets of sources, "no warnings").
   A request expected to fail is a case with `expected/diagnostics.txt` alone.
3. **Snapshot.** `-Pfixtures.update`, then read `expected/` — it must show every fact and every message the checks
   rely on.
4. **`use/`.** A check on a fact is a typed local, not a cast: `StaticFieldRef<Prim.Int> n = Greeter_.N;`, an
   instance of a generic metamodel is `new Box_<>(String_.TOKEN)`. An end-to-end check through the typed layer is
   `typed.apply(…)` (renders, compiles and runs), `typed.render(…)` where the check is on the rendered source or on
   the typed layer rejecting a fact.
5. **`use-fails/`.** A member that has no fact; a token out of bounds.

Pitfalls:

- `use/` compiles under `-Xlint:all -Werror`: a raw type in a declaration is an error — use `var` for facts of raw
  types (`var raw = box.raw_Map;`), and typed locals elsewhere. A class of `use/` must not be named like one of
  `java.lang` (`Deprecated`).
- Do not request JDK types (`String.class`) unless the test is about them: a full metamodel of a JDK class is a
  large snapshot tied to the JDK version. A mentioned JDK type gets its token-only metamodel anyway, `String_.TOKEN`
  is there to use (not in a library without any `Object`: an interface that mentions no `String` has none — add a
  type of the library to be a witness). Tests of the JDK types are in `JdkTypesTest`, which keeps no snapshot.
- Line and column in `diagnostics.txt` are those of `@Facts` in `G.java`: reformatting `G.java` changes the snapshot.
- The class of `request/` that is the request is `G`: a library type of that name cannot be imported there.
- A check must be able to fail: after writing `use/`, break one expectation and see the test fail with a message
  that tells why.
- The harness itself is tested by `harness/FixtureRunTest`; change the harness there first.
