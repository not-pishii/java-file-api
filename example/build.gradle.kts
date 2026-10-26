plugins {
    id("javafile.java-conventions")
}

dependencies {
    implementation(project(":java-file-api-core"))
    testImplementation(testFixtures(project(":java-file-api-core")))
    testImplementation(libs.jqwik)
}

// The examples of the typed layer (`me.supcheg.javafile.example.typed`) know the types they name by the metamodels
// the `@Facts` processor generates of the `@Facts` of that package, into `me.supcheg.javafile.example.typed.facts`.
dependencies {
    testImplementation(project(":java-file-api-typed"))
    testAnnotationProcessor(project(":java-file-api-facts-processor"))
}

// The typed examples of the README are held to what the README shows (ReadmeTypedExamplesTest), so the tests run
// again when the README, or the generator code it shows, changes.
tasks.test {
    inputs.file(rootProject.layout.projectDirectory.file("README.md"))
    inputs.dir("src/test/java/me/supcheg/javafile/example/typed")
}
