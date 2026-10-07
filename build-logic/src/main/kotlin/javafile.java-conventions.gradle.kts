import net.ltgt.gradle.errorprone.errorprone

plugins {
    `java-library`
    jacoco
    alias(libs.plugins.errorprone)
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
    api(libs.jspecify)
    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)
    mockitoAgent(libs.mockito.core)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-parameters", "-Xlint:all,-processing", "-Werror"))
        options.errorprone {
            allDisabledChecksAsWarnings = true
            allSuggestionsAsWarnings = true
            disable(
                    "Java8ApiChecker",
                    "Var",
                    "Varifier",
                    "AddNullMarkedToClass",
                    "CanIgnoreReturnValueSuggester",
                    "ImmutableMemberCollection",
                    "UnnecessarilyFullyQualified",
                    "IdentifierName",
                    "BooleanParameter",
                    "UngroupedOverloads",
                    "InconsistentOverloads",
                    "VarWithPrimitive")
            option("NullAway:AnnotatedPackages", "me.supcheg")
            option("NullAway:JSpecifyMode", "true")
            option("NullAway:CheckOptionalEmptiness", "true")
            option("NullAway:CheckContracts", "true")
            option("NullAway:AssertsEnabled", "true")
            option("NullAway:HandleTestAssertionLibraries", "true")
            option("NullAway:ExhaustiveOverride", "true")
            option("NullAway:JSpecifyExperimental", "true")
            if (name.startsWith("compileTest")) {
                disable("NullAway")
            } else {
                error("NullAway")
            }
            disableWarningsInGeneratedCode = true
            excludedPaths = ".*build[/\\\\]generated.*"
        }
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
        systemProperty("junit.jupiter.execution.parallel.enabled", true)
        systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
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
        palantirJavaFormat("2.96.0")
        importOrder("", "javax|java", "\\#")
        forbidWildcardImports()
        // The sources by their directories, not the source sets: those hold build/generated too, which the
        // annotation processor rewrites while the tree is read ("Could not read path ...build/generated...").
        target("src/*/java/**/*.java")
        targetExclude("build/**")
    }
}
