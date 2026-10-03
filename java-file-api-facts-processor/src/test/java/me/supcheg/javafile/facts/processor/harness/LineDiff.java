package me.supcheg.javafile.facts.processor.harness;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The difference of two texts by lines, as `diff -u` prints it: hunks of
/// the lines that differ, `-` for those only expected and `+` for those
/// only actual, with a few equal lines around.
final class LineDiff {
    private static final int CONTEXT = 3;

    private LineDiff() {}

    /// One line of the two texts side by side.
    private sealed interface Line {
        String text();

        /// A line both texts have.
        record Same(String text, int expected, int actual) implements Line {}

        /// A line only the expected text has.
        record Removed(String text, int expected, int actual) implements Line {}

        /// A line only the actual text has.
        record Added(String text, int expected, int actual) implements Line {}

        /// The number of the line in the expected text, or of the one it would follow there.
        int expected();

        /// The number of the line in the actual text, or of the one it would follow there.
        int actual();

        default String render() {
            return switch (this) {
                case Same _ -> "  " + text();
                case Removed _ -> "- " + text();
                case Added _ -> "+ " + text();
            };
        }
    }

    /// The hunks that turn `expected` into `actual`; none if the texts are equal.
    ///
    /// @param expected the text expected
    /// @param actual the text there is
    /// @return the lines of the hunks, each hunk after its `@@ -line,count +line,count @@`
    static List<String> of(String expected, String actual) {
        List<Line> lines = aligned(expected.lines().toList(), actual.lines().toList());
        List<Integer> changed = IntStream.range(0, lines.size())
                .filter(i -> !(lines.get(i) instanceof Line.Same))
                .boxed()
                .toList();
        // a hunk is a run of changes no more than twice the context apart
        List<Integer> starts = IntStream.range(0, changed.size())
                .filter(i -> i == 0 || changed.get(i) - changed.get(i - 1) > 2 * CONTEXT)
                .boxed()
                .toList();
        return IntStream.range(0, starts.size())
                .boxed()
                .flatMap(h -> {
                    int first = changed.get(starts.get(h));
                    int last = changed.get((h + 1 < starts.size() ? starts.get(h + 1) : changed.size()) - 1);
                    return hunk(
                            lines.subList(Math.max(0, first - CONTEXT), Math.min(lines.size(), last + CONTEXT + 1)));
                })
                .toList();
    }

    private static Stream<String> hunk(List<Line> lines) {
        long expected = lines.stream().filter(l -> !(l instanceof Line.Added)).count();
        long actual = lines.stream().filter(l -> !(l instanceof Line.Removed)).count();
        Line first = lines.getFirst();
        return Stream.concat(
                Stream.of("@@ -" + first.expected() + "," + expected + " +" + first.actual() + "," + actual + " @@"),
                lines.stream().map(Line::render));
    }

    /// The lines of both texts in order, those of a longest common subsequence as the same.
    private static List<Line> aligned(List<String> expected, List<String> actual) {
        int n = expected.size();
        int m = actual.size();
        // common[i][j]: the length of the longest common subsequence of expected[i..] and actual[j..]
        int[][] common = new int[n + 1][m + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                common[i][j] = expected.get(i).equals(actual.get(j))
                        ? common[i + 1][j + 1] + 1
                        : Math.max(common[i + 1][j], common[i][j + 1]);
            }
        }
        return Stream.iterate(
                        line(expected, actual, common, 0, 0),
                        Optional::isPresent,
                        line -> line.flatMap(at -> switch (at) {
                            case Line.Same(var _, int i, int j) -> line(expected, actual, common, i, j);
                            case Line.Removed(var _, int i, int j) -> line(expected, actual, common, i, j - 1);
                            case Line.Added(var _, int i, int j) -> line(expected, actual, common, i - 1, j);
                        }))
                .map(Optional::orElseThrow)
                .toList();
    }

    /// The line at `expected[i]` and `actual[j]`, if the texts are not both over.
    private static Optional<Line> line(List<String> expected, List<String> actual, int[][] common, int i, int j) {
        int n = expected.size();
        int m = actual.size();
        if (i == n && j == m) {
            return Optional.empty();
        }
        if (i < n && j < m && expected.get(i).equals(actual.get(j))) {
            return Optional.of(new Line.Same(expected.get(i), i + 1, j + 1));
        }
        return Optional.of(
                j == m || i < n && common[i + 1][j] >= common[i][j + 1]
                        ? new Line.Removed(expected.get(i), i + 1, j + 1)
                        : new Line.Added(actual.get(j), i + 1, j + 1));
    }
}
