plugins {
    alias(conventions.plugins.javafile.java.conventions)
    alias(conventions.plugins.javafile.publishing)
    `java-test-fixtures`
}

// The oracle the tests of the modules share (src/testFixtures): the Java a JavaFile renders, compiled by javac with
// every lint. It is a tool of the tests and not a part of the library, so the variants of it are left out of what is
// published.
dependencies {
    testFixturesApi(libs.compile.testing)
}

// (the publishing plugin adds the variant of the sources of the fixtures as it is done with the project, hence
// afterEvaluate)
afterEvaluate {
    val java = components["java"] as AdhocComponentWithVariants
    configurations.filter { it.name.startsWith("testFixtures") && it.name.endsWith("Elements") }.forEach {
        java.withVariantsFromConfiguration(it) { skip() }
    }
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            dependencies {
                implementation(libs.jqwik)
            }
        }
    }
}
