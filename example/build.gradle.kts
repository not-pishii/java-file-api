plugins {
    id("javafile.java-conventions")
}

dependencies {
    implementation(project(":java-file-api-core"))
    testImplementation(libs.compile.testing)
    testImplementation(libs.jqwik)
}

// The examples of the typed layer (`me.supcheg.javafile.example.typed`) know the types they name by the metamodels
// the `@Facts` processor generates of the `@Facts` of that package, into `me.supcheg.javafile.example.typed.facts`.
dependencies {
    testImplementation(project(":java-file-api-typed"))
    testAnnotationProcessor(project(":java-file-api-facts-processor"))
}
