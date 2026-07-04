plugins {
    alias(conventions.plugins.javafile.java.conventions)
    alias(conventions.plugins.javafile.publishing)
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
