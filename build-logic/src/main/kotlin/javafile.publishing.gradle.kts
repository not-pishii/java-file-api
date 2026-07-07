plugins {
    alias(libs.plugins.vanniktech.maven.publish)
}

mavenPublishing {
    publishToMavenCentral()

    coordinates("me.supcheg", project.name, version.toString())

    pom {
        name = project.name
        description = "Type-safe Java source code generation library"
        url = "https://github.com/not-pishii/java-file-api"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
            }
        }
        developers {
            developer {
                id = "not-pishii"
                name = "Egor Pishii"
                url = "https://github.com/not-pishii"
            }
        }
        scm {
            url = "https://github.com/not-pishii/java-file-api"
            connection = "scm:git:git://github.com/not-pishii/java-file-api.git"
            developerConnection = "scm:git:ssh://git@github.com/not-pishii/java-file-api.git"
        }
    }
}
