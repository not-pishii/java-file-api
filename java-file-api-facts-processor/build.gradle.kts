plugins {
    alias(conventions.plugins.javafile.java.conventions)
    alias(conventions.plugins.javafile.publishing)
}

dependencies {
    implementation(project(":java-file-api-core"))
    implementation(project(":java-file-api-facts"))
    implementation(project(":java-file-api-lang-model"))
    implementation(libs.routine)
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            dependencies {
                implementation(libs.compile.testing)
                // what the typed layer makes of the generated facts
                implementation(project(":java-file-api-typed"))
            }
        }
    }
}

tasks.test {
    // the fixtures are read from the project directory, not from the classpath: see src/test/fixtures/README.md
    inputs.dir("src/test/fixtures").withPropertyName("fixtures").withPathSensitivity(PathSensitivity.RELATIVE)
    // the snapshots of the tests that are not fixtures
    inputs.files(fileTree("src/test/snapshots")).withPropertyName("snapshots").withPathSensitivity(PathSensitivity.RELATIVE)
    // -Pfixtures.update accepts the output of the processor as the snapshots of the fixtures,
    // -Pfixtures.only=<fixture>[/<case>] leaves one of them,
    // -Pfixtures.dump=<directory> writes the output of every run of the processor there
    listOf("fixtures.update", "fixtures.only", "fixtures.dump").forEach { name ->
        providers.gradleProperty(name).orNull?.let { value ->
            systemProperty(name, value)
            outputs.upToDateWhen { false }
        }
    }
}
