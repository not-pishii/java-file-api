plugins {
    alias(conventions.plugins.javafile.java.conventions)
    alias(conventions.plugins.javafile.publishing)
}

// Phase 1: the arity families (call/staticCall/new_ per MethodRefN/CtorRefN,
// method/constructor declaration combinators, ...) are hand-written under
// src/main for arities 0..3 (0..2 for declarations). Raising the ceiling to
// match java-file-api-facts's MAX_ARITY = 12 is future work: a generator
// analogous to FactsCodegen (java-file-api-facts/src/codegen), written on
// java-file-api-core, is the natural way to do it — see the phase-1 report's
// open questions.

dependencies {
    api(project(":java-file-api-core"))
    api(project(":java-file-api-facts"))
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            dependencies {
                implementation(libs.compile.testing)
            }
        }
    }
}

// The typed layer builds the metamodels of the JDK types it needs with the `@Facts` processor (Q12), and keeps them
// to itself: `-Ajavafile.facts.index=false` lists none of them for the processors of other modules. The processor
// takes `java-file-api-typed` on its own test classpath, which is no cycle: that is its tests' dependency, and the
// processor jar does not depend on the tests.
dependencies {
    annotationProcessor(project(":java-file-api-facts-processor"))
    testAnnotationProcessor(project(":java-file-api-facts-processor"))
}

tasks.compileJava {
    options.compilerArgs.addAll(
        listOf("-Ajavafile.facts.package=me.supcheg.javafile.typed.jdk.facts", "-Ajavafile.facts.index=false")
    )
}

tasks.compileTestJava {
    options.compilerArgs.addAll(
        listOf("-Ajavafile.facts.package=me.supcheg.javafile.typed.testfacts", "-Ajavafile.facts.index=false")
    )
}
