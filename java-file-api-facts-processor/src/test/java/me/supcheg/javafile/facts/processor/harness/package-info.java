/// The one harness of the tests of the processor (mini-spec §11, 9t).
///
/// [me.supcheg.javafile.facts.processor.harness.Javac] describes a run of
/// javac — with the processor or without — and
/// [me.supcheg.javafile.facts.processor.harness.Compiled] is what came of
/// it; a [me.supcheg.javafile.facts.processor.harness.Snapshot] is the
/// output of the processor as files, to compare with a directory or to
/// write there. [me.supcheg.javafile.facts.processor.harness.Fixture] reads
/// `src/test/fixtures` (see its `README.md`) and
/// [me.supcheg.javafile.facts.processor.harness.FixtureRun] makes the tests
/// of a fixture; [me.supcheg.javafile.facts.processor.harness.InCompilation]
/// gives a unit test the `Elements` and `Types` of a compilation.
package me.supcheg.javafile.facts.processor.harness;
