plugins {
    alias(conventions.plugins.javafile.java.conventions)
}

dependencies {
    implementation(project(":java-file-api-core"))
    implementation(project(":java-file-api-facts"))
}
