plugins {
    `java-library`
    jacoco
    alias(libs.plugins.spotless)
}

group = "me.supcheg"
version = providers.gradleProperty("releaseVersion").getOrElse("0.0.0")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

val mockitoAgent = configurations.create("mockitoAgent") { isTransitive = false }

dependencies {
    compileOnly(libs.jspecify)
    mockitoAgent(libs.mockito.core)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-parameters", "-Xlint:all,-processing"))
    }
    test {
        abstract class MockitoAgentProvider : CommandLineArgumentProvider {
            @get:Classpath
            abstract val path: RegularFileProperty

            override fun asArguments() = listOf("-javaagent:${path.asFile.get().absolutePath}")
        }

        jvmArgumentProviders.add(objects.newInstance<MockitoAgentProvider>().apply {
            path = mockitoAgent.asFileTree.singleFile
        })
    }
}

testing {
    suites {
        named<JvmTestSuite>("test") {
            useJUnitJupiter(libs.versions.junit)
            dependencies {
                implementation(libs.assertj.core)
                implementation(libs.mockito.junit.jupiter)
            }
        }
    }
}

spotless {
    java {
        palantirJavaFormat()
        importOrder("", "javax|java", "\\#")
        forbidWildcardImports()
        targetExclude("build/**")
    }
}
