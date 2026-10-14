import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element

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

tasks {
    val testCodeCoverageReport = named<JacocoReport>("testCodeCoverageReport") {
        val classes = classDirectories.files
        reports.xml.required = true
        classDirectories.setFrom(classes.map { fileTree(it) { exclude(generatedMetamodels) } })
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
                }
            }
        }

    val uncoveredBranchesVerification = register<UncoveredBranchesVerification>("uncoveredBranchesVerification") {
        group = "verification"
        description = "Fails on a source line with a missed branch that uncoverable-branches.txt does not list, " +
            "and on an entry of that list that no missed branch answers."

        report.fileProvider(testCodeCoverageReport.map { it.reports.xml.outputLocation.get().asFile })
        sources.from(testCodeCoverageReport.map { it.sourceDirectories })
        allowlist = layout.projectDirectory.file("uncoverable-branches.txt")
    }

    check {
        dependsOn(jacocoAggregatedCoverageVerification, uncoveredBranchesVerification)
    }
}

/// The gate of the branches. A ratio lets an untested condition in for every branch of margin it has, and the
/// compiler makes branches no test can take (the labels of one `case`, the components of a record pattern), so
/// a ratio is either loose or bent to. This gate names every line instead: a source line with a missed branch
/// passes only when `uncoverable-branches.txt` lists it, by its file and its trimmed text — not by its number,
/// which every edit above it moves — and with the reason no test takes the branch. An entry no missed branch
/// answers any longer fails the gate too, so the list cannot outlive what it excuses.
///
/// The list is of entries of two lines:
///
/// ```
/// me/supcheg/javafile/render/Precedence.java :: case EQ, NEQ -> EQUALITY_LEVEL;
///     the reason
/// ```
///
/// A line that starts with `#` is a comment. An entry covers every line of its file that has its text.
abstract class UncoveredBranchesVerification : DefaultTask() {
    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val report: RegularFileProperty

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sources: ConfigurableFileCollection

    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val allowlist: RegularFileProperty

    private data class Line(val file: String, val text: String)

    @TaskAction
    fun verify() {
        val allowed = allowed()
        val missed = missed()
        val unlisted = missed.filterKeys { it !in allowed }
        val stale = allowed.keys - missed.keys
        if (unlisted.isEmpty() && stale.isEmpty()) return
        throw GradleException(
            (unlisted.map { (line, numbers) ->
                "a missed branch that is not listed: ${line.file}:${numbers.joinToString(",")} :: ${line.text}"
            } + stale.map { "a listed line without a missed branch: ${it.file} :: ${it.text}" })
                .joinToString("\n", "${allowlist.get().asFile.name} and the coverage disagree:\n")
        )
    }

    private fun allowed(): Map<Line, String> {
        val lines = allowlist.get().asFile.readLines().filterNot { it.isBlank() || it.startsWith("#") }
        val entries = mutableMapOf<Line, String>()
        var index = 0
        while (index < lines.size) {
            val head = lines[index]
            val separator = head.indexOf(" :: ")
            if (head.first().isWhitespace() || separator < 0) {
                throw GradleException("not an entry '<file> :: <text>': $head")
            }
            val reason = lines.drop(index + 1).takeWhile { it.first().isWhitespace() }
            if (reason.isEmpty()) throw GradleException("an entry without a reason: $head")
            val line = Line(head.substring(0, separator).trim(), head.substring(separator + 4).trim())
            if (entries.put(line, reason.joinToString(" ") { it.trim() }) != null) {
                throw GradleException("an entry listed twice: $head")
            }
            index += 1 + reason.size
        }
        return entries
    }

    private fun missed(): Map<Line, List<Int>> {
        val factory = DocumentBuilderFactory.newInstance()
        // the report names a DTD that is not beside it
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        val root = factory.newDocumentBuilder().parse(report.get().asFile).documentElement
        val roots = sources.files
        val missed = sortedMapOf<Line, MutableList<Int>>(compareBy<Line> { it.file }.thenBy { it.text })
        root.elements("package").forEach { pkg ->
            pkg.elements("sourcefile").forEach { sourceFile ->
                val path = pkg.getAttribute("name") + "/" + sourceFile.getAttribute("name")
                val numbers = sourceFile.elements("line")
                    .filter { it.getAttribute("mb").toInt() > 0 }
                    .map { it.getAttribute("nr").toInt() }
                if (numbers.isNotEmpty()) {
                    val source = roots.map { it.resolve(path) }.singleOrNull { it.isFile }
                        ?: throw GradleException("not one source file of the report: $path")
                    val text = source.readLines()
                    numbers.forEach { missed.getOrPut(Line(path, text[it - 1].trim())) { mutableListOf() }.add(it) }
                }
            }
        }
        return missed
    }

    private fun Element.elements(name: String): List<Element> =
        (0 until childNodes.length).map { childNodes.item(it) }.filterIsInstance<Element>().filter { it.tagName == name }
}
