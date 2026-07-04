plugins {
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