plugins {
    base
    `test-report-aggregation`
    `jacoco-report-aggregation`
}

dependencies {
    rootProject.subprojects.filter { it != project }.forEach { testReportAggregation(it); jacocoAggregation(it) }
}

reporting {
    reports {
        create<AggregateTestReport>("testAggregateTestReport") {
            testSuiteName = "test"
        }
        create<JacocoCoverageReport>("testCodeCoverageReport") {
            testSuiteName = "test"
        }
    }
}

// The metamodels the `@Facts` processor generates are no code of this project to cover: JaCoCo leaves out a class
// annotated `@GeneratedMetamodel` or `@GeneratedMetamodelPart` by the name of the annotation, and the classes nested
// in one, which carry no annotation of their own, are left out here — those of the one module whose main code is
// built with generated metamodels. What the processor generates is tested by its own fixtures.
val generatedMetamodels = listOf("me/supcheg/javafile/typed/jdk/facts/**")

// `InvocableFactories` is the generated superclass `UnsafeFacts` has its factories from. The factories are static and
// tested at every arity (`ArityNFamiliesTest`); the constructor is the one line of the class no code can reach — its
// one subclass is never instantiated — and Java has no class without a constructor.
val uninstantiable = listOf("me/supcheg/javafile/facts/InvocableFactories.class")

tasks {
    val testCodeCoverageReport = named<JacocoReport>("testCodeCoverageReport") {
        val classes = classDirectories.files
        classDirectories.setFrom(classes.map { fileTree(it) { exclude(generatedMetamodels + uninstantiable) } })
    }

    val jacocoAggregatedCoverageVerification =
        register<JacocoCoverageVerification>("jacocoAggregatedCoverageVerification") {
            group = "verification"

            dependsOn(testCodeCoverageReport)
            executionData.setFrom(testCodeCoverageReport.map { it.executionData })
            classDirectories.setFrom(testCodeCoverageReport.map { it.classDirectories })
            sourceDirectories.setFrom(testCodeCoverageReport.map { it.sourceDirectories })

            violationRules {
                rule {
                    limit {
                        counter = "LINE"
                        minimum = "1.00".toBigDecimal()
                    }
                    limit {
                        counter = "METHOD"
                        minimum = "1.00".toBigDecimal()
                    }
                    limit {
                        counter = "CLASS"
                        minimum = "1.00".toBigDecimal()
                    }
                    limit {
                        counter = "BRANCH"
                        minimum = "0.98".toBigDecimal()
                    }
                }
            }
        }

    check {
        dependsOn(jacocoAggregatedCoverageVerification)
    }
}
