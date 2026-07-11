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

tasks {
    val testCodeCoverageReport = named<JacocoReport>("testCodeCoverageReport")

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
