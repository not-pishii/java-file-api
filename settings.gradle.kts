pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "java-file-api"

include(
    "java-file-api-core",
    "java-file-api-lang-model",
    "example",
    "aggregation",
)
