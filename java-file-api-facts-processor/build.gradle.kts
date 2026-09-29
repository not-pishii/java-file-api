plugins {
    alias(conventions.plugins.javafile.java.conventions)
    alias(conventions.plugins.javafile.publishing)
}

dependencies {
    implementation(project(":java-file-api-core"))
    implementation(project(":java-file-api-facts"))
    implementation(project(":java-file-api-lang-model"))
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
