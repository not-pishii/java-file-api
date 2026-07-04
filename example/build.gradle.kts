plugins {
    id("javafile.java-conventions")
}

dependencies {
    implementation(project(":java-file-api-core"))
    testImplementation(libs.compile.testing)
}
