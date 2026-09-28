plugins {
    alias(conventions.plugins.javafile.java.conventions)
    alias(conventions.plugins.javafile.publishing)
}

dependencies {
    api(project(":java-file-api-core"))
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
